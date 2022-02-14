/**
* Copyright 2021 Onsiea All rights reserved.<br><br>
*
* This file is part of Onsiea Engine project. (https://github.com/Onsiea/OnsieaEngine)<br><br>
*
* Onsiea Engine is [licensed] (https://github.com/Onsiea/OnsieaEngine/blob/main/LICENSE) under the terms of the "GNU General Public Lesser License v3.0" (GPL-3.0).
* https://github.com/Onsiea/OnsieaEngine/wiki/License#license-and-copyright<br><br>
*
* Onsiea Engine is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 3.0 of the License, or
* (at your option) any later version.<br><br>
*
* Onsiea Engine is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU Lesser General Public License for more details.<br><br>
*
* You should have received a copy of the GNU Lesser General Public License
* along with Onsiea Engine.  If not, see <https://www.gnu.org/licenses/>.<br><br>
*
* Neither the name "Onsiea", "Onsiea Engine", or any derivative name or the names of its authors / contributors may be used to endorse or promote products derived from this software and even less to name another project or other work without clear and precise permissions written in advance.<br><br>
*
* @Author : Seynax (https://github.com/seynax)<br>
* @Organization : Onsiea Studio (https://github.com/Onsiea)
*/
package fr.onsiea.engine.client.settings;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Seynax
 *
 */
public class ClientSettings implements IClientSettings
{
	private final Map<String, IClientParameter<?>> parameters;

	public ClientSettings()
	{
		this.parameters = new HashMap<>();
	}

	@Override
	public IClientSettings put(String nameIn, IClientParameter<?> parameterIn)
	{
		this.parameters.put(nameIn, parameterIn);

		return this;
	}

	@Override
	public Collection<String> names()
	{
		return this.parameters.keySet();
	}

	@Override
	public IClientParameter<?> get(String nameIn)
	{
		return this.parameters.get(nameIn);
	}

	@Override
	public boolean contains(String nameIn)
	{
		return this.parameters.containsKey(nameIn);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> IClientSettings set(String nameIn, T valueIn)
	{
		final var parameter = this.get(nameIn);

		if (parameter instanceof IClientParameter)
		{
			((IClientParameter<T>) parameter).set(valueIn);
		}

		return this;
	}

	@Override
	public IClientSettings enable(String nameIn)
	{
		final var parameter = this.get(nameIn);

		if (parameter instanceof IClientBooleanParameter)
		{
			((IClientBooleanParameter) parameter).enable();
		}

		return this;
	}

	@Override
	public IClientSettings disable(String nameIn)
	{
		final var parameter = this.get(nameIn);

		if (parameter instanceof IClientBooleanParameter)
		{
			((IClientBooleanParameter) parameter).disable();
		}

		return this;
	}

	@Override
	public IClientSettings toggle(String nameIn)
	{
		final var parameter = this.get(nameIn);

		if (parameter instanceof IClientBooleanParameter)
		{
			((IClientBooleanParameter) parameter).toggle();
		}

		return this;
	}

	@Override
	public IClientSettings remove(String nameIn)
	{
		this.parameters.remove(nameIn);

		return this;
	}
}