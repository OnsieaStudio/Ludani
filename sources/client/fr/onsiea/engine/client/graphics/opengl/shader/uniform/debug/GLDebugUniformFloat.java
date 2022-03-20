package fr.onsiea.engine.client.graphics.opengl.shader.uniform.debug;

import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderProgram;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformFloat;
import fr.onsiea.engine.client.graphics.shader.IShaderProgram;
import fr.onsiea.engine.client.graphics.shader.uniform.IShaderTypedUniform;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PRIVATE)
public class GLDebugUniformFloat extends GLUniformFloat implements IShaderTypedUniform<Float>
{
	private IShaderProgram	parent;
	private String			name;

	public GLDebugUniformFloat(GLShaderProgram parentIn, String nameIn)
	{
		super(parentIn, nameIn);

		this.parent	= parentIn;
		this.name	= nameIn;
	}

	@Override
	@NonNull
	public IShaderProgram load(Float valueIn)
	{
		super.load(valueIn);

		System.err.println("[DEBUG-Shader] Loading float \"" + valueIn + "\" into \"" + this.name + "\" uniform of \""
				+ this.parent.name() + "\" shader !");

		return this.parent();
	}
}