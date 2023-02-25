package fr.onsiea.engine.client.graphics.opengl.flare;

import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;

import fr.onsiea.engine.client.graphics.mesh.IMesh;
import fr.onsiea.engine.client.graphics.opengl.shaders.effects.FlareShader;
import fr.onsiea.engine.client.graphics.render.IRenderAPIContext;
import fr.onsiea.engine.client.graphics.render.IRenderAPIContextSettings;
import fr.onsiea.engine.client.graphics.shader.IShadersManager;
import fr.onsiea.engine.client.graphics.window.IWindow;

/**
 *
 * Renders 2D textured quads onto the screen.
 *
 * @author Karl
 *
 */
public class FlareRenderer
{

	// 4 vertex positions for a 2D quad.
	private static final float[]			POSITIONS	=
	{
			-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f
	};

	// A VAO containing the quad's positions in attribute 0
	private IMesh							mesh;
	private final IRenderAPIContextSettings	settings;

	private final FlareShader				flareShader;
	private final IShadersManager			shadersManager;

	/**
	 * Initializes the shader program, and creates a VAO for the quad, storing
	 * the data for the 4 quad vertices in attribute 0 of the VAO.
	 */
	public FlareRenderer(final IRenderAPIContext renderAPIContextIn)
	{
		this.settings		= renderAPIContextIn.settings();

		this.shadersManager	= renderAPIContextIn.shadersManager();
		this.flareShader	= (FlareShader) renderAPIContextIn.shadersManager().get("flare");

		try
		{
			this.mesh = renderAPIContextIn.meshsManager().create(FlareRenderer.POSITIONS, 2);
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Renders FlareTextures onto the screen at their positions, at the
	 * specified brightness.
	 *
	 * @param flaresIn
	 *            - An array of the FlareTextures that need to be rendered to
	 *            the screen.
	 * @param brightnessIn
	 *            - The brightness that all the FlareTextures should be rendered
	 *            at.
	 */
	public void render(final FlareTexture[] flaresIn, final float brightnessIn, final IWindow windowIn)
	{
		this.prepare(brightnessIn);
		for (final FlareTexture flare : flaresIn)
		{
			this.renderFlare(flare, windowIn);
		}
		this.endRendering();
	}

	/**
	 * Prepares for rendering the FlareTextures. Antialiasing is disabled as it
	 * isn't needed. Additive blending is enables so that transparent parts of
	 * the texture aren't rendered, and to give the entire texture a "glowing"
	 * look. Depth testing is also disabled because the flare textures should
	 * always be rendered on top of everything else in the scene. For this
	 * reason the lens flare effect MUST be rendered after rendering everything
	 * else in the 3D scene (But render before GUIs if you want the GUIs to go
	 * in front of the lens flare.) Backface culling also unnecessary here.
	 *
	 * The shader program is also started, and the brightness value loaded up to
	 * it as a uniform variable. The quad's VAO is bound, ready for use, and its
	 * attribute 0 is enabled (attribute 0 contains the position data).
	 *
	 *
	 * @param brightnessIn
	 *            - the brightness at which the flares are going to be rendered.
	 */
	private void prepare(final float brightnessIn)
	{
		this.settings.user().disable("antialias");
		this.settings.user().enable("blend");
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		this.settings.engine().disable("depthTesting");
		this.settings.user().disable("cullBackFaces");

		this.mesh().attach();
		this.flareShader.attach();
		this.flareShader.uniformBrightness().load(brightnessIn);
	}

	/**
	 * Renders a single flare texture to the screen on a textured 2D quad.
	 *
	 * The texture for this flare is first bound to texture unit 0. The x and y
	 * scale of the quad is then determined. The x scale is simply the scale
	 * value in the FlareTexture instance, and then the y scale is calculated by
	 * multiplying that by the aspect ratio of the display. This ensures that
	 * the quad is a square, and not a rectangle.
	 *
	 * The position and scale is then loaded up the the shader. Finally, the
	 * quad is rendered using glDrawArrays (no index buffer used), and using
	 * GL_TRIANGLE_STRIP. This allows the quad to be specified using only 4
	 * vertex positions, instead of having to specify the 6 vertex positions for
	 * the 2 triangles. See GUI tutorial for more info.
	 *
	 * @param flare
	 *            - The flare to be rendered.
	 */
	private void renderFlare(final FlareTexture flare, final IWindow windowIn)
	{
		flare.texture().attach();
		final var	xScale		= flare.scale();
		final var	yScale		= xScale * windowIn.effectiveWidth() / windowIn.effectiveHeight();
		final var	centerPos	= flare.screenPos();
		this.flareShader.uniformTransformations().load(new Vector4f(centerPos.x, centerPos.y, xScale, yScale));
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
	}

	/**
	 * Unbind the quad VAO, stop the shader program, and undo any settings that
	 * were changed before rendering.
	 */
	private void endRendering()
	{
		this.mesh().detach();
		this.shadersManager.detach();

		this.settings.user().enable("antialiasing");
		this.settings.user().disable("blend");
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		this.settings.engine().enable("depthTesting");
		this.settings.user().enable("cullBackface");
	}

	private final IMesh mesh()
	{
		return this.mesh;
	}
}