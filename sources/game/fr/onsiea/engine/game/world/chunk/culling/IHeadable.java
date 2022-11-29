package fr.onsiea.engine.game.world.chunk.culling;

import org.joml.Vector3f;

public interface IHeadable
{
	public Vector3f position();
	public Vector3f size();
	public boolean isVisible();
	public EnumCullingReason reason();

	public void makeVisible();
	public void makeInvisible();
	public void reason(EnumCullingReason reasonIn);
}