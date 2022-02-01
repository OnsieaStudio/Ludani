package fr.onsiea.engine.client.graphics.opengl.shader.uniform;

import org.lwjgl.opengl.GL20;

import fr.onsiea.engine.client.graphics.opengl.shader.Shader;

public class UniformFloat
{
	public final static void load(int locationIn, float valueIn)
	{
		GL20.glUniform1f(locationIn, valueIn);
	}

	private Shader	parent;
	private int		location;

	public UniformFloat(Shader parentIn, String nameIn)
	{
		this.parent(parentIn);
		this.location(parentIn.uniformLocation(nameIn));
	}

	public Shader load(float valueIn)
	{
		GL20.glUniform1f(this.location(), valueIn);

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