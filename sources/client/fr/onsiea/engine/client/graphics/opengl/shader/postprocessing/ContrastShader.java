package fr.onsiea.engine.client.graphics.opengl.shader.postprocessing;

import fr.onsiea.engine.client.graphics.opengl.shader.Shader;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.UniformInt;

public class ContrastShader extends Shader
{
	private static final String	VERTEX_FILE		= "resources/shaders/postProcessing/contrastVertex.glsl";
	private static final String	FRAGMENT_FILE	= "resources/shaders/postProcessing/contrastFragment.glsl";

	private UniformInt			uniformColourTexture;

	public ContrastShader() throws Exception
	{
		super(ContrastShader.VERTEX_FILE, ContrastShader.FRAGMENT_FILE, "position");

		this.uniformColourTexture(this.intUniform("colourTexture"));
	}

	public final UniformInt uniformColourTexture()
	{
		return this.uniformColourTexture;
	}

	private final void uniformColourTexture(UniformInt uniformColourTextureIn)
	{
		this.uniformColourTexture = uniformColourTextureIn;
	}
}