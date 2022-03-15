package fr.onsiea.engine.client.graphics.opengl.shader.uniform;

import org.joml.Matrix4f;

import fr.onsiea.engine.client.graphics.light.SpotLight;
import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderProgram;
import fr.onsiea.engine.client.graphics.shader.IShaderProgram;
import fr.onsiea.engine.client.graphics.shader.uniform.IShaderTypedUniform;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PRIVATE)
public class GLUniformSpotLight implements IShaderTypedUniform<SpotLight>
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

		GLUniformPointLight.TRANSPOSE_VEC.set(GLUniformPointLight.TRANSPOSE_MAT.set(viewMatrixIn)
				.transform(GLUniformPointLight.TRANSPOSE_VEC.set(spotLightIn.coneDirection(), 0.0f)));

		GLUniformVector3f.load(locationsIn[1], GLUniformPointLight.TRANSPOSE_VEC.x(),
				GLUniformPointLight.TRANSPOSE_VEC.y(), GLUniformPointLight.TRANSPOSE_VEC.z());

		GLUniformVector3f.load(locationsIn[7], GLUniformPointLight.TRANSPOSE_VEC.x(),
				GLUniformPointLight.TRANSPOSE_VEC.y(), GLUniformPointLight.TRANSPOSE_VEC.z());
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
}