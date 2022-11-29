package fr.onsiea.engine.client.graphics.opengl.shader.uniform;

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
public class GLUniformSpotLights implements IShaderTypedUniform<SpotLight[]>
{
	private IShaderProgram			parent;
	private GLUniformSpotLight[]	spotLightsUniform;

	public GLUniformSpotLights(GLShaderProgram parentIn, String nameIn, int sizeIn)
	{
		this.parent(parentIn);

		this.spotLightsUniform = new GLUniformSpotLight[sizeIn];

		for (var i = 0; i < sizeIn; i++)
		{
			this.spotLightsUniform[i] = new GLUniformSpotLight(parentIn, nameIn + "[" + i + "]");
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
		for (final var pointLightUniform : this.spotLightsUniform)
		{
			toLoadIn.execute(pointLightUniform.locations());
		}

		return this.parent();
	}
}