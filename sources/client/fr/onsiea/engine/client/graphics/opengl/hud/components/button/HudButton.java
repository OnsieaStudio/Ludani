/**
 *
 */
package fr.onsiea.engine.client.graphics.opengl.hud.components.button;

import java.util.HashMap;
import java.util.Map;

import org.joml.Vector4f;

import fr.onsiea.engine.client.graphics.opengl.hud.components.HudRectangle;
import fr.onsiea.engine.client.graphics.opengl.hud.components.IHudComponent;
import fr.onsiea.engine.client.graphics.opengl.shaders.Shader2DIn3D;
import fr.onsiea.engine.client.graphics.texture.ITexture;
import fr.onsiea.engine.client.input.InputManager;
import fr.onsiea.engine.client.input.shortcut.Shortcut;
import fr.onsiea.engine.utils.positionnable.IPositionnable;
import lombok.Getter;

/**
 * @author seyro
 *
 */
public class HudButton extends HudRectangle
{
	protected final Map<String, Vector4f>	colorsByEvents;
	protected @Getter Vector4f				currentColor;
	private @Getter boolean					hasClicked;
	private @Getter final Vector4f			releaseColor;

	public HudButton(final IPositionnable positionnableIn, final ITexture textureIn)
	{
		super(positionnableIn, textureIn);

		this.colorsByEvents = new HashMap<>();
		this.colorsByEvents.put("hovering", new Vector4f(1.25f, 1.25f, 1.25f, 1.0f));
		final var pressedColor = new Vector4f(0.75f, 0.75f, 0.75f, 1.0f);
		this.colorsByEvents.put("JUST_PRESSED", pressedColor);
		this.colorsByEvents.put("PRESSED", pressedColor);
		this.colorsByEvents.put("PRESSED_FOR_WHILE", pressedColor);
		this.colorsByEvents.put("JUST_REPEATED", pressedColor);
		this.colorsByEvents.put("REPEATED", pressedColor);
		this.colorsByEvents.put("REPEATED_FOR_WHILE", pressedColor);
		this.releaseColor = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.colorsByEvents.put("JUST_RELEASED", this.releaseColor);
		this.colorsByEvents.put("RELEASED", this.releaseColor);
		this.colorsByEvents.put("RELEASED_FOR_WHILE", this.releaseColor);
		this.currentColor = new Vector4f(this.releaseColor);
	}

	@Override
	public IHudComponent draw2D(final Shader2DIn3D shader2DIn3DIn)
	{
		shader2DIn3DIn.color().load(this.currentColor);
		return super.draw2D(shader2DIn3DIn);
	}

	@Override
	public IHudComponent hovering(final double normalizedMouseXIn, final double normalizedMouseYIn,
			final InputManager inputManagerIn)
	{
		// TODO Repair this (((Shortcut)inputManagerIn.shortcut("USE_CLICK_IN_HUDS")).buttonNode().button().actionType())
		switch (((Shortcut) inputManagerIn.shortcuts().get("USE_CLICK_IN_HUDS")).entryPoints().get(0).button()
				.actionSubType())
		{
			case JUST_PRESSED:
				this.currentColor = this.colorsByEvents.get("JUST_PRESSED");

				break;
			case PRESSED:
				this.currentColor = this.colorsByEvents.get("PRESSED_FOR_WHILE");

				break;
			case PRESSED_FOR_WHILE:
				this.currentColor = this.colorsByEvents.get("PRESSED_FOR_WHILE");

				break;

			case JUST_REPEATED:
				this.currentColor = this.colorsByEvents.get("JUST_REPEATED");

				break;
			case REPEATED:
				this.currentColor = this.colorsByEvents.get("REPEATED");

				break;
			case REPEATED_FOR_WHILE:
				this.currentColor = this.colorsByEvents.get("REPEATED_FOR_WHILE");

				break;
			case JUST_RELEASED:
				this.currentColor = this.colorsByEvents.get("JUST_RELEASED");
				break;
			case RELEASED:
				this.currentColor = this.colorsByEvents.get("RELEASED");
				break;
			case RELEASED_FOR_WHILE:
				this.currentColor = this.colorsByEvents.get("hovering");
				break;

			default:
				this.currentColor = this.colorsByEvents.get("hovering");
				break;
		}
		this.hasClicked = inputManagerIn.shortcuts().isJustTriggered("USE_CLICK_IN_HUDS");

		return this;
	}

	@Override
	public IHudComponent stopHovering(final double normalizedMouseXIn, final double normalizedMouseYIn,
			final InputManager inputManagerIn)
	{
		this.hasClicked		= false;

		this.currentColor	= this.colorsByEvents.get("RELEASED_FOR_WHILE");

		return this;
	}
}
