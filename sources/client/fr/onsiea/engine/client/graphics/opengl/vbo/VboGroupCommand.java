package fr.onsiea.engine.client.graphics.opengl.vbo;

import fr.onsiea.engine.client.graphics.opengl.model.GroupAttributCommand;

public class VboGroupCommand extends GroupAttributCommand
{
	@Override
	protected int[] commandAll()
	{
		return Vbo.genAll(this.total());
	}
}
