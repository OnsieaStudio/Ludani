package fr.onsiea.engine.client.graphics.opengl.hud.component;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;

import fr.seynax.onsiea.graphics.texture.Texture;
import fr.seynax.onsiea.input.ActionTypes;
import fr.seynax.onsiea.input.InputManager;

public class HudSlider extends HudHoldButton
{
	private Vector4f	limits;
	private Vector2f	holdPosition;
	private float		percent;
	private float		max;
	private boolean		mustAffine;
	private float		offset;

	public HudSlider(float xIn, float yIn, float sizeXIn, float sizeYIn, TextureLoader baseTextureIn, TextureLoader... texturesIn)
	{
		super(xIn, yIn, sizeXIn, sizeYIn, baseTextureIn, texturesIn);

		this.limits(new Vector4f());
		this.holdPosition(new Vector2f());
		this.offset(Math.abs(this.limits().y()));
	}

	@Override
	protected void justPressed(double cursorXIn, double cursorYIn)
	{
		super.justPressed(cursorYIn, cursorYIn);

		this.holdPosition().set(cursorXIn - this.transformations().x(), cursorYIn - this.transformations().y());
	}

	@Override
	protected void holdRuntime(double cursorXIn, double cursorYIn, InputManager inputManagerIn)
	{
		super.holdRuntime(cursorYIn, cursorYIn, inputManagerIn);

		if (this.hold())
		{
			final var timedAction = inputManagerIn.cursor().buttionActionOf(GLFW.GLFW_MOUSE_BUTTON_1);
			if (timedAction != null && timedAction.type() != null && ActionTypes.RELEASED.equals(timedAction.type()))
			{
				this.hold(false);
			}
			else
			{
				var x = (float) cursorXIn - this.holdPosition().x();
				if (x < this.limits().x())
				{
					x = this.limits().x();
				}
				else if (x > this.limits().z())
				{
					x = this.limits().z();
				}

				var y = (float) cursorYIn - this.holdPosition().y();
				if (y < this.limits().y())
				{
					y = this.limits().y();
				}
				else if (y > this.limits().w())
				{
					y = this.limits().w();
				}

				this.transformations().x	= x;
				this.transformations().y	= y;

				if (this.mustAffine())
				{
					this.percent((this.transformations().y() + this.offset()) / (this.limits().w() + this.offset()));
					final var A = (int) (this.percent() * this.max()) * this.max() * (this.limits().w() + this.offset())
							- this.offset();

					this.transformations().y = A;
				}

				if (this.transformations().x() < this.limits().x())
				{
					this.transformations().x = this.limits().x();
				}
				else if (this.transformations().x() > this.limits().z())
				{
					this.transformations().x = this.limits().z();
				}

				if (this.transformations().y() < this.limits().y())
				{
					this.transformations().y = this.limits().y();
				}
				else if (this.transformations().y() > this.limits().w())
				{
					this.transformations().y = this.limits().w();
				}
			}
		}
	}

	public HudSlider limitsX(float minXIn, float maxXIn)
	{
		this.limits().set(minXIn, this.transformations().y(), maxXIn, this.transformations().y());

		return this;
	}

	public HudSlider limitsY(float minYIn, float maxYIn)
	{
		this.limits().set(this.transformations().x(), minYIn, this.transformations().x(), maxYIn);

		return this;
	}

	public HudSlider limits(float minXIn, float minYIn, float maxXIn, float maxYIn)
	{
		this.limits().set(minXIn, minYIn, maxXIn, maxYIn);

		return this;
	}

	public HudSlider affine(float maxIn)
	{
		this.max(maxIn);
		this.affine(true);

		return this;
	}

	public HudSlider affine()
	{
		this.affine(true);

		return this;
	}

	public HudSlider noAffine()
	{
		this.affine(false);

		return this;
	}

	private Vector4f limits()
	{
		return this.limits;
	}

	private void limits(Vector4f limitsIn)
	{
		this.limits = limitsIn;
	}

	private Vector2f holdPosition()
	{
		return this.holdPosition;
	}

	private void holdPosition(Vector2f holdPositionIn)
	{
		this.holdPosition = holdPositionIn;
	}

	public float percent()
	{
		return this.percent;
	}

	private void percent(float percentIn)
	{
		this.percent = percentIn;
	}

	public float max()
	{
		return this.max;
	}

	private void max(float maxIn)
	{
		this.max = maxIn;
	}

	public boolean mustAffine()
	{
		return this.mustAffine;
	}

	private void affine(boolean mustAffineIn)
	{
		this.mustAffine = mustAffineIn;
	}

	private float offset()
	{
		return this.offset;
	}

	private void offset(float offsetIn)
	{
		this.offset = offsetIn;
	}
}
