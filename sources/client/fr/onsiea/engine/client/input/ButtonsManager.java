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
package fr.onsiea.engine.client.input;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.glfw.GLFW;

import fr.onsiea.engine.client.input.action.Button;
import fr.onsiea.engine.client.input.action.IButtonTester;
import fr.onsiea.engine.client.input.cursor.Cursor;
import fr.onsiea.engine.client.input.keyboard.Keyboard;
import fr.onsiea.engine.client.input.shortcut.ShortcutsManager;
import lombok.Getter;

/**
 * @author Seynax
 *
 */
public class ButtonsManager
{
	private final Layout						layout;
	private @Getter final Map<String, Button>	buttonsData;
	private @Getter final Map<String, Button>	mouseButtonsData;

	private final Keyboard						keyboard;
	private final Cursor						cursor;
	private @Getter final ShortcutsManager		shortcuts;

	public ButtonsManager(final Layout layoutIn, final Keyboard keyboardIn, final Cursor cursorIn)
	{
		this.layout				= layoutIn;
		this.keyboard			= keyboardIn;
		this.cursor				= cursorIn;

		this.buttonsData		= new HashMap<>();
		this.mouseButtonsData	= new HashMap<>();

		this.addKeyboardButton("GLFW_KEY_UNKNOWN", GLFW.GLFW_KEY_UNKNOWN);
		this.addKeyboardButton("GLFW_KEY_SPACE", GLFW.GLFW_KEY_SPACE);
		this.addKeyboardButton("GLFW_KEY_APOSTROPHE", GLFW.GLFW_KEY_APOSTROPHE);
		this.addKeyboardButton("GLFW_KEY_COMMA", GLFW.GLFW_KEY_COMMA);
		this.addKeyboardButton("GLFW_KEY_MINUS", GLFW.GLFW_KEY_MINUS);
		this.addKeyboardButton("GLFW_KEY_PERIOD", GLFW.GLFW_KEY_PERIOD);
		this.addKeyboardButton("GLFW_KEY_SLASH", GLFW.GLFW_KEY_SLASH);
		this.addKeyboardButton("GLFW_KEY_0", GLFW.GLFW_KEY_0);
		this.addKeyboardButton("GLFW_KEY_1", GLFW.GLFW_KEY_1);
		this.addKeyboardButton("GLFW_KEY_2", GLFW.GLFW_KEY_2);
		this.addKeyboardButton("GLFW_KEY_3", GLFW.GLFW_KEY_3);
		this.addKeyboardButton("GLFW_KEY_4", GLFW.GLFW_KEY_4);
		this.addKeyboardButton("GLFW_KEY_5", GLFW.GLFW_KEY_5);
		this.addKeyboardButton("GLFW_KEY_6", GLFW.GLFW_KEY_6);
		this.addKeyboardButton("GLFW_KEY_7", GLFW.GLFW_KEY_7);
		this.addKeyboardButton("GLFW_KEY_8", GLFW.GLFW_KEY_8);
		this.addKeyboardButton("GLFW_KEY_9", GLFW.GLFW_KEY_9);
		this.addKeyboardButton("GLFW_KEY_SEMICOLON", GLFW.GLFW_KEY_SEMICOLON);
		this.addKeyboardButton("GLFW_KEY_EQUAL", GLFW.GLFW_KEY_EQUAL);
		this.addKeyboardButton("GLFW_KEY_A", GLFW.GLFW_KEY_A);
		this.addKeyboardButton("GLFW_KEY_B", GLFW.GLFW_KEY_B);
		this.addKeyboardButton("GLFW_KEY_C", GLFW.GLFW_KEY_C);
		this.addKeyboardButton("GLFW_KEY_D", GLFW.GLFW_KEY_D);
		this.addKeyboardButton("GLFW_KEY_E", GLFW.GLFW_KEY_E);
		this.addKeyboardButton("GLFW_KEY_F", GLFW.GLFW_KEY_F);
		this.addKeyboardButton("GLFW_KEY_G", GLFW.GLFW_KEY_G);
		this.addKeyboardButton("GLFW_KEY_H", GLFW.GLFW_KEY_H);
		this.addKeyboardButton("GLFW_KEY_I", GLFW.GLFW_KEY_I);
		this.addKeyboardButton("GLFW_KEY_J", GLFW.GLFW_KEY_J);
		this.addKeyboardButton("GLFW_KEY_K", GLFW.GLFW_KEY_K);
		this.addKeyboardButton("GLFW_KEY_L", GLFW.GLFW_KEY_L);
		this.addKeyboardButton("GLFW_KEY_M", GLFW.GLFW_KEY_M);
		this.addKeyboardButton("GLFW_KEY_N", GLFW.GLFW_KEY_N);
		this.addKeyboardButton("GLFW_KEY_O", GLFW.GLFW_KEY_O);
		this.addKeyboardButton("GLFW_KEY_P", GLFW.GLFW_KEY_P);
		if (Layout.AZERTY.equals(layoutIn))
		{
			this.addKeyboardButton("GLFW_KEY_Q", GLFW.GLFW_KEY_A);
		}
		else
		{
			this.addKeyboardButton("GLFW_KEY_Q", GLFW.GLFW_KEY_Q);
		}
		this.addKeyboardButton("GLFW_KEY_R", GLFW.GLFW_KEY_R);
		this.addKeyboardButton("GLFW_KEY_S", GLFW.GLFW_KEY_S);
		this.addKeyboardButton("GLFW_KEY_T", GLFW.GLFW_KEY_T);
		this.addKeyboardButton("GLFW_KEY_U", GLFW.GLFW_KEY_U);
		this.addKeyboardButton("GLFW_KEY_V", GLFW.GLFW_KEY_V);
		this.addKeyboardButton("GLFW_KEY_W", GLFW.GLFW_KEY_W);
		this.addKeyboardButton("GLFW_KEY_X", GLFW.GLFW_KEY_X);
		this.addKeyboardButton("GLFW_KEY_Y", GLFW.GLFW_KEY_Y);
		if (Layout.AZERTY.equals(layoutIn))
		{
			this.addKeyboardButton("GLFW_KEY_Z", GLFW.GLFW_KEY_W);
		}
		else
		{
			this.addKeyboardButton("GLFW_KEY_Z", GLFW.GLFW_KEY_Z);
		}
		this.addKeyboardButton("GLFW_KEY_LEFT_BRACKET", GLFW.GLFW_KEY_LEFT_BRACKET);
		this.addKeyboardButton("GLFW_KEY_BACKSLASH", GLFW.GLFW_KEY_BACKSLASH);
		this.addKeyboardButton("GLFW_KEY_RIGHT_BRACKET", GLFW.GLFW_KEY_RIGHT_BRACKET);
		this.addKeyboardButton("GLFW_KEY_GRAVE_ACCENT", GLFW.GLFW_KEY_GRAVE_ACCENT);
		this.addKeyboardButton("GLFW_KEY_WORLD_1", GLFW.GLFW_KEY_WORLD_1);
		this.addKeyboardButton("GLFW_KEY_WORLD_2", GLFW.GLFW_KEY_WORLD_2);
		this.addKeyboardButton("GLFW_KEY_ESCAPE", GLFW.GLFW_KEY_ESCAPE);
		this.addKeyboardButton("GLFW_KEY_ENTER", GLFW.GLFW_KEY_ENTER);
		this.addKeyboardButton("GLFW_KEY_TAB", GLFW.GLFW_KEY_TAB);
		this.addKeyboardButton("GLFW_KEY_BACKSPACE", GLFW.GLFW_KEY_BACKSPACE);
		this.addKeyboardButton("GLFW_KEY_INSERT", GLFW.GLFW_KEY_INSERT);
		this.addKeyboardButton("GLFW_KEY_DELETE", GLFW.GLFW_KEY_DELETE);
		this.addKeyboardButton("GLFW_KEY_RIGHT", GLFW.GLFW_KEY_RIGHT);
		this.addKeyboardButton("GLFW_KEY_LEFT", GLFW.GLFW_KEY_LEFT);
		this.addKeyboardButton("GLFW_KEY_DOWN", GLFW.GLFW_KEY_DOWN);
		this.addKeyboardButton("GLFW_KEY_UP", GLFW.GLFW_KEY_UP);
		this.addKeyboardButton("GLFW_KEY_PAGE_UP", GLFW.GLFW_KEY_PAGE_UP);
		this.addKeyboardButton("GLFW_KEY_PAGE_DOWN", GLFW.GLFW_KEY_PAGE_DOWN);
		this.addKeyboardButton("GLFW_KEY_HOME", GLFW.GLFW_KEY_HOME);
		this.addKeyboardButton("GLFW_KEY_END", GLFW.GLFW_KEY_END);
		this.addKeyboardButton("GLFW_KEY_CAPS_LOCK", GLFW.GLFW_KEY_CAPS_LOCK);
		this.addKeyboardButton("GLFW_KEY_SCROLL_LOCK", GLFW.GLFW_KEY_SCROLL_LOCK);
		this.addKeyboardButton("GLFW_KEY_NUM_LOCK", GLFW.GLFW_KEY_NUM_LOCK);
		this.addKeyboardButton("GLFW_KEY_PRINT_SCREEN", GLFW.GLFW_KEY_PRINT_SCREEN);
		this.addKeyboardButton("GLFW_KEY_PAUSE", GLFW.GLFW_KEY_PAUSE);
		this.addKeyboardButton("GLFW_KEY_F1", GLFW.GLFW_KEY_F1);
		this.addKeyboardButton("GLFW_KEY_F2", GLFW.GLFW_KEY_F2);
		this.addKeyboardButton("GLFW_KEY_F3", GLFW.GLFW_KEY_F3);
		this.addKeyboardButton("GLFW_KEY_F4", GLFW.GLFW_KEY_F4);
		this.addKeyboardButton("GLFW_KEY_F5", GLFW.GLFW_KEY_F5);
		this.addKeyboardButton("GLFW_KEY_F6", GLFW.GLFW_KEY_F6);
		this.addKeyboardButton("GLFW_KEY_F7", GLFW.GLFW_KEY_F7);
		this.addKeyboardButton("GLFW_KEY_F8", GLFW.GLFW_KEY_F8);
		this.addKeyboardButton("GLFW_KEY_F9", GLFW.GLFW_KEY_F9);
		this.addKeyboardButton("GLFW_KEY_F10", GLFW.GLFW_KEY_F10);
		this.addKeyboardButton("GLFW_KEY_F11", GLFW.GLFW_KEY_F11);
		this.addKeyboardButton("GLFW_KEY_F12", GLFW.GLFW_KEY_F12);
		this.addKeyboardButton("GLFW_KEY_F13", GLFW.GLFW_KEY_F13);
		this.addKeyboardButton("GLFW_KEY_F14", GLFW.GLFW_KEY_F14);
		this.addKeyboardButton("GLFW_KEY_F15", GLFW.GLFW_KEY_F15);
		this.addKeyboardButton("GLFW_KEY_F16", GLFW.GLFW_KEY_F16);
		this.addKeyboardButton("GLFW_KEY_F17", GLFW.GLFW_KEY_F17);
		this.addKeyboardButton("GLFW_KEY_F18", GLFW.GLFW_KEY_F18);
		this.addKeyboardButton("GLFW_KEY_F19", GLFW.GLFW_KEY_F19);
		this.addKeyboardButton("GLFW_KEY_F20", GLFW.GLFW_KEY_F20);
		this.addKeyboardButton("GLFW_KEY_F21", GLFW.GLFW_KEY_F21);
		this.addKeyboardButton("GLFW_KEY_F22", GLFW.GLFW_KEY_F22);
		this.addKeyboardButton("GLFW_KEY_F23", GLFW.GLFW_KEY_F23);
		this.addKeyboardButton("GLFW_KEY_F24", GLFW.GLFW_KEY_F24);
		this.addKeyboardButton("GLFW_KEY_F25", GLFW.GLFW_KEY_F25);
		this.addKeyboardButton("GLFW_KEY_KP_0", GLFW.GLFW_KEY_KP_0);
		this.addKeyboardButton("GLFW_KEY_KP_1", GLFW.GLFW_KEY_KP_1);
		this.addKeyboardButton("GLFW_KEY_KP_2", GLFW.GLFW_KEY_KP_2);
		this.addKeyboardButton("GLFW_KEY_KP_3", GLFW.GLFW_KEY_KP_3);
		this.addKeyboardButton("GLFW_KEY_KP_4", GLFW.GLFW_KEY_KP_4);
		this.addKeyboardButton("GLFW_KEY_KP_5", GLFW.GLFW_KEY_KP_5);
		this.addKeyboardButton("GLFW_KEY_KP_6", GLFW.GLFW_KEY_KP_6);
		this.addKeyboardButton("GLFW_KEY_KP_7", GLFW.GLFW_KEY_KP_7);
		this.addKeyboardButton("GLFW_KEY_KP_8", GLFW.GLFW_KEY_KP_8);
		this.addKeyboardButton("GLFW_KEY_KP_9", GLFW.GLFW_KEY_KP_9);
		this.addKeyboardButton("GLFW_KEY_KP_DECIMAL", GLFW.GLFW_KEY_KP_DECIMAL);
		this.addKeyboardButton("GLFW_KEY_KP_DIVIDE", GLFW.GLFW_KEY_KP_DIVIDE);
		this.addKeyboardButton("GLFW_KEY_KP_MULTIPLY", GLFW.GLFW_KEY_KP_MULTIPLY);
		this.addKeyboardButton("GLFW_KEY_KP_SUBTRACT", GLFW.GLFW_KEY_KP_SUBTRACT);
		this.addKeyboardButton("GLFW_KEY_KP_ADD", GLFW.GLFW_KEY_KP_ADD);
		this.addKeyboardButton("GLFW_KEY_KP_ENTER", GLFW.GLFW_KEY_KP_ENTER);
		this.addKeyboardButton("GLFW_KEY_KP_EQUAL", GLFW.GLFW_KEY_KP_EQUAL);
		this.addKeyboardButton("GLFW_KEY_LEFT_SHIFT", GLFW.GLFW_KEY_LEFT_SHIFT);
		this.addKeyboardButton("GLFW_KEY_LEFT_CONTROL", GLFW.GLFW_KEY_LEFT_CONTROL);
		this.addKeyboardButton("GLFW_KEY_LEFT_ALT", GLFW.GLFW_KEY_LEFT_ALT);
		this.addKeyboardButton("GLFW_KEY_LEFT_SUPER", GLFW.GLFW_KEY_LEFT_SUPER);
		this.addKeyboardButton("GLFW_KEY_RIGHT_SHIFT", GLFW.GLFW_KEY_RIGHT_SHIFT);
		this.addKeyboardButton("GLFW_KEY_RIGHT_CONTROL", GLFW.GLFW_KEY_RIGHT_CONTROL);
		this.addKeyboardButton("GLFW_KEY_RIGHT_ALT", GLFW.GLFW_KEY_RIGHT_ALT);
		this.addKeyboardButton("GLFW_KEY_RIGHT_SUPER", GLFW.GLFW_KEY_RIGHT_SUPER);
		this.addKeyboardButton("GLFW_KEY_MENU", GLFW.GLFW_KEY_MENU);
		this.addKeyboardButton("GLFW_KEY_LAST", GLFW.GLFW_KEY_LAST);

		this.addMouseButton("GLFW_MOUSE_BUTTON_1", GLFW.GLFW_MOUSE_BUTTON_1);
		this.addMouseButton("GLFW_MOUSE_BUTTON_2", GLFW.GLFW_MOUSE_BUTTON_2);
		this.addMouseButton("GLFW_MOUSE_BUTTON_3", GLFW.GLFW_MOUSE_BUTTON_3);
		this.addMouseButton("GLFW_MOUSE_BUTTON_4", GLFW.GLFW_MOUSE_BUTTON_4);
		this.addMouseButton("GLFW_MOUSE_BUTTON_5", GLFW.GLFW_MOUSE_BUTTON_5);
		this.addMouseButton("GLFW_MOUSE_BUTTON_6", GLFW.GLFW_MOUSE_BUTTON_6);
		this.addMouseButton("GLFW_MOUSE_BUTTON_7", GLFW.GLFW_MOUSE_BUTTON_7);
		this.addMouseButton("GLFW_MOUSE_BUTTON_8", GLFW.GLFW_MOUSE_BUTTON_8);
		this.addMouseButton("GLFW_MOUSE_BUTTON_LAST", GLFW.GLFW_MOUSE_BUTTON_LAST);
		this.addMouseButton("GLFW_MOUSE_BUTTON_LEFT", GLFW.GLFW_MOUSE_BUTTON_LEFT);
		this.addMouseButton("GLFW_MOUSE_BUTTON_MIDDLE", GLFW.GLFW_MOUSE_BUTTON_MIDDLE);
		this.addMouseButton("GLFW_MOUSE_BUTTON_RIGHT", GLFW.GLFW_MOUSE_BUTTON_RIGHT);

		this.shortcuts = new ShortcutsManager(this, keyboardIn, cursorIn);
	}

	private final static void addButton(final String idNameIn, final int numericIdIn, final Map<String, Button> keysIn,
			final IButtonTester buttonTesterIn)
	{
		final var	name		= idNameIn.replace("GLFW_", "").replace("KEY_", "").replace("BUTTON_", "");

		var			translated	= name.replace("_", " ").toLowerCase();
		translated = translated.substring(0, 1).toUpperCase() + translated.substring(1);

		keysIn.put(name, new Button(idNameIn, numericIdIn, name, translated, buttonTesterIn));
	}

	private final ButtonsManager addMouseButton(final String idNameIn, final int numericIdIn)
	{
		ButtonsManager.addButton(idNameIn, numericIdIn, this.mouseButtonsData, this.cursor);

		return this;
	}

	private final ButtonsManager addKeyboardButton(final String idNameIn, final int numericIdIn)
	{
		ButtonsManager.addButton(idNameIn, numericIdIn, this.buttonsData, this.keyboard);

		return this;
	}

	public final Button button(final String nameIn)
	{
		if (nameIn.startsWith("MOUSE_"))
		{
			return this.mouseButtonsData.get(nameIn);
		}

		return this.buttonsData.get(nameIn);
	}
}