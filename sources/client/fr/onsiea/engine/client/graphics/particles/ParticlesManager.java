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
package fr.onsiea.engine.client.graphics.particles;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;

import fr.onsiea.engine.client.graphics.opengl.particles.ParticleRenderer;
import fr.onsiea.engine.client.graphics.render.IRenderAPIContext;
import fr.onsiea.engine.client.graphics.texture.Texture;

/**
 * @author Seynax
 *
 */
public class ParticlesManager<T extends IParticle>
{
	private final List<T>				particles;
	private final IParticleSystem<T>	particleSystem;
	private final ParticleRenderer		particleRenderer;

	public ParticlesManager(IParticleSystem<T> particleSystemIn, int particlesCountIn, IRenderAPIContext contextIn,
			Texture<?> particlesTextureIn, int textureRowsIn, int textureColumnsIn) throws Exception
	{
		this.particles			= new ArrayList<>();

		this.particleSystem		= particleSystemIn;
		this.particleRenderer	= new ParticleRenderer(particlesCountIn, contextIn, particlesTextureIn, textureRowsIn,
				textureColumnsIn);

		this.particleSystem.initialization(this.particles, this);
	}

	public ParticlesManager<T> render()
	{
		this.particleRenderer.draw();

		return this;
	}

	public ParticlesManager<T> remove(int indexIn)
	{
		this.particles.remove(indexIn);

		this.particleRenderer.resize(this.particles.size());

		return this;
	}

	public ParticlesManager<T> add(T particleIn)
	{
		this.particles.add(particleIn);

		this.particleRenderer.resize(this.particles.size());

		return this;
	}

	public ParticlesManager<T> update(Matrix4f projViewMatrixIn) throws Exception
	{
		final var	size		= this.particles.size();

		final var	iterator	= this.particles.iterator();

		while (iterator.hasNext())
		{
			final var particle = iterator.next();

			if (this.particleSystem.updateParticle(particle, this))
			{
				iterator.remove();
			}
		}
		this.particleSystem.update(this.particles, this);

		InsertionSort.sortHighToLow(this.particles);

		if (size != this.particles.size())
		{
			this.particleRenderer.resize(this.particles.size());
		}

		for (final T particle : this.particles)
		{
			this.particleRenderer.put(particle, projViewMatrixIn);
		}
		this.particleRenderer.flip();
		this.particleRenderer.update();
		this.particleRenderer.reset();

		return this;
	}

	public void draw()
	{
		this.particleRenderer.draw();
	}
}