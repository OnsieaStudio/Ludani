package fr.onsiea.engine.client.graphics.opengl.shaders.postprocessing;

import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderProgram;
import fr.onsiea.engine.client.graphics.shader.uniform.IShaderTypedUniform;
import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PUBLIC)
public class HorizontalBlurShader extends GLShaderProgram
{
	private static final String					VERTEX_FILE		= "resources/shaders/postprocessing/blur/horizontalBlurVertex.glsl";
	private static final String					FRAGMENT_FILE	= "resources/shaders/postprocessing/blur/blurFragment.glsl";

	private final IShaderTypedUniform<Float>	uniformTargetWidth;
	private final IShaderTypedUniform<float[]>	uniformValues;

	public HorizontalBlurShader() throws Exception
	{
		super("horizontalBlur", HorizontalBlurShader.VERTEX_FILE, HorizontalBlurShader.FRAGMENT_FILE, "position");

		this.uniformTargetWidth	= this.floatUniform("targetWidth");
		this.uniformValues		= this.floatArrayUniform("values");
	}
}