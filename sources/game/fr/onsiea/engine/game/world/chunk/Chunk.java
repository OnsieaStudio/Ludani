/**
 * Copyright 2021 Onsiea All rights reserved.<br>
 * <br>
 *
 * This file is part of Onsiea Engine project.
 * (https://github.com/Onsiea/OnsieaEngine)<br>
 * <br>
 *
 * Onsiea Engine is [licensed]
 * (https://github.com/Onsiea/OnsieaEngine/blob/main/LICENSE) under the terms of
 * the "GNU General Public Lesser License v3.0" (GPL-3.0).
 * https://github.com/Onsiea/OnsieaEngine/wiki/License#license-and-copyright<br>
 * <br>
 *
 * Onsiea Engine is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3.0 of the License, or
 * (at your option) any later version.<br>
 * <br>
 *
 * Onsiea Engine is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.<br>
 * <br>
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Onsiea Engine. If not, see <https://www.gnu.org/licenses/>.<br>
 * <br>
 *
 * Neither the name "Onsiea", "Onsiea Engine", or any derivative name or the
 * names of its authors / contributors may be used to endorse or promote
 * products derived from this software and even less to name another project or
 * other work without clear and precise permissions written in advance.<br>
 * <br>
 *
 * @Author : Seynax (https://github.com/seynax)<br>
 * @Organization : Onsiea Studio (https://github.com/Onsiea)
 */
package fr.onsiea.engine.game.world.chunk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import fr.onsiea.engine.client.graphics.mesh.obj.MeshData;
import fr.onsiea.engine.client.graphics.mesh.obj.normalMapped.NormalMappedObjLoader;
import fr.onsiea.engine.client.graphics.opengl.instanced.Instanced;
import fr.onsiea.engine.client.graphics.opengl.texture.GLTextureArray;
import fr.onsiea.engine.client.graphics.opengl.texture.GLTextureArrayManager;
import fr.onsiea.engine.client.graphics.render.IRenderAPIContext;
import fr.onsiea.engine.game.world.World;
import fr.onsiea.engine.game.world.item.Item;
import fr.onsiea.engine.game.world.item.Item.ItemType;
import fr.onsiea.engine.utils.maths.MathInstances;
import fr.onsiea.engine.utils.maths.MathUtils;
import fr.onsiea.engine.utils.maths.transformations.Transformations3f;
import lombok.Getter;

/**
 * @author Seynax
 *
 */
public class Chunk
{
	private final Map<Vector3f, Item>					items;
	private final Map<ItemType, Map<Vector3f, Item>>	drawers;
	@SuppressWarnings("unused")
	private World										world;
	private @Getter Vector3f							position;
	// private final ShaderNormalMappingThinMatrix shader;
	// private IShadersManager shadersManager;

	private Instanced									instanced;
	private boolean										renderIsInitialized	= false;
	private boolean										isVisible			= false;

	public Chunk(final World worldIn, final Vector3f positionIn)
	{
		this.world		= worldIn;
		this.position	= positionIn;
		this.items		= new HashMap<>();
		this.drawers	= new HashMap<>();
		this.isVisible	= true;
		// shadersManager = contextIn.shadersManager();
		// this.shader = (ShaderNormalMappingThinMatrix)
		// shadersManager.get("normalMappingThinMatrix");
	}

	public Chunk add(final ItemType typeIn, final Item... itemsIn)
	{
		for (final Item item : itemsIn)
		{
			this.items().remove(item.position());
			this.items().put(item.position(), item);

			var itemsDrawers = this.drawers.get(item.type());
			if (itemsDrawers == null)
			{
				itemsDrawers = new HashMap<>();
				this.drawers.put(item.type(), itemsDrawers);
			}

			itemsDrawers.remove(item.position());
			itemsDrawers.put(item.position(), item);
		}

		return this;
	}

	/**
	 * @param typeIn
	 * @param twoIn
	 */
	public Chunk add(final ItemType typeIn, final List<Item> itemsIn)
	{
		for (final Item item : itemsIn)
		{
			this.items().remove(item.position());
			this.items().put(item.position(), item);

			var itemsDrawers = this.drawers.get(item.type());
			if (itemsDrawers == null)
			{
				itemsDrawers = new HashMap<>();
				this.drawers.put(item.type(), itemsDrawers);
			}

			itemsDrawers.remove(item.position());
			itemsDrawers.put(item.position(), item);
		}

		return this;
	}

	public void genRender(final IRenderAPIContext contextIn)
	{
		this.renderIsInitialized = true;

		MeshData meshData = null;
		try
		{
			meshData = ((NormalMappedObjLoader) contextIn.meshsManager().objLoader())
					.loadData("resources\\models\\cube2.obj");
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		if (meshData == null)
		{
			return;
		}

		final var builder = new Instanced.Builder();

		builder.data(meshData.positions(), 3).data(meshData.uvs(), 2).data(meshData.normals(), 3)
				.data(meshData.tangents(), 3);

		final var	buffer		= BufferUtils
				.createFloatBuffer(this.items().size() * (MathInstances.matrixSizeFloats() + 1));
		final var	localBuffer	= BufferUtils.createFloatBuffer(MathInstances.matrixSizeFloats() + 1);

		var			i			= 0;
		for (final var item : this.items().values())
		{
			item.instanceId(i);
			final var	position	= item.position();
			final var	orientation	= item.orientation();
			final var	scale		= item.scale();

			final var	matrix		= Transformations3f.transformations(position, orientation, scale);

			matrix.get(0, localBuffer);
			localBuffer.put(MathInstances.matrixSizeFloats(), MathUtils.randomInt(0, 3));
			buffer.put(localBuffer);

			localBuffer.clear();

			i++;
		}

		buffer.flip();

		builder.interleaveData(buffer, MathInstances.matrixSizeFloats() + 1).unbind();
		builder.indices(meshData.indices());
		this.instanced = builder.build(meshData.indices().length, this.items().size());
	}

	/**
	 * public final void input()
	 * {
	 *
	 * }
	 *
	 * public final void update()
	 * {
	 *
	 * }
	 **/

	public final Chunk draw()
	{
		this.instanced.bindDrawUnbind();

		return this;
	}

	/**
	 * public final void save()
	 * {
	 *
	 * }
	 **/

	public final void cleanup()
	{
		// this.save();
		this.items().clear();
		this.world		= null;
		this.position	= null;
	}

	public boolean renderIsInitialized()
	{
		return this.renderIsInitialized;
	}

	public void renderIsInitialized(final boolean renderIsInitializedIn)
	{
		this.renderIsInitialized = renderIsInitializedIn;
	}

	public void visible(final boolean insideFrustumIn)
	{
		this.isVisible = insideFrustumIn;
	}

	public boolean visible()
	{
		return this.isVisible;
	}

	public Map<Vector3f, Item> items()
	{
		return this.items;
	}
}
