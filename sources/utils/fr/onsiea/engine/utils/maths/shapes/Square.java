package fr.onsiea.engine.utils.maths.shapes;

import org.joml.Vector2f;

public class Square implements Shape2D
{
	private Vector2f	position;
	private float		size;

	public Square(Vector2f positionIn, float sizeIn)
	{
		this.setPosition(positionIn);
		this.setSize(sizeIn);
	}

	@Override
	public Vector2f[] getVertices()
	{
		return new Vector2f[]
		{ this.getMin(), new Vector2f(this.getMin().y, this.getMax().x), this.getMax(),
				new Vector2f(this.getMax().y, this.getMin().x) };
	}

	@Override
	public Vector2f getMin()
	{
		return new Vector2f(this.getPosition().x - this.getSize(), this.getPosition().y - this.getSize());
	}

	@Override
	public Vector2f getMax()
	{
		return new Vector2f(this.getPosition().x + this.getSize(), this.getPosition().y + this.getSize());
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

	private float getSize()
	{
		return this.size;
	}

	private void setSize(float sizeIn)
	{
		this.size = sizeIn;
	}
}