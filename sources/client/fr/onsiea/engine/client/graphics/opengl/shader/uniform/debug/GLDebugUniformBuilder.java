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
package fr.onsiea.engine.client.graphics.opengl.shader.uniform.debug;

import fr.onsiea.engine.client.graphics.light.PointLight;
import fr.onsiea.engine.client.graphics.light.SpotLight;
import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderProgram;
import fr.onsiea.engine.client.graphics.shader.uniform.IShaderTypedUniform;
import fr.onsiea.engine.client.graphics.shader.uniform.IShaderUniform;
import fr.onsiea.engine.client.graphics.shader.uniform.IShaderUniformBuilder;

/**
 * @author Seynax
 *
 */
public class GLDebugUniformBuilder implements IShaderUniformBuilder
{
	private final GLShaderProgram parent;

	public GLDebugUniformBuilder(GLShaderProgram parentIn)
	{
		this.parent = parentIn;
	}

	/**
	 * @param stringIn
	 * @return
	 */
	@Override
	public GLDebugUniform uniform(String nameIn)
	{
		return new GLDebugUniform(this.parent, nameIn);
	}

	/**
	 * @param stringIn
	 * @return
	 */
	@Override
	public GLDebugUniformBoolean booleanUniform(String nameIn)
	{
		return new GLDebugUniformBoolean(this.parent, nameIn);
	}

	@Override
	public GLDebugUniformInt intUniform(String nameIn)
	{
		return new GLDebugUniformInt(this.parent, nameIn);
	}

	@Override
	public GLDebugUniformFloat floatUniform(String nameIn)
	{
		return new GLDebugUniformFloat(this.parent, nameIn);
	}

	/**
	 * @param nameIn
	 * @return
	 */
	@Override
	public GLDebugUniformFloatArray floatArrayUniform(String nameIn)
	{
		return new GLDebugUniformFloatArray(this.parent, nameIn);
	}

	@Override
	public GLDebugUniformVector2f vector2fUniform(String nameIn)
	{
		return new GLDebugUniformVector2f(this.parent, nameIn);
	}

	@Override
	public GLDebugUniformVector3f vector3fUniform(String nameIn)
	{
		return new GLDebugUniformVector3f(this.parent, nameIn);
	}

	@Override
	public GLDebugUniformVector4f vector4fUniform(String nameIn)
	{
		return new GLDebugUniformVector4f(this.parent, nameIn);
	}

	@Override
	public GLDebugUniformMatrix4f matrix4fUniform(String nameIn)
	{
		return new GLDebugUniformMatrix4f(this.parent, nameIn);
	}

	@Override
	public GLDebugUniformMaterial materialUniform(String nameIn)
	{
		return new GLDebugUniformMaterial(this.parent, nameIn);
	}

	@Override
	public GLDebugUniformPointLight pointLightUniform(String nameIn)
	{
		return new GLDebugUniformPointLight(this.parent, nameIn);
	}

	@Override
	public GLDebugUniformSpotLight spotLightUniform(String nameIn)
	{
		return new GLDebugUniformSpotLight(this.parent, nameIn);
	}

	@Override
	public GLDebugUniformDirectionalLight directionalLightUniform(String nameIn)
	{
		return new GLDebugUniformDirectionalLight(this.parent, nameIn);
	}

	@Override
	public IShaderTypedUniform<PointLight[]> pointLightsUniform(String nameIn, int sizeIn)
	{
		return new GLDebugUniformPointLights(this.parent, nameIn, sizeIn);
	}

	@Override
	public IShaderTypedUniform<SpotLight[]> spotLightsUniform(String nameIn, int sizeIn)
	{
		return new GLDebugUniformSpotLights(this.parent, nameIn, sizeIn);
	}

	@Override
	public GLDebugUniformFog fogUniform(String nameIn)
	{
		return new GLDebugUniformFog(this.parent, nameIn);
	}

	@Override
	public IShaderUniform[] uniforms(String... namesIn)
	{
		final var	uniforms	= new IShaderUniform[namesIn.length];

		var			i			= 0;
		for (final String name : namesIn)
		{
			uniforms[i] = new GLDebugUniform(this.parent, name);
			i++;
		}

		return uniforms;
	}
}