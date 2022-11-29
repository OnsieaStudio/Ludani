package fr.onsiea.engine.client.graphics.opengl.shader.uniform.debug;

import org.joml.Matrix4f;

import fr.onsiea.engine.client.graphics.light.PointLight;
import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderProgram;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformPointLight;
import fr.onsiea.engine.client.graphics.shader.IShaderProgram;
import fr.onsiea.engine.client.graphics.shader.uniform.IShaderTypedUniform;
import fr.onsiea.engine.utils.function.IIFunction;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PRIVATE)
public class GLDebugUniformPointLight extends GLUniformPointLight implements IShaderTypedUniform<PointLight>
{
	private IShaderProgram	parent;
	private String			name;

	public GLDebugUniformPointLight(GLShaderProgram parentIn, String nameIn)
	{
		super(parentIn, nameIn);

		this.parent	= parentIn;
		this.name	= nameIn;
	}

	@Override
	public IShaderProgram load(PointLight pointLightIn)
	{
		super.load(pointLightIn);

		System.err.println("[DEBUG-Shader] Loading pointlight \"" + pointLightIn + "\" into \"" + this.name
				+ "\" uniform of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load(PointLight pointLightIn, Matrix4f viewMatrixIn)
	{
		super.load(pointLightIn, viewMatrixIn);

		System.err.println("[DEBUG-Shader] Loading pointlight in view matrix \"" + pointLightIn + "\" into \""
				+ this.name + "\" uniform of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load(IIFunction<int[]> toLoadIn)
	{
		super.load(toLoadIn);

		System.err.println("[DEBUG-Shader] Loading pointLight uniform with function into \"" + this.name
				+ "\" uniform of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}
}