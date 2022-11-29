package fr.onsiea.engine.client.graphics.opengl.shader.uniform.debug;

import org.joml.Vector3f;

import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderProgram;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformVector3f;
import fr.onsiea.engine.client.graphics.shader.IShaderProgram;
import fr.onsiea.engine.client.graphics.shader.uniform.IShaderTypedUniform;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PRIVATE)
public class GLDebugUniformVector3f extends GLUniformVector3f implements IShaderTypedUniform<Vector3f>
{
	private IShaderProgram	parent;
	private String			name;

	public GLDebugUniformVector3f(GLShaderProgram parentIn, String nameIn)
	{
		super(parentIn, nameIn);

		this.parent	= parentIn;
		this.name	= nameIn;
	}

	@Override
	public IShaderProgram load(Vector3f valueIn)
	{
		super.load(valueIn);

		System.err.println("[DEBUG-Shader] Loading vector3f \"" + valueIn + "\" into \"" + this.name
				+ "\" uniform of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load(float xIn, float yIn, float zIn)
	{
		super.load(xIn, yIn, zIn);

		System.err.println("[DEBUG-Shader] Loading vector3f (float xIn, float yIn, float zIn) \"" + xIn + ", " + yIn
				+ ", " + zIn + "\" into \"" + this.name + "\" uniform of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}
}