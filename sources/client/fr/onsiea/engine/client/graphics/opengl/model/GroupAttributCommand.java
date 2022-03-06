package fr.onsiea.engine.client.graphics.opengl.model;

import java.util.HashMap;
import java.util.Map;

public abstract class GroupAttributCommand
{
	private Map<String, Integer>	commandsVbos;
	private Map<String, int[]>		commandedVbos;
	private int						total;

	public GroupAttributCommand()
	{
		this.commandsVbos(new HashMap<>());
	}

	public void command(String nameIn, int numberIn)
	{
		this.commandsVbos().put(nameIn, numberIn);

		this.total += numberIn;
	}

	protected abstract int[] commandAll();

	public void commandAllAndDivide()
	{
		this.commandAll();

		this.commandedVbos(new HashMap<>());

		final var	iterator	= this.commandsVbos().entrySet().iterator();

		var			i			= 0;

		while (iterator.hasNext())
		{
			final var	entry		= iterator.next();

			final int	number		= entry.getValue();

			final var	vbosSection	= new int[number];

			for (var i0 = 0; i0 < number; i0++)
			{
				vbosSection[i0] = i + i0;
			}

			this.commandedVbos().put(entry.getKey(), vbosSection);

			i += number;
		}

		this.commandsVbos().clear();
	}

	public int[] getCommand(String nameIn)
	{
		final var command = this.commandedVbos().get(nameIn);

		this.commandedVbos().remove(nameIn);

		return command;
	}

	private Map<String, Integer> commandsVbos()
	{
		return this.commandsVbos;
	}

	private void commandsVbos(Map<String, Integer> commandsVbosIn)
	{
		this.commandsVbos = commandsVbosIn;
	}

	private Map<String, int[]> commandedVbos()
	{
		return this.commandedVbos;
	}

	private void commandedVbos(Map<String, int[]> commandedVbosIn)
	{
		this.commandedVbos = commandedVbosIn;
	}

	protected int total()
	{
		return this.total;
	}

	protected void total(int totalIn)
	{
		this.total = totalIn;
	}
}