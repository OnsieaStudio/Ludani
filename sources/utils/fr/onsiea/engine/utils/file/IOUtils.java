package fr.onsiea.engine.utils.file;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.lwjgl.system.MemoryUtil;

public class IOUtils
{
	private IOUtils()
	{

	}

	/**
	 * Once the buffer is no longer used, it is necessary to free it MemoryUtil.free (buffer);
	 */
	private static ByteBuffer resizeBuffer(final ByteBuffer bufferIn, final int newCapacityIn)
	{
		final var newBuffer = MemoryUtil.memAlloc(newCapacityIn);
		bufferIn.flip();
		newBuffer.put(bufferIn);
		return newBuffer;
	}

	/**
	 * Once the buffer is no longer used, it is necessary to free it MemoryUtil.free (buffer);
	 *
	 * Reads the specified resource and returns the raw data as a ByteBuffer.
	 *
	 * @param resourceIn   the resource to read
	 * @param bufferSizeIn the initial buffer size
	 *
	 * @return the resource data
	 *
	 * @throws IOException if an IO error occurs
	 */
	public static ByteBuffer ioResourceToByteBuffer(final String resourceIn, final int bufferSizeIn) throws IOException
	{
		ByteBuffer	buffer;

		final var	path	= Paths.get(resourceIn);
		if (Files.isReadable(path))
		{
			try (var fc = Files.newByteChannel(path))
			{
				buffer = MemoryUtil.memAlloc((int) fc.size() + 1);
				while (fc.read(buffer) != -1)
				{

				}
			}
		}
		else
		{
			try (var source = IOUtils.class.getClassLoader().getResourceAsStream(resourceIn);
					var rbc = Channels.newChannel(source))
			{
				buffer = MemoryUtil.memAlloc(bufferSizeIn);

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
