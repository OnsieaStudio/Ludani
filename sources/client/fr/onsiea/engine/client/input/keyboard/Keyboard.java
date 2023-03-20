package fr.onsiea.engine.client.input.keyboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.glfw.GLFW;

import fr.onsiea.engine.client.input.action.EnumSubActionType;
import fr.onsiea.engine.client.input.action.IButtonAction;
import fr.onsiea.engine.client.input.action.IButtonTester;
import fr.onsiea.engine.utils.Pair;
import fr.onsiea.engine.utils.time.Timer;
import lombok.Getter;

public class Keyboard implements IButtonTester
{
	private @Getter final static long			TEXT_CURSOR_BLINKING_SPEED	= 500_000_000L;

	private @Getter boolean						textCursorBlinking;
	private @Getter final Timer					timer;

	private final Map<Integer, ButtonAction>	keysActions;
	private final List<Pair<Integer, Integer>>	codepointsAndMods;

	private @Getter final Keys					justPressed;
	private @Getter final Keys					pressed;
	private @Getter final Keys					pressedForWhile;
	private @Getter final Keys					justReleased;
	private @Getter final Keys					released;
	private @Getter final Keys					releasedForWhile;
	private @Getter final Keys					justRepeated;
	private @Getter final Keys					repeated;
	private @Getter final Keys					repeatedForWhile;

	public Keyboard()
	{
		this.timer				= new Timer();
		this.keysActions		= new HashMap<>();
		this.codepointsAndMods	= new ArrayList<>();

		this.justPressed		= new Keys();
		this.pressed			= new Keys();
		this.pressedForWhile	= new Keys();
		this.justReleased		= new Keys();
		this.released			= new Keys();
		this.releasedForWhile	= new Keys();
		this.justRepeated		= new Keys();
		this.repeated			= new Keys();
		this.repeatedForWhile	= new Keys();
	}

	public void update()
	{
		if (this.timer().isTime(Keyboard.TEXT_CURSOR_BLINKING_SPEED))
		{
			this.textCursorBlinking = !this.textCursorBlinking;
		}
	}

	public void character(final int codepointIn, final int modsIn)
	{
		this.codepointsAndMods().add(new Pair<>(codepointIn, modsIn));
	}

	public void key(final int keyIn, final int scancodeIn, final int actionIn, final int modsIn)
	{
		var keyAction = this.keysActions.get(keyIn);

		if (keyAction == null)
		{
			keyAction = new ButtonAction(keyIn, scancodeIn, modsIn);
			this.keysActions.put(keyIn, keyAction);
		}
		/**else
		{
			keyAndTimedAction.key().key(keyIn);
			keyAndTimedAction.key().scancode(scancodeIn);
			keyAndTimedAction.key().mods(modsIn);
		}**/

		switch (actionIn)
		{
			case GLFW.GLFW_PRESS:
				if (!EnumSubActionType.JUST_PRESSED.equals(keyAction.action().subType())
						&& !EnumSubActionType.PRESSED.equals(keyAction.action().subType())
						&& !EnumSubActionType.PRESSED_FOR_WHILE.equals(keyAction.action().subType()))
				{
					keyAction.action().set(EnumSubActionType.JUST_PRESSED);
				}

				break;

			case GLFW.GLFW_RELEASE:

				if (!EnumSubActionType.JUST_RELEASED.equals(keyAction.action().subType())
						&& !EnumSubActionType.RELEASED.equals(keyAction.action().subType())
						&& !EnumSubActionType.RELEASED_FOR_WHILE.equals(keyAction.action().subType()))
				{
					keyAction.action().set(EnumSubActionType.JUST_RELEASED);
				}

				break;

			case GLFW.GLFW_REPEAT:

				if (!EnumSubActionType.JUST_REPEATED.equals(keyAction.action().subType())
						&& !EnumSubActionType.REPEATED.equals(keyAction.action().subType())
						&& !EnumSubActionType.REPEATED_FOR_WHILE.equals(keyAction.action().subType()))
				{
					keyAction.action().set(EnumSubActionType.JUST_REPEATED);
				}

				break;

			default:

				keyAction.action().set(EnumSubActionType.UNKNOWN);

				break;
		}

		this.addToLists(keyAction);
	}

	private final void addToLists(final ButtonAction keyActionIn)
	{
		switch (keyActionIn.action().subType())
		{
			case JUST_PRESSED:
				this.justPressed.add(keyActionIn);
				break;

			case PRESSED:
				this.pressed.add(keyActionIn);
				break;

			case PRESSED_FOR_WHILE:
				this.pressedForWhile.add(keyActionIn);
				break;

			case JUST_RELEASED:
				this.justReleased.add(keyActionIn);
				break;

			case RELEASED:
				this.released.add(keyActionIn);
				break;

			case RELEASED_FOR_WHILE:
				this.releasedForWhile.add(keyActionIn);
				break;

			case JUST_REPEATED:
				this.justRepeated.add(keyActionIn);
				break;

			case REPEATED:
				this.repeated.add(keyActionIn);
				break;
			case REPEATED_FOR_WHILE:
				this.repeatedForWhile.add(keyActionIn);
				break;
			default:
				break;
		}
	}

	public void end()
	{
		for (final var button : this.keysActions.values())
		{
			button.action().update();

			this.addToLists(button);
		}

		this.codepointsAndMods().clear();
	}

	public EnumSubActionType actionOfKey(final int keyIn)
	{
		final var keyAndTimedAction = this.keysActions.get(keyIn);

		if (keyAndTimedAction == null)
		{
			return EnumSubActionType.NONE;
		}

		return keyAndTimedAction.action().subType();
	}

	@Override
	public IButtonAction buttonAction(final int idIn)
	{
		return this.keysActions.get(idIn);
	}

	public List<ButtonAction> keysAndModsOf(final EnumSubActionType... actionTypesIn)
	{
		final List<ButtonAction> keys = new ArrayList<>();

		for (final Entry<Integer, ButtonAction> entry : this.keysActions.entrySet())
		{
			for (final EnumSubActionType actionType : actionTypesIn)
			{
				if (entry.getValue().action().subType().equals(actionType))
				{
					keys.add(entry.getValue());
				}
			}
		}

		return keys;
	}

	// Key methods

	// glfwSetInputMode (fen�tre, GLFW_STICKY_KEYS , GLFW_TRUE );
	// glfwSetInputMode (fen�tre, GLFW_LOCK_KEY_MODS , GLFW_TRUE );

	public int getKeyScancode(final int glfwKeyIn)
	{
		return GLFW.glfwGetKeyScancode(glfwKeyIn);
	}

	public int getKey(final long handleIn, final int glfwKeyIn)
	{
		return GLFW.glfwGetKey(handleIn, glfwKeyIn);
	}

	public boolean keyIsPress(final long handleIn, final int glfwKeyIn)
	{
		return GLFW.glfwGetKey(handleIn, glfwKeyIn) == GLFW.GLFW_PRESS;
	}

	public boolean keyIsRepeat(final long handleIn, final int glfwKeyIn)
	{
		return GLFW.glfwGetKey(handleIn, glfwKeyIn) == GLFW.GLFW_REPEAT;
	}

	public boolean keyIsRelease(final long handleIn, final int glfwKeyIn)
	{
		return GLFW.glfwGetKey(handleIn, glfwKeyIn) == GLFW.GLFW_RELEASE;
	}

	public String getKeyName(final int glfwKeyIn)
	{
		return GLFW.glfwGetKeyName(glfwKeyIn, 0);
	}

	public String getGamepadKeyName(final int gamepadKeyIn)
	{
		return GLFW.glfwGetGamepadName(gamepadKeyIn);
	}

	public String getJoystickKeyName(final int joystickKeyIn)
	{
		return GLFW.glfwGetJoystickName(joystickKeyIn);
	}

	public String getJoystickKeyGUID(final int joystickKeyIn)
	{
		return GLFW.glfwGetJoystickGUID(joystickKeyIn);
	}

	public String getClipboardContent(final long handleIn)
	{
		return GLFW.glfwGetClipboardString(handleIn);
	}

	public List<Pair<Integer, Integer>> codepointsAndMods()
	{
		return this.codepointsAndMods;
	}
}
