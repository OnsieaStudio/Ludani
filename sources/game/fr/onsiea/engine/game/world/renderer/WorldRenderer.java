/**
 *
 */
package fr.onsiea.engine.game.world.renderer;

import java.io.File;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL13;

import fr.onsiea.engine.client.graphics.opengl.shaders.AdvInstancedShader;
import fr.onsiea.engine.client.graphics.render.IRenderAPIContext;
import fr.onsiea.engine.core.entity.PlayerEntity;
import fr.onsiea.engine.game.world.WorldData;
import fr.onsiea.engine.game.world.chunk.Chunk;
import fr.onsiea.engine.game.world.chunk.ChunkUtils;
import fr.onsiea.engine.game.world.item.ItemsLoader;
import lombok.Getter;

/**
 * @author seyro
 *
 */
public class WorldRenderer
{
	private final WorldData				worldData;
	private final IRenderAPIContext		renderAPIContext;

	//private final ChunkVisualizer		chunkVisualizer;
	//private final PickerVisualizer		pickerVisualizer;
	private final AdvInstancedShader	advInstancedShader;

	private @Getter final ItemsLoader	itemsLoader;

	public WorldRenderer(final WorldData worldInformationsIn, final IRenderAPIContext renderAPIContextIn)
	{
		this.worldData			= worldInformationsIn;
		this.renderAPIContext	= renderAPIContextIn;
		//this.pickerVisualizer	= new PickerVisualizer(renderAPIContextIn);
		//this.chunkVisualizer	= new ChunkVisualizer(renderAPIContextIn);
		this.advInstancedShader	= (AdvInstancedShader) renderAPIContextIn.shadersManager().get("advInstanced");
		this.itemsLoader		= new ItemsLoader(new File("resources\\"), renderAPIContextIn);
	}

	public final void initRenderOfChunk(final Chunk chunkIn)
	{
		chunkIn.genRender(this.renderAPIContext);
	}

	public void update(final PlayerEntity playerEntityIn)
	{
		//this.pickerVisualizer.update(playerEntityIn, 0.5f);
	}

	public void draw(final PlayerEntity playerEntityIn, final IRenderAPIContext renderAPIContextIn)
	{
		this.advInstancedShader.attach();
		var i = 0;
		for (final var texturesManagers : this.itemsLoader.texturesManagers())
		{
			GL13.glActiveTexture(GL13.GL_TEXTURE0 + i);
			texturesManagers.textureArray().bind();
			this.advInstancedShader.textureSamplers()[i].load(i);
			i++;
		}

		this.worldData.chunksManager().chunks().forEach(chunkIn -> {
			if (this.worldData.chunksManager().picker().item() != null)
			{
				this.advInstancedShader.selectedUniqueItemId()
						.load(this.worldData.chunksManager().picker().item().uniqueId());
			}
			else
			{
				this.advInstancedShader.selectedUniqueItemId().load(-1.0f);
			}

			if (this.worldData.chunksManager().cullingTest())
			{
				if (playerEntityIn.timedOrientation().hasChanged() && !this.worldData.chunksManager().frustumCulling()
						.insideFrustum(new Vector3f(chunkIn.position()).mul(ChunkUtils.SIZE), 16))
				{
					chunkIn.isVisible(false);

					return;
				}
				chunkIn.isVisible(true);
			}

			if (!chunkIn.isVisible())
			{
				return;
			}

			if (!chunkIn.renderIsInitialized())
			{
				chunkIn.genRender(renderAPIContextIn);
			}

			if (chunkIn.selected())
			{
				this.advInstancedShader.chunkIsSelected().load(true);
			}
			else
			{
				this.advInstancedShader.chunkIsSelected().load(false);
			}

			if (this.worldData.chunksManager().picker().chunk() != null
					&& this.worldData.chunksManager().picker().chunk().selected()
					&& chunkIn.position().equals(this.worldData.chunksManager().picker().chunk().position())
					&& this.worldData.chunksManager().picker().item() != null)
			{
				this.advInstancedShader.selectedInstanceId()
						.load((float) this.worldData.chunksManager().picker().item().instanceId());
				this.advInstancedShader.selectedPosition()
						.load(this.worldData.chunksManager().picker().item().position());
			}
			else
			{
				this.advInstancedShader.selectedInstanceId().load(-1.0f);
				this.advInstancedShader.selectedPosition().load(new Vector3f(-1.0f));
			}

			chunkIn.draw(this.advInstancedShader, this.worldData.chunksManager().picker().selection());

			//this.chunkVisualizer.draw(chunk.position());
		});

		//this.pickerVisualizer.draw();
		renderAPIContextIn.shadersManager().detach();
		i = 0;
		for (final var texturesManagers : this.itemsLoader.texturesManagers())
		{
			GL13.glActiveTexture(GL13.GL_TEXTURE0 + i);
			texturesManagers.textureArray().unbind();
			i++;
		}
		renderAPIContextIn.texturesManager().resetIndex();
	}
}