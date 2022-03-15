package fr.onsiea.engine.client.graphics.opengl.shader.uniform;

import org.joml.Matrix4f;
import org.joml.Vector4f;

import fr.onsiea.engine.client.graphics.light.PointLight;
import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderProgram;
import fr.onsiea.engine.client.graphics.shader.IShaderProgram;
import fr.onsiea.engine.client.graphics.shader.uniform.IShaderTypedUniform;
import fr.onsiea.engine.utils.function.IIFunction;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PRIVATE)
public class GLUniformPointLight implements IShaderTypedUniform<PointLight>
{
	final static Matrix4f	TRANSPOSE_MAT	= new Matrix4f();
	final static Vector4f	TRANSPOSE_VEC	= new Vector4f();

	/**
	 * Return components locations in int array (int[])
	 * @return
	 */
	public final static int[] create(GLShaderProgram shaderIn, String nameIn)
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
	public final static int[] create(final int[] locationsIn, GLShaderProgram shaderIn, String nameIn)
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
		GLUniformVector3f.load(locationsIn[0], pointLightIn.color());
		GLUniformFloat.load(locationsIn[2], pointLightIn.intensity());
		final var att = pointLightIn.attenuation();
		GLUniformFloat.load(locationsIn[3], att.constant());
		GLUniformFloat.load(locationsIn[4], att.linear());
		GLUniformFloat.load(locationsIn[5], att.exponent());
	}

	public final static void load(int[] locationsIn, PointLight pointLightIn, Matrix4f viewMatrixIn)
	{
		GLUniformPointLight.TRANSPOSE_VEC.set(GLUniformPointLight.TRANSPOSE_MAT.set(viewMatrixIn)
				.transform(GLUniformPointLight.TRANSPOSE_VEC.set(pointLightIn.position(), 1.0f)));

		GLUniformVector3f.load(locationsIn[1], GLUniformPointLight.TRANSPOSE_VEC.x(),
				GLUniformPointLight.TRANSPOSE_VEC.y(), GLUniformPointLight.TRANSPOSE_VEC.z());

		GLUniformPointLight.base(locationsIn, pointLightIn);
	}

	public final static void load(int[] locationsIn, PointLight pointLightIn)
	{
		GLUniformVector3f.load(locationsIn[1], pointLightIn.position());

		GLUniformPointLight.base(locationsIn, pointLightIn);
	}

	private IShaderProgram	parent;
	private int[]			locations;

	public GLUniformPointLight(GLShaderProgram parentIn, String nameIn)
	{
		this.parent(parentIn);

		this.locations(GLUniformPointLight.create(parentIn, nameIn));
	}

	@Override
	public IShaderProgram load(PointLight pointLightIn)
	{
		GLUniformPointLight.load(this.locations(), pointLightIn);

		return this.parent();
	}

	public IShaderProgram load(PointLight pointLightIn, Matrix4f viewMatrixIn)
	{
		GLUniformPointLight.load(this.locations(), pointLightIn, viewMatrixIn);

		return this.parent();
	}

	public IShaderProgram load(IIFunction<int[]> toLoadIn)
	{
		toLoadIn.execute(this.locations);

		return this.parent();
	}
}