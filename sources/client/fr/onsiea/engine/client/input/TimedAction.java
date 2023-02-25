package fr.onsiea.engine.client.input;

public class TimedAction
{
	private long			start;
	private long			startForWhile;
	private EnumActionType	actionType;

	public TimedAction(final long startIn, final EnumActionType actionIn)
	{
		this.start(startIn);
		this.type(actionIn);
	}

	TimedAction()
	{
		this.start(System.nanoTime());
		this.type(EnumActionType.NONE);
	}

	void set(final long startIn, final EnumActionType typeIn)
	{
		this.start(startIn);
		this.type(typeIn);
	}

	void set(final EnumActionType typeIn)
	{
		this.start(System.nanoTime());
		this.type(typeIn);
	}

	void setForWhile(final long startForWhileIn, final EnumActionType typeIn)
	{
		this.startForWhile(startForWhileIn);
		this.type(typeIn);
	}

	void setForWhile(final EnumActionType typeIn)
	{
		this.startForWhile(System.nanoTime());
		this.type(typeIn);
	}

	public void update()
	{
		switch (this.type())
		{
			case JUST_PRESSED:
				this.set(EnumActionType.PRESSED);
				break;

			case PRESSED:
				this.setForWhile(EnumActionType.PRESSED_FOR_WHILE);
				break;

			case JUST_RELEASED:
				this.set(EnumActionType.RELEASED);
				break;

			case RELEASED:
				this.setForWhile(EnumActionType.RELEASED_FOR_WHILE);
				break;

			case JUST_REPEATED:
				this.set(EnumActionType.REPEATED);
				break;

			case REPEATED:
				this.setForWhile(EnumActionType.REPEATED_FOR_WHILE);
				break;

			default:
				//	this.set(EnumActionType.UNKNOWN);
		}
	}

	public boolean isTime(final long timeIn)
	{
		return this.time() >= timeIn;
	}

	public long time()
	{
		return System.nanoTime() - this.start();
	}

	public boolean isTimeForWhile(final long timeIn)
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

	void start(final long startIn)
	{
		this.start = startIn;
	}

	public long startForWhile()
	{
		return this.startForWhile;
	}

	public void startForWhile(final long startForWhileIn)
	{
		this.startForWhile = startForWhileIn;
	}

	public EnumActionType type()
	{
		return this.actionType;
	}

	void type(final EnumActionType actionTypeIn)
	{
		this.actionType = actionTypeIn;
	}
}