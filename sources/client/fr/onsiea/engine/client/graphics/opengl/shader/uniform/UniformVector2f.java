package fr.onsiea.engine.client.graphics.opengl.shader.uniform;

import org.joml.Vector2f;
import org.lwjgl.opengl.GL20;

import fr.onsiea.engine.client.graphics.opengl.shader.Shader;

public class UniformVector2f
{
	public final static void load(int locationIn, Vector2f valueIn)
	{
		GL20.glUniform2f(locationIn, valueIn.x(), valueIn.y());
	}

	public final static void load(int locationIn, float xIn, float yIn)
	{
		GL20.glUniform2f(locationIn, xIn, yIn);
	}

	private Shader	parent;
	private int		location;

	public UniformVector2f(Shader parentIn, String nameIn)
	{
		this.parent(parentIn);
		this.location(parentIn.uniformLocation(nameIn));
	}

	public Shader load(Vector2f valueIn)
	{
		GL20.glUniform2f(this.location(), valueIn.x(), valueIn.y());

		return this.parent();
	}

	public Shader load(float xIn, float yIn)
	{
		GL20.glUniform2f(this.location(), xIn, yIn);

		return this.parent();
	}

	private final Shader parent()
	{
		return this.parent;
	}

	private final void parent(Shader parentIn)
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