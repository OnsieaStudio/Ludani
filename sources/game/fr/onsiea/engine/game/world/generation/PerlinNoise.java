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
package fr.onsiea.engine.game.world.generation;

import java.util.Random;

/**
 * @author Seynax
 *
 */
public class PerlinNoise
{
	private static Random random = new Random(new Random().nextLong());

	public static float[][] generateWhiteNoise(int width, int height) {
	    float[][] noise = new float[width][height];

	    for (int i = 0; i < width; i++) {
	        for (int j = 0; j < height; j++) {
	            noise[i][j] = (float) random.nextDouble() % 1;
	        }
	    }

	    return noise;
	}

	private static float[][] generateSmoothNoise(float[][] baseNoise, int octave) {
	    int width = baseNoise.length;
	    int height = baseNoise[0].length;

	    float[][] smoothNoise = new float[width][height];

	    int samplePeriod = 1 << octave; // calculates 2 ^ k
	    float sampleFrequency = 1.0f / samplePeriod;

	    for (int i = 0; i < width; i++) {
	        // calculate the horizontal sampling indices
	        int sample_i0 = (i / samplePeriod) * samplePeriod;
	        int sample_i1 = (sample_i0 + samplePeriod) % width; // wrap around
	        float horizontal_blend = (i - sample_i0) * sampleFrequency;

	        for (int j = 0; j < height; j++) {
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

	public static float[][] generatePerlinNoise(float[][] baseNoise,
	        int octaveCount) {
	    int width = baseNoise.length;
	    int height = baseNoise[0].length;

	    float[][][] smoothNoise = new float[octaveCount][][]; // an array of 2D
	                                                            // arrays
	                                                            // containing

	    float persistance = 0.5f;

	    // generate smooth noise
	    for (int i = 0; i < octaveCount; i++) {
	        smoothNoise[i] = generateSmoothNoise(baseNoise, i);
	    }

	    float[][] perlinNoise = new float[width][height];
	    float amplitude = 0.0f; // the bigger, the more big mountains
	    float totalAmplitude = 0.0f;

	    // blend noise together
	    for (int octave = octaveCount - 1; octave >= 0; octave--) {
	        amplitude *= persistance;
	        totalAmplitude += amplitude;

	        for (int i = 0; i < width; i++) {
	            for (int j = 0; j < height; j++) {
	                perlinNoise[i][j] += smoothNoise[octave][i][j] * amplitude;
	            }
	        }
	    }

	    for (int i = 0; i < width; i++) {
	        for (int j = 0; j < height; j++) {
	            perlinNoise[i][j] /= totalAmplitude;
	            perlinNoise[i][j] = (float) (Math.floor(perlinNoise[i][j] * 25));
	        }
	    }

	    return perlinNoise;
	}

	/**public static float[][] generateSmoothNoise(final float[][] baseNoiseIn, final int octaveCountIn)
	{
		final var	width			= baseNoiseIn.length;
		final var	height			= baseNoiseIn[0].length;

		final var	smoothNoise		= new float[width][height];

		final var	samplePeriod	= 1 << octaveCountIn;		// calculates 2 ^ k
		final var	sampleFrequency	= 1.0f / samplePeriod;

		for (var i = 0; i < width; i++)
		{
			// calculate the horizontal sampling indices
			final var	sample_i0			= i / samplePeriod * samplePeriod;
			final var	sample_i1			= (sample_i0 + samplePeriod) % width;	// wrap around
			final var	horizontal_blend	= (i - sample_i0) * sampleFrequency;

			for (var j = 0; j < height; j++)
			{
				// calculate the vertical sampling indices
				final var	sample_j0		= j / samplePeriod * samplePeriod;
				final var	sample_j1		= (sample_j0 + samplePeriod) % height;						// wrap
				// around
				final var	vertical_blend	= (j - sample_j0) * sampleFrequency;

				// blend the top two corners
				final var	top				= PerlinNoise.interpolate(baseNoiseIn[sample_i0][sample_j0],
						baseNoiseIn[sample_i1][sample_j0], horizontal_blend);

				// blend the bottom two corners
				final var	bottom			= PerlinNoise.interpolate(baseNoiseIn[sample_i0][sample_j1],
						baseNoiseIn[sample_i1][sample_j1], horizontal_blend);

				// final blend
				smoothNoise[i][j] = PerlinNoise.interpolate(top, bottom, vertical_blend);
			}
		}

		return smoothNoise;
	}

	public static float interpolate(final float x0In, final float x1In, final float alphaIn)
	{
		return x0In * (1 - alphaIn) + alphaIn * x1In;
	}

	public static float[][] generatePerlinNoise(final float[][] baseNoiseIn, final int octaveCountIn,
			float persistanceIn, float baseAmplitudeIn, float amplitudeIn)
	{
		final var	width		= baseNoiseIn.length;
		final var	height		= baseNoiseIn[0].length;

		final var	smoothNoise	= new float[octaveCountIn][][];	// an array of 2D
		// arrays
		// containing

		// generate smooth noise
		for (var i = 0; i < octaveCountIn; i++)
		{
			smoothNoise[i] = PerlinNoise.generateSmoothNoise(baseNoiseIn, i);
		}

		final var	perlinNoise			= new float[width][height];
		var			totalAmplitude		= 0.0f;
		var			current_amplitude	= baseAmplitudeIn;

		// blend noise together
		for (var octave = octaveCountIn - 1; octave >= 0; octave--)
		{
			current_amplitude	*= persistanceIn;
			totalAmplitude		+= amplitudeIn;

			for (var i = 0; i < width; i++)
			{
				for (var j = 0; j < height; j++)
				{
					perlinNoise[i][j] += smoothNoise[octave][i][j] * current_amplitude;
				}
			}
		}

		for (var i = 0; i < width; i++)
		{
			for (var j = 0; j < height; j++)
			{
				perlinNoise[i][j]	/= totalAmplitude;
				perlinNoise[i][j]	= (float) Math.floor(perlinNoise[i][j] * 25);
			}
		}

		return perlinNoise;
	}

	public final static float[][] randomNoises(final int widthIn, final int heightIn, long seedIn)
	{
		PerlinNoise.random.setSeed(seedIn);

		final var noise = new float[widthIn][heightIn];

		for (var i = 0; i < widthIn; i++)
		{
			for (var j = 0; j < heightIn; j++)
			{
				noise[i][j] = PerlinNoise.random.nextFloat();
			}
		}

		return noise;
	}

	public static float[][] generateWhiteNoise(final int widthIn, final int heightIn, long seedIn)
	{
		PerlinNoise.random.setSeed(seedIn);

		final var noise = new float[widthIn][heightIn];

		for (var i = 0; i < widthIn; i++)
		{
			for (var j = 0; j < heightIn; j++)
			{
				noise[i][j] = (float) PerlinNoise.random.nextDouble() % 1;
			}
		}

		return noise;
	}**/

	public final static float[][] heights(float minXIn, float minZIn, int sizeXIn, int sizeZIn, int octavesIn, float octaveBaseFreqIn,
			float octaveFreqIn, float minHeightIn, float roughnessIn, float roughnessVariationsModifierIn,
			float molifyIn, float amplitudeIn, long seedIn)
	{
		final var noises = new float[sizeXIn][sizeZIn];

		for (var x = 0; x < sizeXIn; x++)
		{
			for (var z = 0; z < sizeXIn; z++)
			{
				noises[x][z] = PerlinNoise.height(x + minXIn, z + minZIn, octavesIn, octaveBaseFreqIn, octaveFreqIn, minHeightIn,
						roughnessIn, roughnessVariationsModifierIn, molifyIn, amplitudeIn, seedIn);
			}
		}

		return noises;
	}

	public final static float height(float xIn, float zIn, int octavesIn, float octaveBaseFreqIn, float octaveFreqIn,
			float minHeightIn, float roughnessIn, float roughnessVariationsModifierIn, float molifyIn,
			float amplitudeIn, long seedIn)
	{
		var			total	= 0F + minHeightIn;
		final var	d		= (float) Math.pow(octaveBaseFreqIn, octavesIn - 1);
		for (var i = 0; i < octavesIn; i++)
		{
			final var	freq	= (float) (Math.pow(octaveFreqIn, i * roughnessVariationsModifierIn) / d);
			final var	amp		= (float) Math.pow(roughnessIn, i * molifyIn) * amplitudeIn;
			total += PerlinNoise.getInterpolatedNoise(xIn * freq, zIn * freq, seedIn) * amp;
		}
		return total;
	}

	private final static float getInterpolatedNoise(float x, float z, long seedIn)
	{
		final var	intX	= (int) x;
		final var	intZ	= (int) z;
		final var	fracX	= x - intX;
		final var	fracZ	= z - intZ;

		final var	v1		= PerlinNoise.getSmoothNoise(intX, intZ, seedIn);
		final var	v2		= PerlinNoise.getSmoothNoise(intX + 1, intZ, seedIn);
		final var	v3		= PerlinNoise.getSmoothNoise(intX, intZ + 1, seedIn);
		final var	v4		= PerlinNoise.getSmoothNoise(intX + 1, intZ + 1, seedIn);
		final var	i1		= PerlinNoise.interpolate(v1, v2, fracX);
		final var	i2		= PerlinNoise.interpolate(v3, v4, fracX);
		return PerlinNoise.interpolate(i1, i2, fracZ);
	}

	private final static float interpolate(float a, float b, float blend)
	{
		// return x0 * (1 - alpha) + alpha * x1;
		final var	theta	= blend * Math.PI;
		final var	f		= (float) (1f - Math.cos(theta)) * 0.5f;
		return a * (1f - f) + b * f;
	}

	private final static float getSmoothNoise(int x, int z, long seedIn)
	{
		final var	corners	= (PerlinNoise.getNoise(x - 1, z - 1, seedIn) + PerlinNoise.getNoise(x + 1, z - 1, seedIn)
				+ PerlinNoise.getNoise(x - 1, z + 1, seedIn) + PerlinNoise.getNoise(x + 1, z + 1, seedIn)) / 16f;
		final var	sides	= (PerlinNoise.getNoise(x - 1, z, seedIn) + PerlinNoise.getNoise(x + 1, z, seedIn)
				+ PerlinNoise.getNoise(x, z - 1, seedIn) + PerlinNoise.getNoise(x, z + 1, seedIn)) / 8f;
		final var	center	= PerlinNoise.getNoise(x, z, seedIn) / 4f;

		return corners + sides + center;
	}

	private final static float getNoise(int x, int z, long seedIn)
	{
		PerlinNoise.random.setSeed(x * 49632 + z * 325176 + seedIn);
		return PerlinNoise.random.nextFloat() * 2f - 1f;
	}

	public final static float[] get(float xIn, float zIn, int vertexCountIn, float widthFactorIn, float depthFactorIn,
			float noisedHeightIn)
	{
		return new float[]
		{ zIn / ((float) vertexCountIn - 1) * widthFactorIn, noisedHeightIn,
				xIn / ((float) vertexCountIn - 1) * depthFactorIn };
	}

	public final static float[][] filterMin(float[][] noisesIn, float minIn)
	{
		if (noisesIn.length <= 0 || noisesIn[0].length <= 0)
		{
			return null;
		}

		final var noises = new float[noisesIn.length][noisesIn[0].length];

		for (var x = 0; x < noisesIn.length; x++)
		{
			for (var z = 0; z < noisesIn.length; z++)
			{
				final var height = noisesIn[x][z];

				if (height < minIn)
				{
					noisesIn[x][z] = minIn;
				}
				else
				{
					noises[x][z] = height;
				}
			}
		}

		return noises;
	}

	public final static float[][] filterMax(float[][] noisesIn, float maxIn)
	{
		if (noisesIn.length <= 0 || noisesIn[0].length <= 0)
		{
			return null;
		}

		final var noises = new float[noisesIn.length][noisesIn[0].length];

		for (var x = 0; x < noisesIn.length; x++)
		{
			for (var z = 0; z < noisesIn.length; z++)
			{
				final var height = noisesIn[x][z];

				if (height > maxIn)
				{
					noisesIn[x][z] = maxIn;
				}
				else
				{
					noises[x][z] = height;
				}
			}
		}

		return noises;
	}

	public final static float[][] filter(float[][] noisesIn, float minIn, float maxIn)
	{
		if (noisesIn.length <= 0 || noisesIn[0].length <= 0)
		{
			System.err.println("Noises array is empty ! (PerlinNoise.java:236)");
			return null;
		}

		final var noises = new float[noisesIn.length][noisesIn[0].length];

		for (var x = 0; x < noisesIn.length; x++)
		{
			for (var z = 0; z < noisesIn.length; z++)
			{
				final var height = noisesIn[x][z];

				if (height < minIn)
				{
					noises[x][z] = minIn;
				}
				else if (height > maxIn)
				{
					noises[x][z] = maxIn;
				}
				else
				{
					noises[x][z] = height;
				}
			}
		}

		return noises;
	}

	public final static float[][] merge(float[][] noises0In, float[][] noises1In, IChooser chooserIn)
	{
		if (noises0In.length <= 0 || noises0In[0].length <= 0 || noises1In.length <= 0 || noises1In[0].length <= 0)
		{
			System.err.println("One noises array is empty ! (PerlinNoise.java:270)");

			return null;
		}

		var	xLength	= noises0In.length;
		var	zLength	= noises0In[0].length;

		if (xLength < noises1In.length)
		{
			xLength = noises1In.length;
		}
		if (zLength < noises1In[0].length)
		{
			zLength = noises1In[0].length;
		}

		final var noises = new float[xLength][zLength];

		for (var x = 0; x < xLength; x++)
		{
			for (var z = 0; z < zLength; z++)
			{
				noises[x][z] = chooserIn.choose(noises0In[x][z], noises1In[x][z]);
			}
		}

		return noises;
	}

	public interface IChooser
	{
		float choose(float height0In, float height1In);
	}
}