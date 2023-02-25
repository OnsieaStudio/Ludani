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
package fr.onsiea.engine.client.graphics.opengl.nanovg;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.system.MemoryUtil;

import fr.onsiea.engine.utils.IOUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Seynax
 *
 */
@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
public class NanoVGFonts
{
	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	@AllArgsConstructor
	public final static class Text
	{
		private int		fontSize;
		private String	fontName;
		private int		textAligns;
		private float	letterSpacing;
		private int		r;
		private int		g;
		private int		b;
		private int		a;
		private int		x;
		private int		y;
		private String	content;
		public int		fontId;

		public Text change(final int fontSizeIn, final String fontNameIn, final int textAlignsIn,
				final float letterSpacingIn, final int rIn, final int gIn, final int bIn, final int aIn, final int xIn,
				final int yIn, final String contentIn, final int fontIdIn)
		{
			this.fontSize		= fontSizeIn;
			this.fontName		= fontNameIn;
			this.textAligns		= textAlignsIn;
			this.r				= rIn;
			this.g				= gIn;
			this.b				= bIn;
			this.a				= aIn;
			this.x				= xIn;
			this.y				= yIn;
			this.content		= contentIn;
			this.fontId			= fontIdIn;
			this.letterSpacing	= letterSpacingIn;

			return this;
		}

		public Text change(final String contentIn)
		{
			this.content = contentIn;

			return this;
		}
	}

	private NanoVGManager			nanoVGManager;
	private Map<String, NanoVGFont>	fonts;
	private Map<String, Text>		texts;
	private List<ByteBuffer>		fontsBuffer;

	public NanoVGFonts(final NanoVGManager nanoVGManagerIn)
	{
		this.nanoVGManager	= nanoVGManagerIn;
		this.fonts			= new HashMap<>();
		this.texts			= new HashMap<>();
		this.fontsBuffer	= new ArrayList<>();
	}

	public NanoVGFonts addTextOrChange(final String idIn, final int fontSizeIn, final String fontNameIn,
			final int textAlignsIn, final float letterSpacingIn, final int rIn, final int gIn, final int bIn,
			final int aIn, final int xIn, final int yIn, final String contentIn)
	{
		final var text = this.texts.get(idIn);

		if (text != null)
		{
			text.change(fontSizeIn, fontNameIn, textAlignsIn, letterSpacingIn, rIn, gIn, bIn, aIn, xIn, yIn, contentIn,
					fontNameIn.equals(text.fontName) ? text.fontId
							: NanoVG.nvgFindFont(this.nanoVGManager().handle(), fontNameIn));
		}
		else
		{
			this.texts.put(idIn, new Text(fontSizeIn, fontNameIn, textAlignsIn, letterSpacingIn, rIn, gIn, bIn, aIn,
					xIn, yIn, contentIn, NanoVG.nvgFindFont(this.nanoVGManager().handle(), fontNameIn)));
		}

		return this;
	}

	public NanoVGFonts addText(final String idIn, final Text textIn)
	{
		this.texts.put(idIn, textIn);

		return this;
	}

	public NanoVGFonts removeText(final String idIn, final Text textIn)
	{
		this.texts.remove(idIn);

		return this;
	}

	public NanoVGFonts getText(final String idIn, final Text textIn)
	{
		this.texts.remove(idIn);

		return this;
	}

	public NanoVGFonts drawAllTexts()
	{
		for (final Text text : this.texts.values())
		{
			this.draw(text);
		}

		return this;
	}

	// TODO temporary constant
	private final static NVGColor color = NVGColor.malloc();

	public NanoVGFonts draw(final Text textIn)
	{
		if (textIn.fontId < 0)
		{
			textIn.fontId = NanoVG.nvgFindFont(this.nanoVGManager().handle(), textIn.fontName);
		}

		if (textIn.fontId >= 0)
		{
			NanoVG.nvgFontFaceId(this.nanoVGManager().handle(), textIn.fontId);
			NanoVG.nvgFontSize(this.nanoVGManager().handle(), textIn.fontSize);
			NanoVG.nvgTextAlign(this.nanoVGManager().handle(), textIn.textAligns);
			NanoVG.nvgFillColor(this.nanoVGManager().handle(),
					NanoVGManager.set(NanoVGFonts.color, textIn.r, textIn.g, textIn.b, textIn.a));
			NanoVG.nvgText(this.nanoVGManager().handle(), textIn.x, textIn.y, textIn.content);
		}

		return this;
	}

	public NanoVGFont get(final String nameIn)
	{
		return this.fonts().get(nameIn);
	}

	public NanoVGFonts add(final String filepathIn) throws Exception
	{
		final var file = new File(filepathIn);

		if (file.exists())
		{
			throw new IOException("Could not add font, file doesn't exists !");
		}

		return this.loadWithMemory(file.getName(), file);
	}

	public NanoVGFonts load(final String nameIn, final String filepathIn) throws Exception
	{
		final var font = NanoVG.nvgCreateFont(this.nanoVGManager().handle(), nameIn, filepathIn);

		if (font == -1)
		{
			throw new Exception("Could not add font");
		}
		this.fonts().put(nameIn, new NanoVGFont(new File(filepathIn), nameIn, font));

		return this;
	}

	public NanoVGFonts load(final String nameIn, final File fileIn) throws Exception
	{
		if (!fileIn.exists())
		{
			return null;
		}

		final var font = NanoVG.nvgCreateFont(this.nanoVGManager().handle(), nameIn, fileIn.getAbsolutePath());

		if (font == -1)
		{
			throw new Exception("Could not add font");
		}
		this.fonts().put(nameIn, new NanoVGFont(fileIn, nameIn, font));

		return this;
	}

	public NanoVGFonts loadWithMemory(final String nameIn, final File fileIn) throws Exception
	{
		if (!fileIn.exists())
		{
			return null;
		}

		final var fontBuffer = IOUtils.ioResourceToByteBuffer(fileIn.getAbsolutePath(), 150 * 1024);
		this.fontsBuffer.add(fontBuffer);
		final var font = NanoVG.nvgCreateFontMem(this.nanoVGManager().handle(), nameIn, fontBuffer, false);

		if (font == -1)
		{
			throw new Exception("Could not add font");
		}
		this.fonts().put(nameIn, new NanoVGFont(fileIn, nameIn, font));

		return this;
	}

	public final NanoVGFonts cleanup()
	{
		if (NanoVGFonts.color != null)
		{
			NanoVGFonts.color.free();
		}

		for (final var fontBuffer : this.fontsBuffer)
		{
			MemoryUtil.memFree(fontBuffer);
		}

		return this;
	}
}