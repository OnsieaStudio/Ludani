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

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import fr.onsiea.engine.client.graphics.opengl.OpenGLRenderAPIContext;
import fr.onsiea.engine.client.graphics.texture.ITextureSettings;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Seynax
 *
 */
@Getter(AccessLevel.PUBLIC)
public class GLTextureSettings implements ITextureSettings<GLTextureSettings>
{
	private final OpenGLRenderAPIContext	context;
	private final int						min, mag;
	private final int						wrapS, wrapT;
	private final boolean					mipmapping;

	private final int						pixelStoreAlignement;
	private final int						textureBaseLevel;
	private final int						textureMaxLevel;
	private final float						lodBias;
	private final int						lodMin, lodMax;

	private final int						textureCompareFunc;
	private final int						textureCompareMode;

	private final boolean					mustAnisotropyTextureFiltering;
	private final float						anisotropyTextureFiltering;

	private @Setter(AccessLevel.PUBLIC) int	target;
	private final int						level;
	private final int						internalFormat;
	private final int						border;
	private final int						format;
	private final int						type;

	private GLTextureSettings(GLTextureSettings.Builder builderIn)
	{
		this.context						= builderIn.context;
		this.min							= builderIn.min;
		this.mag							= builderIn.mag;
		this.wrapS							= builderIn.wrapS;
		this.wrapT							= builderIn.wrapT;
		this.mipmapping						= builderIn.mipmapping;
		this.pixelStoreAlignement			= builderIn.pixelStoreAlignement;
		this.textureBaseLevel				= builderIn.textureBaseLevel;
		this.textureMaxLevel				= builderIn.textureMaxLevel;
		this.lodBias						= builderIn.lodBias;
		this.lodMin							= builderIn.lodMin;
		this.lodMax							= builderIn.lodMax;
		this.textureCompareFunc				= builderIn.textureCompareFunc;
		this.textureCompareMode				= builderIn.textureCompareMode;
		this.mustAnisotropyTextureFiltering	= builderIn.mustAnisotropyTextureFiltering;
		this.anisotropyTextureFiltering		= builderIn.anisotropyTextureFiltering;
		this.target							= builderIn.target;
		this.level							= builderIn.level;
		this.internalFormat					= builderIn.internalFormat;
		this.border							= builderIn.border;
		this.format							= builderIn.format;
		this.type							= builderIn.type;
	}

	@Override
	public final GLTextureSettings get()
	{
		return this;
	}

	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	@AllArgsConstructor
	public final static class Builder
	{
		public static GLTextureSettings of(OpenGLRenderAPIContext contextIn)
		{
			return new Builder(contextIn).build();
		}

		public static GLTextureSettings of(OpenGLRenderAPIContext contextIn, int minIn, int magIn, int wrapSIn,
				int wrapTIn, boolean mipmappingIn)
		{
			return new Builder(contextIn, minIn, magIn, wrapSIn, wrapTIn, mipmappingIn).build();
		}

		private OpenGLRenderAPIContext	context;
		private int						min, mag;
		private int						wrapS, wrapT;
		private boolean					mipmapping;

		private int						pixelStoreAlignement;
		private int						textureMaxLevel;
		private int						textureBaseLevel;
		private float					lodBias;
		private int						lodMin, lodMax;
		private int						textureCompareFunc;
		private int						textureCompareMode;
		private boolean					mustAnisotropyTextureFiltering;
		private float					anisotropyTextureFiltering;

		private int						target;
		private int						level;
		private int						internalFormat;
		private int						border;
		private int						format;
		private int						type;

		public Builder(OpenGLRenderAPIContext contextIn)
		{
			this.context						= contextIn;

			this.min							= GL11.GL_LINEAR_MIPMAP_LINEAR;
			this.mag							= GL11.GL_LINEAR;
			this.wrapS							= GL13.GL_CLAMP_TO_BORDER;
			this.wrapT							= GL13.GL_CLAMP_TO_BORDER;
			this.mipmapping						= true;

			this.pixelStoreAlignement			= 1;

			this.textureMaxLevel				= 1000;
			this.textureBaseLevel				= 0;
			this.lodBias						= -0.4f;
			this.lodMin							= -1000;
			this.lodMax							= 1000;
			this.textureCompareFunc				= GL11.GL_LEQUAL;
			this.textureCompareMode				= GL11.GL_NONE;
			this.mustAnisotropyTextureFiltering	= true;
			this.anisotropyTextureFiltering		= -1;							// default value

			this.target							= -1;
			this.level							= 0;
			this.internalFormat					= GL11.GL_RGBA;
			this.border							= 0;
			this.format							= GL11.GL_RGBA;
			this.type							= GL11.GL_UNSIGNED_BYTE;
		}

		public Builder(OpenGLRenderAPIContext contextIn, int minIn, int magIn, int wrapSIn, int wrapTIn,
				boolean mipmappingIn)
		{
			this.context						= contextIn;
			this.min							= minIn;
			this.mag							= magIn;
			this.wrapS							= wrapSIn;
			this.wrapT							= wrapTIn;
			this.mipmapping						= mipmappingIn;

			this.pixelStoreAlignement			= 1;

			this.textureMaxLevel				= 1000;
			this.textureBaseLevel				= 0;
			this.lodBias						= -0.4f;
			this.lodMin							= -1000;
			this.lodMax							= 1000;
			this.textureCompareFunc				= GL11.GL_LEQUAL;
			this.textureCompareMode				= GL11.GL_NONE;
			this.mustAnisotropyTextureFiltering	= true;
			this.anisotropyTextureFiltering		= -1;					// default value

			this.target							= -1;
			this.level							= 0;
			this.internalFormat					= GL11.GL_RGBA;
			this.border							= 0;
			this.format							= GL11.GL_RGBA;
			this.type							= GL11.GL_UNSIGNED_BYTE;
		}

		public GLTextureSettings build()
		{
			return new GLTextureSettings(this);
		}
	}
}