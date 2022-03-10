package fr.onsiea.engine.client.graphics.opengl.shader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformFloat;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformFloatArray;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformInt;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformMatrix4f;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformVector2f;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformVector3f;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformVector4f;
import fr.onsiea.engine.client.graphics.shader.IShaderProgram;
import fr.onsiea.engine.client.graphics.shader.IShaderUniform;

public abstract class Shader implements IShaderProgram
{
	private static final int loadShader(final String filenameIn, final int type)
	{
		final var		shaderSource	= new StringBuilder();

		BufferedReader	bufferedReader	= null;
		try
		{
			bufferedReader = new BufferedReader(new FileReader(filenameIn));
			String line;

			while ((line = bufferedReader.readLine()) != null)
			{
				shaderSource.append(line).append("//\n");
			}
		}
		catch (final IOException exception)
		{
			System.err.println("Could not read file !");

			exception.printStackTrace();

			System.exit(-1);
		}
		finally
		{
			try
			{
				if (bufferedReader != null)
				{
					bufferedReader.close();
				}
			}
			catch (final IOException e)
			{
				e.printStackTrace();
			}
		}

		final var shaderId = GL20.glCreateShader(type);

		GL20.glShaderSource(shaderId, shaderSource);

		GL20.glCompileShader(shaderId);

		if (GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)
		{
			System.out.println(GL20.glGetShaderInfoLog(shaderId, 4096));

			System.err.println("Could not compile shader !");

			System.exit(-1);
		}

		return shaderId;
	}

	private int								vertexId;
	private int								fragmentId;

	private int								programId;

	private Map<String, IShaderUniform<?>>	uniforms;

	protected Shader(String vertexShaderFilepathIn, String fragmentShaderFilepathIn, String... attributesIn)
			throws Exception
	{
		this.uniforms(new HashMap<>());

		this.vertexId(Shader.loadShader(vertexShaderFilepathIn, GL20.GL_VERTEX_SHADER));
		this.fragmentId(Shader.loadShader(fragmentShaderFilepathIn, GL20.GL_FRAGMENT_SHADER));

		this.programId(GL20.glCreateProgram());

		GL20.glAttachShader(this.programId(), this.vertexId());
		GL20.glAttachShader(this.programId(), this.fragmentId());

		this.bindAttributes(attributesIn);

		this.link();
	}

	private void link() throws Exception
	{
		GL20.glLinkProgram(this.programId());

		if (GL20.glGetProgrami(this.programId(), GL20.GL_LINK_STATUS) == 0)
		{
			throw new Exception("Error linking Shader code: " + GL20.glGetProgramInfoLog(this.programId(), 1024));
		}

		if (this.vertexId() != 0)
		{
			GL20.glDetachShader(this.programId(), this.vertexId());
		}
		if (this.fragmentId() != 0)
		{
			GL20.glDetachShader(this.programId(), this.fragmentId());
		}

		GL20.glValidateProgram(this.programId());

		if (GL20.glGetProgrami(this.programId(), GL20.GL_VALIDATE_STATUS) == 0)
		{
			System.err.println("Warning validating Shader code: " + GL20.glGetProgramInfoLog(this.programId(), 1024));
		}
	}

	@Override
	public <T> IShaderUniform<?> get(String nameIn)
	{
		return this.uniforms.get(nameIn);
	}

	public int uniformLocation(final String uniformNameIn)
	{
		return GL20.glGetUniformLocation(this.programId(), uniformNameIn);
	}

	protected int[] getUniformsLocation(final String... uniformNamesIn)
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

	public GLUniformInt intUniform(String nameIn)
	{
		final var uniform = new GLUniformInt(this, nameIn);
		this.uniforms.put(nameIn, uniform);

		return uniform;
	}

	public GLUniformFloat floatUniform(String nameIn)
	{
		final var uniform = new GLUniformFloat(this, nameIn);
		this.uniforms.put(nameIn, uniform);

		return uniform;
	}

	/**
	 * @param nameIn
	 * @return
	 */
	public GLUniformFloatArray floatArrayUniform(String nameIn)
	{
		final var uniform = new GLUniformFloatArray(this, nameIn);
		this.uniforms.put(nameIn, uniform);

		return uniform;
	}

	public GLUniformVector2f vector2fUniform(String nameIn)
	{
		final var uniform = new GLUniformVector2f(this, nameIn);
		this.uniforms.put(nameIn, uniform);

		return uniform;
	}

	public GLUniformVector3f vector3fUniform(String nameIn)
	{
		final var uniform = new GLUniformVector3f(this, nameIn);
		this.uniforms.put(nameIn, uniform);

		return uniform;
	}

	public GLUniformVector4f vector4fUniform(String nameIn)
	{
		final var uniform = new GLUniformVector4f(this, nameIn);
		this.uniforms.put(nameIn, uniform);

		return uniform;
	}

	public GLUniformMatrix4f matrix4fUniform(String nameIn)
	{
		final var uniform = new GLUniformMatrix4f(this, nameIn);
		this.uniforms.put(nameIn, uniform);

		return uniform;
	}

	protected void bindAttribute(final int attributeIn, final String variableNameIn)
	{
		GL20.glBindAttribLocation(this.programId(), attributeIn, variableNameIn);
	}

	protected int[] bindAttributes(final String... attributeNamesIn)
	{
		final var	locations	= new int[attributeNamesIn.length];

		var			i			= 0;

		for (final String uniformName : attributeNamesIn)
		{
			GL20.glBindAttribLocation(this.programId(), i, uniformName);

			locations[i] = i;

			i++;
		}

		return locations;
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

	protected int vertexId()
	{
		return this.vertexId;
	}

	protected void vertexId(int vertexIdIn)
	{
		this.vertexId = vertexIdIn;
	}

	protected int fragmentId()
	{
		return this.fragmentId;
	}

	protected void fragmentId(int fragmentIdIn)
	{
		this.fragmentId = fragmentIdIn;
	}

	protected int programId()
	{
		return this.programId;
	}

	protected void programId(int programIdIn)
	{
		this.programId = programIdIn;
	}

	protected Map<String, IShaderUniform<?>> uniforms()
	{
		return this.uniforms;
	}

	protected void uniforms(Map<String, IShaderUniform<?>> uniformsIn)
	{
		this.uniforms = uniformsIn;
	}
}