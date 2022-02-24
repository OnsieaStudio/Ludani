package fr.onsiea.engine.client.input;

public class Key
{
	private int	key;
	private int	scancode;
	private int	mods;

	public Key(int keyIn, int scancodeIn, int modsIn)
	{
		this.id(keyIn);
		this.scancode(scancodeIn);
		this.mods(modsIn);
	}

	public int id()
	{
		return this.key;
	}

	public void id(int keyIn)
	{
		this.key = keyIn;
	}

	public int scancode()
	{
		return this.scancode;
	}

	public void scancode(int scancodeIn)
	{
		this.scancode = scancodeIn;
	}

	public int mods()
	{
		return this.mods;
	}

	public void mods(int modsIn)
	{
		this.mods = modsIn;
	}
}