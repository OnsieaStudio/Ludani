package fr.onsiea.engine.client.input.action;

import lombok.Getter;

public enum EnumActionType
{
	PRESSED(false), REPEATED(false), RELEASED, NONE, UNKNOWN;

	private @Getter boolean isReleased;

	EnumActionType()
	{
		this.isReleased = true;
	}

	EnumActionType(final boolean isReleasedIn)
	{
		this.isReleased = isReleasedIn;
	}
}
