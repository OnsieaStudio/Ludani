package fr.onsiea.engine.utils;

public class FlagBoolean
{
	private int attributes;

	public FlagBoolean(int... attributesIn)
	{
		for (final int attribute : attributesIn)
		{
			this.enable(attribute);
		}
	}

	public void enable(final int attributIn)
	{
		this.attributes(this.attributes() | 1 << attributIn);
	}

	public void disable(final int attributIn)
	{
		this.attributes(this.attributes() & ~(1 << attributIn));
	}

	public void set(final int attributIn, final boolean valueIn)
	{
		if (valueIn)
		{
			this.attributes(this.attributes() | 1 << attributIn);
		}
		else
		{
			this.attributes(this.attributes() & ~(1 << attributIn));
		}
	}

	public boolean has(final int indexIn)
	{
		return ((0x1 & this.attributes() >> indexIn) == 1);
	}

	private final int attributes()
	{
		return this.attributes;
	}

	private final void attributes(int attributesIn)
	{
		this.attributes = attributesIn;
	}
}