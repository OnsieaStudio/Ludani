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

/**
 * @author Seynax
 *
 */

public class GLFWUtils
{
	public final static String errorString(int errorIn)
	{
		switch (errorIn)
		{
			case GLFW.GLFW_NO_ERROR:
				return "No error has occured";

			case GLFW.GLFW_NOT_INITIALIZED:
				return "GLFW has not been initialized";

			case GLFW.GLFW_NO_CURRENT_CONTEXT:
				return "No context is current for this thread";

			case GLFW.GLFW_INVALID_ENUM:
				return "One of the arguments to the function was an invalid enum value";

			case GLFW.GLFW_INVALID_VALUE:
				return "One of the arguments to the function was an invalid value";

			case GLFW.GLFW_OUT_OF_MEMORY:
				return "A memory allocation failed";

			case GLFW.GLFW_API_UNAVAILABLE:
				return "GLFW could not find support for the requested API on the system";

			case GLFW.GLFW_VERSION_UNAVAILABLE:
				return "The requested OpenGL, OpenGL ES or Vulkan version is not available";

			case GLFW.GLFW_PLATFORM_ERROR:
				return "A platform-specific error occurred that does not match any of the more specific categories";

			case GLFW.GLFW_FORMAT_UNAVAILABLE:
				return "The requested format is not supported or available";

			case GLFW.GLFW_NO_WINDOW_CONTEXT:
				return "The specified window does not have an OpenGL or OpenGL ES context";
		}

		return "Unknown !";
	}

	public final static void boolHint(int hintIn, boolean valueIn)
	{
		GLFW.glfwWindowHint(hintIn, valueIn ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
	}
}