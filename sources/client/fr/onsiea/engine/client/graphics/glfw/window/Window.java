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

import java.io.IOException;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import fr.onsiea.engine.client.graphics.GraphicsConstants;
import fr.onsiea.engine.client.graphics.glfw.GLFWManager;
import fr.onsiea.engine.client.graphics.glfw.GLFWUtils;
import fr.onsiea.engine.client.graphics.glfw.monitor.Monitors;
import fr.onsiea.engine.client.graphics.texture.ITextureData;
import fr.onsiea.engine.client.graphics.texture.data.TextureData;
import fr.onsiea.engine.client.graphics.window.IWindow;
import fr.onsiea.engine.client.graphics.window.context.IWindowContext;
import fr.onsiea.engine.utils.maths.MathInstances;
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
	public final static Window of(final long[] pointerIn, final GLFWManager.GLFWState stateIn,
			final WindowSettings settingsIn, final IWindowContext windowContextIn)
			throws IllegalStateException, Exception
	{
		return Window.of(pointerIn, stateIn, Monitors.of(stateIn), settingsIn, windowContextIn);
	}

	public final static Window of(final long[] pointerIn, final GLFWManager.GLFWState stateIn,
			final Monitors monitorsIn, final WindowSettings settingsIn, final IWindowContext windowContextIn)
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

		if (windowContextIn == null)
		{
			throw new Exception(
					"[ERROR] Failed to create window instance, instance pointing to window context is null !");
		}

		return new Window(monitorsIn, settingsIn, windowContextIn, pointerIn);
	}

	private static GLFWImage.Buffer								icons;
	private static GLFWImage									cursor;
	private static long											cursorHandle;

	private @Getter(value = AccessLevel.PUBLIC) long			handle;
	private WindowSettings										settings;
	private Monitors											monitors;
	private @Getter(value = AccessLevel.PRIVATE) IWindowContext	windowContext;
	private boolean												isResized;
	private @Getter int											effectiveHeight;
	private @Getter int											effectiveWidth;

	public Window(final Monitors monitorsIn, final WindowSettings settingsIn, final IWindowContext windowContextIn)
			throws IllegalStateException, Exception
	{
		this.monitors(monitorsIn);
		this.settings(settingsIn);
		this.windowContext(windowContextIn);

		this.initialization();
	}

	public Window(final Monitors monitorsIn, final WindowSettings settingsIn, final IWindowContext windowContextIn,
			final long[] pointerIn) throws IllegalStateException, Exception
	{
		this.monitors(monitorsIn);
		this.settings(settingsIn);
		this.windowContext(windowContextIn);

		MathInstances.initialization(this);
		this.initialization();

		if (pointerIn.length > 0)
		{
			pointerIn[0] = this.handle();
		}
	}

	private final void initialization() throws IllegalStateException, Exception
	{
		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);

		this.settings().hints();

		final var handle = this.createWindow();
		if (handle == MemoryUtil.NULL)
		{
			throw new RuntimeException("Failed to create the GLFW window");
		}

		GLFW.glfwSetKeyCallback(this.handle(), (window, key, scancode, action, mods) -> {
			if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE)
			{
				GLFW.glfwSetWindowShouldClose(window, true);
			}
		});

		if (this.settings.mustMaximized())
		{
			GLFW.glfwMaximizeWindow(handle);
		}

		final var	vidmode	= GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		var			width	= 0;
		var			height	= 0;
		try (var stack = MemoryStack.stackPush())
		{
			final var	pWidth	= stack.mallocInt(1);
			final var	pHeight	= stack.mallocInt(1);

			GLFW.glfwGetWindowSize(this.handle(), pWidth, pHeight);
			width					= pWidth.get();
			height					= pHeight.get();
			this.effectiveWidth		= width;
			this.effectiveHeight	= height;
			System.out.println("Width : ");
			System.out.println("	Vidmode : " + vidmode.width());
			System.out.println("	Settings : " + this.settings().width());
			System.out.println("	pWidth : " + width);
			System.out.println("	contentWidth : " + this.monitors().primary().contentWidth());
			System.out.println("	width : " + this.monitors().primary().width());
			System.out.println("	workArea width : " + this.monitors().primary().workAera().width());
			System.out.println("x : ");
			System.out.println("	monitor : " + this.monitors().primary().x());
			System.out.println("	workAera : " + this.monitors().primary().workAera().x());

			System.out.println("height : ");
			System.out.println("	Vidmode : " + vidmode.height());
			System.out.println("	Settings : " + this.settings().height());
			System.out.println("	pHeight : " + height);
			System.out.println("	contentHeight : " + this.monitors().primary().contentHeight());
			System.out.println("	height : " + this.monitors().primary().height());
			System.out.println("	workArea height : " + this.monitors().primary().workAera().height());
			System.out.println("y : ");
			System.out.println("	monitor : " + this.monitors().primary().y());
			System.out.println("	workAera : " + this.monitors().primary().workAera().y());
		}

		// GLFW.glfwSetWindowPos(this.handle(), (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);
		//GLFW.glfwSetWindowPos(handle, (this.monitors().primary().workAera().width() - width) / 2,
		//		(this.monitors().primary().workAera().height() - height) / 2);

		this.windowContext().associate(this.handle, this);

		if (this.settings().mustBeSynchronized())
		{
			GLFW.glfwSwapInterval(this.settings().sync());
		}
		else
		{
			GLFW.glfwSwapInterval(0);
		}

		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_TRUE);
		GLFW.glfwShowWindow(this.handle());

		GLFW.glfwSetInputMode(this.handle(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
		// GLFW_CURSOR_DISABLED GLFW_CURSOR_HIDDEN GLFW_CURSOR_NORMAL

		//GLFWcursor* cursor = glfwCreateStandardCursor(GLFW_HRESIZE_CURSOR);

		//glfwSetInputMode (window, GLFW_STICKY_MOUSE_BUTTONS , GLFW_TRUE );

		// Create the cursor object1

		/**Window.cursor = GLFWImage.malloc();
		final var buffer = TextureData.load("resources\\textures\\cursor.png").buffer();
		Window.cursor.set(32, 32, buffer);
		Window.cursorHandle = GLFW.glfwCreateCursor(Window.cursor, 16, 16 * 16 / 9);**/

		/**if (Window.cursorHandle == MemoryUtil.NULL)
		{
			throw new RuntimeException("Error creating cursor");
		}**/

		// Set the cursor on a window
		//GLFW.glfwSetCursor(this.handle(), Window.cursorHandle);
		//MemoryUtil.memFree(buffer);
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

		if (WindowShowType.FULLSCREEN.equals(this.settings.windowShowType()))
		{
			this.effectiveWidth		= this.monitors().primary().vidMode().width();
			this.effectiveHeight	= this.monitors().primary().vidMode().height();
		}

		int width, height;

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
					case DEFAULT -> GraphicsConstants.DEFAULT_WIDTH;
					case SETTINGS -> this.settings().width();
					case MONITOR -> mode.width();
					case WORK_AREA -> this.monitors().primary().workAera().width();
				};

		height	= switch (this.settings().windowShowType().heightSource())
				{
					case DEFAULT -> GraphicsConstants.DEFAULT_HEIGHT;
					case SETTINGS -> this.settings().height();
					case MONITOR -> mode.height();
					case WORK_AREA -> this.monitors().primary().workAera().height();
				};

		this.handle(GLFW.glfwCreateWindow(width, height, this.settings().title(),
				this.settings().windowShowType().useMonitor() ? monitor : MemoryUtil.NULL, MemoryUtil.NULL));

		GLFW.glfwSetWindowAspectRatio(this.handle(), GraphicsConstants.ASPECT_X, GraphicsConstants.ASPECT_Y);

		//System.out.println(width + ", " + height);
		return this.handle();
	}

	public void icon(final String filepathIn) throws IOException
	{
		final var		image		= GLFWImage.malloc();
		ITextureData	textureData	= null;
		try
		{
			textureData = TextureData.load(filepathIn);

			image.set(textureData.width(), textureData.height(), textureData.buffer());
			Window.icons = GLFWImage.malloc(1);
			Window.icons.put(0, image);

			GLFW.glfwSetWindowIcon(this.handle(), Window.icons);

			image.free();
			MemoryUtil.memFree(textureData.buffer());
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

	public void centeredCursor(final boolean mustBeCenteredIn)
	{
		GLFWUtils.boolHint(GLFW.GLFW_CENTER_CURSOR, mustBeCenteredIn);
	}

	@Override
	public boolean shouldClose()
	{
		return GLFW.glfwWindowShouldClose(this.handle());
	}

	@Override
	public final Window pollEvents()
	{
		GLFW.glfwPollEvents();

		return this;
	}

	@Override
	public final Window swapBuffers()
	{
		GLFW.glfwSwapBuffers(this.handle());

		return this;
	}

	/**
	 * @param glfwKeyLeftControlIn
	 * @return
	 */
	@Override
	public int key(final int glfwKeyIn)
	{
		return GLFW.glfwGetKey(this.handle(), glfwKeyIn);
	}

	@Override
	public void cleanup()
	{
		if (Window.cursor != null)
		{
			Window.cursor.free();
		}

		if (Window.cursorHandle != MemoryUtil.NULL)
		{
			GLFW.glfwDestroyCursor(Window.cursorHandle);
			//GLFW.glfwSetCursor(this.handle(), 0L);
		}

		if (Window.icons != null)
		{
			Window.icons.free();
		}
		// Free the window callbacks and destroy the window
		Callbacks.glfwFreeCallbacks(this.handle());

		GLFW.glfwMakeContextCurrent(MemoryUtil.NULL);
		GLFW.glfwDestroyWindow(this.handle());
		this.pollEvents();

		this.windowContext.context().cleanup();
	}
}