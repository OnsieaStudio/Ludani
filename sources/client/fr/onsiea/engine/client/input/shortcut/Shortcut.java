/**
*	Copyright 2021 Onsiea All rights reserved.
*
*	This file is part of Onsiea Engine. (https://github.com/Onsiea/OnsieaEngine)
*
*	Unless noted in license (https://github.com/Onsiea/OnsieaEngine/blob/main/LICENSE.md) notice file (https://github.com/Onsiea/OnsieaEngine/blob/main/LICENSE_NOTICE.md), Onsiea engine and all parts herein is licensed under the terms of the LGPL-3.0 (https://www.gnu.org/licenses/lgpl-3.0.html)  found here https://www.gnu.org/licenses/lgpl-3.0.html and copied below the license file.
*
*	Onsiea Engine is libre software: you can redistribute it and/or modify
*	it under the terms of the GNU Lesser General Public License as published by
*	the Free Software Foundation, either version 3.0 of the License, or
*	(at your option) any later version.
*
*	Onsiea Engine is distributed in the hope that it will be useful,
*	but WITHOUT ANY WARRANTY; without even the implied warranty of
*	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*	GNU Lesser General Public License for more details.
*
*	You should have received a copy of the GNU Lesser General Public License
*	along with Onsiea Engine.  If not, see <https://www.gnu.org/licenses/> <https://www.gnu.org/licenses/lgpl-3.0.html>.
*
*	Neither the name "Onsiea", "Onsiea Engine", or any derivative name or the names of its authors / contributors may be used to endorse or promote products derived from this software and even less to name another project or other work without clear and precise permissions written in advance.
*
*	(more details on https://github.com/Onsiea/OnsieaEngine/wiki/License)
*
*	@author Seynax
*/
package fr.onsiea.engine.client.input.shortcut;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import lombok.Getter;

/**
 * @author Seynax
 *
 */
@Getter
public class Shortcut implements IShortcut
{
	protected final String					name;
	private @Getter final List<ButtonNode>	entryPoints;
	protected final ShortcutsManager		shortcutsManager;
	protected @Getter final List<String>	contexts;

	private boolean							isEnabled	= false;

	public Shortcut(final String nameIn, final ButtonNode firstIn, final ShortcutsManager shortcutsManagerIn,
			final String... contextsIn)
	{
		this.name			= nameIn;
		this.entryPoints	= new LinkedList<>();
		this.entryPoints.add(firstIn);
		this.shortcutsManager	= shortcutsManagerIn;
		this.contexts			= new LinkedList<>();
		Collections.addAll(this.contexts, contextsIn);
	}

	public Shortcut(final String nameIn, final ButtonNode firstIn, final ShortcutsManager shortcutsManagerIn,
			final List<String> contextsIn)
	{
		this.name			= nameIn;
		this.entryPoints	= new LinkedList<>();
		this.entryPoints.add(firstIn);
		this.shortcutsManager	= shortcutsManagerIn;
		this.contexts			= new LinkedList<>();
		this.contexts.addAll(contextsIn);
	}

	public final boolean containsActiveButton()
	{
		final var start = System.nanoTime();
		for (final var contexted : this.shortcutsManager.all())
		{
			if (contexted.isEnabled() && !contexted.name().contentEquals(this.name)
					&& this.containsOneButtonOf(contexted))
			{
				return true;
			}
		}

		return false;
	}

	public final boolean containsOneButtonOf(final IShortcut shortcutIn)
	{
		for (final var entryPoint0 : ((Shortcut) shortcutIn).entryPoints)
		{
			for (final var entryPoint1 : this.entryPoints)
			{
				var iterator0 = entryPoint0;
				while (iterator0 != null)
				{
					var iterator1 = entryPoint1;
					while (iterator1 != null)
					{
						if (iterator1.button().name().equals(iterator0.button().name()))
						{

							return true;
						}

						iterator1 = iterator1.next();
					}

					iterator0 = iterator0.next();
				}
			}
		}

		return false;
	}

	public final boolean isInActiveContext()
	{
		return this.contexts.contains(this.shortcutsManager.context());
	}

	@Override
	public final void update()
	{
		if (this.isTriggered() && this.isInActiveContext() && !this.containsActiveButton())
		{
			this.isEnabled = true;
		}
		else if (this.isReleased() || this.containsActiveButton())
		{
			this.isEnabled = false;
		}
	}

	@Override
	public final IShortcut addEntryPoint(final ButtonNode buttonNodeIn)
	{
		this.entryPoints.add(buttonNodeIn);

		return this;
	}

	@Override
	public boolean isEnabled()

	{
		return this.isEnabled;
	}

	@Override
	public boolean isJustTriggered()
	{
		if (!this.isInActiveContext())
		{
			return false;
		}

		for (final var entryPoint : this.entryPoints)
		{
			if (entryPoint.isJustTriggered())
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isTriggered()
	{
		if (!this.isInActiveContext())
		{
			return false;
		}

		for (final var entryPoint : this.entryPoints)
		{
			if (entryPoint.isTriggered())
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isReleased()
	{
		for (final var entryPoint : this.entryPoints)
		{
			if (!entryPoint.isReleased())
			{
				return false;
			}
		}

		return true;
	}
}