package fr.onsiea.engine.utils.maths.view;

import org.joml.Matrix4d;
import org.joml.Vector2d;

public class View2d
{
	private final static double	defaultAngleDEGREES	= 0.0f;
	private final static double	defaultAngleRADIANS	= Math.toRadians(View2d.defaultAngleDEGREES);

	public final static Matrix4d view(Vector2d positionIn)
	{
		return new Matrix4d().identity()
				.rotateXYZ(View2d.defaultAngleRADIANS, View2d.defaultAngleRADIANS, View2d.defaultAngleRADIANS)
				.rotateZ(Math.toRadians(0.0f)).translate(-positionIn.x(), -positionIn.y(), 0.0f)
				.scale(1.0f, 1.0f, 1.0f);
	}

	public final static Matrix4d view(Vector2d positionIn, Vector2d rotationIn)
	{
		return new Matrix4d().identity()
				.rotateXYZ(Math.toRadians(rotationIn.x()), Math.toRadians(rotationIn.y()), View2d.defaultAngleRADIANS)
				.translate(-positionIn.x(), -positionIn.y(), 0.0f).scale(1.0f);
	}

	public final static Matrix4d view(Vector2d positionIn, Vector2d rotationIn, Vector2d scaleIn)
	{
		return new Matrix4d().identity()
				.rotateXYZ(Math.toRadians(rotationIn.x()), Math.toRadians(rotationIn.y()), View2d.defaultAngleRADIANS)
				.translate(-positionIn.x(), -positionIn.y(), 0.0f).scale(scaleIn.x(), scaleIn.y(), 0.0f);
	}

	public final static Matrix4d view(Vector2d positionIn, Vector2d rotationIn, double scaleIn)
	{
		return new Matrix4d().identity()
				.rotateXYZ(Math.toRadians(rotationIn.x()), Math.toRadians(rotationIn.y()), View2d.defaultAngleRADIANS)
				.translate(-positionIn.x(), -positionIn.y(), -0.0f).scale(scaleIn);
	}

	public static Matrix4d view(double xIn, double yIn, double rxIn, double ryIn, double sxIn, double syIn)
	{
		return new Matrix4d().identity()
				.rotateXYZ(Math.toRadians(rxIn), Math.toRadians(ryIn), View2d.defaultAngleRADIANS)
				.translate(-xIn, -yIn, 0.0f).scale(sxIn, syIn, 1.0f);
	}

	public final static Matrix4d view(Vector2d positionIn, Matrix4d viewIn)
	{
		return viewIn.identity()
				.rotateXYZ(View2d.defaultAngleRADIANS, View2d.defaultAngleRADIANS, View2d.defaultAngleRADIANS)
				.translate(-positionIn.x(), -positionIn.y(), 0.0f).scale(1.0f, 1.0f, 1.0f);
	}

	public final static Matrix4d view(Vector2d positionIn, Vector2d rotationIn, Matrix4d viewIn)
	{
		return viewIn.identity()
				.rotateXYZ(Math.toRadians(rotationIn.x()), Math.toRadians(rotationIn.y()), View2d.defaultAngleRADIANS)
				.translate(-positionIn.x(), -positionIn.y(), 0.0f).scale(1.0f);
	}

	public final static Matrix4d view(Vector2d positionIn, Vector2d rotationIn, Vector2d scaleIn, Matrix4d viewIn)
	{
		return viewIn.identity()
				.rotateXYZ(Math.toRadians(rotationIn.x()), Math.toRadians(rotationIn.y()), View2d.defaultAngleRADIANS)
				.translate(-positionIn.x(), -positionIn.y(), 0.0f).scale(scaleIn.x(), scaleIn.y(), 1.0f);
	}

	public final static Matrix4d view(Vector2d positionIn, Vector2d rotationIn, double scaleIn, Matrix4d viewIn)
	{
		return viewIn.identity()
				.rotateXYZ(Math.toRadians(rotationIn.x()), Math.toRadians(rotationIn.y()), View2d.defaultAngleRADIANS)
				.translate(-positionIn.x(), -positionIn.y(), 0.0f).scale(scaleIn);
	}

	public static Matrix4d view(double xIn, double yIn, double rxIn, double ryIn, double sxIn, double syIn,
			Matrix4d viewIn)
	{
		return viewIn.identity().rotateXYZ(Math.toRadians(rxIn), Math.toRadians(ryIn), View2d.defaultAngleRADIANS)
				.translate(-xIn, -yIn, 0.0f).scale(sxIn, syIn, 1.0f);
	}
}