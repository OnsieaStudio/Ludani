package fr.onsiea.engine.client.sound;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomSoundSource extends SoundSource
{
	private List<Integer>	sourceBuffersId;
	private Random			random;

	public RandomSoundSource(boolean loop, boolean relative)
	{
		super(loop, relative);
		this.sourceBuffersId(new ArrayList<>());
		this.random(new Random());
	}

	public RandomSoundSource addSourceBuffer(int sourceBufferIdIn)
	{
		this.sourceBuffersId().add(sourceBufferIdIn);

		return this;
	}

	public int getSourceBuffer(int indexIn)
	{
		return this.sourceBuffersId().get(indexIn);
	}

	public RandomSoundSource removeSourceBuffer(int indexIn)
	{
		this.sourceBuffersId().remove(indexIn);

		return this;
	}

	public int indexOfSourceBuffer(int sourceBufferIdIn)
	{
		var i = 0;
		for (final int sourceBufferId : this.sourceBuffersId())
		{
			if (sourceBufferId == sourceBufferIdIn)
			{
				return i;
			}

			i++;
		}

		return -1;
	}

	public boolean contains(int sourceBufferIdIn)
	{
		for (final int sourceBufferId : this.sourceBuffersId())
		{
			if (sourceBufferId == sourceBufferIdIn)
			{
				return true;
			}
		}

		return false;
	}

	public RandomSoundSource removeSourceBufferWithValue(int sourceBufferIdIn)
	{
		final var i = this.indexOfSourceBuffer(sourceBufferIdIn);

		if (this.isCorrectSourceBufferIndex(i))
		{
			this.sourceBuffersId().remove(i);
		}

		return this;
	}

	public boolean isCorrectSourceBufferIndex(int indexIn)
	{
		return indexIn >= 0 && indexIn < this.sourceBuffersId().size();
	}

	public RandomSoundSource setRandomBuffer()
	{
		final var i = this.random().nextInt(0, this.sourceBuffersId().size());

		this.buffer(this.sourceBuffersId().get(i));

		return this;
	}

	@Override
	public void play()
	{
		this.setRandomBuffer();

		super.play();
	}

	private List<Integer> sourceBuffersId()
	{
		return this.sourceBuffersId;
	}

	private void sourceBuffersId(List<Integer> sourceBuffersIdIn)
	{
		this.sourceBuffersId = sourceBuffersIdIn;
	}

	private Random random()
	{
		return this.random;
	}

	private void random(Random randomIn)
	{
		this.random = randomIn;
	}
}
