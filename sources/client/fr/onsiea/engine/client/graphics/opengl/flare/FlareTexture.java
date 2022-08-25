package fr.onsiea.engine.client.graphics.opengl.flare;

import org.joml.Vector2f;

import fr.onsiea.engine.client.graphics.texture.Texture;

public class FlareTexture
{
	private Texture<?>	texture;
	private float		scale;

	private Vector2f	screenPos	= new Vector2f();

	public FlareTexture(Texture<?> textureIn, float scaleIn)
	{
		this.texture(textureIn);
		this.scale(scaleIn);
	}

	public final Texture<?> texture()
	{
		return this.texture;
	}

	private final void texture(Texture<?> textureIn)
	{
		this.texture = textureIn;
	}

	public final float scale()
	{
		return this.scale;
	}

	private final void scale(float scaleIn)
	{
		this.scale = scaleIn;
	}

	public final Vector2f screenPos()
	{
		return this.screenPos;
	}

	public final void screenPos(Vector2f screenPosIn)
	{
		this.screenPos = screenPosIn;
	}
}
