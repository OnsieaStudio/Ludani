package fr.onsiea.engine.utils.maths.view;

import org.joml.Matrix4f;
import org.joml.Vector2i;

public class View2i
{
	private final static float	defaultAngleDEGREES	= 0.0f;
	private final static float	defaultAngleRADIANS	= (float) Math.toRadians(View2i.defaultAngleDEGREES);

	public final static Matrix4f view(Vector2i positionIn)
	{
		return new Matrix4f().identity()
				.rotateXYZ(View2i.defaultAngleRADIANS, View2i.defaultAngleRADIANS, View2i.defaultAngleRADIANS)
				.rotateZ((float) Math.toRadians(0.0f)).translate(-positionIn.x(), -positionIn.y(), 0.0f)
				.scale(1.0f, 1.0f, 1.0f);
	}

	public final static Matrix4f view(Vector2i positionIn, Vector2i rotationIn)
	{
		return new Matrix4f().identity().rotateXYZ((float) Math.toRadians(rotationIn.x()),
				(float) Math.toRadians(rotationIn.y()), View2i.defaultAngleRADIANS)
				.translate(-positionIn.x(), -positionIn.y(), 0.0f).scale(1.0f);
	}

	public final static Matrix4f view(Vector2i positionIn, Vector2i rotationIn, Vector2i scaleIn)
	{
		return new Matrix4f().identity()
				.rotateXYZ((float) Math.toRadians(rotationIn.x()), (float) Math.toRadians(rotationIn.y()),
						View2i.defaultAngleRADIANS)
				.translate(-positionIn.x(), -positionIn.y(), 0.0f).scale(scaleIn.x(), scaleIn.y(), 0.0f);
	}

	public final static Matrix4f view(Vector2i positionIn, Vector2i rotationIn, int scaleIn)
	{
		return new Matrix4f().identity().rotateXYZ((float) Math.toRadians(rotationIn.x()),
				(float) Math.toRadians(rotationIn.y()), View2i.defaultAngleRADIANS)
				.translate(-positionIn.x(), -positionIn.y(), -0.0f).scale(scaleIn);
	}

	public static Matrix4f view(int xIn, int yIn, int rxIn, int ryIn, int sxIn, int syIn)
	{
		return new Matrix4f().identity()
				.rotateXYZ((float) Math.toRadians(rxIn), (float) Math.toRadians(ryIn), View2i.defaultAngleRADIANS)
				.translate(-xIn, -yIn, 0.0f).scale(sxIn, syIn, 1.0f);
	}

	public final static Matrix4f view(Vector2i positionIn, Matrix4f viewIn)
	{
		return viewIn.identity()
				.rotateXYZ(View2i.defaultAngleRADIANS, View2i.defaultAngleRADIANS, View2i.defaultAngleRADIANS)
				.translate(-positionIn.x(), -positionIn.y(), 0.0f).scale(1.0f, 1.0f, 1.0f);
	}

	public final static Matrix4f view(Vector2i positionIn, Vector2i rotationIn, Matrix4f viewIn)
	{
		return viewIn.identity().rotateXYZ((float) Math.toRadians(rotationIn.x()),
				(float) Math.toRadians(rotationIn.y()), View2i.defaultAngleRADIANS)
				.translate(-positionIn.x(), -positionIn.y(), 0.0f).scale(1.0f);
	}

	public final static Matrix4f view(Vector2i positionIn, Vector2i rotationIn, Vector2i scaleIn, Matrix4f viewIn)
	{
		return viewIn.identity()
				.rotateXYZ((float) Math.toRadians(rotationIn.x()), (float) Math.toRadians(rotationIn.y()),
						View2i.defaultAngleRADIANS)
				.translate(-positionIn.x(), -positionIn.y(), 0.0f).scale(scaleIn.x(), scaleIn.y(), 1.0f);
	}

	public final static Matrix4f view(Vector2i positionIn, Vector2i rotationIn, int scaleIn, Matrix4f viewIn)
	{
		return viewIn.identity().rotateXYZ((float) Math.toRadians(rotationIn.x()),
				(float) Math.toRadians(rotationIn.y()), View2i.defaultAngleRADIANS)
				.translate(-positionIn.x(), -positionIn.y(), 0.0f).scale(scaleIn);
	}

	public static Matrix4f view(int xIn, int yIn, int rxIn, int ryIn, int sxIn, int syIn, Matrix4f viewIn)
	{
		return viewIn.identity()
				.rotateXYZ((float) Math.toRadians(rxIn), (float) Math.toRadians(ryIn), View2i.defaultAngleRADIANS)
				.translate(-xIn, -yIn, 0.0f).scale(sxIn, syIn, 1.0f);
	}
}