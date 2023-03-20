/**
* Copyright 2021 Onsiea All rights reserved.<br><br>
*
* This file is part of Onsiea Engine project. (https://github.com/Onsiea/OnsieaEngine)<br><br>
*
* Onsiea Engine is [licensed] (https://github.com/Onsiea/OnsieaEngine/blob/main/LICENSE) under the terms of the "GNU General Public Lesser License v3.0" (GPL-3.0).
* https://github.com/Onsiea/OnsieaEngine/wiki/License#license-and-copyright<br><br>
*
* Onsiea Engine is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 3.0 of the License, or
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
package fr.onsiea.engine.utils;

import java.util.Collection;

/**
 * @author Seynax
 *
 */
public class LoopUtils
{
	private static int index;

	public final static <T> int loop(final ILoopFunction<T> loopFunctionIn, final Collection<T> collectionIn)
	{
		LoopUtils.index = 0;

		collectionIn.forEach(objectOfCollection -> loopFunctionIn.iterate(LoopUtils.index++, objectOfCollection));

		return LoopUtils.index;
	}

	public final static <T> int loop(final ILoopFunction<T> loopFunctionIn, final T[] elementsIn)
	{
		LoopUtils.index = 0;

		for (final var element : elementsIn)
		{
			loopFunctionIn.iterate(LoopUtils.index++, element);
		}

		return LoopUtils.index;
	}

	/**
	 *  Double for loop iteration with (x, y) coordinates between 0 and max :	<br>
	 *	0 < x < xMax; i ++														<br>
	 *			*																<br>
	 *	0 < y < yMax; y ++														<br>
	 *	index = xMax * yMax if not returned before								<br>
	 *
	 * @param loopFunctionIn
	 * @param xMaxIn
	 * @param yMaxIn
	 * @return
	 */
	public final static <T> int coordinate2DLoop(final int xMaxIn, final int yMaxIn, final T dataIn,
			final ICoordinate2DLoopFunction<T> loopFunctionIn)
	{
		LoopUtils.index = 0;

		for (var xPosition = 0; xPosition < xMaxIn; xPosition++)
		{
			for (var yPosition = 0; yPosition < yMaxIn; yPosition++)
			{
				if (!loopFunctionIn.iterate(LoopUtils.index, xPosition, yPosition, dataIn))
				{
					return LoopUtils.index;
				}

				LoopUtils.index++;
			}
		}

		return LoopUtils.index;
	}

	/**
	 *  Double for loop iteration with (x, y) coordinates between min and max : <br>
	 *	xMin < x < xMax; i ++													<br>
	 *			*																<br>
	 *	yMin < y < yMax; y ++													<br>
	 *	index = (xMax - xMin) * (yMax - yMin) if not returned before			<br>
	 *
	 * @param loopFunctionIn
	 * @param xMinIn
	 * @param xMaxIn
	 * @param yMinIn
	 * @param yMaxIn
	 * @return
	 */
	public final static <T> int coordinate2DLoop(final int xMinIn, final int xMaxIn, final int yMinIn, final int yMaxIn,
			final T dataIn, final ICoordinate2DLoopFunction<T> loopFunctionIn)
	{
		LoopUtils.index = 0;

		for (var xPosition = xMinIn; xPosition < xMaxIn; xPosition++)
		{
			for (var yPosition = yMinIn; yPosition < yMaxIn; yPosition++)
			{
				if (!loopFunctionIn.iterate(LoopUtils.index, xPosition, yPosition, dataIn))
				{
					return LoopUtils.index;
				}

				LoopUtils.index++;
			}
		}

		return LoopUtils.index;
	}

	/**
	 *  Double for loop iteration with (x, y) coordinates between 0 and max :	<br>
	 *	0 < x < xMax; i ++														<br>
	 *			*																<br>
	 *	0 < y < yMax; y ++														<br>
	 *	index = xMax * yMax if not returned before								<br>
	 *
	 * @param loopFunctionIn
	 * @param xMaxIn
	 * @param yMaxIn
	 * @return
	 */
	public final static <T> int coordinateInversed2DLoop(final int xMaxIn, final int yMaxIn, final T dataIn,
			final ICoordinate2DLoopFunction<T> loopFunctionIn)
	{
		LoopUtils.index = 0;

		for (var yPosition = 0; yPosition < yMaxIn; yPosition++)
		{
			for (var xPosition = 0; xPosition < xMaxIn; xPosition++)
			{
				if (!loopFunctionIn.iterate(LoopUtils.index, xPosition, yPosition, dataIn))
				{
					return LoopUtils.index;
				}

				LoopUtils.index++;
			}
		}

		return LoopUtils.index;
	}

	/**
	 *  Double for loop iteration with (x, y) coordinates between min and max : <br>
	 *	xMin < x < xMax; i ++													<br>
	 *			*																<br>
	 *	yMin < y < yMax; y ++													<br>
	 *	index = (xMax - xMin) * (yMax - yMin) if not returned before			<br>
	 *
	 * @param loopFunctionIn
	 * @param xMinIn
	 * @param xMaxIn
	 * @param yMinIn
	 * @param yMaxIn
	 * @return
	 */
	public final static <T> int coordinateInversed2DLoop(final int xMinIn, final int xMaxIn, final int yMinIn,
			final int yMaxIn, final T dataIn, final ICoordinate2DLoopFunction<T> loopFunctionIn)
	{
		LoopUtils.index = 0;

		for (var yPosition = yMinIn; yPosition < yMaxIn; yPosition++)
		{
			for (var xPosition = xMinIn; xPosition < xMaxIn; xPosition++)
			{
				if (!loopFunctionIn.iterate(LoopUtils.index, xPosition, yPosition, dataIn))
				{
					return LoopUtils.index;
				}

				LoopUtils.index++;
			}
		}

		return LoopUtils.index;
	}

	//

	/**
	 *  Double for loop iteration with (x, y) coordinates between 0 and max :	<br>
	 *	0 < x < xMax; i ++														<br>
	 *			*																<br>
	 *	0 < y < yMax; y ++														<br>
	 *	index = xMax * yMax if not returned before								<br>
	 *
	 * @param loopFunctionIn
	 * @param xMaxIn
	 * @param yMaxIn
	 * @return
	 */
	public final static <T> int coordinateDecrementX2DLoop(final int xMaxIn, final int yMaxIn, final T dataIn,
			final ICoordinate2DLoopFunction<T> loopFunctionIn)
	{
		LoopUtils.index = 0;

		for (var xPosition = xMaxIn - 1; xPosition >= 0; xPosition--)
		{
			for (var yPosition = 0; yPosition < yMaxIn; yPosition++)
			{
				if (!loopFunctionIn.iterate(LoopUtils.index, xPosition, yPosition, dataIn))
				{
					return LoopUtils.index;
				}

				LoopUtils.index++;
			}
		}

		return LoopUtils.index;
	}

	/**
	 *  Double for loop iteration with (x, y) coordinates between min and max : <br>
	 *	xMin < x < xMax; i ++													<br>
	 *			*																<br>
	 *	yMin < y < yMax; y ++													<br>
	 *	index = (xMax - xMin) * (yMax - yMin) if not returned before			<br>
	 *
	 * @param loopFunctionIn
	 * @param xMinIn
	 * @param xMaxIn
	 * @param yMinIn
	 * @param yMaxIn
	 * @return
	 */
	public final static <T> int coordinateDecrementX2DLoop(final int xMinIn, final int xMaxIn, final int yMinIn,
			final int yMaxIn, final T dataIn, final ICoordinate2DLoopFunction<T> loopFunctionIn)
	{
		LoopUtils.index = 0;

		for (var xPosition = xMaxIn - 1; xPosition >= xMinIn; xPosition--)
		{
			for (var yPosition = yMinIn; yPosition < yMaxIn; yPosition++)
			{
				if (!loopFunctionIn.iterate(LoopUtils.index, xPosition, yPosition, dataIn))
				{
					return LoopUtils.index;
				}

				LoopUtils.index++;
			}
		}

		return LoopUtils.index;
	}

	/**
	 *  Double for loop iteration with (x, y) coordinates between 0 and max :	<br>
	 *	0 < x < xMax; i ++														<br>
	 *			*																<br>
	 *	0 < y < yMax; y ++														<br>
	 *	index = xMax * yMax if not returned before								<br>
	 *
	 * @param loopFunctionIn
	 * @param xMaxIn
	 * @param yMaxIn
	 * @return
	 */
	public final static <T> int coordinateDecrementXInversed2DLoop(final int xMaxIn, final int yMaxIn, final T dataIn,
			final ICoordinate2DLoopFunction<T> loopFunctionIn)
	{
		LoopUtils.index = 0;

		for (var yPosition = 0; yPosition < yMaxIn; yPosition++)
		{
			for (var xPosition = xMaxIn - 1; xPosition >= 0; xPosition--)
			{
				if (!loopFunctionIn.iterate(LoopUtils.index, xPosition, yPosition, dataIn))
				{
					return LoopUtils.index;
				}

				LoopUtils.index++;
			}
		}

		return LoopUtils.index;
	}

	/**
	 *  Double for loop iteration with (x, y) coordinates between min and max : <br>
	 *	xMin < x < xMax; i --													<br>
	 *			*																<br>
	 *	yMin < y < yMax; y ++													<br>
	 *	index = (xMax - xMin) * (yMax - yMin) if not returned before			<br>
	 *
	 * @param loopFunctionIn
	 * @param xMinIn
	 * @param xMaxIn
	 * @param yMinIn
	 * @param yMaxIn
	 * @return
	 */
	public final static <T> int coordinateDecrementXInversed2DLoop(final int xMinIn, final int xMaxIn, final int yMinIn,
			final int yMaxIn, final T dataIn, final ICoordinate2DLoopFunction<T> loopFunctionIn)
	{
		LoopUtils.index = 0;

		for (var yPosition = yMinIn; yPosition < yMaxIn; yPosition++)
		{
			for (var xPosition = xMaxIn - 1; xPosition >= xMinIn; xPosition--)
			{
				if (!loopFunctionIn.iterate(LoopUtils.index, xPosition, yPosition, dataIn))
				{
					return LoopUtils.index;
				}

				LoopUtils.index++;
			}
		}

		return LoopUtils.index;
	}

	public interface ILoopFunction<T>
	{
		void iterate(int indexIn, T objectIn);
	}

	public interface ICoordinate2DLoopFunction<T>
	{
		boolean iterate(int indexIn, int xPositionIn, int yPositionIn, T dataIn);
	}
}