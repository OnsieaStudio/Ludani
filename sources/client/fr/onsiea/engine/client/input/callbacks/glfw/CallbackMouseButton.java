package fr.onsiea.engine.client.input.callbacks.glfw;

import org.lwjgl.glfw.GLFWMouseButtonCallback;

import fr.onsiea.engine.client.input.cursor.Cursor;

public class CallbackMouseButton extends GLFWMouseButtonCallback
{
	private Cursor cursor;

	public CallbackMouseButton(final Cursor cursorIn)
	{
		this.cursor(cursorIn);
	}

	@Override
	public void invoke(final long windowIn, final int buttonIn, final int actionIn, final int modsIn)
	{
		this.cursor().input(buttonIn, actionIn);
	}

	private Cursor cursor()
	{
		return this.cursor;
	}

	private void cursor(final Cursor cursorIn)
	{
		this.cursor = cursorIn;
	}
}