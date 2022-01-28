package fr.onsiea.engine.client.graphics.texture.data;

import fr.onsiea.engine.client.graphics.texture.TextureComponents;

public class TextureBytes
{
	private byte[]				bytes;
	private TextureComponents	components;

	public TextureBytes(final byte[] bytesIn, int widthIn, int heightIn)
	{
		this.bytes(bytesIn);
		this.components(new TextureComponents(widthIn, heightIn));
	}

	public byte[] bytes()
	{
		return this.bytes;
	}

	public void bytes(final byte[] bytesIn)
	{
		this.bytes = bytesIn;
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