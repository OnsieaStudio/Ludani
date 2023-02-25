/**
 *
 */
package fr.onsiea.engine.client.graphics.opengl.hud;

import fr.onsiea.engine.client.graphics.opengl.hud.components.HudComponent;

/**
 * @author seyro
 *
 */
public abstract class PositionableHud extends Hud
{

	/**
	 * @param nameIn
	 * @param componentsIn
	 */
	public PositionableHud(final String nameIn, final boolean needFocusIn, final HudComponent... componentsIn)
	{
		super(nameIn, needFocusIn, componentsIn);
	}
}