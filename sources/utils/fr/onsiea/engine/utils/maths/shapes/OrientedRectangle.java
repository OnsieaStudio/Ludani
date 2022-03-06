package fr.onsiea.engine.utils.maths.shapes;

import org.joml.Vector2f;

import fr.onsiea.engine.client.graphics.shapes.ShapeRectangle;

public class OrientedRectangle implements Shape2D
{
	private Vector2f	position;
	private Vector2f	rotation;
	private Vector2f	scale;

	public OrientedRectangle(float xIn, float yIn)
	{
		this.setPosition(new Vector2f(xIn, yIn));
		this.setRotation(new Vector2f(0.0f, 0.0f));
		this.setScale(new Vector2f(1.0f, 1.0f));
	}

	public OrientedRectangle(float xIn, float yIn, float rxIn, float ryIn)
	{
		this.setPosition(new Vector2f(xIn, yIn));
		this.setRotation(new Vector2f(rxIn, ryIn));
		this.setScale(new Vector2f(1.0f, 1.0f));
	}

	public OrientedRectangle(float xIn, float yIn, float rxIn, float ryIn, float sxIn, float syIn)
	{
		this.setPosition(new Vector2f(xIn, yIn));
		this.setRotation(new Vector2f(rxIn, ryIn));
		this.setScale(new Vector2f(sxIn, syIn));
	}

	public OrientedRectangle(Vector2f positionIn)
	{
		this.setPosition(positionIn);
	}

	public OrientedRectangle(Vector2f positionIn, Vector2f rotationIn)
	{
		this.setPosition(positionIn);
		this.setRotation(rotationIn);
	}

	public OrientedRectangle(Vector2f positionIn, Vector2f rotationIn, Vector2f scaleIn)
	{
		this.setPosition(positionIn);
		this.setRotation(rotationIn);
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

	public Vector2f getRotation()
	{
		return this.rotation;
	}

	private void setRotation(Vector2f rotationIn)
	{
		this.rotation = rotationIn;
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
		return ShapeRectangle.min(this.getPosition(), this.getScale(), this.getRotation());
	}

	@Override
	public Vector2f getMax()
	{
		return ShapeRectangle.max(this.getPosition(), this.getScale(), this.getRotation());
	}

	@Override
	public AABB2f getBoundingBox()
	{
		return ShapeRectangle.getBoundingBox(this.getPosition(), this.getScale(), this.getRotation());
	}

	public static boolean hasRotation(Vector2f rotationIn)
	{
		return OrientedRectangle.hasRotation(rotationIn.x) || OrientedRectangle.hasRotation(rotationIn.y);

	}

	public static boolean hasRotation(float angle)
	{
		return angle != 0 && angle != 90 && angle != 180 && angle != 270 && angle != 360;
	}
}
