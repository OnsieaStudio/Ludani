package fr.onsiea.engine.utils.maths.projections;

import org.joml.Matrix4f;

import fr.onsiea.engine.utils.maths.MathUtils;

public class Projections
{
	/**
	 * Alternative method, more artisanal, of calculation of matrix projection,
	 * different from the method of which uses the "perspective(fov, aspectRatio,
	 * zNear, zFar)" method of JOML
	 *
	 * @param fovIn
	 * @param widthIn
	 * @param heightIn
	 * @param zNearIn
	 * @param zFarIn
	 * @return new projection matrix with parameters
	 */
	public final Matrix4f ofAlternative(final float fovIn, final float widthIn, final float heightIn,
			final float zNearIn, final float zFarIn)
	{
		final var	aspectRatio			= widthIn / heightIn;
		final var	y_scale				= MathUtils.coTangent(MathUtils.degreesToRadians(fovIn / 2f));
		// float y_scale = (float) ((1f / Math.tan(Math.toRadians(fieldOfView / 2f))) *
		// aspectRatio);
		final var	x_scale				= y_scale / aspectRatio;
		final var	frustum_length		= zFarIn - zNearIn;

		final var	projectionMatrix	= new Matrix4f();
		projectionMatrix.identity();

		projectionMatrix.m00(x_scale);
		projectionMatrix.m11(y_scale);
		projectionMatrix.m22(-((zFarIn + zNearIn) / frustum_length));
		projectionMatrix.m23(-1);
		projectionMatrix.m32(-(2 * zNearIn * zFarIn / frustum_length));
		projectionMatrix.m33(0);

		return projectionMatrix;
	}

	/**
	 * Alternative method, more artisanal, of calculation of matrix projection,
	 * different from the method of which uses the "perspective(fov, aspectRatio,
	 * zNear, zFar)" method of JOML
	 *
	 * @param fovIn
	 * @param widthIn
	 * @param heightIn
	 * @param zNearIn
	 * @param zFarIn
	 * @param transposeIn
	 * @return transposed projection matrix with parameters into transposeIn
	 *         matrix4f
	 */
	public final Matrix4f ofAlternative(final float fovIn, final float widthIn, final float heightIn,
			final float zNearIn, final float zFarIn, final Matrix4f transposeIn)
	{
		final var	aspectRatio		= widthIn / heightIn;
		final var	y_scale			= MathUtils.coTangent(MathUtils.degreesToRadians(fovIn / 2f));
		// float y_scale = (float) ((1f / Math.tan(Math.toRadians(fieldOfView / 2f))) *
		// aspectRatio);
		final var	x_scale			= y_scale / aspectRatio;
		final var	frustum_length	= zFarIn - zNearIn;

		transposeIn.identity();

		transposeIn.m00(x_scale);
		transposeIn.m11(y_scale);
		transposeIn.m22(-((zFarIn + zNearIn) / frustum_length));
		transposeIn.m23(-1);
		transposeIn.m32(-(2 * zNearIn * zFarIn / frustum_length));
		transposeIn.m33(0);

		return transposeIn;
	}

	/**
	 * @param widthIn
	 * @param heightIn
	 * @param fovIn
	 * @param zNearIn
	 * @param zFarIn
	 * @return new projection matrix with parameters
	 */
	public static Matrix4f of(final float widthIn, final float heightIn, final float fovIn, final float zNearIn,
			final float zFarIn)
	{
		final var	aspectRatio			= widthIn / heightIn;

		final var	projectionMatrix	= new Matrix4f();

		projectionMatrix.identity();

		projectionMatrix.perspective(fovIn, aspectRatio, zNearIn, zFarIn);

		return projectionMatrix;
	}

	/**
	 * @param widthIn
	 * @param heightIn
	 * @param fovIn
	 * @param zNearIn
	 * @param zFarIn
	 * @param transposeIn
	 * @return transposed projection matrix with parameters into transposeIn
	 *         matrix4f
	 */
	public final static Matrix4f of(final float widthIn, final float heightIn, final float fovIn, final float zNearIn,
			final float zFarIn, final Matrix4f transposeIn)
	{
		final var aspectRatio = widthIn / heightIn;

		transposeIn.identity();

		transposeIn.perspective(fovIn, aspectRatio, zNearIn, zFarIn);

		return transposeIn;
	}

	/**
	 * @param leftIn
	 * @param rightIn
	 * @param bottomIn
	 * @param topIn
	 * @param zNearIn
	 * @param zFarIn
	 * @return new ortho projection matrix with parameters
	 */
	public final static Matrix4f ortho(final float leftIn, final float rightIn, final float bottomIn, final float topIn,
			final float zNearIn, final float zFarIn)
	{
		return new Matrix4f().identity().setOrtho(leftIn, rightIn, bottomIn, topIn, zNearIn, zFarIn);
	}

	/**
	 * @param leftIn
	 * @param rightIn
	 * @param bottomIn
	 * @param topIn
	 * @param zNearIn
	 * @param zFarIn
	 * @return transposed ortho projection matrix with parameters into transposeIn
	 *         matrix4f
	 */
	public final static Matrix4f ortho(final float leftIn, final float rightIn, final float bottomIn, final float topIn,
			final float zNearIn, final float zFarIn, final Matrix4f transposeIn)
	{
		return transposeIn.identity().setOrtho(leftIn, rightIn, bottomIn, topIn, zNearIn, zFarIn);
	}

	/**
	 * @param left
	 * @param right
	 * @param bottom
	 * @param top
	 * @return new 2D ortho projection matrix with parameters
	 */
	public final static Matrix4f ortho2D(final float left, final float right, final float bottom, final float top)
	{
		return new Matrix4f().identity().setOrtho2D(left, right, bottom, top);
	}

	/**
	 * @param left
	 * @param right
	 * @param bottom
	 * @param top
	 * @return transposed 2D ortho projection matrix with parameters into
	 *         transposeIn matrix4f
	 */
	public final Matrix4f ortho2D(final float left, final float right, final float bottom, final float top,
			final Matrix4f transposeIn)
	{
		return transposeIn.identity().setOrtho2D(left, right, bottom, top);
	}
}