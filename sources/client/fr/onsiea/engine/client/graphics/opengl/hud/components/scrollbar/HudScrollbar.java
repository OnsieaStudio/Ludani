/**
 *
 */
package fr.onsiea.engine.client.graphics.opengl.hud.components.scrollbar;

import fr.onsiea.engine.client.graphics.GraphicsConstants;
import fr.onsiea.engine.client.graphics.opengl.hud.components.HudComponent;
import fr.onsiea.engine.client.graphics.opengl.hud.components.HudRectangle;
import fr.onsiea.engine.client.graphics.opengl.hud.components.IHudComponent;
import fr.onsiea.engine.client.graphics.opengl.hud.components.button.HudButton;
import fr.onsiea.engine.client.graphics.opengl.hud.inventory.HudInventory;
import fr.onsiea.engine.client.graphics.opengl.shaders.Shader2DIn3D;
import fr.onsiea.engine.client.graphics.opengl.shaders.Shader3DTo2D;
import fr.onsiea.engine.client.graphics.texture.ITexture;
import fr.onsiea.engine.client.graphics.window.IWindow;
import fr.onsiea.engine.client.input.InputManager;
import fr.onsiea.engine.utils.positionnable.IPositionnable;
import lombok.Getter;

/**
 * @author seyro
 *
 */
public class HudScrollbar extends HudComponent
{
	private final HudRectangle			line;
	private final HudScrollbarCursor	cursor;
	private boolean						cursorIsSelected;
	private @Getter float				percent;
	private @Getter boolean				cursorIsDisabled;
	private final HudInventory			inventory;

	/**
	 * @param positionIn
	 * @param scaleIn
	 * @param textureIn
	 */
	public HudScrollbar(final IPositionnable positionnableLineIn, final IPositionnable positionnableCursorIn,
			final ITexture lineTextureIn, final ITexture cursorTextureIn, final float scrollbarCursorXSizeIn,
			final float scrollbarCursorYSizeIn, final HudInventory inventoryIn)
	{
		super(positionnableCursorIn);
		this.line		= new HudRectangle(positionnableLineIn, lineTextureIn);
		this.cursor		= new HudScrollbarCursor(positionnableCursorIn, cursorTextureIn, this);
		this.inventory	= inventoryIn;
	}

	@Override
	public boolean verifyHovering(final double normalizedCursorXIn, final double normalizedCursorYIn)
	{
		if (this.cursorIsDisabled)
		{
			return false;
		}

		if (this.cursorIsSelected)
		{
			return true;
		}

		if (normalizedCursorXIn < this.cursor.position().xNorm().centered() - this.cursor.size().xNorm().percent()
				|| normalizedCursorXIn > this.cursor.position().xNorm().centered()
						+ this.cursor.size().xNorm().percent()
				|| normalizedCursorYIn < this.cursor.position().yNorm().invertedCentered()
						- this.cursor.size().yNorm().percent()
				|| normalizedCursorYIn > this.cursor.position().yNorm().invertedCentered()
						+ this.cursor.size().yNorm().percent())
		{
			return false;
		}
		return true;
	}

	@Override
	public boolean isFocus()
	{
		return this.cursor.isFocus();
	}

	@Override
	public HudComponent isFocus(final boolean isFocusIn)
	{
		this.cursor.isFocus(isFocusIn);

		return this;
	}

	@Override
	public IHudComponent hovering(final double normalizedMouseXIn, final double normalizedMouseYIn,
			final InputManager inputManagerIn)
	{
		this.cursor.hovering(normalizedMouseXIn, normalizedMouseYIn, inputManagerIn);

		return this;
	}

	@Override
	public IHudComponent stopHovering(final double normalizedMouseXIn, final double normalizedMouseYIn,
			final InputManager inputManagerIn)
	{
		this.cursor.stopHovering(normalizedMouseXIn, normalizedMouseYIn, inputManagerIn);

		return this;
	}

	@Override
	public IHudComponent draw2D(final Shader2DIn3D shader2dIn3DIn)
	{
		this.line.draw2D(shader2dIn3DIn);
		this.cursor.draw2D(shader2dIn3DIn);

		return this;
	}

	@Override
	public IHudComponent draw3D(final Shader3DTo2D shader3dTo2DIn, final IWindow windowIn)
	{
		this.line.draw3D(shader3dTo2DIn, windowIn);
		this.cursor.draw3D(shader3dTo2DIn, windowIn);

		return this;
	}

	public IHudComponent update(final IWindow windowIn, final InputManager inputManagerIn)
	{
		final var pages = (this.inventory.items().size() - 1
				- (GraphicsConstants.CreativeInventory.SLOTS_COLUMNS * GraphicsConstants.CreativeInventory.SLOTS_LINES
						- GraphicsConstants.CreativeInventory.CURRENT_SCROLLING_LENGTH))
				/ GraphicsConstants.CreativeInventory.CURRENT_SCROLLING_LENGTH;
		if (pages <= 0)
		{
			return this;
		}

		if (this.cursor.isFocus() && inputManagerIn.shortcuts().isJustTriggered("USE_CLICK_IN_HUDS"))
		{
			this.cursorIsSelected = true;
		}
		else
		{
			this.cursorIsSelected = false;
		}

		if (this.cursorIsSelected && !this.cursorIsDisabled)
		{
			var y = (float) inputManagerIn.cursor().y();

			if (y < this.line.position().y() - this.line.size().y() / 2)
			{
				y = this.line.position().y() - this.line.size().y() / 2;
			}
			else if (y > this.line.position().y() + this.line.size().y() / 2)
			{
				y = this.line.position().y() + this.line.size().y() / 2;
			}

			this.percent = (y - (this.line.position().y() - this.line.size().y() / 2)) / (this.line.position().y()
					+ this.line.size().y() / 2 - (this.line.position().y() - this.line.size().y() / 2));

			if (!GraphicsConstants.CreativeInventory.SMOOTH_SCROLLBAR)
			{
				y = this.line.position().y() - this.line.size().y() / 2
						+ (int) (this.percent * pages) / pages * (this.line.position().y() + this.line.size().y() / 2
								- (this.line.position().y() - this.line.size().y() / 2));
			}
			this.cursor.position().set(this.cursor.position().x(), y);
		}

		return this;
	}

	/**
	 * @param cursorIsDisabledIn the cursorIsDisabled to set
	 */
	public IHudComponent cursorDisable(final boolean cursorIsDisabledIn)
	{
		this.cursorIsDisabled = cursorIsDisabledIn;

		if (this.cursorIsDisabled)
		{
			this.cursor.disable();
		}
		else
		{
			this.cursor.enable();
		}

		return this;
	}

	public final static class HudScrollbarCursor extends HudButton
	{
		private final HudScrollbar scrollbar;

		/**
		 * @param positionIn
		 * @param scaleIn
		 * @param textureIn
		 */
		public HudScrollbarCursor(final IPositionnable positionnableIn, final ITexture textureIn,
				final HudScrollbar scrollbarIn)
		{
			super(positionnableIn, textureIn);

			this.scrollbar = scrollbarIn;
		}

		@Override
		public boolean verifyHovering(final double normalizedCursorXIn, final double normalizedCursorYIn)
		{
			return super.verifyHovering(normalizedCursorXIn, normalizedCursorYIn);
		}

		public IHudComponent disable()
		{
			this.currentColor = GraphicsConstants.CreativeInventory.SCROLLBAR_CURSOR_DISABLED_COLOR;

			return this;
		}

		public IHudComponent enable()
		{
			this.currentColor = this.colorsByEvents.get("RELEASED");

			return this;
		}

		@Override
		public IHudComponent hovering(final double normalizedMouseXIn, final double normalizedMouseYIn,
				final InputManager inputManagerIn)
		{
			if (!this.scrollbar.cursorIsDisabled())
			{
				super.hovering(normalizedMouseXIn, normalizedMouseYIn, inputManagerIn);
			}

			return this;
		}

		@Override
		public IHudComponent stopHovering(final double normalizedMouseXIn, final double normalizedMouseYIn,
				final InputManager inputManagerIn)
		{
			if (!this.scrollbar.cursorIsDisabled())
			{
				super.stopHovering(normalizedMouseXIn, normalizedMouseYIn, inputManagerIn);
			}

			return this;
		}
	}
}