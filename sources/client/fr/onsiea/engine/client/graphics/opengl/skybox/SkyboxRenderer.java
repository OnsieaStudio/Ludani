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
package fr.onsiea.engine.client.graphics.opengl.skybox;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import fr.onsiea.engine.client.graphics.fog.Fog;
import fr.onsiea.engine.client.graphics.light.DirectionalLight;
import fr.onsiea.engine.client.graphics.mesh.IMesh;
import fr.onsiea.engine.client.graphics.opengl.shaders.ShaderSkybox;
import fr.onsiea.engine.client.graphics.shader.IShadersManager;
import fr.onsiea.engine.client.graphics.texture.ITexture;
import fr.onsiea.engine.client.resources.IResourcesPath;

/**
 * @author Seynax
 *
 */
public class SkyboxRenderer
{
	/**
	 * @param ofIn
	 * @return
	 */
	public final static IResourcesPath[] sort(IResourcesPath[] resourcesPathIn)
	{
		final var resourcesPath = new IResourcesPath[resourcesPathIn.length];

		for (final var resourcePath : resourcesPathIn)
		{
			if (resourcePath.file().getName().contains("right"))
			{
				resourcesPath[0] = resourcePath;
			}
			else if (resourcePath.file().getName().contains("left"))
			{
				resourcesPath[1] = resourcePath;
			}
			else if (resourcePath.file().getName().contains("top"))
			{
				resourcesPath[2] = resourcePath;
			}
			else if (resourcePath.file().getName().contains("bottom"))
			{
				resourcesPath[3] = resourcePath;
			}
			else if (resourcePath.file().getName().contains("back"))
			{
				resourcesPath[4] = resourcePath;
			}
			else if (resourcePath.file().getName().contains("front"))
			{
				resourcesPath[5] = resourcePath;
			}
		}

		return resourcesPath;
	}

	private final ITexture			texture;
	private final IMesh				mesh;

	private final IShadersManager	shadersManager;
	private final ShaderSkybox		shader;

	public SkyboxRenderer(IShadersManager shadersManagerIn, IMesh meshIn, ITexture textureIn)
	{
		this.shadersManager	= shadersManagerIn;
		this.shader			= (ShaderSkybox) shadersManagerIn.get("skybox");
		this.mesh			= meshIn;
		this.texture		= textureIn;
	}

	public SkyboxRenderer attach(Fog fogIn, Vector3f ambientLightIn, DirectionalLight directionalLightIn)
	{
		GL11.glDepthMask(false);
		//GL11.glDepthRange(1f, 1f);
		this.shader.attach();
		this.shader.fog().load(fogIn);
		this.shader.ambientLight().load(ambientLightIn);
		this.shader.dirLight().load(directionalLightIn);
		this.mesh.attach();
		this.texture.attach();

		return this;
	}

	public SkyboxRenderer draw()
	{
		this.mesh.draw();

		return this;
	}

	public SkyboxRenderer detach()
	{
		this.texture.detach();
		this.mesh.detach();
		this.shadersManager.detach();
		//GL11.glDepthRange(0f, 1f);
		GL11.glDepthMask(true);

		return this;
	}
}