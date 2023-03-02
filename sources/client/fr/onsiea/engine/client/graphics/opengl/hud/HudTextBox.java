package fr.onsiea.engine.client.graphics.opengl.hud;

import org.lwjgl.nanovg.NVGColor;

import fr.onsiea.engine.client.graphics.opengl.nanovg.NanoVGManager;
import fr.onsiea.engine.client.graphics.window.IWindow;
import fr.onsiea.engine.client.input.InputManager;
import fr.onsiea.engine.client.input.StringRecorder;
import fr.onsiea.engine.utils.time.Timer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
public class HudTextBox
{
	private StringRecorder	recorder;
	private int				fontSize;
	private long			shortcutRate;
	private Timer			shortcutTimer;
	private long			rate;
	private Timer			timer;

	private NVGColor		nvgColor	= NVGColor.malloc();

	public HudTextBox(final IWindow windowIn)
	{
		this.recorder(new StringRecorder());
		this.fontSize(32);
		this.shortcutRate(28L);
		this.shortcutTimer(new Timer());
		this.rate(250L);
		this.timer(new Timer());
	}

	public void input(final InputManager inputManagerIn, final IWindow windowIn)
	{
		/**if (this.shortcutTimer().isTime(7_500_00L))//(long) (1.0D / (this.shortcutRate() * 1D))))
		{
			for (final Key key : inputManagerIn.keyboard().keysAndModsOf(ActionTypes.JUST_PRESSED, ActionTypes.PRESSED,
					ActionTypes.PRESSED_FOR_WHILE, ActionTypes.JUST_REPEATED, ActionTypes.REPEATED,
					ActionTypes.REPEATED_FOR_WHILE))
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
		}**/
		//}
		//if (this.timer().isTime((long) (1.0D / this.rate() * 1_000_000_000D)))
		//{
		/**final var str = new StringBuilder();
		for (final Pair<Integer, Integer> character : inputManagerIn.keyboard().codepointsAndMods())
		{
			str.append(Character.toString(character.one()));
		}
		if (!str.isEmpty())
		{
			this.recorder().record(str.toString());
		}**/

		//}
	}

	public void draw(final IWindow windowIn, final NanoVGManager nanoManagerVGIn)
	{
		/**nanoManagerVGIn.letterSpacing(1.50f);
		nanoManagerVGIn.nanoVGFonts().draw(this.fontSize(), "ARIAL", NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP,
				NanoVGManager.set(this.nvgColor(), 255, 255, 255, 255), 0, 0, this.recorder().contentStr());**/

		/**final var bounds0 = new float[4];
		NanoVG.nvgTextBounds(nanoManagerVGIn.handle(), this.transformations().x(), this.transformations().y(),
				this.recorder().content().substring(0, this.recorder().position()), bounds0);
		
		nanoManagerVGIn.drawText(this.policeSize(), NanoVG.FONT_NAME, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP,
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
		//nanoManagerVGIn.letterSpacing(1.00f);
	}

	public void cleanup()
	{
		this.nvgColor.free();
	}

	public void reset()
	{
		this.recorder().reset();
	}

	public String content()
	{
		return this.recorder().content().toString();
	}
}