package fr.onsiea.engine.core.network.packet;

import fr.onsiea.engine.core.network.NetworkUtils;
import fr.onsiea.engine.core.network.NetworkUtils.Priority;

public class PacketType
{
	private String		name;

	private Priority	priority;

	private boolean		byGroup;

	public PacketType(final String nameIn)
	{
		this.setName(nameIn);
		this.setPriority(NetworkUtils.MIDDLE);
		this.setGroup(false);
	}

	public PacketType(final String nameIn, final Priority priorityIn)
	{
		this.setName(nameIn);
		this.setPriority(priorityIn);
		this.setGroup(false);
	}

	public PacketType(final String nameIn, final Priority priorityIn, final boolean byGroupIn)
	{
		this.setName(nameIn);
		this.setPriority(priorityIn);
		this.setGroup(byGroupIn);
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(final String nameIn)
	{
		this.name = nameIn;
	}

	public Priority getPriority()
	{
		return this.priority;
	}

	public void setPriority(final Priority priorityIn)
	{
		this.priority = priorityIn;
	}

	public boolean isByGroup()
	{
		return this.byGroup;
	}

	public void setGroup(final boolean byGroupIn)
	{
		this.byGroup = byGroupIn;
	}
}