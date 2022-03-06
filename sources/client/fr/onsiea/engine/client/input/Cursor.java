package fr.onsiea.engine.client.input;

import java.util.HashMap;
import java.util.Map;

import org.joml.Vector2d;
import org.lwjgl.glfw.GLFW;

import fr.onsiea.engine.client.graphics.window.IWindow;
import fr.onsiea.engine.client.input.callbacks.glfw.CallbackCursorPosition;
import fr.onsiea.engine.utils.maths.normalization.Normalizer;

public class Cursor
{
	private InputManager				inputManager;

	private double						lastX;
	private double						lastY;
	private double						x;
	private double						y;
	private double						translationX;
	private double						translationY;
	private boolean						hasMoved;
	private boolean						mustBeBlocked;

	private Map<Integer, TimedAction>	buttonsActions;
	private Vector2d					blockedPosition;

	public Cursor(InputManager inputManagerIn)
	{
		this.inputManager(inputManagerIn);
		this.blockedPosition(new Vector2d());
		this.buttonsActions(new HashMap<>());
	}

	void update(long windowHandleIn, CallbackCursorPosition cursorPositionIn)
	{
		if (this.isMustBeBlocked())
		{
			this.lastX(this.blockedPosition().x());
			this.lastY(this.blockedPosition().y());

			this.x(this.blockedPosition().x());
			this.y(this.blockedPosition().y());
		}
		else
		{
			this.lastX(this.x());
			this.lastY(this.y());

			this.x(cursorPositionIn.x());
			this.y(cursorPositionIn.y());
		}

		this.translationX(cursorPositionIn.x() - this.x());
		this.translationY(cursorPositionIn.y() - this.y());

		if (this.translationX() != 0 || this.translationY() != 0)
		{
			if (this.isMustBeBlocked())
			{
				GLFW.glfwSetCursorPos(windowHandleIn, this.blockedPosition().x(), this.blockedPosition().y());
			}

			this.hasMoved(true);
		}
	}

	public void input(int buttonIn, int actionIn)
	{
		var action = this.buttonsActions().get(buttonIn);

		if (action == null)
		{
			action = new TimedAction();
			this.buttonsActions().put(buttonIn, action);
		}

		switch (actionIn)
		{
			case GLFW.GLFW_PRESS:

				if (ActionTypes.JUST_PRESSED.equals(action.type()) || ActionTypes.PRESSED.equals(action.type())
						|| ActionTypes.PRESSED_FOR_WHILE.equals(action.type()))
				{
					return;
				}

				action.set(ActionTypes.JUST_PRESSED);

				break;

			case GLFW.GLFW_RELEASE:

				if (ActionTypes.JUST_RELEASED.equals(action.type()) || ActionTypes.RELEASED.equals(action.type())
						|| ActionTypes.RELEASED_FOR_WHILE.equals(action.type()))
				{
					return;
				}

				action.set(ActionTypes.JUST_RELEASED);

				break;

			case GLFW.GLFW_REPEAT:

				if (ActionTypes.JUST_REPEATED.equals(action.type()) || ActionTypes.REPEATED.equals(action.type())
						|| ActionTypes.REPEATED_FOR_WHILE.equals(action.type()))
				{
					return;
				}

				action.set(ActionTypes.JUST_REPEATED);

				break;
		}
	}

	public void end()
	{
		final var buttonsActionsIterator = this.buttonsActions().entrySet().iterator();

		while (buttonsActionsIterator.hasNext())
		{
			buttonsActionsIterator.next().getValue().update();
		}

		this.hasMoved(false);
		this.translationX(0.0D);
		this.translationY(0.0D);
	}

	public void blockedPosition(IWindow windowIn, double blockedPositionXIn, double blockedPositionYIn)
	{
		this.blockedPosition().x	= Normalizer.denormalizeX(blockedPositionXIn, windowIn.settings().width());
		this.blockedPosition().y	= Normalizer.denormalizeY(blockedPositionYIn, windowIn.settings().height());

		if (this.isMustBeBlocked())
		{
			this.translationX(0.0D);
			this.translationY(0.0D);

			this.x(this.blockedPosition().x());
			this.y(this.blockedPosition().y());
			this.inputManager().setCursorPos(this.blockedPosition().x(), this.blockedPosition().y());

			this.lastX(this.blockedPosition().x());
			this.lastY(this.blockedPosition().y());
		}
	}

	public void blockedPosition(double blockedPositionXIn, double blockedPositionYIn)
	{
		this.blockedPosition().x	= blockedPositionXIn;
		this.blockedPosition().y	= blockedPositionYIn;

		if (this.isMustBeBlocked())
		{
			this.translationX(0.0D);
			this.translationY(0.0D);

			this.x(this.blockedPosition().x());
			this.y(this.blockedPosition().y());

			this.inputManager().setCursorPos(this.blockedPosition().x(), this.blockedPosition().y());

			this.lastX(this.blockedPosition().x());
			this.lastY(this.blockedPosition().y());
		}
	}

	public Cursor mustBeBlocked()
	{
		this.inputManager().setCursorPos(this.blockedPosition().x(), this.blockedPosition().y());
		this.mustBeBlocked(true);

		return this;
	}

	public Cursor mustBeFree()
	{
		this.inputManager().setCursorPos(this.blockedPosition().x(), this.blockedPosition().y());
		this.mustBeBlocked(false);

		return this;
	}

	// glfwSetInputMode(IWindow, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
	// glfwSetInputMode(IWindow, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
	// glfwSetInputMode(IWindow, GLFW_CURSOR, GLFW_CURSOR_NORMAL);

	// GLFWcursor* cursor = glfwCreateStandardCursor(GLFW_HRESIZE_CURSOR);

	// glfwSetInputMode (fenêtre, GLFW_STICKY_MOUSE_BUTTONS , GLFW_TRUE );

	public boolean enableCursorRawMotion(long handleIn)
	{
		if (GLFW.glfwRawMouseMotionSupported())
		{
			GLFW.glfwSetInputMode(handleIn, GLFW.GLFW_RAW_MOUSE_MOTION, GLFW.GLFW_TRUE);

			return true;
		}

		return false;
	}

	public boolean disableCursorRawMotion(long handleIn)
	{
		if (GLFW.glfwRawMouseMotionSupported())
		{
			GLFW.glfwSetInputMode(handleIn, GLFW.GLFW_RAW_MOUSE_MOTION, GLFW.GLFW_TRUE);

			return true;
		}

		return false;
	}

	public TimedAction buttionActionOf(int buttonIn)
	{
		return this.buttonsActions().get(buttonIn);
	}

	/**
	 * unsigned char pixels[16 * 16 * 4]; memset(pixels, 0xff, sizeof(pixels));
	 *
	 * GLFWimage image; image.width = 16; image.height = 16; image.pixels = pixels;
	 *
	 * GLFWcursor* cursor = glfwCreateCursor(&image, 0, 0);
	 */
	/**public void cleanup()
	{
	// glfwDestroyCursor(cursor);
		// glfwSetCursor(IWindow, NULL);
	}**/

	private final InputManager inputManager()
	{
		return this.inputManager;
	}

	private final void inputManager(InputManager inputManagerIn)
	{
		this.inputManager = inputManagerIn;
	}

	public double lastX()
	{
		return this.lastX;
	}

	private void lastX(double lastXIn)
	{
		this.lastX = lastXIn;
	}

	public double lastY()
	{
		return this.lastY;
	}

	private void lastY(double lastYIn)
	{
		this.lastY = lastYIn;
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

	public double translationX()
	{
		return this.translationX;
	}

	private void translationX(double translationXIn)
	{
		this.translationX = translationXIn;
	}

	public double translationY()
	{
		return this.translationY;
	}

	private void translationY(double translationYIn)
	{
		this.translationY = translationYIn;
	}

	public boolean hasMoved()
	{
		return this.hasMoved;
	}

	private void hasMoved(boolean hasMovedIn)
	{
		this.hasMoved = hasMovedIn;
	}

	public boolean isMustBeBlocked()
	{
		return this.mustBeBlocked;
	}

	private void mustBeBlocked(boolean mustBeBlockedIn)
	{
		this.mustBeBlocked = mustBeBlockedIn;
	}

	private Vector2d blockedPosition()
	{
		return this.blockedPosition;
	}

	private void blockedPosition(Vector2d blockedPositionIn)
	{
		this.blockedPosition = blockedPositionIn;
	}

	private Map<Integer, TimedAction> buttonsActions()
	{
		return this.buttonsActions;
	}

	private void buttonsActions(Map<Integer, TimedAction> buttonsActionsIn)
	{
		this.buttonsActions = buttonsActionsIn;
	}
}