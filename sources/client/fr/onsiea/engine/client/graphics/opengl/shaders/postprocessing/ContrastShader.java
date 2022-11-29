package fr.onsiea.engine.client.graphics.opengl.shaders.postprocessing;

import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderProgram;
import fr.onsiea.engine.client.graphics.shader.uniform.IShaderTypedUniform;
import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PUBLIC)
public class ContrastShader extends GLShaderProgram
{
	private static final String					VERTEX_FILE		= "resources/shaders/postProcessing/contrast/contrastChangerVertex.glsl";
	private static final String					FRAGMENT_FILE	= "resources/shaders/postProcessing/contrast/contrastChangerFragment.glsl";

	private final IShaderTypedUniform<Float>	contrast;

	public ContrastShader() throws Exception
	{
		super("contrast", ContrastShader.VERTEX_FILE, ContrastShader.FRAGMENT_FILE, "position");

		this.contrast = this.floatUniform("contrast");
	}
}