/**
 *
 */
package fr.onsiea.engine.client.graphics.opengl.hud.slot;

import org.joml.Vector2f;

import fr.onsiea.engine.client.graphics.opengl.hud.components.button.HudButton;
import fr.onsiea.engine.client.graphics.opengl.shaders.Shader2DIn3D;
import fr.onsiea.engine.client.graphics.opengl.shaders.Shader3DTo2D;
import fr.onsiea.engine.client.graphics.render.IRenderAPIContext;
import fr.onsiea.engine.client.graphics.texture.ITexture;
import fr.onsiea.engine.client.graphics.window.IWindow;
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
	public ObtainSlot(final Vector2f positionIn, final Vector2f scaleIn, final ITexture textureIn,
			final IRenderAPIContext renderAPIContextIn)
	{
		super(positionIn, scaleIn, textureIn);

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
		this.hudItem.position().set(this.position);

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
	public void draw2D(final Shader2DIn3D shader2dIn3DIn)
	{
		super.draw2D(shader2dIn3DIn);
	}

	@Override
	public void draw3D(final Shader3DTo2D shader3DTo2D, final IWindow windowIn)
	{
		if (this.hudItem != null)
		{
			this.hudItem.draw3D(shader3DTo2D, windowIn);
		}
	}
}