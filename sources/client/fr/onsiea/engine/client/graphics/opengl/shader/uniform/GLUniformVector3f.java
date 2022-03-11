package fr.onsiea.engine.client.graphics.opengl.shader.uniform;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL20;

import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderProgram;
import fr.onsiea.engine.client.graphics.shader.IShaderProgram;
import fr.onsiea.engine.client.graphics.shader.IShaderUniform;

public class GLUniformVector3f implements IShaderUniform<Vector3f>
{
	public final static void load(int locationIn, Vector3f valueIn)
	{
		GL20.glUniform3f(locationIn, valueIn.x(), valueIn.y(), valueIn.z());
	}

	public final static void load(int locationIn, float xIn, float yIn, float zIn)
	{
		GL20.glUniform3f(locationIn, xIn, yIn, zIn);
	}

	private IShaderProgram	parent;
	private int				location;

	public GLUniformVector3f(GLShaderProgram parentIn, String nameIn)
	{
		this.parent(parentIn);
		this.location(parentIn.uniformLocation(nameIn));
	}

	@Override
	public IShaderProgram load(Vector3f valueIn)
	{
		GL20.glUniform3f(this.location(), valueIn.x(), valueIn.y(), valueIn.z());

		return this.parent();
	}

	public IShaderProgram load(float xIn, float yIn, float zIn)
	{
		GL20.glUniform3f(this.location(), xIn, yIn, zIn);

		return this.parent();
	}

	private final IShaderProgram parent()
	{
		return this.parent;
	}

	private final void parent(IShaderProgram parentIn)
	{
		this.parent = parentIn;
	}

	private final int location()
	{
		return this.location;
	}

	private final void location(int locationIn)
	{
		this.location = locationIn;
	}
}