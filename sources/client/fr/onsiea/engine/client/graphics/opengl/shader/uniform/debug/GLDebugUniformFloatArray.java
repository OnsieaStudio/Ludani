package fr.onsiea.engine.client.graphics.opengl.shader.uniform.debug;

import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderProgram;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformFloatArray;
import fr.onsiea.engine.client.graphics.shader.IShaderProgram;
import fr.onsiea.engine.client.graphics.shader.uniform.IShaderTypedUniform;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PRIVATE)
public class GLDebugUniformFloatArray extends GLUniformFloatArray implements IShaderTypedUniform<float[]>
{
	private IShaderProgram	parent;
	private String			name;

	public GLDebugUniformFloatArray(GLShaderProgram parentIn, String nameIn)
	{
		super(parentIn, nameIn);

		this.parent	= parentIn;
		this.name	= nameIn;
	}

	@Override
	@NonNull
	public IShaderProgram load(float[] valuesIn)
	{
		super.load(valuesIn);

		System.err.println("[DEBUG-Shader] Loading float array of size \"" + valuesIn.length + "\" into \"" + this.name
				+ "\" uniform of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}
}