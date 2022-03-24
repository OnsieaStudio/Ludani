package fr.onsiea.engine.client.graphics.texture.data;

import java.io.File;
import java.nio.ByteBuffer;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import fr.onsiea.engine.client.graphics.texture.ITextureData;
import lombok.AccessLevel;
import lombok.Setter;

@Setter(AccessLevel.PRIVATE)
public class TextureData implements ITextureData
{
	private ByteBuffer	buffer;
	private int			width;
	private int			height;

	public TextureData()
	{
	}

	/**
	 * @param bufferIn
	 * @param widthIn
	 * @param heightIn
	 */
	public TextureData(ByteBuffer bufferIn, int widthIn, int heightIn)
	{
		this.buffer	= bufferIn;
		this.width	= widthIn;
		this.height	= heightIn;
	}

	/**
	 * Load texture buffer, if the return from texturebuffer is not zero, it worked fine !
	 * @param filepathIn
	 * @return TextureBuffer
	 */
	public final static TextureData load(String filepathIn)
	{
		return TextureData.load(new File(filepathIn), true);
	}

	/**
	 * Load texture buffer, if the return from texturebuffer is not zero, it worked fine !
	 * @param filepathIn
	 * @param flipIn
	 * @return TextureBuffer
	 */
	public final static TextureData load(String filepathIn, boolean flipIn)
	{
		return TextureData.load(new File(filepathIn), flipIn);
	}

	/**
	 * Load texture buffer, if the return from texturebuffer is not zero, it worked fine !
	 * @param fileIn
	 * @return TextureBuffer
	 */
	public final static TextureData load(File fileIn)
	{
		return TextureData.load(fileIn, true);
	}

	/**
	 * Load texture buffer, if the return from texturebuffer is not zero, it worked fine !
	 * @param fileIn
	 * @param flipIn
	 * @return TextureBuffer
	 */
	public final static TextureData load(File fileIn, boolean flipIn)
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

			if (w == null || h == null || channels == null)
			{
				throw new RuntimeException(
						"Can't load metadata of file texture \"" + filepath + "\" " + STBImage.stbi_failure_reason());
			}

			STBImage.stbi_set_flip_vertically_on_load(false);
			if (flipIn)
			{
				System.out.println("Flip !");
				STBImage.stbi_set_flip_vertically_on_load(true);
			}

			final var textureData = new TextureData();
			textureData.buffer(STBImage.stbi_load(filepath, w, h, channels, 4));

			if (textureData.buffer() == null)
			{
				throw new RuntimeException(
						"Can't load buffer of file texture \"" + filepath + "\" " + STBImage.stbi_failure_reason());
			}

			textureData.width(w.get());
			textureData.height(h.get());

			return textureData;
		}
		catch (final Exception e)
		{
			e.printStackTrace();

			throw new RuntimeException("Can't load file \"" + filepath + "\" ");
		}
	}

	@Override
	public boolean cleanup()
	{
		try
		{
			if (this.buffer() != null)
			{
				STBImage.stbi_image_free(this.buffer());
			}
		}
		catch (final Exception e)
		{
			e.printStackTrace();

			return false;
		}

		return true;
	}

	@Override
	public final ByteBuffer buffer()
	{
		return this.buffer;
	}

	@Override
	public final int width()
	{
		return this.width;
	}

	@Override
	public final int height()
	{
		return this.height;
	}
}