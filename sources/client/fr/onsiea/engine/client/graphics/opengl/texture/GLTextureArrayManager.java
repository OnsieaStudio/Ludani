package fr.onsiea.engine.client.graphics.opengl.texture;

import java.util.ArrayList;
import java.util.List;

import fr.onsiea.engine.client.graphics.texture.ITextureData;

public class GLTextureArrayManager
{
	private GLTextureArray	textureArray;
	private List<String>	layers;
	private int				layer;

	public GLTextureArrayManager(final int levelsIn, final int sizeXIn, final int sizeYIn, final int depthIn)
	{
		this.textureArray(new GLTextureArray(levelsIn, sizeXIn, sizeYIn, depthIn));
		this.layers(new ArrayList<>());
		this.layer(0);
	}

	GLTextureArrayManager(final GLTextureArray textureArrayIn)
	{
		this.textureArray(textureArrayIn);
		this.layers(new ArrayList<>());
		this.layer(0);
	}

	public int send(final String texturefilepathIn)
	{
		this.textureArray().send(texturefilepathIn, this.layer());
		this.layers().add(texturefilepathIn);

		final var layer = this.layer;
		this.increaseLayer();

		return layer;
	}

	public int send(final String nameIn, final String texturefilepathIn)
	{
		this.textureArray().send(texturefilepathIn, this.layer());
		this.layers().add(nameIn);

		final var layer = this.layer;
		this.increaseLayer();

		return layer;
	}

	public int send(final String nameIn, final ITextureData textureDataIn)
	{
		this.textureArray().send(textureDataIn, this.layer());
		this.layers().add(nameIn);

		final var layer = this.layer;
		this.increaseLayer();

		return layer;
	}

	public int layerOf(final String nameIn)
	{
		var i = 0;

		for (final String name : this.layers())
		{
			if (name.contentEquals(nameIn))
			{
				return i;
			}
			i++;
		}

		return -1;
	}

	private void increaseLayer()
	{
		this.layer++;
	}

	public GLTextureArray textureArray()
	{
		return this.textureArray;
	}

	private void textureArray(final GLTextureArray textureArrayIn)
	{
		this.textureArray = textureArrayIn;
	}

	private List<String> layers()
	{
		return this.layers;
	}

	private void layers(final List<String> layersIn)
	{
		this.layers = layersIn;
	}

	private int layer()
	{
		return this.layer;
	}

	private void layer(final int layerIn)
	{
		this.layer = layerIn;
	}
}
