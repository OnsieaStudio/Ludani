/**
 *
 */
package fr.onsiea.engine.client.graphics.opengl.hud.slot;

import fr.onsiea.engine.client.graphics.opengl.hud.components.IHudComponent;
import fr.onsiea.engine.client.graphics.opengl.hud.components.button.HudButton;
import fr.onsiea.engine.client.graphics.opengl.shaders.Shader3DTo2D;
import fr.onsiea.engine.client.graphics.render.IRenderAPIContext;
import fr.onsiea.engine.client.graphics.texture.ITexture;
import fr.onsiea.engine.client.graphics.window.IWindow;
import fr.onsiea.engine.utils.positionnable.IPositionnable;
import lombok.Getter;

/**
 * @author seyro
 *
 */
public class ObtainSlot extends HudButton
{
	private @Getter HudItem hudItem;

	/**
	 * @param textureIn
	 */
	public ObtainSlot(final IPositionnable positionnableIn, final ITexture textureIn,
			final IRenderAPIContext renderAPIContextIn)
	{
		super(positionnableIn, textureIn);

		this.hudItem = null;
	}

	/**
	 * Put HudItem (hudItemIn) in slot and return last item of slot (if exist)
	 * @param hudItemIn
	 * @return
	 */
	public HudItem put(final HudItem hudItemIn)
	{
		final var hudItem = this.hudItem;

		this.hudItem = hudItemIn;
		this.hudItem.position().set(this.position());

		return hudItem;
	}

	/**
	 * Clear huditem of slot and return last item of slot (if exist)
	 * @param hudItemIn
	 * @return
	 */
	public HudItem clear()
	{
		final var hudItem = this.hudItem;

		this.hudItem = null;

		return hudItem;
	}

	@Override
	public IHudComponent draw3D(final Shader3DTo2D shader3DTo2D, final IWindow windowIn)
	{
		if (this.hudItem != null)
		{
			this.hudItem.draw3D(shader3DTo2D, windowIn);
		}

		return this;
	}
}