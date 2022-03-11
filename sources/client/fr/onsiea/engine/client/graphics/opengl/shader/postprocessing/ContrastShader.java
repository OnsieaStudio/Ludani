package fr.onsiea.engine.client.graphics.opengl.shader.postprocessing;

import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderProgram;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformFloat;
import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PUBLIC)
public class ContrastShader extends GLShaderProgram
{
	private static final String		VERTEX_FILE		= "resources/shaders/postProcessing/contrast/contrastChangerVertex.glsl";
	private static final String		FRAGMENT_FILE	= "resources/shaders/postProcessing/contrast/contrastChangerFragment.glsl";

	private final GLUniformFloat	contrast;

	public ContrastShader() throws Exception
	{
		super(ContrastShader.VERTEX_FILE, ContrastShader.FRAGMENT_FILE, "position");

		this.contrast = this.floatUniform("contrast");
	}
}