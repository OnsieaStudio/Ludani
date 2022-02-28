package fr.onsiea.engine.client.input.callbacks.glfw;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.glfw.GLFWMouseButtonCallback;

import fr.onsiea.engine.client.input.Cursor;
import fr.onsiea.engine.client.input.callback.IClearableCallback;
import fr.onsiea.engine.client.input.callback.IResetableCallback;

public class CallbackMouseButton extends GLFWMouseButtonCallback implements IClearableCallback, IResetableCallback
{
	private Cursor					cursor;

	private boolean					updated;
	private Map<Integer, Button>	buttons;

	public CallbackMouseButton(Cursor cursorIn)
	{
		this.cursor(cursorIn);
		this.buttons(new HashMap<>());
	}

	@Override
	public void invoke(long windowIn, int buttonIn, int actionIn, int modsIn)
	{
		final var	time	= System.nanoTime();

		var			button	= this.buttons().get(buttonIn);

		if (button == null)
		{
			button = new Button(buttonIn);
			this.buttons().put(buttonIn, button);
		}

		button.add(time, actionIn, modsIn);
		this.cursor().input(buttonIn, actionIn);
	}

	@Override
	public void reset()
	{
		this.updated(false);
	}

	@Override
	public void clear()
	{
		this.buttons().clear();
	}

	public Collection<Button> buttonsValues()
	{
		return this.buttons().values();
	}

	private Cursor cursor()
	{
		return this.cursor;
	}

	private void cursor(Cursor cursorIn)
	{
		this.cursor = cursorIn;
	}

	@Override
	public boolean isUpdated()
	{
		return this.updated;
	}

	public void updated(boolean updatedIn)
	{
		this.updated = updatedIn;
	}

	private Map<Integer, Button> buttons()
	{
		return this.buttons;
	}

	private void buttons(Map<Integer, Button> buttonsIn)
	{
		this.buttons = buttonsIn;
	}

	public final static class Button
	{
		private int					button;
		private List<TimedAction>	actions;

		public Button(int buttonIn)
		{
			this.button(buttonIn);
			this.actions(new ArrayList<>());
		}

		public void add(long timeIn, int actionIn, int modsIn)
		{
			this.actions().add(new TimedAction(timeIn, actionIn, modsIn));
		}

		public void remove(int indexIn)
		{
			this.actions().remove(indexIn);
		}

		public void clear()
		{
			this.actions().clear();
		}

		public Collection<TimedAction> actionsValues()
		{
			return this.actions();
		}

		public int button()
		{
			return this.button;
		}

		private void button(int buttonIn)
		{
			this.button = buttonIn;
		}

		private List<TimedAction> actions()
		{
			return this.actions;
		}

		private void actions(List<TimedAction> actionsIn)
		{
			this.actions = actionsIn;
		}
	}

	public final static class TimedAction
	{
		private long	time;
		private int		action;
		private int		mods;

		public TimedAction(long timeIn, int actionIn, int modsIn)
		{
			this.time(timeIn);
			this.action(actionIn);
			this.mods(modsIn);
		}

		public long time()
		{
			return this.time;
		}

		private void time(long timeIn)
		{
			this.time = timeIn;
		}

		public int action()
		{
			return this.action;
		}

		private void action(int actionIn)
		{
			this.action = actionIn;
		}

		public int mods()
		{
			return this.mods;
		}

		private void mods(int modsIn)
		{
			this.mods = modsIn;
		}
	}
}
