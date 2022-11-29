package fr.onsiea.engine.client.graphics.opengl.vao;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class VaoUtils
{
	final static int gen()
	{
		return GL30.glGenVertexArrays();
	}

	final static int[] gen(int[] vaosIn)
	{
		GL30.glGenVertexArrays(vaosIn);

		return vaosIn;
	}

	final static int[] genAll(int numberIn)
	{
		final var vaos = new int[numberIn];

		GL30.glGenVertexArrays(vaos);

		return vaos;
	}

	final static Vao create()
	{
		return new Vao();
	}

	public final static void bind(int idIn)
	{
		GL30.glBindVertexArray(idIn);
	}

	public final static void enable(int attribIndexIn)
	{
		GL20.glEnableVertexAttribArray(attribIndexIn);
	}

	/**
	 *  Enable all vertex array attrib up to attribIn
	 * @param attribsIn
	 * @return
	 */
	public final static void enables(int attribIn)
	{
		for (var i = 0; i < attribIn; i++)
		{
			GL20.glEnableVertexAttribArray(i);
		}
	}

	/**
	 *  Enable all vertex array attrib from startAttribIn to attribIn
	 * @param attribsIn
	 * @return
	 */
	public final static void enablesFromTo(int startAttribIn, int attribIn)
	{
		for (var i = startAttribIn; i < attribIn; i++)
		{
			GL20.glEnableVertexAttribArray(i);
		}
	}

	/**
	 * Create, bind vao and enable all vertex array attrib up to attribIn;
	 * @param attribsIn
	 * @return
	 */
	final static Vao createBindAndEnables(int attribsIn)
	{
		return new Vao().bindAndEnables(attribsIn);
	}

	/**
	 * Create, bind vao and enable all vertex array attrib up to attribIn;
	 * @param attribsIn
	 * @return
	 */
	final static Vao createBindAndEnablesFromTo(int startIn, int attribsIn)
	{
		final var vao = new Vao();
		vao.bind();

		VaoUtils.enablesFromTo(startIn, attribsIn);

		return vao;
	}

	/**
	 * Create, bind vao and enable all vertex array attrib up to attribIn;
	 * @param attribsIn
	 * @return
	 */
	public final static Vao bindAndEnables(Vao vaoIn, int attribsIn)
	{
		vaoIn.bind();

		VaoUtils.enables(attribsIn);

		return vaoIn.bindAndEnables(attribsIn);
	}

	/**
	 * Create, bind vao and enable all vertex array attrib up to attribIn;
	 * @param attribsIn
	 * @return
	 */
	public final static Vao bindAndEnablesFromTo(Vao vaoIn, int startIn, int attribsIn)
	{
		vaoIn.bind();

		VaoUtils.enablesFromTo(startIn, attribsIn);

		return vaoIn;
	}

	public final static void vertexAttrib(int indexIn, int sizeIn)
	{
		GL20.glVertexAttribPointer(indexIn, sizeIn, GL11.GL_FLOAT, false, 0, 0L);
	}

	public final static void vertexAttrib(int indexIn, int sizeIn, int typeIn)
	{
		GL20.glVertexAttribPointer(indexIn, sizeIn, typeIn, false, 0, 0L);
	}

	public final static void vertexAttrib(int indexIn, int sizeIn, int strideIn, int pointerIn)
	{
		GL20.glVertexAttribPointer(indexIn, sizeIn, GL11.GL_FLOAT, false, strideIn, pointerIn);
	}

	public final static void vertexAttrib(int indexIn, int sizeIn, int typeIn, int strideIn, int pointerIn)
	{
		GL20.glVertexAttribPointer(indexIn, sizeIn, typeIn, false, strideIn, pointerIn);
	}

	public final static void vertexAttrib(int indexIn, int sizeIn, int typeIn, boolean normalizedIn, int strideIn,
			int pointerIn)
	{
		GL20.glVertexAttribPointer(indexIn, sizeIn, typeIn, normalizedIn, strideIn, pointerIn);
	}

	/**
	 *  Disable all vertex array attrib up to attribIn and unbind vao
	 * @param attribsIn
	 * @return
	 */
	public final static void disablesFromToAndUnbind(int startAttribIn, int attribsIn)
	{
		VaoUtils.disablesFromTo(startAttribIn, attribsIn);

		GL30.glBindVertexArray(0);
	}

	/**
	 *  Disable all vertex array attrib up to attribIn and unbind vao
	 * @param attribsIn
	 * @return
	 */
	public final static void disablesAndUnbind(int attribsIn)
	{
		VaoUtils.disables(attribsIn);

		GL30.glBindVertexArray(0);
	}

	public final static void disable(int attribIndexIn)
	{
		GL20.glDisableVertexAttribArray(attribIndexIn);
	}

	public final static void unbind()
	{
		GL30.glBindVertexArray(0);
	}

	/**
	 *  Disable all vertex array attrib from startAttribIn to attribIn
	 * @param attribsIn
	 * @return
	 */
	public final static void disablesFromTo(int startAttribIn, int attribIn)
	{
		for (var i = startAttribIn; i < attribIn; i++)
		{
			GL20.glEnableVertexAttribArray(i);
		}
	}

	/**
	 *  Disable all vertex array attrib up to attribIn
	 * @param attribsIn
	 * @return
	 */
	public final static void disables(int attribIn)
	{
		for (var i = 0; i < attribIn; i++)
		{
			GL20.glEnableVertexAttribArray(i);
		}
	}

	final static void deletes(int[] arraysIn)
	{
		GL30.glDeleteVertexArrays(arraysIn);
	}

	final static void delete(int arrayIn)
	{
		GL30.glDeleteVertexArrays(arrayIn);
	}
}