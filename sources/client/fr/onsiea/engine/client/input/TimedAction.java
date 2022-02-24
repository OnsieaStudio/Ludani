package fr.onsiea.engine.client.input;

public class TimedAction
{
	private long		start;
	private long		startForWhile;
	private ActionTypes	actionType;

	public TimedAction(long startIn, ActionTypes actionIn)
	{
		this.start(startIn);
		this.type(actionIn);
	}

	TimedAction()
	{
		this.start(System.nanoTime());
		this.type(ActionTypes.UNKNOWN);
	}

	void set(long startIn, ActionTypes typeIn)
	{
		this.start(startIn);
		this.type(typeIn);
	}

	void set(ActionTypes typeIn)
	{
		this.start(System.nanoTime());
		this.type(typeIn);
	}

	void setForWhile(long startForWhileIn, ActionTypes typeIn)
	{
		this.startForWhile(startForWhileIn);
		this.type(typeIn);
	}

	void setForWhile(ActionTypes typeIn)
	{
		this.startForWhile(System.nanoTime());
		this.type(typeIn);
	}

	public void update()
	{
		switch (this.type())
		{
			case JUST_PRESSED:
				this.set(ActionTypes.PRESSED);
				break;

			case PRESSED:
				this.setForWhile(ActionTypes.PRESSED_FOR_WHILE);
				break;

			case JUST_RELEASED:
				this.set(ActionTypes.RELEASED);
				break;

			case RELEASED:
				this.setForWhile(ActionTypes.RELEASED_FOR_WHILE);
				break;

			case JUST_REPEATED:
				this.set(ActionTypes.REPEATED);
				break;

			case REPEATED:
				this.setForWhile(ActionTypes.REPEATED_FOR_WHILE);
				break;

			default:
				this.set(ActionTypes.UNKNOWN);
		}
	}

	public boolean isTime(long timeIn)
	{
		return this.time() >= timeIn;
	}

	public long time()
	{
		return System.nanoTime() - this.start();
	}

	public boolean isTimeForWhile(long timeIn)
	{
		return this.timeForWhile() >= timeIn;
	}

	public long timeForWhile()
	{
		return System.nanoTime() - this.startForWhile();
	}

	public long start()
	{
		return this.start;
	}

	void start(long startIn)
	{
		this.start = startIn;
	}

	public long startForWhile()
	{
		return this.startForWhile;
	}

	public void startForWhile(long startForWhileIn)
	{
		this.startForWhile = startForWhileIn;
	}

	public ActionTypes type()
	{
		return this.actionType;
	}

	void type(ActionTypes actionTypeIn)
	{
		this.actionType = actionTypeIn;
	}
}