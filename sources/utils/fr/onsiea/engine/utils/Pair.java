package fr.onsiea.engine.utils;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Pair<S1, S2>
{
	private S1	s1;
	private S2	s2;

	/**
	 * @param itemIn
	 * @param indexIn
	 * @return
	 */
	public Pair<S1, S2> set(final S1 s1In, final S2 s2In)
	{
		this.s1	= s1In;
		this.s2	= s2In;

		return this;
	}
}