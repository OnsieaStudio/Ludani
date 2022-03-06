package fr.onsiea.engine.client.graphics.opengl.flare;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import fr.onsiea.engine.client.graphics.glfw.window.Window;
import fr.onsiea.engine.client.graphics.opengl.GLMeshManager;
import fr.onsiea.engine.client.graphics.opengl.OpenGLSettings;
import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderManager;
import fr.onsiea.engine.client.graphics.render.Renderer;
import fr.onsiea.engine.core.entity.Camera;
import fr.onsiea.engine.utils.maths.MathInstances;

public class FlareManager
{

	private static final Vector2f	CENTER_SCREEN	= new Vector2f(0.5f, 0.5f);

	private final FlareTexture[]	flareTextures;
	private final float				spacing;

	private final FlareRenderer		renderer;

	public FlareManager(float spacing, GLMeshManager meshManagerIn, OpenGLSettings settingsIn, FlareTexture... textures)
	{
		this.spacing		= spacing;
		this.flareTextures	= textures;
		this.renderer		= new FlareRenderer(meshManagerIn, settingsIn);
	}

	public void render(Camera camera, Vector3f sunWorldPos, GLShaderManager shaderManagerIn, Window windowIn,
			Renderer rendererIn)
	{
		final var sunCoords = this.convertToScreenSpace(sunWorldPos, camera.viewMatrix(),
				MathInstances.projectionMatrix());
		if (sunCoords == null)
		{
			return;
		}
		final var	sunToCenter	= sunCoords.sub(FlareManager.CENTER_SCREEN);
		final var	brightness	= 1 - sunToCenter.length() / 0.7f;
		if (brightness > 0)
		{
			this.calcFlarePositions(sunToCenter, sunCoords);
			this.renderer().render(this.flareTextures(), brightness, shaderManagerIn, windowIn, rendererIn);
		}
	}

	private void calcFlarePositions(Vector2f sunToCenter, Vector2f sunCoords)
	{
		for (var i = 0; i < this.flareTextures().length; i++)
		{
			final var direction = new Vector2f(sunToCenter);
			direction.mul(i * this.spacing());
			final var flarePos = direction.add(sunCoords);
			this.flareTextures()[i].screenPos(flarePos);
		}
	}

	private Vector2f convertToScreenSpace(Vector3f worldPos, Matrix4f viewMat, Matrix4f projectionMat)
	{
		final var coords = new Vector4f(worldPos.x(), worldPos.y(), worldPos.z(), 1f);
		coords.mul(viewMat);
		if (coords.w() <= 0)
		{
			return null;
		}
		final var	x	= (coords.x() / coords.w() + 1) / 2f;
		final var	y	= 1 - (coords.y() / coords.w() + 1) / 2f;
		return new Vector2f(x, y);
	}

	private final FlareTexture[] flareTextures()
	{
		return this.flareTextures;
	}

	private final float spacing()
	{
		return this.spacing;
	}

	private final FlareRenderer renderer()
	{
		return this.renderer;
	}
}