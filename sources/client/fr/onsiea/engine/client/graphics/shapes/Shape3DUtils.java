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
package fr.onsiea.engine.client.graphics.shapes;

/**
 * @author Seynax
 *
 */
public class Shape3DUtils
{
	public final static float[] translate(float[] verticesIn, float xIn, float yIn, float zIn)
	{
		final var translated = new float[verticesIn.length];

		for (var i = 0; i < verticesIn.length; i += 3)
		{
			translated[i]		= verticesIn[i] + xIn;
			translated[i + 1]	= verticesIn[i + 1] + yIn;
			translated[i + 2]	= verticesIn[i + 2] + zIn;
		}

		return translated;
	}

	public final static float[] resize(float[] verticesIn, float sizeIn)
	{
		final var sized = new float[verticesIn.length];

		for (var i = 0; i < verticesIn.length; i += 3)
		{
			sized[i]		= verticesIn[i] * sizeIn;
			sized[i + 1]	= verticesIn[i + 1] * sizeIn;
			sized[i + 2]	= verticesIn[i + 2] * sizeIn;
		}

		return sized;
	}

	public final static float[] resize(float[] verticesIn, float sizeXIn, float sizeYIn, float sizeZIn)
	{
		final var sized = new float[verticesIn.length];

		for (var i = 0; i < verticesIn.length; i += 3)
		{
			sized[i]		= verticesIn[i] * sizeXIn;
			sized[i + 1]	= verticesIn[i + 1] * sizeYIn;
			sized[i + 2]	= verticesIn[i + 2] * sizeZIn;
		}

		return sized;
	}

	public final static float[] translateAndResize(float[] verticesIn, float xIn, float yIn, float zIn, float sizeIn)
	{
		return Shape3DUtils.resize(Shape3DUtils.translate(verticesIn, xIn, yIn, zIn), sizeIn);
	}

	public final static float[] translateAndResize(float[] verticesIn, float xIn, float yIn, float zIn, float sizeXIn,
			float sizeYIn, float sizeZIn)
	{
		return Shape3DUtils.resize(Shape3DUtils.translate(verticesIn, xIn, yIn, zIn), sizeXIn, sizeYIn, sizeZIn);
	}

	public final static boolean shareOneAxe(float xIn, float yIn, float zIn, float x0In, float y0In, float z0In)
	{
		if (xIn == x0In || yIn == y0In || zIn == z0In)
		{
			return true;
		}

		return false;
	}

	public final static int sharedAxes(float xIn, float yIn, float zIn, float x0In, float y0In, float z0In)
	{
		var i = 0;

		if (xIn == x0In)
		{
			i++;
		}
		if (yIn == y0In)
		{
			i++;
		}
		if (zIn == z0In)
		{
			i++;
		}

		return i;
	}
}