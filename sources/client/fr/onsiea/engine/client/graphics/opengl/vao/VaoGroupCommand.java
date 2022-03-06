package fr.onsiea.engine.client.graphics.opengl.vao;

import fr.onsiea.engine.client.graphics.opengl.model.GroupAttributCommand;

public class VaoGroupCommand extends GroupAttributCommand
{
	@Override
	protected int[] commandAll()
	{
		return VaoUtils.genAll(this.total());
	}
}
