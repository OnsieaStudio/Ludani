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
package fr.onsiea.engine.game;

import fr.onsiea.engine.common.OnsieaGearings;
import fr.onsiea.engine.common.game.GameOptions;
import fr.onsiea.engine.common.game.IGameLogic;

/**
 * @author Seynax
 *
 */
public class GameTest implements IGameLogic
{
	public final static void main(String[] argsIn)
	{
		try
		{
			OnsieaGearings.start(new GameTest(), GameOptions.of(), argsIn);
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public boolean preInitialization()
	{
		return true;
	}

	@Override
	public boolean initialization()
	{
		return true;
	}

	@Override
	public void highRateInput()
	{
	}

	@Override
	public void input()
	{
	}

	@Override
	public void update()
	{
	}

	@Override
	public void draw()
	{
	}

	@Override
	public void cleanup()
	{
	}
}