/**
 *
 */
package fr.onsiea.engine.client.graphics.opengl.hud.components.scrollbar;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import fr.onsiea.engine.client.graphics.GraphicsConstants;
import fr.onsiea.engine.client.graphics.opengl.hud.components.HudComponent;
import fr.onsiea.engine.client.graphics.opengl.hud.components.HudRectangle;
import fr.onsiea.engine.client.graphics.opengl.hud.components.button.HudButton;
import fr.onsiea.engine.client.graphics.opengl.hud.inventory.HudInventory;
import fr.onsiea.engine.client.graphics.opengl.shaders.Shader2DIn3D;
import fr.onsiea.engine.client.graphics.opengl.shaders.Shader3DTo2D;
import fr.onsiea.engine.client.graphics.texture.ITexture;
import fr.onsiea.engine.client.graphics.window.IWindow;
import fr.onsiea.engine.client.input.EnumActionType;
import fr.onsiea.engine.client.input.InputManager;
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
	private final HudInventory			inventory;
	private @Getter boolean				cursorIsDisabled;

	/**
	 * @param positionIn
	 * @param scaleIn
	 * @param textureIn
	 */
	public HudScrollbar(final Vector2f positionIn, final Vector2f scaleIn, final ITexture lineTextureIn,
			final Vector2f cursorScaleIn, final ITexture cursorTextureIn, final HudInventory inventoryIn)
	{
		super(positionIn, scaleIn);

		this.line	= new HudRectangle(positionIn, scaleIn, lineTextureIn);
		this.cursor	= new HudScrollbarCursor(new Vector2f(positionIn), cursorScaleIn, cursorTextureIn, this);

		this.cursor.position().set(this.cursor.position().x(), this.line.position().y() + this.line.scale().y());
		this.inventory = inventoryIn;
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

		if (normalizedCursorXIn < this.cursor.position().x - this.cursor.scale().x
				|| normalizedCursorXIn > this.cursor.position().x + this.cursor.scale().x
				|| normalizedCursorYIn < this.cursor.position().y - this.cursor.scale().y
				|| normalizedCursorYIn > this.cursor.position().y + this.cursor.scale().y)
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
	public void hovering(final double normalizedMouseXIn, final double normalizedMouseYIn,
			final InputManager inputManagerIn)
	{
		this.cursor.hovering(normalizedMouseXIn, normalizedMouseYIn, inputManagerIn);
	}

	@Override
	public void stopHovering(final double normalizedMouseXIn, final double normalizedMouseYIn,
			final InputManager inputManagerIn)
	{
		this.cursor.stopHovering(normalizedMouseXIn, normalizedMouseYIn, inputManagerIn);
	}

	@Override
	public void draw2D(final Shader2DIn3D shader2dIn3DIn)
	{
		this.line.draw2D(shader2dIn3DIn);
		this.cursor.draw2D(shader2dIn3DIn);
	}

	@Override
	public void draw3D(final Shader3DTo2D shader3dTo2DIn, final IWindow windowIn)
	{
		this.line.draw3D(shader3dTo2DIn, windowIn);
		this.cursor.draw3D(shader3dTo2DIn, windowIn);
	}

	public void update(final IWindow windowIn, final InputManager inputManagerIn)
	{
		final var pages = (this.inventory.items().size() - 1
				- (GraphicsConstants.CreativeInventory.SLOTS_COLUMNS * GraphicsConstants.CreativeInventory.SLOTS_LINES
						- GraphicsConstants.CreativeInventory.CURRENT_SCROLLING_LENGTH))
				/ GraphicsConstants.CreativeInventory.CURRENT_SCROLLING_LENGTH;
		if (pages <= 0)
		{
			return;
		}

		final var action = inputManagerIn.cursor().buttionActionOf(GLFW.GLFW_MOUSE_BUTTON_1);

		if (action != null)
		{
			if (this.cursor.isFocus() && (EnumActionType.JUST_PRESSED.equals(action.type())
					|| EnumActionType.PRESSED.equals(action.type())
					|| EnumActionType.PRESSED_FOR_WHILE.equals(action.type())
					|| EnumActionType.JUST_REPEATED.equals(action.type())
					|| EnumActionType.REPEATED.equals(action.type())
					|| EnumActionType.REPEATED_FOR_WHILE.equals(action.type())))
			{
				this.cursorIsSelected = true;
			}
			if (EnumActionType.JUST_RELEASED.equals(action.type()) || EnumActionType.RELEASED.equals(action.type())
					|| EnumActionType.RELEASED_FOR_WHILE.equals(action.type()))
			{
				this.cursorIsSelected = false;
			}
		}
		else
		{
			this.cursorIsSelected = false;
		}

		if (this.cursorIsSelected && !this.cursorIsDisabled)
		{
			var y = (float) (1.0f - 2.0f * (inputManagerIn.cursor().y() / windowIn.effectiveHeight()));

			if (y < this.line.position().y() - this.line.scale().y())
			{
				y = this.line.position().y() - this.line.scale().y();
			}
			else if (y > this.line.position().y() + this.line.scale().y())
			{
				y = this.line.position().y() + this.line.scale().y();
			}

			this.percent = 1.0f - (y - (this.line.position().y() - this.line.scale().y())) / (this.line.position().y()
					+ this.line.scale().y() - (this.line.position().y() - this.line.scale().y()));

			if (GraphicsConstants.CreativeInventory.SMOOTH_SCROLLBAR)
			{
				this.cursor.position().set(this.cursor.position().x(), y);
			}
			else
			{
				this.cursor.position().set(this.cursor.position().x(),
						this.line.position().y() + this.line.scale().y()
								+ (int) (this.percent * pages) / pages * (this.line.position().y()
										- this.line.scale().y() - (this.line.position().y() + this.line.scale().y())));
			}
		}
	}

	/**
	 * @param cursorIsDisabledIn the cursorIsDisabled to set
	 */
	public void cursorIsDisabled(final boolean cursorIsDisabledIn)
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
	}

	public final static class HudScrollbarCursor extends HudButton
	{
		private final HudScrollbar scrollbar;

		/**
		 * @param positionIn
		 * @param scaleIn
		 * @param textureIn
		 */
		public HudScrollbarCursor(final Vector2f positionIn, final Vector2f scaleIn, final ITexture textureIn,
				final HudScrollbar scrollbarIn)
		{
			super(positionIn, scaleIn, textureIn);

			this.scrollbar = scrollbarIn;
		}

		public void disable()
		{
			this.currentColor = GraphicsConstants.CreativeInventory.SCROLLBAR_CURSOR_DISABLED_COLOR;
		}

		public void enable()
		{
			this.currentColor = this.colorsByEvents.get("RELEASED");
		}

		@Override
		public void hovering(final double normalizedMouseXIn, final double normalizedMouseYIn,
				final InputManager inputManagerIn)
		{
			if (!this.scrollbar.cursorIsDisabled())
			{
				super.hovering(normalizedMouseXIn, normalizedMouseYIn, inputManagerIn);
			}
		}

		@Override
		public void stopHovering(final double normalizedMouseXIn, final double normalizedMouseYIn,
				final InputManager inputManagerIn)
		{
			if (!this.scrollbar.cursorIsDisabled())
			{
				super.stopHovering(normalizedMouseXIn, normalizedMouseYIn, inputManagerIn);
			}
		}
	}
}