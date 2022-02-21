package fr.onsiea.engine.utils;

import org.joml.Vector3f;

public enum Face
{
	FRONT(0.0f, 0.0f, -1.0f), BACK(0.0f, 0.0f, 1.0f), UP(0.0f, 1.0f, 0.0f), DOWN(0.0f, -1.0f, 0.0f),
	RIGHT(1.0f, 0.0f, 0.0f), LEFT(-1.0f, 0.0f, 0.0f);

	private Vector3f position;

	Face(float xIn, float yIn, float zIn)
	{
		this.position(new Vector3f(xIn, yIn, zIn));
	}

	public Vector3f position()
	{
		return this.position;
	}

	public void position(Vector3f positionIn)
	{
		this.position = positionIn;
	}
}
