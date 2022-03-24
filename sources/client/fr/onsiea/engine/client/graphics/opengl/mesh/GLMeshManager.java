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
package fr.onsiea.engine.client.graphics.opengl.mesh;

import java.util.HashMap;
import java.util.Map;

import fr.onsiea.engine.client.graphics.mesh.IMaterialMesh;
import fr.onsiea.engine.client.graphics.mesh.IMesh;
import fr.onsiea.engine.client.graphics.mesh.IMeshsManager;
import fr.onsiea.engine.client.graphics.mesh.obj.IOBJLoader;
import fr.onsiea.engine.client.graphics.opengl.mesh.GLMesh.Builder;
import fr.onsiea.engine.client.graphics.opengl.vao.VaoManager;
import fr.onsiea.engine.client.graphics.opengl.vbo.VboManager;
import fr.onsiea.engine.utils.ICleanable;
import lombok.AccessLevel;
import lombok.Getter;

/**
 * @author Seynax
 *
 */
@Getter(AccessLevel.PUBLIC)
public class GLMeshManager implements IMeshsManager
{
	private final VaoManager			vaoManager;
	private final VboManager			vboManager;

	private final Map<String, IMesh>	meshs;

	private final IOBJLoader			objLoader;

	public GLMeshManager(IOBJLoader objLoaderIn)
	{
		this.objLoader = objLoaderIn;
		this.objLoader.link(this);

		this.vaoManager	= new VaoManager();
		this.vboManager	= new VboManager();

		this.meshs		= new HashMap<>();
	}

	public GLMesh.Builder meshBuilderWithVao()
	{
		return GLMesh.Builder.withVao(this.vaoManager, this.vboManager);
	}

	/**
	 * @param iIn
	 * @return
	 */
	public Builder meshBuilderWithVao(int enablesIn)
	{
		return GLMesh.Builder.withVao(this.vaoManager, this.vboManager, enablesIn);
	}

	public GLMesh.Builder meshBuilder()
	{
		return new GLMesh.Builder(this.vaoManager, this.vboManager);
	}

	/**
	 *
	 * @param indicesIn
	 * @param vertexAndAttributesIn
	 * @param attributesSizesIn
	 * @return
	 * @throws Exception
	 */
	public GLMesh build(int[] indicesIn, float[] vertexAndAttributesIn, int... attributesSizesIn) throws Exception
	{
		return GLMesh.Builder.build(this.vaoManager, this.vboManager, indicesIn, vertexAndAttributesIn,
				attributesSizesIn);
	}

	@Override
	public IMesh load(String filepathIn) throws Exception
	{
		final var mesh = this.objLoader.load(filepathIn);
		this.meshs.put(filepathIn, mesh);

		return mesh;
	}

	@Override
	public IMesh load(String nameIn, String filepathIn) throws Exception
	{
		final var mesh = this.objLoader.load(filepathIn);
		this.meshs.put(nameIn, mesh);

		return mesh;
	}

	@Override
	public IMesh create(float[] positionsIn, int dimensionSizeIn) throws Exception
	{
		return this.meshBuilderWithVao(1).vbo(positionsIn, dimensionSizeIn)
				.vertexCount(positionsIn.length / dimensionSizeIn).unbindVao().build();
	}

	@Override
	public IMesh create(float[] positionsIn, int[] indicesIn, int dimensionSizeIn) throws Exception
	{
		return this.meshBuilderWithVao(1).vbo(positionsIn, dimensionSizeIn).ibo(indicesIn).unbindVao().build();
	}

	@Override
	public IMesh create(float[] positionsIn, float[] texturesCoordinatesIn, float[] normalsIn, int[] indicesIn,
			int dimensionSizeIn) throws Exception
	{
		return this.meshBuilderWithVao(3).vbo(positionsIn, dimensionSizeIn).vbo(texturesCoordinatesIn, 2)
				.vbo(normalsIn, dimensionSizeIn).ibo(indicesIn).unbindVao().build();
	}

	@Override
	public IMesh create(float[] positionsIn, float[] texturesCoordinatesIn, float[] normalsIn, float[] tangentsArrayIn,
			int[] indicesIn, int dimensionSizeIn) throws Exception
	{
		return this.meshBuilderWithVao(4).vbo(positionsIn, dimensionSizeIn).vbo(texturesCoordinatesIn, 2)
				.vbo(normalsIn, dimensionSizeIn).vbo(tangentsArrayIn, dimensionSizeIn).ibo(indicesIn).unbindVao()
				.build();
	}

	@Override
	public IMaterialMesh createMeshWithMaterial(float[] positionsIn, float[] texturesCoordinatesIn, float[] normalsIn,
			int[] indicesIn, int dimensionIn) throws Exception
	{
		return new GLMaterialMesh(this.meshBuilderWithVao(3).vbo(positionsIn, dimensionIn).vbo(texturesCoordinatesIn, 2)
				.vbo(normalsIn, dimensionIn).ibo(indicesIn).unbindVao().build());
	}

	@Override
	public IMaterialMesh createMeshWithMaterial(float[] positionsIn, float[] texturesCoordinatesIn, float[] normalsIn,
			float[] tangentsArrayIn, int[] indicesIn, int dimensionIn) throws Exception
	{
		return new GLMaterialMesh(this.meshBuilderWithVao(4).vbo(positionsIn, dimensionIn).vbo(texturesCoordinatesIn, 2)
				.vbo(normalsIn, dimensionIn).vbo(tangentsArrayIn, dimensionIn).ibo(indicesIn).unbindVao().build());
	}

	public static float[]	weights;
	public static int[]		jointIndices;

	@Override
	public IMaterialMesh createMeshWithMaterial(float[] positionsIn, float[] texturesCoordinatesIn, float[] normalsIn,
			int[] indicesIn, int[] jointIndicesIn, float[] weightsIn, int dimensionIn) throws Exception
	{
		GLMeshManager.weights = new float[weightsIn.length];
		var i = 0;
		for (final float weight : weightsIn)
		{
			GLMeshManager.weights[i] = weight;
			i++;
		}
		GLMeshManager.jointIndices	= new int[jointIndicesIn.length];
		i								= 0;
		for (final int jointIndice : jointIndicesIn)
		{
			GLMeshManager.jointIndices[i] = jointIndice;
			i++;
		}
		return new GLMaterialMesh(this.meshBuilderWithVao(5).vbo(positionsIn, dimensionIn).vbo(texturesCoordinatesIn, 2)
				.vbo(normalsIn, dimensionIn).vbo(weightsIn, 4).vbo(jointIndicesIn, 4).ibo(indicesIn).unbindVao()
				.build());
	}

	@Override
	public IMaterialMesh createMeshWithMaterial(float[] positionsIn, float[] texturesCoordinatesIn, float[] normalsIn,
			float[] tangentsArrayIn, int[] indicesIn, int[] jointIndicesIn, float[] weightsIn, int dimensionIn)
			throws Exception
	{
		return new GLMaterialMesh(this.meshBuilderWithVao(6).vbo(positionsIn, dimensionIn).vbo(texturesCoordinatesIn, 2)
				.vbo(normalsIn, dimensionIn).vbo(weightsIn, 4).vbo(jointIndicesIn, 4).vbo(tangentsArrayIn, dimensionIn)
				.ibo(indicesIn).unbindVao().build());
	}

	@Override
	public IMeshsManager add(String nameIn, IMesh meshIn)
	{
		this.meshs.put(nameIn, meshIn);

		return this;
	}

	@Override
	public boolean has(String nameIn)
	{
		return this.meshs.containsKey(nameIn);
	}

	@Override
	public IMesh get(String nameIn)
	{
		return this.meshs.get(nameIn);
	}

	@Override
	public IMeshsManager remove(String nameIn)
	{
		this.meshs.remove(nameIn);

		return this;
	}

	@Override
	public IMeshsManager clear()
	{
		this.meshs.clear();

		return this;
	}

	@Override
	public IOBJLoader objLoader()
	{
		return this.objLoader;
	}

	@Override
	public ICleanable cleanup()
	{
		this.vboManager.cleanup();

		return this;
	}
}