package fr.onsiea.engine.game.world.chunk.culling;

import org.joml.Vector3f;

public class CoordinateDistanceCulling implements ICulling
{
	private int distance;

	public CoordinateDistanceCulling(int distanceIn)
	{
		this.distance = distanceIn;
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
		if(xIn > distance || xIn < -distance)
		return true;
		if(yIn > distance || yIn < -distance)
			return true;
		if(zIn > distance || zIn < -distance)
			return true;
		
		return false;
	}
	
	/**
	 *  Return true if is not visible
	 */
	@Override
	public boolean isCulling(Vector3f positionIn)
	{
		if(positionIn.x() > distance || positionIn.x() < -distance)
		return true;
		if(positionIn.y() > distance || positionIn.y() < -distance)
			return true;
		if(positionIn.z() > distance || positionIn.z() < -distance)
			return true;
		
		return false;
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
