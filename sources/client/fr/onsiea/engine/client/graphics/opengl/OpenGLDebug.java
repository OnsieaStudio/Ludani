package fr.onsiea.engine.client.graphics.opengl;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL45;
import org.lwjgl.opengl.GLDebugMessageCallback;
import org.lwjgl.opengl.KHRDebug;

public class OpenGLDebug extends GLDebugMessageCallback
{
	@Override
	public void free()
	{
		super.free();
	}

	@Override
	public void invoke(int sourceIn, int typeIn, int idIn, int severityIn, int lengthIn, long messageIn,
			long userParamIn)
	{
		if (severityIn == KHRDebug.GL_DEBUG_SEVERITY_NOTIFICATION)
		{
			return;
		}

		System.err.println("[" + OpenGLDebug.type(typeIn) + "-" + OpenGLDebug.severity(severityIn) + "] " + idIn + " <"
				+ OpenGLDebug.error(idIn) + "> : " + GLDebugMessageCallback.getMessage(lengthIn, messageIn)
				+ " (OpenGL Debug Message - " + OpenGLDebug.source(sourceIn) + ")");
	}

	/**
	 * Translates an OpenGL error code to a String describing the error.
	 *
	 * @param errorIn the error code, as returned by {@link GL11#glGetError
	 *                  GetError}.
	 *
	 * @return the error description
	 */
	public static String errorMessage(final int errorIn)
	{
		switch (errorIn)
		{
			case GL11.GL_NO_ERROR:
				return "No error";

			case GL11.GL_INVALID_ENUM:
				return "Enum argument out of range";

			case GL11.GL_INVALID_VALUE:
				return "Numeric argument out of range";

			case GL11.GL_INVALID_OPERATION:
				return "Operation illegal in current state";

			case GL11.GL_STACK_OVERFLOW:
				return "Command would cause a stack overflow";

			case GL11.GL_STACK_UNDERFLOW:
				return "Command would cause a stack underflow";

			case GL11.GL_OUT_OF_MEMORY:
				return "Not enough memory left to execute command";

			case GL30.GL_INVALID_FRAMEBUFFER_OPERATION:
				return "Framebuffer object is not complete";

			default:
				return "Unknown";
		}
	}

	public final static String error(int errorIn)
	{
		switch (errorIn)
		{
			case GL11.GL_NO_ERROR:
				return "No Error";

			case GL11.GL_INVALID_ENUM:
				return "Invalid Enum";

			case GL11.GL_INVALID_VALUE:
				return "Invalid Value";

			case GL11.GL_INVALID_OPERATION:
				return "Invalid Operation";

			case GL30.GL_INVALID_FRAMEBUFFER_OPERATION:
				return "Invalid Framebuffer Operation";

			case GL11.GL_OUT_OF_MEMORY:
				return "Out of Memory";

			case GL11.GL_STACK_UNDERFLOW:
				return "Stack Underflow";

			case GL11.GL_STACK_OVERFLOW:
				return "Stack Overflow";

			case GL45.GL_CONTEXT_LOST:
				return "Context Lost";

			default:
				return "Unknown Error";
		}
	}

	public final static String source(long sourceIn)
	{
		if (sourceIn == KHRDebug.GL_DEBUG_SOURCE_API)
		{
			return "API";
		}

		if (sourceIn == KHRDebug.GL_DEBUG_SOURCE_WINDOW_SYSTEM)
		{
			return "WINDOW SYSTEM";
		}

		if (sourceIn == KHRDebug.GL_DEBUG_SOURCE_SHADER_COMPILER)
		{
			return "SHADER COMPILER";
		}

		if (sourceIn == KHRDebug.GL_DEBUG_SOURCE_THIRD_PARTY)
		{
			return "THIRD PARTY";
		}

		if (sourceIn == KHRDebug.GL_DEBUG_SOURCE_APPLICATION)
		{
			return "APPLICATION";
		}

		if (sourceIn == KHRDebug.GL_DEBUG_SOURCE_OTHER)
		{
		}

		return "UNKNOWN";
	}

	public final static String type(long typeIn)
	{
		if (typeIn == KHRDebug.GL_DEBUG_TYPE_ERROR)
		{
			return "ERROR";
		}

		if (typeIn == KHRDebug.GL_DEBUG_TYPE_DEPRECATED_BEHAVIOR)
		{
			return "DEPRECATED BEHAVIOR";
		}

		if (typeIn == KHRDebug.GL_DEBUG_TYPE_UNDEFINED_BEHAVIOR)
		{
			return "UDEFINED BEHAVIOR";
		}

		if (typeIn == KHRDebug.GL_DEBUG_TYPE_PORTABILITY)
		{
			return "PORTABILITY";
		}

		if (typeIn == KHRDebug.GL_DEBUG_TYPE_PERFORMANCE)
		{
			return "PERFORMANCE";
		}

		if (typeIn == KHRDebug.GL_DEBUG_TYPE_OTHER)
		{
			return "OTHER";
		}

		if (typeIn == KHRDebug.GL_DEBUG_TYPE_MARKER)
		{
			return "MARKER";
		}

		return "UNKNOWN";
	}

	public final static String severity(long severityIn)
	{
		if (severityIn == KHRDebug.GL_DEBUG_SEVERITY_HIGH)
		{
			return "HIGH";
		}

		if (severityIn == KHRDebug.GL_DEBUG_SEVERITY_MEDIUM)
		{
			return "MEDIUM";
		}

		if (severityIn == KHRDebug.GL_DEBUG_SEVERITY_LOW)
		{
			return "LOW";
		}

		if (severityIn == KHRDebug.GL_DEBUG_SEVERITY_NOTIFICATION)
		{
			return "NOTIFICATION";
		}

		return "UNKNOWN";
	}

	public final static List<String> allErrors()
	{
		final List<String>	errors	= new ArrayList<>();
		var					error	= 0;
		while ((error = OpenGLDebug.getError()) != GL11.GL_NO_ERROR)
		{
			errors.add(OpenGLDebug.error(error));
		}

		return errors;
	}

	public final static void showAllErrors()
	{
		var error = 0;
		while ((error = OpenGLDebug.getError()) != GL11.GL_NO_ERROR)
		{
			System.err.println(OpenGLDebug.error(error));
		}
	}

	public final static int getError()
	{
		return GL11.glGetError();
	}

	public final static boolean checkGLErrorWithException()
	{
		final var error = GL11.glGetError();

		if (error != GL11.GL_NO_ERROR)
		{
			try
			{
				throw new Exception("Error: (" + error + ") " + OpenGLDebug.error(error));
			}
			catch (final Exception e)
			{
				e.printStackTrace();
			}
		}

		return false;
	}

	public final static boolean checkGLError()
	{
		final var error = GL11.glGetError();

		if (error != GL11.GL_NO_ERROR)
		{
			System.err.println("Error: (" + error + ") " + OpenGLDebug.error(error));

			return true;
		}

		return false;
	}

	public final static boolean hasGlError()
	{
		final var error = GL11.glGetError();

		if (error != GL11.GL_NO_ERROR)
		{
			return true;
		}

		return false;
	}
}
