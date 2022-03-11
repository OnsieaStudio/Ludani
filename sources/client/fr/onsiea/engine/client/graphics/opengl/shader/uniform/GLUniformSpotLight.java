package fr.onsiea.engine.client.graphics.opengl.shader.uniform;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import fr.onsiea.engine.client.graphics.light.SpotLight;
import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderProgram;
import fr.onsiea.engine.client.graphics.shader.IShaderProgram;
import fr.onsiea.engine.client.graphics.shader.IShaderUniform;

public class GLUniformSpotLight implements IShaderUniform<SpotLight>
{
	public final static int[] create(GLShaderProgram shaderIn, String nameIn)
	{
		final var locations = new int[8];

		GLUniformPointLight.create(locations, shaderIn, nameIn + ".pl");

		locations[6]	= shaderIn.uniformLocation(nameIn + ".cutoff");
		locations[7]	= shaderIn.uniformLocation(nameIn + ".conedir");

		return locations;
	}

	private final static void base(int[] locationsIn, SpotLight spotLightIn)
	{
		GLUniformPointLight.load(locationsIn, spotLightIn.pointLight());
		GLUniformFloat.load(locationsIn[6], spotLightIn.cutOff());
	}

	public final static void load(int[] locationsIn, SpotLight spotLightIn)
	{
		GLUniformSpotLight.base(locationsIn, spotLightIn);

		GLUniformVector3f.load(locationsIn[7], spotLightIn.coneDirection());
	}

	public final static void load(int[] locationsIn, SpotLight spotLightIn, Matrix4f viewMatrixIn)
	{
		GLUniformSpotLight.base(locationsIn, spotLightIn);

		final var	aux	= new Vector4f(spotLightIn.coneDirection(), 0.0f).mul(viewMatrixIn);
		final var	vec	= new Vector3f();
		vec.set(aux.x(), aux.y(), aux.z());
		GLUniformVector3f.load(locationsIn[7], vec);
	}

	private IShaderProgram	parent;
	private int[]			locations;

	public GLUniformSpotLight(GLShaderProgram parentIn, String nameIn)
	{
		this.parent(parentIn);

		this.locations(GLUniformSpotLight.create(parentIn, nameIn));
	}

	@Override
	public IShaderProgram load(SpotLight spotLightIn)
	{
		GLUniformSpotLight.load(this.locations(), spotLightIn);

		return this.parent();
	}

	public IShaderProgram load(SpotLight spotLightIn, Matrix4f viewMatrixIn)
	{
		GLUniformSpotLight.load(this.locations(), spotLightIn, viewMatrixIn);

		return this.parent();
	}

	private final IShaderProgram parent()
	{
		return this.parent;
	}

	private final void parent(IShaderProgram parentIn)
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