package fr.onsiea.engine.client.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.glfw.GLFW;

import fr.onsiea.engine.utils.Pair;
import fr.onsiea.engine.utils.time.Timer;

public class Keyboard
{
	private final static long				TEXT_CURSOR_BLINKING_SPEED	= 500_000_000L;

	private boolean							textCursorBlinking;
	private Timer							timer;

	private Map<Integer, KeyAndTimedAction>	keysActions;
	private List<Pair<Integer, Integer>>	codepointsAndMods;

	public Keyboard()
	{
		this.timer(new Timer());
		this.keysActions(new HashMap<>());
		this.codepointsAndMods(new ArrayList<>());
	}

	public void update()
	{
		if (this.timer().isTime(Keyboard.TEXT_CURSOR_BLINKING_SPEED))
		{
			this.textCursorBlinking(!this.isTextCursorBlinking());
		}
	}

	public void character(int codepointIn, int modsIn)
	{
		this.codepointsAndMods().add(new Pair<>(codepointIn, modsIn));
	}

	public void key(int keyIn, int scancodeIn, int actionIn, int modsIn)
	{
		var keyAndTimedAction = this.keysActions().get(keyIn);

		if (keyAndTimedAction == null)
		{
			keyAndTimedAction = new KeyAndTimedAction(new Key(keyIn, scancodeIn, modsIn), new TimedAction());
			this.keysActions().put(keyIn, keyAndTimedAction);
		}
		else
		{
			keyAndTimedAction.key().id(keyIn);
			keyAndTimedAction.key().scancode(scancodeIn);
			keyAndTimedAction.key().mods(modsIn);
		}

		switch (actionIn)
		{
			case GLFW.GLFW_PRESS:
				if (ActionTypes.JUST_PRESSED.equals(keyAndTimedAction.timedAction().type())
						|| ActionTypes.PRESSED.equals(keyAndTimedAction.timedAction().type())
						|| ActionTypes.PRESSED_FOR_WHILE.equals(keyAndTimedAction.timedAction().type()))
				{
					return;
				}

				keyAndTimedAction.timedAction().set(ActionTypes.JUST_PRESSED);

				break;

			case GLFW.GLFW_RELEASE:

				if (ActionTypes.JUST_RELEASED.equals(keyAndTimedAction.timedAction().type())
						|| ActionTypes.RELEASED.equals(keyAndTimedAction.timedAction().type())
						|| ActionTypes.RELEASED_FOR_WHILE.equals(keyAndTimedAction.timedAction().type()))
				{
					return;
				}

				keyAndTimedAction.timedAction().set(ActionTypes.JUST_RELEASED);

				break;

			case GLFW.GLFW_REPEAT:

				if (ActionTypes.JUST_REPEATED.equals(keyAndTimedAction.timedAction().type())
						|| ActionTypes.REPEATED.equals(keyAndTimedAction.timedAction().type())
						|| ActionTypes.REPEATED_FOR_WHILE.equals(keyAndTimedAction.timedAction().type()))
				{
					return;
				}

				keyAndTimedAction.timedAction().set(ActionTypes.JUST_REPEATED);

				break;
		}
	}

	public void end()
	{
		final var keysActionsIterator = this.keysActions().entrySet().iterator();

		while (keysActionsIterator.hasNext())
		{
			keysActionsIterator.next().getValue().timedAction().update();
		}

		this.codepointsAndMods().clear();
	}

	public KeyAndTimedAction keyAndActionOf(int keyIn)
	{
		return this.keysActions().get(keyIn);
	}

	public List<Key> keysAndModsOf(ActionTypes... actionTypesIn)
	{
		final List<Key>	keys		= new ArrayList<>();

		final var		iterator	= this.keysActions().entrySet().iterator();
		while (iterator.hasNext())
		{
			final var entry = iterator.next();

			for (final ActionTypes actionType : actionTypesIn)
			{
				if (entry.getValue().timedAction().type().equals(actionType))
				{
					keys.add(entry.getValue().key());
				}
			}
		}

		return keys;
	}

	// Key methods

	// glfwSetInputMode (fenêtre, GLFW_STICKY_KEYS , GLFW_TRUE );
	// glfwSetInputMode (fenêtre, GLFW_LOCK_KEY_MODS , GLFW_TRUE );

	public int getKeyScancode(final int glfwKeyIn)
	{
		return GLFW.glfwGetKeyScancode(glfwKeyIn);
	}

	public int getKey(long handleIn, final int glfwKeyIn)
	{
		return GLFW.glfwGetKey(handleIn, glfwKeyIn);
	}

	public boolean keyIsPress(long handleIn, final int glfwKeyIn)
	{
		return GLFW.glfwGetKey(handleIn, glfwKeyIn) == GLFW.GLFW_PRESS;
	}

	public boolean keyIsRepeat(long handleIn, final int glfwKeyIn)
	{
		return GLFW.glfwGetKey(handleIn, glfwKeyIn) == GLFW.GLFW_REPEAT;
	}

	public boolean keyIsRelease(long handleIn, final int glfwKeyIn)
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

	public String getClipboardContent(long handleIn)
	{
		return GLFW.glfwGetClipboardString(handleIn);
	}

	public List<Pair<Integer, Integer>> codepointsAndMods()
	{
		return this.codepointsAndMods;
	}

	private void codepointsAndMods(List<Pair<Integer, Integer>> codepointsAndModsIn)
	{
		this.codepointsAndMods = codepointsAndModsIn;
	}

	private Map<Integer, KeyAndTimedAction> keysActions()
	{
		return this.keysActions;
	}

	private void keysActions(Map<Integer, KeyAndTimedAction> keysActionsIn)
	{
		this.keysActions = keysActionsIn;
	}

	public boolean isTextCursorBlinking()
	{
		return this.textCursorBlinking;
	}

	public void textCursorBlinking(boolean textCursorBlinkingIn)
	{
		this.textCursorBlinking = textCursorBlinkingIn;
	}

	public Timer timer()
	{
		return this.timer;
	}

	public void timer(Timer timerIn)
	{
		this.timer = timerIn;
	}

	public static long textCursorBlinkingSpeed()
	{
		return Keyboard.TEXT_CURSOR_BLINKING_SPEED;
	}
}
