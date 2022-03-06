package fr.onsiea.engine.client.graphics.opengl.postprocessing.effects;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import fr.onsiea.engine.client.graphics.opengl.fbo.ImageRenderer;
import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderManager;
import fr.onsiea.engine.client.graphics.opengl.shader.Shader;
import fr.onsiea.engine.client.graphics.window.IWindow;

public class CombineFilter
{

	private ImageRenderer renderer;

	public CombineFilter(GLShaderManager shaderManagerIn) throws Exception
	{
		this.renderer(new ImageRenderer());
	}

	public void render(GLShaderManager shaderManagerIn, IWindow windowIn, int colourTextureIn, int... texturesIn)
	{
		shaderManagerIn.combine().start();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, colourTextureIn);
		var i = 1;
		for (final int texture : texturesIn)
		{
			GL13.glActiveTexture(GL13.GL_TEXTURE0 + i);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
			i++;
		}

		this.renderer().renderQuad(windowIn);
		Shader.stop();
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