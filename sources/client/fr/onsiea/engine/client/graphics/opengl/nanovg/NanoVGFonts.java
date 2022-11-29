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
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;

import fr.onsiea.engine.utils.IOUtils;
import lombok.AccessLevel;
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
	private Map<String, NanoVGFont>	fonts;
	private NanoVGManager			nanoVGManager;

	public NanoVGFonts(NanoVGManager nanoVGManagerIn)
	{
		this.fonts			= new HashMap<>();
		this.nanoVGManager	= nanoVGManagerIn;
	}

	public NanoVGFonts draw(int fontSizeIn, String fontNameIn, int textAligns, NVGColor colorIn, int xIn, int yIn,
			String contentIn)
	{
		NanoVG.nvgFontSize(this.nanoVGManager().handle(), fontSizeIn);
		NanoVG.nvgFontFace(this.nanoVGManager().handle(), fontNameIn);
		NanoVG.nvgTextAlign(this.nanoVGManager().handle(), textAligns);
		NanoVG.nvgFillColor(this.nanoVGManager().handle(), colorIn);
		NanoVG.nvgText(this.nanoVGManager().handle(), xIn, yIn, contentIn);

		return this;
	}

	public NanoVGFont get(String nameIn)
	{
		return this.fonts().get(nameIn);
	}

	public NanoVGFonts add(String filepathIn) throws Exception
	{
		final var file = new File(filepathIn);

		if (file.exists())
		{
			throw new IOException("Could not add font, file doesn't exists !");
		}

		return this.add(file.getName(), file);
	}

	public NanoVGFonts add(String nameIn, String filepathIn) throws Exception
	{
		return this.add(nameIn, new File(filepathIn));
	}

	public NanoVGFonts add(String nameIn, File fileIn) throws Exception
	{
		final var	fontBuffer	= IOUtils.ioResourceToByteBuffer(fileIn.getAbsolutePath(), 150 * 1024);
		final var	font		= NanoVG.nvgCreateFontMem(this.nanoVGManager().handle(), nameIn, fontBuffer, 0);
		if (font == -1)
		{
			throw new Exception("Could not add font");
		}
		this.fonts().put(nameIn, new NanoVGFont(fileIn, nameIn));

		return this;
	}
}