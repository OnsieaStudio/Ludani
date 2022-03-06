package fr.onsiea.engine.client.graphics.opengl.shader;

import fr.onsiea.engine.client.graphics.opengl.shader.uniform.UniformFloat;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.UniformInt;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.UniformMatrix4f;

/**
 * Sets up the shader program for the rendering the lens flare. It gets the
 * uniforms of the 3 uniform variables, links the "in_position" variable to
 * attribute 0 of the VAO, and connects the sampler uniform to texture unit 0.
 *
 * @author Karl
 *
 */
public class FlareShader extends Shader
{

	private final static String	VERTEX_SHADER	= "resources/shaders/lensflare/flareVertex.glsl";
	private final static String	FRAGMENT_SHADER	= "resources/shaders/lensflare/flareFragment.glsl";

	private UniformFloat		uniformBrightness;
	private UniformMatrix4f		uniformTransformations;
	private UniformInt			uniformFlareTexture;

	public FlareShader() throws Exception
	{
		super(FlareShader.VERTEX_SHADER, FlareShader.FRAGMENT_SHADER, "position");

		this.uniformBrightness(this.floatUniform("brightness"));
		this.uniformTransformationss(this.matrix4fUniform("transformations"));
		this.uniformFlareTexture(this.intUniform("flareTexture"));

		this.connectTextureUnits();
	}

	private void connectTextureUnits()
	{
		super.start();
		this.uniformFlareTexture().load(0);
		super.stop();
	}

	public final UniformFloat uniformBrightness()
	{
		return this.uniformBrightness;
	}

	private final void uniformBrightness(UniformFloat uniformBrightnessIn)
	{
		this.uniformBrightness = uniformBrightnessIn;
	}

	public final UniformMatrix4f uniformTransformations()
	{
		return this.uniformTransformations;
	}

	private final void uniformTransformationss(UniformMatrix4f uniformTransformationsIn)
	{
		this.uniformTransformations = uniformTransformationsIn;
	}

	public final UniformInt uniformFlareTexture()
	{
		return this.uniformFlareTexture;
	}

	private final void uniformFlareTexture(UniformInt uniformFlareTextureIn)
	{
		this.uniformFlareTexture = uniformFlareTextureIn;
	}
}