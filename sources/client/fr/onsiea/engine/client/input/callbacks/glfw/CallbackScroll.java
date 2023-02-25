package fr.onsiea.engine.client.input.callbacks.glfw;

import org.lwjgl.glfw.GLFWScrollCallback;

import fr.onsiea.engine.client.input.callback.IResetableCallback;
import lombok.Getter;

public class CallbackScroll extends GLFWScrollCallback implements IResetableCallback
{
	private boolean			isUpdated;

	private @Getter double	lastXOffset;
	private @Getter double	lastYOffset;
	private @Getter double	yOffset;
	private @Getter double	xOffset;

	@Override
	public void invoke(final long windowIn, final double xOffsetIn, final double yOffsetIn)
	{
		this.reset();
		this.xOffset	= xOffsetIn;
		this.yOffset	= yOffsetIn;
		this.isUpdated	= true;
	}

	@Override
	public void reset()
	{
		this.lastXOffset	= 0.0D;
		this.lastYOffset	= 0.0D;
		this.xOffset		= 0.0D;
		this.yOffset		= 0.0D;
		this.isUpdated		= false;
	}

	@Override
	public boolean isUpdated()
	{
		return this.isUpdated;
	}
}
