package fr.onsiea.engine.client.graphics.opengl.shader.uniform.debug;

import fr.onsiea.engine.client.graphics.material.Material;
import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderProgram;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformMaterial;
import fr.onsiea.engine.client.graphics.shader.IShaderProgram;
import fr.onsiea.engine.client.graphics.shader.uniform.IShaderTypedUniform;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PRIVATE)
public class GLDebugUniformMaterial extends GLUniformMaterial implements IShaderTypedUniform<Material>
{
	private IShaderProgram	parent;
	private String			name;

	public GLDebugUniformMaterial(GLShaderProgram parentIn, String nameIn)
	{
		super(parentIn, nameIn);

		this.parent	= parentIn;
		this.name	= nameIn;
	}

	@Override
	public IShaderProgram load(Material materialIn)
	{
		super.load(materialIn);

		System.err.println("[DEBUG-Shader] Loading material \"" + materialIn + "\" into \"" + this.name
				+ "\" uniform of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}
}