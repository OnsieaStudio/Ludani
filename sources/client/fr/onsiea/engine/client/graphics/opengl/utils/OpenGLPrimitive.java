package fr.onsiea.engine.client.graphics.opengl.utils;

import org.lwjgl.opengl.GL11;

import fr.onsiea.engine.utils.Primitive;

public class OpenGLPrimitive
{
	public final static int bytesSizeOf(final int typeIn)
	{
		switch (typeIn)
		{
			case GL11.GL_BYTE:
				return Primitive.byteSize();

			case GL11.GL_SHORT:
				return Primitive.shortSize();

			case GL11.GL_INT:
				return Primitive.intSize();

			case GL11.GL_FLOAT:
				return Primitive.floatSize();

			case GL11.GL_UNSIGNED_BYTE:
				return Primitive.byteSize();

			case GL11.GL_UNSIGNED_SHORT:
				return Primitive.shortSize();

			case GL11.GL_UNSIGNED_INT:
				return Primitive.floatSize();
		}

		return -1;
	}

	public final static float min(final int glTypeIn)
	{
		switch (glTypeIn)
		{
			case GL11.GL_BYTE:
				return Byte.MIN_VALUE;

			case GL11.GL_SHORT:
				return Short.MIN_VALUE;

			case GL11.GL_INT:
				return Integer.MIN_VALUE;

			case GL11.GL_FLOAT:
				return Float.MIN_VALUE;

			case GL11.GL_UNSIGNED_BYTE:
				return 0;

			case GL11.GL_UNSIGNED_SHORT:
				return 0;

			case GL11.GL_UNSIGNED_INT:
				return 0;
		}

		return -1;
	}

	public final static float max(final int glTypeIn)
	{
		switch (glTypeIn)
		{
			case GL11.GL_BYTE:
				return Byte.MAX_VALUE;

			case GL11.GL_SHORT:
				return Short.MAX_VALUE;

			case GL11.GL_INT:
				return Integer.MAX_VALUE;

			case GL11.GL_FLOAT:
				return Float.MAX_VALUE;

			case GL11.GL_UNSIGNED_BYTE:
				return Primitive.unsignedByteMax();

			case GL11.GL_UNSIGNED_SHORT:
				return Primitive.unsignedShortMax();

			case GL11.GL_UNSIGNED_INT:
				return Primitive.unsignedIntMax();
		}

		return -1;
	}

}