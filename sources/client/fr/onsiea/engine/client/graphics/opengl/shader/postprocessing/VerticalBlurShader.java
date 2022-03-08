package fr.onsiea.engine.client.graphics.opengl.shader.postprocessing;

import fr.onsiea.engine.client.graphics.opengl.shader.Shader;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.UniformFloat;

public class VerticalBlurShader extends Shader
{
	private static final String	VERTEX_FILE		= "resources/shaders/postprocessing/blur/verticalBlurVertex.glsl";
	private static final String	FRAGMENT_FILE	= "resources/shaders/postprocessing/blur/blurFragment.glsl";

	private UniformFloat		uniformTargetHeight;

	public VerticalBlurShader() throws Exception
	{
		super(VerticalBlurShader.VERTEX_FILE, VerticalBlurShader.FRAGMENT_FILE, "position");

		this.uniformTargetHeight(this.floatUniform("targetHeight"));
	}

	public final UniformFloat uniformTargetHeight()
	{
		return this.uniformTargetHeight;
	}

	private final void uniformTargetHeight(UniformFloat uniformTargetHeightIn)
	{
		this.uniformTargetHeight = uniformTargetHeightIn;
	}
}