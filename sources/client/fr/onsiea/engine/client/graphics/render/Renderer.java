package fr.onsiea.engine.client.graphics.render;

import java.util.HashMap;
import java.util.Map;

import fr.onsiea.engine.client.graphics.GraphicsConstants;
import fr.onsiea.engine.client.graphics.drawable.IDrawable;
import fr.onsiea.engine.client.graphics.glfw.window.Window;
import fr.onsiea.engine.client.input.InputManager;
import fr.onsiea.engine.core.entity.Camera;

/**
 * @author Seynax
 **/
public class Renderer
{
	private IRenderAPIContext				renderAPI;

	private Map<String, SubscriberDrawable>	subscribersDrawable;

	/**
	 * Render API initialization (OpenGL, Vulkan), associated render methods and drawables management
	 */
	public Renderer()
	{
		this.subscribersDrawable(new HashMap<>());
		this.renderAPI(GraphicsConstants.RENDER_API.initializer().initialization());
	}

	/**
	 * Render API initialization (OpenGL, Vulkan), associated render methods and drawables management
	 */
	public Renderer(IRenderAPIContext renderAPIContextIn)
	{
		this.subscribersDrawable(new HashMap<>());
		this.renderAPI(renderAPIContextIn);
	}

	private void unsubscribe(SubscriberDrawable subscriberIn)
	{
		if (subscriberIn.canUnsubscribe())
		{
			this.subscribersDrawable().remove(subscriberIn.name());
		}
	}

	public void draw(/**HudManager hudManagerIn,**/
	Window windowIn, Camera cameraIn, int framerateIn, InputManager inputManagerIn)
	{
		for (final SubscriberDrawable subscriber : this.subscribersDrawable().values())
		{
			subscriber.drawable().draw(this);
		}
		//this.drawHud(hudManagerIn, windowIn, cameraIn, 0, inputManagerIn);
	}

	/**public void drawHud(HudManager hudManagerIn, Window windowIn, Camera cameraIn, int framerateIn,
			InputManager inputManagerIn)
	{
		final var openGLAPI = (OpenGLAPI) this.renderAPI();
		hudManagerIn.draw(openGLAPI.shaderManager(), windowIn, cameraIn, framerateIn, inputManagerIn, this);
	}**/

	public SubscriberDrawable subscribe(String nameIn, IDrawable drawableIn)
	{
		final var subscriber = new SubscriberDrawable(nameIn, drawableIn, this);

		this.subscribersDrawable().put(nameIn, subscriber);

		return subscriber;
	}

	public void cleanup()
	{
		this.renderAPI().cleanup();
	}

	@SuppressWarnings("unused")
	public IRenderAPIContext renderAPI()
	{
		return this.renderAPI;
	}

	private void renderAPI(IRenderAPIContext renderAPIIn)
	{
		this.renderAPI = renderAPIIn;
	}

	public Map<String, SubscriberDrawable> subscribersDrawable()
	{
		return this.subscribersDrawable;
	}

	public void subscribersDrawable(Map<String, SubscriberDrawable> subscribersDrawableIn)
	{
		this.subscribersDrawable = subscribersDrawableIn;
	}

	public final static class SubscriberDrawable
	{
		private String		name;
		private IDrawable	drawable;
		private Renderer	renderer;
		private boolean		canUnsubscribe;

		SubscriberDrawable(String nameIn, IDrawable drawableIn, Renderer rendererIn)
		{
			this.name(nameIn);
			this.drawable(drawableIn);
			this.renderer(rendererIn);
		}

		public void unsubscribe()
		{
			this.canUnsubscribe(true);
			this.renderer().unsubscribe(this);
		}

		private void canUnsubscribe(boolean canUnsubscribeIn)
		{
			this.canUnsubscribe = canUnsubscribeIn;
		}

		private boolean canUnsubscribe()
		{
			return this.canUnsubscribe;
		}

		private String name()
		{
			return this.name;
		}

		private void name(String nameIn)
		{
			this.name = nameIn;
		}

		private IDrawable drawable()
		{
			return this.drawable;
		}

		private void drawable(IDrawable drawableIn)
		{
			this.drawable = drawableIn;
		}

		private Renderer renderer()
		{
			return this.renderer;
		}

		private void renderer(Renderer rendererIn)
		{
			this.renderer = rendererIn;
		}
	}
}