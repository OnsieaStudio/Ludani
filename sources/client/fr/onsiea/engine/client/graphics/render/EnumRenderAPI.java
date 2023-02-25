package fr.onsiea.engine.client.graphics.render;

import fr.onsiea.engine.client.graphics.opengl.OpenGLRenderAPIContext;
import fr.onsiea.engine.client.graphics.vulkan.VulkanAPI;

public enum EnumRenderAPI
{
	OPENGL(() ->
	{
		try
		{
			return OpenGLRenderAPIContext.create();
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}), VULKAN(VulkanAPI::create);

	private IRenderAPIInitializer initializer;

	EnumRenderAPI(IRenderAPIInitializer renderAPIInitializerIn)
	{
		this.initializer(renderAPIInitializerIn);
	}

	public IRenderAPIInitializer initializer()
	{
		return this.initializer;
	}

	private void initializer(IRenderAPIInitializer initializerIn)
	{
		this.initializer = initializerIn;
	}
}