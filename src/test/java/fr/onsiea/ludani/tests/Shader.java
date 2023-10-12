/*
 * Copyright 2021-2023 Onsiea Studio some rights reserved.
 *
 * This file is part of Ludart Game Framework project developed by Onsiea Studio.
 * (https://github.com/OnsieaStudio/Ludart)
 *
 * Ludart is [licensed]
 * (https://github.com/OnsieaStudio/Ludart/blob/main/LICENSE) under the terms of
 * the "GNU General Public License v3.0" (GPL-3.0).
 * https://github.com/OnsieaStudio/Ludart/wiki/License#license-and-copyright
 *
 * Ludart is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3.0 of the License, or
 * (at your option) any later version.
 *
 * Ludart is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Ludart. If not, see <https://www.gnu.org/licenses/>.
 *
 * Any reproduction or alteration of this project may reference it and utilize its name and derivatives, provided it clearly states its modification status and includes a link to the original repository. Usage of all names belonging to authors, developers, and contributors remains subject to copyright.
 * in other cases prior written authorization is required for using names such as "Onsiea," "Ludart," or any names derived from authors, developers, or contributors for product endorsements or promotional purposes.
 *
 *
 * @Author : Seynax (https://github.com/seynax)
 * @Organization : Onsiea Studio (https://github.com/OnsieaStudio)
 */

package fr.onsiea.ludani.tests;

import lombok.Getter;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL32;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.util.concurrent.atomic.AtomicReference;

import static org.lwjgl.opengl.GL20.glCreateProgram;

public class Shader
{
	private final         int programId;
	private final @Getter int projectionsUniformId;
	private final @Getter int transformationsUniformId;
	private final @Getter int viewUniformId;

	public Shader()
	{
		AtomicReference<Integer> shaderVertexId   = new AtomicReference<>();
		AtomicReference<Integer> shaderFragmentId = new AtomicReference<>();

		programId = glCreateProgram();
		if (programId == 0)
		{
			System.err.println("Could not create Shader");

			System.exit(-1);
		}

		String vertexCode = """
				#version 400

				layout(location = 0) in vec3 vertex;

				uniform mat4 projections;
				uniform mat4 transformations;
				uniform mat4 view;

				out vec3 pass_FragmentColor;

				void main()
				{
					gl_Position = projections * transformations * view * vec4(vertex.x, vertex.y, vertex.z, 1.0);

				    pass_FragmentColor = vec3(round(vertex.x/5)/32 * 0.85f, 0.25f * round(vertex.y*2)/16, round(vertex.z/2)/32 * 0.75f);
				}
				""";
		shaderVertexId.set(createShader(programId, vertexCode, GL32.GL_VERTEX_SHADER, 2048));
		String fragmentCode = """
				#version 400

				in vec3 pass_FragmentColor;

				void main()
				{
				    gl_FragColor = vec4(pass_FragmentColor.x, pass_FragmentColor.y, pass_FragmentColor.z, 1.0);
				}
				""";
		shaderFragmentId.set(createShader(programId, fragmentCode, GL32.GL_FRAGMENT_SHADER, 2048));

		GL32.glLinkProgram(programId);
		if (GL32.glGetProgrami(programId, GL32.GL_LINK_STATUS) == 0)
		{
			throw new RuntimeException("Error linking Shader code: " + GL32.glGetProgramInfoLog(programId, 1024));
		}

		if (shaderVertexId.get() != 0)
		{
			GL32.glDetachShader(programId, shaderVertexId.get());
		}
		if (shaderFragmentId.get() != 0)
		{
			GL32.glDetachShader(programId, shaderFragmentId.get());
		}

		GL32.glValidateProgram(programId);
		if (GL32.glGetProgrami(programId, GL32.GL_VALIDATE_STATUS) == 0)
		{
			System.err.println("Warning validating Shader code: " + GL32.glGetProgramInfoLog(programId, 2048));
		}

		GL32.glUseProgram(programId);
		projectionsUniformId     = GL32.glGetUniformLocation(programId, "projections");
		transformationsUniformId = GL32.glGetUniformLocation(programId, "transformations");
		viewUniformId            = GL32.glGetUniformLocation(programId, "view");
		GL32.glUseProgram(0);
	}

	public static int createShader(int shaderProgramIdIn, String shaderCodeIn, int shaderTypeIn, int logSizeIn)
	{
		int shaderId = GL32.glCreateShader(shaderTypeIn);
		if (shaderId == 0)
		{
			throw new RuntimeException("Error creating shader. Type: " + shaderTypeIn);
		}

		GL32.glShaderSource(shaderId, shaderCodeIn);
		GL32.glCompileShader(shaderId);

		if (GL32.glGetShaderi(shaderId, GL32.GL_COMPILE_STATUS) == 0)
		{
			throw new RuntimeException("Error compiling Shader code: " + GL32.glGetShaderInfoLog(shaderId, logSizeIn));
		}

		GL32.glAttachShader(shaderProgramIdIn, shaderId);

		return shaderId;
	}

	public static void unbind()
	{
		GL32.glUseProgram(0);
	}

	public static void uniform(int uniformLocationIn, Matrix4f valueIn)
	{
		try (MemoryStack stack = MemoryStack.stackPush())
		{
			FloatBuffer fb = stack.mallocFloat(16);
			valueIn.get(fb);
			GL32.glUniformMatrix4fv(uniformLocationIn, false, fb);
		}
	}

	public void use()
	{
		GL32.glUseProgram(programId);
	}

	public void cleanup()
	{
		GL32.glUseProgram(0);
		if (programId != 0)
		{
			GL32.glDeleteProgram(programId);
		}
	}
}
