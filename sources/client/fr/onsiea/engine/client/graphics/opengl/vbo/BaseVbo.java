package fr.onsiea.engine.client.graphics.opengl.vbo;

import org.lwjgl.opengl.GL15;

public abstract class BaseVbo
{
	private int	id;
	private int	drawType;

	protected BaseVbo()
	{
		this.id(GL15.glGenBuffers());
		this.drawType(GL15.GL_STATIC_DRAW);
	}

	protected BaseVbo(int drawTypeIn)
	{
		this.id(GL15.glGenBuffers());
		this.drawType(drawTypeIn);
	}

	public BaseVbo bind()
	{
		GL15.glBindBuffer(this.target(), this.id());

		return this;
	}

	public BaseVbo unbind()
	{
		GL15.glBindBuffer(this.target(), 0);

		return this;
	}

	void delete()
	{
		GL15.glDeleteBuffers(this.id());
	}

	int id()
	{
		return this.id;
	}

	protected void id(int idIn)
	{
		this.id = idIn;
	}

	int drawType()
	{
		return this.drawType;
	}

	public void drawType(int drawTypeIn)
	{
		this.drawType = drawTypeIn;
	}

	protected abstract int target();
}
