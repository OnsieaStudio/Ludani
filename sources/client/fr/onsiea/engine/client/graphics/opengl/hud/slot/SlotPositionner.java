/**
 *
 */
package fr.onsiea.engine.client.graphics.opengl.hud.slot;

import fr.onsiea.engine.client.graphics.GraphicsConstants;
import fr.onsiea.engine.client.graphics.texture.ITextureData;
import fr.onsiea.engine.client.graphics.window.IWindow;
import lombok.Getter;

/**
 * @author seyro
 *
 */
@Getter
public class SlotPositionner
{
	public static float	normalizedPaddingX;
	public static float	normalizedPaddingY;
	public static float	normalizedMiddledPaddingX;
	public static float	normalizedMiddledPaddingY;

	// Not normalized

	private final int	width;
	private final int	height;
	private final float	scaledWidth;
	private final float	scaledHeight;
	private final float	scaledPaddedWidth;
	private final float	scaledPaddedHeight;

	// Normalized

	private final float	normalizedWidth;
	private final float	normalizedHeight;
	private final float	scaledNormalizedWidth;
	private final float	scaledNormalizedHeight;
	private final float	scaledPaddedNormalizedWidth;
	private final float	scaledPaddedNormalizedHeight;

	// Middled = value / 2.0f
	// Middled not normalized

	private final float	middledScaledWidth;
	private final float	middledScaledHeight;

	// Middled normalized

	private final float	middledScaledNormalizedWidth;
	private final float	middledScaledNormalizedHeight;

	public SlotPositionner(final ITextureData textureDataIn, final IWindow windowIn)
	{
		// Not normalized

		this.width									= textureDataIn.width();
		this.height									= textureDataIn.height();
		this.scaledWidth							= this.width
				* GraphicsConstants.CreativeInventory.SLOT_SCALE_MULTIPLICATOR;
		this.scaledHeight							= this.height
				* GraphicsConstants.CreativeInventory.SLOT_SCALE_MULTIPLICATOR;
		this.scaledPaddedWidth						= this.scaledWidth + GraphicsConstants.CreativeInventory.PADDING;
		this.scaledPaddedHeight						= this.scaledHeight + GraphicsConstants.CreativeInventory.PADDING;

		// Normalized

		SlotPositionner.normalizedPaddingX			= GraphicsConstants.CreativeInventory.PADDING
				/ textureDataIn.width();
		SlotPositionner.normalizedPaddingY			= GraphicsConstants.CreativeInventory.PADDING
				/ textureDataIn.height();
		SlotPositionner.normalizedMiddledPaddingX	= GraphicsConstants.CreativeInventory.MIDDLED_PADDING
				/ textureDataIn.width();
		SlotPositionner.normalizedMiddledPaddingY	= GraphicsConstants.CreativeInventory.MIDDLED_PADDING
				/ textureDataIn.height();

		this.normalizedWidth						= (float) this.width / windowIn.effectiveWidth();
		this.normalizedHeight						= (float) this.height / windowIn.effectiveHeight();
		this.scaledNormalizedWidth					= this.normalizedWidth
				* GraphicsConstants.CreativeInventory.SLOT_SCALE_MULTIPLICATOR;
		this.scaledNormalizedHeight					= this.normalizedHeight
				* GraphicsConstants.CreativeInventory.SLOT_SCALE_MULTIPLICATOR;
		this.scaledPaddedNormalizedWidth			= (this.scaledWidth + GraphicsConstants.CreativeInventory.PADDING)
				/ windowIn.effectiveWidth();
		this.scaledPaddedNormalizedHeight			= (this.scaledHeight + GraphicsConstants.CreativeInventory.PADDING)
				/ windowIn.effectiveHeight();

		// Middled not normalized

		this.middledScaledWidth						= this.scaledWidth / 2.0f;
		this.middledScaledHeight					= this.scaledHeight / 2.0f;

		// Middled normalized

		this.middledScaledNormalizedWidth			= this.scaledNormalizedWidth / 2.0f;
		this.middledScaledNormalizedHeight			= this.scaledNormalizedHeight / 2.0f;
	}
}