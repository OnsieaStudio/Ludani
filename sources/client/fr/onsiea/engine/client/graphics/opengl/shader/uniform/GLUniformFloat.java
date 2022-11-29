package fr.onsiea.engine.client.graphics.opengl.shader.uniform;

import org.lwjgl.opengl.GL20;

import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderProgram;
import fr.onsiea.engine.client.graphics.shader.IShaderProgram;
import fr.onsiea.engine.client.graphics.shader.uniform.IShaderTypedUniform;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PRIVATE)
public class GLUniformFloat implements IShaderTypedUniform<Float>
{
	public final static void load(int locationIn, float valueIn)
	{
		GL20.glUniform1f(locationIn, valueIn);
	}

	private IShaderProgram	parent;
	private int				location;

	public GLUniformFloat(GLShaderProgram parentIn, String nameIn)
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
}