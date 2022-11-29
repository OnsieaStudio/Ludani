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
package fr.onsiea.engine.client.graphics.mesh.obj.normalMapped;

/**
 * @author ThinMatrix (https://www.youtube.com/user/thinmatrix, https://github.com/TheThinMatrix)
 */
public class ModelDataNM
{

	private final float[]	vertices;
	private final float[]	textureCoords;
	private final float[]	normals;
	private final float[]	tangents;
	private final int[]		indices;
	private final float		furthestPoint;

	public ModelDataNM(float[] vertices, float[] textureCoords, float[] normals, float[] tangents, int[] indices,
			float furthestPoint)
	{
		this.vertices		= vertices;
		this.textureCoords	= textureCoords;
		this.normals		= normals;
		this.indices		= indices;
		this.furthestPoint	= furthestPoint;
		this.tangents		= tangents;
	}

	public float[] getVertices()
	{
		return this.vertices;
	}

	public float[] getTextureCoords()
	{
		return this.textureCoords;
	}

	public float[] getTangents()
	{
		return this.tangents;
	}

	public float[] getNormals()
	{
		return this.normals;
	}

	public int[] getIndices()
	{
		return this.indices;
	}

	public float getFurthestPoint()
	{
		return this.furthestPoint;
	}

}
