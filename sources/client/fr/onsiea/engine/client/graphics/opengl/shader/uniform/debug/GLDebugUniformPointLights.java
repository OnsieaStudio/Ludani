package fr.onsiea.engine.client.graphics.opengl.shader.uniform.debug;

import org.joml.Matrix4f;

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
public class GLDebugUniformPointLights implements IShaderTypedUniform<PointLight[]>
{
	private IShaderProgram				parent;
	private String						name;
	private GLDebugUniformPointLight[]	pointLightsUniform;

	public GLDebugUniformPointLights(GLShaderProgram parentIn, String nameIn, int sizeIn)
	{
		this.parent(parentIn);
		this.name(nameIn);

		this.pointLightsUniform = new GLDebugUniformPointLight[sizeIn];

		for (var i = 0; i < sizeIn; i++)
		{
			this.pointLightsUniform[i] = new GLDebugUniformPointLight(parentIn, nameIn + "[" + i + "]");
		}
	}

	@Override
	public IShaderProgram load(PointLight[] pointLightsIn)
	{
		if (pointLightsIn.length > this.pointLightsUniform.length)
		{
			throw new RuntimeException(
					"[ERROR] GLDebugUnifornPointLights : max point lights is \"" + this.pointLightsUniform.length
							+ "\", parameter point lights length is higher with \"" + pointLightsIn.length + "\"");
		}

		System.err.println("[DEBUG-Shader] Loading \"" + pointLightsIn.length + "\" point lights into \"" + this.name
				+ "\"[" + this.pointLightsUniform.length + "]");

		var i = 0;
		for (final var pointLightUniform : this.pointLightsUniform)
		{
			final var pointLight = pointLightsIn[i];

			if (pointLight == null)
			{
				continue;
			}

			pointLightUniform.load(pointLight);
			i++;
		}

		return this.parent();
	}

	public IShaderProgram load(PointLight[] pointLightsIn, Matrix4f viewMatrixIn)
	{
		if (pointLightsIn.length > this.pointLightsUniform.length)
		{
			throw new RuntimeException(
					"[ERROR] GLDebugUnifornPointLights : max point lights is \"" + this.pointLightsUniform.length
							+ "\", parameter point lights length is higher with \"" + pointLightsIn.length + "\"");
		}

		System.err.println("[DEBUG-Shader] Loading \"" + pointLightsIn.length + "\" point lights into \"" + this.name
				+ "\"[" + this.pointLightsUniform.length + "]");

		var i = 0;
		for (final var pointLightUniform : this.pointLightsUniform)
		{
			final var pointLight = pointLightsIn[i];

			if (pointLight == null)
			{
				continue;
			}

			pointLightUniform.load(pointLight, viewMatrixIn);
			i++;
		}

		return this.parent();
	}

	public IShaderProgram load(IIFunction<int[]> toLoadIn)
	{
		System.err.println("[DEBUG-Shader] Loading \"" + this.name + "\"[" + this.pointLightsUniform.length
				+ "] point lights with function");

		for (final var pointLightUniform : this.pointLightsUniform)
		{
			toLoadIn.execute(pointLightUniform.locations());
		}

		return this.parent();
	}
}