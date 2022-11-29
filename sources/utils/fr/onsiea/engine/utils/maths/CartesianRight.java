package fr.onsiea.engine.utils.maths;

import org.joml.Vector2f;

public class CartesianRight
{
	public float a, b, c;

	public CartesianRight(float aIn, float bIn)
	{
		this.a(aIn);
		this.b(bIn);
		this.c(0);
	}

	public CartesianRight(float aIn, float bIn, float cIn)
	{
		this.a(aIn);
		this.b(bIn);
		this.c(cIn);
	}

	public CartesianRight(CartesianRight determineCartesianRight)
	{
		this.a(determineCartesianRight.a());
		this.b(determineCartesianRight.b());
		this.c(determineCartesianRight.c());
	}

	public CartesianRight determineOrthogonal()
	{
		return new CartesianRight(-this.a(), this.b(), this.c());
	}

	public void add(float valueIn)
	{
		this.c(this.c() + valueIn);
	}

	@Override
	public String toString()
	{
		return "CartesianRight [a=" + this.a() + ", b=" + this.b() + ", c=" + this.c() + "]";
	}

	public float a()
	{
		return this.a;
	}

	private void a(float aIn)
	{
		this.a = aIn;
	}

	public float b()
	{
		return this.b;
	}

	private void b(float bIn)
	{
		this.b = bIn;
	}

	public float c()
	{
		return this.c;
	}

	public void c(float cIn)
	{
		this.c = cIn;
	}

	public static CartesianRight determineCartesianRight(float aIn, float bIn, float cIn, float xIn, float y0In)
	{
		return new CartesianRight(aIn, bIn, Equation.determineC(aIn, xIn, bIn, y0In));
	}

	public static CartesianRight determineCartesianRightWithRay(Vector2f positionIn, Vector2f rotationIn)
	{
		return CartesianRight.determineCartesianRight(
				new Vector2f(positionIn.x - rotationIn.x, positionIn.y - rotationIn.y),
				new Vector2f(positionIn.x + rotationIn.x, positionIn.y + rotationIn.y));
	}

	/**blic static CartesianRight determineCartesianRightWithRay(Vector2f positionIn, Vector2f rotationIn)
	{
		return determineCartesianRight(new Vector2f(positionIn.x - 1 * rotationIn.x, positionIn.y - 1 * rotationIn.y), new Vector2f(positionIn.x + 1 * rotationIn.x, positionIn.y + 1 * rotationIn.y));
	}**/

	public static CartesianRight determineCartesianRight(Vector2f vec0In, Vector2f vec1In)
	{
		if (vec0In.x < vec1In.x)
		{
			return CartesianRight.cartesianRight(vec0In, vec1In);
		}
		return CartesianRight.cartesianRight(vec1In, vec0In);
	}

	public static CartesianRight cartesianRight(Vector2f aIn, Vector2f bIn)
	{
		final var	ab	= new Vector2f(bIn.x - aIn.x, bIn.y - aIn.y);
		final var	a	= ab.y;
		final var	b	= -ab.x;
		final var	c	= Equation.determineC(a, aIn.x, b, aIn.y);
		final var	eq	= Equation.reduct(a, b, c);
		return new CartesianRight(eq.x, eq.y, eq.z);
	}
}
