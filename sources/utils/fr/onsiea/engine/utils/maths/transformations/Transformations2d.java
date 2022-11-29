package fr.onsiea.engine.utils.maths.transformations;

import org.joml.Matrix4d;
import org.joml.Vector2d;

public class Transformations2d
{
	public final static Matrix4d transformations(Vector2d positionIn)
	{
		return new Matrix4d().identity().translate(positionIn.x(), positionIn.y(), -1.0D).scale(1.0D, 1.0D, 1.0D);
	}

	public final static Matrix4d transformations(Vector2d positionIn, Vector2d scaleIn)
	{
		return new Matrix4d().identity().translate(positionIn.x(), positionIn.y(), 0.0D).scale(scaleIn.x(), scaleIn.y(),
				1.0D);
	}

	public final static Matrix4d transformations(Vector2d positionIn, Vector2d rotationIn, Vector2d scaleIn)
	{
		return new Matrix4d().identity().translate(positionIn.x(), positionIn.y(), 0.0D)
				.rotateX(Math.toRadians(-rotationIn.x())).rotateY(Math.toRadians(-rotationIn.y()))
				.scale(scaleIn.x(), scaleIn.y(), 1.0D);
	}

	public final static Matrix4d transformations(Vector2d positionIn, Vector2d rotationIn, double scaleIn)
	{
		return new Matrix4d().identity().translate(positionIn.x(), positionIn.y(), 0.0D)
				.rotateX(Math.toRadians(-rotationIn.x())).rotateY(Math.toRadians(-rotationIn.y())).scale(scaleIn);
	}

	public static Matrix4d transformations(double xIn, double yIn, double rxIn, double ryIn, double sxIn, double syIn)
	{
		return new Matrix4d().identity().translate(xIn, yIn, 0.0D).rotateX(Math.toRadians(rxIn))
				.rotateY(Math.toRadians(ryIn)).scale(sxIn, syIn, 1.0D);
	}

	/**
	 * @param positionIn
	 * @param rxIn
	 * @param ryIn
	 * @param scaleIn
	 * @param transformationsMatrixIn
	 * @return
	 */
	public static Matrix4d transformations(Vector2d positionIn, int rxIn, int ryIn, Vector2d scaleIn, Matrix4d matrixIn)
	{
		return matrixIn.identity().translate(positionIn.x(), positionIn.y(), 0.0D).rotateX(Math.toRadians(rxIn))
				.rotateY(Math.toRadians(ryIn)).scale(scaleIn.x(), scaleIn.y(), 1.0D);
	}
}
