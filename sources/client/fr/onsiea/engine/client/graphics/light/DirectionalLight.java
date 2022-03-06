package fr.onsiea.engine.client.graphics.light;

import org.joml.Vector3f;

public class DirectionalLight
{
	private Vector3f	color;

	private Vector3f	direction;

	private float		intensity;

	public DirectionalLight(Vector3f colorIn, Vector3f directionIn, float intensityIn)
	{
		this.color(colorIn);
		this.direction(directionIn);
		this.intensity(intensityIn);
	}

	public DirectionalLight(DirectionalLight lightIn)
	{
		this(new Vector3f(lightIn.color()), new Vector3f(lightIn.direction()), lightIn.intensity());
	}

	public final Vector3f color()
	{
		return this.color;
	}

	private final void color(Vector3f colorIn)
	{
		this.color = colorIn;
	}

	public final Vector3f direction()
	{
		return this.direction;
	}

	private final void direction(Vector3f directionIn)
	{
		this.direction = directionIn;
	}

	public final float intensity()
	{
		return this.intensity;
	}

	public final void intensity(float intensityIn)
	{
		this.intensity = intensityIn;
	}
}