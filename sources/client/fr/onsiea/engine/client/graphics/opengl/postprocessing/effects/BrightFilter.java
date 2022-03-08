package fr.onsiea.engine.client.graphics.opengl.postprocessing.effects;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import fr.onsiea.engine.client.graphics.opengl.fbo.ImageRenderer;
import fr.onsiea.engine.client.graphics.opengl.shader.postprocessing.BrightFilterShader;
import fr.onsiea.engine.client.graphics.shader.IShadersManager;
import fr.onsiea.engine.client.graphics.window.IWindow;

public class BrightFilter
{
	private ImageRenderer				renderer;

	private final IShadersManager		shadersManager;
	private final BrightFilterShader	shader;

	public BrightFilter(int widthIn, int heightIn, IWindow windowIn, IShadersManager shadersManagerIn) throws Exception
	{
		this.renderer(new ImageRenderer(widthIn, heightIn, windowIn));

		this.shadersManager	= shadersManagerIn;
		this.shader			= (BrightFilterShader) shadersManagerIn.get("brightFilter");
	}

	public void render(int textureIn, IWindow windowIn)
	{
		this.shader.attach();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureIn);
		this.renderer().renderQuad(windowIn);
		this.shadersManager.detach();
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