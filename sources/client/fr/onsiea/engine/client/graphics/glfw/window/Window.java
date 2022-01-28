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
package fr.onsiea.engine.client.graphics.glfw.window;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import fr.onsiea.engine.client.graphics.GraphicsConstants;
import fr.onsiea.engine.client.graphics.glfw.GLFWManager;
import fr.onsiea.engine.client.graphics.glfw.GLFWUtils;
import fr.onsiea.engine.client.graphics.glfw.monitor.Monitors;
import fr.onsiea.engine.client.graphics.render.IRenderContext;
import fr.onsiea.engine.client.graphics.window.IWindow;
import fr.onsiea.engine.client.graphics.window.context.IWindowContext;
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
@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PRIVATE)
public class Window implements IWindow
{
	public final static Window of(long[] pointerIn, GLFWManager.GLFWState stateIn, WindowSettings settingsIn,
			IRenderContext contextIn, IWindowContext windowContextIn) throws IllegalStateException, Exception
	{
		return Window.of(pointerIn, stateIn, Monitors.of(stateIn), settingsIn, contextIn, windowContextIn);
	}

	public final static Window of(long[] pointerIn, GLFWManager.GLFWState stateIn, Monitors monitorsIn,
			WindowSettings settingsIn, IRenderContext renderContextIn, IWindowContext windowContextIn)
			throws IllegalStateException, Exception
	{
		if (!stateIn.initialized())
		{
			throw new IllegalStateException("[CRITICAL_ERROR] GLFW Library isn't initialized !");
		}

		if (monitorsIn == null)
		{
			throw new Exception(
					"[ERROR] Failed to create window instance, instance pointing to monitor manager is null !");
		}

		if (settingsIn == null)
		{
			throw new Exception("[ERROR] Failed to create window instance, instance pointing to settings is null !");
		}

		if (renderContextIn == null)
		{
			throw new Exception(
					"[ERROR] Failed to create window instance, instance pointing to render context is null !");
		}

		if (windowContextIn == null)
		{
			throw new Exception(
					"[ERROR] Failed to create window instance, instance pointing to window context is null !");
		}

		return new Window(monitorsIn, settingsIn, windowContextIn).initialization(pointerIn, renderContextIn);
	}

	private @Getter(value = AccessLevel.PROTECTED) long			handle;
	private WindowSettings										settings;
	private Monitors											monitors;
	private @Getter(value = AccessLevel.PRIVATE) IWindowContext	windowContext;

	protected Window(Monitors monitorsIn, WindowSettings settingsIn, IWindowContext windowContextIn)
	{
		this.monitors(monitorsIn);
		this.settings(settingsIn);
		this.windowContext(windowContextIn);
	}

	private final Window initialization(long[] pointerIn, IRenderContext contextIn)
	{
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);

		GLFW.glfwDefaultWindowHints();
		this.settings().hints();

		final var handle = this.createWindow();
		if (handle == MemoryUtil.NULL)
		{
			throw new RuntimeException("Failed to create the GLFW window");
		}
		if (pointerIn.length > 0)
		{
			pointerIn[0] = handle;
		}

		GLFW.glfwSetKeyCallback(this.handle(), (window, key, scancode, action, mods) ->
		{
			if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE)
			{
				GLFW.glfwSetWindowShouldClose(window, true);
			}
		});

		try (var stack = MemoryStack.stackPush())
		{
			final var	pWidth	= stack.mallocInt(1);
			final var	pHeight	= stack.mallocInt(1);

			GLFW.glfwGetWindowSize(this.handle(), pWidth, pHeight);

			final var vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

			GLFW.glfwSetWindowPos(this.handle(), (vidmode.width() - pWidth.get(0)) / 2,
					(vidmode.height() - pHeight.get(0)) / 2);
		}

		this.windowContext().associate(this.handle(), this);

		if (this.settings().mustBeSynchronized())
		{
			GLFW.glfwSwapInterval(1);
		}
		else
		{
			GLFW.glfwSwapInterval(0);
		}

		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_TRUE);
		GLFW.glfwShowWindow(this.handle());

		return this;
	}

	/**
	 * @implNote This method creates the window according to the selected profile, (windowed, fullscreen, windowed_fullscreen, windowed_wide).<br><br>
	 * - Should we decorate the window? Or answer this question based on the parameters? (we usually disable decoration for full screen and its derivatives, because isn't visible)<br><br>
	 *
	 * - Should we use the screen resolution [width and height] of the monitor, parameters or through a default value? (we usually use the monitor resolution for full screen mode and windowed fullscreen)<br><br>
	 *
	 * - Should we associate the window with a monitor? (we usually associate the monitor for full screen mode and full screen window)<br><br>
	 *
	 * @return window pointer handle
	 */
	private long createWindow()
	{
		GLFWUtils.boolHint(GLFW.GLFW_DECORATED,
				WindowShowType.State.SETTINGS.equals(this.settings().windowShowType().decoratedState())
						? this.settings().mustBeDecorated()
						: WindowShowType.State.TRUE.equals(this.settings().windowShowType().decoratedState()));

		final var	monitor	= GLFW.glfwGetPrimaryMonitor();
		final var	mode	= GLFW.glfwGetVideoMode(monitor);
		int			width, height;

		switch (this.settings().windowShowType().framebufferSettingsSource())
		{
			case DEFAULT:
				this.settings().defaultFramebufferHints();
				break;

			case SETTINGS:
				this.settings().framebufferHints();
				break;

			case MONITOR:
				this.settings().framebufferHints(mode);
				break;
		}

		width	= switch (this.settings().windowShowType().widthSource())
				{
					case DEFAULT -> GraphicsConstants.defaultWidth();
					case SETTINGS -> this.settings().width();
					case MONITOR -> mode.width();
				};

		height	= switch (this.settings().windowShowType().heightSource())
				{
					case DEFAULT -> GraphicsConstants.defaultHeight();
					case SETTINGS -> this.settings().height();
					case MONITOR -> mode.height();
				};

		this.handle(GLFW.glfwCreateWindow(width, height, this.settings().title(),
				this.settings().windowShowType().useMonitor() ? monitor : MemoryUtil.NULL, MemoryUtil.NULL));

		return this.handle();
	}

	public void centeredCursor(boolean mustBeCenteredIn)
	{
		GLFWUtils.boolHint(GLFW.GLFW_CENTER_CURSOR, mustBeCenteredIn);
	}

	@Override
	public boolean shouldClose()
	{
		return GLFW.glfwWindowShouldClose(this.handle());
	}

	@Override
	public final Window swapBuffers()
	{
		GLFW.glfwSwapBuffers(this.handle());

		return this;
	}

	@Override
	public void cleanup()
	{
		// Free the window callbacks and destroy the window
		Callbacks.glfwFreeCallbacks(this.handle());

		GLFW.glfwDestroyWindow(this.handle());
	}
}