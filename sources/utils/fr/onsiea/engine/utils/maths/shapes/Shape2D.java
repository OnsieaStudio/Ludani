package fr.onsiea.engine.utils.maths.shapes;

import org.joml.Vector2f;

public interface Shape2D
{
	Vector2f[] getVertices();

	Vector2f getMin();

	Vector2f getMax();

	AABB2f getBoundingBox();
}
