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
package fr.onsiea.engine.client.graphics.shader.building;

/**
 * @author Seynax
 *
 */
public enum EnumShaderComponentType
{
	STRUCTURE("struct <name>\n{\n<instructions>\n}"), FUNCTION("<return> <name>(<parameters>)\n{\n<instructions>\n}"),
	MAIN("void main()\n{\n<main_instructions>\n}"), MAIN_INSTRUCTION(""),
	IN("in layout (location = <location>) <variable_type> <name>;"), IN_PASS("in <variable_type> <name>;"),
	UNIFORM("uniform <variable_type> <name>;"), OUT("out layout (location = <location>) <variable_type> <name>;"),
	OUT_PASS("out <variable_type> <name>;"), CONST("const <varialbe_type> <name> = <value>"),
	LOCAL("<varialbe_type> <name>");

	private String baseContent;

	EnumShaderComponentType(String baseContentIn)
	{
		this.baseContent = baseContentIn;
	}

	/**
	 * @return
	 */
	public String baseContent()
	{
		return this.baseContent;
	}
}