package fr.onsiea.engine.client.graphics.mesh.md5;

import java.util.ArrayList;
import java.util.List;

import fr.onsiea.engine.utils.file.FileUtils;

public class MD5AnimModel
{

	private MD5AnimHeader	header;

	private MD5Hierarchy	hierarchy;

	private MD5BoundInfo	boundInfo;

	private MD5BaseFrame	baseFrame;

	private List<MD5Frame>	frames;

	public MD5AnimModel()
	{
		this.frames = new ArrayList<>();
	}

	public MD5AnimHeader getHeader()
	{
		return this.header;
	}

	public void setHeader(MD5AnimHeader header)
	{
		this.header = header;
	}

	public MD5Hierarchy getHierarchy()
	{
		return this.hierarchy;
	}

	public void setHierarchy(MD5Hierarchy hierarchy)
	{
		this.hierarchy = hierarchy;
	}

	public MD5BoundInfo getBoundInfo()
	{
		return this.boundInfo;
	}

	public void setBoundInfo(MD5BoundInfo boundInfo)
	{
		this.boundInfo = boundInfo;
	}

	public MD5BaseFrame getBaseFrame()
	{
		return this.baseFrame;
	}

	public void setBaseFrame(MD5BaseFrame baseFrame)
	{
		this.baseFrame = baseFrame;
	}

	public List<MD5Frame> getFrames()
	{
		return this.frames;
	}

	public void setFrames(List<MD5Frame> frames)
	{
		this.frames = frames;
	}

	@Override
	public String toString()
	{
		final var str = new StringBuilder("MD5AnimModel: " + System.lineSeparator());
		str.append(this.getHeader()).append(System.lineSeparator());
		str.append(this.getHierarchy()).append(System.lineSeparator());
		str.append(this.getBoundInfo()).append(System.lineSeparator());
		str.append(this.getBaseFrame()).append(System.lineSeparator());

		for (final MD5Frame frame : this.frames)
		{
			str.append(frame).append(System.lineSeparator());
		}
		return str.toString();
	}

	public static MD5AnimModel parse(String animFile) throws Exception
	{
		final var	lines		= FileUtils.loadLines(animFile);

		final var	result		= new MD5AnimModel();

		final var			numLines	= lines != null ? lines.size() : 0;
		if (numLines == 0)
		{
			throw new Exception("Cannot parse empty file");
		}

		// Parse Header
		var	headerEnd	= false;
		var		start		= 0;
		for (var i = 0; i < numLines && !headerEnd; i++)
		{
			final var line = lines.get(i);
			headerEnd	= line.trim().endsWith("{");
			start		= i;
		}
		if (!headerEnd)
		{
			throw new Exception("Cannot find header");
		}
		final var	headerBlock	= lines.subList(0, start);
		final var	header		= MD5AnimHeader.parse(headerBlock);
		result.setHeader(header);

		// Parse the rest of block
		var		blockStart	= 0;
		var	inBlock		= false;
		var	blockId		= "";
		for (var i = start; i < numLines; i++)
		{
			final var line = lines.get(i);
			if (line.endsWith("{"))
			{
				blockStart	= i;
				blockId		= line.substring(0, line.lastIndexOf(" "));
				inBlock		= true;
			}
			else if (inBlock && line.endsWith("}"))
			{
				final var blockBody = lines.subList(blockStart + 1, i);
				MD5AnimModel.parseBlock(result, blockId, blockBody);
				inBlock = false;
			}
		}

		return result;
	}

	private static void parseBlock(MD5AnimModel model, String blockId, List<String> blockBody) throws Exception
	{
		switch (blockId)
		{
			case "hierarchy":
				final var hierarchy = MD5Hierarchy.parse(blockBody);
				model.setHierarchy(hierarchy);
				break;

			case "bounds":
				final var boundInfo = MD5BoundInfo.parse(blockBody);
				model.setBoundInfo(boundInfo);
				break;

			case "baseframe":
				final var baseFrame = MD5BaseFrame.parse(blockBody);
				model.setBaseFrame(baseFrame);
				break;

			default:
				if (blockId.startsWith("frame "))
				{
					final var frame = MD5Frame.parse(blockId, blockBody);
					model.getFrames().add(frame);
				}
				break;
		}
	}
}
