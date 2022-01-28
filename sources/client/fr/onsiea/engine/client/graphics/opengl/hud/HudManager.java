package fr.onsiea.engine.client.graphics.opengl.hud;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.Renderer;

import org.lwjgl.opengl.GL11;

import fr.onsiea.engine.client.graphics.glfw.window.Window;
import fr.onsiea.engine.client.graphics.opengl.nanovg.NanoVGUtils;
import fr.seynax.onsiea.game.entity.camera.Camera;
import fr.seynax.onsiea.graphics.opengl.huds.HudTchat;
import fr.seynax.onsiea.graphics.opengl.mesh.GLMesh;
import fr.seynax.onsiea.graphics.opengl.mesh.GLMeshManager;
import fr.seynax.onsiea.graphics.opengl.shader.GLShaderManager;
import fr.seynax.onsiea.graphics.opengl.shader.Shader;
import fr.seynax.onsiea.graphics.shapes.ShapeRectangle;
import fr.seynax.onsiea.graphics.texture.TexturesManager;
import fr.seynax.onsiea.input.InputManager;

public class HudManager
{
	private GLMesh				rectangle;
	private NanoVGUtils			nanoVGUtils;

	private Map<String, Hud>	huds;

	public HudManager(GLMeshManager meshManagerIn)
	{
		final var meshBuilder = meshManagerIn.meshBuilder(1).attribBuffer(2).data(ShapeRectangle.allPositions).unbind()
				.vertexCount(4);
		try
		{
			this.rectangle(meshManagerIn.build("rectangle", meshBuilder));
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		this.huds(new LinkedHashMap<>());
		this.nanoVGUtils(new NanoVGUtils());
	}

	public void initialization(/**BlocksManager blocksManagerIn,**/
	Window windowIn, Camera cameraIn, TexturesManager texturesManagerIn) throws Exception
	{
		this.nanoVGUtils().initialization(windowIn);

		this.huds().put("tchat", new HudTchat());
		//this.huds().put("inventory", new HudCreativeInventory(blocksManagerIn));

		for (final Hud hud : this.huds().values())
		{
			hud.initialization(windowIn, cameraIn, texturesManagerIn);
		}
	}

	public void input(InputManager inputManagerIn, Window windowIn)
	{
		for (final Hud hud : this.huds().values())
		{
			hud.input(this, inputManagerIn, windowIn);
		}
	}

	public void update(Window windowIn, Camera cameraIn)
	{
		for (final Hud hud : this.huds().values())
		{
			hud.update(windowIn, cameraIn);
		}
	}

	public void draw(GLShaderManager shaderManagerIn, Window windowIn, Camera cameraIn, int framerateIn,
			InputManager inputManagerIn, Renderer rendererIn)
	{
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		shaderManagerIn.hudShader().start();
		for (final Hud hud : this.huds().values())
		{
			hud.draw(windowIn, cameraIn, shaderManagerIn.hudShader(), this.rectangle(), rendererIn);
		}
		Shader.stop();
		GL11.glEnable(GL11.GL_DEPTH_TEST);

		this.nanoVGUtils().startRender(windowIn);

		for (final Hud hud : this.huds().values())
		{
			hud.draw(windowIn, cameraIn, shaderManagerIn.hudShader(), inputManagerIn, this.nanoVGUtils());
		}

		this.nanoVGUtils().render(cameraIn, framerateIn, windowIn, inputManagerIn);
		this.nanoVGUtils().finishRender();

		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);

		shaderManagerIn.simpleShader().start();
		//shaderManagerIn.simpleShader().gamma(1.30f);

		for (final Hud hud : this.huds().values())
		{
			hud.draw3D(windowIn, cameraIn, shaderManagerIn.simpleShader());
		}

		Shader.stop();
	}

	public void cleanup()
	{
		for (final Hud hud : this.huds().values())
		{
			hud.cleanup();
		}

		this.nanoVGUtils().cleanup();
	}

	public boolean isPause()
	{
		for (final Hud hud : this.huds().values())
		{
			if (hud.isOpen() && hud.mustPause())
			{
				return true;
			}
		}

		return false;
	}

	public boolean hasHudOpen()
	{
		for (final Hud hud : this.huds().values())
		{
			if (hud.isOpen())
			{
				return true;
			}
		}

		return false;
	}

	public void addHud(String nameIn, Hud hudIn)
	{
		this.huds().put(nameIn, hudIn);
	}

	public Hud get(String nameIn)
	{
		return this.huds().get(nameIn);
	}

	private Map<String, Hud> huds()
	{
		return this.huds;
	}

	private void huds(Map<String, Hud> hudsIn)
	{
		this.huds = hudsIn;
	}

	final GLMesh rectangle()
	{
		return this.rectangle;
	}

	private final void rectangle(GLMesh rectangleIn)
	{
		this.rectangle = rectangleIn;
	}

	private NanoVGUtils nanoVGUtils()
	{
		return this.nanoVGUtils;
	}

	private void nanoVGUtils(NanoVGUtils nanoVGUtilsIn)
	{
		this.nanoVGUtils = nanoVGUtilsIn;
	}
}
