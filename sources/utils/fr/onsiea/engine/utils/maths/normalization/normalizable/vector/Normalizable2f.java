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
package fr.onsiea.engine.utils.maths.normalization.normalizable.vector;

import org.joml.Vector2f;

import fr.onsiea.engine.utils.IListener;
import fr.onsiea.engine.utils.maths.normalization.normalizable.NormalizablefWithListener;
import lombok.Getter;

/**
 * @author Seynax
 *
 */
public class Normalizable2f
{
	private @Getter final Vector2f					vector;
	private @Getter final NormalizablefWithListener	xNorm;
	private @Getter final NormalizablefWithListener	yNorm;

	public Normalizable2f(final Vector2f vectorIn, final IListener<Float> xMaxListenerIn,
			final IListener<Float> yMaxListenerIn)
	{
		this.vector	= vectorIn;

		this.xNorm	= new NormalizablefWithListener(() -> vectorIn.x, newXIn -> vectorIn.x = newXIn, xMaxListenerIn);
		this.yNorm	= new NormalizablefWithListener(() -> vectorIn.y, newYIn -> vectorIn.y = newYIn, yMaxListenerIn);
	}

	public Normalizable2f(final float xIn, final float yIn, final IListener<Float> xMaxListenerIn,
			final IListener<Float> yMaxListenerIn)
	{
		this.vector	= new Vector2f(xIn, yIn);

		this.xNorm	= new NormalizablefWithListener(() -> this.vector.x, newXIn -> this.vector.x = newXIn,
				xMaxListenerIn);
		this.yNorm	= new NormalizablefWithListener(() -> this.vector.y, newYIn -> this.vector.y = newYIn,
				yMaxListenerIn);
	}

	public Normalizable2f(final Normalizable2f normalizable2fIn)
	{
		this.vector	= new Vector2f(normalizable2fIn.vector());

		this.xNorm	= new NormalizablefWithListener(() -> this.vector.x, newXIn -> this.vector.x = newXIn,
				normalizable2fIn.xNorm());
		this.yNorm	= new NormalizablefWithListener(() -> this.vector.y, newYIn -> this.vector.y = newYIn,
				normalizable2fIn.yNorm());
	}

	public Normalizable2f set(final Normalizable2f normalizable2dIn)
	{
		this.vector.x	= normalizable2dIn.vector.x;
		this.vector.y	= normalizable2dIn.vector.y;

		return this;
	}

	public Normalizable2f set(final float xIn, final float yIn)
	{
		this.vector.x	= xIn;
		this.vector.y	= yIn;

		return this;
	}

	public float x()
	{
		return this.vector.x;
	}

	public Normalizable2f x(final float xIn)
	{
		this.vector.x = xIn;

		return this;
	}

	public float y()
	{
		return this.vector.y;
	}

	public Normalizable2f y(final float yIn)
	{
		this.vector.y = yIn;

		return this;
	}
}