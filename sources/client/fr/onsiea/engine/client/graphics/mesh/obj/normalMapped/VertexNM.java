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

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

/**
 * @author ThinMatrix (https://www.youtube.com/user/thinmatrix, https://github.com/TheThinMatrix)
 */
public class VertexNM
{

	private static final int	NO_INDEX		= -1;

	private final Vector3f		position;
	private int					textureIndex	= VertexNM.NO_INDEX;
	private int					normalIndex		= VertexNM.NO_INDEX;
	private VertexNM			duplicateVertex	= null;
	private final int			index;
	private final float			length;
	public List<Vector3f>		tangents		= new ArrayList<>();
	private final Vector3f		averagedTangent	= new Vector3f(0, 0, 0);

	protected VertexNM(int index, Vector3f position)
	{
		this.index		= index;
		this.position	= position;
		this.length		= position.length();
	}

	protected void addTangent(Vector3f tangent)
	{
		this.tangents.add(tangent);
	}

	//NEW
	protected VertexNM duplicate(int newIndex)
	{
		final var vertex = new VertexNM(newIndex, this.position);
		vertex.tangents = this.tangents;
		return vertex;
	}

	protected void averageTangents()
	{
		if (this.tangents.isEmpty())
		{
			return;
		}
		for (final Vector3f tangent : this.tangents)
		{
			this.averagedTangent.add(tangent);
		}
		this.averagedTangent.normalize();
	}

	protected Vector3f getAverageTangent()
	{
		return this.averagedTangent;
	}

	protected int getIndex()
	{
		return this.index;
	}

	protected float getLength()
	{
		return this.length;
	}

	protected boolean isSet()
	{
		return this.textureIndex != VertexNM.NO_INDEX && this.normalIndex != VertexNM.NO_INDEX;
	}

	protected boolean hasSameTextureAndNormal(int textureIndexOther, int normalIndexOther)
	{
		return textureIndexOther == this.textureIndex && normalIndexOther == this.normalIndex;
	}

	protected void setTextureIndex(int textureIndex)
	{
		this.textureIndex = textureIndex;
	}

	protected void setNormalIndex(int normalIndex)
	{
		this.normalIndex = normalIndex;
	}

	protected Vector3f getPosition()
	{
		return this.position;
	}

	protected int getTextureIndex()
	{
		return this.textureIndex;
	}

	protected int getNormalIndex()
	{
		return this.normalIndex;
	}

	protected VertexNM getDuplicateVertex()
	{
		return this.duplicateVertex;
	}

	protected void setDuplicateVertex(VertexNM duplicateVertex)
	{
		this.duplicateVertex = duplicateVertex;
	}

}
