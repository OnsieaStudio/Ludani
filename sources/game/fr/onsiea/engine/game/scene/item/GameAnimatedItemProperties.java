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
package fr.onsiea.engine.game.scene.item;

import java.util.List;
import java.util.Objects;

import org.joml.Matrix4f;

import fr.onsiea.engine.client.graphics.material.Material;
import fr.onsiea.engine.client.graphics.mesh.IMesh;
import fr.onsiea.engine.client.graphics.mesh.anim.AnimatedFrame;
import lombok.AccessLevel;
import lombok.Getter;

/**
 * @author Seynax
 *
 */
@Getter(AccessLevel.PUBLIC)
public class GameAnimatedItemProperties
{
	public static final int		MAX_WEIGHTS	= 4;

	private String				name;
	private Material			material;
	private IMesh[]				meshes;
	private int					currentFrame;

	private List<AnimatedFrame>	frames;

	private List<Matrix4f>		invJointMatrices;

	public GameAnimatedItemProperties(IMesh[] meshesIn, List<AnimatedFrame> framesIn, List<Matrix4f> invJointMatricesIn)
	{
		this.meshes				= meshesIn;
		this.frames				= framesIn;
		this.invJointMatrices	= invJointMatricesIn;
		this.currentFrame		= 0;
	}

	public GameAnimatedItemProperties(String nameIn, Material materialIn)
	{
		this.name		= nameIn;
		this.material	= materialIn;
	}

	public GameAnimatedItemProperties(String nameIn, Material materialIn, IMesh meshIn)
	{
		this.name		= nameIn;
		this.material	= materialIn;
		this.meshes		= new IMesh[]
		{ meshIn };
	}

	public GameAnimatedItemProperties(String nameIn, Material materialIn, IMesh[] meshesIn)
	{
		this.name		= nameIn;
		this.material	= materialIn;
		this.meshes		= meshesIn;
	}

	/**
	 * @param meshesIn
	 */
	public GameAnimatedItemProperties(IMesh[] meshesIn)
	{
		this.meshes = meshesIn;
	}

	public AnimatedFrame getCurrentFrame()
	{
		return this.frames.get(this.currentFrame);
	}

	public AnimatedFrame getNextFrame()
	{
		var nextFrame = this.currentFrame + 1;
		if (nextFrame > this.frames.size() - 1)
		{
			nextFrame = 0;
		}
		return this.frames.get(nextFrame);
	}

	public void nextFrame()
	{
		final var nextFrame = this.currentFrame + 1;
		if (nextFrame > this.frames.size() - 1)
		{
			this.currentFrame = 0;
		}
		else
		{
			this.currentFrame = nextFrame;
		}
	}

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
		final var other = (GameAnimatedItemProperties) obj;
		return Objects.equals(this.name, other.name);
	}

	public IMesh getMesh()
	{
		return this.meshes[0];
	}

	public void meshes(IMesh[] meshesIn)
	{
		this.meshes = meshesIn;
	}

	public void meshes(IMesh meshIn)
	{
		/**if (this.meshes != null) {
		    for (IMesh currMesh : meshes) {
		        currMesh.cleanup();
		    }
		}**/
		this.meshes = new IMesh[]
		{ meshIn };
	}
}