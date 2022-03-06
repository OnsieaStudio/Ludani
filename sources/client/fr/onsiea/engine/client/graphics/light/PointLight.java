package fr.onsiea.engine.client.graphics.light;

import org.joml.Vector3f;

public class PointLight
{
	private Vector3f	color;

	private Vector3f	position;

	protected float		intensity;

	private Attenuation	attenuation;

	public PointLight(Vector3f color, Vector3f position, float intensity)
	{
		this.attenuation(new Attenuation(1, 0, 0));
		this.color(color);
		this.position(position);
		this.intensity(intensity);
	}

	public PointLight(Vector3f color, Vector3f position, float intensity, Attenuation attenuation)
	{
		this(color, position, intensity);
		this.attenuation(attenuation);
	}

	public PointLight(PointLight pointLight)
	{
		this(new Vector3f(pointLight.color()), new Vector3f(pointLight.position()), pointLight.intensity(),
				pointLight.attenuation());
	}

	public final Vector3f color(float rIn, float gIn, float bIn)
	{
		this.color().set(rIn, gIn, bIn);

		return this.color();
	}

	public final Vector3f position(float xIn, float yIn, float zIn)
	{
		this.position().set(xIn, yIn, zIn);

		return this.position();
	}

	public final Vector3f color()
	{
		return this.color;
	}

	public final void color(Vector3f colorIn)
	{
		this.color = colorIn;
	}

	public final Vector3f position()
	{
		return this.position;
	}

	public final void position(Vector3f positionIn)
	{
		this.position = positionIn;
	}

	public final float intensity()
	{
		return this.intensity;
	}

	public final void intensity(float intensityIn)
	{
		this.intensity = intensityIn;
	}

	public final Attenuation attenuation()
	{
		return this.attenuation;
	}

	public final void attenuation(Attenuation attenuationIn)
	{
		this.attenuation = attenuationIn;
	}

	public static class Attenuation
	{

		private float	constant;

		private float	linear;

		private float	exponent;

		public Attenuation(float constantIn, float linearIn, float exponentIn)
		{
			this.constant	= constantIn;
			this.linear		= linearIn;
			this.exponent	= exponentIn;
		}

		public float constant()
		{
			return this.constant;
		}

		public void constant(float constantIn)
		{
			this.constant = constantIn;
		}

		public float linear()
		{
			return this.linear;
		}

		public void linear(float linearIn)
		{
			this.linear = linearIn;
		}

		public float exponent()
		{
			return this.exponent;
		}

		public void exponent(float exponentIn)
		{
			this.exponent = exponentIn;
		}
	}
}
