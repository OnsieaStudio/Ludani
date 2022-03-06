package fr.onsiea.engine.client.graphics.opengl.vbo;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL33;

import fr.onsiea.engine.client.graphics.opengl.utils.OpenGLPrimitive;

public class GLDataBuilder
{
	private BaseVbo									vbo;
	private VboManager								vboManager;
	private Map<Integer, VertexAttribPointerGroup>	vertexAttribPointers;
	private VertexAttribPointerGroup				lastVertexAttribPointerGroup;
	private int										globalIndex;

	public GLDataBuilder(VboManager vboManagerIn)
	{
		this.vboManager(vboManagerIn);
		this.vertexAttribPointers(new HashMap<>());
	}

	public BaseVbo newVboAndBind()
	{
		this.vbo(this.vboManager().createArrayBuffer().bind());
		this.lastVertexAttribPointerGroup(new VertexAttribPointerGroup());

		this.vertexAttribPointers().put(this.vbo().id(), this.lastVertexAttribPointerGroup());

		return this.vbo();
	}

	public BaseVbo newVboAndBind(int usageIn)
	{
		this.vbo(this.vboManager().createArrayBuffer(usageIn).bind());
		this.lastVertexAttribPointerGroup(new VertexAttribPointerGroup());

		this.vertexAttribPointers().put(this.vbo().id(), this.lastVertexAttribPointerGroup());

		return this.vbo();
	}

	public BaseVbo newVboAndBind(int targetIn, int usageIn)
	{
		this.vbo(this.vboManager().create(targetIn, usageIn).bind());
		this.lastVertexAttribPointerGroup(new VertexAttribPointerGroup());

		this.vertexAttribPointers().put(this.vbo().id(), this.lastVertexAttribPointerGroup());

		return this.vbo();
	}

	public BaseVbo newIboAndBind()
	{
		this.vbo(this.vboManager().createElements().bind());

		return this.vbo();
	}

	public BaseVbo newIboAndBind(int usageIn)
	{
		this.vbo(this.vboManager().createElements(usageIn).bind());

		return this.vbo();
	}

	public GLDataBuilder data(ByteBuffer bufferIn)
	{
		GL15.glBufferData(this.vbo().target(), bufferIn, this.vbo().drawType());

		this.loadVertexAttribPointers();

		return this;
	}

	public GLDataBuilder data(short[] arrayIn)
	{
		GL15.glBufferData(this.vbo().target(), arrayIn, this.vbo().drawType());

		this.loadVertexAttribPointers();

		return this;
	}

	public GLDataBuilder data(ShortBuffer bufferIn)
	{
		GL15.glBufferData(this.vbo().target(), bufferIn, this.vbo().drawType());

		this.loadVertexAttribPointers();

		return this;
	}

	public GLDataBuilder data(int[] arrayIn)
	{
		GL15.glBufferData(this.vbo().target(), arrayIn, this.vbo().drawType());

		this.loadVertexAttribPointers();

		return this;
	}

	public GLDataBuilder data(IntBuffer bufferIn)
	{
		GL15.glBufferData(this.vbo().target(), bufferIn, this.vbo().drawType());

		this.loadVertexAttribPointers();

		return this;
	}

	public GLDataBuilder data(float[] arrayIn)
	{
		GL15.glBufferData(this.vbo().target(), arrayIn, this.vbo().drawType());

		this.loadVertexAttribPointers();

		return this;
	}

	public GLDataBuilder data(FloatBuffer bufferIn)
	{
		GL15.glBufferData(this.vbo().target(), bufferIn, this.vbo().drawType());

		this.loadVertexAttribPointers();

		return this;
	}

	public GLDataBuilder data(double[] arrayIn)
	{
		GL15.glBufferData(this.vbo().target(), arrayIn, this.vbo().drawType());

		this.loadVertexAttribPointers();

		return this;
	}

	public GLDataBuilder data(DoubleBuffer bufferIn)
	{
		GL15.glBufferData(this.vbo().target(), bufferIn, this.vbo().drawType());

		this.loadVertexAttribPointers();

		return this;
	}

	public GLDataBuilder data(long[] arrayIn)
	{
		GL15.glBufferData(this.vbo().target(), arrayIn, this.vbo().drawType());

		this.loadVertexAttribPointers();

		return this;
	}

	public GLDataBuilder data(LongBuffer bufferIn)
	{
		GL15.glBufferData(this.vbo().target(), bufferIn, this.vbo().drawType());

		this.loadVertexAttribPointers();

		return this;
	}

	public GLDataBuilder data(long sizeIn)
	{
		GL15.glBufferData(this.vbo().target(), sizeIn, this.vbo().drawType());

		this.loadVertexAttribPointers();

		return this;
	}

	public VertexAttribPointer createVertexAttribPointer()
	{
		final var vertexAttribPointer = new VertexAttribPointer(this).index(this.globalIndex());
		this.add(vertexAttribPointer);

		this.increaseGlobalIndex();
		return vertexAttribPointer;
	}

	public VertexAttribPointer createVertexAttribPointer(int sizeIn)
	{
		final var vertexAttribPointer = new VertexAttribPointer(this, sizeIn).index(this.globalIndex());
		this.add(vertexAttribPointer);

		this.increaseGlobalIndex();
		return vertexAttribPointer;
	}

	public VertexAttribPointer createVertexAttribPointerAndDivisor()
	{
		final var vertexAttribPointer = new VertexAttribPointerAndDivisor(this).index(this.globalIndex());
		this.add(vertexAttribPointer);

		this.increaseGlobalIndex();
		return vertexAttribPointer;
	}

	public VertexAttribPointer createVertexAttribPointerAndDivisor(int sizeIn)
	{
		final var vertexAttribPointer = new VertexAttribPointerAndDivisor(this, sizeIn).index(this.globalIndex());
		this.add(vertexAttribPointer);

		this.increaseGlobalIndex();
		return vertexAttribPointer;
	}

	public void divisor()
	{
		if (this.lastVertexAttribPointerGroup() != null)
		{
			this.lastVertexAttribPointerGroup().divisor();
		}
	}

	public void divisor(int divisorIn)
	{
		if (this.lastVertexAttribPointerGroup() != null)
		{
			this.lastVertexAttribPointerGroup().divisor(divisorIn);
		}
	}

	private void add(VertexAttribPointer vertexAttribPointerIn)
	{
		var vertexAttribPointerGroup = this.vertexAttribPointers().get(this.vbo().id());

		if (vertexAttribPointerGroup == null)
		{
			vertexAttribPointerGroup = new VertexAttribPointerGroup();

			this.vertexAttribPointers().put(this.vbo().id(), vertexAttribPointerGroup);
			this.lastVertexAttribPointerGroup(vertexAttribPointerGroup);
		}

		vertexAttribPointerGroup.add(vertexAttribPointerIn);
	}

	public void size(int lastIn, int sizeIn, VertexAttribPointer vertexAttribPointerIn)
	{
		this.lastVertexAttribPointerGroup().size(lastIn, sizeIn, vertexAttribPointerIn);
	}

	private void loadVertexAttribPointers()
	{
		for (final VertexAttribPointerGroup group : this.vertexAttribPointers().values())
		{
			var pointer = 0L;

			for (final VertexAttribPointer vertexAttribPointer : group.list())
			{
				vertexAttribPointer.stride(group.size());
				vertexAttribPointer.pointer(pointer);
				vertexAttribPointer.set();

				pointer += VertexAttribPointer.bytesSizeOf(vertexAttribPointer);
			}
		}

		this.vertexAttribPointers().clear();
	}

	public GLDataBuilder unbind()
	{
		this.vbo().unbind();

		return this;
	}

	public void reset()
	{
		this.vbo(null);
		this.vertexAttribPointers().clear();
		this.lastVertexAttribPointerGroup(null);
		this.globalIndex(0);
	}

	private void increaseGlobalIndex()
	{
		this.globalIndex++;
	}

	private final BaseVbo vbo()
	{
		return this.vbo;
	}

	private final void vbo(BaseVbo vboIn)
	{
		this.vbo = vboIn;
	}

	private final VboManager vboManager()
	{
		return this.vboManager;
	}

	private final void vboManager(VboManager vboManagerIn)
	{
		this.vboManager = vboManagerIn;
	}

	private final Map<Integer, VertexAttribPointerGroup> vertexAttribPointers()
	{
		return this.vertexAttribPointers;
	}

	private final void vertexAttribPointers(Map<Integer, VertexAttribPointerGroup> vertexAttribPointersIn)
	{
		this.vertexAttribPointers = vertexAttribPointersIn;
	}

	private final VertexAttribPointerGroup lastVertexAttribPointerGroup()
	{
		return this.lastVertexAttribPointerGroup;
	}

	private final void lastVertexAttribPointerGroup(VertexAttribPointerGroup lastVertexAttribPointerGroupIn)
	{
		this.lastVertexAttribPointerGroup = lastVertexAttribPointerGroupIn;
	}

	public final int globalIndex()
	{
		return this.globalIndex;
	}

	public final void globalIndex(int indexIn)
	{
		this.globalIndex = indexIn;
	}

	public final static class VertexAttribPointerGroup
	{
		private List<VertexAttribPointer>	list;
		private int							size;
		private VertexAttribPointer			last;

		public VertexAttribPointerGroup()
		{
			this.list(new ArrayList<>());
		}

		public void size(int lastIn, int sizeIn, VertexAttribPointer vertexAttribPointerIn)
		{
			this.size(this.size() - OpenGLPrimitive.bytesSizeOf(vertexAttribPointerIn.type())
					+ OpenGLPrimitive.bytesSizeOf(vertexAttribPointerIn.type()));
		}

		public void add(VertexAttribPointer vertexAttribPointerIn)
		{
			this.last(vertexAttribPointerIn);

			this.size(this.size() + VertexAttribPointer.bytesSizeOf(vertexAttribPointerIn));

			this.list().add(vertexAttribPointerIn);
		}

		public void divisor()
		{
			if (this.list().size() - 1 > 0)
			{
				this.list().remove(this.list().size() - 1);
			}
			if (this.last() instanceof VertexAttribPointerAndDivisor)
			{
				return;
			}
			this.add(new VertexAttribPointerAndDivisor(this.last()));
		}

		public void divisor(int divisorIn)
		{
			if (this.list().size() - 1 > 0)
			{
				this.list().remove(this.list().size() - 1);
			}
			if (this.last() instanceof VertexAttribPointerAndDivisor)
			{
				((VertexAttribPointerAndDivisor) this.last()).divisor(divisorIn);

				return;
			}
			this.add(new VertexAttribPointerAndDivisor(this.last()).divisor(divisorIn));
		}

		private List<VertexAttribPointer> list()
		{
			return this.list;
		}

		private void list(List<VertexAttribPointer> listIn)
		{
			this.list = listIn;
		}

		private int size()
		{
			return this.size;
		}

		private void size(int sizeIn)
		{
			this.size = sizeIn;
		}

		private VertexAttribPointer last()
		{
			return this.last;
		}

		private void last(VertexAttribPointer lastIn)
		{
			this.last = lastIn;
		}
	}

	public static class VertexAttribPointer
	{
		public final static int bytesSizeOf(VertexAttribPointer vertexAttribPointerIn)
		{
			return OpenGLPrimitive.bytesSizeOf(vertexAttribPointerIn.type());
		}

		private GLDataBuilder	dataBuilder;

		private int				index;
		private int				size;
		private int				type;
		private boolean			normalized;
		private int				stride;
		private long			pointer;

		private VertexAttribPointer(GLDataBuilder dataBuilderIn)
		{
			this.dataBuilder(dataBuilderIn);

			this.index(0);
			this.size(3);
			this.type(GL11.GL_FLOAT);
			this.normalized(false);
			this.stride(0);
			this.pointer(0L);
		}

		private VertexAttribPointer(GLDataBuilder dataBuilderIn, int sizeIn)
		{
			this.dataBuilder(dataBuilderIn);

			this.index(0);
			this.size(sizeIn);
			this.type(GL11.GL_FLOAT);
			this.normalized(false);
			this.stride(0);
			this.pointer(0L);
		}

		public VertexAttribPointer set()
		{
			GL20.glVertexAttribPointer(this.index(), this.size(), this.type(), this.normalized(), this.stride(),
					this.pointer());

			return this;
		}

		private GLDataBuilder dataBuilder()
		{
			return this.dataBuilder;
		}

		private void dataBuilder(GLDataBuilder dataBuilderIn)
		{
			this.dataBuilder = dataBuilderIn;
		}

		protected int index()
		{
			return this.index;
		}

		public VertexAttribPointer index(int indexIn)
		{
			this.index = indexIn;

			return this;
		}

		private int size()
		{
			return this.size;
		}

		public VertexAttribPointer size(int sizeIn)
		{
			this.dataBuilder().size(this.size(), sizeIn, this);

			this.size = sizeIn;

			return this;
		}

		private int type()
		{
			return this.type;
		}

		public VertexAttribPointer type(int typeIn)
		{
			this.type = typeIn;

			return this;
		}

		private boolean normalized()
		{
			return this.normalized;
		}

		public VertexAttribPointer normalized(boolean normalizedIn)
		{
			this.normalized = normalizedIn;

			return this;
		}

		private int stride()
		{
			return this.stride;
		}

		public VertexAttribPointer stride(int strideIn)
		{
			this.stride = strideIn;

			return this;
		}

		private long pointer()
		{
			return this.pointer;
		}

		public VertexAttribPointer pointer(long pointerIn)
		{
			this.pointer = pointerIn;

			return this;
		}
	}

	public final static class VertexAttribPointerAndDivisor extends VertexAttribPointer
	{
		private int divisor;

		private VertexAttribPointerAndDivisor(GLDataBuilder dataBuilderIn)
		{
			super(dataBuilderIn);
			this.divisor(1);
		}

		private VertexAttribPointerAndDivisor(GLDataBuilder dataBuilderIn, int sizeIn)
		{
			super(dataBuilderIn, sizeIn);
			this.divisor(1);
		}

		private VertexAttribPointerAndDivisor(VertexAttribPointer vertexAttribPointerIn)
		{
			super(vertexAttribPointerIn.dataBuilder(), vertexAttribPointerIn.size());

			this.type(vertexAttribPointerIn.type());
			this.normalized(vertexAttribPointerIn.normalized());
			this.stride(vertexAttribPointerIn.stride());
			this.pointer(vertexAttribPointerIn.pointer());
			this.divisor(1);
		}

		@Override
		public VertexAttribPointer set()
		{
			super.set();

			GL33.glVertexAttribDivisor(this.index(), this.divisor());

			return this;
		}

		private int divisor()
		{
			return this.divisor;
		}

		public VertexAttribPointer divisor(int divisorIn)
		{
			this.divisor = divisorIn;

			return this;
		}
	}
}