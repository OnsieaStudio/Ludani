package fr.onsiea.engine.game.world.chunk.culling;

import java.util.List;

import org.joml.FrustumIntersection;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3i;

import fr.onsiea.engine.game.world.chunk.Chunk;

public class FrustumCulling implements ICulling
{
	// private static final int NUM_PLANES = 6;

	private final Matrix4f				prjViewMatrix;

	// private final Vector4f[] frustumPlanes;
	private final FrustumIntersection	frustumInt;

	public FrustumCulling()
	{
		this.prjViewMatrix	= new Matrix4f();
		this.frustumInt		= new FrustumIntersection();
		/**
		 * frustumPlanes = new Vector4f[NUM_PLANES];
		 * for (int i = 0; i < NUM_PLANES; i++) {
		 * frustumPlanes[i] = new Vector4f();
		 * }
		 **/
	}

	public void updateFrustum(final Matrix4f projMatrixIn, final Matrix4f viewMatrixIn)
	{

		// Calculate projection view matrix
		this.prjViewMatrix.set(projMatrixIn);
		this.prjViewMatrix.mul(viewMatrixIn);
		// Update frustum intersection class
		this.frustumInt.set(this.prjViewMatrix);

		/**
		 * // Get frustum planes
		 * for (int i = 0; i < NUM_PLANES; i++) {
		 * prjViewMatrix.frustumPlane(i, frustumPlanes[i]);
		 * }
		 **/
	}

	public boolean insideFrustum(final Vector3f positionIn, final float boundingRadiusIn)
	{
		return this.frustumInt.testSphere(positionIn.x, positionIn.y, positionIn.z, boundingRadiusIn);
	}

	public boolean insideFrustum(final float x0In, final float y0In, final float z0In, final float boundingRadiusIn)
	{
		return this.frustumInt.testSphere(x0In, y0In, z0In, boundingRadiusIn);
		/**
		 * boolean result = true;
		 * for (int i = 0; i < NUM_PLANES; i++) {
		 * Vector4f plane = frustumPlanes[i];
		 * if (plane.x * x0 + plane.y * y0 + plane.z * z0 + plane.w <= -boundingRadius)
		 * {
		 * result = false; return result;
		 * }
		 * }
		 * return result;
		 **/
	}

	public void filter(final List<Chunk> chunksIn, final float meshBoundingRadiusIn)
	{
		float		boundingRadius;
		Vector3i	pos;
		for (final Chunk chunk : chunksIn)
		{
			/**
			 * float scale = item.scale().x;
			 * if(scale < item.scale().y && item.scale().y < item.scale().z)
			 * scale = item.scale().y;
			 * else
			 * scale = item.scale().z;
			 **/

			boundingRadius	= 16;
			pos				= chunk.position();
			chunk.isVisible(this.insideFrustum(pos.x, pos.y, pos.z, boundingRadius));
		}
	}

	/**
	 * public void filter(final Map<? extends Mesh, List<GameItem>> mapMesh)
	 * {
	 * for (Map.Entry<? extends Mesh, List<GameItem>> entry : mapMesh.entrySet())
	 * {
	 * final List<GameItem> gameItems = entry.getValue();
	 * this.filter(gameItems, entry.getKey().getBoundingRadius());
	 * }
	 * }
	 **/

	/**
	 * Return true if is not visible
	 *
	 * @param xIn
	 * @param yIn
	 * @param zIn
	 * @return
	 */
	public boolean isCulling(final float xIn, final float yIn, final float zIn)
	{
		return false;
	}

	/**
	 * Return true if is not visible
	 */
	@Override
	public boolean isCulling(final Vector3f positionIn)
	{
		return false;
	}

	/**
	 * Return true if is not visible
	 * startPositionIn is the minimal position of object (position - size)
	 **/
	@Override
	public boolean isCulling(final Vector3f startPositionIn, final Vector3f sizeIn)
	{
		return this.isCulling(startPositionIn) && this.isCulling(startPositionIn.x() + sizeIn.x(),
				startPositionIn.y() + sizeIn.y(), startPositionIn.z() + sizeIn.z());
	}

	@Override
	public EnumCullingReason reason()
	{
		return EnumCullingReason.FRUSTUM;
	}
}
