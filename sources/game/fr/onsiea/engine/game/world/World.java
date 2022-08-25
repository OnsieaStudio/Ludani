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

import java.util.HashMap;
import java.util.Map;

import org.joml.Random;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import fr.onsiea.engine.client.graphics.material.Material;
import fr.onsiea.engine.client.graphics.opengl.shaders.AdvInstancedShader;
import fr.onsiea.engine.client.graphics.opengl.texture.GLTextureArray;
import fr.onsiea.engine.client.graphics.opengl.texture.GLTextureArrayManager;
import fr.onsiea.engine.client.graphics.render.IRenderAPIContext;
import fr.onsiea.engine.client.graphics.shader.IShadersManager;
import fr.onsiea.engine.client.graphics.window.IWindow;
import fr.onsiea.engine.client.input.InputManager;
import fr.onsiea.engine.core.entity.Camera;
import fr.onsiea.engine.game.world.chunk.Chunk;
import fr.onsiea.engine.game.world.chunk.culling.CoordinateDistanceCulling;
import fr.onsiea.engine.game.world.chunk.culling.CubeDistanceCulling;
import fr.onsiea.engine.game.world.chunk.culling.CullingManager;
import fr.onsiea.engine.game.world.chunk.culling.FrustumCulling;
import fr.onsiea.engine.game.world.chunk.culling.SquareDistanceCulling;
import fr.onsiea.engine.game.world.item.Item;
import fr.onsiea.engine.game.world.item.Item.ItemType;
import fr.onsiea.engine.game.world.picking.Picker;
import fr.onsiea.engine.utils.maths.MathInstances;
import lombok.AccessLevel;
import lombok.Getter;

/**
 * @author Seynax
 *
 */
public class World
{
	private final Map<Vector3f, Chunk>				chunks;

	private @Getter(AccessLevel.PUBLIC) final long	seed;
	private final CullingManager					cullingManager;
	private final FrustumCulling					frustumCulling;
	private ItemType								itemType;
	private final boolean							cullingTest	= true;
	private static final Vector3f					chunkSize	= new Vector3f(16.0f, 16.0f, 16.0f);
	private boolean									hasPress;
	private boolean									hasPress0;
	private final Picker							picker;
	private final Selection							selection;
	private final AdvInstancedShader				advInstancedShader;
	private GLTextureArray								textureArray;

	public World(final IShadersManager shadersManagerIn, final IRenderAPIContext renderAPIContextIn,
			final Camera cameraIn)
	{
		MathInstances.random().setSeed(Random.newSeed() * 49331L);
		this.seed	= MathInstances.random().nextLong();
		this.chunks	= new HashMap<>();

		this.gen(renderAPIContextIn, cameraIn);
		this.cullingManager	= new CullingManager(new CoordinateDistanceCulling(8 * 16),
				new SquareDistanceCulling(6 * 16), new CubeDistanceCulling(4 * 16));
		this.frustumCulling	= new FrustumCulling();
		this.picker			= new Picker();
		this.selection		= new Selection();

		try
		{
			this.itemType = new ItemType("cube0",
					renderAPIContextIn.meshsManager().load("resources\\models\\cube2.obj"),
					new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(1.0f, 1.0f, 1.0f), new Material());
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		this.advInstancedShader = (AdvInstancedShader) renderAPIContextIn.shadersManager().get("advInstanced");
		
		final var texturesArrayManager = new GLTextureArrayManager(5, 16, 16, 5);
		texturesArrayManager.send("resources\\textures\\instanced0.png");
		texturesArrayManager.send("resources\\textures\\instanced1.png");
		texturesArrayManager.send("resources\\textures\\instanced2.png");
		texturesArrayManager.send("resources\\textures\\instanced3.png");
		this.textureArray = texturesArrayManager.textureArray();
	}

	public void gen(final IRenderAPIContext renderAPIContextIn, final Camera cameraIn)
	{
		for (var x = -16; x < 16; x++)
		{
			for (var z = -16; z < 16; z++)
			{
				final var	position	= new Vector3f(x, 0, z);
				final var	chunk		= new Chunk(this, position);
				final var	items		= new Item[16 * 16];

				var			i			= 0;
				for (var x0 = 0; x0 < 16; x0++)
				{
					for (var z0 = 0; z0 < 16; z0++)
					{
						final var item = new Item(this.itemType, new Vector3f(x0 * 2 + x * 16, 0, z0 * 2 + z * 16),
								new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(1.0f, 1.0f, 1.0f));
						items[i] = item;
						i++;
					}
				}
				chunk.add(this.itemType, items);
				chunk.genRender(renderAPIContextIn);

				this.chunks.put(position, chunk);

			}
		}
	}

	public void update(final InputManager inputManagerIn, final Camera cameraIn, final IWindow windowIn)
	{
		if (cameraIn.timedOrientation().hasChanged() || cameraIn.timedPosition().hasChanged())
		{
			this.selection.chunk	= null;
			this.selection.item		= null;
			this.picker.selectGameItem(this.chunks.values(), cameraIn, this.selection);
		}

		if (windowIn.key(GLFW.GLFW_KEY_F5) == GLFW.GLFW_PRESS && !this.hasPress0 && this.selection.item != null)
		{
			Chunk chunk = null;
			if (this.selection.item.position().x >= this.selection.chunk.position().x * 16
					&& this.selection.item.position().y >= this.selection.chunk.position().y * 16
					&& this.selection.item.position().z >= this.selection.chunk.position().z * 16
					&& this.selection.item.position().x < this.selection.chunk.position().x * 16 + 16
					&& this.selection.item.position().y < this.selection.chunk.position().y * 16 + 16
					&& this.selection.item.position().z < this.selection.chunk.position().z * 16 + 16)
			{
				chunk = this.selection.chunk;
			}
			else
			{
				final var	x			= (int) (this.selection.item.position().x() / 16.0f);
				final var	y			= (int) (this.selection.item.position().y() / 16.0f);
				final var	z			= (int) (this.selection.item.position().z() / 16.0f);

				final var	position	= new Vector3f(x, y, z);
				chunk = this.chunks.get(position);

				if (chunk == null)
				{
					chunk = new Chunk(this, position);
					this.chunks.put(position, chunk);
				}
			}

			chunk.add(this.itemType,
					new Item(this.itemType, new Vector3f(this.selection.item.position()).add(0.0f, 2.0f, 0.0f),
							new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(1.0f, 1.0f, 1.0f)));
			chunk.renderIsInitialized(false);

			this.hasPress0 = true;
		}
		else if (windowIn.key(GLFW.GLFW_KEY_F5) == GLFW.GLFW_RELEASE)
		{
			this.hasPress0 = false;
		}
		/**
		 * else
		 * {
		 * System.out.println("A");
		 * }
		 **/

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
		 * cameraIn.view());
		 *
		 * final var last = cameraIn.timedPosition().last();
		 * final var actual = cameraIn.position();
		 *
		 * if (!cameraIn.timedPosition().hasChanged() || (int) (last.x / 16.0f) == (int)
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
		 * final var dist = new Vector3f(cameraIn.position())
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
		 * final var position = new Vector3f((int) (cameraIn.position().x / 16.0f) + x,
		 * 0,
		 * (int) (cameraIn.position().z / 16.0f) + z);
		 *
		 * if (this.chunks.containsKey(position))
		 * {
		 * continue;
		 * }
		 *
		 * var isVisible = true;
		 * for (final var culling : this.cullingManager.cullings)
		 * {
		 * final var dist = new Vector3f(cameraIn.position()).sub(new
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

	public World draw(final Camera cameraIn, final IRenderAPIContext renderAPIContextIn)
	{
		this.advInstancedShader.attach();
		this.textureArray.bind();
		for (final Chunk chunk : this.chunks.values())
		{
			if (this.selection.chunk != null && this.selection.item != null
					&& chunk.position().equals(this.selection.chunk.position()))
			{
				this.advInstancedShader.selectedInstanceId().load((float) this.selection.item.instanceId());
				this.advInstancedShader.selectedPosition().load(this.selection.item.position());
			}
			else
			{
				this.advInstancedShader.selectedInstanceId().load(-1.0f);
			}

			if (this.cullingTest)
			{
				if (cameraIn.timedOrientation().hasChanged()
						&& !this.frustumCulling.insideFrustum(new Vector3f(chunk.position()).mul(World.chunkSize), 16))
				{
					chunk.visible(false);

					continue;
				}
				chunk.visible(true);
			}

			if (!chunk.visible())
			{
				continue;
			}

			if (!chunk.renderIsInitialized())
			{
				chunk.genRender(renderAPIContextIn);
			}

			chunk.draw();
		}
		renderAPIContextIn.shadersManager().detach();

		return this;
	}

	public void cleanup()
	{
		for (final Chunk chunk : this.chunks.values())
		{
			chunk.cleanup();
		}
	}

	public final static class Selection
	{
		public Item		item;
		public Chunk	chunk;
	}
}