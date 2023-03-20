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

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * @author Seynax
 *
 */
@RequiredArgsConstructor
public class ChunksNodes<T>
{
	private @Getter final int		coordinate;

	private @Getter ChunksNodes<T>	next;
	private @Getter ChunksNodes<T>	previous;

	private @Setter @Getter T		value;

	public ChunksNodes(final int coordinateIn, final T valueIn)
	{
		this.coordinate	= coordinateIn;
		this.value		= valueIn;
	}

	private final ChunksNodes<T> addNext(final int coordinateIn)
	{
		final var lastNext = this.next;
		this.next			= new ChunksNodes<>(coordinateIn);
		this.next.previous	= this;
		if (lastNext != null)
		{
			this.next.next		= lastNext;
			lastNext.previous	= this.next;
		}
		return this.next;
	}

	private final ChunksNodes<T> addPrevious(final int coordinateIn)
	{
		final var lastPrevious = this.previous;
		this.previous		= new ChunksNodes<>(coordinateIn);
		this.previous.next	= this;
		if (lastPrevious != null)
		{
			lastPrevious.previous	= lastPrevious;
			lastPrevious.next		= this.previous;
		}
		return this.previous;
	}

	public final ChunksNodes<T> getOrCreate(final int coordinateIn)
	{
		if (coordinateIn > this.coordinate)
		{
			if (this.hasNext() && coordinateIn >= this.next.coordinate)
			{
				return this.next.getOrCreate(coordinateIn);
			}
			return this.addNext(coordinateIn);
		}
		if (coordinateIn < this.coordinate)
		{
			if (this.hasPrevious() && coordinateIn <= this.previous.coordinate)
			{
				return this.previous.getOrCreate(coordinateIn);
			}
			return this.addPrevious(coordinateIn);
		}
		if (coordinateIn == this.coordinate)
		{
			return this;
		}

		return null;
	}

	public final ChunksNodes<T> get(final int coordinateIn)
	{
		if (coordinateIn < this.coordinate)
		{
			return this.hasPrevious() ? this.previous.get(coordinateIn) : null;
		}
		if (coordinateIn > this.coordinate)
		{
			return this.hasNext() ? this.next.get(coordinateIn) : null;
		}
		if (coordinateIn == this.coordinate)
		{
			return this;
		}

		return null;
	}

	public ChunksNodes<T> remove()
	{
		this.value(null);
		if (this.hasNext())
		{
			this.next.previous = this.previous;
		}
		this.next = null;
		if (this.hasPrevious())
		{
			this.previous.next = this.next;
		}
		this.previous = null;

		return this;
	}

	public ChunksNodes<T> remove(final int coordinateIn)
	{
		final var chunksNodes = this.get(coordinateIn);

		if (chunksNodes == null)
		{
			return this;
		}

		chunksNodes.remove();

		return this;
	}

	public int size()
	{
		var	i			= 0;
		var	iterator	= this;
		while (iterator != null)
		{
			i++;

			iterator = iterator.previous();
		}
		iterator = this;
		while (iterator != null)
		{
			i++;

			iterator = iterator.next();
		}

		return i + 1;
	}

	public boolean isEmpty()
	{
		return this.value == null;
	}

	public final boolean hasPrevious()
	{
		return this.previous != null;
	}

	public final boolean hasNext()
	{
		return this.next != null;
	}
}