package fr.onsiea.engine.client.graphics.opengl.shader.postprocessing;

import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderProgram;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformFloat;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformFloatArray;
import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PUBLIC)
public class HorizontalBlurShader extends GLShaderProgram
{
	private static final String			VERTEX_FILE		= "resources/shaders/postprocessing/blur/horizontalBlurVertex.glsl";
	private static final String			FRAGMENT_FILE	= "resources/shaders/postprocessing/blur/blurFragment.glsl";

	private final GLUniformFloat		uniformTargetWidth;
	private final GLUniformFloatArray	uniformValues;

	public HorizontalBlurShader() throws Exception
	{
		super(HorizontalBlurShader.VERTEX_FILE, HorizontalBlurShader.FRAGMENT_FILE, "position");

		this.uniformTargetWidth	= this.floatUniform("targetWidth");
		this.uniformValues		= this.floatArrayUniform("values");
	}
}