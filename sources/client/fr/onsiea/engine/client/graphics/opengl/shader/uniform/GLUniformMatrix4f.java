package fr.onsiea.engine.client.graphics.opengl.shader.uniform;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryStack;

import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderProgram;
import fr.onsiea.engine.client.graphics.shader.IShaderProgram;
import fr.onsiea.engine.client.graphics.shader.uniform.IShaderTypedUniform;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PRIVATE)
public class GLUniformMatrix4f implements IShaderTypedUniform<Matrix4f>
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

	private IShaderProgram	parent;
	private int				location;

	public GLUniformMatrix4f(GLShaderProgram parentIn, String nameIn)
	{
		this.parent(parentIn);
		this.location(parentIn.uniformLocation(nameIn));
	}

	@Override
	public IShaderProgram load(Matrix4f valueIn)
	{
		GLUniformMatrix4f.load(this.location(), valueIn);

		return this.parent();
	}

	public IShaderProgram loadTranspose(Matrix4f valueIn)
	{
		GLUniformMatrix4f.loadTranspose(this.location(), valueIn);

		return this.parent();
	}
}