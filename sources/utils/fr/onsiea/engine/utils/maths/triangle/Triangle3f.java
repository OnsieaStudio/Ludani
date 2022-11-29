package fr.onsiea.engine.utils.maths.triangle;

import java.util.Objects;

import org.joml.Vector3f;

public class Triangle3f
{
	/**public final static boolean hasDiagonalsIntersect(Triangle3f fromTriangleIn, Triangle3f toTriangleIn)
	{
		Vertice3f	fromA	= null;
		Vertice3f	fromB	= null;
	
		if (fromTriangleIn.a().distUpper(fromTriangleIn.b(), 1.0f))
		{
			fromA	= fromTriangleIn.a();
			fromB	= fromTriangleIn.b();
		}
		else
		{
			if (fromTriangleIn.a().distUpper(fromTriangleIn.c(), 1.0f))
			{
				fromA = fromTriangleIn.a();
			}
			else if (fromTriangleIn.b().distUpper(fromTriangleIn.c(), 1.0f))
			{
				fromA = fromTriangleIn.b();
			}
			else
			{
				return false;
			}
	
			fromB = fromTriangleIn.c();
		}
	
		Vertice3f	toA	= null;
		Vertice3f	toB	= null;
	
		if (toTriangleIn.a().distUpper(toTriangleIn.b(), 1.0f))
		{
			toA	= toTriangleIn.a();
			toB	= toTriangleIn.b();
		}
		else
		{
			if (toTriangleIn.a().distUpper(toTriangleIn.c(), 1.0f))
			{
				toA = toTriangleIn.a();
			}
			else if (toTriangleIn.b().distUpper(toTriangleIn.c(), 1.0f))
			{
				toA = toTriangleIn.b();
			}
			else
			{
				return false;
			}
	
			toB = toTriangleIn.c();
		}
	
		final var	from	= fromA.vector(fromB);
		final var	to		= toA.vector(toB);
	
		final var	A		= from.x() / to.x();
		final var	B		= from.y() / to.y();
		final var	C		= from.z() / to.z();
	
		System.out.println("");
		System.out.println("--------------------------------------------");
		System.out.println("");
		System.out.println("FROM : " + fromTriangleIn);
		System.out.println("TO : " + toTriangleIn);
		System.out.println(from);
		System.out.println(to);
		System.out.println(A + " | " + B + " | " + C);
		System.out.println("--------------------------------------------");
		System.out.println("");
		System.out.println("");
		System.out.println("");
	
		if (A == B && B == C)
		{
			return false;
		}
	
		return true;
	}**/

	public final static boolean isOnSameFace(Triangle3f fromTriangleIn, Triangle3f toTriangleIn)
	{
		if (toTriangleIn.a().x() == toTriangleIn.b().x() && toTriangleIn.b().x() == toTriangleIn.c().x()
				&& toTriangleIn.c().x() == fromTriangleIn.a().x() && fromTriangleIn.a().x() == fromTriangleIn.b().x()
				&& fromTriangleIn.b().x() == fromTriangleIn.c().x())
		{
			return true;
		}
		if (toTriangleIn.a().y() == toTriangleIn.b().y() && toTriangleIn.b().y() == toTriangleIn.c().y()
				&& toTriangleIn.c().y() == fromTriangleIn.a().y() && fromTriangleIn.a().y() == fromTriangleIn.b().y()
				&& fromTriangleIn.b().y() == fromTriangleIn.c().y())
		{
			return true;
		}
		if (toTriangleIn.a().z() == toTriangleIn.b().z() && toTriangleIn.b().z() == toTriangleIn.c().z()
				&& toTriangleIn.c().z() == fromTriangleIn.a().z() && fromTriangleIn.a().z() == fromTriangleIn.b().z()
				&& fromTriangleIn.b().z() == fromTriangleIn.c().z())
		{
			return true;
		}

		return false;
	}

	private Vector3f	a;
	private Vector3f	b;
	private Vector3f	c;

	public Triangle3f()
	{
	}

	public Triangle3f(Vector3f aIn, Vector3f bIn, Vector3f cIn)
	{
		this.a	= aIn;
		this.b	= bIn;
		this.c	= cIn;
	}

	public Triangle3f(Triangle3f triangleIn)
	{
		this.a	= triangleIn.a();
		this.b	= triangleIn.b();
		this.c	= triangleIn.c();
	}

	public Triangle3f set(Triangle3f triangleIn)
	{
		this.a	= triangleIn.a();
		this.b	= triangleIn.b();
		this.c	= triangleIn.c();

		return this;
	}

	public final Triangle3f Copy()
	{
		return new Triangle3f(this);
	}

	public boolean recurseEquals(Triangle3f fromTriangleIn)
	{
		if (fromTriangleIn.a().equals(this.a()))
		{
			if (fromTriangleIn.b().equals(this.b()))
			{
				if (fromTriangleIn.c().equals(this.c()))
				{
					return true;
				}
			}
			else if (fromTriangleIn.b().equals(this.c()) && fromTriangleIn.c().equals(this.b()))
			{
				return true;
			}
		}
		else if (fromTriangleIn.a().equals(this.b()))
		{
			if (fromTriangleIn.b().equals(this.a()))
			{
				if (fromTriangleIn.c().equals(this.c()))
				{
					return true;
				}
			}
			else if (fromTriangleIn.b().equals(this.c()) && fromTriangleIn.c().equals(this.a()))
			{
				return true;
			}
		}
		else if (fromTriangleIn.a().equals(this.c()))
		{
			if (fromTriangleIn.b().equals(this.b()))
			{
				if (fromTriangleIn.c().equals(this.a()))
				{
					return true;
				}
			}
			else if (fromTriangleIn.b().equals(this.a()) && fromTriangleIn.c().equals(this.b()))
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(this.a, this.b, this.c);
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
		final var other = (Triangle3f) obj;
		return Objects.equals(this.a, other.a) && Objects.equals(this.b, other.b) && Objects.equals(this.c, other.c);
	}

	@Override
	public String toString()
	{
		return "Triangle3f [a=" + this.a + ", b=" + this.b + ", c=" + this.c + "]";
	}

	public final Vector3f a()
	{
		return this.a;
	}

	public final Triangle3f a(Vector3f aIn)
	{
		this.a = aIn;

		return this;
	}

	public final Vector3f b()
	{
		return this.b;
	}

	public final Triangle3f b(Vector3f bIn)
	{
		this.b = bIn;

		return this;
	}

	public final Vector3f c()
	{
		return this.c;
	}

	public final Triangle3f c(Vector3f cIn)
	{
		this.c = cIn;

		return this;
	}
}
