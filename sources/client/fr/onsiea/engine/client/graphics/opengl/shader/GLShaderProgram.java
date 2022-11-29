package fr.onsiea.engine.client.graphics.opengl.shader;

import org.lwjgl.opengl.GL20;

import fr.onsiea.engine.client.graphics.GraphicsConstants;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformBuilder;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.debug.GLDebugUniformBuilder;
import fr.onsiea.engine.client.graphics.opengl.shader.utils.GLShaderUtils;
import fr.onsiea.engine.client.graphics.shader.IShaderProgram;
import fr.onsiea.engine.client.graphics.shader.ShaderProgram;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
public class GLShaderProgram extends ShaderProgram
{
	private int	vertexId;
	private int	fragmentId;

	private int	programId;

	public GLShaderProgram(ShaderProgram.Builder builderIn) throws Exception
	{
		if (builderIn.uniformBuilder() == null)
		{
			builderIn.uniformBuilder(new GLUniformBuilder(this));
		}

		this.build(builderIn);
	}

	/**
	 * @param stringIn
	 * @param vertexFileIn
	 * @param fragmentFileIn
	 * @param string2In
	 * @throws Exception
	 */
	public GLShaderProgram(String nameIn, String fragmentScriptFilepathIn, String vertexScriptFilepathIn,
			String... attributesIn) throws Exception
	{
		this.build(nameIn, fragmentScriptFilepathIn, vertexScriptFilepathIn, attributesIn);
		if (GraphicsConstants.SHADER_UNIFORM_DEBUG)
		{
			this.uniformBuilder = new GLDebugUniformBuilder(this);
		}
		else
		{
			this.uniformBuilder = new GLUniformBuilder(this);
		}

	}

	@Override
	protected boolean load(String vertexScriptIn, String fragmentScriptIn) throws Exception
	{
		this.vertexId(GLShaderUtils.compile(vertexScriptIn, GL20.GL_VERTEX_SHADER));
		this.fragmentId(GLShaderUtils.compile(fragmentScriptIn, GL20.GL_FRAGMENT_SHADER));

		this.programId(GL20.glCreateProgram());

		GLShaderUtils.link(this.programId(), this.vertexId(), this.fragmentId());

		return true;
	}

	public int uniformLocation(final String uniformNameIn)
	{
		return GL20.glGetUniformLocation(this.programId(), uniformNameIn);
	}

	protected int[] uniformsLocation(final String... uniformNamesIn)
	{
		final var	locations	= new int[uniformNamesIn.length];

		var			i			= 0;

		for (final String uniformName : uniformNamesIn)
		{
			final var id = GL20.glGetUniformLocation(this.programId(), uniformName);

			locations[i] = id;

			i++;
		}

		return locations;
	}

	@Override
	protected void createAttribute(String nameIn, int indexIn)
	{
		GL20.glBindAttribLocation(this.programId(), indexIn, nameIn);
	}

	@Override
	public IShaderProgram attach()
	{
		GL20.glUseProgram(this.programId());

		return this;
	}

	@Override
	public IShaderProgram cleanup()
	{
		GL20.glUseProgram(0);

		//GL20.glDetachShader(this.programId(), this.vertexId());
		//GL20.glDetachShader(this.programId(), this.fragmentId());

		if (this.vertexId() != 0)
		{
			GL20.glDeleteShader(this.vertexId());
		}
		if (this.fragmentId() != 0)
		{
			GL20.glDeleteShader(this.fragmentId());
		}

		if (this.programId() != 0)
		{
			GL20.glDeleteProgram(this.programId());
		}

		return this;
	}
}