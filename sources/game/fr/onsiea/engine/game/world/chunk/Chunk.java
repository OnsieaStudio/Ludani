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
package fr.onsiea.engine.game.world.chunk;

import org.joml.Vector3i;

import fr.onsiea.engine.client.graphics.opengl.shaders.AdvInstancedShader;
import fr.onsiea.engine.client.graphics.render.IRenderAPIContext;
import fr.onsiea.engine.game.world.World;
import fr.onsiea.engine.game.world.picking.Picker.Selection;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Seynax
 *
 */
public class Chunk
{
	private @Getter final Vector3i position;

	private @Getter @Setter boolean		isVisible	= false;
	private @Getter @Setter boolean		selected	= false;
	private @Getter final ChunkItems	items;
	private final ChunkRenderer			renderer;

	public Chunk(final World worldIn, final Vector3i positionIn)
	{
		this.position	= positionIn;
		this.isVisible	= true;
		this.renderer	= new ChunkRenderer(this);
		this.items		= new ChunkItems(this, this.renderer);
	}

	public void genRender(final IRenderAPIContext contextIn)
	{
		this.renderer.genRender(contextIn);
	}

	public final Chunk draw(final AdvInstancedShader advInstancedShaderIn, final Selection selectionIn)
	{
		this.renderer.draw(advInstancedShaderIn, selectionIn);

		return this;
	}

	public final boolean renderIsInitialized()
	{
		return this.renderer.renderIsInitialized();
	}

	public final Chunk renderIsInitialized(final boolean renderIsInitializedIn)
	{
		this.renderer.renderIsInitialized(renderIsInitializedIn);

		return this;
	}

	public final boolean is(final Chunk chunkIn)
	{
		return chunkIn.x() == this.x() && chunkIn.y() == this.y() && chunkIn.z() == this.z();
	}

	public boolean isEmpty()
	{
		return this.items.isEmpty();
	}

	public final void cleanup()
	{
		this.items.cleanup();
	}

	@Override
	public int hashCode()
	{
		return this.position.hashCode();
	}

	@Override
	public boolean equals(final Object objIn)
	{
		if (objIn == null || !(objIn instanceof Chunk))
		{
			return false;
		}

		return this.position.equals(((Chunk) objIn).position());
	}

	public final int x()
	{
		return this.position.x;
	}

	public final int y()
	{
		return this.position.y;
	}

	public final int z()
	{
		return this.position.z;
	}
}
