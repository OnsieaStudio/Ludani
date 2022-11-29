package fr.onsiea.engine.client.input;

public class KeyAndTimedAction
{
	private Key			key;
	private TimedAction	timedAction;

	public KeyAndTimedAction(Key keyIn, TimedAction timedActionIn)
	{
		this.key(keyIn);
		this.timedAction(timedActionIn);
	}

	public Key key()
	{
		return this.key;
	}

	public void key(Key keyIn)
	{
		this.key = keyIn;
	}

	public TimedAction timedAction()
	{
		return this.timedAction;
	}

	public void timedAction(TimedAction timedActionIn)
	{
		this.timedAction = timedActionIn;
	}
}
