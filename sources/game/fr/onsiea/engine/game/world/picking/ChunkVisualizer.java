package fr.onsiea.engine.game.world.picking;

import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import fr.onsiea.engine.client.graphics.mesh.obj.MeshData;
import fr.onsiea.engine.client.graphics.mesh.obj.normalMapped.NormalMappedObjLoader;
import fr.onsiea.engine.client.graphics.opengl.instanced.Instanced;
import fr.onsiea.engine.client.graphics.render.IRenderAPIContext;
import fr.onsiea.engine.utils.maths.MathInstances;
import fr.onsiea.engine.utils.maths.MathUtils;
import fr.onsiea.engine.utils.maths.transformations.Transformations3f;

public class ChunkVisualizer
{
	//private Instanced									instanced;
	//private final AdvInstancedShaderWithTransformations	shader;

	public ChunkVisualizer(final IRenderAPIContext contextIn)
	{
		//this.shader = (AdvInstancedShaderWithTransformations) contextIn.shadersManager()
		//		.get("advInstancedWithTransformations");

		MeshData meshData = null;
		try
		{
			meshData = ((NormalMappedObjLoader) contextIn.meshsManager().objLoader())
					.loadData("resources\\models\\cube_0-5_quads.obj");
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		if (meshData == null)
		{
			return;
		}

		final var builder = new Instanced.Builder();

		builder.data(meshData.positions(), 3).data(meshData.uvs(), 2).data(meshData.normals(), 3)
				.data(meshData.tangents(), 3);

		final var	buffer		= BufferUtils.createFloatBuffer(1 * (MathInstances.matrixSizeFloats() + 1));
		final var	localBuffer	= BufferUtils.createFloatBuffer(MathInstances.matrixSizeFloats() + 1);

		final var	position	= new Vector3f(0.0f, 0.0f, 0.0f);
		final var	orientation	= new Vector3f(0.0f, 0.0f, 0.0f);
		final var	scale		= new Vector3f(1.0f, 1.0f, 1.0f);

		final var	matrix		= Transformations3f.transformations(position, orientation, scale);

		matrix.get(0, localBuffer);
		localBuffer.put(MathInstances.matrixSizeFloats(), MathUtils.randomInt(0, 3));
		buffer.put(localBuffer);

		localBuffer.clear();

		buffer.flip();

		builder.interleaveData(buffer, MathInstances.matrixSizeFloats() + 1).unbind();
		builder.indices(meshData.indices());
		//this.instanced = builder.build(meshData.indices().length, 1);
	}

	public void draw(final Vector3f positionIn)
	{
		/**shader.attach();
		shader.transformations().load(Transformations3f.transformations(new Vector3f(positionIn).mul(16.0f, 16.0f, 16.0f).add(7.25f, 7.25f, 7.25f), MathInstances.zero3f(), new Vector3f(8.5f, 8.5f, 8.5f)));
		shader.textured().load(false);

		GL32.glDisable(GL32.GL_CULL_FACE);
		this.instanced.bind();
		this.instanced.draw(GL32.GL_LINES_ADJACENCY);
		this.instanced.unbind();**/
	}
}