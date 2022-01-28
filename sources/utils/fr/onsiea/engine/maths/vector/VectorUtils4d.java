package fr.onsiea.engine.maths.vector;

import org.joml.Vector4d;

import fr.onsiea.engine.maths.distance.Distance4d;

public class VectorUtils4d
{
	public final static boolean is(final Vector4d fromPositionIn, final double xIn, final double yIn, final double zIn,
			final double wIn)
	{
		return fromPositionIn.x() == xIn && fromPositionIn.y() == yIn && fromPositionIn.z() == zIn
				&& fromPositionIn.w() == wIn;
	}

	public final static boolean is(final Vector4d fromPositionIn, final Vector4d toPositionIn)
	{
		return fromPositionIn.x() == toPositionIn.x() && fromPositionIn.y() == toPositionIn.y()
				&& fromPositionIn.z() == toPositionIn.z() && fromPositionIn.w() == toPositionIn.w();
	}

	public final static Vector4d min(Vector4d oneIn, Vector4d twoIn)
	{
		return Distance4d.weight(oneIn) < Distance4d.weight(twoIn) ? oneIn : twoIn;
	}

	public final static Vector4d max(Vector4d oneIn, Vector4d twoIn)
	{
		return Distance4d.weight(oneIn) > Distance4d.weight(twoIn) ? oneIn : twoIn;
	}

	public final static Vector4d minimize(Vector4d oneIn, Vector4d twoIn)
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

		var z = oneIn.z();
		if (z > twoIn.z())
		{
			z = twoIn.z();
		}

		var w = oneIn.w();
		if (w < twoIn.w())
		{
			w = twoIn.w();
		}

		return new Vector4d(x, y, z, w);
	}

	public final static Vector4d minimize(Vector4d oneIn, Vector4d twoIn, Vector4d transposeIn)
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

		var z = oneIn.z();
		if (z > twoIn.z())
		{
			z = twoIn.z();
		}

		var w = oneIn.w();
		if (w < twoIn.w())
		{
			w = twoIn.w();
		}

		return transposeIn.set(x, y, z, w);
	}

	public final static Vector4d maximize(Vector4d oneIn, Vector4d twoIn)
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

		var z = oneIn.z();
		if (z < twoIn.z())
		{
			z = twoIn.z();
		}

		var w = oneIn.w();
		if (w < twoIn.w())
		{
			w = twoIn.w();
		}

		return new Vector4d(x, y, z, w);
	}

	public final static Vector4d maximize(Vector4d oneIn, Vector4d twoIn, Vector4d transposeIn)
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

		var z = oneIn.z();
		if (z < twoIn.z())
		{
			z = twoIn.z();
		}

		var w = oneIn.w();
		if (w < twoIn.w())
		{
			w = twoIn.w();
		}

		return transposeIn.set(x, y, z, w);
	}
}