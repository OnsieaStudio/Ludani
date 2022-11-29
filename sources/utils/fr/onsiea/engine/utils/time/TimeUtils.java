/**
*	Copyright 2021 Onsiea All rights reserved.
*
*	This file is part of OnsieaLogger. (https://github.com/Onsiea/OnsieaLogger)
*
*	Unless noted in license (https://github.com/Onsiea/OnsieaLogger/blob/main/LICENSE.md) notice file (https://github.com/Onsiea/OnsieaLogger/blob/main/LICENSE_NOTICE.md), OnsieaLogger and all parts herein is licensed under the terms of the LGPL-3.0 (https://www.gnu.org/licenses/lgpl-3.0.html)  found here https://www.gnu.org/licenses/lgpl-3.0.html and copied below the license file.
*
*	OnsieaLogger is libre software: you can redistribute it and/or modify
*	it under the terms of the GNU Lesser General Public License as published by
*	the Free Software Foundation, either version 3.0 of the License, or
*	(at your option) any later version.
*
*	OnsieaLogger is distributed in the hope that it will be useful,
*	but WITHOUT ANY WARRANTY; without even the implied warranty of
*	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*	GNU Lesser General Public License for more details.
*
*	You should have received a copy of the GNU Lesser General Public License
*	along with OnsieaLogger.  If not, see <https://www.gnu.org/licenses/> <https://www.gnu.org/licenses/lgpl-3.0.html>.
*
*	Neither the name "Onsiea", "OnsieaLogger", or any derivative name or the names of its authors / contributors may be used to endorse or promote products derived from this software and even less to name another project or other work without clear and precise permissions written in advance.
*
*	(more details on https://github.com/Onsiea/OnsieaLogger/wiki/License)
*
*	@author Seynax
*/
package fr.onsiea.engine.utils.time;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Seynax
 *
 */
public class TimeUtils
{
	private final static SimpleDateFormat DEFAULT_FORMAT = new SimpleDateFormat("HH'h'mm ss's'S");

	public final static String str()
	{
		return TimeUtils.DEFAULT_FORMAT.format(new Date());
	}

	public final static String str(String formatIn)
	{
		return new SimpleDateFormat(formatIn).format(new Date());
	}

	public final static String str(DateFormat dateFormatIn)
	{
		return dateFormatIn.format(new Date());
	}

	// VARIABLES : -------------------------------------------------------------

	private static final Calendar	calendar	= Calendar.getInstance();

	private EnumTimes				times;

	// -------------------------------------------------------------------------

	// CLASS CONSTRUCTOR : -----------------------------------------------------

	public TimeUtils()
	{
		this.setTimes(EnumTimes.NANOSECOND);
	}

	public TimeUtils(final EnumTimes timesIn)
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
		return TimeUtils.ns() / 1_000_000_000L;
	}

	public static final long currentSeconds()
	{
		return TimeUtils.ns() / 1_000_000_000L % 60;
	}

	public static final long minutes()
	{
		return TimeUtils.ns() / 60_000_000_000L;
	}

	public static final long currentMinutes()
	{
		return TimeUtils.ns() / 60_000_000_000L % 60;
	}

	public static final long hours()
	{
		return TimeUtils.ns() / 3_600_000_000_000L;
	}

	public static final long currentHours()
	{
		return TimeUtils.ns() / 3_600_000_000_000L % 24;
	}

	public static final long day()
	{
		return TimeUtils.ns() / 86_400_000_000_000L;
	}

	public static final long currentDay()
	{
		return TimeUtils.ns() / 86_400_000_000_000L % TimeUtils.getCalendar().getMaximum(Calendar.DAY_OF_MONTH);
	}

	public static final long month()
	{
		return TimeUtils.ns() / (TimeUtils.getCalendar().getMaximum(Calendar.DAY_OF_MONTH) * 86_400_000_000_000L);
	}

	public static final long currentMonth()
	{
		return TimeUtils.ns() / (TimeUtils.getCalendar().getMaximum(Calendar.DAY_OF_MONTH) * 86_400_000_000_000L)
				% TimeUtils.getCalendar().getMaximum(Calendar.DAY_OF_MONTH);
	}

	public static final long week()
	{
		return TimeUtils.ns() / 604_800_000_000_000L;
	}

	public static final long currentWeek()
	{
		return TimeUtils.ns() / 604_800_000_000_000L % (TimeUtils.getCalendar().getMaximum(Calendar.DAY_OF_MONTH) / 7L);
	}

	public static final long year()
	{
		return TimeUtils.ns() / 31_536_000_000_000_000L;
	}

	public static final long currentYear()
	{
		return TimeUtils.ns() / 31_536_000_000_000_000L % 100;
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
		return TimeUtils.calendar;
	}

	// -------------------------------------------------------------------------

	// CLASS ENUM : ------------------------------------------------------------

	public enum EnumTimes
	{
		NANOSECOND, MILLISECOND(1_000_000L), SECOND(1_000_000_000L), MINUTE(60_000_000_000L), HOUR(3_600_000_000_000L),
		DAY(86_400_000_000_000L), WEEK(604_800_000_000_000L),
		MONTH(TimeUtils.getCalendar().getMaximum(Calendar.DAY_OF_MONTH) * 86_400_000_000_000L),
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