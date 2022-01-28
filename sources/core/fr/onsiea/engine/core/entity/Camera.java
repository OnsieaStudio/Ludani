package fr.onsiea.engine.core.entity;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import fr.onsiea.engine.client.graphics.glfw.window.Window;
import fr.onsiea.engine.maths.MathInstances;
import fr.onsiea.engine.maths.vector.TimedVector3f;

public class Camera extends Entity
{
	private Matrix4f viewMatrix;

	public Camera()
	{
		this.timedOrientation(new TimedVector3f());
		this.initialization();
		this.viewMatrix(new Matrix4f().identity());
	}

	public Camera(Vector3f positionIn)
	{
		super(positionIn);
		this.viewMatrix(new Matrix4f().identity());
	}

	public Camera(Vector3f positionIn, Vector3f orientationIn)
	{
		super(positionIn, orientationIn);
		this.viewMatrix(new Matrix4f().identity());
	}

	public void input(Window windowIn)
	{
		var speed = 0.050f;
		// final var	rotateSpeed	= 0.25f;

		if (windowIn.key(GLFW.GLFW_KEY_LEFT_CONTROL) == GLFW.GLFW_PRESS)
		{
			speed *= 2.0f;
		}

		if (windowIn.key(GLFW.GLFW_KEY_TAB) == GLFW.GLFW_PRESS)
		{
			speed /= 2.0f;
		}

		// this.timedOrientation().add((float) inputManagerIn.cursor().translationY() * rotateSpeed,
		//		(float) inputManagerIn.cursor().translationX() * rotateSpeed, 0);

		var	rx	= this.orientation().x();
		var	ry	= this.orientation().y();
		var	rz	= this.orientation().z();

		rx %= 360;
		if (this.orientation().x() > 90)
		{
			rx = 90;
		}
		else if (this.orientation().x() < -90)
		{
			rx = -90;
		}

		ry	%= 360;

		rz	%= 360;
		if (this.orientation().z() > 90)
		{
			rz = 90;
		}
		else if (this.orientation().z() < -90)
		{
			rz = -90;
		}

		this.orientation().set(rx, ry, rz);

		var	x	= 0.0f;
		var	y	= 0.0f;
		var	z	= 0.0f;

		if (windowIn.key(GLFW.GLFW_KEY_W) == GLFW.GLFW_PRESS)
		{
			x	+= Math.sin(this.orientation().y() * MathInstances.pi180()) * speed;
			z	+= -Math.cos(this.orientation().y() * MathInstances.pi180()) * speed;
		}

		if (windowIn.key(GLFW.GLFW_KEY_S) == GLFW.GLFW_PRESS)
		{
			x	+= -Math.sin(this.orientation().y() * MathInstances.pi180()) * speed;
			z	+= Math.cos(this.orientation().y() * MathInstances.pi180()) * speed;
		}

		if (windowIn.key(GLFW.GLFW_KEY_A) == GLFW.GLFW_PRESS)
		{
			x	+= Math.sin((this.orientation().y() - 90) * MathInstances.pi180()) * speed;
			z	+= -Math.cos((this.orientation().y() - 90) * MathInstances.pi180()) * speed;
		}

		if (windowIn.key(GLFW.GLFW_KEY_D) == GLFW.GLFW_PRESS)
		{
			x	+= Math.sin((this.orientation().y() + 90) * MathInstances.pi180()) * speed;
			z	+= -Math.cos((this.orientation().y() + 90) * MathInstances.pi180()) * speed;
		}

		if (windowIn.key(GLFW.GLFW_KEY_LEFT_SHIFT) == GLFW.GLFW_PRESS)
		{
			y -= speed;
		}

		if (windowIn.key(GLFW.GLFW_KEY_SPACE) == GLFW.GLFW_PRESS)
		{
			y += speed;
		}

		/**this.velocityManager().accelerate(x, y, z);
		this.velocityManager().runtime();
		
		this.position().add((float) this.velocityManager().speed().x(), (float) this.velocityManager().speed().y(),
				(float) this.velocityManager().speed().z());**/

		this.timedPosition().add(x, y, z);

		final var negativePosition = new Vector3f(-this.position().x(), -this.position().y(), -this.position().z());

		this.viewMatrix().identity().rotateX((float) Math.toRadians(this.orientation().x()))
				.rotateY((float) Math.toRadians(this.orientation().y()))
				.rotateZ((float) Math.toRadians(this.orientation().z())).translate(negativePosition);

		/**xa += xDir * Mathf.cos(Mathf.toRadians(rot.y)) - zDir * Mathf.sin(Mathf.toRadians(rot.y));
			za += zDir * Mathf.cos(Mathf.toRadians(rot.y)) + xDir * Mathf.sin(Mathf.toRadians(rot.y));
			ya += yDir;


		public Vec3 getForward()
		{
			Vec3 r = new Vec3();
			float cosY = (float) Mathf.cos(Mathf.toRadians(rot.y + 90));
			float sinY = (float) Math.sin(Math.toRadians(rot.y + 90));
			float cosP = (float) Math.cos(Math.toRadians(rot.x));
			float sinP = (float) Math.sin(Math.toRadians(rot.x));

			r.x = cosY * cosP;
			r.y = -sinP;
			r.z = sinY * cosP;

			return r;
		}

		public Vec3 getLookAt()
		{
			Vec3 forward = getForward();
			return new Vec3(pos.x + forward.x, pos.y + forward.y, pos.z + forward.z);
		}**/
		/**this.getAt().setX(this.getViewMatrix().m02);
		this.getAt().setY(this.getViewMatrix().m12);
		this.getAt().setZ(this.getViewMatrix().m22);
		this.getUp().setX(this.getViewMatrix().m01);
		this.getUp().setY(this.getViewMatrix().m11);
		this.getUp().setZ(this.getViewMatrix().m21);**/
	}

	/**private void loadInformations()
	{
		this.linkedInformations().reset().add(MathUtils.round(this.position().x(), 100) + ", "
				+ MathUtils.round(this.position().y(), 100) + ", " + MathUtils.round(this.position().z(), 100));
		final var totalVelocity = MathUtils
				.round(Math.sqrt(this.timedPosition().variation().x() * this.timedPosition().variation().x()
						+ this.timedPosition().variation().y() * this.timedPosition().variation().y()
						+ this.timedPosition().variation().z() * this.timedPosition().variation().z()), 100);
		if (totalVelocity != 0)
		{
			this.linkedInformations().add(MathUtils.round(this.timedPosition().variation().x(), 100) + ", "
					+ MathUtils.round(this.timedPosition().variation().y(), 100) + ", "
					+ MathUtils.round(this.timedPosition().variation().z(), 100) + "[" + totalVelocity + " m/s]");
		}
		this.linkedInformations().add(MathUtils.round(this.orientation().x(), 100) + ", "
				+ MathUtils.round(this.orientation().y(), 100) + ", " + MathUtils.round(this.orientation().z(), 100));

		final var totalRotationSpeed = MathUtils
				.round(Math.sqrt(this.timedOrientation().variation().x() * this.timedOrientation().variation().x()
						+ this.timedOrientation().variation().y() * this.timedOrientation().variation().y()
						+ this.timedOrientation().variation().z() * this.timedOrientation().variation().z()), 100);
		if (totalRotationSpeed != 0)
		{
			this.linkedInformations()
					.add(MathUtils.round(this.timedOrientation().variation().x(), 100) + ", "
							+ MathUtils.round(this.timedOrientation().variation().y(), 100) + ", "
							+ MathUtils.round(this.timedOrientation().variation().z(), 100) + "[" + totalRotationSpeed
							+ " m/s]");
		}
	}**/

	public Matrix4f viewMatrix()
	{
		return this.viewMatrix;
	}

	private void viewMatrix(Matrix4f viewMatrixIn)
	{
		this.viewMatrix = viewMatrixIn;
	}
}
