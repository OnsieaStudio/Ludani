package fr.onsiea.engine.maths.vertice;

import java.util.Objects;

import org.joml.Vector2f;

public class Vertice2f
{
	public final static boolean hasTwoCommonPoint(float fromXIn, float fromYIn, float toXIn, float toYIn)
	{
		var i = 0;

		if (fromXIn == toXIn)
		{
			i++;
		}
		if (fromYIn == toYIn)
		{
			i++;
		}

		return i >= 2;
	}

	public final static boolean isOpposed(float fromXIn, float fromYIn, float toXIn, float toYIn)
	{
		return fromXIn != toXIn && fromYIn != toYIn;
	}

	public final static boolean hasCommonPoint(float fromXIn, float fromYIn, float toXIn, float toYIn)
	{
		return fromXIn == toXIn || fromYIn == toYIn;
	}

	public final static boolean isEqual(float fromXIn, float fromYIn, float toXIn, float toYIn)
	{
		return fromXIn == toXIn && fromYIn == toYIn;
	}

	public final static float dist(float fromXIn, float fromYIn, float toXIn, float toYIn)
	{
		final var	distX	= fromXIn - toXIn;
		final var	distY	= fromYIn - toYIn;

		return distX * distX + distY * distY;
	}

	public final static boolean distUpper(float fromXIn, float fromYIn, float toXIn, float toYIn, float valueIn)
	{
		return Vertice2f.dist(fromXIn, fromYIn, toXIn, toYIn) >= valueIn;
	}

	private float	x;
	private float	y;

	public Vertice2f()
	{

	}

	public Vertice2f(float xIn)
	{
		this.x(xIn);
	}

	public Vertice2f(float xIn, float yIn)
	{
		this.x(xIn);
		this.y(yIn);
	}

	public Vertice2f(Vertice2f verticeIn)
	{
		this.x(verticeIn.x());
		this.y(verticeIn.y());
	}

	public final boolean isOpposed(Vertice2f verticeIn)
	{
		return this.x() != verticeIn.x() && this.y() != verticeIn.y();
	}

	public final boolean hasCommonPoint(Vertice2f verticeIn)
	{
		return this.x() == verticeIn.x() || this.y() == verticeIn.y();
	}

	public final boolean isEqual(Vertice2f verticeIn)
	{
		return this.x() == verticeIn.x() && this.y() == verticeIn.y();
	}

	public final float dist(Vertice2f verticeIn)
	{
		final var	distX	= this.x() - verticeIn.x();
		final var	distY	= this.y() - verticeIn.y();

		return distX * distX + distY * distY;
	}

	public final boolean distUpper(Vertice2f verticeIn, float valueIn)
	{
		return this.dist(verticeIn) >= valueIn;
	}

	public final Vertice2f copy()
	{
		return new Vertice2f(this);
	}

	public final Vector2f vector()
	{
		return new Vector2f(this.x(), this.y());
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(this.x(), this.y());
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null || this.getClass() != obj.getClass())
		{
			return false;
		}
		final var other = (Vertice2f) obj;
		return Float.floatToIntBits(this.x()) == Float.floatToIntBits(other.x())
				&& Float.floatToIntBits(this.y()) == Float.floatToIntBits(other.y());
	}

	@Override
	public String toString()
	{
		return "Vertice [x=" + this.x() + ", y=" + this.y() + "]";
	}

	public float x()
	{
		return this.x;
	}

	public void x(float xIn)
	{
		this.x = xIn;
	}

	public float y()
	{
		return this.y;
	}

	public void y(float yIn)
	{
		this.y = yIn;
	}
}
