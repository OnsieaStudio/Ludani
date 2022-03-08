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

import org.joml.Vector4f;

/**
 * @author Seynax
 *
 */
public class Color4i
{
	public final static Vector4f color(int pixelIn)
	{
		final var	r	= pixelIn >> 16 & 0xFF;
		final var	g	= pixelIn >> 8 & 0xFF;
		final var	b	= pixelIn & 0xFF;
		final var	a	= pixelIn >> 24 & 0xFF;

		return new Vector4f(r, g, b, a);
	}

	public final static void color(int pixelIn, Vector4f vecIn)
	{
		vecIn.x	= pixelIn >> 16 & 0xFF;	// r
		vecIn.y	= pixelIn >> 8 & 0xFF;	// g
		vecIn.z	= pixelIn & 0xFF;		// b
		vecIn.w	= pixelIn >> 24 & 0xFF;	// a
	}

	public final static int pixel(int rIn, int gIn, int bIn, int aIn)
	{
		return (aIn & 0xff) << 24 | (rIn & 0xff) << 16 | (gIn & 0xff) << 8 | bIn & 0xff;
	}
}