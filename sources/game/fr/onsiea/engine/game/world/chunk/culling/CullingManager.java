package fr.onsiea.engine.game.world.chunk.culling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.joml.Vector3f;

public class CullingManager
{
	public List<ICulling> cullings;

	public CullingManager(final ICulling... cullingsIn)
	{
		this.cullings = new ArrayList<>();

		Collections.addAll(this.cullings, cullingsIn);
	}

	public void update(final Map<Vector3f, IHeadable> objectsIn)
	{
		for (final Entry<Vector3f, IHeadable> entry : objectsIn.entrySet())
		{
			final var	position	= entry.getKey();
			final var	object		= entry.getValue();

			for (final var culling : this.cullings)
			{
				if (culling.isCulling(position))
				{
					object.makeInvisible();
					continue;
				}
				object.makeVisible();
			}
		}
	}
}