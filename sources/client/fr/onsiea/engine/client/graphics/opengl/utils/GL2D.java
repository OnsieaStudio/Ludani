package fr.onsiea.engine.client.graphics.opengl.utils;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import fr.onsiea.engine.client.graphics.opengl.model.GLRawModel;
import fr.onsiea.engine.client.graphics.opengl.shader.manager.GLShaderManager;
import fr.onsiea.engine.client.graphics.opengl.shader.utils.GLShaderUtils;
import fr.onsiea.engine.utils.maths.normalization.Normalizer;
import fr.onsiea.engine.utils.maths.transformations.Transformations2f;
import fr.onsiea.engine.utils.maths.transformations.Transformations3f;

public class GL2D
{
	private static GLShaderManager	shaderManager;
	private final static Vector2f	SCALE					= new Vector2f();
	private final static Vector2f	POSITION				= new Vector2f();
	private final static Matrix4f	TRANSFORMATIONS_MATRIX	= new Matrix4f();
	//private static GLRawModel RECTANGLE_RAW_MODEL;

	static
	{
		//RECTANGLE_RAW_MODEL = new GLRawModel();
		//rawModel		= BASEGL.LOADER.loadToVAO(vertex, 2, "RECTANGLE");
	}

	public static void initialize(GLShaderManager shaderManagerIn)
	{
		GL2D.shaderManager = shaderManagerIn;
	}

	/**
	 * Work in progress
	 */
	public static void prepare()
	{
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL2D.shaderManager.shader2D().attach();
		//GL30.glBindVertexArray(rawModel.getVaoId());
		GL20.glEnableVertexAttribArray(0);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
	}

	/**
	 * Work in progress
	 */
	public static void prepareFake3D(GLRawModel rawModel)
	{
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL2D.shaderManager.shader3DTo2D().attach();
		GL30.glBindVertexArray(rawModel.vao());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
	}

	/**
	 * Work in progress
	 * @param xIn
	 * @param yIn
	 * @param scalexIn
	 * @param scaleyIn
	 * @param textureIdIn
	 */
	public static void draw(float xIn, float yIn, float scalexIn, float scaleyIn, int textureIdIn)
	{
		GL2D.SCALE.x	= scalexIn / 100.0f;
		GL2D.SCALE.y	= scaleyIn / 100.0f;
		GL2D.POSITION.x	= (float) (Normalizer.percentToNormalizedX(xIn) + GL2D.SCALE.x);
		GL2D.POSITION.y	= (float) (Normalizer.percentToNormalizedY(yIn) - GL2D.SCALE.y);
		Transformations2f.transformations(GL2D.POSITION, 0.0f, 0.0f, GL2D.SCALE, GL2D.TRANSFORMATIONS_MATRIX);
		GL2D.shaderManager.shader2D().transformations().load(GL2D.TRANSFORMATIONS_MATRIX);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureIdIn);
		//GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, rawModel.getVertexCount());
	}

	public static void drawFake3D(float xIn, float yIn, float scalexIn, float scaleyIn, int textureIdIn,
			float ObjectScale, int vertexCount, float z, float rx, float ry, float rz)
	{
		GL2D.SCALE.x	= scalexIn / 100.0f;
		GL2D.SCALE.y	= scaleyIn / 100.0f;
		GL2D.POSITION.x	= (float) (Normalizer.percentToNormalizedX(xIn) + GL2D.SCALE.x);
		GL2D.POSITION.y	= (float) (Normalizer.percentToNormalizedY(yIn) - GL2D.SCALE.y);
		Transformations3f.transformations(GL2D.POSITION.x + 0.045f, GL2D.POSITION.y - 0.045f, z, -20 + rx, 290 + ry,
				90 + rz, GL2D.SCALE.x * ObjectScale, GL2D.SCALE.y / 2 * ObjectScale, GL2D.SCALE.y / 2 * ObjectScale,
				GL2D.TRANSFORMATIONS_MATRIX);
		GL2D.shaderManager.shader3DTo2D().transformations().load(GL2D.TRANSFORMATIONS_MATRIX);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureIdIn);
		GL11.glDrawElements(GL11.GL_TRIANGLES, vertexCount, GL11.GL_UNSIGNED_INT, 0);
	}

	public static void finishRenderingFake3D()
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		GLShaderUtils.detach();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}

	public static void finishRendering()
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		GLShaderUtils.detach();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

	public static void cleanup()
	{
		/**GL30.glDeleteVertexArrays(rawModel.getBaseModel().getVaoId());
		for (int vbo : rawModel.getBaseModel().getVbos())
		{
			GL15.glDeleteBuffers(vbo);
		}
		rawModel = null;**/
	}
}
