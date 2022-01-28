/**
* Copyright 2021 Onsiea All rights reserved.<br><br>
*
* This file is part of Onsiea Engine project. (https://github.com/Onsiea/OnsieaEngine)<br><br>
*
* Onsiea Engine is [licensed] (https://github.com/Onsiea/OnsieaEngine/blob/main/LICENSE) under the terms of the "GNU General Public Lesser License v2.1" (LGPL-2.1).
* https://github.com/Onsiea/OnsieaEngine/wiki/License#license-and-copyright<br><br>
*
* Onsiea Engine is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 2.1 of the License, or
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
package fr.onsiea.engine.common;

import fr.onsiea.engine.client.graphics.GraphicsConstants;
import fr.onsiea.engine.client.graphics.glfw.GLFWManager;
import fr.onsiea.engine.client.graphics.glfw.window.WindowSettings;
import fr.onsiea.engine.client.graphics.glfw.window.WindowShowType;
import fr.onsiea.engine.client.graphics.glfw.window.context.GLWindowContext;
import fr.onsiea.engine.client.graphics.lwjgl.LWJGLContext;
import fr.onsiea.engine.client.graphics.render.GLRenderContext;
import fr.onsiea.engine.client.graphics.render.IRenderContext;
import fr.onsiea.engine.client.graphics.window.IWindow;
import fr.onsiea.engine.client.graphics.window.context.IWindowContext;
import fr.onsiea.engine.common.game.GameOptions;
import fr.onsiea.engine.common.game.IGameLogic;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Seynax
 *
 */

@Getter(value = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PRIVATE)
public class OnsieaGearings
{
	private IGameLogic	gameLogic;
	private GameOptions	options;
	private String[]	args;

	private boolean		running;

	private GLFWManager	glfwManager;
	private IWindow		window;

	public final static OnsieaGearings start(IGameLogic gameLogicIn, GameOptions optionsIn, String[] argsIn)
			throws Exception
	{
		final var onsieaEngine = new OnsieaGearings().gameLogic(gameLogicIn).options(optionsIn).args(argsIn);

		onsieaEngine.start();

		return onsieaEngine;
	}

	private OnsieaGearings()
	{
	}

	private void start() throws Exception
	{
		if (GraphicsConstants.debug())
		{
			LWJGLContext.enableDebugging();
		}

		this.gameLogic().preInitialization();
		final IRenderContext	renderContext	= new GLRenderContext();
		final IWindowContext	windowContext	= new GLWindowContext();
		this.glfwManager(new GLFWManager().initialization(
				WindowSettings.Builder.of("Onsiea !", 1920, 1080, 60, WindowShowType.WINDOWED), renderContext,
				windowContext));
		this.window(this.glfwManager().window());
		renderContext.initialization();

		this.gameLogic().initialization();

		this.loop();

		this.cleanup();
	}

	private void loop()
	{
		/**final long		start		= 0L, stop = 0L, last = System.nanoTime(), actual = 0L;
		var				lastShow	= System.nanoTime();
		var				actualShow	= 0L;
		final double	time;
		double			showTime;
		final var		FPS			= 6000;
		var				executions	= 0;**/

		this.running(true);

		while (this.running() /**&& this.window().shouldClose()**/
		)
		{
			this.runtime();
		}
	}

	private void runtime()
	{
		this.gameLogic().highRateInput();
		this.gameLogic().input();
		this.gameLogic().update();
		this.gameLogic().draw();
		this.window().swapBuffers();
	}

	private void cleanup()
	{
		this.gameLogic().cleanup();
		this.glfwManager().cleanup();
	}
}