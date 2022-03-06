package fr.onsiea.engine.utils.maths.shapes;

import org.joml.Vector2f;

import fr.onsiea.engine.client.graphics.shapes.ShapeRectangle;

public class OrientedSquare implements Shape2D
{
	private Vector2f	position;
	private Vector2f	rotation;
	private float		size;

	public OrientedSquare(Vector2f positionIn, Vector2f rotationIn, float sizeIn)
	{
		this.setPosition(positionIn);
		this.setRotation(rotationIn);
		this.setSize(sizeIn);
	}

	@Override
	public Vector2f[] getVertices()
	{
		return ShapeRectangle.vertices(this.getPosition().x, this.getPosition().y, this.getSize(), this.getSize(),
				this.getRotation().x, this.getRotation().y);
	}

	@Override
	public Vector2f getMin()
	{
		return ShapeRectangle.min(this.getPosition().x, this.getPosition().y, this.getSize(), this.getSize(),
				this.getRotation().x, this.getRotation().y);
	}

	@Override
	public Vector2f getMax()
	{
		return ShapeRectangle.max(this.getPosition().x, this.getPosition().y, this.getSize(), this.getSize(),
				this.getRotation().x, this.getRotation().y);
	}

	@Override
	public AABB2f getBoundingBox()
	{
		return ShapeRectangle.getBoundingBox(this.getPosition().x, this.getPosition().y, this.getSize(), this.getSize(),
				this.getRotation().x, this.getRotation().y);
	}

	private Vector2f getPosition()
	{
		return this.position;
	}

	private void setPosition(Vector2f positionIn)
	{
		this.position = positionIn;
	}

	private Vector2f getRotation()
	{
		return this.rotation;
	}

	private void setRotation(Vector2f rotationIn)
	{
		this.rotation = rotationIn;
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