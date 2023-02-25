/**
 *
 */
package fr.onsiea.engine.client.graphics.opengl.hud.slot;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import fr.onsiea.engine.client.graphics.mesh.IMesh;
import fr.onsiea.engine.client.graphics.opengl.hud.components.HudComponent;
import fr.onsiea.engine.client.graphics.opengl.shaders.Shader2DIn3D;
import fr.onsiea.engine.client.graphics.opengl.shaders.Shader3DTo2D;
import fr.onsiea.engine.client.graphics.texture.ITexture;
import fr.onsiea.engine.client.graphics.window.IWindow;
import fr.onsiea.engine.client.input.InputManager;
import fr.onsiea.engine.game.world.item.Item.ItemTypeVariant;
import fr.onsiea.engine.utils.maths.transformations.Transformations3f;
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

	public HudItem(final Vector2f positionIn, final Vector2f scaleIn, final ItemTypeVariant itemTypeVariantIn,
			final float zIn)
	{
		super(positionIn, scaleIn);
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
	public HudItem(final Vector2f positionIn, final Vector2f scaleIn, final IMesh meshIn, final ITexture textureIn)
	{
		super(positionIn, scaleIn);
		this.mesh		= meshIn;
		this.texture	= textureIn;
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

	@Override
	public void draw2D(final Shader2DIn3D shader2dIn3DIn)
	{
	}

	@Override
	public void draw3D(final Shader3DTo2D shader3DTo2D, final IWindow windowIn)
	{
		final var	zoom	= 0.001f;
		final var	x		= this.position().x() * (windowIn.effectiveWidth() / 2 * (zoom * 2));
		final var	y		= this.position().y() * (windowIn.effectiveHeight() / 2 * (zoom * 2));

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

		final var	startX	= (this.position().x() - this.scale().x() + 1.0f) / 2.0f * windowIn.effectiveWidth();
		final var	startY	= (1.0f + this.position().y() - this.scale().y()) / 2.0f * windowIn.effectiveHeight();

		final var	sizeX	= this.scale().x() * 1 * windowIn.effectiveWidth();
		final var	sizeY	= this.scale().y() * 1 * windowIn.effectiveHeight();

		//GL11.glViewport((int) startX, (int) startY, (int) sizeX, (int) sizeY);

		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		GL11.glScissor((int) startX, (int) startY, (int) sizeX, (int) sizeY);

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
	}

}