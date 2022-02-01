package fr.onsiea.engine.client.graphics.opengl.shader.uniform;

import org.joml.Vector4f;
import org.lwjgl.opengl.GL20;

import fr.onsiea.engine.client.graphics.opengl.shader.Shader;

public class UniformVector4f
{
	public final static void load(int locationIn, Vector4f valueIn)
	{
		GL20.glUniform4f(locationIn, valueIn.x(), valueIn.y(), valueIn.z(), valueIn.w());
	}

	public final static void load(int locationIn, float xIn, float yIn, float zIn, float wIn)
	{
		GL20.glUniform4f(locationIn, xIn, yIn, zIn, wIn);
	}

	private Shader	parent;
	private int		location;

	public UniformVector4f(Shader parentIn, String nameIn)
	{
		this.parent(parentIn);
		this.location(parentIn.uniformLocation(nameIn));
	}

	public Shader load(Vector4f valueIn)
	{
		GL20.glUniform4f(this.location(), valueIn.x(), valueIn.y(), valueIn.z(), valueIn.w());

		return this.parent();
	}

	public Shader load(float xIn, float yIn, float zIn, float wIn)
	{
		GL20.glUniform4f(this.location(), xIn, yIn, zIn, wIn);

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