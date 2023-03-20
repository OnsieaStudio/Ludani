package fr.onsiea.engine.client.input.action;

import lombok.Getter;
import lombok.NonNull;

public enum EnumSubActionType
{
	JUST_PRESSED(EnumActionType.PRESSED), PRESSED(EnumActionType.PRESSED), PRESSED_FOR_WHILE(EnumActionType.PRESSED),
	JUST_REPEATED(EnumActionType.REPEATED), REPEATED(EnumActionType.REPEATED),
	REPEATED_FOR_WHILE(EnumActionType.REPEATED), JUST_RELEASED(EnumActionType.RELEASED),
	RELEASED(EnumActionType.RELEASED), RELEASED_FOR_WHILE(EnumActionType.RELEASED), NONE(EnumActionType.NONE),
	UNKNOWN(EnumActionType.UNKNOWN);

	private @Getter EnumActionType type;

	@NonNull
	EnumSubActionType(final EnumActionType typeIn)
	{
		this.type = typeIn;
	}

	public boolean isReleased()
	{
		return this.type.isReleased();
	}
}
