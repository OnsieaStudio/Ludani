package fr.onsiea.engine.client.sound;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.system.MemoryUtil;

import fr.onsiea.engine.core.entity.PlayerEntity;

public class SoundManager implements Runnable
{
	private Thread						thread;
	private Thread						stopperThread;
	private boolean						isRunning;
	private boolean						isStopped;
	private boolean						isCleaned;

	private long						device;

	private long						context;

	private SoundListener				listener;

	private List<SoundBuffer>			soundBufferList;

	private Map<String, SoundSource>	soundSourceMap;

	private Matrix4f					playerEntityMatrix;

	public int							i	= 0;

	//private SoundInputManager			soundInputManager;

	public SoundManager()
	{
		this.soundBufferList(new ArrayList<>());
		this.soundSourceMap(new HashMap<>());
		this.playerEntityMatrix(new Matrix4f());
	}

	public void start()
	{
		this.thread(new Thread(this));
		this.thread().start();
	}

	@Override
	public void run()
	{
		try
		{
			this.init();

			this.running(true);

			this.loop();

			this.stopped(true);
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
		finally
		{

		}
	}

	private boolean loop()
	{
		/**while (this.isRunning())
		{
			this.runtime();
		}**/

		return true;
	}

	private void init() throws Exception
	{
		this.device(ALC10.alcOpenDevice((ByteBuffer) null));

		if (this.device() == MemoryUtil.NULL)
		{
			throw new IllegalStateException("Failed to open the default OpenAL device.");
		}

		final var deviceCaps = ALC.createCapabilities(this.device());
		this.context(ALC10.alcCreateContext(this.device(), (IntBuffer) null));

		if (this.context() == MemoryUtil.NULL)
		{
			throw new IllegalStateException("Failed to create OpenAL context.");
		}

		ALC10.alcMakeContextCurrent(this.context());
		AL.createCapabilities(deviceCaps);

		//this.soundInputManager = new SoundInputManager();
		//this.soundInputManager.start();
	}

	//private void runtime()
	//{
	/**this.i++;

	if (this.i >= 100)
	{
		this.i = 0;
	}**/

	//this.soundInputManager.runtime();
	//}

	public void addSoundSource(final String name, final SoundSource soundSource)
	{
		this.soundSourceMap().put(name, soundSource);
	}

	public SoundSource getSoundSource(final String name)
	{
		return this.soundSourceMap().get(name);
	}

	public void playSoundSource(final String name)
	{
		final var soundSource = this.soundSourceMap().get(name);
		if (soundSource != null && !soundSource.isPlaying())
		{
			soundSource.play();
		}
	}

	public void stopAndPlaySoundSource(final String name)
	{
		final var soundSource = this.soundSourceMap().get(name);
		if (soundSource != null)
		{
			if (soundSource.isPlaying())
			{
				soundSource.stop();
			}

			soundSource.play();
		}
	}

	public void removeSoundSource(final String name)
	{
		this.soundSourceMap().remove(name);
	}

	public void addSoundBuffer(final SoundBuffer soundBuffer)
	{
		this.soundBufferList().add(soundBuffer);
	}

	public void updateListenerPosition(final PlayerEntity playerEntity)
	{
		// Update playerEntity matrix with playerEntity data
		this.playerEntityMatrix().set(playerEntity.view());

		this.listener().setPosition(playerEntity.position());
		final var at = new Vector3f();
		this.playerEntityMatrix().positiveZ(at).negate();
		final var up = new Vector3f();
		this.playerEntityMatrix().positiveY(up);
		this.listener().setOrientation(at, up);
	}

	public void setAttenuationModel(final int model)
	{
		AL10.alDistanceModel(model);
	}

	public void stop()
	{
		this.running(false);

		this.stopperThread(new Thread(() -> {
			while (!this.isStopped() || !this.isCleaned())
			{
				if (this.isStopped())
				{
					while (!this.isCleaned())
					{
						this.cleanup();
					}
				}
			}
		}));
		this.stopperThread().start();
	}

	private void cleanup()
	{
		//this.soundInputManager.cleanup();

		for (final SoundSource soundSource : this.soundSourceMap().values())
		{
			soundSource.cleanup();
		}
		this.soundSourceMap().clear();
		for (final SoundBuffer soundBuffer : this.soundBufferList())
		{
			soundBuffer.cleanup();
		}
		this.soundBufferList().clear();
		if (this.context() != MemoryUtil.NULL)
		{
			ALC10.alcDestroyContext(this.context());
		}
		if (this.device() != MemoryUtil.NULL)
		{
			ALC10.alcCloseDevice(this.device());
		}
		this.cleaned(true);
	}

	public long device()
	{
		return this.device;
	}

	public void device(final long deviceIn)
	{
		this.device = deviceIn;
	}

	public long context()
	{
		return this.context;
	}

	public void context(final long contextIn)
	{
		this.context = contextIn;
	}

	public SoundListener listener()
	{
		return this.listener;
	}

	public void listener(final SoundListener listenerIn)
	{
		this.listener = listenerIn;
	}

	private final List<SoundBuffer> soundBufferList()
	{
		return this.soundBufferList;
	}

	private final void soundBufferList(final List<SoundBuffer> soundBufferListIn)
	{
		this.soundBufferList = soundBufferListIn;
	}

	private final Map<String, SoundSource> soundSourceMap()
	{
		return this.soundSourceMap;
	}

	private final void soundSourceMap(final Map<String, SoundSource> soundSourceMapIn)
	{
		this.soundSourceMap = soundSourceMapIn;
	}

	public Matrix4f playerEntityMatrix()
	{
		return this.playerEntityMatrix;
	}

	public void playerEntityMatrix(final Matrix4f playerEntityMatrixIn)
	{
		this.playerEntityMatrix = playerEntityMatrixIn;
	}

	public final boolean isRunning()
	{
		return this.isRunning;
	}

	private final void running(final boolean isRunningIn)
	{
		this.isRunning = isRunningIn;
	}

	private final boolean isStopped()
	{
		return this.isStopped;
	}

	private final void stopped(final boolean isStoppedIn)
	{
		this.isStopped = isStoppedIn;
	}

	private final boolean isCleaned()
	{
		return this.isCleaned;
	}

	private final void cleaned(final boolean isCleanedIn)
	{
		this.isCleaned = isCleanedIn;
	}

	private final Thread thread()
	{
		return this.thread;
	}

	private final void thread(final Thread threadIn)
	{
		this.thread = threadIn;
	}

	private final Thread stopperThread()
	{
		return this.stopperThread;
	}

	private final void stopperThread(final Thread stopperThreadIn)
	{
		this.stopperThread = stopperThreadIn;
	}
}