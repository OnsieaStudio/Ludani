package fr.onsiea.engine.game.world.picking;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import fr.onsiea.engine.client.graphics.mesh.obj.MeshData;
import fr.onsiea.engine.client.graphics.mesh.obj.normalMapped.NormalMappedObjLoader;
import fr.onsiea.engine.client.graphics.opengl.OpenGLRenderAPIContext;
import fr.onsiea.engine.client.graphics.opengl.instanced.Instanced;
import fr.onsiea.engine.client.graphics.opengl.shaders.AdvInstancedShaderWithTransformations;
import fr.onsiea.engine.client.graphics.opengl.texture.GLTextureSettings;
import fr.onsiea.engine.client.graphics.opengl.utils.OpenGLUtils;
import fr.onsiea.engine.client.graphics.render.IRenderAPIContext;
import fr.onsiea.engine.client.graphics.texture.ITexturesManager;
import fr.onsiea.engine.client.graphics.texture.Texture;
import fr.onsiea.engine.core.entity.PlayerEntity;
import fr.onsiea.engine.utils.maths.MathInstances;
import fr.onsiea.engine.utils.maths.MathUtils;
import fr.onsiea.engine.utils.maths.transformations.Transformations3f;

public class PickerVisualizer
{
	private Vector3f									position;
	private Vector3f									dir;
	//private float x = 0;
	//private Instanced instanced;
	private final AdvInstancedShaderWithTransformations	shader;
	private Texture<GLTextureSettings>					texture;

	@SuppressWarnings("unchecked")
	public PickerVisualizer(final IRenderAPIContext contextIn)
	{
		this.texture	= ((ITexturesManager<GLTextureSettings>) contextIn.texturesManager()).load("laser.png",
				GLTextureSettings.Builder.of((OpenGLRenderAPIContext) contextIn, GL11.GL_LINEAR, GL11.GL_LINEAR,
						GL13.GL_CLAMP_TO_BORDER, GL13.GL_CLAMP_TO_BORDER, true));

		this.shader		= (AdvInstancedShaderWithTransformations) contextIn.shadersManager()
				.get("advInstancedWithTransformations");

		this.position	= new Vector3f();
		this.dir		= new Vector3f();

		MeshData meshData = null;
		try
		{
			meshData = ((NormalMappedObjLoader) contextIn.meshsManager().objLoader())
					.loadData("resources\\models\\cube2.obj");
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
		final var	scale		= new Vector3f(0.25f, 0.25f, 8.0f);

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

	private final Vector3f worldCoords3f = new Vector3f();

	public void update(final PlayerEntity playerEntityIn, final float speedIn)
	{
		/**if(x >= 40)
		{
			x = 0;**/

		final var	normalizedMouseX	= 2.0f * (1920.0f / 2.0f - 0.0f + 16.0f) / 1920.0f - 1f;
		final var	normalizedMouseY	= 2.0f * (1040.0f / 2.0f - 0.0f - 16.0f) / 1080.0f - 1f;
		final var	clipCoords			= new Vector4f(normalizedMouseX, normalizedMouseY, 0.0f * 2f - 1f, 1.0f);
		final var	invertedProjection	= new Matrix4f(MathInstances.projectionMatrix()).invert();
		var			eyeCoords			= new Vector4f(clipCoords).mul(invertedProjection);
		eyeCoords = new Vector4f(eyeCoords.x, eyeCoords.y, -1.0f, 0.0f);
		final var	invertedView	= new Matrix4f(playerEntityIn.view()).invert();
		final var	rayWorld		= new Vector4f(eyeCoords).mul(invertedView);
		final var	mouseRay		= new Vector3f(rayWorld.x, rayWorld.y, rayWorld.z);
		mouseRay.normalize();
		this.dir = mouseRay;

		final var	eyeCoords0	= new Vector4f(clipCoords).mul(new Matrix4f(MathInstances.projectionMatrix()));

		final var	worldCoords	= new Matrix4f(playerEntityIn.view()).invert().transform(eyeCoords0);

		if (Math.abs(0.0f - worldCoords.w()) > 0.00001f)
		{
			worldCoords.mul(1.0f / worldCoords.w());
		}
		this.worldCoords3f.set(worldCoords.x(), worldCoords.y(), worldCoords.z());
		this.dir.set(playerEntityIn.orientation());
		//}

		//Vector3f temp = new Vector3f(dir).mul(0.0f);
		this.position.set(new Vector3f(0.0f, 0.0f, -20.25f));

		//x += speedIn;

	}

	public void draw()
	{
		this.shader.attach();
		this.shader.textured().load(true);
		OpenGLUtils.enableTransparency();
		this.texture.attach();

		//final var		x				= (float) Math.toDegrees(Math.sin(this.dir.y() * MathInstances.pi180()) * 1.0f);
		//final var		y				= (float) Math
		//		.toDegrees(-Math.cos(this.dir.y() * MathInstances.pi180()) * 1.0f);
		final var transformations = new Matrix4f().translate(this.position).scale(new Vector3f(0.025f, 0.025f, 20.0f));
		this.shader.transformations().load(transformations);

		//this.instanced.bindDrawUnbind();
		this.texture.detach();
		OpenGLUtils.disableTransparency();
	}
}