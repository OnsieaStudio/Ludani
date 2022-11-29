package fr.onsiea.engine.game.world.chunk.culling;

import org.joml.Vector3f;

public class CubeDistanceCulling implements ICulling
{
	/**
	 * Not use root, to optimize performance
	 */
	private final int cubeDistance;

	public CubeDistanceCulling(final int distanceIn)
	{
		this.cubeDistance = distanceIn * distanceIn * distanceIn;
	}

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
		return xIn * xIn + yIn * yIn + zIn * zIn > this.cubeDistance;
	}

	/**
	 * Return true if is not visible
	 */
	@Override
	public boolean isCulling(final Vector3f positionIn)
	{
		return positionIn.x() * positionIn.x() + positionIn.y() * positionIn.y()
				+ positionIn.z() * positionIn.z() > this.cubeDistance;
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
		return EnumCullingReason.DISTANCE;
	}
}
