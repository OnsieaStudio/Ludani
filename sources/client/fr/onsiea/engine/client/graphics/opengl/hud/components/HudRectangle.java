/**
 *
 */
package fr.onsiea.engine.client.graphics.opengl.hud.components;

import fr.onsiea.engine.client.graphics.opengl.GraphicsUtils;
import fr.onsiea.engine.client.graphics.opengl.shaders.Shader2DIn3D;
import fr.onsiea.engine.client.graphics.texture.ITexture;
import fr.onsiea.engine.utils.maths.transformations.Transformations2f;
import fr.onsiea.engine.utils.positionnable.IPositionnable;

/**
 * @author seyro
 *
 */
public class HudRectangle extends HudComponent
{
	private final ITexture texture;

	public HudRectangle(final IPositionnable positionnableIn, final ITexture textureIn)
	{
		super(positionnableIn);

		this.texture = textureIn;
	}

	@Override
	public IHudComponent draw2D(final Shader2DIn3D shader2DIn3D)
	{
		GraphicsUtils.prepare2D();
		this.texture.attach();

		shader2DIn3D.transformations()
				.load(Transformations2f.transformations(this.position().xNorm().centered(),
						this.position().yNorm().invertedCentered(), 0.0f, 0.0f, this.size().xNorm().percent(),
						this.size().yNorm().percent()));

		GraphicsUtils.drawQuad();

		this.texture.detach();
		GraphicsUtils.end2D();

		return this;
	}
}