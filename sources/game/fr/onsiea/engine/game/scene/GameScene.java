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
package fr.onsiea.engine.game.scene;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.joml.Matrix4f;

import fr.onsiea.engine.client.graphics.opengl.mesh.GLMesh;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Seynax
 *
 */
public class GameScene
{
	private Map<IdentifiableMesh, List<Matrix4f>> objects;

	public GameScene()
	{
		this.objects = new HashMap<>();
	}

	public void add(String nameIn, GLMesh meshIn, Matrix4f... transformationsIn)
	{
		this.add(new IdentifiableMesh(nameIn, meshIn), transformationsIn);
	}

	public void add(IdentifiableMesh identifiableMeshIn, Matrix4f... transformationsIn)
	{
		var list = this.objects().get(identifiableMeshIn);

		if (list == null)
		{
			list = new ArrayList<>();

			this.objects().put(identifiableMeshIn, list);
		}

		Collections.addAll(list, transformationsIn);
	}

	public final Map<IdentifiableMesh, List<Matrix4f>> objects()
	{
		return this.objects;
	}

	public final void objects(Map<IdentifiableMesh, List<Matrix4f>> objectsIn)
	{
		this.objects = objectsIn;
	}

	@Getter(AccessLevel.PUBLIC)
	@AllArgsConstructor
	public final static class IdentifiableMesh
	{
		private final String	name;
		private final GLMesh	mesh;

		@Override
		public int hashCode()
		{
			return Objects.hash(this.name);
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
			final var other = (IdentifiableMesh) obj;
			return Objects.equals(this.name, other.name);
		}
	}
}