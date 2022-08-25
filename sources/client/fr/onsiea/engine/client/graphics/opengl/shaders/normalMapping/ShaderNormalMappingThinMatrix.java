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
package fr.onsiea.engine.client.graphics.opengl.shaders.normalMapping;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import fr.onsiea.engine.client.graphics.fog.Fog;
import fr.onsiea.engine.client.graphics.light.DirectionalLight;
import fr.onsiea.engine.client.graphics.light.PointLight;
import fr.onsiea.engine.client.graphics.light.SpotLight;
import fr.onsiea.engine.client.graphics.material.Material;
import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderProgram;
import fr.onsiea.engine.client.graphics.shader.uniform.IShaderTypedUniform;
import fr.onsiea.engine.client.graphics.shader.utils.IProjection;
import fr.onsiea.engine.client.graphics.shader.utils.IView;
import fr.onsiea.engine.game.scene.Scene;
import lombok.AccessLevel;
import lombok.Getter;

/**
 * @author ThinMatrix
 * @author Seynax
 */
@Getter(AccessLevel.PUBLIC)
public class ShaderNormalMappingThinMatrix extends GLShaderProgram implements IProjection, IView
{
	private final IShaderTypedUniform<Matrix4f>			transformations;
	private final IShaderTypedUniform<Matrix4f>			projection;
	private final IShaderTypedUniform<Matrix4f>			view;
	private final IShaderTypedUniform<Vector4f>			plane;
	private final IShaderTypedUniform<Matrix4f>			lightProjection;
	private final IShaderTypedUniform<Matrix4f>			lightView;
	private final IShaderTypedUniform<Matrix4f>			bias;

	private final IShaderTypedUniform<Integer>			texture;
	private final IShaderTypedUniform<Integer>			normalMap;
	private final IShaderTypedUniform<Integer>			shadowMap;
	private final IShaderTypedUniform<Vector3f>			ambientLight;
	private final IShaderTypedUniform<Float>			specularPower;
	private final IShaderTypedUniform<Material>			material;
	private final IShaderTypedUniform<Fog>				fog;
	private final IShaderTypedUniform<PointLight[]>		pointLights;
	private final IShaderTypedUniform<SpotLight[]>		spotLights;
	private final IShaderTypedUniform<DirectionalLight>	directionalLight;

	/**
	 * @throws Exception
	 */
	public ShaderNormalMappingThinMatrix() throws Exception
	{
		super("shaderBasic", "resources/shaders/normalRendering/thinmatrix/normalMapVShader.txt",
				"resources/shaders/normalRendering/thinmatrix/normalMapFShader.txt");

		this.attributes("position", "textureCoordinates", "normal", "tangent");

		this.transformations	= this.matrix4fUniform("transformation");
		this.projection			= this.matrix4fUniform("projection");
		this.view				= this.matrix4fUniform("view");
		this.plane				= this.vector4fUniform("plane");
		this.lightProjection	= this.matrix4fUniform("lightProjection");
		this.lightView			= this.matrix4fUniform("lightView");
		this.bias				= this.matrix4fUniform("bias");

		this.texture			= this.intUniform("textureSampler");
		this.normalMap			= this.intUniform("normalMapSampler");
		this.shadowMap			= this.intUniform("shadowMapSampler");
		this.ambientLight		= this.vector3fUniform("ambientLight");
		this.specularPower		= this.floatUniform("specularPower");
		this.material			= this.materialUniform("material");
		this.fog				= this.fogUniform("fog");

		this.pointLights		= this.pointLightsUniform("pointLights", Scene.MAX_LIGHTS);
		this.spotLights			= this.spotLightsUniform("spotLights", Scene.MAX_LIGHTS);

		this.directionalLight	= this.directionalLightUniform("directionalLight");

		this.attach();
		this.texture.load(0);
		this.normalMap.load(1);
		this.shadowMap.load(2);
	}
}