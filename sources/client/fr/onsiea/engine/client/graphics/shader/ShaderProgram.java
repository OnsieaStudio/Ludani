/**
* Copyright 2021 Onsiea All rights reserved.<br><br>
*
* This file is part of Onsiea Engine project. (https://github.com/Onsiea/OnsieaEngine)<br><br>
*
* Onsiea Engine is [licensed] (https://github.com/Onsiea/OnsieaEngine/blob/main/LICENSE) under the terms of the "GNU General Public Lesser License v3.0" (GPL-3.0).
* https://github.com/Onsiea/OnsieaEngine/wiki/License#license-and-copyright<br><br>
*
* Onsiea Engine is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 3.0 of the License, or
* (at your option) any later version.<br><br>
*
* Onsiea Engine is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU Lesser General Public License for more details.<br><br>
*
* You should have received a copy of the GNU Lesser General Public License
* along with Onsiea Engine.  If not, see <https://www.gnu.org/licenses/>.<br><br>
*
* Neither the name "Onsiea", "Onsiea Engine", or any derivative name or the names of its authors / contributors may be used to endorse or promote products derived from this software and even less to name another project or other work without clear and precise permissions written in advance.<br><br>
*
* @Author : Seynax (https://github.com/seynax)<br>
* @Organization : Onsiea Studio (https://github.com/Onsiea)
*/
package fr.onsiea.engine.client.graphics.shader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.onsiea.engine.client.graphics.shader.uniform.IShaderTypedUniform;
import fr.onsiea.engine.client.graphics.shader.uniform.IShaderUniform;
import fr.onsiea.engine.client.resources.ResourcesPath;
import fr.onsiea.engine.utils.file.FileUtils;

/**
 * @author Seynax
 *
 */
public abstract class ShaderProgram implements IShaderProgram
{
	private final List<String>					attributes;
	private final Map<String, IShaderUniform>	uniforms;

	public ShaderProgram(String vertexScriptFilepathIn, String fragmentScriptFilepathIn, String... attributesIn)
			throws Exception
	{
		final var vertexScript = FileUtils.loadResource(vertexScriptFilepathIn);
		if (vertexScript == null)
		{
			throw new Exception("[ERROR]-GLShaderProgramWithFiles : unable to load \"" + vertexScriptFilepathIn
					+ "\" vertex shader script file ! ");
		}

		final var fragmentScript = FileUtils.loadResource(fragmentScriptFilepathIn);
		if (fragmentScript == null)
		{
			throw new Exception("[ERROR]-GLShaderProgramWithFiles : unable to load \"" + fragmentScriptFilepathIn
					+ "\" fragment shader script file ! ");
		}

		if (!this.load(vertexScript, fragmentScript))
		{
			throw new Exception("[ERROR]-ShaderProgram : failed to load shader !\nVertex script : \"" + vertexScript
					+ "\"\nFragment script : \"" + fragmentScript + "\"");
		}

		this.attributes	= new ArrayList<>();
		this.uniforms	= new HashMap<>();

		this.attributes(attributesIn);
	}

	/**
	 * @param vertexShaderScriptIn (script or file path, according to isScriptIn)
	 * @param fragmentShaderScriptIn (script or file path, according to isScriptIn)
	 * @param isPathIn
	 * @param attributesIn
	 * @throws Exception
	 */
	public ShaderProgram(String vertexShaderIn, String fragmentShaderIn, boolean isScriptIn, String... attributesIn)
			throws Exception
	{
		var	vertexScript	= vertexShaderIn;
		var	fragmentScript	= fragmentShaderIn;

		if (!isScriptIn)
		{
			vertexScript = FileUtils.loadResource(vertexShaderIn);
			if (vertexScript == null)
			{
				throw new Exception("[ERROR]-GLShaderProgramWithFiles : unable to load \"" + vertexShaderIn
						+ "\" vertex shader script file ! ");
			}

			fragmentScript = FileUtils.loadResource(fragmentShaderIn);
			if (fragmentScript == null)
			{
				throw new Exception("[ERROR]-GLShaderProgramWithFiles : unable to load \"" + fragmentShaderIn
						+ "\" fragment shader script file ! ");
			}
		}

		if (!this.load(vertexScript, fragmentScript))
		{
			throw new Exception("[ERROR]-ShaderProgram : failed to load shader !\nVertex script : \"" + vertexScript
					+ "\"\nFragment script : \"" + fragmentScript + "\"");
		}

		this.attributes	= new ArrayList<>();
		this.uniforms	= new HashMap<>();

		this.attributes(attributesIn);
	}

	/**
	 * @param vertexShaderScriptResourcespathIn
	 * @param fragmentShaderScriptResourcespathIn
	 * @param attributesIn
	 * @throws Exception
	 */
	public ShaderProgram(ResourcesPath vertexShaderScriptResourcespathIn,
			ResourcesPath fragmentShaderScriptResourcespathIn, String... attributesIn) throws Exception
	{
		final var vertexScript = FileUtils.loadResource(vertexShaderScriptResourcespathIn.path());
		if (vertexScript == null)
		{
			throw new Exception("[ERROR]-GLShaderProgramWithFiles : unable to load \""
					+ vertexShaderScriptResourcespathIn.path() + "\" vertex shader script file ! ");
		}

		final var fragmentScript = FileUtils.loadResource(fragmentShaderScriptResourcespathIn.path());
		if (fragmentScript == null)
		{
			throw new Exception("[ERROR]-GLShaderProgramWithFiles : unable to load \""
					+ fragmentShaderScriptResourcespathIn.path() + "\" fragment shader script file ! ");
		}

		if (!this.load(vertexScript, fragmentScript))
		{
			throw new Exception("[ERROR]-ShaderProgram : failed to load shader !\nVertex script : \"" + vertexScript
					+ "\"\nFragment script : \"" + fragmentScript + "\"");
		}

		this.attributes	= new ArrayList<>();
		this.uniforms	= new HashMap<>();

		this.attributes(attributesIn);
	}

	protected abstract boolean load(String vertexScriptIn, String fragmentScriptIn) throws Exception;

	@Override
	public IShaderProgram attributes(String... namesIn)
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
	public IShaderProgram uniform(String nameIn, IShaderUniform uniformIn)
	{
		this.uniforms.put(nameIn, uniformIn);

		return this;
	}

	@Override
	public <T> IShaderProgram uniform(String nameIn, IShaderTypedUniform<T> uniformIn)
	{
		this.uniforms.put(nameIn, uniformIn);

		return this;
	}

	@Override
	public IShaderUniform get(String nameIn)
	{
		return this.uniforms.get(nameIn);
	}
}