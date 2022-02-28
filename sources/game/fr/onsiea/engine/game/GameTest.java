/**
* Copyright 2021 Onsiea All rights reserved.<br><br>
*
* This file is part of Onsiea Engine project. (https://github.com/Onsiea/OnsieaEngine)<br><br>
*
* Onsiea Engine is [licensed] (https://github.com/Onsiea/OnsieaEngine/blob/main/LICENSE) under the terms of the "GNU General Public Lesser License v2.1" (LGPL-2.1).
* https://github.com/Onsiea/OnsieaEngine/wiki/License#license-and-copyright<br><br>
*
* Onsiea Engine is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 2.1 of the License, or
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
package fr.onsiea.engine.game;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import fr.onsiea.engine.client.graphics.glfw.window.Window;
import fr.onsiea.engine.client.graphics.opengl.OpenGLUtils;
import fr.onsiea.engine.client.graphics.opengl.nanovg.NanoVGManager;
import fr.onsiea.engine.client.graphics.opengl.shader.Shader;
import fr.onsiea.engine.client.graphics.opengl.shader.ShaderBasic;
import fr.onsiea.engine.client.graphics.shapes.AlignedShapeIndexer;
import fr.onsiea.engine.client.graphics.shapes.Cube;
import fr.onsiea.engine.client.graphics.window.IWindow;
import fr.onsiea.engine.common.OnsieaGearings;
import fr.onsiea.engine.common.game.GameOptions;
import fr.onsiea.engine.common.game.IGameLogic;
import fr.onsiea.engine.core.entity.Camera;
import fr.onsiea.engine.maths.MathInstances;
import fr.onsiea.engine.maths.projections.Projections;
import fr.onsiea.engine.utils.time.Timer;

/**
 * @author Seynax
 *
 */
public class GameTest implements IGameLogic
{
	public final static void main(String[] argsIn)
	{
		try
		{
			OnsieaGearings.start(new GameTest(), GameOptions.of(), argsIn);
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

	private NanoVGManager	nanoVG;
	private int				vao;
	private int				vbo;
	private ShaderBasic		shaderBasic;
	private Camera			camera;
	private NVGColor		color;

	@Override
	public boolean preInitialization()
	{
		return true;
	}

	@Override
	public boolean initialization(IWindow windowIn)
	{
		AlignedShapeIndexer.runtime();
		try
		{
			((Window) windowIn).icon("resources/textures/aeison.png");
			this.nanoVG	= new NanoVGManager(windowIn);
			this.color	= NVGColor.malloc();
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		this.vao = GL30.glGenVertexArrays();

		GL30.glBindVertexArray(this.vao);

		GL20.glEnableVertexAttribArray(0);
		//GL20.glEnableVertexAttribArray(1);

		this.vbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, Cube.positionsForIndices, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0L);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		/**final var ubo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, ubo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, Cube.textureCoordinates, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0L);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);**/

		final var ibo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, Cube.INDICES, GL15.GL_STATIC_DRAW);

		//GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(0);

		GL30.glBindVertexArray(0);

		this.camera = new Camera();

		try
		{
			this.shaderBasic = new ShaderBasic();

			this.shaderBasic.start();
			this.shaderBasic.projectionMatrix().load(Projections.of(((Window) windowIn).settings().width(),
					((Window) windowIn).settings().height(), 90.0f, 0.1f, 1000.0f));
			this.shaderBasic.transformationsMatrix().load(MathInstances.simpleTransformationsMatrix3d());
			Shader.stop();
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		return true;
	}

	@Override
	public void highRateInput()
	{
	}

	private final Timer cameraTimer = new Timer();

	@Override
	public void input(IWindow windowIn)
	{
		if (this.cameraTimer.isTime(1_000_000_0L))
		{
			this.camera.input((Window) windowIn);
		}
	}

	@Override
	public void update()
	{
	}

	@Override
	public void draw(IWindow windowIn)
	{
		OpenGLUtils.restoreState();
		//GL11.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_COLOR_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
		this.shaderBasic.start();

		this.shaderBasic.viewMatrix().load(this.camera.viewMatrix());
		this.shaderBasic.transformationsMatrix().load(MathInstances.simpleTransformationsMatrix3d());
		GL30.glBindVertexArray(this.vao);
		GL20.glEnableVertexAttribArray(0);
		//GL20.glEnableVertexAttribArray(1);
		GL11.glDrawElements(GL11.GL_TRIANGLES, 36, GL11.GL_UNSIGNED_INT, 0);
		//GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, Cube.INDICES.length);
		//GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		Shader.stop();

		/**
		this.nanoVG.startRender(windowIn);
		this.nanoVG.nanoVGFonts().draw(42, "ARIAL", NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP,
				NanoVGManager.set(this.color, 100, 125, 127, 255), 0, 0, "Ceci est un texte !");
		this.nanoVG.finishRender();**/
	}

	@Override
	public void cleanup()
	{
		if (this.color != null)
		{
			this.color.free();
		}

		this.nanoVG.cleanup();

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glDeleteBuffers(this.vbo);
		GL30.glBindVertexArray(0);
		GL30.glDeleteVertexArrays(this.vao);
	}
}