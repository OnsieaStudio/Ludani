package fr.onsiea.engine.client.graphics.texture;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import fr.onsiea.engine.client.graphics.render.IRenderAPIMethods;

public class TexturesManager
{
	private IRenderAPIMethods		renderAPIContext;
	private Map<String, ITexture>	textures;

	public TexturesManager(IRenderAPIMethods renderAPIContextIn)
	{
		this.renderAPIContext(renderAPIContextIn);

		this.textures(new HashMap<>());
	}

	/**
	 * @param pixelsIn
	 * @param widthIn
	 * @param heightIn
	 * @param capabilitiesIn
	 * @return
	 */
	public ITexture load(String nameIn, ByteBuffer pixelsIn, int widthIn, int heightIn)
	{
		var texture = this.textures().get(nameIn);

		if (texture != null)
		{
			return texture;
		}

		texture = this.renderAPIContext().createTexture(widthIn, heightIn, pixelsIn);

		this.textures().put(nameIn, texture);

		return texture;
	}

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

	public void cleanup()
	{
		this.renderAPIContext().deleteTextures(this.textures().values());

		this.textures().clear();
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