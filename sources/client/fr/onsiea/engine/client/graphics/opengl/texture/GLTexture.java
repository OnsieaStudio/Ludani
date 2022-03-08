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
import java.nio.IntBuffer;
import java.util.Objects;

import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;

import fr.onsiea.engine.client.graphics.opengl.OpenGLRenderAPIContext;
import fr.onsiea.engine.client.graphics.texture.ITexture;
import fr.onsiea.engine.client.graphics.texture.TextureComponents;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Seynax
 *
 */
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PRIVATE)
public class GLTexture implements ITexture
{
	public final static int gen()
	{
		return GL11.glGenTextures();
	}

	public final static int[] gen(int[] texturesIn)
	{
		GL11.glGenTextures(texturesIn);

		return texturesIn;
	}

	public final static IntBuffer gen(IntBuffer texturesIn)
	{
		GL11.glGenTextures(texturesIn);

		return texturesIn;
	}

	public final static void bind(final int textureIdIn)
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureIdIn);
	}

	public final static void active(final int idIn)
	{
		GL13.glActiveTexture(idIn);
	}

	public final static void unbind()
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	public final static void delete(final int textureIdIn)
	{
		GL11.glDeleteTextures(textureIdIn);
	}

	public final static void deletes(IntBuffer texturesBufferIn)
	{
		GL11.glDeleteTextures(texturesBufferIn);
	}

	private int						id;
	private TextureComponents		components;
	private OpenGLRenderAPIContext	context;

	/**
	 * @param widthIn
	 * @param heightIn
	 * @param bufferIn
	 */
	public GLTexture(int widthIn, int heightIn, ByteBuffer bufferIn, OpenGLRenderAPIContext contextIn)
	{
		this.id(GLTexture.gen());

		this.components(new TextureComponents(widthIn, heightIn));

		this.context(contextIn);

		this.load(bufferIn);
	}

	private void initialization()
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.id());
		GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -0.4f);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MIN_LOD, -1000);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LOD, 1000);

		if (this.context().settings().user().isEnabled("mustAnisotropyTextureFiltering"))
		{
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT,
					(float) this.context().settings().user().get("anisotropyTextureFilteringAmount").value());
		}
	}

	private void mipmap()
	{
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LEVEL, 1000);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_BASE_LEVEL, 0);
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
	}

	private void load(ByteBuffer bufferIn)
	{
		this.initialization();

		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, this.components().width(), this.components().height(), 0,
				GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, bufferIn);

		this.mipmap();

		GLTexture.unbind();
	}

	@Override
	public ITexture attach()
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.id);

		return this;
	}

	public ITexture attach(int typeIn)
	{
		GL11.glBindTexture(typeIn, this.id);

		return this;
	}

	public ITexture attachAndActive0()
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.id());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);

		return this;
	}

	@Override
	public void send(ByteBuffer bufferIn)
	{
		this.attach();

		GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, this.components().width(), this.components().height(),
				GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, bufferIn);

		GLTexture.unbind();
	}

	@Override
	public ITexture detach()
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

		return this;
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
		final var other = (GLTexture) obj;
		return this.id == other.id;
	}
}