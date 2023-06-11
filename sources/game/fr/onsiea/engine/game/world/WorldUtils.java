/**
 *
 */
package fr.onsiea.engine.game.world;

import org.joml.Vector3f;

import fr.onsiea.engine.game.world.chunk.ChunkUtils;

/**
 * @author seyro
 *
 */
public class WorldUtils
{
	private final static Vector3f	CHUNKEDPOSITION_TEMP_TO_VEC	= new Vector3f();
	private static float			uniqueItemId;

	public final static float uniqueItemId()
	{
		return WorldUtils.uniqueItemId++;
	}

	public final static Vector3f chunkedPosition(final Vector3f fromIn)
	{
		return WorldUtils.chunkedPosition(fromIn, WorldUtils.CHUNKEDPOSITION_TEMP_TO_VEC);
	}

	public final static Vector3f newChunkedPosition(final Vector3f positionIn)
	{
		return WorldUtils.chunkedPosition(positionIn, new Vector3f());
	}

	public final static Vector3f chunkedPosition(final Vector3f fromIn, final Vector3f toIn)
	{
		return WorldUtils.CHUNKEDPOSITION_TEMP_TO_VEC.set(Math.floor(fromIn.x() / ChunkUtils.SIZE.x()), Math.floor(fromIn.y() / ChunkUtils.SIZE.y()), Math.floor(fromIn.z() / ChunkUtils.SIZE.z()));
	}
}