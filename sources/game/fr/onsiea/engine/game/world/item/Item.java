/**
 * Copyright 2021 Onsiea All rights reserved.<br>
 * <br>
 *
 * This file is part of Onsiea Engine project.
 * (https://github.com/Onsiea/OnsieaEngine)<br>
 * <br>
 *
 * Onsiea Engine is [licensed]
 * (https://github.com/Onsiea/OnsieaEngine/blob/main/LICENSE) under the terms of
 * the "GNU General Public Lesser License v3.0" (GPL-3.0).
 * https://github.com/Onsiea/OnsieaEngine/wiki/License#license-and-copyright<br>
 * <br>
 *
 * Onsiea Engine is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3.0 of the License, or
 * (at your option) any later version.<br>
 * <br>
 *
 * Onsiea Engine is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.<br>
 * <br>
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Onsiea Engine. If not, see <https://www.gnu.org/licenses/>.<br>
 * <br>
 *
 * Neither the name "Onsiea", "Onsiea Engine", or any derivative name or the
 * names of its authors / contributors may be used to endorse or promote
 * products derived from this software and even less to name another project or
 * other work without clear and precise permissions written in advance.<br>
 * <br>
 *
 * @Author : Seynax (https://github.com/seynax)<br>
 * @Organization : Onsiea Studio (https://github.com/Onsiea)
 */
package fr.onsiea.engine.game.world.item;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

import fr.onsiea.engine.client.graphics.material.Material;
import fr.onsiea.engine.client.graphics.mesh.IMesh;
import fr.onsiea.engine.client.graphics.mesh.obj.MeshData;
import fr.onsiea.engine.client.graphics.opengl.texture.GLTextureArrayManager;
import fr.onsiea.engine.client.graphics.texture.ITexture;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * @author Seynax
 *
 */
@RequiredArgsConstructor
@Getter
@Setter
public class Item
{
	private final ItemTypeVariant	typeVariant;
	private final Vector3f			position;
	private final Vector3f			orientation;
	private final Vector3f			scale;
	private int						instanceId;
	private int						instanceSubIndex;
	private float					uniqueId;

	@Getter
	@Setter
	public final static class ItemType
	{
		private String						name;
		private IMesh						mesh;
		private MeshData					meshData;
		private Vector3f					min;
		private Vector3f					max;
		private Material					material;
		private List<GLTextureArrayManager>	textures;

		/**
		 * @param nameIn
		 * @param meshIn
		 * @param meshDataIn
		 * @param minIn
		 * @param maxIn
		 * @param materialIn
		 * @param texturesIn
		 */
		public ItemType(final String nameIn, final IMesh meshIn, final MeshData meshDataIn, final Vector3f minIn,
				final Vector3f maxIn, final Material materialIn)
		{
			this.name		= nameIn;
			this.mesh		= meshIn;
			this.meshData	= meshDataIn;
			this.min		= minIn;
			this.max		= maxIn;
			this.material	= materialIn;
			this.textures	= new ArrayList<>();
		}
	}

	@AllArgsConstructor
	@Getter
	@Setter
	public final static class ItemTypeVariant
	{
		private ItemType	itemType;
		private String		key;
		private int			textureIndex;
		private int			textureSubIndex;
		private ITexture	texture;
	}
}
