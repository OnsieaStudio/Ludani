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

import java.util.Collections;

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
public class GLShaderBuilder
{
	/**public final static GLShaderBuildingInstruction createInstruction(int... instructionsIn)
	{
		return new GLShaderBuildingInstruction(instructionsIn);
	}
	
	public final static GLShaderBuildingInstructions createInstructions(GLShaderBuildingInstruction... instructionsIn)
	{
		return new GLShaderBuildingInstructions(instructionsIn);
	}
	
	public final static GLShaderBuildingInstructions	_2D_POSITIONS_TEXTURECOORDS_NORMALS		= GLShaderBuilder
			.createInstructions(GLShaderBuilder.createInstruction(GLShaderBuilder._2D, GLShaderBuilder.POSITIONS,
					GLShaderBuilder.TEXTURECOORDS, GLShaderBuilder.NORMALS));
	public final static GLShaderBuildingInstructions	_3D_POSITIONS_TEXTURECOORDS_NORMALS		= GLShaderBuilder
			.createInstructions(GLShaderBuilder.createInstruction(GLShaderBuilder._3D, GLShaderBuilder.POSITIONS,
					GLShaderBuilder.TEXTURECOORDS, GLShaderBuilder.NORMALS));
	
	public final static GLShaderBuildingInstructions	POSITIONS3D_TEXTURECOORDS2D_NORMALS3D	= GLShaderBuilder
			.createInstructions(GLShaderBuilder.createInstruction(GLShaderBuilder._3D, GLShaderBuilder.POSITIONS),
					GLShaderBuilder.createInstruction(GLShaderBuilder._2D, GLShaderBuilder.TEXTURECOORDS),
					GLShaderBuilder.createInstruction(GLShaderBuilder._3D, GLShaderBuilder.NORMALS));
	
	public final static GLShaderBuildingInstructions	POSITIONS2D_TEXTURECOORDS2D_NORMALS3D	= GLShaderBuilder
			.createInstructions(
					GLShaderBuilder.createInstruction(GLShaderBuilder._2D, GLShaderBuilder.POSITIONS,
							GLShaderBuilder.TEXTURECOORDS),
					GLShaderBuilder.createInstruction(GLShaderBuilder._3D, GLShaderBuilder.NORMALS));
	
	public final static GLShaderBuildingInstructions	POSITIONS3D_TEXTURECOORDS2D_NORMALS2D	= GLShaderBuilder
			.createInstructions(GLShaderBuilder.createInstruction(GLShaderBuilder._3D, GLShaderBuilder.POSITIONS),
					GLShaderBuilder.createInstruction(GLShaderBuilder._2D, GLShaderBuilder.TEXTURECOORDS,
							GLShaderBuilder.NORMALS));
	
	public final static GLShaderBuildingInstructions	POSITIONS2D_TEXTURECOORDS3D_NORMALS3D	= GLShaderBuilder
			.createInstructions(GLShaderBuilder.createInstruction(GLShaderBuilder._2D, GLShaderBuilder.POSITIONS),
					GLShaderBuilder.createInstruction(GLShaderBuilder._3D, GLShaderBuilder.TEXTURECOORDS,
							GLShaderBuilder.NORMALS));
	
	public final static GLShaderBuildingInstructions	POSITIONS3D_TEXTURECOORDS3D_NORMALS2D	= GLShaderBuilder
			.createInstructions(
					GLShaderBuilder.createInstruction(GLShaderBuilder._3D, GLShaderBuilder.POSITIONS,
							GLShaderBuilder.TEXTURECOORDS),
					GLShaderBuilder.createInstruction(GLShaderBuilder._2D, GLShaderBuilder.NORMALS));**/

	private final static EnumShaderComponentType[]	ORDER	=
	{ EnumShaderComponentType.STRUCTURE, EnumShaderComponentType.CONST, EnumShaderComponentType.LOCAL,
			EnumShaderComponentType.IN, EnumShaderComponentType.IN_PASS, EnumShaderComponentType.UNIFORM,
			EnumShaderComponentType.OUT, EnumShaderComponentType.OUT_PASS, EnumShaderComponentType.FUNCTION,
			EnumShaderComponentType.MAIN };

	private final GLShaderData						vertexData;
	private String									vertexScript;
	private final GLShaderData						fragmentData;
	private String									fragmentScript;

	public GLShaderBuilder()
	{
		this.vertexData = new GLShaderData();
		this.vertexData.component(new GLShaderComponent());

		this.fragmentData = new GLShaderData();
		this.fragmentData.component(new GLShaderComponent());
	}

	public GLShaderBuilder(IShaderComponent[] vertexComponentIn, IShaderComponent[] fragmentComponentIn)
	{
		this.vertexData = new GLShaderData(vertexComponentIn);
		this.vertexData.component(new GLShaderComponent());

		this.fragmentData = new GLShaderData(fragmentComponentIn);
		this.fragmentData.component(new GLShaderComponent());
	}

	public GLShaderBuilder(int... attributesIn)
	{
		this.vertexData		= new GLShaderData();
		this.fragmentData	= new GLShaderData();

		/**final var flag = new FlagBoolean(attributesIn);
		if (!flag.has(GLShaderBuilder.NO_MAIN_METHOD))
		{
			if (!flag.has(GLShaderBuilder.VERTEX_NO_MAIN_METHOD))
			{
				this.vertexData.component(new GLShaderComponent());
			}
			if (!flag.has(GLShaderBuilder.FRAGMENT_NO_MAIN_METHOD))
			{
				this.fragmentData.component(new GLShaderComponent());
			}
		}
		
		if (flag.has(GLShaderBuilder.POSITIONS_TEXTURECOORDS_NORMALS_2F))
		{
			this.outPassVertexAttribute(EnumShaderVariableType.VEC2, "pass_texturecoords");
			this.inPassFragmentAttribute(EnumShaderVariableType.VEC2, "pass_texturecoords");
			this.vertexMainInstructions("pass_texturecoords = textureCoords;");
			this.inVertexAttribute(EnumShaderVariableType.VEC2, "positions", "textureCoords", "normals");
		}
		else if (flag.has(GLShaderBuilder.POSITIONS_TEXTURECOORDS_NORMALS_3F))
		{
			this.outPassVertexAttribute(EnumShaderVariableType.VEC3, "pass_texturecoords");
			this.inPassFragmentAttribute(EnumShaderVariableType.VEC3, "pass_texturecoords");
			this.vertexMainInstructions("pass_texturecoords = textureCoords;");
			this.inVertexAttribute(EnumShaderVariableType.VEC3, "positions", "textureCoords", "normals");
		}
		else if (flag.has(GLShaderBuilder.POSITIONS_NORMALS_2F))
		{
			if (flag.has(GLShaderBuilder.TEXTURECOORDS_2F))
			{
				this.outPassVertexAttribute(EnumShaderVariableType.VEC2, "pass_texturecoords");
				this.inPassFragmentAttribute(EnumShaderVariableType.VEC2, "pass_texturecoords");
				this.vertexMainInstructions("pass_texturecoords = textureCoords;");
				this.inVertexAttribute(EnumShaderVariableType.VEC2, "positions", "textureCoords", "normals");
			}
			else if (flag.has(GLShaderBuilder.TEXTURECOORDS_3F))
			{
				this.outPassVertexAttribute(EnumShaderVariableType.VEC3, "pass_texturecoords");
				this.inPassFragmentAttribute(EnumShaderVariableType.VEC3, "pass_texturecoords");
				this.vertexMainInstructions("pass_texturecoords = textureCoords;");
				this.inVertexAttribute(EnumShaderVariableType.VEC2, "positions");
				this.inVertexAttribute(EnumShaderVariableType.VEC3, "textureCoords");
				this.inVertexAttribute(EnumShaderVariableType.VEC2, "normals");
			}
			else
			{
				this.inVertexAttribute(EnumShaderVariableType.VEC2, "positions", "normals");
			}
		}
		else if (flag.has(GLShaderBuilder.POSITIONS_NORMALS_3F))
		{
			this.inVertexAttribute(EnumShaderVariableType.VEC3, "positions");
			if (flag.has(GLShaderBuilder.TEXTURECOORDS_2F))
			{
				this.outPassVertexAttribute(EnumShaderVariableType.VEC2, "pass_texturecoords");
				this.inPassFragmentAttribute(EnumShaderVariableType.VEC2, "pass_texturecoords");
				this.vertexMainInstructions("pass_texturecoords = textureCoords;");
				this.inVertexAttribute(EnumShaderVariableType.VEC2, "textureCoords");
			}
			else if (flag.has(GLShaderBuilder.TEXTURECOORDS_3F))
			{
				this.outPassVertexAttribute(EnumShaderVariableType.VEC3, "pass_texturecoords");
				this.inPassFragmentAttribute(EnumShaderVariableType.VEC3, "pass_texturecoords");
				this.vertexMainInstructions("pass_texturecoords = textureCoords;");
				this.inVertexAttribute(EnumShaderVariableType.VEC3, "textureCoords");
			}
			this.inVertexAttribute(EnumShaderVariableType.VEC3, "normals");
		}
		else
		{
			if (flag.has(GLShaderBuilder.POSITIONS_TEXTURECOORDS_2F))
			{
				this.outPassVertexAttribute(EnumShaderVariableType.VEC2, "pass_texturecoords");
				this.inPassFragmentAttribute(EnumShaderVariableType.VEC2, "pass_texturecoords");
				this.vertexMainInstructions("pass_texturecoords = textureCoords;");
				this.inVertexAttribute(EnumShaderVariableType.VEC2, "positions", "textureCoords");
			}
			else if (flag.has(GLShaderBuilder.POSITIONS_TEXTURECOORDS_3F))
			{
				this.outPassVertexAttribute(EnumShaderVariableType.VEC3, "pass_texturecoords");
				this.inPassFragmentAttribute(EnumShaderVariableType.VEC3, "pass_texturecoords");
				this.vertexMainInstructions("pass_texturecoords = textureCoords;");
				this.inVertexAttribute(EnumShaderVariableType.VEC3, "positions", "textureCoords");
			}
			else
			{
				if (flag.has(GLShaderBuilder.POSITIONS_2F))
				{
					this.inVertexAttribute(EnumShaderVariableType.VEC2, "positions");
				}
				else if (flag.has(GLShaderBuilder.POSITIONS_3F))
				{
					this.inVertexAttribute(EnumShaderVariableType.VEC3, "positions");
				}
				if (flag.has(GLShaderBuilder.TEXTURECOORDS_2F))
				{
					this.outPassVertexAttribute(EnumShaderVariableType.VEC2, "pass_texturecoords");
					this.inPassFragmentAttribute(EnumShaderVariableType.VEC2, "pass_texturecoords");
					this.vertexMainInstructions("pass_texturecoords = textureCoords;");
					this.inVertexAttribute(EnumShaderVariableType.VEC2, "textureCoords");
				}
				else if (flag.has(GLShaderBuilder.TEXTURECOORDS_3F))
				{
					this.outPassVertexAttribute(EnumShaderVariableType.VEC3, "pass_texturecoords");
					this.inPassFragmentAttribute(EnumShaderVariableType.VEC3, "pass_texturecoords");
					this.vertexMainInstructions("pass_texturecoords = textureCoords;");
					this.inVertexAttribute(EnumShaderVariableType.VEC3, "textureCoords");
				}
			}
			if (flag.has(GLShaderBuilder.NORMALS_2F))
			{
				this.inVertexAttribute(EnumShaderVariableType.VEC2, "normals");
			}
			else if (flag.has(GLShaderBuilder.NORMALS_3F))
			{
				this.inVertexAttribute(EnumShaderVariableType.VEC3, "normals");
			}
		}
		
		if (flag.has(GLShaderBuilder.ATTACHED_PROJECTION_VIEW_TRANSFORMATIONS))
		{
			this.vertexAttachedMatrices("projection", "view", "transformations");
		}
		else if (flag.has(GLShaderBuilder.ATTACHED_PROJECTION_VIEW))
		{
			this.vertexAttachedMatrices("projection", "view");
			if (flag.has(GLShaderBuilder.TRANSFORMATIONS))
			{
				this.vertexMatrices("transformations");
			}
		}
		else if (flag.has(GLShaderBuilder.ATTACHED_VIEW_TRANSFORMATIONS))
		{
			this.vertexAttachedMatrices("view", "transformations");
			if (flag.has(GLShaderBuilder.PROJECTION))
			{
				this.vertexMatrices("projection");
			}
		}
		else if (flag.has(GLShaderBuilder.ATTACHED_PROJECTION_TRANSFORMATIONS))
		{
			this.vertexAttachedMatrices("projection", "transformations");
			if (flag.has(GLShaderBuilder.VIEW))
			{
				this.vertexMatrices("view");
			}
		}
		else
		{
			if (flag.has(GLShaderBuilder.PROJECTION))
			{
				this.vertexMatrices("projection");
			}
			if (flag.has(GLShaderBuilder.VIEW))
			{
				this.vertexMatrices("view");
			}
			if (flag.has(GLShaderBuilder.TRANSFORMATIONS))
			{
				this.vertexMatrices("transformations");
			}
		}
		
		if (flag.has(GLShaderBuilder.TEXTURE_SAMPLER))
		{
			this.fragmentUniform(EnumShaderVariableType.SAMPLER2D, "texture");
		}**/
	}

	public GLShaderBuilder vertexMatrices(String... namesIn)
	{
		Collections.addAll(this.vertexData.separatedMatrices(), namesIn);

		return this;
	}

	public GLShaderBuilder vertexAttachedMatrices(String... namesIn)
	{
		Collections.addAll(this.vertexData.attachedMatrices(), namesIn);

		return this;
	}

	public GLShaderBuilder fragmentMatrices(String... namesIn)
	{
		Collections.addAll(this.fragmentData.separatedMatrices(), namesIn);

		return this;
	}

	public GLShaderBuilder fragmentAttachedMatrices(String... namesIn)
	{
		Collections.addAll(this.fragmentData.attachedMatrices(), namesIn);

		return this;
	}

	public GLShaderBuilder inVertexAttribute(EnumShaderVariableType variableTypeIn, String... namesIn)
	{
		this.vertexData.inAttribute(variableTypeIn, namesIn);

		return this;
	}

	public GLShaderBuilder vertexUniform(EnumShaderVariableType variableTypeIn, String... namesIn)
	{
		this.vertexData.uniform(variableTypeIn, namesIn);

		return this;
	}

	public GLShaderBuilder outVertexAttribute(EnumShaderVariableType variableTypeIn, String... namesIn)
	{
		this.vertexData.outAttribute(variableTypeIn, namesIn);

		return this;
	}

	public GLShaderBuilder outPassVertexAttribute(EnumShaderVariableType variableTypeIn, String... namesIn)
	{
		this.vertexData.outPassAttribute(variableTypeIn, namesIn);

		return this;
	}

	public GLShaderBuilder vertexConstAttribute(EnumShaderVariableType variableTypeIn, String... namesIn)
	{
		this.vertexData.constAttribute(variableTypeIn, namesIn);

		return this;
	}

	public GLShaderBuilder vertexLocalAttribute(EnumShaderVariableType variableTypeIn, String... namesIn)
	{
		this.vertexData.localAttribute(variableTypeIn, namesIn);

		return this;
	}

	/**
	 * @param contentIn
	 * @return
	 */
	public GLShaderBuilder vertexMainInstructions(String... contentsIn)
	{
		for (final String content : contentsIn)
		{
			this.vertexData
					.component(new GLShaderComponent(content, content, EnumShaderComponentType.MAIN_INSTRUCTION));
		}

		return this;
	}

	public GLShaderBuilder addVertexComponent(IShaderComponent... vertexComponentIn)
	{
		this.vertexData.component(vertexComponentIn);

		return this;
	}

	public GLShaderBuilder inFragmentAttribute(EnumShaderVariableType variableTypeIn, String... namesIn)
	{
		this.fragmentData.inAttribute(variableTypeIn, namesIn);

		return this;
	}

	public GLShaderBuilder inPassFragmentAttribute(EnumShaderVariableType variableTypeIn, String... namesIn)
	{
		this.fragmentData.inPassAttribute(variableTypeIn, namesIn);

		return this;
	}

	public GLShaderBuilder fragmentUniform(EnumShaderVariableType variableTypeIn, String... namesIn)
	{
		this.fragmentData.uniform(variableTypeIn, namesIn);

		return this;
	}

	public GLShaderBuilder outFragmentAttribute(EnumShaderVariableType variableTypeIn, String... namesIn)
	{
		this.fragmentData.outAttribute(variableTypeIn, namesIn);

		return this;
	}

	public GLShaderBuilder fragmentConstAttribute(EnumShaderVariableType variableTypeIn, String... namesIn)
	{
		this.fragmentData.constAttribute(variableTypeIn, namesIn);

		return this;
	}

	public GLShaderBuilder fragmentLocalAttribute(EnumShaderVariableType variableTypeIn, String... namesIn)
	{
		this.fragmentData.localAttribute(variableTypeIn, namesIn);

		return this;
	}

	/**
	 * @param contentIn
	 * @return
	 */
	public GLShaderBuilder fragmentMainInstructions(String... contentsIn)
	{
		for (final String content : contentsIn)
		{
			this.fragmentData
					.component(new GLShaderComponent(content, content, EnumShaderComponentType.MAIN_INSTRUCTION));
		}

		return this;
	}

	public GLShaderBuilder addFragmentComponent(IShaderComponent vertexComponentIn)
	{
		this.fragmentData.component(vertexComponentIn);

		return this;
	}

	public final void build()
	{
		final var	vertexMatrices			= GLShaderBuilder.matrices(this.vertexData);
		final var	fragmentMatrices		= GLShaderBuilder.matrices(this.fragmentData);

		final var	vertexScriptBuilder		= new StringBuilder();
		final var	fragmentScriptBuilder	= new StringBuilder();
		for (final EnumShaderComponentType currentType : GLShaderBuilder.ORDER)
		{
			GLShaderBuilder.build(vertexScriptBuilder, currentType, this.vertexData, vertexMatrices);
			GLShaderBuilder.build(fragmentScriptBuilder, currentType, this.fragmentData, fragmentMatrices);
		}

		this.vertexScript	= vertexScriptBuilder.toString().replaceAll("<main_instructions>",
				GLShaderBuilder.mainInstructions(this.vertexData, vertexMatrices));
		this.fragmentScript	= fragmentScriptBuilder.toString().replaceAll("<main_instructions>",
				GLShaderBuilder.mainInstructions(this.fragmentData, fragmentMatrices));
	}

	private final static void build(StringBuilder builderIn, EnumShaderComponentType currentTypeIn, GLShaderData dataIn,
			String matricesMulIn)
	{
		final var namedScripts = dataIn.components().get(currentTypeIn);

		if (namedScripts == null)
		{
			return;
		}

		final var	namedScriptsIterator	= namedScripts.entrySet().iterator();

		var			i						= 0;
		while (namedScriptsIterator.hasNext())
		{
			final var	entry		= namedScriptsIterator.next();

			// final var	name		= entry.getKey();
			final var	component	= entry.getValue();

			component.addTagValue("location", "" + i);
			component.addTagValue("matrices_mul", matricesMulIn);

			builderIn.append(component.content());

			if ((!EnumShaderComponentType.STRUCTURE.equals(currentTypeIn)
					|| !EnumShaderComponentType.FUNCTION.equals(currentTypeIn)
					|| !EnumShaderComponentType.MAIN.equals(currentTypeIn)) && !component.content().endsWith(";"))
			{
				builderIn.append(";");
			}

			builderIn.append("\n");
			i++;
		}
		builderIn.append("\n");
	}

	private final static String matrices(GLShaderData dataIn)
	{
		final var attachedMatrices = new StringBuilder();
		for (final String matrice : dataIn.attachedMatrices())
		{
			attachedMatrices.append(matrice);
		}
		final var mul = new StringBuilder();
		if (!attachedMatrices.toString().isBlank())
		{
			dataIn.uniform(EnumShaderVariableType.MAT4, attachedMatrices.toString());
			mul.append(attachedMatrices.toString());
			if (dataIn.separatedMatrices().size() > 0)
			{
				mul.append(" * ");
			}
		}
		var i = 0;
		for (final String matrice : dataIn.separatedMatrices())
		{
			dataIn.uniform(EnumShaderVariableType.MAT4, matrice);

			mul.append(matrice);
			if (i < dataIn.separatedMatrices().size() - 1)
			{
				mul.append(" * ");
			}

			i++;
		}

		return mul.toString();
	}

	public final static String mainInstructions(GLShaderData dataIn, String matricesMulIn)
	{
		final var namedScripts = dataIn.components().get(EnumShaderComponentType.MAIN_INSTRUCTION);

		if (namedScripts == null)
		{
			return "";
		}

		final var	builder					= new StringBuilder();
		final var	namedScriptsIterator	= namedScripts.entrySet().iterator();

		var			i						= 0;
		while (namedScriptsIterator.hasNext())
		{
			final var	entry		= namedScriptsIterator.next();

			// final var	name		= entry.getKey();
			final var	component	= entry.getValue();

			component.addTagValue("location", "" + i);
			component.addTagValue("matrices_mul", matricesMulIn);
			builder.append("	" + component.content());
			if (!component.content().endsWith(";"))
			{
				builder.append(";");
			}

			if (namedScriptsIterator.hasNext())
			{
				builder.append("\n");
			}

			i++;
		}
		return builder.toString();
	}
}