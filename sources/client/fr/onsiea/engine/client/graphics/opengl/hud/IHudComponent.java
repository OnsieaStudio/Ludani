package fr.onsiea.engine.client.graphics.opengl.hud.component;

import javax.swing.Renderer;

import fr.onsiea.engine.client.graphics.glfw.window.Window;
import fr.onsiea.engine.client.graphics.opengl.nanovg.NanoVGUtils;
import fr.seynax.onsiea.game.entity.camera.Camera;
import fr.seynax.onsiea.graphics.opengl.mesh.GLMesh;
import fr.seynax.onsiea.graphics.opengl.shader.hud.HudShader;
import fr.seynax.onsiea.input.InputManager;

public interface IHudComponent
{
	void initialization(Window windowIn, Camera cameraIn);

	void input(InputManager inputManagerIn, Window windowIn);

	void update(Window windowIn, Camera cameraIn);

	void draw(Window windowIn, Camera cameraIn, HudShader hudShaderIn, GLMesh rectangleIn, Renderer rendererIn);

	void draw(Window windowIn, Camera cameraIn, HudShader hudShaderIn, InputManager inputManagerIn,
			NanoVGUtils nanoVGUtilsIn);

	void cleanup();
}