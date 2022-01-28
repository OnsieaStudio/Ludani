package fr.onsiea.engine.maths.vector;

import org.joml.Vector2d;

import fr.onsiea.engine.maths.distance.Distance2d;

public class VectorUtils2d
{
	public final static boolean is(final Vector2d fromPositionIn, final double xIn, final double yIn)
	{
		return fromPositionIn.x() == xIn && fromPositionIn.y() == yIn;
	}

	public final static boolean is(final Vector2d fromPositionIn, final Vector2d toPositionIn)
	{
		return fromPositionIn.x() == toPositionIn.x() && fromPositionIn.y() == toPositionIn.y();
	}

	public final static Vector2d min(Vector2d oneIn, Vector2d twoIn)
	{
		return Distance2d.weight(oneIn) < Distance2d.weight(twoIn) ? oneIn : twoIn;
	}

	public final static Vector2d max(Vector2d oneIn, Vector2d twoIn)
	{
		return Distance2d.weight(oneIn) > Distance2d.weight(twoIn) ? oneIn : twoIn;
	}

	public final static Vector2d minimize(Vector2d oneIn, Vector2d twoIn)
	{
		var x = oneIn.x();
		if (x > twoIn.x())
		{
			x = twoIn.x();
		}

		var y = oneIn.y();
		if (y > twoIn.y())
		{
			y = twoIn.y();
		}

		return new Vector2d(x, y);
	}

	public final static Vector2d minimize(Vector2d oneIn, Vector2d twoIn, Vector2d transposeIn)
	{
		var x = oneIn.x();
		if (x > twoIn.x())
		{
			x = twoIn.x();
		}

		var y = oneIn.y();
		if (y > twoIn.y())
		{
			y = twoIn.y();
		}

		return transposeIn.set(x, y);
	}

	public final static Vector2d maximize(Vector2d oneIn, Vector2d twoIn)
	{
		var x = oneIn.x();
		if (x < twoIn.x())
		{
			x = twoIn.x();
		}

		var y = oneIn.y();
		if (y < twoIn.y())
		{
			y = twoIn.y();
		}

		return new Vector2d(x, y);
	}

	public final static Vector2d maximize(Vector2d oneIn, Vector2d twoIn, Vector2d transposeIn)
	{
		var x = oneIn.x();
		if (x < twoIn.x())
		{
			x = twoIn.x();
		}

		var y = oneIn.y();
		if (y < twoIn.y())
		{
			y = twoIn.y();
		}

		return transposeIn.set(x, y);
	}
}