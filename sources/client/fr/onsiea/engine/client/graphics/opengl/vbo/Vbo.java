package fr.onsiea.engine.client.graphics.opengl.vbo;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.opengl.GL15;

public class Vbo extends BaseVbo
{
	public final static int gen()
	{
		return GL15.glGenBuffers();
	}

	public final static int[] gen(int[] vbosIn)
	{
		GL15.glGenBuffers(vbosIn);

		return vbosIn;
	}

	public final static int[] genAll(int numberIn)
	{
		final var vbos = new int[numberIn];

		GL15.glGenBuffers(vbos);

		return vbos;
	}

	private int target;

	public Vbo()
	{
		this.id(GL15.glGenBuffers());
		this.drawType(GL15.GL_STATIC_DRAW);
		this.target(GL15.GL_ARRAY_BUFFER);
	}

	public Vbo(int targetIn)
	{
		this.id(GL15.glGenBuffers());
		this.drawType(GL15.GL_STATIC_DRAW);
		this.target(targetIn);
	}

	public Vbo(int targetIn, int drawTypeIn)
	{
		super(drawTypeIn);
		this.target(targetIn);
	}

	/**
	 * bind, update data with bufferIn, vbo target and drawType and unbind
	 * different from data(Buffer buffer) which only updates the data
	 * @param bufferIn
	 * @return
	 */
	public Vbo updateData(ByteBuffer bufferIn)
	{
		this.bind();
		GL15.glBufferData(this.target(), bufferIn, this.drawType());
		this.unbind();

		return this;
	}

	/**
	 * bind, update data with arrayIn, vbo target and drawType and unbind
	 * different from data(primitive[] arrayIn) which only updates the data
	 * @param bufferIn
	 * @return
	 */
	public Vbo updateData(short[] arrayIn)
	{
		this.bind();
		GL15.glBufferData(this.target(), arrayIn, this.drawType());
		this.unbind();

		return this;
	}

	/**
	 * bind, update data with bufferIn, vbo target and drawType and unbind
	 * different from data(Buffer buffer) which only updates the data
	 * @param bufferIn
	 * @return
	 */
	public Vbo updateData(ShortBuffer bufferIn)
	{
		this.bind();
		GL15.glBufferData(this.target(), bufferIn, this.drawType());
		this.unbind();

		return this;
	}

	/**
	 * bind, update data with arrayIn, vbo target and drawType and unbind
	 * different from data(primitive[] arrayIn) which only updates the data
	 * @param bufferIn
	 * @return
	 */
	public Vbo updateData(int[] arrayIn)
	{
		this.bind();
		GL15.glBufferData(this.target(), arrayIn, this.drawType());
		this.unbind();

		return this;
	}

	/**
	 * bind, update data with bufferIn, vbo target and drawType and unbind
	 * different from data(Buffer buffer) which only updates the data
	 * @param bufferIn
	 * @return
	 */
	public Vbo updateData(IntBuffer bufferIn)
	{
		this.bind();
		GL15.glBufferData(this.target(), bufferIn, this.drawType());
		this.unbind();

		return this;
	}

	/**
	 * bind, update data with arrayIn, vbo target and drawType and unbind
	 * different from data(primitive[] arrayIn) which only updates the data
	 * @param bufferIn
	 * @return
	 */
	public Vbo updateData(float[] arrayIn)
	{
		this.bind();
		GL15.glBufferData(this.target(), arrayIn, this.drawType());
		this.unbind();

		return this;
	}

	/**
	 * bind, update data with bufferIn, vbo target and drawType and unbind
	 * different from data(Buffer buffer) which only updates the data
	 * @param bufferIn
	 * @return
	 */
	public Vbo updateData(FloatBuffer bufferIn)
	{
		this.bind();
		GL15.glBufferData(this.target(), bufferIn, this.drawType());
		this.unbind();

		return this;
	}

	/**
	 * bind, update data with arrayIn, vbo target and drawType and unbind
	 * different from data(primitive[] arrayIn) which only updates the data
	 * @param bufferIn
	 * @return
	 */
	public Vbo updateData(double[] arrayIn)
	{
		this.bind();
		GL15.glBufferData(this.target(), arrayIn, this.drawType());
		this.unbind();

		return this;
	}

	/**
	 * bind, update data with bufferIn, vbo target and drawType and unbind
	 * different from data(Buffer buffer) which only updates the data
	 * @param bufferIn
	 * @return
	 */
	public Vbo updateData(DoubleBuffer bufferIn)
	{
		this.bind();
		GL15.glBufferData(this.target(), bufferIn, this.drawType());
		this.unbind();

		return this;
	}

	/**
	 * bind, update data with arrayIn, vbo target and drawType and unbind
	 * different from data(primitive[] arrayIn) which only updates the data
	 * @param bufferIn
	 * @return
	 */
	public Vbo updateData(long[] arrayIn)
	{
		this.bind();
		GL15.glBufferData(this.target(), arrayIn, this.drawType());
		this.unbind();

		return this;
	}

	/**
	 * bind, update data with bufferIn, vbo target and drawType and unbind
	 * different from data(Buffer buffer) which only updates the data
	 * @param bufferIn
	 * @return
	 */
	public Vbo updateData(LongBuffer bufferIn)
	{
		this.bind();
		GL15.glBufferData(this.target(), bufferIn, this.drawType());
		this.unbind();

		return this;
	}

	/**
	 * bind, update data with sizeIn, vbo target and drawType and unbind
	 * different from data(long sizeIn) which only updates the data
	 * @param bufferIn
	 * @return
	 */
	public Vbo updateData(long sizeIn)
	{
		this.bind();
		GL15.glBufferData(this.target(), sizeIn, this.drawType());
		this.unbind();

		return this;
	}

	/**
	 * send data with bufferIn, vbo target and drawType
	 * different from updateData(Buffer bufferIn) which bind, update data and unbind
	 * @param bufferIn
	 * @return
	 */
	public Vbo data(ByteBuffer bufferIn)
	{
		GL15.glBufferData(this.target(), bufferIn, this.drawType());

		return this;
	}

	/**
	 * send data with arrayIn, vbo target and drawType
	 * different from updateData(primitive[] arrayIn) which bind, update data and unbind
	 * @param bufferIn
	 * @return
	 */
	public Vbo data(short[] arrayIn)
	{
		GL15.glBufferData(this.target(), arrayIn, this.drawType());

		return this;
	}

	/**
	 * send data with bufferIn, vbo target and drawType
	 * different from updateData(Buffer bufferIn) which bind, update data and unbind
	 * @param bufferIn
	 * @return
	 */
	public Vbo data(ShortBuffer bufferIn)
	{
		GL15.glBufferData(this.target(), bufferIn, this.drawType());

		return this;
	}

	/**
	 * send data with arrayIn, vbo target and drawType
	 * different from updateData(primitive[] arrayIn) which bind, update data and unbind
	 * @param bufferIn
	 * @return
	 */
	public Vbo data(int[] arrayIn)
	{
		GL15.glBufferData(this.target(), arrayIn, this.drawType());

		return this;
	}

	/**
	 * send data with bufferIn, vbo target and drawType
	 * different from updateData(Buffer bufferIn) which bind, update data and unbind
	 * @param bufferIn
	 * @return
	 */
	public Vbo data(IntBuffer bufferIn)
	{
		GL15.glBufferData(this.target(), bufferIn, this.drawType());

		return this;
	}

	/**
	 * send data with arrayIn, vbo target and drawType
	 * different from updateData(primitive[] arrayIn) which bind, update data and unbind
	 * @param bufferIn
	 * @return
	 */
	public Vbo data(float[] arrayIn)
	{
		GL15.glBufferData(this.target(), arrayIn, this.drawType());

		return this;
	}

	/**
	 * send data with bufferIn, vbo target and drawType
	 * different from updateData(Buffer bufferIn) which bind, update data and unbind
	 * @param bufferIn
	 * @return
	 */
	public Vbo data(FloatBuffer bufferIn)
	{
		GL15.glBufferData(this.target(), bufferIn, this.drawType());

		return this;
	}

	/**
	 * send data with arrayIn, vbo target and drawType
	 * different from updateData(primitive[] arrayIn) which bind, update data and unbind
	 * @param bufferIn
	 * @return
	 */
	public Vbo data(double[] arrayIn)
	{
		GL15.glBufferData(this.target(), arrayIn, this.drawType());

		return this;
	}

	/**
	 * send data with bufferIn, vbo target and drawType
	 * different from updateData(Buffer bufferIn) which bind, update data and unbind
	 * @param bufferIn
	 * @return
	 */
	public Vbo data(DoubleBuffer bufferIn)
	{
		GL15.glBufferData(this.target(), bufferIn, this.drawType());

		return this;
	}

	/**
	 * send data with arrayIn, vbo target and drawType
	 * different from updateData(primitive[] arrayIn) which bind, update data and unbind
	 * @param bufferIn
	 * @return
	 */
	public Vbo data(long[] arrayIn)
	{
		GL15.glBufferData(this.target(), arrayIn, this.drawType());

		return this;
	}

	/**
	 * send data with bufferIn, vbo target and drawType
	 * different from updateData(Buffer bufferIn) which bind, update data and unbind
	 * @param bufferIn
	 * @return
	 */
	public Vbo data(LongBuffer bufferIn)
	{
		GL15.glBufferData(this.target(), bufferIn, this.drawType());

		return this;
	}

	/**
	 * send data with sizeIn, vbo target and drawType
	 * different from updateData(long sizeIn) which bind, update data and unbind
	 * @param bufferIn
	 * @return
	 */
	public Vbo data(long sizeIn)
	{
		GL15.glBufferData(this.target(), sizeIn, this.drawType());

		return this;
	}

	public final static void unbindArrayBuffer()
	{
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	final static void deletes(int[] idsIn)
	{
		GL15.glDeleteBuffers(idsIn);
	}

	final static void delete(int idIn)
	{
		GL15.glDeleteBuffers(idIn);
	}

	@Override
	protected int target()
	{
		return this.target;
	}

	private void target(int targetIn)
	{
		this.target = targetIn;
	}
}
