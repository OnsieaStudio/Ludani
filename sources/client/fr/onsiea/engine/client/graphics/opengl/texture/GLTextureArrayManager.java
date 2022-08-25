package fr.onsiea.engine.client.graphics.opengl.texture;

import java.util.ArrayList;
import java.util.List;

public class GLTextureArrayManager
{
	private GLTextureArray	textureArray;
	private List<String>	layers;
	private int				layer;

	public GLTextureArrayManager(int levelsIn, int sizeXIn, int sizeYIn, int depthIn)
	{
		this.textureArray(new GLTextureArray(levelsIn, sizeXIn, sizeYIn, depthIn));
		this.layers(new ArrayList<>());
		this.layer(0);
	}

	GLTextureArrayManager(GLTextureArray textureArrayIn)
	{
		this.textureArray(textureArrayIn);
		this.layers(new ArrayList<>());
		this.layer(0);
	}

	public GLTextureArrayManager send(String texturefilepathIn)
	{
		this.textureArray().send(texturefilepathIn, this.layer());
		this.layers().add(texturefilepathIn);

		this.increaseLayer();

		return this;
	}

	public GLTextureArrayManager send(String nameIn, String texturefilepathIn)
	{
		this.textureArray().send(texturefilepathIn, this.layer());
		this.layers().add(nameIn);

		this.increaseLayer();

		return this;
	}

	public int layerOf(String nameIn)
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

	private void textureArray(GLTextureArray textureArrayIn)
	{
		this.textureArray = textureArrayIn;
	}

	private List<String> layers()
	{
		return this.layers;
	}

	private void layers(List<String> layersIn)
	{
		this.layers = layersIn;
	}

	private int layer()
	{
		return this.layer;
	}

	private void layer(int layerIn)
	{
		this.layer = layerIn;
	}
}
