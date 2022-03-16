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

import fr.onsiea.engine.client.graphics.shader.EnumShaderVariableType;
import lombok.AccessLevel;
import lombok.Getter;

/**
 * @author Seynax
 *
 */
@Getter(AccessLevel.PUBLIC)
public class GLShaderBuilderInstructionsCompiler
{
	public final static int			_2D						= 0;
	public final static int			_3D						= 1;
	public final static int			_4D						= 3;
	public final static int			POSITIONS				= 4;
	public final static int			TEXTURECOORDS			= 5;
	public final static int			NORMALS					= 6;
	public final static int			SEPARATED_MATRICE		= 7;
	public final static int			ATTACHED_MATRICE		= 8;
	public final static int			TRANSFORMATIONS			= 9;
	public final static int			PROJECTION				= 10;
	public final static int			VIEW					= 11;
	public final static int			NO						= 12;
	public final static int			VERTEX					= 13;
	public final static int			FRAGMENT				= 14;
	public final static int			MAIN_METHOD				= 15;
	public final static int			TEXTURE_SAMPLER			= 16;
	public final static int			SIMPLE_TEXTURE_SCRIPT	= 17;

	private EnumShaderVariableType	variableType;
	private String					name;
	private int						index;
	public int						dimension;

	public GLShaderBuilderInstructionsCompiler(GLShaderBuildingInstructions... instructionsGroupsIn)
	{
		for (final var groups : instructionsGroupsIn)
		{
			for (final var group : groups.instructions)
			{
				for (final int instruction : group.instructions())
				{
					switch (instruction)
					{
						case _2D:
							this.dimension = GLShaderBuilderInstructionsCompiler._2D;
						case _3D:
							this.dimension = GLShaderBuilderInstructionsCompiler._3D;
						case _4D:
							this.dimension = GLShaderBuilderInstructionsCompiler._4D;
					}
				}
			}
		}
	}

	@Getter(AccessLevel.PUBLIC)
	final static class GLShaderBuildingInstruction
	{
		private int[] instructions;

		public GLShaderBuildingInstruction(int... instructionsIn)
		{
			var i = 0;
			for (final int instruction : instructionsIn)
			{
				this.instructions[i] = instruction;

				i++;
			}
		}
	}

	@Getter(AccessLevel.PUBLIC)
	final static class GLShaderBuildingInstructions
	{
		private GLShaderBuildingInstruction[] instructions;

		public GLShaderBuildingInstructions(GLShaderBuildingInstruction... instructionsIn)
		{
			var i = 0;
			for (final GLShaderBuildingInstruction instruction : instructionsIn)
			{
				this.instructions[i] = instruction;

				i++;
			}
		}
	}
}