package fr.onsiea.engine.client.graphics.opengl.postprocessing.effects;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import fr.onsiea.engine.client.graphics.opengl.fbo.ImageRenderer;
import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderManager;
import fr.onsiea.engine.client.graphics.opengl.shader.Shader;
import fr.onsiea.engine.client.graphics.window.IWindow;

public class ContrastChanger
{
	private ImageRenderer imageRenderer;

	public ContrastChanger() throws Exception
	{
		this.imageRenderer(new ImageRenderer());
	}

	public void render(GLShaderManager shaderManagerIn, int textureIn, IWindow windowIn)
	{
		shaderManagerIn.contrast().start();
		shaderManagerIn.contrast().uniformColourTexture().load(0);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureIn);
		this.imageRenderer().renderQuad(windowIn);
		Shader.stop();
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
