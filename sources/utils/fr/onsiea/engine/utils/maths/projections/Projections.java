package fr.onsiea.engine.utils.maths.projections;

import org.joml.Matrix4f;

import fr.onsiea.engine.client.graphics.GraphicsConstants;
import fr.onsiea.engine.utils.maths.MathUtils;

public class Projections
{
	public final Matrix4f of2(float fov, float width, float height, float zNear, float zFar)
	{
		final var	aspectRatio			= width / height;
		final var	y_scale				= MathUtils.coTangent(MathUtils.degreesToRadians(fov / 2f));
		//float y_scale = (float) ((1f / Math.tan(Math.toRadians(fieldOfView / 2f))) * aspectRatio);
		final var	x_scale				= y_scale / aspectRatio;
		final var	frustum_length		= zFar - zNear;

		final var	projectionMatrix	= new Matrix4f();
		projectionMatrix.identity();

		projectionMatrix.m00(x_scale);
		projectionMatrix.m11(y_scale);
		projectionMatrix.m22(-((zFar + zNear) / frustum_length));
		projectionMatrix.m23(-1);
		projectionMatrix.m32(-(2 * zNear * zFar / frustum_length));
		projectionMatrix.m33(0);

		return projectionMatrix;
	}

	public static Matrix4f of(final float widthIn, final float heightIn, final float fovIn, final float zNearIn,
			final float zFarIn)
	{
		final var	aspectRatio			= widthIn / heightIn;

		final var	projectionMatrix	= new Matrix4f();

		projectionMatrix.identity();

		projectionMatrix.perspective(fovIn, aspectRatio, zNearIn, zFarIn);

		return projectionMatrix;
	}

	public static Matrix4f of(final float widthIn, final float heightIn, final float fovIn, final float zNearIn,
			final float zFarIn, Matrix4f transposeIn)
	{
		final var aspectRatio = widthIn / heightIn;

		transposeIn.identity();

		transposeIn.perspective(fovIn, aspectRatio, zNearIn, zFarIn);

		return transposeIn;
	}

	/**
	 *
	 * @param widthIn (window or framebuffer width)
	 * @param heightIn (window or framebuffer height)
	 * @return projection with FOV, ZNEAR and ZFAR of GraphicsConstants class
	 */
	public static Matrix4f of(int widthIn, int heightIn)
	{
		final var	aspectRatio			= widthIn / heightIn;

		final var	projectionMatrix	= new Matrix4f();

		projectionMatrix.identity();

		projectionMatrix.perspective(GraphicsConstants.FOV, aspectRatio, GraphicsConstants.ZNEAR,
				GraphicsConstants.ZFAR);

		return projectionMatrix;
	}

	/**
	 *
	 * @param widthIn (window or framebuffer width)
	 * @param heightIn (window or framebuffer height)
	 * @return projection with FOV, ZNEAR and ZFAR of GraphicsConstants class
	 */
	public static Matrix4f of(int widthIn, int heightIn, Matrix4f transposeIn)
	{
		final var aspectRatio = widthIn / heightIn;

		transposeIn.identity();

		transposeIn.perspective(GraphicsConstants.FOV, aspectRatio, GraphicsConstants.ZNEAR, GraphicsConstants.ZFAR);

		return transposeIn;
	}
}
