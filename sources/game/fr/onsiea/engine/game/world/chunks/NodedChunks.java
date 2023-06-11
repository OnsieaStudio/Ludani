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

import org.joml.Vector3i;

import fr.onsiea.engine.game.world.World;
import fr.onsiea.engine.game.world.chunk.Chunk;
import fr.onsiea.engine.game.world.chunk.node.ChunkNode;
import fr.onsiea.engine.game.world.chunk.node.Node;
import fr.onsiea.engine.utils.function.IIFunction;
import lombok.Getter;

/**
 * @author Seynax
 *
 */
public class NodedChunks
{
	private final World											world;
	private Node<Node<Node<ChunkNode>>>	nodes;
	private ChunkNode											first;
	private ChunkNode											last;

	private @Getter int size;

	public NodedChunks(final World worldIn)
	{
		this.world = worldIn;
	}

	private final Chunk initializeChunks(final int xIn, final int yIn, final int zIn)
	{
		this.nodes = new Node<>(xIn);
		final var yNodes = new Node<Node<ChunkNode>>(yIn);
		this.nodes.value(yNodes);
		final var zNodes = new Node<ChunkNode>(zIn);
		yNodes.value(zNodes);
		final var chunk = new Chunk(this.world, new Vector3i(xIn, yIn, zIn));
		this.first	= new ChunkNode(chunk);
		this.last	= this.first;
		this.size++;
		zNodes.value(this.first);

		return chunk;
	}

	public Chunk getOrCreateChunk(final int xIn, final int yIn, final int zIn)
	{
		if (this.nodes == null)
		{
			return this.initializeChunks(xIn, yIn, zIn);
		}

		final var	xNodes	= this.nodes.getOrCreate(xIn);
		var			yNodes	= xNodes.value();
		if (yNodes == null)
		{
			yNodes = new Node<>(yIn);
			xNodes.value(yNodes);
		}
		else
		{
			yNodes = xNodes.value().getOrCreate(yIn);
		}
		var zNodes = yNodes.value();
		if (zNodes == null)
		{
			zNodes = new Node<>(zIn);
			yNodes.value(zNodes);
		}
		else
		{
			zNodes = yNodes.value().getOrCreate(zIn);
		}

		if (zNodes.value() == null)
		{
			this.last = this.last.add(new Chunk(this.world, new Vector3i(xIn, yIn, zIn)));
			zNodes.value(this.last);
			this.size++;
		}

		if (zNodes.value().chunk() == null)
		{
			zNodes.value().chunk(new Chunk(this.world, new Vector3i(xIn, yIn, zIn)));

		}

		return zNodes.value().chunk();
	}

	public Chunk getChunk(final int xIn, final int yIn, final int zIn)
	{
		if (this.nodes == null)
		{
			return null;
		}

		final var xNodes = this.nodes.get(xIn);
		if (xNodes == null || xNodes.value() == null)
		{
			return null;
		}

		final var yNodes = xNodes.value().get(yIn);
		if (yNodes == null || yNodes.value() == null)
		{
			return null;
		}

		final var zNodes = yNodes.value().get(zIn);
		if (zNodes == null || zNodes.value() == null)
		{
			return null;
		}

		return zNodes.value().chunk();
	}

	public NodedChunks remove(final Chunk chunkIn)
	{
		/**
		 * if (this.nodes == null) { return this; }
		 *
		 * final var xNodes = this.nodes.get(chunkIn.x()); if (xNodes == null || xNodes.value() == null) { return this; }
		 *
		 * final var yNodes = xNodes.value().get(chunkIn.y()); if (yNodes == null || yNodes.value() == null) { return this; }
		 *
		 * final var zNodes = yNodes.value().get(chunkIn.z()); if (zNodes == null || zNodes.value() == null) { return this; }
		 *
		 * final var lastPrevious = zNodes.value().previous(); final var lastNext = zNodes.value().next();
		 *
		 * if (zNodes.value().equals(this.first)) { this.first = lastNext; } if (zNodes.value().equals(this.last)) { this.last = lastPrevious; } zNodes.value().remove(); if (zNodes.value().isEmpty()) { if (zNodes ==
		 * yNodes.value()) { if (zNodes.hasPrevious()) { yNodes.value(zNodes.previous()); } else if (zNodes.hasNext()) { yNodes.value(zNodes.next()); } }
		 *
		 * zNodes.remove(); } if (yNodes.isEmpty()) { if (yNodes == xNodes.value()) { if (yNodes.hasPrevious()) { xNodes.value(yNodes.previous()); } else if (zNodes.hasNext()) { xNodes.value(yNodes.next()); } }
		 *
		 * yNodes.remove(); } if (xNodes.isEmpty()) { if (this.nodes == xNodes) { if (this.nodes.hasPrevious()) { this.nodes = xNodes.previous(); } else if (this.nodes.hasNext()) { this.nodes = xNodes.next(); } }
		 *
		 * xNodes.remove(); }
		 *
		 * this.size--;
		 **/

		return this;
	}

	public final NodedChunks forEach(final IIFunction<Chunk> functionIn)
	{
		var iterator = this.first;
		while (iterator != null)
		{
			functionIn.execute(iterator.chunk());

			iterator = iterator.next();
		}

		return this;
	}
}
