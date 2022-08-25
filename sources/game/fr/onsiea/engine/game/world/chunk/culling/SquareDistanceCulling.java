package fr.onsiea.engine.game.world.chunk.culling;

import org.joml.Vector3f;

public class SquareDistanceCulling implements ICulling
{
	private int squaredDistance;

	public SquareDistanceCulling(int distanceIn)
	{
		this.squaredDistance = distanceIn * distanceIn;
	}

	/**
	 *  Return true if is not visible
	 * @param xIn
	 * @param yIn
	 * @param zIn
	 * @return
	 */
	public boolean isCulling(float xIn, float yIn, float zIn)
	{
		return xIn * xIn + zIn * zIn > squaredDistance;
	}
	
	/**
	 *  Return true if is not visible
	 */
	@Override
	public boolean isCulling(Vector3f positionIn)
	{
		return positionIn.x() * positionIn.x() + positionIn.z() * positionIn.z() > squaredDistance;
	}

	/** Return true if is not visible
	* startPositionIn is the minimal position of object (position - size) 
	**/
	@Override
	public boolean isCulling(Vector3f startPositionIn, Vector3f sizeIn)
	{
		return
		this.isCulling(startPositionIn) &&
		this.isCulling(startPositionIn.x() + sizeIn.x(), startPositionIn.y() + sizeIn.y(), startPositionIn.z() + sizeIn.z());
	}

	@Override
	public EnumCullingReason reason()
	{
		return EnumCullingReason.DISTANCE;
	}
}
