package fr.onsiea.engine.client.graphics.mesh.obj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MeshData
{
	private float[] positions;
	private float[] uvs;
	private float[] normals;
	private float[] tangents;
	private int[] indices;
	
	public MeshData(float[] positionsIn, float[] uvsIn, float[] normalsIn, int[] indicesIn)
	{
		this.positions = positionsIn;
		this.uvs = uvsIn;
		this.normals = normalsIn;
		this.indices = indicesIn;
	}

	public final boolean hasTextureUVS()
	{
		return this.uvs != null;
	}

	public final boolean hasTangents()
	{
		return this.tangents != null;
	}

	public final boolean hasNormals()
	{
		return this.normals != null;
	}
	
	public final boolean hasIndices()
	{
		return this.indices != null;
	}
	
	public final int vertexCount()
	{
		if(this.hasIndices())
			return this.indices.length;
		
		return this.positions.length;
	}
}
