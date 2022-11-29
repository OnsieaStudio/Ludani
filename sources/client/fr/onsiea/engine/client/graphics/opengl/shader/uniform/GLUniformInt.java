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
public class GLUniformInt implements IShaderTypedUniform<Integer>
{
	public final static void load(int locationIn, int valueIn)
	{
		GL20.glUniform1i(locationIn, valueIn);
	}

	private IShaderProgram	parent;
	private int				location;

	public GLUniformInt(GLShaderProgram parentIn, String nameIn)
	{
		this.parent(parentIn);
		this.location(parentIn.uniformLocation(nameIn));
	}

	@Override
	@NonNull
	public IShaderProgram load(Integer valueIn)
	{
		GL20.glUniform1i(this.location(), valueIn);

		return this.parent();
	}
}