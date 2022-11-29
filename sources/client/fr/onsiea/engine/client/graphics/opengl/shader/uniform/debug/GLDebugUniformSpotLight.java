package fr.onsiea.engine.client.graphics.opengl.shader.uniform.debug;

import org.joml.Matrix4f;

import fr.onsiea.engine.client.graphics.light.SpotLight;
import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderProgram;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformSpotLight;
import fr.onsiea.engine.client.graphics.shader.IShaderProgram;
import fr.onsiea.engine.client.graphics.shader.uniform.IShaderTypedUniform;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PRIVATE)
public class GLDebugUniformSpotLight extends GLUniformSpotLight implements IShaderTypedUniform<SpotLight>
{
	private IShaderProgram	parent;
	private String			name;

	public GLDebugUniformSpotLight(GLShaderProgram parentIn, String nameIn)
	{
		super(parentIn, nameIn);

		this.parent	= parentIn;
		this.name	= nameIn;
	}

	@Override
	public IShaderProgram load(SpotLight spotLightIn)
	{
		super.load(spotLightIn);

		System.err.println("[DEBUG-Shader] Loading spotlight \"" + spotLightIn + "\" into \"" + this.name
				+ "\" uniform of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load(SpotLight spotLightIn, Matrix4f viewMatrixIn)
	{
		super.load(spotLightIn, viewMatrixIn);

		System.err.println("[DEBUG-Shader] Loading spotlight in view matrix \"" + spotLightIn + "\" into \"" + this.name
				+ "\" uniform of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}
}