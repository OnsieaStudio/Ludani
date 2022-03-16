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
package fr.onsiea.engine.client.graphics.opengl.shader.building;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import fr.onsiea.engine.client.graphics.shader.EnumShaderComponentType;
import fr.onsiea.engine.client.graphics.shader.EnumShaderVariableType;
import fr.onsiea.engine.client.graphics.shader.IShaderComponent;
import lombok.AccessLevel;
import lombok.Getter;

/**
 * @author Seynax
 *
 */
@Getter(AccessLevel.PUBLIC)
public class GLShaderData
{
	private final Map<EnumShaderComponentType, Map<String, IShaderComponent>>	components;

	private final List<String>													attachedMatrices;
	private final List<String>													separatedMatrices;

	public GLShaderData(IShaderComponent... vertexAttributesIn)
	{
		this.components			= new LinkedHashMap<>();
		this.attachedMatrices	= new LinkedList<>();
		this.separatedMatrices	= new LinkedList<>();

		for (final IShaderComponent attribute : vertexAttributesIn)
		{
			this.component(attribute);
		}
	}

	public GLShaderData inAttribute(EnumShaderVariableType variableTypeIn, String... namesIn)
	{
		for (final String name : namesIn)
		{
			this.component(new GLShaderVariableComponent(name, EnumShaderComponentType.IN, variableTypeIn));
		}

		return this;
	}

	/**
	 * @param variableTypeIn
	 * @param namesIn
	 */
	public GLShaderData inPassAttribute(EnumShaderVariableType variableTypeIn, String[] namesIn)
	{
		for (final String name : namesIn)
		{
			this.component(new GLShaderVariableComponent(name, EnumShaderComponentType.IN_PASS, variableTypeIn));
		}

		return this;
	}

	public GLShaderData uniform(EnumShaderVariableType variableTypeIn, String... namesIn)
	{
		for (final String name : namesIn)
		{
			this.component(new GLShaderVariableComponent(name, EnumShaderComponentType.UNIFORM, variableTypeIn));
		}

		return this;
	}

	public GLShaderData outAttribute(EnumShaderVariableType variableTypeIn, String... namesIn)
	{
		for (final String name : namesIn)
		{
			this.component(new GLShaderVariableComponent(name, EnumShaderComponentType.OUT, variableTypeIn));
		}

		return this;
	}

	public GLShaderData outPassAttribute(EnumShaderVariableType variableTypeIn, String... namesIn)
	{
		for (final String name : namesIn)
		{
			this.component(new GLShaderVariableComponent(name, EnumShaderComponentType.OUT_PASS, variableTypeIn));
		}

		return this;
	}

	public GLShaderData constAttribute(EnumShaderVariableType variableTypeIn, String... namesIn)
	{
		for (final String name : namesIn)
		{
			this.component(new GLShaderVariableComponent(name, EnumShaderComponentType.CONST, variableTypeIn));
		}

		return this;
	}

	public GLShaderData localAttribute(EnumShaderVariableType variableTypeIn, String... namesIn)
	{
		for (final String name : namesIn)
		{
			this.component(new GLShaderVariableComponent(name, EnumShaderComponentType.LOCAL, variableTypeIn));
		}

		return this;
	}

	public GLShaderData component(IShaderComponent... componentsIn)
	{
		for (final IShaderComponent component : componentsIn)
		{
			var typedScripts = this.components.get(component.type());

			if (typedScripts == null)
			{
				typedScripts = new LinkedHashMap<>();
				this.components.put(component.type(), typedScripts);
			}

			typedScripts.put(component.name(), component);
		}

		return this;
	}
}