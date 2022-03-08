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
package fr.onsiea.engine.client.graphics.opengl.utils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL33;

import fr.onsiea.engine.client.graphics.GraphicsConstants;
import fr.onsiea.engine.client.graphics.opengl.model.GLRawModel;
import fr.onsiea.engine.client.graphics.opengl.model.GLTexturedModel;
import fr.onsiea.engine.client.graphics.opengl.vao.Vao;
import fr.onsiea.engine.client.graphics.opengl.vbo.BaseVbo;
import fr.onsiea.engine.client.graphics.opengl.vbo.Vbo;

/**
 * @author Seynax
 *
 */
public class OpenGLUtils
{
	public final static void clearColor(float rIn, float gIn, float bIn, float aIn)
	{
		GL11.glClearColor(rIn, bIn, gIn, aIn);
	}

	public final static void clear()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
	}

	public final static void clear(int flagsIn)
	{
		GL11.glClear(flagsIn);
	}

	public final static void restoreState()
	{
		OpenGLUtils.initialize3D();

		if (GraphicsConstants.SHOW_TRIANGLES)
		{
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		}
	}

	public final static void initialize3D()
	{
		// Enables

		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LESS);
		if (GraphicsConstants.CULL_FACE)
		{
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glCullFace(GL11.GL_BACK);
			GL11.glFrontFace(GL11.GL_CCW);
		}
		GL11.glEnable(GL11.GL_STENCIL_TEST);
		GL11.glDepthMask(true);
	}

	public final static void initialize2D()
	{
		// Disables

		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_STENCIL_TEST);

		// Modes

		GL11.glDepthMask(false);
	}

	public final static void initialize2DWithStencil()
	{
		// Disables

		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_STENCIL_TEST);

		// Modes

		GL11.glDepthMask(false);
	}

	public final static void enableTransparency()
	{
		// Enables

		GL11.glEnable(GL11.GL_BLEND);

		// Modes

		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	public final static void enableTransparencyWithAdditiveBlending()
	{
		// Enables

		GL11.glEnable(GL11.GL_BLEND);

		// Modes

		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
	}

	public final static void disableTransparency()
	{
		// Enables

		GL11.glDisable(GL11.GL_BLEND);
	}

	public static GLRawModel makeModel(float[] vertexIn, float[] uvIn)
	{
		final var vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);

		final var vbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexIn, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		final var ubo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, ubo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, uvIn, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		GL30.glBindVertexArray(0);

		return new GLRawModel(vao, vbo, vertexIn.length, ubo);
	}

	public static GLRawModel makeModel(FloatBuffer vertexBufferIn, FloatBuffer uvBufferIn)
	{
		final var vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);

		final var vbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexBufferIn, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		final var ubo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, ubo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, uvBufferIn, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		GL30.glBindVertexArray(0);

		return new GLRawModel(vao, vbo, vertexBufferIn.capacity(), ubo);
	}

	public static GLRawModel makeModel(float[] vertexIn, float[] uvIn, int[] indicesIn)
	{
		final var vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);

		final var vbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexIn, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		final var ubo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, ubo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, uvIn, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		final var ibo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesIn, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

		GL30.glBindVertexArray(0);

		return new GLRawModel(vao, vbo, vertexIn.length, ubo, ibo);
	}

	public static GLRawModel makeVao(FloatBuffer vertexBufferIn, FloatBuffer uvBufferIn, IntBuffer indicesBufferIn)
	{
		final var vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);

		final var vbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexBufferIn, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		final var ubo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, ubo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, uvBufferIn, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		final var ibo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBufferIn, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

		GL30.glBindVertexArray(0);

		return new GLRawModel(vao, vbo, vertexBufferIn.capacity(), ubo, ibo);
	}

	public static void drawArray(int textureIdIn, int vaoIn, int vertexCountIn)
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureIdIn);

		GL30.glBindVertexArray(vaoIn);
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);

		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertexCountIn);

		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}

	public static void drawArrayLines(int textureIdIn, int vaoIn, int vertexCountIn)
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureIdIn);

		GL30.glBindVertexArray(vaoIn);
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);

		GL11.glDrawArrays(GL11.GL_LINES, 0, vertexCountIn);

		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}

	public static void drawElements(int textureIdIn, int vaoIn, int vertexCountIn)
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureIdIn);

		GL30.glBindVertexArray(vaoIn);
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);

		GL11.glDrawElements(GL11.GL_TRIANGLES, vertexCountIn, GL11.GL_UNSIGNED_INT, 0);

		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}

	public static void drawElementsLines(int textureIdIn, int vaoIn, int vertexCountIn)
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureIdIn);
		GL30.glBindVertexArray(vaoIn);
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);

		GL11.glDrawElements(GL11.GL_LINES, vertexCountIn, GL11.GL_UNSIGNED_INT, 0);

		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}

	public static void drawElements(GLTexturedModel modelIn)
	{
		OpenGLUtils.drawElements(modelIn.texture().id(), modelIn.rawModel().vao(), modelIn.rawModel().vertexCount());
	}

	public static void drawElementsLines(GLTexturedModel modelIn)
	{
		OpenGLUtils.drawElementsLines(modelIn.texture().id(), modelIn.rawModel().vao(),
				modelIn.rawModel().vertexCount());
	}

	public static void drawElementsCubeMap(int textureIdIn, int vaoIn, int vertexCountIn)
	{
		//GL11.glDepthMask(false);
		//GL11.glDepthRange(1f, 1f);

		GL30.glBindVertexArray(vaoIn);
		GL20.glEnableVertexAttribArray(0);
		//GL20.glEnableVertexAttribArray(1);

		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, textureIdIn);

		GL11.glDrawElements(GL11.GL_TRIANGLES, vertexCountIn, GL11.GL_UNSIGNED_INT, 0);

		//GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);

		//GL11.glDepthRange(0f, 1f);
		//GL11.glDepthMask(true);
	}

	public static void drawElementsLinesCubeMap(int textureIdIn, int vaoIn, int vertexCountIn)
	{
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, textureIdIn);
		GL30.glBindVertexArray(vaoIn);
		GL20.glEnableVertexAttribArray(0);
		//GL20.glEnableVertexAttribArray(1);

		GL11.glDrawElements(GL11.GL_LINES, vertexCountIn, GL11.GL_UNSIGNED_INT, 0);

		//GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}

	public static void drawArraysCubeMap(int textureIdIn, int vaoIn, int vertexCountIn)
	{
		//GL11.glDepthMask(false);
		//GL11.glDepthRange(1f, 1f);

		GL30.glBindVertexArray(vaoIn);
		GL20.glEnableVertexAttribArray(0);
		//GL20.glEnableVertexAttribArray(1);

		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, textureIdIn);

		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertexCountIn);

		//GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);

		//GL11.glDepthRange(0f, 1f);
		//GL11.glDepthMask(true);
	}

	public static void drawArraysLinesCubeMap(int textureIdIn, int vaoIn, int vertexCountIn)
	{
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, textureIdIn);
		GL30.glBindVertexArray(vaoIn);
		GL20.glEnableVertexAttribArray(0);
		//GL20.glEnableVertexAttribArray(1);

		GL11.glDrawArrays(GL11.GL_LINES, 0, vertexCountIn);

		//GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}

	public static void drawElementsCubeMap(GLTexturedModel modelIn)
	{
		OpenGLUtils.drawElementsCubeMap(modelIn.texture().id(), modelIn.rawModel().vao(),
				modelIn.rawModel().vertexCount());
	}

	public static void drawElementsLinesCubeMap(GLTexturedModel modelIn)
	{
		OpenGLUtils.drawElementsLinesCubeMap(modelIn.texture().id(), modelIn.rawModel().vao(),
				modelIn.rawModel().vertexCount());
	}

	public static void drawArraysCubeMap(GLTexturedModel modelIn)
	{
		OpenGLUtils.drawArraysCubeMap(modelIn.texture().id(), modelIn.rawModel().vao(),
				modelIn.rawModel().vertexCount());
	}

	public static void drawArraysLinesCubeMap(GLTexturedModel modelIn)
	{
		OpenGLUtils.drawArraysLinesCubeMap(modelIn.texture().id(), modelIn.rawModel().vao(),
				modelIn.rawModel().vertexCount());
	}

	public final static BaseVbo createEmpty(final int sizeIn)
	{
		return new Vbo().data(sizeIn).unbind();
	}

	public final static void addInstancedAttribute(final int attributeIn, final int dataSizeIn,
			final int instancedDataLengthIn, final int offsetIn)
	{
		GL20.glVertexAttribPointer(attributeIn, dataSizeIn, GL11.GL_FLOAT, false, instancedDataLengthIn * 4, offsetIn);
		GL33.glVertexAttribDivisor(attributeIn, 1);
	}

	public final static void addInstancedAttribute(final Vao vaoIn, final Vbo vboIn, final int attributeIn,
			final int dataSizeIn, final int instancedDataLengthIn, final int offsetIn)
	{
		vboIn.bind();
		vaoIn.bind();

		GL20.glVertexAttribPointer(attributeIn, dataSizeIn, GL11.GL_FLOAT, false, instancedDataLengthIn * 4, offsetIn);
		GL33.glVertexAttribDivisor(attributeIn, 1);

		OpenGLUtils.unbindVbo(GL15.GL_ARRAY_BUFFER);
		Vao.unbind();
	}

	// -------------------

	public final static void enableVertexArray()
	{
		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
	}

	public final static void enableColorArray()
	{
		GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
	}

	public final static void vertexPointer(final int sizeIn, final int typeIn)
	{
		GL11.glVertexPointer(sizeIn, typeIn, 0, 0);
	}

	public final static void colorPointer(final int sizeIn, final int typeIn)
	{
		GL11.glColorPointer(sizeIn, typeIn, 0, 0);
	}

	public final static void vertexPointer(final int sizeIn)
	{
		GL11.glVertexPointer(sizeIn, GL11.GL_FLOAT, 0, 0);
	}

	public final static void colorPointer(final int sizeIn)
	{
		GL11.glColorPointer(sizeIn, GL11.GL_FLOAT, 0, 0);
	}

	public final static void bind(final int typeIn, final int vboIdIn)
	{
		GL15.glBindBuffer(typeIn, vboIdIn);
	}

	public final static void bufferData(final int typeIn, final int vboIdIn, final FloatBuffer bufferDataIn)
	{
		GL15.glBindBuffer(typeIn, vboIdIn);
		GL15.glBufferData(typeIn, bufferDataIn, GL15.GL_STATIC_DRAW);
	}

	public final static void bufferData(final int typeIn, final int vboIdIn, final IntBuffer bufferDataIn)
	{
		GL15.glBindBuffer(typeIn, vboIdIn);
		GL15.glBufferData(typeIn, bufferDataIn, GL15.GL_STATIC_DRAW);
	}

	// draw(final Vbo vertexVboIn, final Vbo colorVboIn)
	// --------------------------------------------

	public final static void draw(final int sizeIn, final Vbo vertexVboIn, final Vbo colorVboIn)
	{
		OpenGLUtils.enableVertexArray();
		vertexVboIn.bind();
		OpenGLUtils.vertexPointer(6);

		OpenGLUtils.enableColorArray();
		colorVboIn.bind();
		OpenGLUtils.colorPointer(4);

		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, sizeIn);
	}

	public final static void draw(final int sizeIn, final Vbo vertexVboIn, final int vertexSizeIn, final Vbo colorVboIn)
	{
		OpenGLUtils.enableVertexArray();
		vertexVboIn.bind();
		OpenGLUtils.vertexPointer(vertexSizeIn);

		OpenGLUtils.enableColorArray();
		colorVboIn.bind();
		OpenGLUtils.colorPointer(4);

		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, sizeIn);
	}

	public final static void draw(final int sizeIn, final Vbo vertexVboIn, final Vbo colorVboIn, final int colorSizeIn)
	{
		OpenGLUtils.enableVertexArray();
		vertexVboIn.bind();
		OpenGLUtils.vertexPointer(3);

		OpenGLUtils.enableColorArray();
		colorVboIn.bind();
		OpenGLUtils.colorPointer(colorSizeIn);

		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, sizeIn);
	}

	public final static void draw(final int sizeIn, final Vbo vertexVboIn, final int vertexSizeIn, final Vbo colorVboIn,
			final int colorSizeIn)
	{
		OpenGLUtils.enableVertexArray();
		vertexVboIn.bind();
		OpenGLUtils.vertexPointer(vertexSizeIn);

		OpenGLUtils.enableColorArray();
		colorVboIn.bind();
		OpenGLUtils.colorPointer(colorSizeIn);

		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, sizeIn);
	}

	// ----------------------------------------------------------------------------------------------

	public final static void unbindVbo(final int typeIn)
	{
		GL15.glBindBuffer(typeIn, 0);
	}
}