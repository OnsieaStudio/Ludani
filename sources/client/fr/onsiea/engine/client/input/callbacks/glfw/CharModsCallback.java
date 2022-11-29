package fr.onsiea.engine.client.input.callbacks.glfw;

import org.lwjgl.glfw.GLFWCharModsCallback;

import fr.onsiea.engine.client.input.Keyboard;

public class CharModsCallback extends GLFWCharModsCallback
{
	private Keyboard keyboard;

	public CharModsCallback(Keyboard keyboardIn)
	{
		this.keyboard(keyboardIn);
	}

	@Override
	public void invoke(long windowIn, int codepointIn, int modsIn)
	{
		this.keyboard().character(codepointIn, modsIn);
	}

	public Keyboard keyboard()
	{
		return this.keyboard;
	}

	public void keyboard(Keyboard keyboardIn)
	{
		this.keyboard = keyboardIn;
	}
}
