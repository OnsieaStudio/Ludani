package fr.onsiea.engine.client.graphics.light;

import org.joml.Vector3f;

public class SpotLight
{
	private PointLight	pointLight;

	private Vector3f	coneDirection;

	private float		cutOff;

	public SpotLight(PointLight pointLightIn, Vector3f coneDirectionIn, float cutoffIn)
	{
		this.pointLight(pointLightIn);
		this.coneDirection(coneDirectionIn);
		this.cutOff(cutoffIn);
	}

	public SpotLight(SpotLight spotLightIn)
	{
		this(new PointLight(spotLightIn.pointLight()), new Vector3f(spotLightIn.coneDirection()), 0);
		this.cutOff(spotLightIn.cutOff());
	}

	public final void cutOffAngle(float cutOffAngleIn)
	{
		this.cutOff((float) Math.cos(Math.toRadians(cutOffAngleIn)));
	}

	public final PointLight pointLight()
	{
		return this.pointLight;
	}

	private final void pointLight(PointLight pointLightIn)
	{
		this.pointLight = pointLightIn;
	}

	public final Vector3f coneDirection()
	{
		return this.coneDirection;
	}

	private final void coneDirection(Vector3f coneDirectionIn)
	{
		this.coneDirection = coneDirectionIn;
	}

	public final float cutOff()
	{
		return this.cutOff;
	}

	private final void cutOff(float cutOffIn)
	{
		this.cutOff = cutOffIn;
	}
}
