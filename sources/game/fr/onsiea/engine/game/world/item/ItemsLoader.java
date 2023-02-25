/**
 *
 */
package fr.onsiea.engine.game.world.item;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import fr.onsiea.engine.client.graphics.material.Material;
import fr.onsiea.engine.client.graphics.mesh.IMesh;
import fr.onsiea.engine.client.graphics.mesh.IMeshsManager;
import fr.onsiea.engine.client.graphics.mesh.obj.MeshData;
import fr.onsiea.engine.client.graphics.mesh.obj.normalMapped.NormalMappedObjLoader;
import fr.onsiea.engine.client.graphics.opengl.OpenGLRenderAPIContext;
import fr.onsiea.engine.client.graphics.opengl.texture.GLTextureArrayManager;
import fr.onsiea.engine.client.graphics.opengl.texture.GLTextureSettings;
import fr.onsiea.engine.client.graphics.render.IRenderAPIContext;
import fr.onsiea.engine.client.graphics.texture.ITexture;
import fr.onsiea.engine.client.graphics.texture.ITexturesManager;
import fr.onsiea.engine.client.graphics.texture.data.TextureData;
import fr.onsiea.engine.game.world.item.Item.ItemType;
import fr.onsiea.engine.game.world.item.Item.ItemTypeVariant;
import fr.onsiea.engine.utils.Pair;
import lombok.Getter;

/**
 * @author seyro
 *
 */
@Getter
public class ItemsLoader
{
	private final static Material													material	= new Material();

	private final Map<Integer, ItemType>											itemTypes;
	private final List<ItemTypeVariant>												items;
	private final List<ItemType>													itemsWithoutTextures;
	private final Map<Integer, Map<Integer, Pair<Integer, GLTextureArrayManager>>>	sizedTexturesManager;
	private final Map<String, Integer>												subIndexOfTextureManagerOfTexture;
	private final List<String>														texturesName;
	private final List<GLTextureArrayManager>										texturesManagers;
	private final IRenderAPIContext													renderAPIContext;

	public ItemsLoader(final File resourcesPathIn, final IRenderAPIContext renderAPIContextIn)
	{
		this.itemTypes							= new HashMap<>();
		this.items								= new ArrayList<>();
		this.itemsWithoutTextures				= new ArrayList<>();
		this.sizedTexturesManager				= new HashMap<>();
		this.subIndexOfTextureManagerOfTexture	= new HashMap<>();
		this.texturesName						= new ArrayList<>();
		this.texturesManagers					= new ArrayList<>();
		this.renderAPIContext					= renderAPIContextIn;

		final Map<String, Pair<IMesh, MeshData>>	models		= new HashMap<>();

		final var									modelsPath	= new File(resourcesPathIn + "\\objects\\models\\");

		try
		{
			ItemsLoader.load(modelsPath, modelsPath, models, renderAPIContextIn.meshsManager());
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		final Map<String, Map<String, Pair<ITexture, Integer>>>	modeledTextures	= new HashMap<>();

		final var												texturesPath	= new File(
				resourcesPathIn + "\\objects\\textures\\");

		this.load(texturesPath, texturesPath, modeledTextures);

		for (final Entry<String, Pair<IMesh, MeshData>> entry : models.entrySet())
		{
			final var	modelKey	= entry.getKey();
			final var	pair		= entry.getValue();

			final var	min			= new Vector3f(Float.POSITIVE_INFINITY);
			final var	max			= new Vector3f(Float.NEGATIVE_INFINITY);
			for (var i = 0; i < pair.s2().positions().length;)
			{
				final var x = pair.s2().positions()[i];
				i++;
				final var y = pair.s2().positions()[i];
				i++;
				final var z = pair.s2().positions()[i];
				i++;

				if (x < min.x)
				{
					min.x = x;
				}
				else if (x > max.x)
				{
					max.x = x;
				}
				if (y < min.y)
				{
					min.y = y;
				}
				else if (y > max.y)
				{
					max.y = y;
				}
				if (z < min.z)
				{
					min.z = z;
				}
				else if (z > max.z)
				{
					max.z = z;
				}
			}

			final var	itemType	= new ItemType(modelKey, pair.s1(), pair.s2(), min, max, ItemsLoader.material);

			final var	textures	= modeledTextures.get(modelKey);

			if (textures == null)
			{
				this.itemsWithoutTextures.add(itemType);

				continue;
			}

			for (final var texturesEntry : textures.entrySet())
			{
				final var	textureKey	= texturesEntry.getKey();
				final var	texturePair	= texturesEntry.getValue();

				this.itemTypes.put(this.itemTypes.size(), itemType);

				this.items.add(new ItemTypeVariant(itemType, textureKey, texturePair.s2(),
						this.subIndexOfTextureManagerOfTexture.get(textureKey), texturePair.s1()));
			}

		}
	}

	/**
	 * Work in progress
	 * @param texturesManagerIn
	 * @param sizeXIn
	 * @param sizeYIn
	 */
	@SuppressWarnings("unused")
	private final static void putTexture(final GLTextureArrayManager texturesManagerIn, final int sizeXIn,
			final int sizeYIn)
	{
		// Work in progress
		/**
		 *
		 * 16  * 16
		 * 32  * 32		|		32  * 16	| 16 * 32
		 * 64  * 64		|		64  * 16	| 16 * 64	| 64  * 32 	| 32 * 64
		 * 128 * 128	|		128 * 16	| 16 * 128	| 128 * 32	| 32 * 128 | 128 * 64 | 64 * 128
		 * 256 * 256	|		256 * 16	| 16 * 256	| 256 * 32	| 32 * 256 | 256 * 64 | 64 * 256 | 256 * 128 | 128 * 256
		 * 512 * 512	|		512 * 16	| 16 * 512	| 512 * 32	| 32 * 256 | 512 * 64 | 64 * 512 | 512 * 128 | 128 * 512
		 */
	}

	@SuppressWarnings("unchecked")
	private final void load(final File texturesPathIn, final File rootIn,
			final Map<String, Map<String, Pair<ITexture, Integer>>> modeledTexturesIn)
	{
		if (rootIn.listFiles() == null)
		{
			return;
		}
		for (final File file : rootIn.listFiles())
		{
			if (file.isDirectory())
			{
				this.load(texturesPathIn, file, modeledTexturesIn);
			}
			else
			{
				final var path = file.getAbsolutePath();
				if (path.endsWith(".png"))
				{
					final var	name		= file.getName();

					var			modelKey	= path.substring(texturesPathIn.getAbsolutePath().length() + 1,
							path.length() - name.length() - 1);
					modelKey = modelKey.replace("\\", ".");

					final var								textureKey						= modelKey + "."
							+ name.substring(0, name.length() - 4);

					final var								textureData						= TextureData.load(path);

					Pair<Integer, GLTextureArrayManager>	textureManagerPair				= null;

					GLTextureArrayManager					textureManager					= null;

					var										subIndex						= -1;

					var										heightedTextureArrayManagers	= this.sizedTexturesManager
							.get(textureData.width());

					if (heightedTextureArrayManagers == null)
					{
						heightedTextureArrayManagers = new HashMap<>();
						this.sizedTexturesManager.put(textureData.width(), heightedTextureArrayManagers);

						textureManager	= new GLTextureArrayManager(4, textureData.width(), textureData.height(), 10);

						subIndex		= this.texturesManagers.size();

						this.texturesManagers.add(textureManager);

						textureManagerPair = new Pair<>(subIndex, textureManager);
						heightedTextureArrayManagers.put(textureData.height(), textureManagerPair);

					}
					else
					{
						textureManagerPair = heightedTextureArrayManagers.get(textureData.height());

						if (textureManagerPair == null)
						{
							textureManager	= new GLTextureArrayManager(4, textureData.width(), textureData.height(),
									10);
							subIndex		= this.texturesManagers.size();
							this.texturesManagers.add(textureManager);
							textureManagerPair = new Pair<>(subIndex, textureManager);
							heightedTextureArrayManagers.put(textureData.height(), textureManagerPair);
						}
						else
						{
							subIndex = textureManagerPair.s1();
						}
					}

					final var	index		= textureManagerPair.s2().send(textureKey, textureData);

					var			textures	= modeledTexturesIn.get(modelKey);

					if (textures == null)
					{
						textures = new HashMap<>();

						modeledTexturesIn.put(modelKey, textures);
					}

					textures.put(textureKey, new Pair<ITexture, Integer>(
							((ITexturesManager<GLTextureSettings>) this.renderAPIContext.texturesManager()).load(path,
									textureData,
									GLTextureSettings.Builder.of((OpenGLRenderAPIContext) this.renderAPIContext,
											GL11.GL_NEAREST, GL11.GL_NEAREST, GL12.GL_CLAMP_TO_EDGE,
											GL12.GL_CLAMP_TO_EDGE, false)),
							index));

					// When textureData is used to load texture into Opengl or Vulkan, buffer is freed automatically at the end of the operations
					// textureData.cleanup();

					this.subIndexOfTextureManagerOfTexture.put(textureKey, subIndex);
				}
			}
		}
	}

	private final static void load(final File modelsPathIn, final File rootIn,
			final Map<String, Pair<IMesh, MeshData>> modelsIn, final IMeshsManager meshsManagerIn) throws Exception
	{
		if (rootIn.listFiles() == null)
		{
			return;
		}
		for (final File file : rootIn.listFiles())
		{
			if (file.isDirectory())
			{
				ItemsLoader.load(modelsPathIn, file, modelsIn, meshsManagerIn);
			}
			else
			{
				final var path = file.getAbsolutePath();
				if (path.endsWith(".obj"))
				{
					var key = path.substring(modelsPathIn.getAbsolutePath().length() + 1, path.length() - 4);
					key = key.replace("\\", ".");

					MeshData meshData = null;
					try
					{
						meshData = ((NormalMappedObjLoader) meshsManagerIn.objLoader()).loadData(path);
					}
					catch (final Exception e)
					{
						e.printStackTrace();
					}

					if (meshData == null)
					{
						return;
					}

					final var mesh = ((NormalMappedObjLoader) meshsManagerIn.objLoader()).load(meshData);
					modelsIn.put(key, new Pair<>(mesh, meshData));
				}
			}
		}
	}
}