/**
 *
 */
package fr.onsiea.engine.utils.time;

import java.util.Calendar;

/**
 * @author Seynax
 *
 */
public class Time
{
	// VARIABLES : -------------------------------------------------------------

	private static final Calendar	calendar	= Calendar.getInstance();

	private EnumTimes				times;

	// -------------------------------------------------------------------------

	// CLASS CONSTRUCTOR : -----------------------------------------------------

	public Time()
	{
		this.setTimes(EnumTimes.NANOSECOND);
	}

	public Time(final EnumTimes timesIn)
	{
		this.setTimes(timesIn);
	}

	// -------------------------------------------------------------------------

	// CLASS METHODS : ---------------------------------------------------------

	public static final long ns()
	{
		return System.nanoTime();
	}

	public static final long currentNs()
	{
		return System.nanoTime() % 1_000_000L;
	}

	public static final long ms()
	{
		return System.nanoTime() / 1_000_000L;
	}

	public static final long currentMs()
	{
		return System.nanoTime() / 1_000_000L % 1_000L;
	}

	public static final long seconds()
	{
		return Time.ns() / 1_000_000_000L;
	}

	public static final long currentSeconds()
	{
		return Time.ns() / 1_000_000_000L % 60;
	}

	public static final long minutes()
	{
		return Time.ns() / 60_000_000_000L;
	}

	public static final long currentMinutes()
	{
		return Time.ns() / 60_000_000_000L % 60;
	}

	public static final long hours()
	{
		return Time.ns() / 3_600_000_000_000L;
	}

	public static final long currentHours()
	{
		return Time.ns() / 3_600_000_000_000L % 24;
	}

	public static final long day()
	{
		return Time.ns() / 86_400_000_000_000L;
	}

	public static final long currentDay()
	{
		return Time.ns() / 86_400_000_000_000L % Time.getCalendar().getMaximum(Calendar.DAY_OF_MONTH);
	}

	public static final long month()
	{
		return Time.ns() / (Time.getCalendar().getMaximum(Calendar.DAY_OF_MONTH) * 86_400_000_000_000L);
	}

	public static final long currentMonth()
	{
		return Time.ns() / (Time.getCalendar().getMaximum(Calendar.DAY_OF_MONTH) * 86_400_000_000_000L)
				% Time.getCalendar().getMaximum(Calendar.DAY_OF_MONTH);
	}

	public static final long week()
	{
		return Time.ns() / 604_800_000_000_000L;
	}

	public static final long currentWeek()
	{
		return Time.ns() / 604_800_000_000_000L % (Time.getCalendar().getMaximum(Calendar.DAY_OF_MONTH) / 7L);
	}

	public static final long year()
	{
		return Time.ns() / 31_536_000_000_000_000L;
	}

	public static final long currentYear()
	{
		return Time.ns() / 31_536_000_000_000_000L % 100;
	}

	// -------------------------------------------------------------------------

	// GETTER | SETTER : -------------------------------------------------------

	/**
	 * @return the times
	 */
	public EnumTimes getTimes()
	{
		return this.times;
	}

	/**
	 * @param timesIn the times to set
	 */
	private void setTimes(final EnumTimes timesIn)
	{
		this.times = timesIn;
	}

	// -------------------------------------------------------------------------

	// STATIC GETTER | SETTER : ------------------------------------------------

	/**
	 * @return the calendar
	 */
	public static Calendar getCalendar()
	{
		return Time.calendar;
	}

	// -------------------------------------------------------------------------

	// CLASS ENUM : ------------------------------------------------------------

	public enum EnumTimes
	{
		NANOSECOND, MILLISECOND(1_000_000L), SECOND(1_000_000_000L), MINUTE(60_000_000_000L), HOUR(3_600_000_000_000L),
		DAY(86_400_000_000_000L), WEEK(604_800_000_000_000L),
		MONTH(Time.getCalendar().getMaximum(Calendar.DAY_OF_MONTH) * 86_400_000_000_000L),
		YEAR(31_536_000_000_000_000L), CENTURY(31_536_000_000_000_000_00L);

		private long value;

		EnumTimes()
		{
			this.setValue(1L);
		}

		EnumTimes(final long nsIn)
		{
			this.setValue(nsIn);
		}

		/**
		 * @param nsIn
		 * @return
		 */
		public final long convert(final long nsIn)
		{
			return nsIn / this.getValue();
		}

		public static final long convertInNs(final EnumTimes timesIn, final long valueIn)
		{
			return valueIn * timesIn.getValue();
		}

		public final long convert(final EnumTimes timesIn, final long valueIn)
		{
			return valueIn * timesIn.getValue() / this.getValue();
		}

		/**
		 * @return the nsIn
		 */
		public long getValue()
		{
			return this.value;
		}

		/**
		 * @param nsInIn the nsIn to set
		 */
		private void setValue(final long valueIn)
		{
			this.value = valueIn;
		}
	}

	// -------------------------------------------------------------------------
}
