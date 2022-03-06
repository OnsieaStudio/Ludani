package fr.onsiea.engine.client.sound;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALC11;
import org.lwjgl.system.MemoryUtil;

public class SoundInputManager
{
	private final static int		BYTES_PER_SAMPLE	= 2;
	private final static int		FREQUENCY			= 48000;
	private final static int		FORMAT				= AL10.AL_FORMAT_STEREO16;
	private final static int		BUFFERSIZE			= (int) (0.01D / (1.0D / (SoundInputManager.FREQUENCY * 1.0D)));
	private final static ByteBuffer	buffer				= BufferUtils
			.createByteBuffer(SoundInputManager.BUFFERSIZE * SoundInputManager.BYTES_PER_SAMPLE * 2);

	private long					device;
	private boolean					isStarted;
	private SoundSource				source;

	private Map<Integer, Integer>	buffers				= new HashMap<>();

	public SoundInputManager()
	{
		this.buffers(new HashMap<>());
		this.source(new SoundSource(false, false));
		this.source().position(new Vector3f(0.0f, 0.0f, 0.0f));

		final var	delay			= 0.5D;

		final var	bufferSize		= (int) (delay / (1 / (float) SoundInputManager.FREQUENCY));
		final var	delayBuffer		= AL10.alGenBuffers();
		final var	samplesBuffer	= BufferUtils.createByteBuffer(bufferSize * SoundInputManager.BYTES_PER_SAMPLE * 2);

		AL10.alBufferData(delayBuffer, SoundInputManager.FORMAT, samplesBuffer, SoundInputManager.FREQUENCY);
		AL10.alSourceQueueBuffers(this.source().sourceId(), delayBuffer);
		AL10.alSourcef(this.source().sourceId(), AL10.AL_REFERENCE_DISTANCE, 1f);
		AL10.alSourcef(this.source().sourceId(), AL10.AL_ROLLOFF_FACTOR, 1f);

		if (ALC10.alcIsExtensionPresent(this.device(), "ALC_EXT_CAPTURE"))
		{
			final var inputDevice = ALC11.alcCaptureOpenDevice((ByteBuffer) null, SoundInputManager.FREQUENCY,
					SoundInputManager.FORMAT, SoundInputManager.BUFFERSIZE);

			if (inputDevice == MemoryUtil.NULL)
			{
				System.err.println("No capture device found !");
			}
			else
			{
				this.device(inputDevice);
			}
		}
		else
		{
			System.err.print("Failed to detect capture extension !");
		}
	}

	public void start()
	{
		if (!this.hasDevice())
		{
			return;
		}

		ALC11.alcCaptureStart(this.device());
		this.started(true);
	}

	public static byte[] toBytes(short s)
	{
		return new byte[]
		{ (byte) (s & 0x00FF), (byte) ((s & 0xFF00) >> 8) };
	}

	public void runtime()
	{
		if (!this.started() || !this.hasDevice())
		{
			return;
		}

		final var samples = ALC10.alcGetInteger(this.device(), ALC11.ALC_CAPTURE_SAMPLES);

		if (samples >= SoundInputManager.BUFFERSIZE)
		{
			ALC11.alcCaptureSamples(this.device(), SoundInputManager.buffer, samples);

			/**final var	finalBuffer	= BufferUtils.createFloatBuffer(SoundInputManager.buffer.capacity());
			final var	duplicate	= SoundInputManager.buffer.duplicate();
			duplicate.rewind();

			for (var i = 0; i < duplicate.capacity(); i++)
			{
				final var f = duplicate.get();
				finalBuffer.put(f);
			}
			finalBuffer.flip();**/

			final var oaBuffer = AL10.alGenBuffers();
			AL10.alBufferData(oaBuffer, SoundInputManager.FORMAT, SoundInputManager.buffer,
					SoundInputManager.FREQUENCY);

			this.PushBufferToQueue(oaBuffer);
		}

		this.PopBufferToQueue();
	}

	private void PushBufferToQueue(int pBuffer)
	{
		AL10.alSourceQueueBuffers(this.source().sourceId(), pBuffer);

		final var state = AL10.alGetSourcei(this.source().sourceId(), AL10.AL_SOURCE_STATE);
		if (state != AL10.AL_PLAYING)
		{
			this.buffers().put(pBuffer, pBuffer);
			AL10.alSourcePlay(this.source().sourceId());
		}
	}

	private void PopBufferToQueue()
	{
		var bufferProcessed = AL10.alGetSourcei(this.source().sourceId(), AL10.AL_BUFFERS_PROCESSED);

		while (bufferProcessed-- > 0)
		{
			final var uiBuffer = AL10.alSourceUnqueueBuffers(this.source().sourceId());
			AL10.alDeleteBuffers(uiBuffer);
		}
	}

	public void stop()
	{
		if (!this.started() || !this.hasDevice())
		{
			return;
		}

		ALC11.alcCaptureStop(this.device());

		this.started(false);
	}

	public final boolean hasDevice()
	{
		return this.device() >= 0 && this.device() != MemoryUtil.NULL;
	}

	public void cleanup()
	{
		this.stop();
		this.PopBufferToQueue();

		for (final int buffer : this.buffers().values())
		{
			AL10.alDeleteBuffers(buffer);
		}
		this.buffers().clear();
	}

	private final SoundSource source()
	{
		return this.source;
	}

	private final void source(SoundSource sourceIn)
	{
		this.source = sourceIn;
	}

	private final Map<Integer, Integer> buffers()
	{
		return this.buffers;
	}

	private final void buffers(Map<Integer, Integer> buffersIn)
	{
		this.buffers = buffersIn;
	}

	private final long device()
	{
		return this.device;
	}

	private final void device(long deviceIn)
	{
		this.device = deviceIn;
	}

	private final boolean started()
	{
		return this.isStarted;
	}

	private final void started(boolean isStartedIn)
	{
		this.isStarted = isStartedIn;
	}
}