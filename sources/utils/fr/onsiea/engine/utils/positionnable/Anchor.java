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
package fr.onsiea.engine.utils.positionnable;

import fr.onsiea.engine.utils.IListener;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Seynax
 *
 */
@Setter
@Getter
public class Anchor
{
	private EnumAnchorDirectionX	directionX;
	private EnumAnchorMode			modeX;
	private EnumAnchorDirectionY	directionY;
	private EnumAnchorMode			modeY;

	private float					relativeX;
	private float					relativeY;
	private float					w;
	private float					h;

	private IPositionnable			xPositionnable;
	private IPositionnable			yPositionnable;

	public Anchor()
	{
		this.centered();
		this.modeX	= EnumAnchorMode.IN;
		this.modeY	= EnumAnchorMode.IN;
	}

	public Anchor centered()
	{
		this.directionX	= EnumAnchorDirectionX.CENTER;
		this.directionY	= EnumAnchorDirectionY.CENTER;

		return this;
	}

	public final Anchor modes(final EnumAnchorMode modeXIn, final EnumAnchorMode modeYIn)
	{
		this.modeX	= modeXIn;
		this.modeY	= modeYIn;

		return this;
	}

	public final Anchor directionsAndModes(final EnumAnchorMode modeXIn, final EnumAnchorDirectionX directionXIn,
			final EnumAnchorMode modeYIn, final EnumAnchorDirectionY directionYIn)
	{
		this.directionX	= directionXIn;
		this.directionY	= directionYIn;
		this.modeX		= modeXIn;
		this.modeY		= modeYIn;

		return this;
	}

	public final Anchor directionsAndModes(final EnumAnchorMode modeXIn, final EnumAnchorDirectionX directionXIn)
	{
		this.directionX	= directionXIn;
		this.modeX		= modeXIn;

		return this;
	}

	public final Anchor directionsAndModes(final EnumAnchorMode modeYIn, final EnumAnchorDirectionY directionYIn)
	{
		this.directionY	= directionYIn;
		this.modeY		= modeYIn;

		return this;
	}

	public final Anchor directions(final EnumAnchorDirectionX directionXIn, final EnumAnchorDirectionY directionYIn)
	{
		this.directionX	= directionXIn;
		this.directionY	= directionYIn;

		return this;
	}

	public final Anchor set(final float relativeXIn, final float relativeYIn, final float wIn, final float hIn)
	{
		this.relativeX	= relativeXIn;
		this.relativeY	= relativeYIn;
		this.w			= wIn;
		this.h			= hIn;

		return this;
	}

	public final Anchor size(final float wIn, final float hIn)
	{
		this.w	= wIn;
		this.h	= hIn;

		return this;
	}

	public final Anchor relative(final float relativeXIn, final float relativeYIn)
	{
		this.relativeX	= relativeXIn;
		this.relativeY	= relativeYIn;

		return this;
	}

	public final Anchor positionnable(final IPositionnable positionnableIn)
	{
		this.xPositionnable	= positionnableIn;
		this.yPositionnable	= positionnableIn;

		return this;
	}

	public final Anchor positionnable(final IPositionnable xPositionnableIn, final IPositionnable yPositionnableIn)
	{
		this.xPositionnable	= xPositionnableIn;
		this.yPositionnable	= yPositionnableIn;

		return this;
	}

	public final Positionnable build() throws Exception
	{
		return this.build(() -> this.xPositionnable.size().x(), () -> this.yPositionnable.size().y());
	}

	public final Positionnable build(final IListener<Float> maxListenerIn) throws Exception
	{
		return this.build(maxListenerIn, maxListenerIn);
	}

	public final Positionnable build(final IListener<Float> xMaxListenerIn, final IListener<Float> yMaxListenerIn)
			throws Exception
	{
		var x = this.relativeX;
		if (this.xPositionnable != null)
		{
			x += this.xPositionnable.position().x();
			if (EnumAnchorDirectionX.LEFT.equals(this.directionX))
			{
				if (EnumAnchorMode.OUT.equals(this.modeX))
				{
					x -= this.xPositionnable.size().x() / 2.0f + this.w / 2.0f;
				}
				else if (EnumAnchorMode.BORDER.equals(this.modeX))
				{
					x -= this.xPositionnable.size().x() / 2.0f;
				}
				else if (EnumAnchorMode.IN.equals(this.modeX))
				{
					x -= this.xPositionnable.size().x() / 2.0f - this.w / 2.0f;
				}
			}
			else if (EnumAnchorDirectionX.RIGHT.equals(this.directionX))
			{
				if (EnumAnchorMode.OUT.equals(this.modeX))
				{
					x += this.xPositionnable.size().x() / 2.0f + this.w / 2.0f;
				}
				else if (EnumAnchorMode.BORDER.equals(this.modeX))
				{
					x += this.xPositionnable.size().x() / 2.0f;
				}
				else if (EnumAnchorMode.IN.equals(this.modeX))
				{
					x += this.xPositionnable.size().x() / 2.0f - this.w / 2.0f;
				}
			}
		}

		var y = this.relativeY;
		if (this.yPositionnable != null)
		{
			y += this.yPositionnable.position().y();
			if (EnumAnchorDirectionY.UP.equals(this.directionY))
			{
				if (EnumAnchorMode.OUT.equals(this.modeY))
				{
					y -= this.yPositionnable.size().y() / 2.0f + this.h / 2.0f;
				}
				else if (EnumAnchorMode.BORDER.equals(this.modeY))
				{
					y -= this.yPositionnable.size().y() / 2.0f;
				}
				else if (EnumAnchorMode.IN.equals(this.modeY))
				{
					y -= this.yPositionnable.size().y() / 2.0f - this.h / 2.0f;
				}
			}
			else if (EnumAnchorDirectionY.DOWN.equals(this.directionY))
			{
				if (EnumAnchorMode.OUT.equals(this.modeY))
				{
					y += this.yPositionnable.size().y() / 2.0f + this.h / 2.0f;
				}
				else if (EnumAnchorMode.BORDER.equals(this.modeY))
				{
					y += this.yPositionnable.size().y() / 2.0f;
				}
				else if (EnumAnchorMode.IN.equals(this.modeY))
				{
					y += this.yPositionnable.size().y() / 2.0f - this.h / 2.0f;
				}
			}
		}

		return Positionnable.Builder.of().set(x, y, this.w, this.h).maxListener(xMaxListenerIn, yMaxListenerIn).build();
	}
}