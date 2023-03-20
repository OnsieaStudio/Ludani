/**
 *
 */
package fr.onsiea.engine.game.world;

import fr.onsiea.engine.game.world.chunks.ChunksManager;
import fr.onsiea.engine.utils.maths.MathInstances;
import lombok.Getter;

/**
 * @author seyro
 *
 */

public class WorldData
{
	private final World					world;
	private @Getter final long			seed;
	private @Getter final ChunksManager	chunksManager;

	public WorldData(final World worldIn)
	{
		this.world			= worldIn;
		this.seed			= MathInstances.random().nextLong();
		this.chunksManager	= new ChunksManager(worldIn);
	}
}