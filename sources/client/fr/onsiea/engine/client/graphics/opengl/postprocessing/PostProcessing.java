package fr.onsiea.engine.client.graphics.opengl.postprocessing;

import org.lwjgl.opengl.GL11;

import fr.onsiea.engine.client.graphics.mesh.IMesh;
import fr.onsiea.engine.client.graphics.opengl.postprocessing.effects.BrightFilter;
import fr.onsiea.engine.client.graphics.opengl.postprocessing.effects.CombineFilter;
import fr.onsiea.engine.client.graphics.opengl.postprocessing.effects.ContrastChanger;
import fr.onsiea.engine.client.graphics.opengl.postprocessing.effects.HorizontalBlur;
import fr.onsiea.engine.client.graphics.opengl.postprocessing.effects.VerticalBlur;
import fr.onsiea.engine.client.graphics.opengl.shader.Shader2D;
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
	private static final float[]	POSITIONS	=
	{ -1, 1, -1, -1, 1, 1, 1, -1 };

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

		this.brightFilter(new BrightFilter(windowIn.settings().width() / 2, windowIn.settings().height() / 2, windowIn,
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
		this.attach();

		//this.brightFilter().render(shaderManagerIn, colourTextureIn, windowIn);

		this.horizontalBlur2().render(colourTextureIn, windowIn);
		this.verticalBlur2().render(this.horizontalBlur2().outputTexture(), windowIn);
		this.horizontalBlur4().render(this.brightFilter().outputTexture(), windowIn);
		this.verticalBlur4().render(this.horizontalBlur4().outputTexture(), windowIn);
		this.horizontalBlur8().render(this.brightFilter().outputTexture(), windowIn);
		this.verticalBlur8().render(this.horizontalBlur8().outputTexture(), windowIn);

		//this.combineFilter().render(shaderManagerIn, windowIn, colourTextureIn, this.verticalBlur2().outputTexture(),
		//		this.verticalBlur4().outputTexture(), this.verticalBlur8().outputTexture());
		//this.contrastChanger().render(shaderManagerIn, colourTextureIn, windowIn);

		this.shader.attach();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.verticalBlur2().outputTexture());
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, PostProcessing.POSITIONS.length / 2);
		this.shadersManager.detach();

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