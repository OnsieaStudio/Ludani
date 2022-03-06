package fr.onsiea.engine.client.graphics.opengl.vao;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL30;

public class VaoManager
{
	private Map<Integer, Vao> vaos;

	public VaoManager()
	{
		this.vaos(new HashMap<>());
	}

	public Vao create()
	{
		final var vao = VaoUtils.create();

		this.vaos().put(vao.id(), vao);

		return vao;
	}

	public Vao createBindAndEnables(int attribIn)
	{
		final var vao = VaoUtils.createBindAndEnables(attribIn);

		this.vaos().put(vao.id(), vao);

		return vao;
	}

	public Vao createBindAndEnablesFromTo(int startAttribIn, int attribIn)
	{
		final var vao = VaoUtils.createBindAndEnablesFromTo(startAttribIn, attribIn);

		this.vaos().put(vao.id(), vao);

		return vao;
	}

	public VaoManager remove(int arrayIn)
	{
		GL30.glBindVertexArray(0);

		//final Vao vao = this.vaos().get(arrayIn);
		//int array = arrayIn;

		//if (vao != null)
		//{
		// array = vao.id();

		this.vaos().remove(arrayIn);
		//}

		VaoUtils.delete(arrayIn);

		return this;
	}

	public void remove(Vao vaoIn)
	{
		GL30.glBindVertexArray(0);

		this.vaos().remove(vaoIn.id());

		VaoUtils.delete(vaoIn.id());
	}

	public void cleanup()
	{
		GL30.glBindVertexArray(0);

		final var	vaos	= new int[this.vaos().size()];
		var			i		= 0;

		for (final Vao vao : this.vaos().values())
		{
			vaos[i] = vao.id();

			i++;
		}
		this.vaos().clear();

		VaoUtils.deletes(vaos);
	}

	private Map<Integer, Vao> vaos()
	{
		return this.vaos;
	}

	private void vaos(Map<Integer, Vao> vaosIn)
	{
		this.vaos = vaosIn;
	}
}