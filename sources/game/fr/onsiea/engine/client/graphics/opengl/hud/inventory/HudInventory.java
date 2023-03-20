/**
 *
 */
package fr.onsiea.engine.client.graphics.opengl.hud.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import fr.onsiea.engine.client.graphics.GraphicsConstants;
import fr.onsiea.engine.client.graphics.opengl.GraphicsUtils;
import fr.onsiea.engine.client.graphics.opengl.hud.Hud;
import fr.onsiea.engine.client.graphics.opengl.hud.HudManager;
import fr.onsiea.engine.client.graphics.opengl.hud.components.HudRectangle;
import fr.onsiea.engine.client.graphics.opengl.hud.components.scrollbar.HudScrollbar;
import fr.onsiea.engine.client.graphics.opengl.hud.inventory.components.HotBar;
import fr.onsiea.engine.client.graphics.opengl.hud.slot.HudItem;
import fr.onsiea.engine.client.graphics.opengl.hud.slot.Slot;
import fr.onsiea.engine.client.graphics.opengl.hud.slot.SlotPositionner;
import fr.onsiea.engine.client.graphics.opengl.nanovg.NanoVGManager;
import fr.onsiea.engine.client.graphics.opengl.shaders.Shader2DIn3D;
import fr.onsiea.engine.client.graphics.opengl.shaders.Shader3DTo2D;
import fr.onsiea.engine.client.graphics.render.IRenderAPIContext;
import fr.onsiea.engine.client.graphics.texture.ITexture;
import fr.onsiea.engine.client.graphics.texture.ITextureData;
import fr.onsiea.engine.client.graphics.window.IWindow;
import fr.onsiea.engine.client.input.InputManager;
import fr.onsiea.engine.game.world.World;
import fr.onsiea.engine.game.world.item.Item.ItemTypeVariant;
import fr.onsiea.engine.utils.LoopUtils;
import fr.onsiea.engine.utils.Pair;
import fr.onsiea.engine.utils.Triplet;
import fr.onsiea.engine.utils.maths.MathUtils;
import fr.onsiea.engine.utils.maths.normalization.normalizable.vector.Normalizable2f;
import fr.onsiea.engine.utils.positionnable.Anchor;
import fr.onsiea.engine.utils.positionnable.EnumAnchorDirectionX;
import fr.onsiea.engine.utils.positionnable.EnumAnchorDirectionY;
import fr.onsiea.engine.utils.positionnable.EnumAnchorMode;
import fr.onsiea.engine.utils.positionnable.Positionnable;
import lombok.Getter;

/**
 * @author seyro
 *
 */
public class HudInventory extends Hud
{
	private final SlotPositionner					slotPositionner;
	public HudItem									hudItem;
	private @Getter final List<ItemTypeVariant>		items;
	private final Map<Integer, Map<Integer, Slot>>	sortedSlots;
	private final List<Slot>						slots;
	private int										lines;
	private HudScrollbar							scrollbar;
	private @Getter HotBar							hotBar;
	public boolean									selected					= false;
	public boolean									releaseItem;
	private float									percent;
	private float									lastI						= 0;
	public HudItem									last;

	private final static String						BACKGROUND_TEXTURE			= "hudBackground";
	public final static String						SLOT_TEXTURE				= "slot";
	private final static String						SCROLLBAR_LINE_TEXTURE		= "scrollbarLine";
	private final static String						SCROLLBAR_CURSOR_TEXTURE	= "scrollbarCursor";

	public HudRectangle								background;

	/**
	 * @param nameIn
	 * @param needFocusIn
	 */
	public HudInventory(final String nameIn, final boolean needFocusIn, final IRenderAPIContext renderAPIContextIn,
			final IWindow windowIn, final World worldIn, final NanoVGManager nanoVGManagerIn,
			final HudManager hudManagerIn)
	{
		super(nameIn, needFocusIn);
		this.items			= new LinkedList<>();
		this.slots			= new ArrayList<>();
		this.sortedSlots	= new HashMap<>();

		final var textures = GraphicsUtils.loadTexturesAndData("resources\\textures\\hud", GL11.GL_NEAREST,
				GL11.GL_NEAREST, GL12.GL_CLAMP_TO_EDGE, GL12.GL_CLAMP_TO_EDGE, false, "hudBackground", "slot",
				"scrollbarLine", "scrollbarCursor");

		this.slotPositionner = new SlotPositionner(textures.get(HudInventory.SLOT_TEXTURE).s1(), windowIn);

		/**
		 * Loading of items in one list
		 */
		{
			var i1 = 0;
			if (GraphicsConstants.CreativeInventory.DEBUG_SCROLLBAR)
			{
				/**for (var pages = 0; pages < 4; pages++)
				{
					for (var x = 0; x < GraphicsConstants.CreativeInventory.SLOTS_COLUMNS; x++)
					{
						for (var y = 0; y < lines; y++)
						{
							if (x == 0 && y == 0)
							{
								i1 = worldIn.itemsLoader().items().size() - 1;
							}
							else
							{
								i1 = MathUtils.randomInt(0, worldIn.itemsLoader().items().size() - 2);
							}
				
							final var itemTypeVariant = worldIn.itemsLoader().items().get(i1);
				
							this.items.add(itemTypeVariant);
						}
					}
				}**/
				for (var pages = 0; pages < 150; pages++)
				{
					i1 = MathUtils.randomInt(0, worldIn.itemsLoader().items().size() - 2);

					final var itemTypeVariant = worldIn.itemsLoader().items().get(i1);

					this.items.add(itemTypeVariant);
				}
			}
			else
			{
				for (var i0 = 0; i0 < worldIn.itemsLoader().items().size(); i0++)
				{
					this.items.add(worldIn.itemsLoader().items().get(i0));
				}
			}

			this.lines = GraphicsConstants.CreativeInventory.SLOTS_LINES;
			if (GraphicsConstants.CreativeInventory.ONLY_NEEDED_SLOTS
					&& this.items.size() <= GraphicsConstants.CreativeInventory.SLOTS_COLUMNS * this.lines)
			{
				var lines = (float) this.items.size() / GraphicsConstants.CreativeInventory.SLOTS_COLUMNS;

				if (lines - (int) lines > 0)
				{
					lines = (int) lines + 1;
				}

				this.lines = (int) lines;
			}
		}

		final var	slotsSize			= this.slotPositionner.scaledWidth()
				+ GraphicsConstants.CreativeInventory.PADDING;
		final var	scrollbarCursorSize	= this.slotPositionner.scaledWidth() / 2.0f;
		final var	scrollbarLineSizeX	= this.slotPositionner.width() * 4.0f / 2.0f;
		final var	categorySizeY		= this.slotPositionner.width() * 6;

		final var	backgroundSizeX		= slotsSize * GraphicsConstants.CreativeInventory.SLOTS_COLUMNS
				+ scrollbarCursorSize + GraphicsConstants.CreativeInventory.PADDING;
		final var	backgroundSizeY		= slotsSize * (this.lines + 1) + 8 * GraphicsConstants.CreativeInventory.PADDING
				+ categorySizeY;

		this.background = new HudRectangle(
				Positionnable.Builder.of(
						new Normalizable2f(0.5f * windowIn.effectiveWidth(), 0.5f * windowIn.effectiveHeight(),
								() -> (float) windowIn.effectiveWidth(), () -> (float) windowIn.effectiveHeight()),
						new Normalizable2f(0.60f * windowIn.effectiveWidth(), 0.75f * windowIn.effectiveHeight(),
								() -> (float) windowIn.effectiveWidth(), () -> (float) windowIn.effectiveHeight())),
				textures.get(HudInventory.BACKGROUND_TEXTURE).s2());
		this.components.add(this.background);
		final var	scrollbarSizeY	= this.lines * slotsSize - 1 * GraphicsConstants.CreativeInventory.PADDING
				- GraphicsConstants.CreativeInventory.PADDING * 4 * 2;
		var			fx0				= backgroundSizeX / 2 - scrollbarCursorSize / 2;
		fx0				= fx0 + 1.0f;
		this.scrollbar	= null;
		try
		{
			final var	linePositionnable	= this.background.positionnable.anchor()
					.set(-50, this.slotPositionner.scaledHeight() * 1.25f, scrollbarLineSizeX, scrollbarSizeY)
					.directionX(EnumAnchorDirectionX.RIGHT).directionY(EnumAnchorDirectionY.UP)
					.build(() -> (float) windowIn.effectiveWidth(), () -> (float) windowIn.effectiveHeight());

			final var	cursorPositionnable	= linePositionnable.anchor()
					.directionsAndModes(EnumAnchorMode.BORDER, EnumAnchorDirectionY.UP)
					.size(scrollbarCursorSize, scrollbarCursorSize)
					.build(() -> (float) windowIn.effectiveWidth(), () -> (float) windowIn.effectiveHeight());

			this.scrollbar = new HudScrollbar(linePositionnable, cursorPositionnable,
					textures.get(HudInventory.SCROLLBAR_LINE_TEXTURE).s2(),
					textures.get(HudInventory.SCROLLBAR_CURSOR_TEXTURE).s2(), scrollbarLineSizeX, scrollbarSizeY, this);
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
		this.components.add(this.scrollbar);

		//final var	a		= Math.max(worldIn.itemsLoader().items().size() / GraphicsConstants.CreativeInventory.SLOTS_COLUMNS, 1);

		/**{
			// Auto Slot Resizer System ASRS
		
			// (lines * (slotSizeX +SlotPositionner.PADDING) * column * (slotSizeY +SlotPositionner.PADDING))
			final var	totalSpace	= 10 * (slotPositionner.scaledPaddedWidth()) * 5
					* (slotPositionner.scaledPaddedHeight());
		
			final var	slotSpace	= (slotPositionner.scaledPaddedWidth())
					* (slotPositionner.scaledPaddedHeight());
		
			System.out.println("Total space : " + totalSpace);
			System.out.println("Slot space : " + slotSpace);
			System.out.println("Slot possibility number : " + totalSpace / slotSpace);
			System.out.println("Resized space : " + totalSpace / (GraphicsConstants.CreativeInventory.SLOTS_COLUMNS * lines));
			final var size = (float) Math.sqrt(totalSpace / (GraphicsConstants.CreativeInventory.SLOTS_COLUMNS * lines));
			System.out.println("Squared resized space : " + size);
			mulNormalizedSizeX	= size / windowIn.effectiveWidth();
			mulNormalizedSizeY	= size / windowIn.effectiveHeight();
			GraphicsConstants.CreativeInventory.SLOTS_COLUMNS				= (int) Math.sqrt(totalSpace / slotSpace);
			lines				= worldIn.itemsLoader().items().size() / GraphicsConstants.CreativeInventory.SLOTS_COLUMNS;
		
			System.out.println(GraphicsConstants.CreativeInventory.SLOTS_COLUMNS + ", " + lines);
		}**/

		final var data = new loopIterationData();

		LoopUtils.coordinateInversed2DLoop(GraphicsConstants.CreativeInventory.SLOTS_COLUMNS, this.lines, data,
				(indexIn, xPositionIn, yPositionIn, dataIn) -> this.slotLoopIteration(indexIn, xPositionIn, yPositionIn,
						dataIn, windowIn, textures, false));
		this.components.addAll(this.slots);

		try
		{
			this.hotBar = new HotBar(nameIn, needFocusIn, this, windowIn, this.slotPositionner, renderAPIContextIn,
					textures);
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		hudManagerIn.add("inventoryHotbar", this.hotBar);

		if (this.items.size() <= GraphicsConstants.CreativeInventory.SLOTS_COLUMNS
				* GraphicsConstants.CreativeInventory.SLOTS_LINES)
		{
			this.scrollbar.cursorDisable(true);
		}
		else
		{
			this.scrollbar.cursorDisable(false);
		}
	}

	class loopIterationData
	{
		int		specialIndex	= 0;
		int		remainingItems	= -1;
		Slot	last;
		Slot	lastX;
		Slot	lastY;
		int		lastYPosition	= 0;
	}

	private final boolean slotLoopIteration(final int indexIn, final int xPositionIn, final int yPositionIn,
			final loopIterationData dataIn, final IWindow windowIn,
			final Map<String, Pair<ITextureData, ITexture>> texturesIn, final boolean inRightIn)
	{
		if (dataIn.specialIndex >= this.items.size() && GraphicsConstants.CreativeInventory.ONLY_NEEDED_SLOTS)
		{
			return false;
		}

		dataIn.lastX = dataIn.last;
		if (yPositionIn != dataIn.lastYPosition)
		{
			dataIn.lastY			= dataIn.last;
			dataIn.lastX			= null;
			dataIn.lastYPosition	= yPositionIn;
		}

		if (this.items.size() - dataIn.specialIndex < GraphicsConstants.CreativeInventory.SLOTS_COLUMNS
				&& dataIn.specialIndex < 0)
		{
			dataIn.remainingItems = this.items.size() - dataIn.specialIndex;
		}

		final var	triplet	= this.autoSlotCentralizer(dataIn.remainingItems, xPositionIn, dataIn.specialIndex, -1.0f);

		final var	item	= triplet.s1();
		dataIn.specialIndex = triplet.s3();

		final var anchor = new Anchor();

		anchor.size(this.slotPositionner.scaledWidth(), this.slotPositionner.scaledHeight());

		if (dataIn.lastY == null)
		{
			anchor.relativeY(anchor.h() * 1);
		}
		else
		{
			anchor.relativeY(10);
		}
		if (dataIn.lastX == null)
		{
			if (inRightIn)
			{
				anchor.relativeX(-70);
				anchor.xPositionnable(this.background.positionnable()).directionX(EnumAnchorDirectionX.RIGHT);
			}
			else
			{
				anchor.relativeX(10);
				anchor.xPositionnable(this.background.positionnable()).directionX(EnumAnchorDirectionX.LEFT);
			}
		}
		else if (inRightIn)
		{
			anchor.relativeX(-10);
			anchor.xPositionnable(dataIn.lastX.positionnable).directionX(EnumAnchorDirectionX.LEFT)
					.modeX(EnumAnchorMode.OUT);
		}
		else
		{
			anchor.relativeX(10);
			anchor.xPositionnable(dataIn.lastX.positionnable).directionX(EnumAnchorDirectionX.RIGHT)
					.modeX(EnumAnchorMode.OUT);
		}

		if (dataIn.lastY == null)
		{
			anchor.yPositionnable(this.background.positionnable()).directionY(EnumAnchorDirectionY.UP);
		}
		else
		{
			anchor.yPositionnable(dataIn.lastY.positionnable).directionY(EnumAnchorDirectionY.DOWN)
					.modeY(EnumAnchorMode.OUT);
		}

		Slot slot = null;
		try
		{
			slot = new Slot(
					anchor.build(() -> (float) windowIn.effectiveWidth(), () -> (float) windowIn.effectiveHeight()),
					texturesIn.get(HudInventory.SLOT_TEXTURE).s2(), item);
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		var a = this.sortedSlots.get(xPositionIn);
		if (a == null)
		{
			a = new HashMap<>();

			this.sortedSlots.put(xPositionIn, a);
		}
		a.put(yPositionIn, slot);

		this.slots.add(slot);
		dataIn.last = slot;

		return true;
	}

	/**
	 * Auto Slot Centralizer System (ASCS)
	 * @param remainingItemsIn
	 * @return triple with <itemTypeVariant, x, index>
	 */
	private final static Triplet<ItemTypeVariant, Float, Integer> ASCS_TEMP_TRIPLET = new Triplet<>();

	private Triplet<ItemTypeVariant, Float, Integer> autoSlotCentralizer(final float remainingItemsIn,
			final float slotCurrentColumnIn, int indexIn, final float xBase)
	{
		var				x			= xBase;

		ItemTypeVariant	item		= null;
		var				selected	= false;
		// Auto Slot Centralizer System ASCS
		if (remainingItemsIn >= 0 && GraphicsConstants.CreativeInventory.AUTO_SLOT_CENTRALIZER_SYSTEM)
		{
			if (GraphicsConstants.CreativeInventory.ONLY_NEEDED_SLOTS)
			{
				x = xBase
						+ (GraphicsConstants.CreativeInventory.SLOTS_COLUMNS * this.slotPositionner.scaledPaddedWidth()
								- remainingItemsIn * this.slotPositionner.scaledPaddedWidth()) / 2.0f;
			}
			else
			{
				final var triplet = this.autoItemCentralizer(remainingItemsIn, slotCurrentColumnIn, indexIn);
				item		= triplet.s1();
				selected	= triplet.s2();
				indexIn		= triplet.s3();
			}
		}

		if (!selected)
		{
			if (indexIn < this.items.size())
			{
				item = this.items.get(indexIn);
			}
			indexIn++;
		}

		return HudInventory.ASCS_TEMP_TRIPLET.set(item, x, indexIn);
	}

	/**
	 * Auto Item Centralizer System (AICS)
	 * @param remainingItemsIn
	 * @return triplet with <itemTypeVariant, if item has been, index>
	 */
	private final static Triplet<ItemTypeVariant, Boolean, Integer> AICS_TEMP_TRIPLET = new Triplet<>();

	private Triplet<ItemTypeVariant, Boolean, Integer> autoItemCentralizer(final float remainingItemsIn,
			final float slotCurrentColumnIn, int indexIn)
	{
		ItemTypeVariant	item		= null;
		var				selected	= false;
		// Auto Slot Centralizer System ASCS
		if (remainingItemsIn >= 0 && GraphicsConstants.CreativeInventory.AUTO_SLOT_CENTRALIZER_SYSTEM_WITHOUT_ONS)
		{
			if (slotCurrentColumnIn >= GraphicsConstants.CreativeInventory.SLOTS_COLUMNS / 2.0f
					- remainingItemsIn / 2.0f
					&& slotCurrentColumnIn <= GraphicsConstants.CreativeInventory.SLOTS_COLUMNS / 2.0f
							+ remainingItemsIn / 2.0f)
			{
				if (indexIn < this.items.size())
				{
					item = this.items.get(indexIn);
				}
				indexIn++;
			}
			else
			{
				item = null;
			}
			selected = true;
		}

		return HudInventory.AICS_TEMP_TRIPLET.set(item, selected, indexIn);
	}

	public void updateSlots(final int startIn)
	{
		var	i				= startIn;
		var	i0				= startIn;
		var	remainingItems	= -1;

		for (var y = 0; y < this.lines; y++)
		{
			if (this.items.size() - i < GraphicsConstants.CreativeInventory.SLOTS_COLUMNS && remainingItems < 0)
			{
				remainingItems = this.items.size() - i;
			}
			for (var x = 0; x < GraphicsConstants.CreativeInventory.SLOTS_COLUMNS; x++)
			{
				final var a = this.sortedSlots.get(x);
				if (a != null)
				{
					final var slot = a.get(y);

					if (slot != null)
					{
						ItemTypeVariant	item		= null;
						var				selected	= false;
						if (!GraphicsConstants.CreativeInventory.ONLY_NEEDED_SLOTS)
						{
							final var	triplet	= this.autoItemCentralizer(remainingItems, x, i);

							final var	lastI	= i;
							item		= triplet.s1();
							selected	= triplet.s2();
							i			= triplet.s3();

							if (i != lastI)
							{
								i0++;
							}
						}

						if (!selected)
						{
							if (i < this.items.size())
							{
								item = this.items.get(i);
								i0++;
							}
							i++;
						}

						slot.set(item);
					}
				}
			}
		}
	}

	@Override
	public void draw2D(final Shader2DIn3D shader2dIn3DIn)
	{
		super.draw2D(shader2dIn3DIn);
		this.hotBar.draw2D(shader2dIn3DIn);
	}

	@Override
	public void draw3D(final Shader3DTo2D shader3DTo2DIn, final IWindow windowIn)
	{
		super.draw3D(shader3DTo2DIn, windowIn);
		this.hotBar.draw3D(shader3DTo2DIn, windowIn);

		if (this.selected)
		{
			this.hudItem.draw3D(shader3DTo2DIn, windowIn);
		}
	}

	@Override
	public void update(final IWindow windowIn, final InputManager inputManagerIn)
	{
		super.update(windowIn, inputManagerIn);

		this.scrollbar.update(windowIn, inputManagerIn);

		if (this.scrollbar.percent() != this.percent)
		{
			this.percent = this.scrollbar.percent();

			final var i = (int) ((int) (this.scrollbar.percent()
					* (this.items.size() - 1
							- (GraphicsConstants.CreativeInventory.SLOTS_COLUMNS
									* GraphicsConstants.CreativeInventory.SLOTS_LINES
									- GraphicsConstants.CreativeInventory.CURRENT_SCROLLING_LENGTH))
					/ GraphicsConstants.CreativeInventory.CURRENT_SCROLLING_LENGTH)
					* GraphicsConstants.CreativeInventory.CURRENT_SCROLLING_LENGTH);

			if (this.lastI != i)
			{
				this.lastI = i;

				this.updateSlots(i);
			}

		}

		final var quick = inputManagerIn.shortcuts().isEnabled("QUICK_ITEM_MOVING");
		this.last			= this.hudItem;
		this.releaseItem	= false;
		if (this.selected)
		{
			this.hudItem.position().set((float) inputManagerIn.cursor().x(), (float) inputManagerIn.cursor().y());

			if (inputManagerIn.shortcuts().isJustTriggered("USE_CLICK_IN_HUDS") || quick)
			{
				this.selected		= false;
				this.hudItem		= null;
				this.releaseItem	= true;
			}
		}

		this.hotBar.update(windowIn, inputManagerIn);

		for (final var slot : this.slots)
		{
			if (slot != null && slot.isFocus() && slot.hasClicked() && slot.hudItem() != null)
			{
				var success = false;
				if (quick && this.hotBar.putInVoidSlot(new HudItem(
						Positionnable.Builder.of(new Normalizable2f(slot.position()), new Normalizable2f(slot.size())),
						slot.hudItem().itemTypeVariant(), 4.0f)))
				{
					this.selected	= false;
					success			= true;
				}
				if (!success)
				{
					this.hudItem = new HudItem(Positionnable.Builder.of(new Normalizable2f(slot.position()),
							new Normalizable2f(slot.size())), slot.hudItem().itemTypeVariant(), 4.0f);
					this.hudItem.position().set((float) inputManagerIn.cursor().x(),
							(float) inputManagerIn.cursor().y());

					this.selected = true;
				}
			}
		}
	}
}