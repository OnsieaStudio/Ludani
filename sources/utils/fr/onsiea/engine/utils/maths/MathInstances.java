package fr.onsiea.engine.utils.maths;

import java.util.Random;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import fr.onsiea.engine.client.graphics.glfw.window.Window;
import fr.onsiea.engine.client.graphics.window.IWindow;
import fr.onsiea.engine.utils.maths.projections.Projections;
import fr.onsiea.engine.utils.maths.transformations.Transformations2f;
import fr.onsiea.engine.utils.maths.transformations.Transformations3f;

public class MathInstances
{
	// ----------------------------------------------------------------------------------------------------------------------------------------------
	// ------------------ Static constantes header
	// ----------------------------------------------------------------------------------------------------------------------------------------------

	private final static Random		RANDOM								= new Random();

	private final static Vector3f	AXE_X								= new Vector3f(1.0f, 0.0f, 0.0f);
	private final static Vector3f	AXE_Y								= new Vector3f(0.0f, 1.0f, 0.0f);
	private final static Vector3f	AXE_Z								= new Vector3f(0.0f, 0.0f, 1.0f);

	private final static Vector2f	ZERO2F								= new Vector2f(0.0f, 0.0f);
	private final static Vector2f	ONE2F								= new Vector2f(1.0f, 1.0f);
	private final static Vector3f	ZERO3F								= new Vector3f(0.0f, 0.0f, 0.0f);
	private final static Vector3f	ONE3F								= new Vector3f(1.0f, 1.0f, 1.0f);
	private final static Vector4f	ZERO4F								= new Vector4f(0.0f, 0.0f, 0.0f, 0.0f);
	private final static Vector4f	ONE4F								= new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);

	private final static double		PI_180								= Math.PI / 180;

	private final static int		VECTOR4F_SIZE_BYTES					= 4 * 4;
	private final static int		MATRIX_SIZE_BYTES					= 4 * MathInstances.VECTOR4F_SIZE_BYTES;
	private final static int		MATRIX_SIZE_FLOATS					= 4 * 4;

	private final static Matrix4f	projectionMatrix					= new Matrix4f();
	private final static Matrix4f	viewMatrix							= new Matrix4f().identity();

	private final static Matrix4f	SIMPLE_TRANSFORMATIONS_MATRIX_2D	= Transformations2f
			.transformations(MathInstances.zero2f(), MathInstances.zero2f(), MathInstances.one2f());
	private final static Matrix4f	SIMPLE_TRANSFORMATIONS_MATRIX_3D	= Transformations3f
			.transformations(MathInstances.zero3f(), MathInstances.zero3f(), MathInstances.one3f());

	private final static float		DEFAULT_FOV							= 70.0f;
	private final static float		DEFAULT_NEAR_PLANE					= 0.1f;
	private final static float		DEFAULT_FAR_PLANE					= 1000.0f;

	// ----------------------------------------------------------------------------------------------------------------------------------------------

	// ----------------------------------------------------------------------------------------------------------------------------------------------
	// ------------------ Static variables header
	// ----------------------------------------------------------------------------------------------------------------------------------------------

	private static float			fov;
	private static float			zNear;
	private static float			zFar;

	// ----------------------------------------------------------------------------------------------------------------------------------------------

	public final static void initialization(IWindow windowIn)
	{
		MathInstances.fov(MathInstances.DEFAULT_FOV);
		MathInstances.zNear(MathInstances.DEFAULT_NEAR_PLANE);
		MathInstances.zFar(MathInstances.DEFAULT_FAR_PLANE);

		Projections.of(windowIn.settings().width(), windowIn.settings().height(), MathInstances.fov(),
				MathInstances.zNear(), MathInstances.zFar(), MathInstances.projectionMatrix());
	}

	public final static void initialization(Window windowIn, float fovIn, float zNearIn, float zFarIn)
	{
		MathInstances.fov(fovIn);
		MathInstances.zNear(zNearIn);
		MathInstances.zFar(zFarIn);

		Projections.of(windowIn.settings().width(), windowIn.settings().height(), MathInstances.fov(),
				MathInstances.zNear(), MathInstances.zFar(), MathInstances.projectionMatrix());
	}

	public final static Random randomSeed(long seedIn)
	{
		MathInstances.random().setSeed(seedIn);

		return MathInstances.random();
	}

	// ----------------------------------------------------------------------------------------------------------------------------------------------
	// ------------------ Methods and Getters | Setters
	// ----------------------------------------------------------------------------------------------------------------------------------------------

	public final static float fov()
	{
		return MathInstances.fov;
	}

	private final static void fov(float fovIn)
	{
		MathInstances.fov = fovIn;
	}

	public final static float zNear()
	{
		return MathInstances.zNear;
	}

	private final static void zNear(float zNearIn)
	{
		MathInstances.zNear = zNearIn;
	}

	public final static float zFar()
	{
		return MathInstances.zFar;
	}

	private final static void zFar(float zFarIn)
	{
		MathInstances.zFar = zFarIn;
	}

	public final static Random random()
	{
		return MathInstances.RANDOM;
	}

	public final static Vector3f axeX()
	{
		return MathInstances.AXE_X;
	}

	public final static Vector3f axeY()
	{
		return MathInstances.AXE_Y;
	}

	public final static Vector3f axeZ()
	{
		return MathInstances.AXE_Z;
	}

	public final static Vector2f zero2f()
	{
		return MathInstances.ZERO2F;
	}

	public final static Vector2f one2f()
	{
		return MathInstances.ONE2F;
	}

	public final static Vector3f zero3f()
	{
		return MathInstances.ZERO3F;
	}

	public final static Vector3f one3f()
	{
		return MathInstances.ONE3F;
	}

	public final static Vector4f zero4f()
	{
		return MathInstances.ZERO4F;
	}

	public final static Vector4f one4f()
	{
		return MathInstances.ONE4F;
	}

	public final static double pi180()
	{
		return MathInstances.PI_180;
	}

	public final static int vector4fSizeBytes()
	{
		return MathInstances.VECTOR4F_SIZE_BYTES;
	}

	public final static int matrixSizeBytes()
	{
		return MathInstances.MATRIX_SIZE_BYTES;
	}

	public final static int matrixSizeFloats()
	{
		return MathInstances.MATRIX_SIZE_FLOATS;
	}

	public final static Matrix4f projectionMatrix()
	{
		return MathInstances.projectionMatrix;
	}

	public final static Matrix4f viewMatrix()
	{
		return MathInstances.viewMatrix;
	}

	public final static Matrix4f simpleTransformationsMatrix2d()
	{
		return MathInstances.SIMPLE_TRANSFORMATIONS_MATRIX_2D;
	}

	public final static Matrix4f simpleTransformationsMatrix3d()
	{
		return MathInstances.SIMPLE_TRANSFORMATIONS_MATRIX_3D;
	}

	public final static float defaultFov()
	{
		return MathInstances.DEFAULT_FOV;
	}

	public final static float defaultNearPlane()
	{
		return MathInstances.DEFAULT_NEAR_PLANE;
	}

	public final static float defaultFarPlane()
	{
		return MathInstances.DEFAULT_FAR_PLANE;
	}

	// ----------------------------------------------------------------------------------------------------------------------------------------------
}