package fr.onsiea.engine.client.graphics.opengl.shader.uniform.debug;

import fr.onsiea.engine.client.graphics.fog.Fog;
import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderProgram;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformFog;
import fr.onsiea.engine.client.graphics.shader.IShaderProgram;
import fr.onsiea.engine.client.graphics.shader.uniform.IShaderTypedUniform;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PRIVATE)
public class GLDebugUniformFog extends GLUniformFog implements IShaderTypedUniform<Fog>
{
	private IShaderProgram	parent;
	private String			name;

	public GLDebugUniformFog(GLShaderProgram parentIn, String nameIn)
	{
		super(parentIn, nameIn);

		this.parent	= parentIn;
		this.name	= nameIn;
	}

	@Override
	public IShaderProgram load(Fog fogIn)
	{
		super.load(fogIn);

		System.err.println("[DEBUG-Shader] Loading fog \"" + fogIn + "\" into \"" + this.name + "\" uniform of \""
				+ this.parent.name() + "\" shader !");

		return this.parent();
	}
}