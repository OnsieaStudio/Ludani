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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.joml.Vector2f;
import org.joml.Vector3f;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Seynax
 *
 */
@Getter(AccessLevel.PUBLIC)
public class VertexAttributes
{
	public static final int									NO_VALUE	= -1;

	private final int										index;
	private @Setter(AccessLevel.PUBLIC) int					textureCoordsIndex;
	private @Setter(AccessLevel.PUBLIC) int					normalIndex;

	private final Vector3f									position;
	private Vector2f										textureCoords;
	private Vector3f										normal;

	private @Setter(AccessLevel.PUBLIC) VertexAttributes	duplicate;

	public final Vector3f									averagedTangent;

	public List<Vector3f>									tangents;

	private final float										length;

	/**
	 * @param positionIndexIn
	 * @param textureCoordsIndexIn
	 * @param normalsIndexIn
	 * @param v_positionIn
	 * @param v_textureCoordsIn
	 * @param v_normalsIn
	 */
	public VertexAttributes(int positionIndexIn, Vector3f positionIn)
	{
		this.index				= positionIndexIn;
		this.textureCoordsIndex	= VertexAttributes.NO_VALUE;
		this.normalIndex		= VertexAttributes.NO_VALUE;

		this.position			= positionIn;
		this.textureCoords		= null;
		this.normal				= null;

		this.averagedTangent	= new Vector3f();

		this.tangents			= new ArrayList<>();

		this.length				= this.position.length();
	}

	private VertexAttributes(VertexAttributes vertexAttributesIn)
	{
		this.index				= vertexAttributesIn.index;
		this.textureCoordsIndex	= vertexAttributesIn.textureCoordsIndex;
		this.normalIndex		= vertexAttributesIn.normalIndex;

		this.position			= vertexAttributesIn.position;
		this.textureCoords		= vertexAttributesIn.textureCoords;
		this.normal				= vertexAttributesIn.normal;

		this.averagedTangent	= vertexAttributesIn.averagedTangent;

		this.tangents			= new ArrayList<>();
		this.tangents.addAll(vertexAttributesIn.tangents);

		this.length = vertexAttributesIn.length;
	}

	public final void setTextureCoords(int textureCoordsIndexIn, Vector2f textureCoordsIn)
	{
		this.textureCoordsIndex	= textureCoordsIndexIn;
		this.textureCoords		= textureCoordsIn;
	}

	public final void setNormal(int normalIndexIn, Vector3f normalIn)
	{
		this.normalIndex	= normalIndexIn;
		this.normal			= normalIn;
	}

	protected void addTangent(Vector3f tangent)
	{
		this.tangents.add(tangent);
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

	protected boolean isSet()
	{
		return this.textureCoordsIndex != VertexAttributes.NO_VALUE && this.normalIndex != VertexAttributes.NO_VALUE;
	}

	protected boolean hasSameTextureAndNormal(int textureIndexOther, int normalIndexOther)
	{
		return textureIndexOther == this.textureCoordsIndex && normalIndexOther == this.normalIndex;
	}

	public VertexAttributes genDuplicate(int indexIn)
	{
		this.duplicate = new VertexAttributes(this);

		return this.duplicate;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(this.normal, this.position, this.textureCoords);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null || this.getClass() != obj.getClass())
		{
			return false;
		}
		final var other = (VertexAttributes) obj;
		return Objects.equals(this.normal, other.normal) && Objects.equals(this.position, other.position)
				&& Objects.equals(this.textureCoords, other.textureCoords);
	}
}