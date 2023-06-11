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

import java.util.LinkedHashMap;
import java.util.Map;

import org.joml.Vector3i;

import fr.onsiea.engine.game.world.World;
import fr.onsiea.engine.game.world.chunk.Chunk;
import fr.onsiea.engine.utils.function.IIFunction;
import lombok.RequiredArgsConstructor;

/**
 * @author Seynax
 *
 */
@RequiredArgsConstructor
public class Chunks
{
	private Map<Float, Map<Float, Map<Float, Chunk>>>	chunks;
	private World										world;
	private int											size;

	public Chunks(final World worldIn)
	{
		this.world	= worldIn;
		this.chunks	= new LinkedHashMap<>();
	}

	public final boolean contains(final float xIn, final float yIn, final float zIn)
	{
		final var mapX = this.chunks.get(xIn);
		if (mapX == null)
		{
			return false;
		}

		final var mapY = mapX.get(yIn);
		if (mapY == null)
		{
			return false;
		}

		return mapY.containsKey(zIn);
	}

	public final Chunk getOrCreate(final float xIn, final float yIn, final float zIn)
	{
		var mapX = this.chunks.get(xIn);
		if (mapX == null)
		{
			mapX = new LinkedHashMap<>();
			this.chunks.put(xIn, mapX);
		}
		var mapY = mapX.get(yIn);
		if (mapY == null)
		{
			mapY = new LinkedHashMap<>();
			mapX.put(yIn, mapY);
		}
		var chunk = mapY.get(zIn);
		if (chunk == null)
		{
			chunk = new Chunk(this.world, new Vector3i((int) xIn, (int) yIn, (int) zIn));
			mapY.put(zIn, chunk);
			this.size++;
		}

		return chunk;
	}

	public final Chunk get(final float xIn, final float yIn, final float zIn)
	{
		final var mapX = this.chunks.get(xIn);
		if (mapX == null)
		{
			return null;
		}

		final var mapY = mapX.get(yIn);
		if (mapY == null)
		{
			return null;
		}

		return mapY.get(zIn);
	}

	public Chunks remove(final float xIn, final float yIn, final float zIn)
	{
		final var mapX = this.chunks.get(xIn);
		if (mapX == null)
		{
			return null;
		}

		final var mapY = mapX.get(yIn);
		if (mapY == null)
		{
			return null;
		}

		if (mapY.containsKey(zIn))
		{
			this.size--;
			mapY.remove(zIn);
		}
		return this;
	}

	public int size()
	{
		return this.size;
	}

	public boolean isEmpty()
	{
		return this.size == 0;
	}

	public void forEach(final IIFunction<Chunk> functionIn)
	{
		for (final var mapX : this.chunks.values())
		{
			for (final var mapY : mapX.values())
			{
				for (final var chunk : mapY.values())
				{
					functionIn.execute(chunk);
				}
			}
		}
	}
}