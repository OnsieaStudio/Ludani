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

/**
 * @author Seynax
 *
 */
public interface IButtonTester
{
	default boolean isPresent(final int idIn)
	{
		return this.buttonAction(idIn) != null;
	}

	default boolean isPresent(final Button buttonDataIn)
	{
		if (buttonDataIn == null)
		{
			return false;
		}

		return this.buttonAction(buttonDataIn.id()) != null;
	}

	default boolean isJustPressed(final int idIn)
	{
		final var buttonAction = this.buttonAction(idIn);

		if (buttonAction == null)
		{
			return false;
		}

		return buttonAction.isJustPressed();
	}

	default boolean isJustPressed(final Button buttonDataIn)
	{
		if (buttonDataIn == null)
		{
			return false;
		}

		return this.isJustPressed(buttonDataIn.id());
	}

	default boolean isReleased(final int idIn)
	{
		final var buttonAction = this.buttonAction(idIn);

		if (buttonAction == null)
		{
			return true;
		}

		return buttonAction.isReleased();
	}

	default boolean isReleased(final Button buttonDataIn)
	{
		if (buttonDataIn == null)
		{
			return true;
		}

		return this.isReleased(buttonDataIn.id());
	}

	IButtonAction buttonAction(int idIn);

	default IButtonAction buttonAction(final Button buttonDataIn)
	{
		return this.buttonAction(buttonDataIn.id());
	}

	default EnumActionType buttonActionType(final int idIn)
	{
		final var buttonAction = this.buttonAction(idIn);

		if (buttonAction == null)
		{
			return EnumActionType.NONE;
		}

		return buttonAction.type();
	}

	default EnumActionType buttonActionType(final Button buttonDataIn)
	{
		if (buttonDataIn == null)
		{
			return EnumActionType.NONE;
		}

		return this.buttonActionType(buttonDataIn.id());
	}

	default EnumSubActionType buttonActionSubType(final int idIn)
	{
		final var buttonAction = this.buttonAction(idIn);

		if (buttonAction == null)
		{
			return EnumSubActionType.NONE;
		}

		return buttonAction.subType();
	}

	default EnumSubActionType buttonActionSubType(final Button buttonDataIn)
	{
		if (buttonDataIn == null)
		{
			return EnumSubActionType.NONE;
		}

		return this.buttonActionSubType(buttonDataIn.id());
	}
}