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
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import fr.onsiea.engine.client.graphics.glfw.window.Window;
import fr.onsiea.engine.client.graphics.opengl.OpenGLRenderAPIContext;
import fr.onsiea.engine.client.graphics.opengl.mesh.GLMesh;
import fr.onsiea.engine.client.graphics.opengl.nanovg.NanoVGManager;
import fr.onsiea.engine.client.graphics.opengl.shader.InstancedShader;
import fr.onsiea.engine.client.graphics.opengl.shader.Shader;
import fr.onsiea.engine.client.graphics.opengl.shader.ShaderBasic;
import fr.onsiea.engine.client.graphics.opengl.utils.OpenGLUtils;
import fr.onsiea.engine.client.graphics.particles.IParticleSystem;
import fr.onsiea.engine.client.graphics.particles.Particle;
import fr.onsiea.engine.client.graphics.particles.ParticleManager;
import fr.onsiea.engine.client.graphics.render.IRenderAPIContext;
import fr.onsiea.engine.client.graphics.texture.ITexture;
import fr.onsiea.engine.client.graphics.window.IWindow;
import fr.onsiea.engine.client.input.InputManager;
import fr.onsiea.engine.common.OnsieaGearings;
import fr.onsiea.engine.common.game.GameOptions;
import fr.onsiea.engine.common.game.IGameLogic;
import fr.onsiea.engine.core.entity.Camera;
import fr.onsiea.engine.utils.maths.MathInstances;
import fr.onsiea.engine.utils.maths.MathUtils;
import fr.onsiea.engine.utils.time.Timer;

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

	private NanoVGManager				nanoVG;
	private int							vao;
	private int							vbo;
	private ShaderBasic					shaderBasic;
	private InstancedShader				instancedShader;
	private Camera						camera;
	private NVGColor					color;

	private Timer						cameraTimer;

	private ITexture					texture;

	private GLMesh						mesh;

	private Matrix4f					projView;

	private ParticleManager<Particle>	particleManager;

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
			this.nanoVG	= new NanoVGManager(windowIn);
			this.color	= NVGColor.malloc();
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		this.texture = ((OpenGLRenderAPIContext) renderAPIContextIn).texturesManager()
				.load("resources/textures/aeison.png");

		try
		{
			this.mesh = ((OpenGLRenderAPIContext) renderAPIContextIn).objLoader()
					.loadMesh("resources\\models\\barrel.obj");//((OpenGLRenderAPIContext) renderAPIContextIn).meshManager()
			//.build(Cube.interleaveIndicesPositionsAndTextures, Cube.positionsAndTextureCoordinates, 3, 2);
		}
		catch (final Exception e1)
		{
			e1.printStackTrace();
		}

		this.camera			= new Camera();
		this.cameraTimer	= new Timer();

		try
		{
			this.shaderBasic = new ShaderBasic();

			this.shaderBasic.start();
			this.shaderBasic.projectionMatrix().load(MathInstances.projectionMatrix());

			Shader.stop();

			this.instancedShader = new InstancedShader();

			this.instancedShader.start();
			this.instancedShader.projectionMatrix().load(MathInstances.projectionMatrix());
			Shader.stop();
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		this.projView = new Matrix4f(MathInstances.projectionMatrix()).identity();
		this.projView.mul(this.camera.viewMatrix());

		try
		{
			this.particleManager = new ParticleManager<>(new IParticleSystem<Particle>()
			{
				@Override
				public IParticleSystem<Particle> initialization(List<Particle> particlesIn,
						ParticleManager<Particle> particleManagerIn)
				{
					for (var i = 0; i < 100; i++)
					{
						final var particle = new Particle();

						particle.position().x		= MathUtils.randomInt(4, 50);
						particle.position().y		= MathUtils.randomInt(4, 50);
						particle.position().z		= MathUtils.randomInt(4, 50);
						particle.orientation().x	= MathUtils.randomInt(0, 360);
						particle.orientation().y	= MathUtils.randomInt(0, 360);
						particle.orientation().z	= MathUtils.randomInt(0, 360);
						particle.scale().x			= MathUtils.randomInt(0, 2);
						particle.scale().y			= MathUtils.randomInt(0, 2);
						particle.scale().z			= MathUtils.randomInt(0, 2);

						particlesIn.add(particle);
					}

					return this;
				}

				@Override
				public IParticleSystem<Particle> update(Particle particleIn,
						ParticleManager<Particle> particleManagerIn)
				{
					particleIn.position().x		+= MathUtils.randomInt(-1, 1) / 12.0f;
					particleIn.position().y		+= MathUtils.randomInt(0, 1);
					particleIn.position().z		+= MathUtils.randomInt(-1, 1) / 12.0f;
					particleIn.orientation().x	+= MathUtils.randomInt(-10, 10);
					particleIn.orientation().y	+= MathUtils.randomInt(-10, 10);
					particleIn.orientation().z	+= MathUtils.randomInt(-10, 10);
					//particleIn.scale().x			+= MathUtils.randomInt(-1, 1);
					//particleIn.scale().y			+= MathUtils.randomInt(-1, 1);
					//particleIn.scale().z			+= MathUtils.randomInt(-1, 1);

					return this;
				}
			}, 100, ((OpenGLRenderAPIContext) renderAPIContextIn).meshManager());
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		return true;
	}

	@Override
	public void highRateInput()
	{
	}

	@Override
	public void input(IWindow windowIn, InputManager inputManagerIn)
	{
		if (this.cameraTimer.isTime(1_000_000_0L))
		{
			this.camera.input((Window) windowIn, inputManagerIn);
		}
	}

	@Override
	public void update()
	{
	}

	@Override
	public void draw(IWindow windowIn)
	{
		this.projView.set(MathInstances.projectionMatrix()).mul(this.camera.viewMatrix());
		try
		{
			this.particleManager.update(this.projView);
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		OpenGLUtils.restoreState();
		//GL11.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_COLOR_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
		this.shaderBasic.start();

		this.shaderBasic.viewMatrix().load(this.camera.viewMatrix());
		this.shaderBasic.transformationsMatrix().load(MathInstances.simpleTransformationsMatrix3d());

		this.texture.attach();

		this.mesh.startDrawing(null);
		this.mesh.draw(null);
		this.mesh.stopDrawing(null);

		Shader.stop();

		this.instancedShader.start();

		this.instancedShader.viewMatrix().load(this.camera.viewMatrix());

		this.particleManager.draw();

		this.texture.detach();

		Shader.stop();

		/**
		this.nanoVG.startRender(windowIn);
		this.nanoVG.nanoVGFonts().draw(42, "ARIAL", NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP,
				NanoVGManager.set(this.color, 100, 125, 127, 255), 0, 0, "Ceci est un texte !");
		this.nanoVG.finishRender();**/
	}

	@Override
	public void cleanup()
	{
		if (this.color != null)
		{
			this.color.free();
		}

		this.nanoVG.cleanup();

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glDeleteBuffers(this.vbo);
		GL30.glBindVertexArray(0);
		GL30.glDeleteVertexArrays(this.vao);
	}
}