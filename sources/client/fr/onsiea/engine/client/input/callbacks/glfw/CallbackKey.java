package fr.onsiea.engine.client.input.callbacks.glfw;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

import fr.onsiea.engine.client.input.keyboard.Keyboard;

public class CallbackKey extends GLFWKeyCallback
{
	private Keyboard keyboard;

	public CallbackKey(Keyboard keyboardIn)
	{
		this.keyboard(keyboardIn);
	}

	@Override
	public void invoke(long windowIn, int keyIn, int scancodeIn, int actionIn, int modsIn)
	{
		if (keyIn == GLFW.GLFW_KEY_ESCAPE && actionIn == GLFW.GLFW_RELEASE)
		{
			GLFW.glfwSetWindowShouldClose(windowIn, true);
		}

		this.keyboard().key(keyIn, scancodeIn, actionIn, modsIn);
	}

	private Keyboard keyboard()
	{
		return this.keyboard;
	}

	private void keyboard(Keyboard keyboardIn)
	{
		this.keyboard = keyboardIn;
	}
}
