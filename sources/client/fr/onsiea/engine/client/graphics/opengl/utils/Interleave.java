package fr.onsiea.engine.client.graphics.opengl.utils;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL33;

import fr.onsiea.engine.utils.Primitive;

public class Interleave
{
	// Without type

	public final static void attrib(final int sizeIn, final int... informationsIn)
	{
		var currentSize = 0;

		for (var i = 0; i < informationsIn.length; i += 2)
		{
			GL20.glVertexAttribPointer(informationsIn[i], informationsIn[i + 1], GL11.GL_FLOAT, false, sizeIn,
					currentSize);
			currentSize += informationsIn[i + 1] * Primitive.floatSize();
		}
	}

	public final static void attribAndDivisor(final int sizeIn, final int... informationsIn)
	{
		var currentSize = 0;

		for (var i = 0; i < informationsIn.length; i += 3)
		{
			final var attribute = informationsIn[i];

			GL20.glVertexAttribPointer(attribute, informationsIn[i + 1], GL11.GL_FLOAT, false, sizeIn, currentSize);

			GL33.glVertexAttribDivisor(attribute, informationsIn[i + 2]);
			System.out.println(sizeIn + " : " + attribute + " : " + informationsIn[i + 1] + " : "
					+ informationsIn[i + 2] + " | " + currentSize);
			currentSize += informationsIn[i + 1] * Primitive.floatSize();
		}
	}

	// With type

	public final static void typedAttrib(final int sizeIn, final int... informationsIn)
	{
		var currentSize = 0;

		for (var i = 0; i < informationsIn.length; i += 3)
		{
			final var type = informationsIn[i + 2];

			GL20.glVertexAttribPointer(informationsIn[i], informationsIn[i + 1], informationsIn[i + 2], false, sizeIn,
					currentSize);

			currentSize += informationsIn[i + 3] * OpenGLPrimitive.getOpenGLSize(type);
		}
	}

	public final static void typedAttribAndDivisor(final int sizeIn, final int... informationsIn)
	{
		var currentSize = 0;

		for (var i = 0; i < informationsIn.length; i += 4)
		{
			final var	attribute	= informationsIn[i];
			final var	type		= informationsIn[i + 2];

			GL20.glVertexAttribPointer(attribute, informationsIn[i + 1], informationsIn[i + 2], false, sizeIn,
					currentSize);

			GL33.glVertexAttribDivisor(attribute, informationsIn[i + 3]);

			currentSize += informationsIn[i + 4] * OpenGLPrimitive.getOpenGLSize(type);
		}
	}

	// With type

	public final static void typedAttribN(final int sizeIn, final int... informationsIn)
	{
		var currentSize = 0;

		for (var i = 0; i < informationsIn.length; i += 4)
		{
			final var type = informationsIn[i + 2];

			GL20.glVertexAttribPointer(informationsIn[i], informationsIn[i + 1], informationsIn[i + 2],
					(informationsIn[i + 3] == 1), sizeIn, currentSize);

			currentSize += informationsIn[i + 4] * OpenGLPrimitive.getOpenGLSize(type);
		}
	}

	public final static void typedAttribAndDivisorN(final int sizeIn, final int... informationsIn)
	{
		var currentSize = 0;

		for (var i = 0; i < informationsIn.length; i += 5)
		{
			final var	attribute	= informationsIn[i];
			final var	type		= informationsIn[i + 2];

			GL20.glVertexAttribPointer(attribute, informationsIn[i + 1], informationsIn[i + 2],
					(informationsIn[i + 3] == 1), sizeIn, currentSize);

			GL33.glVertexAttribDivisor(attribute, informationsIn[i + 4]);

			currentSize += informationsIn[i + 4] * OpenGLPrimitive.getOpenGLSize(type);
		}
	}
}
