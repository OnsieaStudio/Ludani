package fr.onsiea.engine.utils;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.CLongBuffer;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public class BufferHelper
{

	public final static FloatBuffer convertToBuffer(Matrix4f matrixIn)
	{
		// Dump the matrix into a float buffer

		try (var stack = MemoryStack.stackPush())
		{
			final var fb = stack.mallocFloat(16);

			matrixIn.get(fb);

			return fb;
		}
	}

	public static ByteBuffer byteBuffer(final byte... bytesIn)
	{
		final var outputByteBuffer = BufferUtils.createByteBuffer(bytesIn.length);
		outputByteBuffer.put(bytesIn);
		outputByteBuffer.flip();

		return outputByteBuffer;
	}

	public static CharBuffer charBuffer(final char... charsIn)
	{
		final var outputCharBuffer = BufferUtils.createCharBuffer(charsIn.length);
		outputCharBuffer.put(charsIn);
		outputCharBuffer.flip();

		return outputCharBuffer;
	}

	public static ShortBuffer shortBuffer(final short... shortsIn)
	{
		final var outputShortBuffer = BufferUtils.createShortBuffer(shortsIn.length);
		outputShortBuffer.put(shortsIn);
		outputShortBuffer.flip();

		return outputShortBuffer;
	}

	public static IntBuffer intbuffer(final int... intsIn)
	{
		final var outputIntBuffer = BufferUtils.createIntBuffer(intsIn.length);
		outputIntBuffer.put(intsIn);
		outputIntBuffer.flip();

		return outputIntBuffer;
	}

	public static FloatBuffer floatBuffer(final float... floatsIn)
	{
		final var outputFloatBuffer = BufferUtils.createFloatBuffer(floatsIn.length);
		outputFloatBuffer.put(floatsIn);
		outputFloatBuffer.flip();

		return outputFloatBuffer;
	}

	public static DoubleBuffer doubleBuffer(final double... doublesIn)
	{
		final var outputDoubleBuffer = BufferUtils.createDoubleBuffer(doublesIn.length);
		outputDoubleBuffer.put(doublesIn);
		outputDoubleBuffer.flip();

		return outputDoubleBuffer;
	}

	public static CLongBuffer clongBuffer(final long... clongsIn)
	{
		final var outputCLongBuffer = BufferUtils.createCLongBuffer(clongsIn.length);
		outputCLongBuffer.put(clongsIn);
		outputCLongBuffer.flip();

		return outputCLongBuffer;
	}

	public static LongBuffer longBuffer(final long... longsIn)
	{
		final var outputLongBuffer = BufferUtils.createLongBuffer(longsIn.length);
		outputLongBuffer.put(longsIn);
		outputLongBuffer.flip();

		return outputLongBuffer;
	}

	public static PointerBuffer pointerBuffer(final long... pointersIn)
	{
		final var outputPointerBuffer = BufferUtils.createPointerBuffer(pointersIn.length);
		outputPointerBuffer.put(pointersIn);
		outputPointerBuffer.flip();

		return outputPointerBuffer;
	}

	public final static PointerBuffer pointerBuffer(final PointerBuffer pointerBufferIn)
	{
		return MemoryUtil.memAllocPointer(pointerBufferIn.remaining()).put(pointerBufferIn).flip();
	}

	public final static PointerBuffer pointerBuffer(final PointerBuffer basePointerBufferIn,
			final long... otherPointersIn)
	{
		return MemoryUtil.memAllocPointer(basePointerBufferIn.remaining() + otherPointersIn.length)
				.put(basePointerBufferIn).put(otherPointersIn).flip();
	}

	public final static PointerBuffer pointerBuffer(final PointerBuffer basePointerBufferIn,
			final ByteBuffer otherPointersIn)
	{
		return MemoryUtil.memAllocPointer(basePointerBufferIn.remaining() + otherPointersIn.remaining())
				.put(basePointerBufferIn).put(otherPointersIn).flip();
	}

	public final static PointerBuffer pointerBuffer(final PointerBuffer basePointerBufferIn,
			final ByteBuffer... otherPointersBytesIn)
	{
		var size = basePointerBufferIn.remaining();

		for (final ByteBuffer element : otherPointersBytesIn)
		{
			size += element.remaining();
		}

		final var buffer = MemoryUtil.memAllocPointer(size);

		buffer.put(basePointerBufferIn);

		for (final ByteBuffer element : otherPointersBytesIn)
		{
			buffer.put(element);
		}

		buffer.flip();

		return buffer;
	}

	public final static PointerBuffer pointerBuffer(final PointerBuffer basePointerBufferIn, final int sizeIn,
			final ByteBuffer... otherPointersBytesIn)
	{
		final var buffer = MemoryUtil.memAllocPointer(sizeIn);

		buffer.put(basePointerBufferIn);

		for (final ByteBuffer element : otherPointersBytesIn)
		{
			buffer.put(element);
		}

		buffer.flip();

		return buffer;
	}

	public static PointerBuffer pointerBuffer(final String... requiredExtensionsIn)
	{
		final var buffer = MemoryUtil.memAllocPointer(requiredExtensionsIn.length);

		for (final String requiredExtension : requiredExtensionsIn)
		{
			buffer.put(MemoryUtil.memUTF8(requiredExtension));
		}

		buffer.flip();

		return buffer;
	}
}
