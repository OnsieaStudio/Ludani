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

import fr.onsiea.engine.client.graphics.opengl.texture.utils.GLTextureUtils;
import fr.onsiea.engine.client.graphics.texture.ITextureData;
import fr.onsiea.engine.client.graphics.texture.ITextureSettings;
import fr.onsiea.engine.client.graphics.texture.Texture;
import fr.onsiea.engine.client.graphics.texture.data.TextureData;
import fr.onsiea.engine.utils.function.IFunction;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Seynax
 *
 */
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PRIVATE)
public class GLTexture extends Texture<GLTextureSettings>
{
	/**
	 * @param settingsIn
	 */
	protected GLTexture(ITextureSettings<GLTextureSettings> settingsIn, ITextureData... texturesIn)
	{
		super(settingsIn, texturesIn);
	}

	/**
	 * @param textureIdIn
	 * @param settingsIn
	 */
	protected GLTexture(int textureIdIn, int widthIn, int heightIn, ITextureSettings<GLTextureSettings> settingsIn)
	{
		super(textureIdIn, widthIn, heightIn, settingsIn);
	}

	@Override
	protected int genId()
	{
		return GLTextureUtils.gen();
	}

	@Override
	protected void load()
	{
		if (this.hasNotTexture())
		{
			throw new RuntimeException("[ERROR] GLTexture : has not texture defined !");
		}

		if (this.textures.length > 1)
		{
			if (this.settings.target() < 0)
			{
				this.settings.target(GL13.GL_TEXTURE_CUBE_MAP);
			}

			this.load(() ->
			{
				for (var i = 0; i < this.textures.length; i++)
				{
					final var textureData = this.textures[i];
					GL11.glTexImage2D(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL11.GL_RGBA, textureData.width(),
							textureData.height(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, textureData.buffer());

					if (!textureData.cleanup())
					{
						throw new RuntimeException("[ERROR] Unable to unload buffer of textures cube map data !");
					}
				}
			});
		}
		else
		{
			if (this.settings.target() < 0)
			{
				this.settings.target(GL11.GL_TEXTURE_2D);
			}

			if (((TextureData) this.textures[0]).isEmpty())
			{
				this.load((ByteBuffer) null); // Empty texture
			}
			else
			{
				this.load(this.textures[0].buffer());
			}
		}
	}

	private void load(ByteBuffer bufferIn)
	{
		this.load(() -> GL11.glTexImage2D(this.settings.target(), this.settings.level(), this.settings.internalFormat(),
				this.textures[0].width(), this.textures[0].height(), this.settings.border(), this.settings.format(),
				this.settings.type(), bufferIn));
	}

	private void load(IFunction toLoadIn)
	{
		GL11.glBindTexture(this.settings.target(), this.id());
		GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, this.settings.pixelStoreAlignement());

		GL11.glTexParameteri(this.settings.target(), GL11.GL_TEXTURE_MIN_FILTER, this.settings.min());
		GL11.glTexParameteri(this.settings.target(), GL11.GL_TEXTURE_MAG_FILTER, this.settings.mag());
		GL11.glTexParameteri(this.settings.target(), GL11.GL_TEXTURE_WRAP_S, this.settings.wrapS());
		GL11.glTexParameteri(this.settings.target(), GL11.GL_TEXTURE_WRAP_T, this.settings.wrapT());
		GL11.glTexParameterf(this.settings.target(), GL14.GL_TEXTURE_LOD_BIAS, this.settings.lodBias());
		GL11.glTexParameteri(this.settings.target(), GL12.GL_TEXTURE_MIN_LOD, this.settings.lodMin());
		GL11.glTexParameteri(this.settings.target(), GL12.GL_TEXTURE_MAX_LOD, this.settings.lodMax());

		GL11.glTexParameteri(this.settings.target(), GL14.GL_TEXTURE_COMPARE_FUNC, this.settings.textureCompareFunc());
		GL11.glTexParameteri(this.settings.target(), GL14.GL_TEXTURE_COMPARE_MODE, this.settings.textureCompareMode());

		if (this.settings.context().settings().user().isEnabled("mustAnisotropyTextureFiltering")
				&& this.settings.mustAnisotropyTextureFiltering())
		{
			GL11.glTexParameterf(this.settings.target(), EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT,
					this.settings.anisotropyTextureFiltering() >= 0 ? this.settings.anisotropyTextureFiltering()
							: (float) this.settings.context().settings().user().get("anisotropyTextureFilteringAmount")
									.value());
		}

		toLoadIn.execute();

		if (this.settings.mipmapping())
		{
			GL11.glTexParameteri(this.settings.target(), GL12.GL_TEXTURE_BASE_LEVEL, this.settings.textureBaseLevel());
			GL11.glTexParameteri(this.settings.target(), GL12.GL_TEXTURE_MAX_LEVEL, this.settings.textureMaxLevel());
			GL30.glGenerateMipmap(this.settings.target());
		}

		GLTextureUtils.unbind();
	}

	@Override
	public GLTexture attach()
	{
		GL11.glBindTexture(this.settings.target(), this.id);

		return this;
	}

	public GLTexture attach(int typeIn)
	{
		GL11.glBindTexture(typeIn, this.id);

		return this;
	}

	@Override
	public GLTexture send(ByteBuffer bufferIn)
	{
		this.attach();

		GL11.glTexSubImage2D(this.settings.target(), this.settings.level(), 0, 0, this.textures[0].width(),
				this.textures[0].height(), this.settings.format(), this.settings.type(), bufferIn);

		GLTextureUtils.unbind();

		return this;
	}

	@Override
	public GLTexture detach()
	{
		GL11.glBindTexture(this.settings.target(), 0);

		return this;
	}

	@Override
	public GLTexture delete()
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