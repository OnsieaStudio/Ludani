package fr.onsiea.engine.maths.distance;

import org.joml.Vector2d;

public class Distance2d
{
	public final static double xSignedDistance(Vector2d fromIn, Vector2d toIn)
	{
		return toIn.x() - fromIn.x();
	}

	public final static double ySignedDistance(Vector2d fromIn, Vector2d toIn)
	{
		return toIn.y() - fromIn.y();
	}

	public final static double xDistance(Vector2d fromIn, Vector2d toIn)
	{
		return Math.abs(toIn.x() - fromIn.x());
	}

	public final static double yDistance(Vector2d fromIn, Vector2d toIn)
	{
		return Math.abs(toIn.y() - fromIn.y());
	}

	public final static Vector2d signedAxesDistance(Vector2d fromIn, Vector2d toIn)
	{
		return new Vector2d(toIn.x() - fromIn.x(), toIn.y() - fromIn.y());
	}

	public final static Vector2d axesDistance(Vector2d fromIn, Vector2d toIn)
	{
		return new Vector2d(Math.abs(toIn.x() - fromIn.x()), Math.abs(toIn.y() - fromIn.y()));
	}

	public final static Vector2d signedAxesDistance(Vector2d fromIn, Vector2d toIn, Vector2d transposeIn)
	{
		return transposeIn.set(toIn.x() - fromIn.x(), toIn.y() - fromIn.y());
	}

	public final static Vector2d axesDistance(Vector2d fromIn, Vector2d toIn, Vector2d transposeIn)
	{
		return transposeIn.set(toIn.x() - fromIn.x(), Math.abs(toIn.y() - fromIn.y()));
	}

	public final static double distance(Vector2d fromIn, Vector2d toIn)
	{
		final var axesDistance = Distance2d.signedAxesDistance(fromIn, toIn);

		return axesDistance.x() * axesDistance.x() + axesDistance.y() * axesDistance.y();
	}

	public final static double distance(Vector2d fromIn, Vector2d toIn, Vector2d transposeIn)
	{
		final var axesDistance = Distance2d.signedAxesDistance(fromIn, toIn, transposeIn);

		return Distance2d.weight(axesDistance);
	}

	public final static boolean isFullyOut(double distanceIn, double maxDistanceIn)
	{
		return distanceIn > maxDistanceIn;
	}

	public final static boolean isJustOut(double distanceIn, double maxDistanceIn)
	{
		return distanceIn >= maxDistanceIn;
	}

	public final static boolean isFullyIn(double distanceIn, double maxDistanceIn)
	{
		return distanceIn <= maxDistanceIn;
	}

	public final static boolean isJustIn(double distanceIn, double maxDistanceIn)
	{
		return distanceIn <= maxDistanceIn;
	}

	public final static boolean isFullyOut(Vector2d fromIn, Vector2d toIn, double maxDistanceIn)
	{
		return Distance2d.distance(fromIn, toIn) > maxDistanceIn;
	}

	public final static boolean isJustOut(Vector2d fromIn, Vector2d toIn, double maxDistanceIn)
	{
		return Distance2d.distance(fromIn, toIn) >= maxDistanceIn;
	}

	public final static boolean isFullyIn(Vector2d fromIn, Vector2d toIn, double maxDistanceIn)
	{
		return Distance2d.distance(fromIn, toIn) <= maxDistanceIn;
	}

	public final static boolean isJustIn(Vector2d fromIn, Vector2d toIn, double maxDistanceIn)
	{
		return Distance2d.distance(fromIn, toIn) <= maxDistanceIn;
	}

	public final static boolean equals(Vector2d fromIn, Vector2d toIn, double maxDistanceIn)
	{
		return Distance2d.distance(fromIn, toIn) == maxDistanceIn;
	}

	public final static boolean isFullyOut(Vector2d fromIn, Vector2d toIn, Vector2d transposeIn, double maxDistanceIn)
	{
		return Distance2d.distance(fromIn, toIn, transposeIn) > maxDistanceIn;
	}

	public final static boolean isJustOut(Vector2d fromIn, Vector2d toIn, Vector2d transposeIn, double maxDistanceIn)
	{
		return Distance2d.distance(fromIn, toIn, transposeIn) >= maxDistanceIn;
	}

	public final static boolean isFullyIn(Vector2d fromIn, Vector2d toIn, Vector2d transposeIn, double maxDistanceIn)
	{
		return Distance2d.distance(fromIn, toIn, transposeIn) <= maxDistanceIn;
	}

	public final static boolean isJustIn(Vector2d fromIn, Vector2d toIn, Vector2d transposeIn, double maxDistanceIn)
	{
		return Distance2d.distance(fromIn, toIn, transposeIn) <= maxDistanceIn;
	}

	public final static boolean equals(Vector2d fromIn, Vector2d toIn, Vector2d transposeIn, double maxDistanceIn)
	{
		return Distance2d.distance(fromIn, toIn, transposeIn) == maxDistanceIn;
	}

	public final static double weight(Vector2d vectorIn)
	{
		return vectorIn.x() * vectorIn.x() + vectorIn.y() * vectorIn.y();
	}
}
