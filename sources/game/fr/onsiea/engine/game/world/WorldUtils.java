/**
 *
 */
package fr.onsiea.engine.game.world;

import org.joml.Vector3i;

import fr.onsiea.engine.game.world.chunk.ChunkUtils;

/**
 * @author seyro
 *
 */
public class WorldUtils
{
	private final static Vector3i	CHUNKEDPOSITION_TEMP_TO_VEC	= new Vector3i();
	private static float			uniqueItemId;

	public final static float uniqueItemId()
	{
		return WorldUtils.uniqueItemId++;
	}

	public final static Vector3i chunkedPosition(final Vector3i fromIn)
	{
		return WorldUtils.chunkedPosition(fromIn, WorldUtils.CHUNKEDPOSITION_TEMP_TO_VEC);
	}

	public final static Vector3i newChunkedPosition(final Vector3i fromIn)
	{
		return WorldUtils.chunkedPosition(fromIn, new Vector3i());
	}

	public final static Vector3i chunkedPosition(final Vector3i fromIn, final Vector3i toIn)
	{
		return WorldUtils.CHUNKEDPOSITION_TEMP_TO_VEC.set((int) Math.floor(fromIn.x() / ChunkUtils.SIZE.x()),
				(int) Math.floor(fromIn.y() / ChunkUtils.SIZE.y()), (int) Math.floor(fromIn.z() / ChunkUtils.SIZE.z()));
	}
}