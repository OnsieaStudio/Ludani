package fr.onsiea.engine.client.graphics.texture;

import java.io.File;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;

import fr.onsiea.engine.client.graphics.render.IRenderAPIMethods;
import fr.onsiea.engine.client.graphics.texture.data.TextureBytes;
import fr.onsiea.engine.client.graphics.texture.data.TextureData;
import fr.onsiea.engine.client.resources.IResourcesPath;

public class TextureLoader
{
	public final static ITexture load(final String filepathIn, final TextureBytes textureBytesIn,
			IRenderAPIMethods renderAPIContextIn)
	{
		final var	realFilepath	= TextureUtils.filepath(filepathIn);

		final var	textureData		= TextureData.load(new File(realFilepath), false);
		if (textureData != null)
		{
			final var buffer = BufferUtils.createByteBuffer(textureBytesIn.bytes().length);
			buffer.put(textureBytesIn.bytes());
			buffer.flip();

			final var texture = renderAPIContextIn.createTexture(textureData);

			if (!textureData.cleanup())
			{
				throw new RuntimeException("[ERROR] Unable to unload texture buffer : \"" + realFilepath + "\"");
			}

			return texture;
		}

		throw new RuntimeException("[ERROR] Unable to load texture : \"" + realFilepath + "\"");
	}

	final static ITexture load(final String filepathIn, IRenderAPIMethods renderAPIContextIn)
	{
		final var realFilepath = TextureUtils.filepath(filepathIn);

		try
		{
			final var textureData = TextureData.load(new File(realFilepath), false);
			if (textureData != null)
			{
				final var texture = renderAPIContextIn.createTexture(textureData);

				if (!textureData.cleanup())
				{
					throw new RuntimeException("[ERROR] Unable to unload texture buffer : \"" + realFilepath + "\"");
				}

				return texture;
			}
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		throw new RuntimeException("[ERROR] Unable to load texture : \"" + realFilepath + "\"");
	}

	/**
	 * @param filepathIn
	 * @param renderAPIContextIn
	 * @param minIn
	 * @param magIn
	 * @param wrapSIn
	 * @param wrapTIn
	 * @param mipmappingIn
	 * @return
	 */
	public static ITexture load(String filepathIn, IRenderAPIMethods renderAPIContextIn, int minIn, int magIn,
			int wrapSIn, int wrapTIn, boolean mipmappingIn)
	{
		final var realFilepath = TextureUtils.filepath(filepathIn);

		try
		{
			final var textureData = TextureData.load(new File(realFilepath), false);
			if (textureData != null)
			{
				final var texture = renderAPIContextIn.createTexture(textureData, minIn, magIn, wrapSIn, wrapTIn,
						mipmappingIn);

				if (!textureData.cleanup())
				{
					throw new RuntimeException("[ERROR] Unable to unload texture buffer : \"" + realFilepath + "\"");
				}

				return texture;
			}
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		throw new RuntimeException("[ERROR] Unable to load texture : \"" + realFilepath + "\"");
	}

	public final static ITexture load(final ByteBuffer bufferIn, final int widthIn, final int heightIn,
			IRenderAPIMethods renderAPIContextIn)
	{
		if (bufferIn == null)
		{
			return null;
		}

		final var texture = renderAPIContextIn.createTexture(new TextureData(bufferIn, widthIn, heightIn));

		STBImage.stbi_image_free(bufferIn);

		return texture;
	}

	/**
	 * @param renderAPIContextIn
	 * @param filespathsIn
	 * @return
	 * @throws Exception
	 */
	public static ITexture loadCubeMapTextures(IRenderAPIMethods renderAPIContextIn, IResourcesPath... resourcesPathIn)
			throws Exception
	{
		final ITextureData[]	textureData	= new TextureData[resourcesPathIn.length];
		var						i			= 0;

		for (final IResourcesPath resourcePath : resourcesPathIn)
		{
			final var realFilepath = resourcePath.path();
			textureData[i] = TextureData.load(new File(realFilepath), false);

			i++;
		}

		return renderAPIContextIn.createCubeMapTextures(textureData);
	}

	/**
	 * @param renderAPIContextIn
	 * @param minIn
	 * @param magIn
	 * @param wrapSIn
	 * @param wrapTIn
	 * @param mipmappingIn
	 * @param filespathsIn
	 * @return
	 * @throws Exception
	 */
	public static ITexture loadCubeMapTextures(IRenderAPIMethods renderAPIContextIn, int minIn, int magIn, int wrapSIn,
			int wrapTIn, boolean mipmappingIn, IResourcesPath... resourcesPathIn) throws Exception
	{
		final ITextureData[]	textureData	= new TextureData[resourcesPathIn.length];
		var						i			= 0;

		for (final IResourcesPath resourcePath : resourcesPathIn)
		{
			final var realFilepath = resourcePath.path();
			textureData[i] = TextureData.load(new File(realFilepath), false);

			i++;
		}

		return renderAPIContextIn.createCubeMapTextures(minIn, magIn, wrapSIn, wrapTIn, mipmappingIn, textureData);
	}

	final static void send(final ITexture textureIn, final byte[] dataIn)
	{
		final var byteBuffer = BufferUtils.createByteBuffer(dataIn.length);

		byteBuffer.put(dataIn);

		byteBuffer.flip();

		textureIn.send(byteBuffer);
	}
}