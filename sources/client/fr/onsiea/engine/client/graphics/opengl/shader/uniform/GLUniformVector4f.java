package fr.onsiea.engine.client.graphics.opengl.shader.uniform;

import org.joml.Vector4f;
import org.lwjgl.opengl.GL20;

import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderProgram;
import fr.onsiea.engine.client.graphics.shader.IShaderProgram;
import fr.onsiea.engine.client.graphics.shader.IShaderUniform;

public class GLUniformVector4f implements IShaderUniform<Vector4f>
{
	public final static void load(int locationIn, Vector4f valueIn)
	{
		GL20.glUniform4f(locationIn, valueIn.x(), valueIn.y(), valueIn.z(), valueIn.w());
	}

	public final static void load(int locationIn, float xIn, float yIn, float zIn, float wIn)
	{
		GL20.glUniform4f(locationIn, xIn, yIn, zIn, wIn);
	}

	private IShaderProgram	parent;
	private int				location;

	public GLUniformVector4f(GLShaderProgram parentIn, String nameIn)
	{
		this.parent(parentIn);

		this.location(parentIn.uniformLocation(nameIn));
	}

	@Override
	public IShaderProgram load(Vector4f valueIn)
	{
		GL20.glUniform4f(this.location(), valueIn.x(), valueIn.y(), valueIn.z(), valueIn.w());

		return this.parent();
	}

	public IShaderProgram load(float xIn, float yIn, float zIn, float wIn)
	{
		GL20.glUniform4f(this.location(), xIn, yIn, zIn, wIn);

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