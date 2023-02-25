/**
 *
 */
package fr.onsiea.engine.client.graphics.opengl.hud.slot;

import org.joml.Vector2f;

import fr.onsiea.engine.client.graphics.opengl.hud.components.button.HudButton;
import fr.onsiea.engine.client.graphics.opengl.shaders.Shader2DIn3D;
import fr.onsiea.engine.client.graphics.opengl.shaders.Shader3DTo2D;
import fr.onsiea.engine.client.graphics.texture.ITexture;
import fr.onsiea.engine.client.graphics.window.IWindow;
import fr.onsiea.engine.game.world.item.Item.ItemTypeVariant;
import lombok.Getter;

/**
 * @author seyro
 *
 */
public class Slot extends HudButton
{
	private @Getter HudItem hudItem;

	/**
	 * @param textureIn
	 * @param scaleIn
	 * @param positionIn
	 */
	public Slot(final Vector2f positionIn, final Vector2f scaleIn, final ITexture textureIn,
			final ItemTypeVariant itemTypeVariantIn)
	{
		super(positionIn, scaleIn, textureIn);

		try
		{
			if (itemTypeVariantIn != null)
			{
				this.hudItem = new HudItem(new Vector2f(positionIn), new Vector2f(scaleIn), itemTypeVariantIn, 0.0f);
				this.hudItem.position().set(this.position());
				this.hudItem.scale().set(this.scale());
			}
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void draw2D(final Shader2DIn3D shader2dIn3DIn)
	{
		super.draw2D(shader2dIn3DIn);
	}

	@Override
	public void draw3D(final Shader3DTo2D shader3DTo2DIn, final IWindow windowIn)
	{
		if (this.hudItem != null)
		{
			this.hudItem.draw3D(shader3DTo2DIn, windowIn);
		}
	}

	/**
	 * @param itemTypeVariantIn
	 */
	public void set(final ItemTypeVariant itemTypeVariantIn)
	{
		if (itemTypeVariantIn != null)
		{
			this.hudItem = new HudItem(new Vector2f(this.position), new Vector2f(this.scale), itemTypeVariantIn, 0.0f);
		}
		else
		{
			this.hudItem = null;
		}
	}
}