package fr.onsiea.engine.client.graphics.glfw.monitor;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;

import fr.onsiea.engine.client.graphics.glfw.GLFWManager.GLFWState;

public class Monitors
{
	public final static Monitors of(GLFWState stateIn) throws Exception
	{
		if (!stateIn.initialized())
		{
			throw new Exception("[ERROR] GLFW library isn't initialized !");
		}

		return new Monitors();
	}

	private Monitor[]	all;
	private Monitor		primary;
	private Monitor		windowMonitor;

	private Monitors()
	{
		this.initialization();
	}

	private void initialization()
	{
		this.primary(new Monitor(GLFW.glfwGetPrimaryMonitor()));

		final var monitorsHandle = GLFW.glfwGetMonitors();
		this.all(new Monitor[monitorsHandle.capacity()]);

		for (var i = 0; i < monitorsHandle.capacity(); i++)
		{
			final var monitorHandle = monitorsHandle.get();
			if (this.primary().is(monitorHandle))
			{
				this.all()[i] = this.primary();
			}
			else
			{
				this.all()[i] = new Monitor(monitorHandle);
			}
		}

	}

	public Monitor get(long monitorHandleIn)
	{
		if (monitorHandleIn == MemoryUtil.NULL)
		{
			return null;
		}

		for (final Monitor monitor : this.all())
		{
			if (monitor.is(monitorHandleIn))
			{
				return monitor;
			}
		}

		return null;
	}

	/**
	 * Return true if monitor is already initialized and stocked in all[]
	 * @param monitorHandleIn
	 * @return
	 */
	public boolean windowMonitor(long monitorHandleIn)
	{
		if (monitorHandleIn == MemoryUtil.NULL)
		{
			return false;
		}

		for (final Monitor monitor : this.all())
		{
			if (monitor.is(monitorHandleIn))
			{
				this.windowMonitor(monitor);

				return true;
			}
		}

		this.windowMonitor(new Monitor(monitorHandleIn));

		return false;
	}

	public Monitor[] all()
	{
		return this.all;
	}

	private void all(Monitor[] allIn)
	{
		this.all = allIn;
	}

	public Monitor primary()
	{
		return this.primary;
	}

	private void primary(Monitor primaryIn)
	{
		this.primary = primaryIn;
	}

	public Monitor windowMonitor()
	{
		return this.windowMonitor;
	}

	private void windowMonitor(Monitor windowMonitorIn)
	{
		this.windowMonitor = windowMonitorIn;
	}
}