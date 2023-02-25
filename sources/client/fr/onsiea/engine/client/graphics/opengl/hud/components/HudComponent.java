/**
 *
 */
package fr.onsiea.engine.client.graphics.opengl.hud.components;

import org.joml.Vector2f;

import fr.onsiea.engine.client.graphics.opengl.shaders.Shader2DIn3D;
import fr.onsiea.engine.client.graphics.opengl.shaders.Shader3DTo2D;
import fr.onsiea.engine.client.graphics.window.IWindow;
import fr.onsiea.engine.client.input.InputManager;
import lombok.Getter;
import lombok.Setter;

/**
 * @author seyro
 *
 */
@Getter
public abstract class HudComponent
{
	protected final Vector2f	position;
	protected final Vector2f	scale;

	protected @Setter boolean	isFocus;

	public HudComponent(final Vector2f positionIn, final Vector2f scaleIn)
	{
		this.position	= positionIn;
		this.scale		= scaleIn;
	}

	/**
	 * Verifty focus with normalized mouse coordinates
	 * @param normalizedCursorXIn
	 * @param normalizedCursorYIn
	 * @return
	 */
	public boolean verifyHovering(final double normalizedCursorXIn, final double normalizedCursorYIn)
	{
		if (normalizedCursorXIn < this.position.x - this.scale.x || normalizedCursorXIn > this.position.x + this.scale.x
				|| normalizedCursorYIn < this.position.y - this.scale.y
				|| normalizedCursorYIn > this.position.y + this.scale.y)
		{
			return false;
		}

		return true;
	}

	public abstract void hovering(final double normalizedMouseXIn, final double normalizedMouseYIn,
			final InputManager inputManagerIn);

	public abstract void stopHovering(final double normalizedMouseXIn, final double normalizedMouseYIn,
			final InputManager inputManagerIn);

	/**
	 * @param shader2dIn3DIn
	 */
	public abstract void draw2D(Shader2DIn3D shader2dIn3DIn);

	/**
	 * @param shader3dTo2DIn
	 * @param windowIn
	 */
	public abstract void draw3D(Shader3DTo2D shader3dTo2DIn, IWindow windowIn);
	
	public void cleanup()
	{
		
	}
}