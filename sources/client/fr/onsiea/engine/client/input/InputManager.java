package fr.onsiea.engine.client.input;

import java.util.LinkedHashMap;
import java.util.Map;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.Callback;

import fr.onsiea.engine.client.input.callback.ICallbackInitializationFunction;
import fr.onsiea.engine.client.input.callback.IClearableCallback;
import fr.onsiea.engine.client.input.callback.IResetableCallback;
import fr.onsiea.engine.client.input.callbacks.glfw.CallbackCursorPosition;
import fr.onsiea.engine.client.input.callbacks.glfw.CallbackMouseButton;
import fr.onsiea.engine.client.input.callbacks.glfw.CharModsCallback;
import fr.onsiea.engine.client.input.callbacks.glfw.FramebufferSizeCallback;
import fr.onsiea.engine.client.input.callbacks.glfw.KeyCallback;
import fr.onsiea.engine.client.input.callbacks.glfw.WindowSizeCallback;

public class InputManager
{
	private Map<String, InitializableCallback<?>>			initializableCallbacks;
	private long											windowHandle;

	private InitializableCallback<CallbackMouseButton>		mouseButtonCallback;
	private InitializableCallback<CallbackCursorPosition>	cursorPositionCallback;
	private InitializableCallback<CharModsCallback>			charModsCallback;
	private InitializableCallback<KeyCallback>				keyCallback;
	private InitializableCallback<FramebufferSizeCallback>	framebufferSizeCallback;
	private InitializableCallback<WindowSizeCallback>		windowSizeCallback;

	private Cursor											cursor;
	private Keyboard										keyboard;

	private InputManager(long windowHandleIn)
	{
		this.initializableCallbacks(new LinkedHashMap<>());

		this.windowHandle(windowHandleIn);
	}

	// Methods

	private <T extends Callback> InitializableCallback<T> add(final T callbackIn,
			final ICallbackInitializationFunction<T> callbackInitializationFunctionIn) throws Exception
	{
		return this.add(new InitializableCallback<>(callbackIn.getClass().getName(), callbackIn,
				callbackInitializationFunctionIn));
	}

	@SuppressWarnings("unused")
	private <T extends Callback> InitializableCallback<T> add(String nameIn, final T callbackIn,
			final ICallbackInitializationFunction<T> callbackInitializationFunctionIn) throws Exception
	{
		return this.add(new InitializableCallback<>(nameIn, callbackIn, callbackInitializationFunctionIn));
	}

	private <T extends Callback> InitializableCallback<T> add(InitializableCallback<T> initializableCallbackIn)
			throws Exception
	{
		if (initializableCallbackIn == null)
		{
			throw new Exception("[ERREUR] Echec de l'initialisation du callback !");
		}
		if (this.initializableCallbacks().containsKey(initializableCallbackIn.name()))
		{
			throw new Exception("[ERREUR] Echec de l'initialisation du callback \"" + initializableCallbackIn.name()
					+ "\" celui-ci a déjà était initialisé !");
		}

		initializableCallbackIn.initialize(this.windowHandle());
		this.initializableCallbacks().put(initializableCallbackIn.name(), initializableCallbackIn);

		return initializableCallbackIn;
	}

	private void initialization() throws Exception
	{
		this.cursor(new Cursor(this));
		this.keyboard(new Keyboard());

		this.mouseButtonCallback(this.add(new CallbackMouseButton(this.cursor()), GLFW::glfwSetMouseButtonCallback));
		this.cursorPositionCallback(
				this.add(new CallbackCursorPosition(this.cursor()), GLFW::glfwSetCursorPosCallback));
		GLFW.glfwSetKeyCallback(this.windowHandle(), null).free();
		this.keyCallback(this.add(new KeyCallback(this.keyboard()), GLFW::glfwSetKeyCallback));
		this.charModsCallback(this.add(new CharModsCallback(this.keyboard()), GLFW::glfwSetCharModsCallback));
		this.framebufferSizeCallback(this.add(new FramebufferSizeCallback(), GLFW::glfwSetFramebufferSizeCallback));
		this.windowSizeCallback(this.add(new WindowSizeCallback(), GLFW::glfwSetWindowSizeCallback));
	}

	public void update()
	{
		this.cursor().update(this.windowHandle(), this.cursorPositionCallback().callback());
		this.keyboard().update();
	}

	public InputManager pollEvents()
	{
		GLFW.glfwPollEvents();

		return this;
	}

	public InputManager waitEvents()
	{
		GLFW.glfwWaitEvents();

		return this;
	}

	public InputManager waitTimeoutEvents(final double timeoutIn)
	{
		GLFW.glfwWaitEventsTimeout(timeoutIn);

		return this;
	}

	public InputManager postEmptyEvent()
	{
		GLFW.glfwPostEmptyEvent();

		return this;
	}

	public int glfwGetKey(int keyIn)
	{
		return GLFW.glfwGetKey(this.windowHandle(), keyIn);
	}

	public void setCursorPos(double xIn, double yIn)
	{
		GLFW.glfwSetCursorPos(this.windowHandle(), xIn, yIn);
	}

	public String getClipboardString()
	{

		return GLFW.glfwGetClipboardString(this.windowHandle());
	}

	public void setClipboardString(String contentIn)
	{
		GLFW.glfwSetClipboardString(this.windowHandle(), contentIn);
	}

	public void reset()
	{
		this.cursor().end();
		this.keyboard().end();

		for (final InitializableCallback<?> initializableCallback : this.initializableCallbacks().values())
		{
			final var callback = initializableCallback.callback();

			if (callback instanceof IResetableCallback)
			{
				((IResetableCallback) callback).reset();
			}
		}

		this.clear();
	}

	private void clear()
	{
		for (final InitializableCallback<?> initializableCallback : this.initializableCallbacks().values())
		{
			final var callback = initializableCallback.callback();

			if (callback instanceof IClearableCallback)
			{
				((IClearableCallback) callback).clear();
			}
		}
	}

	public void cleanup() throws Exception
	{
		for (final InitializableCallback<?> initializableCallback : this.initializableCallbacks().values())
		{
			initializableCallback.cleanup(this.windowHandle());
		}

		this.initializableCallbacks().clear();
	}

	private Map<String, InitializableCallback<?>> initializableCallbacks()
	{
		return this.initializableCallbacks;
	}

	private void initializableCallbacks(Map<String, InitializableCallback<?>> initializableCallbacksIn)
	{
		this.initializableCallbacks = initializableCallbacksIn;
	}

	private long windowHandle()
	{
		return this.windowHandle;
	}

	private void windowHandle(long windowHandleIn)
	{
		this.windowHandle = windowHandleIn;
	}

	public final Cursor cursor()
	{
		return this.cursor;
	}

	private final void cursor(Cursor cursorIn)
	{
		this.cursor = cursorIn;
	}

	public final Keyboard keyboard()
	{
		return this.keyboard;
	}

	private final void keyboard(Keyboard keyboardIn)
	{
		this.keyboard = keyboardIn;
	}

	@SuppressWarnings("unused")
	private InitializableCallback<CallbackMouseButton> mouseButtonCallback()
	{
		return this.mouseButtonCallback;
	}

	private void mouseButtonCallback(InitializableCallback<CallbackMouseButton> mouseButtonCallbackIn)
	{
		this.mouseButtonCallback = mouseButtonCallbackIn;
	}

	private final InitializableCallback<CallbackCursorPosition> cursorPositionCallback()
	{
		return this.cursorPositionCallback;
	}

	private final void cursorPositionCallback(InitializableCallback<CallbackCursorPosition> cursorPositionCallbackIn)
	{
		this.cursorPositionCallback = cursorPositionCallbackIn;
	}

	@SuppressWarnings("unused")
	private final InitializableCallback<CharModsCallback> charModsCallback()
	{
		return this.charModsCallback;
	}

	private final void charModsCallback(InitializableCallback<CharModsCallback> charModsCallbackIn)
	{
		this.charModsCallback = charModsCallbackIn;
	}

	@SuppressWarnings("unused")
	private final InitializableCallback<KeyCallback> keyCallback()
	{
		return this.keyCallback;
	}

	private final void keyCallback(InitializableCallback<KeyCallback> keyCallbackIn)
	{
		this.keyCallback = keyCallbackIn;
	}

	@SuppressWarnings("unused")
	private final InitializableCallback<FramebufferSizeCallback> framebufferSizeCallback()
	{
		return this.framebufferSizeCallback;
	}

	private final void framebufferSizeCallback(InitializableCallback<FramebufferSizeCallback> framebufferSizeCallbackIn)
	{
		this.framebufferSizeCallback = framebufferSizeCallbackIn;
	}

	@SuppressWarnings("unused")
	private final InitializableCallback<WindowSizeCallback> windowSizeCallback()
	{
		return this.windowSizeCallback;
	}

	private final void windowSizeCallback(InitializableCallback<WindowSizeCallback> windowSizeCallbackIn)
	{
		this.windowSizeCallback = windowSizeCallbackIn;
	}

	final static class InitializableCallback<T extends Callback>
	{
		private String								name;
		private T									callback;
		private ICallbackInitializationFunction<T>	callbackInitializationFunction;

		InitializableCallback(String nameIn, final T callbackIn,
				final ICallbackInitializationFunction<T> callbackInitializationFunctionIn)
		{
			this.name(nameIn);
			this.callback(callbackIn);
			this.callbackInitializationFunction(callbackInitializationFunctionIn);
		}

		void initialize(final long windowHandleIn) throws Exception
		{
			this.callbackInitializationFunction().initialize(windowHandleIn, this.callback());
		}

		void cleanup(final long windowHandleIn) throws Exception
		{
			final var callback = this.callbackInitializationFunction().initialize(windowHandleIn, null);
			if (callback == null)
			{
				throw new Exception("[ERREUR] Echec de la libération du callback \"" + this.name() + "\"");
			}
			callback.free();

			this.callbackInitializationFunction(null);
			this.callback(null);
		}

		String name()
		{
			return this.name;
		}

		private void name(String nameIn)
		{
			this.name = nameIn;
		}

		private T callback()
		{
			return this.callback;
		}

		private void callback(T callbackIn)
		{
			this.callback = callbackIn;
		}

		private ICallbackInitializationFunction<T> callbackInitializationFunction()
		{
			return this.callbackInitializationFunction;
		}

		private void callbackInitializationFunction(ICallbackInitializationFunction<T> callbackInitializationFunctionIn)
		{
			this.callbackInitializationFunction = callbackInitializationFunctionIn;
		}
	}

	public final static class Builder
	{
		private long windowHandle;

		public Builder(long windowHandleIn)
		{
			this.windowHandle(windowHandleIn);
		}

		public InputManager build() throws Exception
		{
			if (this.windowHandle() <= 0)
			{
				throw new Exception(
						"[ERREUR] Echec de la création de l'instance du gestionnaire des entrées, l'handle de la fenètre n'est pas spécifié ou est incorrect !");
			}

			final var inputManager = new InputManager(this.windowHandle());
			inputManager.initialization();

			return inputManager;
		}

		private long windowHandle()
		{
			return this.windowHandle;
		}

		private void windowHandle(long windowHandleIn)
		{
			this.windowHandle = windowHandleIn;
		}
	}
}