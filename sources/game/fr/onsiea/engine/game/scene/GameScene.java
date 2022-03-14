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
import org.lwjgl.opengl.GL13;

import fr.onsiea.engine.client.graphics.GraphicsConstants;
import fr.onsiea.engine.client.graphics.fog.Fog;
import fr.onsiea.engine.client.graphics.light.DirectionalLight;
import fr.onsiea.engine.client.graphics.light.PointLight;
import fr.onsiea.engine.client.graphics.light.PointLight.Attenuation;
import fr.onsiea.engine.client.graphics.light.SpotLight;
import fr.onsiea.engine.client.graphics.material.Material;
import fr.onsiea.engine.client.graphics.mesh.IMesh;
import fr.onsiea.engine.client.graphics.opengl.shaders.ShaderBasic;
import fr.onsiea.engine.client.graphics.opengl.skybox.SkyboxRenderer;
import fr.onsiea.engine.client.graphics.render.IRenderAPIContext;
import fr.onsiea.engine.client.graphics.shader.IShadersManager;
import fr.onsiea.engine.client.graphics.shapes.ShapeCube;
import fr.onsiea.engine.client.graphics.window.IWindow;
import fr.onsiea.engine.client.input.InputManager;
import fr.onsiea.engine.client.resources.ResourcesPath;
import fr.onsiea.engine.core.entity.Camera;
import fr.onsiea.engine.utils.maths.MathInstances;
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
	private final ShaderBasic										shader;

	private final SkyboxRenderer									skyboxRenderer;

	private final Vector3f											ambientLight;
	private final PointLight										pointLight;
	private final SpotLight											spotLight;
	private final DirectionalLight									directionalLight;
	private final Fog												fog;
	private final float												specularPower;

	public GameScene(IRenderAPIContext renderAPIContextIn) throws Exception
	{
		this.renderAPIContext	= renderAPIContextIn;
		this.shadersManager		= renderAPIContextIn.shadersManager();
		this.shader				= (ShaderBasic) renderAPIContextIn.shadersManager().get("basic");

		this.objects			= new HashMap<>();

		this.camera				= new Camera();
		this.cameraTimer		= new Timer();
		this.shadersManager.updateProjectionAndView(MathInstances.projectionMatrix(), this.camera);

		this.fog = new Fog(true, new Vector3f(0.125f, 0.125f, 0.25f), 0.0075f, 5.0f);
		this.shader.attach();
		this.shader.fog().load(this.fog);
		renderAPIContextIn.shadersManager().detach();

		this.skyboxRenderer		= new SkyboxRenderer(renderAPIContextIn.shadersManager(),
				renderAPIContextIn.meshsManager().create(ShapeCube.withSize(400.0f), ShapeCube.INDICES, 3),
				renderAPIContextIn.texturesManager().loadCubeMapTextures("skybox",
						ResourcesPath.of(new ResourcesPath(GraphicsConstants.TEXTURES, "skybox"))));

		this.ambientLight		= new Vector3f(0.125f, 0.125f, 0.125f);
		this.pointLight			= new PointLight(new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(0.0f, 2.0f, 0.0f), 1.0f,
				new Attenuation(0.025f, 0.01f, 0.01f));
		this.spotLight			= new SpotLight(new PointLight(new Vector3f(1.0f, 1.0f, 1.0f),
				new Vector3f(0.0f, 2.0f, 0.0f), 1.5f, new Attenuation(0.025f, 0.01f, 0.01f)),
				new Vector3f(-0.25f, -0.5f, -0.25f), 75.0f);
		this.directionalLight	= new DirectionalLight(new Vector3f(1.0f, 1.0f, 1.0f),
				new Vector3f(-0.125f, -0.25f, -0.125f), 1.5f);

		this.specularPower		= 10.0f;
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

	public void input(IWindow windowIn, InputManager inputManagerIn)
	{
		if (this.cameraTimer.isTime(1_000_000_0L))
		{
			this.camera.input(windowIn, inputManagerIn);
		}
	}

	public void draw()
	{
		this.shader.attach();
		this.shader.ambientLight().load(this.ambientLight);
		this.shader.pointLights()[0].load(this.pointLight, this.camera.view());
		this.shader.spotLights()[0].load(this.spotLight, this.camera.view());
		this.shader.directionalLight().load(this.directionalLight, this.camera.view());
		this.shader.specularPower().load(this.specularPower);

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

				this.shader.material().load(material);

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
					this.shader.transformations().load(gameItem.transformations());

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