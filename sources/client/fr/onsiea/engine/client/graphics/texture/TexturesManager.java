package fr.onsiea.engine.client.graphics.texture;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.stb.STBImage;

import fr.onsiea.engine.client.graphics.texture.data.TextureData;
import fr.onsiea.engine.client.resources.IResourcesPath;
import fr.onsiea.engine.utils.ICleanable;

public abstract class TexturesManager<T extends ITextureSettings<T>> implements ITexturesManager<T>
{
	protected final Map<String, Texture<T>> textures;

	public TexturesManager()
	{
		this.textures = new HashMap<>();
	}

	protected abstract Texture<T> create(ITextureSettings<T> settingsIn, ITextureData... texturesIn);

	protected abstract Texture<T> create(int textureIdIn, int heightIn, int widthIn, ITextureSettings<T> settingsIn);

	protected abstract TexturesManager<T> deleteTextures();

	@Override
	public Texture<T> load(final String filepathIn)
	{
		return this.load(filepathIn, this.defaultTextureSettings());
	}

	@Override
	public Texture<T> load(final String filepathIn, final ITextureSettings<T> settingsIn)
	{
		return this.load(filepathIn, filepathIn, settingsIn);
	}

	@Override
	public Texture<T> load(final IResourcesPath resourcepathIn)
	{
		return this.load(resourcepathIn, this.defaultTextureSettings());
	}

	@Override
	public Texture<T> load(final IResourcesPath resourcepathIn, final ITextureSettings<T> settingsIn)
	{
		final var path = resourcepathIn.path();

		return this.load(path, path, settingsIn);
	}

	@Override
	public Texture<T> load(final String nameIn, final IResourcesPath resourcepathIn)
	{
		return this.load(nameIn, this.defaultTextureSettings(), resourcepathIn);
	}

	@Override
	public Texture<T> load(final String nameIn, final IResourcesPath resourcepathIn,
			final ITextureSettings<T> settingsIn)
	{
		return this.load(nameIn, resourcepathIn.path(), settingsIn);
	}

	@Override
	public Texture<T> load(final String nameIn, final String filepathIn)
	{
		return this.load(nameIn, this.defaultTextureSettings(), filepathIn);
	}

	@Override
	public Texture<T> load(final String nameIn, final String filepathIn, final ITextureSettings<T> settingsIn)
	{
		var texture = this.textures.get(nameIn);

		if (texture != null)
		{
			return texture;
		}

		final var	path		= TextureUtils.filepath(filepathIn);

		final var	textureData	= TextureData.load(path);

		if (textureData == null)
		{
			throw new RuntimeException("[ERROR] Unable to load texture : \"" + path + "\"");
		}

		texture = this.create(settingsIn, textureData);

		this.textures.put(nameIn, texture);

		if (!textureData.cleanup())
		{
			throw new RuntimeException("[ERROR] Unable to unload texture buffer : \"" + path + "\"");
		}

		return texture;
	}

	@Override
	public Texture<T> load(final String nameIn, final String... filepathsIn)
	{
		return this.load(nameIn, this.defaultTextureSettings(), filepathsIn);
	}

	@Override
	public Texture<T> load(final String nameIn, final ITextureSettings<T> settingsIn, final String... filepathsIn)
	{
		var texture = this.textures.get(nameIn);

		if (texture != null)
		{
			return texture;
		}

		final var	texturesData	= new ITextureData[filepathsIn.length];
		var			i				= 0;

		for (final String filepath : filepathsIn)
		{
			final var	path		= TextureUtils.filepath(filepath);
			final var	textureData	= TextureData.load(path, false);
			if (textureData == null)
			{
				throw new RuntimeException("[ERROR] Unable to load texture : \"" + path + "\"");
			}

			texturesData[i] = textureData;

			i++;
		}

		texture = this.create(settingsIn, texturesData);

		this.textures.put(nameIn, texture);

		for (final var textureData : texturesData)
		{
			if (!textureData.cleanup())
			{
				throw new RuntimeException("[ERROR] Unable to unload texture buffer : \"" + filepathsIn[0] + "\"");
			}
		}

		return texture;
	}

	@Override
	public Texture<T> load(final String nameIn, final IResourcesPath... resourcespathsIn)
	{
		return this.load(nameIn, this.defaultTextureSettings(), resourcespathsIn);
	}

	@Override
	public Texture<T> load(final String nameIn, final ITextureSettings<T> settingsIn,
			final IResourcesPath... resourcespathsIn)
	{
		var texture = this.textures.get(nameIn);

		if (texture != null)
		{
			return texture;
		}

		final var	texturesData	= new ITextureData[resourcespathsIn.length];
		var			i				= 0;

		for (final var resourcespath : resourcespathsIn)
		{
			final var	path		= resourcespath.path();
			final var	textureData	= TextureData.load(path, false);

			if (textureData == null)
			{
				throw new RuntimeException("[ERROR] Unable to load texture : \"" + path + "\"");
			}

			texturesData[i] = textureData;

			i++;
		}

		texture = this.create(settingsIn, texturesData);

		this.textures.put(nameIn, texture);

		/**for (final var textureData : texturesData)
		{
			if (!textureData.cleanup())
			{
				throw new RuntimeException("[ERROR] Unable to unload texture buffer : \"" + resourcespathsIn[0] + "\"");
			}
		}**/

		return texture;
	}

	@Override
	public Texture<T> load(final String nameIn, final ByteBuffer pixelsIn, final int widthIn, final int heightIn)
	{
		return this.load(nameIn, pixelsIn, widthIn, heightIn, this.defaultTextureSettings());
	}

	@Override
	public Texture<T> load(final String nameIn, final ByteBuffer bufferIn, final int widthIn, final int heightIn,
			final ITextureSettings<T> settingsIn)
	{
		var texture = this.textures.get(nameIn);

		if (texture != null)
		{
			STBImage.stbi_image_free(bufferIn);

			return texture;
		}

		texture = this.create(settingsIn, new TextureData(bufferIn, widthIn, heightIn));

		this.textures.put(nameIn, texture);

		STBImage.stbi_image_free(bufferIn);

		return texture;
	}

	@Override
	public Texture<T> load(final String nameIn, final ITextureData textureDataIn)
	{
		return this.load(nameIn, textureDataIn, this.defaultTextureSettings());
	}

	@Override
	public Texture<T> load(final String nameIn, final ITextureData textureDataIn, final ITextureSettings<T> settingsIn)
	{
		var texture = this.textures.get(nameIn);

		if (texture != null)
		{
			if (!textureDataIn.cleanup())
			{
				throw new RuntimeException("[ERROR] Unable to unload texture buffer of : \"" + nameIn + "\"");
			}

			return texture;
		}

		texture = this.create(settingsIn, textureDataIn);

		this.textures.put(nameIn, texture);

		if (!textureDataIn.cleanup())
		{
			throw new RuntimeException("[ERROR] Unable to unload texture buffer of : \"" + nameIn + "\"");
		}

		return texture;
	}

	@Override
	public ITexturesManager<T> add(final String nameIn, final Texture<T> textureIn)
	{
		this.textures.put(nameIn, textureIn);

		return this;
	}

	@Override
	public boolean has(final String nameIn)
	{
		return this.textures.containsKey(nameIn);
	}

	@Override
	public Texture<T> get(final String nameIn)
	{
		return this.textures.get(nameIn);
	}

	@Override
	public ITexturesManager<T> remove(final String nameIn)
	{
		this.textures.remove(nameIn);

		return this;
	}

	@Override
	public ITexturesManager<T> clear()
	{
		this.textures.clear();

		return this;
	}

	@Override
	public ICleanable cleanup()
	{
		this.deleteTextures();

		this.textures.clear();

		return this;
	}

	/**public final static class TextureBuilder<T extends ITextureSettings<T>>
	{
		// name(...)					->		filespath(...)													-> settings	-> build(name, settings, filespath...)
		// 									-> 	resourcespath(...)										-> settings	-> build(name, settings, filespath...)
		// 									-> 	buffer(buffer, width, height)						-> settings	-> build(name, settings, buffer, width, height)
		// 									-> 	textureId(textureId, width, height)		-> settings	-> build(name, settings, textureId, width, height)
		// filepath(...)				-> 	name(...)														-> settings	-> build(name, settings, filepath)
		// filepath(...)																								-> settings	-> build(name, settings, filepath)
		// resourcepath(...)		-> 	name(...)														-> settings	-> build(name, settings, resourcepath)
		// resourcepath(...)																						-> settings	-> build(name, settings, resourcepath)

		private final TexturesManager<T>	texturesManager;
		private final String				name;
		private final ITextureSettings<T>	settings;
		private TextureData					textureData;

		public TextureBuilder(TexturesManager<T> texturesManagerIn, String nameIn, ITextureSettings<T> settingsIn)
		{
			this.texturesManager	= texturesManagerIn;
			this.name				= nameIn;
			this.settings			= settingsIn;
		}

		public Texture<T> load()
		{
			return this.load(this.name);
		}

		public Texture<T> load(IResourcesPath... filepathIn)
		{
			return null;
		}

		public Texture<T> load(String... filepathIn)
		{
			return null;
		}
	}**/
}