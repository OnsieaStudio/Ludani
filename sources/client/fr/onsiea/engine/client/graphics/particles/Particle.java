package fr.onsiea.engine.client.graphics.particles;

import org.joml.Vector3f;

import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PUBLIC)
public class Particle implements IParticle
{
	private final Vector3f	position;
	private final Vector3f	orientation;
	private final Vector3f	scale;

	public Particle()
	{
		this.position		= new Vector3f();
		this.orientation	= new Vector3f();
		this.scale			= new Vector3f(1.0f);
	}
}