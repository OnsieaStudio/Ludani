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

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL32;

import java.nio.FloatBuffer;
import java.util.Random;

public class Render
{
	private static final Random random = new Random(new Random().nextLong());

	private final int      vao;
	private final int      vbo;
	private final int      vertexCount;
	private final Shader   shader;
	private final Vector3f offsets               = new Vector3f(0.0f, 0.0f, 0.0f);
	private final Vector3f rotations             = new Vector3f(0.0f, 0.0f, 0.0f);
	private final float    scale                 = 1.0f;
	private final Matrix4f transformationsMatrix = new Matrix4f();

	public Render()
	{
		var         size       = 32;
		FloatBuffer cubeVertex = BufferUtils.createFloatBuffer((size * 2) * (size * 2) * (36 * 3));

		float[][] noises = generatePerlinNoise(generateWhiteNoise(size * 2, size * 2), 6, 0.75f, 0.024f);

		final var cubeVertexArray = new float[]{
				-1.0f, -1.0f, -1.0f, // triangle 1 : begin
				-1.0f, -1.0f, 1.0f,
				-1.0f, 1.0f, 1.0f, // triangle 1 : end
				1.0f, 1.0f, -1.0f, // triangle 2 : begin
				-1.0f, -1.0f, -1.0f,
				-1.0f, 1.0f, -1.0f, // triangle 2 : end
				1.0f, -1.0f, 1.0f,
				-1.0f, -1.0f, -1.0f,
				1.0f, -1.0f, -1.0f,
				1.0f, 1.0f, -1.0f,
				1.0f, -1.0f, -1.0f,
				-1.0f, -1.0f, -1.0f,
				-1.0f, -1.0f, -1.0f,
				-1.0f, 1.0f, 1.0f,
				-1.0f, 1.0f, -1.0f,
				1.0f, -1.0f, 1.0f,
				-1.0f, -1.0f, 1.0f,
				-1.0f, -1.0f, -1.0f,
				-1.0f, 1.0f, 1.0f,
				-1.0f, -1.0f, 1.0f,
				1.0f, -1.0f, 1.0f,
				1.0f, 1.0f, 1.0f,
				1.0f, -1.0f, -1.0f,
				1.0f, 1.0f, -1.0f,
				1.0f, -1.0f, -1.0f,
				1.0f, 1.0f, 1.0f,
				1.0f, -1.0f, 1.0f,
				1.0f, 1.0f, 1.0f,
				1.0f, 1.0f, -1.0f,
				-1.0f, 1.0f, -1.0f,
				1.0f, 1.0f, 1.0f,
				-1.0f, 1.0f, -1.0f,
				-1.0f, 1.0f, 1.0f,
				1.0f, 1.0f, 1.0f,
				-1.0f, 1.0f, 1.0f,
				1.0f, -1.0f, 1.0f
		};

		for (int x = 0; x < size; x++)
		{
			for (int z = 0; z < size; z++)
			{
				var y = noises[x][z];

				for (int i = 0; i < cubeVertexArray.length; i += 3)
				{
					cubeVertex
							.put(cubeVertexArray[i] / 2 + x)
							.put(cubeVertexArray[i + 1] / 2 * y)
							.put(cubeVertexArray[i + 2] / 2 + z);
				}
			}
		}
		cubeVertex.flip();
		vertexCount = cubeVertex.capacity() / 3;

		vao = GL32.glGenVertexArrays();
		GL32.glBindVertexArray(vao);
		GL32.glEnableVertexAttribArray(0);
		vbo = GL32.glGenBuffers();
		GL32.glBindBuffer(GL32.GL_ARRAY_BUFFER, vbo);
		GL32.glVertexAttribPointer(0, 3, GL32.GL_FLOAT, false, 0, 0L);
		GL32.glBufferData(GL32.GL_ARRAY_BUFFER, cubeVertex, GL32.GL_DYNAMIC_DRAW);
		GL32.glBindBuffer(GL32.GL_ARRAY_BUFFER, 0);
		GL32.glBindVertexArray(0);
		shader = new Shader();
		shader.use();
		Shader.uniform(shader.projectionsUniformId(), projections(90.0f, 0.1f, 1000.0f, 1920, 1080, new Matrix4f()));
		Shader.unbind();
	}

	private static float[][] generatePerlinNoise(float[][] baseNoise,
	                                             int octaveCount, float persistanceIn, float amplitudeIn)
	{
		int width  = baseNoise.length;
		int height = baseNoise[0].length;

		float[][][] smoothNoise = new float[octaveCount][][]; // an array of 2D
		// arrays
		// containing

		float persistance = persistanceIn;

		// generate smooth noise
		for (int i = 0; i < octaveCount; i++)
		{
			smoothNoise[i] = generateSmoothNoise(baseNoise, i);
		}

		float[][] perlinNoise    = new float[width][height];
		float     amplitude      = amplitudeIn; // the bigger, the more big mountains
		float     totalAmplitude = 0.0f;

		// blend noise together
		for (int octave = octaveCount - 1; octave >= 0; octave--)
		{
			amplitude *= persistance;
			totalAmplitude += amplitude;

			for (int i = 0; i < width; i++)
			{
				for (int j = 0; j < height; j++)
				{
					perlinNoise[i][j] += smoothNoise[octave][i][j] * amplitude;
					System.out.println("> " + smoothNoise[octave][i][j] + " * " + amplitude);
				}
			}
		}

		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++)
			{
				perlinNoise[i][j] /= totalAmplitude;
				perlinNoise[i][j] = (float) (Math.floor(perlinNoise[i][j] * 25));
			}
		}

		return perlinNoise;
	}

	private static float[][] generateWhiteNoise(int width, int height)
	{
		float[][] noise = new float[width][height];

		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++)
			{
				noise[i][j] = (float) random.nextDouble() % 1;
			}
		}

		return noise;
	}

	public static Matrix4f projections(float fovIn, float zNearIn, float zFarIn, int widthInIn, int heightIn, Matrix4f projectionMatrixIn)
	{
		float aspectRatio = (float) widthInIn / heightIn;

		projectionMatrixIn.perspective(fovIn, aspectRatio,
				zNearIn, zFarIn);

		return projectionMatrixIn;
	}

	private static float[][] generateSmoothNoise(float[][] baseNoise, int octave)
	{
		int width  = baseNoise.length;
		int height = baseNoise[0].length;

		float[][] smoothNoise = new float[width][height];

		int   samplePeriod    = 1 << octave; // calculates 2 ^ k
		float sampleFrequency = 1.0f / samplePeriod;

		for (int i = 0; i < width; i++)
		{
			// calculate the horizontal sampling indices
			int   sample_i0        = (i / samplePeriod) * samplePeriod;
			int   sample_i1        = (sample_i0 + samplePeriod) % width; // wrap around
			float horizontal_blend = (i - sample_i0) * sampleFrequency;

			for (int j = 0; j < height; j++)
			{
				// calculate the vertical sampling indices
				int sample_j0 = (j / samplePeriod) * samplePeriod;
				int sample_j1 = (sample_j0 + samplePeriod) % height; // wrap
				// around
				float vertical_blend = (j - sample_j0) * sampleFrequency;

				// blend the top two corners
				float top = interpolate(baseNoise[sample_i0][sample_j0],
						baseNoise[sample_i1][sample_j0], horizontal_blend);

				// blend the bottom two corners
				float bottom = interpolate(baseNoise[sample_i0][sample_j1],
						baseNoise[sample_i1][sample_j1], horizontal_blend);

				// final blend
				smoothNoise[i][j] = interpolate(top, bottom, vertical_blend);
			}
		}

		return smoothNoise;
	}

	private static float interpolate(float x0, float x1, float alpha)
	{
		return x0 * (1 - alpha) + alpha * x1;
	}

	public void draw(Camera cameraIn)
	{
		GL32.glBindVertexArray(vao);
		shader.use();
		Shader.uniform(shader.transformationsUniformId(), transformations(offsets, rotations, scale, transformationsMatrix));
		Shader.uniform(shader.viewUniformId(), cameraIn.view());
		GL32.glDrawArrays(GL32.GL_TRIANGLES, 0, vertexCount);
		GL32.glBindVertexArray(0);
		Shader.unbind();
	}

	public static Matrix4f transformations(Vector3f offsetIn, Vector3f rotationIn, float scaleIn, Matrix4f transformationsMatrixIn)
	{
		transformationsMatrixIn.identity().translate(offsetIn).
				rotateX((float) Math.toRadians(rotationIn.x)).
				rotateY((float) Math.toRadians(rotationIn.y)).
				rotateZ((float) Math.toRadians(rotationIn.z)).
				scale(scaleIn);

		return transformationsMatrixIn;
	}

	public void cleanup()
	{
		GL32.glBindVertexArray(0);
		GL32.glDeleteBuffers(vbo);
		GL32.glBindVertexArray(0);
		GL32.glDeleteVertexArrays(vao);

		shader.cleanup();
	}
}