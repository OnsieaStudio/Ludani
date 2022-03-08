package fr.onsiea.engine.client.graphics.opengl.shader.uniform;

import org.lwjgl.opengl.GL20;

import fr.onsiea.engine.client.graphics.opengl.shader.Shader;
import fr.onsiea.engine.client.graphics.shader.IShaderProgram;
import fr.onsiea.engine.client.graphics.shader.IShaderUniform;
import lombok.NonNull;

public class GLUniformFloat implements IShaderUniform<Float>
{
	public final static void load(int locationIn, float valueIn)
	{
		GL20.glUniform1f(locationIn, valueIn);
	}

	private IShaderProgram	parent;
	private int				location;

	public GLUniformFloat(Shader parentIn, String nameIn)
	{
		this.parent(parentIn);

		this.location(parentIn.uniformLocation(nameIn));
	}

	@Override
	@NonNull
	public IShaderProgram load(Float valueIn)
	{
		GL20.glUniform1f(this.location(), valueIn);

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