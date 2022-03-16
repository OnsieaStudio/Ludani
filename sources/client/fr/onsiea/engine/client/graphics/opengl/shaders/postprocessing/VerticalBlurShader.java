package fr.onsiea.engine.client.graphics.opengl.shaders.postprocessing;

import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderProgram;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformFloat;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformFloatArray;
import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PUBLIC)
public class VerticalBlurShader extends GLShaderProgram
{
	private static final String			VERTEX_FILE		= "resources/shaders/postprocessing/blur/verticalBlurVertex.glsl";
	private static final String			FRAGMENT_FILE	= "resources/shaders/postprocessing/blur/blurFragment.glsl";

	private final GLUniformFloat		uniformTargetHeight;
	private final GLUniformFloatArray	uniformValues;

	public VerticalBlurShader() throws Exception
	{
		super(VerticalBlurShader.VERTEX_FILE, VerticalBlurShader.FRAGMENT_FILE, "position");

		this.uniformTargetHeight	= this.floatUniform("targetHeight");
		this.uniformValues			= this.floatArrayUniform("values");
	}
}