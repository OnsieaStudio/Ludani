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
package fr.onsiea.engine.client.graphics.texture;

import fr.onsiea.engine.client.graphics.texture.data.TextureData;
import lombok.AccessLevel;
import lombok.Getter;

/**
 * @author Seynax
 *
 */
public abstract class Texture<T extends ITextureSettings<T>> implements ITexture
{
	protected @Getter(AccessLevel.PUBLIC) final T				settings;
	protected @Getter(AccessLevel.PUBLIC) final ITextureData[]	textures;
	protected final int											id;

	protected Texture(final Texture<T> textureIn)
	{
		this.settings	= textureIn.settings;
		this.id			= textureIn.id;
		this.textures	= textureIn.textures;
	}

	protected Texture(final ITextureSettings<T> settingsIn, final ITextureData... texturesIn)
	{
		this.settings	= settingsIn.get();
		this.textures	= new ITextureData[texturesIn.length];
		var i = 0;
		for (final var component : texturesIn)
		{
			this.textures[i] = component;
			i++;
		}

		this.id = this.genId();

		this.load();
	}

	protected Texture(final int textureIdIn, final int heightIn, final int widthIn,
			final ITextureSettings<T> settingsIn)
	{
		this.settings		= settingsIn.get();
		this.textures		= new ITextureData[1];
		this.textures[0]	= new TextureData(widthIn, heightIn);
		this.id				= textureIdIn;
	}

	public boolean hasNotTexture()
	{
		return this.textures == null || this.textures.length <= 0;
	}

	/**
	 * @return New texture identifier
	 */
	protected abstract int genId();

	/**
	 * @param settingsIn
	 */
	protected abstract void load();

	/**
	 * @return Texture identifier (int)
	 */
	@Override
	public int id()
	{
		return this.id;
	}
}