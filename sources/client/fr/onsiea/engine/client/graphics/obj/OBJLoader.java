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
package fr.onsiea.engine.client.graphics.obj;

/**
 * @author Seynax
 *
 */
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

import fr.onsiea.engine.client.graphics.opengl.GLMeshManager;
import fr.onsiea.engine.client.graphics.opengl.mesh.GLMesh;
import fr.onsiea.engine.utils.file.FileUtils;

public class OBJLoader
{
	private GLMeshManager meshManager;

	public OBJLoader(GLMeshManager meshManagerIn)
	{
		this.meshManager(meshManagerIn);
	}

	public GLMesh loadMesh(String fileName) throws Exception
	{
		final var				lines		= FileUtils.loadLines(fileName);

		final List<Vector3f>	vertices	= new ArrayList<>();
		final List<Vector2f>	textures	= new ArrayList<>();
		final List<Vector3f>	normals		= new ArrayList<>();
		final List<Face>		faces		= new ArrayList<>();

		for (final String line : lines)
		{
			final var tokens = line.split("\\s+");
			switch (tokens[0])
			{
				case "v":
					// Geometric vertex
					final var vec3f = new Vector3f(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]),
							Float.parseFloat(tokens[3]));
					vertices.add(vec3f);
					break;

				case "vt":
					// Texture coordinate
					final var vec2f = new Vector2f(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]));
					textures.add(vec2f);
					break;

				case "vn":
					// Vertex normal
					final var vec3fNorm = new Vector3f(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]),
							Float.parseFloat(tokens[3]));
					normals.add(vec3fNorm);
					break;

				case "f":
					final var face = new Face(tokens[1], tokens[2], tokens[3]);
					faces.add(face);
					break;

				default:
					// Ignore other lines
					break;
			}
		}

		return this.reorderLists(vertices, textures, normals, faces);
	}

	private GLMesh reorderLists(List<Vector3f> posList, List<Vector2f> textCoordList, List<Vector3f> normList,
			List<Face> facesList) throws Exception
	{

		final List<Integer>	indices	= new ArrayList<>();
		// Create position array in the order it has been declared
		final var			posArr	= new float[posList.size() * 3];
		var					i		= 0;
		for (final Vector3f pos : posList)
		{
			posArr[i * 3]		= pos.x;
			posArr[i * 3 + 1]	= pos.y;
			posArr[i * 3 + 2]	= pos.z;
			i++;
		}
		final var	textCoordArr	= new float[posList.size() * 2];
		final var	normArr			= new float[posList.size() * 3];

		for (final Face face : facesList)
		{
			final var faceVertexIndices = face.getFaceVertexIndices();
			for (final IdxGroup indValue : faceVertexIndices)
			{
				OBJLoader.processFaceVertex(indValue, textCoordList, normList, indices, textCoordArr, normArr);
			}
		}
		var indicesArr = new int[indices.size()];
		indicesArr = indices.stream().mapToInt((Integer v) -> v).toArray();

		return this.meshManager().meshBuilderWithVao(3).vbo(posArr, 3).vbo(textCoordArr, 2).vbo(normArr, 3)
				.ibo(indicesArr).unbindVao().build();
	}

	private static void processFaceVertex(IdxGroup indices, List<Vector2f> textCoordList, List<Vector3f> normList,
			List<Integer> indicesList, float[] texCoordArr, float[] normArr)
	{

		// Set index for vertex coordinates
		final var posIndex = indices.idxPos;
		indicesList.add(posIndex);

		// Reorder texture coordinates
		if (indices.idxTextCoord >= 0)
		{
			final var textCoord = textCoordList.get(indices.idxTextCoord);
			texCoordArr[posIndex * 2]		= textCoord.x;
			texCoordArr[posIndex * 2 + 1]	= 1 - textCoord.y;
		}
		if (indices.idxVecNormal >= 0)
		{
			// Reorder vectornormals
			final var vecNorm = normList.get(indices.idxVecNormal);
			normArr[posIndex * 3]		= vecNorm.x;
			normArr[posIndex * 3 + 1]	= vecNorm.y;
			normArr[posIndex * 3 + 2]	= vecNorm.z;
		}
	}

	private final GLMeshManager meshManager()
	{
		return this.meshManager;
	}

	private final void meshManager(GLMeshManager meshManagerIn)
	{
		this.meshManager = meshManagerIn;
	}

	protected static class Face
	{
		/**
		 * List of idxGroup groups for a face triangle (3 vertices per face).
		 */
		private IdxGroup[] idxGroups = new IdxGroup[3];

		public Face(String v1, String v2, String v3)
		{
			this.idxGroups		= new IdxGroup[3];
			// Parse the lines
			this.idxGroups[0]	= this.parseLine(v1);
			this.idxGroups[1]	= this.parseLine(v2);
			this.idxGroups[2]	= this.parseLine(v3);
		}

		private IdxGroup parseLine(String line)
		{
			final var	idxGroup	= new IdxGroup();

			final var	lineTokens	= line.split("/");
			final var	length		= lineTokens.length;
			idxGroup.idxPos = Integer.parseInt(lineTokens[0]) - 1;
			if (length > 1)
			{
				// It can be empty if the obj does not define text coords
				final var textCoord = lineTokens[1];
				idxGroup.idxTextCoord = textCoord.length() > 0 ? Integer.parseInt(textCoord) - 1 : IdxGroup.NO_VALUE;
				if (length > 2)
				{
					idxGroup.idxVecNormal = Integer.parseInt(lineTokens[2]) - 1;
				}
			}

			return idxGroup;
		}

		public IdxGroup[] getFaceVertexIndices()
		{
			return this.idxGroups;
		}
	}

	protected static class IdxGroup
	{

		public static final int	NO_VALUE	= -1;

		public int				idxPos;

		public int				idxTextCoord;

		public int				idxVecNormal;

		public IdxGroup()
		{
			this.idxPos			= IdxGroup.NO_VALUE;
			this.idxTextCoord	= IdxGroup.NO_VALUE;
			this.idxVecNormal	= IdxGroup.NO_VALUE;
		}
	}
}