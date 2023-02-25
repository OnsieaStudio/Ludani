/**
 *
 */
package fr.onsiea.engine.client.graphics.opengl.hud.components;

import org.joml.Vector2f;

import fr.onsiea.engine.client.graphics.opengl.GraphicsUtils;
import fr.onsiea.engine.client.graphics.opengl.shaders.Shader2DIn3D;
import fr.onsiea.engine.client.graphics.opengl.shaders.Shader3DTo2D;
import fr.onsiea.engine.client.graphics.texture.ITexture;
import fr.onsiea.engine.client.graphics.window.IWindow;
import fr.onsiea.engine.client.input.InputManager;
import fr.onsiea.engine.utils.maths.MathInstances;
import fr.onsiea.engine.utils.maths.transformations.Transformations2f;

/**
 * @author seyro
 *
 */
public class HudRectangle extends HudComponent
{
	private final ITexture texture;

	public HudRectangle(final Vector2f positionIn, final Vector2f scaleIn, final ITexture textureIn)
	{
		super(positionIn, scaleIn);

		this.texture = textureIn;
	}

	@Override
	public void draw2D(final Shader2DIn3D shader2DIn3D)
	{
		GraphicsUtils.prepare2D();
		this.texture.attach();
		shader2DIn3D.transformations()
				.load(Transformations2f.transformations(this.position(), MathInstances.zero2f(), this.scale));

		GraphicsUtils.drawQuad();

		this.texture.detach();
		GraphicsUtils.end2D();
	}

	@Override
	public void draw3D(final Shader3DTo2D shader3dTo2DIn, final IWindow windowIn)
	{
	}

	@Override
	public void hovering(final double normalizedMouseXIn, final double normalizedMouseYIn,
			final InputManager inputManagerIn)
	{

	}

	@Override
	public void stopHovering(final double normalizedMouseXIn, final double normalizedMouseYIn,
			final InputManager inputManagerIn)
	{

	}
}