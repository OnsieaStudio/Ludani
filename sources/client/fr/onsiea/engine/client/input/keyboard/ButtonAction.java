package fr.onsiea.engine.client.input.keyboard;

import java.util.Objects;

import fr.onsiea.engine.client.input.action.BaseButtonAction;
import fr.onsiea.engine.client.input.action.IButtonAction;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Delegate;

public class ButtonAction extends BaseButtonAction implements IButtonAction
{
	private @Getter @Delegate final Key					key;

	private @Getter @Setter(AccessLevel.PACKAGE) Keys	lastList;

	public ButtonAction(final int idIn, final int scancodeIn, final int modsIn)
	{
		this.key = new Key(idIn, scancodeIn, modsIn);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(this.key);
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null || this.getClass() != obj.getClass())
		{
			return false;
		}
		final var other = (ButtonAction) obj;
		return Objects.equals(this.key, other.key);
	}
}