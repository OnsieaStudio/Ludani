package fr.onsiea.engine.client.graphics.light;

import org.joml.Vector3f;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class DirectionalLight
{
	private Vector3f									color;

	private Vector3f									direction;

	private float										intensity;

	private @Setter(AccessLevel.PRIVATE) OrthoCoords	orthoCoords;

	private float										shadowPosMult;

	public DirectionalLight(Vector3f colorIn, Vector3f directionIn, float intensityIn)
	{
		this.color(colorIn);
		this.direction(directionIn);
		this.intensity(intensityIn);
		this.orthoCoords(new OrthoCoords());
		this.shadowPosMult = 1;
	}

	public DirectionalLight(DirectionalLight lightIn)
	{
		this(new Vector3f(lightIn.color()), new Vector3f(lightIn.direction()), lightIn.intensity());
	}

	public DirectionalLight orthoCords(float left, float right, float bottom, float top, float near, float far)
	{
		this.orthoCoords.left	= left;
		this.orthoCoords.right	= right;
		this.orthoCoords.bottom	= bottom;
		this.orthoCoords.top	= top;
		this.orthoCoords.near	= near;
		this.orthoCoords.far	= far;

		return this;
	}

	public static class OrthoCoords
	{
		public float	left;

		public float	right;

		public float	bottom;

		public float	top;

		public float	near;

		public float	far;
	}
}