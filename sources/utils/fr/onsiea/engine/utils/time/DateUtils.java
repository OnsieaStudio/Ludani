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
public class DateUtils
{
	private final static SimpleDateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy.MM.dd");

	public final static String str()
	{
		return DateUtils.DEFAULT_FORMAT.format(new Date());
	}

	public final static String str(String formatIn)
	{
		return new SimpleDateFormat(formatIn).format(new Date());
	}

	public final static String str(DateFormat dateFormatIn)
	{
		return dateFormatIn.format(new Date());
	}

	private long ms;

	public DateUtils()
	{
		this.setMs(DateUtils.calendar().getTimeInMillis());
	}

	// Methods : -------------------------------------------------------

	/**
	 * @return the ns
	 */
	public long currentNs()
	{
		return this.currentMs() * 1_000_000L;
	}

	/**
	 * @return the second
	 */
	public long currentSecond()
	{
		return this.currentMs() / 1_000L;
	}

	/**
	 * @return the minute
	 */
	public long currentMinute()
	{
		return this.currentMs() / 60_000L;
	}

	/**
	 * @return the hour
	 */
	public long currentHour()
	{
		return this.currentMs() / 3_600_000L;
	}

	/**
	 * @return the day
	 */
	public long currentDay()
	{
		return this.currentMs() / 86_400_000_000L;
	}

	/**
	 * @return the week
	 */
	public long currentWeek()
	{
		return this.currentMs() / 4_492_800_000_000L;
	}

	/**
	 * @return the month
	 */
	public long currentMonth()
	{
		return this.currentDay() / DateUtils.calendar().getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * @return the year
	 */
	public long currentYear()
	{
		return this.currentDay() / 365L;
	}

	@Override
	public String toString()
	{
		return "Data[" + this.currentNs() + "ns " + this.currentMs() + "ms " + this.currentSecond() + "s "
				+ this.currentMinute() + "m " + this.currentHour() + "h " + this.currentDay() + "d "
				+ this.currentWeek() + "w " + this.currentMonth() + "month " + this.currentYear() + "year ]";
	}

	public String toString(final String prefixIn)
	{
		return prefixIn + "Data[" + this.currentNs() + "ns " + this.currentMs() + "ms " + this.currentSecond() + "s "
				+ this.currentMinute() + "m " + this.currentHour() + "h " + this.currentDay() + "d "
				+ this.currentWeek() + "w " + this.currentMonth() + "month " + this.currentYear() + "year ]";
	}

	// -------------------------------------------------------------------------

	// GETTER | SETTER : -------------------------------------------------------

	/**
	 * @return the ms
	 */
	public long currentMs()
	{
		return this.ms;
	}

	/**
	 * @param msIn the ms to set
	 */
	public void setMs(final long msIn)
	{
		this.ms = msIn;
	}

	// -------------------------------------------------------------------------

	private static final Calendar calendar = Calendar.getInstance();

	public final static String getDate()
	{
		return Calendar.getInstance().get(Calendar.MONTH) + 1 + "." + Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
				+ "." + Calendar.getInstance().get(Calendar.HOUR) + "." + Calendar.getInstance().get(Calendar.MINUTE)
				+ "." + (Calendar.getInstance().get(Calendar.SECOND) + 1);
	}

	/**
	 * @return the ns
	 */
	public long ns()
	{
		return DateUtils.calendar().getTimeInMillis() * 1_000_000L;
	}

	/**
	 * @return the ns
	 */
	public long ms()
	{
		return DateUtils.calendar().getTimeInMillis();
	}

	/**
	 * @return the second
	 */
	public long seconds()
	{
		return DateUtils.calendar().getTimeInMillis() / 1_000L;
	}

	/**
	 * @return the minute
	 */
	public long minutes()
	{
		return DateUtils.calendar().getTimeInMillis() / 60_000L;
	}

	/**
	 * @return the hour
	 */
	public long hours()
	{
		return DateUtils.calendar().getTimeInMillis() / 3_600_000L;
	}

	/**
	 * @return the day
	 */
	public long days()
	{
		return this.currentMs() / 86_400_000_000L;
	}

	/**
	 * @return the week
	 */
	public long weeks()
	{
		return this.currentMs() / 4_492_800_000_000L;
	}

	/**
	 * @return the month
	 */
	public long months()
	{
		return this.currentDay() / DateUtils.calendar().getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * @return the year
	 */
	public long years()
	{
		return this.currentDay() / 365L;
	}

	public static final Calendar calendar()
	{
		return DateUtils.calendar;
	}
}