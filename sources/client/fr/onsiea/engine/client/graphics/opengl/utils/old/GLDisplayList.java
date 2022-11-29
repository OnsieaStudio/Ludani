package fr.onsiea.engine.client.graphics.opengl.utils.old;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;

public class GLDisplayList
{
	private Map<String, Integer> displayLists;

	public GLDisplayList()
	{
		this.displayLists(new HashMap<>());
	}

	public void start(String nameIn)
	{
		final var id = GL11.glGenLists(1);
		this.displayLists().put(nameIn, id);
		GL11.glNewList(id, GL11.GL_COMPILE);
	}

	public void end()
	{
		GL11.glEndList();
	}

	public void call(String nameIn)
	{
		if (this.displayLists().containsKey(nameIn))
		{
			final int id = this.displayLists().get(nameIn);
			if (id >= 0)
			{
				this.call(this.displayLists().get(nameIn));
			}
		}
	}

	public void call(int idIn)
	{
		GL11.glCallList(idIn);
	}

	private final Map<String, Integer> displayLists()
	{
		return this.displayLists;
	}

	private final void displayLists(Map<String, Integer> displayListsIn)
	{
		this.displayLists = displayListsIn;
	}
}