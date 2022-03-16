package fr.onsiea.engine.utils.maths.view;

import org.joml.Matrix4d;
import org.joml.Vector3d;

public class View3d
{
	private final static double	defaultAngleDEGREES	= 0.0f;
	private final static double	defaultAngleRADIANS	= Math.toRadians(View3d.defaultAngleDEGREES);

	public final static Matrix4d view(Vector3d positionIn)
	{
		return new Matrix4d().identity()
				.rotateXYZ(View3d.defaultAngleRADIANS, View3d.defaultAngleRADIANS, View3d.defaultAngleRADIANS)
				.rotateZ(Math.toRadians(0.0f)).translate(-positionIn.x(), -positionIn.y(), -positionIn.z())
				.scale(1.0f, 1.0f, 1.0f);
	}

	public final static Matrix4d view(Vector3d positionIn, Vector3d rotationIn)
	{
		return new Matrix4d().identity()
				.rotateXYZ(Math.toRadians(rotationIn.x()), Math.toRadians(rotationIn.y()),
						Math.toRadians(rotationIn.z()))
				.translate(-positionIn.x(), -positionIn.y(), -positionIn.z()).scale(1.0d);
	}

	public final static Matrix4d view(Vector3d positionIn, Vector3d rotationIn, Vector3d scaleIn)
	{
		return new Matrix4d().identity()
				.rotateXYZ(Math.toRadians(rotationIn.x()), Math.toRadians(rotationIn.y()),
						Math.toRadians(rotationIn.z()))
				.translate(-positionIn.x(), -positionIn.y(), -positionIn.z())
				.scale(scaleIn.x(), scaleIn.y(), scaleIn.z());
	}

	public final static Matrix4d view(Vector3d positionIn, Vector3d rotationIn, double scaleIn)
	{
		return new Matrix4d().identity()
				.rotateXYZ(Math.toRadians(rotationIn.x()), Math.toRadians(rotationIn.y()),
						Math.toRadians(rotationIn.z()))
				.translate(-positionIn.x(), -positionIn.y(), -positionIn.z()).scale(scaleIn);
	}

	public static Matrix4d view(double xIn, double yIn, double zIn, double rxIn, double ryIn, double rzIn, double sxIn,
			double syIn, double szIn)
	{
		return new Matrix4d().identity().rotateXYZ(Math.toRadians(rxIn), Math.toRadians(ryIn), Math.toRadians(rzIn))
				.translate(-xIn, -yIn, -zIn).scale(sxIn, syIn, szIn);
	}

	public final static Matrix4d view(Vector3d positionIn, Matrix4d viewIn)
	{
		return viewIn.identity()
				.rotateXYZ(View3d.defaultAngleRADIANS, View3d.defaultAngleRADIANS, View3d.defaultAngleRADIANS)
				.translate(-positionIn.x(), -positionIn.y(), -positionIn.z()).scale(1.0f, 1.0f, 1.0f);
	}

	public final static Matrix4d view(Vector3d positionIn, Vector3d rotationIn, Matrix4d viewIn)
	{
		return viewIn.identity()
				.rotateXYZ(Math.toRadians(rotationIn.x()), Math.toRadians(rotationIn.y()),
						Math.toRadians(rotationIn.z()))
				.translate(-positionIn.x(), -positionIn.y(), -positionIn.z()).scale(1.0f);
	}

	public final static Matrix4d view(Vector3d positionIn, Vector3d rotationIn, Vector3d scaleIn, Matrix4d viewIn)
	{
		return viewIn.identity()
				.rotateXYZ(Math.toRadians(rotationIn.x()), Math.toRadians(rotationIn.y()),
						Math.toRadians(rotationIn.z()))
				.translate(-positionIn.x(), -positionIn.y(), -positionIn.z())
				.scale(scaleIn.x(), scaleIn.y(), scaleIn.z());
	}

	public final static Matrix4d view(Vector3d positionIn, Vector3d rotationIn, double scaleIn, Matrix4d viewIn)
	{
		return viewIn.identity()
				.rotateXYZ(Math.toRadians(rotationIn.x()), Math.toRadians(rotationIn.y()),
						Math.toRadians(rotationIn.z()))
				.translate(-positionIn.x(), -positionIn.y(), -positionIn.z()).scale(scaleIn);
	}

	public static Matrix4d view(double xIn, double yIn, double zIn, double rxIn, double ryIn, double rzIn, double sxIn,
			double syIn, double szIn, Matrix4d viewIn)
	{
		return viewIn.identity().rotateXYZ(Math.toRadians(rxIn), Math.toRadians(ryIn), Math.toRadians(rzIn))
				.translate(-xIn, -yIn, -zIn).scale(sxIn, syIn, szIn);
	}
}