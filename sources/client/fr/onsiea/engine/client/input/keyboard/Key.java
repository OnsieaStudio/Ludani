package fr.onsiea.engine.client.input.keyboard;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter(AccessLevel.PACKAGE)
@EqualsAndHashCode
public class Key
{
	private int	id;
	private int	scancode;
	private int	mods;
}