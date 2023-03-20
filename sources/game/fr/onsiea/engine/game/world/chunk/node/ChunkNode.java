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
package fr.onsiea.engine.game.world.chunk.node;

import fr.onsiea.engine.game.world.chunk.Chunk;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Delegate;

/**
 * @author Seynax
 *
 */
public class ChunkNode
{
	private @Setter @Getter @Delegate Chunk	chunk;
	private @Getter ChunkNode				previous;
	private @Getter ChunkNode				next;

	public ChunkNode(final Chunk chunkIn)
	{
		this.chunk = chunkIn;
	}

	public final ChunkNode add(final Chunk chunkIn)
	{
		if (this.hasNext())
		{
			return this.next().add(chunkIn);
		}

		this.next			= new ChunkNode(chunkIn);
		this.next.previous	= this;

		return this.next;
	}

	public final ChunkNode remove()
	{
		if (this.hasPrevious())
		{
			this.previous.next = this.next;
		}
		if (this.hasNext())
		{
			this.next.previous = this.previous;
		}
		this.previous	= null;
		this.next		= null;
		this.chunk		= null;

		return this;
	}

	public final boolean isEmpty()
	{
		return this.chunk == null;
	}

	public final boolean hasPrevious()
	{
		return this.previous != null;
	}

	public final boolean hasNext()
	{
		return this.next != null;
	}

	@Override
	public int hashCode()
	{
		return this.chunk.hashCode();
	}

	@Override
	public boolean equals(final Object objIn)
	{
		if (objIn == null || !(objIn instanceof ChunkNode))
		{
			return false;
		}

		return this.chunk.equals(((ChunkNode) objIn).chunk());
	}
}