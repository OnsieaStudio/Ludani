package fr.onsiea.engine.client.graphics.opengl.shader.postprocessing;

import fr.onsiea.engine.client.graphics.opengl.shader.Shader;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.UniformFloat;

public class HorizontalBlurShader extends Shader
{
	private static final String	VERTEX_FILE		= "resources/shaders/postprocessing/blur/horizontalBlurVertex.glsl";
	private static final String	FRAGMENT_FILE	= "resources/shaders/postprocessing/blur/blurFragment.glsl";

	private UniformFloat		uniformTargetWidth;

	public HorizontalBlurShader() throws Exception
	{
		super(HorizontalBlurShader.VERTEX_FILE, HorizontalBlurShader.FRAGMENT_FILE, "position");

		this.uniformTargetWidth(this.floatUniform("targetWidth"));
	}

	public final UniformFloat uniformTargetWidth()
	{
		return this.uniformTargetWidth;
	}

	private final void uniformTargetWidth(UniformFloat uniformTargetWidthIn)
	{
		this.uniformTargetWidth = uniformTargetWidthIn;
	}
}