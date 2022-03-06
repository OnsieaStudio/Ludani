package fr.onsiea.engine.client.graphics.opengl.postprocessing.effects;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import fr.onsiea.engine.client.graphics.opengl.fbo.ImageRenderer;
import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderManager;
import fr.onsiea.engine.client.graphics.opengl.shader.Shader;
import fr.onsiea.engine.client.graphics.window.IWindow;

public class VerticalBlur
{
	private ImageRenderer renderer;

	public VerticalBlur(int targetFboWidthIn, int targetFboHeightIn, IWindow windowIn, GLShaderManager shaderManagerIn)
			throws Exception
	{
		this.renderer(new ImageRenderer(targetFboWidthIn, targetFboHeightIn, windowIn));
		shaderManagerIn.verticalBlur().start();
		shaderManagerIn.verticalBlur().uniformTargetHeight().load(targetFboHeightIn);
		Shader.stop();
	}

	public void render(int textureIn, IWindow windowIn, GLShaderManager shaderManagerIn)
	{
		shaderManagerIn.verticalBlur().start();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureIn);
		this.renderer().renderQuad(windowIn);
		Shader.stop();
	}

	public int outputTexture()
	{
		return this.renderer().colourTexture();
	}

	public void cleanup()
	{
		this.renderer().cleanup();
	}

	private final ImageRenderer renderer()
	{
		return this.renderer;
	}

	private final void renderer(ImageRenderer rendererIn)
	{
		this.renderer = rendererIn;
	}
}