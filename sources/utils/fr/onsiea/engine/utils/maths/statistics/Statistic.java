package fr.onsiea.engine.utils.maths.statistics;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;

public class Statistic
{
	public static final int	SUPERIOR	= 0;
	public static final int	EQUAL		= 1;
	public static final int	INFERIOR	= 2;

	// With float :

	public static final float average(float... values)
	{
		var average = 0.0F;
		for (final float value : values)
		{
			average += value;
		}

		return average / values.length;
	}

	public static final float average(float[] workforce, float[] values)
	{
		var	average			= 0.0F;
		var	workforceTotal	= 0.0F;
		for (var i = 0; i < values.length; i++)
		{
			average			+= values[i % values.length] * workforce[i % workforce.length];
			workforceTotal	+= workforce[i % workforce.length];
		}

		return average / workforceTotal;
	}

	public static final float median(float... values)
	{
		return values[(values.length - 1) / 2];
	}

	public static final float median(float[] workforce, float[] values)
	{
		var workforceTotal = 0.0F;
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

	public static final float workforceTotal(float[] workforce)
	{
		var workforceTotal = 0.0F;
		for (final float element : workforce)
		{
			workforceTotal += element;
		}
		return workforceTotal;
	}

	public static final float[] cumulativeWorkforce(float[] workforce)
	{
		final var	cumulativeWorkforce	= new float[workforce.length];
		var			cumulativeEffective	= 0.0F;
		var			i					= 0;
		for (final float effective : workforce)
		{
			cumulativeWorkforce[i] = cumulativeEffective += effective;
			i++;
		}
		return cumulativeWorkforce;
	}

	public static final float quartile(float quartile, float... values)
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

	public static final float firstQuartile(float... values)
	{
		return Statistic.quartile(1.0f, values);
	}

	public static final float secondQuartile(float... values)
	{
		return Statistic.quartile(2.0f, values);
	}

	public static final float thirdQuartile(float... values)
	{
		return Statistic.quartile(3.0f, values);
	}

	public static final float fourthQuartile(float... values)
	{
		return Statistic.quartile(4.0f, values);
	}

	public static final float quartile(float quartile, float[] workforce, float[] values)
	{
		var workforceTotal = 0.0F;
		for (var i = 0; i < values.length; i++)
		{
			workforceTotal += workforce[i % workforce.length];
		}
		final var	series	= new float[(int) workforceTotal];
		var			index	= 0;
		for (var i = 0; i < workforce.length; i++)
		{
			for (var x = 0; x < workforce[i]; x++)
			{
				series[index] = values[i % values.length];
				index++;
			}
		}
		final var	q	= quartile * (1.0f / 4.0f) * workforceTotal;
		var			i	= (int) q;
		if (q > i)
		{
			i += 1;
		}
		i -= 1;
		return series[i];
	}

	public static final float firstQuartile(float[] workforce, float[] values)
	{
		return Statistic.quartile(1.0f, workforce, values);
	}

	public static final float secondQuartile(float[] workforce, float[] values)
	{
		return Statistic.quartile(2.0f, workforce, values);
	}

	public static final float thirdQuartile(float[] workforce, float[] values)
	{
		return Statistic.quartile(3.0f, workforce, values);
	}

	public static final float fourthQuartile(float[] workforce, float[] values)
	{
		return Statistic.quartile(4.0f, workforce, values);
	}

	public static final float interquartileRange(float[] workforce, float[] values)
	{
		return Statistic.interquartileRange(Statistic.quartile(1.0f, workforce, values),
				Statistic.quartile(3.0f, workforce, values));
	}

	public static final float interquartileRange(float... values)
	{
		return Statistic.interquartileRange(Statistic.quartile(1.0f, values), Statistic.quartile(3.0f, values));
	}

	public static final float interquartileRange(float firstQuartile, float lastQuartile)
	{
		return lastQuartile - firstQuartile;
	}

	public static final float decile(float decile, float[] values)
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

	public static final Vector2f interquartileInterval(float[] workforce, float[] values)
	{
		return Statistic.interquartileInterval(Statistic.quartile(1.0f, workforce, values),
				Statistic.quartile(3.0f, workforce, values));
	}

	public static final Vector2f interquartileInterval(float... values)
	{
		return Statistic.interquartileInterval(Statistic.quartile(1.0f, values), Statistic.quartile(3.0f, values));
	}

	public static final Vector2f interquartileInterval(float firstQuartile, float lastQuartile)
	{
		return new Vector2f(firstQuartile, lastQuartile);
	}

	public static final float firstDecile(float[] values)
	{
		return Statistic.decile(1.0f, values);
	}

	public static final float secondDecile(float[] values)
	{
		return Statistic.decile(2.0f, values);
	}

	public static final float thirdDecile(float[] values)
	{
		return Statistic.decile(3.0f, values);
	}

	public static final float fourthDecile(float[] values)
	{
		return Statistic.decile(4.0f, values);
	}

	public static final float fifthDecile(float[] values)
	{
		return Statistic.decile(5.0f, values);
	}

	public static final float sixthDecile(float[] values)
	{
		return Statistic.decile(6.0f, values);
	}

	public static final float seventhDecile(float[] values)
	{
		return Statistic.decile(7.0f, values);
	}

	public static final float eighthDecile(float[] values)
	{
		return Statistic.decile(8.0f, values);
	}

	public static final float ninthDecile(float[] values)
	{
		return Statistic.decile(9.0f, values);
	}

	public static final float tenthDecile(float[] values)
	{
		return Statistic.decile(10.0f, values);
	}

	public static final float decile(float decile, float[] workforce, float[] values)
	{
		var workforceTotal = 0.0F;
		for (var i = 0; i < values.length; i++)
		{
			workforceTotal += workforce[i % workforce.length];
		}
		final var	series	= new float[(int) workforceTotal];
		var			index	= 0;
		for (var i = 0; i < workforce.length; i++)
		{
			for (var x = 0; x < workforce[i]; x++)
			{
				series[index] = values[i % values.length];
				index++;
			}
		}
		final var	q	= decile * (1.0f / 4.0f) * workforceTotal;
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

	public static final float firstDecile(float[] workforce, float[] values)
	{
		return Statistic.decile(1.0f, workforce, values);
	}

	public static final float secondDecile(float[] workforce, float[] values)
	{
		return Statistic.decile(2.0f, workforce, values);
	}

	public static final float thirdDecile(float[] workforce, float[] values)
	{
		return Statistic.decile(3.0f, workforce, values);
	}

	public static final float fourthDecile(float[] workforce, float[] values)
	{
		return Statistic.decile(4.0f, workforce, values);
	}

	public static final float fifthDecile(float[] workforce, float[] values)
	{
		return Statistic.decile(5.0f, workforce, values);
	}

	public static final float sixthDecile(float[] workforce, float[] values)
	{
		return Statistic.decile(6.0f, workforce, values);
	}

	public static final float seventhDecile(float[] workforce, float[] values)
	{
		return Statistic.decile(7.0f, workforce, values);
	}

	public static final float eighthDecile(float[] workforce, float[] values)
	{
		return Statistic.decile(8.0f, workforce, values);
	}

	public static final float ninthDecile(float[] workforce, float[] values)
	{
		return Statistic.decile(9.0f, workforce, values);
	}

	public static final float tenthDecile(float[] workforce, float[] values)
	{
		return Statistic.decile(10.0f, workforce, values);
	}

	public static final float interdecileRange(float[] workforce, float[] values)
	{
		return Statistic.interdecileRange(Statistic.decile(1.0f, workforce, values),
				Statistic.decile(9.0f, workforce, values));
	}

	public static final float interdecileRange(float... values)
	{
		return Statistic.interdecileRange(Statistic.decile(1.0f, values), Statistic.decile(9.0f, values));
	}

	public static final float interdecileRange(float firstDecile, float lastDecile)
	{
		return lastDecile - firstDecile;
	}

	public static final float min(float... values)
	{
		var min = Float.POSITIVE_INFINITY;
		for (final float value : values)
		{
			if (value < min)
			{
				min = value;
			}
		}
		return min;
	}

	public static final float max(float... values)
	{
		var max = 0.0F;
		for (final float value : values)
		{
			if (value > max)
			{
				max = value;
			}
		}
		return max;
	}

	public static final Vector2f extremum(float... values)
	{
		var	min	= Float.POSITIVE_INFINITY;
		var	max	= 0.0F;
		for (final float value : values)
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
		return new Vector2f(min, max);
	}

	public static final float range(float... values)
	{
		final var extremum = Statistic.extremum(values);
		return extremum.y - extremum.x;
	}

	public static final float min(float[] workforce, float[] values)
	{
		var			min	= Float.POSITIVE_INFINITY;
		float		fvalue;
		final var	i	= 0;
		for (final float value : values)
		{
			fvalue = value * workforce[i % workforce.length];
			if (fvalue < min)
			{
				min = value;
			}
		}
		return min;
	}

	public static final float max(float[] workforce, float[] values)
	{
		var			max	= 0.0F;
		float		fvalue;
		final var	i	= 0;
		for (final float value : values)
		{
			fvalue = value * workforce[i % workforce.length];
			if (fvalue > max)
			{
				max = value;
			}
		}
		return max;
	}

	public static final Vector2f extremum(float[] workforce, float[] values)
	{
		var		min	= Float.POSITIVE_INFINITY;
		var		max	= 0.0F;
		float	fvalue;
		var		i	= 0;
		for (final float value : values)
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
		return new Vector2f(min, max);
	}

	public static final float range(float[] workforce, float[] values)
	{
		final var extremum = Statistic.extremum(workforce, values);
		return extremum.y - extremum.x;
	}

	public static final float[] ascendingOrder(float... values)
	{
		var	index	= 0;
		var	output	= new float[values.length];
		for (final float value : values)
		{
			for (var i = 0; i < output.length; i++)
			{
				if (value > output[i] && i < index)
				{
					continue;
				}
				output		= Statistic.shift(i, output);
				output[i]	= value;
				index++;
				break;
			}
		}
		return output;
	}

	public static final float[] descendingOrder(float... values)
	{
		var	index	= 0;
		var	output	= new float[values.length];
		for (final float value : values)
		{
			for (var i = 0; i < output.length; i++)
			{
				if (value < output[i] && i < index)
				{
					continue;
				}
				output		= Statistic.shift(i, output);
				output[i]	= value;
				index++;
				break;
			}
		}
		return output;
	}

	public static final float[] shift(int index, float[] array)
	{
		float last = 0.0F, actual = 0.0F;
		for (var i = index; i < array.length; i++)
		{
			actual		= array[i];
			array[i]	= last;
			last		= actual;
		}
		return array;
	}

	public static final float[] shift(int index, float[] array, int d)
	{
		if (d <= 0)
		{
			return Statistic.shift(index, array);
		}
		float		last	= 0.0F, actual = 0.0F;
		final var	output	= new float[array.length];
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

	public static float variance(float... values)
	{
		final var	average		= Statistic.average(values);
		var			variance	= 0.0F;
		for (final float value : values)
		{
			variance += 1 * ((value - average) * (value - average));
		}
		return variance / values.length;
	}

	public static float variance(float[] workforce, float[] values)
	{
		final var	average			= Statistic.average(workforce, values);
		var			workforceTotal	= 0.0F;
		var			variance		= 0.0F;
		var			i				= 0;
		for (final float value : values)
		{
			workforceTotal	+= workforce[i % workforce.length];
			variance		+= workforce[i % workforce.length] * Math.pow(value - average, 2);
			i++;
		}
		return variance / workforceTotal;
	}

	public static float standardDeviation(float... values)
	{
		return (float) Math.sqrt(Statistic.variance(values));
	}

	public static final int compare(float from, float to)
	{
		if (from > to)
		{
			return Statistic.SUPERIOR;
		}
		if (from == to)
		{
			return Statistic.EQUAL;
		}
		return Statistic.INFERIOR;
	}

	public static float standardDeviation(float[] workforce, float[] values)
	{
		return (float) Math.sqrt(Statistic.variance(workforce, values));
	}

	public static final List<Float> sub(List<Float> fromMemoriesIn, List<Float> toMemoriesIn)
	{
		final List<Float> outputMemories = new ArrayList<>();
		for (var i = 0; i < fromMemoriesIn.size(); i++)
		{
			outputMemories.add(fromMemoriesIn.get(i) - toMemoriesIn.get(i % toMemoriesIn.size()));
		}
		return outputMemories;
	}

	public static final double sub(float fromIn, float toIn)
	{
		return fromIn - toIn;
	}
}