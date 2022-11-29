package fr.onsiea.engine.client.graphics.opengl.shader.uniform.debug;

import org.joml.Matrix4f;

import fr.onsiea.engine.client.graphics.light.DirectionalLight;
import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderProgram;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformDirectionalLight;
import fr.onsiea.engine.client.graphics.shader.IShaderProgram;
import fr.onsiea.engine.client.graphics.shader.uniform.IShaderTypedUniform;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PRIVATE)
public class GLDebugUniformDirectionalLight extends GLUniformDirectionalLight
		implements IShaderTypedUniform<DirectionalLight>
{
	private IShaderProgram	parent;
	private String			name;

	public GLDebugUniformDirectionalLight(GLShaderProgram parentIn, String nameIn)
	{
		super(parentIn, nameIn);

		this.parent	= parentIn;
		this.name	= nameIn;
	}

	@Override
	public IShaderProgram load(DirectionalLight directionalLightIn)
	{
		super.load(directionalLightIn);
		System.err.println("[DEBUG-Shader] Loading directional lights \"" + directionalLightIn + "\" into \""
				+ this.name + "\" uniform of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load(DirectionalLight directionalLightIn, Matrix4f viewMatrixIn)
	{
		super.load(directionalLightIn, viewMatrixIn);

		System.err.println("[DEBUG-Shader] Loading directional light in view matrix \"" + directionalLightIn
				+ "\" in viewMatrix into \"" + this.name + "\" uniform of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}
}