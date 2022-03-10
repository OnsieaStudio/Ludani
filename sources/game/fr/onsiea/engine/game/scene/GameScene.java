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
import org.joml.Vector4f;

import fr.onsiea.engine.client.graphics.GraphicsConstants;
import fr.onsiea.engine.client.graphics.light.PointLight;
import fr.onsiea.engine.client.graphics.light.PointLight.Attenuation;
import fr.onsiea.engine.client.graphics.material.Material;
import fr.onsiea.engine.client.graphics.mesh.IMesh;
import fr.onsiea.engine.client.graphics.opengl.shader.ShaderBasic;
import fr.onsiea.engine.client.graphics.opengl.skybox.SkyboxRenderer;
import fr.onsiea.engine.client.graphics.render.IRenderAPIContext;
import fr.onsiea.engine.client.graphics.shader.IShadersManager;
import fr.onsiea.engine.client.graphics.shapes.ShapeCube;
import fr.onsiea.engine.client.graphics.texture.ITexture;
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
	private Map<IdentifiableMesh, Map<ITexture, List<Matrix4f>>>	objects;

	private @Getter(AccessLevel.PUBLIC) final Camera				camera;
	private final Timer												cameraTimer;
	// private PostProcessing											postProcessing;

	private final IShadersManager									shadersManager;
	private final IRenderAPIContext									renderAPIContext;
	private final ShaderBasic										shader;

	private final SkyboxRenderer									skyboxRenderer;

	private final Vector3f											ambientLight;
	private final PointLight										pointLight;
	private final float												specularPower;
	private Material												material;

	public GameScene(IRenderAPIContext renderAPIContextIn) throws Exception
	{
		this.renderAPIContext	= renderAPIContextIn;
		this.shadersManager		= renderAPIContextIn.shadersManager();
		this.shader				= (ShaderBasic) renderAPIContextIn.shadersManager().get("basic");

		this.objects			= new HashMap<>();

		this.camera				= new Camera();
		this.cameraTimer		= new Timer();
		this.shadersManager.updateProjectionAndView(MathInstances.projectionMatrix(), this.camera);

		this.shader.attach();
		this.shader.fogColour().load(new Vector3f(0.125f, 0.125f, 0.25f));
		renderAPIContextIn.shadersManager().detach();

		this.skyboxRenderer	= new SkyboxRenderer(renderAPIContextIn.shadersManager(),
				renderAPIContextIn.meshsManager().create(ShapeCube.withSize(400.0f), ShapeCube.INDICES, 3),
				renderAPIContextIn.texturesManager().loadCubeMapTextures("skybox",
						ResourcesPath.of(new ResourcesPath(GraphicsConstants.TEXTURES, "skybox"))));

		this.ambientLight	= new Vector3f(1.0f, 1.0f, 1.0f);
		this.pointLight		= new PointLight(new Vector3f(1.0f, 1.0f, 0.25f), new Vector3f(0.0f, 2.0f, 0.0f), 1.0f,
				new Attenuation(0.025f, 0.01f, 0.01f));
		this.specularPower	= 1.0f;
	}

	public void add(String nameIn, IMesh meshIn, ITexture textureIn, Matrix4f... transformationsIn)
	{
		this.add(new IdentifiableMesh(nameIn, meshIn), textureIn, transformationsIn);
	}

	public void add(String nameIn, String meshFilepathIn, String textureFilepathIn, Matrix4f... transformationsIn)
			throws Exception
	{
		final var texture = this.renderAPIContext.texturesManager().load(textureFilepathIn);
		this.add(new IdentifiableMesh(nameIn, this.renderAPIContext.meshsManager().objLoader().load(meshFilepathIn)),
				texture, transformationsIn);
		this.material = new Material(new Vector4f(1.0f), new Vector4f(1.0f), new Vector4f(1.0f), texture, 0.025f);
	}

	public void add(IdentifiableMesh identifiableMeshIn, ITexture textureIn, Matrix4f... transformationsIn)
	{
		var texturedObjects = this.objects().get(identifiableMeshIn);

		if (texturedObjects == null)
		{
			texturedObjects = new HashMap<>();

			this.objects().put(identifiableMeshIn, texturedObjects);
		}

		var objects = texturedObjects.get(textureIn);

		if (objects == null)
		{
			objects = new ArrayList<>();

			texturedObjects.put(textureIn, objects);
		}

		Collections.addAll(objects, transformationsIn);
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
		this.shader.pointLight().load(this.pointLight);
		this.shader.specularPower().load(this.specularPower);
		this.shader.material().load(this.material);

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
				final var	texture					= texturedObjectsEntry.getKey();
				texture.attach();

				final var objects = texturedObjectsEntry.getValue();

				for (final Matrix4f transformations : objects)
				{
					this.shader.transformations().load(transformations);

					mesh.mesh().draw();
				}

				texture.detach();
			}
			mesh.mesh().detach();
		}
		this.shadersManager.detach();

		this.skyboxRenderer.attach();
		this.skyboxRenderer.draw();
		this.skyboxRenderer.detach();
	}

	public final Map<IdentifiableMesh, Map<ITexture, List<Matrix4f>>> objects()
	{
		return this.objects;
	}

	public final void objects(Map<IdentifiableMesh, Map<ITexture, List<Matrix4f>>> objectsIn)
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