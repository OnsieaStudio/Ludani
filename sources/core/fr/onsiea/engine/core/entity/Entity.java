package fr.onsiea.engine.core.entity;

import org.joml.Vector3d;
import org.joml.Vector3f;

import fr.onsiea.engine.utils.maths.vector.timed.TimedVector3f;
import fr.onsiea.engine.utils.maths.velocity.VelocityManager;

public class Entity
{
	private TimedVector3f	timedOrientation;
	private TimedVector3f	timedPosition;

	private VelocityManager	velocityManager;

	public Entity()
	{
		this.timedOrientation(new TimedVector3f());
		this.timedPosition(new TimedVector3f());
		this.initialization();
	}

	public Entity(Vector3f positionIn)
	{
		this.timedOrientation(new TimedVector3f());
		this.timedPosition(new TimedVector3f(positionIn));
		this.initialization();
	}

	public Entity(Vector3f positionIn, Vector3f orientationIn)
	{
		this.timedOrientation(new TimedVector3f(orientationIn));
		this.timedPosition(new TimedVector3f(positionIn));
		this.initialization();
	}

	protected void initialization()
	{
		this.velocityManager(
				new VelocityManager(new Vector3d(1.5f, 1.5f, 1.5f), new Vector3d(1.075f, 1.1075f, 1.075f)));
	}

	final void position(float xIn, float yIn, float zIn)
	{
		this.timedPosition().set(xIn, yIn, zIn);
	}

	final void move(float xIn, float yIn, float zIn)
	{
		this.timedPosition().add(xIn, yIn, zIn);
	}

	final void orientation(float rxIn, float ryIn, float rzIn)
	{
		this.timedOrientation().set(rxIn, ryIn, rzIn);
	}

	final void rotate(float rxIn, float ryIn, float rzIn)
	{
		this.timedOrientation().add(rxIn, ryIn, rzIn);
	}

	final void localisation(float xIn, float yIn, float zIn, float rxIn, float ryIn, float rzIn)
	{
		this.timedPosition().set(xIn, yIn, zIn);
		this.timedOrientation().set(rxIn, ryIn, rzIn);
	}

	final void rotateAndMove(float xIn, float yIn, float zIn, float rxIn, float ryIn, float rzIn)
	{
		this.timedPosition().add(xIn, yIn, zIn);
		this.timedOrientation().add(rxIn, ryIn, rzIn);
	}

	public final Vector3f position()
	{
		return this.timedPosition().base();
	}

	public final Vector3f orientation()
	{
		return this.timedOrientation().base();
	}

	public final TimedVector3f timedPosition()
	{
		return this.timedPosition;
	}

	protected final void timedPosition(TimedVector3f timedPositionIn)
	{
		this.timedPosition = timedPositionIn;
	}

	public final TimedVector3f timedOrientation()
	{
		return this.timedOrientation;
	}

	protected final void timedOrientation(TimedVector3f timedOrientationIn)
	{
		this.timedOrientation = timedOrientationIn;
	}

	protected final VelocityManager velocityManager()
	{
		return this.velocityManager;
	}

	protected final void velocityManager(VelocityManager velocityManagerIn)
	{
		this.velocityManager = velocityManagerIn;
	}
}