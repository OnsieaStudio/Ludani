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
package fr.onsiea.engine.client.graphics.opengl.shader;

import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;

import fr.onsiea.engine.client.graphics.opengl.shader.effects.FlareShader;
import fr.onsiea.engine.client.graphics.opengl.shader.postprocessing.BrightFilterShader;
import fr.onsiea.engine.client.graphics.opengl.shader.postprocessing.CombineShader;
import fr.onsiea.engine.client.graphics.opengl.shader.postprocessing.ContrastShader;
import fr.onsiea.engine.client.graphics.opengl.shader.postprocessing.HorizontalBlurShader;
import fr.onsiea.engine.client.graphics.opengl.shader.postprocessing.VerticalBlurShader;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Seynax
 *
 */
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PRIVATE)
public class GLShaderManager
{
	private @Getter(AccessLevel.PRIVATE) final Map<String, Shader>	shaders;

	// General

	private ShaderBasic												shaderBasic;
	private Shader2D												shader2D;
	private Shader3DTo2D											shader3DTo2D;
	private InstancedShader											instancedShader;

	// Effects

	private FlareShader												flareShader;

	// PostProcessing

	private BrightFilterShader										brightFilter;
	private CombineShader											combine;
	private ContrastShader											contrast;
	private HorizontalBlurShader									horizontalBlur;
	private VerticalBlurShader										verticalBlur;

	public GLShaderManager()
	{
		this.shaders = new HashMap<>();

		try
		{
			this.add("basic", this.shaderBasic = new ShaderBasic());
			this.add("flare", this.flareShader = new FlareShader());
			this.add("2d", this.shader2D = new Shader2D());
			/**this.add("3dto2d", this.shader3DTo2D = new Shader3DTo2D());
			this.add("brightFilter", this.brightFilter = new BrightFilterShader());
			this.add("combine", this.combine = new CombineShader());
			this.add("contrast", this.contrast = new ContrastShader());**/
			this.add("horizontalBlur", this.horizontalBlur = new HorizontalBlurShader());
			this.add("verticalBlur", this.verticalBlur = new VerticalBlurShader());
			this.add("instanced", this.instancedShader = new InstancedShader());
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

	public final GLShaderManager initialization(Matrix4f projectionIn) throws Exception
	{
		this.updateProjection(projectionIn);

		return this;
	}

	public final GLShaderManager initialization(Matrix4f projectionIn, Matrix4f viewIn) throws Exception
	{
		for (final Shader shader : this.shaders().values())
		{
			shader.start();

			if (shader instanceof IView)
			{
				((IView) shader).view().load(viewIn);
			}

			if (shader instanceof IProjection)
			{
				((IProjection) shader).projection().load(projectionIn);
			}

			Shader.stop();
		}

		return this;
	}

	public GLShaderManager updateProjection(Matrix4f projectionIn)
	{
		for (final Shader shader : this.shaders().values())
		{
			shader.start();

			if (shader instanceof IProjection)
			{
				((IProjection) shader).projection().load(projectionIn);
			}

			Shader.stop();
		}

		return this;
	}

	public GLShaderManager updateViewMatrix(Matrix4f viewIn)
	{
		for (final Shader shader : this.shaders().values())
		{
			shader.start();

			if (shader instanceof IView)
			{
				((IView) shader).view().load(viewIn);
			}

			Shader.stop();
		}

		return this;
	}

	public GLShaderManager add(String nameIn, Shader shaderIn)
	{
		this.shaders.put(nameIn, shaderIn);

		return this;
	}

	public Shader get(String nameIn)
	{
		return this.shaders.get(nameIn);
	}

	public boolean contains(String nameIn)
	{
		return this.shaders.containsKey(nameIn);
	}

	public GLShaderManager remove(String nameIn)
	{
		this.shaders.remove(nameIn);

		return this;
	}

	/**
	 *
	 */
	public void cleanup()
	{
		for (final Shader shader : this.shaders.values())
		{
			shader.cleanup();
		}
	}
}