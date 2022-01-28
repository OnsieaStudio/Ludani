package fr.onsiea.engine.client.graphics.opengl.hud.component;

import javax.swing.Renderer;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NanoVG;

import fr.onsiea.engine.client.graphics.glfw.window.Window;
import fr.onsiea.engine.client.graphics.opengl.hud.IHudComponent;
import fr.onsiea.engine.client.graphics.opengl.nanovg.NanoVGUtils;
import fr.onsiea.engine.utils.time.Timer;
import fr.seynax.onsiea.game.entity.camera.Camera;
import fr.seynax.onsiea.graphics.opengl.mesh.GLMesh;
import fr.seynax.onsiea.graphics.opengl.shader.hud.HudShader;
import fr.seynax.onsiea.input.ActionTypes;
import fr.seynax.onsiea.input.InputManager;
import fr.seynax.onsiea.input.UserInputTextRecorder;
import fr.seynax.onsiea.utils.Pair;

public class HudTextBox implements IHudComponent
{
	private Vector2f				transformations;

	private UserInputTextRecorder	userInputTextRecorder;
	private float					policeSize;
	private long					shortcutRate;
	private Timer					shortcutTimer;
	private long					rate;
	private Timer					timer;

	public HudTextBox()
	{
		this.transformations(new Vector2f());
	}

	public HudTextBox(float xIn, float yIn)
	{
		this.transformations(new Vector2f(xIn, yIn));
	}

	@Override
	public void initialization(Window windowIn, Camera cameraIn)
	{
		this.userInputTextRecorder(new UserInputTextRecorder());
		this.policeSize(32.0f);
		this.shortcutRate(28L);
		this.shortcutTimer(new Timer());
		this.rate(250L);
		this.timer(new Timer());
	}

	@Override
	public void input(InputManager inputManagerIn, Window windowIn)
	{
		if (this.shortcutTimer().isTime((long) (1.0D / this.shortcutRate() * 1_000_000_000D)))
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
							this.userInputTextRecorder().selectLeft();
						}
						else if (key.mods() == GLFW.GLFW_MOD_CONTROL)
						{
							this.userInputTextRecorder().extendSelectionInLeft();
						}
						/**else if (modsIn == GLFW.GLFW_MOD_CONTROL)
						{
							this.userInputTextRecorder().leftWord();
						}**/
						else
						{
							this.userInputTextRecorder().left();
						}

						break;

					case GLFW.GLFW_KEY_RIGHT:
						if (key.mods() == GLFW.GLFW_MOD_SHIFT)
						{
							this.userInputTextRecorder().selectRight();
						}
						else if (key.mods() == GLFW.GLFW_MOD_CONTROL)
						{
							this.userInputTextRecorder().extendSelectionInRight();
						}
						/**else if (modsIn == GLFW.GLFW_MOD_CONTROL)
						{
							this.userInputTextRecorder().rightWord();
						}**/
						else
						{
							this.userInputTextRecorder().right();
						}

						break;

					case GLFW.GLFW_KEY_BACKSPACE:
						this.userInputTextRecorder().backspace();

						break;

					case GLFW.GLFW_KEY_DELETE:
						this.userInputTextRecorder().delete();

						break;

					case GLFW.GLFW_KEY_INSERT:

						this.userInputTextRecorder().insert();

						break;

					case GLFW.GLFW_KEY_TAB:

						this.userInputTextRecorder().addTabulation();

						break;

					case GLFW.GLFW_KEY_Q:
						if (key.mods() == GLFW.GLFW_MOD_CONTROL)
						{
							this.userInputTextRecorder().selectAll();
						}

						break;

					case GLFW.GLFW_KEY_V:
						if (key.mods() == GLFW.GLFW_MOD_CONTROL)
						{
							final var clipboard = inputManagerIn.getClipboardString();
							this.userInputTextRecorder().record(clipboard);
						}

						break;

					case GLFW.GLFW_KEY_C:
						if (key.mods() == GLFW.GLFW_MOD_CONTROL)
						{
							final var content = this.userInputTextRecorder().copy();
							if (content != null)
							{
								inputManagerIn.setClipboardString(content);
							}
						}

						break;

					case GLFW.GLFW_KEY_X:
						if (key.mods() == GLFW.GLFW_MOD_CONTROL)
						{
							final var content = this.userInputTextRecorder().cut();
							if (content != null)
							{
								inputManagerIn.setClipboardString(content);
							}
						}

						break;
				}
			}
		}
		if (this.timer().isTime((long) (1.0D / this.rate() * 1_000_000_000D)))
		{
			final var str = new StringBuilder();
			for (final Pair<Integer, Integer> character : inputManagerIn.keyboard().codepointsAndMods())
			{
				str.append(Character.toString(character.one()));
			}
			if (!str.isEmpty())
			{
				this.userInputTextRecorder().record(str.toString());
			}
		}
	}

	@Override
	public void update(Window windowIn, Camera cameraIn)
	{

	}

	@Override
	public void draw(Window windowIn, Camera cameraIn, HudShader hudShaderIn, GLMesh rectangleIn, Renderer rendererIn)
	{

	}

	@Override
	public void draw(Window windowIn, Camera cameraIn, HudShader hudShaderIn, InputManager inputManagerIn,
			NanoVGUtils nanoVGUtilsIn)
	{
		nanoVGUtilsIn.letterSpacing(1.50f);
		nanoVGUtilsIn.drawText(this.policeSize(), NanoVGUtils.FONT_NAME, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP,
				this.transformations().x(), this.transformations().y(), 255, 255, 255, 255,
				this.userInputTextRecorder().contentStr());

		final var bounds0 = new float[4];
		NanoVG.nvgTextBounds(nanoVGUtilsIn.handle(), this.transformations().x(), this.transformations().y(),
				this.userInputTextRecorder().content().substring(0, this.userInputTextRecorder().position()), bounds0);

		nanoVGUtilsIn.drawText(this.policeSize(), NanoVGUtils.FONT_NAME, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP,
				bounds0[2] - this.transformations().x(), this.transformations().y(), 255, 255, 255, 255,
				inputManagerIn.keyboard().isTextCursorBlinking() ? "|" : " ");

		if (this.userInputTextRecorder().isSelected())
		{
			final var selectionStr = this.userInputTextRecorder().selectionStr();
			if (selectionStr != null)
			{
				final var bounds1 = new float[4];
				NanoVG.nvgTextBounds(nanoVGUtilsIn.handle(), this.transformations().x(), this.transformations().y(),
						this.userInputTextRecorder().content().substring(0,
								this.userInputTextRecorder().selection().x()),
						bounds1);
				final var bounds2 = new float[4];

				NanoVG.nvgTextBounds(nanoVGUtilsIn.handle(), this.transformations().x(), this.transformations().y(),
						selectionStr, bounds2);

				NanoVG.nvgBeginPath(nanoVGUtilsIn.handle());
				NanoVG.nvgRect(nanoVGUtilsIn.handle(), bounds1[2], bounds2[1], bounds2[2], bounds2[3] - bounds2[1]);
				NanoVG.nvgFillColor(nanoVGUtilsIn.handle(), nanoVGUtilsIn.rgba(50, 100, 128, 200));
				NanoVG.nvgFill(nanoVGUtilsIn.handle());
			}
		}
		nanoVGUtilsIn.letterSpacing(1.00f);
	}

	@Override
	public void cleanup()
	{

	}

	public void reset()
	{
		this.userInputTextRecorder().reset();
	}

	public String content()
	{
		return this.userInputTextRecorder().content().toString();
	}

	public Vector2f transformations()
	{
		return this.transformations;
	}

	public void transformations(Vector2f transformationsIn)
	{
		this.transformations = transformationsIn;
	}

	private UserInputTextRecorder userInputTextRecorder()
	{
		return this.userInputTextRecorder;
	}

	private void userInputTextRecorder(UserInputTextRecorder userInputTextRecorderIn)
	{
		this.userInputTextRecorder = userInputTextRecorderIn;
	}

	private float policeSize()
	{
		return this.policeSize;
	}

	private void policeSize(float policeSizeIn)
	{
		this.policeSize = policeSizeIn;
	}

	private long shortcutRate()
	{
		return this.shortcutRate;
	}

	private void shortcutRate(long shortcutRateIn)
	{
		this.shortcutRate = shortcutRateIn;
	}

	private Timer shortcutTimer()
	{
		return this.shortcutTimer;
	}

	private void shortcutTimer(Timer shortcutTimerIn)
	{
		this.shortcutTimer = shortcutTimerIn;
	}

	private long rate()
	{
		return this.rate;
	}

	private void rate(long rateIn)
	{
		this.rate = rateIn;
	}

	private Timer timer()
	{
		return this.timer;
	}

	private void timer(Timer timerIn)
	{
		this.timer = timerIn;
	}
}