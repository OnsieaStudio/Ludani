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

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

import fr.onsiea.engine.client.graphics.glfw.GLFWUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Seynax
 *
 */

@AllArgsConstructor(access = AccessLevel.PRIVATE, staticName = "of")
@EqualsAndHashCode
@ToString
@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE, buildMethodName = "create")
public class WindowSettings
{
	private String			title;
	private int				width;
	private int				height;
	private int				refreshRate;

	private WindowShowType	windowShowType;

	private boolean			mustBeSynchronized;
	private boolean			mustBeResizable;
	private boolean			mustBeDecorated;
	private boolean			mustBeFocused;
	private boolean			mustIconify;
	private boolean			mustFloating;
	private boolean			mustMaximized;
	private boolean			mustCenterCursor;
	private boolean			hasTransparentFramebuffer;
	private boolean			mustFocusOnShow;
	private boolean			mustScaleToMonitor;
	private boolean			mustAntialiasing;
	private boolean			mustAuxBuffers;
	private boolean			mustStereo;
	private boolean			mustSRGBCapable;
	private boolean			mustDoubleBuffering;

	private WindowSettings()
	{
	}

	final void hints()
	{
		GLFWUtils.boolHint(GLFW.GLFW_FOCUSED, this.mustBeFocused());
		GLFWUtils.boolHint(GLFW.GLFW_AUTO_ICONIFY, this.mustIconify());
		GLFWUtils.boolHint(GLFW.GLFW_FLOATING, this.mustFloating());
		GLFWUtils.boolHint(GLFW.GLFW_MAXIMIZED, this.mustMaximized());
		GLFWUtils.boolHint(GLFW.GLFW_CENTER_CURSOR, this.mustCenterCursor());
		GLFWUtils.boolHint(GLFW.GLFW_TRANSPARENT_FRAMEBUFFER, this.hasTransparentFramebuffer());
		GLFWUtils.boolHint(GLFW.GLFW_FOCUS_ON_SHOW, this.mustFocusOnShow());
		GLFWUtils.boolHint(GLFW.GLFW_SCALE_TO_MONITOR, this.mustScaleToMonitor());
		GLFWUtils.boolHint(GLFW.GLFW_AUX_BUFFERS, this.mustAuxBuffers());
		GLFWUtils.boolHint(GLFW.GLFW_STEREO, this.mustStereo());
		GLFWUtils.boolHint(GLFW.GLFW_SRGB_CAPABLE, this.mustSRGBCapable());
		//GLFWUtils.boolHint(GLFW.GLFW_DOUBLEBUFFER, this.mustDoubleBuffering());

		if (this.mustAntialiasing())
		{
			GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, 8);
		}
	}

	final void defaultFramebufferHints()
	{
		//GLFW.glfwWindowHint(GLFW.GLFW_RED_BITS, modeIn.redBits());
		//GLFW.glfwWindowHint(GLFW.GLFW_GREEN_BITS, modeIn.greenBits());
		//GLFW.glfwWindowHint(GLFW.GLFW_BLUE_BITS, modeIn.blueBits());
		//GLFW.glfwWindowHint(GLFW.GLFW_ALPHA_BITS, 0);
		//GLFW.glfwWindowHint(GLFW.GLFW_DEPTH_BITS, 0);
		//GLFW.glfwWindowHint(GLFW.GLFW_STENCIL_BITS, 0);
		//GLFW.glfwWindowHint(GLFW.GLFW_ACCUM_RED_BITS, 0);
		//GLFW.glfwWindowHint(GLFW.GLFW_ACCUM_GREEN_BITS, 0);
		//GLFW.glfwWindowHint(GLFW.GLFW_ACCUM_BLUE_BITS, 0);
		//GLFW.glfwWindowHint(GLFW.GLFW_ACCUM_ALPHA_BITS, 0);

		if (this.refreshRate() > 0)
		{
			GLFW.glfwWindowHint(GLFW.GLFW_REFRESH_RATE, this.refreshRate());
		}
	}

	// Work in progress
	final void framebufferHints()
	{
		this.defaultFramebufferHints();
	}

	final void framebufferHints(GLFWVidMode modeIn)
	{
		GLFW.glfwWindowHint(GLFW.GLFW_RED_BITS, modeIn.redBits());
		GLFW.glfwWindowHint(GLFW.GLFW_GREEN_BITS, modeIn.greenBits());
		GLFW.glfwWindowHint(GLFW.GLFW_BLUE_BITS, modeIn.blueBits());
		GLFW.glfwWindowHint(GLFW.GLFW_REFRESH_RATE, modeIn.refreshRate());
	}

	private static class WindowSettingsBuilder
	{
	}

	public final static class Builder extends WindowSettingsBuilder
	{
		public static WindowSettings of(String titleIn, int widthIn, int heightIn) throws Exception
		{
			return new Builder().set(titleIn, widthIn, heightIn, -1, WindowShowType.WINDOWED).build();
		}

		public static WindowSettings of(String titleIn, int widthIn, int heightIn, int refreshRateIn,
				WindowShowType windowShowTypeIn) throws Exception
		{
			return new Builder().set(titleIn, widthIn, heightIn, refreshRateIn, windowShowTypeIn).build();
		}

		public static Builder base(String titleIn, int widthIn, int heightIn)
		{
			return new Builder().set(titleIn, widthIn, heightIn, -1, WindowShowType.WINDOWED);
		}

		public static Builder base(String titleIn, int widthIn, int heightIn, int refreshRateIn,
				WindowShowType windowShowTypeIn)
		{
			return new Builder().set(titleIn, widthIn, heightIn, refreshRateIn, windowShowTypeIn);
		}

		private Builder()
		{
			super.mustBeDecorated = true;
		}

		public Builder set(String titleIn, int widthIn, int heightIn)
		{
			super.title		= titleIn;
			super.width		= widthIn;
			super.height	= heightIn;

			return this;
		}

		public Builder set(String titleIn, int widthIn, int heightIn, int refreshRateIn,
				WindowShowType windowShowTypeIn)
		{
			super.title				= titleIn;
			super.width				= widthIn;
			super.height			= heightIn;
			super.refreshRate		= refreshRateIn;
			super.windowShowType	= windowShowTypeIn;

			return this;
		}

		public WindowSettings build() throws Exception
		{
			if (super.title == null)
			{
				throw new Exception("[ERREUR] WindowSettings Builder FAILED : title is undefined !");
			}
			if (super.title.isBlank() || super.title.contentEquals("\\s+"))
			{
				System.err.println("[WARNING] WindowSettings Builder : title is empty or blank !");
			}

			if (super.width == 0)
			{
				throw new Exception("[ERREUR] WindowSettings Builder FAILED : width is undefined !");
			}
			if (super.width < 0)
			{
				throw new Exception("[ERREUR] WindowSettings Builder FAILED : width has negative value !");
			}

			if (super.height == 0)
			{
				throw new Exception("[ERREUR] WindowSettings Builder FAILED : height is undefined !");
			}
			if (super.height < 0)
			{
				throw new Exception("[ERREUR] WindowSettings Builder FAILED : height has negative value !");
			}

			if (super.refreshRate == 0)
			{
				throw new Exception("[ERREUR] WindowSettings Builder FAILED : refreshRate is undefined !");
			}
			// If refreshRate has negative value, no refresh rate limit is applied

			if (super.windowShowType == null)
			{
				throw new Exception("[ERREUR] WindowSettings Builder FAILED : show type is undefined !");
			}

			return super.create();
		}
	}
}