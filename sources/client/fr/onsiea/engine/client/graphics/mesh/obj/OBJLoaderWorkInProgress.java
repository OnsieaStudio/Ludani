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
package fr.onsiea.engine.client.graphics.mesh.obj;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.joml.Vector2f;
import org.joml.Vector3f;

import fr.onsiea.engine.client.graphics.mesh.IMesh;
import fr.onsiea.engine.client.graphics.mesh.IMeshsManager;
import fr.onsiea.engine.utils.file.FileUtils;

/**
 * /!\ Work in progress /!\
 * @author Seynax
 */
public class OBJLoaderWorkInProgress implements IOBJLoader
{
	private final Vector3f	deltaPos1	= new Vector3f();
	private final Vector3f	deltaPos2	= new Vector3f();
	private final Vector2f	uv0			= new Vector2f();
	private final Vector2f	uv1			= new Vector2f();
	private final Vector2f	uv2			= new Vector2f();
	private IMeshsManager	meshsManager;

	public OBJLoaderWorkInProgress()
	{

	}

	@Override
	public IOBJLoader link(IMeshsManager meshsManagerIn)
	{
		this.meshsManager = meshsManagerIn;

		return this;
	}

	@Override
	public IMesh load(String filepathIn) throws Exception
	{
		final List<Vector3f>					vertices		= new LinkedList<>();
		final List<Vector2f>					textureCoords	= new LinkedList<>();
		final List<Vector3f>					normals			= new LinkedList<>();
		final List<Integer>						indices			= new LinkedList<>();

		final Map<Integer, VertexAttributes>	attributes		= new LinkedHashMap<>();
		final List<OBJTriangle>					triangles		= new LinkedList<>();

		FileUtils.loadLines(filepathIn, line ->
		{
			final var tokens = line.split("\\s+");
			switch (tokens[0])
			{
				case "v":
					// Geometric vertex
					final var position = new Vector3f(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]),
							Float.parseFloat(tokens[3]));

					vertices.add(position);

					break;

				case "vt":
					// Texture coordinate
					final var textureCoord = new Vector2f(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]));

					textureCoords.add(textureCoord);

					break;

				case "vn":
					// Vertex normal
					final var normal = new Vector3f(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]),
							Float.parseFloat(tokens[3]));

					normals.add(normal);

					break;

				case "f":
					final var V0 = this.calcVertexAttributes(vertices, textureCoords, normals, tokens[1], attributes,
							indices);
					final var V1 = this.calcVertexAttributes(vertices, textureCoords, normals, tokens[2], attributes,
							indices);
					final var V2 = this.calcVertexAttributes(vertices, textureCoords, normals, tokens[3], attributes,
							indices);

					triangles.add(new OBJTriangle(V0, V1, V2));

					break;

				default:
					// Ignore other lines
					break;
			}
		});
		OBJLoaderWorkInProgress.removeUnusedVertices(attributes);
		attributes.clear();

		return this.reorderLists(vertices, textureCoords, normals, indices, triangles);
	}

	/**
	 * @param verticesIn
	 * @param textureCoordsIn
	 * @param normalsIn
	 * @param stringIn
	 * @param indicesDataIn
	 * @param attributesIn
	 * @return
	 */
	private VertexAttributes calcVertexAttributes(List<Vector3f> verticesIn, List<Vector2f> textureCoordsIn,
			List<Vector3f> normalsIn, String tokenIn, Map<Integer, VertexAttributes> attributesIn,
			List<Integer> indicesIn)
	{
		final var	V					= tokenIn;

		final var	V_attributes		= V.split("/");
		final var	positionIndex		= Integer.parseInt(V_attributes[0]) - 1;
		var			vertexAttributes	= attributesIn.get(positionIndex);

		if (vertexAttributes == null)
		{
			final var V_position = verticesIn.get(positionIndex);
			vertexAttributes = new VertexAttributes(positionIndex, V_position);

			this.setTextureAndNormal(vertexAttributes, positionIndex, verticesIn, textureCoordsIn, normalsIn,
					V_attributes);

			attributesIn.put(positionIndex, vertexAttributes);
		}
		else if (!vertexAttributes.isSet())
		{
			this.setTextureAndNormal(vertexAttributes, positionIndex, verticesIn, textureCoordsIn, normalsIn,
					V_attributes);
		}

		indicesIn.add(vertexAttributes.index());

		return vertexAttributes;
	}

	private void setTextureAndNormal(VertexAttributes vertexAttributeIn, int positionIndex, List<Vector3f> verticesIn,
			List<Vector2f> textureCoordsIn, List<Vector3f> normalsIn, String[] V_attributesIn)
	{
		if (V_attributesIn.length > 1)
		{
			final var textureCoordsIndexStr = V_attributesIn[1];
			if (textureCoordsIndexStr != null && !textureCoordsIndexStr.isBlank())
			{
				final var	textureCoordsIndex	= Integer.parseInt(textureCoordsIndexStr) - 1;
				final var	textureCoords		= textureCoordsIn.get(textureCoordsIndex);
				if (textureCoords != null && textureCoordsIndex >= 0)
				{
					vertexAttributeIn.setTextureCoords(textureCoordsIndex, textureCoords);
				}
			}

			if (V_attributesIn.length > 2)
			{
				final var normalsIndexStr = V_attributesIn[2];
				if (normalsIndexStr != null && !normalsIndexStr.isBlank())
				{
					final var	normalIndex	= Integer.parseInt(normalsIndexStr) - 1;
					final var	normal		= normalsIn.get(normalIndex);
					if (normal != null && normalIndex >= 0)
					{
						vertexAttributeIn.setNormal(normalIndex, normal);
					}
				}
			}
		}
	}

	private IMesh reorderLists(List<Vector3f> verticesIn, List<Vector2f> texturesIn, List<Vector3f> normalsIn,
			List<Integer> indicesIn, List<OBJTriangle> trianglesIn) throws Exception
	{
		final var	verticesArray	= new float[verticesIn.size() * 3];

		var			i				= 0;
		for (final Vector3f vertice : verticesIn)
		{
			verticesArray[i * 3]		= vertice.x();
			verticesArray[i * 3 + 1]	= vertice.y();
			verticesArray[i * 3 + 2]	= vertice.z();
			i++;
		}

		final var	textureCoordsArray	= new float[verticesIn.size() * 2];
		final var	normalsArray		= new float[verticesIn.size() * 3];
		final var	tangentsArray		= new float[verticesIn.size() * 3];

		i = 0;
		final var indicesArray = indicesIn.stream().mapToInt((Integer v) -> v).toArray();
		indicesIn.clear();
		for (final OBJTriangle triangle : trianglesIn)
		{
			OBJLoaderWorkInProgress.processFaceVertex(triangle.V0(), verticesIn, texturesIn, normalsIn, indicesIn, textureCoordsArray,
					normalsArray, tangentsArray);
			OBJLoaderWorkInProgress.processFaceVertex(triangle.V1(), verticesIn, texturesIn, normalsIn, indicesIn, textureCoordsArray,
					normalsArray, tangentsArray);
			OBJLoaderWorkInProgress.processFaceVertex(triangle.V2(), verticesIn, texturesIn, normalsIn, indicesIn, textureCoordsArray,
					normalsArray, tangentsArray);
			this.calculateTangents(triangle.V0(), triangle.V1(), triangle.V2());
		}
		verticesIn.clear();
		texturesIn.clear();
		normalsIn.clear();
		trianglesIn.clear();

		return this.meshsManager.create(verticesArray, textureCoordsArray, normalsArray, tangentsArray, indicesArray,
				3);
	}

	private static void processFaceVertex(VertexAttributes vertexAttributesIn, List<Vector3f> verticesIn,
			List<Vector2f> textureCoordsIn, List<Vector3f> normalsIn, List<Integer> indicesIn,
			float[] textureCoordsArrayIn, float[] normalsArrayIn, float[] tangentsArrayIn)
	{
		if (vertexAttributesIn.textureCoordsIndex() >= 0)
		{
			final var textureCoord = textureCoordsIn.get(vertexAttributesIn.textureCoordsIndex());
			textureCoordsArrayIn[vertexAttributesIn.index() * 2]		= textureCoord.x;
			textureCoordsArrayIn[vertexAttributesIn.index() * 2 + 1]	= 1 - textureCoord.y;
		}
		if (vertexAttributesIn.normalIndex() >= 0)
		{
			final var vecNorm = normalsIn.get(vertexAttributesIn.normalIndex());
			normalsArrayIn[vertexAttributesIn.index() * 3]		= vecNorm.x;
			normalsArrayIn[vertexAttributesIn.index() * 3 + 1]	= vecNorm.y;
			normalsArrayIn[vertexAttributesIn.index() * 3 + 2]	= vecNorm.z;
		}
		tangentsArrayIn[vertexAttributesIn.index() * 3]		= vertexAttributesIn.averagedTangent().x();
		tangentsArrayIn[vertexAttributesIn.index() * 3 + 1]	= vertexAttributesIn.averagedTangent().y();
		tangentsArrayIn[vertexAttributesIn.index() * 3 + 2]	= vertexAttributesIn.averagedTangent().z();

	}

	private void calculateTangents(VertexAttributes V0In, VertexAttributes V1In, VertexAttributes V2In)
	{
		this.deltaPos1.set(V1In.position()).sub(V0In.position());
		this.deltaPos2.set(V2In.position()).sub(V0In.position());
		this.uv0.set(V0In.textureCoords());
		this.uv1.set(V1In.textureCoords());
		this.uv2.set(V2In.textureCoords());
		final var	deltaUv1	= this.uv1.sub(this.uv0);
		final var	deltaUv2	= this.uv2.sub(this.uv0);

		final var	r			= 1.0f / (deltaUv1.x * deltaUv2.y - deltaUv1.y * deltaUv2.x);

		this.deltaPos1.mul(deltaUv2.y);
		this.deltaPos2.mul(deltaUv1.y);
		final var tangent = this.deltaPos1.sub(this.deltaPos2);
		tangent.mul(r);

		V0In.addTangent(tangent);
		V1In.addTangent(tangent);
		V2In.addTangent(tangent);
	}

	private static void removeUnusedVertices(Map<Integer, VertexAttributes> vertexAttributesIn)
	{
		final var iterator = vertexAttributesIn.entrySet().iterator();
		while (iterator.hasNext())
		{
			final var	entry				= iterator.next();

			final var	vertexAttributes	= entry.getValue();

			vertexAttributes.normalIndex(0);
			if (!vertexAttributes.isSet())
			{
				vertexAttributes.textureCoordsIndex(0);
				vertexAttributes.normalIndex(0);
			}
		}
	}
}