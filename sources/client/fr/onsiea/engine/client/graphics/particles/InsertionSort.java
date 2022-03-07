package fr.onsiea.engine.client.graphics.particles;

import java.util.List;

/**
 * A simple implementation of an insertion sort. I implemented this very quickly
 * the other day so it may not be perfect or the most efficient! Feel free to
 * implement your own sorter instead.
 *
 * @author Karl
 *
 */
public class InsertionSort
{
	/**
	 * Sorts a list of particles so that the particles with the highest distance
	 * from the camera are first, and the particles with the shortest distance
	 * are last.
	 *
	 * @param list
	 *            - the list of particles needing sorting.
	 */
	public static <T extends IParticle> void sortHighToLow(List<T> list)
	{
		for (var i = 1; i < list.size(); i++)
		{
			final var item = list.get(i);
			if (item.getSquareDistance() > list.get(i - 1).getSquareDistance())
			{
				InsertionSort.sortUpHighToLow(list, i);
			}
		}
	}

	private static <T extends IParticle> void sortUpHighToLow(List<T> list, int i)
	{
		final var	item		= list.get(i);
		var			attemptPos	= i - 1;
		while (attemptPos != 0 && list.get(attemptPos - 1).getSquareDistance() < item.getSquareDistance())
		{
			attemptPos--;
		}
		list.remove(i);
		list.add(attemptPos, item);
	}
}