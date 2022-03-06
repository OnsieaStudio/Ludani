package fr.onsiea.engine.maths.distance;

import org.joml.Vector3d;

public class Distance3d
{
	public final static double xSignedDistance(Vector3d fromIn, Vector3d toIn)
	{
		return toIn.x() - fromIn.x();
	}

	public final static double ySignedDistance(Vector3d fromIn, Vector3d toIn)
	{
		return toIn.y() - fromIn.y();
	}

	public final static double zSignedDistance(Vector3d fromIn, Vector3d toIn)
	{
		return toIn.z() - fromIn.z();
	}

	public final static double xDistance(Vector3d fromIn, Vector3d toIn)
	{
		return Math.abs(toIn.x() - fromIn.x());
	}

	public final static double yDistance(Vector3d fromIn, Vector3d toIn)
	{
		return Math.abs(toIn.y() - fromIn.y());
	}

	public final static double zDistance(Vector3d fromIn, Vector3d toIn)
	{
		return Math.abs(toIn.z() - fromIn.z());
	}

	public final static Vector3d signedAxesDistance(Vector3d fromIn, Vector3d toIn)
	{
		return new Vector3d(toIn.x() - fromIn.x(), toIn.y() - fromIn.y(), toIn.z() - fromIn.z());
	}

	public final static Vector3d axesDistance(Vector3d fromIn, Vector3d toIn)
	{
		return new Vector3d(Math.abs(toIn.x() - fromIn.x()), Math.abs(toIn.y() - fromIn.y()),
				Math.abs(toIn.z() - fromIn.z()));
	}

	public final static Vector3d signedAxesDistance(Vector3d fromIn, Vector3d toIn, Vector3d transposeIn)
	{
		return transposeIn.set(toIn.x() - fromIn.x(), toIn.y() - fromIn.y(), toIn.z() - fromIn.z());
	}

	public final static Vector3d axesDistance(Vector3d fromIn, Vector3d toIn, Vector3d transposeIn)
	{
		return transposeIn.set(toIn.x() - fromIn.x(), Math.abs(toIn.y() - fromIn.y()), Math.abs(toIn.z() - fromIn.z()));
	}

	public final static double distance(Vector3d fromIn, Vector3d toIn)
	{
		final var axesDistance = Distance3d.signedAxesDistance(fromIn, toIn);

		return Distance3d.weight(axesDistance);
	}

	public final static double distanceXY(Vector3d fromIn, Vector3d toIn)
	{
		final var axesDistance = Distance3d.signedAxesDistance(fromIn, toIn);

		return Distance3d.weightXY(axesDistance);
	}

	public final static double distanceXZ(Vector3d fromIn, Vector3d toIn)
	{
		final var axesDistance = Distance3d.signedAxesDistance(fromIn, toIn);

		return Distance3d.weightXZ(axesDistance);
	}

	public final static double distanceYZ(Vector3d fromIn, Vector3d toIn)
	{
		final var axesDistance = Distance3d.signedAxesDistance(fromIn, toIn);

		return Distance3d.weightYZ(axesDistance);
	}

	public final static double distance(Vector3d fromIn, Vector3d toIn, Vector3d transposeIn)
	{
		final var axesDistance = Distance3d.signedAxesDistance(fromIn, toIn, transposeIn);

		return Distance3d.weight(axesDistance);
	}

	public final static double distanceXY(Vector3d fromIn, Vector3d toIn, Vector3d transposeIn)
	{
		final var axesDistance = Distance3d.signedAxesDistance(fromIn, toIn, transposeIn);

		return Distance3d.weightXY(axesDistance);
	}

	public final static double distanceXZ(Vector3d fromIn, Vector3d toIn, Vector3d transposeIn)
	{
		final var axesDistance = Distance3d.signedAxesDistance(fromIn, toIn, transposeIn);

		return Distance3d.weightXZ(axesDistance);
	}

	public final static double distanceYZ(Vector3d fromIn, Vector3d toIn, Vector3d transposeIn)
	{
		final var axesDistance = Distance3d.signedAxesDistance(fromIn, toIn, transposeIn);

		return Distance3d.weightYZ(axesDistance);
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

	public final static boolean isFullyOut(Vector3d fromIn, Vector3d toIn, double maxDistanceIn)
	{
		return Distance3d.distance(fromIn, toIn) > maxDistanceIn;
	}

	public final static boolean isJustOut(Vector3d fromIn, Vector3d toIn, double maxDistanceIn)
	{
		return Distance3d.distance(fromIn, toIn) >= maxDistanceIn;
	}

	public final static boolean isFullyIn(Vector3d fromIn, Vector3d toIn, double maxDistanceIn)
	{
		return Distance3d.distance(fromIn, toIn) <= maxDistanceIn;
	}

	public final static boolean isJustIn(Vector3d fromIn, Vector3d toIn, double maxDistanceIn)
	{
		return Distance3d.distance(fromIn, toIn) <= maxDistanceIn;
	}

	public final static boolean equals(Vector3d fromIn, Vector3d toIn, double maxDistanceIn)
	{
		return Distance3d.distance(fromIn, toIn) == maxDistanceIn;
	}

	public final static boolean isFullyOut(Vector3d fromIn, Vector3d toIn, Vector3d transposeIn, double maxDistanceIn)
	{
		return Distance3d.distance(fromIn, toIn, transposeIn) > maxDistanceIn;
	}

	public final static boolean isJustOut(Vector3d fromIn, Vector3d toIn, Vector3d transposeIn, double maxDistanceIn)
	{
		return Distance3d.distance(fromIn, toIn, transposeIn) >= maxDistanceIn;
	}

	public final static boolean isFullyIn(Vector3d fromIn, Vector3d toIn, Vector3d transposeIn, double maxDistanceIn)
	{
		return Distance3d.distance(fromIn, toIn, transposeIn) <= maxDistanceIn;
	}

	public final static boolean isJustIn(Vector3d fromIn, Vector3d toIn, Vector3d transposeIn, double maxDistanceIn)
	{
		return Distance3d.distance(fromIn, toIn, transposeIn) <= maxDistanceIn;
	}

	public final static boolean equals(Vector3d fromIn, Vector3d toIn, Vector3d transposeIn, double maxDistanceIn)
	{
		return Distance3d.distance(fromIn, toIn, transposeIn) == maxDistanceIn;
	}

	public final static double weight(Vector3d vectorIn)
	{
		return vectorIn.x() * vectorIn.x() + vectorIn.y() * vectorIn.y() + vectorIn.z() * vectorIn.z();
	}

	public final static double weightXY(Vector3d vectorIn)
	{
		return vectorIn.x() * vectorIn.x() + vectorIn.y() * vectorIn.y();
	}

	public final static double weightXZ(Vector3d vectorIn)
	{
		return vectorIn.x() * vectorIn.x() + vectorIn.z() * vectorIn.z();
	}

	public final static double weightYZ(Vector3d vectorIn)
	{
		return vectorIn.y() * vectorIn.y() + vectorIn.z() * vectorIn.z();
	}
}
