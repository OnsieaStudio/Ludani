package fr.onsiea.engine.client.graphics.opengl.shader;

import org.lwjgl.opengl.GL20;

import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniform;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformBoolean;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformDirectionalLight;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformFloat;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformFloatArray;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformFog;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformInt;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformMaterial;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformMatrix4f;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformPointLight;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformSpotLight;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformVector2f;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformVector3f;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformVector4f;
import fr.onsiea.engine.client.graphics.opengl.shader.utils.GLShaderUtils;
import fr.onsiea.engine.client.graphics.shader.IShaderProgram;
import fr.onsiea.engine.client.graphics.shader.ShaderProgram;
import fr.onsiea.engine.client.resources.ResourcesPath;
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

	public GLShaderProgram(String vertexShaderScriptFilepathIn, String fragmentShaderScriptFilepathIn,
			String... attributesIn) throws Exception
	{
		super(vertexShaderScriptFilepathIn, fragmentShaderScriptFilepathIn, attributesIn);
	}

	public GLShaderProgram(String vertexShaderIn, String fragmentShaderIn, boolean isScriptIn, String... attributesIn)
			throws Exception
	{
		super(vertexShaderIn, fragmentShaderIn, isScriptIn, attributesIn);
	}

	public GLShaderProgram(ResourcesPath vertexShaderScriptResourcespathIn,
			ResourcesPath fragmentShaderScriptResourcespathIn, String... attributesIn) throws Exception
	{
		super(vertexShaderScriptResourcespathIn, fragmentShaderScriptResourcespathIn, attributesIn);
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

	/**
	 * @param stringIn
	 * @return
	 */
	public GLUniform uniform(String nameIn)
	{
		final var uniform = new GLUniform(this, nameIn);
		this.uniform(nameIn, uniform);

		return uniform;
	}

	/**
	 * @param stringIn
	 * @return
	 */
	public GLUniformBoolean booleanUniform(String nameIn)
	{
		final var uniform = new GLUniformBoolean(this, nameIn);
		this.uniform(nameIn, uniform);

		return uniform;
	}

	public GLUniformInt intUniform(String nameIn)
	{
		final var uniform = new GLUniformInt(this, nameIn);
		this.uniform(nameIn, uniform);

		return uniform;
	}

	public GLUniformFloat floatUniform(String nameIn)
	{
		final var uniform = new GLUniformFloat(this, nameIn);
		this.uniform(nameIn, uniform);

		return uniform;
	}

	/**
	 * @param nameIn
	 * @return
	 */
	public GLUniformFloatArray floatArrayUniform(String nameIn)
	{
		final var uniform = new GLUniformFloatArray(this, nameIn);
		this.uniform(nameIn, uniform);

		return uniform;
	}

	public GLUniformVector2f vector2fUniform(String nameIn)
	{
		final var uniform = new GLUniformVector2f(this, nameIn);
		this.uniform(nameIn, uniform);

		return uniform;
	}

	public GLUniformVector3f vector3fUniform(String nameIn)
	{
		final var uniform = new GLUniformVector3f(this, nameIn);
		this.uniform(nameIn, uniform);

		return uniform;
	}

	public GLUniformVector4f vector4fUniform(String nameIn)
	{
		final var uniform = new GLUniformVector4f(this, nameIn);
		this.uniform(nameIn, uniform);

		return uniform;
	}

	public GLUniformMatrix4f matrix4fUniform(String nameIn)
	{
		final var uniform = new GLUniformMatrix4f(this, nameIn);
		this.uniform(nameIn, uniform);

		return uniform;
	}

	public GLUniformMaterial materialUniform(String nameIn)
	{
		final var uniform = new GLUniformMaterial(this, nameIn);
		this.uniform(nameIn, uniform);

		return uniform;
	}

	public GLUniformPointLight pointLightUniform(String nameIn)
	{
		final var uniform = new GLUniformPointLight(this, nameIn);
		this.uniform(nameIn, uniform);

		return uniform;
	}

	public GLUniformSpotLight spotLightUniform(String nameIn)
	{
		final var uniform = new GLUniformSpotLight(this, nameIn);
		this.uniform(nameIn, uniform);

		return uniform;
	}

	public GLUniformDirectionalLight directionalLightUniform(String nameIn)
	{
		final var uniform = new GLUniformDirectionalLight(this, nameIn);
		this.uniform(nameIn, uniform);

		return uniform;
	}

	public GLUniformFog fogUniform(String nameIn)
	{
		final var uniform = new GLUniformFog(this, nameIn);
		this.uniform(nameIn, uniform);

		return uniform;
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