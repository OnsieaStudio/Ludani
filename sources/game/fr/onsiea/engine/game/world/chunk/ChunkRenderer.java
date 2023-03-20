/**
*	Copyright 2021 Onsiea All rights reserved.
*
*	This file is part of Onsiea Engine. (https://github.com/Onsiea/OnsieaEngine)
*
*	Unless noted in license (https://github.com/Onsiea/OnsieaEngine/blob/main/LICENSE.md) notice file (https://github.com/Onsiea/OnsieaEngine/blob/main/LICENSE_NOTICE.md), Onsiea engine and all parts herein is licensed under the terms of the LGPL-3.0 (https://www.gnu.org/licenses/lgpl-3.0.html)  found here https://www.gnu.org/licenses/lgpl-3.0.html and copied below the license file.
*
*	Onsiea Engine is libre software: you can redistribute it and/or modify
*	it under the terms of the GNU Lesser General Public License as published by
*	the Free Software Foundation, either version 3.0 of the License, or
*	(at your option) any later version.
*
*	Onsiea Engine is distributed in the hope that it will be useful,
*	but WITHOUT ANY WARRANTY; without even the implied warranty of
*	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*	GNU Lesser General Public License for more details.
*
*	You should have received a copy of the GNU Lesser General Public License
*	along with Onsiea Engine.  If not, see <https://www.gnu.org/licenses/> <https://www.gnu.org/licenses/lgpl-3.0.html>.
*
*	Neither the name "Onsiea", "Onsiea Engine", or any derivative name or the names of its authors / contributors may be used to endorse or promote products derived from this software and even less to name another project or other work without clear and precise permissions written in advance.
*
*	(more details on https://github.com/Onsiea/OnsieaEngine/wiki/License)
*
*	@author Seynax
*/
package fr.onsiea.engine.game.world.chunk;

import java.util.LinkedHashMap;
import java.util.Map;

import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import fr.onsiea.engine.client.graphics.opengl.instanced.Instanced;
import fr.onsiea.engine.client.graphics.opengl.shaders.AdvInstancedShader;
import fr.onsiea.engine.client.graphics.render.IRenderAPIContext;
import fr.onsiea.engine.game.world.item.Item;
import fr.onsiea.engine.game.world.item.Item.ItemType;
import fr.onsiea.engine.game.world.picking.Picker.Selection;
import fr.onsiea.engine.utils.Triplet;
import fr.onsiea.engine.utils.maths.MathInstances;
import fr.onsiea.engine.utils.maths.transformations.Transformations3f;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Seynax
 *
 */
class ChunkRenderer
{
	private final Chunk																chunk;
	private final Map<ItemType, Triplet<Integer, Instanced, Map<Vector3f, Item>>>	drawers;
	private @Getter @Setter boolean													renderIsInitialized	= false;

	ChunkRenderer(final Chunk chunkIn)
	{
		this.chunk		= chunkIn;
		this.drawers	= new LinkedHashMap<>();
	}

	public void genRender(final IRenderAPIContext contextIn)
	{
		this.renderIsInitialized = true;

		var	i	= 0;
		var	i0	= 0;
		for (final var entry : this.drawers.entrySet())
		{
			// final var	key		= entry.getKey();
			final var	pair	= entry.getValue();

			final var	builder	= new Instanced.Builder();
			pair.s1(i0);
			i0++;

			builder.data(entry.getKey().meshData().positions(), 3).data(entry.getKey().meshData().uvs(), 2)
					.data(entry.getKey().meshData().normals(), 3).data(entry.getKey().meshData().tangents(), 3);

			final var	buffer		= BufferUtils
					.createFloatBuffer(this.chunk.items().size() * (MathInstances.matrixSizeFloats() + 3));
			final var	localBuffer	= BufferUtils.createFloatBuffer(MathInstances.matrixSizeFloats() + 3);

			i = 0;
			for (final Item item : pair.s3().values())
			{
				item.instanceId(i);

				final var	position	= item.position();
				final var	orientation	= item.orientation();
				final var	scale		= item.scale();

				final var	matrix		= Transformations3f.transformations(position, orientation, scale);

				matrix.get(0, localBuffer);
				localBuffer.put(MathInstances.matrixSizeFloats(), item.typeVariant().textureIndex());
				localBuffer.put(MathInstances.matrixSizeFloats() + 1, item.typeVariant().textureSubIndex() * 10 + 5);
				localBuffer.put(MathInstances.matrixSizeFloats() + 2, item.uniqueId());
				buffer.put(localBuffer);

				localBuffer.clear();

				i++;
			}
			buffer.flip();

			builder.interleaveData(buffer, MathInstances.matrixSizeFloats() + 3).unbind();
			builder.indices(entry.getKey().meshData().indices());

			pair.s2(builder.build(entry.getKey().meshData().indices().length, this.chunk.items().size()));
		}
	}

	public ChunkRenderer add(final Item itemIn)
	{
		var itemsDrawers = this.drawers.get(itemIn.typeVariant().itemType());
		if (itemsDrawers == null)
		{
			itemsDrawers = new Triplet<>(-1, null, new LinkedHashMap<>());
			itemIn.instanceSubIndex(this.drawers.size());
			this.drawers.put(itemIn.typeVariant().itemType(), itemsDrawers);
		}

		itemsDrawers.s3().remove(itemIn.position());
		itemsDrawers.s3().put(itemIn.position(), itemIn);

		return this;
	}

	public void remove(final ItemType itemTypeIn, final Vector3f positionIn)
	{
		final var drawers = this.drawers.get(itemTypeIn);

		drawers.s3().remove(positionIn);

		if (drawers.s3().size() <= 0)
		{
			this.drawers.remove(itemTypeIn);
		}
	}

	public final ChunkRenderer draw(final AdvInstancedShader advInstancedShaderIn, final Selection selectionIn)
	{
		for (final var pair : this.drawers.values())
		{
			advInstancedShaderIn.instanceIsSelected().load(false);
			if (selectionIn.item() != null && this.chunk.is(selectionIn.chunk()))
			{
				advInstancedShaderIn.instanceIsSelected().load(pair.s1() == selectionIn.item().instanceSubIndex());
			}

			pair.s2().bindDrawUnbind();
		}

		return this;
	}
}
