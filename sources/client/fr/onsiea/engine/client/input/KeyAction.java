package fr.onsiea.engine.client.input;

import java.util.Objects;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PACKAGE)
public class KeyAction
{
	private KeyData		keyData;
	private TimedAction	timedAction;

	private Keys		lastList;

	public KeyAction(final int idIn, final int scancodeIn, final int modsIn)
	{
		this.keyData		= new KeyData(idIn, scancodeIn, modsIn);

		this.timedAction	= new TimedAction();
	}

	/**
	 * @return
	 * @see fr.onsiea.engine.client.input.KeyAction.KeyData#key()
	 */
	public int id()
	{
		return this.keyData.id();
	}

	/**
	 * @return
	 * @see fr.onsiea.engine.client.input.KeyAction.KeyData#mods()
	 */
	public int mods()
	{
		return this.keyData.mods();
	}

	/**
	 * @return
	 * @see fr.onsiea.engine.client.input.KeyAction.KeyData#scancode()
	 */
	public int scancode()
	{
		return this.keyData.scancode();
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(this.keyData);
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if ((obj == null) || (this.getClass() != obj.getClass()))
		{
			return false;
		}
		final var other = (KeyAction) obj;
		return Objects.equals(this.keyData, other.keyData);
	}

	@AllArgsConstructor
	@Getter
	@Setter(AccessLevel.PACKAGE)
	@EqualsAndHashCode
	public final static class KeyData
	{
		private int	id;
		private int	scancode;
		private int	mods;
	}
}