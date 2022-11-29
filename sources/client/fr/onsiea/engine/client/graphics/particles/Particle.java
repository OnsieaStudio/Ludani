package fr.onsiea.engine.client.graphics.particles;

import org.joml.Vector3f;

import fr.onsiea.engine.core.entity.Camera;
import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PUBLIC)
public class Particle implements IParticle
{
	private final Vector3f	position;
	private final Vector3f	orientation;
	private final Vector3f	scale;
	public float			texX;
	public float			texY;

	private final Camera	camera;

	public Particle(Camera cameraIn)
	{
		this.position		= new Vector3f();
		this.orientation	= new Vector3f();
		this.scale			= new Vector3f(1.0f);
		this.texX			= 1.0f;
		this.texY			= 1.0f;

		this.camera			= cameraIn;
	}

	@Override
	public float getSquareDistance()
	{
		final var	distX	= this.camera.position().x - this.position.x;
		final var	distY	= this.camera.position().y - this.position.y;
		final var	distZ	= this.camera.position().z - this.position.z;

		return distX * distX + distY * distY + distZ * distZ;
	}
}