package fr.onsiea.engine.client.graphics.opengl.shader.effects;

import fr.onsiea.engine.client.graphics.opengl.shader.Shader;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformFloat;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformInt;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformVector4f;

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

	private GLUniformFloat		uniformBrightness;
	private GLUniformVector4f	uniformTransformations;
	private GLUniformInt		uniformFlareTexture;

	public FlareShader() throws Exception
	{
		super(FlareShader.VERTEX_SHADER, FlareShader.FRAGMENT_SHADER, "position");

		this.uniformBrightness(this.floatUniform("brightness"));
		this.uniformTransformationss(this.vector4fUniform("transformations"));
		this.uniformFlareTexture(this.intUniform("flareTexture"));

		this.connectTextureUnits();
	}

	private void connectTextureUnits()
	{
		super.attach();
		this.uniformFlareTexture().load(0);
	}

	public final GLUniformFloat uniformBrightness()
	{
		return this.uniformBrightness;
	}

	private final void uniformBrightness(GLUniformFloat uniformBrightnessIn)
	{
		this.uniformBrightness = uniformBrightnessIn;
	}

	public final GLUniformVector4f uniformTransformations()
	{
		return this.uniformTransformations;
	}

	private final void uniformTransformationss(GLUniformVector4f uniformTransformationsIn)
	{
		this.uniformTransformations = uniformTransformationsIn;
	}

	public final GLUniformInt uniformFlareTexture()
	{
		return this.uniformFlareTexture;
	}

	private final void uniformFlareTexture(GLUniformInt uniformFlareTextureIn)
	{
		this.uniformFlareTexture = uniformFlareTextureIn;
	}
}