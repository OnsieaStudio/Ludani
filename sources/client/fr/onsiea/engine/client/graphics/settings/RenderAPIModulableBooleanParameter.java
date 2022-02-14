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
import lombok.NonNull;

/**
 * @author Seynax
 *
 */
public class RenderAPIModulableBooleanParameter implements IRenderAPIBooleanParameter, INameable
{
	private final IRenderAPISettings						parent;
	private final String									name;
	public boolean											status;

	private final IIFunction<IRenderAPIBooleanParameter>	enableMethod;
	private final IIFunction<IRenderAPIBooleanParameter>	disableMethod;

	protected RenderAPIModulableBooleanParameter(IRenderAPISettings parentIn, String nameIn,
			IIFunction<IRenderAPIBooleanParameter> enableMethodIn,
			IIFunction<IRenderAPIBooleanParameter> disableMethodIn)
	{
		this.parent			= parentIn;
		this.name			= nameIn;
		this.enableMethod	= enableMethodIn;
		this.disableMethod	= disableMethodIn;
	}

	@Override
	@NonNull
	public IRenderAPISettings set(Boolean statusIn)
	{
		this.status = statusIn;

		this.refresh();

		return this.parent;
	}

	@Override
	public IRenderAPISettings enable()
	{
		this.status = true;

		this.enableMethod.execute(this);

		return this.parent;
	}

	@Override
	public IRenderAPISettings disable()
	{
		this.status = false;

		this.disableMethod.execute(this);

		return this.parent;
	}

	@Override
	public IRenderAPISettings toggle()
	{
		this.status = !this.status;

		this.refresh();

		return this.parent;
	}

	public IRenderAPISettings refresh()
	{
		if (this.status)
		{
			this.enableMethod.execute(this);
		}
		else
		{
			this.disableMethod.execute(this);
		}

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

	@Override
	public final boolean status()
	{
		return this.status;
	}

	public static class Builder
	{
		private final IRenderAPISettings				parent;
		private final String							name;

		private IIFunction<IRenderAPIBooleanParameter>	enableMethod;
		private IIFunction<IRenderAPIBooleanParameter>	disableMethod;

		public Builder(IRenderAPISettings parentIn, String nameIn)
		{
			this.parent	= parentIn;
			this.name	= nameIn;
		}

		public Builder(IRenderAPISettings parentIn, String nameIn,
				IIFunction<IRenderAPIBooleanParameter> enableMethodIn,
				IIFunction<IRenderAPIBooleanParameter> disableMethodIn)
		{
			this.parent			= parentIn;
			this.name			= nameIn;
			this.enableMethod	= enableMethodIn;
			this.disableMethod	= disableMethodIn;
		}

		public IRenderAPIBooleanParameter build() throws Exception
		{
			if (this.parent == null)
			{
				throw new Exception("[ERROR] Parent is null ! (GraphicsParameter)");
			}

			if (this.name == null)
			{
				throw new Exception("[ERROR] Name is null ! (GraphicsParameter)");
			}

			if (this.enableMethod == null)
			{
				throw new Exception("[ERROR] Disable method is null ! (GraphicsParameter)");
			}

			if (this.disableMethod == null)
			{
				throw new Exception("[ERROR] Enable method is null ! (GraphicsParameter)");
			}

			return new RenderAPIModulableBooleanParameter(this.parent, this.name, this.enableMethod, this.disableMethod);
		}

		public final void enableMethod(IIFunction<IRenderAPIBooleanParameter> enableMethodIn)
		{
			this.enableMethod = enableMethodIn;
		}

		public final void disableMethod(IIFunction<IRenderAPIBooleanParameter> disableMethodIn)
		{
			this.disableMethod = disableMethodIn;
		}
	}
}