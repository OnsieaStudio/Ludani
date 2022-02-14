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
package fr.onsiea.engine.client.graphics.opengl.settings;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import fr.onsiea.engine.client.graphics.settings.IRenderAPIBooleanParameter;
import fr.onsiea.engine.client.graphics.settings.RenderAPIBooleanParameter;
import fr.onsiea.engine.client.graphics.settings.RenderAPIModulableBooleanParameter;
import fr.onsiea.engine.client.graphics.settings.RenderAPISettings;
import fr.onsiea.engine.utils.function.IIFunction;

/**
 * @author
import fr.onsiea.engine.utils.function.IIFunction; Seynax
 *
 */
public class OpenGLRenderAPISettings extends RenderAPISettings
{
	public OpenGLRenderAPISettings() throws Exception
	{
		this.add("antialiasing", GL13.GL_MULTISAMPLE).add("blend", GL11.GL_BLEND).add("cullBackface", parameterIn ->
		{
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glCullFace(GL11.GL_BACK);
		}, parameterIn -> GL11.glDisable(GL11.GL_CULL_FACE)).add("anisotropy");
	}

	private OpenGLRenderAPISettings add(String nameIn) throws Exception
	{
		this.put(nameIn, new RenderAPIBooleanParameter.Builder(this, nameIn).build());

		return this;
	}

	private OpenGLRenderAPISettings add(String nameIn, int idIn) throws Exception
	{
		this.put(nameIn, new RenderAPIModulableBooleanParameter.Builder(this, nameIn, parameterIn -> GL11.glEnable(idIn),
				parameterIn -> GL11.glDisable(idIn)).build());

		return this;
	}

	private OpenGLRenderAPISettings add(String nameIn, IIFunction<IRenderAPIBooleanParameter> enableMethodIn,
			IIFunction<IRenderAPIBooleanParameter> disableMethodIn) throws Exception
	{
		this.put(nameIn,
				new RenderAPIModulableBooleanParameter.Builder(this, nameIn, enableMethodIn, disableMethodIn).build());

		return this;
	}
}