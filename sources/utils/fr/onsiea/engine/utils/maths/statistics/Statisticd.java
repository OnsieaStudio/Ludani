package fr.onsiea.engine.utils.maths.statistics;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2d;

public class Statisticd
{
	public static final int	SUPERIOR	= 0;
	public static final int	EQUAL		= 1;
	public static final int	INFERIOR	= 2;

	// With double :

	public static final double average(double... values)
	{
		var average = 0.0D;
		for (final double value : values)
		{
			average += value;
		}

		return average / values.length;
	}

	public static final double average(double[] workforce, double[] values)
	{
		var	average			= 0.0D;
		var	workforceTotal	= 0.0D;
		for (var i = 0; i < values.length; i++)
		{
			average			+= values[i % values.length] * workforce[i % workforce.length];
			workforceTotal	+= workforce[i % workforce.length];
		}

		return average / workforceTotal;
	}

	public static final double median(double... values)
	{
		return values[(values.length - 1) / 2];
	}

	public static final double median(double[] workforce, double[] values)
	{
		var workforceTotal = 0.0D;
		for (var i = 0; i < values.length; i++)
		{
			workforceTotal += workforce[i % workforce.length];
		}
		var i = (int) (workforceTotal / 2) - 1;
		if (i >= values.length)
		{
			i = values.length - 1;
		}
		return values[i];
	}

	public static final double workforceTotal(double[] workforce)
	{
		var workforceTotal = 0.0D;
		for (final double element : workforce)
		{
			workforceTotal += element;
		}
		return workforceTotal;
	}

	public static final double[] cumulativeWorkforce(double[] workforce)
	{
		final var	cumulativeWorkforce	= new double[workforce.length];
		var			cumulativeEffective	= 0.0D;
		var			i					= 0;
		for (final double effective : workforce)
		{
			cumulativeWorkforce[i] = cumulativeEffective += effective;
			i++;
		}
		return cumulativeWorkforce;
	}

	public static final double quartile(float quartile, double... values)
	{
		final var	q	= quartile * (1.0f / 4.0f) * values.length;
		var			i	= (int) q;
		if (q > i)
		{
			i += 1;
		}
		i -= 1;
		if (i >= values.length)
		{
			i = values.length - 1;
		}
		return values[i];
	}

	public static final double firstQuartile(double... values)
	{
		return Statisticd.quartile(1.0f, values);
	}

	public static final double secondQuartile(double... values)
	{
		return Statisticd.quartile(2.0f, values);
	}

	public static final double thirdQuartile(double... values)
	{
		return Statisticd.quartile(3.0f, values);
	}

	public static final double fourthQuartile(double... values)
	{
		return Statisticd.quartile(4.0f, values);
	}

	public static final double quartile(float quartile, double[] workforce, double[] values)
	{
		var workforceTotal = 0.0D;
		for (var i = 0; i < values.length; i++)
		{
			workforceTotal += workforce[i % workforce.length];
		}
		final var	series	= new double[(int) workforceTotal];
		var			index	= 0;
		for (var i = 0; i < workforce.length; i++)
		{
			for (var x = 0; x < workforce[i]; x++)
			{
				series[index] = values[i % values.length];
				index++;
			}
		}
		final var	q	= (float) (quartile * (1.0f / 4.0f) * workforceTotal);
		var			i	= (int) q;
		if (q > i)
		{
			i += 1;
		}
		i -= 1;
		return series[i];
	}

	public static final double firstQuartile(double[] workforce, double[] values)
	{
		return Statisticd.quartile(1.0f, workforce, values);
	}

	public static final double secondQuartile(double[] workforce, double[] values)
	{
		return Statisticd.quartile(2.0f, workforce, values);
	}

	public static final double thirdQuartile(double[] workforce, double[] values)
	{
		return Statisticd.quartile(3.0f, workforce, values);
	}

	public static final double fourthQuartile(double[] workforce, double[] values)
	{
		return Statisticd.quartile(4.0f, workforce, values);
	}

	public static final double interquartileRange(double[] workforce, double[] values)
	{
		return Statisticd.interquartileRange(Statisticd.quartile(1.0f, workforce, values),
				Statisticd.quartile(3.0f, workforce, values));
	}

	public static final double interquartileRange(double... values)
	{
		return Statisticd.interquartileRange(Statisticd.quartile(1.0f, values), Statisticd.quartile(3.0f, values));
	}

	public static final double interquartileRange(double firstQuartile, double lastQuartile)
	{
		return lastQuartile - firstQuartile;
	}

	public static final double decile(float decile, double[] values)
	{
		final var	q	= decile * (1.0f / 10.0f) * values.length;
		var			i	= (int) q;
		if (q > i)
		{
			i += 1;
		}
		i -= 1;
		if (i >= values.length)
		{
			i = values.length - 1;
		}
		return values[i];
	}

	public static final Vector2d interquartileInterval(double[] workforce, double[] values)
	{
		return Statisticd.interquartileInterval(Statisticd.quartile(1.0f, workforce, values),
				Statisticd.quartile(3.0f, workforce, values));
	}

	public static final Vector2d interquartileInterval(double... values)
	{
		return Statisticd.interquartileInterval(Statisticd.quartile(1.0f, values), Statisticd.quartile(3.0f, values));
	}

	public static final Vector2d interquartileInterval(double firstQuartile, double lastQuartile)
	{
		return new Vector2d(firstQuartile, lastQuartile);
	}

	public static final double firstDecile(double[] values)
	{
		return Statisticd.decile(1.0f, values);
	}

	public static final double secondDecile(double[] values)
	{
		return Statisticd.decile(2.0f, values);
	}

	public static final double thirdDecile(double[] values)
	{
		return Statisticd.decile(3.0f, values);
	}

	public static final double fourthDecile(double[] values)
	{
		return Statisticd.decile(4.0f, values);
	}

	public static final double fifthDecile(double[] values)
	{
		return Statisticd.decile(5.0f, values);
	}

	public static final double sixthDecile(double[] values)
	{
		return Statisticd.decile(6.0f, values);
	}

	public static final double seventhDecile(double[] values)
	{
		return Statisticd.decile(7.0f, values);
	}

	public static final double eighthDecile(double[] values)
	{
		return Statisticd.decile(8.0f, values);
	}

	public static final double ninthDecile(double[] values)
	{
		return Statisticd.decile(9.0f, values);
	}

	public static final double tenthDecile(double[] values)
	{
		return Statisticd.decile(10.0f, values);
	}

	public static final double decile(float decile, double[] workforce, double[] values)
	{
		var workforceTotal = 0.0D;
		for (var i = 0; i < values.length; i++)
		{
			workforceTotal += workforce[i % workforce.length];
		}
		final var	series	= new double[(int) workforceTotal];
		var			index	= 0;
		for (var i = 0; i < workforce.length; i++)
		{
			for (var x = 0; x < workforce[i]; x++)
			{
				series[index] = values[i % values.length];
				index++;
			}
		}
		final var	q	= (float) (decile * (1.0f / 4.0f) * workforceTotal);
		var			i	= (int) q;
		if (q > i)
		{
			i += 1;
		}
		i -= 1;
		if (i >= series.length)
		{
			i = series.length - 1;
		}
		return series[i];
	}

	public static final double firstDecile(double[] workforce, double[] values)
	{
		return Statisticd.decile(1.0f, workforce, values);
	}

	public static final double secondDecile(double[] workforce, double[] values)
	{
		return Statisticd.decile(2.0f, workforce, values);
	}

	public static final double thirdDecile(double[] workforce, double[] values)
	{
		return Statisticd.decile(3.0f, workforce, values);
	}

	public static final double fourthDecile(double[] workforce, double[] values)
	{
		return Statisticd.decile(4.0f, workforce, values);
	}

	public static final double fifthDecile(double[] workforce, double[] values)
	{
		return Statisticd.decile(5.0f, workforce, values);
	}

	public static final double sixthDecile(double[] workforce, double[] values)
	{
		return Statisticd.decile(6.0f, workforce, values);
	}

	public static final double seventhDecile(double[] workforce, double[] values)
	{
		return Statisticd.decile(7.0f, workforce, values);
	}

	public static final double eighthDecile(double[] workforce, double[] values)
	{
		return Statisticd.decile(8.0f, workforce, values);
	}

	public static final double ninthDecile(double[] workforce, double[] values)
	{
		return Statisticd.decile(9.0f, workforce, values);
	}

	public static final double tenthDecile(double[] workforce, double[] values)
	{
		return Statisticd.decile(10.0f, workforce, values);
	}

	public static final double interdecileRange(double[] workforce, double[] values)
	{
		return Statisticd.interdecileRange(Statisticd.decile(1.0f, workforce, values),
				Statisticd.decile(9.0f, workforce, values));
	}

	public static final double interdecileRange(double... values)
	{
		return Statisticd.interdecileRange(Statisticd.decile(1.0f, values), Statisticd.decile(9.0f, values));
	}

	public static final double interdecileRange(double firstDecile, double lastDecile)
	{
		return lastDecile - firstDecile;
	}

	public static final double min(double... values)
	{
		var min = Double.POSITIVE_INFINITY;
		for (final double value : values)
		{
			if (value < min)
			{
				min = value;
			}
		}
		return min;
	}

	public static final double max(double... values)
	{
		var max = 0.0D;
		for (final double value : values)
		{
			if (value > max)
			{
				max = value;
			}
		}
		return max;
	}

	public static final Vector2d extremum(double... values)
	{
		var	min	= Double.POSITIVE_INFINITY;
		var	max	= 0.0D;
		for (final double value : values)
		{
			if (value < min)
			{
				min = value;
			}
			if (value > max)
			{
				max = value;
			}
		}
		return new Vector2d(min, max);
	}

	public static final double range(double... values)
	{
		final var extremum = Statisticd.extremum(values);
		return extremum.y - extremum.x;
	}

	public static final double min(double[] workforce, double[] values)
	{
		var			min	= Double.POSITIVE_INFINITY;
		double		fvalue;
		final var	i	= 0;
		for (final double value : values)
		{
			fvalue = value * workforce[i % workforce.length];
			if (fvalue < min)
			{
				min = value;
			}
		}
		return min;
	}

	public static final double max(double[] workforce, double[] values)
	{
		var			max	= 0.0D;
		double		fvalue;
		final var	i	= 0;
		for (final double value : values)
		{
			fvalue = value * workforce[i % workforce.length];
			if (fvalue > max)
			{
				max = value;
			}
		}
		return max;
	}

	public static final Vector2d extremum(double[] workforce, double[] values)
	{
		var		min	= Double.POSITIVE_INFINITY;
		var		max	= 0.0D;
		double	fvalue;
		var		i	= 0;
		for (final double value : values)
		{
			fvalue = value * workforce[i % workforce.length];
			if (fvalue < min)
			{
				min = fvalue;
			}
			if (fvalue > max)
			{
				max = fvalue;
			}
			i++;
		}
		return new Vector2d(min, max);
	}

	public static final double range(double[] workforce, double[] values)
	{
		final var extremum = Statisticd.extremum(workforce, values);
		return extremum.y - extremum.x;
	}

	public static final double[] ascendingOrder(double... values)
	{
		var	index	= 0;
		var	output	= new double[values.length];
		for (final double value : values)
		{
			for (var i = 0; i < output.length; i++)
			{
				if (value > output[i] && i < index)
				{
					continue;
				}
				output		= Statisticd.shift(i, output);
				output[i]	= value;
				index++;
				break;
			}
		}
		return output;
	}

	public static final double[] descendingOrder(double... values)
	{
		var	index	= 0;
		var	output	= new double[values.length];
		for (final double value : values)
		{
			for (var i = 0; i < output.length; i++)
			{
				if (value < output[i] && i < index)
				{
					continue;
				}
				output		= Statisticd.shift(i, output);
				output[i]	= value;
				index++;
				break;
			}
		}
		return output;
	}

	public static final double[] shift(int index, double[] array)
	{
		double last = 0.0D, actual = 0.0D;
		for (var i = index; i < array.length; i++)
		{
			actual		= array[i];
			array[i]	= last;
			last		= actual;
		}
		return array;
	}

	public static final double[] shift(int index, double[] array, int d)
	{
		if (d <= 0)
		{
			return Statisticd.shift(index, array);
		}
		double		last	= 0.0D, actual = 0.0D;
		final var	output	= new double[array.length];
		for (var i = 0; i < array.length - d - 1; i++)
		{
			if (i < index)
			{
				output[i] = array[i];
			}
			else
			{
				actual				= array[i];
				output[i + d - 1]	= last;
				last				= actual;
			}
		}
		return output;
	}

	public static double variance(double... values)
	{
		final var	average		= Statisticd.average(values);
		var			variance	= 0.0D;
		for (final double value : values)
		{
			variance += 1 * ((value - average) * (value - average));
		}
		return variance / values.length;
	}

	public static double variance(double[] workforce, double[] values)
	{
		final var	average			= Statisticd.average(workforce, values);
		var			workforceTotal	= 0.0D;
		var			variance		= 0.0D;
		var			i				= 0;
		for (final double value : values)
		{
			workforceTotal	+= workforce[i % workforce.length];
			variance		+= workforce[i % workforce.length] * Math.pow(value - average, 2);
			i++;
		}
		return variance / workforceTotal;
	}

	public static double standardDeviation(double... values)
	{
		return Math.sqrt(Statisticd.variance(values));
	}

	public static double standardDeviation(double[] workforce, double[] values)
	{
		return Math.sqrt(Statisticd.variance(workforce, values));
	}

	public static final int compare(double from, double to)
	{
		if (from > to)
		{
			return Statisticd.SUPERIOR;
		}
		if (from == to)
		{
			return Statisticd.EQUAL;
		}
		return Statisticd.INFERIOR;
	}

	public static final List<Double> sub(List<Double> fromMemoriesIn, List<Double> toMemoriesIn)
	{
		final List<Double> outputMemories = new ArrayList<>();
		for (var i = 0; i < fromMemoriesIn.size(); i++)
		{
			outputMemories.add(fromMemoriesIn.get(i) - toMemoriesIn.get(i % toMemoriesIn.size()));
		}
		return outputMemories;
	}

	public static final double sub(double fromIn, double toIn)
	{
		return fromIn - toIn;
	}
}