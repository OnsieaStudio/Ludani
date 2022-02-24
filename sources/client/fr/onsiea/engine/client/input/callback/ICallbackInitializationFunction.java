package fr.onsiea.engine.client.input.callback;

import org.lwjgl.system.Callback;

public interface ICallbackInitializationFunction<T extends Callback>
{
	Callback initialize(long windowHandleIn, T callbackIn);
}