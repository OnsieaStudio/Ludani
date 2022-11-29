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
package fr.onsiea.engine.client.graphics.opengl.shader.uniform;

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
public class GLUniformBuilder implements IShaderUniformBuilder
{
	private final GLShaderProgram parent;

	public GLUniformBuilder(GLShaderProgram parentIn)
	{
		this.parent = parentIn;
	}

	/**
	 * @param stringIn
	 * @return
	 */
	@Override
	public GLUniform uniform(String nameIn)
	{
		return new GLUniform(this.parent, nameIn);
	}

	/**
	 * @param stringIn
	 * @return
	 */
	@Override
	public GLUniformBoolean booleanUniform(String nameIn)
	{
		return new GLUniformBoolean(this.parent, nameIn);
	}

	@Override
	public GLUniformInt intUniform(String nameIn)
	{
		return new GLUniformInt(this.parent, nameIn);
	}

	@Override
	public GLUniformFloat floatUniform(String nameIn)
	{
		return new GLUniformFloat(this.parent, nameIn);
	}

	/**
	 * @param nameIn
	 * @return
	 */
	@Override
	public GLUniformFloatArray floatArrayUniform(String nameIn)
	{
		return new GLUniformFloatArray(this.parent, nameIn);
	}

	@Override
	public GLUniformVector2f vector2fUniform(String nameIn)
	{
		return new GLUniformVector2f(this.parent, nameIn);
	}

	@Override
	public GLUniformVector3f vector3fUniform(String nameIn)
	{
		return new GLUniformVector3f(this.parent, nameIn);
	}

	@Override
	public GLUniformVector4f vector4fUniform(String nameIn)
	{
		return new GLUniformVector4f(this.parent, nameIn);
	}

	@Override
	public GLUniformMatrix4f matrix4fUniform(String nameIn)
	{
		return new GLUniformMatrix4f(this.parent, nameIn);
	}

	@Override
	public GLUniformMaterial materialUniform(String nameIn)
	{
		return new GLUniformMaterial(this.parent, nameIn);
	}

	@Override
	public GLUniformPointLight pointLightUniform(String nameIn)
	{
		return new GLUniformPointLight(this.parent, nameIn);
	}

	@Override
	public GLUniformSpotLight spotLightUniform(String nameIn)
	{
		return new GLUniformSpotLight(this.parent, nameIn);
	}

	@Override
	public IShaderTypedUniform<PointLight[]> pointLightsUniform(String nameIn, int sizeIn)
	{
		return new GLUniformPointLights(this.parent, nameIn, sizeIn);
	}

	@Override
	public IShaderTypedUniform<SpotLight[]> spotLightsUniform(String nameIn, int sizeIn)
	{
		return new GLUniformSpotLights(this.parent, nameIn, sizeIn);
	}

	@Override
	public GLUniformDirectionalLight directionalLightUniform(String nameIn)
	{
		return new GLUniformDirectionalLight(this.parent, nameIn);
	}

	@Override
	public GLUniformFog fogUniform(String nameIn)
	{
		return new GLUniformFog(this.parent, nameIn);
	}

	@Override
	public IShaderUniform[] uniforms(String... namesIn)
	{
		final var	uniforms	= new IShaderUniform[namesIn.length];

		var			i			= 0;
		for (final String name : namesIn)
		{
			uniforms[i] = new GLUniform(this.parent, name);
			i++;
		}

		return uniforms;
	}
}