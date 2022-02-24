package fr.onsiea.engine.maths.vector.timed;

import org.joml.Vector3f;
import org.joml.Vector3i;

import fr.onsiea.engine.maths.vector.TimedVector3f;

public class TimedVector3fWithChunk extends TimedVector3f
{
	private Vector3i	chunkPosition;
	private Vector3i	lastChunkPosition;
	private boolean		hasMovedIntoOtherChunk;

	public TimedVector3fWithChunk()
	{
		this.base(new Vector3f());
		this.variation(new Vector3f());
		this.last(new Vector3f());
		this.chunkPosition(new Vector3i());
		this.lastChunkPosition(new Vector3i());
	}

	public TimedVector3fWithChunk(Vector3f baseIn)
	{
		super(baseIn);
	}

	@Override
	public void add(float xIn, float yIn, float zIn)
	{
		super.add(xIn, yIn, zIn);
	}

	@Override
	public void set(float xIn, float yIn, float zIn)
	{
		super.set(xIn, yIn, zIn);
	}

	public final Vector3i chunkPosition()
	{
		return this.chunkPosition;
	}

	protected final void chunkPosition(Vector3i chunkPositionIn)
	{
		this.chunkPosition = chunkPositionIn;
	}

	public final Vector3i lastChunkPosition()
	{
		return this.lastChunkPosition;
	}

	protected final void lastChunkPosition(Vector3i lastChunkPositionIn)
	{
		this.lastChunkPosition = lastChunkPositionIn;
	}

	public final boolean hasMovedIntoOtherChunk()
	{
		return this.hasMovedIntoOtherChunk;
	}

	protected final void hasMovedIntoOtherChunk(boolean hasMovedIntoOtherChunkIn)
	{
		this.hasMovedIntoOtherChunk = hasMovedIntoOtherChunkIn;
	}
}
