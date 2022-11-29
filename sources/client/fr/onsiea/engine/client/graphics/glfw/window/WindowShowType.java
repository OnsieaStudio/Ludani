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
package fr.onsiea.engine.client.graphics.glfw.window;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Seynax
 *
 */

@Getter(AccessLevel.PUBLIC)
public enum WindowShowType
{
	WINDOWED(State.SETTINGS, Source.SETTINGS, Source.SETTINGS, Source.SETTINGS, false),
	FULLSCREEN(State.FALSE, Source.MONITOR, Source.SETTINGS, Source.SETTINGS, true),
	WINDOWED_FULLSCREEN(State.FALSE, Source.MONITOR, Source.MONITOR, Source.MONITOR, true),
	WINDOWED_WIDE(State.FALSE, Source.MONITOR, Source.MONITOR, Source.MONITOR, false);

	@Setter(AccessLevel.PRIVATE)
	private State	decoratedState;
	@Setter(AccessLevel.PRIVATE)
	private Source	framebufferSettingsSource;
	@Setter(AccessLevel.PRIVATE)
	private Source	widthSource;
	@Setter(AccessLevel.PRIVATE)
	private Source	heightSource;
	@Setter(AccessLevel.PRIVATE)
	private boolean	useMonitor;

	WindowShowType(State decoratedStateIn, Source framebufferSettingsSourcesIn, Source widthSourceIn,
			Source heightSourceIn, boolean useMonitorIn)
	{
		this.decoratedState(decoratedStateIn);
		this.framebufferSettingsSource(framebufferSettingsSourcesIn);
		this.widthSource(widthSourceIn);
		this.heightSource(heightSourceIn);
		this.useMonitor(useMonitorIn);
	}

	public static enum State
	{
		SETTINGS, FALSE, TRUE;
	}

	public static enum Source
	{
		DEFAULT, SETTINGS, MONITOR;
	}
}