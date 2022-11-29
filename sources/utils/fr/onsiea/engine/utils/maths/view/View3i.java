package fr.onsiea.engine.utils.maths.view;

import org.joml.Matrix4f;
import org.joml.Vector3i;

public class View3i
{
	private final static float	defaultAngleDEGREES	= 0.0f;
	private final static float	defaultAngleRADIANS	= (int) Math.toRadians(View3i.defaultAngleDEGREES);

	public final static Matrix4f view(Vector3i positionIn)
	{
		return new Matrix4f().identity()
				.rotateXYZ(View3i.defaultAngleRADIANS, View3i.defaultAngleRADIANS, View3i.defaultAngleRADIANS)
				.rotateZ((int) Math.toRadians(0.0f)).translate(-positionIn.x(), -positionIn.y(), -positionIn.z())
				.scale(1);
	}

	public final static Matrix4f view(Vector3i positionIn, Vector3i rotationIn)
	{
		return new Matrix4f().identity()
				.rotateXYZ((int) Math.toRadians(rotationIn.x()), (int) Math.toRadians(rotationIn.y()),
						(int) Math.toRadians(rotationIn.z()))
				.translate(-positionIn.x(), -positionIn.y(), -positionIn.z()).scale(1);
	}

	public final static Matrix4f view(Vector3i positionIn, Vector3i rotationIn, Vector3i scaleIn)
	{
		return new Matrix4f().identity()
				.rotateXYZ((int) Math.toRadians(rotationIn.x()), (int) Math.toRadians(rotationIn.y()),
						(int) Math.toRadians(rotationIn.z()))
				.translate(-positionIn.x(), -positionIn.y(), -positionIn.z())
				.scale(scaleIn.x(), scaleIn.y(), scaleIn.z());
	}

	public final static Matrix4f view(Vector3i positionIn, Vector3i rotationIn, int scaleIn)
	{
		return new Matrix4f().identity()
				.rotateXYZ((int) Math.toRadians(rotationIn.x()), (int) Math.toRadians(rotationIn.y()),
						(int) Math.toRadians(rotationIn.z()))
				.translate(-positionIn.x(), -positionIn.y(), -positionIn.z()).scale(scaleIn);
	}

	public static Matrix4f view(int xIn, int yIn, int zIn, int rxIn, int ryIn, int rzIn, int sxIn, int syIn, int szIn)
	{
		return new Matrix4f().identity()
				.rotateXYZ((int) Math.toRadians(rxIn), (int) Math.toRadians(ryIn), (int) Math.toRadians(rzIn))
				.translate(-xIn, -yIn, -zIn).scale(sxIn, syIn, szIn);
	}

	public final static Matrix4f view(Vector3i positionIn, Matrix4f viewIn)
	{
		return viewIn.identity()
				.rotateXYZ(View3i.defaultAngleRADIANS, View3i.defaultAngleRADIANS, View3i.defaultAngleRADIANS)
				.translate(-positionIn.x(), -positionIn.y(), -positionIn.z()).scale(1);
	}

	public final static Matrix4f view(Vector3i positionIn, Vector3i rotationIn, Matrix4f viewIn)
	{
		return viewIn.identity()
				.rotateXYZ((int) Math.toRadians(rotationIn.x()), (int) Math.toRadians(rotationIn.y()),
						(int) Math.toRadians(rotationIn.z()))
				.translate(-positionIn.x(), -positionIn.y(), -positionIn.z()).scale(1);
	}

	public final static Matrix4f view(Vector3i positionIn, Vector3i rotationIn, Vector3i scaleIn, Matrix4f viewIn)
	{
		return viewIn.identity()
				.rotateXYZ((int) Math.toRadians(rotationIn.x()), (int) Math.toRadians(rotationIn.y()),
						(int) Math.toRadians(rotationIn.z()))
				.translate(-positionIn.x(), -positionIn.y(), -positionIn.z())
				.scale(scaleIn.x(), scaleIn.y(), scaleIn.z());
	}

	public final static Matrix4f view(Vector3i positionIn, Vector3i rotationIn, int scaleIn, Matrix4f viewIn)
	{
		return viewIn.identity()
				.rotateXYZ((int) Math.toRadians(rotationIn.x()), (int) Math.toRadians(rotationIn.y()),
						(int) Math.toRadians(rotationIn.z()))
				.translate(-positionIn.x(), -positionIn.y(), -positionIn.z()).scale(scaleIn);
	}

	public static Matrix4f view(int xIn, int yIn, int zIn, int rxIn, int ryIn, int rzIn, int sxIn, int syIn, int szIn,
			Matrix4f viewIn)
	{
		return viewIn.identity()
				.rotateXYZ((int) Math.toRadians(rxIn), (int) Math.toRadians(ryIn), (int) Math.toRadians(rzIn))
				.translate(-xIn, -yIn, -zIn).scale(sxIn, syIn, szIn);
	}
}