package fr.onsiea.engine.client.graphics.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;

public final class OpenGLInitializer
{
	private static GLCapabilities	capabilities;
	private static boolean			isInitialised;

	static GLCapabilities initialisationOrGet() throws IllegalStateException
	{
		if (!OpenGLInitializer.isInitialised())
		{
			OpenGLInitializer.capabilities = GL.createCapabilities();

			if (OpenGLInitializer.capabilities == null)
			{
				throw new IllegalStateException("[ERROR] OpenGL loading failed.");
			}

			OpenGLInitializer.initialised(true);
		}

		return OpenGLInitializer.capabilities;
	}

	public static boolean isInitialised()
	{
		return OpenGLInitializer.isInitialised;
	}

	private static void initialised(boolean isInitialisedIn)
	{
		OpenGLInitializer.isInitialised = isInitialisedIn;
	}
}
