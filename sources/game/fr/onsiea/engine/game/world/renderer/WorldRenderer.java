/**
 *
 */
package fr.onsiea.engine.game.world.renderer;

import java.io.File;
import java.util.Map;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL13;

import fr.onsiea.engine.client.graphics.opengl.shaders.AdvInstancedShader;
import fr.onsiea.engine.client.graphics.render.IRenderAPIContext;
import fr.onsiea.engine.core.entity.PlayerEntity;
import fr.onsiea.engine.game.world.WorldInformations;
import fr.onsiea.engine.game.world.chunk.Chunk;
import fr.onsiea.engine.game.world.chunk.culling.CullingManager;
import fr.onsiea.engine.game.world.chunk.culling.FrustumCulling;
import fr.onsiea.engine.game.world.item.ItemsLoader;
import fr.onsiea.engine.game.world.picking.Picker;
import fr.onsiea.engine.utils.time.Timer;
import lombok.Getter;

/**
 * @author seyro
 *
 */
public class WorldRenderer
{
	private final WorldInformations		worldInformations;
	private final IRenderAPIContext		renderAPIContext;

	//private final ChunkVisualizer		chunkVisualizer;
	//private final PickerVisualizer		pickerVisualizer;
	private final AdvInstancedShader	advInstancedShader;

	private @Getter final ItemsLoader	itemsLoader;

	public WorldRenderer(final WorldInformations worldInformationsIn, final IRenderAPIContext renderAPIContextIn)
	{
		this.worldInformations	= worldInformationsIn;
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
		for (final Chunk chunk : this.chunks().values())
		{
			if (this.picker().item() != null)
			{
				this.advInstancedShader.selectedUniqueItemId().load(this.picker().item().uniqueId());
			}

			if (this.cullingTest())
			{
				if (playerEntityIn.timedOrientation().hasChanged()
						&& !this.frustumCulling().insideFrustum(new Vector3f(chunk.position()).mul(Chunk.SIZE), 16))
				{
					chunk.visible(false);

					continue;
				}
				chunk.visible(true);
			}

			if (!chunk.visible())
			{
				continue;
			}

			if (!chunk.renderIsInitialized())
			{
				chunk.genRender(renderAPIContextIn);
			}

			if (chunk.selected())
			{
				this.advInstancedShader.chunkIsSelected().load(true);
			}
			else
			{
				this.advInstancedShader.chunkIsSelected().load(false);
			}

			if (this.picker().chunk() != null && chunk.position().equals(this.picker().chunk().position())
					&& this.picker().item() != null)
			{
				this.advInstancedShader.selectedInstanceId().load((float) this.picker().item().instanceId());
				this.advInstancedShader.selectedPosition().load(this.picker().item().position());
			}
			else
			{
				this.advInstancedShader.selectedInstanceId().load(-1.0f);
			}

			chunk.draw(this.advInstancedShader, this.picker().selection());

			//this.chunkVisualizer.draw(chunk.position());
		}

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

	public Map<Vector3f, Chunk> chunks()
	{
		return this.worldInformations.chunks();
	}

	public CullingManager cullingManager()
	{
		return this.worldInformations.cullingManager();
	}

	public boolean cullingTest()
	{
		return this.worldInformations.cullingTest();
	}

	public Timer destroyTimer()
	{
		return this.worldInformations.destroyTimer();
	}

	public FrustumCulling frustumCulling()
	{
		return this.worldInformations.frustumCulling();
	}

	public Picker picker()
	{
		return this.worldInformations.picker();
	}

	public Timer placeTimer()
	{
		return this.worldInformations.placeTimer();
	}

	public long seed()
	{
		return this.worldInformations.seed();
	}
}