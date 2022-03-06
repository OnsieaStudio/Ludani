package fr.onsiea.engine.utils.maths.shapes;

import org.joml.Vector2f;

public class AABB2f implements Shape2D
{
	private Vector2f	min;
	private Vector2f	max;

	public AABB2f(Vector2f minIn, Vector2f maxIn)
	{
		this.setMin(minIn);
		this.setMax(maxIn);
	}

	@Override
	public Vector2f[] getVertices()
	{
		return new Vector2f[]
		{ this.getMin(), new Vector2f(this.getMax().x, this.getMin().y), this.getMax(),
				new Vector2f(this.getMin().x, this.getMax().y) };
	}

	@Override
	public Vector2f getMin()
	{
		return this.min;
	}

	private void setMin(Vector2f minIn)
	{
		this.min = minIn;
	}

	@Override
	public Vector2f getMax()
	{
		return this.max;
	}

	public void setMax(Vector2f maxIn)
	{
		this.max = maxIn;
	}

	@Override
	public AABB2f getBoundingBox()
	{
		return this;
	}
}