package fr.onsiea.engine.maths.triangle;

import java.util.Objects;

import fr.onsiea.engine.maths.vertice.Vertice2f;

public class Triangle2f
{
	public final static boolean hasDiagonalsIntersect(Triangle2f fromTriangleIn, Triangle2f toTriangleIn)
	{
		return false;
	}

	public final static boolean isOnSameAxe(Triangle2f fromTriangleIn, Triangle2f toTriangleIn)
	{
		if (toTriangleIn.first().x() == toTriangleIn.second().x()
				&& toTriangleIn.second().x() == toTriangleIn.third().x()
				&& fromTriangleIn.first().x() == toTriangleIn.first().x()
				&& fromTriangleIn.second().x() == toTriangleIn.first().x()
				&& fromTriangleIn.third().x() == toTriangleIn.first().x())
		{
			return true;
		}
		if (toTriangleIn.first().y() == toTriangleIn.second().y()
				&& toTriangleIn.second().y() == toTriangleIn.third().y()
				&& fromTriangleIn.first().y() == toTriangleIn.first().y()
				&& fromTriangleIn.second().y() == toTriangleIn.first().y()
				&& fromTriangleIn.third().y() == toTriangleIn.first().y())
		{
			return true;
		}

		return false;
	}

	private Vertice2f	first;
	private Vertice2f	second;
	private Vertice2f	third;

	public Triangle2f()
	{
	}

	public Triangle2f(Vertice2f firstIn, Vertice2f secondIn, Vertice2f thirdIn)
	{
		this.first(firstIn);
		this.second(secondIn);
		this.third(thirdIn);
	}

	public Triangle2f(Triangle2f triangleIn)
	{
		this.first(triangleIn.first());
		this.second(triangleIn.second());
		this.third(triangleIn.third());
	}

	public final Triangle2f Copy()
	{
		return new Triangle2f(this);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(this.first(), this.second(), this.third());
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
		final var other = (Triangle2f) obj;
		return Objects.equals(this.first(), other.first()) && Objects.equals(this.second(), other.second())
				&& Objects.equals(this.third(), other.third());
	}

	@Override
	public String toString()
	{
		return "Triangle [first=" + this.first() + ", second=" + this.second() + ", third=" + this.third() + "]";
	}

	public Vertice2f first()
	{
		return this.first;
	}

	public void first(Vertice2f firstIn)
	{
		this.first = firstIn;
	}

	public Vertice2f second()
	{
		return this.second;
	}

	public void second(Vertice2f secondIn)
	{
		this.second = secondIn;
	}

	public Vertice2f third()
	{
		return this.third;
	}

	public void third(Vertice2f thirdIn)
	{
		this.third = thirdIn;
	}
}
