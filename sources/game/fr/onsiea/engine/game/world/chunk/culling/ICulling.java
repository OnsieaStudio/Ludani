package fr.onsiea.engine.game.world.chunk.culling;

import org.joml.Vector3f;

public interface ICulling
{
	public boolean isCulling(Vector3f positionIn);
	public boolean isCulling(Vector3f positionIn, Vector3f sizeIn);
	public EnumCullingReason reason();
}