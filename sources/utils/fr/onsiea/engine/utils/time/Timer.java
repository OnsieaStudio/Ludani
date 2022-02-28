/**
* Copyright 2021 Onsiea All rights reserved.<br><br>
*
* This file is part of Onsiea Engine project. (https://github.com/Onsiea/OnsieaEngine)<br><br>
*
* Onsiea Engine is [licensed] (https://github.com/Onsiea/OnsieaEngine/blob/main/LICENSE) under the terms of the "GNU General Public Lesser License v2.1" (LGPL-2.1).
* https://github.com/Onsiea/OnsieaEngine/wiki/License#license-and-copyright<br><br>
*
* Onsiea Engine is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 2.1 of the License, or
* (at your option) any later version.<br><br>
*
* Onsiea Engine is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU Lesser General Public License for more details.<br><br>
*
* You should have received a copy of the GNU Lesser General Public License
* along with Onsiea Engine.  If not, see <https://www.gnu.org/licenses/>.<br><br>
*
* Neither the name "Onsiea", "Onsiea Engine", or any derivative name or the names of its authors / contributors may be used to endorse or promote products derived from this software and even less to name another project or other work without clear and precise permissions written in advance.<br><br>
*
* @Author : Seynax (https://github.com/seynax)<br>
* @Organization : Onsiea Studio (https://github.com/Onsiea)
*/
package fr.onsiea.engine.utils.time;

/**
 * @author Seynax
 *
 */

public class Timer
{
	private long last;

	public Timer()
	{
		this.start();
	}

	public void start()
	{
		this.last(System.nanoTime());
	}

	/**
	 * Return nanoseconds time (long) between the execution of the start and stop method and restart the timer for a next execution
	 * @return nanoseconds time (long) between the execution of the start and stop method
	 */
	public long time()
	{
		final var	actual	= System.nanoTime();
		final var	time	= actual - this.last();
		this.last(actual);

		return time;
	}

	/**
	 * @param textCursorBlinkingSpeedIn
	 * @return
	 */
	public boolean isTime(long timeIn)
	{
		final var actual = System.nanoTime();

		if (actual - this.last() >= timeIn)
		{
			this.last(actual);

			return true;
		}

		return false;
	}

	public final long last()
	{
		return this.last;
	}

	private final void last(long lastIn)
	{
		this.last = lastIn;
	}
}
