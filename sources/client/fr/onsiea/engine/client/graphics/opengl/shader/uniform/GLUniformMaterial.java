package fr.onsiea.engine.client.graphics.opengl.shader.uniform;

import fr.onsiea.engine.client.graphics.material.Material;
import fr.onsiea.engine.client.graphics.opengl.shader.Shader;
import fr.onsiea.engine.client.graphics.shader.IShaderProgram;
import fr.onsiea.engine.client.graphics.shader.IShaderUniform;

public class GLUniformMaterial implements IShaderUniform<Material>
{
	public final static int[] create(Shader shaderIn, String nameIn)
	{
		final var locations = new int[5];

		locations[0]	= shaderIn.uniformLocation(nameIn + ".ambient");
		locations[1]	= shaderIn.uniformLocation(nameIn + ".diffuse");
		locations[2]	= shaderIn.uniformLocation(nameIn + ".specular");
		locations[3]	= shaderIn.uniformLocation(nameIn + ".hasTexture");
		locations[4]	= shaderIn.uniformLocation(nameIn + ".reflectance");

		return locations;
	}

	public final static void load(int[] locationsIn, Material materialIn)
	{
		GLUniformVector4f.load(locationsIn[0], materialIn.ambientColour());
		GLUniformVector4f.load(locationsIn[1], materialIn.diffuseColour());
		GLUniformVector4f.load(locationsIn[2], materialIn.specularColour());
		GLUniformInt.load(locationsIn[3], materialIn.isTextured() ? 1 : 0);
		GLUniformFloat.load(locationsIn[4], materialIn.reflectance());
	}

	private IShaderProgram	parent;
	private int[]			locations;

	public GLUniformMaterial(Shader parentIn, String nameIn)
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

	private final IShaderProgram parent()
	{
		return this.parent;
	}

	private final void parent(IShaderProgram parentIn)
	{
		this.parent = parentIn;
	}

	private final int[] locations()
	{
		return this.locations;
	}

	private final void locations(int[] locationsIn)
	{
		this.locations = locationsIn;
	}
}
