/**
 *
 */
package fr.onsiea.engine.client.input;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

/**
 * @author seyro
 *
 */
public class Keys
{
	private @Getter final Map<Integer, KeyAction> keys;

	public Keys()
	{
		this.keys = new HashMap<>();
	}

	/**
	 * @param keyActionIn
	 */
	public Keys add(final KeyAction keyActionIn)
	{
		if (keyActionIn != null)
		{
			this.keys.put(keyActionIn.id(), keyActionIn);

			if (keyActionIn.lastList() != null)
			{
				keyActionIn.lastList().remove(keyActionIn.id());
			}
			keyActionIn.lastList(this);
		}

		return this;
	}

	public Keys remove(final int keyIdIn)
	{
		this.keys.remove(keyIdIn);

		return this;
	}

	public boolean has(final int keyIdIn)
	{
		return this.keys.containsKey(keyIdIn);
	}

	public boolean hasOne(final int... keysIdIn)
	{
		for (final int key : keysIdIn)
		{
			if (this.has(key))
			{
				return true;
			}
		}
		return false;
	}

	public boolean hasAll(final int... keysIdIn)
	{
		for (final int key : keysIdIn)
		{
			if (!this.has(key))
			{
				return false;
			}
		}
		return true;
	}
}