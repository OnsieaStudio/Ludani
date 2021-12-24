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
package fr.onsiea.engine.core;

import fr.onsiea.engine.core.game.GameOptions;
import fr.onsiea.engine.core.game.IGameLogic;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Seynax
 *
 */

@Getter
@Setter
public class OnsieaEngine
{
	private IGameLogic	gameLogic;
	private GameOptions	options;
	private String[]	args;

	private boolean		running;

	public final static OnsieaEngine start(IGameLogic gameLogicIn, GameOptions optionsIn, String[] argsIn)
	{
		final var onsieaEngine = new OnsieaEngine().gameLogic(gameLogicIn).options(optionsIn).args(argsIn);

		onsieaEngine.start();

		return onsieaEngine;
	}

	private OnsieaEngine()
	{
	}

	private void start()
	{
		this.gameLogic().preInitialization();
		this.gameLogic().initialization();

		this.loop();

		this.cleanup();
	}

	private void loop()
	{
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
	}

	private void cleanup()
	{
		this.gameLogic().cleanup();
	}
}