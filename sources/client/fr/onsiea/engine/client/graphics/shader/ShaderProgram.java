/**
 * Copyright 2021 Onsiea All rights reserved.<br>
 * <br>
 *
 * This file is part of Onsiea Engine project.
 * (https://github.com/Onsiea/OnsieaEngine)<br>
 * <br>
 *
 * Onsiea Engine is [licensed]
 * (https://github.com/Onsiea/OnsieaEngine/blob/main/LICENSE) under the terms of
 * the "GNU General Public Lesser License v3.0" (GPL-3.0).
 * https://github.com/Onsiea/OnsieaEngine/wiki/License#license-and-copyright<br>
 * <br>
 *
 * Onsiea Engine is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3.0 of the License, or
 * (at your option) any later version.<br>
 * <br>
 *
 * Onsiea Engine is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.<br>
 * <br>
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Onsiea Engine. If not, see <https://www.gnu.org/licenses/>.<br>
 * <br>
 *
 * Neither the name "Onsiea", "Onsiea Engine", or any derivative name or the
 * names of its authors / contributors may be used to endorse or promote
 * products derived from this software and even less to name another project or
 * other work without clear and precise permissions written in advance.<br>
 * <br>
 *
 * @Author : Seynax (https://github.com/seynax)<br>
 * @Organization : Onsiea Studio (https://github.com/Onsiea)
 */
package fr.onsiea.engine.client.graphics.shader;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import fr.onsiea.engine.client.graphics.fog.Fog;
import fr.onsiea.engine.client.graphics.light.DirectionalLight;
import fr.onsiea.engine.client.graphics.light.PointLight;
import fr.onsiea.engine.client.graphics.light.SpotLight;
import fr.onsiea.engine.client.graphics.material.Material;
import fr.onsiea.engine.client.graphics.shader.uniform.IShaderTypedUniform;
import fr.onsiea.engine.client.graphics.shader.uniform.IShaderUniform;
import fr.onsiea.engine.client.graphics.shader.uniform.IShaderUniformBuilder;
import fr.onsiea.engine.utils.file.FileUtils;
import fr.onsiea.engine.utils.function.IIFunction;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Seynax
 *
 */
public abstract class ShaderProgram implements IShaderProgram
{
	protected final List<String>				attributes;
	protected final Map<String, IShaderUniform>	uniforms;

	protected IShaderUniformBuilder				uniformBuilder;

	private @Getter(AccessLevel.PUBLIC) String	name;

	protected ShaderProgram()
	{
		this.attributes	= new LinkedList<>();
		this.uniforms	= new LinkedHashMap<>();
	}

	/**
	 * @param builderIn
	 * @throws Exception
	 */
	public ShaderProgram(final ShaderProgram.Builder builderIn) throws Exception
	{
		this.attributes	= new LinkedList<>();
		this.uniforms	= new LinkedHashMap<>();
		this.build(builderIn);
	}

	/**
	 * @param builderIn
	 * @throws Exception
	 */
	protected void build(final ShaderProgram.Builder builderIn) throws Exception
	{
		this.name = builderIn.name();

		if (!this.load(builderIn.vertex().script(), builderIn.fragment().script()))
		{
			throw new Exception("[ERROR]-ShaderProgram : failed to load shader !\nVertex script : \""
					+ builderIn.vertex().script() + "\"\nFragment script : \"" + builderIn.fragment().script() + "\"");
		}

		this.attributes.addAll(builderIn.attributes);

		this.uniforms.putAll(builderIn.uniforms);
	}

	/**
	 * @param nameIn
	 * @param fragmentScriptFilepathIn
	 * @param vertexScriptFilepathIn
	 * @param attributesIn
	 * @throws Exception
	 */
	protected void build(final String nameIn, final String vertexScriptFilepathIn,
			final String fragmentScriptFilepathIn, final String[] attributesIn) throws Exception
	{
		final var builder = new Builder(nameIn).vertexScriptFilepath(vertexScriptFilepathIn)
				.fragmentScriptFilepath(fragmentScriptFilepathIn).build(attributesIn);
		this.name = nameIn;

		if (!this.load(builder.vertex().script(), builder.fragment().script()))
		{
			throw new Exception("[ERROR]-ShaderProgram : failed to load shader !\nVertex script : \""
					+ builder.vertex().script() + "\"\nFragment script : \"" + builder.fragment().script() + "\"");
		}

		this.attributes.addAll(builder.attributes);
	}

	protected abstract boolean load(String vertexScriptIn, String fragmentScriptIn) throws Exception;

	@Override
	public IShaderProgram attributes(final String... namesIn)
	{
		for (final String name : namesIn)
		{
			this.attributes.add(name);
			this.createAttribute(name, this.attributes.size() - 1);
		}

		return this;
	}

	protected abstract void createAttribute(String nameIn, int indexIn);

	@Override
	public IShaderProgram uniform(final String nameIn, final IShaderUniform uniformIn)
	{
		this.uniforms.put(nameIn, uniformIn);

		return this;
	}

	@Override
	public <T> IShaderProgram uniform(final String nameIn, final IShaderTypedUniform<T> uniformIn)
	{
		this.uniforms.put(nameIn, uniformIn);

		return this;
	}

	public IShaderUniform uniform(final String nameIn)
	{
		return this.uniformBuilder.uniform(nameIn);
	}

	public IShaderUniform[] uniforms(final String... namesIn)
	{
		final var	uniforms	= new IShaderUniform[namesIn.length];
		var			i			= 0;
		for (final String name : namesIn)
		{
			final var shaderUniform = this.uniformBuilder.uniform(name);
			this.uniforms.put(name, shaderUniform);
			uniforms[i] = shaderUniform;
			i++;
		}
		return uniforms;
	}

	public IShaderTypedUniform<Boolean> booleanUniform(final String nameIn)
	{
		final var uniform = this.uniformBuilder.booleanUniform(nameIn);

		this.uniforms.put(nameIn, uniform);

		return uniform;
	}

	public IShaderTypedUniform<Integer> intUniform(final String nameIn)
	{
		final var uniform = this.uniformBuilder.intUniform(nameIn);

		this.uniforms.put(nameIn, uniform);

		return uniform;
	}

	public IShaderTypedUniform<Float> floatUniform(final String nameIn)
	{
		final var uniform = this.uniformBuilder.floatUniform(nameIn);

		this.uniforms.put(nameIn, uniform);

		return uniform;
	}

	public IShaderTypedUniform<float[]> floatArrayUniform(final String nameIn)
	{
		final var uniform = this.uniformBuilder.floatArrayUniform(nameIn);

		this.uniforms.put(nameIn, uniform);

		return uniform;
	}

	public IShaderTypedUniform<Vector2f> vector2fUniform(final String nameIn)
	{
		final var uniform = this.uniformBuilder.vector2fUniform(nameIn);

		this.uniforms.put(nameIn, uniform);

		return uniform;
	}

	public IShaderTypedUniform<Vector3f> vector3fUniform(final String nameIn)
	{
		final var uniform = this.uniformBuilder.vector3fUniform(nameIn);

		this.uniforms.put(nameIn, uniform);

		return uniform;
	}

	public IShaderTypedUniform<Vector4f> vector4fUniform(final String nameIn)
	{
		final var uniform = this.uniformBuilder.vector4fUniform(nameIn);

		this.uniforms.put(nameIn, uniform);

		return uniform;
	}

	public IShaderTypedUniform<Matrix4f> matrix4fUniform(final String nameIn)
	{
		final var uniform = this.uniformBuilder.matrix4fUniform(nameIn);

		this.uniforms.put(nameIn, uniform);

		return uniform;
	}

	public IShaderTypedUniform<DirectionalLight> directionalLightUniform(final String nameIn)
	{
		final var uniform = this.uniformBuilder.directionalLightUniform(nameIn);

		this.uniforms.put(nameIn, uniform);

		return uniform;
	}

	public IShaderTypedUniform<PointLight> pointLightUniform(final String nameIn)
	{
		final var uniform = this.uniformBuilder.pointLightUniform(nameIn);

		this.uniforms.put(nameIn, uniform);

		return uniform;
	}

	public IShaderTypedUniform<SpotLight> spotLightUniform(final String nameIn)
	{
		final var uniform = this.uniformBuilder.spotLightUniform(nameIn);

		this.uniforms.put(nameIn, uniform);

		return uniform;
	}

	/**
	 * @param nameIn
	 * @return
	 */
	public IShaderTypedUniform<PointLight[]> pointLightsUniform(final String nameIn, final int sizeIn)
	{
		final var uniform = this.uniformBuilder.pointLightsUniform(nameIn, sizeIn);

		this.uniforms.put(nameIn, uniform);

		return uniform;
	}

	/**
	 * @param nameIn
	 * @return
	 */
	public IShaderTypedUniform<SpotLight[]> spotLightsUniform(final String nameIn, final int sizeIn)
	{
		final var uniform = this.uniformBuilder.spotLightsUniform(nameIn, sizeIn);

		this.uniforms.put(nameIn, uniform);

		return uniform;
	}

	public IShaderTypedUniform<Fog> fogUniform(final String nameIn)
	{
		final var uniform = this.uniformBuilder.fogUniform(nameIn);

		this.uniforms.put(nameIn, uniform);

		return uniform;
	}

	public IShaderTypedUniform<Material> materialUniform(final String nameIn)
	{
		final var uniform = this.uniformBuilder.materialUniform(nameIn);

		this.uniforms.put(nameIn, uniform);

		return uniform;
	}

	@Override
	public IShaderUniform get(final String nameIn)
	{
		return this.uniforms.get(nameIn);
	}

	@Getter(AccessLevel.PUBLIC)
	public static class Builder
	{
		private final String										name;
		private final ShaderScript									vertex;
		private final ShaderScript									fragment;
		private @Setter(AccessLevel.PUBLIC) IShaderUniformBuilder	uniformBuilder;
		private final List<String>									attributes;
		private final Map<String, IShaderUniform>					uniforms;

		public Builder(final String nameIn)
		{
			this.name		= nameIn;
			this.vertex		= new ShaderScript();
			this.fragment	= new ShaderScript();
			this.attributes	= new LinkedList<>();
			this.uniforms	= new LinkedHashMap<>();
		}

		public Builder(final String nameIn, final IShaderUniformBuilder uniformBuilderIn)
		{
			this.name			= nameIn;
			this.uniformBuilder	= uniformBuilderIn;
			this.vertex			= new ShaderScript();
			this.fragment		= new ShaderScript();
			this.attributes		= new LinkedList<>();
			this.uniforms		= new LinkedHashMap<>();
		}

		public final Builder build(final String... attributesIn) throws Exception
		{
			Builder.load("vertex", this.vertex);
			Builder.load("fragment", this.fragment);

			if (attributesIn != null && attributesIn.length > 0)
			{
				Collections.addAll(this.attributes, attributesIn);
			}

			return this;
		}

		public final Builder build(final IIFunction<Builder> buildMethodIn, final String... attributesIn)
				throws Exception
		{
			Builder.load("vertex", this.vertex);
			Builder.load("fragment", this.fragment);

			if (attributesIn != null && attributesIn.length > 0)
			{
				Collections.addAll(this.attributes, attributesIn);
			}

			buildMethodIn.execute(this);

			return this;
		}

		public Builder uniform(final String nameIn, final IShaderUniform uniformIn)
		{
			this.uniforms.put(nameIn, uniformIn);

			return this;
		}

		private final static void load(final String scriptTypeIn, final ShaderScript scriptIn) throws Exception
		{
			if (scriptIn.script() == null)
			{
				if (scriptIn.filepath() == null)
				{
					throw new Exception(
							"[ERROR]-GLShaderProgram : " + scriptTypeIn + " script and filepath are null pointer !");
				}

				scriptIn.script(FileUtils.loadResource(scriptIn.filepath()));
				if (scriptIn.script() == null)
				{
					throw new Exception("[ERROR]-GLShaderProgram : unable to load \"" + scriptIn.filepath() + "\""
							+ scriptTypeIn + " shader script file ! ");
				}
			}
		}

		public final Builder vertexScriptFilepath(final String scriptFilepathIn)
		{
			this.vertex.filepath(scriptFilepathIn);

			return this;
		}

		public final Builder vertexScript(final String scriptIn)
		{
			this.vertex.script(scriptIn);

			return this;
		}

		public final Builder fragmentScript(final String scriptIn)
		{
			this.fragment.script(scriptIn);

			return this;
		}

		public final Builder fragmentScriptFilepath(final String scriptFilepathIn)
		{
			this.fragment.filepath(scriptFilepathIn);

			return this;
		}
	}

	@AllArgsConstructor
	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	public final static class ShaderScript
	{
		private String	filepath;
		private String	script;

		private ShaderScript()
		{

		}
	}
}