package fr.onsiea.engine.client.input.callbacks.glfw;

import org.lwjgl.glfw.GLFWCursorPosCallback;

import fr.onsiea.engine.client.input.cursor.Cursor;

public class CallbackCursorPosition extends GLFWCursorPosCallback
{
	private Cursor	cursor;
	private double	x;
	private double	y;

	public CallbackCursorPosition(Cursor cursorIn)
	{
		this.cursor(cursorIn);
	}

	@Override
	public void invoke(long windowIn, double xposIn, double yposIn)
	{
		this.x(xposIn);
		this.y(yposIn);
	}

	@SuppressWarnings("unused")
	private Cursor cursor()
	{
		return this.cursor;
	}

	private void cursor(Cursor cursorIn)
	{
		this.cursor = cursorIn;
	}

	public double x()
	{
		return this.x;
	}

	private void x(double xIn)
	{
		this.x = xIn;
	}

	public double y()
	{
		return this.y;
	}

	private void y(double yIn)
	{
		this.y = yIn;
	}
}