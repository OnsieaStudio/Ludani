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
package fr.onsiea.engine.client.graphics.opengl;

import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import fr.onsiea.engine.client.graphics.GraphicsConstants;
import fr.onsiea.engine.client.graphics.render.IRenderAPIContextSettings;
import fr.onsiea.engine.client.settings.ClientParameter;
import fr.onsiea.engine.client.settings.ClientSettings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Seynax
 *
 */
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PRIVATE)
public class OpenGLSettings implements IRenderAPIContextSettings
{
	private ClientSettings			user;
	private ClientSettings			engine;
	private OpenGLRenderAPIContext	context;

	public OpenGLSettings(final OpenGLRenderAPIContext contextIn) throws Exception
	{
		this.context(contextIn);

		this.user(new ClientSettings());
		this.engine(new ClientSettings());

		this.user()
				.add("antialiasing", parameterIn -> GL11.glEnable(GL13.GL_MULTISAMPLE),
						parameterIn -> GL11.glDisable(GL13.GL_MULTISAMPLE))
				.add("blend", parameterIn -> GL11.glEnable(GL11.GL_BLEND), parameterIn -> GL11.glDisable(GL11.GL_BLEND))
				.add("cullBackface", parameterIn -> {
					GL11.glEnable(GL11.GL_CULL_FACE);
					GL11.glCullFace(GL11.GL_BACK);
				}, parameterIn -> GL11.glDisable(GL11.GL_CULL_FACE)).add("anisotropy");

		this.engine()
				.add("wireframe", parameterIn -> GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE),
						parameterIn -> GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL))
				.add("alphaBlending", parameterIn -> {
					GL11.glEnable(GL11.GL_BLEND);
					GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				}, parameterIn -> GL11.glDisable(GL11.GL_BLEND)).add("additiveBlending", parameterIn -> {
					GL11.glEnable(GL11.GL_BLEND);
					GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
				}, parameterIn -> GL11.glDisable(GL11.GL_BLEND))
				.add("depthTesting", parameterIn -> GL11.glEnable(GL11.GL_DEPTH_TEST),
						parameterIn -> GL11.glDisable(GL11.GL_DEPTH_TEST))
				.add("mustAnisotropyTextureFiltering")
				.put("anisotropyTextureFilteringAmount", new ClientParameter.Builder<Float>(this.engine(),
						"anisotropyTextureFilteringAmount", (parameterIn, valueIn) -> {
							if (this.context().textureFilterAnisotropicIsCompatible())
							{
								if (valueIn < GL11
										.glGetFloat(EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT))
								{
									parameterIn.set(valueIn);
								}
								else if (GraphicsConstants.DEBUG)
				{
					System.err.println("GL_EXT texture filter anisotropic is not supported !");
				}

							}
						}).build());

	}
}