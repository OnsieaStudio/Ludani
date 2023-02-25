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

import org.joml.Vector4f;

import fr.onsiea.engine.client.graphics.render.EnumRenderAPI;
import fr.onsiea.engine.client.resources.IResourcesPath;
import fr.onsiea.engine.client.resources.ResourcesRootPath;
import lombok.Getter;

/**
 * @author Seynax
 *
 */

@Getter
public class GraphicsConstants
{
	public final static boolean			DEBUG					= false;
	public static final boolean			SHADER_UNIFORM_DEBUG	= false;

	public final static int				DEFAULT_WIDTH			= 3440;											// 120 * 16 = 1920
	public final static int				DEFAULT_HEIGHT			= 1440;											// 120 * 9 = 1080
	public final static int				ASPECT_X				= 21;
	public final static int				ASPECT_Y				= 9;

	public final static int				DEFAULT_REFRESH_RATE	= 175;
	public final static String			DEFAULT_WINDOW_TITLE	= "Onsiea";

	public final static IResourcesPath	TEXTURES				= new ResourcesRootPath("resources\\textures");
	public final static IResourcesPath	SHADERS					= new ResourcesRootPath("resources\\shaders");
	public final static IResourcesPath	FONTS					= new ResourcesRootPath("resources\\fonts");

	/**
	 * Render
	 */

	public final static float			FOV						= 110.0f;
	public final static float			ZNEAR					= 0.1f;
	public final static float			ZFAR					= 1000.0f;

	public final static EnumRenderAPI	RENDER_API				= EnumRenderAPI.OPENGL;

	public static final boolean			CULL_FACE				= false;
	public static final boolean			SHOW_TRIANGLES			= false;

	/**
	 * Many parameters of creative inventory hud.
	 */
	public final static class CreativeInventory
	{
		public final static Vector4f	SCROLLBAR_CURSOR_DISABLED_COLOR				= new Vector4f(0.5f, 0.5f, 0.5f,
				1.0f);
		public final static float		PADDING										= 4.0f;
		public final static float		MIDDLED_PADDING								= CreativeInventory.PADDING / 2.0f;
		public final static float		SLOT_SCALE_MULTIPLICATOR					= 8.0f;
		public final static int			SLOTS_COLUMNS								= 14;
		public final static int			SLOTS_LINES									= 7;
		public final static float		PER_LINES									= CreativeInventory.SLOTS_COLUMNS;
		public final static float		PER_MIDDLE_LINES							= CreativeInventory.SLOTS_COLUMNS
				* CreativeInventory.SLOTS_LINES / 2.0f;
		public final static float		PER_PAGE									= CreativeInventory.SLOTS_COLUMNS
				* CreativeInventory.SLOTS_LINES;
		public final static float		CURRENT_SCROLLING_LENGTH					= CreativeInventory.PER_MIDDLE_LINES;
		public final static boolean		DEBUG_SCROLLBAR								= false;
		public final static boolean		SMOOTH_SCROLLBAR							= false;
		// Only Needed Slots (ONS)
		public final static boolean		ONLY_NEEDED_SLOTS							= false;
		// Auto Slot Centralizer System (ASCS)
		public final static boolean		AUTO_SLOT_CENTRALIZER_SYSTEM				= false;
		public final static boolean		AUTO_SLOT_CENTRALIZER_SYSTEM_WITHOUT_ONS	= false;

	}
}