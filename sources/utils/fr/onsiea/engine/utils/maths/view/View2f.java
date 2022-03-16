package fr.onsiea.engine.utils.maths.view;

import org.joml.Matrix4f;
import org.joml.Vector2f;

public class View2f
{
	private final static float	defaultAngleDEGREES	= 0.0f;
	private final static float	defaultAngleRADIANS	= (float) Math.toRadians(View2f.defaultAngleDEGREES);

	public final static Matrix4f view(Vector2f positionIn)
	{
		return new Matrix4f().identity()
				.rotateXYZ(View2f.defaultAngleRADIANS, View2f.defaultAngleRADIANS, View2f.defaultAngleRADIANS)
				.rotateZ((float) Math.toRadians(0.0f)).translate(-positionIn.x(), -positionIn.y(), 0.0f)
				.scale(1.0f, 1.0f, 1.0f);
	}

	public final static Matrix4f view(Vector2f positionIn, Vector2f rotationIn)
	{
		return new Matrix4f().identity().rotateXYZ((float) Math.toRadians(rotationIn.x()),
				(float) Math.toRadians(rotationIn.y()), View2f.defaultAngleRADIANS)
				.translate(-positionIn.x(), -positionIn.y(), 0.0f).scale(1.0f);
	}

	public final static Matrix4f view(Vector2f positionIn, Vector2f rotationIn, Vector2f scaleIn)
	{
		return new Matrix4f().identity()
				.rotateXYZ((float) Math.toRadians(rotationIn.x()), (float) Math.toRadians(rotationIn.y()),
						View2f.defaultAngleRADIANS)
				.translate(-positionIn.x(), -positionIn.y(), 0.0f).scale(scaleIn.x(), scaleIn.y(), 0.0f);
	}

	public final static Matrix4f view(Vector2f positionIn, Vector2f rotationIn, float scaleIn)
	{
		return new Matrix4f().identity().rotateXYZ((float) Math.toRadians(rotationIn.x()),
				(float) Math.toRadians(rotationIn.y()), View2f.defaultAngleRADIANS)
				.translate(-positionIn.x(), -positionIn.y(), -0.0f).scale(scaleIn);
	}

	public static Matrix4f view(float xIn, float yIn, float rxIn, float ryIn, float sxIn, float syIn)
	{
		return new Matrix4f().identity()
				.rotateXYZ((float) Math.toRadians(rxIn), (float) Math.toRadians(ryIn), View2f.defaultAngleRADIANS)
				.translate(-xIn, -yIn, 0.0f).scale(sxIn, syIn, 1.0f);
	}

	public final static Matrix4f view(Vector2f positionIn, Matrix4f viewIn)
	{
		return viewIn.identity()
				.rotateXYZ(View2f.defaultAngleRADIANS, View2f.defaultAngleRADIANS, View2f.defaultAngleRADIANS)
				.translate(-positionIn.x(), -positionIn.y(), 0.0f).scale(1.0f, 1.0f, 1.0f);
	}

	public final static Matrix4f view(Vector2f positionIn, Vector2f rotationIn, Matrix4f viewIn)
	{
		return viewIn.identity().rotateXYZ((float) Math.toRadians(rotationIn.x()),
				(float) Math.toRadians(rotationIn.y()), View2f.defaultAngleRADIANS)
				.translate(-positionIn.x(), -positionIn.y(), 0.0f).scale(1.0f);
	}

	public final static Matrix4f view(Vector2f positionIn, Vector2f rotationIn, Vector2f scaleIn, Matrix4f viewIn)
	{
		return viewIn.identity()
				.rotateXYZ((float) Math.toRadians(rotationIn.x()), (float) Math.toRadians(rotationIn.y()),
						View2f.defaultAngleRADIANS)
				.translate(-positionIn.x(), -positionIn.y(), 0.0f).scale(scaleIn.x(), scaleIn.y(), 1.0f);
	}

	public final static Matrix4f view(Vector2f positionIn, Vector2f rotationIn, float scaleIn, Matrix4f viewIn)
	{
		return viewIn.identity().rotateXYZ((float) Math.toRadians(rotationIn.x()),
				(float) Math.toRadians(rotationIn.y()), View2f.defaultAngleRADIANS)
				.translate(-positionIn.x(), -positionIn.y(), 0.0f).scale(scaleIn);
	}

	public static Matrix4f view(float xIn, float yIn, float rxIn, float ryIn, float sxIn, float syIn, Matrix4f viewIn)
	{
		return viewIn.identity()
				.rotateXYZ((float) Math.toRadians(rxIn), (float) Math.toRadians(ryIn), View2f.defaultAngleRADIANS)
				.translate(-xIn, -yIn, 0.0f).scale(sxIn, syIn, 1.0f);
	}
}