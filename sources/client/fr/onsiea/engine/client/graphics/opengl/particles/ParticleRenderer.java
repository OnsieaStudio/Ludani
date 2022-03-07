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
package fr.onsiea.engine.client.graphics.opengl.particles;

import java.nio.FloatBuffer;
import java.util.List;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;

import fr.onsiea.engine.client.graphics.opengl.GLMeshManager;
import fr.onsiea.engine.client.graphics.opengl.mesh.GLMesh;
import fr.onsiea.engine.client.graphics.opengl.vbo.BaseVbo;
import fr.onsiea.engine.client.graphics.opengl.vbo.Vbo;
import fr.onsiea.engine.client.graphics.particles.IParticle;
import fr.onsiea.engine.client.graphics.shapes.ShapeRectangle;
import fr.onsiea.engine.utils.Pair;
import fr.onsiea.engine.utils.maths.transformations.Transformations3f;
import lombok.AccessLevel;
import lombok.Getter;

/**
 * @author Seynax
 *
 */
public class ParticleRenderer
{
	private final static Matrix4f	TRANSFORMATIONS				= new Matrix4f().identity();
	private final static Matrix4f	PROJ_VIEW_TRANSFORMATIONS	= new Matrix4f().identity();

	public final static void put(IParticle particleIn, FloatBuffer instancesIn, Matrix4f projViewIn)
	{
		Transformations3f.transformations(particleIn.position().x(), particleIn.position().y(),
				particleIn.position().z(), particleIn.orientation().x(), particleIn.orientation().y(),
				particleIn.orientation().z(), particleIn.scale().x(), particleIn.scale().y(), particleIn.scale().z(),
				ParticleRenderer.TRANSFORMATIONS);
		/**ParticleRenderer.TRANSFORMATIONS.identity().translate(particleIn.position());
		ParticleRenderer.TRANSFORMATIONS.rotateX((float) Math.toRadians(particleIn.orientation().x()))
				.rotateY((float) Math.toRadians(particleIn.orientation().y()))
				.rotateZ((float) Math.toRadians(particleIn.orientation().z()));**/
		ParticleRenderer.TRANSFORMATIONS.m00(projViewIn.m00());
		ParticleRenderer.TRANSFORMATIONS.m01(projViewIn.m10());
		ParticleRenderer.TRANSFORMATIONS.m02(projViewIn.m20());
		ParticleRenderer.TRANSFORMATIONS.m10(projViewIn.m01());
		ParticleRenderer.TRANSFORMATIONS.m11(projViewIn.m11());
		ParticleRenderer.TRANSFORMATIONS.m12(projViewIn.m21());
		ParticleRenderer.TRANSFORMATIONS.m20(projViewIn.m02());
		ParticleRenderer.TRANSFORMATIONS.m21(projViewIn.m12());
		ParticleRenderer.TRANSFORMATIONS.m22(projViewIn.m22());

		ParticleRenderer.PROJ_VIEW_TRANSFORMATIONS.set(projViewIn);
		ParticleRenderer.PROJ_VIEW_TRANSFORMATIONS.mul(ParticleRenderer.TRANSFORMATIONS);

		// var modelMatrix = new Matrix4f().identity().translate(particle.position());

		//modelMatrix.rotateX(rotation);
		//modelMatrix.rotateY(rotation);
		//modelMatrix.rotateZ((float) Math.toRadians(rotation));

		instancesIn.put(ParticleRenderer.PROJ_VIEW_TRANSFORMATIONS.m00());
		instancesIn.put(ParticleRenderer.PROJ_VIEW_TRANSFORMATIONS.m01());
		instancesIn.put(ParticleRenderer.PROJ_VIEW_TRANSFORMATIONS.m02());
		instancesIn.put(ParticleRenderer.PROJ_VIEW_TRANSFORMATIONS.m03());
		instancesIn.put(ParticleRenderer.PROJ_VIEW_TRANSFORMATIONS.m10());
		instancesIn.put(ParticleRenderer.PROJ_VIEW_TRANSFORMATIONS.m11());
		instancesIn.put(ParticleRenderer.PROJ_VIEW_TRANSFORMATIONS.m12());
		instancesIn.put(ParticleRenderer.PROJ_VIEW_TRANSFORMATIONS.m13());
		instancesIn.put(ParticleRenderer.PROJ_VIEW_TRANSFORMATIONS.m20());
		instancesIn.put(ParticleRenderer.PROJ_VIEW_TRANSFORMATIONS.m21());
		instancesIn.put(ParticleRenderer.PROJ_VIEW_TRANSFORMATIONS.m22());
		instancesIn.put(ParticleRenderer.PROJ_VIEW_TRANSFORMATIONS.m23());
		instancesIn.put(ParticleRenderer.PROJ_VIEW_TRANSFORMATIONS.m30());
		instancesIn.put(ParticleRenderer.PROJ_VIEW_TRANSFORMATIONS.m31());
		instancesIn.put(ParticleRenderer.PROJ_VIEW_TRANSFORMATIONS.m32());
		instancesIn.put(ParticleRenderer.PROJ_VIEW_TRANSFORMATIONS.m33());
		instancesIn.put(particleIn.texX());
		instancesIn.put(particleIn.texY());
	}

	public final static Pair<GLMesh, BaseVbo> make(GLMeshManager meshManagerIn, List<IParticle> particlesIn,
			Matrix4f projViewIn, FloatBuffer instancesIn) throws Exception
	{
		final var builder = meshManagerIn.meshBuilderWithVao(5).vbo(GL15.GL_STREAM_DRAW, ShapeRectangle.positions, 2)
				.ibo(GL15.GL_STREAM_DRAW, ShapeRectangle.indices).newInstancesVboAndBind()
				.multipleVertexAttribPointerAndDivisor(4, 4)
				.createVertexAttribPointerAndDivisor(2)/**.data(4L * 4L * 2L * particlesIn.size())**/
				.data(instancesIn).primCount(particlesIn.size());

		return new Pair<>(builder.build(), builder.instancesVbo());
	}

	private FloatBuffer									instances;
	private @Getter(AccessLevel.PUBLIC) final GLMesh	mesh;
	private final Vbo									vbo;
	private boolean										somethingToShow;

	public ParticleRenderer(int particlesIn, GLMeshManager meshManagerIn) throws Exception
	{
		this.instances = BufferUtils.createFloatBuffer(4 * 4 * 2 * particlesIn);

		final var builder = meshManagerIn.meshBuilderWithVao(6).vbo(GL15.GL_STREAM_DRAW, ShapeRectangle.positions, 2)
				.ibo(GL15.GL_STREAM_DRAW, ShapeRectangle.indices).newInstancesVboAndBind()
				.multipleVertexAttribPointerAndDivisor(4, 4).createVertexAttribPointerAndDivisor(2)
				.data(4L * 4L * 2L * particlesIn).primCount(particlesIn);
		this.vbo	= (Vbo) builder.instancesVbo();
		this.mesh	= builder.build();
	}

	/**
	 * A shader using the instanced data must be attached
	 */
	public void draw()
	{
		if (this.somethingToShow)
		{
			this.mesh.startDrawing(null);
			this.mesh.draw(null);
			this.mesh.stopDrawing(null);
		}
	}

	/**
	 *
	 */
	public void reset()
	{
		this.instances.clear();
	}

	/**
	 *
	 */
	public void flip()
	{
		if (this.instances.position() > 0)
		{
			this.instances.flip();
		}
	}

	public final void put(IParticle particleIn, Matrix4f projViewIn)
	{
		ParticleRenderer.put(particleIn, this.instances, projViewIn);
	}

	public final Pair<GLMesh, BaseVbo> makeNewMesh(GLMeshManager meshManagerIn, List<IParticle> particlesIn,
			Matrix4f projViewIn) throws Exception
	{
		final var builder = meshManagerIn.meshBuilderWithVao(5).vbo(GL15.GL_STREAM_DRAW, ShapeRectangle.positions, 2)
				.ibo(GL15.GL_STREAM_DRAW, ShapeRectangle.indices).newInstancesVboAndBind()
				.multipleVertexAttribPointerAndDivisor(4, 4)
				.createVertexAttribPointerAndDivisor(2)/**.data(4L * 4L * 2L * particlesIn.size())**/
				.data(this.bufferOf(particlesIn, projViewIn)).primCount(particlesIn.size());

		return new Pair<>(builder.build(), builder.instancesVbo());
	}

	public final FloatBuffer bufferOf(List<IParticle> particlesIn, Matrix4f projViewIn)
	{
		this.instances.clear();
		for (final IParticle particle : particlesIn)
		{
			ParticleRenderer.put(particle, this.instances, projViewIn);
		}
		this.instances.flip();

		return this.instances;
	}

	public final void update(Vbo vboIn, List<IParticle> particlesIn, Matrix4f projViewIn) throws Exception
	{
		vboIn.updateData(this.bufferOf(particlesIn, projViewIn));
	}

	public final void update() throws Exception
	{
		if (this.instances.capacity() > 0)
		{
			this.vbo.updateData(this.instances);

			this.somethingToShow = true;
		}
		else
		{
			this.vbo.updateData(0L);

			this.somethingToShow = false;
		}
	}

	/**
	 * @param sizeIn
	 */
	public void resize(int sizeIn)
	{
		this.instances = BufferUtils.createFloatBuffer(sizeIn * 4 * 4 * 2);

		this.vbo.updateData(sizeIn * 4 * 4 * 2);
	}
}