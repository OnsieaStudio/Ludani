
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

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;

import fr.onsiea.engine.client.graphics.GraphicsConstants;
import fr.onsiea.engine.client.graphics.fog.Fog;
import fr.onsiea.engine.client.graphics.light.DirectionalLight;
import fr.onsiea.engine.client.graphics.light.PointLight;
import fr.onsiea.engine.client.graphics.light.PointLight.Attenuation;
import fr.onsiea.engine.client.graphics.material.Material;
import fr.onsiea.engine.client.graphics.mesh.md5.MD5AnimModel;
import fr.onsiea.engine.client.graphics.mesh.md5.MD5Loader;
import fr.onsiea.engine.client.graphics.mesh.md5.MD5Model;
import fr.onsiea.engine.client.graphics.mesh.md5.MD5Utils;
import fr.onsiea.engine.client.graphics.opengl.flare.FlareManager;
import fr.onsiea.engine.client.graphics.opengl.shaders.InstancedShader;
import fr.onsiea.engine.client.graphics.opengl.shaders.Shader2D;
import fr.onsiea.engine.client.graphics.opengl.shaders.ShaderAnimatedBasic;
import fr.onsiea.engine.client.graphics.opengl.shaders.ShaderBasic;
import fr.onsiea.engine.client.graphics.opengl.shaders.ShaderSkybox;
import fr.onsiea.engine.client.graphics.opengl.shaders.effects.AnimatedShadowShader;
import fr.onsiea.engine.client.graphics.opengl.shaders.effects.FlareShader;
import fr.onsiea.engine.client.graphics.opengl.shaders.effects.ShadowShader;
import fr.onsiea.engine.client.graphics.opengl.shaders.normalMapping.ShaderNormalMappingThinMatrix;
import fr.onsiea.engine.client.graphics.opengl.shaders.postprocessing.BrightFilterShader;
import fr.onsiea.engine.client.graphics.opengl.shaders.postprocessing.CombineShader;
import fr.onsiea.engine.client.graphics.opengl.shaders.postprocessing.ContrastShader;
import fr.onsiea.engine.client.graphics.opengl.shaders.postprocessing.HorizontalBlurShader;
import fr.onsiea.engine.client.graphics.opengl.shaders.postprocessing.VerticalBlurShader;
import fr.onsiea.engine.client.graphics.opengl.skybox.SkyboxRenderer;
import fr.onsiea.engine.client.graphics.render.IRenderAPIContext;
import fr.onsiea.engine.client.graphics.render.Renderer;
import fr.onsiea.engine.client.graphics.shapes.ShapeCube;
import fr.onsiea.engine.client.graphics.window.IWindow;
import fr.onsiea.engine.client.input.InputManager;
import fr.onsiea.engine.client.resources.ResourcesPath;
import fr.onsiea.engine.common.OnsieaGearings;
import fr.onsiea.engine.common.game.GameOptions;
import fr.onsiea.engine.common.game.IGameLogic;
import fr.onsiea.engine.game.scene.Scene;
import fr.onsiea.engine.game.scene.item.GameAnimatedItemProperties;
import fr.onsiea.engine.game.scene.item.GameItem;
import fr.onsiea.engine.game.scene.item.GameItemProperties;
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

	public final static Matrix4f	temp				= new Matrix4f().identity();
	public static int				selectedJointMatrix	= 15;
	public final static Vector3f	position			= new Vector3f();

	private Scene					scene;
	private GameItem				gameItem;
	private MD5Loader				md5Loader;
	private PointLight				pointLight;
	private Timer					updateTimer;
	private boolean					mustBeBlockedIsPressed;

	@Override
	public boolean preInitialization()
	{
		return true;
	}

	@Override
	public boolean initialization(IWindow windowIn, IRenderAPIContext renderAPIContextIn) throws Exception
	{
		this.md5Loader = new MD5Loader();
		this.md5Loader.link(renderAPIContextIn.meshsManager(), renderAPIContextIn.texturesManager());

		renderAPIContextIn.shadersManager().add("normalMappingThinMatrix", new ShaderNormalMappingThinMatrix());
		renderAPIContextIn.shadersManager().add("scene", new ShaderBasic(ShaderBasic.Normal.SCENE));
		renderAPIContextIn.shadersManager().add("animatedScene", new ShaderAnimatedBasic());
		renderAPIContextIn.shadersManager().add("shadow", new ShadowShader());
		renderAPIContextIn.shadersManager().add("animatedShadow", new AnimatedShadowShader());
		renderAPIContextIn.shadersManager().add("flare", new FlareShader());
		renderAPIContextIn.shadersManager().add("2D", new Shader2D());
		renderAPIContextIn.shadersManager().add("skybox", new ShaderSkybox());
		renderAPIContextIn.shadersManager().add("brightFilter", new BrightFilterShader());
		renderAPIContextIn.shadersManager().add("combineFilter", new CombineShader());
		renderAPIContextIn.shadersManager().add("contrastChanger", new ContrastShader());
		renderAPIContextIn.shadersManager().add("horizontalBlur", new HorizontalBlurShader());
		renderAPIContextIn.shadersManager().add("verticalBlur", new VerticalBlurShader());
		renderAPIContextIn.shadersManager().add("instanced", new InstancedShader());

		final var skyboxRenderer = new SkyboxRenderer(renderAPIContextIn.shadersManager(),
				renderAPIContextIn.meshsManager().create(ShapeCube.withSize(100.0f), ShapeCube.INDICES, 3),
				renderAPIContextIn.texturesManager().loadCubeMapTextures("skybox",
						ResourcesPath.of(new ResourcesPath(GraphicsConstants.TEXTURES, "skybox"))));

		this.scene		= new Scene(renderAPIContextIn, windowIn, 0.125f, 0.125f, 0.125f,
				new DirectionalLight(new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(0.00f, 1.00f, 1.00f), 1.0f)
						.shadowPosMult(8).orthoCords(-10.0f, 10.0f, -10.0f, 10.0f, -1.0f, 20.0f),
				10.0f, new Vector3f(0.125f, 0.125f, 0.125f),
				new Fog(true, new Vector3f(0.125f, 0.125f, 0.125f), 0.00050f, 0.75f),
				new FlareManager(0.125f, renderAPIContextIn, i -> 0.75f / (3.0f * i), i -> "tex" + (i + 1) + ".png", 9),
				skyboxRenderer);

		this.gameItem	= new GameItem(new Vector3f(105 + 2.0f * 4.0f * 4.0f + 8.0f, 16, -75),
				new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(4.0f, 4.0f, 4.0f));

		final var	reflectance	= 0.5f;
		/**this.scene.sceneItems().add(
				new GameItemProperties("rockPlane",
						new Material(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), new Vector4f(1.0f, 1.0f, 1.0f, 1.0f),
								new Vector4f(1.0f, 1.0f, 1.0f, 1.0f),
								renderAPIContextIn.texturesManager().load("resources/textures/rock.png"), reflectance,
								renderAPIContextIn.texturesManager().load("resources/textures/rock_normals.png")),
						renderAPIContextIn.meshsManager().load("plane", "resources\\models\\plane.obj")),
				new Vector3f(0, 0, 0), new Vector3f(-180.0f, 0, 0.0f), new Vector3f(1.0f, 1.0f, 1.0f));**/

		/**this.scene.sceneItems()
				.add(new GameItemProperties("rockCube",
						new Material(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), new Vector4f(1.0f, 1.0f, 1.0f, 1.0f),
								new Vector4f(1.0f, 1.0f, 1.0f, 1.0f),
								renderAPIContextIn.texturesManager().load("resources/textures/rock.png"), reflectance,
								renderAPIContextIn.texturesManager().load("resources/textures/rock_normals.png")),
						renderAPIContextIn.meshsManager().load("plane", "resources\\models\\cube.obj")), this.gameItem);**/

		final var	ry			= -0.0f;
		for (var x = -8; x < 8; x++)
		{
			for (var z = -8; z < 8; z++)
			{
				this.scene
						.sceneItems().add(
								new GameItemProperties("rockPlane", new Material(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f),
										new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), new Vector4f(1.0f, 1.0f, 1.0f, 1.0f),
										renderAPIContextIn.texturesManager().load("resources/textures/rock.png"),
										reflectance,
										renderAPIContextIn.texturesManager()
												.load("resources/textures/rock_normals.png")),
										renderAPIContextIn.meshsManager().load("plane",
												"resources\\models\\plane.obj")),
								new Vector3f(x * 2.0f, 0, z * 2.0f), new Vector3f(-180.0f, ry, 0.0f),
								new Vector3f(1.00f, 1.00f, 1.00f));
				//ry -= 90.0f;
			}
			//	ry -= 90.0f;
		}

		this.scene.sceneItems().add(
				new GameItemProperties("rock",
						new Material(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), new Vector4f(1.0f, 1.0f, 1.0f, 1.0f),
								new Vector4f(1.0f, 1.0f, 1.0f, 1.0f),
								renderAPIContextIn.texturesManager().load("resources/textures/rock.png"), reflectance,
								renderAPIContextIn.texturesManager().load("resources/textures/rock_normals.png")),
						renderAPIContextIn.meshsManager().load("cube", "resources\\models\\cube.obj")),
				new Vector3f(0.0f, 2.0f, -6.75f), new Vector3f(-0.0f, 0.0f, 0.0f), new Vector3f(1.00f, 1.00f, 1.00f));

		final var					md5Meshodel		= MD5Model.parse("resources/models/boblamp.md5mesh");
		final var					md5AnimModel	= MD5AnimModel.parse("resources/models/boblamp.md5anim");
		GameAnimatedItemProperties	bob				= null;
		try
		{
			bob = this.md5Loader.process("bob", md5Meshodel, md5AnimModel, new Vector4f(1, 1, 1, 1));
			final var bobGameItem = new GameItem(new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(90.0f, 0.0f, 90.0f),
					new Vector3f(0.05f));
			this.scene.sceneItems().add(bob, bobGameItem);
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		this.pointLight = new PointLight(new Vector3f(1.0f, 1.0f, 0.0f), new Vector3f(), 1.0f,
				new Attenuation(0.05f, 0.1f, 0.5f));
		this.scene.sceneLights().add(this.pointLight);
		this.updateTimer = new Timer();

		//this.projView = new Matrix4f(MathInstances.projectionMatrix()).identity();
		//this.projView.mul(this.scene.camera().view());

		return true;
	}

	@Override
	public void highRateInput()
	{
	}

	@Override
	public void input(IWindow windowIn, InputManager inputManagerIn)
	{
		this.scene.input(windowIn, inputManagerIn);

		if (windowIn.key(GLFW.GLFW_KEY_F1) == GLFW.GLFW_PRESS && !this.mustBeBlockedIsPressed)
		{
			if (inputManagerIn.cursor().isMustBeBlocked())
			{
				inputManagerIn.cursor().mustBeFree();
			}
			else
			{
				inputManagerIn.cursor().mustBeBlocked();
			}
			this.mustBeBlockedIsPressed = true;
		}
		else if (windowIn.key(GLFW.GLFW_KEY_F1) == GLFW.GLFW_RELEASE && this.mustBeBlockedIsPressed)
		{
			this.mustBeBlockedIsPressed = false;
		}
	}

	@Override
	public void update()
	{
		this.scene.update();
		if (!this.updateTimer.isTime(5_000_000_0L))
		{
			return;
		}

		this.scene.sceneItems().executeAnimated((item, gameItems) ->
		{
			item.nextFrame();

			final var	frame	= item.getCurrentFrame();

			final var	mat		= frame.getLocalJointMatrices()[GameTest.selectedJointMatrix];

			GameTest.temp.identity().translate(0.0f, 0.0f, 0.0f).rotateXYZ(0.0f, 0.0f, 0.0f).scale(1.0f).mul(mat);
			final var	m30	= GameTest.temp.m30();
			final var	m31	= GameTest.temp.m32();
			final var	m32	= GameTest.temp.m31();
			GameTest.temp.m30(m32);
			GameTest.temp.m31(m31);
			GameTest.temp.m32(m30);
			GameTest.temp.scale(4.0f);

			var	angles		= new Quaternionf();
			var	rotation	= new Vector3f();
			rotation	= mat.getNormalizedRotation(angles).getEulerAnglesXYZ(rotation);
			angles		= MD5Utils.calculateQuaternion(rotation);

			GameTest.temp.identity().translate(m32, m31, m30)
					.rotateXYZ((float) Math.toRadians(90.0f), (float) -Math.toRadians(90.0f),
							(float) Math.toRadians(90.0f))
					.rotateXYZ(angles.x - (float) Math.toRadians(180.0f), -angles.z - (float) Math.toRadians(180.0f),
							angles.y - (float) Math.toRadians(180.0f))
					.scale(1.0f, 1.0f, 20.0f).translate(-0.5f, 0.5f, 0.5f).translate(0.0f, 0.0f, -1.5f);

			GameTest.position.set(m32, m31, m30);
			this.pointLight.position().set(GameTest.position).mul(0.05f);
		});

		var rotX = this.gameItem.orientation().x;
		rotX += 0.5f;
		if (rotX >= 360)
		{
			rotX -= 360;
		}
		this.gameItem.orientation().x = rotX;
		var rotY = this.gameItem.orientation().y;
		rotY += 0.5f;
		if (rotY >= 360)
		{
			rotY -= 360;
		}
		this.gameItem.orientation().y = rotY;
		var rotZ = this.gameItem.orientation().z;
		rotZ += 0.5f;
		if (rotZ >= 360)
		{
			rotZ -= 360;
		}
		this.gameItem.orientation().z = rotZ;
	}

	@Override
	public void draw(IWindow windowIn, IRenderAPIContext renderAPIContextIn, Renderer rendererIn)
	{
		this.scene.render(windowIn);
	}

	@Override
	public void cleanup()
	{
		this.scene.cleanup();
	}
}