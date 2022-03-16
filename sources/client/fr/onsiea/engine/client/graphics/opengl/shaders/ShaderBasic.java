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
package fr.onsiea.engine.client.graphics.opengl.shaders;

import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderProgram;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniform;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformDirectionalLight;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformFog;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformMaterial;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformMatrix4f;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformPointLight;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformSpotLight;
import fr.onsiea.engine.client.graphics.shader.utils.IProjection;
import fr.onsiea.engine.client.graphics.shader.utils.IView;
import lombok.AccessLevel;
import lombok.Getter;

/**
 * @author Seynax
 *
 */
@Getter(AccessLevel.PUBLIC)
public class ShaderBasic extends GLShaderProgram implements IProjection, IView
{
	public enum Normal
	{
		FAKE, NORMAL, EXCLUSIVE_NORMAL, WITHOUT, NORMAL_WITH_TANGENT, SCENE;
	}

	private final static int				LIGHTS	= 5;

	private final GLUniformMatrix4f			projection;
	private final GLUniformMatrix4f			view;
	private final GLUniform					transformations;
	private final GLUniform					ambientLight;
	private final GLUniform					specularPower;
	private final GLUniformMaterial			material;
	private final GLUniform					hasNormalMap;
	private final GLUniformPointLight[]		pointLights;
	private final GLUniformSpotLight[]		spotLights;
	private final GLUniformDirectionalLight	directionalLight;
	private final GLUniform					textureSampler;
	private final GLUniform					normalMapSampler;
	private final GLUniformFog				fog;

	/**
	 * @throws Exception
	 */
	public ShaderBasic(Normal normalIn) throws Exception
	{
		super(Normal.FAKE.equals(normalIn) ? "resources/shaders/normalRendering/fakeNormalVertex.vs"
				: Normal.WITHOUT.equals(normalIn) ? "resources/shaders/normalRendering/vertex.vs"
						: Normal.NORMAL.equals(normalIn) || Normal.EXCLUSIVE_NORMAL.equals(normalIn)
								? "resources/shaders/normalRendering/normalVertex.vs"
								: Normal.SCENE.equals(normalIn) ? "resources/shaders/normalRendering/scene_vertex.vs"
										: "resources/shaders/normalRendering/normalVertexWithTangent.vs",

				Normal.WITHOUT.equals(normalIn) ? "resources/shaders/normalRendering/fragment.fs"
						: Normal.FAKE.equals(normalIn) ? "resources/shaders/normalRendering/exclusiveNormalFragment.fs"
								: Normal.NORMAL.equals(normalIn) ? "resources/shaders/normalRendering/normalFragment.fs"
										: Normal.EXCLUSIVE_NORMAL.equals(normalIn)
												? "resources/shaders/normalRendering/exclusiveNormalFragment.fs"
												: Normal.SCENE.equals(normalIn)
														? "resources/shaders/normalRendering/scene_fragment.fs"
														: "resources/shaders/normalRendering/normalFragmentWithTangent.fs");

		if (Normal.FAKE.equals(normalIn) || Normal.WITHOUT.equals(normalIn))
		{
			this.attributes("position", "texCoord");
		}
		else if (Normal.NORMAL.equals(normalIn))
		{
			this.attributes("position", "texCoord", "vertexNormal");
		}
		else if (Normal.NORMAL_WITH_TANGENT.equals(normalIn))
		{
			this.attributes("position", "texCoord", "vertexNormal", "tangent");
		}

		this.projection			= this.matrix4fUniform("projection");
		this.view				= this.matrix4fUniform("view");
		this.transformations	= this.uniform("transformations");
		this.ambientLight		= this.uniform("ambientLight");
		this.specularPower		= this.uniform("specularPower");
		this.material			= this.materialUniform("material");
		this.hasNormalMap		= this.uniform("hasNormalMap");
		this.pointLights		= new GLUniformPointLight[ShaderBasic.LIGHTS];
		this.spotLights			= new GLUniformSpotLight[ShaderBasic.LIGHTS];
		for (var i = 0; i < ShaderBasic.LIGHTS; i++)
		{
			this.pointLights[i]	= this.pointLightUniform("pointLights[" + i + "]");
			this.spotLights[i]	= this.spotLightUniform("spotLights[" + i + "]");
		}
		this.directionalLight	= this.directionalLightUniform("directionalLight");
		this.fog				= this.fogUniform("fog");
		this.attach();
		this.textureSampler = this.uniform("texture_sampler");
		this.textureSampler.load(0);
		this.normalMapSampler = this.uniform("normalMap_sampler");
		this.normalMapSampler.load(1);
	}
}