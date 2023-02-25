package fr.onsiea.engine.client.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.glfw.GLFW;

import fr.onsiea.engine.utils.Pair;
import fr.onsiea.engine.utils.time.Timer;
import lombok.Getter;

public class Keyboard
{
	private @Getter final static long			TEXT_CURSOR_BLINKING_SPEED	= 500_000_000L;

	private @Getter boolean						textCursorBlinking;
	private @Getter final Timer					timer;

	private final Map<Integer, KeyAction>		keysActions;
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
			keyAction = new KeyAction(keyIn, scancodeIn, modsIn);
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
				if (!EnumActionType.JUST_PRESSED.equals(keyAction.timedAction().type())
						&& !EnumActionType.PRESSED.equals(keyAction.timedAction().type())
						&& !EnumActionType.PRESSED_FOR_WHILE.equals(keyAction.timedAction().type()))
				{
					keyAction.timedAction().set(EnumActionType.JUST_PRESSED);
				}

				break;

			case GLFW.GLFW_RELEASE:

				if (!EnumActionType.JUST_RELEASED.equals(keyAction.timedAction().type())
						&& !EnumActionType.RELEASED.equals(keyAction.timedAction().type())
						&& !EnumActionType.RELEASED_FOR_WHILE.equals(keyAction.timedAction().type()))
				{
					keyAction.timedAction().set(EnumActionType.JUST_RELEASED);
				}

				break;

			case GLFW.GLFW_REPEAT:

				if (!EnumActionType.JUST_REPEATED.equals(keyAction.timedAction().type())
						&& !EnumActionType.REPEATED.equals(keyAction.timedAction().type())
						&& !EnumActionType.REPEATED_FOR_WHILE.equals(keyAction.timedAction().type()))
				{
					keyAction.timedAction().set(EnumActionType.JUST_REPEATED);
				}

				break;

			default:

				keyAction.timedAction().set(EnumActionType.UNKNOWN);

				break;
		}

		this.addToLists(keyAction);
	}

	private final void addToLists(final KeyAction keyActionIn)
	{
		switch (keyActionIn.timedAction().type())
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
		final var keysActionsIterator = this.keysActions.entrySet().iterator();

		while (keysActionsIterator.hasNext())
		{
			final var	entry	= keysActionsIterator.next();
			final var	value	= entry.getValue();
			if (EnumActionType.RELEASED_FOR_WHILE.equals(value.timedAction().type()))
			{
				keysActionsIterator.remove();
			}
			else
			{
				value.timedAction().update();
			}

			this.addToLists(value);
		}

		this.codepointsAndMods().clear();
	}

	public EnumActionType actionOfKey(final int keyIn)
	{
		final var keyAndTimedAction = this.keysActions.get(keyIn);

		if (keyAndTimedAction == null)
		{
			return EnumActionType.NONE;
		}

		return keyAndTimedAction.timedAction().type();
	}

	public KeyAction keyActionOf(final int keyIn)
	{
		return this.keysActions.get(keyIn);
	}

	public List<KeyAction> keysAndModsOf(final EnumActionType... actionTypesIn)
	{
		final List<KeyAction> keys = new ArrayList<>();

		for (final Entry<Integer, KeyAction> entry : this.keysActions.entrySet())
		{
			for (final EnumActionType actionType : actionTypesIn)
			{
				if (entry.getValue().timedAction().type().equals(actionType))
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
