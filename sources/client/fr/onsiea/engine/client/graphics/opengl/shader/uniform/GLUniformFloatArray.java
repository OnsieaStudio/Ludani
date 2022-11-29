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
public class GLUniformFloatArray implements IShaderTypedUniform<float[]>
{
	public final static void load(int locationIn, float[] valuesIn)
	{
		GL20.glUniform1fv(locationIn, valuesIn);
	}

	private IShaderProgram	parent;
	private int				location;

	public GLUniformFloatArray(GLShaderProgram parentIn, String nameIn)
	{
		this.parent(parentIn);

		this.location(parentIn.uniformLocation(nameIn));
	}

	@Override
	@NonNull
	public IShaderProgram load(float[] valuesIn)
	{
		GL20.glUniform1fv(this.location(), valuesIn);

		return this.parent();
	}
}