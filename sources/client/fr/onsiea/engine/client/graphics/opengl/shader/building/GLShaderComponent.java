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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import fr.onsiea.engine.client.graphics.shader.EnumShaderComponentType;
import fr.onsiea.engine.client.graphics.shader.IShaderComponent;
import lombok.AccessLevel;
import lombok.Getter;

/**
 * @author Seynax
 *
 */
@Getter(AccessLevel.PUBLIC)
public class GLShaderComponent implements IShaderComponent
{
	private final String					name;
	private final String					content;
	private final EnumShaderComponentType	type;

	private final Map<String, String>		tags;

	public GLShaderComponent(String nameIn, EnumShaderComponentType typeIn)
	{
		this.name		= nameIn;
		this.content	= typeIn.baseContent();
		this.type		= typeIn;

		this.tags		= new HashMap<>();
	}

	public GLShaderComponent(String nameIn, String contentIn, EnumShaderComponentType typeIn)
	{
		this.name		= nameIn;
		this.content	= contentIn;
		this.type		= typeIn;

		this.tags		= new HashMap<>();
	}

	/**
	 * Main function shader component type
	 */
	public GLShaderComponent()
	{
		this.name		= "MAIN";
		this.content	= EnumShaderComponentType.MAIN.baseContent();
		this.type		= EnumShaderComponentType.MAIN;

		this.tags		= new HashMap<>();
	}

	@Override
	public IShaderComponent addTagValue(String tagNameIn, String valueIn)
	{
		this.tags.put(tagNameIn, valueIn);

		return this;
	}

	@Override
	public String content()
	{
		var			content			= this.content.replaceAll("<name>", this.name).replaceAll("<type>",
				this.type.name());

		final var	tagsIterator	= this.tags.entrySet().iterator();

		while (tagsIterator.hasNext())
		{
			final var	entry	= tagsIterator.next();

			final var	name	= entry.getKey();
			final var	value	= entry.getValue();

			content = content.replaceAll("<" + name + ">", value);
		}

		return content;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(this.name);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null || this.getClass() != obj.getClass())
		{
			return false;
		}
		final var other = (GLShaderComponent) obj;
		return Objects.equals(this.name, other.name);
	}
}