package fr.onsiea.engine.maths.vertice;

import java.util.Objects;

import org.joml.Vector3f;

public class Vertice3f
{
	public final static boolean hasTwoCommonPoint(float fromXIn, float fromYIn, float fromZIn, float toXIn, float toYIn,
			float toZIn)
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
		if (fromZIn == toZIn)
		{
			i++;
		}

		return i >= 2;
	}

	public final static boolean isOpposed(float fromXIn, float fromYIn, float fromZIn, float toXIn, float toYIn,
			float toZIn)
	{
		return fromXIn != toXIn && fromYIn != toYIn && fromZIn != toZIn;
	}

	public final static boolean hasCommonPoint(float fromXIn, float fromYIn, float fromZIn, float toXIn, float toYIn,
			float toZIn)
	{
		return fromXIn == toXIn || fromYIn == toYIn || fromZIn == toZIn;
	}

	public final static boolean isEqual(float fromXIn, float fromYIn, float fromZIn, float toXIn, float toYIn,
			float toZIn)
	{
		return fromXIn == toXIn && fromYIn == toYIn && fromZIn == toZIn;
	}

	public final static float dist(float fromXIn, float fromYIn, float fromZIn, float toXIn, float toYIn, float toZIn)
	{
		final var	distX	= fromXIn - toXIn;
		final var	distY	= fromYIn - toYIn;
		final var	distZ	= fromZIn - toZIn;

		return distX * distX + distY * distY + distZ * distZ;
	}

	public final static boolean distUpper(float fromXIn, float fromYIn, float fromZIn, float toXIn, float toYIn,
			float toZIn, float valueIn)
	{
		return Vertice3f.dist(fromXIn, fromYIn, fromZIn, toXIn, toYIn, toZIn) > valueIn;
	}

	private float	x;
	private float	y;
	private float	z;

	public Vertice3f()
	{

	}

	public Vertice3f(float xIn)
	{
		this.x(xIn);
	}

	public Vertice3f(float xIn, float yIn)
	{
		this.x(xIn);
		this.y(yIn);
	}

	public Vertice3f(float xIn, float yIn, float zIn)
	{
		this.x(xIn);
		this.y(yIn);
		this.z(zIn);
	}

	public Vertice3f(Vertice3f verticeIn)
	{
		this.x(verticeIn.x());
		this.y(verticeIn.y());
		this.z(verticeIn.z());
	}

	public final boolean isOpposed(Vertice3f verticeIn)
	{
		return this.x() != verticeIn.x() && this.y() != verticeIn.y() && this.z() != verticeIn.z();
	}

	public final boolean hasTwoCommonPoint(Vertice3f verticeIn)
	{
		var i = 0;

		if (this.x() == verticeIn.x())
		{
			i++;
		}
		if (this.y() == verticeIn.y())
		{
			i++;
		}
		if (this.z() == verticeIn.z())
		{
			i++;
		}

		return i >= 2;
	}

	public final boolean hasCommonPoint(Vertice3f verticeIn)
	{
		return this.x() == verticeIn.x() || this.y() == verticeIn.y() || this.z() == verticeIn.z();
	}

	public final boolean isEqual(Vertice3f verticeIn)
	{
		return this.x() == verticeIn.x() && this.y() == verticeIn.y() && this.z() == verticeIn.z();
	}

	public final float dist(Vertice3f verticeIn)
	{
		final var	distX	= this.x() - verticeIn.x();
		final var	distY	= this.y() - verticeIn.y();
		final var	distZ	= this.z() - verticeIn.z();

		return distX * distX + distY * distY + distZ * distZ;
	}

	public final boolean distUpper(Vertice3f verticeIn, float valueIn)
	{
		return this.dist(verticeIn) > valueIn;
	}

	public final boolean distBetween(Vertice3f verticeIn, float minIn, float maxIn)
	{
		final var dist = this.dist(verticeIn);
		return dist > minIn && dist < maxIn;
	}

	public final boolean distEqual(Vertice3f verticeIn, float valueIn)
	{
		return this.dist(verticeIn) == valueIn;
	}

	public float weight()
	{
		return this.x() * this.x() + this.y() * this.y() + this.z() * this.z();
	}

	public final Vertice3f copy()
	{
		return new Vertice3f(this);
	}

	public final Vector3f vector()
	{
		return new Vector3f(this.x(), this.y(), this.z());
	}

	public Vector3f vector(Vertice3f toIn)
	{
		return new Vector3f(toIn.x() - this.x(), toIn.y() - this.y(), toIn.z() - this.z());
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(this.x(), this.y(), this.z());
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
		final var other = (Vertice3f) obj;
		return Float.floatToIntBits(this.x()) == Float.floatToIntBits(other.x())
				&& Float.floatToIntBits(this.y()) == Float.floatToIntBits(other.y())
				&& Float.floatToIntBits(this.z()) == Float.floatToIntBits(other.z());
	}

	@Override
	public String toString()
	{
		return "Vertice [x=" + this.x() + ", y=" + this.y() + ", z=" + this.z() + "]";
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

	public float z()
	{
		return this.z;
	}

	public void z(float zIn)
	{
		this.z = zIn;
	}
}
