package fr.onsiea.engine.client.graphics.opengl.postprocessing.effects;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import fr.onsiea.engine.client.graphics.opengl.fbo.ImageRenderer;
import fr.onsiea.engine.client.graphics.opengl.shaders.postprocessing.ContrastShader;
import fr.onsiea.engine.client.graphics.shader.IShadersManager;
import fr.onsiea.engine.client.graphics.window.IWindow;

public class ContrastChanger
{
	private ImageRenderer			imageRenderer;

	private final IShadersManager	shadersManager;
	private final ContrastShader	shader;

	public ContrastChanger(IShadersManager shadersManagerIn) throws Exception
	{
		this.imageRenderer(new ImageRenderer());

		this.shadersManager	= shadersManagerIn;
		this.shader			= (ContrastShader) this.shadersManager.get("contrastChanger");
	}

	public void render(int textureIn, IWindow windowIn, float contrastIn)
	{
		this.shader.attach();
		//this.shader.colourTexture().load(textureIn);
		this.shader.contrast().load(contrastIn);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureIn);
		this.imageRenderer().renderQuad(windowIn);
		this.shadersManager.detach();
	}

	public void cleanup()
	{
		this.imageRenderer().cleanup();
	}

	private final ImageRenderer imageRenderer()
	{
		return this.imageRenderer;
	}

	private final void imageRenderer(ImageRenderer imageRendererIn)
	{
		this.imageRenderer = imageRendererIn;
	}
}
