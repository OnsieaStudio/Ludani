package fr.onsiea.engine.client.graphics.opengl.shader.postprocessing;

import fr.onsiea.engine.client.graphics.opengl.shader.Shader;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformInt;
import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PUBLIC)
public class CombineShader extends Shader
{
	private static final String	VERTEX_FILE		= "resources/shaders/postprocessing/combine/combineVertex.glsl";
	private static final String	FRAGMENT_FILE	= "resources/shaders/postprocessing/combine/combineFragment.glsl";

	private final GLUniformInt	uniformColourTexture;
	private final GLUniformInt	uniformHighlightTexture2;
	private final GLUniformInt	uniformHighlightTexture4;
	private final GLUniformInt	uniformHighlightTexture8;

	public CombineShader() throws Exception
	{
		super(CombineShader.VERTEX_FILE, CombineShader.FRAGMENT_FILE, "position");

		this.uniformColourTexture		= this.intUniform("colourTexture");
		this.uniformHighlightTexture2	= this.intUniform("highlightTexture2");
		this.uniformHighlightTexture4	= this.intUniform("highlightTexture4");
		this.uniformHighlightTexture8	= this.intUniform("highlightTexture8");

		this.attach();
		this.connectTextureUnits();
	}

	private void connectTextureUnits()
	{
		this.uniformColourTexture.load(0);
		this.uniformHighlightTexture2.load(1);
		this.uniformHighlightTexture4.load(2);
		this.uniformHighlightTexture8.load(3);
	}
}