package fr.onsiea.engine.client.graphics.opengl.vbo;

import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL31;

public class Elements extends BaseVbo
{
	public Elements()
	{
		this.id(GL15.glGenBuffers());
	}

	public Elements(int drawTypeIn)
	{
		super(drawTypeIn);
	}

	public void data(IntBuffer indicesIn)
	{
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesIn, this.drawType());
	}

	public void data(int[] indicesIn)
	{
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesIn, this.drawType());
	}

	public void draw(int vertexCountIn)
	{
		GL11.glDrawElements(GL11.GL_TRIANGLES, vertexCountIn, GL11.GL_UNSIGNED_INT, 0L);
	}

	public void draw(int modeIn, int vertexCountIn)
	{
		GL11.glDrawElements(modeIn, vertexCountIn, GL11.GL_UNSIGNED_INT, 0L);
	}

	public void draw(int modeIn, int typeIn, int vertexCountIn)
	{
		GL11.glDrawElements(modeIn, vertexCountIn, typeIn, 0L);
	}

	public void draw(int modeIn, int typeIn, int vertexCountIn, long indicesIn)
	{
		GL11.glDrawElements(modeIn, vertexCountIn, typeIn, indicesIn);
	}

	public void drawInstanced(int vertexCountIn, int primCountIn)
	{
		GL31.glDrawElementsInstanced(GL11.GL_TRIANGLES, vertexCountIn, GL11.GL_UNSIGNED_INT, 0L, primCountIn);
	}

	public void drawInstanced(int modeIn, int vertexCountIn, int primCountIn)
	{
		GL31.glDrawElementsInstanced(modeIn, vertexCountIn, GL11.GL_UNSIGNED_INT, 0L, primCountIn);
	}

	public void drawInstanced(int modeIn, int vertexCountIn, int typeIn, int primCountIn)
	{
		GL31.glDrawElementsInstanced(modeIn, vertexCountIn, typeIn, 0L, primCountIn);
	}

	public void drawInstanced(int modeIn, int vertexCountIn, int typeIn, long indicesIn, int primCountIn)
	{
		GL31.glDrawElementsInstanced(modeIn, vertexCountIn, typeIn, indicesIn, primCountIn);
	}

	public void drawRange(final int startIn, int endIn, int vertexCountIn)
	{
		GL12.glDrawRangeElements(GL11.GL_TRIANGLES, startIn, endIn, vertexCountIn, GL11.GL_UNSIGNED_BYTE, 0);
	}

	@Override
	protected int target()
	{
		return GL15.GL_ELEMENT_ARRAY_BUFFER;
	}
}
