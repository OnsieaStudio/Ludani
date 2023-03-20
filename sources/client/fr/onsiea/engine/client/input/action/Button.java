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
package fr.onsiea.engine.client.input.action;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Seynax
 *
 */
@Getter
@RequiredArgsConstructor
public class Button
{
	private final String		idName;
	private final int			id;
	private final String		name;
	private final String		translated;
	private final IButtonTester	buttonTester;
	private IButtonAction		buttonAction;

	/**
	 * @return
	 * @see fr.onsiea.engine.client.input.action.IButtonTester#isPresent(fr.onsiea.engine.client.input.action.Button)
	 */
	public boolean isPresent()
	{
		return this.buttonTester.isPresent(this);
	}

	/**
	 * @return
	 * @see fr.onsiea.engine.client.input.action.IButtonTester#isJustPressed(fr.onsiea.engine.client.input.action.Button)
	 */
	public boolean isJustPressed()
	{
		final var buttonAction = this.buttonAction();
		if (buttonAction != null)
		{
			return buttonAction.isJustPressed();
		}
		return false;
	}

	/**
	 * @return
	 * @see fr.onsiea.engine.client.input.action.IButtonTester#isReleased(fr.onsiea.engine.client.input.action.Button)
	 */
	public boolean isReleased()
	{
		final var buttonAction = this.buttonAction();
		if (buttonAction != null)
		{
			return buttonAction.isReleased();
		}
		return true;
	}

	/**
	 * @return
	 * @see fr.onsiea.engine.client.input.action.IButtonTester#buttonAction(fr.onsiea.engine.client.input.action.Button)
	 */
	public IButtonAction buttonAction()
	{
		if (this.buttonAction == null)
		{
			this.buttonAction = this.buttonTester.buttonAction(this);
		}

		return this.buttonAction;
	}

	/**
	 * @return
	 * @see fr.onsiea.engine.client.input.action.IButtonTester#buttonActionType(fr.onsiea.engine.client.input.action.Button)
	 */
	public EnumActionType actionType()
	{
		final var buttonAction = this.buttonAction();
		if (buttonAction != null)
		{
			return buttonAction.type();
		}
		return EnumActionType.NONE;
	}

	/**
	 * @return
	 * @see fr.onsiea.engine.client.input.action.IButtonTester#buttonActionSubType(fr.onsiea.engine.client.input.action.Button)
	 */
	public EnumSubActionType actionSubType()
	{
		final var buttonAction = this.buttonAction();
		if (buttonAction != null)
		{
			return buttonAction.subType();
		}
		return EnumSubActionType.NONE;
	}

}
