package fr.onsiea.engine.utils.maths.shapes;

import org.joml.Vector2f;

public class Circle implements CurvedShape2D
{
	public Vector2f	position;
	public float	rayon;

	public Circle(float x, float y, float r)
	{
		this.setPosition(new Vector2f(x, y));
		this.setRayon(r);
	}

	@Override
	public Vector2f getMin()
	{
		return new Vector2f(this.getPosition().x - this.getRayon(), this.getPosition().y - this.getRayon());
	}

	@Override
	public Vector2f getMax()
	{
		return new Vector2f(this.getPosition().x + this.getRayon(), this.getPosition().y + this.getRayon());
	}

	@Override
	public AABB2f getBoundingBox()
	{
		return new AABB2f(this.getMin(), this.getMax());
	}

	private Vector2f getPosition()
	{
		return this.position;
	}

	private void setPosition(Vector2f positionIn)
	{
		this.position = positionIn;
	}

	private float getRayon()
	{
		return this.rayon;
	}

	private void setRayon(float rayonIn)
	{
		this.rayon = rayonIn;
	}
}
