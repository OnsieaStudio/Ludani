/**
* Copyright 2021 Onsiea All rights reserved.<br><br>
*
* This file is part of Onsiea Engine project. (https://github.com/Onsiea/OnsieaEngine)<br><br>
*
* Onsiea Engine is [licensed] (https://github.com/Onsiea/OnsieaEngine/blob/main/LICENSE) under the terms of the "GNU General Public Lesser License v2.1" (LGPL-2.1).
* https://github.com/Onsiea/OnsieaEngine/wiki/License#license-and-copyright<br><br>
*
* Onsiea Engine is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 2.1 of the License, or
* (at your option) any later version.<br><br>
*
* Onsiea Engine is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU Lesser General Public License for more details.<br><br>
*
* You should have received a copy of the GNU Lesser General Public License
* along with Onsiea Engine.  If not, see <https://www.gnu.org/licenses/>.<br><br>
*
* Neither the name "Onsiea", "Onsiea Engine", or any derivative name or the names of its authors / contributors may be used to endorse or promote products derived from this software and even less to name another project or other work without clear and precise permissions written in advance.<br><br>
*
* @Author : Seynax (https://github.com/seynax)<br>
* @Organization : Onsiea Studio (https://github.com/Onsiea)
*/
package fr.onsiea.engine.game;

import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import fr.onsiea.engine.client.graphics.glfw.window.Window;
import fr.onsiea.engine.client.graphics.material.Material;
import fr.onsiea.engine.client.graphics.opengl.fbo.FBO;
import fr.onsiea.engine.client.graphics.opengl.flare.FlareManager;
import fr.onsiea.engine.client.graphics.opengl.flare.FlareTexture;
import fr.onsiea.engine.client.graphics.opengl.nanovg.NanoVGManager;
import fr.onsiea.engine.client.graphics.opengl.postprocessing.PostProcessing;
import fr.onsiea.engine.client.graphics.opengl.shaders.InstancedShader;
import fr.onsiea.engine.client.graphics.opengl.utils.OpenGLUtils;
import fr.onsiea.engine.client.graphics.particles.IParticleSystem;
import fr.onsiea.engine.client.graphics.particles.ParticleManager;
import fr.onsiea.engine.client.graphics.particles.ParticleWithLifeTime;
import fr.onsiea.engine.client.graphics.render.IRenderAPIContext;
import fr.onsiea.engine.client.graphics.render.Renderer;
import fr.onsiea.engine.client.graphics.shader.IShadersManager;
import fr.onsiea.engine.client.graphics.texture.ITexture;
import fr.onsiea.engine.client.graphics.window.IWindow;
import fr.onsiea.engine.client.input.InputManager;
import fr.onsiea.engine.common.OnsieaGearings;
import fr.onsiea.engine.common.game.GameOptions;
import fr.onsiea.engine.common.game.IGameLogic;
import fr.onsiea.engine.game.scene.GameScene;
import fr.onsiea.engine.utils.maths.MathInstances;
import fr.onsiea.engine.utils.maths.MathUtils;

/**
 * @author Seynax
 *
 */
public class GameTest implements IGameLogic
{
	public final static void main(String[] argsIn)
	{
		try
		{
			OnsieaGearings.start(new GameTest(), GameOptions.of(), argsIn);
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

	private NanoVGManager							nanoVG;

	private ITexture								particleTexture;

	private Matrix4f								projView;

	private ParticleManager<ParticleWithLifeTime>	particleManager;

	private FlareManager							flareManager;
	private PostProcessing							postProcessing;

	private FBO										fbo;

	private GameScene								scene;

	private InstancedShader							shader;
	private IShadersManager							shadersManager;

	@Override
	public boolean preInitialization()
	{
		return true;
	}

	@Override
	public boolean initialization(IWindow windowIn, IRenderAPIContext renderAPIContextIn)
	{
		try
		{
			((Window) windowIn).icon("resources/textures/aeison.png");
			this.nanoVG = new NanoVGManager(windowIn);
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		try
		{
			this.particleTexture	= renderAPIContextIn.texturesManager().load("resources/textures/particle.png");
			this.scene				= new GameScene(renderAPIContextIn);
			this.scene.add("barrel", "resources\\models\\barrel.obj",
					new Material(new Vector4f(1.0f), new Vector4f(1.0f), new Vector4f(1.0f),
							renderAPIContextIn.texturesManager().load("resources/textures/barrel.png"), 1.025f,
							renderAPIContextIn.texturesManager().load("resources/textures/barrelNormal.png")),
					MathInstances.simpleTransformationsMatrix3d());
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
		this.projView = new Matrix4f(MathInstances.projectionMatrix()).identity();
		this.projView.mul(this.scene.camera().view());

		try
		{
			this.particleManager	= new ParticleManager<>(new IParticleSystem<ParticleWithLifeTime>()
									{
										private long last0;

										@Override
										public IParticleSystem<ParticleWithLifeTime> initialization(
												List<ParticleWithLifeTime> particlesIn,
												ParticleManager<ParticleWithLifeTime> particleManagerIn)
										{
											for (var i = 0; i < 10; i++)
											{
												final var particle = new ParticleWithLifeTime(
														GameTest.this.scene.camera(),
														MathUtils.randomLong(2_500_000L, 24_000_000_000L),
														MathUtils.randomInt(0, 1), MathUtils.randomInt(0, 2),
														MathUtils.randomInt(0, 1));

												particle.position().x		= MathUtils.randomInt(0, 40);
												particle.position().y		= MathUtils.randomInt(0, 40);
												particle.position().z		= MathUtils.randomInt(0, 40);
												particle.orientation().x	= MathUtils.randomInt(0, 360);
												particle.orientation().y	= MathUtils.randomInt(0, 360);
												particle.orientation().z	= MathUtils.randomInt(0, 360);
												particle.scale().x			= MathUtils.randomInt(1, 2) / 4.0f;
												particle.scale().y			= MathUtils.randomInt(1, 2) / 4.0f;
												particle.scale().z			= MathUtils.randomInt(1, 2) / 4.0f;

												particlesIn.add(particle);
											}

											return this;
										}

										@Override
										public boolean updateParticle(ParticleWithLifeTime particleIn,
												ParticleManager<ParticleWithLifeTime> particleManagerIn)
										{
											final var actual = System.nanoTime();
											if (MathUtils.randomInt(0, 5000) == 0
													|| actual - particleIn.lastLifeTime() >= particleIn.lifeTime()
													|| particleIn.position().y() <= 0)
											{
												return true;
											}

											if (actual - particleIn.last > 2_500_000_0L)
											{
												particleIn.last = actual;
												particleIn.texX++;

												if (particleIn.texX() >= 4)
												{
													particleIn.texX = 0;
													particleIn.texY++;
												}

												if (particleIn.texY() >= 4)
												{
													particleIn.texX	= 0;
													particleIn.texY	= 0;
												}
											}

											particleIn.position().x		+= MathUtils.randomInt(-1, 1) / 14.0f;
											particleIn.velocity.y		-= 9.8f * 1.0f * (1.0f / 60.0f);

											particleIn.position().y		+= particleIn.velocity.y();
											particleIn.position().z		+= MathUtils.randomInt(-1, 1) / 14.0f;
											particleIn.orientation().x	+= MathUtils.randomInt(-50, 50);
											particleIn.orientation().y	+= MathUtils.randomInt(-50, 50);
											particleIn.orientation().z	+= MathUtils.randomInt(-50, 50);
											particleIn.scale().x		+= MathUtils.randomInt(-1, 1) / 2.0f;
											particleIn.scale().y		+= MathUtils.randomInt(-1, 1) / 2.0f;
											particleIn.scale().z		+= MathUtils.randomInt(-1, 1) / 2.0f;

											return false;
										}

										@Override
										public IParticleSystem<ParticleWithLifeTime> update(
												List<ParticleWithLifeTime> particlesIn,
												ParticleManager<ParticleWithLifeTime> particleManagerIn)
										{
											if (particlesIn.size() >= 1_000)
											{
												return this;
											}

											final var actual = System.nanoTime();
											if (actual - this.last0 > 1_000_000L)
											{
												for (var i = 0; i < Math.min(1000 - particlesIn.size(), 10); i++)
												{
													if (MathUtils.randomInt(0, 1) > 0)
													{
														continue;
													}

													final var particle = new ParticleWithLifeTime(
															GameTest.this.scene.camera(),
															MathUtils.randomLong(2_500_000L, 24_000_000_000L),
															MathUtils.randomInt(0, 1), MathUtils.randomInt(0, 2),
															MathUtils.randomInt(0, 1));

													particle.position().x		= MathUtils.randomInt(0, 2);
													particle.position().y		= MathUtils.randomInt(0, 2);
													particle.position().z		= MathUtils.randomInt(0, 2);
													particle.orientation().x	= MathUtils.randomInt(0, 360);
													particle.orientation().y	= MathUtils.randomInt(0, 360);
													particle.orientation().z	= MathUtils.randomInt(0, 360);
													particle.scale().x			= MathUtils.randomInt(1, 2) / 4.0f;
													particle.scale().y			= MathUtils.randomInt(1, 2) / 4.0f;
													particle.scale().z			= MathUtils.randomInt(1, 2) / 4.0f;

													particlesIn.add(particle);
												}
												this.last0 = actual;
											}

											return this;
										}
									},
					100, renderAPIContextIn.meshsManager());

			this.postProcessing		= new PostProcessing(windowIn, renderAPIContextIn);
			this.fbo				= new FBO(windowIn.settings().width(), windowIn.settings().height(), windowIn);

			this.flareManager		= new FlareManager(0.125f, renderAPIContextIn,
					new FlareTexture(renderAPIContextIn.texturesManager().load("tex1.png", GL11.GL_LINEAR_MIPMAP_LINEAR,
							GL11.GL_LINEAR, GL12.GL_CLAMP_TO_EDGE, GL12.GL_CLAMP_TO_EDGE, true), 0.75f / 2.0f),
					new FlareTexture(renderAPIContextIn.texturesManager().load("tex2.png", GL11.GL_LINEAR_MIPMAP_LINEAR,
							GL11.GL_LINEAR, GL12.GL_CLAMP_TO_EDGE, GL12.GL_CLAMP_TO_EDGE, true), 0.50f / 2.0f),
					new FlareTexture(renderAPIContextIn.texturesManager().load("tex3.png", GL11.GL_LINEAR_MIPMAP_LINEAR,
							GL11.GL_LINEAR, GL12.GL_CLAMP_TO_EDGE, GL12.GL_CLAMP_TO_EDGE, true), 0.25f / 2.0f),
					new FlareTexture(renderAPIContextIn.texturesManager().load("tex4.png", GL11.GL_LINEAR_MIPMAP_LINEAR,
							GL11.GL_LINEAR, GL12.GL_CLAMP_TO_EDGE, GL12.GL_CLAMP_TO_EDGE, true), 0.125f / 2.0f),
					new FlareTexture(renderAPIContextIn.texturesManager().load("tex4.png", GL11.GL_LINEAR_MIPMAP_LINEAR,
							GL11.GL_LINEAR, GL12.GL_CLAMP_TO_EDGE, GL12.GL_CLAMP_TO_EDGE, true), 0.075f / 2.0f),
					new FlareTexture(renderAPIContextIn.texturesManager().load("tex5.png", GL11.GL_LINEAR_MIPMAP_LINEAR,
							GL11.GL_LINEAR, GL12.GL_CLAMP_TO_EDGE, GL12.GL_CLAMP_TO_EDGE, true), 0.050f / 2.0f),
					new FlareTexture(renderAPIContextIn.texturesManager().load("tex6.png", GL11.GL_LINEAR_MIPMAP_LINEAR,
							GL11.GL_LINEAR, GL12.GL_CLAMP_TO_EDGE, GL12.GL_CLAMP_TO_EDGE, true), 0.025f / 2.0f),
					new FlareTexture(
							renderAPIContextIn.texturesManager().load("tex7.png", GL11.GL_LINEAR_MIPMAP_LINEAR,
									GL11.GL_LINEAR, GL12.GL_CLAMP_TO_EDGE, GL12.GL_CLAMP_TO_EDGE, true),
							0.0125f / 2.0f),
					new FlareTexture(
							renderAPIContextIn.texturesManager().load("tex8.png", GL11.GL_LINEAR_MIPMAP_LINEAR,
									GL11.GL_LINEAR, GL12.GL_CLAMP_TO_EDGE, GL12.GL_CLAMP_TO_EDGE, true),
							0.0075f / 2.0f),
					new FlareTexture(
							renderAPIContextIn.texturesManager().load("tex9.png", GL11.GL_LINEAR_MIPMAP_LINEAR,
									GL11.GL_LINEAR, GL12.GL_CLAMP_TO_EDGE, GL12.GL_CLAMP_TO_EDGE, true),
							0.0050f / 2.0f));
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		this.shadersManager	= renderAPIContextIn.shadersManager();
		this.shader			= (InstancedShader) renderAPIContextIn.shadersManager().get("instanced");

		return true;
	}

	@Override
	public void highRateInput()
	{
	}

	private static boolean e;

	@Override
	public void input(IWindow windowIn, InputManager inputManagerIn)
	{
		this.scene.input(windowIn, inputManagerIn);
		if (inputManagerIn.glfwGetKey(GLFW.GLFW_KEY_0) == GLFW.GLFW_PRESS && !GameTest.e)
		{
			this.FBO = !this.FBO;
			System.out.println(this.FBO);

			GameTest.e = true;
		}
		else if (GameTest.e && inputManagerIn.glfwGetKey(GLFW.GLFW_KEY_0) == GLFW.GLFW_RELEASE)
		{
			GameTest.e = false;
		}
	}

	@Override
	public void update()
	{
	}

	private boolean FBO = false;

	@Override
	public void draw(IWindow windowIn, IRenderAPIContext renderAPIContextIn, Renderer rendererIn)
	{
		this.projView.set(MathInstances.projectionMatrix()).mul(this.scene.camera().view());
		try
		{
			this.particleManager.update(this.projView);
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		if (this.FBO)
		{
			this.fbo.start();
		}

		renderAPIContextIn.shadersManager().updateView(this.scene.camera());
		GL11.glClearColor(0.125f, 0.125f, 0.25f, 1.0f);
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_COLOR_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
		OpenGLUtils.restoreState();

		this.scene.draw();

		this.shader.attach();

		this.shader.rowsAndColumns().load(4.0f, 4.0f);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		this.particleTexture.attach();

		this.particleManager.draw();

		this.particleTexture.detach();
		GL11.glDisable(GL11.GL_BLEND);

		this.shadersManager.detach();

		//this.flareManager.render(this.scene.camera(), new Vector3f(0.0f, 0.0f, 0.0f), windowIn, rendererIn);

		if (this.FBO)
		{
			this.fbo.stop(windowIn);

			this.postProcessing.doPostProcessing(this.fbo.colourTexture(), windowIn);
		}

		/**this.nanoVG.startRender(windowIn);
		this.nanoVG.nanoVGFonts().draw(42, "ARIAL", NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP,
				NanoVGManager.set(this.color, 100, 125, 127, 255), 0, 0, "Ceci est un texte !");
		this.nanoVG.finishRender();**/
	}

	@Override
	public void cleanup()
	{
		if (this.nanoVG != null)
		{
			this.nanoVG.cleanup();
		}
	}
}