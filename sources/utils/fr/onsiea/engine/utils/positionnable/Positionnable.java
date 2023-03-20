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
package fr.onsiea.engine.utils.positionnable;

import fr.onsiea.engine.utils.IListener;
import fr.onsiea.engine.utils.maths.normalization.normalizable.vector.Normalizable2f;
import lombok.Getter;

/**
 * @author Seynax
 *
 */
public class Positionnable implements IPositionnable
{
	private @Getter final Normalizable2f	position;
	private @Getter final Normalizable2f	size;

	public Positionnable(final IPositionnable positionnableIn)
	{
		this.position	= new Normalizable2f(positionnableIn.position());
		this.size		= new Normalizable2f(positionnableIn.size());
	}

	private Positionnable(final Normalizable2f positionIn, final Normalizable2f sizeIn)
	{
		this.position	= positionIn;
		this.size		= sizeIn;
	}

	@Override
	public Anchor anchor(final float relativeXIn, final float relativeYIn, final float wIn, final float hIn)
	{
		return new Anchor().positionnable(this).set(relativeXIn, relativeYIn, wIn, hIn);
	}

	@Override
	public Anchor anchor()
	{
		return new Anchor().positionnable(this);
	}

	public final static class Builder
	{
		public static Builder of()
		{
			return new Builder();
		}

		public static Positionnable of(final Normalizable2f positionIn, final Normalizable2f sizeIn)
		{
			return new Positionnable(positionIn, sizeIn);
		}

		private IListener<Float>	xMaxListener;
		private IListener<Float>	yMaxListener;

		private float				x;
		private float				y;

		private float				w;
		private float				h;

		private Builder()
		{

		}

		public Builder maxListener(final IListener<Float> maxListenerIn)
		{
			this.xMaxListener	= maxListenerIn;
			this.yMaxListener	= maxListenerIn;

			return this;
		}

		public Builder maxListenerX(final IListener<Float> maxListenerIn)
		{
			this.xMaxListener = maxListenerIn;

			return this;
		}

		public Builder maxListenerY(final IListener<Float> maxListenerIn)
		{
			this.yMaxListener = maxListenerIn;

			return this;
		}

		public Builder maxListener(final IListener<Float> xMaxListenerIn, final IListener<Float> yMaxListenerIn)
		{
			this.xMaxListener	= xMaxListenerIn;
			this.yMaxListener	= yMaxListenerIn;

			return this;
		}

		public Builder set(final float xIn, final float yIn, final float wIn, final float hIn)
		{
			this.x	= xIn;
			this.y	= yIn;
			this.w	= wIn;
			this.h	= hIn;

			return this;
		}

		public Positionnable buildWith(final Normalizable2f positionIn, final Normalizable2f sizeIn)
		{
			return new Positionnable(positionIn, sizeIn);
		}

		public Positionnable build() throws Exception
		{
			if (this.xMaxListener == null || this.yMaxListener == null)
			{
				throw new Exception("[ERROR] HudComponent-Positionnable : x and y max listener cannot be null !");
			}

			return new Positionnable(new Normalizable2f(this.x, this.y, this.xMaxListener, this.yMaxListener),
					new Normalizable2f(this.w, this.h, this.xMaxListener, this.yMaxListener));
		}
	}
}
