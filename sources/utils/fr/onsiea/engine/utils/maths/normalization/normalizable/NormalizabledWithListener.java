/**
*	Copyright 2021 Onsiea All rights reserved.
*
*	This file is part of Onsiea Engine. (https://github.com/Onsiea/OnsieaEngine)
*
*	Unless noted in license (https://github.com/Onsiea/OnsieaEngine/blob/main/LICENSE.md) notice file (https://github.com/Onsiea/OnsieaEngine/blob/main/LICENSE_NOTICE.md), Onsiea engine and all parts herein is licensed under the terms of the LGPL-3.0 (https://www.gnu.org/licenses/lgpl-3.0.html)  found here https://www.gnu.org/licenses/lgpl-3.0.html and copied below the license file.
*
*	Onsiea Engine is libre software: you can redistribute it and/or modify
*	it under the terms of the GNU Lesser General Public License as published by
*	the Free Software Foundation, either version 3.0 of the License, or
*	(at your option) any later version.
*
*	Onsiea Engine is distributed in the hope that it will be useful,
*	but WITHOUT ANY WARRANTY; without even the implied warranty of
*	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*	GNU Lesser General Public License for more details.
*
*	You should have received a copy of the GNU Lesser General Public License
*	along with Onsiea Engine.  If not, see <https://www.gnu.org/licenses/> <https://www.gnu.org/licenses/lgpl-3.0.html>.
*
*	Neither the name "Onsiea", "Onsiea Engine", or any derivative name or the names of its authors / contributors may be used to endorse or promote products derived from this software and even less to name another project or other work without clear and precise permissions written in advance.
*
*	(more details on https://github.com/Onsiea/OnsieaEngine/wiki/License)
*
*	@author Seynax
*/
package fr.onsiea.engine.utils.maths.normalization.normalizable;

import fr.onsiea.engine.utils.IListener;
import fr.onsiea.engine.utils.ISetter;

/**
 * @author Seynax
 *
 */
public class NormalizabledWithListener extends NormalizableWithListener<Double>
{
	/**
	 * @param valueListenerIn
	 * @param valueSetterIn
	 * @param maxListenerIn
	 */
	public NormalizabledWithListener(final IListener<Double> valueListenerIn, final ISetter<Double> valueSetterIn,
			final IListener<Double> maxListenerIn)
	{
		super(valueListenerIn, valueSetterIn, maxListenerIn);
	}

	public NormalizabledWithListener(final IListener<Double> valueListenerIn, final ISetter<Double> valueSetterIn,
			final NormalizabledWithListener normalizableIn)
	{
		super(valueListenerIn, valueSetterIn, normalizableIn);
	}

	@Override
	public NormalizabledWithListener invert()
	{
		this.value(this.max() - this.notNormalized());

		return this;
	}

	@Override
	public Double inverted()
	{
		return this.max() - this.notNormalized();
	}

	@Override
	public Double percent()
	{
		return this.notNormalized() / this.max();
	}

	@Override
	public Double invertedPercent()
	{
		return 1.0f - this.notNormalized() / this.max();
	}

	@Override
	public Double centered()
	{
		return this.percent() * 2.0f - 1.0f;
	}

	@Override
	public Double invertedCentered()
	{
		return 1.0f - this.percent() * 2.0f;
	}

	@Override
	public Double centered(final Double relativeMaxIn)
	{
		return this.notNormalized() * 2.0f - relativeMaxIn;
	}

	@Override
	public Double invertedCentered(final Double relativeMaxIn)
	{
		return relativeMaxIn - this.notNormalized() * 2.0f;
	}

	@Override
	public Double between(final Double minIn, final Double maxIn)
	{
		return this.percent() * (maxIn - minIn) + minIn;
	}

	@Override
	public Double invertedBetween(final Double minIn, final Double maxIn)
	{
		return this.invertedPercent() * (maxIn - minIn) + minIn;
	}

	/**
	 * Between minIn and max()
	 * @param minIn
	 * @return
	 */
	@Override
	public Double between(final Double minIn)
	{
		return this.percent() * (this.max() - minIn) + minIn;
	}

	/**
	 * Between max() and minIn
	 * @param minIn
	 * @return
	 */
	@Override
	public Double invertedBetween(final Double minIn)
	{
		return this.invertedPercent() * (this.max() - minIn) + minIn;
	}
}