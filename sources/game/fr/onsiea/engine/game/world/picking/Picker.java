package fr.onsiea.engine.game.world.picking;

import java.util.Collection;

import org.joml.Intersectionf;
import org.joml.Vector2f;
import org.joml.Vector3f;

import fr.onsiea.engine.core.entity.Camera;
import fr.onsiea.engine.game.GameTest;
import fr.onsiea.engine.game.world.World.Selection;
import fr.onsiea.engine.game.world.chunk.Chunk;
import fr.onsiea.engine.game.world.item.Item;

public class Picker
{
	private final Vector3f	max;
	private final Vector3f	min;
	private final Vector2f	nearFar;
	private Vector3f		dir;

	public Picker()
	{
		this.dir		= new Vector3f();
		this.min		= new Vector3f();
		this.max		= new Vector3f();
		this.nearFar	= new Vector2f();
	}

	public void selectGameItem(final Collection<Chunk> chunksIn, final Camera cameraIn, final Selection selectionIn)
	{
		selectionIn.item	= null;
		selectionIn.chunk	= null;

		var closestDistance = Float.POSITIVE_INFINITY;
		this.dir = cameraIn.view().positiveZ(this.dir).negate();
		GameTest.loggers.logLn("Picker informations :\n{");
		GameTest.loggers.logLn("	Center : " + cameraIn.position());
		GameTest.loggers.logLn("	Chunks : " + chunksIn.size());
		GameTest.loggers.logLn("	Selection chunk : " + selectionIn.chunk);
		GameTest.loggers.logLn("	Selection item : " + selectionIn.chunk);

		/**
		 * Chunk selectedChunk = null;
		 * for (final Chunk chunk : chunksIn)
		 * {
		 *
		 * this.min.set(chunk.position()).mul(16f);
		 * this.max.set(chunk.position()).mul(16f);
		 * this.min.add(0, 0, 0);
		 * this.max.add(16, 16, 16);
		 * if (Intersectionf.intersectRayAab(camera.position(), this.dir, this.min,
		 * this.max, this.nearFar)
		 * && this.nearFar.x < closestDistance)
		 * {
		 * closestDistance = this.nearFar.x;
		 * selectedChunk = chunk;
		 * }
		 * }
		 **/

		// final var dir0 = new Vector3f((float)
		// Math.sin(Math.toRadians(cameraIn.orientation().y())),
		// (float) -Math.sin(Math.toRadians(cameraIn.orientation().x())),
		// (float) -Math.cos(Math.toRadians(cameraIn.orientation().y()))).normalize();

		final var	dir0	= new Vector3f();

		final var	rotX	= cameraIn.orientation().y();
		final var	rotY	= cameraIn.orientation().x();

		dir0.y = (float) -Math.sin(Math.toRadians(rotY));

		final var h = (float) Math.cos(Math.toRadians(rotY));

		dir0.x	= (float) (h * Math.sin(Math.toRadians(rotX)));
		dir0.z	= (float) (-h * Math.cos(Math.toRadians(rotX)));

		GameTest.loggers.logLn("	Dir : " + this.dir);
		GameTest.loggers.logLn("	Dir0 : " + dir0);

		for (final var chunk : chunksIn)
		{
			for (final Item item : chunk.items().values())
			{
				this.min.set(item.position());
				this.max.set(item.position());
				this.min.sub(item.scale());
				this.max.add(item.scale());
				// this.max.add(new Vector3f(item.scale()).mul(2.0f));
				// GameTest.loggers.logLn(" min : " + this.min);
				// GameTest.loggers.logLn(" max : " + this.max);
				if (Intersectionf.intersectRayAab(cameraIn.position(), dir0, this.min, this.max, this.nearFar)
						&& this.nearFar.x < closestDistance)
				{
					closestDistance		= this.nearFar.x;
					selectionIn.item	= item;
					selectionIn.chunk	= chunk;

					// GameTest.loggers.logLn(" item : " + item);
					// GameTest.loggers.logLn(" chunk : " + chunk);
					GameTest.loggers.logLn(" NearFar : " + this.nearFar);
					GameTest.loggers.logLn(" ClosestDistance : " + closestDistance);
				}
			}
		}
		GameTest.loggers.logLn("	Selection chunk : " + selectionIn.chunk);
		GameTest.loggers.logLn("	Selection item : " + selectionIn.chunk);
		GameTest.loggers.logLn("}");
		// }
	}

	/**
	 * public void selectGameItem(GameItem[] gameItems, Camera camera) {
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
}