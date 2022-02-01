package fr.onsiea.engine.client.graphics.opengl.shader.uniform;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL20;

import fr.onsiea.engine.client.graphics.opengl.shader.Shader;

public class UniformVector3f
{
	public final static void load(int locationIn, Vector3f valueIn)
	{
		GL20.glUniform3f(locationIn, valueIn.x(), valueIn.y(), valueIn.z());
	}

	public final static void load(int locationIn, float xIn, float yIn, float zIn)
	{
		GL20.glUniform3f(locationIn, xIn, yIn, zIn);
	}

	private Shader	parent;
	private int		location;

	public UniformVector3f(Shader parentIn, String nameIn)
	{
		this.parent(parentIn);
		this.location(parentIn.uniformLocation(nameIn));
	}

	public Shader load(Vector3f valueIn)
	{
		GL20.glUniform3f(this.location(), valueIn.x(), valueIn.y(), valueIn.z());

		return this.parent();
	}

	public Shader load(float xIn, float yIn, float zIn)
	{
		GL20.glUniform3f(this.location(), xIn, yIn, zIn);

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