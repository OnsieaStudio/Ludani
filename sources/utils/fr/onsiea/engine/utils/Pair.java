package fr.onsiea.engine.utils;

public class Pair<ONE, TWO>
{
	private ONE	one;
	private TWO	two;

	public Pair(ONE oneIn, TWO twoIn)
	{
		this.one(oneIn);
		this.two(twoIn);
	}

	public ONE one()
	{
		return this.one;
	}

	public void one(ONE oneIn)
	{
		this.one = oneIn;
	}

	public TWO two()
	{
		return this.two;
	}

	public void two(TWO twoIn)
	{
		this.two = twoIn;
	}
}