package fr.onsiea.engine.utils.maths.vector.timed;

import org.joml.Vector3f;

public class TimedVector3f
{
	private Vector3f	base;
	private Vector3f	variation;
	private Vector3f	last;
	private boolean		hasChanged;

	public TimedVector3f()
	{
		this.base(new Vector3f());
		this.variation(new Vector3f());
		this.last(new Vector3f());
	}

	public TimedVector3f(Vector3f baseIn)
	{
		this.base(baseIn);
		this.variation(new Vector3f());
		this.last(new Vector3f());
	}

	public void add(float xIn, float yIn, float zIn)
	{
		this.variation().set(xIn - (this.base().x() + xIn), yIn - (this.base().y() + yIn),
				zIn - (this.base().z() + zIn));

		if (this.variation().x() != 0 || this.variation().y() != 0 || this.variation().z() != 0)
		{
			this.hasChanged(true);
		}

		this.last().set(this.base().x(), this.base().y(), this.base().z());
		this.base().add(xIn, yIn, zIn);
	}

	public void set(float xIn, float yIn, float zIn)
	{
		this.variation().set(xIn - this.base().x(), yIn - this.base().y(), zIn - this.base().z());

		if (this.variation().x() != 0 || this.variation().y() != 0 || this.variation().z() != 0)
		{
			this.hasChanged(true);
		}

		this.last().set(this.base().x(), this.base().y(), this.base().z());
		this.base().set(xIn, yIn, zIn);
	}

	public float x()
	{
		return this.base().x();
	}

	public float y()
	{
		return this.base().y();
	}

	public float z()
	{
		return this.base().z();
	}

	public final Vector3f base()
	{
		return this.base;
	}

	protected final void base(Vector3f baseIn)
	{
		this.base = baseIn;
	}

	public final Vector3f variation()
	{
		return this.variation;
	}

	protected final void variation(Vector3f variationIn)
	{
		this.variation = variationIn;
	}

	public final Vector3f last()
	{
		return this.last;
	}

	protected final void last(Vector3f lastIn)
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
