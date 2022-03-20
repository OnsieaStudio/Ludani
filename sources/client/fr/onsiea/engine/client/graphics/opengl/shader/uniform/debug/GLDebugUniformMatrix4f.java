package fr.onsiea.engine.client.graphics.opengl.shader.uniform.debug;

import org.joml.Matrix4f;

import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderProgram;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformMatrix4f;
import fr.onsiea.engine.client.graphics.shader.IShaderProgram;
import fr.onsiea.engine.client.graphics.shader.uniform.IShaderTypedUniform;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PRIVATE)
public class GLDebugUniformMatrix4f extends GLUniformMatrix4f implements IShaderTypedUniform<Matrix4f>
{
	private IShaderProgram	parent;
	private String			name;

	public GLDebugUniformMatrix4f(GLShaderProgram parentIn, String nameIn)
	{
		super(parentIn, nameIn);

		this.parent	= parentIn;
		this.name	= nameIn;
	}

	@Override
	public IShaderProgram load(Matrix4f valueIn)
	{
		super.load(valueIn);

		System.err.println("[DEBUG-Shader] Loading matrix4f \"" + GLDebugUniformMatrix4f.toString(valueIn)
				+ "\" into \"" + this.name + "\" uniform of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram loadTranspose(Matrix4f valueIn)
	{
		super.loadTranspose(valueIn);

		System.err.println("[DEBUG-Shader] Loading transpose matrix4f \"" + GLDebugUniformMatrix4f.toString(valueIn)
				+ "\" into \"" + this.name + "\" uniform of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	private final static String toString(Matrix4f valueIn)
	{
		return valueIn.m00() + ", " + valueIn.m01() + ", " + valueIn.m02() + ", " + valueIn.m03() + ",\n"
				+ valueIn.m10() + ", " + valueIn.m11() + ", " + valueIn.m12() + ", " + valueIn.m13() + ",\n"
				+ valueIn.m20() + ", " + valueIn.m21() + ", " + valueIn.m22() + ", " + valueIn.m23() + ",\n"
				+ valueIn.m30() + ", " + valueIn.m31() + ", " + valueIn.m32() + ", " + valueIn.m33();
	}
}