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
package fr.onsiea.engine.client.graphics.opengl.texture;

import java.nio.ByteBuffer;
import java.util.Objects;

import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;

import fr.onsiea.engine.client.graphics.opengl.OpenGLRenderAPIContext;
import fr.onsiea.engine.client.graphics.texture.ITexture;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Seynax
 *
 */
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PRIVATE)
public class GLTextureCubeMap implements ITexture
{
	private int						id;
	private OpenGLRenderAPIContext	context;

	/**
	 * @param widthIn
	 * @param heightIn
	 * @param bufferIn
	 * @param contextIn
	 * @param minIn
	 * @param magIn
	 * @param wrapXIn
	 * @param wrapTIn
	 * @param mipmappingIn
	 */
	public GLTextureCubeMap(final int textureIdIn, final OpenGLRenderAPIContext contextIn, final int minIn,
			final int magIn, final int wrapSIn, final int wrapTIn, final boolean mipmappingIn)
	{
		this.id(textureIdIn);

		this.context(contextIn);

		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, this.id());
		GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);

		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MIN_FILTER, minIn);
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MAG_FILTER, magIn);
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_WRAP_S, wrapSIn);
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_WRAP_T, wrapTIn);
		GL11.glTexParameterf(GL13.GL_TEXTURE_CUBE_MAP, GL14.GL_TEXTURE_LOD_BIAS, -0.4f);
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL12.GL_TEXTURE_MIN_LOD, -1000);
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL12.GL_TEXTURE_MAX_LOD, 1000);

		if (this.context().settings().user().isEnabled("mustAnisotropyTextureFiltering"))
		{
			GL11.glTexParameterf(GL13.GL_TEXTURE_CUBE_MAP, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT,
					(float) this.context().settings().user().get("anisotropyTextureFilteringAmount").value());
		}

		if (mipmappingIn)
		{
			GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL12.GL_TEXTURE_MAX_LEVEL, 1000);
			GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL12.GL_TEXTURE_BASE_LEVEL, 0);
			GL30.glGenerateMipmap(GL13.GL_TEXTURE_CUBE_MAP);
		}
	}

	@Override
	public ITexture resetIndex()
	{
		GL13.glActiveTexture(GL13.GL_TEXTURE0);

		return this;
	}

	@Override
	public ITexture attach()
	{
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, this.id);

		return this;
	}

	@Override
	public ITexture attachAt0()
	{
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, this.id);

		return this;
	}

	@Override
	public ITexture attachAt(final int indexIn)
	{
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + indexIn);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, this.id);

		return this;
	}

	@Override
	public ITexture attach(final int typeIn)
	{
		GL11.glBindTexture(typeIn, this.id);

		return this;
	}

	@Override
	public ITexture attachAt0(final int typeIn)
	{
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(typeIn, this.id);

		return this;
	}

	@Override
	public ITexture attachAt(final int typeIn, final int indexIn)
	{
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + indexIn);
		GL11.glBindTexture(typeIn, this.id);

		return this;
	}

	@Override
	public ITexture send(final ByteBuffer bufferIn)
	{
		this.attach();

		GL11.glTexSubImage2D(GL13.GL_TEXTURE_CUBE_MAP, 0, 0, 0, this.width(), this.height(), GL11.GL_RGBA,
				GL11.GL_UNSIGNED_BYTE, bufferIn);

		this.detach();

		return this;
	}

	@Override
	public ITexture detachAt(final int typeIn, final int indexIn)
	{
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + indexIn);
		GL11.glBindTexture(typeIn, 0);

		return this;
	}

	@Override
	public ITexture detachAt0(final int typeIn)
	{
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(typeIn, this.id);

		return this;
	}

	@Override
	public ITexture detach(final int typeIn)
	{
		GL11.glBindTexture(typeIn, 0);

		return this;
	}

	@Override
	public ITexture detachAt(final int indexIn)
	{
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + indexIn);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, 0);

		return this;
	}

	@Override
	public ITexture detachAt0()
	{
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, 0);

		return this;
	}

	@Override
	public ITexture detach()
	{
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, 0);

		return this;
	}

	@Override
	public int width()
	{
		return -1;
	}

	@Override
	public int height()
	{
		return -1;
	}

	@Override
	public ITexture delete()
	{
		this.detach();

		GL11.glDeleteTextures(this.id);

		return this;
	}

	@Override
	public String toString()
	{
		return "GLTexture [id=" + this.id + "]";
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(this.id);
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null || this.getClass() != obj.getClass())
		{
			return false;
		}
		final var other = (GLTextureCubeMap) obj;
		return this.id == other.id;
	}
}