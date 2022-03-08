package fr.onsiea.engine.client.sound;

import java.nio.ShortBuffer;

import org.joml.Vector3f;
import org.lwjgl.openal.AL10;
import org.lwjgl.stb.STBVorbis;
import org.lwjgl.stb.STBVorbisInfo;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import fr.onsiea.engine.utils.IOUtils;

public class ALUtils
{
	public static ShortBuffer readVorbis(final String resource, final int bufferSize, final STBVorbisInfo info)
			throws Exception
	{
		try (var stack = MemoryStack.stackPush())
		{
			final var	vorbis	= IOUtils.ioResourceToByteBuffer(resource, bufferSize);
			final var	error	= stack.mallocInt(1);
			final var	decoder	= STBVorbis.stb_vorbis_open_memory(vorbis, error, null);
			if (decoder == MemoryUtil.NULL)
			{
				throw new RuntimeException("Failed to open Ogg Vorbis file. Error: " + error.get(0));
			}

			STBVorbis.stb_vorbis_get_info(decoder, info);

			final var	channels		= info.channels();

			final var	lengthSamples	= STBVorbis.stb_vorbis_stream_length_in_samples(decoder);

			final var	pcm				= MemoryUtil.memAllocShort(lengthSamples);

			pcm.limit(STBVorbis.stb_vorbis_get_samples_short_interleaved(decoder, channels, pcm) * channels);
			STBVorbis.stb_vorbis_close(decoder);

			return pcm;
		}
	}

	public final static void position(final Vector3f positionIn)
	{
		AL10.alListener3f(AL10.AL_VELOCITY, positionIn.x(), positionIn.y(), positionIn.z());
	}

	public final static void velocity(final Vector3f translationVelocityIn)
	{
		AL10.alListener3f(AL10.AL_VELOCITY, translationVelocityIn.x(), translationVelocityIn.y(),
				translationVelocityIn.z());
	}

	public final static void orientation(final Vector3f at, final Vector3f up)
	{
		final var data = new float[6];

		data[0]	= at.x;
		data[1]	= at.y;
		data[2]	= at.z;
		data[3]	= up.x;
		data[4]	= up.y;
		data[5]	= up.z;

		AL10.alListenerfv(AL10.AL_ORIENTATION, data);
	}
}