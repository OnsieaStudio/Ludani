/**
*	Copyright 2021 Onsiea All rights reserved.
*
*	This file is part of Onsiea Engine. (https://github.com/Onsiea/OnsieaEngine)
*
*	Unless noted in license (https://github.com/Onsiea/OnsieaEngine/blob/main/LICENSE.md) notice file (https://github.com/Onsiea/OnsieaEngine/blob/main/LICENSE_NOTICE.md), Onsiea engine and all parts herein is licensed under the terms of the LGPL-3.0 (https://www.gnu.org/licenses/lgpl-3.0.html)  found here https://www.gnu.org/licenses/lgpl-3.0.html and copied below the license file.
*
*	Onsiea Engine is libre software: you can redistribute it and/or modify
*	it under the terms of the GNU Lesser General Public License as published by
*	the Free Software Foundation, either version 3.0 of the License, or
*	(at your option) any later version.
*
*	Onsiea Engine is distributed in the hope that it will be useful,
*	but WITHOUT ANY WARRANTY; without even the implied warranty of
*	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*	GNU Lesser General Public License for more details.
*
*	You should have received a copy of the GNU Lesser General Public License
*	along with Onsiea Engine.  If not, see <https://www.gnu.org/licenses/> <https://www.gnu.org/licenses/lgpl-3.0.html>.
*
*	Neither the name "Onsiea", "Onsiea Engine", or any derivative name or the names of its authors / contributors may be used to endorse or promote products derived from this software and even less to name another project or other work without clear and precise permissions written in advance.
*
*	(more details on https://github.com/Onsiea/OnsieaEngine/wiki/License)
*
*	@author Seynax
*/
package fr.onsiea.engine.game.world.chunk;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.joml.Vector3f;

import fr.onsiea.engine.game.world.WorldUtils;
import fr.onsiea.engine.game.world.item.Item;
import fr.onsiea.engine.game.world.item.Item.ItemType;
import lombok.Getter;

/**
 * @author Seynax
 *
 */
public class ChunkItems
{
	private final ChunkRenderer					chunkRenderer;
	private @Getter final Map<Vector3f, Item>	items;
	private final Chunk							chunk;

	public ChunkItems(final Chunk chunkIn, final ChunkRenderer chunkRendererIn)
	{
		this.chunk			= chunkIn;
		this.chunkRenderer	= chunkRendererIn;
		this.items			= new LinkedHashMap<>();
	}

	private final void add(final Item itemIn)
	{
		itemIn.uniqueId(WorldUtils.uniqueItemId());

		if (this.items().containsKey(itemIn.position()))
		{
			return;
		}

		this.items().put(itemIn.position(), itemIn);

		this.chunkRenderer.add(itemIn);
	}

	public ChunkItems add(final ItemType typeIn, final Item... itemsIn)
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
	public ChunkItems add(final ItemType typeIn, final List<Item> itemsIn)
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
	public ChunkItems add(final Map<ItemType, List<Item>> itemsIn)
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

			this.chunkRenderer.remove(itemTypeIn, positionIn);

			return true;
		}

		return false;
	}

	public final Collection<Item> all()
	{
		return this.items.values();
	}

	public int size()
	{
		return this.items.size();
	}

	public boolean isEmpty()
	{
		return this.items.size() <= 0;
	}

	public final void cleanup()
	{
		this.items().clear();
	}
}
