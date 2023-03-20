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

import org.joml.Vector4f;

import fr.onsiea.engine.utils.IListener;
import fr.onsiea.engine.utils.maths.normalization.normalizable.NormalizablefWithListener;
import lombok.Getter;

/**
 * @author Seynax
 *
 */
public class Normalizable4f
{
	private @Getter final Vector4f					vector;
	private @Getter final NormalizablefWithListener	xNorm;
	private @Getter final NormalizablefWithListener	yNorm;
	private @Getter final NormalizablefWithListener	zNorm;
	private @Getter final NormalizablefWithListener	wNorm;

	public Normalizable4f(final Vector4f vectorIn, final IListener<Float> xMaxListenerIn,
			final IListener<Float> yMaxListenerIn, final IListener<Float> zMaxListenerIn,
			final IListener<Float> wMaxListenerIn)
	{
		this.vector	= vectorIn;

		this.xNorm	= new NormalizablefWithListener(() -> vectorIn.x, newXIn -> vectorIn.x = newXIn, xMaxListenerIn);
		this.yNorm	= new NormalizablefWithListener(() -> vectorIn.y, newYIn -> vectorIn.y = newYIn, yMaxListenerIn);
		this.zNorm	= new NormalizablefWithListener(() -> vectorIn.z, newZIn -> vectorIn.z = newZIn, zMaxListenerIn);
		this.wNorm	= new NormalizablefWithListener(() -> vectorIn.w, newWIn -> vectorIn.w = newWIn, wMaxListenerIn);
	}

	public Normalizable4f(final float xIn, final float yIn, final float zIn, final float wIn,
			final IListener<Float> xMaxListenerIn, final IListener<Float> yMaxListenerIn,
			final IListener<Float> zMaxListenerIn, final IListener<Float> wMaxListenerIn)
	{
		this.vector	= new Vector4f(xIn, yIn, zIn, wIn);

		this.xNorm	= new NormalizablefWithListener(() -> this.vector.x, newXIn -> this.vector.x = newXIn,
				xMaxListenerIn);
		this.yNorm	= new NormalizablefWithListener(() -> this.vector.y, newYIn -> this.vector.y = newYIn,
				yMaxListenerIn);
		this.zNorm	= new NormalizablefWithListener(() -> this.vector.z, newZIn -> this.vector.z = newZIn,
				zMaxListenerIn);
		this.wNorm	= new NormalizablefWithListener(() -> this.vector.w, newWIn -> this.vector.w = newWIn,
				wMaxListenerIn);
	}

	public Normalizable4f(final Normalizable4f normalizable4fIn)
	{
		this.vector	= new Vector4f(normalizable4fIn.vector());

		this.xNorm	= new NormalizablefWithListener(() -> this.vector.x, newXIn -> this.vector.x = newXIn,
				normalizable4fIn.xNorm());
		this.yNorm	= new NormalizablefWithListener(() -> this.vector.y, newYIn -> this.vector.y = newYIn,
				normalizable4fIn.yNorm());
		this.zNorm	= new NormalizablefWithListener(() -> this.vector.z, newZIn -> this.vector.z = newZIn,
				normalizable4fIn.zNorm());
		this.wNorm	= new NormalizablefWithListener(() -> this.vector.w, newWIn -> this.vector.w = newWIn,
				normalizable4fIn.wNorm());
	}

	public Normalizable4f set(final Normalizable4f normalizable2dIn)
	{
		this.vector.x	= normalizable2dIn.vector.x;
		this.vector.y	= normalizable2dIn.vector.y;
		this.vector.z	= normalizable2dIn.vector.z;
		this.vector.w	= normalizable2dIn.vector.w;

		return this;
	}

	public Normalizable4f set(final float xIn, final float yIn, final float zIn, final float wIn)
	{
		this.vector.x	= xIn;
		this.vector.y	= yIn;
		this.vector.z	= zIn;
		this.vector.w	= wIn;

		return this;
	}

	public float x()
	{
		return this.vector.x;
	}

	public Normalizable4f x(final float xIn)
	{
		this.vector.x = xIn;

		return this;
	}

	public float y()
	{
		return this.vector.y;
	}

	public Normalizable4f y(final float yIn)
	{
		this.vector.y = yIn;

		return this;
	}

	public float z()
	{
		return this.vector.z;
	}

	public Normalizable4f z(final float zIn)
	{
		this.vector.z = zIn;

		return this;
	}

	public float w()
	{
		return this.vector.w;
	}

	public Normalizable4f w(final float wIn)
	{
		this.vector.w = wIn;

		return this;
	}
}