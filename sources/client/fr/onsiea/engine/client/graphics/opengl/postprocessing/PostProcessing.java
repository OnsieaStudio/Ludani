package fr.onsiea.engine.client.graphics.opengl.postprocessing;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import fr.onsiea.engine.client.graphics.mesh.IMesh;
import fr.onsiea.engine.client.graphics.opengl.postprocessing.effects.BrightFilter;
import fr.onsiea.engine.client.graphics.opengl.postprocessing.effects.CombineFilter;
import fr.onsiea.engine.client.graphics.opengl.postprocessing.effects.ContrastChanger;
import fr.onsiea.engine.client.graphics.opengl.postprocessing.effects.HorizontalBlur;
import fr.onsiea.engine.client.graphics.opengl.postprocessing.effects.VerticalBlur;
import fr.onsiea.engine.client.graphics.opengl.shaders.Shader2D;
import fr.onsiea.engine.client.graphics.render.IRenderAPIContext;
import fr.onsiea.engine.client.graphics.shader.IShadersManager;
import fr.onsiea.engine.client.graphics.window.IWindow;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
public class PostProcessing
{
	private final static float[]	POSITIONS	=
	{ -1, 1, -1, -1, 1, 1, 1, -1 };

	private static float[]			values		=
	{ 0.0093f, 0.028002f, 0.065984f, 0.121703f, 0.175713f, 0.198596f, 0.175713f, 0.121703f, 0.065984f, 0.028002f,
			0.0093f };

	private IMesh					rectangleMesh;

	private IShadersManager			shadersManager;
	private Shader2D				shader;

	private HorizontalBlur			horizontalBlur2;
	private VerticalBlur			verticalBlur2;
	private HorizontalBlur			horizontalBlur4;
	private VerticalBlur			verticalBlur4;
	private HorizontalBlur			horizontalBlur8;
	private VerticalBlur			verticalBlur8;

	private BrightFilter			brightFilter;
	private CombineFilter			combineFilter;
	private ContrastChanger			contrastChanger;

	/**private float					i0			= 0;
	private boolean					inv			= false;
	private final static float		INCREASE	= 0.00005f;**/

	/**
	 * Work in progress
	 * @param meshManagerIn
	 * @param windowIn
	 * @param shaderManagerIn
	 * @throws Exception
	 */
	public PostProcessing(IWindow windowIn, IRenderAPIContext renderAPIContextIn) throws Exception
	{
		this.shadersManager	= renderAPIContextIn.shadersManager();
		this.shader			= (Shader2D) this.shadersManager.get("2D");

		this.rectangleMesh	= renderAPIContextIn.meshsManager().create(PostProcessing.POSITIONS, 2);

		this.brightFilter(new BrightFilter(windowIn.settings().width() / 2, windowIn.settings().height() / 2, windowIn,
				renderAPIContextIn.shadersManager()));
		this.horizontalBlur2(new HorizontalBlur(windowIn.settings().width() / 2, windowIn.settings().height() / 2,
				windowIn, renderAPIContextIn.shadersManager()));
		this.verticalBlur2(new VerticalBlur(windowIn.settings().width() / 2, windowIn.settings().height() / 2, windowIn,
				renderAPIContextIn.shadersManager()));
		this.horizontalBlur4(new HorizontalBlur(windowIn.settings().width() / 4, windowIn.settings().height() / 4,
				windowIn, renderAPIContextIn.shadersManager()));
		this.verticalBlur4(new VerticalBlur(windowIn.settings().width() / 4, windowIn.settings().height() / 4, windowIn,
				renderAPIContextIn.shadersManager()));
		this.horizontalBlur8(new HorizontalBlur(windowIn.settings().width() / 8, windowIn.settings().height() / 8,
				windowIn, renderAPIContextIn.shadersManager()));
		this.verticalBlur8(new VerticalBlur(windowIn.settings().width() / 8, windowIn.settings().height() / 8, windowIn,
				renderAPIContextIn.shadersManager()));

		this.combineFilter(new CombineFilter(renderAPIContextIn.shadersManager()));
		this.contrastChanger(new ContrastChanger(renderAPIContextIn.shadersManager()));
	}

	/**
	 * @param shaderManagerIn
	 * @param colourTextureIn
	 * @param windowIn
	 */
	public int doPostProcessing(int colourTextureIn, IWindow windowIn)
	{
		/**for (var i = 0; i < PostProcessing.values.length; i++)
		{
			if (!this.inv)
			{
				PostProcessing.values[i] += PostProcessing.INCREASE;
			}
			else
			{
				PostProcessing.values[i] -= PostProcessing.INCREASE;
			}
		}
		if (!this.inv)
		{
			this.i0 += PostProcessing.INCREASE;
		}
		else
		{
			this.i0 -= PostProcessing.INCREASE;
		}
		if (this.i0 >= 0.005f || this.i0 <= -0.005f)
		{
			this.inv = !this.inv;
		}

		final var count = MathUtils.randomInt(0, 10);

		for (var i = 0; i < count; i++)
		{
			final var index = MathUtils.randomInt(0, 10);

			PostProcessing.values[index] += PostProcessing.INCREASE * 10.0f * MathUtils.randomInt(-10, 10);
		}**/

		this.attach();

		this.brightFilter().render(colourTextureIn, windowIn);

		this.horizontalBlur2().render(this.brightFilter().outputTexture(), windowIn, PostProcessing.values);
		this.verticalBlur2().render(this.horizontalBlur2().outputTexture(), windowIn, PostProcessing.values);
		this.horizontalBlur4().render(this.brightFilter().outputTexture(), windowIn, PostProcessing.values);
		this.verticalBlur4().render(this.horizontalBlur4().outputTexture(), windowIn, PostProcessing.values);
		this.horizontalBlur8().render(this.brightFilter().outputTexture(), windowIn, PostProcessing.values);
		this.verticalBlur8().render(this.horizontalBlur8().outputTexture(), windowIn, PostProcessing.values);

		this.combineFilter().render(windowIn, colourTextureIn, this.verticalBlur2().outputTexture(),
				this.verticalBlur4().outputTexture(), this.verticalBlur8().outputTexture());

		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		//this.contrastChanger().render(colourTextureIn, windowIn, 0.5f);

		/**this.shader.attach();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.brightFilter().outputTexture());
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, PostProcessing.POSITIONS.length / 2);
		this.shadersManager.detach();**/

		this.detach();

		return this.verticalBlur8().outputTexture();
	}

	/**
	 *  Work in progress
	 * @param rendererIn
	 */
	private void attach()
	{
		this.rectangleMesh.attach();
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}

	/**
	 *  Work in progress
	 * @param rendererIn
	 */
	private void detach()
	{
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		this.rectangleMesh().detach();
	}

	public void cleanUp()
	{
		this.horizontalBlur2().cleanup();
		this.verticalBlur2().cleanup();
		this.horizontalBlur4().cleanup();
		this.verticalBlur4().cleanup();
		this.horizontalBlur8().cleanup();
		this.verticalBlur8().cleanup();
		this.brightFilter().cleanup();
		this.combineFilter().cleanup();
		this.contrastChanger().cleanup();
	}
}