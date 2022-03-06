package fr.onsiea.engine.client.graphics.opengl.vao;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;

public class Vao
{
	private int id;

	Vao()
	{
		this.id(GL30.glGenVertexArrays());
	}

	public Vao bind()
	{
		GL30.glBindVertexArray(this.id());

		return this;
	}

	public Vao bindAndEnables(int attribIn)
	{
		GL30.glBindVertexArray(this.id());

		this.enables(attribIn);

		return this;
	}

	public Vao bindAndEnablesFromTo(int startAttribIn, int attribsIn)
	{
		GL30.glBindVertexArray(this.id());

		this.enablesFromTo(startAttribIn, attribsIn);

		return this;
	}

	/**
	 *  Enable all vertex array attrib up to attribIn
	 * @param attribsIn
	 * @return
	 */
	public Vao enables(int attribIn)
	{
		for (var i = 0; i < attribIn; i++)
		{
			GL20.glEnableVertexAttribArray(i);
		}

		return this;
	}

	/**
	 *  Enable all vertex array attrib from startAttribIn to attribIn
	 * @param attribsIn
	 * @return
	 */
	public Vao enablesFromTo(int startAttribIn, int attribIn)
	{
		for (var i = startAttribIn; i < attribIn; i++)
		{
			GL20.glEnableVertexAttribArray(i);
		}

		return this;
	}

	public Vao vertexAttrib(int indexIn, int sizeIn)
	{
		GL20.glVertexAttribPointer(indexIn, sizeIn, GL11.GL_FLOAT, false, 0, 0L);

		return this;
	}

	public Vao vertexAttrib(int indexIn, int sizeIn, int typeIn)
	{
		GL20.glVertexAttribPointer(indexIn, sizeIn, typeIn, false, 0, 0L);

		return this;
	}

	public Vao vertexAttrib(int indexIn, int sizeIn, int strideIn, int pointerIn)
	{
		GL20.glVertexAttribPointer(indexIn, sizeIn, GL11.GL_FLOAT, false, strideIn, pointerIn);

		return this;
	}

	public Vao vertexAttrib(int indexIn, int sizeIn, int typeIn, int strideIn, int pointerIn)
	{
		GL20.glVertexAttribPointer(indexIn, sizeIn, typeIn, false, strideIn, pointerIn);

		return this;
	}

	public Vao vertexAttrib(int indexIn, int sizeIn, int typeIn, boolean normalizedIn, int strideIn, int pointerIn)
	{
		GL20.glVertexAttribPointer(indexIn, sizeIn, typeIn, normalizedIn, strideIn, pointerIn);

		return this;
	}

	public void draw(int vertexCountIn)
	{
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertexCountIn);
	}

	public void draw(int modeIn, int vertexCountIn)
	{
		GL11.glDrawArrays(modeIn, 0, vertexCountIn);
	}

	public void draw(int modeIn, int firstIn, int vertexCountIn)
	{
		GL11.glDrawArrays(modeIn, firstIn, vertexCountIn);
	}

	public void drawInstanced(int vertexCountIn, int primCountIn)
	{
		GL31.glDrawArraysInstanced(GL11.GL_TRIANGLES, 0, vertexCountIn, vertexCountIn);
	}

	public void drawInstanced(int modeIn, int vertexCountIn, int primCountIn)
	{
		GL31.glDrawArraysInstanced(modeIn, 0, vertexCountIn, vertexCountIn);
	}

	public void drawInstanced(int modeIn, int firstIn, int vertexCountIn, int primCountIn)
	{
		GL31.glDrawArraysInstanced(modeIn, firstIn, vertexCountIn, vertexCountIn);
	}

	/**
	 *  Disable all vertex array attrib from startAttribIn to attribIn
	 * @param attribsIn
	 * @return
	 */
	public Vao disablesFromTo(int startAttribIn, int attribIn)
	{
		for (var i = startAttribIn; i < attribIn; i++)
		{
			GL20.glEnableVertexAttribArray(i);
		}

		return this;
	}

	/**
	 *  Disable all vertex array attrib up to attribIn
	 * @param attribsIn
	 * @return
	 */
	public Vao disables(int attribIn)
	{
		for (var i = 0; i < attribIn; i++)
		{
			GL20.glEnableVertexAttribArray(i);
		}

		return this;
	}

	/**
	 *  Disable all vertex array attrib up to attribIn and unbind vao
	 * @param attribsIn
	 * @return
	 */
	public Vao disablesFromToAndUnbind(int startAttribIn, int attribsIn)
	{
		VaoUtils.disablesFromTo(startAttribIn, attribsIn);

		GL30.glBindVertexArray(0);

		return this;
	}

	/**
	 *  Disable all vertex array attrib up to attribIn and unbind vao
	 * @param attribsIn
	 * @return
	 */
	public Vao disablesAndUnbind(int attribsIn)
	{
		VaoUtils.disables(attribsIn);

		GL30.glBindVertexArray(0);

		return this;
	}

	public final static void unbind()
	{
		GL30.glBindVertexArray(0);
	}

	void delete()
	{
		GL30.glDeleteVertexArrays(this.id());
	}

	int id()
	{
		return this.id;
	}

	private void id(int idIn)
	{
		this.id = idIn;
	}
}