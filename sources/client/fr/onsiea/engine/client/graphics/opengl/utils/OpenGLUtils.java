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
package fr.onsiea.engine.client.graphics.opengl.utils;

import org.lwjgl.opengl.GL11;

/**
 * @author Seynax
 *
 */
public class OpenGLUtils
{
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
		initialize3D();

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
		/**if (GraphicsConstants.isCullFace())
		{
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glCullFace(GL11.GL_BACK);
			// GL11.glFrontFace(GL11.GL_CCW);
		}**/
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
}
