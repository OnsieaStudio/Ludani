package fr.onsiea.engine.client.graphics.mesh.md5;

import java.util.ArrayList;
import java.util.List;

import fr.onsiea.engine.utils.file.FileUtils;

public class MD5Model
{

	private MD5JointInfo	jointInfo;

	private MD5ModelHeader	header;

	private List<MD5Mesh>	meshes;

	public MD5Model()
	{
		this.meshes = new ArrayList<>();
	}

	public MD5JointInfo getJointInfo()
	{
		return this.jointInfo;
	}

	public void setJointInfo(MD5JointInfo jointInfo)
	{
		this.jointInfo = jointInfo;
	}

	public MD5ModelHeader getHeader()
	{
		return this.header;
	}

	public void setHeader(MD5ModelHeader header)
	{
		this.header = header;
	}

	public List<MD5Mesh> getMeshes()
	{
		return this.meshes;
	}

	public void setMeshes(List<MD5Mesh> meshes)
	{
		this.meshes = meshes;
	}

	@Override
	public String toString()
	{
		final var str = new StringBuilder("MD5MeshModel: " + System.lineSeparator());
		str.append(this.getHeader()).append(System.lineSeparator());
		str.append(this.getJointInfo()).append(System.lineSeparator());

		for (final MD5Mesh mesh : this.meshes)
		{
			str.append(mesh).append(System.lineSeparator());
		}
		return str.toString();
	}

	public static MD5Model parse(String meshModelFile) throws Exception
	{
		final var	lines		= FileUtils.loadLines(meshModelFile);

		final var		result		= new MD5Model();

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
		final var		headerBlock	= lines.subList(0, start);
		final var	header		= MD5ModelHeader.parse(headerBlock);
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
				MD5Model.parseBlock(result, blockId, blockBody);
				inBlock = false;
			}
		}

		return result;
	}

	private static void parseBlock(MD5Model model, String blockId, List<String> blockBody) throws Exception
	{
		switch (blockId)
		{
			case "joints":
				final var jointInfo = MD5JointInfo.parse(blockBody);
				model.setJointInfo(jointInfo);
				break;

			case "mesh":
				final var md5Mesh = MD5Mesh.parse(blockBody);
				model.getMeshes().add(md5Mesh);
				break;

			default:
				break;
		}
	}

}
