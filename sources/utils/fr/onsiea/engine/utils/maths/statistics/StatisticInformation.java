package fr.onsiea.engine.utils.maths.statistics;

import java.util.Collection;
import java.util.List;

import org.joml.Vector2f;

public class StatisticInformation
{
	private float		min;
	private float		max;

	private float		average;
	private float		median;
	private float		range;

	private float		firstQuartile;
	private float		secondQuartile;
	private float		thirdQuartile;
	private float		fourthQuartile;
	private Vector2f	interquartileInterval;
	private float		interquartileRange;

	private float		firstDecile;
	private float		secondDecile;
	private float		thirdDecile;
	private float		fourthDecile;
	private float		fifthDecile;
	private float		sixthDecile;
	private float		seventhDecile;
	private float		eighthDecile;
	private float		ninthDecile;
	private float		tenthDecile;
	private Vector2f	interdecileInterval;
	private float		interdecileRange;

	private float		variance;
	private float		standartDeviation;

	public StatisticInformation(Collection<Float> listSerieIn)
	{
		final var	series	= new float[listSerieIn.size()];
		var			i		= 0;
		for (final float value : series)
		{
			series[i] = value;
			i++;
		}

		this.setMin(Float.POSITIVE_INFINITY);
		this.setMax(0.0F);
		this.setAverage(0.0F);
		for (final float value : series)
		{
			if (value < this.getMin())
			{
				this.setMin(value);
			}
			if (value > this.getMax())
			{
				this.setMax(value);
			}
			this.setAverage(this.getAverage() + value);
		}

		this.setVariance(0.0F);
		for (final float value : series)
		{
			this.setVariance((float) (this.getVariance() + 1.0f * Math.pow(value - this.getAverage(), 2)));
		}
		this.setAverage(this.getAverage() / series.length);
		this.setVariance(this.getVariance() / series.length);

		this.setMedian(series[(series.length - 1) / 2]);
		this.setRange(this.getMax() - this.getMin());
		this.setStandartDeviation((float) Math.sqrt(this.getVariance()));

		this.setFirstQuartile(Statistic.firstQuartile(series));
		this.setSecondQuartile(Statistic.secondQuartile(series));
		this.setThirdQuartile(Statistic.thirdQuartile(series));
		this.setFourthQuartile(Statistic.fourthQuartile(series));
		this.setInterquartileInterval(new Vector2f(this.getFirstQuartile(), this.getThirdQuartile()));
		this.setInterquartileRange(this.getInterquartileInterval().y - this.getInterquartileInterval().x);

		this.setFirstDecile(Statistic.firstDecile(series));
		this.setSecondDecile(Statistic.secondDecile(series));
		this.setThirdDecile(Statistic.thirdDecile(series));
		this.setFourthDecile(Statistic.fourthDecile(series));
		this.setFifthDecile(Statistic.fifthDecile(series));
		this.setSixthDecile(Statistic.sixthDecile(series));
		this.setSeventhDecile(Statistic.seventhDecile(series));
		this.setEighthDecile(Statistic.eighthDecile(series));
		this.setNinthDecile(Statistic.ninthDecile(series));
		this.setTenthDecile(Statistic.tenthDecile(series));
		this.setInterdecileInterval(new Vector2f(this.getFirstDecile(), this.getThirdDecile()));
		this.setInterdecileRange(this.getInterdecileInterval().y - this.getInterdecileInterval().x);
	}

	public StatisticInformation(List<Float> listSerieIn)
	{
		final var	series	= new float[listSerieIn.size()];
		var			i		= 0;
		for (final float value : series)
		{
			series[i] = value;
			i++;
		}

		this.setMin(Float.POSITIVE_INFINITY);
		this.setMax(0.0F);
		this.setAverage(0.0F);
		for (final float value : series)
		{
			if (value < this.getMin())
			{
				this.setMin(value);
			}
			if (value > this.getMax())
			{
				this.setMax(value);
			}
			this.setAverage(this.getAverage() + value);
		}

		this.setVariance(0.0F);
		for (final float value : series)
		{
			this.setVariance((float) (this.getVariance() + 1.0f * Math.pow(value - this.getAverage(), 2)));
		}
		this.setAverage(this.getAverage() / series.length);
		this.setVariance(this.getVariance() / series.length);

		this.setMedian(series[(series.length - 1) / 2]);
		this.setRange(this.getMax() - this.getMin());
		this.setStandartDeviation((float) Math.sqrt(this.getVariance()));

		this.setFirstQuartile(Statistic.firstQuartile(series));
		this.setSecondQuartile(Statistic.secondQuartile(series));
		this.setThirdQuartile(Statistic.thirdQuartile(series));
		this.setFourthQuartile(Statistic.fourthQuartile(series));
		this.setInterquartileInterval(new Vector2f(this.getFirstQuartile(), this.getThirdQuartile()));
		this.setInterquartileRange(this.getInterquartileInterval().y - this.getInterquartileInterval().x);

		this.setFirstDecile(Statistic.firstDecile(series));
		this.setSecondDecile(Statistic.secondDecile(series));
		this.setThirdDecile(Statistic.thirdDecile(series));
		this.setFourthDecile(Statistic.fourthDecile(series));
		this.setFifthDecile(Statistic.fifthDecile(series));
		this.setSixthDecile(Statistic.sixthDecile(series));
		this.setSeventhDecile(Statistic.seventhDecile(series));
		this.setEighthDecile(Statistic.eighthDecile(series));
		this.setNinthDecile(Statistic.ninthDecile(series));
		this.setTenthDecile(Statistic.tenthDecile(series));
		this.setInterdecileInterval(new Vector2f(this.getFirstDecile(), this.getThirdDecile()));
		this.setInterdecileRange(this.getInterdecileInterval().y - this.getInterdecileInterval().x);
	}

	public StatisticInformation(float[] series)
	{
		this.setMin(Float.POSITIVE_INFINITY);
		this.setMax(0.0F);
		this.setAverage(0.0F);
		for (final float value : series)
		{
			if (value < this.getMin())
			{
				this.setMin(value);
			}
			if (value > this.getMax())
			{
				this.setMax(value);
			}
			this.setAverage(this.getAverage() + value);
		}

		this.setVariance(0.0F);
		for (final float value : series)
		{
			this.setVariance((float) (this.getVariance() + 1.0f * Math.pow(value - this.getAverage(), 2)));
		}
		this.setAverage(this.getAverage() / series.length);
		this.setVariance(this.getVariance() / series.length);

		this.setMedian(series[(series.length - 1) / 2]);
		this.setRange(this.getMax() - this.getMin());
		this.setStandartDeviation((float) Math.sqrt(this.getVariance()));

		this.setFirstQuartile(Statistic.firstQuartile(series));
		this.setSecondQuartile(Statistic.secondQuartile(series));
		this.setThirdQuartile(Statistic.thirdQuartile(series));
		this.setFourthQuartile(Statistic.fourthQuartile(series));
		this.setInterquartileInterval(new Vector2f(this.getFirstQuartile(), this.getThirdQuartile()));
		this.setInterquartileRange(this.getInterquartileInterval().y - this.getInterquartileInterval().x);

		this.setFirstDecile(Statistic.firstDecile(series));
		this.setSecondDecile(Statistic.secondDecile(series));
		this.setThirdDecile(Statistic.thirdDecile(series));
		this.setFourthDecile(Statistic.fourthDecile(series));
		this.setFifthDecile(Statistic.fifthDecile(series));
		this.setSixthDecile(Statistic.sixthDecile(series));
		this.setSeventhDecile(Statistic.seventhDecile(series));
		this.setEighthDecile(Statistic.eighthDecile(series));
		this.setNinthDecile(Statistic.ninthDecile(series));
		this.setTenthDecile(Statistic.tenthDecile(series));
		this.setInterdecileInterval(new Vector2f(this.getFirstDecile(), this.getThirdDecile()));
		this.setInterdecileRange(this.getInterdecileInterval().y - this.getInterdecileInterval().x);
	}

	/**
	 * @return this.the min
	 */
	public float getMin()
	{
		return this.min;
	}

	/**
	 * @param min the min to set
	 */
	private void setMin(float minIn)
	{
		this.min = minIn;
	}

	/**
	 * @return this.the max
	 */
	public float getMax()
	{
		return this.max;
	}

	/**
	 * @param max the max to set
	 */
	private void setMax(float maxIn)
	{
		this.max = maxIn;
	}

	/**
	 * @return this.the average
	 */
	public float getAverage()
	{
		return this.average;
	}

	/**
	 * @param average the average to set
	 */
	private void setAverage(float averageIn)
	{
		this.average = averageIn;
	}

	/**
	 * @return this.the median
	 */
	public float getMedian()
	{
		return this.median;
	}

	/**
	 * @param median the median to set
	 */
	private void setMedian(float medianIn)
	{
		this.median = medianIn;
	}

	/**
	 * @return this.the range
	 */
	public float getRange()
	{
		return this.range;
	}

	/**
	 * @param range the range to set
	 */
	private void setRange(float rangeIn)
	{
		this.range = rangeIn;
	}

	/**
	 * @return this.the firstQuartile
	 */
	public float getFirstQuartile()
	{
		return this.firstQuartile;
	}

	/**
	 * @param firstQuartile the firstQuartile to set
	 */
	private void setFirstQuartile(float firstQuartileIn)
	{
		this.firstQuartile = firstQuartileIn;
	}

	/**
	 * @return this.the secondQuartile
	 */
	public float getSecondQuartile()
	{
		return this.secondQuartile;
	}

	/**
	 * @param secondQuartile the secondQuartile to set
	 */
	private void setSecondQuartile(float secondQuartileIn)
	{
		this.secondQuartile = secondQuartileIn;
	}

	/**
	 * @return this.the thirdQuartile
	 */
	public float getThirdQuartile()
	{
		return this.thirdQuartile;
	}

	/**
	 * @param thirdQuartile the thirdQuartile to set
	 */
	private void setThirdQuartile(float thirdQuartileIn)
	{
		this.thirdQuartile = thirdQuartileIn;
	}

	/**
	 * @return this.the fourthQuartile
	 */
	public float getFourthQuartile()
	{
		return this.fourthQuartile;
	}

	/**
	 * @param fourthQuartile the fourthQuartile to set
	 */
	private void setFourthQuartile(float fourthQuartileIn)
	{
		this.fourthQuartile = fourthQuartileIn;
	}

	/**
	 * @return this.the interquartileInterval
	 */
	public Vector2f getInterquartileInterval()
	{
		return this.interquartileInterval;
	}

	/**
	 * @param interquartileInterval the interquartileInterval to set
	 */
	private void setInterquartileInterval(Vector2f interquartileIntervalIn)
	{
		this.interquartileInterval = interquartileIntervalIn;
	}

	/**
	 * @return this.the interquartileRange
	 */
	public float getInterquartileRange()
	{
		return this.interquartileRange;
	}

	/**
	 * @param interquartileRange the interquartileRange to set
	 */
	private void setInterquartileRange(float interquartileRangeIn)
	{
		this.interquartileRange = interquartileRangeIn;
	}

	/**
	 * @return this.the firstDecile
	 */
	public float getFirstDecile()
	{
		return this.firstDecile;
	}

	/**
	 * @param firstDecile the firstDecile to set
	 */
	private void setFirstDecile(float firstDecileIn)
	{
		this.firstDecile = firstDecileIn;
	}

	/**
	 * @return this.the secondDecile
	 */
	public float getSecondDecile()
	{
		return this.secondDecile;
	}

	/**
	 * @param secondDecile the secondDecile to set
	 */
	private void setSecondDecile(float secondDecileIn)
	{
		this.secondDecile = secondDecileIn;
	}

	/**
	 * @return this.the thirdDecile
	 */
	public float getThirdDecile()
	{
		return this.thirdDecile;
	}

	/**
	 * @param thirdDecile the thirdDecile to set
	 */
	private void setThirdDecile(float thirdDecileIn)
	{
		this.thirdDecile = thirdDecileIn;
	}

	/**
	 * @return this.the fourthDecile
	 */
	public float getFourthDecile()
	{
		return this.fourthDecile;
	}

	/**
	 * @param fourthDecile the fourthDecile to set
	 */
	private void setFourthDecile(float fourthDecileIn)
	{
		this.fourthDecile = fourthDecileIn;
	}

	/**
	 * @return this.the fifthDecile
	 */
	public float getFifthDecile()
	{
		return this.fifthDecile;
	}

	/**
	 * @param fifthDecile the fifthDecile to set
	 */
	private void setFifthDecile(float fifthDecileIn)
	{
		this.fifthDecile = fifthDecileIn;
	}

	/**
	 * @return this.the sixthDecile
	 */
	public float getSixthDecile()
	{
		return this.sixthDecile;
	}

	/**
	 * @param sixthDecile the sixthDecile to set
	 */
	private void setSixthDecile(float sixthDecileIn)
	{
		this.sixthDecile = sixthDecileIn;
	}

	/**
	 * @return this.the seventhDecile
	 */
	public float getSeventhDecile()
	{
		return this.seventhDecile;
	}

	/**
	 * @param seventhDecile the seventhDecile to set
	 */
	private void setSeventhDecile(float seventhDecileIn)
	{
		this.seventhDecile = seventhDecileIn;
	}

	/**
	 * @return this.the eighthDecile
	 */
	public float getEighthDecile()
	{
		return this.eighthDecile;
	}

	/**
	 * @param eighthDecile the eighthDecile to set
	 */
	private void setEighthDecile(float eighthDecileIn)
	{
		this.eighthDecile = eighthDecileIn;
	}

	/**
	 * @return this.the ninthDecile
	 */
	public float getNinthDecile()
	{
		return this.ninthDecile;
	}

	/**
	 * @param ninthDecile the ninthDecile to set
	 */
	private void setNinthDecile(float ninthDecileIn)
	{
		this.ninthDecile = ninthDecileIn;
	}

	/**
	 * @return this.the tenthDecile
	 */
	public float getTenthDecile()
	{
		return this.tenthDecile;
	}

	/**
	 * @param tenthDecile the tenthDecile to set
	 */
	private void setTenthDecile(float tenthDecileIn)
	{
		this.tenthDecile = tenthDecileIn;
	}

	/**
	 * @return this.the interdecileInterval
	 */
	public Vector2f getInterdecileInterval()
	{
		return this.interdecileInterval;
	}

	/**
	 * @param interdecileInterval the interdecileInterval to set
	 */
	private void setInterdecileInterval(Vector2f interdecileIntervalIn)
	{
		this.interdecileInterval = interdecileIntervalIn;
	}

	/**
	 * @return this.the interdecileRange
	 */
	public float getInterdecileRange()
	{
		return this.interdecileRange;
	}

	/**
	 * @param interdecileRange the interdecileRange to set
	 */
	private void setInterdecileRange(float interdecileRangeIn)
	{
		this.interdecileRange = interdecileRangeIn;
	}

	/**
	 * @return this.the variance
	 */
	public float getVariance()
	{
		return this.variance;
	}

	/**
	 * @param variance the variance to set
	 */
	private void setVariance(float varianceIn)
	{
		this.variance = varianceIn;
	}

	/**
	 * @return this.the standartDeviation
	 */
	public float getStandartDeviation()
	{
		return this.standartDeviation;
	}

	/**
	 * @param standartDeviation the standartDeviation to set
	 */
	private void setStandartDeviation(float standartDeviationIn)
	{
		this.standartDeviation = standartDeviationIn;
	}

	public static final boolean statisticInformationIsNull(StatisticInformation fromStatisticInformationIn)
	{
		return fromStatisticInformationIn == null;
	}
}