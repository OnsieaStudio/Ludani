package fr.onsiea.engine.maths.vector;

import org.joml.Vector3i;

public class TimedVector3i
{
	private Vector3i	base;
	private Vector3i	variation;
	private Vector3i	last;
	private boolean		hasChanged;

	public TimedVector3i()
	{
		this.base(new Vector3i());
		this.variation(new Vector3i());
		this.last(new Vector3i());
	}

	public TimedVector3i(Vector3i baseIn)
	{
		this.base(baseIn);
		this.variation(new Vector3i());
		this.last(new Vector3i());
	}

	public void add(int xIn, int yIn, int zIn)
	{
		this.variation().set(xIn - (this.base().x() + xIn), yIn - (this.base().y() + yIn),
				zIn - (this.base().z() + zIn));

		if (this.variation().x() != 0 || this.variation().y() != 0 || this.variation().z() != 0)
		{
			this.hasChanged(true);
		}

		this.last().add(this.base().x(), this.base().y(), this.base().z());
		this.base().set(xIn, yIn, zIn);
	}

	public void set(int xIn, int yIn, int zIn)
	{
		this.variation().set(xIn - this.base().x(), yIn - this.base().y(), zIn - this.base().z());

		if (this.variation().x() != 0 || this.variation().y() != 0 || this.variation().z() != 0)
		{
			this.hasChanged(true);
		}

		this.last().set(this.base().x(), this.base().y(), this.base().z());
		this.base().set(xIn, yIn, zIn);
	}

	public int x()
	{
		return this.base().x();
	}

	public int y()
	{
		return this.base().y();
	}

	public int z()
	{
		return this.base().z();
	}

	public final Vector3i base()
	{
		return this.base;
	}

	protected final void base(Vector3i baseIn)
	{
		this.base = baseIn;
	}

	public final Vector3i variation()
	{
		return this.variation;
	}

	protected final void variation(Vector3i variationIn)
	{
		this.variation = variationIn;
	}

	public final Vector3i last()
	{
		return this.last;
	}

	protected final void last(Vector3i lastIn)
	{
		this.last = lastIn;
	}

	public final boolean hasChanged()
	{
		return this.hasChanged;
	}

	protected final void hasChanged(boolean hasChangedIn)
	{
		this.hasChanged = hasChangedIn;
	}
}
