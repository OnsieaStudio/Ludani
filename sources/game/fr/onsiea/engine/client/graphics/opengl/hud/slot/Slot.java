/**
 *
 */
package fr.onsiea.engine.client.graphics.opengl.hud.slot;

import fr.onsiea.engine.client.graphics.opengl.hud.components.IHudComponent;
import fr.onsiea.engine.client.graphics.opengl.hud.components.button.HudButton;
import fr.onsiea.engine.client.graphics.opengl.shaders.Shader3DTo2D;
import fr.onsiea.engine.client.graphics.texture.ITexture;
import fr.onsiea.engine.client.graphics.window.IWindow;
import fr.onsiea.engine.game.world.item.Item.ItemTypeVariant;
import fr.onsiea.engine.utils.positionnable.IPositionnable;
import fr.onsiea.engine.utils.positionnable.Positionnable;
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
	public Slot(final IPositionnable positionnableIn, final ITexture textureIn, final ItemTypeVariant itemTypeVariantIn)
	{
		super(positionnableIn, textureIn);

		try
		{
			if (itemTypeVariantIn != null)
			{
				this.hudItem = new HudItem(positionnableIn, itemTypeVariantIn, 0.0f);
			}
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public IHudComponent draw3D(final Shader3DTo2D shader3DTo2DIn, final IWindow windowIn)
	{
		if (this.hudItem != null)
		{
			this.hudItem.draw3D(shader3DTo2DIn, windowIn);
		}

		return this;
	}

	/**
	 * @param itemTypeVariantIn
	 */
	public void set(final ItemTypeVariant itemTypeVariantIn)
	{
		if (itemTypeVariantIn != null)
		{
			this.hudItem = new HudItem(new Positionnable(this.positionnable()), itemTypeVariantIn, 0.0f);
		}
		else
		{
			this.hudItem = null;
		}
	}
}