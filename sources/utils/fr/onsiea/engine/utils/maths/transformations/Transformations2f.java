package fr.onsiea.engine.utils.maths.transformations;

import org.joml.Matrix4f;
import org.joml.Vector2f;

public class Transformations2f
{
	public final static Matrix4f transformations(Vector2f positionIn)
	{
		return new Matrix4f().identity().translate(positionIn.x(), positionIn.y(), -1.0f).scale(1.0f, 1.0f, 1.0f);
	}

	public final static Matrix4f transformations(Vector2f positionIn, Vector2f scaleIn)
	{
		return new Matrix4f().identity().translate(positionIn.x(), positionIn.y(), 0.0f).scale(scaleIn.x(), scaleIn.y(),
				1.0f);
	}

	public final static Matrix4f transformations(Vector2f positionIn, Vector2f rotationIn, Vector2f scaleIn)
	{
		return new Matrix4f().identity().translate(positionIn.x(), positionIn.y(), 0.0f)
				.rotateX((float) Math.toRadians(-rotationIn.x())).rotateY((float) Math.toRadians(-rotationIn.y()))
				.scale(scaleIn.x(), scaleIn.y(), 1.0f);
	}

	public final static Matrix4f transformations(Vector2f positionIn, Vector2f rotationIn, float scaleIn)
	{
		return new Matrix4f().identity().translate(positionIn.x(), positionIn.y(), 0.0f)
				.rotateX((float) Math.toRadians(-rotationIn.x())).rotateY((float) Math.toRadians(-rotationIn.y()))
				.scale(scaleIn);
	}

	public static Matrix4f transformations(float xIn, float yIn, float rxIn, float ryIn, float sxIn, float syIn)
	{
		return new Matrix4f().identity().translate(xIn, yIn, 0.0f).rotateX((float) Math.toRadians(rxIn))
				.rotateY((float) Math.toRadians(ryIn)).scale(sxIn, syIn, 1.0f);
	}

	/**
	 * @param positionIn
	 * @param fIn
	 * @param f2In
	 * @param scaleIn
	 * @param transformationsMatrixIn
	 * @return
	 */
	public static Matrix4f transformations(Vector2f positionIn, float rxIn, float ryIn, Vector2f scaleIn,
			Matrix4f transformationsMatrixIn)
	{
		return transformationsMatrixIn.identity().translate(positionIn.x(), positionIn.y(), 0.0f)
				.rotateX((float) Math.toRadians(rxIn)).rotateY((float) Math.toRadians(ryIn))
				.scale(scaleIn.x(), scaleIn.y(), 1.0f);
	}
}
