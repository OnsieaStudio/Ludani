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
package fr.onsiea.engine.game.scene;

import java.util.LinkedHashMap;
import java.util.Map;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import fr.onsiea.engine.client.graphics.fog.Fog;
import fr.onsiea.engine.client.graphics.light.DirectionalLight;
import fr.onsiea.engine.client.graphics.opengl.flare.FlareManager;
import fr.onsiea.engine.client.graphics.opengl.skybox.SkyboxRenderer;
import fr.onsiea.engine.client.graphics.particles.ParticlesManager;
import fr.onsiea.engine.client.graphics.render.IRenderAPIContext;
import fr.onsiea.engine.client.graphics.window.IWindow;
import fr.onsiea.engine.client.input.InputManager;
import fr.onsiea.engine.core.entity.Camera;
import fr.onsiea.engine.game.scene.item.SceneItems;
import fr.onsiea.engine.game.scene.light.SceneLights;
import fr.onsiea.engine.utils.time.Timer;
import lombok.AccessLevel;
import lombok.Getter;

/**
 * @author Seynax
 *
 */
@Getter(AccessLevel.PUBLIC)
public class Scene
{
	private final SceneLights						sceneLights;
	private final SceneItems						sceneItems;
	private final Fog								fog;
	private final Camera							camera;

	private float									lightAngle;
	private float									angleInc;

	private final SceneRenderer						sceneRenderer;

	// Sun

	private final Vector3f							sunPosition;
	private final boolean							hasSun;

	// Particles

	private final Map<String, ParticlesManager<?>>	particlesManagers;

	// Timers

	private final Timer								inputTimer;
	private final Timer								updateTimer;

	public Scene(IRenderAPIContext contextIn, IWindow windowIn, float clearColorRIn, float clearColorGIn,
			float clearColorBIn, DirectionalLight directionalLightIn, float specularPowerIn, Vector3f ambientLightIn,
			Fog fogIn, FlareManager flareManagerIn, SkyboxRenderer skyboxRendererIn) throws Exception
	{
		this.sceneLights			= new SceneLights(directionalLightIn, specularPowerIn, ambientLightIn);
		this.sceneItems				= new SceneItems();
		this.fog					= fogIn;
		this.camera					= new Camera();
		this.camera.position().z	= 2.0f;

		this.lightAngle				= 45.0f;
		this.angleInc				= 0.0f;

		this.sceneRenderer			= new SceneRenderer(contextIn, windowIn, clearColorRIn, clearColorGIn,
				clearColorBIn, fogIn, this.camera, flareManagerIn, skyboxRendererIn);

		// Sun

		this.sunPosition			= new Vector3f();
		this.hasSun					= false;

		// Particles

		this.particlesManagers		= new LinkedHashMap<>();

		// Timers

		this.inputTimer				= new Timer();
		this.updateTimer			= new Timer();
	}

	public final Scene input(IWindow windowIn, InputManager inputManagerIn)
	{
		if (this.inputTimer.isTime(1_000_000_0L))
		{
			this.camera.input(windowIn, inputManagerIn);
			if (inputManagerIn.glfwGetKey(GLFW.GLFW_KEY_LEFT) == GLFW.GLFW_PRESS)
			{
				this.angleInc -= 0.05f;
			}
			else if (inputManagerIn.glfwGetKey(GLFW.GLFW_KEY_RIGHT) == GLFW.GLFW_PRESS)
			{
				this.angleInc += 0.05f;
			}
			else
			{
				this.angleInc = 0;
			}
		}

		return this;
	}

	public final Scene render(IWindow windowIn)
	{
		this.lightAngle += this.angleInc;
		if (this.lightAngle < 0)
		{
			this.lightAngle = 0;
		}
		else if (this.lightAngle > 180)
		{
			this.lightAngle = 180;
		}
		final var	zValue			= (float) Math.cos(Math.toRadians(this.lightAngle));
		final var	yValue			= (float) Math.sin(Math.toRadians(this.lightAngle));
		final var	lightDirection	= this.sceneLights.directionalLight().direction();
		lightDirection.x	= 0;
		lightDirection.y	= yValue;
		lightDirection.z	= zValue;
		lightDirection.normalize();

		this.sceneRenderer.render(this, windowIn, false);

		return this;
	}

	public final void cleanup()
	{
		this.sceneRenderer().cleanup();
	}
}