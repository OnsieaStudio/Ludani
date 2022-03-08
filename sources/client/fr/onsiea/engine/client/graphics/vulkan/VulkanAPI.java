package fr.onsiea.engine.client.graphics.vulkan;

import fr.onsiea.engine.client.graphics.mesh.IMeshsManager;
import fr.onsiea.engine.client.graphics.render.IRenderAPIContext;
import fr.onsiea.engine.client.graphics.shader.IShadersManager;
import fr.onsiea.engine.client.graphics.texture.ITexturesManager;

public class VulkanAPI implements IRenderAPIContext
{
	public final static VulkanAPI create()
	{
		return null;
	}

	@Override
	public void cleanup()
	{

	}

	@Override
	public ITexturesManager texturesManager()
	{
		return null;
	}

	@Override
	public IMeshsManager meshsManager()
	{
		return null;
	}

	@Override
	public IShadersManager shadersManager()
	{
		return null;
	}
}
