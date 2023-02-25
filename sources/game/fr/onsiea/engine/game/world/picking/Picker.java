package fr.onsiea.engine.game.world.picking;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.joml.Intersectionf;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import fr.onsiea.engine.client.graphics.window.IWindow;
import fr.onsiea.engine.client.input.InputManager;
import fr.onsiea.engine.core.entity.PlayerEntity;
import fr.onsiea.engine.game.world.chunk.Chunk;
import fr.onsiea.engine.game.world.item.Item;
import fr.onsiea.engine.utils.maths.MathInstances;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

public class Picker
{
	@Getter
	@Setter
	@AllArgsConstructor
	@EqualsAndHashCode
	public final static class Selection
	{
		private Item		item;
		private Chunk		chunk;
		private EnumFacing	face;
		private Vector3f	toMake;

		public Selection()
		{
			// item = null;
			// chunk = null;
			this.face	= EnumFacing.NONE;
			this.toMake	= new Vector3f();
		}
	}

	private final Vector3f	max;
	private final Vector3f	min;
	private final Vector2f	nearFar;
	private Vector3f		dir;
	@Getter
	private final Selection	selection;

	public Picker()
	{
		this.dir		= new Vector3f();
		this.min		= new Vector3f();
		this.max		= new Vector3f();
		this.nearFar	= new Vector2f();
		this.selection	= new Selection();
	}

	public void update(final PlayerEntity cameraIn, final InputManager inputManagerIn, final IWindow windowIn)
	{
		//this.dir = cameraIn.view().positiveZ(this.dir).negate();
		// final var dir0 = new Vector3f((float)
		// Math.sin(Math.toRadians(cameraIn.orientation().y())),
		// (float) -Math.sin(Math.toRadians(cameraIn.orientation().x())),
		// (float) -Math.cos(Math.toRadians(cameraIn.orientation().y()))).normalize();

		/**var	dir0	= new Vector3f();
		
		final var	rotX	= cameraIn.orientation().y();
		final var	rotY	= cameraIn.orientation().x();
		
		dir0.y = (float) -Math.sin(Math.toRadians(rotY));
		
		final var h = (float) Math.cos(Math.toRadians(rotY));
		
		dir0.x	= (float) (h * Math.sin(Math.toRadians(rotX)));
		dir0.z	= (float) (-h * Math.cos(Math.toRadians(rotX)));**/

		//System.out.println(inputManagerIn.cursor().x() + ", " + inputManagerIn.cursor().y());

		// Mouse position normalization
		/**final var	mouseX				= (float) inputManagerIn.cursor().x();
		final var	mouseY				= (float) inputManagerIn.cursor().y();
		final var	normalizedMouseX	= 2.0f * mouseX / GraphicsConstants.DEFAULT_WIDTH - 1.0f;
		final var	normalizedMouseY	= 2.0f * mouseY / GraphicsConstants.DEFAULT_HEIGHT - 1.0f;**/
		final var	normalizedMouseX	= (float) (2.0f * inputManagerIn.cursor().x() / windowIn.effectiveWidth()
				- 1.0f);
		final var	normalizedMouseY	= (float) (1.0f
				- 2.0f * inputManagerIn.cursor().y() / windowIn.effectiveHeight());

		// Inverted matrices

		final var	invertedProjection	= new Matrix4f(MathInstances.projectionMatrix()).invert();
		final var	invertedView		= new Matrix4f(cameraIn.view()).invert();

		// Clip

		final var	clipCoords			= new Vector4f(normalizedMouseX, normalizedMouseY, -1.0f, 1.0f);
		final var	eyeCoords			= invertedProjection.transform(clipCoords, new Vector4f());

		var			worldCoords			= new Matrix4f(cameraIn.view()).invert().transform(eyeCoords, new Vector4f());

		if (Math.abs(0.0f - worldCoords.w()) > 0.00001f)
		{
			worldCoords.mul(1.0f / worldCoords.w());
		}
		this.worldCoords3f.set(worldCoords.x(), worldCoords.y(), worldCoords.z());

		eyeCoords.z	= -1.0f;
		eyeCoords.w	= 0.0f;
		worldCoords	= invertedView.transform(eyeCoords, new Vector4f());
		worldCoords.normalize();
		this.dir = new Vector3f(worldCoords.x(), worldCoords.y(), worldCoords.z());

	}

	private final Vector3f worldCoords3f = new Vector3f();

	private final Chunk selectChunk(final Collection<Chunk> chunksIn, final Map<Vector3f, Chunk> whitoutChunksIn)
	{
		var		closestDistance	= Float.POSITIVE_INFINITY;
		Chunk	selectedChunk	= null;
		for (final Chunk chunk : chunksIn)
		{
			/**if (chunk.position().x < 0 || chunk.position().y < 0 || chunk.position().z < 0)
			{

				this.min.set(chunk.position()).mul(16f);
				this.max.set(chunk.position()).mul(16f);
				this.min.add(0.5f, 0.5f, 0.5f);
				this.max.sub(15.5f, 15.5f, 15.5f);
			}
			else
			{**/
			this.min.set(chunk.position()).mul(16f);
			this.max.set(chunk.position()).mul(16f);
			this.min.sub(0.5f, 0.5f, 0.5f);
			this.max.add(15.5f, 15.5f, 15.5f);
			//}

			chunk.selected(false);
			if (Intersectionf.intersectRayAab(this.worldCoords3f, this.dir, this.min, this.max, this.nearFar)
					&& this.nearFar.x <= closestDistance && !whitoutChunksIn.containsKey(chunk.position()))
			{
				if (selectedChunk != null)
				{
					selectedChunk.selected(false);
				}
				chunk.selected(true);
				closestDistance	= this.nearFar.x;
				selectedChunk	= chunk;
			}
		}

		return selectedChunk;
	}

	public final void reset()
	{
		this.selection.item		= null;
		this.selection.chunk	= null;
		this.selection.face		= EnumFacing.NONE;
		this.selection.toMake.set(-1.0f, -1.0f, -1.0f);
	}

	public void selectGameItem(final Collection<Chunk> chunksIn, final PlayerEntity cameraIn)
	{
		this.reset();

		final Map<Vector3f, Chunk>	Withoutchunks	= new HashMap<>();
		Chunk						selectedChunk	= null;

		while (this.selection.item == null && (selectedChunk = this.selectChunk(chunksIn, Withoutchunks)) != null)
		{
			Withoutchunks.put(selectedChunk.position(), selectedChunk);
			var closestDistance = Float.POSITIVE_INFINITY;

			for (final Item item : selectedChunk.items().values())
			{
				this.min.set(item.typeVariant().itemType().min());
				this.max.set(item.typeVariant().itemType().max());
				this.min.add(item.position());
				this.max.add(item.position());

				if (Intersectionf.intersectRayAab(cameraIn.position(), this.dir, this.min, this.max, this.nearFar)
						&& this.nearFar.x < closestDistance)
				{
					closestDistance			= this.nearFar.x;
					this.selection.item		= item;
					this.selection.chunk	= selectedChunk;
				}
			}
		}

		if (this.selection.item != null)
		{
			this.selectFace(cameraIn);
		}
	}

	private final void selectFace(final PlayerEntity cameraIn)
	{
		final var	corner			= new Vector3f(this.selection.item.position()).sub(0.5f, 0.5f, 0.5f);

		var			closestDistance	= Float.POSITIVE_INFINITY;

		this.selection.face = EnumFacing.NONE;
		final var precision = 0.0001f;
		this.min.set(corner).add(1.0f - precision, 0.0f, 0.0f);
		this.max.set(corner).add(1.0f, 1.0f, 1.0f);
		if (Intersectionf.intersectRayAab(cameraIn.position(), this.dir, this.min, this.max, this.nearFar)
				&& this.nearFar.x < closestDistance)
		{
			this.selection.face	= EnumFacing.RIGHT;
			closestDistance		= this.nearFar.x;
		}

		this.min.set(corner);
		this.max.set(corner).add(precision, 1.0f, 1.0f);
		if (Intersectionf.intersectRayAab(cameraIn.position(), this.dir, this.min, this.max, this.nearFar)
				&& this.nearFar.x < closestDistance)
		{
			this.selection.face	= EnumFacing.LEFT;
			closestDistance		= this.nearFar.x;
		}

		this.min.set(corner).add(0.0f, 1.0f - precision, 0.0f);
		this.max.set(corner).add(1.0f, 1.0f, 1.0f);
		if (Intersectionf.intersectRayAab(cameraIn.position(), this.dir, this.min, this.max, this.nearFar)
				&& this.nearFar.x < closestDistance)
		{
			this.selection.face	= EnumFacing.UP;
			closestDistance		= this.nearFar.x;
		}

		this.min.set(corner);
		this.max.set(corner).add(1.0f, precision, 1.0f);
		if (Intersectionf.intersectRayAab(cameraIn.position(), this.dir, this.min, this.max, this.nearFar)
				&& this.nearFar.x < closestDistance)
		{
			this.selection.face	= EnumFacing.DOWN;
			closestDistance		= this.nearFar.x;
		}

		this.min.set(corner).add(0.0f, 0.0f, 1.0f - precision);
		this.max.set(corner).add(1.0f, 1.0f, 1.0f);
		if (Intersectionf.intersectRayAab(cameraIn.position(), this.dir, this.min, this.max, this.nearFar)
				&& this.nearFar.x < closestDistance)
		{
			this.selection.face	= EnumFacing.BACK;
			closestDistance		= this.nearFar.x;
		}

		this.min.set(corner);
		this.max.set(corner).add(1.0f, 1.0f, precision);
		if (Intersectionf.intersectRayAab(cameraIn.position(), this.dir, this.min, this.max, this.nearFar)
				&& this.nearFar.x < closestDistance)
		{
			this.selection.face	= EnumFacing.FRONT;
			closestDistance		= this.nearFar.x;
		}

		if (!EnumFacing.NONE.equals(this.selection.face))
		{
			this.selection.toMake.set((int) this.selection.item.position().x(),
					(int) this.selection.item.position().y(), (int) this.selection.item.position().z())
					.add(this.selection.face.pointsTo());
		}
	}

	/**
	 * public void selectGameItem(GameItem[] gameItems, PlayerEntity camera) {
	 * dir = camera.getViewMatrix().positiveZ(dir).negate();
	 * selectGameItem(gameItems, camera.getPosition(), dir);
	 * }
	 *
	 * protected void selectGameItem(GameItem[] gameItems, Vector3f center, Vector3f
	 * dir) {
	 * GameItem selectedGameItem = null;
	 * float closestDistance = Float.POSITIVE_INFINITY;
	 *
	 * for (GameItem gameItem : gameItems) {
	 * gameItem.setSelected(false);
	 * min.set(gameItem.getPosition());
	 * max.set(gameItem.getPosition());
	 * min.add(-gameItem.getScale(), -gameItem.getScale(), -gameItem.getScale());
	 * max.add(gameItem.getScale(), gameItem.getScale(), gameItem.getScale());
	 * if (Intersectionf.intersectRayAab(center, dir, min, max, nearFar) &&
	 * nearFar.x < closestDistance) {
	 * closestDistance = nearFar.x;
	 * selectedGameItem = gameItem;
	 * }
	 * }
	 *
	 * if (selectedGameItem != null) {
	 * selectedGameItem.setSelected(true);
	 * }
	 * }
	 **/

	// Update view matrix
	/**
	 * camera.updateViewMatrix();
	 *
	 * // Update sound listener position;
	 * soundMgr.updateListenerPosition(camera);
	 *
	 * this.selectDetector.selectGameItem(gameItems, camera);
	 **/

	public Chunk chunk()
	{
		return this.selection.chunk();
	}

	public EnumFacing face()
	{
		return this.selection.face();
	}

	public Item item()
	{
		return this.selection.item();
	}

	public Vector3f toMake()
	{
		return this.selection.toMake();
	}
}