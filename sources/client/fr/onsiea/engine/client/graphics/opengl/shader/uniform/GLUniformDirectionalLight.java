package fr.onsiea.engine.client.graphics.opengl.shader.uniform;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL20;

import fr.onsiea.engine.client.graphics.light.DirectionalLight;
import fr.onsiea.engine.client.graphics.opengl.shader.Shader;
import fr.onsiea.engine.client.graphics.shader.IShaderProgram;
import fr.onsiea.engine.client.graphics.shader.IShaderUniform;

public class GLUniformDirectionalLight implements IShaderUniform<DirectionalLight>
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
		GLUniformVector3f.load(locationsIn[0], directionalLightIn.color());
		GLUniformFloat.load(locationsIn[2], directionalLightIn.intensity());
	}

	public final static void load(int[] locationsIn, DirectionalLight directionalLightIn)
	{
		GLUniformDirectionalLight.base(locationsIn, directionalLightIn);

		GLUniformVector3f.load(locationsIn[1], directionalLightIn.direction());
	}

	public final static void load(int[] locationsIn, DirectionalLight directionalLightIn, Matrix4f viewMatrixIn)
	{
		GLUniformDirectionalLight.base(locationsIn, directionalLightIn);

		final var	aux	= new Vector4f(directionalLightIn.direction(), 0.0f).mul(viewMatrixIn);
		final var	vec	= new Vector3f();
		vec.set(aux.x(), aux.y(), aux.z());
		GL20.glUniform3f(locationsIn[1], vec.x(), vec.y(), vec.z());
	}

	private IShaderProgram	parent;
	private int[]			locations;

	public GLUniformDirectionalLight(Shader parentIn, String nameIn)
	{
		this.parent(parentIn);

		this.locations(GLUniformDirectionalLight.create(parentIn, nameIn));
	}

	@Override
	public IShaderProgram load(DirectionalLight directionalLightIn)
	{
		GLUniformDirectionalLight.load(this.locations(), directionalLightIn);

		return this.parent();
	}

	public IShaderProgram load(DirectionalLight directionalLightIn, Matrix4f viewMatrixIn)
	{
		GLUniformDirectionalLight.load(this.locations(), directionalLightIn, viewMatrixIn);

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