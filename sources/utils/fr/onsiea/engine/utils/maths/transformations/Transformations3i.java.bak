package fr.onsiea.engine.maths.transformations;

import org.joml.Matrix4f;
import org.joml.Vector3i;

public class Transformations3i
{
	public final static Matrix4f transformations(Vector3i positionIn)
	{
		return new Matrix4f().identity().translate(positionIn.x(), positionIn.y(), positionIn.z()).scale(1.0f, 1.0f,
				1.0f);
	}

	public final static Matrix4f transformations(Vector3i positionIn, Vector3i scaleIn)
	{
		return new Matrix4f().identity().translate(positionIn.x(), positionIn.y(), positionIn.z()).scale(scaleIn.x(),
				scaleIn.y(), scaleIn.z());
	}

	public final static Matrix4f transformations(Vector3i positionIn, Vector3i rotationIn, Vector3i scaleIn)
	{
		return new Matrix4f().identity().translate(positionIn.x(), positionIn.y(), positionIn.z())
				.rotateX((float) Math.toRadians(rotationIn.x())).rotateY((float) Math.toRadians(rotationIn.y()))
				.rotateZ((float) Math.toRadians(rotationIn.z())).scale(scaleIn.x(), scaleIn.y(), scaleIn.z());
	}

	public final static Matrix4f transformations(Vector3i positionIn, Vector3i rotationIn, float scaleIn)
	{
		return new Matrix4f().identity().translate(positionIn.x(), positionIn.y(), positionIn.z())
				.rotateX((float) Math.toRadians(rotationIn.x())).rotateY((float) Math.toRadians(rotationIn.y()))
				.rotateZ((float) Math.toRadians(rotationIn.z())).scale(scaleIn);
	}
}
