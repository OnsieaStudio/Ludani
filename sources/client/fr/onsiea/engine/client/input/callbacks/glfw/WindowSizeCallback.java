package fr.onsiea.engine.client.input.callbacks.glfw;

import org.lwjgl.glfw.GLFWWindowSizeCallback;

public class WindowSizeCallback extends GLFWWindowSizeCallback
{
	private int		width;
	private int		height;
	private boolean	hasChanged;

	public WindowSizeCallback()
	{

	}

	@Override
	public void invoke(long windowIn, int widthIn, int heightIn)
	{
		this.width(widthIn);
		this.height(heightIn);
		this.hasChanged(true);
	}

	public void reset(int widthIn, int heightIn)
	{
		this.width(widthIn);
		this.height(heightIn);
		this.hasChanged(false);
	}

	public void reset()
	{
		this.hasChanged(false);
	}

	public int width()
	{
		return this.width;
	}

	private void width(int widthIn)
	{
		this.width = widthIn;
	}

	public int height()
	{
		return this.height;
	}

	private void height(int heightIn)
	{
		this.height = heightIn;
	}

	public boolean hasChanged()
	{
		return this.hasChanged;
	}

	private void hasChanged(boolean hasChangedIn)
	{
		this.hasChanged = hasChangedIn;
	}
}
