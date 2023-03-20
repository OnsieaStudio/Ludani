/**
 *
 */
package fr.onsiea.engine.client.graphics.opengl.hud.components;

import fr.onsiea.engine.utils.maths.normalization.normalizable.vector.Normalizable2f;
import fr.onsiea.engine.utils.positionnable.IPositionnable;
import lombok.Getter;
import lombok.Setter;

/**
 * @author seyro
 *
 */
@Getter
public abstract class HudComponent implements IHudComponent
{
	protected @Setter boolean	isFocus;
	@Getter
	public IPositionnable		positionnable;

	public HudComponent(final IPositionnable positionnableIn)
	{
		this.positionnable = positionnableIn;
	}

	/**
	 * Verifty focus with normalized mouse coordinates
	 * @param normalizedCursorXIn
	 * @param normalizedCursorYIn
	 * @return
	 */
	@Override
	public boolean verifyHovering(final double normalizedCursorXIn, final double normalizedCursorYIn)
	{
		if (normalizedCursorXIn < this.position().xNorm().centered() - this.size().xNorm().percent()
				|| normalizedCursorXIn > this.position().xNorm().centered() + this.size().xNorm().percent()
				|| normalizedCursorYIn < this.position().yNorm().invertedCentered() - this.size().yNorm().percent()
				|| normalizedCursorYIn > this.position().yNorm().invertedCentered() + this.size().yNorm().percent())
		{
			return false;
		}

		return true;
	}

	public final Normalizable2f position()
	{
		return this.positionnable.position();
	}

	public final Normalizable2f size()
	{
		return this.positionnable.size();
	}
}