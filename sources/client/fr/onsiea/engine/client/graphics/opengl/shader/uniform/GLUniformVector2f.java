package fr.onsiea.engine.client.graphics.opengl.shader.uniform;

import org.joml.Vector2f;
import org.lwjgl.opengl.GL20;

import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderProgram;
import fr.onsiea.engine.client.graphics.shader.IShaderProgram;
import fr.onsiea.engine.client.graphics.shader.IShaderUniform;

public class GLUniformVector2f implements IShaderUniform<Vector2f>
{
	public final static void load(int locationIn, Vector2f valueIn)
	{
		GL20.glUniform2f(locationIn, valueIn.x(), valueIn.y());
	}

	public final static void load(int locationIn, float xIn, float yIn)
	{
		GL20.glUniform2f(locationIn, xIn, yIn);
	}

	private IShaderProgram	parent;
	private int				location;

	public GLUniformVector2f(GLShaderProgram parentIn, String nameIn)
	{
		this.parent(parentIn);
		this.location(parentIn.uniformLocation(nameIn));
	}

	@Override
	public IShaderProgram load(Vector2f valueIn)
	{
		GL20.glUniform2f(this.location(), valueIn.x(), valueIn.y());

		return this.parent();
	}

	public IShaderProgram load(float xIn, float yIn)
	{
		GL20.glUniform2f(this.location(), xIn, yIn);

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