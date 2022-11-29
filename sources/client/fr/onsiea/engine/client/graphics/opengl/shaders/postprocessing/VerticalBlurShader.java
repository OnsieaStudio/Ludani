package fr.onsiea.engine.client.graphics.opengl.shaders.postprocessing;

import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderProgram;
import fr.onsiea.engine.client.graphics.shader.uniform.IShaderTypedUniform;
import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PUBLIC)
public class VerticalBlurShader extends GLShaderProgram
{
	private static final String					VERTEX_FILE		= "resources/shaders/postprocessing/blur/verticalBlurVertex.glsl";
	private static final String					FRAGMENT_FILE	= "resources/shaders/postprocessing/blur/blurFragment.glsl";

	private final IShaderTypedUniform<Float>	uniformTargetHeight;
	private final IShaderTypedUniform<float[]>	uniformValues;

	public VerticalBlurShader() throws Exception
	{
		super("verticalBlur", VerticalBlurShader.VERTEX_FILE, VerticalBlurShader.FRAGMENT_FILE, "position");

		this.uniformTargetHeight	= this.floatUniform("targetHeight");
		this.uniformValues			= this.floatArrayUniform("values");
	}
}