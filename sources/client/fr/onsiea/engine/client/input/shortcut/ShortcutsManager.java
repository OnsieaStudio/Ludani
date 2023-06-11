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

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import fr.onsiea.engine.client.input.ButtonsManager;
import fr.onsiea.engine.client.input.cursor.Cursor;
import fr.onsiea.engine.client.input.keyboard.Keyboard;
import fr.onsiea.engine.game.GameTest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Seynax
 *
 */
public class ShortcutsManager
{
	private @Getter @Setter String				context;
	private final Map<String, IShortcut>		shortcuts;
	private final Map<String, List<IShortcut>>	contextedShortcuts;
	private final Keyboard						keyboard;
	private final Cursor						cursor;
	private final ButtonsManager				buttonsManager;

	private IShortcut last;

	public ShortcutsManager(final ButtonsManager buttonsManagerIn, final Keyboard keyboardIn, final Cursor cursorIn, final IShortcut... shortcutsIn)
	{
		this.shortcuts			= new HashMap<>();
		this.contextedShortcuts	= new HashMap<>();
		this.buttonsManager		= buttonsManagerIn;
		this.keyboard			= keyboardIn;
		this.cursor				= cursorIn;
		this.context			= "GENERAL";
	}

	public final ShortcutsManager update()
	{
		for (final var shortcut : this.shortcuts.values())
		{
			shortcut.update();
		}

		return this;
	}

	public final ShortcutsManager setContext(final String contextIn)
	{
		this.context = contextIn;

		return this;
	}

	private final ButtonNode makeButtonsNode(final String... buttonNamesIn)
	{
		ButtonNode	first		= null;
		ButtonNode	iterator	= null;
		for (final var buttonName : buttonNamesIn)
		{
			final var button = this.buttonsManager.button(buttonName);
			if (button == null)
			{
				GameTest.loggers.logLn("[INFO] " + buttonName + " button not found !");

				continue;
			}

			if (iterator == null)
			{
				iterator	= new ButtonNode(button);
				first		= iterator;
			}
			else
			{
				final var next = new ButtonNode(button);

				iterator.next(next);

				iterator = next;
			}
		}

		return first;
	}

	public final ShortcutsManager addAll(final IShortcut... shortcutsIn)
	{
		for (final var shortcut : shortcutsIn)
		{
			this.last = shortcut;
			this.add(shortcut);
		}

		return this;
	}

	public final ShortcutsManager add(final IShortcut shortcutIn)
	{
		this.last = shortcutIn;
		this.shortcuts.put(shortcutIn.name(), shortcutIn);

		for (final var context : shortcutIn.contexts())
		{
			var list = this.contextedShortcuts.get(context);

			if (list == null)
			{
				list = new LinkedList<>();
				this.contextedShortcuts.put(context, list);
			}

			list.add(shortcutIn);
		}

		return this;
	}

	public final ShortcutsManager addEntryPoint(final String... buttonsIn)
	{
		this.last.addEntryPoint(this.makeButtonsNode(buttonsIn));

		return this;
	}

	public final ShortcutsManager addEntryPoint(final ButtonNode buttonNodeIn)
	{
		this.last.addEntryPoint(buttonNodeIn);

		return this;
	}

	public ShortcutsManager addContext(final String contextIn)
	{
		this.last.contexts().add(contextIn);

		return this;
	}

	public final ShortcutsManager add(final String nameIn, final String... buttonsIn)
	{
		this.last = new Shortcut(nameIn, this.makeButtonsNode(buttonsIn), this, this.context);
		this.add(this.last);

		return this;
	}

	public IShortcut get(final String shortcutNameIn)
	{
		return this.shortcuts.get(shortcutNameIn);
	}

	public ShortcutsManager remove(final String shortcutNameIn)
	{
		this.shortcuts.remove(shortcutNameIn);

		return this;
	}

	public Collection<IShortcut> all()
	{
		return this.shortcuts.values();
	}

	public Collection<String> allNames()
	{
		return this.shortcuts.keySet();
	}

	public ShortcutsManager fileRuntime(final String filePathIn) throws Exception
	{
		final var shortcutFile = new File(filePathIn);
		if (!shortcutFile.exists())
		{
			if (!this.save(shortcutFile))
			{
				throw new Exception("[ERROR] Failed to save shortcuts !");
			}
		}
		else
		{
			if (shortcutFile.isDirectory())
			{
				throw new Exception("[ERROR] Shortcut \"file\" exists and is directory !");
			}

			this.load(shortcutFile);
		}

		return this;
	}

	private final ShortcutsManager load(final File shortcutFileIn) throws Exception
	{
		/**
		 * final var lines = FileUtils.loadLines(shortcutFileIn); for (final var line : lines) { }
		 **/

		return this;
	}

	private final boolean save(final File shortcutFileIn) throws IOException, Exception
	{
		/**
		 * if (!shortcutFileIn.getParentFile().exists()) { shortcutFileIn.getParentFile().mkdirs(); } if (!shortcutFileIn.exists() && !shortcutFileIn.createNewFile()) { throw new Exception( "[ERROR] Failed to create \"" +
		 * shortcutFileIn.getAbsolutePath() + "\" shortcut file !"); }
		 *
		 * final var content = null;
		 *
		 * return FileUtils.write(shortcutFileIn, content.toString(), false);
		 **/

		return true;
	}

	// Delegated methods

	public boolean isJustTriggered(final String shortcutNameIn)
	{
		final var shortcut = this.get(shortcutNameIn);

		if (shortcut == null)
		{
			return false;
		}

		return shortcut.isJustTriggered();
	}

	public boolean isTriggered(final String shortcutNameIn)
	{
		final var shortcut = this.get(shortcutNameIn);

		if (shortcut == null)
		{
			return false;
		}

		return shortcut.isTriggered();
	}

	public boolean isEnabled(final String shortcutNameIn)
	{
		final var shortcut = this.get(shortcutNameIn);

		if (shortcut == null)
		{
			return false;
		}

		return shortcut.isEnabled();
	}

	public boolean isPresent(final String shortcutNameIn)
	{
		return this.shortcuts.containsKey(shortcutNameIn);
	}

	/**
	 * @param contextIn
	 * @return
	 */
	public List<IShortcut> contexteds(final String contextIn)
	{
		return this.contextedShortcuts.get(contextIn);
	}
}