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
package fr.onsiea.engine.client.graphics.opengl.mesh.draw;

import fr.onsiea.engine.client.graphics.opengl.mesh.GLMesh;
import fr.onsiea.engine.client.graphics.opengl.mesh.IMeshDrawFunction;
import fr.onsiea.engine.client.graphics.opengl.vao.Vao;
import fr.onsiea.engine.client.graphics.opengl.vao.VaoUtils;
import fr.onsiea.engine.client.graphics.opengl.vbo.Elements;
import fr.onsiea.engine.client.graphics.render.Renderer;

/**
 * @author Seynax
 *
 */
public class DrawersInstancedElements implements IMeshDrawFunction
{
	private final Elements	elements;
	private final Vao		vao;
	private final int		attribs;
	private final int		vertexCount;
	private final int		primCount;

	public DrawersInstancedElements(Elements elementsIn, Vao vaoIn, int attribsIn, int vertexCountIn, int primCountIn)
	{
		this.elements		= elementsIn;
		this.vao			= vaoIn;
		this.attribs		= attribsIn;
		this.vertexCount	= vertexCountIn;
		this.primCount		= primCountIn;
	}

	@Override
	public IMeshDrawFunction start(GLMesh meshIn, Renderer rendererIn)
	{
		VaoUtils.bindAndEnables(this.vao, this.attribs);

		return this;
	}

	@Override
	public IMeshDrawFunction draw(GLMesh meshIn, Renderer rendererIn)
	{
		this.elements.drawInstanced(this.vertexCount, this.primCount);

		return this;
	}

	@Override
	public IMeshDrawFunction stop(GLMesh meshIn, Renderer rendererIn)
	{
		VaoUtils.disablesAndUnbind(this.attribs);

		return this;
	}
}