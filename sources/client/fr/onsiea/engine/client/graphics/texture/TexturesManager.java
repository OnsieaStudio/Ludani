package fr.onsiea.engine.client.graphics.texture;

import java.util.HashMap;
import java.util.Map;

import fr.onsiea.engine.client.graphics.render.IRenderAPIContext;

public class TexturesManager
{
	private IRenderAPIContext		renderAPIContext;
	private Map<String, ITexture>	textures;

	public TexturesManager(IRenderAPIContext renderAPIContextIn)
	{
		this.renderAPIContext(renderAPIContextIn);

		this.textures(new HashMap<>());
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

	private final IRenderAPIContext renderAPIContext()
	{
		return this.renderAPIContext;
	}

	private final void renderAPIContext(IRenderAPIContext renderAPIContextIn)
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