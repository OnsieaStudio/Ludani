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
package fr.onsiea.engine.client.resources;

import java.io.File;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * @author Seynax
 *
 */
@Getter(AccessLevel.PUBLIC)
public class ResourcesPath implements IResourcesPath
{
	public final static ResourcesPath[] of(IResourcesPath rootIn) throws Exception
	{
		final var	path	= rootIn.path();
		final var	root	= new File(path);

		if (!root.exists())
		{
			return null;
		}
		if (root.isFile())
		{
			throw new Exception("[ERROR] \"" + path + "\" is file  !");
		}

		final var	files			= root.listFiles();
		final var	resourcespath	= new ResourcesPath[files.length];
		var			i				= 0;
		for (final File file : files)
		{
			resourcespath[i] = new ResourcesPath(rootIn, file.getName());

			i++;
		}

		return resourcespath;
	}

	public String			child;
	public IResourcesPath	parent;

	private final File		file;

	public ResourcesPath(String childIn)
	{
		this.parent	= ResourcesRootPath.ROOT;
		this.child	= childIn;
		this.file	= new File(childIn);
	}

	public ResourcesPath(IResourcesPath parentIn, String childIn)
	{
		this.parent	= parentIn;
		this.child	= childIn;
		this.file	= new File(this.path());
	}

	@Override
	public String path()
	{
		return this.parent.path() + "\\" + this.child;
	}

	@Override
	public File file()
	{
		return this.file;
	}
}