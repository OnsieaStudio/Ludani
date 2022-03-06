package fr.onsiea.engine.utils;

import org.joml.Vector3d;

public class Velocity
{
	private Vector3d	speed;
	private Vector3d	maxSpeed;
	private Vector3d	decreaseSpeed;

	public Velocity(Vector3d maxSpeedIn, Vector3d decreaseSpeedIn)
	{
		this.speed(new Vector3d());
		this.maxSpeed(maxSpeedIn);
		this.decreaseSpeed(decreaseSpeedIn);
	}

	public void runtime()
	{
		this.speed().div(this.decreaseSpeed().x(), this.decreaseSpeed().y(), this.decreaseSpeed().z());

		if ((int) (this.speed().x() * 250.0f) == 0)
		{
			this.speed().x = 0;
		}
		if ((int) (this.speed().y() * 250.0f) == 0)
		{
			this.speed().y = 0;
		}
		if ((int) (this.speed().z() * 250.0f) == 0)
		{
			this.speed().z = 0;
		}

		this.verify();
	}

	public void accelerate(float xIn, float yIn, float zIn)
	{
		this.speed().add(xIn, yIn, zIn);

		this.verify();
	}

	private boolean verify()
	{
		var i = 0;
		if (this.speed().x() > this.maxSpeed().x())
		{
			this.speed().x = this.maxSpeed().x();

			i++;
		}
		if (this.speed().x() < -this.maxSpeed().x())
		{
			this.speed().x = -this.maxSpeed().x();

			i++;
		}

		if (this.speed().y() > this.maxSpeed().y())
		{
			this.speed().y = this.maxSpeed().y();

			i++;
		}
		if (this.speed().y() < -this.maxSpeed().y())
		{
			this.speed().y = -this.maxSpeed().y();

			i++;
		}

		if (this.speed().z() > this.maxSpeed().z())
		{
			this.speed().z = this.maxSpeed().z();

			i++;
		}
		if (this.speed().z() < -this.maxSpeed().z())
		{
			this.speed().z = -this.maxSpeed().z();

			i++;
		}

		return i < 1;
	}

	public Vector3d speed()
	{
		return this.speed;
	}

	private void speed(Vector3d speedIn)
	{
		this.speed = speedIn;
	}

	public Vector3d maxSpeed()
	{
		return this.maxSpeed;
	}

	private void maxSpeed(Vector3d maxSpeedIn)
	{
		this.maxSpeed = maxSpeedIn;
	}

	public Vector3d decreaseSpeed()
	{
		return this.decreaseSpeed;
	}

	private void decreaseSpeed(Vector3d decreaseSpeedIn)
	{
		this.decreaseSpeed = decreaseSpeedIn;
	}
}
