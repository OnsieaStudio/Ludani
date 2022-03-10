package fr.onsiea.engine.utils.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FileUtils
{
	public final static boolean copy(final File fromFileIn, final File toFileIn)
	{
		if (fromFileIn == null || toFileIn == null)
		{
			System.err.println("[COPIE] Le fichier source ou cible est null !");

			return false;
		}

		if (!fromFileIn.exists())
		{
			System.err.println("[COPIE] Le fichier source \"" + fromFileIn.getAbsolutePath() + "\" n'existe pas !");

			return false;
		}

		if (fromFileIn.getAbsolutePath() == toFileIn.getAbsolutePath())
		{
			return false;
		}

		if (!toFileIn.getParentFile().exists())
		{
			toFileIn.getParentFile().mkdirs();
		}

		FileInputStream	fileInputStream	= null;

		final var		bFile			= new byte[(int) fromFileIn.length()];
		try
		{
			// convert file into array of bytes
			fileInputStream = new FileInputStream(fromFileIn);
			fileInputStream.read(bFile);
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (fileInputStream != null)
			{
				try
				{
					fileInputStream.close();
				}
				catch (final IOException e)
				{
					e.printStackTrace();
				}
			}
		}

		FileOutputStream fileOutputStream = null;

		try
		{
			fileOutputStream = new FileOutputStream(toFileIn);

			fileOutputStream.write(bFile);
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (fileOutputStream != null)
			{
				try
				{
					fileOutputStream.close();
				}
				catch (final IOException e)
				{
					e.printStackTrace();
				}
			}
		}

		return true;
	}

	public static byte[] readBytes(final File fromFileIn)
	{
		if (fromFileIn == null)
		{
			System.err.println("[COPIE] Le fichier source est null !");

			return null;
		}

		if (!fromFileIn.exists())
		{
			System.err.println("[COPIE] Le fichier source \"" + fromFileIn.getAbsolutePath() + "\" n'existe pas !");

			return null;
		}

		FileInputStream	fileInputStream	= null;

		final var		bFile			= new byte[(int) fromFileIn.length()];
		try
		{
			// convert file into array of bytes
			fileInputStream = new FileInputStream(fromFileIn);
			fileInputStream.read(bFile);
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (fileInputStream != null)
			{
				try
				{
					fileInputStream.close();
				}
				catch (final IOException e)
				{
					e.printStackTrace();
				}
			}
		}

		return bFile;
	}

	public static void write(final File toFileIn, final byte[] contentIn)
	{
		if (toFileIn == null)
		{
			System.err.println("[COPIE] Le fichier cible est null !");

			return;
		}

		if (!toFileIn.getParentFile().exists())
		{
			toFileIn.getParentFile().mkdirs();
		}

		if (!toFileIn.exists())
		{
			try
			{
				toFileIn.getParentFile().createNewFile();
			}
			catch (final IOException e)
			{
				e.printStackTrace();
			}
		}

		FileOutputStream fileOutputStream = null;

		try
		{
			fileOutputStream = new FileOutputStream(toFileIn);

			fileOutputStream.write(contentIn);
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (fileOutputStream != null)
			{
				try
				{
					fileOutputStream.close();
				}
				catch (final IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	public static byte[] readBytes(final InputStream inputStreamIn)
	{
		byte[] bFile = null;

		try
		{
			bFile = inputStreamIn.readAllBytes();
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (inputStreamIn != null)
			{
				try
				{
					inputStreamIn.close();
				}
				catch (final IOException e)
				{
					e.printStackTrace();
				}
			}
		}

		return bFile;
	}

	public final static boolean writeIfFileExist(final String filepathIn, final String contentIn)
	{
		return FileUtils.writeIfFileExist(filepathIn, contentIn, false);
	}

	public final static boolean writeIfFileExist(final String filepathIn, final String contentIn,
			final boolean canAppendIn)
	{
		final var file = new File(filepathIn);

		if (!file.exists())
		{
			return false;
		}

		BufferedWriter bufferedWriter = null;

		try
		{
			bufferedWriter = new BufferedWriter(new FileWriter(file, canAppendIn));

			bufferedWriter.write(contentIn);
		}
		catch (final IOException e)
		{
			e.printStackTrace();

			return false;
		}
		finally
		{
			if (bufferedWriter != null)
			{
				try
				{
					bufferedWriter.close();
				}
				catch (final IOException e)
				{
					e.printStackTrace();
				}
			}
		}

		return true;
	}

	public final static boolean write(final String filepathIn, final String contentIn)
	{
		return FileUtils.write(filepathIn, contentIn, false);
	}

	public final static boolean write(final String filepathIn, final String contentIn, final boolean canAppendIn)
	{
		final var	file	= new File(filepathIn);

		final var	parent	= file.getParentFile();

		if (parent.exists() && parent.isFile())
		{
			return false;
		}
		if (!parent.exists())
		{
			parent.mkdirs();
		}

		if (!file.exists())
		{
			try
			{
				file.createNewFile();
			}
			catch (final IOException e1)
			{
				e1.printStackTrace();
			}
		}

		BufferedWriter bufferedWriter = null;

		try
		{
			bufferedWriter = new BufferedWriter(new FileWriter(file, canAppendIn));

			bufferedWriter.write(contentIn);
		}
		catch (final IOException e)
		{
			e.printStackTrace();

			return false;
		}
		finally
		{
			if (bufferedWriter != null)
			{
				try
				{
					bufferedWriter.close();
				}
				catch (final IOException e)
				{
					e.printStackTrace();
				}
			}
		}

		return true;
	}

	public static List<String> loadLines(final String filepathIn)
	{
		final var file = new File(filepathIn);

		if (!file.exists() || file.isDirectory())
		{
			System.err.println("[ERREUR] Le fichier \"" + filepathIn + "\" n'existe pas !");

			return null;
		}

		final List<String>	lines			= new ArrayList<>();

		BufferedReader		bufferedReader	= null;

		try
		{
			bufferedReader = new BufferedReader(new FileReader(file));

			String line;

			while ((line = bufferedReader.readLine()) != null)
			{
				lines.add(line);
			}
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (bufferedReader != null)
				{
					bufferedReader.close();
				}
			}
			catch (final IOException e)
			{
				e.printStackTrace();
			}
		}

		return lines;
	}

	public final static String loadResource(final String filePathIn)
	{
		final var file = new File(filePathIn);

		if (!file.exists() || file.isDirectory())
		{
			return null;
		}

		BufferedReader	bufferedReader	= null;

		final var		content			= new StringBuilder();

		try
		{
			bufferedReader = new BufferedReader(new FileReader(file));

			String line;

			while ((line = bufferedReader.readLine()) != null)
			{
				content.append(line).append("//\n");
			}
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (bufferedReader != null)
				{
					bufferedReader.close();
				}
			}
			catch (final IOException e)
			{
				e.printStackTrace();
			}
		}

		return content.toString();
	}

	public static boolean copy(final String fromFilepathIn, final String toFilepathIn)
	{
		if (fromFilepathIn.contentEquals(toFilepathIn))
		{
			return false;
		}

		final var	fromFile	= new File(fromFilepathIn);
		final var	toFile		= new File(toFilepathIn);

		if (!fromFile.exists() || fromFile.isDirectory())
		{
			return false;
		}

		final var parent = toFile.getParentFile();

		if (parent.exists() && parent.isFile())
		{
			return false;
		}
		if (!parent.exists())
		{
			parent.mkdirs();
		}

		if (!toFile.exists())
		{
			try
			{
				toFile.createNewFile();
			}
			catch (final IOException e1)
			{
				e1.printStackTrace();

				return false;
			}
		}

		final var		content			= new StringBuilder();

		BufferedReader	bufferedReader	= null;

		try
		{
			bufferedReader = new BufferedReader(new FileReader(fromFile));

			String line;

			while ((line = bufferedReader.readLine()) != null)
			{
				content.append(line).append("//\n");
			}
		}
		catch (final IOException e)
		{
			e.printStackTrace();

			return false;
		}
		finally
		{
			try
			{
				if (bufferedReader != null)
				{
					bufferedReader.close();
				}
			}
			catch (final IOException e)
			{
				e.printStackTrace();
			}
		}

		BufferedWriter bufferedWriter = null;

		try
		{
			bufferedWriter = new BufferedWriter(new FileWriter(toFile));

			bufferedWriter.write(content.toString());
		}
		catch (final IOException e)
		{
			e.printStackTrace();

			return false;
		}
		finally
		{
			try
			{
				if (bufferedWriter != null)
				{
					bufferedWriter.close();
				}
			}
			catch (final IOException e)
			{
				e.printStackTrace();
			}
		}

		return true;
	}

	/**
	 * @param stringIn
	 * @return
	 * @throws Exception
	 */
	public static String[] allAbsoluteFilepathOf(String filepathIn) throws Exception
	{
		final var root = new File(filepathIn);

		if (!root.exists())
		{
			return null;
		}
		if (root.isFile())
		{
			throw new Exception("[ERROR] \"" + filepathIn + "\" is file  !");
		}

		final var	files		= root.listFiles();
		final var	filespath	= new String[files.length];
		var			i			= 0;
		for (final File file : files)
		{
			filespath[i] = file.getAbsolutePath();

			i++;
		}

		return filespath;
	}

	/**
	 * @param stringIn
	 * @return
	 * @throws Exception
	 */
	public static String[] allLocalFilepathOf(String filepathIn) throws Exception
	{
		final var root = new File(filepathIn);

		if (!root.exists())
		{
			return null;
		}
		if (root.isFile())
		{
			throw new Exception("[ERROR] \"" + filepathIn + "\" is file  !");
		}

		final var	files		= root.listFiles();
		final var	filespath	= new String[files.length];
		var			i			= 0;
		for (final File file : files)
		{
			filespath[i] = file.getName();

			i++;
		}

		return filespath;
	}
}