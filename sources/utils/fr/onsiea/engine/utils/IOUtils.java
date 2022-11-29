package fr.onsiea.engine.utils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.lwjgl.BufferUtils;

public class IOUtils
{
	private IOUtils()
	{

	}

	/**
	 * Once the buffer is no longer used, it is necessary to free it MemoryUtil.free (buffer);
	 */
	private static ByteBuffer resizeBuffer(final ByteBuffer buffer, final int newCapacity)
	{
		final var newBuffer = BufferUtils.createByteBuffer(newCapacity);
		buffer.flip();
		newBuffer.put(buffer);
		return newBuffer;
	}

	/**
	 * Reads the specified resource and returns the raw data as a ByteBuffer.
	 *
	 * Once the buffer is no longer used, it is necessary to free it MemoryUtil.free (buffer);
	 *
	 * @param resource   the resource to read
	 * @param bufferSize the initial buffer size
	 *
	 * @return the resource data
	 *
	 * @throws IOException if an IO error occurs
	 */
	public static ByteBuffer ioResourceToByteBuffer(final String resource, final int bufferSize) throws IOException
	{
		ByteBuffer	buffer;

		final var	path	= Paths.get(resource);
		if (Files.isReadable(path))
		{
			try (var fc = Files.newByteChannel(path))
			{
				buffer = BufferUtils.createByteBuffer((int) fc.size() + 1);
				while (fc.read(buffer) != -1)
				{

				}
			}
		}
		else
		{
			try (var source = IOUtils.class.getClassLoader().getResourceAsStream(resource);
					var rbc = Channels.newChannel(source))
			{
				buffer = BufferUtils.createByteBuffer(bufferSize);

				while (true)
				{
					final var bytes = rbc.read(buffer);
					if (bytes == -1)
					{
						break;
					}
					if (buffer.remaining() == 0)
					{
						buffer = IOUtils.resizeBuffer(buffer, buffer.capacity() * 3 / 2); // 50%
					}
				}
			}
		}

		buffer.flip();
		return buffer;
	}
}
