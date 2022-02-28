package fr.onsiea.engine.client.graphics.opengl;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;

public final class OpenGLInitializer
{
	private final static Map<Thread, GLCapabilities> THREADED_CAPABILITIES = new HashMap<>();

	public static GLCapabilities initialize()
	{
		final var capabilities = GL.createCapabilities();

		if (capabilities == null)
		{
			throw new IllegalStateException("[ERROR] OpenGL loading failed.");
		}
		OpenGLInitializer.THREADED_CAPABILITIES.put(Thread.currentThread(), capabilities);

		return capabilities;
	}

	public static GLCapabilities get()
	{
		return OpenGLInitializer.THREADED_CAPABILITIES.get(Thread.currentThread());
	}

	public static GLCapabilities get(Thread threadIn)
	{
		return OpenGLInitializer.THREADED_CAPABILITIES.get(threadIn);
	}
}