/**
 * 
 */
package fr.onsiea.engine.client.graphics.glfw.window;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

import fr.onsiea.engine.client.graphics.glfw.GLFWUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Seynax
 *
 */

@EqualsAndHashCode
@ToString
@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PRIVATE)
@Builder(builderClassName = "WindowSettingsBuilder", buildMethodName =  "create")
@AllArgsConstructor
public class WindowSettings
{
	private String			title;
	private int				width;
	private int				height;
	private int				refreshRate;
	private int				sync;

	private WindowShowType	windowShowType;

	private boolean			mustBeSynchronized;
	private boolean			mustBeResizable;
	private boolean			mustBeDecorated;
	private boolean			mustBeFocused;
	private boolean			mustIconify;
	private boolean			mustFloating;
	private boolean			mustMaximized;
	private boolean			mustCenterCursor;
	private boolean			hasTransparentFramebuffer;
	private boolean			mustFocusOnShow;
	private boolean			mustScaleToMonitor;
	private boolean			mustAntialiasing;
	private boolean			mustAuxBuffers;
	private boolean			mustStereo;
	private boolean			mustSRGBCapable;
	private boolean			mustDoubleBuffering;

	final void hints()
	{
		GLFWUtils.boolHint(GLFW.GLFW_FOCUSED, this.mustBeFocused());
		GLFWUtils.boolHint(GLFW.GLFW_AUTO_ICONIFY, this.mustIconify());
		GLFWUtils.boolHint(GLFW.GLFW_FLOATING, this.mustFloating());
		GLFWUtils.boolHint(GLFW.GLFW_MAXIMIZED, this.mustMaximized());
		GLFWUtils.boolHint(GLFW.GLFW_CENTER_CURSOR, this.mustCenterCursor());
		GLFWUtils.boolHint(GLFW.GLFW_TRANSPARENT_FRAMEBUFFER, this.hasTransparentFramebuffer());
		GLFWUtils.boolHint(GLFW.GLFW_FOCUS_ON_SHOW, this.mustFocusOnShow());
		GLFWUtils.boolHint(GLFW.GLFW_SCALE_TO_MONITOR, this.mustScaleToMonitor());
		GLFWUtils.boolHint(GLFW.GLFW_AUX_BUFFERS, this.mustAuxBuffers());
		GLFWUtils.boolHint(GLFW.GLFW_STEREO, this.mustStereo());
		GLFWUtils.boolHint(GLFW.GLFW_SRGB_CAPABLE, this.mustSRGBCapable());
		//GLFWUtils.boolHint(GLFW.GLFW_DOUBLEBUFFER, this.mustDoubleBuffering());

		if (this.mustAntialiasing())
		{
			GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, 8);
		}
	}

	final void defaultFramebufferHints()
	{
		//GLFW.glfwWindowHint(GLFW.GLFW_RED_BITS, 0);
		//GLFW.glfwWindowHint(GLFW.GLFW_GREEN_BITS, 0);
		//GLFW.glfwWindowHint(GLFW.GLFW_BLUE_BITS, 0);
		//GLFW.glfwWindowHint(GLFW.GLFW_ALPHA_BITS, 0);
		//GLFW.glfwWindowHint(GLFW.GLFW_DEPTH_BITS, 0);
		//GLFW.glfwWindowHint(GLFW.GLFW_STENCIL_BITS, 0);
		//GLFW.glfwWindowHint(GLFW.GLFW_ACCUM_RED_BITS, 0);
		//GLFW.glfwWindowHint(GLFW.GLFW_ACCUM_GREEN_BITS, 0);
		//GLFW.glfwWindowHint(GLFW.GLFW_ACCUM_BLUE_BITS, 0);
		//GLFW.glfwWindowHint(GLFW.GLFW_ACCUM_ALPHA_BITS, 0);

		if (this.refreshRate() > 0)
		{
			GLFW.glfwWindowHint(GLFW.GLFW_REFRESH_RATE, this.refreshRate());
		}
	}

	// Work in progress
	final void framebufferHints()
	{
		this.defaultFramebufferHints();
	}

	final void framebufferHints(GLFWVidMode modeIn)
	{
		GLFW.glfwWindowHint(GLFW.GLFW_RED_BITS, modeIn.redBits());
		GLFW.glfwWindowHint(GLFW.GLFW_GREEN_BITS, modeIn.greenBits());
		GLFW.glfwWindowHint(GLFW.GLFW_BLUE_BITS, modeIn.blueBits());
		GLFW.glfwWindowHint(GLFW.GLFW_REFRESH_RATE, modeIn.refreshRate());
	}

	public static class WindowSettingsBuilder
	{
	}

	public final static class Builder extends WindowSettingsBuilder
	{
		public static WindowSettings of(String titleIn, int widthIn, int heightIn) throws Exception
		{
			return new Builder().set(titleIn, widthIn, heightIn, -1, WindowShowType.WINDOWED).build();
		}

		public static WindowSettings of(String titleIn, int widthIn, int heightIn, int refreshRateIn,
				WindowShowType windowShowTypeIn) throws Exception
		{
			return new Builder().set(titleIn, widthIn, heightIn, refreshRateIn, windowShowTypeIn).build();
		}

		public static Builder base(String titleIn, int widthIn, int heightIn)
		{
			return new Builder().set(titleIn, widthIn, heightIn, -1, WindowShowType.WINDOWED);
		}

		public static Builder base(String titleIn, int widthIn, int heightIn, int refreshRateIn,
				WindowShowType windowShowTypeIn)
		{
			return new Builder().set(titleIn, widthIn, heightIn, refreshRateIn, windowShowTypeIn);
		}

		private Builder()
		{
			super.mustBeDecorated	= true;
			super.sync				= -1;
		}

		public Builder set(String titleIn, int widthIn, int heightIn)
		{
			super.title		= titleIn;
			super.width		= widthIn;
			super.height	= heightIn;

			return this;
		}

		public Builder set(String titleIn, int widthIn, int heightIn, int refreshRateIn,
				WindowShowType windowShowTypeIn)
		{
			super.title				= titleIn;
			super.width				= widthIn;
			super.height			= heightIn;
			super.refreshRate		= refreshRateIn;
			super.windowShowType	= windowShowTypeIn;

			return this;
		}

		public WindowSettings build() throws Exception
		{
			if (super.title == null)
			{
				throw new Exception("[ERREUR] WindowSettings Builder FAILED : title is undefined !");
			}
			if (super.title.isBlank() || super.title.contentEquals("\\s+"))
			{
				System.err.println("[WARNING] WindowSettings Builder : title is empty or blank !");
			}

			if (super.width == 0)
			{
				throw new Exception("[ERREUR] WindowSettings Builder FAILED : width is undefined !");
			}
			if (super.width < 0)
			{
				throw new Exception("[ERREUR] WindowSettings Builder FAILED : width has negative value !");
			}

			if (super.height == 0)
			{
				throw new Exception("[ERREUR] WindowSettings Builder FAILED : height is undefined !");
			}
			if (super.height < 0)
			{
				throw new Exception("[ERREUR] WindowSettings Builder FAILED : height has negative value !");
			}

			if (super.refreshRate == 0)
			{
				throw new Exception("[ERREUR] WindowSettings Builder FAILED : refreshRate is undefined !");
			}
			// If refreshRate has negative value, no refresh rate limit is applied

			if (super.windowShowType == null)
			{
				throw new Exception("[ERREUR] WindowSettings Builder FAILED : show type is undefined !");
			}

			if (super.sync < 0)
			{
				if (super.mustBeSynchronized)
				{
					super.sync = 1;
				}
				else
				{
					super.sync = 0;
				}
			}

			return super.create();
		}
	}
}
