package fr.onsiea.engine.maths.vector;

import org.joml.Vector3d;

import fr.onsiea.engine.maths.distance.Distance3d;

public class VectorUtils3d
{
	public final static boolean is(final Vector3d fromPositionIn, final double xIn, final double yIn, final double zIn)
	{
		return fromPositionIn.x() == xIn && fromPositionIn.y() == yIn && fromPositionIn.z() == zIn;
	}

	public final static boolean is(final Vector3d fromPositionIn, final Vector3d toPositionIn)
	{
		return fromPositionIn.x() == toPositionIn.x() && fromPositionIn.y() == toPositionIn.y()
				&& fromPositionIn.z() == toPositionIn.z();
	}

	public final static Vector3d min(Vector3d oneIn, Vector3d twoIn)
	{
		return Distance3d.weight(oneIn) < Distance3d.weight(twoIn) ? oneIn : twoIn;
	}

	public final static Vector3d max(Vector3d oneIn, Vector3d twoIn)
	{
		return Distance3d.weight(oneIn) > Distance3d.weight(twoIn) ? oneIn : twoIn;
	}

	public final static Vector3d minimize(Vector3d oneIn, Vector3d twoIn)
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

		return new Vector3d(x, y, z);
	}

	public final static Vector3d minimize(Vector3d oneIn, Vector3d twoIn, Vector3d transposeIn)
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

		return transposeIn.set(x, y, z);
	}

	public final static Vector3d maximize(Vector3d oneIn, Vector3d twoIn)
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

		return new Vector3d(x, y, z);
	}

	public final static Vector3d maximize(Vector3d oneIn, Vector3d twoIn, Vector3d transposeIn)
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

		return transposeIn.set(x, y, z);
	}
}