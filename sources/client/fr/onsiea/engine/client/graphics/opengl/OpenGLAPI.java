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

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL43;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.opengl.GLDebugMessageCallback;
import org.lwjgl.opengl.KHRDebug;
import org.lwjgl.system.Callback;

import fr.onsiea.engine.client.graphics.GraphicsConstants;
import fr.onsiea.engine.client.graphics.render.IRenderAPIContext;

/**
 * @author Seynax
 *
 */
public class OpenGLAPI implements IRenderAPIContext
{
	public final static OpenGLAPI create()
	{
		return new OpenGLAPI(OpenGLInitializer.initialisationOrGet());
	}

	private static boolean	cullingBackFace		= false;
	private static boolean	inWireframe			= false;
	private static boolean	isAlphaBlending		= false;
	private static boolean	additiveBlending	= false;
	private static boolean	antialiasing		= false;
	private static boolean	depthTesting		= false;

	public static void antialias(boolean enable)
	{
		if (enable && !OpenGLAPI.antialiasing())
		{
			GL11.glEnable(GL13.GL_MULTISAMPLE);
			OpenGLAPI.antialiasing(true);
		}
		else if (!enable && OpenGLAPI.antialiasing())
		{
			GL11.glDisable(GL13.GL_MULTISAMPLE);
			OpenGLAPI.antialiasing(false);
		}
	}

	public static void enableAlphaBlending()
	{
		if (!OpenGLAPI.isAlphaBlending())
		{
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			OpenGLAPI.isAlphaBlending(true);
			OpenGLAPI.additiveBlending(false);
		}
	}

	public static void enableAdditiveBlending()
	{
		if (!OpenGLAPI.additiveBlending())
		{
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			OpenGLAPI.additiveBlending(true);
			OpenGLAPI.isAlphaBlending(false);
		}
	}

	public static void disableBlending()
	{
		if (OpenGLAPI.isAlphaBlending() || OpenGLAPI.additiveBlending())
		{
			GL11.glDisable(GL11.GL_BLEND);
			OpenGLAPI.isAlphaBlending(false);
			OpenGLAPI.additiveBlending(false);
		}
	}

	public static void enableDepthTesting(boolean enable)
	{
		if (enable && !OpenGLAPI.depthTesting())
		{
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			OpenGLAPI.depthTesting(true);
		}
		else if (!enable && OpenGLAPI.depthTesting())
		{
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			OpenGLAPI.depthTesting(false);
		}
	}

	public static void cullBackFaces(boolean cull)
	{
		if (cull && !OpenGLAPI.cullingBackFace())
		{
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glCullFace(GL11.GL_BACK);
			OpenGLAPI.cullingBackFace(true);
		}
		else if (!cull && OpenGLAPI.cullingBackFace())
		{
			GL11.glDisable(GL11.GL_CULL_FACE);
			OpenGLAPI.cullingBackFace(false);
		}
	}

	public static void goWireframe(boolean goWireframe)
	{
		if (goWireframe && !OpenGLAPI.inWireframe())
		{
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
			OpenGLAPI.inWireframe(true);
		}
		else if (!goWireframe && OpenGLAPI.inWireframe())
		{
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
			OpenGLAPI.inWireframe(false);
		}
	}

	public static final boolean cullingBackFace()
	{
		return OpenGLAPI.cullingBackFace;
	}

	private static final void cullingBackFace(boolean cullingBackFaceIn)
	{
		OpenGLAPI.cullingBackFace = cullingBackFaceIn;
	}

	public static final boolean inWireframe()
	{
		return OpenGLAPI.inWireframe;
	}

	private static final void inWireframe(boolean inWireframeIn)
	{
		OpenGLAPI.inWireframe = inWireframeIn;
	}

	public static final boolean isAlphaBlending()
	{
		return OpenGLAPI.isAlphaBlending;
	}

	private static final void isAlphaBlending(boolean isAlphaBlendingIn)
	{
		OpenGLAPI.isAlphaBlending = isAlphaBlendingIn;
	}

	public static final boolean additiveBlending()
	{
		return OpenGLAPI.additiveBlending;
	}

	private static final void additiveBlending(boolean additiveBlendingIn)
	{
		OpenGLAPI.additiveBlending = additiveBlendingIn;
	}

	public static final boolean antialiasing()
	{
		return OpenGLAPI.antialiasing;
	}

	private static final void antialiasing(boolean antialiasingIn)
	{
		OpenGLAPI.antialiasing = antialiasingIn;
	}

	public static final boolean depthTesting()
	{
		return OpenGLAPI.depthTesting;
	}

	private static final void depthTesting(boolean depthTestingIn)
	{
		OpenGLAPI.depthTesting = depthTestingIn;
	}

	private GLCapabilities	capabilities;
	private Callback		debugProc;
	private OpenGLDebug		openGLDebug;

	/**private GLMeshManager	meshManager;
	private GLShaderManager	shaderManager;
	private TexturesManager	texturesManager;
	private OBJLoader		objLoader;**/

	private OpenGLAPI(GLCapabilities capabilitiesIn)
	{
		this.capabilities(capabilitiesIn);
		this.initialization();
	}

	@Override
	public void initialization()
	{
		if (GraphicsConstants.isDebug())
		{
			this.enableDebugging();
		}
		else
		{
			this.disableDebugging();
		}

		/**this.meshManager(new GLMeshManager());
		this.shaderManager(new GLShaderManager());
		this.objLoader(new OBJLoader(this.meshManager()));
		Texture.initialization(capabilitiesIn);
		this.texturesManager(new TexturesManager(capabilitiesIn));**/
	}

	public void initialization(Matrix4f projectionMatrixIn) throws Exception
	{
		OpenGLAPI.initialize3D();

		// this.shaderManager().initialization(projectionMatrixIn);
	}

	public final static void clearColor(float rIn, float gIn, float bIn, float aIn)
	{
		GL11.glClearColor(rIn, bIn, gIn, aIn);
	}

	public final static void clear()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
	}

	public final static void restoreState()
	{
		OpenGLAPI.initialize3D();

		// Accept fragment if it closer to the camera than the former on

		//GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		// glEnable(GL_DEPTH_TEST);
		//         if (opts.showTriangles) {
		// glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		// }

	}

	public final static void initialize3D()
	{
		// Enables

		GL11.glEnable(GL11.GL_DEPTH_TEST);
		// GL11.glDepthFunc(GL11.GL_LESS);
		if (GraphicsConstants.isCullFace())
		{
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glCullFace(GL11.GL_BACK);
			// GL11.glFrontFace(GL11.GL_CCW);
		}
		GL11.glEnable(GL11.GL_STENCIL_TEST);
		GL11.glDepthMask(true);
	}

	public final static void initialize2D()
	{
		// Disables

		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_STENCIL_TEST);

		// Modes

		GL11.glDepthMask(false);
	}

	public final static void initialize2DWithStencil()
	{
		// Disables

		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_STENCIL_TEST);

		// Modes

		GL11.glDepthMask(false);
	}

	public final static void enableTransparency()
	{
		// Enables

		GL11.glEnable(GL11.GL_BLEND);

		// Modes

		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	public final static void disableTransparency()
	{
		// Enables

		GL11.glDisable(GL11.GL_BLEND);
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
	}

	public void disableDebugging()
	{
		// GL

		GL11.glDisable(GL43.GL_DEBUG_OUTPUT);
		GL11.glDisable(GL43.GL_DEBUG_OUTPUT_SYNCHRONOUS);

		// Callback

		if (this.debugProc() != null)
		{
			this.debugProc().free();
			this.debugProc(null);
		}
		this.openGLDebug(null);
		KHRDebug.glDebugMessageCallback(null, 0);

		// GLFW

		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_DEBUG_CONTEXT, GLFW.GLFW_FALSE);
	}

	/**public GLMesh loadMesh(String fileNameIn) throws Exception
	{
		return this.objLoader().loadMesh(fileNameIn);
	}**/

	@Override
	public void cleanup()
	{
		OpenGLAPI.restoreState();

		/**this.meshManager().cleanup();
		OpenGLScreenshot.cleanup();
		this.shaderManager().cleanup();**/
		this.disableDebugging();

		GL.destroy();
		GL.setCapabilities(null);
	}

	@SuppressWarnings("unused")
	private final GLCapabilities capabilities()
	{
		return this.capabilities;
	}

	private final void capabilities(GLCapabilities capabilitiesIn)
	{
		this.capabilities = capabilitiesIn;
	}

	private final Callback debugProc()
	{
		return this.debugProc;
	}

	private final void debugProc(Callback debugProcIn)
	{
		this.debugProc = debugProcIn;
	}

	private final OpenGLDebug openGLDebug()
	{
		return this.openGLDebug;
	}

	private final void openGLDebug(OpenGLDebug openGLDebugIn)
	{
		this.openGLDebug = openGLDebugIn;
	}

	/**public final GLMeshManager meshManager()
	{
		return this.meshManager;
	}
	
	private final void meshManager(GLMeshManager meshManagerIn)
	{
		this.meshManager = meshManagerIn;
	}
	
	public final GLShaderManager shaderManager()
	{
		return this.shaderManager;
	}
	
	private final void shaderManager(GLShaderManager shaderManagerIn)
	{
		this.shaderManager = shaderManagerIn;
	}
	
	public final TexturesManager texturesManager()
	{
		return this.texturesManager;
	}
	
	private final void texturesManager(TexturesManager texturesManagerIn)
	{
		this.texturesManager = texturesManagerIn;
	}
	
	private final OBJLoader objLoader()
	{
		return this.objLoader;
	}
	
	private final void objLoader(OBJLoader objLoaderIn)
	{
		this.objLoader = objLoaderIn;
	}**/
}