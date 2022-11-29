package fr.onsiea.engine.client.graphics.mesh.md5;

import java.util.ArrayList;
import java.util.List;

import fr.onsiea.engine.utils.ArrayUtils;

public class MD5Frame
{

	private int		id;

	private float[]	frameData;

	public int getId()
	{
		return this.id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public float[] getFrameData()
	{
		return this.frameData;
	}

	public void setFrameData(float[] frameData)
	{
		this.frameData = frameData;
	}

	@Override
	public String toString()
	{
		final var str = new StringBuilder("frame " + this.id + " [data: " + System.lineSeparator());
		for (final float frameData : this.frameData)
		{
			str.append(frameData).append(System.lineSeparator());
		}
		str.append("]").append(System.lineSeparator());
		return str.toString();
	}

	public static MD5Frame parse(String blockId, List<String> blockBody) throws Exception
	{
		final var	result	= new MD5Frame();
		final var	tokens	= blockId.trim().split("\\s+");
		if ((tokens == null) || (tokens.length < 2))
		{
			throw new Exception("Wrong frame definition: " + blockId);
		}
		result.setId(Integer.parseInt(tokens[1]));

		final List<Float> data = new ArrayList<>();
		for (final String line : blockBody)
		{
			final var lineData = MD5Frame.parseLine(line);
			if (lineData != null)
			{
				data.addAll(lineData);
			}
		}
		final var dataArr = ArrayUtils.listToArray(data);
		result.setFrameData(dataArr);

		return result;
	}

	private static List<Float> parseLine(String line)
	{
		final var		tokens	= line.trim().split("\\s+");
		final List<Float>	data	= new ArrayList<>();
		for (final String token : tokens)
		{
			data.add(Float.parseFloat(token));
		}
		return data;
	}
}