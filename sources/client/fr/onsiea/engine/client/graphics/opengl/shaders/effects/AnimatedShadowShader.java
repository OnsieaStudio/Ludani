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
package fr.onsiea.engine.client.graphics.opengl.shaders.effects;

import org.joml.Matrix4f;

import fr.onsiea.engine.client.graphics.mesh.anim.AnimatedFrame;
import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderProgram;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformMatrix4f;
import fr.onsiea.engine.client.graphics.shader.uniform.IShaderTypedUniform;
import lombok.AccessLevel;
import lombok.Getter;

/**
 * @author Seynax
 *
 */
@Getter(AccessLevel.PUBLIC)
public class AnimatedShadowShader extends GLShaderProgram
{
	private final IShaderTypedUniform<Matrix4f>		lightProjection;
	private final IShaderTypedUniform<Matrix4f>		lightView;
	private final IShaderTypedUniform<Matrix4f>		bias;
	private final IShaderTypedUniform<Matrix4f>[]	jointsMatrix;

	/**
	 * @throws Exception
	 */
	public AnimatedShadowShader() throws Exception
	{
		super("shadow", "resources\\shaders\\shadow\\animatedDepthVertex.vs",
				"resources\\shaders\\shadow\\depthFragment.fs", "in_position", "texCoord", "vertexNormal",
				"jointWeights", "jointIndices");

		this.lightProjection	= this.matrix4fUniform("lightProjection");
		this.lightView			= this.matrix4fUniform("lightView");
		this.bias				= this.matrix4fUniform("bias");
		this.jointsMatrix		= new GLUniformMatrix4f[AnimatedFrame.MAX_JOINTS];
		for (var i = 0; i < AnimatedFrame.MAX_JOINTS; i++)
		{
			this.jointsMatrix[i] = this.matrix4fUniform("jointsMatrix[" + i + "]");
		}
	}
}