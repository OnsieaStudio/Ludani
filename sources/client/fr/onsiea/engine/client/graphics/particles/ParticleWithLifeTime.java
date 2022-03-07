package fr.onsiea.engine.client.graphics.particles;

import org.joml.Vector3f;

import fr.onsiea.engine.core.entity.Camera;
import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PUBLIC)
public class ParticleWithLifeTime extends Particle implements IParticle
{
	public long				last;
	public long				lastLifeTime;
	private final double	lifeTime;

	public final Vector3f	velocity;

	public ParticleWithLifeTime(Camera cameraIn, double lifeTimeIn, float velocityXIn, float velocityYIn,
			float velocityZIn)
	{
		super(cameraIn);

		this.lifeTime		= lifeTimeIn;
		this.last			= System.nanoTime();
		this.lastLifeTime	= System.nanoTime();

		this.velocity		= new Vector3f(velocityXIn, velocityYIn, velocityZIn);
	}
}