/**
 *
 */
package fr.onsiea.engine.game.world.chunk;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import fr.onsiea.engine.client.graphics.opengl.shaders.NoiseShader;
import fr.onsiea.engine.client.graphics.opengl.shaders.normalMapping.ShaderNormalMappingThinMatrix;
import fr.onsiea.engine.client.graphics.shader.IShadersManager;
import fr.onsiea.engine.game.world.World;
import fr.onsiea.engine.utils.maths.transformations.Transformations3f;

/**
 * @author Seynax
 *
 */
public class NoisedChunk
{
	private int				vao;
	private int				ibo;
	private int				indicesCount;

	private final Vector3f	chunkPosition;

	// private World world;

	/**
	 * @param world2
	 * @param chunkPosition
	 */
	public NoisedChunk(final World worldIn, final Vector3f chunkPositionIn)
	{
		// this.world = worldIn;

		this.chunkPosition = chunkPositionIn;
	}

	public void initialization(final int vertexCountIn)
	{
		final var	count			= vertexCountIn * vertexCountIn;
		final var	vertices		= new float[count * 3];

		/**
		 * final var noises0 = PerlinNoise.heights
		 * (this.chunkPosition.x * vertexCountIn, this.chunkPosition.z * vertexCountIn,
		 * vertexCountIn, vertexCountIn,
		 * 8, 0.05f, 0.25f, 0.0f, 0.25f,
		 * 0.25f, 5.00f, 20.0f, world.seed());
		 **/
		// final var noises0 = PerlinNoise.heights(this.chunkPosition.x * vertexCountIn,
		// this.chunkPosition.z * vertexCountIn, vertexCountIn, vertexCountIn,
		// 8, 2, 2, 0, 0.5f, 1.0f,
		// 1.0f, 75.0f, world.seed());
		// final var noises0 = PerlinNoise
		// .generatePerlinNoise(PerlinNoise.generateWhiteNoise(vertexCountIn,
		// vertexCountIn), 8/* octave count */);

		/**
		 * var noises = PerlinNoise.merge(noises0, noises1, (var height0In,
		 * var height1In) -> height1In > 4.0f ? height1In * 1.0f : (height0In * 4.0f +
		 * height1In * 0f) / 4f);
		 **/
		// final var noises = noises0;

		var			vertexPointer	= 0;
		for (var x = 0; x < vertexCountIn; x++)
		{
			for (var z = 0; z < vertexCountIn; z++)
			{
				// final var vertice = PerlinNoise.get(z, x, vertexCountIn, 100, 100,
				// noises[x][z]); //noises[x][z]);

				// vertices[vertexPointer * 3 + 0] = vertice[0];
				// vertices[vertexPointer * 3 + 1] = vertice[1];
				// vertices[vertexPointer * 3 + 2] = vertice[2];
				vertices[vertexPointer * 3 + 0]	= x;
				vertices[vertexPointer * 3 + 1]	= 1.0f;	// noises[x][z];
				vertices[vertexPointer * 3 + 2]	= z;

				vertexPointer++;
			}
		}

		final var	indices	= new int[6 * (vertexCountIn - 1) * (vertexCountIn - 1)];
		var			pointer	= 0;
		for (var gz = 0; gz < vertexCountIn - 1; gz++)
		{
			for (var gx = 0; gx < vertexCountIn - 1; gx++)
			{
				final var	topLeft		= gz * vertexCountIn + gx;
				final var	topRight	= topLeft + 1;
				final var	bottomLeft	= (gz + 1) * vertexCountIn + gx;
				final var	bottomRight	= bottomLeft + 1;
				indices[pointer] = bottomLeft;
				pointer++;
				indices[pointer] = bottomRight;
				pointer++;
				indices[pointer] = topRight;
				pointer++;
				indices[pointer] = topRight;
				pointer++;
				indices[pointer] = topLeft;
				pointer++;
				indices[pointer] = bottomLeft;
				pointer++;

			}
		}
		this.indicesCount	= indices.length;

		this.vao			= GL30.glGenVertexArrays();
		GL30.glBindVertexArray(this.vao);
		GL20.glEnableVertexAttribArray(0);

		var vbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0L);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		this.ibo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.ibo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_STATIC_DRAW);

		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}

	/**
	 * @param shaderIn
	 * @param shader
	 */
	public void draw(final ShaderNormalMappingThinMatrix shaderIn, final NoiseShader noiseShaderIn,
			final IShadersManager shadersManagerIn)
	{
		noiseShaderIn.attach();
		noiseShaderIn.transformations().load(Transformations3f.transformations(100 * this.chunkPosition.x, 40,
				100 * this.chunkPosition.z, 0, 0, 0, 1, 1, 1));
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL30.glBindVertexArray(this.vao);
		GL20.glEnableVertexAttribArray(0);
		GL11.glDrawElements(GL11.GL_TRIANGLES, this.indicesCount, GL11.GL_UNSIGNED_INT, 0L);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shadersManagerIn.detach();
		GL11.glEnable(GL11.GL_CULL_FACE);
	}

	/**
	 *
	 */
	public void cleanup()
	{
		// TODO Auto-generated method stub
	}
}