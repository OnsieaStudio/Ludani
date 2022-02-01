package fr.onsiea.engine.client.graphics.opengl.shader.uniform;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryStack;

import fr.onsiea.engine.client.graphics.opengl.shader.Shader;

public class UniformMatrix4f
{
	public final static void load(int locationIn, Matrix4f valueIn)
	{
		try (var stack = MemoryStack.stackPush())
		{
			final var buffer = stack.mallocFloat(16);

			valueIn.get(buffer);

			GL20.glUniformMatrix4fv(locationIn, false, buffer);
		}
	}

	public final static void loadTranspose(int locationIn, Matrix4f valueIn)
	{
		try (var stack = MemoryStack.stackPush())
		{
			final var buffer = stack.mallocFloat(16);

			valueIn.get(buffer);

			GL20.glUniformMatrix4fv(locationIn, true, buffer);
		}
	}

	private Shader	parent;
	private int		location;

	public UniformMatrix4f(Shader parentIn, String nameIn)
	{
		this.parent(parentIn);
		this.location(parentIn.uniformLocation(nameIn));
	}

	public Shader load(Matrix4f valueIn)
	{
		UniformMatrix4f.load(this.location(), valueIn);

		return this.parent();
	}

	public Shader loadTranspose(Matrix4f valueIn)
	{
		UniformMatrix4f.loadTranspose(this.location(), valueIn);

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