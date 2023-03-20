/**
 *
 */
package fr.onsiea.engine.client.graphics.opengl.hud;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.onsiea.engine.client.graphics.opengl.hud.components.HudComponent;
import fr.onsiea.engine.client.graphics.opengl.shaders.Shader2DIn3D;
import fr.onsiea.engine.client.graphics.opengl.shaders.Shader3DTo2D;
import fr.onsiea.engine.client.graphics.window.IWindow;
import fr.onsiea.engine.client.input.InputManager;
import fr.onsiea.engine.utils.maths.MathInstances;
import lombok.Getter;

/**
 * @author seyro
 *
 */
public abstract class Hud
{
	private @Getter final String		name;

	protected final List<HudComponent>	components;

	private boolean						isOpen;
	private @Getter final boolean		needFocus;

	public Hud(final String nameIn, final boolean needFocus, final HudComponent... componentsIn)
	{
		this.name		= nameIn;
		this.needFocus	= needFocus;
		this.components	= new ArrayList<>();

		Collections.addAll(this.components, componentsIn);
	}

	public void update(final IWindow windowIn, final InputManager inputManagerIn)
	{
		for (final var component : this.components)
		{
			if (component != null)
			{
				final var	normalizedCursorX	= 2.0f * (inputManagerIn.cursor().x() / windowIn.effectiveWidth())
						- 1.0f;
				final var	normalizedCursorY	= 1.0f
						- 2.0f * (inputManagerIn.cursor().y() / windowIn.effectiveHeight());

				if (inputManagerIn.cursor().hasMoved())
				{
					if (component.verifyHovering(normalizedCursorX, normalizedCursorY))
					{
						component.hovering(normalizedCursorX, normalizedCursorY, inputManagerIn);

						component.isFocus(true);
					}
					else if (component.isFocus())
					{
						component.stopHovering(normalizedCursorX, normalizedCursorY, inputManagerIn);

						component.isFocus(false);
					}
				}
				else if (component.isFocus())
				{
					component.hovering(normalizedCursorX, normalizedCursorY, inputManagerIn);
				}
			}
		}
	}

	/**
	 * @param shader2dIn3DIn
	 */
	public void draw2D(final Shader2DIn3D shader2dIn3DIn)
	{
		for (final HudComponent component : this.components)
		{
			if (component != null)
			{
				shader2dIn3DIn.color().load(MathInstances.one4f());

				component.draw2D(shader2dIn3DIn);
			}
		}
	}

	/**
	 * @param shader3DTo2DIn
	 */
	public void draw3D(final Shader3DTo2D shader3DTo2DIn, final IWindow windowIn)
	{
		for (final HudComponent component : this.components)
		{
			if (component != null)
			{
				component.draw3D(shader3DTo2DIn, windowIn);
			}
		}
	}

	final void reverseOpening(final InputManager inputManagerIn, final IWindow windowIn)
	{
		if (this.isOpen)
		{
			this.close(inputManagerIn, windowIn);
		}
		else
		{
			this.open(inputManagerIn, windowIn);
		}
	}

	protected void open(final InputManager inputManagerIn, final IWindow windowIn)
	{
		this.isOpen = true;

		for (final var component : this.components)
		{
			if (component != null)
			{
				final var	normalizedCursorX	= 2.0f * (inputManagerIn.cursor().x() / windowIn.effectiveWidth())
						- 1.0f;
				final var	normalizedCursorY	= 1.0f
						- 2.0f * (inputManagerIn.cursor().y() / windowIn.effectiveHeight());
				if (component.verifyHovering(normalizedCursorX, normalizedCursorY))
				{
					component.hovering(normalizedCursorX, normalizedCursorY, inputManagerIn);

					component.isFocus(true);
				}
				else if (component.isFocus())
				{
					component.stopHovering(normalizedCursorX, normalizedCursorY, inputManagerIn);

					component.isFocus(false);
				}
			}
		}
	}

	protected void close(final InputManager inputManagerIn, final IWindow windowIn)
	{
		this.isOpen = false;
	}

	public final boolean isOpen()
	{
		return this.isOpen;
	}

	/**
	 * @param isOpenIn
	 */
	final void open(final boolean isOpenIn, final InputManager inputManagerIn, final IWindow windowIn)
	{
		if (isOpenIn)
		{
			this.open(inputManagerIn, windowIn);
		}
		else
		{
			this.close(inputManagerIn, windowIn);
		}
	}

	public void cleanup()
	{
		for (final var component : this.components)
		{
			if (component != null)
			{
				component.cleanup();
			}
		}
	}
}