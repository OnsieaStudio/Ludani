package fr.onsiea.engine.client.sound;

import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.openal.AL10;
import org.lwjgl.stb.STBVorbis;
import org.lwjgl.stb.STBVorbisInfo;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import fr.onsiea.engine.utils.IOUtils;

public class SoundBuffer
{
	private final int	bufferId;

	private ShortBuffer	pcm		= null;

	private ByteBuffer	vorbis	= null;

	public SoundBuffer(String file) throws Exception
	{
		this.bufferId = AL10.alGenBuffers();
		try (var info = STBVorbisInfo.malloc())
		{
			final var pcm = this.readVorbis(file, 32 * 1024, info);

			// Copy to buffer
			AL10.alBufferData(this.bufferId(), info.channels() == 1 ? AL10.AL_FORMAT_MONO16 : AL10.AL_FORMAT_STEREO16,
					pcm, info.sample_rate());
		}
	}

	private ShortBuffer readVorbis(String resource, int bufferSize, STBVorbisInfo info) throws Exception
	{
		try (var stack = MemoryStack.stackPush())
		{
			this.vorbis(IOUtils.ioResourceToByteBuffer(resource, bufferSize));
			final var	error	= stack.mallocInt(1);
			final var	decoder	= STBVorbis.stb_vorbis_open_memory(this.vorbis(), error, null);
			if (decoder == MemoryUtil.NULL)
			{
				throw new RuntimeException("Failed to open Ogg Vorbis file. Error: " + error.get(0));
			}

			STBVorbis.stb_vorbis_get_info(decoder, info);

			final var	channels		= info.channels();

			final var	lengthSamples	= STBVorbis.stb_vorbis_stream_length_in_samples(decoder);

			this.pcm(MemoryUtil.memAllocShort(lengthSamples));

			this.pcm().limit(
					STBVorbis.stb_vorbis_get_samples_short_interleaved(decoder, channels, this.pcm()) * channels);
			STBVorbis.stb_vorbis_close(decoder);

			return this.pcm();
		}
	}

	public void cleanup()
	{
		AL10.alDeleteBuffers(this.bufferId());
		if (this.pcm() != null)
		{
			MemoryUtil.memFree(this.pcm());
		}
	}

	public ShortBuffer pcm()
	{
		return this.pcm;
	}

	public void pcm(ShortBuffer pcmIn)
	{
		this.pcm = pcmIn;
	}

	public ByteBuffer vorbis()
	{
		return this.vorbis;
	}

	public void vorbis(ByteBuffer vorbisIn)
	{
		this.vorbis = vorbisIn;
	}

	public int bufferId()
	{
		return this.bufferId;
	}
}