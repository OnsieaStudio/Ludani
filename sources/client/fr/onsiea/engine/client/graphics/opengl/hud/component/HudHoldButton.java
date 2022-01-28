package fr.onsiea.engine.client.graphics.opengl.hud.component;

import javax.swing.Renderer;

import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;

import fr.onsiea.engine.client.graphics.glfw.window.Window;
import fr.onsiea.engine.client.graphics.opengl.hud.IHudComponent;
import fr.onsiea.engine.client.graphics.opengl.nanovg.NanoVGUtils;
import fr.onsiea.engine.maths.normalization.Normalizer;
import fr.seynax.onsiea.game.entity.camera.Camera;
import fr.seynax.onsiea.graphics.opengl.mesh.GLMesh;
import fr.seynax.onsiea.graphics.opengl.shader.hud.HudShader;
import fr.seynax.onsiea.graphics.opengl.vao.VaoUtils;
import fr.seynax.onsiea.graphics.texture.Texture;
import fr.seynax.onsiea.input.ActionTypes;
import fr.seynax.onsiea.input.InputManager;

public class HudHoldButton implements IHudComponent
{
	private Texture[]	textures;
	private Texture		activeTexture;
	private int			activeTextureIndex;
	private Vector4f	transformations;
	private boolean		hold;

	public HudHoldButton(float xIn, float yIn, float sizeXIn, float sizeYIn, Texture baseTextureIn,
			Texture... texturesIn)
	{
		this.transformations(new Vector4f(xIn, yIn, sizeXIn, sizeYIn));

		this.activeTexture(baseTextureIn);

		var baseLength = 1;

		if (texturesIn != null)
		{
			baseLength += texturesIn.length;
		}
		this.textures(new Texture[baseLength]);
		this.textures()[0] = baseTextureIn;

		var i = 1;
		for (final Texture texture : texturesIn)
		{
			this.textures()[i] = texture;
			i++;
		}
	}

	@Override
	public void initialization(Window windowIn, Camera cameraIn)
	{

	}

	@Override
	public void input(InputManager inputManagerIn, Window windowIn)
	{
		final var	cursorX	= Normalizer.normalizeX(inputManagerIn.cursor().x(), windowIn.width());
		final var	cursorY	= Normalizer.normalizeY(inputManagerIn.cursor().y(), windowIn.height());

		if (cursorX > this.transformations().x() && cursorX < this.transformations().x() + this.transformations().z()
				&& cursorY > this.transformations().y()
				&& cursorY < this.transformations().y() + this.transformations().w())
		{
			final var timedAction = inputManagerIn.cursor().buttionActionOf(GLFW.GLFW_MOUSE_BUTTON_1);
			if (timedAction != null && timedAction.type() == ActionTypes.JUST_PRESSED)
			{
				if (!this.hold())
				{
					this.justPressed(cursorX, cursorY);
				}
			}
			else if (!this.hold())
			{
				this.changeActiveTexture(1);
			}
		}
		else if (!this.hold())
		{
			this.changeActiveTexture(0);
		}

		final var timedAction = inputManagerIn.cursor().buttionActionOf(GLFW.GLFW_MOUSE_BUTTON_1);
		if (timedAction != null && timedAction.type() == ActionTypes.JUST_RELEASED)
		{
			this.hold(false);
		}

		if (this.hold())
		{
			this.holdRuntime(cursorX, cursorY, inputManagerIn);
		}
	}

	@Override
	public void update(Window windowIn, Camera cameraIn)
	{
	}

	@Override
	public void draw(Window windowIn, Camera cameraIn, HudShader hudShaderIn, GLMesh rectangleIn, Renderer rendererIn)
	{
		this.activeTexture().bindAndActive0();
		hudShaderIn.uniformTransformations().load(this.transformations());
		rectangleIn.draw(rendererIn);
		VaoUtils.disablesAndUnbind(1);
		Texture.unbind();
	}

	@Override
	public void draw(Window windowIn, Camera cameraIn, HudShader hudShaderIn, InputManager inputManagerIn,
			NanoVGUtils nanoVGUtilsIn)
	{

	}

	@Override
	public void cleanup()
	{

	}

	protected void justPressed(double cursorXIn, double cursorYIn)
	{
		this.hold(true);
		this.changeActiveTexture(2);
	}

	protected void holdRuntime(double cursorXIn, double cursorYIn, InputManager inputManagerIn)
	{
	}

	public void changeActiveTexture(int textureIndexIn)
	{
		if (this.isCorrect(textureIndexIn))
		{
			this.activeTextureIndex(textureIndexIn);
			this.activeTexture(this.textures()[this.activeTextureIndex()]);
		}
	}

	public boolean isCorrect(int textureIndexIn)
	{
		return textureIndexIn >= 0 && textureIndexIn < this.textures().length;
	}

	public Vector4f transformations()
	{
		return this.transformations;
	}

	private void transformations(Vector4f transformationsIn)
	{
		this.transformations = transformationsIn;
	}

	private Texture[] textures()
	{
		return this.textures;
	}

	private void textures(Texture[] texturesIn)
	{
		this.textures = texturesIn;
	}

	private Texture activeTexture()
	{
		return this.activeTexture;
	}

	private void activeTexture(Texture activeTextureIn)
	{
		this.activeTexture = activeTextureIn;
	}

	private int activeTextureIndex()
	{
		return this.activeTextureIndex;
	}

	private void activeTextureIndex(int activeTextureIn)
	{
		this.activeTextureIndex = activeTextureIn;
	}

	public boolean hold()
	{
		return this.hold;
	}

	protected void hold(boolean holdIn)
	{
		this.hold = holdIn;
	}
}
