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
package fr.onsiea.engine.game.scene.light;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joml.Vector3f;

import fr.onsiea.engine.client.graphics.light.DirectionalLight;
import fr.onsiea.engine.client.graphics.light.PointLight;
import fr.onsiea.engine.client.graphics.light.SpotLight;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Seynax
 *
 */
public class SceneLights
{
	private final List<PointLight>											pointLights;
	private final List<SpotLight>											spotLights;
	private final @Getter(AccessLevel.PUBLIC) DirectionalLight				directionalLight;
	private @Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) float	specularPower;
	private final @Getter(AccessLevel.PUBLIC) Vector3f						ambientLight;

	public SceneLights(DirectionalLight directionalLightIn, float specularPowerIn, Vector3f ambientLightIn)
	{
		this.pointLights		= new ArrayList<>();
		this.spotLights			= new ArrayList<>();

		this.directionalLight	= directionalLightIn;
		this.specularPower		= specularPowerIn;
		this.ambientLight		= ambientLightIn;
	}

	public SceneLights add(PointLight... pointLightsIn)
	{
		Collections.addAll(this.pointLights, pointLightsIn);

		return this;
	}

	public SceneLights add(SpotLight... spotLightsIn)
	{
		Collections.addAll(this.spotLights, spotLightsIn);

		return this;
	}

	public SceneLights ambientLight(float rIn, float gIn, float bIn)
	{
		this.ambientLight.set(rIn, gIn, bIn);

		return this;
	}
}