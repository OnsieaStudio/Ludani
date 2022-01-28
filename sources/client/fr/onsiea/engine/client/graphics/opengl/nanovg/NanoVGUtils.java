package fr.onsiea.engine.client.graphics.opengl.nanovg;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;
import org.lwjgl.system.MemoryUtil;

import fr.onsiea.engine.client.graphics.glfw.window.Window;
import fr.onsiea.engine.client.graphics.opengl.OpenGLAPI;
import fr.onsiea.engine.core.entity.Camera;
import fr.onsiea.engine.utils.IOUtils;

public class NanoVGUtils
{
	public static final String	FONT_NAME			= "ARIAL";
	public static final float	DEFAULT_FONT_SIZE	= 32.0f;

	private long				handle;

	private ByteBuffer			fontBuffer;

	@SuppressWarnings("unused")
	private final DateFormat	dateFormat			= new SimpleDateFormat("HH:mm:ss");

	private NVGColor			colour;
	private DoubleBuffer		posx;
	private DoubleBuffer		posy;
	private int					counter;

	public NanoVGUtils()
	{

	}

	public NanoVGUtils initialization(Window windowIn) throws Exception
	{
		this.handle(windowIn.settings().mustAntialiasing()
				? NanoVGGL3.nvgCreate(NanoVGGL3.NVG_ANTIALIAS | NanoVGGL3.NVG_STENCIL_STROKES)
				: NanoVGGL3.nvgCreate(NanoVGGL3.NVG_STENCIL_STROKES));

		if (this.handle == MemoryUtil.NULL)
		{
			throw new Exception("Could not init nanovg");
		}

		this.fontBuffer = IOUtils.ioResourceToByteBuffer("resources\\fonts\\arial.ttf", 150 * 1024);
		final var font = NanoVG.nvgCreateFontMem(this.handle(), NanoVGUtils.FONT_NAME, this.fontBuffer, 0);
		if (font == -1)
		{
			throw new Exception("Could not add font");
		}

		this.colour		= NVGColor.create();

		this.posx		= MemoryUtil.memAllocDouble(1);
		this.posy		= MemoryUtil.memAllocDouble(1);

		this.counter	= 0;

		return this;
	}

	public void startRender(Window windowIn)
	{
		NanoVG.nvgBeginFrame(this.handle(), windowIn.settings().width(), windowIn.settings().height(), 1);
	}

	public void render(Camera cameraIn, int framerateIn, Window windowIn)
	{
		// Upper ribbon
		NanoVG.nvgBeginPath(this.handle());
		NanoVG.nvgRect(this.handle(), 0, windowIn.settings().height() - 100, windowIn.settings().width(), 50);
		NanoVG.nvgFillColor(this.handle(), this.rgba(0x23, 0xa1, 0xf1, 200, this.colour));
		NanoVG.nvgFill(this.handle());

		// Lower ribbon
		NanoVG.nvgBeginPath(this.handle());
		NanoVG.nvgRect(this.handle(), 0, windowIn.settings().height() - 50, windowIn.settings().width(), 10);
		NanoVG.nvgFillColor(this.handle(), this.rgba(0xc1, 0xe3, 0xf9, 200, this.colour));
		NanoVG.nvgFill(this.handle());

		//final var	hover	= Math.pow(x - xcenter, 2) + Math.pow(y - ycenter, 2) < Math.pow(radius, 2);

		// Circle
		/**NanoVG.nvgBeginPath(this.handle());
		NanoVG.nvgCircle(this.handle(), xcenter, ycenter, radius);
		NanoVG.nvgFillColor(this.handle(), this.rgba(0xc1, 0xe3, 0xf9, 200, this.colour));
		NanoVG.nvgFill(this.handle());**/

		// Render hour text
		/**NanoVG.nvgFontSize(this.handle(), 40.0f);
		NanoVG.nvgFontFace(this.handle(), NanoVGUtils.FONT_NAME);
		NanoVG.nvgTextAlign(this.handle(), NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP);
		NanoVG.nvgFillColor(this.handle(), this.rgba(0xe6, 0xea, 0xed, 255, this.colour));
		NanoVG.nvgText(this.handle(), windowIn.width() - 150, windowIn.height() - 95,
				this.dateFormat.format(new Date()));**/

		this.incCounter();
	}

	public void finishRender()
	{
		NanoVG.nvgEndFrame(this.handle());

		// Restore state

		OpenGLAPI.restoreState();
	}

	public void letterSpacing(float letterSpacingIn)
	{
		NanoVG.nvgTextLetterSpacing(this.handle(), letterSpacingIn);
	}

	public void drawText(float fontSizeIn, String fontNameIn, int alignsIn, float xIn, float yIn, int rIn, int gIn,
			int bIn, int aIn, String contentIn)
	{
		NanoVG.nvgFontSize(this.handle(), fontSizeIn);
		NanoVG.nvgFontFace(this.handle(), fontNameIn);
		NanoVG.nvgTextAlign(this.handle(), alignsIn);
		NanoVG.nvgFillColor(this.handle(), this.rgba(rIn, gIn, bIn, aIn, this.colour));
		NanoVG.nvgText(this.handle(), xIn, yIn, contentIn);
	}

	public void drawText(float fontSizeIn, int alignsIn, float xIn, float yIn, int rIn, int gIn, int bIn, int aIn,
			String contentIn)
	{
		NanoVG.nvgFontSize(this.handle(), fontSizeIn);
		NanoVG.nvgFontFace(this.handle(), NanoVGUtils.FONT_NAME);
		NanoVG.nvgTextAlign(this.handle(), alignsIn);
		NanoVG.nvgFillColor(this.handle(), this.rgba(rIn, gIn, bIn, aIn, this.colour));
		NanoVG.nvgText(this.handle(), xIn, yIn, contentIn);
	}

	public void drawText(float fontSizeIn, float xIn, float yIn, int rIn, int gIn, int bIn, int aIn, String contentIn)
	{
		NanoVG.nvgFontSize(this.handle(), fontSizeIn);
		NanoVG.nvgFontFace(this.handle(), NanoVGUtils.FONT_NAME);
		NanoVG.nvgTextAlign(this.handle(), NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP);
		NanoVG.nvgFillColor(this.handle(), this.rgba(rIn, gIn, bIn, aIn, this.colour));
		NanoVG.nvgText(this.handle(), xIn, yIn, contentIn);
	}

	public void drawText(float xIn, float yIn, int rIn, int gIn, int bIn, int aIn, String contentIn)
	{
		NanoVG.nvgFontSize(this.handle(), NanoVGUtils.DEFAULT_FONT_SIZE);
		NanoVG.nvgFontFace(this.handle(), NanoVGUtils.FONT_NAME);
		NanoVG.nvgTextAlign(this.handle(), NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP);
		NanoVG.nvgFillColor(this.handle(), this.rgba(rIn, gIn, bIn, aIn, this.colour));
		NanoVG.nvgText(this.handle(), xIn, yIn, contentIn);
	}

	public void drawText(float xIn, float yIn, int rIn, int gIn, int bIn, String contentIn)
	{
		NanoVG.nvgFontSize(this.handle(), NanoVGUtils.DEFAULT_FONT_SIZE);
		NanoVG.nvgFontFace(this.handle(), NanoVGUtils.FONT_NAME);
		NanoVG.nvgTextAlign(this.handle(), NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP);
		NanoVG.nvgFillColor(this.handle(), this.rgba(rIn, gIn, bIn, 255, this.colour));
		NanoVG.nvgText(this.handle(), xIn, yIn, contentIn);
	}

	public void drawText(float xIn, float yIn, String contentIn)
	{
		NanoVG.nvgFontSize(this.handle(), NanoVGUtils.DEFAULT_FONT_SIZE);
		NanoVG.nvgFontFace(this.handle(), NanoVGUtils.FONT_NAME);
		NanoVG.nvgTextAlign(this.handle(), NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP);
		NanoVG.nvgFillColor(this.handle(), this.rgba(255, 255, 255, 255, this.colour));
		NanoVG.nvgText(this.handle(), xIn, yIn, contentIn);
	}

	public void incCounter()
	{
		this.counter++;
		if (this.counter > 99)
		{
			this.counter = 0;
		}
	}

	public NVGColor rgba(int r, int g, int b, int a)
	{
		this.colour.r(r / 255.0f);
		this.colour.g(g / 255.0f);
		this.colour.b(b / 255.0f);
		this.colour.a(a / 255.0f);

		return this.colour;
	}

	private NVGColor rgba(int r, int g, int b, int a, NVGColor colour)
	{
		colour.r(r / 255.0f);
		colour.g(g / 255.0f);
		colour.b(b / 255.0f);
		colour.a(a / 255.0f);

		return colour;
	}

	public void cleanup()
	{
		if (this.fontBuffer != null)
		{
			MemoryUtil.memFree(this.fontBuffer);
		}
		NanoVGGL3.nvgDelete(this.handle);
		if (this.posx != null)
		{
			MemoryUtil.memFree(this.posx);
		}
		if (this.posy != null)
		{
			MemoryUtil.memFree(this.posy);
		}
	}

	public long handle()
	{
		return this.handle;
	}

	private void handle(long handleIn)
	{
		this.handle = handleIn;
	}
}