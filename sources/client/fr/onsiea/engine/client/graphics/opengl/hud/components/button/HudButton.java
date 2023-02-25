/**
 *
 */
package fr.onsiea.engine.client.graphics.opengl.hud.components.button;

import java.util.HashMap;
import java.util.Map;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;

import fr.onsiea.engine.client.graphics.opengl.hud.components.HudRectangle;
import fr.onsiea.engine.client.graphics.opengl.shaders.Shader2DIn3D;
import fr.onsiea.engine.client.graphics.opengl.shaders.Shader3DTo2D;
import fr.onsiea.engine.client.graphics.texture.ITexture;
import fr.onsiea.engine.client.graphics.window.IWindow;
import fr.onsiea.engine.client.input.InputManager;
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

	public HudButton(final Vector2f positionIn, final Vector2f scaleIn, final ITexture textureIn)
	{
		super(positionIn, scaleIn, textureIn);

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
	public void draw2D(final Shader2DIn3D shader2DIn3DIn)
	{
		shader2DIn3DIn.color().load(this.currentColor);
		super.draw2D(shader2DIn3DIn);
	}

	@Override
	public void draw3D(final Shader3DTo2D shader3dTo2DIn, final IWindow windowIn)
	{
		super.draw3D(shader3dTo2DIn, windowIn);
	}

	public void cursorMove(final double normalizedMouseXIn, final double normalizedMouseYIn,
			final InputManager inputManagerIn)
	{
	}

	@Override
	public void hovering(final double normalizedMouseXIn, final double normalizedMouseYIn,
			final InputManager inputManagerIn)
	{
		super.hovering(normalizedMouseXIn, normalizedMouseYIn, inputManagerIn);

		final var action = inputManagerIn.cursor().buttionActionOf(GLFW.GLFW_MOUSE_BUTTON_1);
		if (action != null)
		{
			switch (action.type())
			{
				case JUST_PRESSED:
					this.currentColor = this.colorsByEvents.get("JUST_PRESSED");
					this.hasClicked = true;

					break;
				case PRESSED:
					this.currentColor = this.colorsByEvents.get("PRESSED_FOR_WHILE");
					this.hasClicked = false;

					break;
				case PRESSED_FOR_WHILE:
					this.currentColor = this.colorsByEvents.get("PRESSED_FOR_WHILE");
					this.hasClicked = false;

					break;

				case JUST_REPEATED:
					this.currentColor = this.colorsByEvents.get("JUST_REPEATED");
					this.hasClicked = false;

					break;
				case REPEATED:
					this.currentColor = this.colorsByEvents.get("REPEATED");
					this.hasClicked = false;

					break;
				case REPEATED_FOR_WHILE:
					this.currentColor = this.colorsByEvents.get("REPEATED_FOR_WHILE");
					this.hasClicked = false;

					break;
				case JUST_RELEASED:
					this.currentColor = this.colorsByEvents.get("JUST_RELEASED");
					this.hasClicked = false;
					break;
				case RELEASED:
					this.currentColor = this.colorsByEvents.get("RELEASED");
					this.hasClicked = false;
					break;
				case RELEASED_FOR_WHILE:
					this.currentColor = this.colorsByEvents.get("hovering");
					break;

				default:
					break;
			}
		}
		else
		{
			this.currentColor = this.colorsByEvents.get("hovering");
		}
	}

	@Override
	public void stopHovering(final double normalizedMouseXIn, final double normalizedMouseYIn,
			final InputManager inputManagerIn)
	{
		super.stopHovering(normalizedMouseXIn, normalizedMouseYIn, inputManagerIn);
		this.hasClicked		= false;

		this.currentColor	= this.colorsByEvents.get("RELEASED_FOR_WHILE");
	}
}
