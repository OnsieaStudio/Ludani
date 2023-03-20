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

import org.joml.Vector3d;

import fr.onsiea.engine.utils.IListener;
import fr.onsiea.engine.utils.maths.normalization.normalizable.NormalizabledWithListener;
import lombok.Getter;

/**
 * @author Seynax
 *
 */
public class Normalizable3d
{
	private @Getter final Vector3d					vector;
	private @Getter final NormalizabledWithListener	xNorm;
	private @Getter final NormalizabledWithListener	yNorm;
	private @Getter final NormalizabledWithListener	zNorm;

	public Normalizable3d(final Vector3d vectorIn, final IListener<Double> xMaxListenerIn,
			final IListener<Double> yMaxListenerIn, final IListener<Double> zMaxListenerIn)
	{
		this.vector	= vectorIn;

		this.xNorm	= new NormalizabledWithListener(() -> vectorIn.x, newXIn -> vectorIn.x = newXIn, xMaxListenerIn);
		this.yNorm	= new NormalizabledWithListener(() -> vectorIn.y, newYIn -> vectorIn.y = newYIn, yMaxListenerIn);
		this.zNorm	= new NormalizabledWithListener(() -> vectorIn.z, newZIn -> vectorIn.z = newZIn, zMaxListenerIn);
	}

	public Normalizable3d(final double xIn, final double yIn, final double zIn, final IListener<Double> xMaxListenerIn,
			final IListener<Double> yMaxListenerIn, final IListener<Double> zMaxListenerIn)
	{
		this.vector	= new Vector3d(xIn, yIn, zIn);

		this.xNorm	= new NormalizabledWithListener(() -> this.vector.x, newXIn -> this.vector.x = newXIn,
				xMaxListenerIn);
		this.yNorm	= new NormalizabledWithListener(() -> this.vector.y, newYIn -> this.vector.y = newYIn,
				yMaxListenerIn);
		this.zNorm	= new NormalizabledWithListener(() -> this.vector.z, newZIn -> this.vector.z = newZIn,
				zMaxListenerIn);
	}

	public Normalizable3d(final Normalizable3d normalizable3dIn)
	{
		this.vector	= new Vector3d(normalizable3dIn.vector());

		this.xNorm	= new NormalizabledWithListener(() -> this.vector.x, newXIn -> this.vector.x = newXIn,
				normalizable3dIn.xNorm());
		this.yNorm	= new NormalizabledWithListener(() -> this.vector.y, newYIn -> this.vector.y = newYIn,
				normalizable3dIn.yNorm());
		this.zNorm	= new NormalizabledWithListener(() -> this.vector.z, newZIn -> this.vector.z = newZIn,
				normalizable3dIn.zNorm());
	}

	public Normalizable3d set(final Normalizable3d normalizable2dIn)
	{
		this.vector.x	= normalizable2dIn.vector.x;
		this.vector.y	= normalizable2dIn.vector.y;
		this.vector.z	= normalizable2dIn.vector.z;

		return this;
	}

	public Normalizable3d set(final double xIn, final double yIn, final double zIn)
	{
		this.vector.x	= xIn;
		this.vector.y	= yIn;
		this.vector.z	= zIn;

		return this;
	}

	public double x()
	{
		return this.vector.x;
	}

	public Normalizable3d x(final double xIn)
	{
		this.vector.x = xIn;

		return this;
	}

	public double y()
	{
		return this.vector.y;
	}

	public Normalizable3d y(final double yIn)
	{
		this.vector.y = yIn;

		return this;
	}

	public double z()
	{
		return this.vector.z;
	}

	public Normalizable3d z(final double zIn)
	{
		this.vector.z = zIn;

		return this;
	}
}