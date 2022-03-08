package fr.onsiea.engine.client.graphics.opengl.flare;

import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import fr.onsiea.engine.client.graphics.opengl.GLMeshManager;
import fr.onsiea.engine.client.graphics.opengl.OpenGLSettings;
import fr.onsiea.engine.client.graphics.opengl.mesh.GLMesh;
import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderManager;
import fr.onsiea.engine.client.graphics.opengl.shader.Shader;
import fr.onsiea.engine.client.graphics.render.Renderer;
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
	private static final float[]	POSITIONS	=
	{ -0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f };

	// A VAO containing the quad's positions in attribute 0
	private GLMesh					mesh;
	private final OpenGLSettings	settings;

	/**
	 * Initializes the shader program, and creates a VAO for the quad, storing
	 * the data for the 4 quad vertices in attribute 0 of the VAO.
	 */
	public FlareRenderer(GLMeshManager meshManagerIn, OpenGLSettings settingsIn)
	{
		this.settings = settingsIn;

		try
		{
			this.mesh = meshManagerIn.meshBuilderWithVao(1).vbo(GL15.GL_STREAM_DRAW, FlareRenderer.positions(), 2)
					.vertexCount(FlareRenderer.positions().length).unbind().build();
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
	 * @param flares
	 *            - An array of the FlareTextures that need to be rendered to
	 *            the screen.
	 * @param brightness
	 *            - The brightness that all the FlareTextures should be rendered
	 *            at.
	 */
	public void render(FlareTexture[] flares, float brightness, GLShaderManager shaderManagerIn, IWindow windowIn,
			Renderer rendererIn)
	{
		this.prepare(brightness, shaderManagerIn, rendererIn);
		for (final FlareTexture flare : flares)
		{
			this.renderFlare(flare, windowIn, shaderManagerIn);
		}
		this.endRendering(rendererIn);
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
	 * @param brightness
	 *            - the brightness at which the flares are going to be rendered.
	 */
	private void prepare(float brightness, GLShaderManager shaderManagerIn, Renderer rendererIn)
	{
		this.settings.userSettings().disable("antialias");
		this.settings.userSettings().enable("blend");
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		this.settings.engineSettings().disable("depthTesting");
		this.settings.engineSettings().disable("cullBackFaces");

		this.mesh().startDrawing(rendererIn);
		shaderManagerIn.flareShader().start();
		shaderManagerIn.flareShader().uniformBrightness().load(brightness);
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
	private void renderFlare(FlareTexture flare, IWindow windowIn, GLShaderManager shaderManagerIn)
	{
		flare.texture().attach();
		final var	xScale		= flare.scale();
		final var	yScale		= xScale * windowIn.settings().width() / windowIn.settings().height();
		final var	centerPos	= flare.screenPos();
		shaderManagerIn.flareShader().uniformTransformations()
				.load(new Vector4f(centerPos.x, centerPos.y, xScale, yScale));
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
	}

	/**
	 * Unbind the quad VAO, stop the shader program, and undo any settings that
	 * were changed before rendering.
	 */
	private void endRendering(Renderer rendererIn)
	{
		this.mesh().stopDrawing(rendererIn);
		Shader.stop();

		this.settings.userSettings().enable("antialiasing");
		this.settings.userSettings().disable("blend");
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		this.settings.engineSettings().enable("depthTesting");
		this.settings.userSettings().enable("cullBackface");
	}

	private final GLMesh mesh()
	{
		return this.mesh;
	}

	private static final float[] positions()
	{
		return FlareRenderer.POSITIONS;
	}
}