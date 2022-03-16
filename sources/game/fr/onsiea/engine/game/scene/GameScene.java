/**
* Copyright 2021 Onsiea All rights reserved.<br><br>
*
* This file is part of Onsiea Engine project. (https://github.com/Onsiea/OnsieaEngine)<br><br>
*
* Onsiea Engine is [licensed] (https://github.com/Onsiea/OnsieaEngine/blob/main/LICENSE) under the terms of the "GNU General Public Lesser License v3.0" (GPL-3.0).
* https://github.com/Onsiea/OnsieaEngine/wiki/License#license-and-copyright<br><br>
*
* Onsiea Engine is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 3.0 of the License, or
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
package fr.onsiea.engine.game.scene;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

import fr.onsiea.engine.client.graphics.GraphicsConstants;
import fr.onsiea.engine.client.graphics.fog.Fog;
import fr.onsiea.engine.client.graphics.light.DirectionalLight;
import fr.onsiea.engine.client.graphics.material.Material;
import fr.onsiea.engine.client.graphics.mesh.IMesh;
import fr.onsiea.engine.client.graphics.opengl.shaders.ShaderBasic;
import fr.onsiea.engine.client.graphics.opengl.shaders.effects.ShadowShader;
import fr.onsiea.engine.client.graphics.opengl.shadow.ShadowMap;
import fr.onsiea.engine.client.graphics.opengl.skybox.SkyboxRenderer;
import fr.onsiea.engine.client.graphics.render.IRenderAPIContext;
import fr.onsiea.engine.client.graphics.shader.IShadersManager;
import fr.onsiea.engine.client.graphics.shapes.ShapeCube;
import fr.onsiea.engine.client.graphics.window.IWindow;
import fr.onsiea.engine.client.input.InputManager;
import fr.onsiea.engine.client.resources.ResourcesPath;
import fr.onsiea.engine.core.entity.Camera;
import fr.onsiea.engine.utils.maths.MathInstances;
import fr.onsiea.engine.utils.maths.projections.Projections;
import fr.onsiea.engine.utils.maths.view.View3f;
import fr.onsiea.engine.utils.time.Timer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Seynax
 *
 */
public class GameScene
{
	private Map<IdentifiableMesh, Map<Material, List<GameItem>>>	objects;

	private @Getter(AccessLevel.PUBLIC) final Camera				camera;
	private final Timer												cameraTimer;
	// private PostProcessing											postProcessing;

	private final IShadersManager									shadersManager;
	private final IRenderAPIContext									renderAPIContext;
	private final ShaderBasic[]										shadersBasic;
	private final String[]											shadersBasicNames;
	private int														selectedShader;
	private final Timer												shaderTimer;

	private final SkyboxRenderer									skyboxRenderer;

	private final Vector3f											ambientLight;
	private final DirectionalLight									directionalLight;
	private final Fog												fog;
	private final float												specularPower;
	private float													lightAngle;

	private ShadowMap												shadowMap;
	private final ShadowShader										shadowShader;

	public GameScene(IRenderAPIContext renderAPIContextIn) throws Exception
	{
		this.renderAPIContext		= renderAPIContextIn;
		this.shadersManager			= renderAPIContextIn.shadersManager();
		this.shadersBasic			= new ShaderBasic[6];
		this.shadersBasicNames		= new String[6];
		this.shadersBasic[0]		= (ShaderBasic) renderAPIContextIn.shadersManager().get("basicNormalWithTangent");
		this.shadersBasicNames[0]	= "basicNormalWithTangent";
		this.shadersBasic[1]		= (ShaderBasic) renderAPIContextIn.shadersManager().get("basicExclusiveNormal");
		this.shadersBasicNames[1]	= "basicExclusiveNormal";
		this.shadersBasic[2]		= (ShaderBasic) renderAPIContextIn.shadersManager().get("basicNormal");
		this.shadersBasicNames[2]	= "basicNormal";
		this.shadersBasic[3]		= (ShaderBasic) renderAPIContextIn.shadersManager().get("basicFakeNormal");
		this.shadersBasicNames[3]	= "basicFakeNormal";
		this.shadersBasic[4]		= (ShaderBasic) renderAPIContextIn.shadersManager().get("basicWithoutNormal");
		this.shadersBasicNames[4]	= "basicWithoutNormal";
		this.shadersBasic[5]		= (ShaderBasic) renderAPIContextIn.shadersManager().get("scene");
		this.shadersBasicNames[5]	= "scene";
		this.selectedShader			= 5;
		this.shadowShader			= (ShadowShader) renderAPIContextIn.shadersManager().get("shadow");

		this.objects				= new HashMap<>();

		this.camera					= new Camera();
		this.camera.position().y	= 5.0f;
		this.camera.orientation().x	= 90.0f;
		this.cameraTimer			= new Timer();
		this.shaderTimer			= new Timer();
		this.shadersManager.updateProjectionAndView(MathInstances.projectionMatrix(), this.camera);

		this.fog = new Fog(false, new Vector3f(0.0f, 0.0f, 0.0f), 0.0f, 0.0f);

		for (final var shader : this.shadersBasic)
		{
			shader.attach();
			shader.fog().load(this.fog);
		}
		renderAPIContextIn.shadersManager().detach();

		this.skyboxRenderer		= new SkyboxRenderer(renderAPIContextIn.shadersManager(),
				renderAPIContextIn.meshsManager().create(ShapeCube.withSize(400.0f), ShapeCube.INDICES, 3),
				renderAPIContextIn.texturesManager().loadCubeMapTextures("skybox",
						ResourcesPath.of(new ResourcesPath(GraphicsConstants.TEXTURES, "skybox"))));

		this.ambientLight		= new Vector3f(0.3f, 0.3f, 0.3f);
		this.directionalLight	= new DirectionalLight(new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(1.0f, 1.0f, 0.0f),
				1.0f);

		this.specularPower		= 10.00f;
		this.lightAngle			= -35.0f;
	}

	public void add(String nameIn, String meshFilepathIn, Material materialIn, Matrix4f... transformationsIn)
			throws Exception
	{
		this.add(nameIn, this.renderAPIContext.meshsManager().objLoader().load(meshFilepathIn), materialIn,
				transformationsIn);
	}

	public void add(String nameIn, IMesh meshIn, Material commonMaterialIn, Matrix4f... transformationsIn)
			throws Exception
	{
		final var	gameItems	= new GameItem[transformationsIn.length];
		var			i			= 0;
		for (final Matrix4f transformations : transformationsIn)
		{
			gameItems[i] = new GameItem(nameIn, meshIn, commonMaterialIn, transformations);
			i++;
		}

		this.add(new IdentifiableMesh(nameIn, meshIn), commonMaterialIn, gameItems);
	}

	public void add(IdentifiableMesh identifiableMeshIn, Material commonMaterialIn, GameItem... gameItemsIn)
	{
		var texturedObjects = this.objects().get(identifiableMeshIn);

		if (texturedObjects == null)
		{
			texturedObjects = new HashMap<>();

			this.objects().put(identifiableMeshIn, texturedObjects);
		}

		var objects = texturedObjects.get(commonMaterialIn);

		if (objects == null)
		{
			objects = new ArrayList<>();

			texturedObjects.put(commonMaterialIn, objects);
		}

		Collections.addAll(objects, gameItemsIn);
	}

	public void add(IdentifiableMesh identifiableMeshIn, GameItem gameItemIn)
	{
		var texturedObjects = this.objects().get(identifiableMeshIn);

		if (texturedObjects == null)
		{
			texturedObjects = new HashMap<>();

			this.objects().put(identifiableMeshIn, texturedObjects);
		}

		var objects = texturedObjects.get(gameItemIn.material());

		if (objects == null)
		{
			objects = new ArrayList<>();

			texturedObjects.put(gameItemIn.material(), objects);
		}

		objects.add(gameItemIn);
	}

	private static boolean e = false;

	public void input(IWindow windowIn, InputManager inputManagerIn)
	{
		if (this.cameraTimer.isTime(1_000_000_0L))
		{
			this.camera.input(windowIn, inputManagerIn);
		}

		if (this.shaderTimer.isTime(7_500_000_0L))
		{
			if (inputManagerIn.glfwGetKey(GLFW.GLFW_KEY_KP_ADD) == GLFW.GLFW_PRESS)
			{
				this.selectedShader++;

				if (this.selectedShader >= this.shadersBasic.length)
				{
					this.selectedShader = 0;
				}

				System.out.println(this.selectedShader + " : " + this.shadersBasicNames[this.selectedShader]);
			}
			else if (inputManagerIn.glfwGetKey(GLFW.GLFW_KEY_KP_SUBTRACT) == GLFW.GLFW_PRESS)
			{
				this.selectedShader--;

				if (this.selectedShader < 0)
				{
					this.selectedShader = this.shadersBasic.length - 1;
				}

				System.out.println(this.selectedShader + " : " + this.shadersBasicNames[this.selectedShader]);
			}
			else if (inputManagerIn.glfwGetKey(GLFW.GLFW_KEY_KP_MULTIPLY) == GLFW.GLFW_PRESS && !GameScene.e)
			{
				GameScene.e = true;

				if (this.selectedShader != 2)
				{
					this.selectedShader = 2;
				}
				else
				{
					this.selectedShader = 0;
				}

				System.out.println(this.selectedShader + " : " + this.shadersBasicNames[this.selectedShader]);
			}
			else if (inputManagerIn.glfwGetKey(GLFW.GLFW_KEY_KP_MULTIPLY) == GLFW.GLFW_RELEASE && GameScene.e)
			{
				GameScene.e = false;
			}

			if (inputManagerIn.glfwGetKey(GLFW.GLFW_KEY_LEFT) == GLFW.GLFW_PRESS)
			{
				this.lightAngle -= 2.5f;
				if (this.lightAngle < -90)
				{
					this.lightAngle = -90;
				}
			}
			else if (inputManagerIn.glfwGetKey(GLFW.GLFW_KEY_RIGHT) == GLFW.GLFW_PRESS)
			{
				this.lightAngle += 2.5f;
				if (this.lightAngle > 90)
				{
					this.lightAngle = 90;
				}
			}
		}
	}

	public void draw()
	{
		final var shader = this.shadersBasic[this.selectedShader];
		shader.attach();
		shader.ambientLight().load(this.ambientLight);
		final var angRad = Math.toRadians(this.lightAngle);
		this.directionalLight.direction().x	= (float) Math.sin(angRad);
		this.directionalLight.direction().y	= (float) Math.cos(angRad);
		shader.directionalLight().load(this.directionalLight, this.camera.view());
		shader.specularPower().load(this.specularPower);

		final var meshedObjectsIterator = this.objects.entrySet().iterator();

		while (meshedObjectsIterator.hasNext())
		{
			final var	meshedObjectsEntry		= meshedObjectsIterator.next();
			final var	mesh					= meshedObjectsEntry.getKey();
			final var	texturedObjects			= meshedObjectsEntry.getValue();

			final var	texturedObjectsIterator	= texturedObjects.entrySet().iterator();

			mesh.mesh().attach();
			while (texturedObjectsIterator.hasNext())
			{
				final var	texturedObjectsEntry	= texturedObjectsIterator.next();
				final var	material				= texturedObjectsEntry.getKey();

				shader.material().load(material);

				GL13.glActiveTexture(GL13.GL_TEXTURE0);
				material.texture().attach();

				if (material.hasNormalMap())
				{
					GL13.glActiveTexture(GL13.GL_TEXTURE1);
					material.normalMap().attach();
				}

				final var objects = texturedObjectsEntry.getValue();

				for (final GameItem gameItem : objects)
				{
					shader.transformations().load(gameItem.transformations());

					mesh.mesh().draw();
				}

				if (material.hasNormalMap())
				{
					material.normalMap().detach();
				}
				GL13.glActiveTexture(GL13.GL_TEXTURE0);
				material.texture().detach();
			}
			mesh.mesh().detach();
		}
		this.shadersManager.detach();

		this.skyboxRenderer.attach(this.fog, this.ambientLight, this.directionalLight);
		this.skyboxRenderer.draw();
		this.skyboxRenderer.detach();
	}

	final Matrix4f	lightViewMatrix	= new Matrix4f();
	final Matrix4f	orthoProjMatrix	= new Matrix4f();

	private final void renderDepthMap()
	{
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, this.shadowMap.getDepthMapFBO());
		GL11.glViewport(0, 0, ShadowMap.SHADOW_MAP_WIDTH, ShadowMap.SHADOW_MAP_HEIGHT);
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);

		this.shadowShader.attach();

		// var directionalLight = this.directionalLight;
		final var	lightDirection	= this.directionalLight.direction();

		final var	lightAngleX		= (float) Math.toDegrees(Math.acos(lightDirection.z()));
		final var	lightAngleY		= (float) Math.toDegrees(Math.asin(lightDirection.x()));
		final var	lightAngleZ		= 0F;
		View3f.view(new Vector3f(lightDirection).mul(this.directionalLight.shadowPosMult()),
				new Vector3f(lightAngleX, lightAngleY, lightAngleZ), this.lightViewMatrix);
		final var orthCoords = this.directionalLight.orthoCoords();
		Projections.ortho(orthCoords.left, orthCoords.right, orthCoords.bottom, orthCoords.top, orthCoords.near,
				orthCoords.far, this.orthoProjMatrix);

	}

	public final Map<IdentifiableMesh, Map<Material, List<GameItem>>> objects()
	{
		return this.objects;
	}

	public final void objects(Map<IdentifiableMesh, Map<Material, List<GameItem>>> objectsIn)
	{
		this.objects = objectsIn;
	}

	@Getter(AccessLevel.PUBLIC)
	@AllArgsConstructor
	public final static class IdentifiableMesh
	{
		private final String	name;
		private final IMesh		mesh;

		@Override
		public int hashCode()
		{
			return Objects.hash(this.name);
		}

		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
			{
				return true;
			}
			if (obj == null || this.getClass() != obj.getClass())
			{
				return false;
			}
			final var other = (IdentifiableMesh) obj;
			return Objects.equals(this.name, other.name);
		}
	}
}