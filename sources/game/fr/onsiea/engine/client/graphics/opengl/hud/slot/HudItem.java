/**
 *
 */
package fr.onsiea.engine.client.graphics.opengl.hud.slot;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import fr.onsiea.engine.client.graphics.mesh.IMesh;
import fr.onsiea.engine.client.graphics.opengl.hud.components.HudComponent;
import fr.onsiea.engine.client.graphics.opengl.hud.components.IHudComponent;
import fr.onsiea.engine.client.graphics.opengl.shaders.Shader3DTo2D;
import fr.onsiea.engine.client.graphics.texture.ITexture;
import fr.onsiea.engine.client.graphics.window.IWindow;
import fr.onsiea.engine.game.world.item.Item.ItemTypeVariant;
import fr.onsiea.engine.utils.maths.transformations.Transformations3f;
import fr.onsiea.engine.utils.positionnable.IPositionnable;
import lombok.Getter;
import lombok.Setter;

/**
 * @author seyro
 *
 */
public class HudItem extends HudComponent
{
	private @Getter ItemTypeVariant	itemTypeVariant;
	private IMesh					mesh;
	private ITexture				texture;
	private float					rx;
	@Setter
	public float					z;

	public HudItem(final IPositionnable positionnableIn, final ItemTypeVariant itemTypeVariantIn, final float zIn)
	{
		super(positionnableIn);
		try
		{
			this.itemTypeVariant	= itemTypeVariantIn;
			this.mesh				= itemTypeVariantIn.itemType().mesh();
			this.texture			= itemTypeVariantIn.texture();
			this.z					= zIn;
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @param loadIn
	 * @param load2In
	 */
	public HudItem(final IPositionnable positionnableIn, final IMesh meshIn, final ITexture textureIn)
	{
		super(positionnableIn);
		this.mesh		= meshIn;
		this.texture	= textureIn;
	}

	@Override
	public IHudComponent draw3D(final Shader3DTo2D shader3DTo2D, final IWindow windowIn)
	{
		final var	zoom	= 0.001f;
		final var	x		= this.position().xNorm().centered() * (windowIn.effectiveWidth() / 2 * (zoom * 2));
		final var	y		= this.position().yNorm().invertedCentered()
				* (windowIn.effectiveHeight() / 2 * (zoom * 2));

		this.rx	+= 1f;
		this.rx	%= 360.0f;
		final var	baseScaleX	= 1.0f;
		final var	baseScaleY	= 1.0f;
		final var	baseScaleZ	= 1.0f;
		final var	objectScale	= 1.64f;

		final var	scaleX		= baseScaleX / 10.0f * objectScale;
		final var	scaleY		= baseScaleY / 10.0f * objectScale;
		final var	scaleZ		= baseScaleZ / 10.0f * objectScale;

		this.texture.attach();
		this.mesh.attach();

		final var	startX	= this.position().x() - this.size().x() / 2;
		final var	startY	= this.position().yNorm().inverted() - this.size().y() / 2;

		//GL11.glViewport((int) startX, (int) startY, (int) sizeX, (int) sizeY);

		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		GL11.glScissor((int) startX, (int) startY, (int) this.size().x(), (int) this.size().y());

		shader3DTo2D.projections()
				.load(new Matrix4f().identity().setOrtho(-windowIn.effectiveWidth() / 2 * (zoom * 2),
						windowIn.effectiveWidth() / 2 * (zoom * 2), -windowIn.effectiveHeight() / 2 * (zoom * 2),
						windowIn.effectiveHeight() / 2 * (zoom * 2), -100f, 100.0f));
		shader3DTo2D.transformations().load(Transformations3f.transformations(new Vector3f(x, y, this.z),
				new Vector3f(0 + this.rx, 0 + this.rx, 0), new Vector3f(scaleX, scaleY, scaleZ)));
		/**shader3DTo2D.transformations()
				.load(new Matrix4f().identity().setOrtho(startX, startY, startX + endX, startY + endY, -100f, 100.0f)
						.mul(Transformations3f.transformations(new Vector3f(0, 0, 0.0f),
								new Vector3f(0 + this.rx, 0 + this.rx, 0), new Vector3f(scaleX, scaleY, scaleZ))));**/

		/**Transformations3f.transformations(new Vector3f(xp + 0.045f, yp - 0.045f, z),
				new Vector3f(this.rx - 20, ry + 290, rz + 90), new Vector3f(scaleX, scaleY, scaleZ),
				new Matrix4f()));**/
		this.mesh.draw();

		this.mesh.detach();
		this.texture.detach();

		return this;
	}

}