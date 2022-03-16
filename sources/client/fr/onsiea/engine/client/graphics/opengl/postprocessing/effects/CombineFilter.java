package fr.onsiea.engine.client.graphics.opengl.postprocessing.effects;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import fr.onsiea.engine.client.graphics.opengl.fbo.ImageRenderer;
import fr.onsiea.engine.client.graphics.opengl.shaders.postprocessing.CombineShader;
import fr.onsiea.engine.client.graphics.shader.IShadersManager;
import fr.onsiea.engine.client.graphics.window.IWindow;

public class CombineFilter
{
	private ImageRenderer			renderer;

	private final IShadersManager	shadersManager;
	private final CombineShader		shader;

	public CombineFilter(IShadersManager shadersManagerIn) throws Exception
	{
		this.renderer(new ImageRenderer());

		this.shadersManager	= shadersManagerIn;
		this.shader			= (CombineShader) shadersManagerIn.get("combineFilter");
	}

	public void render(IWindow windowIn, int colourTextureIn, int... texturesIn)
	{
		this.shader.attach();
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
		this.shadersManager.detach();
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