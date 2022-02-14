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
package fr.onsiea.engine.client.graphics.settings;

import fr.onsiea.engine.utils.INameable;
import fr.onsiea.engine.utils.function.IIFunction;

/**
 * @author Seynax
 *
 */
public class RenderAPIParameter<T> implements IRenderAPIParameter<T>, INameable
{
	private final IRenderAPISettings					parent;
	private final String								name;
	public T											value;

	private final IIFunction<IRenderAPIParameter<T>>	setMethod;

	protected RenderAPIParameter(IRenderAPISettings parentIn, String nameIn,
			IIFunction<IRenderAPIParameter<T>> setMethodIn)
	{
		this.parent		= parentIn;
		this.name		= nameIn;
		this.setMethod	= setMethodIn;
	}

	@Override
	public IRenderAPISettings set(T valueIn)
	{
		this.value = valueIn;

		this.setMethod.execute(this);

		return this.parent;
	}

	public final IRenderAPISettings parent()
	{
		return this.parent;
	}

	@Override
	public final String name()
	{
		return this.name;
	}

	public final T value()
	{
		return this.value;
	}

	public static class Builder<T>
	{
		private final IRenderAPISettings			parent;
		private final String						name;

		private IIFunction<IRenderAPIParameter<T>>	setMethod;

		public Builder(IRenderAPISettings parentIn, String nameIn)
		{
			this.parent	= parentIn;
			this.name	= nameIn;
		}

		public Builder(IRenderAPISettings parentIn, String nameIn, IIFunction<IRenderAPIParameter<T>> setMethodIn)
		{
			this.parent		= parentIn;
			this.name		= nameIn;
			this.setMethod	= setMethodIn;
		}

		public IRenderAPIParameter<T> build() throws Exception
		{
			if (this.parent == null)
			{
				throw new Exception("[ERROR] Parent is null ! (GraphicsParameter)");
			}

			if (this.name == null)
			{
				throw new Exception("[ERROR] Name is null ! (GraphicsParameter)");
			}

			if (this.setMethod == null)
			{
				throw new Exception("[ERROR] Set method is null ! (GraphicsParameter)");
			}

			return new RenderAPIParameter<>(this.parent, this.name, this.setMethod);
		}

		public void setMethod(IIFunction<IRenderAPIParameter<T>> setMethodIn)
		{
			this.setMethod = setMethodIn;
		}
	}
}