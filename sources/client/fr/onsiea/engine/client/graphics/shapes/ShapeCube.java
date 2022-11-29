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

import org.joml.Vector3f;

import fr.onsiea.engine.utils.maths.transformations.Transformations3f;
import fr.onsiea.engine.utils.maths.triangle.Triangle3f;

/**
 * @author Seynax
 *
 */
public class ShapeCube
{
	/**
	* @formatter:off
	*/
	public final static Vector3f	V000				= new Vector3f(0.0f, 0.0f, 0.0f);
	public final static Vector3f	V100				= new Vector3f(1.0f, 0.0f, 0.0f);
	public final static Vector3f	V010				= new Vector3f(0.0f, 1.0f, 0.0f);
	public final static Vector3f	V110				= new Vector3f(1.0f, 1.0f, 0.0f);
	public final static Vector3f	V001				= new Vector3f(0.0f, 0.0f, 1.0f);
	public final static Vector3f	V101				= new Vector3f(1.0f, 0.0f, 1.0f);
	public final static Vector3f	V011				= new Vector3f(0.0f, 1.0f, 1.0f);
	public final static Vector3f	V111				= new Vector3f(1.0f, 1.0f, 1.0f);

	public final static float[]			CORNERS			=
	{
		ShapeCube.V000.x(), ShapeCube.V000.y(), ShapeCube.V000.z(),
		ShapeCube.V100.x(), ShapeCube.V100.y(), ShapeCube.V100.z(),
		ShapeCube.V110.x(), ShapeCube.V110.y(), ShapeCube.V110.z(),
		ShapeCube.V010.x(), ShapeCube.V010.y(), ShapeCube.V010.z(),
		ShapeCube.V001.x(), ShapeCube.V001.y(), ShapeCube.V001.z(),
		ShapeCube.V101.x(), ShapeCube.V101.y(), ShapeCube.V101.z(),
		ShapeCube.V011.x(), ShapeCube.V011.y(), ShapeCube.V011.z(),
		ShapeCube.V111.x(), ShapeCube.V111.y(), ShapeCube.V111.z()
	};

	public final static Triangle3f		FRONT_LOW			= new Triangle3f(ShapeCube.V000, ShapeCube.V100, ShapeCube.V110);
	public final static Triangle3f		FRONT_HIGH			= new Triangle3f(ShapeCube.V110, ShapeCube.V010, ShapeCube.V000);

	public final static Triangle3f		BACK_LOW			= new Triangle3f(ShapeCube.V001, ShapeCube.V101, ShapeCube.V111);
	public final static Triangle3f		BACK_HIGH			= new Triangle3f(ShapeCube.V111, ShapeCube.V011, ShapeCube.V001);

	public final static Triangle3f		RIGHT_LOW			= new Triangle3f(ShapeCube.V100, ShapeCube.V101, ShapeCube.V111);
	public final static Triangle3f		RIGHT_HIGH			= new Triangle3f(ShapeCube.V111, ShapeCube.V110, ShapeCube.V100);

	public final static Triangle3f		LEFT_LOW				= new Triangle3f(ShapeCube.V000, ShapeCube.V001, ShapeCube.V011);
	public final static Triangle3f		LEFT_HIGH				= new Triangle3f(ShapeCube.V011, ShapeCube.V010, ShapeCube.V000);

	public final static Triangle3f		BOTTOM_LOW		= new Triangle3f(ShapeCube.V000, ShapeCube.V100, ShapeCube.V101);
	public final static Triangle3f		BOTTOM_HIGH		= new Triangle3f(ShapeCube.V101, ShapeCube.V001, ShapeCube.V000);

	public final static Triangle3f		TOP_LOW				= new Triangle3f(ShapeCube.V010, ShapeCube.V110, ShapeCube.V111);
	public final static Triangle3f		TOP_HIGH				= new Triangle3f(ShapeCube.V111, ShapeCube.V011, ShapeCube.V010);

	public final static float[]		allPositions		=
	{
		ShapeCube.FRONT_LOW.a().x(),	ShapeCube.FRONT_LOW.a().y(), ShapeCube.FRONT_LOW.a().z(),
		ShapeCube.FRONT_LOW.b().x(),	ShapeCube.FRONT_LOW.b().y(), ShapeCube.FRONT_LOW.b().z(),
		ShapeCube.FRONT_LOW.c().x(),	ShapeCube.FRONT_LOW.c().y(), ShapeCube.FRONT_LOW.c().z(),

		ShapeCube.FRONT_HIGH.a().x(),	ShapeCube.FRONT_HIGH.a().y(), ShapeCube.FRONT_HIGH.a().z(),
		ShapeCube.FRONT_HIGH.b().x(),	ShapeCube.FRONT_HIGH.b().y(), ShapeCube.FRONT_HIGH.b().z(),
		ShapeCube.FRONT_HIGH.c().x(),	ShapeCube.FRONT_HIGH.c().y(), ShapeCube.FRONT_HIGH.c().z(),

		ShapeCube.LEFT_LOW.a().x(),	ShapeCube.LEFT_LOW.a().y(), ShapeCube.LEFT_LOW.a().z(),
		ShapeCube.LEFT_LOW.b().x(),	ShapeCube.LEFT_LOW.b().y(), ShapeCube.LEFT_LOW.b().z(),
		ShapeCube.LEFT_LOW.c().x(),	ShapeCube.LEFT_LOW.c().y(), ShapeCube.LEFT_LOW.c().z(),

		ShapeCube.LEFT_HIGH.a().x(),	ShapeCube.LEFT_HIGH.a().y(), ShapeCube.LEFT_HIGH.a().z(),
		ShapeCube.LEFT_HIGH.b().x(),	ShapeCube.LEFT_HIGH.b().y(), ShapeCube.LEFT_HIGH.b().z(),
		ShapeCube.LEFT_HIGH.c().x(),	ShapeCube.LEFT_HIGH.c().y(), ShapeCube.LEFT_HIGH.c().z(),

		ShapeCube.BOTTOM_LOW.a().x(),	ShapeCube.BOTTOM_LOW.a().y(), ShapeCube.BOTTOM_LOW.a().z(),
		ShapeCube.BOTTOM_LOW.b().x(),	ShapeCube.BOTTOM_LOW.b().y(), ShapeCube.BOTTOM_LOW.b().z(),
		ShapeCube.BOTTOM_LOW.c().x(),	ShapeCube.BOTTOM_LOW.c().y(), ShapeCube.BOTTOM_LOW.c().z(),

		ShapeCube.BOTTOM_HIGH.a().x(),	ShapeCube.BOTTOM_HIGH.a().y(), ShapeCube.BOTTOM_HIGH.a().z(),
		ShapeCube.BOTTOM_HIGH.b().x(),	ShapeCube.BOTTOM_HIGH.b().y(), ShapeCube.BOTTOM_HIGH.b().z(),
		ShapeCube.BOTTOM_HIGH.c().x(),	ShapeCube.BOTTOM_HIGH.c().y(), ShapeCube.BOTTOM_HIGH.c().z(),

		ShapeCube.BACK_LOW.a().x(),	ShapeCube.BACK_LOW.a().y(), ShapeCube.BACK_LOW.a().z(),
		ShapeCube.BACK_LOW.b().x(),	ShapeCube.BACK_LOW.b().y(), ShapeCube.BACK_LOW.b().z(),
		ShapeCube.BACK_LOW.c().x(),	ShapeCube.BACK_LOW.c().y(), ShapeCube.BACK_LOW.c().z(),

		ShapeCube.BACK_HIGH.a().x(),	ShapeCube.BACK_HIGH.a().y(), ShapeCube.BACK_HIGH.a().z(),
		ShapeCube.BACK_HIGH.b().x(),	ShapeCube.BACK_HIGH.b().y(), ShapeCube.BACK_HIGH.b().z(),
		ShapeCube.BACK_HIGH.c().x(),	ShapeCube.BACK_HIGH.c().y(), ShapeCube.BACK_HIGH.c().z(),

		ShapeCube.TOP_LOW.a().x(), ShapeCube.TOP_LOW.a().y(), ShapeCube.TOP_LOW.a().z(),
		ShapeCube.TOP_LOW.b().x(), ShapeCube.TOP_LOW.b().y(), ShapeCube.TOP_LOW.b().z(),
		ShapeCube.TOP_LOW.c().x(), ShapeCube.TOP_LOW.c().y(), ShapeCube.TOP_LOW.c().z(),

		ShapeCube.TOP_HIGH.a().x(), ShapeCube.TOP_HIGH.a().y(), ShapeCube.TOP_HIGH.a().z(),
		ShapeCube.TOP_HIGH.b().x(), ShapeCube.TOP_HIGH.b().y(), ShapeCube.TOP_HIGH.b().z(),
		ShapeCube.TOP_HIGH.c().x(), ShapeCube.TOP_HIGH.c().y(), ShapeCube.TOP_HIGH.c().z(),

		ShapeCube.RIGHT_HIGH.a().x(), ShapeCube.RIGHT_HIGH.a().y(), ShapeCube.RIGHT_HIGH.a().z(),
		ShapeCube.RIGHT_HIGH.b().x(), ShapeCube.RIGHT_HIGH.b().y(), ShapeCube.RIGHT_HIGH.b().z(),
		ShapeCube.RIGHT_HIGH.c().x(), ShapeCube.RIGHT_HIGH.c().y(), ShapeCube.RIGHT_HIGH.c().z(),

		ShapeCube.RIGHT_LOW.a().x(), ShapeCube.RIGHT_LOW.a().y(), ShapeCube.RIGHT_LOW.a().z(),
		ShapeCube.RIGHT_LOW.b().x(), ShapeCube.RIGHT_LOW.b().y(), ShapeCube.RIGHT_LOW.b().z(),
		ShapeCube.RIGHT_LOW.c().x(), ShapeCube.RIGHT_LOW.c().y(), ShapeCube.RIGHT_LOW.c().z(),
	};

	public final static float[]		textureCoordinates	=
	{
		0.0f, 0.0f,
		1.0f, 0.0f,
		1.0f, 1.0f,

		1.0f, 1.0f,
		0.0f, 1.0f,
		0.0f, 0.0f,

		0.0f, 0.0f,
		1.0f, 0.0f,
		1.0f, 1.0f,

		1.0f, 1.0f,
		0.0f, 1.0f,
		0.0f, 0.0f,

		0.0f, 0.0f,
		1.0f, 0.0f,
		1.0f, 1.0f,

		1.0f, 1.0f,
		0.0f, 1.0f,
		0.0f, 0.0f,

		0.0f, 0.0f,
		1.0f, 0.0f,
		1.0f, 1.0f,

		1.0f, 1.0f,
		0.0f, 1.0f,
		0.0f, 0.0f,

		0.0f, 0.0f,
		1.0f, 0.0f,
		1.0f, 1.0f,

		1.0f, 1.0f,
		0.0f, 1.0f,
		0.0f, 0.0f,

		0.0f, 0.0f,
		1.0f, 0.0f,
		1.0f, 1.0f,

		1.0f, 1.0f,
		0.0f, 1.0f,
		0.0f, 0.0f
	};

	public final static float[]			positionsAndTextureCoordinates			=
	{
		0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
		1.0f, 0.0f, 0.0f, 1.0f, 0.0f,
		1.0f, 1.0f, 0.0f, 1.0f, 1.0f,
		0.0f, 1.0f, 0.0f, 0.0f, 1.0f,
		0.0f, 0.0f, 1.0f, 0.0f, 0.0f,
		1.0f, 0.0f, 1.0f, 1.0f, 0.0f,
		1.0f, 1.0f, 1.0f, 1.0f, 1.0f,
		0.0f, 1.0f, 1.0f, 0.0f, 1.0f,
		1.0f, 0.0f, 0.0f, 0.0f, 0.0f,
		1.0f, 1.0f, 0.0f, 0.0f, 1.0f,
		0.0f, 0.0f, 1.0f, 1.0f, 0.0f,
		0.0f, 1.0f, 1.0f, 1.0f, 1.0f,
		0.0f, 1.0f, 0.0f, 0.0f, 0.0f,
		1.0f, 1.0f, 0.0f, 1.0f, 0.0f,
		1.0f, 0.0f, 1.0f, 1.0f, 1.0f,
		0.0f, 0.0f, 1.0f, 0.0f, 1.0f

		/**Cube.V000.x(), Cube.V000.y(), Cube.V000.z(), 0.0f, 0.0f,
			Cube.V100.x(), Cube.V100.y(), Cube.V100.z(), 1.0f, 0.0f,
			Cube.V010.x(), Cube.V010.y(), Cube.V010.z(), 0.0f, 1.0f,
			Cube.V110.x(), Cube.V110.y(), Cube.V110.z(), 1.0f, 1.0f,
			Cube.V001.x(), Cube.V001.y(), Cube.V001.z(), 0.0f, 0.1f,
			Cube.V101.x(), Cube.V101.y(), Cube.V101.z(), 1.0f, 1.0f,
			Cube.V011.x(), Cube.V011.y(), Cube.V011.z(), 1.0f, 1.0f,
			Cube.V111.x(), Cube.V111.y(), Cube.V111.z(), 1.0f, 1.0f,**/
	};

	public final static float[]			positionsAndTextureCoordinatesAndNormals			=
	{
			0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -1.0f,
			1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, -1.0f,
			1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, -1.0f,
			0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, -1.0f,
			0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f,
			1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f,
			1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f,
			0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,
			1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f,
			1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f,
			1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f,
			1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f,
			0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f,
			0.0f, 0.0f, 1.0f, 1.0f, 0.0f, -1.0f, 0.0f, 0.0f,
			0.0f, 1.0f, 1.0f, 1.0f, 1.0f, -1.0f, 0.0f, 0.0f,
			0.0f, 1.0f, 0.0f, 0.0f, -1.0f, 1.0f, 0.0f, 0.0f,
			0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f,
			1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f,
			1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f,
			0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f,
			0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f,
			1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, -1.0f, 0.0f,
			1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, -1.0f, 0.0f,
			0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, -1.0f, 0.0f
	};

	public final static float[]		allPositionsAndTextureCoordinates		=
	{
		ShapeCube.FRONT_LOW.a().x(), ShapeCube.FRONT_LOW.a().y(), ShapeCube.FRONT_LOW.a().z(), 0.0f, 0.0f,
		ShapeCube.FRONT_LOW.b().x(), ShapeCube.FRONT_LOW.b().y(), ShapeCube.FRONT_LOW.b().z(), 1.0f, 0.0f,
		ShapeCube.FRONT_LOW.c().x(), ShapeCube.FRONT_LOW.c().y(), ShapeCube.FRONT_LOW.c().z(), 1.0f, 1.0f,

		ShapeCube.FRONT_HIGH.a().x(), ShapeCube.FRONT_HIGH.a().y(), ShapeCube.FRONT_HIGH.a().z(), 1.0f, 1.0f,
		ShapeCube.FRONT_HIGH.b().x(), ShapeCube.FRONT_HIGH.b().y(), ShapeCube.FRONT_HIGH.b().z(), 0.0f, 1.0f,
		ShapeCube.FRONT_HIGH.c().x(), ShapeCube.FRONT_HIGH.c().y(), ShapeCube.FRONT_HIGH.c().z(), 0.0f, 0.0f,

		ShapeCube.BACK_LOW.a().x(), ShapeCube.BACK_LOW.a().y(), ShapeCube.BACK_LOW.a().z(), 0.0f, 0.0f,
		ShapeCube.BACK_LOW.b().x(), ShapeCube.BACK_LOW.b().y(), ShapeCube.BACK_LOW.b().z(),1.0f, 0.0f,
		ShapeCube.BACK_LOW.c().x(), ShapeCube.BACK_LOW.c().y(), ShapeCube.BACK_LOW.c().z(), 1.0f, 1.0f,

		ShapeCube.BACK_HIGH.a().x(), ShapeCube.BACK_HIGH.a().y(), ShapeCube.BACK_HIGH.a().z(), 1.0f, 1.0f,
		ShapeCube.BACK_HIGH.b().x(), ShapeCube.BACK_HIGH.b().y(), ShapeCube.BACK_HIGH.b().z(),0.0f, 1.0f,
		ShapeCube.BACK_HIGH.c().x(), ShapeCube.BACK_HIGH.c().y(), ShapeCube.BACK_HIGH.c().z(), 0.0f, 0.0f,

		ShapeCube.RIGHT_LOW.a().x(), ShapeCube.RIGHT_LOW.a().y(), ShapeCube.RIGHT_LOW.a().z(), 0.0f, 0.0f,
		ShapeCube.RIGHT_LOW.b().x(), ShapeCube.RIGHT_LOW.b().y(), ShapeCube.RIGHT_LOW.b().z(),1.0f, 0.0f,
		ShapeCube.RIGHT_LOW.c().x(), ShapeCube.RIGHT_LOW.c().y(), ShapeCube.RIGHT_LOW.c().z(), 1.0f, 1.0f,

		ShapeCube.RIGHT_HIGH.a().x(), ShapeCube.RIGHT_HIGH.a().y(), ShapeCube.RIGHT_HIGH.a().z(), 1.0f, 1.0f,
		ShapeCube.RIGHT_HIGH.b().x(), ShapeCube.RIGHT_HIGH.b().y(), ShapeCube.RIGHT_HIGH.b().z(),0.0f, 1.0f,
		ShapeCube.RIGHT_HIGH.c().x(), ShapeCube.RIGHT_HIGH.c().y(), ShapeCube.RIGHT_HIGH.c().z(), 0.0f, 0.0f,

		ShapeCube.LEFT_LOW.a().x(), ShapeCube.LEFT_LOW.a().y(), ShapeCube.LEFT_LOW.a().z(), 0.0f, 0.0f,
		ShapeCube.LEFT_LOW.b().x(), ShapeCube.LEFT_LOW.b().y(), ShapeCube.LEFT_LOW.b().z(),1.0f, 0.0f,
		ShapeCube.LEFT_LOW.c().x(), ShapeCube.LEFT_LOW.c().y(), ShapeCube.LEFT_LOW.c().z(), 1.0f, 1.0f,

		ShapeCube.LEFT_HIGH.a().x(), ShapeCube.LEFT_HIGH.a().y(), ShapeCube.LEFT_HIGH.a().z(), 1.0f, 1.0f,
		ShapeCube.LEFT_HIGH.b().x(), ShapeCube.LEFT_HIGH.b().y(), ShapeCube.LEFT_HIGH.b().z(),0.0f, 1.0f,
		ShapeCube.LEFT_HIGH.c().x(), ShapeCube.LEFT_HIGH.c().y(), ShapeCube.LEFT_HIGH.c().z(), 0.0f, 0.0f,

		ShapeCube.TOP_LOW.a().x(), ShapeCube.TOP_LOW.a().y(), ShapeCube.TOP_LOW.a().z(), 0.0f, 0.0f,
		ShapeCube.TOP_LOW.b().x(), ShapeCube.TOP_LOW.b().y(), ShapeCube.TOP_LOW.b().z(),1.0f, 0.0f,
		ShapeCube.TOP_LOW.c().x(), ShapeCube.TOP_LOW.c().y(), ShapeCube.TOP_LOW.c().z(), 1.0f, 1.0f,

		ShapeCube.TOP_HIGH.a().x(), ShapeCube.TOP_HIGH.a().y(), ShapeCube.TOP_HIGH.a().z(), 1.0f, 1.0f,
		ShapeCube.TOP_HIGH.b().x(), ShapeCube.TOP_HIGH.b().y(), ShapeCube.TOP_HIGH.b().z(),0.0f, 1.0f,
		ShapeCube.TOP_HIGH.c().x(), ShapeCube.TOP_HIGH.c().y(), ShapeCube.TOP_HIGH.c().z(), 0.0f, 0.0f,

		ShapeCube.BOTTOM_LOW.a().x(), ShapeCube.BOTTOM_LOW.a().y(), ShapeCube.BOTTOM_LOW.a().z(), 0.0f, 0.0f,
		ShapeCube.BOTTOM_LOW.b().x(), ShapeCube.BOTTOM_LOW.b().y(), ShapeCube.BOTTOM_LOW.b().z(),1.0f, 0.0f,
		ShapeCube.BOTTOM_LOW.c().x(), ShapeCube.BOTTOM_LOW.c().y(), ShapeCube.BOTTOM_LOW.c().z(), 1.0f, 1.0f,

		ShapeCube.BOTTOM_HIGH.a().x(), ShapeCube.BOTTOM_HIGH.a().y(), ShapeCube.BOTTOM_HIGH.a().z(), 1.0f, 1.0f,
		ShapeCube.BOTTOM_HIGH.b().x(), ShapeCube.BOTTOM_HIGH.b().y(), ShapeCube.BOTTOM_HIGH.b().z(),0.0f, 1.0f,
		ShapeCube.BOTTOM_HIGH.c().x(), ShapeCube.BOTTOM_HIGH.c().y(), ShapeCube.BOTTOM_HIGH.c().z(),  0.0f, 0.0f
	};

	public final static float[]		allPositionsAndTextureCoordinatesAndNormals		=
	{
		ShapeCube.FRONT_LOW.a().x(), ShapeCube.FRONT_LOW.a().y(), ShapeCube.FRONT_LOW.a().z(), 0.0f, 0.0f, 0.0f, 0.0f, -1.0f,
		ShapeCube.FRONT_LOW.b().x(), ShapeCube.FRONT_LOW.b().y(), ShapeCube.FRONT_LOW.b().z(), 1.0f, 0.0f, 0.0f, 0.0f, -1.0f,
		ShapeCube.FRONT_LOW.c().x(), ShapeCube.FRONT_LOW.c().y(), ShapeCube.FRONT_LOW.c().z(), 1.0f, 1.0f, 0.0f, 0.0f, -1.0f,

		ShapeCube.FRONT_HIGH.a().x(), ShapeCube.FRONT_HIGH.a().y(), ShapeCube.FRONT_HIGH.a().z(), 1.0f, 1.0f, 0.0f, 0.0f, -1.0f,
		ShapeCube.FRONT_HIGH.b().x(), ShapeCube.FRONT_HIGH.b().y(), ShapeCube.FRONT_HIGH.b().z(), 0.0f, 1.0f, 0.0f, 0.0f, -1.0f,
		ShapeCube.FRONT_HIGH.c().x(), ShapeCube.FRONT_HIGH.c().y(), ShapeCube.FRONT_HIGH.c().z(), 0.0f, 0.0f, 0.0f, 0.0f, -1.0f,

		ShapeCube.BACK_LOW.a().x(), ShapeCube.BACK_LOW.a().y(), ShapeCube.BACK_LOW.a().z(), 0.0f, 0.0f, 0.0f, 0.0f, 1.0f,
		ShapeCube.BACK_LOW.b().x(), ShapeCube.BACK_LOW.b().y(), ShapeCube.BACK_LOW.b().z(),1.0f, 0.0f, 0.0f, 0.0f, 1.0f,
		ShapeCube.BACK_LOW.c().x(), ShapeCube.BACK_LOW.c().y(), ShapeCube.BACK_LOW.c().z(), 1.0f, 1.0f, 0.0f, 0.0f, 1.0f,

		ShapeCube.BACK_HIGH.a().x(), ShapeCube.BACK_HIGH.a().y(), ShapeCube.BACK_HIGH.a().z(), 1.0f, 1.0f, 0.0f, 0.0f, 1.0f,
		ShapeCube.BACK_HIGH.b().x(), ShapeCube.BACK_HIGH.b().y(), ShapeCube.BACK_HIGH.b().z(),0.0f, 1.0f, 0.0f, 0.0f, 1.0f,
		ShapeCube.BACK_HIGH.c().x(), ShapeCube.BACK_HIGH.c().y(), ShapeCube.BACK_HIGH.c().z(), 0.0f, 0.0f, 0.0f, 0.0f, 1.0f,

		ShapeCube.RIGHT_LOW.a().x(), ShapeCube.RIGHT_LOW.a().y(), ShapeCube.RIGHT_LOW.a().z(), 0.0f, 0.0f, 1.0f, 0.0f, 0.0f,
		ShapeCube.RIGHT_LOW.b().x(), ShapeCube.RIGHT_LOW.b().y(), ShapeCube.RIGHT_LOW.b().z(),1.0f, 0.0f, 1.0f, 0.0f, 0.0f,
		ShapeCube.RIGHT_LOW.c().x(), ShapeCube.RIGHT_LOW.c().y(), ShapeCube.RIGHT_LOW.c().z(), 1.0f, 1.0f, 1.0f, 0.0f, 0.0f,

		ShapeCube.RIGHT_HIGH.a().x(), ShapeCube.RIGHT_HIGH.a().y(), ShapeCube.RIGHT_HIGH.a().z(), 1.0f, 1.0f, 1.0f, 0.0f, 0.0f,
		ShapeCube.RIGHT_HIGH.b().x(), ShapeCube.RIGHT_HIGH.b().y(), ShapeCube.RIGHT_HIGH.b().z(),0.0f, 1.0f, 1.0f, 0.0f, 0.0f,
		ShapeCube.RIGHT_HIGH.c().x(), ShapeCube.RIGHT_HIGH.c().y(), ShapeCube.RIGHT_HIGH.c().z(), 0.0f, 0.0f, 1.0f, 0.0f, 0.0f,

		ShapeCube.LEFT_LOW.a().x(), ShapeCube.LEFT_LOW.a().y(), ShapeCube.LEFT_LOW.a().z(), 0.0f, 0.0f, -1.0f, 0.0f, 0.0f,
		ShapeCube.LEFT_LOW.b().x(), ShapeCube.LEFT_LOW.b().y(), ShapeCube.LEFT_LOW.b().z(),1.0f, 0.0f, -1.0f, 0.0f, 0.0f,
		ShapeCube.LEFT_LOW.c().x(), ShapeCube.LEFT_LOW.c().y(), ShapeCube.LEFT_LOW.c().z(), 1.0f, 1.0f, -1.0f, 0.0f, 0.0f,

		ShapeCube.LEFT_HIGH.a().x(), ShapeCube.LEFT_HIGH.a().y(), ShapeCube.LEFT_HIGH.a().z(), 1.0f, 1.0f, -1.0f, 0.0f, 0.0f,
		ShapeCube.LEFT_HIGH.b().x(), ShapeCube.LEFT_HIGH.b().y(), ShapeCube.LEFT_HIGH.b().z(),0.0f, 1.0f, -1.0f, 0.0f, 0.0f,
		ShapeCube.LEFT_HIGH.c().x(), ShapeCube.LEFT_HIGH.c().y(), ShapeCube.LEFT_HIGH.c().z(), 0.0f, 0.0f, -1.0f, 0.0f, 0.0f,

		ShapeCube.TOP_LOW.a().x(), ShapeCube.TOP_LOW.a().y(), ShapeCube.TOP_LOW.a().z(), 0.0f, 0.0f, 0.0f, 1.0f, 0.0f,
		ShapeCube.TOP_LOW.b().x(), ShapeCube.TOP_LOW.b().y(), ShapeCube.TOP_LOW.b().z(),1.0f, 0.0f, 0.0f, 1.0f, 0.0f,
		ShapeCube.TOP_LOW.c().x(), ShapeCube.TOP_LOW.c().y(), ShapeCube.TOP_LOW.c().z(), 1.0f, 1.0f, 0.0f, 1.0f, 0.0f,

		ShapeCube.TOP_HIGH.a().x(), ShapeCube.TOP_HIGH.a().y(), ShapeCube.TOP_HIGH.a().z(), 1.0f, 1.0f, 0.0f, 1.0f, 0.0f,
		ShapeCube.TOP_HIGH.b().x(), ShapeCube.TOP_HIGH.b().y(), ShapeCube.TOP_HIGH.b().z(),0.0f, 1.0f, 0.0f, 1.0f, 0.0f,
		ShapeCube.TOP_HIGH.c().x(), ShapeCube.TOP_HIGH.c().y(), ShapeCube.TOP_HIGH.c().z(), 0.0f, 0.0f, 0.0f, 1.0f, 0.0f,

		ShapeCube.BOTTOM_LOW.a().x(), ShapeCube.BOTTOM_LOW.a().y(), ShapeCube.BOTTOM_LOW.a().z(), 0.0f, 0.0f, 0.0f, -1.0f, 0.0f,
		ShapeCube.BOTTOM_LOW.b().x(), ShapeCube.BOTTOM_LOW.b().y(), ShapeCube.BOTTOM_LOW.b().z(),1.0f, 0.0f, 0.0f, -1.0f, 0.0f,
		ShapeCube.BOTTOM_LOW.c().x(), ShapeCube.BOTTOM_LOW.c().y(), ShapeCube.BOTTOM_LOW.c().z(), 1.0f, 1.0f, 0.0f, -1.0f, 0.0f,

		ShapeCube.BOTTOM_HIGH.a().x(), ShapeCube.BOTTOM_HIGH.a().y(), ShapeCube.BOTTOM_HIGH.a().z(), 1.0f, 1.0f, 0.0f, -1.0f, 0.0f,
		ShapeCube.BOTTOM_HIGH.b().x(), ShapeCube.BOTTOM_HIGH.b().y(), ShapeCube.BOTTOM_HIGH.b().z(),0.0f, 1.0f, 0.0f, -1.0f, 0.0f,
		ShapeCube.BOTTOM_HIGH.c().x(), ShapeCube.BOTTOM_HIGH.c().y(), ShapeCube.BOTTOM_HIGH.c().z(),  0.0f, 0.0f, 0.0f, -1.0f, 0.0f
	};

	public final static float[] positionsForIndices =  {
			0.0f, 0.0f, 0.0f,
			1.0f, 0.0f, 0.0f,
			1.0f, 1.0f, 0.0f,
			0.0f, 1.0f, 0.0f,
			0.0f, 0.0f, 1.0f,
			1.0f, 0.0f, 1.0f,
			1.0f, 1.0f, 1.0f,
			0.0f, 1.0f, 1.0f
		};

	public final static int[] INDICES = {
			0, 1, 2,
			2, 3, 0,
			4, 5, 6,
			6, 7, 4,
			1, 5, 6,
			6, 2, 1,
			0, 4, 7,
			7, 3, 0,
			3, 2, 6,
			6, 7, 3,
			0, 1, 5,
			5, 4, 0
	};

	public final static int[] interleaveIndicesPositionsAndTextures = {
			0, 1, 2,
			2, 3, 0,

			4, 5, 6,
			6, 7, 4,

			8, 5, 6,
			6, 9, 8,

			0, 10, 11,
			11, 3, 0,

			12, 13, 6,
			6, 7, 12,

			0, 1, 14,
			14, 15, 0,
	};

	public final static int[] interleaveIndicesPositionsAndTexturesAndNormals = {
			0, 1, 2,
			2, 3, 0,
			4, 5, 6,
			6, 7, 4,
			8, 9, 10,
			10, 11, 8,
			12, 13, 14,
			14, 15, 12,
			16, 17, 18,
			18, 19, 16,
			20, 21, 22,
			22, 23, 20
	};

	/**
	* @formatter:on
	*/

	public float[] vertices(int xIn, int yIn, int zIn)
	{
		/**
		 * @formatter:off
		 */
		/**  v6 _____ v5
		*	   	   /		   /|
		*	v1 /_____/v0
		*		|	 |v7	|	 |v4
		* 		|			|	/
		*		|/	____|/
		*		v2         v3
		* Font : Calibri, 18
		*/

		return new float[]
		{
					-1.0f + xIn, 	-1.0f + yIn, 	-1.0f + zIn, // Triangle 1 ; begin
					-1.0f + xIn, 	-1.0f + yIn, 	 1.0f + zIn,
					-1.0f + xIn, 	 1.0f + yIn, 	 1.0f + zIn, // Triangle 1 ; end
					 1.0f + xIn, 	 1.0f + yIn, 	-1.0f + zIn, // Triangle 2 ; begin
					-1.0f + xIn, 	-1.0f + yIn, 	-1.0f + zIn,
					-1.0f + xIn, 	 1.0f + yIn, 	-1.0f + zIn, // Triangle 2 ; end
					 1.0f + xIn, 	-1.0f + yIn, 	 1.0f + zIn, // Triangle 3 ; begin
					-1.0f + xIn, 	-1.0f + yIn, 	-1.0f + zIn,
					 1.0f + xIn, 	-1.0f + yIn, 	-1.0f + zIn, // Triangle 3 ; end
					 1.0f + xIn, 	 1.0f + yIn, 	-1.0f + zIn, // Triangle 4 ; begin
					 1.0f + xIn, 	-1.0f + yIn, 	-1.0f + zIn,
					-1.0f + xIn, 	-1.0f + yIn, 	-1.0f + zIn, // Triangle 4 ; end
					-1.0f + xIn, 	-1.0f + yIn, 	-1.0f + zIn, // Triangle 5 ; begin
					-1.0f + xIn, 	 1.0f + yIn, 	 1.0f + zIn,
					-1.0f + xIn, 	 1.0f + yIn, 	-1.0f + zIn, // Triangle 5 ; end
					 1.0f + xIn, 	-1.0f + yIn, 	 1.0f + zIn, // Triangle 6 ; begin
					-1.0f + xIn, 	-1.0f + yIn, 	 1.0f + zIn,
					-1.0f + xIn, 	-1.0f + yIn, 	-1.0f + zIn, // Triangle 6 ; end
					-1.0f + xIn, 	 1.0f + yIn, 	 1.0f + zIn, // Triangle 7 ; begin
					-1.0f + xIn, 	-1.0f + yIn, 	 1.0f + zIn,
					 1.0f + xIn, 	-1.0f + yIn, 	 1.0f + zIn, // Triangle 7 ; end
					 1.0f + xIn, 	 1.0f + yIn, 	 1.0f + zIn, // Triangle 8 ; begin
					 1.0f + xIn, 	-1.0f + yIn, 	-1.0f + zIn,
					 1.0f + xIn, 	 1.0f + yIn, 	-1.0f + zIn, // Triangle 8 ; end
					 1.0f + xIn, 	-1.0f + yIn, 	-1.0f + zIn, // Triangle 9 ; begin
					 1.0f + xIn, 	 1.0f + yIn, 	 1.0f + zIn,
					 1.0f + xIn, 	-1.0f + yIn, 	 1.0f + zIn, // Triangle 9 ; end
					 1.0f + xIn, 	 1.0f + yIn, 	 1.0f + zIn, // Triangle 10 ; begin
					 1.0f + xIn, 	 1.0f + yIn, 	-1.0f + zIn,
					-1.0f + xIn, 	 1.0f + yIn, 	-1.0f + zIn, // Triangle 10 ; end
					 1.0f + xIn, 	 1.0f + yIn, 	 1.0f + zIn, // Triangle 11 ; begin
					-1.0f + xIn, 	 1.0f + yIn, 	-1.0f + zIn,
					-1.0f + xIn, 	 1.0f + yIn, 	 1.0f + zIn, // Triangle 11 ; end
					 1.0f + xIn, 	 1.0f + yIn, 	 1.0f + zIn, // Triangle 12 ; begin
					-1.0f + xIn, 	 1.0f + yIn, 	 1.0f + zIn,
					 1.0f + xIn, 	-1.0f + yIn, 	 1.0f + zIn // Triangle 12 ; end
			};
		/**
		 * @formatter:on
		 */
	}

	public static Vector3f[] vertices(float x, float y, float z, float ax, float ay, float az, float sx, float sy,
			float sz)
	{
		final var	m		= Transformations3f.transformations(x, y, z, ax, ay, az, sx, sy, sz);

		final var	vec		= new Vector3f();

		final var	corners	= new Vector3f[8];
		var			i		= 0;
		corners[i]	= m.transformPosition(0, 0, 0, vec);
		corners[i]	= new Vector3f(vec);
		i++;
		i++;
		corners[i]	= m.transformPosition(0, 0, 1, vec);
		corners[i]	= new Vector3f(vec);
		i++;
		i++;
		corners[i]	= m.transformPosition(1, 0, 1, vec);
		corners[i]	= new Vector3f(vec);
		i++;
		i++;
		m.transformPosition(1, 0, 0, vec);
		corners[i] = new Vector3f(vec);
		i++;

		m.transformPosition(0, 1, 0, vec);
		corners[i] = new Vector3f(vec);
		i++;
		m.transformPosition(0, 1, 1, vec);
		corners[i] = new Vector3f(vec);
		i++;
		m.transformPosition(1, 1, 1, vec);
		corners[i] = new Vector3f(vec);
		i++;
		m.transformPosition(1, 1, 0, vec);
		corners[i] = new Vector3f(vec);
		i++;
		return corners;
	}

	/**
	 * @param fIn
	 * @return
	 */
	public static float[] withSize(float sizeIn)
	{
		final var positions = new float[ShapeCube.positionsForIndices.length];
		for (var i = 0; i < ShapeCube.positionsForIndices.length; i++)
		{
			var v = ShapeCube.positionsForIndices[i];

			if (v == 0.0f)
			{
				v = -sizeIn;
			}
			else
			{
				v = sizeIn;
			}

			positions[i] = v;
		}

		return positions;
	}
}