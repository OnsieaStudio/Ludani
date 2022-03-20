
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

import org.joml.Vector3f;
import org.joml.Vector4f;

import fr.onsiea.engine.client.graphics.GraphicsConstants;
import fr.onsiea.engine.client.graphics.fog.Fog;
import fr.onsiea.engine.client.graphics.light.DirectionalLight;
import fr.onsiea.engine.client.graphics.material.Material;
import fr.onsiea.engine.client.graphics.opengl.flare.FlareManager;
import fr.onsiea.engine.client.graphics.opengl.shaders.InstancedShader;
import fr.onsiea.engine.client.graphics.opengl.shaders.Shader2D;
import fr.onsiea.engine.client.graphics.opengl.shaders.ShaderBasic;
import fr.onsiea.engine.client.graphics.opengl.shaders.ShaderSkybox;
import fr.onsiea.engine.client.graphics.opengl.shaders.effects.FlareShader;
import fr.onsiea.engine.client.graphics.opengl.shaders.effects.ShadowShader;
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
import fr.onsiea.engine.game.scene.item.GameItem;
import fr.onsiea.engine.game.scene.item.GameItemProperties;

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

	private Scene		scene;
	private GameItem	gameItem;

	@Override
	public boolean preInitialization()
	{
		return true;
	}

	@Override
	public boolean initialization(IWindow windowIn, IRenderAPIContext renderAPIContextIn) throws Exception
	{
		renderAPIContextIn.shadersManager().add("scene", new ShaderBasic(ShaderBasic.Normal.SCENE));
		renderAPIContextIn.shadersManager().add("shadow", new ShadowShader());
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
				new DirectionalLight(new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(0.0f, 1.0f, 1.0f), 1.0f)
						.shadowPosMult(5).orthoCords(-10.0f, 10.0f, -10.0f, 10.0f, -1.0f, 20.0f),
				10.0f, new Vector3f(0.3f, 0.3f, 0.3f), Fog.NO_FOG,
				new FlareManager(0.125f, renderAPIContextIn, i -> 0.75f / (3.0f * i), i -> "tex" + (i + 1) + ".png", 9),
				skyboxRenderer);

		this.gameItem	= new GameItem(new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(0.0f, 0.0f, 0.0f),
				new Vector3f(0.5f, 0.5f, 0.5f));

		final var reflectance = 1.00f;
		this.scene.sceneItems()
				.add(new GameItemProperties("rockCube",
						new Material(new Vector4f(0, 1, 0, 1), new Vector4f(0, 1, 0, 1), new Vector4f(0, 1, 0, 1),
								renderAPIContextIn.texturesManager().load("resources/textures/rock.png"), reflectance),
						renderAPIContextIn.meshsManager().load("cube", "resources\\models\\cube.obj")), this.gameItem);
		this.scene.sceneItems().add(
				new GameItemProperties("rockPlane",
						new Material(new Vector4f(0.0f, 0.0f, 1.0f, 10.0f), new Vector4f(0.0f, 0.0f, 1.0f, 10.0f),
								new Vector4f(0.0f, 0.0f, 1.0f, 10.0f),
								renderAPIContextIn.texturesManager().load("resources/textures/rock.png"), reflectance),
						renderAPIContextIn.meshsManager().load("plane", "resources\\models\\plane.obj")),
				new Vector3f(0.0f, -1.0f, 0.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(2.5f, 2.5f, 2.5f));

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
	}

	@Override
	public void update()
	{
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