package fr.onsiea.engine.client.graphics.glfw.monitor;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFW;

public class Monitor
{
	private long		handle;
	private long		userPointer;
	private String		name;
	private Vector2f	contentScale;
	private Vector2i	size;
	private Vector2i	position;
	private WorkArea	workArea;

	Monitor(long handleIn)
	{
		this.handle(handleIn);

		this.initialization();
	}

	private void initialization()
	{
		this.name(GLFW.glfwGetMonitorName(this.handle()));
		this.userPointer(GLFW.glfwGetMonitorUserPointer(this.handle()));
		this.contentScale(this.inferContentScale());
		this.size(this.inferScale());
		this.position(this.inferPosition());
		this.workAera(this.inferWorkArea());
	}

	private Vector2f inferContentScale()
	{
		final var	width	= new float[1];
		final var	height	= new float[1];
		GLFW.glfwGetMonitorContentScale(this.handle(), width, height);

		return new Vector2f(width[0], height[0]);
	}

	private Vector2i inferScale()
	{
		final var	width	= new int[1];
		final var	height	= new int[1];
		GLFW.glfwGetMonitorPhysicalSize(this.handle(), width, height);

		return new Vector2i(width[0], height[0]);
	}

	private Vector2i inferPosition()
	{
		final var	width	= new int[1];
		final var	height	= new int[1];
		GLFW.glfwGetMonitorPos(this.handle(), width, height);

		return new Vector2i(width[0], height[0]);
	}

	private WorkArea inferWorkArea()
	{
		final var	width	= new int[1];
		final var	height	= new int[1];
		final var	x		= new int[1];
		final var	y		= new int[1];
		GLFW.glfwGetMonitorWorkarea(this.handle(), width, height, x, y);

		return new WorkArea(width[0], height[0], x[0], y[0]);
	}

	public boolean is(long handleIn)
	{
		return handleIn == this.handle();
	}

	public float contentWidth()
	{
		return this.contentScale().x();
	}

	public float contentHeight()
	{
		return this.contentScale().y();
	}

	public int width()
	{
		return this.size().x();
	}

	public int height()
	{
		return this.size().y();
	}

	public int x()
	{
		return this.position().x();
	}

	public int y()
	{
		return this.position().y();
	}

	private long handle()
	{
		return this.handle;
	}

	private void handle(long handleIn)
	{
		this.handle = handleIn;
	}

	@SuppressWarnings("unused")
	private long userPointer()
	{
		return this.userPointer;
	}

	private void userPointer(long userPointerIn)
	{
		this.userPointer = userPointerIn;
	}

	public String name()
	{
		return this.name;
	}

	private void name(String nameIn)
	{
		this.name = nameIn;
	}

	private Vector2f contentScale()
	{
		return this.contentScale;
	}

	private void contentScale(Vector2f contentScaleIn)
	{
		this.contentScale = contentScaleIn;
	}

	private Vector2i size()
	{
		return this.size;
	}

	private void size(Vector2i sizeIn)
	{
		this.size = sizeIn;
	}

	private Vector2i position()
	{
		return this.position;
	}

	private void position(Vector2i positionIn)
	{
		this.position = positionIn;
	}

	public WorkArea workAera()
	{
		return this.workArea;
	}

	private void workAera(WorkArea workAreaIn)
	{
		this.workArea = workAreaIn;
	}

	public final static class WorkArea
	{
		private int	x;
		private int	y;
		private int	width;
		private int	height;

		WorkArea(int xIn, int yIn, int widthIn, int heightIn)
		{
			this.x(xIn);
			this.y(yIn);
			this.width(widthIn);
			this.height(heightIn);
		}

		public int x()
		{
			return this.x;
		}

		private void x(int xIn)
		{
			this.x = xIn;
		}

		public int y()
		{
			return this.y;
		}

		private void y(int yIn)
		{
			this.y = yIn;
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
	}
}
