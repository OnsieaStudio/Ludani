package fr.onsiea.engine.client.graphics.texture.data;

import java.io.File;
import java.nio.ByteBuffer;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import fr.onsiea.engine.client.graphics.texture.TextureComponents;

public class TextureBuffer
{
	private ByteBuffer			buffer;
	private TextureComponents	components;

	public TextureBuffer()
	{
	}

	/**
	 * Load texture buffer, if the return from texturebuffer is not zero, it worked fine !
	 * @param filepathIn
	 * @return TextureBuffer
	 */
	public TextureBuffer load(String filepathIn)
	{
		return TextureBuffer.load(new File(filepathIn), true);
	}

	/**
	 * Load texture buffer, if the return from texturebuffer is not zero, it worked fine !
	 * @param filepathIn
	 * @param flipIn
	 * @return TextureBuffer
	 */
	public TextureBuffer load(String filepathIn, boolean flipIn)
	{
		return TextureBuffer.load(new File(filepathIn), flipIn);
	}

	/**
	 * Load texture buffer, if the return from texturebuffer is not zero, it worked fine !
	 * @param fileIn
	 * @return TextureBuffer
	 */
	public final static TextureBuffer load(File fileIn)
	{
		return TextureBuffer.load(fileIn, true);
	}

	/**
	 * Load texture buffer, if the return from texturebuffer is not zero, it worked fine !
	 * @param fileIn
	 * @param flipIn
	 * @return TextureBuffer
	 */
	public final static TextureBuffer load(File fileIn, boolean flipIn)
	{
		if (!fileIn.exists())
		{
			throw new RuntimeException("Can't load file " + fileIn.getAbsolutePath() + " file not exist !");
		}

		final var filepath = fileIn.getAbsolutePath();

		try (var stack = MemoryStack.stackPush())
		{
			final var	w			= stack.mallocInt(1);
			final var	h			= stack.mallocInt(1);
			final var	channels	= stack.mallocInt(1);

			STBImage.stbi_set_flip_vertically_on_load(flipIn);

			final var textureBuffer = new TextureBuffer();
			textureBuffer.buffer(STBImage.stbi_load(filepath, w, h, channels, 4));

			if (textureBuffer.buffer() == null)
			{
				throw new RuntimeException(
						"Can't load buffer of file texture \"" + filepath + "\" " + STBImage.stbi_failure_reason());
			}

			textureBuffer.components(new TextureComponents(w.get(), h.get()));

			return textureBuffer;
		}
		catch (final Exception e)
		{
			e.printStackTrace();

			throw new RuntimeException("Can't load file \"" + filepath + "\" ");
		}
	}

	public boolean cleanup()
	{
		try
		{
			STBImage.stbi_image_free(this.buffer());
		}
		catch (final Exception e)
		{
			e.printStackTrace();

			return false;
		}

		return true;
	}

	public ByteBuffer buffer()
	{
		return this.buffer;
	}

	private void buffer(ByteBuffer bufferIn)
	{
		this.buffer = bufferIn;
	}

	public TextureComponents components()
	{
		return this.components;
	}

	private void components(TextureComponents componentsIn)
	{
		this.components = componentsIn;
	}
}