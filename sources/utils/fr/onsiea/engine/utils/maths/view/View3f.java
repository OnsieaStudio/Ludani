package fr.onsiea.engine.utils.maths.view;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class View3f
{
	private final static float	defaultAngleDEGREES	= 0.0f;
	private final static float	defaultAngleRADIANS	= (float) Math.toRadians(View3f.defaultAngleDEGREES);

	public final static Matrix4f view(Vector3f positionIn)
	{
		return new Matrix4f().identity()
				.rotateXYZ(View3f.defaultAngleRADIANS, View3f.defaultAngleRADIANS, View3f.defaultAngleRADIANS)
				.rotateZ((float) Math.toRadians(0.0f)).translate(-positionIn.x(), -positionIn.y(), -positionIn.z())
				.scale(1.0f);
	}

	public final static Matrix4f view(Vector3f positionIn, Vector3f rotationIn)
	{
		return new Matrix4f().identity()
				.rotateXYZ((float) Math.toRadians(rotationIn.x()), (float) Math.toRadians(rotationIn.y()),
						(float) Math.toRadians(rotationIn.z()))
				.translate(-positionIn.x(), -positionIn.y(), -positionIn.z()).scale(1.0f);
	}

	public final static Matrix4f view(Vector3f positionIn, Vector3f rotationIn, Vector3f scaleIn)
	{
		return new Matrix4f().identity()
				.rotateXYZ((float) Math.toRadians(rotationIn.x()), (float) Math.toRadians(rotationIn.y()),
						(float) Math.toRadians(rotationIn.z()))
				.translate(-positionIn.x(), -positionIn.y(), -positionIn.z())
				.scale(scaleIn.x(), scaleIn.y(), scaleIn.z());
	}

	public final static Matrix4f view(Vector3f positionIn, Vector3f rotationIn, float scaleIn)
	{
		return new Matrix4f().identity()
				.rotateXYZ((float) Math.toRadians(rotationIn.x()), (float) Math.toRadians(rotationIn.y()),
						(float) Math.toRadians(rotationIn.z()))
				.translate(-positionIn.x(), -positionIn.y(), -positionIn.z()).scale(scaleIn);
	}

	public static Matrix4f view(float xIn, float yIn, float zIn, float rxIn, float ryIn, float rzIn, float sxIn,
			float syIn, float szIn)
	{
		return new Matrix4f().identity()
				.rotateXYZ((float) Math.toRadians(rxIn), (float) Math.toRadians(ryIn), (float) Math.toRadians(rzIn))
				.translate(-xIn, -yIn, -zIn).scale(sxIn, syIn, szIn);
	}

	public final static Matrix4f view(Vector3f positionIn, Matrix4f viewIn)
	{
		return viewIn.identity()
				.rotateXYZ(View3f.defaultAngleRADIANS, View3f.defaultAngleRADIANS, View3f.defaultAngleRADIANS)
				.translate(-positionIn.x(), -positionIn.y(), -positionIn.z()).scale(1.0f);
	}

	public final static Matrix4f view(Vector3f positionIn, Vector3f rotationIn, Matrix4f viewIn)
	{
		return viewIn.identity()
				.rotateXYZ((float) Math.toRadians(rotationIn.x()), (float) Math.toRadians(rotationIn.y()),
						(float) Math.toRadians(rotationIn.z()))
				.translate(-positionIn.x(), -positionIn.y(), -positionIn.z()).scale(1.0f);
	}

	public final static Matrix4f view(Vector3f positionIn, Vector3f rotationIn, Vector3f scaleIn, Matrix4f viewIn)
	{
		return viewIn.identity()
				.rotateXYZ((float) Math.toRadians(rotationIn.x()), (float) Math.toRadians(rotationIn.y()),
						(float) Math.toRadians(rotationIn.z()))
				.translate(-positionIn.x(), -positionIn.y(), -positionIn.z())
				.scale(scaleIn.x(), scaleIn.y(), scaleIn.z());
	}

	public final static Matrix4f view(Vector3f positionIn, Vector3f rotationIn, float scaleIn, Matrix4f viewIn)
	{
		return viewIn.identity()
				.rotateXYZ((float) Math.toRadians(rotationIn.x()), (float) Math.toRadians(rotationIn.y()),
						(float) Math.toRadians(rotationIn.z()))
				.translate(-positionIn.x(), -positionIn.y(), -positionIn.z()).scale(scaleIn);
	}

	public static Matrix4f view(float xIn, float yIn, float zIn, float rxIn, float ryIn, float rzIn, float sxIn,
			float syIn, float szIn, Matrix4f viewIn)
	{
		return viewIn.identity()
				.rotateXYZ((float) Math.toRadians(rxIn), (float) Math.toRadians(ryIn), (float) Math.toRadians(rzIn))
				.translate(-xIn, -yIn, -zIn).scale(sxIn, syIn, szIn);
	}
}