package fr.onsiea.engine.client.graphics.opengl.hud.components.textbox;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;

import fr.onsiea.engine.client.graphics.opengl.hud.components.HudComponent;
import fr.onsiea.engine.client.graphics.opengl.hud.components.IHudComponent;
import fr.onsiea.engine.client.graphics.opengl.nanovg.NanoVGManager;
import fr.onsiea.engine.client.graphics.opengl.shaders.Shader2DIn3D;
import fr.onsiea.engine.client.graphics.window.IWindow;
import fr.onsiea.engine.client.input.InputManager;
import fr.onsiea.engine.client.input.StringRecorder;
import fr.onsiea.engine.utils.positionnable.IPositionnable;
import fr.onsiea.engine.utils.time.Timer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
public class HudTextBox extends HudComponent
{
	private StringRecorder	recorder;
	private int				fontSize;
	private long			shortcutRate;
	private Timer			shortcutTimer;
	private long			rate;
	private Timer			timer;
	private NanoVGManager	nanoVGManager;
	private NVGColor		nvgColor	= NVGColor.malloc();
	private IWindow			window;

	public HudTextBox(final IPositionnable positionnableIn, final IWindow windowIn, final NanoVGManager nanoVGManagerIn)
	{
		super(positionnableIn);
		this.recorder(new StringRecorder());
		this.fontSize(32);
		this.shortcutRate(28L);
		this.shortcutTimer(new Timer());
		this.rate(250L);
		this.timer(new Timer());
		this.nanoVGManager	= nanoVGManagerIn;
		this.window			= windowIn;
	}

	public void input(final InputManager inputManagerIn, final IWindow windowIn)
	{
		/**if (this.shortcutTimer().isTime(7_500_00L))//(long) (1.0D / (this.shortcutRate() * 1D))))
		{
			for (final KeyAction key : inputManagerIn.keyboard().keysAndModsOf(EnumActionType.JUST_PRESSED,
					EnumActionType.PRESSED, EnumActionType.PRESSED_FOR_WHILE, EnumActionType.JUST_REPEATED,
					EnumActionType.REPEATED, EnumActionType.REPEATED_FOR_WHILE))
			{
				switch (key.id())
				{
					case GLFW.GLFW_KEY_LEFT:
						if (key.mods() == GLFW.GLFW_MOD_SHIFT)
						{
							this.recorder().selectLeft();
						}
						else if (key.mods() == GLFW.GLFW_MOD_CONTROL)
						{
							this.recorder().extendSelectionInLeft();
						}
						/**else if (modsIn == GLFW.GLFW_MOD_CONTROL)
						{
							this.recorder().leftWord();
						}**/
		/**else
		{
			this.recorder().left();
		}

		break;

		case GLFW.GLFW_KEY_RIGHT:
		if (key.mods() == GLFW.GLFW_MOD_SHIFT)
		{
			this.recorder().selectRight();
		}
		else if (key.mods() == GLFW.GLFW_MOD_CONTROL)
		{
			this.recorder().extendSelectionInRight();
		}
		/**else if (modsIn == GLFW.GLFW_MOD_CONTROL)
		{
			this.recorder().rightWord();
		}**/
		/**else
		{
			this.recorder().right();
		}

		break;

		case GLFW.GLFW_KEY_BACKSPACE:
		this.recorder().backspace();

		break;

		case GLFW.GLFW_KEY_DELETE:
		this.recorder().delete();

		break;

		case GLFW.GLFW_KEY_INSERT:

		this.recorder().insert();

		break;

		case GLFW.GLFW_KEY_TAB:

		this.recorder().addTabulation();

		break;

		case GLFW.GLFW_KEY_Q:
		if (key.mods() == GLFW.GLFW_MOD_CONTROL)
		{
			this.recorder().selectAll();
		}

		break;

		case GLFW.GLFW_KEY_V:
		if (key.mods() == GLFW.GLFW_MOD_CONTROL)
		{
			final var clipboard = inputManagerIn.getClipboardString();
			this.recorder().record(clipboard);
		}

		break;

		case GLFW.GLFW_KEY_C:
		if (key.mods() == GLFW.GLFW_MOD_CONTROL)
		{
			final var content = this.recorder().copy();
			if (content != null)
			{
				inputManagerIn.setClipboardString(content);
			}
		}

		break;

		case GLFW.GLFW_KEY_X:
		if (key.mods() == GLFW.GLFW_MOD_CONTROL)
		{
			final var content = this.recorder().cut();
			if (content != null)
			{
				inputManagerIn.setClipboardString(content);
			}
		}

		break;
		}
		}
		}
		//if (this.timer().isTime((long) (1.0D / this.rate() * 1_000_000_000D)))
		//{
		final var str = new StringBuilder();
		for (final Pair<Integer, Integer> character : inputManagerIn.keyboard().codepointsAndMods())
		{
		str.append(Character.toString(character.s1()));
		}
		if (!str.isEmpty())
		{
		this.recorder().record(str.toString());
		}
		//}**/
	}

	@Override
	public IHudComponent draw2D(final Shader2DIn3D shader2dIn3DIn)
	{
		this.nanoVGManager.nanoVGFonts().addTextOrChange("creativeHudTextBox", this.fontSize(), "ARIAL",
				NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP, 1.0f, 255, 255, 255, 255, 0, 0, "abvdsoubviubsdisdvb");

		/**final var bounds0 = new float[4];
		NanoVG.nvgTextBounds(nanoManagerVGIn.handle(), this.transformations().x(), this.transformations().y(),
				this.recorder().content().substring(0, this.recorder().position()), bounds0);

		nanoVGManager.nanoVGFonts().draw(this.policeSize(), NanoVG.FONT_NAME, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP,
				bounds0[2] - this.transformations().x(), this.transformations().y(), 255, 255, 255, 255,
				inputManagerIn.keyboard().isTextCursorBlinking() ? "|" : " ");

		if (this.recorder().isSelected())
		{
			final var selectionStr = this.recorder().selectionStr();
			if (selectionStr != null)
			{
				final var bounds1 = new float[4];
				NanoVG.nvgTextBounds(nanoManagerVGIn.handle(), this.transformations().x(), this.transformations().y(),
						this.recorder().content().substring(0, this.recorder().selection().x()), bounds1);
				final var bounds2 = new float[4];

				NanoVG.nvgTextBounds(nanoManagerVGIn.handle(), this.transformations().x(), this.transformations().y(),
						selectionStr, bounds2);

				NanoVG.nvgBeginPath(nanoManagerVGIn.handle());
				NanoVG.nvgRect(nanoManagerVGIn.handle(), bounds1[2], bounds2[1], bounds2[2], bounds2[3] - bounds2[1]);
				NanoVG.nvgFillColor(nanoManagerVGIn.handle(), nanoManagerVGIn.rgba(50, 100, 128, 200));
				NanoVG.nvgFill(nanoManagerVGIn.handle());
			}
		}**/
		this.nanoVGManager.letterSpacing(1.00f);

		return this;
	}

	@Override
	public IHudComponent cleanup()
	{
		if (this.nvgColor != null)
		{
			this.nvgColor.free();
		}

		return this;
	}

	public void reset()
	{
		//this.recorder().reset();
	}

	public String content()
	{
		return this.recorder().content().toString();
	}
}