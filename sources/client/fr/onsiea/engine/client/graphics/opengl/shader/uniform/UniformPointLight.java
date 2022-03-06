package fr.onsiea.engine.client.graphics.opengl.shader.uniform;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import fr.onsiea.engine.client.graphics.light.PointLight;
import fr.onsiea.engine.client.graphics.opengl.shader.Shader;

public class UniformPointLight
{
	/**
	 * Return components locations in int array (int[])
	 * @return
	 */
	public final static int[] create(Shader shaderIn, String nameIn)
	{
		final var locations = new int[6];

		locations[0]	= shaderIn.uniformLocation(nameIn + ".colour");
		locations[1]	= shaderIn.uniformLocation(nameIn + ".position");
		locations[2]	= shaderIn.uniformLocation(nameIn + ".intensity");
		locations[3]	= shaderIn.uniformLocation(nameIn + ".att.constant");
		locations[4]	= shaderIn.uniformLocation(nameIn + ".att.linear");
		locations[5]	= shaderIn.uniformLocation(nameIn + ".att.exponent");

		return locations;
	}

	/**
	 * Return components locations in int array (int[])
	 * @return
	 */
	public final static int[] create(final int[] locationsIn, Shader shaderIn, String nameIn)
	{
		locationsIn[0]	= shaderIn.uniformLocation(nameIn + ".colour");
		locationsIn[1]	= shaderIn.uniformLocation(nameIn + ".position");
		locationsIn[2]	= shaderIn.uniformLocation(nameIn + ".intensity");
		locationsIn[3]	= shaderIn.uniformLocation(nameIn + ".att.constant");
		locationsIn[4]	= shaderIn.uniformLocation(nameIn + ".att.linear");
		locationsIn[5]	= shaderIn.uniformLocation(nameIn + ".att.exponent");

		return locationsIn;
	}

	private final static void base(int[] locationsIn, PointLight pointLightIn)
	{
		UniformVector3f.load(locationsIn[0], pointLightIn.color());
		UniformFloat.load(locationsIn[2], pointLightIn.intensity());
		final var att = pointLightIn.attenuation();
		UniformFloat.load(locationsIn[3], att.constant());
		UniformFloat.load(locationsIn[4], att.linear());
		UniformFloat.load(locationsIn[5], att.exponent());
	}

	public final static void load(int[] locationsIn, PointLight pointLightIn, Matrix4f viewMatrixIn)
	{
		final var	aux	= new Vector4f(pointLightIn.position(), 1.0f).mul(viewMatrixIn);
		final var	vec	= new Vector3f();
		vec.x	= aux.x();
		vec.y	= aux.y();
		vec.z	= aux.z();
		UniformVector3f.load(locationsIn[1], vec);

		UniformPointLight.base(locationsIn, pointLightIn);
	}

	public final static void load(int[] locationsIn, PointLight pointLightIn)
	{
		UniformVector3f.load(locationsIn[1], pointLightIn.position());

		UniformPointLight.base(locationsIn, pointLightIn);
	}

	private Shader	parent;
	private int[]	locations;

	public UniformPointLight(Shader parentIn, String nameIn)
	{
		this.parent(parentIn);

		this.locations(UniformPointLight.create(parentIn, nameIn));
	}

	public Shader load(PointLight pointLightIn)
	{
		UniformPointLight.load(this.locations(), pointLightIn);

		return this.parent();
	}

	public Shader load(PointLight pointLightIn, Matrix4f viewMatrixIn)
	{
		UniformPointLight.load(this.locations(), pointLightIn, viewMatrixIn);

		return this.parent();
	}

	private final Shader parent()
	{
		return this.parent;
	}

	private final void parent(Shader parentIn)
	{
		this.parent = parentIn;
	}

	private final int[] locations()
	{
		return this.locations;
	}

	private final void locations(int[] locationsIn)
	{
		this.locations = locationsIn;
	}
}