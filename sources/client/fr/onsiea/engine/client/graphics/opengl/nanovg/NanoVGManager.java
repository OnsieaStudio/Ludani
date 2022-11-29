package fr.onsiea.engine.client.graphics.opengl.nanovg;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;
import org.lwjgl.system.MemoryUtil;

import fr.onsiea.engine.client.graphics.glfw.window.Window;
import fr.onsiea.engine.client.graphics.window.IWindow;

public class NanoVGManager
{
	private long				handle;

	private final NanoVGFonts	nanoVGFonts;

	public NanoVGManager(IWindow windowIn) throws Exception
	{
		this.handle(((Window) windowIn).settings().mustAntialiasing()
				? NanoVGGL3.nvgCreate(NanoVGGL3.NVG_ANTIALIAS | NanoVGGL3.NVG_STENCIL_STROKES)
				: NanoVGGL3.nvgCreate(NanoVGGL3.NVG_STENCIL_STROKES));

		if (this.handle == MemoryUtil.NULL)
		{
			throw new Exception("Could not init nanovg");
		}

		this.nanoVGFonts = new NanoVGFonts(this);
		try
		{
			this.nanoVGFonts.add("ARIAL", "resources/fonts/arial.ttf");
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

	public void startRender(IWindow windowIn)
	{
		NanoVG.nvgBeginFrame(this.handle(), ((Window) windowIn).settings().width(),
				((Window) windowIn).settings().height(), 1);
	}

	public void finishRender()
	{
		NanoVG.nvgEndFrame(this.handle());
	}

	public void letterSpacing(float letterSpacingIn)
	{
		NanoVG.nvgTextLetterSpacing(this.handle(), letterSpacingIn);
	}

	public static NVGColor set(NVGColor colorIn, int rIn, int gIn, int bIn, int aIn)
	{
		colorIn.r(rIn / 255.0f);
		colorIn.g(gIn / 255.0f);
		colorIn.b(bIn / 255.0f);
		colorIn.a(aIn / 255.0f);

		return colorIn;
	}

	public void cleanup()
	{
		NanoVGGL3.nvgDelete(this.handle);
	}

	public final long handle()
	{
		return this.handle;
	}

	private final void handle(long handleIn)
	{
		this.handle = handleIn;
	}

	public final NanoVGFonts nanoVGFonts()
	{
		return this.nanoVGFonts;
	}
}