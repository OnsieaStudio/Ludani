/**
* Copyright 2021 Onsiea All rights reserved.<br><br>
*
* This file is part of Onsiea Engine project. (https://github.com/Onsiea/OnsieaEngine)<br><br>
*
* Onsiea Engine is [licensed] (https://github.com/Onsiea/OnsieaEngine/blob/main/LICENSE) under the terms of the "GNU General Public Lesser License v3.0" (GPL-3.0).
* https://github.com/Onsiea/OnsieaEngine/wiki/License#license-and-copyright<br><br>
*
* Onsiea Engine is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 3.0 of the License, or
* (at your option) any later version.<br><br>
*
* Onsiea Engine is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU Lesser General Public License for more details.<br><br>
*
* You should have received a copy of the GNU Lesser General Public License
* along with Onsiea Engine.  If not, see <https://www.gnu.org/licenses/>.<br><br>
*
* Neither the name "Onsiea", "Onsiea Engine", or any derivative name or the names of its authors / contributors may be used to endorse or promote products derived from this software and even less to name another project or other work without clear and precise permissions written in advance.<br><br>
*
* @Author : Seynax (https://github.com/seynax)<br>
* @Organization : Onsiea Studio (https://github.com/Onsiea)
*/
package fr.onsiea.engine.client.graphics.particles.system;

import java.util.List;

import fr.onsiea.engine.client.graphics.particles.IParticleSystem;
import fr.onsiea.engine.client.graphics.particles.ParticleWithLifeTime;
import fr.onsiea.engine.client.graphics.particles.ParticlesManager;
import fr.onsiea.engine.core.entity.PlayerEntity;
import fr.onsiea.engine.utils.maths.MathUtils;

/**
 * @author Seynax
 *
 */
public class SimpleParticleSystem implements IParticleSystem<ParticleWithLifeTime>
{
	private final PlayerEntity	playerEntity;

	private long				last;

	public SimpleParticleSystem(final PlayerEntity playerEntityIn)
	{
		this.playerEntity	= playerEntityIn;

		this.last			= System.nanoTime();
	}

	@Override
	public IParticleSystem<ParticleWithLifeTime> initialization(final List<ParticleWithLifeTime> particlesIn,
			final ParticlesManager<ParticleWithLifeTime> particleManagerIn)
	{
		for (var i = 0; i < 10; i++)
		{
			final var particle = new ParticleWithLifeTime(this.playerEntity,
					MathUtils.randomLong(2_500_000L, 24_000_000_000L), MathUtils.randomInt(0, 1),
					MathUtils.randomInt(0, 2), MathUtils.randomInt(0, 1));

			particle.position().x		= MathUtils.randomInt(0, 40);
			particle.position().y		= MathUtils.randomInt(0, 40);
			particle.position().z		= MathUtils.randomInt(0, 40);
			particle.orientation().x	= MathUtils.randomInt(0, 360);
			particle.orientation().y	= MathUtils.randomInt(0, 360);
			particle.orientation().z	= MathUtils.randomInt(0, 360);
			particle.scale().x			= MathUtils.randomInt(1, 2) / 4.0f;
			particle.scale().y			= MathUtils.randomInt(1, 2) / 4.0f;
			particle.scale().z			= MathUtils.randomInt(1, 2) / 4.0f;

			particlesIn.add(particle);
		}

		return this;
	}

	@Override
	public boolean updateParticle(final ParticleWithLifeTime particleIn,
			final ParticlesManager<ParticleWithLifeTime> particleManagerIn)
	{
		final var actual = System.nanoTime();
		if (MathUtils.randomInt(0, 5000) == 0 || actual - particleIn.lastLifeTime() >= particleIn.lifeTime()
				|| particleIn.position().y() <= 0)
		{
			return true;
		}

		if (actual - particleIn.last > 2_500_000_0L)
		{
			particleIn.last = actual;
			particleIn.texX++;

			if (particleIn.texX() >= 4)
			{
				particleIn.texX = 0;
				particleIn.texY++;
			}

			if (particleIn.texY() >= 4)
			{
				particleIn.texX	= 0;
				particleIn.texY	= 0;
			}
		}

		particleIn.position().x		+= MathUtils.randomInt(-1, 1) / 14.0f;
		particleIn.velocity.y		-= 9.8f * 1.0f * (1.0f / 60.0f);

		particleIn.position().y		+= particleIn.velocity.y();
		particleIn.position().z		+= MathUtils.randomInt(-1, 1) / 14.0f;
		particleIn.orientation().x	+= MathUtils.randomInt(-50, 50);
		particleIn.orientation().y	+= MathUtils.randomInt(-50, 50);
		particleIn.orientation().z	+= MathUtils.randomInt(-50, 50);
		particleIn.scale().x		+= MathUtils.randomInt(-1, 1) / 2.0f;
		particleIn.scale().y		+= MathUtils.randomInt(-1, 1) / 2.0f;
		particleIn.scale().z		+= MathUtils.randomInt(-1, 1) / 2.0f;

		return false;
	}

	@Override
	public IParticleSystem<ParticleWithLifeTime> update(final List<ParticleWithLifeTime> particlesIn,
			final ParticlesManager<ParticleWithLifeTime> particleManagerIn)
	{
		if (particlesIn.size() >= 1_000)
		{
			return this;
		}

		final var actual = System.nanoTime();
		if (actual - this.last > 1_000_000L)
		{
			for (var i = 0; i < Math.min(1000 - particlesIn.size(), 10); i++)
			{
				if (MathUtils.randomInt(0, 1) > 0)
				{
					continue;
				}

				final var particle = new ParticleWithLifeTime(this.playerEntity,
						MathUtils.randomLong(2_500_000L, 24_000_000_000L), MathUtils.randomInt(0, 1),
						MathUtils.randomInt(0, 2), MathUtils.randomInt(0, 1));

				particle.position().x		= MathUtils.randomInt(0, 2);
				particle.position().y		= MathUtils.randomInt(0, 2);
				particle.position().z		= MathUtils.randomInt(0, 2);
				particle.orientation().x	= MathUtils.randomInt(0, 360);
				particle.orientation().y	= MathUtils.randomInt(0, 360);
				particle.orientation().z	= MathUtils.randomInt(0, 360);
				particle.scale().x			= MathUtils.randomInt(1, 2) / 4.0f;
				particle.scale().y			= MathUtils.randomInt(1, 2) / 4.0f;
				particle.scale().z			= MathUtils.randomInt(1, 2) / 4.0f;

				particlesIn.add(particle);
			}
			this.last = actual;
		}

		return this;
	}
}