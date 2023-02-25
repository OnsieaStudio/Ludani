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
package fr.onsiea.engine.client.graphics.opengl.texture;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import fr.onsiea.engine.client.graphics.opengl.OpenGLRenderAPIContext;
import fr.onsiea.engine.client.graphics.texture.ITextureData;
import fr.onsiea.engine.client.graphics.texture.ITextureSettings;
import fr.onsiea.engine.client.graphics.texture.Texture;
import fr.onsiea.engine.client.graphics.texture.TexturesManager;
import fr.onsiea.engine.utils.ICleanable;

/**
 * @author Seynax
 *
 *
 * Specialized texture manager for opengl, managing texture tables and using the generic texture manager.
 */
public class GLTexturesManager extends TexturesManager<GLTextureSettings>
{
	private final OpenGLRenderAPIContext		renderAPIContext;
	private Map<String, GLTextureArrayManager>	texturesArrayManager;

	public GLTexturesManager(final OpenGLRenderAPIContext renderAPIContextIn)
	{
		this.renderAPIContext = renderAPIContextIn;
		this.texturesArrayManager(new HashMap<>());
	}

	@Override
	public TexturesManager<GLTextureSettings> resetIndex()
	{
		GL13.glActiveTexture(GL13.GL_TEXTURE0);

		return this;
	}

	@Override
	protected Texture<GLTextureSettings> create(final ITextureSettings<GLTextureSettings> settingsIn,
			final ITextureData... texturesIn)
	{
		return new GLTexture(settingsIn, texturesIn);
	}

	@Override
	protected Texture<GLTextureSettings> create(final int textureIdIn, final int heightIn, final int widthIn,
			final ITextureSettings<GLTextureSettings> settingsIn)
	{
		return new GLTexture(textureIdIn, widthIn, heightIn, settingsIn);
	}

	@Override
	protected TexturesManager<GLTextureSettings> deleteTextures()
	{
		final var	textures	= new int[this.textures.size()];
		var			i			= 0;
		for (final var texture : this.textures.values())
		{
			textures[i] = texture.id();
			i++;
		}

		GL11.glDeleteTextures(textures);

		return this;
	}

	public GLTextureArrayManager newTextureArray(final String nameIn, final int levelsIn, final int sizeXIn,
			final int sizeYIn, final int depthIn)
	{
		final var textureArrayManager = new GLTextureArrayManager(levelsIn, sizeXIn, sizeYIn, depthIn);

		this.texturesArrayManager().put(nameIn, textureArrayManager);

		return textureArrayManager;
	}

	public GLTextureArrayManager textureArrayManager(final String nameIn)
	{
		return this.texturesArrayManager().get(nameIn);
	}

	@Override
	public ICleanable cleanup()
	{
		this.texturesArrayManager.clear();

		return super.cleanup();
	}

	private final Map<String, GLTextureArrayManager> texturesArrayManager()
	{
		return this.texturesArrayManager;
	}

	private final void texturesArrayManager(final Map<String, GLTextureArrayManager> texturesArrayManagerIn)
	{
		this.texturesArrayManager = texturesArrayManagerIn;
	}

	@Override
	public ITextureSettings<GLTextureSettings> defaultTextureSettings()
	{
		return GLTextureSettings.Builder.of(this.renderAPIContext);
	}
}