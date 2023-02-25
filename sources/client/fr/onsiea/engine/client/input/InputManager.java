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
import fr.onsiea.engine.client.input.callbacks.glfw.CallbackScroll;
import fr.onsiea.engine.client.input.callbacks.glfw.CharModsCallback;
import fr.onsiea.engine.client.input.callbacks.glfw.FramebufferSizeCallback;
import fr.onsiea.engine.client.input.callbacks.glfw.KeyCallback;
import fr.onsiea.engine.client.input.callbacks.glfw.WindowSizeCallback;
import lombok.AccessLevel;
import lombok.Getter;

public class InputManager
{
	private final Map<String, InitializableCallback<?>>		initializableCallbacks;
	private final long										windowHandle;

	// private InitializableCallback<CallbackMouseButton>		mouseButtonCallback;
	private InitializableCallback<CallbackCursorPosition>	cursorPositionCallback;
	// private InitializableCallback<CharModsCallback>			charModsCallback;
	// private InitializableCallback<KeyCallback>				keyCallback;
	// private InitializableCallback<FramebufferSizeCallback>	framebufferSizeCallback;
	// private InitializableCallback<WindowSizeCallback>		windowSizeCallback;
	private InitializableCallback<CallbackScroll>			scrollCallback;

	private @Getter Cursor									cursor;
	private @Getter Keyboard								keyboard;

	private InputManager(final long windowHandleIn)
	{
		this.initializableCallbacks	= new LinkedHashMap<>();

		this.windowHandle			= windowHandleIn;
	}

	// Methods

	private <T extends Callback> InitializableCallback<T> add(final T callbackIn,
			final ICallbackInitializationFunction<T> callbackInitializationFunctionIn) throws Exception
	{
		return this.add(new InitializableCallback<>(callbackIn.getClass().getName(), callbackIn,
				callbackInitializationFunctionIn));
	}

	@SuppressWarnings("unused")
	private <T extends Callback> InitializableCallback<T> add(final String nameIn, final T callbackIn,
			final ICallbackInitializationFunction<T> callbackInitializationFunctionIn) throws Exception
	{
		return this.add(new InitializableCallback<>(nameIn, callbackIn, callbackInitializationFunctionIn));
	}

	private <T extends Callback> InitializableCallback<T> add(final InitializableCallback<T> initializableCallbackIn)
			throws Exception
	{
		if (initializableCallbackIn == null)
		{
			throw new Exception("[ERREUR] Echec de l'initialisation du callback !");
		}
		if (this.initializableCallbacks.containsKey(initializableCallbackIn.name()))
		{
			throw new Exception("[ERREUR] Echec de l'initialisation du callback \"" + initializableCallbackIn.name()
					+ "\" celui-ci a d�j� �tait initialis� !");
		}

		initializableCallbackIn.initialize(this.windowHandle);
		this.initializableCallbacks.put(initializableCallbackIn.name(), initializableCallbackIn);

		return initializableCallbackIn;
	}

	private void initialization() throws Exception
	{
		this.cursor		= new Cursor(this);
		this.keyboard	= new Keyboard();

		// this.mouseButtonCallback	=
		this.add(new CallbackMouseButton(this.cursor()), GLFW::glfwSetMouseButtonCallback);
		this.cursorPositionCallback = this.add(new CallbackCursorPosition(this.cursor()),
				GLFW::glfwSetCursorPosCallback);
		GLFW.glfwSetKeyCallback(this.windowHandle, null).free();
		// this.keyCallback				=
		this.add(new KeyCallback(this.keyboard()), GLFW::glfwSetKeyCallback);
		// this.charModsCallback			=
		this.add(new CharModsCallback(this.keyboard()), GLFW::glfwSetCharModsCallback);
		// this.framebufferSizeCallback	=
		this.add(new FramebufferSizeCallback(), GLFW::glfwSetFramebufferSizeCallback);
		// this.windowSizeCallback			=
		this.add(new WindowSizeCallback(), GLFW::glfwSetWindowSizeCallback);
		this.scrollCallback = this.add(new CallbackScroll(), GLFW::glfwSetScrollCallback);
	}

	public void update()
	{
		this.cursor().updateScroll(this.scrollCallback.callback);
		this.cursor().update(this.windowHandle, this.cursorPositionCallback.callback);
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

	public int glfwGetKey(final int keyIn)
	{
		return GLFW.glfwGetKey(this.windowHandle, keyIn);
	}

	public void setCursorPos(final double xIn, final double yIn)
	{
		GLFW.glfwSetCursorPos(this.windowHandle, xIn, yIn);
	}

	public String getClipboardString()
	{

		return GLFW.glfwGetClipboardString(this.windowHandle);
	}

	public void setClipboardString(final String contentIn)
	{
		GLFW.glfwSetClipboardString(this.windowHandle, contentIn);
	}

	public void reset()
	{
		this.cursor().end();
		this.keyboard().end();

		for (final InitializableCallback<?> initializableCallback : this.initializableCallbacks.values())
		{
			final var callback = initializableCallback.callback;

			if (callback instanceof IResetableCallback)
			{
				((IResetableCallback) callback).reset();
			}
		}

		this.clear();
	}

	private void clear()
	{
		for (final InitializableCallback<?> initializableCallback : this.initializableCallbacks.values())
		{
			final var callback = initializableCallback.callback;

			if (callback instanceof IClearableCallback)
			{
				((IClearableCallback) callback).clear();
			}
		}
	}

	public void cleanup() throws Exception
	{
		for (final InitializableCallback<?> initializableCallback : this.initializableCallbacks.values())
		{
			initializableCallback.cleanup(this.windowHandle);
		}

		this.initializableCallbacks.clear();
	}

	final static class InitializableCallback<T extends Callback>
	{
		private @Getter(AccessLevel.PACKAGE) final String	name;
		private T											callback;
		private ICallbackInitializationFunction<T>			callbackInitializationFunction;

		InitializableCallback(final String nameIn, final T callbackIn,
				final ICallbackInitializationFunction<T> callbackInitializationFunctionIn)
		{
			this.name							= nameIn;
			this.callback						= callbackIn;
			this.callbackInitializationFunction	= callbackInitializationFunctionIn;
		}

		void initialize(final long windowHandleIn) throws Exception
		{
			this.callbackInitializationFunction.initialize(windowHandleIn, this.callback);
		}

		void cleanup(final long windowHandleIn) throws Exception
		{
			final var callback = this.callbackInitializationFunction.initialize(windowHandleIn, null);
			if (callback == null)
			{
				throw new Exception("[ERREUR] Echec de la lib�ration du callback \"" + this.name() + "\"");
			}
			callback.free();

			this.callbackInitializationFunction	= null;
			this.callback						= null;
		}
	}

	public final static class Builder
	{
		private final long windowHandle;

		public Builder(final long windowHandleIn)
		{
			this.windowHandle = windowHandleIn;
		}

		public InputManager build() throws Exception
		{
			if (this.windowHandle <= 0)
			{
				throw new Exception(
						"[ERREUR] Echec de la cr�ation de l'instance du gestionnaire des entr�es, l'handle de la fen�tre n'est pas sp�cifi� ou est incorrect !");
			}

			final var inputManager = new InputManager(this.windowHandle);
			inputManager.initialization();

			return inputManager;
		}
	}
}