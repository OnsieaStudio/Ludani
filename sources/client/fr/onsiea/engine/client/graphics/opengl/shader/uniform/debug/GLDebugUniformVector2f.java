package fr.onsiea.engine.client.graphics.opengl.shader.uniform.debug;

import org.joml.Vector2f;

import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderProgram;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformVector2f;
import fr.onsiea.engine.client.graphics.shader.IShaderProgram;
import fr.onsiea.engine.client.graphics.shader.uniform.IShaderTypedUniform;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PRIVATE)
public class GLDebugUniformVector2f extends GLUniformVector2f implements IShaderTypedUniform<Vector2f>
{
	private IShaderProgram	parent;
	private String			name;

	public GLDebugUniformVector2f(GLShaderProgram parentIn, String nameIn)
	{
		super(parentIn, nameIn);

		this.parent	= parentIn;
		this.name	= nameIn;
	}

	@Override
	public IShaderProgram load(Vector2f valueIn)
	{
		super.load(valueIn);

		System.err.println("[DEBUG-Shader] Loading vector2f \"" + valueIn + "\" into \"" + this.name
				+ "\" uniform of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load(float xIn, float yIn)
	{
		super.load(xIn, yIn);

		System.err.println("[DEBUG-Shader] Loading vector2f (float xIn, float yIn) \"" + xIn + "," + yIn + "\" into \""
				+ this.name + "\" uniform of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}
}