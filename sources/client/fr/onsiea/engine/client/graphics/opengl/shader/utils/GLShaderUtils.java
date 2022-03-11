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
package fr.onsiea.engine.client.graphics.opengl.shader.utils;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

/**
 * @author Seynax
 *
 */
public class GLShaderUtils
{
	public final static int compile(final String scriptIn, final int typeIn) throws Exception
	{
		final var shaderId = GL20.glCreateShader(typeIn);

		GL20.glShaderSource(shaderId, scriptIn);

		GL20.glCompileShader(shaderId);

		if (GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)
		{
			throw new Exception("[ERROR] Could not compile shader !\n" + GL20.glGetShaderInfoLog(shaderId, 4096));
		}

		return shaderId;
	}

	public final static void link(int programIdIn, int vertexIdIn, int fragmentIdIn) throws Exception
	{
		GL20.glAttachShader(programIdIn, vertexIdIn);
		GL20.glAttachShader(programIdIn, fragmentIdIn);

		GL20.glLinkProgram(programIdIn);

		if (GL20.glGetProgrami(programIdIn, GL20.GL_LINK_STATUS) == 0)
		{
			throw new Exception("Error linking Shader code: " + GL20.glGetProgramInfoLog(programIdIn, 1024));
		}

		if (vertexIdIn != 0)
		{
			GL20.glDetachShader(programIdIn, vertexIdIn);
		}
		if (fragmentIdIn != 0)
		{
			GL20.glDetachShader(programIdIn, fragmentIdIn);
		}

		GL20.glValidateProgram(programIdIn);

		if (GL20.glGetProgrami(programIdIn, GL20.GL_VALIDATE_STATUS) == 0)
		{
			System.err.println("Warning validating Shader code: " + GL20.glGetProgramInfoLog(programIdIn, 1024));
		}
	}

	public final static void detach()
	{
		GL20.glUseProgram(0);
	}
}