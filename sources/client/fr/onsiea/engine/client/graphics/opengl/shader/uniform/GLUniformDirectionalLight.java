package fr.onsiea.engine.client.graphics.opengl.shader.uniform;

import org.joml.Matrix4f;

import fr.onsiea.engine.client.graphics.light.DirectionalLight;
import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderProgram;
import fr.onsiea.engine.client.graphics.shader.IShaderProgram;
import fr.onsiea.engine.client.graphics.shader.uniform.IShaderTypedUniform;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PRIVATE)
public class GLUniformDirectionalLight implements IShaderTypedUniform<DirectionalLight>
{
	public final static int[] create(GLShaderProgram shaderIn, String nameIn)
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

		GLUniformPointLight.TRANSPOSE_VEC.set(GLUniformPointLight.TRANSPOSE_MAT.set(viewMatrixIn)
				.transform(GLUniformPointLight.TRANSPOSE_VEC.set(directionalLightIn.direction(), 0.0f)));

		GLUniformVector3f.load(locationsIn[1], GLUniformPointLight.TRANSPOSE_VEC.x(),
				GLUniformPointLight.TRANSPOSE_VEC.y(), GLUniformPointLight.TRANSPOSE_VEC.z());

		GLUniformVector3f.load(locationsIn[1], GLUniformPointLight.TRANSPOSE_VEC.x(),
				GLUniformPointLight.TRANSPOSE_VEC.y(), GLUniformPointLight.TRANSPOSE_VEC.z());
	}

	private IShaderProgram	parent;
	private int[]			locations;

	public GLUniformDirectionalLight(GLShaderProgram parentIn, String nameIn)
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
}