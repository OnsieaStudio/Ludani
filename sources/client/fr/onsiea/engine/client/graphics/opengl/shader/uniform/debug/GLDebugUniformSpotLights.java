package fr.onsiea.engine.client.graphics.opengl.shader.uniform.debug;

import org.joml.Matrix4f;

import fr.onsiea.engine.client.graphics.light.SpotLight;
import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderProgram;
import fr.onsiea.engine.client.graphics.shader.IShaderProgram;
import fr.onsiea.engine.client.graphics.shader.uniform.IShaderTypedUniform;
import fr.onsiea.engine.utils.function.IIFunction;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PRIVATE)
public class GLDebugUniformSpotLights implements IShaderTypedUniform<SpotLight[]>
{
	private IShaderProgram				parent;
	private String						name;
	private GLDebugUniformSpotLight[]	spotLightsUniform;

	public GLDebugUniformSpotLights(GLShaderProgram parentIn, String nameIn, int sizeIn)
	{
		this.parent(parentIn);
		this.name(nameIn);

		this.spotLightsUniform = new GLDebugUniformSpotLight[sizeIn];

		for (var i = 0; i < sizeIn; i++)
		{
			this.spotLightsUniform[i] = new GLDebugUniformSpotLight(parentIn, nameIn + "[" + i + "]");
		}
	}

	@Override
	public IShaderProgram load(SpotLight[] spotLightsIn)
	{
		if (spotLightsIn.length > this.spotLightsUniform.length)
		{
			throw new RuntimeException(
					"[ERROR] GLDebugUnifornSpotLights : max spot lights is \"" + this.spotLightsUniform.length
							+ "\", parameter spot lights length is higher with \"" + spotLightsIn.length + "\"");
		}

		System.err.println("[DEBUG-Shader] Loading \"" + spotLightsIn.length + "\" spot lights into \"" + this.name
				+ "\"[" + this.spotLightsUniform.length + "]");

		var i = 0;
		for (final var spotLightUniform : this.spotLightsUniform)
		{
			final var spotLight = spotLightsIn[i];

			if (spotLight == null)
			{
				continue;
			}

			spotLightUniform.load(spotLight);
			i++;
		}

		return this.parent();
	}

	public IShaderProgram load(SpotLight[] spotLightsIn, Matrix4f viewMatrixIn)
	{
		if (spotLightsIn.length > this.spotLightsUniform.length)
		{
			throw new RuntimeException(
					"[ERROR] GLDebugUnifornSpotLights : max spot lights is \"" + this.spotLightsUniform.length
							+ "\", parameter spot lights length is higher with \"" + spotLightsIn.length + "\"");
		}

		System.err.println("[DEBUG-Shader] Loading \"" + spotLightsIn.length + "\" spot lights into \"" + this.name
				+ "\"[" + this.spotLightsUniform.length + "]");

		var i = 0;
		for (final var spotLightUniform : this.spotLightsUniform)
		{
			final var spotLight = spotLightsIn[i];

			if (spotLight == null)
			{
				continue;
			}

			spotLightUniform.load(spotLight, viewMatrixIn);
			i++;
		}

		return this.parent();
	}

	public IShaderProgram load(IIFunction<int[]> toLoadIn)
	{
		System.err.println("[DEBUG-Shader] Loading \"" + this.name + "\"[" + this.spotLightsUniform.length
				+ "] spot lights with function");

		for (final var pointLightUniform : this.spotLightsUniform)
		{
			toLoadIn.execute(pointLightUniform.locations());
		}

		return this.parent();
	}
}