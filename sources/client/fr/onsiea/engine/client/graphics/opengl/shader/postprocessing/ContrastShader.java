package fr.onsiea.engine.client.graphics.opengl.shader.postprocessing;

import fr.onsiea.engine.client.graphics.opengl.shader.Shader;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformInt;

public class ContrastShader extends Shader
{
	private static final String	VERTEX_FILE		= "resources/shaders/postProcessing/contrastVertex.glsl";
	private static final String	FRAGMENT_FILE	= "resources/shaders/postProcessing/contrastFragment.glsl";

	private GLUniformInt		uniformColourTexture;

	public ContrastShader() throws Exception
	{
		super(ContrastShader.VERTEX_FILE, ContrastShader.FRAGMENT_FILE, "position");

		this.uniformColourTexture(this.intUniform("colourTexture"));
	}

	public final GLUniformInt uniformColourTexture()
	{
		return this.uniformColourTexture;
	}

	private final void uniformColourTexture(GLUniformInt uniformColourTextureIn)
	{
		this.uniformColourTexture = uniformColourTextureIn;
	}
}