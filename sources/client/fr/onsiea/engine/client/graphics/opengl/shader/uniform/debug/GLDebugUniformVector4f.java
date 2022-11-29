package fr.onsiea.engine.client.graphics.opengl.shader.uniform.debug;

import org.joml.Vector4f;

import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderProgram;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformVector4f;
import fr.onsiea.engine.client.graphics.shader.IShaderProgram;
import fr.onsiea.engine.client.graphics.shader.uniform.IShaderTypedUniform;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PRIVATE)
public class GLDebugUniformVector4f extends GLUniformVector4f implements IShaderTypedUniform<Vector4f>
{
	private IShaderProgram	parent;
	private String			name;

	public GLDebugUniformVector4f(GLShaderProgram parentIn, String nameIn)
	{
		super(parentIn, nameIn);

		this.parent	= parentIn;
		this.name	= nameIn;
	}

	@Override
	public IShaderProgram load(Vector4f valueIn)
	{
		super.load(valueIn);

		System.err.println("[DEBUG-Shader] Loading vector4f \"" + valueIn + "\" into \"" + this.name
				+ "\" uniform of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load(float xIn, float yIn, float zIn, float wIn)
	{
		super.load(xIn, yIn, zIn, wIn);

		System.err.println("[DEBUG-Shader] Loading vector4f  (float xIn, float yIn, float zIn, float wIn) \"" + xIn
				+ ", " + yIn + ", " + zIn + ", " + wIn + "\" into \"" + this.name + "\" uniform of \""
				+ this.parent.name() + "\" shader !");

		return this.parent();
	}
}