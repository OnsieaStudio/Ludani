package fr.onsiea.engine.utils.maths.statistics;

import java.util.Collection;
import java.util.List;

import org.joml.Vector2d;

public class StatisticdInformation
{
	private boolean		isLoaded;
	private double		min;
	private double		max;

	private double		average;
	private double		median;
	private double		range;

	private double		firstQuartile;
	private double		secondQuartile;
	private double		thirdQuartile;
	private double		fourthQuartile;
	private Vector2d	interquartileInterval;
	private double		interquartileRange;

	private double		firstDecile;
	private double		secondDecile;
	private double		thirdDecile;
	private double		fourthDecile;
	private double		fifthDecile;
	private double		sixthDecile;
	private double		seventhDecile;
	private double		eighthDecile;
	private double		ninthDecile;
	private double		tenthDecile;
	private Vector2d	interdecileInterval;
	private double		interdecileRange;

	private double		variance;
	private double		standartDeviation;

	public StatisticdInformation(Collection<Double> listSerieIn)
	{
		final var	series	= new double[listSerieIn.size()];
		var			i		= 0;
		for (final double value : listSerieIn)
		{
			series[i] = value;
			i++;
		}

		this.setMin(Double.POSITIVE_INFINITY);
		this.setMax(0.0F);
		this.setAverage(0.0F);
		for (final double value : series)
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
		for (final double value : series)
		{
			this.setVariance(this.getVariance() + 1.0f * Math.pow(value - this.getAverage(), 2));
		}
		this.setAverage(this.getAverage() / series.length);
		this.setVariance(this.getVariance() / series.length);

		this.setMedian(series[(series.length - 1) / 2]);
		this.setRange(this.getMax() - this.getMin());
		this.setStandartDeviation(Math.sqrt(this.getVariance()));

		this.setFirstQuartile(Statisticd.firstQuartile(series));
		this.setSecondQuartile(Statisticd.secondQuartile(series));
		this.setThirdQuartile(Statisticd.thirdQuartile(series));
		this.setFourthQuartile(Statisticd.fourthQuartile(series));
		this.setInterquartileInterval(new Vector2d(this.getFirstQuartile(), this.getThirdQuartile()));
		this.setInterquartileRange(this.getInterquartileInterval().y - this.getInterquartileInterval().x);

		this.setFirstDecile(Statisticd.firstDecile(series));
		this.setSecondDecile(Statisticd.secondDecile(series));
		this.setThirdDecile(Statisticd.thirdDecile(series));
		this.setFourthDecile(Statisticd.fourthDecile(series));
		this.setFifthDecile(Statisticd.fifthDecile(series));
		this.setSixthDecile(Statisticd.sixthDecile(series));
		this.setSeventhDecile(Statisticd.seventhDecile(series));
		this.setEighthDecile(Statisticd.eighthDecile(series));
		this.setNinthDecile(Statisticd.ninthDecile(series));
		this.setTenthDecile(Statisticd.tenthDecile(series));
		this.setInterdecileInterval(new Vector2d(this.getFirstDecile(), this.getThirdDecile()));
		this.setInterdecileRange(this.getInterdecileInterval().y - this.getInterdecileInterval().x);
		this.setLoaded(true);
	}

	public StatisticdInformation(List<Double> listSerieIn)
	{
		final var	series	= new double[listSerieIn.size()];
		var			i		= 0;
		for (final double value : listSerieIn)
		{
			series[i] = value;
			i++;
		}

		this.setMin(Double.POSITIVE_INFINITY);
		this.setMax(0.0F);
		this.setAverage(0.0F);
		for (final double value : series)
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
		for (final double value : series)
		{
			this.setVariance(this.getVariance() + 1.0f * Math.pow(value - this.getAverage(), 2));
		}
		this.setAverage(this.getAverage() / series.length);
		this.setVariance(this.getVariance() / series.length);

		this.setMedian(series[(series.length - 1) / 2]);
		this.setRange(this.getMax() - this.getMin());
		this.setStandartDeviation(Math.sqrt(this.getVariance()));

		this.setFirstQuartile(Statisticd.firstQuartile(series));
		this.setSecondQuartile(Statisticd.secondQuartile(series));
		this.setThirdQuartile(Statisticd.thirdQuartile(series));
		this.setFourthQuartile(Statisticd.fourthQuartile(series));
		this.setInterquartileInterval(new Vector2d(this.getFirstQuartile(), this.getThirdQuartile()));
		this.setInterquartileRange(this.getInterquartileInterval().y - this.getInterquartileInterval().x);

		this.setFirstDecile(Statisticd.firstDecile(series));
		this.setSecondDecile(Statisticd.secondDecile(series));
		this.setThirdDecile(Statisticd.thirdDecile(series));
		this.setFourthDecile(Statisticd.fourthDecile(series));
		this.setFifthDecile(Statisticd.fifthDecile(series));
		this.setSixthDecile(Statisticd.sixthDecile(series));
		this.setSeventhDecile(Statisticd.seventhDecile(series));
		this.setEighthDecile(Statisticd.eighthDecile(series));
		this.setNinthDecile(Statisticd.ninthDecile(series));
		this.setTenthDecile(Statisticd.tenthDecile(series));
		this.setInterdecileInterval(new Vector2d(this.getFirstDecile(), this.getThirdDecile()));
		this.setInterdecileRange(this.getInterdecileInterval().y - this.getInterdecileInterval().x);
		this.setLoaded(true);
	}

	public StatisticdInformation(double[] series)
	{
		this.setMin(Double.POSITIVE_INFINITY);
		this.setMax(0.0F);
		this.setAverage(0.0F);
		for (final double value : series)
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
		for (final double value : series)
		{
			this.setVariance(this.getVariance() + 1.0f * Math.pow(value - this.getAverage(), 2));
		}
		this.setAverage(this.getAverage() / series.length);
		this.setVariance(this.getVariance() / series.length);

		this.setMedian(series[(series.length - 1) / 2]);
		this.setRange(this.getMax() - this.getMin());
		this.setStandartDeviation(Math.sqrt(this.getVariance()));

		this.setFirstQuartile(Statisticd.firstQuartile(series));
		this.setSecondQuartile(Statisticd.secondQuartile(series));
		this.setThirdQuartile(Statisticd.thirdQuartile(series));
		this.setFourthQuartile(Statisticd.fourthQuartile(series));
		this.setInterquartileInterval(new Vector2d(this.getFirstQuartile(), this.getThirdQuartile()));
		this.setInterquartileRange(this.getInterquartileInterval().y - this.getInterquartileInterval().x);

		this.setFirstDecile(Statisticd.firstDecile(series));
		this.setSecondDecile(Statisticd.secondDecile(series));
		this.setThirdDecile(Statisticd.thirdDecile(series));
		this.setFourthDecile(Statisticd.fourthDecile(series));
		this.setFifthDecile(Statisticd.fifthDecile(series));
		this.setSixthDecile(Statisticd.sixthDecile(series));
		this.setSeventhDecile(Statisticd.seventhDecile(series));
		this.setEighthDecile(Statisticd.eighthDecile(series));
		this.setNinthDecile(Statisticd.ninthDecile(series));
		this.setTenthDecile(Statisticd.tenthDecile(series));
		this.setInterdecileInterval(new Vector2d(this.getFirstDecile(), this.getThirdDecile()));
		this.setInterdecileRange(this.getInterdecileInterval().y - this.getInterdecileInterval().x);
		this.setLoaded(true);
	}

	public boolean isLoaded()
	{
		return this.isLoaded;
	}

	private void setLoaded(boolean isLoadedIn)
	{
		this.isLoaded = isLoadedIn;
	}

	/**
	 * @return this.the min
	 */
	public double getMin()
	{
		return this.min;
	}

	/**
	 * @param min the min to set
	 */
	private void setMin(double minIn)
	{
		this.min = minIn;
	}

	/**
	 * @return this.the max
	 */
	public double getMax()
	{
		return this.max;
	}

	/**
	 * @param max the max to set
	 */
	private void setMax(double maxIn)
	{
		this.max = maxIn;
	}

	/**
	 * @return this.the average
	 */
	public double getAverage()
	{
		return this.average;
	}

	/**
	 * @param average the average to set
	 */
	private void setAverage(double averageIn)
	{
		this.average = averageIn;
	}

	/**
	 * @return this.the median
	 */
	public double getMedian()
	{
		return this.median;
	}

	/**
	 * @param median the median to set
	 */
	private void setMedian(double medianIn)
	{
		this.median = medianIn;
	}

	/**
	 * @return this.the range
	 */
	public double getRange()
	{
		return this.range;
	}

	/**
	 * @param range the range to set
	 */
	private void setRange(double rangeIn)
	{
		this.range = rangeIn;
	}

	/**
	 * @return this.the firstQuartile
	 */
	public double getFirstQuartile()
	{
		return this.firstQuartile;
	}

	/**
	 * @param firstQuartile the firstQuartile to set
	 */
	private void setFirstQuartile(double firstQuartileIn)
	{
		this.firstQuartile = firstQuartileIn;
	}

	/**
	 * @return this.the secondQuartile
	 */
	public double getSecondQuartile()
	{
		return this.secondQuartile;
	}

	/**
	 * @param secondQuartile the secondQuartile to set
	 */
	private void setSecondQuartile(double secondQuartileIn)
	{
		this.secondQuartile = secondQuartileIn;
	}

	/**
	 * @return this.the thirdQuartile
	 */
	public double getThirdQuartile()
	{
		return this.thirdQuartile;
	}

	/**
	 * @param thirdQuartile the thirdQuartile to set
	 */
	private void setThirdQuartile(double thirdQuartileIn)
	{
		this.thirdQuartile = thirdQuartileIn;
	}

	/**
	 * @return this.the fourthQuartile
	 */
	public double getFourthQuartile()
	{
		return this.fourthQuartile;
	}

	/**
	 * @param fourthQuartile the fourthQuartile to set
	 */
	private void setFourthQuartile(double fourthQuartileIn)
	{
		this.fourthQuartile = fourthQuartileIn;
	}

	/**
	 * @return this.the interquartileInterval
	 */
	public Vector2d getInterquartileInterval()
	{
		if (this.interquartileInterval == null)
		{
			return new Vector2d(0, 0);
		}
		return this.interquartileInterval;
	}

	/**
	 * @param interquartileInterval the interquartileInterval to set
	 */
	private void setInterquartileInterval(Vector2d interquartileIntervalIn)
	{
		this.interquartileInterval = interquartileIntervalIn;
	}

	/**
	 * @return this.the interquartileRange
	 */
	public double getInterquartileRange()
	{
		return this.interquartileRange;
	}

	/**
	 * @param interquartileRange the interquartileRange to set
	 */
	private void setInterquartileRange(double interquartileRangeIn)
	{
		this.interquartileRange = interquartileRangeIn;
	}

	/**
	 * @return this.the firstDecile
	 */
	public double getFirstDecile()
	{
		return this.firstDecile;
	}

	/**
	 * @param firstDecile the firstDecile to set
	 */
	private void setFirstDecile(double firstDecileIn)
	{
		this.firstDecile = firstDecileIn;
	}

	/**
	 * @return this.the secondDecile
	 */
	public double getSecondDecile()
	{
		return this.secondDecile;
	}

	/**
	 * @param secondDecile the secondDecile to set
	 */
	private void setSecondDecile(double secondDecileIn)
	{
		this.secondDecile = secondDecileIn;
	}

	/**
	 * @return this.the thirdDecile
	 */
	public double getThirdDecile()
	{
		return this.thirdDecile;
	}

	/**
	 * @param thirdDecile the thirdDecile to set
	 */
	private void setThirdDecile(double thirdDecileIn)
	{
		this.thirdDecile = thirdDecileIn;
	}

	/**
	 * @return this.the fourthDecile
	 */
	public double getFourthDecile()
	{
		return this.fourthDecile;
	}

	/**
	 * @param fourthDecile the fourthDecile to set
	 */
	private void setFourthDecile(double fourthDecileIn)
	{
		this.fourthDecile = fourthDecileIn;
	}

	/**
	 * @return this.the fifthDecile
	 */
	public double getFifthDecile()
	{
		return this.fifthDecile;
	}

	/**
	 * @param fifthDecile the fifthDecile to set
	 */
	private void setFifthDecile(double fifthDecileIn)
	{
		this.fifthDecile = fifthDecileIn;
	}

	/**
	 * @return this.the sixthDecile
	 */
	public double getSixthDecile()
	{
		return this.sixthDecile;
	}

	/**
	 * @param sixthDecile the sixthDecile to set
	 */
	private void setSixthDecile(double sixthDecileIn)
	{
		this.sixthDecile = sixthDecileIn;
	}

	/**
	 * @return this.the seventhDecile
	 */
	public double getSeventhDecile()
	{
		return this.seventhDecile;
	}

	/**
	 * @param seventhDecile the seventhDecile to set
	 */
	private void setSeventhDecile(double seventhDecileIn)
	{
		this.seventhDecile = seventhDecileIn;
	}

	/**
	 * @return this.the eighthDecile
	 */
	public double getEighthDecile()
	{
		return this.eighthDecile;
	}

	/**
	 * @param eighthDecile the eighthDecile to set
	 */
	private void setEighthDecile(double eighthDecileIn)
	{
		this.eighthDecile = eighthDecileIn;
	}

	/**
	 * @return this.the ninthDecile
	 */
	public double getNinthDecile()
	{
		return this.ninthDecile;
	}

	/**
	 * @param ninthDecile the ninthDecile to set
	 */
	private void setNinthDecile(double ninthDecileIn)
	{
		this.ninthDecile = ninthDecileIn;
	}

	/**
	 * @return this.the tenthDecile
	 */
	public double getTenthDecile()
	{
		return this.tenthDecile;
	}

	/**
	 * @param tenthDecile the tenthDecile to set
	 */
	private void setTenthDecile(double tenthDecileIn)
	{
		this.tenthDecile = tenthDecileIn;
	}

	/**
	 * @return this.the interdecileInterval
	 */
	public Vector2d getInterdecileInterval()
	{

		if (this.interdecileInterval == null)
		{
			return new Vector2d(0, 0);
		}
		return this.interdecileInterval;
	}

	/**
	 * @param interdecileInterval the interdecileInterval to set
	 */
	private void setInterdecileInterval(Vector2d interdecileIntervalIn)
	{
		this.interdecileInterval = interdecileIntervalIn;
	}

	/**
	 * @return this.the interdecileRange
	 */
	public double getInterdecileRange()
	{
		return this.interdecileRange;
	}

	/**
	 * @param interdecileRange the interdecileRange to set
	 */
	private void setInterdecileRange(double interdecileRangeIn)
	{
		this.interdecileRange = interdecileRangeIn;
	}

	/**
	 * @return this.the variance
	 */
	public double getVariance()
	{
		return this.variance;
	}

	/**
	 * @param variance the variance to set
	 */
	private void setVariance(double varianceIn)
	{
		this.variance = varianceIn;
	}

	/**
	 * @return this.the standartDeviation
	 */
	public double getStandartDeviation()
	{
		return this.standartDeviation;
	}

	/**
	 * @param standartDeviation the standartDeviation to set
	 */
	private void setStandartDeviation(double standartDeviationIn)
	{
		this.standartDeviation = standartDeviationIn;
	}

	public static final boolean statisticInformationIsNull(StatisticdInformation fromStatisticInformationIn)
	{
		return fromStatisticInformationIn != null ? !fromStatisticInformationIn.isLoaded() : true;
	}
}