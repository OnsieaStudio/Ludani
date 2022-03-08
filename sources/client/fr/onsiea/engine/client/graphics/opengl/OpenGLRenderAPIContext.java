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
package fr.onsiea.engine.client.graphics.opengl;

import java.nio.ByteBuffer;
import java.util.Collection;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL43;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.opengl.GLDebugMessageCallback;
import org.lwjgl.opengl.KHRDebug;
import org.lwjgl.system.MemoryUtil;

import fr.onsiea.engine.client.graphics.GraphicsConstants;
import fr.onsiea.engine.client.graphics.mesh.IMeshsManager;
import fr.onsiea.engine.client.graphics.mesh.OBJLoader;
import fr.onsiea.engine.client.graphics.opengl.mesh.GLMeshManager;
import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderManager;
import fr.onsiea.engine.client.graphics.opengl.texture.GLTexture;
import fr.onsiea.engine.client.graphics.opengl.texture.GLTexturesManager;
import fr.onsiea.engine.client.graphics.opengl.utils.OpenGLUtils;
import fr.onsiea.engine.client.graphics.render.IRenderAPIContext;
import fr.onsiea.engine.client.graphics.render.IRenderAPIMethods;
import fr.onsiea.engine.client.graphics.shader.IShadersManager;
import fr.onsiea.engine.client.graphics.texture.ITexture;
import fr.onsiea.engine.client.graphics.texture.ITexturesManager;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Seynax
 *
 */
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PRIVATE)
public class OpenGLRenderAPIContext implements IRenderAPIContext, IRenderAPIMethods
{
	public final static OpenGLRenderAPIContext create() throws IllegalStateException, Exception
	{
		return new OpenGLRenderAPIContext(OpenGLInitializer.initialize());
	}

	private GLCapabilities			capabilities;
	private GLDebugMessageCallback	debugProc;
	private GLDebugMessageCallback	openGLDebug;

	private OpenGLSettings			settings;

	private ITexturesManager		texturesManager;
	private IMeshsManager			meshsManager;
	private IShadersManager			shadersManager;

	/**
	 *
	 * @param capabilitiesIn
	 * @throws Exception
	 */
	private OpenGLRenderAPIContext(GLCapabilities capabilitiesIn) throws Exception
	{
		this.capabilities(capabilitiesIn);
		this.settings(new OpenGLSettings(this));

		if (GraphicsConstants.DEBUG)
		{
			this.enableDebugging();
		}
		else
		{
			this.disableDebugging();
		}

		this.settings().user().enable("mustAnisotropyTextureFiltering").set("anisotropyTextureFilteringAmount", 4.0f);

		this.shadersManager(new GLShaderManager());
		this.texturesManager(new GLTexturesManager(this));

		this.meshsManager(new GLMeshManager(new OBJLoader()));

		OpenGLUtils.restoreState();
	}

	@Override
	public ITexture createTexture(int widthIn, int heightIn, ByteBuffer bufferIn)
	{
		return new GLTexture(widthIn, heightIn, bufferIn, this);
	}

	/**
	 * @return
	 */
	public boolean textureFilterAnisotropicIsCompatible()
	{
		return this.capabilities.GL_EXT_texture_filter_anisotropic;
	}

	public void enableDebugging()
	{
		// GLFW

		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_DEBUG_CONTEXT, GLFW.GLFW_TRUE);

		// Callback

		this.openGLDebug(new OpenGLDebug());

		// this.debugProc(GLUtil.setupDebugMessageCallback());
		this.debugProc(GLDebugMessageCallback.create(this.openGLDebug()));
		KHRDebug.glDebugMessageCallback(this.openGLDebug(), 0);

		// GL

		GL11.glEnable(GL43.GL_DEBUG_OUTPUT);
		GL11.glEnable(GL43.GL_DEBUG_OUTPUT_SYNCHRONOUS);
		GL11.glEnable(KHRDebug.GL_DEBUG_OUTPUT);
	}

	public void disableDebugging()
	{
		// GL

		GL11.glDisable(GL43.GL_DEBUG_OUTPUT);
		GL11.glDisable(GL43.GL_DEBUG_OUTPUT_SYNCHRONOUS);
		//GL11.glDisable(KHRDebug.GL_DEBUG_OUTPUT);

		// GLFW

		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_DEBUG_CONTEXT, GLFW.GLFW_FALSE);

		// Callback

		if (this.openGLDebug != null)
		{
			this.openGLDebug().free();
			this.openGLDebug(null);
			// this.debugProc().free();
			this.debugProc(null);
		}

		KHRDebug.glDebugMessageCallback(null, 0);
	}

	@Override
	public void cleanup()
	{
		OpenGLUtils.restoreState();

		this.meshsManager().cleanup();
		// OpenGLScreenshot.cleanup();
		// this.shaderManager().cleanup();
		this.disableDebugging();

		GL.destroy();
		GL.setCapabilities(null);
	}

	@Override
	public void deleteTextures(Collection<ITexture> valuesIn)
	{
		final var texturesBuffer = MemoryUtil.memAllocInt(valuesIn.size());

		for (final ITexture texture : valuesIn)
		{
			texture.detach();

			texturesBuffer.put(texture.id());

		}

		texturesBuffer.flip();

		GLTexture.deletes(texturesBuffer);

		MemoryUtil.memFree(texturesBuffer);
	}
}