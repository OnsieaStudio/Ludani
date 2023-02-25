/**
 *
 */
package fr.onsiea.engine.utils;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author seyro
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Triplet<S1, S2, S3>
{
	private S1	s1;
	private S2	s2;
	private S3	s3;

	/**
	 * @param itemIn
	 * @param xIn
	 * @param indexIn
	 * @return
	 */
	public Triplet<S1, S2, S3> set(final S1 s1In, final S2 s2In, final S3 s3In)
	{
		this.s1	= s1In;
		this.s2	= s2In;
		this.s3	= s3In;

		return this;
	}
}
