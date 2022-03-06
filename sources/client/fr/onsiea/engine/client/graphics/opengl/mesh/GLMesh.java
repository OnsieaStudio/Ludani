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
package fr.onsiea.engine.client.graphics.opengl.mesh;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;

import fr.onsiea.engine.client.graphics.drawable.IDrawable;
import fr.onsiea.engine.client.graphics.opengl.mesh.components.GLMeshElementsComponent;
import fr.onsiea.engine.client.graphics.opengl.mesh.components.GLMeshVaoComponent;
import fr.onsiea.engine.client.graphics.opengl.mesh.components.GLMeshVboComponent;
import fr.onsiea.engine.client.graphics.opengl.mesh.components.IGLMeshComponent;
import fr.onsiea.engine.client.graphics.opengl.mesh.draw.DrawersElements;
import fr.onsiea.engine.client.graphics.opengl.mesh.draw.DrawersInstancedElements;
import fr.onsiea.engine.client.graphics.opengl.mesh.draw.DrawersInstancedVao;
import fr.onsiea.engine.client.graphics.opengl.mesh.draw.DrawersVao;
import fr.onsiea.engine.client.graphics.opengl.vao.Vao;
import fr.onsiea.engine.client.graphics.opengl.vao.VaoManager;
import fr.onsiea.engine.client.graphics.opengl.vbo.Elements;
import fr.onsiea.engine.client.graphics.opengl.vbo.GLDataBuilder;
import fr.onsiea.engine.client.graphics.opengl.vbo.GLDataBuilder.VertexAttribPointer;
import fr.onsiea.engine.client.graphics.opengl.vbo.VboManager;
import fr.onsiea.engine.client.graphics.render.Renderer;
import lombok.AccessLevel;
import lombok.Getter;

/**
 * @author Seynax
 *
 */
@Getter(AccessLevel.PUBLIC)
public class GLMesh implements IDrawable
{
	private IMeshDrawFunction				drawFunction;
	private final List<IGLMeshComponent>	components;

	public GLMesh(IMeshDrawFunction drawFunctionIn)
	{
		this.drawFunction	= drawFunctionIn;
		this.components		= new ArrayList<>();
	}

	private GLMesh()
	{
		this.components = new ArrayList<>();
	}

	public GLMesh add(IGLMeshComponent meshComponentIn)
	{
		this.components().add(meshComponentIn);

		return this;
	}

	@Override
	public void startDrawing(Renderer rendererIn)
	{
		this.drawFunction.start(this, rendererIn);

	}

	@Override
	public void draw(Renderer rendererIn)
	{
		this.drawFunction.draw(this, rendererIn);

	}

	@Override
	public void stopDrawing(Renderer rendererIn)
	{
		this.drawFunction.stop(this, rendererIn);
	}

	public void cleanup()
	{

	}

	public final static class Builder
	{
		public static Builder withVao(VaoManager vaoManagerIn, VboManager vboManagerIn)
		{
			return new Builder(vaoManagerIn, vboManagerIn).newVao().bindVao();
		}

		public static Builder withVao(VaoManager vaoManagerIn, VboManager vboManagerIn, int enablesIn)
		{
			return new Builder(vaoManagerIn, vboManagerIn).newVao().bindAndEnables(enablesIn);
		}

		/**
		 * @param indicesIn
		 * @param vertexAndAttributesIn
		 * @param attributesSizesIn
		 * @return
		 * @throws Exception
		 */
		public static GLMesh build(VaoManager vaoManagerIn, VboManager vboManagerIn, int[] indicesIn,
				float[] vertexAndAttributesIn, int[] attributesSizesIn) throws Exception
		{
			return Builder.withVao(vaoManagerIn, vboManagerIn, attributesSizesIn.length)
					.vbo(vertexAndAttributesIn, attributesSizesIn).ibo(indicesIn)
					.disablesAndUnbind(attributesSizesIn.length).build();
		}

		private final GLMesh		mesh;

		private final GLDataBuilder	dataBuilder;

		private Vao					lastVao;
		// private Vbo								lastVbo;
		private Elements			lastElements;

		private int					attribs;
		private int					vertexCount;
		private int					primCount;

		private final VaoManager	vaoManager;

		private boolean				elementsRunning;

		public Builder(VaoManager vaoManagerIn, VboManager vboManagerIn)
		{
			this.vaoManager		= vaoManagerIn;
			this.dataBuilder	= new GLDataBuilder(vboManagerIn);

			this.mesh			= new GLMesh();

			this.attribs		= -1;
			this.vertexCount	= -1;
			this.primCount		= -1;
		}

		public Builder(VaoManager vaoManagerIn, VboManager vboManagerIn, IMeshDrawFunction drawFunctionIn)
		{
			this.vaoManager		= vaoManagerIn;
			this.dataBuilder	= new GLDataBuilder(vboManagerIn);
			this.mesh			= new GLMesh(drawFunctionIn);

			this.attribs		= -1;
			this.vertexCount	= -1;
			this.primCount		= -1;
		}

		public GLMesh build() throws Exception
		{
			if (this.mesh.components().size() <= 0)
			{
				throw new Exception("[ERROR] You need at least one component !");
			}

			if (this.lastVao == null)
			{
				throw new Exception("[ERROR] You need VAO !");
			}
			if (this.attribs <= 0)
			{
				this.attribs = this.dataBuilder.globalIndex();
			}

			if (this.vertexCount < 0)
			{
				throw new Exception("[ERROR] Vertex count isn't specified !");
			}
			if (this.vertexCount == 0)
			{
				throw new Exception("[ERROR] Vertex count cannot be 0 !");
			}

			if (this.lastVao == null)
			{
				throw new Exception("[ERROR] You need VAO !");
			}

			if (this.mesh.drawFunction == null)
			{
				if (this.lastElements != null)
				{
					if (this.primCount >= 0)
					{
						this.drawFunction(new DrawersInstancedElements(this.lastElements, this.lastVao, this.attribs,
								this.vertexCount, this.primCount));
					}
					else
					{
						this.drawFunction(
								new DrawersElements(this.lastElements, this.lastVao, this.attribs, this.vertexCount));
					}
				}
				else if (this.primCount >= 0)
				{
					this.drawFunction(new DrawersInstancedVao(this.lastElements, this.lastVao, this.attribs,
							this.vertexCount, this.primCount));
				}
				else
				{
					this.drawFunction(new DrawersVao(this.lastVao, this.attribs, this.vertexCount));
				}
			}

			return this.mesh;
		}

		public Builder newVboAndBind()
		{
			this.add(new GLMeshVboComponent(this.dataBuilder.newVboAndBind()));

			return this;
		}

		public Builder vbo(FloatBuffer bufferIn)
		{
			this.add(new GLMeshVboComponent(this.dataBuilder.newVboAndBind()));

			this.data(bufferIn);

			this.unbind();

			this.elementsRunning = false;

			return this;
		}

		public Builder vbo(float[] arrayIn, int... sizes)
		{
			this.add(new GLMeshVboComponent(this.dataBuilder.newVboAndBind()));

			for (final int size : sizes)
			{
				this.dataBuilder.createVertexAttribPointer(size);
			}
			this.dataBuilder.data(arrayIn);

			this.dataBuilder.unbind();

			this.elementsRunning = false;

			return this;
		}

		public Builder vbo(FloatBuffer bufferIn, int... sizes)
		{
			this.add(new GLMeshVboComponent(this.dataBuilder.newVboAndBind()));

			for (final int size : sizes)
			{
				this.dataBuilder.createVertexAttribPointer(size);
			}
			this.dataBuilder.data(bufferIn);

			this.dataBuilder.unbind();

			return this;
		}

		public Builder newVboAndBind(int usageIn)
		{
			this.add(new GLMeshVboComponent(this.dataBuilder.newVboAndBind(usageIn)));

			return this;
		}

		public Builder newVboAndBind(int targetIn, int usageIn)
		{
			this.add(new GLMeshVboComponent(this.dataBuilder.newVboAndBind(targetIn, usageIn)));

			return this;
		}

		public Builder newIboAndBind()
		{
			this.lastElements = this.dataBuilder.newIboAndBind();

			this.add(new GLMeshElementsComponent(this.lastElements));

			this.elementsRunning = true;

			return this;
		}

		public Builder newIboAndBind(int usageIn)
		{
			this.lastElements = this.dataBuilder.newIboAndBind();

			this.add(new GLMeshElementsComponent(this.lastElements));

			this.elementsRunning = true;

			return this;
		}

		public Builder ibo(int[] indicesIn)
		{
			this.lastElements = this.dataBuilder.newIboAndBind();

			this.add(new GLMeshElementsComponent(this.lastElements));

			this.data(indicesIn);

			this.vertexCount(indicesIn.length);

			this.elementsRunning = false;

			return this;
		}

		public Builder data(ByteBuffer bufferIn)
		{
			if (this.elementsRunning)
			{
				this.vertexCount(bufferIn.capacity());
			}

			this.dataBuilder.data(bufferIn);

			return this;
		}

		public Builder data(short[] arrayIn)
		{
			if (this.elementsRunning)
			{
				this.vertexCount(arrayIn.length);
			}

			this.dataBuilder.data(arrayIn);

			return this;
		}

		public Builder data(ShortBuffer bufferIn)
		{
			if (this.elementsRunning)
			{
				this.vertexCount(bufferIn.capacity());
			}

			this.dataBuilder.data(bufferIn);

			return this;
		}

		public Builder data(int[] arrayIn)
		{
			if (this.elementsRunning)
			{
				this.vertexCount(arrayIn.length);
			}

			this.dataBuilder.data(arrayIn);

			return this;
		}

		public Builder data(IntBuffer bufferIn)
		{
			if (this.elementsRunning)
			{
				this.vertexCount(bufferIn.capacity());
			}

			this.dataBuilder.data(bufferIn);

			return this;
		}

		public Builder data(float[] arrayIn)
		{
			if (this.elementsRunning)
			{
				this.vertexCount(arrayIn.length);
			}

			this.dataBuilder.data(arrayIn);

			return this;
		}

		public Builder data(FloatBuffer bufferIn)
		{
			if (this.elementsRunning)
			{
				this.vertexCount(bufferIn.capacity());
			}

			this.dataBuilder.data(bufferIn);

			return this;
		}

		public Builder data(double[] arrayIn)
		{
			if (this.elementsRunning)
			{
				this.vertexCount(arrayIn.length);
			}

			this.dataBuilder.data(arrayIn);

			return this;
		}

		public Builder data(DoubleBuffer bufferIn)
		{
			if (this.elementsRunning)
			{
				this.vertexCount(bufferIn.capacity());
			}

			this.dataBuilder.data(bufferIn);

			return this;
		}

		public Builder data(long[] arrayIn)
		{
			if (this.elementsRunning)
			{
				this.vertexCount(arrayIn.length);
			}

			this.dataBuilder.data(arrayIn);

			return this;
		}

		public Builder data(LongBuffer bufferIn)
		{
			if (this.elementsRunning)
			{
				this.vertexCount(bufferIn.capacity());
			}

			this.dataBuilder.data(bufferIn);

			return this;
		}

		public Builder data(long sizeIn)
		{
			this.dataBuilder.data(sizeIn);

			return this;
		}

		public Builder createVertexAttribPointer()
		{
			this.dataBuilder.createVertexAttribPointer();

			return this;
		}

		public Builder createVertexAttribPointer(int sizeIn)
		{
			this.dataBuilder.createVertexAttribPointer(sizeIn);

			return this;
		}

		/**
		 * @param iIn
		 * @param i2In
		 * @return
		 */
		public Builder createVertexAttribPointers(int... sizesIn)
		{
			for (final int size : sizesIn)
			{
				this.dataBuilder.createVertexAttribPointer(size);
			}

			return this;
		}

		public Builder createVertexAttribPointerAndDivisor()
		{
			this.dataBuilder.createVertexAttribPointerAndDivisor();

			return this;
		}

		public Builder createVertexAttribPointerAndDivisor(int sizeIn)
		{
			this.dataBuilder.createVertexAttribPointerAndDivisor(sizeIn);

			return this;
		}

		public Builder divisor()
		{
			this.dataBuilder.divisor();

			return this;
		}

		public Builder divisor(int divisorIn)
		{
			this.dataBuilder.divisor(divisorIn);

			return this;
		}

		public Builder size(int lastIn, int sizeIn, VertexAttribPointer vertexAttribPointerIn)
		{
			this.dataBuilder.size(lastIn, sizeIn, vertexAttribPointerIn);

			return this;
		}

		public Builder unbind()
		{
			this.dataBuilder.unbind();

			this.elementsRunning = false;

			return this;
		}

		public Builder reset()
		{
			this.dataBuilder.reset();

			return this;
		}

		public Builder globalIndex(int indexIn)
		{
			this.dataBuilder.globalIndex(indexIn);

			return this;
		}

		public Builder add(GLMeshVaoComponent componentIn)
		{
			this.lastVao = componentIn.vao();

			this.mesh.components.add(componentIn);

			return this;
		}

		public Builder newVao()
		{
			this.lastVao = this.vaoManager.create();
			this.lastVao.bind();

			this.mesh.components.add(new GLMeshVaoComponent(this.lastVao));

			return this;
		}

		public Builder bindVao()
		{
			this.lastVao.bind();

			return this;
		}

		public Builder unbindVao()
		{
			Vao.unbind();

			return this;
		}

		public Builder bindAndEnables(int attribIn)
		{
			this.lastVao.bindAndEnables(attribIn);

			return this;
		}

		public Builder bindAndEnablesFromTo(int startAttribIn, int attribsIn)
		{
			this.lastVao.bindAndEnablesFromTo(startAttribIn, attribsIn);

			return this;
		}

		public Builder enables(int attribIn)
		{
			this.lastVao.enables(attribIn);

			return this;
		}

		public Builder enablesFromTo(int startAttribIn, int attribIn)
		{
			this.lastVao.enablesFromTo(startAttribIn, attribIn);

			return this;
		}

		public Builder vertexAttrib(int indexIn, int sizeIn)
		{
			this.lastVao.vertexAttrib(indexIn, sizeIn);

			return this;
		}

		public Builder vertexAttrib(int indexIn, int sizeIn, int typeIn)
		{
			this.lastVao.vertexAttrib(indexIn, sizeIn, typeIn);

			return this;
		}

		public Builder vertexAttrib(int indexIn, int sizeIn, int strideIn, int pointerIn)
		{
			this.lastVao.vertexAttrib(indexIn, sizeIn, strideIn, pointerIn);

			return this;
		}

		public Builder vertexAttrib(int indexIn, int sizeIn, int typeIn, int strideIn, int pointerIn)
		{
			this.lastVao.vertexAttrib(indexIn, sizeIn, typeIn, strideIn, pointerIn);

			return this;
		}

		public Builder vertexAttrib(int indexIn, int sizeIn, int typeIn, boolean normalizedIn, int strideIn,
				int pointerIn)
		{
			this.lastVao.vertexAttrib(indexIn, sizeIn, typeIn, normalizedIn, strideIn, pointerIn);

			return this;
		}

		public Builder disablesFromTo(int startAttribIn, int attribIn)
		{
			this.lastVao.disablesFromTo(startAttribIn, attribIn);

			return this;
		}

		public Builder disables(int attribIn)
		{
			this.lastVao.disables(attribIn);

			return this;
		}

		public Builder disablesFromToAndUnbind(int startAttribIn, int attribsIn)
		{
			this.lastVao.disablesFromToAndUnbind(startAttribIn, attribsIn);

			return this;
		}

		public Builder disablesAndUnbind(int attribsIn)
		{
			this.lastVao.disablesAndUnbind(attribsIn);

			return this;
		}

		public Builder add(GLMeshVboComponent componentIn)
		{
			// this.lastVbo = componentIn.vbo();

			this.mesh.components.add(componentIn);

			return this;
		}

		/**public Builder bindVbo()
		{
			this.lastVbo.bind();

			return this;
		}

		public Builder unbindVbo()
		{
			this.lastVbo.unbind();

			this.elementsRunning = false;

			return this;
		}

		public Builder drawType(int drawTypeIn)
		{
			this.lastVbo.drawType(drawTypeIn);

			return this;
		}

		public Builder updateData(ByteBuffer bufferIn)
		{
			this.lastVbo.updateData(bufferIn);

			return this;
		}

		public Builder updateData(short[] arrayIn)
		{
			this.lastVbo.updateData(arrayIn);

			return this;
		}

		public Builder updateData(ShortBuffer bufferIn)
		{
			this.lastVbo.updateData(bufferIn);

			return this;
		}

		public Builder updateData(int[] arrayIn)
		{
			this.lastVbo.updateData(arrayIn);

			return this;
		}

		public Builder updateData(IntBuffer bufferIn)
		{
			this.lastVbo.updateData(bufferIn);

			return this;
		}

		public Builder updateData(float[] arrayIn)
		{
			this.lastVbo.updateData(arrayIn);

			return this;
		}

		public Builder updateData(FloatBuffer bufferIn)
		{
			this.lastVbo.updateData(bufferIn);

			return this;
		}

		public Builder updateData(double[] arrayIn)
		{
			this.lastVbo.updateData(arrayIn);

			return this;
		}

		public Builder updateData(DoubleBuffer bufferIn)
		{
			this.lastVbo.updateData(bufferIn);

			return this;
		}

		public Builder updateData(long[] arrayIn)
		{
			this.lastVbo.updateData(arrayIn);

			return this;
		}

		public Builder updateData(LongBuffer bufferIn)
		{
			this.lastVbo.updateData(bufferIn);

			return this;
		}

		public Builder updateData(long sizeIn)
		{
			this.lastVbo.updateData(sizeIn);

			return this;
		}

		public Builder data(ByteBuffer bufferIn)
		{
			this.lastVbo.data(bufferIn);

			return this;
		}

		public Builder data(short[] arrayIn)
		{
			this.lastVbo.data(arrayIn);

			return this;
		}

		public Builder data(ShortBuffer bufferIn)
		{
			this.lastVbo.data(bufferIn);

			return this;
		}

		public Builder data(int[] arrayIn)
		{
			this.lastVbo.data(arrayIn);

			return this;
		}

		public Builder data(IntBuffer bufferIn)
		{
			this.lastVbo.data(bufferIn);

			return this;
		}

		public Builder data(float[] arrayIn)
		{
			this.lastVbo.data(arrayIn);

			return this;
		}

		public Builder data(FloatBuffer bufferIn)
		{
			this.lastVbo.data(bufferIn);

			return this;
		}

		public Builder data(double[] arrayIn)
		{
			this.lastVbo.data(arrayIn);

			return this;
		}

		public Builder data(DoubleBuffer bufferIn)
		{
			this.lastVbo.data(bufferIn);

			return this;
		}

		public Builder data(long[] arrayIn)
		{
			this.lastVbo.data(arrayIn);

			return this;
		}

		public Builder data(LongBuffer bufferIn)
		{
			this.lastVbo.data(bufferIn);

			return this;
		}

		public Builder data(long sizeIn)
		{
			this.lastVbo.data(sizeIn);

			return this;
		}**/

		public Builder add(GLMeshElementsComponent componentIn)
		{
			this.lastElements = componentIn.elements();

			this.mesh.components.add(componentIn);

			return this;
		}

		public Builder bindElements()
		{
			this.lastElements.bind();

			return this;
		}

		public Builder unbindElements()
		{
			this.lastElements.unbind();

			this.elementsRunning = false;

			return this;
		}

		public Builder dataElements(IntBuffer indicesIn)
		{
			this.lastElements.data(indicesIn);

			this.vertexCount(indicesIn.capacity());

			return this;
		}

		public Builder dataElements(int[] indicesIn)
		{
			this.vertexCount(indicesIn.length);

			this.lastElements.data(indicesIn);

			return this;
		}

		public Builder drawFunction(IMeshDrawFunction drawFunctionIn)
		{
			this.mesh.drawFunction = drawFunctionIn;

			return this;
		}

		public Builder attribs(int attribsIn)
		{
			this.attribs = attribsIn;

			return this;
		}

		public Builder vertexCount(int vertexCountIn)
		{
			this.vertexCount = vertexCountIn;

			return this;
		}

		public Builder primCount(int primCountIn)
		{
			this.primCount = primCountIn;

			return this;
		}
	}
}