package fr.onsiea.engine.client.graphics.opengl.vbo;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL15;

public class VboManager
{
	private Map<Integer, BaseVbo> vbos;

	public VboManager()
	{
		this.vbos(new HashMap<>());
	}

	public Vbo createArrayBuffer()
	{
		final var vbo = new Vbo();

		this.vbos().put(vbo.id(), vbo);

		return vbo;
	}

	public Vbo createArrayBuffer(int drawTypeIn)
	{
		final var vbo = new Vbo();
		vbo.drawType(drawTypeIn);

		this.vbos().put(vbo.id(), vbo);

		return vbo;
	}

	public Elements createElements()
	{
		final var elements = new Elements();

		this.vbos().put(elements.id(), elements);

		return elements;
	}

	public Elements createElements(int drawTypeIn)
	{
		final var elements = new Elements(drawTypeIn);

		this.vbos().put(elements.id(), elements);

		return elements;
	}

	public Vbo create(int targetIn)
	{
		final var vbo = new Vbo(targetIn);

		this.vbos().put(vbo.id(), vbo);

		return vbo;
	}

	public Vbo create(int targetIn, int drawTypeIn)
	{
		final var vbo = new Vbo(targetIn, drawTypeIn);

		this.vbos().put(vbo.id(), vbo);

		return vbo;
	}

	public Vbo createArrayBufferAndBind()
	{
		final var vbo = new Vbo();

		this.vbos().put(vbo.id(), vbo);

		vbo.bind();

		return vbo;
	}

	public Vbo createArrayBufferAndBind(int drawTypeIn)
	{
		final var vbo = new Vbo();
		vbo.drawType(drawTypeIn);

		this.vbos().put(vbo.id(), vbo);

		vbo.bind();

		return vbo;
	}

	public Elements createElementsAndBind()
	{
		final var elements = new Elements();

		this.vbos().put(elements.id(), elements);

		elements.bind();

		return elements;
	}

	public Elements createElementsAndBind(int drawTypeIn)
	{
		final var elements = new Elements(drawTypeIn);
		elements.drawType(drawTypeIn);

		this.vbos().put(elements.id(), elements);

		elements.bind();

		return elements;
	}

	public BaseVbo createAndBind(int targetIn)
	{
		BaseVbo vbo = null;
		if (targetIn == GL15.GL_ELEMENT_ARRAY_BUFFER)
		{
			vbo = new Elements();
		}
		else
		{
			vbo = new Vbo(targetIn);
		}

		this.vbos().put(vbo.id(), vbo);

		vbo.bind();

		return vbo;
	}

	public BaseVbo createAndBind(int targetIn, int drawTypeIn)
	{
		BaseVbo vbo = null;
		if (targetIn == GL15.GL_ELEMENT_ARRAY_BUFFER)
		{
			vbo = new Elements(drawTypeIn);
		}
		else
		{
			vbo = new Vbo(targetIn, drawTypeIn);
		}

		this.vbos().put(vbo.id(), vbo);

		vbo.bind();

		return vbo;
	}

	public VboManager removeArrayBuffer(int vboIn)
	{
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		this.vbos().remove(vboIn);

		Vbo.delete(vboIn);

		return this;
	}

	public VboManager remove(Vbo vboIn)
	{
		GL15.glBindBuffer(vboIn.target(), 0);

		this.vbos().remove(vboIn.id());

		vboIn.delete();

		return this;
	}

	public void cleanup()
	{
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

		final var	vbos	= new int[this.vbos().size()];
		var			i		= 0;

		for (final BaseVbo vbo : this.vbos().values())
		{
			vbos[i] = vbo.id();

			i++;
		}
		this.vbos().clear();

		Vbo.deletes(vbos);
	}

	private Map<Integer, BaseVbo> vbos()
	{
		return this.vbos;
	}

	private void vbos(Map<Integer, BaseVbo> vbosIn)
	{
		this.vbos = vbosIn;
	}
}