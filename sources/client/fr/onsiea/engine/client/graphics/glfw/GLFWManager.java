/**
*	Copyright 2021 Onsiea All rights reserved.
*
*	This file is part of Onsiea Engine. (https://github.com/Onsiea/OnsieaEngine)
*
*	Unless noted in license (https://github.com/Onsiea/OnsieaEngine/blob/main/LICENSE.md) notice file (https://github.com/Onsiea/OnsieaEngine/blob/main/LICENSE_NOTICE.md), Onsiea engine and all parts herein is licensed under the terms of the LGPL-3.0 (https://www.gnu.org/licenses/lgpl-3.0.html)  found here https://www.gnu.org/licenses/lgpl-3.0.html and copied below the license file.
*
*	Onsiea Engine is libre software: you can redistribute it and/or modify
*	it under the terms of the GNU Lesser General Public License as published by
*	the Free Software Foundation, either version 3.0 of the License, or
*	(at your option) any later version.
*
*	Onsiea Engine is distributed in the hope that it will be useful,
*	but WITHOUT ANY WARRANTY; without even the implied warranty of
*	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*	GNU Lesser General Public License for more details.
*
*	You should have received a copy of the GNU Lesser General Public License
*	along with Onsiea Engine.  If not, see <https://www.gnu.org/licenses/> <https://www.gnu.org/licenses/lgpl-3.0.html>.
*
*	Neither the name "Onsiea", "Onsiea Engine", or any derivative name or the names of its authors / contributors may be used to endorse or promote products derived from this software and even less to name another project or other work without clear and precise permissions written in advance.
*
*	(more details on https://github.com/Onsiea/OnsieaEngine/wiki/License)
*
*	@author Seynax
*/
package fr.onsiea.engine.client.graphics.glfw;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

import fr.onsiea.engine.client.graphics.GraphicsConstants;
import fr.onsiea.engine.client.graphics.glfw.callback.ErrorCallback;
import fr.onsiea.engine.client.graphics.glfw.monitor.Monitors;
import fr.onsiea.engine.client.graphics.glfw.window.Window;
import fr.onsiea.engine.client.graphics.glfw.window.WindowSettings;
import fr.onsiea.engine.client.graphics.window.context.IWindowContext;
import fr.onsiea.engine.client.input.InputManager;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Seynax
 *
 */

@EqualsAndHashCode
@ToString
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PRIVATE)
public class GLFWManager
{
	private GLFWState		state;

	private Monitors		monitors;
	private Window			window;
	private InputManager	inputManager;

	public GLFWManager()
	{
		this.state(new GLFWState());
	}

	public GLFWManager initialization(WindowSettings windowSettingsIn, IWindowContext windowContextIn) throws Exception
	{
		if (GraphicsConstants.DEBUG)
		{
			this.enableDebugging();
		}

		if (!GLFW.glfwInit())
		{
			throw new IllegalStateException("[CRITICAL_ERROR] GLFW initialization failed !");
		}

		this.state().initialized(true);
		this.state().thread(Thread.currentThread());

		this.determinVersion();

		this.monitors(Monitors.of(this.state()));
		final var pointer = new long[1];
		this.window(Window.of(pointer, this.state(), this.monitors(), windowSettingsIn, windowContextIn));
		this.inputManager(new InputManager.Builder(pointer[0]).build());

		return this;
	}

	private void determinVersion()
	{
		final var	major	= new int[1];
		final var	minor	= new int[1];
		final var	rev		= new int[1];
		GLFW.glfwGetVersion(major, minor, rev);

		this.state().version(GLFW.glfwGetVersionString(), major[0], minor[0], rev[0]);
	}

	public void enableDebugging()
	{
		if (this.state().glfwErrorCallback() == null)
		{
			this.state().glfwErrorCallback(GLFW.glfwSetErrorCallback(this.state().errorCallbackInstance()));
		}
	}

	public void enableBasicDebugging()
	{
		if (this.state().glfwErrorCallback() == null)
		{
			this.state().glfwErrorCallback(GLFWErrorCallback.createPrint(System.err).set());
		}
	}

	public void disableDebugging()
	{
		final var glfwErrorCallback = GLFW.glfwSetErrorCallback(null);

		if (glfwErrorCallback != null)
		{
			glfwErrorCallback.free();
		}

		this.state().glfwErrorCallback(null);
	}

	public void cleanup()
	{
		// this.inputManager().cleanup();
		this.window().cleanup();

		this.disableDebugging();

		// Terminate GLFW and free the error callback
		GLFW.glfwTerminate();
	}

	@EqualsAndHashCode
	@ToString
	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PRIVATE)
	public final static class GLFWState
	{
		private GLFWErrorCallback	glfwErrorCallback;
		private String				version;
		private int					versionMajor;
		private int					versionMinor;
		private int					versionRev;
		private boolean				initialized;
		private Thread				thread;

		private ErrorCallback		errorCallbackInstance;

		private GLFWState()
		{
			this.errorCallbackInstance(new ErrorCallback());
		}

		public GLFWState version(String versionIn, int majorIn, int minorIn, int revIn)
		{
			this.version(versionIn);
			this.versionMajor(majorIn);
			this.versionMinor(minorIn);
			this.versionRev(revIn);

			return this;
		}
	}
}