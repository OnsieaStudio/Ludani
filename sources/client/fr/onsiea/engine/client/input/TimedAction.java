package fr.onsiea.engine.client.input;

import fr.onsiea.engine.client.input.action.EnumSubActionType;

public class TimedAction
{
	private long			start;
	private long			startForWhile;
	private EnumSubActionType	subType;

	public TimedAction(final long startIn, final EnumSubActionType actionIn)
	{
		this.start(startIn);
		this.type(actionIn);
	}

	public TimedAction()
	{
		this.start(System.nanoTime());
		this.type(EnumSubActionType.NONE);
	}

	void set(final long startIn, final EnumSubActionType typeIn)
	{
		this.start(startIn);
		this.type(typeIn);
	}

	public void set(final EnumSubActionType typeIn)
	{
		this.start(System.nanoTime());
		this.type(typeIn);
	}

	void setForWhile(final long startForWhileIn, final EnumSubActionType typeIn)
	{
		this.startForWhile(startForWhileIn);
		this.type(typeIn);
	}

	void setForWhile(final EnumSubActionType typeIn)
	{
		this.startForWhile(System.nanoTime());
		this.type(typeIn);
	}

	public void update()
	{
		switch (this.subType())
		{
			case JUST_PRESSED:
				this.set(EnumSubActionType.PRESSED);
				break;

			case PRESSED:
				this.setForWhile(EnumSubActionType.PRESSED_FOR_WHILE);
				break;

			case JUST_RELEASED:
				this.set(EnumSubActionType.RELEASED);
				break;

			case RELEASED:
				this.setForWhile(EnumSubActionType.RELEASED_FOR_WHILE);
				break;

			case JUST_REPEATED:
				this.set(EnumSubActionType.REPEATED);
				break;

			case REPEATED:
				this.setForWhile(EnumSubActionType.REPEATED_FOR_WHILE);
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

	public EnumSubActionType subType()
	{
		return this.subType;
	}

	void type(final EnumSubActionType actionTypeIn)
	{
		this.subType = actionTypeIn;
	}
}