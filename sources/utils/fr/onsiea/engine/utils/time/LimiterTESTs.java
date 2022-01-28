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
package fr.onsiea.engine.utils.time;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Seynax
 *
 */

public class LimiterTESTs
{
	private void complex()
	{
		// Parameters

		final var	TARGET_IPS		= 45.0D;
		final var	TARGET_UPS		= 30.0D;
		final var	TARGET_FPS		= 60.0D;

		// Constants

		final var	secsPerInput	= 1.0D / TARGET_IPS;
		final var	secsPerUpdate	= 1.0D / TARGET_IPS;
		final var	secsPerFrame	= 1.0D / TARGET_IPS;

		// Variables

		final var	loopTimer		= MSTimer.create();
		final var	inputTimer		= MSTimer.create();
		final var	updateTimer		= MSTimer.create();
		final var	renderTimer		= MSTimer.create();

		{
			loopTimer.start();

			// Input
			{
				inputTimer.start();
				inputTimer.stop();
			}
			// Update
			{
				updateTimer.start();
				updateTimer.stop();
			}
			// Render
			{
				renderTimer.start();
				renderTimer.stop();
			}

			loopTimer.stop();
		}
	}

	private void a()
	{
		long		start		= 0L, stop = 0L, last = System.nanoTime(), actual = 0L;
		var			lastShow	= System.nanoTime();
		var			actualShow	= 0L;
		double		time, showTime;
		final var	FPS			= 120;
		var			executions	= 0;

		{
			actual	= System.nanoTime();

			time	= (actual - last) / 1_000_000D;

			if (time >= 1000.0D / FPS - (stop - start))
			{
				start	= System.nanoTime();

				// executions

				stop	= System.nanoTime();

				last	= actual;

				executions++;
			}

			actualShow	= System.nanoTime();
			showTime	= (actualShow - lastShow) / 1_000_000D;

			if (showTime >= 1_000D)
			{
				System.out.println("FPS : " + executions);

				executions	= 0;

				lastShow	= actualShow;
			}
		}
	}

	@Getter(value = AccessLevel.PACKAGE)
	@Setter(value = AccessLevel.PRIVATE)
	public final static class MSTimer
	{
		public static MSTimer create()
		{
			return new MSTimer();
		}

		private double	start;
		private double	stop;
		private double	time;

		public MSTimer()
		{
			this.start(LimiterTESTs.time());
			this.stop(this.start());
			this.time(0.0D);
		}

		public MSTimer startTimer()
		{
			this.start(LimiterTESTs.time());
			this.stop(this.start());

			return this;
		}

		public double stopTimer()
		{
			this.stop(LimiterTESTs.time());
			this.time(this.stop() - this.start());
			this.startTimer();

			return this.time();
		}
	}

	/**
	 * MS
	 * @return
	 */
	private final static double time()
	{
		return System.nanoTime() / 1_000_000.0D;
	}

	private void simple()
	{
		final var	time	= 1L;
		long		current, actual = System.nanoTime(), last = actual;

		{
			actual	= System.nanoTime();

			current	= (last - actual) / 1_000_000L;	// MS

			if (current >= time)
			{
				// executions

				last = System.nanoTime();
			}
		}
	}

	private void update()
	{
		final var	secsPerUpdate	= 1.0D / 30.0D;

		double		steps			= 0.0D, loopStartTime;
		double		elapsed, last;

		{
			loopStartTime	= 1.0D * (System.nanoTime() / 1_000_000D);
			last			= loopStartTime;
			elapsed			= loopStartTime - last;
			steps			+= elapsed;

			while (steps >= secsPerUpdate)
			{
				// executions
				steps -= secsPerUpdate;
			}
		}
	}

	private void sync(double loopStartTime)
	{
		final var	TARGET_FPS	= 50.0D;

		final var	secsPerFPS	= 1.0D / TARGET_FPS;

		{
			// executions

			final var endTime = loopStartTime + secsPerFPS;
			while (System.nanoTime() / 1_000_000D < endTime)
			{
				try
				{
					Thread.sleep(1);
				}
				catch (final InterruptedException ie)
				{
				}
			}
		}
	}

	private void variableTimestep()
	{
		double		fps				= 0, lastFpsTime = 0;

		final var	TARGET_FPS		= 60;
		final long	OPTIMAL_TIME	= 1000000000 / TARGET_FPS;

		var			lastLoopTime	= System.nanoTime();

		// keep looping round til the game ends
		{
			// work out how long its been since the last update, this
			// will be used to calculate how far the entities should
			// move this loop
			final var	now				= System.nanoTime();
			final var	updateLength	= now - lastLoopTime;
			lastLoopTime = now;
			final var delta = updateLength / (double) OPTIMAL_TIME;

			// update the frame counter
			lastFpsTime += updateLength;
			fps++;

			// update our FPS counter if a second has passed since
			// we last recorded
			if (lastFpsTime >= 1000000000)
			{
				System.out.println("(FPS: " + fps + ")");
				lastFpsTime	= 0;
				fps			= 0;
			}

			// update the game logic

			// update executions (delta)

			// draw everyting

			// render executions

			// we want each frame to take 10 milliseconds, to do this
			// we've recorded when we started the frame. We add 10 milliseconds
			// to this and then factor in the current time to give
			// us our final value to wait for
			// remember this is in ms, whereas our lastLoopTime etc. vars are in ns.
			try
			{
				Thread.sleep((lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000);
			}
			catch (final InterruptedException ie)
			{

			}
		}
	}

	private void fixedTimestep()
	{
		double		fps, frameCount = 0;

		//This value would probably be stored elsewhere.
		final var	GAME_HERTZ					= 30.0;
		//Calculate how many ns each frame should take for our target game hertz.
		final var	TIME_BETWEEN_UPDATES		= 1000000000 / GAME_HERTZ;
		//At the very most we will update the game this many times before a new render.
		//If you're worried about visual hitches more than perfect timing, set this to 1.
		final var	MAX_UPDATES_BEFORE_RENDER	= 5;
		//We will need the last update time.
		double		lastUpdateTime				= System.nanoTime();
		//Store the last time we rendered.
		double		lastRenderTime				= System.nanoTime();

		//If we are able to get as high as this FPS, don't render again.
		final var	TARGET_FPS					= 60D;
		final var	TARGET_TIME_BETWEEN_RENDERS	= 1000000000 / TARGET_FPS;

		//Simple way of finding FPS.
		var			lastSecondTime				= (int) (lastUpdateTime / 1000000000);

		{
			double	now			= System.nanoTime();
			var		updateCount	= 0;

			//Do as many game updates as we need to, potentially playing catchup.
			while (now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER)
			{
				// update
				lastUpdateTime += TIME_BETWEEN_UPDATES;
				updateCount++;
			}

			//If for some reason an update takes forever, we don't want to do an insane number of catchups.
			//If you were doing some sort of game that needed to keep EXACT time, you would get rid of this.
			if (now - lastUpdateTime > TIME_BETWEEN_UPDATES)
			{
				lastUpdateTime = now - TIME_BETWEEN_UPDATES;
			}

			//Render. To do so, we need to calculate interpolation for a smooth render.
			@SuppressWarnings("unused")
			final var interpolation = Math.min(1.0f, (float) ((now - lastUpdateTime) / TIME_BETWEEN_UPDATES));
			// render (interpolation);
			lastRenderTime = now;

			//Update the frames we got.
			final var thisSecond = (int) (lastUpdateTime / 1000000000);
			if (thisSecond > lastSecondTime)
			{
				System.out.println("NEW SECOND " + thisSecond + " " + frameCount);
				fps				= frameCount;
				frameCount		= 0;
				lastSecondTime	= thisSecond;
			}

			//Yield until it has been at least the target time between renders. This saves the CPU from hogging.
			while (now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES)
			{
				//Thread.yield();

				//This stops the app from consuming all your CPU. It makes this slightly less accurate, but is worth it.
				//You can remove this line and it will still work (better), your CPU just climbs on certain OSes.
				//FYI on some OS's this can cause pretty bad stuttering.
				try
				{
					Thread.sleep(1);
				}
				catch (final Exception e)
				{
				}

				now = System.nanoTime();
			}
		}
	}
}
