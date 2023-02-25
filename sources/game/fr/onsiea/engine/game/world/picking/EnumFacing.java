/**
 *
 */
package fr.onsiea.engine.game.world.picking;

import org.joml.Vector3f;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * @author seyro
 *
 */
public enum EnumFacing
{
	NONE(new Vector3f()), RIGHT(new Vector3f(1.0f, 0.0f, 0.0f)), LEFT(new Vector3f(-1.0f, 0.0f, 0.0f)),
	UP(new Vector3f(0.0f, 1.0f, 0.0f)), DOWN(new Vector3f(0.0f, -1.0f, 0.0f)), FRONT(new Vector3f(0.0f, 0.0f, -1.0f)),
	BACK(new Vector3f(0.0f, 0.0f, 1.0f));

	@Getter(AccessLevel.PUBLIC)
	private Vector3f pointsTo;

	EnumFacing(final Vector3f pointsTo)
	{
		this.pointsTo = pointsTo;
	}
}
