package fr.onsiea.engine.maths.distance;

import org.joml.Vector4d;

public class Distance4d
{
	public final static double xSignedDistance(Vector4d fromIn, Vector4d toIn)
	{
		return toIn.x() - fromIn.x();
	}

	public final static double ySignedDistance(Vector4d fromIn, Vector4d toIn)
	{
		return toIn.y() - fromIn.y();
	}

	public final static double zSignedDistance(Vector4d fromIn, Vector4d toIn)
	{
		return toIn.z() - fromIn.z();
	}

	public final static double xDistance(Vector4d fromIn, Vector4d toIn)
	{
		return Math.abs(toIn.x() - fromIn.x());
	}

	public final static double yDistance(Vector4d fromIn, Vector4d toIn)
	{
		return Math.abs(toIn.y() - fromIn.y());
	}

	public final static double zDistance(Vector4d fromIn, Vector4d toIn)
	{
		return Math.abs(toIn.z() - fromIn.z());
	}

	public final static Vector4d signedAxesDistance(Vector4d fromIn, Vector4d toIn)
	{
		return new Vector4d(toIn.x() - fromIn.x(), toIn.y() - fromIn.y(), toIn.z() - fromIn.z(), toIn.w() - fromIn.w());
	}

	public final static Vector4d axesDistance(Vector4d fromIn, Vector4d toIn)
	{
		return new Vector4d(Math.abs(toIn.x() - fromIn.x()), Math.abs(toIn.y() - fromIn.y()),
				Math.abs(toIn.z() - fromIn.z()), Math.abs(toIn.w() - fromIn.w()));
	}

	public final static Vector4d signedAxesDistance(Vector4d fromIn, Vector4d toIn, Vector4d transposeIn)
	{
		return transposeIn.set(toIn.x() - fromIn.x(), toIn.y() - fromIn.y(), toIn.z() - fromIn.z());
	}

	public final static Vector4d axesDistance(Vector4d fromIn, Vector4d toIn, Vector4d transposeIn)
	{
		return transposeIn.set(toIn.x() - fromIn.x(), Math.abs(toIn.y() - fromIn.y()), Math.abs(toIn.z() - fromIn.z()));
	}

	public final static double distance(Vector4d fromIn, Vector4d toIn)
	{
		final var axesDistance = Distance4d.signedAxesDistance(fromIn, toIn);

		return Distance4d.weight(axesDistance);
	}

	public final static double distanceXY(Vector4d fromIn, Vector4d toIn)
	{
		final var axesDistance = Distance4d.signedAxesDistance(fromIn, toIn);

		return Distance4d.weightXY(axesDistance);
	}

	public final static double distanceXZ(Vector4d fromIn, Vector4d toIn)
	{
		final var axesDistance = Distance4d.signedAxesDistance(fromIn, toIn);

		return Distance4d.weightXZ(axesDistance);
	}

	public final static double distanceYZ(Vector4d fromIn, Vector4d toIn)
	{
		final var axesDistance = Distance4d.signedAxesDistance(fromIn, toIn);

		return Distance4d.weightYZ(axesDistance);
	}

	public final static double distance(Vector4d fromIn, Vector4d toIn, Vector4d transposeIn)
	{
		final var axesDistance = Distance4d.signedAxesDistance(fromIn, toIn, transposeIn);

		return Distance4d.weight(axesDistance);
	}

	public final static double distanceXY(Vector4d fromIn, Vector4d toIn, Vector4d transposeIn)
	{
		final var axesDistance = Distance4d.signedAxesDistance(fromIn, toIn, transposeIn);

		return Distance4d.weightXY(axesDistance);
	}

	public final static double distanceXZ(Vector4d fromIn, Vector4d toIn, Vector4d transposeIn)
	{
		final var axesDistance = Distance4d.signedAxesDistance(fromIn, toIn, transposeIn);

		return Distance4d.weightXZ(axesDistance);
	}

	public final static double distanceYZ(Vector4d fromIn, Vector4d toIn, Vector4d transposeIn)
	{
		final var axesDistance = Distance4d.signedAxesDistance(fromIn, toIn, transposeIn);

		return Distance4d.weightYZ(axesDistance);
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

	public final static boolean isFullyOut(Vector4d fromIn, Vector4d toIn, double maxDistanceIn)
	{
		return Distance4d.distance(fromIn, toIn) > maxDistanceIn;
	}

	public final static boolean isJustOut(Vector4d fromIn, Vector4d toIn, double maxDistanceIn)
	{
		return Distance4d.distance(fromIn, toIn) >= maxDistanceIn;
	}

	public final static boolean isFullyIn(Vector4d fromIn, Vector4d toIn, double maxDistanceIn)
	{
		return Distance4d.distance(fromIn, toIn) <= maxDistanceIn;
	}

	public final static boolean isJustIn(Vector4d fromIn, Vector4d toIn, double maxDistanceIn)
	{
		return Distance4d.distance(fromIn, toIn) <= maxDistanceIn;
	}

	public final static boolean equals(Vector4d fromIn, Vector4d toIn, double maxDistanceIn)
	{
		return Distance4d.distance(fromIn, toIn) == maxDistanceIn;
	}

	public final static boolean isFullyOut(Vector4d fromIn, Vector4d toIn, Vector4d transposeIn, double maxDistanceIn)
	{
		return Distance4d.distance(fromIn, toIn, transposeIn) > maxDistanceIn;
	}

	public final static boolean isJustOut(Vector4d fromIn, Vector4d toIn, Vector4d transposeIn, double maxDistanceIn)
	{
		return Distance4d.distance(fromIn, toIn, transposeIn) >= maxDistanceIn;
	}

	public final static boolean isFullyIn(Vector4d fromIn, Vector4d toIn, Vector4d transposeIn, double maxDistanceIn)
	{
		return Distance4d.distance(fromIn, toIn, transposeIn) <= maxDistanceIn;
	}

	public final static boolean isJustIn(Vector4d fromIn, Vector4d toIn, Vector4d transposeIn, double maxDistanceIn)
	{
		return Distance4d.distance(fromIn, toIn, transposeIn) <= maxDistanceIn;
	}

	public final static boolean equals(Vector4d fromIn, Vector4d toIn, Vector4d transposeIn, double maxDistanceIn)
	{
		return Distance4d.distance(fromIn, toIn, transposeIn) == maxDistanceIn;
	}

	public final static double weight(Vector4d vectorIn)
	{
		return vectorIn.x() * vectorIn.x() + vectorIn.y() * vectorIn.y() + vectorIn.z() * vectorIn.z()
				+ vectorIn.w() * vectorIn.w();
	}

	public final static double weightXY(Vector4d vectorIn)
	{
		return vectorIn.x() * vectorIn.x() + vectorIn.y() * vectorIn.y();
	}

	public final static double weightXZ(Vector4d vectorIn)
	{
		return vectorIn.x() * vectorIn.x() + vectorIn.z() * vectorIn.z();
	}

	public final static double weightYZ(Vector4d vectorIn)
	{
		return vectorIn.y() * vectorIn.y() + vectorIn.z() * vectorIn.z();
	}
}
