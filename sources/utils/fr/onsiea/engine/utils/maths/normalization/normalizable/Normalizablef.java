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

/**
 * @author Seynax
 *
 */
public class Normalizablef extends Normalizable<Float>
{
	public Normalizablef(final float valueIn, final float maxIn)
	{
		super(valueIn, maxIn);
	}

	@Override
	public Normalizablef invert()
	{
		this.value = this.max() - this.value;

		return this;
	}

	@Override
	public Float inverted()
	{
		return this.max() - this.value;
	}

	@Override
	public Float percent()
	{
		return this.notNormalized() / this.max();
	}

	@Override
	public Float invertedPercent()
	{
		return 1.0f - this.notNormalized() / this.max();
	}

	@Override
	public Float centered()
	{
		return this.percent() * 2.0f - 1.0f;
	}

	@Override
	public Float invertedCentered()
	{
		return 1.0f - this.percent() * 2.0f;
	}

	@Override
	public Float centered(final Float relativeMaxIn)
	{
		return this.notNormalized() * 2.0f - relativeMaxIn;
	}

	@Override
	public Float invertedCentered(final Float relativeMaxIn)
	{
		return relativeMaxIn - this.notNormalized() * 2.0f;
	}

	@Override
	public Float between(final Float minIn, final Float maxIn)
	{
		return this.percent() * (maxIn - minIn) + minIn;
	}

	@Override
	public Float invertedBetween(final Float minIn, final Float maxIn)
	{
		return this.invertedPercent() * (maxIn - minIn) + minIn;
	}

	/**
	 * Between minIn and max()
	 * @param minIn
	 * @return
	 */
	@Override
	public Float between(final Float minIn)
	{
		return this.percent() * (this.max() - minIn) + minIn;
	}

	/**
	 * Between max() and minIn
	 * @param minIn
	 * @return
	 */
	@Override
	public Float invertedBetween(final Float minIn)
	{
		return this.invertedPercent() * (this.max() - minIn) + minIn;
	}
}