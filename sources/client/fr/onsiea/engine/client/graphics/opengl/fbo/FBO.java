/**
* Copyright 2021 Onsiea All rights reserved.<br><br>
*
* This file is part of Onsiea Engine project. (https://github.com/Onsiea/OnsieaEngine)<br><br>
*
* Onsiea Engine is [licensed] (https://github.com/Onsiea/OnsieaEngine/blob/main/LICENSE) under the terms of the "GNU General Public Lesser License v3.0" (GPL-3.0).
* https://github.com/Onsiea/OnsieaEngine/wiki/License#license-and-copyright<br><br>
*
* Onsiea Engine is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 3.0 of the License, or
* (at your option) any later version.<br><br>
*
* Onsiea Engine is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU Lesser General Public License for more details.<br><br>
*
* You should have received a copy of the GNU Lesser General Public License
* along with Onsiea Engine.  If not, see <https://www.gnu.org/licenses/>.<br><br>
*
* Neither the name "Onsiea", "Onsiea Engine", or any derivative name or the names of its authors / contributors may be used to endorse or promote products derived from this software and even less to name another project or other work without clear and precise permissions written in advance.<br><br>
*
* @Author : Seynax (https://github.com/seynax)<br>
* @Organization : Onsiea Studio (https://github.com/Onsiea)
*/
package fr.onsiea.engine.client.graphics.opengl.fbo;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;

import fr.onsiea.engine.client.graphics.GraphicsConstants;
import fr.onsiea.engine.client.graphics.glfw.window.Window;
import fr.onsiea.engine.client.graphics.window.IWindow;

/**
 * @author Seynax
 *
 */
public class FBO
{
	public static final int	NONE				= 0;
	public static final int	DEPTH_TEXTURE		= 1;
	public static final int	DEPTH_RENDER_BUFFER	= 2;

	private int				width;
	private int				height;

	private int				frameBuffer;

	private int				colourTexture;
	private int				depthTexture;

	private int				depthBuffer;
	private int				colourBuffer;

	private boolean			multisample;

	/**
		 * Creates an FBO of a specified width and height, with the desired type of
		 * depth buffer attachment.
		 *
		 * @param widthIn
		 *            - the width of the FBO.
		 * @param heightIn
		 *            - the height of the FBO.
		 * @param depthBufferTypeIn
		 *            - an int indicating the type of depth buffer attachment that
		 *            this FBO should use.
		 */
	public FBO(int widthIn, int heightIn, int depthBufferTypeIn, IWindow windowIn) throws Exception
	{
		this.width(widthIn);
		this.height(heightIn);
		this.initialiseFrameBuffer(depthBufferTypeIn, windowIn);
		this.multisample(false);
	}

	public FBO(int widthIn, int heightIn, Window windowIn) throws Exception
	{
		this.width(widthIn);
		this.height(heightIn);
		this.multisample(false);
		this.initialiseFrameBuffer(FBO.DEPTH_TEXTURE, windowIn);
	}

	public FBO(int widthIn, int heightIn, int depthRenderBufferIn, Window windowIn, boolean multisampledIn)
			throws Exception
	{
		this.width(widthIn);
		this.height(heightIn);
		this.multisample(multisampledIn);
		this.initialiseFrameBuffer(FBO.DEPTH_TEXTURE, windowIn);
	}

	// Static methods

	public final static void unbind()
	{
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
	}

	// Methods

	public void bind()
	{
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, this.frameBuffer());
	}

	/**
	 * Binds the frame buffer, setting it as the current render target. Anything
	 * rendered after this will be rendered to this FBO, and not to the screen.
	 */
	public void start()
	{
		GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, this.frameBuffer());
		GL11.glViewport(0, 0, this.width(), this.height());
	}

	/**
	 * Binds the frame buffer, setting it as the current render target. Anything
	 * rendered after this will be rendered to this FBO, and not to the screen.
	 */
	public void start(final int xIn, final int yIn, final int widthIn, final int heightIn)
	{
		GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, this.frameBuffer());
		GL11.glViewport(xIn, yIn, widthIn, heightIn);
	}

	/**
	 * Unbinds the frame buffer, setting the default frame buffer as the current
	 * render target. Anything rendered after this will be rendered to the
	 * screen, and not this FBO.
	 */
	public void stop(IWindow windowIn)
	{
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
		GL11.glViewport(0, 0, windowIn.settings().width(), windowIn.settings().height());
	}

	/**
	 * Binds the current FBO to be read from (not used in tutorial 43).
	 */
	public void bindToRead()
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, this.frameBuffer());
		GL11.glReadBuffer(GL30.GL_COLOR_ATTACHMENT0);
	}

	public void resolveToFBO(FBO outputFBOIn, Window windowIn)
	{
		GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, outputFBOIn.frameBuffer());
		GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, this.frameBuffer());
		GL30.glBlitFramebuffer(0, 0, this.width(), this.height(), 0, 0, outputFBOIn.width(), outputFBOIn.height(),
				GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT, GL11.GL_NEAREST);
		this.stop(windowIn);
	}

	public void resolveToScreen(Window windowIn)
	{
		GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, 0);
		GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, this.frameBuffer());
		GL11.glDrawBuffer(GL11.GL_BACK);
		GL30.glBlitFramebuffer(0, 0, this.width(), this.height(), 0, 0, windowIn.settings().width(),
				windowIn.settings().height(), GL11.GL_COLOR_BUFFER_BIT, GL11.GL_NEAREST);
		this.stop(windowIn);
	}

	/**
	 * Creates the FBO along with a colour buffer texture attachment, and
	 * possibly a depth buffer.
	 *
	 * @param typeIn
	 *            - the type of depth buffer attachment to be attached to the
	 *            FBO.
	 * @throws Exception
	 */
	private void initialiseFrameBuffer(int typeIn, IWindow windowIn) throws Exception
	{
		this.createFrameBuffer();
		if (this.multisample())
		{
			this.createMultisampleColourAttachment();
		}
		else
		{
			this.createTextureAttachment();
		}

		if (typeIn == FBO.DEPTH_RENDER_BUFFER)
		{
			this.createDepthBufferAttachment();
		}
		else if (typeIn == FBO.DEPTH_TEXTURE)
		{
			this.createDepthTextureAttachment();
		}

		if (GL30.glCheckFramebufferStatus(GL30.GL_FRAMEBUFFER) != GL30.GL_FRAMEBUFFER_COMPLETE)
		{
			throw new Exception("[ERROR] Failed to create framebuffer !");
		}
		if (GraphicsConstants.DEBUG)
		{
			System.out.println("[INFO] Successful creation of the framebuffer !");
		}

		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);

		this.stop(windowIn);
	}

	/**
	 * Creates a new frame buffer object and sets the buffer to which drawing
	 * will occur - colour attachment 0. This is the attachment where the colour
	 * buffer texture is.
	 *
	 */
	private void createFrameBuffer()
	{
		this.frameBuffer(GL30.glGenFramebuffers());
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, this.frameBuffer());
		GL11.glDrawBuffer(GL30.GL_COLOR_ATTACHMENT0);
	}

	/**
	 * Creates a texture and sets it as the colour buffer attachment for this
	 * FBO.
	 */
	private void createTextureAttachment()
	{
		this.colourTexture(GL11.glGenTextures());
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.colourTexture());
		GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
		/**
		* GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, widthIn, heightIn, 0, GL11.GL_RGBA,
		* 		GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
		**/
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, this.width(), this.height(), 0, GL11.GL_RGBA,
				GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL11.GL_TEXTURE_2D,
				this.colourTexture(), 0);
	}

	private void createMultisampleColourAttachment()
	{
		this.colourBuffer(GL30.glGenRenderbuffers());
		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, this.colourBuffer());
		GL30.glRenderbufferStorageMultisample(GL30.GL_RENDERBUFFER, 4, GL11.GL_RGBA8, this.width(), this.height());
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL30.GL_RENDERBUFFER,
				this.depthBuffer());
	}

	/**
	 * Adds a depth buffer to the FBO in the form of a texture, which can later
	 * be sampled.
	 */
	private void createDepthTextureAttachment()
	{
		this.depthTexture(GL11.glGenTextures());
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.depthTexture());
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL14.GL_DEPTH_COMPONENT24, this.width(), this.height(), 0,
				GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, (ByteBuffer) null);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL11.GL_TEXTURE_2D,
				this.depthTexture(), 0);
	}

	/**
	 * Adds a depth buffer to the FBO in the form of a render buffer. This can't
	 * be used for sampling in the shaders.
	 */
	private void createDepthBufferAttachment()
	{
		this.depthBuffer(GL30.glGenRenderbuffers());
		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, this.depthBuffer());
		if (!this.multisample())
		{
			GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL14.GL_DEPTH_COMPONENT24, this.width(), this.height());
		}
		else
		{
			GL30.glRenderbufferStorageMultisample(GL30.GL_RENDERBUFFER, 4, GL14.GL_DEPTH_COMPONENT24, this.width(),
					this.height());
		}
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_RENDERBUFFER,
				this.depthBuffer());
	}

	/**
	 * It is necessary to attach the frame buffer beforehand.
	**/
	public void clear(Window windowIn)
	{
		// GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

		this.stop(windowIn);
	}

	/**
	 * Deletes the frame buffer and its attachments when the game closes.
	 */
	public void cleanup()
	{
		GL30.glDeleteFramebuffers(this.frameBuffer());
		GL11.glDeleteTextures(this.colourTexture());
		GL11.glDeleteTextures(this.depthTexture());
		GL30.glDeleteRenderbuffers(this.depthBuffer());
		GL30.glDeleteRenderbuffers(this.colourBuffer());
	}

	private final int frameBuffer()
	{
		return this.frameBuffer;
	}

	private final void frameBuffer(int frameBufferIn)
	{
		this.frameBuffer = frameBufferIn;
	}

	private final int depthBuffer()
	{
		return this.depthBuffer;
	}

	private final void depthBuffer(int depthBufferIn)
	{
		this.depthBuffer = depthBufferIn;
	}

	private final int colourBuffer()
	{
		return this.colourBuffer;
	}

	public final void colourBuffer(int colourBufferIn)
	{
		this.colourBuffer = colourBufferIn;
	}

	private final int width()
	{
		return this.width;
	}

	private final void width(int widthIn)
	{
		this.width = widthIn;
	}

	private final int height()
	{
		return this.height;
	}

	private final void height(int heightIn)
	{
		this.height = heightIn;
	}

	public final int colourTexture()
	{
		return this.colourTexture;
	}

	private final void colourTexture(int colourTextureIn)
	{
		this.colourTexture = colourTextureIn;
	}

	public final int depthTexture()
	{
		return this.depthTexture;
	}

	private final void depthTexture(int depthTextureIn)
	{
		this.depthTexture = depthTextureIn;
	}

	public final boolean multisample()
	{
		return this.multisample;
	}

	private final void multisample(boolean multisampleIn)
	{
		this.multisample = multisampleIn;
	}
}
