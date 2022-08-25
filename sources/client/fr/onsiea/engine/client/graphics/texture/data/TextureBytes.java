package fr.onsiea.engine.client.graphics.texture.data;

import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PUBLIC)
public class TextureBytes
{
	private final byte[]	bytes;
	private final int		width;
	private final int		height;

	public TextureBytes(final byte[] bytesIn, int widthIn, int heightIn)
	{
		this.bytes	= bytesIn;
		this.width	= widthIn;
		this.height	= heightIn;
	}
}