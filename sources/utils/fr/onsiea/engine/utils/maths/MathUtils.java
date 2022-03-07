package fr.onsiea.engine.utils.maths;

import org.joml.Vector2d;
import org.joml.Vector3f;

public class MathUtils
{
	public final static double round(double valueIn, double aroundDegreesIn)
	{
		return Math.round(valueIn * aroundDegreesIn) / aroundDegreesIn;
	}

	public static double round(double value)
	{
		return Math.round(value * 100.0d) / 100.0d;
	}

	public static Vector2d round(Vector2d vec)
	{
		return new Vector2d(MathUtils.round(vec.x()), MathUtils.round(vec.y()));
	}

	public static Vector3f round(Vector3f convertToVector3f, int pos)
	{
		convertToVector3f.x	= (float) MathUtils.round(convertToVector3f.x(), pos);
		convertToVector3f.y	= (float) MathUtils.round(convertToVector3f.y(), pos);
		convertToVector3f.z	= (float) MathUtils.round(convertToVector3f.z(), pos);
		return convertToVector3f;
	}

	public final static float coTangent(float angleIn)
	{
		return (float) (1f / Math.tan(angleIn));
	}

	public final static float degreesToRadians(float degreesIn)
	{
		return degreesIn * (float) MathInstances.pi180();
	}

	public final static int randomInt(final int minIn, final int maxIn)
	{
		return MathInstances.random().nextInt(maxIn - minIn + 1) + minIn;
	}

	public final static float randomFloat()
	{
		return MathInstances.random().nextFloat();
	}

	public final static long randomLong(long minIn, long maxIn)
	{
		return MathInstances.random().nextLong(minIn, maxIn);
	}

	public final static Vector3f generateRandomUnitVector(Vector3f transposeIn)
	{
		final var theta = (float) (MathUtils.randomFloat() * 2f * Math.PI);
		transposeIn.z = MathUtils.randomFloat() * 2 - 1;
		final var rootOneMinusZSquared = (float) Math.sqrt(1 - transposeIn.z * transposeIn.z);
		transposeIn.x	= (float) (rootOneMinusZSquared * Math.cos(theta));
		transposeIn.y	= (float) (rootOneMinusZSquared * Math.sin(theta));
		return transposeIn;
	}

	public final static Vector3f generateRandomUnitVector()
	{
		final var	theta					= (float) (MathUtils.randomFloat() * 2f * Math.PI);
		final var	z						= MathUtils.randomFloat() * 2 - 1;
		final var	rootOneMinusZSquared	= (float) Math.sqrt(1 - z * z);
		final var	x						= (float) (rootOneMinusZSquared * Math.cos(theta));
		final var	y						= (float) (rootOneMinusZSquared * Math.sin(theta));
		return new Vector3f(x, y, z);
	}

	public final static float generateValue(float averageIn, float offsetIn)
	{
		return averageIn + offsetIn * MathUtils.randomFloat();
	}
}