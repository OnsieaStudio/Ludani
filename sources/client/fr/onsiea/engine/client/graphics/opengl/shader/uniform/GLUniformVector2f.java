package fr.onsiea.engine.client.graphics.opengl.shader.uniform;

import org.joml.Vector2f;
import org.lwjgl.opengl.GL20;

import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderProgram;
import fr.onsiea.engine.client.graphics.shader.IShaderProgram;
import fr.onsiea.engine.client.graphics.shader.uniform.IShaderTypedUniform;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PRIVATE)
public class GLUniformVector2f implements IShaderTypedUniform<Vector2f>
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
}