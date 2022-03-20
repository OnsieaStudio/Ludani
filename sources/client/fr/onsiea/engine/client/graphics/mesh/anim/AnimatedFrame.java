package fr.onsiea.engine.client.graphics.mesh.anim;

import java.util.Arrays;

import org.joml.Matrix4f;

public class AnimatedFrame
{

	public static final int			MAX_JOINTS		= 150;

	private static final Matrix4f	IDENTITY_MATRIX	= new Matrix4f();

	private final Matrix4f[]		localJointMatrices;

	private final Matrix4f[]		jointMatrices;

	public AnimatedFrame()
	{
		this.localJointMatrices = new Matrix4f[AnimatedFrame.MAX_JOINTS];
		Arrays.fill(this.localJointMatrices, AnimatedFrame.IDENTITY_MATRIX);

		this.jointMatrices = new Matrix4f[AnimatedFrame.MAX_JOINTS];
		Arrays.fill(this.jointMatrices, AnimatedFrame.IDENTITY_MATRIX);
	}

	public Matrix4f[] getLocalJointMatrices()
	{
		return this.localJointMatrices;
	}

	public Matrix4f[] getJointMatrices()
	{
		return this.jointMatrices;
	}

	public void setMatrix(int pos, Matrix4f localJointMatrix, Matrix4f invJointMatrix)
	{
		this.localJointMatrices[pos] = localJointMatrix;
		final var mat = new Matrix4f(localJointMatrix);
		mat.mul(invJointMatrix);
		this.jointMatrices[pos] = mat;
	}
}
