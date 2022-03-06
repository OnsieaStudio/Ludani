package fr.onsiea.engine.client.graphics.opengl.fbo;

import org.lwjgl.opengl.GL11;

import fr.onsiea.engine.client.graphics.glfw.window.Window;

public class ImageRenderer
{
	private FBO fbo;

	protected ImageRenderer(int widthIn, int heightIn, Window windowIn) throws Exception
	{
		this.fbo(new FBO(widthIn, heightIn, FBO.NONE, windowIn));
	}

	protected void renderQuad(Window windowIn)
	{
		if (this.fbo() != null)
		{
			this.fbo().start();
		}
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);

		if (this.fbo() != null)
		{
			this.fbo().stop(windowIn);
		}
	}

	protected int colourTexture()
	{
		return this.fbo().colourTexture();
	}

	protected void cleanup()
	{
		if (this.fbo() != null)
		{
			this.fbo().cleanup();
		}
	}

	private final FBO fbo()
	{
		return this.fbo;
	}

	private final void fbo(FBO fboIn)
	{
		this.fbo = fboIn;
	}
}
