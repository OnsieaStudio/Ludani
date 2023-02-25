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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import fr.onsiea.engine.client.graphics.opengl.instanced.Instanced;
import fr.onsiea.engine.client.graphics.opengl.shaders.AdvInstancedShader;
import fr.onsiea.engine.client.graphics.render.IRenderAPIContext;
import fr.onsiea.engine.game.world.World;
import fr.onsiea.engine.game.world.WorldUtils;
import fr.onsiea.engine.game.world.item.Item;
import fr.onsiea.engine.game.world.item.Item.ItemType;
import fr.onsiea.engine.game.world.picking.Picker.Selection;
import fr.onsiea.engine.utils.Triplet;
import fr.onsiea.engine.utils.maths.MathInstances;
import fr.onsiea.engine.utils.maths.transformations.Transformations3f;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Seynax
 *
 */
public class Chunk
{
	public static final Vector3f													SIZE				= new Vector3f(
			16.0f, 16.0f, 16.0f);

	private final Map<Vector3f, Item>												items;
	private final Map<ItemType, Triplet<Integer, Instanced, Map<Vector3f, Item>>>	drawers;
	@SuppressWarnings("unused")
	private World																	world;
	private @Getter Vector3f														position;
	// private final ShaderNormalMappingThinMatrix shader;
	// private IShadersManager shadersManager;

	private boolean																	renderIsInitialized	= false;
	private boolean																	isVisible			= false;
	private @Getter @Setter boolean													selected			= false;

	public Chunk(final World worldIn, final Vector3f positionIn)
	{
		this.world		= worldIn;
		this.position	= positionIn;
		this.items		= new LinkedHashMap<>();
		this.drawers	= new LinkedHashMap<>();
		this.isVisible	= true;
		// shadersManager = contextIn.shadersManager();
		// this.shader = (ShaderNormalMappingThinMatrix)
		// shadersManager.get("normalMappingThinMatrix");
	}

	private final void add(final Item itemIn)
	{
		itemIn.uniqueId(WorldUtils.uniqueItemId());
		if (this.items().containsKey(itemIn.position()))
		{
			return;
		}
		this.items().put(itemIn.position(), itemIn);

		var itemsDrawers = this.drawers.get(itemIn.typeVariant().itemType());
		if (itemsDrawers == null)
		{
			itemsDrawers = new Triplet<>(-1, null, new LinkedHashMap<>());
			itemIn.instanceSubIndex(this.drawers.size());
			this.drawers.put(itemIn.typeVariant().itemType(), itemsDrawers);
		}

		itemsDrawers.s3().remove(itemIn.position());
		itemsDrawers.s3().put(itemIn.position(), itemIn);
	}

	public Chunk add(final ItemType typeIn, final Item... itemsIn)
	{
		for (final Item item : itemsIn)
		{
			this.add(item);
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
			this.add(item);
		}

		return this;
	}

	/**
	 * @param itemsIn
	 */
	public Chunk add(final Map<ItemType, List<Item>> itemsIn)
	{
		for (final var entry : itemsIn.entrySet())
		{
			this.add(entry.getKey(), entry.getValue());
		}

		return this;
	}

	/**
	 * @param positionIn
	 */
	public boolean removeAll(final Item... itemsIn)
	{
		var success = false;

		for (final Item item : itemsIn)
		{
			if (this.removeOne(item.typeVariant().itemType(), item.position()))
			{
				success = true;
			}
		}

		return success;
	}

	public boolean removeOne(final Item itemIn)
	{
		this.removeOne(itemIn.typeVariant().itemType(), itemIn.position());

		return false;
	}

	public boolean removeOne(final ItemType itemTypeIn, final Vector3f positionIn)
	{
		if (this.items.containsKey(positionIn))
		{
			this.items.remove(positionIn);

			final var drawers = this.drawers.get(itemTypeIn);

			drawers.s3().remove(positionIn);

			if (drawers.s3().size() <= 0)
			{
				this.drawers.remove(itemTypeIn);
			}

			return true;
		}

		return false;
	}

	public void genRender(final IRenderAPIContext contextIn)
	{
		this.renderIsInitialized = true;

		var	i	= 0;
		var	i0	= 0;
		for (final var entry : this.drawers.entrySet())
		{
			// final var	key		= entry.getKey();
			final var	pair	= entry.getValue();

			final var	builder	= new Instanced.Builder();
			pair.s1(i0);
			i0++;

			builder.data(entry.getKey().meshData().positions(), 3).data(entry.getKey().meshData().uvs(), 2)
					.data(entry.getKey().meshData().normals(), 3).data(entry.getKey().meshData().tangents(), 3);

			final var	buffer		= BufferUtils
					.createFloatBuffer(this.items().size() * (MathInstances.matrixSizeFloats() + 3));
			final var	localBuffer	= BufferUtils.createFloatBuffer(MathInstances.matrixSizeFloats() + 3);

			i = 0;
			for (final Item item : pair.s3().values())
			{
				item.instanceId(i);

				final var	position	= item.position();
				final var	orientation	= item.orientation();
				final var	scale		= item.scale();

				final var	matrix		= Transformations3f.transformations(position, orientation, scale);

				matrix.get(0, localBuffer);
				localBuffer.put(MathInstances.matrixSizeFloats(), item.typeVariant().textureIndex());
				localBuffer.put(MathInstances.matrixSizeFloats() + 1, item.typeVariant().textureSubIndex() * 10 + 5);
				localBuffer.put(MathInstances.matrixSizeFloats() + 2, item.uniqueId());
				buffer.put(localBuffer);

				localBuffer.clear();

				i++;
			}
			buffer.flip();

			builder.interleaveData(buffer, MathInstances.matrixSizeFloats() + 3).unbind();
			builder.indices(entry.getKey().meshData().indices());

			pair.s2(builder.build(entry.getKey().meshData().indices().length, this.items().size()));
		}
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
	 * @param selectionIn
	 **/

	public final Chunk draw(final AdvInstancedShader advInstancedShaderIn, final Selection selectionIn)
	{
		for (final var pair : this.drawers.values())
		{
			advInstancedShaderIn.instanceIsSelected().load(false);
			if (selectionIn.item() != null && selectionIn.chunk().position().equals(this.position))
			{
				advInstancedShaderIn.instanceIsSelected().load(pair.s1() == selectionIn.item().instanceSubIndex());
			}
			pair.s2().bindDrawUnbind();
		}

		return this;
	}

	/**
	 * public final void save()
	 * {
	 *
	 * }
	 **/

	/**
	 * @return
	 */
	public boolean isEmpty()
	{
		return this.items.size() <= 0;
	}

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
