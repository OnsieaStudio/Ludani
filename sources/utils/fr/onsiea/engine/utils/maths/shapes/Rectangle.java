package fr.onsiea.engine.utils.maths.shapes;

import org.joml.Vector2f;

import fr.onsiea.engine.client.graphics.shapes.ShapeRectangle;

public class Rectangle implements Shape2D
{
	private Vector2f	position;
	private Vector2f	scale;

	public Rectangle(float xIn, float yIn)
	{
		this.setPosition(new Vector2f(xIn, yIn));
		this.setScale(new Vector2f(1.0f, 1.0f));
	}

	public Rectangle(float xIn, float yIn, float sxIn, float syIn)
	{
		this.setPosition(new Vector2f(xIn, yIn));
		this.setScale(new Vector2f(sxIn, syIn));
	}

	public Rectangle(Vector2f positionIn)
	{
		this.setPosition(positionIn);
	}

	public Rectangle(Vector2f positionIn, Vector2f scaleIn)
	{
		this.setPosition(positionIn);
		this.setScale(scaleIn);
	}

	public Vector2f getPosition()
	{
		return this.position;
	}

	private void setPosition(Vector2f positionIn)
	{
		this.position = positionIn;
	}

	public Vector2f getScale()
	{
		return this.scale;
	}

	private void setScale(Vector2f scaleIn)
	{
		this.scale = scaleIn;
	}

	@Override
	public Vector2f[] getVertices()
	{
		return ShapeRectangle.vertices(this);
	}

	@Override
	public Vector2f getMin()
	{
		return new Vector2f(this.getPosition().x - this.getScale().x, this.getPosition().y - this.getScale().y);
	}

	@Override
	public Vector2f getMax()
	{
		return new Vector2f(this.getPosition().x + this.getScale().x, this.getPosition().y + this.getScale().y);
	}

	@Override
	public AABB2f getBoundingBox()
	{
		return new AABB2f(this.getMin(), this.getMax());
	}

	public static boolean hasRotation(Vector2f rotationIn)
	{
		return Rectangle.hasRotation(rotationIn.x) || Rectangle.hasRotation(rotationIn.y);

	}

	public static boolean hasRotation(float angle)
	{
		return angle != 0 && angle != 90 && angle != 180 && angle != 270 && angle != 360;
	}
}
