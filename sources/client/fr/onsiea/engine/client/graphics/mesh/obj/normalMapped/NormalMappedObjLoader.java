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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

import fr.onsiea.engine.client.graphics.mesh.IMesh;
import fr.onsiea.engine.client.graphics.mesh.IMeshsManager;
import fr.onsiea.engine.client.graphics.mesh.obj.IOBJLoader;
import fr.onsiea.engine.client.graphics.mesh.obj.MeshData;

/**
 * @author ThinMatrix (https://www.youtube.com/user/thinmatrix, https://github.com/TheThinMatrix)
 * @author Seynax (minor changes)
 */
public class NormalMappedObjLoader implements IOBJLoader
{
	private IMeshsManager meshManager;

	public NormalMappedObjLoader()
	{

	}

	@Override
	public NormalMappedObjLoader link(final IMeshsManager meshManagerIn)
	{
		this.meshManager = meshManagerIn;

		return this;
	}

	public MeshData loadData(final String fileName) throws Exception
	{
		FileReader	isr		= null;
		final var	objFile	= new File(fileName);
		try
		{
			isr = new FileReader(objFile);
		}
		catch (final FileNotFoundException e)
		{
			System.err.println("File not found in res; don't use any extension");
		}
		final var				reader		= new BufferedReader(isr);
		String					line;
		final List<VertexNM>	vertices	= new ArrayList<>();
		final List<Vector2f>	textures	= new ArrayList<>();
		final List<Vector3f>	normals		= new ArrayList<>();
		final List<Integer>		indices		= new ArrayList<>();
		try
		{
			while (true)
			{
				line = reader.readLine();
				if (line.startsWith("v "))
				{
					final var	currentLine	= line.split("\\s+");
					final var	vertex		= new Vector3f(Float.parseFloat(currentLine[1]),
							Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					final var	newVertex	= new VertexNM(vertices.size(), vertex);
					vertices.add(newVertex);

				}
				else if (line.startsWith("vt "))
				{
					final var	currentLine	= line.split("\\s+");
					final var	texture		= new Vector2f(Float.parseFloat(currentLine[1]),
							Float.parseFloat(currentLine[2]));
					textures.add(texture);
				}
				else if (line.startsWith("vn "))
				{
					final var	currentLine	= line.split("\\s+");
					final var	normal		= new Vector3f(Float.parseFloat(currentLine[1]),
							Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					normals.add(normal);
				}
				else if (line.startsWith("f "))
				{
					break;
				}
			}
			while (line != null && line.startsWith("f "))
			{
				final var	currentLine	= line.split("\\s+");
				final var	vertex1		= currentLine[1].split("/");
				final var	vertex2		= currentLine[2].split("/");
				final var	vertex3		= currentLine[3].split("/");

				final var	v0			= NormalMappedObjLoader.processVertex(vertex1, vertices, indices);
				final var	v1			= NormalMappedObjLoader.processVertex(vertex2, vertices, indices);
				final var	v2			= NormalMappedObjLoader.processVertex(vertex3, vertices, indices);
				NormalMappedObjLoader.calculateTangents(v0, v1, v2, textures);
				line = reader.readLine();
			}
			reader.close();
		}
		catch (final IOException e)
		{
			System.err.println("Error reading the file");
		}
		NormalMappedObjLoader.removeUnusedVertices(vertices);
		final var	verticesArray	= new float[vertices.size() * 3];
		final var	texturesArray	= new float[vertices.size() * 2];
		final var	normalsArray	= new float[vertices.size() * 3];
		final var	tangentsArray	= new float[vertices.size() * 3];
		/**final var	furthest		=**/
		NormalMappedObjLoader.convertDataToArrays(vertices, textures, normals, verticesArray, texturesArray,
				normalsArray, tangentsArray);
		final var indicesArray = NormalMappedObjLoader.convertIndicesListToArray(indices);

		return new MeshData(verticesArray, texturesArray, normalsArray, tangentsArray, indicesArray);
	}

	@Override
	public IMesh load(final String objFileName) throws Exception
	{
		FileReader	isr		= null;
		final var	objFile	= new File(objFileName);
		try
		{
			isr = new FileReader(objFile);
		}
		catch (final FileNotFoundException e)
		{
			System.err.println("File not found in res; don't use any extension");
		}
		final var				reader		= new BufferedReader(isr);
		String					line;
		final List<VertexNM>	vertices	= new ArrayList<>();
		final List<Vector2f>	textures	= new ArrayList<>();
		final List<Vector3f>	normals		= new ArrayList<>();
		final List<Integer>		indices		= new ArrayList<>();
		try
		{
			while (true)
			{
				line = reader.readLine();
				if (line.startsWith("v "))
				{
					final var	currentLine	= line.split("\\s+");
					final var	vertex		= new Vector3f(Float.parseFloat(currentLine[1]),
							Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					final var	newVertex	= new VertexNM(vertices.size(), vertex);
					vertices.add(newVertex);

				}
				else if (line.startsWith("vt "))
				{
					final var	currentLine	= line.split("\\s+");
					final var	texture		= new Vector2f(Float.parseFloat(currentLine[1]),
							Float.parseFloat(currentLine[2]));
					textures.add(texture);
				}
				else if (line.startsWith("vn "))
				{
					final var	currentLine	= line.split("\\s+");
					final var	normal		= new Vector3f(Float.parseFloat(currentLine[1]),
							Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					normals.add(normal);
				}
				else if (line.startsWith("f "))
				{
					break;
				}
			}
			while (line != null && line.startsWith("f "))
			{
				final var	currentLine	= line.split("\\s+");
				final var	vertex1		= currentLine[1].split("/");
				final var	vertex2		= currentLine[2].split("/");
				final var	vertex3		= currentLine[3].split("/");

				final var	v0			= NormalMappedObjLoader.processVertex(vertex1, vertices, indices);
				final var	v1			= NormalMappedObjLoader.processVertex(vertex2, vertices, indices);
				final var	v2			= NormalMappedObjLoader.processVertex(vertex3, vertices, indices);
				NormalMappedObjLoader.calculateTangents(v0, v1, v2, textures);
				line = reader.readLine();
			}
			reader.close();
		}
		catch (final IOException e)
		{
			System.err.println("Error reading the file");
		}
		NormalMappedObjLoader.removeUnusedVertices(vertices);
		final var	verticesArray	= new float[vertices.size() * 3];
		final var	texturesArray	= new float[vertices.size() * 2];
		final var	normalsArray	= new float[vertices.size() * 3];
		final var	tangentsArray	= new float[vertices.size() * 3];
		/**final var	furthest		=**/
		NormalMappedObjLoader.convertDataToArrays(vertices, textures, normals, verticesArray, texturesArray,
				normalsArray, tangentsArray);
		final var indicesArray = NormalMappedObjLoader.convertIndicesListToArray(indices);

		return this.meshManager.create(verticesArray, texturesArray, normalsArray, tangentsArray, indicesArray, 3);
	}

	private static void calculateTangents(final VertexNM v0, final VertexNM v1, final VertexNM v2,
			final List<Vector2f> textures)
	{
		final var	delatPos1	= new Vector3f(v1.getPosition()).sub(v0.getPosition());
		final var	delatPos2	= new Vector3f(v2.getPosition()).sub(v0.getPosition());
		final var	uv0			= textures.get(v0.getTextureIndex());
		final var	uv1			= textures.get(v1.getTextureIndex());
		final var	uv2			= textures.get(v2.getTextureIndex());
		final var	deltaUv1	= new Vector2f(uv1).sub(uv0);
		final var	deltaUv2	= new Vector2f(uv2).sub(uv0);

		final var	r			= 1.0f / (deltaUv1.x * deltaUv2.y - deltaUv1.y * deltaUv2.x);
		delatPos1.mul(deltaUv2.y);
		delatPos2.mul(deltaUv1.y);
		final var tangent = new Vector3f(delatPos1).sub(delatPos2);
		tangent.mul(r);
		v0.addTangent(tangent);
		v1.addTangent(tangent);
		v2.addTangent(tangent);
	}

	private static VertexNM processVertex(final String[] vertex, final List<VertexNM> vertices,
			final List<Integer> indices)
	{
		final var	index			= Integer.parseInt(vertex[0]) - 1;
		final var	currentVertex	= vertices.get(index);
		final var	textureIndex	= Integer.parseInt(vertex[1]) - 1;
		final var	normalIndex		= Integer.parseInt(vertex[2]) - 1;

		if (!currentVertex.isSet())
		{
			currentVertex.setTextureIndex(textureIndex);
			currentVertex.setNormalIndex(normalIndex);
			indices.add(index);
			return currentVertex;
		}
		return NormalMappedObjLoader.dealWithAlreadyProcessedVertex(currentVertex, textureIndex, normalIndex, indices,
				vertices);
	}

	private static int[] convertIndicesListToArray(final List<Integer> indices)
	{
		final var indicesArray = new int[indices.size()];
		for (var i = 0; i < indicesArray.length; i++)
		{
			indicesArray[i] = indices.get(i);
		}
		return indicesArray;
	}

	private static float convertDataToArrays(final List<VertexNM> vertices, final List<Vector2f> textures,
			final List<Vector3f> normals, final float[] verticesArray, final float[] texturesArray,
			final float[] normalsArray, final float[] tangentsArray)
	{
		var furthestPoint = 0F;
		for (var i = 0; i < vertices.size(); i++)
		{
			final var currentVertex = vertices.get(i);
			if (currentVertex.getLength() > furthestPoint)
			{
				furthestPoint = currentVertex.getLength();
			}
			final var	position		= currentVertex.getPosition();
			final var	textureCoord	= textures.get(currentVertex.getTextureIndex());
			final var	normalVector	= normals.get(currentVertex.getNormalIndex());
			final var	tangent			= currentVertex.getAverageTangent();
			verticesArray[i * 3]		= position.x;
			verticesArray[i * 3 + 1]	= position.y;
			verticesArray[i * 3 + 2]	= position.z;
			texturesArray[i * 2]		= textureCoord.x;
			texturesArray[i * 2 + 1]	= 1 - textureCoord.y;
			normalsArray[i * 3]			= normalVector.x;
			normalsArray[i * 3 + 1]		= normalVector.y;
			normalsArray[i * 3 + 2]		= normalVector.z;
			tangentsArray[i * 3]		= tangent.x;
			tangentsArray[i * 3 + 1]	= tangent.y;
			tangentsArray[i * 3 + 2]	= tangent.z;
		}
		return furthestPoint;
	}

	private static VertexNM dealWithAlreadyProcessedVertex(final VertexNM previousVertex, final int newTextureIndex,
			final int newNormalIndex, final List<Integer> indices, final List<VertexNM> vertices)
	{
		if (previousVertex.hasSameTextureAndNormal(newTextureIndex, newNormalIndex))
		{
			indices.add(previousVertex.getIndex());
			return previousVertex;
		}
		final var anotherVertex = previousVertex.getDuplicateVertex();
		if (anotherVertex != null)
		{
			return NormalMappedObjLoader.dealWithAlreadyProcessedVertex(anotherVertex, newTextureIndex, newNormalIndex,
					indices, vertices);
		}

		final var duplicateVertex = new VertexNM(vertices.size(), previousVertex.getPosition());
		duplicateVertex.setTextureIndex(newTextureIndex);
		duplicateVertex.setNormalIndex(newNormalIndex);
		previousVertex.setDuplicateVertex(duplicateVertex);
		vertices.add(duplicateVertex);
		indices.add(duplicateVertex.getIndex());

		return duplicateVertex;
	}

	private static void removeUnusedVertices(final List<VertexNM> vertices)
	{
		for (final VertexNM vertex : vertices)
		{
			vertex.averageTangents();
			if (!vertex.isSet())
			{
				vertex.setTextureIndex(0);
				vertex.setNormalIndex(0);
			}
		}
	}

	/**
	 * @param meshDataIn
	 * @return
	 * @throws Exception
	 */
	public IMesh load(final MeshData meshDataIn) throws Exception
	{
		return this.meshManager.create(meshDataIn.positions(), meshDataIn.uvs(), meshDataIn.normals(),
				meshDataIn.tangents(), meshDataIn.indices(), 3);
	}

}