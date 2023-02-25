/**
 *
 */
package fr.onsiea.engine.game.world;

import java.util.HashMap;
import java.util.Map;

import org.joml.Vector3f;

import fr.onsiea.engine.game.world.chunk.Chunk;
import fr.onsiea.engine.game.world.chunk.culling.CoordinateDistanceCulling;
import fr.onsiea.engine.game.world.chunk.culling.CubeDistanceCulling;
import fr.onsiea.engine.game.world.chunk.culling.CullingManager;
import fr.onsiea.engine.game.world.chunk.culling.FrustumCulling;
import fr.onsiea.engine.game.world.chunk.culling.SquareDistanceCulling;
import fr.onsiea.engine.game.world.picking.Picker;
import fr.onsiea.engine.utils.maths.MathInstances;
import fr.onsiea.engine.utils.time.Timer;
import lombok.Getter;
import lombok.Setter;

/**
 * @author seyro
 *
 */
@Getter
public class WorldInformations
{
	private final Map<Vector3f, Chunk>	chunks;

	private final long					seed;

	private final Picker				picker;

	private final CullingManager		cullingManager;
	private final FrustumCulling		frustumCulling;
	private @Setter boolean				cullingTest;

	private final Timer					placeTimer;
	private final Timer					destroyTimer;

	public WorldInformations()
	{
		this.chunks			= new HashMap<>();

		this.seed			= MathInstances.random().nextLong();

		this.picker			= new Picker();

		// Cullings

		this.cullingManager	= new CullingManager(new CoordinateDistanceCulling(8 * 16),
				new SquareDistanceCulling(6 * 16), new CubeDistanceCulling(4 * 16));
		this.frustumCulling	= new FrustumCulling();
		this.cullingTest	= true;

		// Timers

		this.placeTimer		= new Timer().start();
		this.destroyTimer	= new Timer().start();
	}
}
