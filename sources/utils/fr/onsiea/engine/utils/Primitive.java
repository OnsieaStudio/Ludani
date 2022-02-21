package fr.onsiea.engine.utils;

public class Primitive
{
	/**
	 * Java method to return size of primitive data type based on hard coded values
	 * valid but provided by developer
	 * @return bytes size of dataType primitive, if unknown type, return 4 (default for 32-bit memory pointer, therefore for the pointer of a 64 bits memory it would be 8 bytes)
	 */
	public static int sizeof(@SuppressWarnings("rawtypes") Class dataType)
	{
		if (dataType == null)
		{
			throw new NullPointerException();
		}
		if (dataType == boolean.class || dataType == Boolean.class || dataType == byte.class || dataType == Byte.class)
		{
			return 1;
		}
		if (dataType == short.class || dataType == Short.class || dataType == char.class || dataType == Character.class)
		{
			return 2;
		}
		if (dataType == int.class || dataType == Integer.class)
		{
			return 4;
		}
		if (dataType == long.class || dataType == Long.class)
		{
			return 8;
		}
		if (dataType == float.class || dataType == Float.class)
		{
			return 4;
		}
		if (dataType == double.class || dataType == Double.class)
		{
			return 8;
		}
		return 4; // default for 32-bit memory pointer
	}

	// Sizes

	public static int byteSize()
	{
		return 1;
	}

	/** no idea, therefore,
	* not guaranteed: the size of a boolean in java not being defined in a safe way. A boolean is 2 bits or 0.25 byte ...
	* do not hesitate if you have an idea or if you can confirm this data from a reliable source
	*/
	public static int boolSize()
	{
		return 1;
	}

	public static int shortSize()
	{
		return 2;
	}

	public static int intSize()
	{
		return 4;
	}

	public static int floatSize()
	{
		return 4;
	}

	public static int doubleSize()
	{
		return 8;
	}

	// Max

	public static int unsignedByteMax()
	{
		return Byte.MAX_VALUE + Math.abs(Byte.MIN_VALUE);
	}

	public static int unsignedShortMax()
	{
		return Short.MAX_VALUE + Math.abs(Short.MIN_VALUE);
	}

	public static float unsignedIntMax()
	{
		return Integer.MAX_VALUE + Math.abs(Integer.MIN_VALUE);
	}
}