package fr.onsiea.engine.client.graphics.opengl.shader.uniform;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import fr.onsiea.engine.client.graphics.light.SpotLight;
import fr.onsiea.engine.client.graphics.opengl.shader.Shader;

public class UniformSpotLight
{
	public final static int[] create(Shader shaderIn, String nameIn)
	{
		final var locations = new int[8];

		UniformPointLight.create(locations, shaderIn, nameIn + ".pl");

		locations[6]	= shaderIn.uniformLocation(nameIn + ".cutoff");
		locations[7]	= shaderIn.uniformLocation(nameIn + ".conedir");

		return locations;
	}

	private final static void base(int[] locationsIn, SpotLight spotLightIn)
	{
		UniformPointLight.load(locationsIn, spotLightIn.pointLight());
		UniformFloat.load(locationsIn[6], spotLightIn.cutOff());
	}

	public final static void load(int[] locationsIn, SpotLight spotLightIn)
	{
		UniformSpotLight.base(locationsIn, spotLightIn);

		UniformVector3f.load(locationsIn[7], spotLightIn.coneDirection());
	}

	public final static void load(int[] locationsIn, SpotLight spotLightIn, Matrix4f viewMatrixIn)
	{
		UniformSpotLight.base(locationsIn, spotLightIn);

		final var	aux	= new Vector4f(spotLightIn.coneDirection(), 0.0f).mul(viewMatrixIn);
		final var	vec	= new Vector3f();
		vec.set(aux.x(), aux.y(), aux.z());
		UniformVector3f.load(locationsIn[7], vec);
	}

	private Shader	parent;
	private int[]	locations;

	public UniformSpotLight(Shader parentIn, String nameIn)
	{
		this.parent(parentIn);

		this.locations(UniformSpotLight.create(parentIn, nameIn));
	}

	public Shader load(SpotLight spotLightIn)
	{
		UniformSpotLight.load(this.locations(), spotLightIn);

		return this.parent();
	}

	public Shader load(SpotLight spotLightIn, Matrix4f viewMatrixIn)
	{
		UniformSpotLight.load(this.locations(), spotLightIn, viewMatrixIn);

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