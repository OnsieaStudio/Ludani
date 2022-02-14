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
package fr.onsiea.engine.client.graphics;

/**
 * @author Seynax
 *
 */

public class GraphicsConstants
{
	private final static boolean	DEBUG					= true;
	private final static int		DEFAULT_WIDTH			= 1920;
	private final static int		DEFAULT_HEIGHT			= 1080;

	private final static int		DEFAULT_REFRESH_RATE	= 60;
	private final static String		DEFAULT_WINDOW_TITLE	= "Onsiea";

	/**
	 * Render
	 */

	private final static float		FOV						= 70.0f;
	private final static float		ZNEAR					= 0.1f;
	private final static float		ZFAR					= 1000.0f;

	public final static int defaultRefreshRate()
	{
		return GraphicsConstants.DEFAULT_REFRESH_RATE;
	}

	public final static String defaultWindowTitle()
	{
		return GraphicsConstants.DEFAULT_WINDOW_TITLE;
	}

	public final static boolean isDebug()
	{
		return GraphicsConstants.DEBUG;
	}

	/**public final static EnumRenderAPI renderAPI()
	{
		return GraphicsConstants.renderAPI;
	}**/

	public final static float fov()
	{
		return GraphicsConstants.FOV;
	}

	public final static float zNear()
	{
		return GraphicsConstants.ZNEAR;
	}

	public final static float zFar()
	{
		return GraphicsConstants.ZFAR;
	}

	public static final boolean debug()
	{
		return GraphicsConstants.DEBUG;
	}

	public static final int defaultWidth()
	{
		return GraphicsConstants.DEFAULT_WIDTH;
	}

	public static final int defaultHeight()
	{
		return GraphicsConstants.DEFAULT_HEIGHT;
	}
}