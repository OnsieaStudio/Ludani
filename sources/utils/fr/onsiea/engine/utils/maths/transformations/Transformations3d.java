package fr.onsiea.engine.utils.maths.transformations;

import org.joml.Matrix4d;
import org.joml.Vector3d;

public class Transformations3d
{
	public final static Matrix4d transformations(Vector3d positionIn)
	{
		return new Matrix4d().identity().translate(positionIn.x(), positionIn.y(), positionIn.z()).scale(1.0D, 1.0D,
				1.0D);
	}

	public final static Matrix4d transformations(Vector3d positionIn, Vector3d scaleIn)
	{
		return new Matrix4d().identity().translate(positionIn.x(), positionIn.y(), positionIn.z()).scale(scaleIn.x(),
				scaleIn.y(), scaleIn.z());
	}

	public final static Matrix4d transformations(Vector3d positionIn, Vector3d rotationIn, Vector3d scaleIn)
	{
		return new Matrix4d().identity().translate(positionIn.x(), positionIn.y(), positionIn.z())
				.rotateX(Math.toRadians(rotationIn.x())).rotateY(Math.toRadians(rotationIn.y()))
				.rotateZ(Math.toRadians(rotationIn.z())).scale(scaleIn.x(), scaleIn.y(), scaleIn.z());
	}

	public final static Matrix4d transformations(Vector3d positionIn, Vector3d rotationIn, double scaleIn)
	{
		return new Matrix4d().identity().translate(positionIn.x(), positionIn.y(), positionIn.z())
				.rotateX(Math.toRadians(rotationIn.x())).rotateY(Math.toRadians(rotationIn.y()))
				.rotateZ(Math.toRadians(rotationIn.z())).scale(scaleIn);
	}

	public static Matrix4d transformations(double xIn, double yIn, double zIn, double rxIn, double ryIn, double rzIn,
			double sxIn, double syIn, double szIn)
	{
		return new Matrix4d().identity().translate(xIn, yIn, zIn).rotateX(Math.toRadians(rxIn))
				.rotateY(Math.toRadians(ryIn)).rotateZ(Math.toRadians(rzIn)).scale(sxIn, syIn, szIn);
	}

	public final static Matrix4d transformations(Vector3d positionIn, Matrix4d transformationsIn)
	{
		return transformationsIn.identity().translate(positionIn.x(), positionIn.y(), positionIn.z()).scale(1.0D, 1.0D,
				1.0D);
	}

	public final static Matrix4d transformations(Vector3d positionIn, Vector3d scaleIn, Matrix4d transformationsIn)
	{
		return transformationsIn.identity().translate(positionIn.x(), positionIn.y(), positionIn.z()).scale(scaleIn.x(),
				scaleIn.y(), scaleIn.z());
	}

	public final static Matrix4d transformations(Vector3d positionIn, Vector3d rotationIn, Vector3d scaleIn,
			Matrix4d transformationsIn)
	{
		return transformationsIn.identity().translate(positionIn.x(), positionIn.y(), positionIn.z())
				.rotateX(Math.toRadians(rotationIn.x())).rotateY(Math.toRadians(rotationIn.y()))
				.rotateZ(Math.toRadians(rotationIn.z())).scale(scaleIn.x(), scaleIn.y(), scaleIn.z());
	}

	public final static Matrix4d transformations(Vector3d positionIn, Vector3d rotationIn, double scaleIn,
			Matrix4d transformationsIn)
	{
		return transformationsIn.identity().translate(positionIn.x(), positionIn.y(), positionIn.z())
				.rotateX(Math.toRadians(rotationIn.x())).rotateY(Math.toRadians(rotationIn.y()))
				.rotateZ(Math.toRadians(rotationIn.z())).scale(scaleIn);
	}

	public static Matrix4d transformations(double xIn, double yIn, double zIn, double rxIn, double ryIn, double rzIn,
			double sxIn, double syIn, double szIn, Matrix4d transformationsIn)
	{
		return transformationsIn.identity().translate(xIn, yIn, zIn).rotateX(Math.toRadians(rxIn))
				.rotateY(Math.toRadians(ryIn)).rotateZ(Math.toRadians(rzIn)).scale(sxIn, syIn, szIn);
	}
}