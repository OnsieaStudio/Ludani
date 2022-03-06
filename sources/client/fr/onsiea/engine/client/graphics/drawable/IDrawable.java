package fr.onsiea.engine.client.graphics.drawable;

import fr.onsiea.engine.client.graphics.render.Renderer;

public interface IDrawable
{
	void startDrawing(Renderer renderAPIContextIn);

	void draw(Renderer rendererIn);

	void stopDrawing(Renderer renderAPIContextIn);
}
