package fr.onsiea.engine.client.graphics.opengl.postprocessing;

import org.lwjgl.opengl.GL11;

import fr.onsiea.engine.client.graphics.opengl.GLMeshManager;
import fr.onsiea.engine.client.graphics.opengl.mesh.GLMesh;
import fr.onsiea.engine.client.graphics.opengl.postprocessing.effects.BrightFilter;
import fr.onsiea.engine.client.graphics.opengl.postprocessing.effects.CombineFilter;
import fr.onsiea.engine.client.graphics.opengl.postprocessing.effects.ContrastChanger;
import fr.onsiea.engine.client.graphics.opengl.postprocessing.effects.HorizontalBlur;
import fr.onsiea.engine.client.graphics.opengl.postprocessing.effects.VerticalBlur;
import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderManager;
import fr.onsiea.engine.client.graphics.opengl.shader.Shader;
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

	private GLMesh					mesh;

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
	public PostProcessing(GLMeshManager meshManagerIn, IWindow windowIn, GLShaderManager shaderManagerIn)
			throws Exception
	{
		this.mesh(meshManagerIn.meshBuilderWithVao(1).vbo(PostProcessing.POSITIONS, 2).unbind().vertexCount(4).build());

		this.horizontalBlur2(new HorizontalBlur(windowIn.settings().width() / 2, windowIn.settings().height() / 2,
				windowIn, shaderManagerIn));
		this.verticalBlur2(new VerticalBlur(windowIn.settings().width() / 2, windowIn.settings().height() / 2, windowIn,
				shaderManagerIn));
		this.horizontalBlur4(new HorizontalBlur(windowIn.settings().width() / 4, windowIn.settings().height() / 4,
				windowIn, shaderManagerIn));
		this.verticalBlur4(new VerticalBlur(windowIn.settings().width() / 4, windowIn.settings().height() / 4, windowIn,
				shaderManagerIn));
		this.horizontalBlur8(new HorizontalBlur(windowIn.settings().width() / 8, windowIn.settings().height() / 8,
				windowIn, shaderManagerIn));
		this.verticalBlur8(new VerticalBlur(windowIn.settings().width() / 8, windowIn.settings().height() / 8, windowIn,
				shaderManagerIn));

		this.brightFilter(
				new BrightFilter(windowIn.settings().width() / 2, windowIn.settings().height() / 2, windowIn));
		this.combineFilter(new CombineFilter(shaderManagerIn));
		this.contrastChanger(new ContrastChanger());
	}

	/**
	 * Work in progress
	 * @param shaderManagerIn
	 * @param colourTextureIn
	 * @param windowIn
	 */
	public void doPostProcessing(GLShaderManager shaderManagerIn, int colourTextureIn,
			IWindow windowIn/**,
							Renderer rendererIn**/
	)
	{
		this.start();

		//this.brightFilter().render(shaderManagerIn, colourTextureIn, windowIn);

		this.horizontalBlur2().render(colourTextureIn, windowIn, shaderManagerIn);
		this.verticalBlur2().render(this.horizontalBlur2().outputTexture(), windowIn, shaderManagerIn);
		this.horizontalBlur4().render(this.brightFilter().outputTexture(), windowIn, shaderManagerIn);
		this.verticalBlur4().render(this.horizontalBlur4().outputTexture(), windowIn, shaderManagerIn);
		this.horizontalBlur8().render(this.brightFilter().outputTexture(), windowIn, shaderManagerIn);
		this.verticalBlur8().render(this.horizontalBlur8().outputTexture(), windowIn, shaderManagerIn);

		//this.combineFilter().render(shaderManagerIn, windowIn, colourTextureIn, this.verticalBlur2().outputTexture(),
		//		this.verticalBlur4().outputTexture(), this.verticalBlur8().outputTexture());
		//this.contrastChanger().render(shaderManagerIn, colourTextureIn, windowIn);

		shaderManagerIn.shader2D().start();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.verticalBlur2().outputTexture());
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, PostProcessing.POSITIONS.length / 2);
		Shader.stop();
		this.end();

	}

	/**
	 *  Work in progress
	 * @param rendererIn
	 */
	private void start(/**Renderer rendererIn**/
	)
	{
		this.mesh().startDrawing(null);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}

	/**
	 *  Work in progress
	 * @param rendererIn
	 */
	private void end(/**Renderer rendererIn**/
	)
	{
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		this.mesh().stopDrawing(null);
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