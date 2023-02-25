package fr.onsiea.engine.client.graphics.opengl.instanced;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL33;

import fr.onsiea.engine.utils.maths.MathInstances;
import lombok.AccessLevel;
import lombok.Getter;

public class Instanced
{
	public final int	vao;
	private final int	vertexCount;
	private final int	primCount;
	private final int	indexes;

	private Instanced(final int vaoIn, final int indexesIn, final int vertexCountIn, final int primCountIn)
	{
		this.vao			= vaoIn;
		this.indexes		= indexesIn;
		this.vertexCount	= vertexCountIn;
		this.primCount		= primCountIn;
	}

	public void enableVertexAttribs()
	{
		for (var i = 0; i <= this.indexes; i++)
		{
			GL20.glEnableVertexAttribArray(i);
		}
	}

	public final void bind()
	{
		GL30.glBindVertexArray(this.vao);

		this.enableVertexAttribs();
	}

	public void draw(int modeIn)
	{	
		GL31.glDrawElementsInstanced(modeIn, this.vertexCount, GL11.GL_UNSIGNED_INT, 0L, this.primCount);
	}

	public final void draw()
	{
		GL31.glDrawElementsInstanced(GL11.GL_TRIANGLES, this.vertexCount, GL11.GL_UNSIGNED_INT, 0L, this.primCount);
	}

	public final void bindDraw()
	{
		this.bind();
		this.draw();
	}

	public final void drawUnbind()
	{
		this.draw();
		this.unbind();
	}

	public final void bindDrawUnbind()
	{
		this.bind();
		this.draw();
		this.unbind();
	}

	public final void unbind()
	{
		this.disableVertexAttribs();

		GL30.glBindVertexArray(0);
	}

	public void disableVertexAttribs()
	{
		for (var i = this.indexes - 1; i >= 0; i--)
		{
			GL20.glDisableVertexAttribArray(i);
		}
	}

	@Getter(AccessLevel.PACKAGE)
	public final static class Builder
	{
		private int					currentIndex;
		private final int			vao;
		public final List<Integer>	datas;

		public Builder()
		{
			this.datas	= new ArrayList<>();
			this.vao	= GL30.glGenVertexArrays();
			GL30.glBindVertexArray(this.vao);
		}

		public Builder indices(final int[] dataIn)
		{
			final var dataId = GL15.glGenBuffers();
			this.datas.add(dataId);
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, dataId);
			GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, dataIn, GL15.GL_DYNAMIC_DRAW);

			return this;
		}

		public Builder data(final int[] dataIn, final int sizeIn)
		{
			final var dataId = GL15.glGenBuffers();
			this.datas.add(dataId);
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, dataId);
			GL20.glEnableVertexAttribArray(this.currentIndex);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, dataIn, GL15.GL_DYNAMIC_DRAW);
			GL20.glVertexAttribPointer(this.currentIndex, sizeIn, GL11.GL_INT, false, 0, 0L);
			this.currentIndex++;

			return this;
		}

		public Builder data(final float[] dataIn, final int sizeIn)
		{
			final var dataId = GL15.glGenBuffers();
			this.datas.add(dataId);
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, dataId);
			GL20.glEnableVertexAttribArray(this.currentIndex);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, dataIn, GL15.GL_DYNAMIC_DRAW);
			GL20.glVertexAttribPointer(this.currentIndex, sizeIn, GL11.GL_FLOAT, false, 0, 0L);
			this.currentIndex++;

			return this;
		}

		public int instanced = -1;

		public Builder interleaveData(final FloatBuffer dataIn, final int sizeIn)
		{
			final var dataId = GL15.glGenBuffers();
			this.instanced = dataId;
			this.datas.add(dataId);
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, dataId);

			if (sizeIn > 4)
			{
				for (var i = 0; i < sizeIn / 4; i++)
				{
					GL20.glEnableVertexAttribArray(this.currentIndex);
					GL20.glVertexAttribPointer(this.currentIndex, 4, GL11.GL_FLOAT, false, sizeIn * 4,
							i * MathInstances.vector4fSizeBytes());
					GL33.glVertexAttribDivisor(this.currentIndex, 1);
					this.currentIndex++;
				}

				final var i = sizeIn % 4;

				if (i > 0)
				{
					GL20.glEnableVertexAttribArray(this.currentIndex);
					GL20.glVertexAttribPointer(this.currentIndex, i, GL11.GL_FLOAT, false, sizeIn * 4,
							(sizeIn - i) * 4);
					GL33.glVertexAttribDivisor(this.currentIndex, 1);
					this.currentIndex++;
				}
			}
			else
			{
				GL20.glEnableVertexAttribArray(this.currentIndex);
				GL20.glVertexAttribPointer(this.currentIndex, sizeIn * 4, GL11.GL_FLOAT, false, 0, 0L);
				GL33.glVertexAttribDivisor(this.currentIndex, 1);
				this.currentIndex++;
			}
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, dataIn, GL15.GL_DYNAMIC_DRAW);

			return this;
		}

		public Builder unbind()
		{
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

			return this;
		}

		public Instanced build(final int vertexCountIn, final int primCountIn)
		{
			GL30.glBindVertexArray(0);
			for (var i = this.currentIndex - 1; i >= 0; i--)
			{
				GL20.glDisableVertexAttribArray(i);
			}

			return new Instanced(this.vao, this.currentIndex - 1, vertexCountIn, primCountIn);
		}
	}
}