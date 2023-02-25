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

	public NanoVGManager(final IWindow windowIn) throws Exception
	{
		this.handle(((Window) windowIn).settings().mustAntialiasing()
				? NanoVGGL3.nvgCreate(NanoVGGL3.NVG_ANTIALIAS | NanoVGGL3.NVG_STENCIL_STROKES | NanoVGGL3.NVG_DEBUG)
				: NanoVGGL3.nvgCreate(NanoVGGL3.NVG_STENCIL_STROKES | NanoVGGL3.NVG_DEBUG));

		if (this.handle == MemoryUtil.NULL)
		{
			throw new Exception("Could not init nanovg");
		}

		this.nanoVGFonts = new NanoVGFonts(this);
		try
		{
			this.nanoVGFonts.load("ARIAL", "resources/fonts/arial.ttf");
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

	public void startRender(final IWindow windowIn)
	{
		NanoVG.nvgBeginFrame(this.handle(), ((Window) windowIn).effectiveWidth(), ((Window) windowIn).effectiveHeight(),
				1);
	}

	public void finishRender()
	{
		NanoVG.nvgEndFrame(this.handle());
	}

	public void letterSpacing(final float letterSpacingIn)
	{
		NanoVG.nvgTextLetterSpacing(this.handle(), letterSpacingIn);
	}

	public static NVGColor set(final NVGColor colorIn, final int rIn, final int gIn, final int bIn, final int aIn)
	{
		colorIn.r(rIn / 255.0f);
		colorIn.g(gIn / 255.0f);
		colorIn.b(bIn / 255.0f);
		colorIn.a(aIn / 255.0f);

		return colorIn;
	}

	public void cleanup()
	{
		this.nanoVGFonts().cleanup();
		NanoVGGL3.nvgDelete(this.handle);
	}

	public final long handle()
	{
		return this.handle;
	}

	private final void handle(final long handleIn)
	{
		this.handle = handleIn;
	}

	public final NanoVGFonts nanoVGFonts()
	{
		return this.nanoVGFonts;
	}
}