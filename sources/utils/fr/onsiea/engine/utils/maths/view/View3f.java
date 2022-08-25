package fr.onsiea.engine.utils.maths.view;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class View3f
{
	private final static float	defaultAngleDEGREES	= 0.0f;
	private final static float	defaultAngleRADIANS	= (float) Math.toRadians(View3f.defaultAngleDEGREES);

	/**
	 * Work in progress
	 *
	 * @param eye
	 * @param center
	 * @param up
	 * @return
	 */
	public static Matrix4f lookAt(final Vector3f eye, final Vector3f center, final Vector3f up)
	{
		final var	f	= center.sub(eye).normalize();
		var			u	= up.normalize();
		final var	s	= f.cross(u).normalize();
		u = s.cross(f);

		final var result = new Matrix4f().identity();
		result.set(0, 0, s.x);
		result.set(1, 0, s.y);
		result.set(2, 0, s.z);
		result.set(0, 1, u.x);
		result.set(1, 1, u.y);
		result.set(2, 1, u.z);
		result.set(0, 2, -f.x);
		result.set(1, 2, -f.y);
		result.set(2, 2, -f.z);

		return result.translate(new Vector3f(-eye.x, -eye.y, -eye.z));
	}

	public final static Matrix4f view(final Vector3f positionIn)
	{
		return new Matrix4f().identity()
				.rotateXYZ(View3f.defaultAngleRADIANS, View3f.defaultAngleRADIANS, View3f.defaultAngleRADIANS)
				.rotateZ((float) Math.toRadians(0.0f)).translate(-positionIn.x(), -positionIn.y(), -positionIn.z())
				.scale(1.0f);
	}

	public final static Matrix4f view(final Vector3f positionIn, final Vector3f rotationIn)
	{
		return new Matrix4f().identity()
				.rotateXYZ((float) Math.toRadians(rotationIn.x()), (float) Math.toRadians(rotationIn.y()),
						(float) Math.toRadians(rotationIn.z()))
				.translate(-positionIn.x(), -positionIn.y(), -positionIn.z()).scale(1.0f);
	}

	public final static Matrix4f view(final Vector3f positionIn, final Vector3f rotationIn, final Vector3f scaleIn)
	{
		return new Matrix4f().identity()
				.rotateXYZ((float) Math.toRadians(rotationIn.x()), (float) Math.toRadians(rotationIn.y()),
						(float) Math.toRadians(rotationIn.z()))
				.translate(-positionIn.x(), -positionIn.y(), -positionIn.z())
				.scale(scaleIn.x(), scaleIn.y(), scaleIn.z());
	}

	public final static Matrix4f view(final Vector3f positionIn, final Vector3f rotationIn, final float scaleIn)
	{
		return new Matrix4f().identity()
				.rotateXYZ((float) Math.toRadians(rotationIn.x()), (float) Math.toRadians(rotationIn.y()),
						(float) Math.toRadians(rotationIn.z()))
				.translate(-positionIn.x(), -positionIn.y(), -positionIn.z()).scale(scaleIn);
	}

	public static Matrix4f view(final float xIn, final float yIn, final float zIn, final float rxIn, final float ryIn,
			final float rzIn, final float sxIn, final float syIn, final float szIn)
	{
		return new Matrix4f().identity()
				.rotateXYZ((float) Math.toRadians(rxIn), (float) Math.toRadians(ryIn), (float) Math.toRadians(rzIn))
				.translate(-xIn, -yIn, -zIn).scale(sxIn, syIn, szIn);
	}

	public final static Matrix4f view(final Vector3f positionIn, final Matrix4f viewIn)
	{
		return viewIn.identity()
				.rotateXYZ(View3f.defaultAngleRADIANS, View3f.defaultAngleRADIANS, View3f.defaultAngleRADIANS)
				.translate(-positionIn.x(), -positionIn.y(), -positionIn.z()).scale(1.0f);
	}

	public final static Matrix4f view(final Vector3f positionIn, final Vector3f rotationIn, final Matrix4f viewIn)
	{
		return viewIn.rotationX((float) Math.toRadians(rotationIn.x)).rotateY((float) Math.toRadians(rotationIn.y))
				.translate(-positionIn.x, -positionIn.y, -positionIn.z);

		/**
		 * viewIn.identity()
		 * .rotateXYZ((float) Math.toRadians(rotationIn.x()), (float)
		 * Math.toRadians(rotationIn.y()),
		 * (float) Math.toRadians(rotationIn.z()))
		 * .translate(-positionIn.x(), -positionIn.y(), -positionIn.z()).scale(1.0f);
		 **/
	}

	public final static Matrix4f view(final Vector3f positionIn, final Vector3f rotationIn, final Vector3f scaleIn,
			final Matrix4f viewIn)
	{
		return viewIn.identity()
				.rotateXYZ((float) Math.toRadians(rotationIn.x()), (float) Math.toRadians(rotationIn.y()),
						(float) Math.toRadians(rotationIn.z()))
				.translate(-positionIn.x(), -positionIn.y(), -positionIn.z())
				.scale(scaleIn.x(), scaleIn.y(), scaleIn.z());
	}

	public final static Matrix4f view(final Vector3f positionIn, final Vector3f rotationIn, final float scaleIn,
			final Matrix4f viewIn)
	{
		return viewIn.identity()
				.rotateXYZ((float) Math.toRadians(rotationIn.x()), (float) Math.toRadians(rotationIn.y()),
						(float) Math.toRadians(rotationIn.z()))
				.translate(-positionIn.x(), -positionIn.y(), -positionIn.z()).scale(scaleIn);
	}

	public static Matrix4f view(final float xIn, final float yIn, final float zIn, final float rxIn, final float ryIn,
			final float rzIn, final float sxIn, final float syIn, final float szIn, final Matrix4f viewIn)
	{
		return viewIn.identity()
				.rotateXYZ((float) Math.toRadians(rxIn), (float) Math.toRadians(ryIn), (float) Math.toRadians(rzIn))
				.translate(-xIn, -yIn, -zIn).scale(sxIn, syIn, szIn);
	}
}