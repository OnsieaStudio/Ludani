package fr.onsiea.engine.utils;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class Indexer
{
	public static int index3i(int x, int y, int z)
	{
		return x << 16 | y << 8 | z;
	}

	public static int index2i(int x, int y)
	{
		return x << 16 | y << 8;
	}

	public static int index2ib(int x, int y)
	{
		return x << 16 | y << 8;
	}

	public static int index6i(int x, int y, int z, int w, int a, int b)
	{
		return x << 24 | y << 16 | z << 8 | w << 4 | a << 2 | b;
	}

	public static Vector3f solve3i(int index)
	{
		final var	x	= (index & 0xff0000) >> 16;
		final var	y	= (index & 0xff00) >> 8;
		final var	z	= index & 0xff;

		return new Vector3f(x, y, z);
	}

	/**public static Vector6f solve6i(int index)
	{
		int x = (index & 0xff000000) >> 16;
		int y = (index & 0xff0000) >> 8;
		int z = (index & 0xff00);
		int w = (index & 0xff) >> 16;
		int a = (index & 0xff) >> 8;
		int b = (index & 0xff);

		return new Vector6f(x, y, z, w, a, b);
	}*/

	public static Vector2f solve2i(int index)
	{
		final var	x	= (index & 0xff0000) >> 16;
		final var	y	= (index & 0xff00) >> 8;

		return new Vector2f(x, y);
	}

	public static Vector2f solve2ib(int index)
	{
		final var	x	= index >> 16 & 0xff;
		final var	y	= index >> 8 & 0xff;

		return new Vector2f(x, y);
	}

	public static long pack(int x, int y)
	{
		final var	xPacked	= (long) x << 32;
		final var	yPacked	= y & 0xFFFFFFFFL;
		return xPacked | yPacked;
	}

	public static int unpackX(long packed)
	{
		return (int) (packed >> 32);
	}

	public static int unpackY(long packed)
	{
		return (int) (packed & 0xFFFFFFFFL);
	}

	public static Vector2f unpack(long packed)
	{
		return new Vector2f(packed >> 32, packed & 0xFFFFFFFFL);
	}
}
