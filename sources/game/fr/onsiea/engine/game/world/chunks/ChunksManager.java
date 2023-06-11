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
package fr.onsiea.engine.game.world.chunks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Vector3f;

import fr.onsiea.engine.client.graphics.window.IWindow;
import fr.onsiea.engine.client.input.InputManager;
import fr.onsiea.engine.core.entity.PlayerEntity;
import fr.onsiea.engine.game.world.World;
import fr.onsiea.engine.game.world.WorldUtils;
import fr.onsiea.engine.game.world.chunk.Chunk;
import fr.onsiea.engine.game.world.chunk.ChunkUtils;
import fr.onsiea.engine.game.world.chunk.culling.CoordinateDistanceCulling;
import fr.onsiea.engine.game.world.chunk.culling.CubeDistanceCulling;
import fr.onsiea.engine.game.world.chunk.culling.CullingManager;
import fr.onsiea.engine.game.world.chunk.culling.FrustumCulling;
import fr.onsiea.engine.game.world.chunk.culling.SquareDistanceCulling;
import fr.onsiea.engine.game.world.item.Item;
import fr.onsiea.engine.game.world.item.Item.ItemType;
import fr.onsiea.engine.game.world.item.Item.ItemTypeVariant;
import fr.onsiea.engine.game.world.picking.Picker;
import fr.onsiea.engine.game.world.renderer.WorldRenderer;
import fr.onsiea.engine.utils.time.Timer;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Seynax
 *
 */
public class ChunksManager
{
	private final World						world;
	private @Getter final Chunks			chunks;
	private @Getter final Picker			picker;
	private @Getter final CullingManager	cullingManager;
	private @Getter final FrustumCulling	frustumCulling;
	private @Getter @Setter boolean			cullingTest;
	private boolean							cameraHasChanged			= false;
	private boolean							pickMustBeUpdated			= false;
	private boolean							pickMustBeUpdatedQuickly	= false;
	private @Getter final Timer				placeTimer;
	private @Getter final Timer				destroyTimer;
	private @Getter final Timer				pickTimer;
	private @Getter final Timer				quickPickTimer;

	public ChunksManager(final World worldIn)
	{
		this.world	= worldIn;
		this.chunks	= new Chunks(this.world);

		// Culling

		this.cullingManager	= new CullingManager(new CoordinateDistanceCulling(8 * 16), new SquareDistanceCulling(6 * 16), new CubeDistanceCulling(4 * 16));
		this.frustumCulling	= new FrustumCulling();
		this.cullingTest	= true;

		// Picking

		this.picker = new Picker();

		// Timing

		this.placeTimer		= new Timer().start();
		this.destroyTimer	= new Timer().start();
		this.pickTimer		= new Timer().start();
		this.quickPickTimer	= new Timer().start();
	}

	public final void gen(final float chunkNumberXIn, final float chunkNumberYIn, final WorldRenderer worldRendererIn)
	{
		final Map<ItemType, List<Item>>	typedItems	= new HashMap<>();
		var								i			= 0;
		for (var x = 0; x < chunkNumberXIn; x++)
		{
			for (var z = 0; z < chunkNumberYIn; z++)
			{
				final var chunk = this.chunks.getOrCreate(x, 0, z);

				for (var x0 = 0; x0 < ChunkUtils.SIZE.x(); x0++)
				{
					for (var z0 = 0; z0 < ChunkUtils.SIZE.y(); z0++)
					{
						final var variant = worldRendererIn.itemsLoader().randomVariant();
						if (variant == null)
						{
							continue;
						}

						var items = typedItems.get(variant.itemType());

						if (items == null)
						{
							items = new ArrayList<>();
							typedItems.put(variant.itemType(), items);
						}

						items.add(new Item(variant, new Vector3f(x0 + x * ChunkUtils.SIZE.x(), i * (1.0f / ChunkUtils.SIZE.x()), z0 + z * ChunkUtils.SIZE.y()), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(1.0f, 1.0f, 1.0f)));
					}
				}
				chunk.items().add(typedItems);

				typedItems.clear();
				worldRendererIn.initRenderOfChunk(chunk);
				i++;
			}
		}
		typedItems.clear();
	}

	private final void selectGameItem(final InputManager inputManagerIn, final PlayerEntity playerEntityIn, final IWindow windowIn)
	{
		this.picker().update(playerEntityIn, inputManagerIn, windowIn);
		this.picker().selectGameItem(this.chunks, playerEntityIn);
	}

	public final void update(final InputManager inputManagerIn, final PlayerEntity playerEntityIn, final IWindow windowIn, final boolean freePickingMouseIn)
	{
		if (this.pickMustBeUpdated && this.pickTimer().isTime(1_400_500_0L) || this.pickMustBeUpdatedQuickly && this.quickPickTimer().isTime(1_400L))
		{
			this.pickTimer.start();
			this.quickPickTimer().start();
			this.selectGameItem(inputManagerIn, playerEntityIn, windowIn);

			this.pickMustBeUpdatedQuickly	= false;
			this.pickMustBeUpdated			= false;
			this.cameraHasChanged			= false;
		}

		if (!this.cameraHasChanged)
		{
			this.cameraHasChanged = playerEntityIn.timedOrientation().hasChanged() || playerEntityIn.timedPosition().hasChanged() || freePickingMouseIn && inputManagerIn.cursor().hasMoved();
		}

		if (this.cameraHasChanged)
		{
			if (!playerEntityIn.timedOrientation().hasChanged() && !playerEntityIn.timedPosition().hasChanged() && (!freePickingMouseIn || !inputManagerIn.cursor().hasMoved()) && !this.pickMustBeUpdatedQuickly)
			{
				this.pickMustBeUpdatedQuickly = true;

			}
			else if (!this.pickMustBeUpdated)
			{
				this.pickMustBeUpdated = true;
			}
		}

		// Place object

		ItemTypeVariant variant = null;
		if (this.picker().item() != null && inputManagerIn.shortcuts().isEnabled("PLACE") && this.placeTimer().isTime(1_100_500_00L) && playerEntityIn.hotBar() != null && (variant = playerEntityIn.hotBar().selectedItem()) != null)
		{
			final var	position	= WorldUtils.newChunkedPosition(this.picker().toMake());
			final var	chunk		= this.chunks.getOrCreate(position.x, position.y, position.z);

			this.placeTimer().start();

			chunk.items().add(variant.itemType(), new Item(variant, new Vector3f(this.picker().toMake()), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(1.0f, 1.0f, 1.0f)));

			chunk.renderIsInitialized(false);

			// this.pickMustBeUpdatedQuickly = true;
			this.selectGameItem(inputManagerIn, playerEntityIn, windowIn);
		}

		// Destroy object
		if (inputManagerIn.shortcuts().isEnabled("BREAK") && this.picker().chunk() != null && this.destroyTimer().isTime(1_400_500_00L))

		{
			this.picker().chunk().items().removeAll(this.picker().item());
			this.destroyTimer().start();

			if (this.picker().chunk().isEmpty())
			{
				this.chunks.remove(this.picker().chunk().x(), this.picker().chunk().y(), this.picker().chunk().z());
			}
			else
			{
				this.picker().chunk().renderIsInitialized(false);
			}

			// this.pickMustBeUpdatedQuickly = true;
			this.selectGameItem(inputManagerIn, playerEntityIn, windowIn);
		}
	}

	public void reset()
	{
		this.picker().reset();
	}

	public void cleanup()
	{
		this.chunks.forEach(Chunk::cleanup);
	}
}