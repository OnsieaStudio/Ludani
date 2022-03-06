package fr.onsiea.engine.utils.maths.transformations;

import org.joml.Matrix4f;
import org.joml.Vector2i;

public class Transformations2i
{
	public final static Matrix4f transformations(Vector2i positionIn)
	{
		return new Matrix4f().identity().translate(positionIn.x(), positionIn.y(), -1.0f).scale(1.0f, 1.0f, 1.0f);
	}

	public final static Matrix4f transformations(Vector2i positionIn, Vector2i scaleIn)
	{
		return new Matrix4f().identity().translate(positionIn.x(), positionIn.y(), 0.0f).scale(scaleIn.x(), scaleIn.y(),
				1.0f);
	}

	public final static Matrix4f transformations(Vector2i positionIn, Vector2i rotationIn, Vector2i scaleIn)
	{
		return new Matrix4f().identity().translate(positionIn.x(), positionIn.y(), 0.0f)
				.rotateX((float) Math.toRadians(rotationIn.x())).rotateY((float) Math.toRadians(rotationIn.y()))
				.scale(scaleIn.x(), scaleIn.y(), 1.0f);
	}

	public final static Matrix4f transformations(Vector2i positionIn, Vector2i rotationIn, float scaleIn)
	{
		return new Matrix4f().identity().translate(positionIn.x(), positionIn.y(), 0.0f)
				.rotateX((float) Math.toRadians(rotationIn.x())).rotateY((float) Math.toRadians(rotationIn.y()))
				.scale(scaleIn);
	}
}
