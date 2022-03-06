package fr.onsiea.engine.client.graphics.opengl.shader.uniform;

import fr.onsiea.engine.client.graphics.material.Material;
import fr.onsiea.engine.client.graphics.opengl.shader.Shader;

public class UniformMaterial
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
		UniformVector4f.load(locationsIn[0], materialIn.ambientColour());
		UniformVector4f.load(locationsIn[1], materialIn.diffuseColour());
		UniformVector4f.load(locationsIn[2], materialIn.specularColour());
		UniformInt.load(locationsIn[3], materialIn.isTextured() ? 1 : 0);
		UniformFloat.load(locationsIn[4], materialIn.reflectance());
	}

	private Shader	parent;
	private int[]	locations;

	public UniformMaterial(Shader parentIn, String nameIn)
	{
		this.parent(parentIn);

		this.locations(UniformMaterial.create(parentIn, nameIn));
	}

	public Shader load(Material materialIn)
	{
		UniformMaterial.load(this.locations(), materialIn);

		return this.parent();
	}

	private final Shader parent()
	{
		return this.parent;
	}

	private final void parent(Shader parentIn)
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
