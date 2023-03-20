/**
*	Copyright 2021-2023 Onsea Studio All rights reserved.
*
*	This file is part of Onsiea Engine. (https://github.com/Onsea Studio/Onsea StudioEngine)
*
*	Unless noted in license (https://github.com/Onsea Studio/Onsea StudioEngine/blob/main/LICENSE.md) notice file (https://github.com/Onsea Studio/Onsea StudioEngine/blob/main/LICENSE_NOTICE.md), Onsea Studio engine and all parts herein is licensed under the terms of the LGPL-3.0 (https://www.gnu.org/licenses/lgpl-3.0.html)  found here https://www.gnu.org/licenses/lgpl-3.0.html and copied below the license file.
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
*	Neither the name "Onsea Studio", "Onsiea Engine", or any derivative name or the names of its authors / contributors may be used to endorse or promote products derived from this software and even less to name another project or other work without clear and precise permissions written in advance.
*
*	(more details on https://github.com/OnseaStudio/OnsieaEngine/wiki/License)
*
*	@author Seynax
*/
package fr.onsiea.engine.client.graphics.opengl.hud.inventory.components;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import fr.onsiea.engine.client.graphics.GraphicsConstants;
import fr.onsiea.engine.client.graphics.opengl.hud.Hud;
import fr.onsiea.engine.client.graphics.opengl.hud.inventory.HudInventory;
import fr.onsiea.engine.client.graphics.opengl.hud.slot.HudItem;
import fr.onsiea.engine.client.graphics.opengl.hud.slot.ObtainSlot;
import fr.onsiea.engine.client.graphics.opengl.hud.slot.SlotPositionner;
import fr.onsiea.engine.client.graphics.opengl.shaders.Shader2DIn3D;
import fr.onsiea.engine.client.graphics.opengl.shaders.Shader3DTo2D;
import fr.onsiea.engine.client.graphics.render.IRenderAPIContext;
import fr.onsiea.engine.client.graphics.texture.ITexture;
import fr.onsiea.engine.client.graphics.texture.ITextureData;
import fr.onsiea.engine.client.graphics.window.IWindow;
import fr.onsiea.engine.client.input.InputManager;
import fr.onsiea.engine.game.world.item.Item.ItemTypeVariant;
import fr.onsiea.engine.utils.Pair;
import fr.onsiea.engine.utils.positionnable.EnumAnchorDirectionX;
import fr.onsiea.engine.utils.positionnable.EnumAnchorDirectionY;
import fr.onsiea.engine.utils.positionnable.EnumAnchorMode;

/**
 * @Organization Onsea
 * @author Seynax
 *
 */
public class HotBar extends Hud
{
	private final HudInventory		hudInventory;
	private final List<ObtainSlot>	slots;

	/**
	 * @param nameIn
	 * @param needFocusIn
	 * @param componentsIn
	 * @throws Exception
	 */
	public HotBar(final String nameIn, final boolean needFocusIn, final HudInventory hudInventoryIn,
			final IWindow windowIn, final SlotPositionner slotPositionnerIn, final IRenderAPIContext renderAPIContextIn,
			final Map<String, Pair<ITextureData, ITexture>> texturesIn) throws Exception
	{
		super(nameIn, needFocusIn);
		this.hudInventory	= hudInventoryIn;
		this.slots			= new LinkedList<>();

		ObtainSlot	lastXSlot	= null;
		final var	slotsArray	= new ObtainSlot[GraphicsConstants.CreativeInventory.SLOTS_COLUMNS];
		var			i			= 0;
		for (var x = 0; x < GraphicsConstants.CreativeInventory.SLOTS_COLUMNS; x++)
		{
			final var anchor = hudInventoryIn.background.positionnable().anchor()
					.set(0, 5, slotPositionnerIn.scaledWidth(), slotPositionnerIn.scaledHeight())
					.directionsAndModes(EnumAnchorMode.OUT, EnumAnchorDirectionY.DOWN);

			if (x < GraphicsConstants.CreativeInventory.SLOTS_COLUMNS / 2)
			{
				if (lastXSlot != null)
				{
					anchor.relativeX(20);
					anchor.xPositionnable(lastXSlot.positionnable()).directionsAndModes(EnumAnchorMode.OUT,
							EnumAnchorDirectionX.RIGHT);
				}
				else
				{
					anchor.directionsAndModes(EnumAnchorMode.IN, EnumAnchorDirectionX.LEFT);
				}
			}
			else
			{
				if (x == GraphicsConstants.CreativeInventory.SLOTS_COLUMNS / 2)
				{
					lastXSlot	= null;
					i			= GraphicsConstants.CreativeInventory.SLOTS_COLUMNS - 1;
				}
				if (lastXSlot != null)
				{
					anchor.relativeX(-20);
					anchor.xPositionnable(lastXSlot.positionnable()).directionsAndModes(EnumAnchorMode.OUT,
							EnumAnchorDirectionX.LEFT);
				}
				else
				{
					anchor.directionsAndModes(EnumAnchorMode.IN, EnumAnchorDirectionX.RIGHT);
				}
			}

			final var slot = new ObtainSlot(
					anchor.build(() -> (float) windowIn.effectiveWidth(), () -> (float) windowIn.effectiveHeight()),
					texturesIn.get(HudInventory.SLOT_TEXTURE).s2(), renderAPIContextIn);

			slotsArray[i]	= slot;

			lastXSlot		= slot;
			if (x < GraphicsConstants.CreativeInventory.SLOTS_COLUMNS / 2)
			{
				i++;
			}
			else
			{
				i--;
			}
		}
		Collections.addAll(this.slots, slotsArray);
		this.components.addAll(this.slots);
	}

	public boolean putInVoidSlot(final HudItem hudItemIn)
	{
		for (final var slot : this.slots)
		{
			if (slot.hudItem() == null)
			{
				slot.put(hudItemIn);

				return true;
			}
		}

		return false;
	}

	@Override
	public void update(final IWindow windowIn, final InputManager inputManagerIn)
	{
		super.update(windowIn, inputManagerIn);

		for (final var slot : this.slots)
		{
			if (slot != null && slot.isFocus() && slot.hasClicked())
			{
				if (this.hudInventory.releaseItem)
				{
					this.hudInventory.last.z(0.0f);
					this.hudInventory.hudItem = slot.put(this.hudInventory.last);
				}
				else
				{
					this.hudInventory.hudItem = slot.clear();
				}
				if (this.hudInventory.hudItem != null)
				{
					this.hudInventory.hudItem.position().set(
							(float) (2.0f * (inputManagerIn.cursor().x() / windowIn.effectiveWidth()) - 1.0f),
							(float) (1.0f - 2.0f * (inputManagerIn.cursor().y() / windowIn.effectiveHeight())));
					this.hudInventory.hudItem.z(4.0f);
				}
				this.hudInventory.selected = this.hudInventory.hudItem != null;
			}
		}
	}

	public final static class OutHotBar extends Hud
	{
		private int				selected;
		private final HotBar	hotBar;

		/**
		 * @param nameIn
		 * @param needFocusIn
		 * @param componentsIn
		 */
		public OutHotBar(final String nameIn, final boolean needFocusIn, final HotBar hotbarIn)
		{
			super(nameIn, needFocusIn);
			this.hotBar = hotbarIn;
		}

		private void disableSelectedSlotSurrounding()
		{
			final var selectedSlot = this.hotBar.slots.get(this.selected);
			selectedSlot.releaseColor().set(1.0f, 1.0f, 1.0f, 1.0f);
			selectedSlot.currentColor().set(selectedSlot.releaseColor());
		}

		private void enableSelectedSlotSurrounding()
		{
			final var selectedSlot = this.hotBar.slots.get(this.selected);
			selectedSlot.releaseColor().set(1.25f, 1.25f, 1.25f, 1.0f);
			selectedSlot.currentColor().set(selectedSlot.releaseColor());
		}

		//private float lastY;

		public void setSlotPosition(final boolean isOutIn, final IWindow windowIn)
		{
			/**for (final var slot : this.hotBar.slots)
			{
				var y = -1.0f;
				if (isOutIn)
				{
					this.lastY	= slot.position().y();
					y			= windowIn.effectiveHeight() - slot.size().y();
				}
				else
				{
					y = this.lastY;
				}
				System.out.println(y);
			
				slot.position().y(y);
				if (slot.hudItem() != null)
				{
					slot.hudItem().position().set(slot.position());
				}
			}**/
		}

		@Override
		protected void open(final InputManager inputManagerIn, final IWindow windowIn)
		{
			super.open(inputManagerIn, windowIn);
			this.setSlotPosition(true, windowIn);
			this.enableSelectedSlotSurrounding();

			for (final ObtainSlot slot : this.hotBar.slots)
			{
				final var	normalizedCursorX	= 2.0f * (inputManagerIn.cursor().x() / windowIn.effectiveWidth())
						- 1.0f;
				final var	normalizedCursorY	= 1.0f
						- 2.0f * (inputManagerIn.cursor().y() / windowIn.effectiveHeight());

				slot.stopHovering(normalizedCursorX, normalizedCursorY, inputManagerIn);

				slot.isFocus(false);
			}
		}

		@Override
		protected void close(final InputManager inputManagerIn, final IWindow windowIn)
		{
			super.close(inputManagerIn, windowIn);
			this.setSlotPosition(false, windowIn);
			this.disableSelectedSlotSurrounding();
		}

		@Override
		public void update(final IWindow windowIn, final InputManager inputManagerIn)
		{
			super.update(windowIn, inputManagerIn);

			this.disableSelectedSlotSurrounding();

			if (inputManagerIn.cursor().scrollTranslationX() < 0)
			{
				this.selected++;
			}
			if (inputManagerIn.cursor().scrollTranslationX() > 0)
			{
				this.selected--;
			}
			if (this.selected < 0)
			{
				this.selected = this.hotBar.slots.size() - 1;
			}
			if (this.selected > this.hotBar.slots.size() - 1)
			{
				this.selected = 0;
			}

			this.enableSelectedSlotSurrounding();
		}

		public ItemTypeVariant selectedItem()
		{
			final var slot = this.hotBar.slots.get(this.selected);
			if (slot.hudItem() != null)
			{
				return this.hotBar.slots.get(this.selected).hudItem().itemTypeVariant();
			}

			return null;
		}

		@Override
		public void draw2D(final Shader2DIn3D shader2dIn3DIn)
		{
			super.draw2D(shader2dIn3DIn);
			this.hotBar.draw2D(shader2dIn3DIn);
		}

		@Override
		public void draw3D(final Shader3DTo2D shader3DTo2DIn, final IWindow windowIn)
		{
			super.draw3D(shader3DTo2DIn, windowIn);
			this.hotBar.draw3D(shader3DTo2DIn, windowIn);
		}

		@Override
		public void cleanup()
		{
			super.cleanup();
			this.hotBar.cleanup();
		}
	}
}