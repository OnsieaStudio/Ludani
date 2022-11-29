package fr.onsiea.engine.utils.maths.shapes;

import org.joml.Vector2f;

import fr.onsiea.engine.utils.maths.CartesianRight;

public class Ray2f
{
	private Vector2f	position;
	private Vector2f	coefficient;

	public Ray2f(float xIn, float yIn, float aIn, float bIn)
	{
		this.setPosition(new Vector2f(xIn, yIn));
		this.setCoefficient(new Vector2f(aIn, bIn));
	}

	public Ray2f(Vector2f positionIn, Vector2f coefficientIn)
	{
		this.setPosition(positionIn);
		this.setCoefficient(coefficientIn);
	}

	public CartesianRight determineCartesianRight()
	{
		return CartesianRight.determineCartesianRightWithRay(this.getPosition(), this.getCoefficient());
	}

	public Vector2f getPoint(float translationIn)
	{
		return new Vector2f(this.getPosition().x + translationIn * this.getCoefficient().x,
				this.getPosition().y + translationIn * this.getCoefficient().y);
	}

	public Vector2f getPosition()
	{
		return this.position;
	}

	private void setPosition(Vector2f positionIn)
	{
		this.position = positionIn;
	}

	public Vector2f getCoefficient()
	{
		return this.coefficient;
	}

	private void setCoefficient(Vector2f coefficientIn)
	{
		this.coefficient = coefficientIn;
	}
}
