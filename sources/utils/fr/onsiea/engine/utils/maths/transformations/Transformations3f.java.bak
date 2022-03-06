package fr.onsiea.engine.maths.transformations;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transformations3f
{
	public final static Matrix4f transformations(Vector3f positionIn)
	{
		return new Matrix4f().identity().translate(positionIn.x(), positionIn.y(), positionIn.z()).scale(1.0f, 1.0f,
				1.0f);
	}

	public final static Matrix4f transformations(Vector3f positionIn, Vector3f scaleIn)
	{
		return new Matrix4f().identity().translate(positionIn.x(), positionIn.y(), positionIn.z()).scale(scaleIn.x(),
				scaleIn.y(), scaleIn.z());
	}

	public final static Matrix4f transformations(Vector3f positionIn, Vector3f rotationIn, Vector3f scaleIn)
	{
		return new Matrix4f().identity().translate(positionIn.x(), positionIn.y(), positionIn.z())
				.rotateX((float) Math.toRadians(rotationIn.x())).rotateY((float) Math.toRadians(rotationIn.y()))
				.rotateZ((float) Math.toRadians(rotationIn.z())).scale(scaleIn.x(), scaleIn.y(), scaleIn.z());
	}

	public final static Matrix4f transformations(Vector3f positionIn, Vector3f rotationIn, float scaleIn)
	{
		return new Matrix4f().identity().translate(positionIn.x(), positionIn.y(), positionIn.z())
				.rotateX((float) Math.toRadians(rotationIn.x())).rotateY((float) Math.toRadians(rotationIn.y()))
				.rotateZ((float) Math.toRadians(rotationIn.z())).scale(scaleIn);
	}

	public static Matrix4f transformations(float xIn, float yIn, float zIn, float rxIn, float ryIn, float rzIn,
			float sxIn, float syIn, float szIn)
	{
		return new Matrix4f().identity().translate(xIn, yIn, zIn).rotateX((float) Math.toRadians(rxIn))
				.rotateY((float) Math.toRadians(ryIn)).rotateZ((float) Math.toRadians(rzIn)).scale(sxIn, syIn, szIn);
	}

	public final static Matrix4f transformations(Vector3f positionIn, Matrix4f transformationsIn)
	{
		return transformationsIn.identity().translate(positionIn.x(), positionIn.y(), positionIn.z()).scale(1.0f, 1.0f,
				1.0f);
	}

	public final static Matrix4f transformations(Vector3f positionIn, Vector3f scaleIn, Matrix4f transformationsIn)
	{
		return transformationsIn.identity().translate(positionIn.x(), positionIn.y(), positionIn.z()).scale(scaleIn.x(),
				scaleIn.y(), scaleIn.z());
	}

	public final static Matrix4f transformations(Vector3f positionIn, Vector3f rotationIn, Vector3f scaleIn,
			Matrix4f transformationsIn)
	{
		return transformationsIn.identity().translate(positionIn.x(), positionIn.y(), positionIn.z())
				.rotateX((float) Math.toRadians(rotationIn.x())).rotateY((float) Math.toRadians(rotationIn.y()))
				.rotateZ((float) Math.toRadians(rotationIn.z())).scale(scaleIn.x(), scaleIn.y(), scaleIn.z());
	}

	public final static Matrix4f transformations(Vector3f positionIn, Vector3f rotationIn, float scaleIn,
			Matrix4f transformationsIn)
	{
		return transformationsIn.identity().translate(positionIn.x(), positionIn.y(), positionIn.z())
				.rotateX((float) Math.toRadians(rotationIn.x())).rotateY((float) Math.toRadians(rotationIn.y()))
				.rotateZ((float) Math.toRadians(rotationIn.z())).scale(scaleIn);
	}

	public static Matrix4f transformations(float xIn, float yIn, float zIn, float rxIn, float ryIn, float rzIn,
			float sxIn, float syIn, float szIn, Matrix4f transformationsIn)
	{
		return transformationsIn.identity().translate(xIn, yIn, zIn).rotateX((float) Math.toRadians(rxIn))
				.rotateY((float) Math.toRadians(ryIn)).rotateZ((float) Math.toRadians(rzIn)).scale(sxIn, syIn, szIn);
	}
}