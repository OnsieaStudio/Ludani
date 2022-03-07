package fr.onsiea.engine.client.graphics.particles;

import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PUBLIC)
public class ParticleWithLifeTime extends Particle implements IParticle
{
	private final long		last;
	private final double	lifeTime;

	public ParticleWithLifeTime(double lifeTimeIn)
	{
		this.lifeTime	= lifeTimeIn;
		this.last		= System.nanoTime();
	}
}