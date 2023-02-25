/**
 *
 */
package fr.onsiea.engine.client.graphics.opengl;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import fr.onsiea.engine.client.graphics.opengl.texture.GLTexture;
import fr.onsiea.engine.client.graphics.opengl.texture.GLTextureSettings;
import fr.onsiea.engine.client.graphics.opengl.utils.OpenGLUtils;
import fr.onsiea.engine.client.graphics.texture.ITexture;
import fr.onsiea.engine.client.graphics.texture.ITextureData;
import fr.onsiea.engine.client.graphics.texture.Texture;
import fr.onsiea.engine.client.graphics.texture.data.TextureData;
import fr.onsiea.engine.utils.LoopUtils;
import fr.onsiea.engine.utils.Pair;

/**
 * @author seyro
 *
 */
public class GraphicsUtils
{
	public final static int					quadVao;
	public final static int					quadVbo;

	private static OpenGLRenderAPIContext	openGLRenderAPIContext;

	public final static void set(final OpenGLRenderAPIContext openGLRenderAPIContextIn)
	{
		if (GraphicsUtils.openGLRenderAPIContext == null)
		{
			GraphicsUtils.openGLRenderAPIContext = openGLRenderAPIContextIn;
		}
	}

	/**
	 *
	 * @param textureFilePathIn
	 * @param minIn
	 * @param magIn
	 * @param wrapSIn
	 * @param wrapTIn
	 * @param mipmappingIn
	 * @return loaded texture
	 */
	public final static Texture<GLTextureSettings> loadTexture(final String textureFilePathIn, final int minIn,
			final int magIn, final int wrapSIn, final int wrapTIn, final boolean mipmappingIn)
	{
		return GraphicsUtils.openGLRenderAPIContext.texturesManager().load(textureFilePathIn, GLTextureSettings.Builder
				.of(GraphicsUtils.openGLRenderAPIContext, minIn, magIn, wrapSIn, wrapTIn, mipmappingIn));
	}

	/**
	 * Load all textures data and ITexture of texturesFilePathIn[...] paths
	 * @param basePathIn
	 * @param minIn
	 * @param magIn
	 * @param wrapSIn
	 * @param wrapTIn
	 * @param mipmappingIn
	 * @param texturesFilePathIn : png textures filepath (.png at end is optional)
	 * @return array of loaded textures
	 */
	public final static Texture<GLTextureSettings>[] loadTextures(final int minIn, final int magIn, final int wrapSIn,
			final int wrapTIn, final boolean mipmappingIn, final String... texturesFilePathIn)
	{
		final Texture<GLTextureSettings>[] textures = new GLTexture[texturesFilePathIn.length];

		LoopUtils.loop((indexIn,
				textureFilePath) -> textures[indexIn] = GraphicsUtils.openGLRenderAPIContext.texturesManager()
						.load(textureFilePath, GLTextureSettings.Builder.of(GraphicsUtils.openGLRenderAPIContext, minIn,
								magIn, wrapSIn, wrapTIn, mipmappingIn)),
				texturesFilePathIn);

		return textures;
	}

	/**
	 *
	 * @param texturesFilePathIn : png texture filepath (.png at end is optional)
	 * @param minIn
	 * @param magIn
	 * @param wrapSIn
	 * @param wrapTIn
	 * @param mipmappingIn
	 * @return pair of loaded textureData and texture
	 */
	public final static Pair<ITextureData, Texture<GLTextureSettings>> loadTextureAndData(
			final String textureFilePathIn, final int minIn, final int magIn, final int wrapSIn, final int wrapTIn,
			final boolean mipmappingIn)
	{
		var path = textureFilePathIn;
		if (!path.endsWith(".png"))
		{
			path = path + ".png";
		}

		final var	textureData	= TextureData.load(textureFilePathIn);
		final var	texture		= GraphicsUtils.openGLRenderAPIContext.texturesManager().load(path, textureData,
				GLTextureSettings.Builder.of(GraphicsUtils.openGLRenderAPIContext, minIn, magIn, wrapSIn, wrapTIn,
						mipmappingIn));

		return new Pair<>(textureData, texture);
	}

	/**
	 * Load all textures data and ITexture of texturesFilePathIn[...] paths
	 * @param basePathIn
	 * @param minIn
	 * @param magIn
	 * @param wrapSIn
	 * @param wrapTIn
	 * @param mipmappingIn
	 * @param texturesFilePathIn : png textures filepath (.png at end is optional)
	 * @return map of paired textureData and texture sorted by name in format a.b.c where all '/' are replaced by '.' and .png is removed
	 */
	public final static Map<String, Pair<ITextureData, ITexture>> loadTexturesAndData(final int minIn, final int magIn,
			final int wrapSIn, final int wrapTIn, final boolean mipmappingIn, final String... texturesFilePathIn)
	{
		final var textures = new HashMap<String, Pair<ITextureData, ITexture>>();

		LoopUtils.loop((indexIn, textureFilePath) -> {
			var path = textureFilePath;
			if (!path.endsWith(".png"))
			{
				path = path + ".png";
			}

			final var	textureData	= TextureData.load(textureFilePath);
			final var	texture		= GraphicsUtils.openGLRenderAPIContext.texturesManager().load(textureFilePath,
					textureData, GLTextureSettings.Builder.of(GraphicsUtils.openGLRenderAPIContext, minIn, magIn,
							wrapSIn, wrapTIn, mipmappingIn));

			var			name		= textureFilePath.replace('/', '.');

			if (name.endsWith(".png"))
			{
				name = name.substring(0, name.length() - 4);
			}

			textures.put(name, new Pair<>(textureData, texture));
		}, texturesFilePathIn);

		return textures;
	}

	/**
	 * Load all textures data and ITexture of basePathIn\texturesFilePathIn[...] paths
	 * @param basePathIn
	 * @param minIn
	 * @param magIn
	 * @param wrapSIn
	 * @param wrapTIn
	 * @param mipmappingIn
	 * @param texturesFilePathIn : png textures filepath (.png at end is optional)
	 * @return map of paired textureData and texture sorted by name in format a.b.c where all '/' are replaced by '.' and .png is removed
	 */
	public final static Map<String, Pair<ITextureData, ITexture>> loadTexturesAndData(final String basePathIn,
			final int minIn, final int magIn, final int wrapSIn, final int wrapTIn, final boolean mipmappingIn,
			final String... texturesFilePathIn)
	{
		final var textures = new HashMap<String, Pair<ITextureData, ITexture>>();

		LoopUtils.loop((indexIn, textureFilePath) -> {

			var path = textureFilePath;
			if (!path.endsWith(".png"))
			{
				path = path + ".png";
			}

			final var	textureData	= TextureData.load(basePathIn + "\\" + path);
			final var	texture		= GraphicsUtils.openGLRenderAPIContext.texturesManager().load(textureFilePath,
					textureData, GLTextureSettings.Builder.of(GraphicsUtils.openGLRenderAPIContext, minIn, magIn,
							wrapSIn, wrapTIn, mipmappingIn));

			var			name		= textureFilePath.replace('/', '.');

			if (name.endsWith(".png"))
			{
				name = name.substring(0, name.length() - 4);
			}

			textures.put(name, new Pair<>(textureData, texture));
		}, texturesFilePathIn);

		return textures;
	}

	static
	{
		quadVao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(GraphicsUtils.quadVao);
		GL20.glEnableVertexAttribArray(0);

		quadVbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, GraphicsUtils.quadVbo);
		GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, 0, 0L);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, new float[]
		{
				-1.0f, -1.0f, 1.0f, -1.0f, 1.0f, 1.0f, 1.0f, 1.0f, -1.0f, 1.0f, -1.0f, -1.0f
		}, GL15.GL_DYNAMIC_DRAW);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		GL30.glBindVertexArray(0);
	}

	/**
	 * Used to draw quad with drawQuad() method
	 */
	public final static void prepare2D()
	{
		OpenGLUtils.initialize2D();
		OpenGLUtils.enableTransparency();
		GL30.glBindVertexArray(GraphicsUtils.quadVao);
		GL20.glEnableVertexAttribArray(0);
	}

	/**
	 * Need executing prepareQuad before and unbindQuad after.
	 */
	public final static void drawQuad()
	{
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);
	}

	/**
	 * No need to execute prepareQuad() before but attaches and detaches quadVao on each drawing call
	 */
	public final static void drawOneQuad()
	{
		GL30.glBindVertexArray(GraphicsUtils.quadVao);
		GL20.glEnableVertexAttribArray(0);
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);
		GL30.glBindVertexArray(0);
	}

	/**
	 * Used to end draw quad
	 */
	public final static void end2D()
	{
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		OpenGLUtils.disableTransparency();
		OpenGLUtils.initialize3D();
	}
}
