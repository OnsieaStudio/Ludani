package fr.onsiea.engine.client.graphics.opengl.shader.uniform;

import fr.onsiea.engine.client.graphics.fog.Fog;
import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderProgram;
import fr.onsiea.engine.client.graphics.shader.IShaderProgram;
import fr.onsiea.engine.client.graphics.shader.uniform.IShaderTypedUniform;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PRIVATE)
public class GLUniformFog implements IShaderTypedUniform<Fog>
{
	public final static int[] create(GLShaderProgram shaderIn, String nameIn)
	{
		final var locations = new int[4];

		locations[0]	= shaderIn.uniformLocation(nameIn + ".activeFog");
		locations[1]	= shaderIn.uniformLocation(nameIn + ".colour");
		locations[2]	= shaderIn.uniformLocation(nameIn + ".density");
		locations[3]	= shaderIn.uniformLocation(nameIn + ".gradient");

		return locations;
	}

	public final static void load(int[] locationsIn, Fog fogIn)
	{
		GLUniformInt.load(locationsIn[0], fogIn.active() ? 1 : 0);
		GLUniformVector3f.load(locationsIn[1], fogIn.colour());
		GLUniformFloat.load(locationsIn[2], fogIn.density());
		GLUniformFloat.load(locationsIn[3], fogIn.gradient());
	}

	private IShaderProgram	parent;
	private int[]			locations;

	public GLUniformFog(GLShaderProgram parentIn, String nameIn)
	{
		this.parent(parentIn);

		this.locations(GLUniformFog.create(parentIn, nameIn));
	}

	@Override
	public IShaderProgram load(Fog fogIn)
	{
		GLUniformFog.load(this.locations(), fogIn);

		return this.parent();
	}
}
