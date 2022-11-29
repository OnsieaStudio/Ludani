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
package fr.onsiea.engine.client.graphics.opengl.texture.utils;

import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

/**
 * @author Seynax
 *
 */
public class GLTextureUtils
{
	public final static int gen()
	{
		return GL11.glGenTextures();
	}

	public final static int[] gen(int[] texturesIn)
	{
		GL11.glGenTextures(texturesIn);

		return texturesIn;
	}

	public final static IntBuffer gen(IntBuffer texturesIn)
	{
		GL11.glGenTextures(texturesIn);

		return texturesIn;
	}

	public final static void bind(final int textureIdIn)
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureIdIn);
	}

	public final static void active(final int idIn)
	{
		GL13.glActiveTexture(idIn);
	}

	public final static void unbind()
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	public final static void delete(final int textureIdIn)
	{
		GL11.glDeleteTextures(textureIdIn);
	}

	public final static void deletes(IntBuffer texturesBufferIn)
	{
		GL11.glDeleteTextures(texturesBufferIn);
	}
}