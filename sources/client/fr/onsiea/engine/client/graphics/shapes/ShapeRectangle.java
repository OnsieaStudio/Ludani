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

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import fr.onsiea.engine.utils.maths.shapes.AABB2f;
import fr.onsiea.engine.utils.maths.shapes.OrientedRectangle;
import fr.onsiea.engine.utils.maths.shapes.Rectangle;
import fr.onsiea.engine.utils.maths.transformations.Transformations2f;
import fr.onsiea.engine.utils.maths.triangle.Triangle2f;
import fr.onsiea.engine.utils.maths.vertice.Vertice2f;

/**
 * @author Seynax
 *
 */
public class ShapeRectangle
{
	private final static float[]	VERTICES	=
	{ 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f };

	private final static float[]	UVS			= ShapeRectangle.VERTICES;

	private final static int[]		INDICES		=
	{ 0, 1, 2, 2, 3, 0 };

	public static final float[] vertices()
	{
		return ShapeRectangle.VERTICES;
	}

	public static final float[] uvs()
	{
		return ShapeRectangle.UVS;
	}

	public static final int[] indices()
	{
		return ShapeRectangle.INDICES;
	}

	/**
	 * @formatter:off
	 */
	public final static Vertice2f	V00				= new Vertice2f(0.0f, 0.0f);
	public final static Vertice2f	V10				= new Vertice2f(1.0f, 0.0f);
	public final static Vertice2f	V01				= new Vertice2f(0.0f, 1.0f);
	public final static Vertice2f	V11				= new Vertice2f(1.0f, 1.0f);

	public final static float[]			positions			=
	{
		ShapeRectangle.V00.x(), ShapeRectangle.V00.y(),
		ShapeRectangle.V10.x(), ShapeRectangle.V10.y(),
		ShapeRectangle.V01.x(), ShapeRectangle.V01.y(),
		ShapeRectangle.V11.x(), ShapeRectangle.V11.y(),
	};

	public final static Triangle2f	LOW			= new Triangle2f(ShapeRectangle.V00, ShapeRectangle.V10,
			ShapeRectangle.V11);
	public final static Triangle2f	HIGH			= new Triangle2f(ShapeRectangle.V11, ShapeRectangle.V01,
			ShapeRectangle.V00);

	public final static float[]		allPositions		=
	{
		ShapeRectangle.LOW.first().x(), ShapeRectangle.LOW.first().y(),
		ShapeRectangle.LOW.second().x(), ShapeRectangle.LOW.second().y(),
		ShapeRectangle.LOW.third().x(), ShapeRectangle.LOW.third().y(),

		ShapeRectangle.HIGH.first().x(), ShapeRectangle.HIGH.first().y(),
		ShapeRectangle.HIGH.second().x(), ShapeRectangle.HIGH.second().y(),
		ShapeRectangle.HIGH.third().x(), ShapeRectangle.HIGH.third().y(),
	};

	public final static int[] indices = {
			0, 1, 3,
			3, 2, 0
	};
	/**
	 * @formatter:on
	 */

	// Oriented Rectangle
	public static Vector2f[] vertices(OrientedRectangle rectangleIn)
	{
		return ShapeRectangle.vertices(rectangleIn.getPosition().x(), rectangleIn.getPosition().y(),
				rectangleIn.getRotation().x(), rectangleIn.getRotation().y(), rectangleIn.getScale().x(),
				rectangleIn.getScale().y());
	}

	public static Vector2f[] vertices(float xIn, float yIn, float rxIn, float ryIn, float sxIn, float syIn)
	{
		return ShapeRectangle.vertices(Transformations2f.transformations(xIn, yIn, rxIn, ryIn, sxIn, syIn));
	}

	public static Vector2f[] vertices(Vector2f positionIn, Vector2f scaleIn, Vector2f rotationIn)
	{
		return ShapeRectangle.vertices(Transformations2f.transformations(positionIn.x(), positionIn.y(), rotationIn.x(),
				rotationIn.y(), scaleIn.x(), scaleIn.y()));

	}

	public static Vector2f[] vertices(Matrix4f transformationsIn)
	{
		final var	vec		= new Vector3f();

		final var	corners	= new Vector2f[8];
		var			i		= 0;
		transformationsIn.transformPosition(0, 0, 0, vec);
		corners[i] = new Vector2f(vec.x(), vec.y());
		i++;
		transformationsIn.transformPosition(1, 0, 0, vec);
		corners[i] = new Vector2f(vec.x(), vec.y());
		i++;

		transformationsIn.transformPosition(0, 1, 0, vec);
		corners[i] = new Vector2f(vec.x(), vec.y());
		i++;
		transformationsIn.transformPosition(1, 1, 0, vec);
		corners[i] = new Vector2f(vec.x(), vec.y());
		i++;
		return corners;
	}

	public static final Vector2f min(float xIn, float yIn, float scaleXIn, float scaleYIn, float rotationXIn,
			float rotationYIn)
	{
		return ShapeRectangle
				.min(Transformations2f.transformations(xIn, yIn, rotationXIn, rotationYIn, scaleXIn, scaleYIn));
	}

	public static final Vector2f max(float xIn, float yIn, float scaleXIn, float scaleYIn, float rotationXIn,
			float rotationYIn)
	{
		return ShapeRectangle
				.max(Transformations2f.transformations(xIn, yIn, rotationXIn, rotationYIn, scaleXIn, scaleYIn));
	}

	public static final AABB2f getBoundingBox(float xIn, float yIn, float scaleXIn, float scaleYIn, float rotationXIn,
			float rotationYIn)
	{
		return ShapeRectangle
				.extremum(Transformations2f.transformations(xIn, yIn, rotationXIn, rotationYIn, scaleXIn, scaleYIn));
	}

	public static final Vector2f min(Vector2f positionIn, Vector2f scaleIn, Vector2f rotationIn)
	{
		return ShapeRectangle.min(Transformations2f.transformations(positionIn.x(), positionIn.y(), rotationIn.x(),
				rotationIn.y(), scaleIn.x(), scaleIn.y()));
	}

	public static final Vector2f max(Vector2f positionIn, Vector2f scaleIn, Vector2f rotationIn)
	{
		return ShapeRectangle.max(Transformations2f.transformations(positionIn.x(), positionIn.y(), rotationIn.x(),
				rotationIn.y(), scaleIn.x(), scaleIn.y()));
	}

	public static final AABB2f getBoundingBox(Vector2f positionIn, Vector2f scaleIn, Vector2f rotationIn)
	{
		return ShapeRectangle.extremum(Transformations2f.transformations(positionIn.x(), positionIn.y(), rotationIn.x(),
				rotationIn.y(), scaleIn.x(), scaleIn.y()));
	}

	public static final Vector2f min(Matrix4f transformationsIn)
	{
		final var	min	= new Vector2f(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
		final var	vec	= new Vector3f();

		transformationsIn.transformPosition(0, 0, 0, vec);
		ShapeRectangle.min(vec, min);

		transformationsIn.transformPosition(1, 0, 0, vec);
		ShapeRectangle.min(vec, min);

		transformationsIn.transformPosition(0, 1, 0, vec);
		ShapeRectangle.min(vec, min);

		transformationsIn.transformPosition(1, 1, 0, vec);
		ShapeRectangle.min(vec, min);

		return min;
	}

	public static final Vector2f max(Matrix4f transformationsIn)
	{
		final var	min	= new Vector2f(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
		final var	vec	= new Vector3f();

		transformationsIn.transformPosition(0, 0, 0, vec);
		ShapeRectangle.min(vec, min);

		transformationsIn.transformPosition(1, 0, 0, vec);
		ShapeRectangle.min(vec, min);

		transformationsIn.transformPosition(0, 1, 0, vec);
		ShapeRectangle.min(vec, min);

		transformationsIn.transformPosition(1, 1, 0, vec);
		ShapeRectangle.min(vec, min);

		return min;
	}

	public static final AABB2f extremum(Matrix4f transformationsIn)
	{
		final var	min	= new Vector2f(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
		final var	max	= new Vector2f(-Float.POSITIVE_INFINITY, -Float.POSITIVE_INFINITY);
		final var	vec	= new Vector3f();

		transformationsIn.transformPosition(0, 0, 0, vec);
		ShapeRectangle.min(vec, min);
		ShapeRectangle.max(vec, max);

		transformationsIn.transformPosition(1, 0, 0, vec);
		ShapeRectangle.min(vec, min);
		ShapeRectangle.max(vec, max);

		transformationsIn.transformPosition(0, 1, 0, vec);
		ShapeRectangle.min(vec, min);
		ShapeRectangle.max(vec, max);

		transformationsIn.transformPosition(1, 1, 0, vec);
		ShapeRectangle.min(vec, min);
		ShapeRectangle.max(vec, max);

		return new AABB2f(min, max);
	}

	public static final Vector2f min(Vector2f currentIn, Vector2f minIn)
	{
		if (currentIn.x() < minIn.x())
		{
			minIn.x = currentIn.x();
		}
		if (currentIn.y() < minIn.y())
		{
			minIn.y = currentIn.y();
		}
		return minIn;
	}

	public static final Vector2f max(Vector2f currentIn, Vector2f maxIn)
	{
		if (currentIn.x() < maxIn.x())
		{
			maxIn.x = currentIn.x();
		}
		if (currentIn.y() < maxIn.y())
		{
			maxIn.y = currentIn.y();
		}
		return maxIn;
	}

	public static final Vector2f min(Vector3f currentIn, Vector2f minIn)
	{
		if (currentIn.x() < minIn.x())
		{
			minIn.x = currentIn.x();
		}
		if (currentIn.y() < minIn.y())
		{
			minIn.y = currentIn.y();
		}
		return minIn;
	}

	public static final Vector2f max(Vector3f currentIn, Vector2f maxIn)
	{
		if (currentIn.x() < maxIn.x())
		{
			maxIn.x = currentIn.x();
		}
		if (currentIn.y() < maxIn.y())
		{
			maxIn.y = currentIn.y();
		}
		return maxIn;
	}

	// Aligned Rectangle

	public static Vector2f[] vertices(Rectangle rectangleIn)
	{
		return ShapeRectangle.vertices(rectangleIn.getPosition().x, rectangleIn.getPosition().y,
				rectangleIn.getScale().x, rectangleIn.getScale().y);
	}

	public static Vector2f[] vertices(float x, float y, float sx, float sy)
	{
		final var	corners	= new Vector2f[4];
		var			i		= 0;
		corners[i] = new Vector2f(x, y);
		i++;
		corners[i] = new Vector2f(x + sx, y);
		i++;

		corners[i] = new Vector2f(x, y + sy);
		i++;
		corners[i] = new Vector2f(x + sx, y + sy);
		i++;
		return corners;
	}

	public static Vector2f[] vertices(Vector2f positionIn, Vector2f scaleIn)
	{
		final var	corners	= new Vector2f[4];
		var			i		= 0;
		corners[i] = new Vector2f(positionIn.x(), positionIn.y());
		i++;
		corners[i] = new Vector2f(positionIn.x() + scaleIn.x(), positionIn.y());
		i++;

		corners[i] = new Vector2f(positionIn.x(), positionIn.y() + scaleIn.y());
		i++;
		corners[i] = new Vector2f(positionIn.x() + scaleIn.x(), positionIn.y() + scaleIn.y());
		i++;
		return corners;
	}
}