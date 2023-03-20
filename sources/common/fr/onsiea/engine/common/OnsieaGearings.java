/**
 * Copyright 2021 Onsiea All rights reserved.<br>
 * <br>
 *
 * This file is part of Onsiea Engine project.
 * (https://github.com/Onsiea/OnsieaEngine)<br>
 * <br>
 *
 * Onsiea Engine is [licensed]
 * (https://github.com/Onsiea/OnsieaEngine/blob/main/LICENSE) under the terms of
 * the "GNU General Public Lesser License v2.1" (LGPL-2.1).
 * https://github.com/Onsiea/OnsieaEngine/wiki/License#license-and-copyright<br>
 * <br>
 *
 * Onsiea Engine is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.<br>
 * <br>
 *
 * Onsiea Engine is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.<br>
 * <br>
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Onsiea Engine. If not, see <https://www.gnu.org/licenses/>.<br>
 * <br>
 *
 * Neither the name "Onsiea", "Onsiea Engine", or any derivative name or the
 * names of its authors / contributors may be used to endorse or promote
 * products derived from this software and even less to name another project or
 * other work without clear and precise permissions written in advance.<br>
 * <br>
 *
 * @Author : Seynax (https://github.com/seynax)<br>
 * @Organization : Onsiea Studio (https://github.com/Onsiea)
 */
package fr.onsiea.engine.common;

import org.lwjgl.glfw.GLFW;

import fr.onsiea.engine.client.graphics.GraphicsConstants;
import fr.onsiea.engine.client.graphics.glfw.GLFWManager;
import fr.onsiea.engine.client.graphics.glfw.window.Window;
import fr.onsiea.engine.client.graphics.glfw.window.WindowSettings;
import fr.onsiea.engine.client.graphics.glfw.window.WindowShowType;
import fr.onsiea.engine.client.graphics.glfw.window.context.GLWindowContext;
import fr.onsiea.engine.client.graphics.render.IRenderAPIContext;
import fr.onsiea.engine.client.graphics.render.Renderer;
import fr.onsiea.engine.client.graphics.window.IWindow;
import fr.onsiea.engine.client.lwjgl.LWJGLContext;
import fr.onsiea.engine.client.sound.SoundManager;
import fr.onsiea.engine.common.game.GameOptions;
import fr.onsiea.engine.common.game.IGameLogic;
import fr.onsiea.engine.utils.time.Timer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Seynax
 *
 */

@Getter(value = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PRIVATE)
public class OnsieaGearings
{
	private IGameLogic			gameLogic;
	private GameOptions			options;
	private String[]			args;

	private boolean				running;

	private GLFWManager			glfwManager;
	private SoundManager		soundManager;
	private IWindow				window;
	private Timer				timer;
	private Timer				inputTimer;
	private Timer				framerateCounterTimer;
	private IRenderAPIContext	renderAPIContext;
	private Renderer			renderer;

	@Getter
	public final static class DataCounter
	{
		double	refreshRate;
		double	updateRate;
		double	inputRate;
		double	secsPerFrame;
		double	secsPerUpdate;
		double	secsPerInput;
		double	elapsedTime;
		double	accumulator			= 0.0D;
		int		framerateCounter	= 0;
		int		framerateValue		= 0;
		double	loopStartTime;
	}

	private @Getter DataCounter dataCounter;

	public final static OnsieaGearings start(final IGameLogic gameLogicIn, final GameOptions optionsIn,
			final String[] argsIn) throws Exception
	{
		return new OnsieaGearings(gameLogicIn, optionsIn, argsIn);
	}

	private OnsieaGearings(final IGameLogic gameLogicIn, final GameOptions optionsIn, final String[] argsIn)
			throws Exception
	{
		if (gameLogicIn == null)
		{
			throw new Exception("[ERROR] GameLogic is null !");
		}
		if (optionsIn == null)
		{
			throw new Exception("[ERROR] GameOptions are null !");
		}

		try
		{
			this.gameLogic(gameLogicIn);
			this.options(optionsIn);
			this.args(argsIn);

			{
				if (GraphicsConstants.DEBUG)
				{
					LWJGLContext.enableDebugging();
					LWJGLContext.enableDebugStack();
				}

				this.gameLogic().preInitialization();
				final var windowContext = new GLWindowContext();
				this.glfwManager(
						new GLFWManager().initialization(
								WindowSettings.Builder
										.base("Onsiea Engine !", GraphicsConstants.DEFAULT_WIDTH,
												GraphicsConstants.DEFAULT_HEIGHT, 60, WindowShowType.WINDOWED_WIDE)
										.mustMaximized(false).mustFocusOnShow(true).mustIconify(true)
										.mustBeFocused(false).mustFloating(false).mustScaleToMonitor(false).create(),
								windowContext));
				this.window(this.glfwManager().window());
				GLFW.glfwRequestWindowAttention(((Window) this.window).handle());
				this.glfwManager().inputManager().cursor().blockedPosition(this.window().effectiveWidth() / 2.0D,
						this.window().settings().height() / 2.0D);
				this.glfwManager().inputManager().cursor().mustBeBlocked();
				this.renderAPIContext	= windowContext.context();
				this.renderer			= new Renderer(this.renderAPIContext);
				this.gameLogic().initialization(this.window, this.renderAPIContext, this.glfwManager().inputManager());
			}

			{
				this.timer(new Timer());
				this.inputTimer(new Timer());
				this.framerateCounterTimer(new Timer());

				this.dataCounter				= new DataCounter();
				this.dataCounter.refreshRate	= 60.0D;
				this.dataCounter.updateRate		= 60.0D;
				this.dataCounter.inputRate		= 60.0D;
				this.dataCounter.secsPerFrame	= 1.0d / this.dataCounter.refreshRate;
				this.dataCounter.secsPerUpdate	= 1.0d / this.dataCounter.updateRate;
				this.dataCounter.secsPerInput	= 1.0d / this.dataCounter.inputRate;
			}

			this.loop();
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.cleanup();
		}
	}

	private void loop()
	{
		this.running(true);

		while (this.running() && !this.window().shouldClose())
		{
			this.dataCounter.loopStartTime	= System.nanoTime();
			this.dataCounter.elapsedTime	= this.timer().time() / 1_000_000_000D;
			this.dataCounter.accumulator	+= this.dataCounter.elapsedTime;

			this.runtime();
		}
	}

	private void runtime()
	{
		this.window().pollEvents();
		this.glfwManager().inputManager().update();
		this.gameLogic().highRateInput();

		if (this.inputTimer().isTime((long) (this.dataCounter.secsPerInput() * 1_000_000_000L)))
		{
			this.gameLogic().input(this.window(), this.glfwManager().inputManager());
		}

		while (this.dataCounter.accumulator >= this.dataCounter.secsPerUpdate())
		{
			this.gameLogic().update(this.dataCounter);

			this.dataCounter.accumulator -= this.dataCounter.secsPerUpdate();
		}

		this.gameLogic().draw(this.window(), this.renderAPIContext, this.renderer);
		this.window().swapBuffers();

		this.glfwManager().inputManager().reset();

		if (!((Window) this.window()).settings().mustBeSynchronized())
		{
			this.sync(this.dataCounter.loopStartTime, this.dataCounter.secsPerFrame());
		}

		if (this.framerateCounterTimer().isTime(1_000_000_000L))
		{
			this.dataCounter.framerateValue		= this.dataCounter.framerateCounter;
			this.dataCounter.framerateCounter	= 0;
		}
		this.dataCounter.framerateCounter++;
	}

	public final static void sleep(final long timeIn)
	{
		try
		{
			Thread.sleep(timeIn);
		}
		catch (final InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public void sync(final double loopStartTimeIn, final double secsPerFrameIn)
	{
		final var endTime = loopStartTimeIn + secsPerFrameIn * 1_000_000_000D;

		while (System.nanoTime() < endTime)
		{
			OnsieaGearings.sleep(1L);
		}
	}

	private void cleanup()
	{
		this.gameLogic().cleanup();
		this.glfwManager().cleanup();
	}
}