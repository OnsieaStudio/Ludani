package fr.onsiea.engine.client.sound;

import org.joml.Vector3f;
import org.lwjgl.openal.AL10;

public class SoundSource
{
	private int sourceId;

	public SoundSource(boolean loopIn, boolean relativeIn)
	{
		this.sourceId(AL10.alGenSources());

		if (loopIn)
		{
			AL10.alSourcei(this.sourceId(), AL10.AL_LOOPING, AL10.AL_TRUE);
		}
		if (relativeIn)
		{
			AL10.alSourcei(this.sourceId(), AL10.AL_SOURCE_RELATIVE, AL10.AL_TRUE);
		}
		AL10.alSourcef(this.sourceId(), AL10.AL_ROLLOFF_FACTOR, 1);
		AL10.alSourcef(this.sourceId(), AL10.AL_REFERENCE_DISTANCE, 1);
		AL10.alSourcef(this.sourceId(), AL10.AL_MAX_DISTANCE, 600);
	}

	public void buffer(int bufferIdIn)
	{
		this.stop();
		AL10.alSourcei(this.sourceId(), AL10.AL_BUFFER, bufferIdIn);
	}

	public void position(Vector3f positionIn)
	{
		AL10.alSource3f(this.sourceId(), AL10.AL_POSITION, positionIn.x(), positionIn.y, positionIn.z);
	}

	public void velocity(float xIn, float yIn, float zIn)
	{
		AL10.alSource3f(this.sourceId(), AL10.AL_VELOCITY, xIn, yIn, zIn);
	}

	public void velocity(Vector3f speedIn)
	{
		AL10.alSource3f(this.sourceId(), AL10.AL_VELOCITY, speedIn.x(), speedIn.y(), speedIn.z());
	}

	public void gain(float gainIn)
	{
		AL10.alSourcef(this.sourceId(), AL10.AL_GAIN, gainIn);
	}

	public void pitch(float pitchIn)
	{
		AL10.alSourcef(this.sourceId(), AL10.AL_PITCH, pitchIn);
	}

	public void property(int paramIn, float valueIn)
	{
		AL10.alSourcef(this.sourceId(), paramIn, valueIn);
	}

	public void play()
	{
		AL10.alSourcePlay(this.sourceId());
	}

	public boolean isPlaying()
	{
		return AL10.alGetSourcei(this.sourceId(), AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
	}

	public void pause()
	{
		AL10.alSourcePause(this.sourceId());
	}

	public void stop()
	{
		AL10.alSourceStop(this.sourceId());
	}

	public void cleanup()
	{
		this.stop();
		//AL10.alSourcei		(this.sourceId(), AL10.AL_BUFFER, 0);
		AL10.alDeleteSources(this.sourceId());
	}

	public int sourceId()
	{
		return this.sourceId;
	}

	private final void sourceId(int sourceIdIn)
	{
		this.sourceId = sourceIdIn;
	}
}
