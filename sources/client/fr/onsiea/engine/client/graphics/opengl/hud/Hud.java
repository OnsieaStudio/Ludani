package fr.onsiea.engine.client.graphics.opengl.hud;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.Renderer;

import fr.onsiea.engine.client.graphics.glfw.window.Window;
import fr.onsiea.engine.client.graphics.opengl.nanovg.NanoVGUtils;
import fr.onsiea.engine.client.graphics.texture.TexturesManager;
import fr.onsiea.engine.core.entity.Camera;
import fr.seynax.onsiea.graphics.opengl.mesh.GLMesh;
import fr.seynax.onsiea.graphics.opengl.shader.hud.HudShader;
import fr.seynax.onsiea.graphics.opengl.shaders.SimpleShader;
import fr.seynax.onsiea.input.InputManager;

public class Hud
{
	private List<IHudComponent>	components;
	private boolean				isOpen;
	private boolean				mustPause;

	public Hud(IHudComponent... componentsIn)
	{
		this.components(new ArrayList<>());

		Collections.addAll(this.components(), componentsIn);

		this.mustPause(true);
	}

	public void initialization(Window windowIn, Camera cameraIn, TexturesManager texturesManagerIn)
	{
		for (final IHudComponent component : this.components())
		{
			component.initialization(windowIn, cameraIn);
		}
	}

	public void input(HudManager hudManagerIn, InputManager inputManagerIn, Window windowIn)
	{
		this.isOpen(false);

		if (!this.isOpen())
		{
			return;
		}

		for (final IHudComponent component : this.components())
		{
			component.input(inputManagerIn, windowIn);
		}
	}

	public void update(Window windowIn, Camera cameraIn)
	{
		if (!this.isOpen())
		{
			return;
		}

		for (final IHudComponent component : this.components())
		{
			component.update(windowIn, cameraIn);
		}
	}

	public void draw(Window windowIn, Camera cameraIn, HudShader hudShaderIn, GLMesh rectangleIn, Renderer rendererIn)
	{
		if (!this.isOpen())
		{
			return;
		}

		for (final IHudComponent component : this.components())
		{
			component.draw(windowIn, cameraIn, hudShaderIn, rectangleIn, rendererIn);
		}
	}

	public void draw(Window windowIn, Camera cameraIn, HudShader hudshaderIn, InputManager inputManagerIn,
			NanoVGUtils nanoVGUtilsIn)
	{
		if (!this.isOpen())
		{
			return;
		}

		for (final IHudComponent component : this.components())
		{
			component.draw(windowIn, cameraIn, hudshaderIn, inputManagerIn, nanoVGUtilsIn);
		}
	}

	public void draw3D(Window windowIn, Camera cameraIn, SimpleShader simpleShaderIn)
	{
		if (!this.isOpen())
		{
		}
	}

	public void cleanup()
	{
		for (final IHudComponent component : this.components())
		{
			component.cleanup();
		}
	}

	public Hud addComponent(IHudComponent componentIn)
	{
		this.components().add(componentIn);

		return this;
	}

	public Hud addComponents(IHudComponent... componentsIn)
	{
		Collections.addAll(this.components(), componentsIn);

		return this;
	}

	public IHudComponent getComponent(int indexIn)
	{
		if (this.isCorrectComponentIndex(indexIn))
		{
			return this.components().get(indexIn);
		}

		return null;
	}

	public Hud removeComponent(int indexIn)
	{
		if (this.isCorrectComponentIndex(indexIn))
		{
			this.components().remove(indexIn);
		}
		return this;
	}

	public boolean isCorrectComponentIndex(int indexIn)
	{
		return indexIn >= 0 && indexIn < this.components().size();
	}

	public int componentsNumber()
	{
		return this.components().size();
	}

	protected List<IHudComponent> components()
	{
		return this.components;
	}

	private void components(List<IHudComponent> componentsIn)
	{
		this.components = componentsIn;
	}

	public boolean isOpen()
	{
		return this.isOpen;
	}

	protected void isOpen(boolean isOpenIn)
	{
		this.isOpen = isOpenIn;
	}

	public boolean mustPause()
	{
		return this.mustPause;
	}

	protected void mustPause(boolean mustPauseIn)
	{
		this.mustPause = mustPauseIn;
	}
}
