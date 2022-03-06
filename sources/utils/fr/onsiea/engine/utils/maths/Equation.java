package fr.onsiea.engine.utils.maths;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

public class Equation
{
	private List<Float> parameters;

	public Equation(float... parametersIn)
	{
		this.parameters(new ArrayList<>());
		for (final float parameter : parametersIn)
		{
			if (parameter == 0)
			{
				continue;
			}
			this.parameters().add(parameter);
		}
	}

	public float determine(float... valuesIn)
	{
		var	output	= 0F;
		var	i		= 0;
		for (final float parameter : this.parameters())
		{
			if (valuesIn.length <= i)
			{
				System.err.println("Il manque des valeurs, le calcule de l'inconnue sera faut");
				break;
			}
			output += parameter * valuesIn[i];
			i++;
		}
		return output;
	}

	public float determineC(float value0In, float value1In)
	{
		return Equation.opposite(this.determine(value0In, value1In));
	}

	public float determineX(float... valuesIn)
	{
		var	value	= 0F;
		var	i		= 0;
		for (final float parameter : this.parameters())
		{
			if (i == this.parameters().size() - 1)
			{
				break;
			}
			value += parameter * valuesIn[i];
			i++;
		}
		return Equation.determineX(value, this.parameters().get(this.parameters().size()));
	}

	private final List<Float> parameters()
	{
		return this.parameters;
	}

	private final void parameters(List<Float> parametersIn)
	{
		this.parameters = parametersIn;
	}

	public static Vector3f reduct(float aIn, float bIn, float cIn)
	{
		var min = Float.POSITIVE_INFINITY;

		if (aIn < min)
		{
			min = aIn;
		}
		if (bIn < min)
		{
			min = bIn;
		}
		if (cIn < min)
		{
			min = cIn;
		}
		var i = (int) Math.abs(min);

		for (; true; i--)
		{
			if (i <= 0)
			{
				break;
			}
			final var	a	= aIn / i;
			final var	b	= bIn / i;
			final var	c	= cIn / i;
			if ((int) a - a != 0 || (int) b - b != 0 || (int) c - c != 0)
			{
				continue;
			}
			return new Vector3f(a, b, c);
		}
		return new Vector3f(aIn, bIn, cIn);
	}

	public static float determine(float aIn, float value0In, float bIn, float value1In)
	{
		return aIn * value0In + bIn * value1In;
	}

	public static float determineC(float aIn, float value0In, float bIn, float value1In)
	{
		return Equation.opposite(Equation.determine(aIn, value0In, bIn, value1In));
	}

	public static float determineX(float aIn, float valueIn, float bIn)
	{
		return Equation.determineX(aIn * valueIn, bIn);
	}

	public static float determineX(float valueIn, float bIn)
	{
		return Equation.opposite(valueIn) / bIn;
	}

	public static float determineY(float aIn, float valueIn, float bIn, float cIn)
	{
		return Equation.determineX(aIn * valueIn + cIn, bIn);
	}

	/**public static Equation determineEquation(float leftAIn, float leftBIn, float leftCIn, float rightAIn, float rightBIn, float rightCIn)
	{
		return new Equation(leftAIn + rightAIn, leftBIn + rightBIn, leftCIn + rightCIn);
	}**/

	public static Equation determineEquation(float leftAIn, float leftBIn, float leftCIn, float rightAIn,
			float rightBIn, float rightCIn)
	{
		float		leftA	= leftAIn, leftB = leftBIn, leftC = leftCIn;
		final float	rightA	= rightAIn, rightB = rightBIn, rightC = rightCIn;

		leftA	+= Equation.opposite(rightA);
		leftB	+= Equation.opposite(rightB);
		leftC	+= Equation.opposite(rightC);
		return new Equation(leftA, leftB, leftC);
	}

	public static float determineXWithEquation(float leftAIn, float leftBIn, float leftCIn, float rightAIn,
			float rightBIn, float rightCIn)
	{
		float		leftA	= leftAIn, leftB = leftBIn, leftC = leftCIn;
		final float	rightA	= rightAIn, rightB = rightBIn, rightC = rightCIn;

		leftA	+= Equation.opposite(rightA);
		leftB	+= Equation.opposite(rightB);
		leftC	+= Equation.opposite(rightC);
		if (leftA != 0)
		{
			return Equation.opposite(leftC) / leftA;
		}
		if (leftB != 0)
		{
			return Equation.opposite(leftC) / leftB;
		}
		return 0;
	}

	public static float opposite(float valueIn)
	{
		if (valueIn <= 0)
		{
			return Math.abs(valueIn);
		}
		return -valueIn;
	}
}