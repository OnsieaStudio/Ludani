package fr.onsiea.engine.client.graphics.opengl.shaders.effects;

import org.joml.Vector4f;

import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderProgram;
import fr.onsiea.engine.client.graphics.shader.uniform.IShaderTypedUniform;
import lombok.AccessLevel;
import lombok.Getter;

/**
 * Sets up the shader program for the rendering the lens flare. It gets the
 * uniforms of the 3 uniform variables, links the "in_position" variable to
 * attribute 0 of the VAO, and connects the sampler uniform to texture unit 0.
 *
 * @author Karl
 *
 */
@Getter(AccessLevel.PUBLIC)
public class FlareShader extends GLShaderProgram
{

	private final static String					VERTEX_SHADER	= "resources/shaders/lensflare/flareVertex.glsl";
	private final static String					FRAGMENT_SHADER	= "resources/shaders/lensflare/flareFragment.glsl";

	private final IShaderTypedUniform<Float>	uniformBrightness;
	private final IShaderTypedUniform<Vector4f>	uniformTransformations;
	private final IShaderTypedUniform<Integer>	uniformFlareTexture;

	public FlareShader() throws Exception
	{
		super("lensFlare", FlareShader.VERTEX_SHADER, FlareShader.FRAGMENT_SHADER, "position");

		this.uniformBrightness		= this.floatUniform("brightness");
		this.uniformTransformations	= this.vector4fUniform("transformations");
		this.uniformFlareTexture	= this.intUniform("flareTexture");

		this.connectTextureUnits();
	}

	private void connectTextureUnits()
	{
		super.attach();
		this.uniformFlareTexture().load(0);
	}
}