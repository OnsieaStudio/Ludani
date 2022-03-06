package fr.onsiea.engine.client.graphics.opengl.shader.uniform;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL20;

import fr.onsiea.engine.client.graphics.light.DirectionalLight;
import fr.onsiea.engine.client.graphics.opengl.shader.Shader;

public class UniformDirectionalLight
{
	public final static int[] create(Shader shaderIn, String nameIn)
	{
		final var locations = new int[3];

		locations[0]	= shaderIn.uniformLocation(nameIn + ".colour");
		locations[1]	= shaderIn.uniformLocation(nameIn + ".direction");
		locations[2]	= shaderIn.uniformLocation(nameIn + ".intensity");

		return locations;
	}

	private final static void base(int[] locationsIn, DirectionalLight directionalLightIn)
	{
		UniformVector3f.load(locationsIn[0], directionalLightIn.color());
		UniformFloat.load(locationsIn[2], directionalLightIn.intensity());

	}

	public final static void load(int[] locationsIn, DirectionalLight directionalLightIn)
	{
		UniformDirectionalLight.base(locationsIn, directionalLightIn);

		UniformVector3f.load(locationsIn[1], directionalLightIn.direction());
	}

	public final static void load(int[] locationsIn, DirectionalLight directionalLightIn, Matrix4f viewMatrixIn)
	{
		UniformDirectionalLight.base(locationsIn, directionalLightIn);

		final var	aux	= new Vector4f(directionalLightIn.direction(), 0.0f).mul(viewMatrixIn);
		final var	vec	= new Vector3f();
		vec.set(aux.x(), aux.y(), aux.z());
		GL20.glUniform3f(locationsIn[1], vec.x(), vec.y(), vec.z());
	}

	private Shader	parent;
	private int[]	locations;

	public UniformDirectionalLight(Shader parentIn, String nameIn)
	{
		this.parent(parentIn);

		this.locations(UniformDirectionalLight.create(parentIn, nameIn));
	}

	public Shader load(DirectionalLight directionalLightIn)
	{
		UniformDirectionalLight.load(this.locations(), directionalLightIn);

		return this.parent();
	}

	public Shader load(DirectionalLight directionalLightIn, Matrix4f viewMatrixIn)
	{
		UniformDirectionalLight.load(this.locations(), directionalLightIn, viewMatrixIn);

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