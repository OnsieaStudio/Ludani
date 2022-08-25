package fr.onsiea.engine.client.graphics.opengl.shader.uniform;

import fr.onsiea.engine.client.graphics.material.Material;
import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderProgram;
import fr.onsiea.engine.client.graphics.shader.IShaderProgram;
import fr.onsiea.engine.client.graphics.shader.uniform.IShaderTypedUniform;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PRIVATE)
public class GLUniformMaterial implements IShaderTypedUniform<Material>
{
	public final static int[] create(GLShaderProgram shaderIn, String nameIn)
	{
		final var locations = new int[6];

		locations[0]	= shaderIn.uniformLocation(nameIn + ".ambient");
		locations[1]	= shaderIn.uniformLocation(nameIn + ".diffuse");
		locations[2]	= shaderIn.uniformLocation(nameIn + ".specular");
		locations[3]	= shaderIn.uniformLocation(nameIn + ".hasTexture");
		locations[4]	= shaderIn.uniformLocation(nameIn + ".hasNormalMap");
		locations[5]	= shaderIn.uniformLocation(nameIn + ".reflectance");

		return locations;
	}

	public final static void load(int[] locationsIn, Material materialIn)
	{
		GLUniformVector4f.load(locationsIn[0], materialIn.ambientColour());
		GLUniformVector4f.load(locationsIn[1], materialIn.diffuseColour());
		GLUniformVector4f.load(locationsIn[2], materialIn.specularColour());
		GLUniformInt.load(locationsIn[3], materialIn.isTextured() ? 1 : 0);
		GLUniformInt.load(locationsIn[4], materialIn.textures().size() > 1 ? 1 : 0);
		GLUniformFloat.load(locationsIn[5], materialIn.reflectance());
	}

	private IShaderProgram	parent;
	private int[]			locations;

	public GLUniformMaterial(GLShaderProgram parentIn, String nameIn)
	{
		this.parent(parentIn);

		this.locations(GLUniformMaterial.create(parentIn, nameIn));
	}

	@Override
	public IShaderProgram load(Material materialIn)
	{
		GLUniformMaterial.load(this.locations(), materialIn);

		return this.parent();
	}
}
