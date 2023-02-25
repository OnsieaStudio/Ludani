/**
 * -* Copyright 2021 Onsiea All rights reserved.<br>
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
package fr.onsiea.engine.game.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Random;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import fr.onsiea.engine.client.graphics.glfw.window.Window;
import fr.onsiea.engine.client.graphics.render.IRenderAPIContext;
import fr.onsiea.engine.client.graphics.shader.IShadersManager;
import fr.onsiea.engine.client.graphics.window.IWindow;
import fr.onsiea.engine.client.input.InputManager;
import fr.onsiea.engine.core.entity.PlayerEntity;
import fr.onsiea.engine.game.world.chunk.Chunk;
import fr.onsiea.engine.game.world.chunk.culling.CullingManager;
import fr.onsiea.engine.game.world.chunk.culling.FrustumCulling;
import fr.onsiea.engine.game.world.item.Item;
import fr.onsiea.engine.game.world.item.Item.ItemType;
import fr.onsiea.engine.game.world.item.Item.ItemTypeVariant;
import fr.onsiea.engine.game.world.item.ItemsLoader;
import fr.onsiea.engine.game.world.picking.Picker;
import fr.onsiea.engine.game.world.renderer.WorldRenderer;
import fr.onsiea.engine.utils.maths.MathInstances;
import fr.onsiea.engine.utils.maths.MathUtils;
import fr.onsiea.engine.utils.time.Timer;
import lombok.Getter;

/**
 * @author Seynax
 *
 */
public class World
{
	private final WorldInformations		worldInformations;
	private final @Getter WorldRenderer	worldRenderer;

	public World(final IShadersManager shadersManagerIn, final IRenderAPIContext renderAPIContextIn,
			final PlayerEntity playerEntityIn, final IWindow windowIn)
	{
		MathInstances.random().setSeed(Random.newSeed() * 49331L);

		this.worldInformations	= new WorldInformations();

		this.worldRenderer		= new WorldRenderer(this.worldInformations, renderAPIContextIn);

		this.gen(renderAPIContextIn, playerEntityIn);

		//picker.updateProjection(MathInstances.projectionMatrix());
		//picker.updateClipAndEyeCoords(windowIn.effectiveWidth()/2.0f, windowIn.effectiveHeight()/2.0f, windowIn.settings().width(), windowIn.settings().height(), 16.0f, 16.0f);
	}

	public final ItemTypeVariant randomVariant()
	{
		if (this.itemsLoader().items().size() - 1 < 0)
		{
			return null;
		}

		final var index = MathUtils.randomInt(0, this.itemsLoader().items().size() - 1);

		return this.itemsLoader().items().get(index);
	}

	public void gen(final IRenderAPIContext renderAPIContextIn, final PlayerEntity playerEntityIn)
	{
		final Map<ItemType, List<Item>> typedItems = new HashMap<>();
		for (var x = 0; x < 8; x++)
		{
			for (var z = 0; z < 8; z++)
			{
				final var	position	= new Vector3f(x, 0, z);

				final var	chunk		= new Chunk(this, position);

				for (var x0 = 0; x0 < 16; x0 += 1)
				{
					for (var z0 = 0; z0 < 16; z0 += 1)
					{
						final var variant = this.itemsLoader().items().get(0); //.randomVariant();
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

						items.add(new Item(variant, new Vector3f(x0 + x * 16, 0, z0 + z * 16),
								new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(1.0f, 1.0f, 1.0f)));
					}
				}
				chunk.add(typedItems);
				typedItems.clear();
				this.worldRenderer.initRenderOfChunk(chunk);

				this.worldInformations.chunks().put(position, chunk);

			}
		}
		typedItems.clear();
	}

	public void update(final InputManager inputManagerIn, final PlayerEntity playerEntityIn, final IWindow windowIn,
			final boolean freePickingMouseIn)
	{
		this.picker().update(playerEntityIn, inputManagerIn, windowIn);
		this.worldRenderer.update(playerEntityIn);

		if (playerEntityIn.timedOrientation().hasChanged() || playerEntityIn.timedPosition().hasChanged()
				|| freePickingMouseIn && inputManagerIn.cursor().hasMoved())
		{
			this.picker().selectGameItem(this.worldInformations.chunks().values(), playerEntityIn);
		}

		// Place object
		if (GLFW.glfwGetMouseButton(((Window) windowIn).handle(), GLFW.GLFW_MOUSE_BUTTON_1) == GLFW.GLFW_PRESS
				&& this.picker().item() != null && this.placeTimer().isTime(1_400_500_00L))
		{
			this.placeTimer().start();

			final var	position	= WorldUtils.newChunkedPosition(this.picker().toMake());
			var			chunk		= this.chunks().get(position);

			if (chunk == null)
			{
				chunk = new Chunk(this, position);
				this.chunks().put(position, chunk);
			}
			final var variant = playerEntityIn.hotBar().selectedItem();
			if (variant != null)
			{
				chunk.add(variant.itemType(), new Item(variant, new Vector3f(this.picker().toMake()),
						new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(1.0f, 1.0f, 1.0f)));

				chunk.renderIsInitialized(false);

				this.picker().selectGameItem(this.chunks().values(), playerEntityIn);
			}
		}

		// Destroy object
		if (GLFW.glfwGetMouseButton(((Window) windowIn).handle(), GLFW.GLFW_MOUSE_BUTTON_2) == GLFW.GLFW_PRESS
				&& this.picker().item() != null && this.picker().chunk() != null
				&& this.destroyTimer().isTime(1_400_500_00L))
		{
			this.picker().chunk().removeAll(this.picker().item());
			this.destroyTimer().start();

			if (this.picker().chunk().isEmpty())
			{
				this.chunks().remove(this.picker().chunk().position());
			}

			this.picker().chunk().renderIsInitialized(false);

			this.picker().selectGameItem(this.chunks().values(), playerEntityIn);
		}

		/**
		 * if (windowIn.key(GLFW.GLFW_KEY_F4) == GLFW.GLFW_PRESS && !this.hasPress)
		 * {
		 * this.cullingTest = !this.cullingTest;
		 * this.hasPress = true;
		 * }
		 * else if (windowIn.key(GLFW.GLFW_KEY_F4) == GLFW.GLFW_RELEASE)
		 * {
		 * this.hasPress = false;
		 * }
		 **/

		/**
		 * if (this.cullingTest)
		 * {
		 * this.frustumCulling.updateFrustum(MathInstances.projectionMatrix(),
		 * playerEntityIn.view());
		 *
		 * final var last = playerEntityIn.timedPosition().last();
		 * final var actual = playerEntityIn.position();
		 *
		 * if (!playerEntityIn.timedPosition().hasChanged() || (int) (last.x / 16.0f) == (int)
		 * (actual.x / 16.0f)
		 * && (int) (last.z / 16.0f) == (int) (actual.z / 16.0f))
		 * {
		 * return;
		 * }
		 *
		 * final List<Vector3f> positionsToRemove = new ArrayList<>();
		 * for (final Chunk chunk : this.chunks.values())
		 * {
		 * var isVisible = true;
		 * for (final var culling : this.cullingManager.cullings)
		 * {
		 * final var dist = new Vector3f(playerEntityIn.position())
		 * .sub(new Vector3f(chunk.position()).mul(World.chunkSize));
		 *
		 * if (culling.isCulling(dist))
		 * {
		 * isVisible = false;
		 *
		 * break;
		 * }
		 * }
		 *
		 * if (!isVisible)
		 * {
		 * positionsToRemove.add(chunk.position());
		 * }
		 * }
		 *
		 * for (final var positionToRemove : positionsToRemove)
		 * {
		 * final var chunk = this.chunks.get(positionToRemove);
		 *
		 * if (chunk != null)
		 * {
		 * this.chunks.remove(positionToRemove);
		 * chunk.cleanup();
		 * }
		 * }
		 * positionsToRemove.clear();
		 * }
		 *
		 * for (var x = -16; x < 16; x++)
		 * {
		 * for (var z = -16; z < 16; z++)
		 * {
		 * final var position = new Vector3f((int) (playerEntityIn.position().x / 16.0f) + x,
		 * 0,
		 * (int) (playerEntityIn.position().z / 16.0f) + z);
		 *
		 * if (this.chunks.containsKey(position))
		 * {
		 * continue;
		 * }
		 *
		 * var isVisible = true;
		 * for (final var culling : this.cullingManager.cullings)
		 * {
		 * final var dist = new Vector3f(playerEntityIn.position()).sub(new
		 * Vector3f(position).mul(World.chunkSize));
		 *
		 * if (culling.isCulling(dist))
		 * {
		 * isVisible = false;
		 *
		 * break;
		 * }
		 * }
		 *
		 * if (!isVisible)
		 * {
		 * continue;
		 * }
		 *
		 * final var chunk = new Chunk(this, position);
		 * final var items = new Item[16 * 16];
		 *
		 * var i = 0;
		 * for (var x0 = 0; x0 < 16; x0++)
		 * {
		 * for (var z0 = 0; z0 < 16; z0++)
		 * {
		 * final var item = new Item(this.itemType,
		 * new Vector3f(x0 * 2 + position.x * 16, 0, z0 * 2 + position.z * 16),
		 * new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(1.0f, 1.0f, 1.0f));
		 * items[i] = item;
		 * i++;
		 * }
		 * }
		 * chunk.add(this.itemType, items);
		 *
		 * this.chunks.put(position, chunk);
		 * }
		 * }
		 **/
	}

	/**
	 *
	 */
	public void reset()
	{
		this.picker().reset();
	}

	public void cleanup()
	{
		for (final Chunk chunk : this.chunks().values())
		{
			chunk.cleanup();
		}
	}

	public Map<Vector3f, Chunk> chunks()
	{
		return this.worldInformations.chunks();
	}

	public CullingManager cullingManager()
	{
		return this.worldInformations.cullingManager();
	}

	public boolean cullingTest()
	{
		return this.worldInformations.cullingTest();
	}

	public Timer destroyTimer()
	{
		return this.worldInformations.destroyTimer();
	}

	public FrustumCulling frustumCulling()
	{
		return this.worldInformations.frustumCulling();
	}

	public Picker picker()
	{
		return this.worldInformations.picker();
	}

	public Timer placeTimer()
	{
		return this.worldInformations.placeTimer();
	}

	public long seed()
	{
		return this.worldInformations.seed();
	}

	public WorldInformations cullingTest(final boolean cullingTestIn)
	{
		return this.worldInformations.cullingTest(cullingTestIn);
	}

	public ItemsLoader itemsLoader()
	{
		return this.worldRenderer.itemsLoader();
	}
}