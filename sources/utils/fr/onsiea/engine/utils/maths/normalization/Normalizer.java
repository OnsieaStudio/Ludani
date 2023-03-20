package fr.onsiea.engine.utils.maths.normalization;

import org.joml.Vector2d;
import org.joml.Vector2f;

public class Normalizer
{
	private static final Normalizer	INSTANCE	= new Normalizer();

	private int						width;
	private int						height;

	Normalizer()
	{
	}

	public static void update(final int widthIn, final int heightIn)
	{
		Normalizer.getInstance().updateSizes(widthIn, heightIn);
	}

	public void updateSizes(final int widthIn, final int heightIn)
	{
		this.setWidth(widthIn);
		this.setHeight(heightIn);
	}

	public Vector2f normalize01(final Vector2f scaleIn, final float scaleXIn, final float scaleYIn)
	{
		return new Vector2f(scaleIn.x() / scaleXIn, scaleIn.y() / scaleYIn);
	}

	public Vector2f normalize01(final Vector2f scaleIn, final Vector2f toNormalScaleInstanceIn, final float scaleXIn,
			final float scaleYIn)
	{
		toNormalScaleInstanceIn.x	= scaleIn.x() / scaleXIn;

		toNormalScaleInstanceIn.y	= scaleIn.y() / scaleYIn;

		return toNormalScaleInstanceIn;
	}

	public Vector2f normalize(final Vector2f positionIn, final Vector2f toNormalPositionInstanceIn)
	{
		toNormalPositionInstanceIn.x	= this.normalizeX(positionIn.x());

		toNormalPositionInstanceIn.y	= this.normalizeY(positionIn.y());

		return toNormalPositionInstanceIn;
	}

	public Vector2f normalize(final Vector2f positionIn)
	{
		return new Vector2f(this.normalizeX(positionIn.x()), this.normalizeY(positionIn.y()));
	}

	/** Between [0; 1] interval **/

	/** Normalize value between [0; 1] on axe X, return xIn / maxIn; **/
	public double normalizeX01(final double xIn)
	{
		return xIn / this.getWidth();
	}

	/** Normalize value between [0; 1] on axe X, return xIn / maxIn; **/
	public double denormalizeX01(final double xIn)
	{
		return xIn * this.getWidth();
	}

	/** Normalize value between [0; 1] on axe X, return xIn / maxIn; **/
	public float normalizeX01(final float xIn)
	{
		return xIn / this.getWidth();
	}

	/** Normalize value between [0; 1] on axe X, return xIn / maxIn; **/
	public float denormalizeX01(final float xIn)
	{
		return xIn * this.getWidth();
	}

	/** Normalize value between [1; 0] on axe Y, return yIn / maxIn; **/
	public double normalizeY01(final double yIn)
	{
		return 1.0D - yIn / this.getHeight();
	}

	/** Normalize value between [1; 0] on axe Y, return yIn / maxIn; **/
	public double denormalizeY01(final double yIn)
	{
		return (1.0D - yIn) * this.getHeight();
	}

	/** Normalize value between [1; 0] on axe Y, return yIn / maxIn; **/
	public float normalizeY01(final float yIn)
	{
		return 1.0F - yIn / this.getHeight();
	}

	/** Normalize value between [1; 0] on axe Y, return yIn / maxIn; **/
	public float denormalizeY01(final float yIn)
	{
		return (1.0F - yIn) * this.getHeight();
	}

	/** Between [-1; 1] interval **/

	/** Normalize value between [-1; 1] on axe X, return xIn / maxIn; **/
	public double normalizeX(final double xIn)
	{
		return xIn / this.getWidth() * 2.0D - 1.0D;
	}

	/** Denormalize value from [-1; 1] on axe X, return xIn / maxIn; **/
	public double denormalizeX(final double xIn)
	{
		return (xIn + 1) * this.getWidth() / 2.0D;
	}

	/** Normalize value between [0; 1] on axe X, return xIn / maxIn; **/
	public float normalizeX(final float xIn)
	{
		return xIn / this.getWidth() * 2.0F - 1.0F;
	}

	/** Normalize value between [0; 1] on axe X, return xIn / maxIn; **/
	public double denormalizeX(final float xIn)
	{
		return (xIn + 1) * this.getWidth() / 2.0F;
	}

	/** Normalize value between [1; 0] on axe Y, return yIn / maxIn; **/
	public double normalizeY(final double yIn)
	{
		return 1 - yIn / this.getHeight() * 2.0D;
	}

	/** Normalize value between [1; 0] on axe Y, return yIn / maxIn; **/
	public double denormalizeY(final double yIn)
	{
		return (1 - yIn) * this.getHeight() / 2.0D;
	}

	/** Normalize value between [1; 0] on axe Y, return yIn / maxIn; **/
	public float normalizeY(final float yIn)
	{
		return 1 - yIn / this.getHeight() * 2.0f;
	}

	/** Normalize value between [1; 0] on axe Y, return yIn / maxIn; **/
	public double denormalizeY(final float yIn)
	{
		return (1 - yIn) * this.getHeight() / 2.0D;
	}

	public int getWidth()
	{
		return this.width;
	}

	public void setWidth(final int widthIn)
	{
		this.width = widthIn;
	}

	public int getHeight()
	{
		return this.height;
	}

	public void setHeight(final int heightIn)
	{
		this.height = heightIn;
	}

	public static Normalizer getInstance()
	{
		return Normalizer.INSTANCE;
	}

	/** Between [0; 1] interval **/

	/** Normalize value between [0; 1] on axe X, return xIn / maxIn; **/
	public static double normalizeX01(final double xIn, final double maxIn)
	{
		return xIn / maxIn;
	}

	/** Normalize value between [0; 1] on axe X, return xIn / maxIn; **/
	public static double denormalizeX01(final double xIn, final double maxIn)
	{
		return xIn * maxIn;
	}

	/** Normalize value between [0; 1] on axe X, return xIn / maxIn; **/
	public static float normalizeX01(final float xIn, final float maxIn)
	{
		return xIn / maxIn;
	}

	/** Normalize value between [0; 1] on axe X, return xIn / maxIn; **/
	public static float denormalizeX01(final float xIn, final float maxIn)
	{
		return xIn * maxIn;
	}

	/** Normalize value between [1; 0] on axe Y, return yIn / maxIn; **/
	public static double normalizeY01(final double yIn, final double maxIn)
	{
		return 1.0D - yIn / maxIn;
	}

	/** Normalize value between [1; 0] on axe Y, return yIn / maxIn; **/
	public static double denormalizeY01(final double yIn, final double maxIn)
	{
		return (1.0D - yIn) * maxIn;
	}

	/** Normalize value between [1; 0] on axe Y, return yIn / maxIn; **/
	public static float normalizeY01(final float yIn, final float maxIn)
	{
		return 1.0F - yIn / maxIn;
	}

	/** Normalize value between [1; 0] on axe Y, return yIn / maxIn; **/
	public static float denormalizeY01(final float yIn, final float maxIn)
	{
		return (1.0F - yIn) * maxIn;
	}

	/** Between [-1; 1] interval **/

	/** Normalize value between [0; 1] on axe X, return xIn / maxIn; **/
	public static double normalizeX(final double xIn, final double maxIn)
	{
		return xIn / maxIn * 2.0D - 1.0D;
	}

	/** Normalize value between [0; 1] on axe X, return xIn / maxIn; **/
	public static double denormalizeX(final double xIn, final double maxIn)
	{
		return (xIn + 1) * maxIn / 2.0D;
	}

	/** Normalize value between [0; 1] on axe X, return xIn / maxIn; **/
	public static float normalizeX(final float xIn, final float maxIn)
	{
		return xIn / maxIn * 2.0F - 1.0F;
	}

	/** Normalize value between [0; 1] on axe X, return xIn / maxIn; **/
	public static double denormalizeX(final float xIn, final float maxIn)
	{
		return (xIn + 1) * maxIn / 2.0F;
	}

	/** Normalize value between [1; 0] on axe Y, return yIn / maxIn; **/
	public static double normalizeY(final double yIn, final double maxIn)
	{
		return 1 - yIn / maxIn * 2.0D;
	}

	/** Normalize value between [1; 0] on axe Y, return yIn / maxIn; **/
	public static double denormalizeY(final double yIn, final double maxIn)
	{
		return (1 - yIn) * maxIn / 2.0D;
	}

	/** Normalize value between [1; 0] on axe Y, return yIn / maxIn; **/
	public static float normalizeY(final float yIn, final float maxIn)
	{
		return 1 - yIn / maxIn * 2.0f;
	}

	/** Normalize value between [1; 0] on axe Y, return yIn / maxIn; **/
	public static double denormalizeY(final float yIn, final float maxIn)
	{
		return (1 - yIn) * maxIn / 2.0D;
	}

	/** Normalize value between [0; 1] on axe X, return xIn / maxIn; **/
	public static double normalizeX01toX(final double xIn, final double maxIn)
	{
		return xIn * maxIn / maxIn * 2.0D - 1.0F;
	}

	/** Normalize value between [0; 1] on axe X, return xIn / maxIn; **/
	public static double normalizeY01toY(final double yIn, final double maxIn)
	{
		return (1.0F - yIn) * maxIn / maxIn * 2.0D;
	}

	/** Normalize value between [0; 1] on axe X, return xIn / maxIn; **/
	public static double normalizeXtoX01(final double xIn, final double maxIn)
	{
		return (xIn + 1) * maxIn / 2.0F / maxIn;
	}

	/** Normalize value between [0; 1] on axe X, return xIn / maxIn; **/
	public static double normalizeYtoY01(final double yIn, final double maxIn)
	{
		return 1.0F - (1 - yIn) * maxIn / 2.0D / maxIn;
	}

	public final static double normalizeX(final double positionComponentIn, final int widthIn)
	{
		return positionComponentIn / widthIn * 2.0f - 1.0f;
	}

	public final static double normalizeY(final double positionComponentIn, final int heightIn)
	{
		return 1.0f - positionComponentIn / heightIn * 2.0f;
	}

	public final static double denormalizeX(final double positionComponentIn, final int widthIn)
	{
		return (positionComponentIn + 1.0f) / 2.0f * widthIn;
	}

	public final static double denormalizeY(final double positionComponentIn, final int heightIn)
	{
		return (1.0f - positionComponentIn) / 2.0f * heightIn;
	}

	public final static boolean seemsToBeNormalized(final double positionComponentIn)
	{
		if (positionComponentIn >= -1 && positionComponentIn <= 1)
		{
			return true;
		}

		return false;
	}

	public static double percent(final double from, final double to)
	{
		return from / to * 100.0d;
	}

	public static float percent(final float from, final float to)
	{
		return from / to * 100.0f;
	}

	public static Vector2d percent(final Vector2d from, final Vector2d to)
	{
		return new Vector2d(Normalizer.percent(from.x, to.x), Normalizer.percent(from.y, to.y));
	}

	public static Vector2f percent(final Vector2f from, final Vector2f to)
	{
		return new Vector2f(Normalizer.percent(from.x, to.x), Normalizer.percent(from.y, to.y));
	}

	public static double percentToValue(final double percent, final double value)
	{
		return value * percent / 100.0d;
	}

	public static float percentToValue(final float percent, final float value)
	{
		return value * percent / 100.0f;
	}

	public static Vector2d percentToNormalized(final Vector2d positionIn)
	{
		return new Vector2d(Normalizer.percentToNormalizedX(positionIn.x),
				Normalizer.percentToNormalizedY(positionIn.y));
	}

	public static Vector2d percentToNormalized(final Vector2d positionIn, final Vector2d destinationIn)
	{
		destinationIn.x	= Normalizer.percentToNormalizedX(positionIn.x);
		destinationIn.y	= Normalizer.percentToNormalizedX(positionIn.y);
		return destinationIn;
	}

	public static Vector2d percentToNormalized(final double x, final double y, final Vector2d destinationIn)
	{
		destinationIn.x	= Normalizer.percentToNormalizedX(x);
		destinationIn.y	= Normalizer.percentToNormalizedX(y);
		return destinationIn;
	}

	public static double percentToNormalizedX(final double percent)
	{
		return percent * 2.0d / 100.0d - 1.0d;
	}

	public static double percentToNormalizedY(final double percent)
	{
		return 1.0d - percent * 2.0d / 100.0d;
	}

	public static Vector2d normalizedToPercent(final Vector2d positionIn)
	{
		return new Vector2d(Normalizer.normalizedXToPercent(positionIn.x),
				Normalizer.normalizedYToPercent(positionIn.y));
	}

	public static double normalizedXToPercent(final double normalizedValue)
	{
		return (normalizedValue + 1.0d) * 100.0d / 2.0d;
	}

	public static double normalizedYToPercent(final double normalizedValue)
	{
		return (1.0d - normalizedValue) * 100.0d / 2.0d;
	}

	/**public static Vector2d normal(Vector2d positionIn, WindowOptions windowOptionsIn)
	{
		return new Vector2d(normalX(positionIn.x, windowOptionsIn.getCurrentWidth()), normalY(positionIn.y, windowOptionsIn.getCurrentHeight()));
	}**/

	public static double normalX(final double x, final double width)
	{
		return (x + 1.0d) * (width / 2.0d);
	}

	public static double normalY(final double y, final double height)
	{
		return (1.0d - y) * (height / 2.0d);//- 1;
	}

	/**public static Vector2d normalized(Vector2d positionIn, WindowOptions windowOptionsIn)
	{
		return new Vector2d(normalizedX(positionIn.x, windowOptionsIn.getCurrentWidth()), normalizedY(positionIn.y, windowOptionsIn.getCurrentHeight()));
	}**/

	public static double normalizedX(final double x, final double width)
	{
		return x / (width / 2.0d) - 1.0d;
	}

	public static double normalizedY(final double y, final double height)
	{
		return 1.0d - y / (height / 2.0d);
	}
}