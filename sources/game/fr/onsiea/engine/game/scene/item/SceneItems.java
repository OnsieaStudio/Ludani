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
package fr.onsiea.engine.game.scene.item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.joml.Vector3f;

import fr.onsiea.engine.client.graphics.material.Material;
import fr.onsiea.engine.client.graphics.mesh.IMesh;

/**
 * @author Seynax
 *
 */
public class SceneItems
{
	private final Map<GameItemProperties, List<GameItem>>			items;
	private final Map<GameAnimatedItemProperties, List<GameItem>>	animatedItems;
	private GameItemProperties										currentItemProperties;

	public SceneItems()
	{
		this.items			= new LinkedHashMap<>();
		this.animatedItems	= new LinkedHashMap<>();
	}

	/**
	 * Stores item properties for use with add(GameItem... gameItemsIn) and add(Vector3f positionIn, Vector3f orientationIn, Vector3f scaleIn)
	 * @param gameItemPropertiesIn
	 * @return
	 */
	public final SceneItems selectItemProperties(GameItemProperties gameItemPropertiesIn)
	{
		this.currentItemProperties = gameItemPropertiesIn;

		return this;
	}

	/**
	 * Only works if item properties have been selected previously with selectItemProperties(GameItemProperties gameItemPropertiesIn);
	 * @param gameItemsIn
	 * @return
	 */
	public final SceneItems add(GameItem... gameItemsIn)
	{
		if (this.currentItemProperties != null)
		{
			this.add(this.currentItemProperties, gameItemsIn);
		}

		return this;
	}

	/**
	 * @param nameIn
	 * @param materialIn
	 * @param meshIn
	 * @param gameItemsIn
	 * @return
	 */
	public final SceneItems add(String nameIn, Material materialIn, IMesh meshIn, GameItem... gameItemsIn)
	{
		return this.add(new GameItemProperties(nameIn, materialIn, meshIn), gameItemsIn);
	}

	/**
	 * @param gameItemPropertiesIn
	 * @param gameItemsIn
	 * @return
	 */
	public final SceneItems add(GameItemProperties gameItemPropertiesIn, GameItem... gameItemsIn)
	{
		var gameItems = this.items.get(gameItemPropertiesIn);

		if (gameItems == null)
		{
			gameItems = new ArrayList<>();
			this.items.put(gameItemPropertiesIn, gameItems);
		}
		Collections.addAll(gameItems, gameItemsIn);

		return this;
	}

	/**
	 * Only works if item properties have been selected previously with selectItemProperties(GameItemProperties gameItemPropertiesIn);
	 * @param positionIn
	 * @param orientationIn
	 * @param scaleIn
	 * @return
	 */
	public final SceneItems add(Vector3f positionIn, Vector3f orientationIn, Vector3f scaleIn)
	{
		if (this.currentItemProperties != null)
		{
			this.add(this.currentItemProperties, positionIn, orientationIn, scaleIn);
		}

		return this;
	}

	/**
	 * @param nameIn
	 * @param materialIn
	 * @param meshIn
	 * @param gameItemsIn
	 * @return
	 */
	public final SceneItems add(String nameIn, Material materialIn, IMesh meshIn, Vector3f positionIn,
			Vector3f orientationIn, Vector3f scaleIn)
	{
		return this.add(new GameItemProperties(nameIn, materialIn, meshIn), positionIn, orientationIn, scaleIn);
	}

	/**
	 * @param gameItemPropertiesIn
	 * @param positionIn
	 * @param orientationIn
	 * @param scaleIn
	 * @return
	 */
	public SceneItems add(GameItemProperties gameItemPropertiesIn, Vector3f positionIn, Vector3f orientationIn,
			Vector3f scaleIn)
	{
		var gameItems = this.items.get(gameItemPropertiesIn);

		if (gameItems == null)
		{
			gameItems = new ArrayList<>();
			this.items.put(gameItemPropertiesIn, gameItems);
		}

		gameItems.add(new GameItem(positionIn, orientationIn, scaleIn));

		return this;
	}

	/**
	 * @param gameAnimatedItemPropertiesIn
	 * @param gameItemIn
	 * @return
	 */
	public SceneItems add(GameAnimatedItemProperties gameAnimatedItemPropertiesIn, GameItem gameItemIn)
	{
		var animatedGameItems = this.animatedItems.get(gameAnimatedItemPropertiesIn);

		if (animatedGameItems == null)
		{
			animatedGameItems = new ArrayList<>();
			this.animatedItems.put(gameAnimatedItemPropertiesIn, animatedGameItems);
		}

		animatedGameItems.add(gameItemIn);

		return this;
	}

	public SceneItems execute(ISceneItemsFunction functionIn)
	{
		final var iterator = this.items.entrySet().iterator();
		while (iterator.hasNext())
		{
			final var entry = iterator.next();

			functionIn.execute(entry.getKey(), entry.getValue());
		}

		return this;
	}

	public SceneItems executeAnimated(ISceneAnimatedItemsFunction functionIn)
	{
		final var iterator = this.animatedItems.entrySet().iterator();
		while (iterator.hasNext())
		{
			final var entry = iterator.next();

			functionIn.execute(entry.getKey(), entry.getValue());
		}

		return this;
	}

	public interface ISceneItemsFunction
	{
		void execute(GameItemProperties gameItemPropertiesIn, List<GameItem> gameItemsIn);
	}

	public interface ISceneAnimatedItemsFunction
	{
		void execute(GameAnimatedItemProperties gameAnimatedItemPropertiesIn, List<GameItem> gameItemsIn);
	}
}