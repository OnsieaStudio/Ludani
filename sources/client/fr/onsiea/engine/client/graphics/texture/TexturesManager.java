package fr.onsiea.engine.client.graphics.texture;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import fr.onsiea.engine.client.graphics.render.IRenderAPIMethods;
import fr.onsiea.engine.client.graphics.texture.data.TextureData;
import fr.onsiea.engine.client.resources.IResourcesPath;
import fr.onsiea.engine.utils.ICleanable;

public class TexturesManager implements ITexturesManager
{
	private IRenderAPIMethods		renderAPIContext;
	private Map<String, ITexture>	textures;

	public TexturesManager(IRenderAPIMethods renderAPIContextIn)
	{
		this.renderAPIContext(renderAPIContextIn);

		this.textures(new HashMap<>());
	}

	@Override
	public ITexture load(String filepathIn)
	{
		var texture = this.textures().get(filepathIn);

		if (texture != null)
		{
			return texture;
		}

		texture = TextureLoader.load(filepathIn, this.renderAPIContext());

		this.textures().put(filepathIn, texture);

		return texture;
	}

	@Override
	public ITexture load(String nameIn, String filepathIn)
	{
		var texture = this.textures().get(nameIn);

		if (texture != null)
		{
			return texture;
		}

		texture = TextureLoader.load(filepathIn, this.renderAPIContext());

		this.textures().put(nameIn, texture);

		return texture;
	}

	@Override
	public ITexture load(String filepathIn, int minIn, int magIn, int wrapSIn, int wrapTIn, boolean mipmappingIn)
	{
		var texture = this.textures().get(filepathIn);

		if (texture != null)
		{
			return texture;
		}

		texture = TextureLoader.load(filepathIn, this.renderAPIContext(), minIn, magIn, wrapSIn, wrapTIn, mipmappingIn);

		this.textures().put(filepathIn, texture);

		return texture;
	}

	/**
	 * @param pixelsIn
	 * @param widthIn
	 * @param heightIn
	 * @param capabilitiesIn
	 * @return
	 */
	@Override
	public ITexture load(String nameIn, ByteBuffer pixelsIn, int widthIn, int heightIn)
	{
		var texture = this.textures().get(nameIn);

		if (texture != null)
		{
			return texture;
		}

		texture = this.renderAPIContext().createTexture(new TextureData(pixelsIn, widthIn, heightIn));

		this.textures().put(nameIn, texture);

		return texture;
	}

	@Override
	public ITexture createEmpty(String nameIn, int widthIn, int heightIn, int pixelFormatIn)
	{
		return this.renderAPIContext().createTexture(widthIn, heightIn, pixelFormatIn);
	}

	@Override
	public ITexture createEmpty(String nameIn, int widthIn, int heightIn, int pixelFormatIn, int minIn, int magIn,
			int wrapSIn, int wrapTIn, boolean mipmappingIn)
	{
		return this.renderAPIContext().createTexture(widthIn, heightIn, pixelFormatIn, minIn, magIn, wrapSIn, wrapTIn,
				mipmappingIn);
	}

	@Override
	public ITexture loadCubeMapTextures(String nameIn, IResourcesPath... resourcesPathIn) throws Exception
	{
		var texture = this.textures().get(nameIn);

		if (texture != null)
		{
			return texture;
		}

		texture = TextureLoader.loadCubeMapTextures(this.renderAPIContext(), resourcesPathIn);

		this.textures().put(nameIn, texture);

		return texture;
	}

	@Override
	public ITexture loadCubeMapTextures(String nameIn, int minIn, int magIn, int wrapSIn, int wrapTIn,
			boolean mipmappingIn, IResourcesPath... resourcesPathIn) throws Exception
	{
		var texture = this.textures().get(nameIn);

		if (texture != null)
		{
			return texture;
		}

		texture = TextureLoader.loadCubeMapTextures(this.renderAPIContext(), minIn, magIn, wrapSIn, wrapTIn,
				mipmappingIn, resourcesPathIn);

		this.textures().put(nameIn, texture);

		return texture;
	}

	@Override
	public ITexturesManager add(String nameIn, ITexture textureIn)
	{
		this.textures.put(nameIn, textureIn);

		return this;
	}

	@Override
	public boolean has(String nameIn)
	{
		return this.textures().containsKey(nameIn);
	}

	@Override
	public ITexture get(String nameIn)
	{
		return this.textures().get(nameIn);
	}

	@Override
	public ITexturesManager remove(String nameIn)
	{
		this.textures().remove(nameIn);

		return this;
	}

	@Override
	public ITexturesManager clear()
	{
		this.textures().clear();

		return this;
	}

	@Override
	public ICleanable cleanup()
	{
		this.renderAPIContext().deleteTextures(this.textures().values());

		this.textures().clear();

		return this;
	}

	private final IRenderAPIMethods renderAPIContext()
	{
		return this.renderAPIContext;
	}

	private final void renderAPIContext(IRenderAPIMethods renderAPIContextIn)
	{
		this.renderAPIContext = renderAPIContextIn;
	}

	private final Map<String, ITexture> textures()
	{
		return this.textures;
	}

	private final void textures(Map<String, ITexture> texturesIn)
	{
		this.textures = texturesIn;
	}
}