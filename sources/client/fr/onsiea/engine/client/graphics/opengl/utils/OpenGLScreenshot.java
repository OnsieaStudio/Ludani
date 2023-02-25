package fr.onsiea.engine.client.graphics.opengl.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import fr.onsiea.engine.client.graphics.glfw.window.Window;
import fr.onsiea.engine.client.graphics.texture.Texture;
import fr.onsiea.engine.client.graphics.texture.TexturesManager;
import fr.onsiea.engine.utils.time.DateUtils;

/**
 * @author seyro
 *
 */
public class OpenGLScreenshot
{
	private static ByteBuffer			pixels;
	private static BufferedImage		bufferedImage;
	private final static int			BPP	= 4;
	private static TexturesManager<?>	texturesManager;

	public final static void initialize(TexturesManager<?> texturesManagerIn)
	{
		OpenGLScreenshot.texturesManager = texturesManagerIn;
	}

	public final static void take(final int widthIn, final int heightIn)
	{
		GL11.glReadBuffer(GL11.GL_FRONT);
		final var	width	= widthIn;
		final var	height	= heightIn;
		final var	bpp		= 4;													// Assuming a 32-bit display
		// with a byte each for red,
		// green, blue, and alpha.
		final var	buffer	= BufferUtils.createByteBuffer(width * height * bpp);
		GL11.glReadPixels(0, 0, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

		final var	dtf		= DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
		final var	now		= LocalDateTime.now();
		final var	date	= dtf.format(now);

		final var	file	= new File("gamedata/screenshots/screenshot_" + date + ".png");	// The file to
		// save to.
		file.getParentFile().mkdirs();
		final var	format	= "PNG";														// Example: "PNG" or
		// "JPG"
		final var	image	= new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		for (var x = 0; x < width; x++)
		{
			for (var y = 0; y < height; y++)
			{
				final var	i	= (x + width * y) * bpp;
				final var	r	= buffer.get(i) & 0xFF;
				final var	g	= buffer.get(i + 1) & 0xFF;
				final var	b	= buffer.get(i + 2) & 0xFF;
				image.setRGB(x, height - (y + 1), 0xFF << 24 | r << 16 | g << 8 | b);
			}
		}

		try
		{
			ImageIO.write(image, format, file);
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
	}

	public final static void resize(final Window windowIn)
	{
		OpenGLScreenshot.pixels(MemoryUtil
				.memAlloc(windowIn.effectiveWidth() * windowIn.effectiveHeight() * OpenGLScreenshot.bpp()));
		OpenGLScreenshot.bufferedImage(new BufferedImage(windowIn.effectiveWidth(), windowIn.effectiveHeight(),
				BufferedImage.TYPE_INT_RGB));
	}

	public final static ByteBuffer pixels(final Window windowIn)
	{
		GL11.glReadBuffer(GL11.GL_FRONT);

		OpenGLScreenshot.pixels().clear();

		GL11.glReadPixels(0, 0, windowIn.effectiveWidth(), windowIn.effectiveHeight(), GL11.GL_RGBA,
				GL11.GL_UNSIGNED_BYTE, OpenGLScreenshot.pixels());

		return OpenGLScreenshot.pixels();
	}

	public final static Texture<?> intoTexture(final Window windowIn)
	{
		return OpenGLScreenshot.texturesManager.load(DateUtils.getDate(), OpenGLScreenshot.pixels(windowIn),
				windowIn.effectiveWidth(), windowIn.effectiveHeight(), null);
	}

	public final static Texture<?> intoTexture(final ByteBuffer pixelsIn, final Window windowIn)
	{
		return OpenGLScreenshot.texturesManager.load(DateUtils.getDate(), pixelsIn, windowIn.effectiveWidth(),
				windowIn.effectiveHeight(), null);
	}

	public static boolean write(final BufferedImage bufferedImageIn)
	{
		final var saveDirectory = new File("resources/screenshots/");

		if (!saveDirectory.exists() && !saveDirectory.mkdir())
		{
			System.err.println("The screenshot directory could not be created.");
		}

		final var	file	= new File(saveDirectory + "/" + DateUtils.getDate() + ".png");	// The file to save the pixels
		// too.
		final var	format	= "png";														// "PNG" or "JPG".

		// Tries to create image.
		try
		{
			ImageIO.write(bufferedImageIn, format, file);
		}
		catch (final Exception e)
		{
			e.printStackTrace();

			return false;
		}

		return true;
	}

	public static BufferedImage image(final Window windowIn)
	{
		final var pixels = OpenGLScreenshot.pixels(windowIn);

		for (var x = OpenGLScreenshot.bufferedImage().getWidth() - 1; x >= 0; x--)
		{
			for (var y = OpenGLScreenshot.bufferedImage().getHeight() - 1; y >= 0; y--)
			{
				final var i = (x + windowIn.effectiveWidth() * y) * OpenGLScreenshot.bpp();
				OpenGLScreenshot.bufferedImage().setRGB(x, OpenGLScreenshot.bufferedImage().getHeight() - 1 - y,
						(pixels.get(i) & 0xFF & 0x0ff) << 16 | (pixels.get(i + 1) & 0xFF & 0x0ff) << 8
								| pixels.get(i + 2) & 0xFF & 0x0ff);
			}
		}

		return OpenGLScreenshot.bufferedImage();
	}

	public static BufferedImage image(final ByteBuffer pixelsIn, final Window windowIn)
	{
		final var bufferedImage = new BufferedImage(windowIn.effectiveWidth(), windowIn.effectiveHeight(),
				BufferedImage.TYPE_INT_RGB);

		for (var x = bufferedImage.getWidth() - 1; x >= 0; x--)
		{
			for (var y = bufferedImage.getHeight() - 1; y >= 0; y--)
			{
				final var i = (x + windowIn.effectiveWidth() * y) * OpenGLScreenshot.bpp();
				bufferedImage.setRGB(x, bufferedImage.getHeight() - 1 - y, (pixelsIn.get(i) & 0xFF & 0x0ff) << 16
						| (pixelsIn.get(i + 1) & 0xFF & 0x0ff) << 8 | pixelsIn.get(i + 2) & 0xFF & 0x0ff);
			}
		}

		return bufferedImage;
	}

	public final static void cleanup()
	{
		if (OpenGLScreenshot.pixels() != null)
		{
			MemoryUtil.memFree(OpenGLScreenshot.pixels());
		}
		OpenGLScreenshot.bufferedImage(null);
	}

	private static final ByteBuffer pixels()
	{
		return OpenGLScreenshot.pixels;
	}

	private static final void pixels(ByteBuffer pixelsIn)
	{
		OpenGLScreenshot.pixels = pixelsIn;
	}

	private static final BufferedImage bufferedImage()
	{
		return OpenGLScreenshot.bufferedImage;
	}

	private static final void bufferedImage(BufferedImage bufferedImageIn)
	{
		OpenGLScreenshot.bufferedImage = bufferedImageIn;
	}

	private static final int bpp()
	{
		return OpenGLScreenshot.BPP;
	}

	static class ScreenshotWriter implements Runnable
	{
		private ByteBuffer	byteBuffer;
		private int			width;
		private int			height;

		public ScreenshotWriter()
		{
		}

		public void init(final ByteBuffer byteBufferIn, final int widthIn, final int heightIn)
		{
			this.setByteBuffer(byteBufferIn);
			this.setWidth(widthIn);
			this.setHeight(heightIn);
		}

		@Override
		public void run()
		{
			var file = new File("gameData/screenshots/");
			if (!file.exists())
			{
				file.mkdirs();
			}
			final DateFormat	dateFormat	= new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
			final var			date		= new Date();

			file = new File("gameData/screenshots/" + dateFormat.format(date) + ".png"); // The file to save
			// to.
			final var	format	= "png";																			// Example:
			// "PNG"
			// or
			// "JPG"
			final var	image	= new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);

			for (var x = 0; x < this.getWidth(); x++)
			{
				for (var y = 0; y < this.getHeight(); y++)
				{
					final var	i	= (x + this.getWidth() * y) * OpenGLScreenshot.BPP;
					final var	r	= this.getByteBuffer().get(i) & 0xFF;
					final var	g	= this.getByteBuffer().get(i + 1) & 0xFF;
					final var	b	= this.getByteBuffer().get(i + 2) & 0xFF;
					image.setRGB(x, this.getHeight() - (y + 1), 0xFF << 24 | r << 16 | g << 8 | b);
				}
			}

			try
			{
				ImageIO.write(image, format, file);
			}
			catch (final IOException e)
			{
				e.printStackTrace();
			}
		}

		/**
		 * @return the byteBuffer
		 */
		private ByteBuffer getByteBuffer()
		{
			return this.byteBuffer;
		}

		/**
		 * @param byteBufferIn the byteBuffer to set
		 */
		private void setByteBuffer(final ByteBuffer byteBufferIn)
		{
			this.byteBuffer = byteBufferIn;
		}

		/**
		 * @return the width
		 */
		private int getWidth()
		{
			return this.width;
		}

		/**
		 * @param widthIn the width to set
		 */
		private void setWidth(final int widthIn)
		{
			this.width = widthIn;
		}

		/**
		 * @return the height
		 */
		private int getHeight()
		{
			return this.height;
		}

		/**
		 * @param heightIn the height to set
		 */
		private void setHeight(final int heightIn)
		{
			this.height = heightIn;
		}
	}
}