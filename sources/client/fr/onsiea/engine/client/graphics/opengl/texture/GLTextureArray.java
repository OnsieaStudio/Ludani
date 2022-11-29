package fr.onsiea.engine.client.graphics.opengl.texture;

import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL42;

import fr.onsiea.engine.client.graphics.texture.ITextureData;
import fr.onsiea.engine.client.graphics.texture.data.TextureData;

public class GLTextureArray
{
	private int id;

	public GLTextureArray(int levelsIn, int sizeXIn, int sizeYIn, int depthIn)
	{
		this.id(GL11.glGenTextures());
		this.bind();
		GL42.glTexStorage3D(GL30.GL_TEXTURE_2D_ARRAY, levelsIn, GL11.GL_RGBA8, sizeXIn, sizeYIn, depthIn);
		this.unbind();
	}

	public void bind()
	{
		GL11.glBindTexture(GL30.GL_TEXTURE_2D_ARRAY, this.id());
	}

	public void unbind()
	{
		GL11.glBindTexture(GL30.GL_TEXTURE_2D_ARRAY, 0);
	}

	public void send(final String textureFilepathIn, final int layerIn)
	{
		final var textureBuffer = TextureData.load(textureFilepathIn, true);
		if (textureBuffer == null)
		{
			throw new RuntimeException("[ERREUR] Unable to load \"" + textureFilepathIn + "\"");
		}

		this.send(textureBuffer, layerIn);

		if (!textureBuffer.cleanup())
		{
			throw new RuntimeException("[ERREUR] Unable to unload buffer of texture \"" + textureFilepathIn + "\"");
		}
	}

	void send(final ITextureData textureBufferIn, final int layerIn)
	{
		GL11.glBindTexture(GL30.GL_TEXTURE_2D_ARRAY, this.id());

		// GL12.glTexImage3D(GL30.GL_TEXTURE_2D_ARRAY, 0, GL11.GL_RGBA8,
		// textureBufferIn.width(), textureBufferIn.height(),
		// 0, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, textureBufferIn.buffer());

		GL12.glTexSubImage3D(GL30.GL_TEXTURE_2D_ARRAY, 0, 0, 0, layerIn, textureBufferIn.width(),
				textureBufferIn.height(), 1, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, textureBufferIn.buffer());

		GL11.glTexParameteri(GL30.GL_TEXTURE_2D_ARRAY, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
		GL11.glTexParameteri(GL30.GL_TEXTURE_2D_ARRAY, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL30.GL_TEXTURE_2D_ARRAY, GL12.GL_TEXTURE_MAX_LEVEL, 1000);
		GL30.glGenerateMipmap(GL30.GL_TEXTURE_2D_ARRAY);

		GL11.glTexParameterf(GL30.GL_TEXTURE_2D_ARRAY, GL14.GL_TEXTURE_LOD_BIAS, 1.0f);
		if (GL.getCapabilities().GL_EXT_texture_filter_anisotropic)
		{
			final var amount = Math.max(4f,
					GL11.glGetFloat(EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT));
			GL11.glTexParameterf(GL30.GL_TEXTURE_2D_ARRAY, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT,
					amount);
		}
		else
		{
			System.out.println("Not Supported Anisotropic");
		}

		GL11.glTexParameteri(GL30.GL_TEXTURE_2D_ARRAY, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL30.GL_TEXTURE_2D_ARRAY, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		// GL11.glTexParameteri(GL30.GL_TEXTURE_2D_ARRAY, GL12.GL_TEXTURE_WRAP_R,
		// GL11.GL_REPEAT);
		GL11.glBindTexture(GL30.GL_TEXTURE_2D_ARRAY, 0);
	}

	public int id()
	{
		return this.id;
	}

	public void id(final int idIn)
	{
		this.id = idIn;
	}
}