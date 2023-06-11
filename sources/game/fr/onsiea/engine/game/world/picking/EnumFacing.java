/**
 *
 */
package fr.onsiea.engine.game.world.picking;

import org.joml.Vector3i;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * @author seyro
 *
 */
public enum EnumFacing
{
	NONE(new Vector3i()), RIGHT(new Vector3i(1, 0, 0)), LEFT(new Vector3i(-1, 0, 0)), UP(new Vector3i(0, 1, 0)), DOWN(new Vector3i(0, -1, 0)), FRONT(new Vector3i(0, 0, -1)), BACK(new Vector3i(0, 0, 1));

	@Getter(AccessLevel.PUBLIC)
	private Vector3i pointsTo;

	EnumFacing(final Vector3i pointsTo)
	{
		this.pointsTo = pointsTo;
	}
}
