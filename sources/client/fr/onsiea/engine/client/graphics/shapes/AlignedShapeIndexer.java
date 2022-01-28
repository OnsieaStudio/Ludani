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

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * @author Seynax
 *
 */
public class AlignedShapeIndexer
{
	private final static List<C>		cs		= new ArrayList<>();
	private final static List<Integer>	indices	= new ArrayList<>();

	static
	{
		var i0 = 0;
		for (var i = 0; i < Cube.allPositionsAndTextureCoordinatesAndNormals.length; i += 3 + 2 + 3)
		{
			final var	x	= Cube.allPositionsAndTextureCoordinatesAndNormals[i];
			final var	y	= Cube.allPositionsAndTextureCoordinatesAndNormals[i + 1];
			final var	z	= Cube.allPositionsAndTextureCoordinatesAndNormals[i + 2];
			final var	ux	= Cube.allPositionsAndTextureCoordinatesAndNormals[i + 3];
			final var	uy	= Cube.allPositionsAndTextureCoordinatesAndNormals[i + 4];
			final var	nx	= Cube.allPositionsAndTextureCoordinatesAndNormals[i + 5];
			final var	ny	= Cube.allPositionsAndTextureCoordinatesAndNormals[i + 6];
			final var	nz	= Cube.allPositionsAndTextureCoordinatesAndNormals[i + 7];

			// final var	str	= x + " " + y + " " + z + " " + ux + " " + uy + " " + nx + " " + ny + " " + nz + " ";

			C			c	= null;

			for (final C c0 : AlignedShapeIndexer.cs)
			{
				if (c0.position.x == x && c0.position.y == y && c0.position.z == z && c0.texCoords.x == ux
						&& c0.texCoords.y == uy && c0.normal.x == nx && c0.normal.y == ny && c0.normal.z == nz)
				{
					c = c0;

					break;
				}
			}

			if (c == null)
			{
				c			= new C();
				c.position	= new Vector3f(x, y, z);
				c.texCoords	= new Vector2f(ux, uy);
				c.normal	= new Vector3f(nx, ny, nz);
				c.index		= i0;
				i0++;
				AlignedShapeIndexer.cs.add(c);
			}

			AlignedShapeIndexer.indices.add(c.index);
		}

		for (final C c : AlignedShapeIndexer.cs)
		{
			System.out.println(c.position.x() + "f, " + c.position.y() + "f, " + c.position.z() + "f, "
					+ c.texCoords.x() + "f, " + c.texCoords.y() + "f, " + c.normal.x() + "f, " + c.normal.y() + "f, "
					+ c.normal.z() + "f, ");
		}

		for (var i = 0; i < AlignedShapeIndexer.indices.size(); i += 3)
		{
			final int	a0	= AlignedShapeIndexer.indices.get(i);
			final int	a1	= AlignedShapeIndexer.indices.get(i + 1);
			final int	a2	= AlignedShapeIndexer.indices.get(i + 2);

			System.out.println(a0 + ", " + a1 + ", " + a2 + ",");
		}
	}

	final static class C
	{
		public Vector3f	position;
		public Vector2f	texCoords;
		public Vector3f	normal;
		public int		index;
	}
}
