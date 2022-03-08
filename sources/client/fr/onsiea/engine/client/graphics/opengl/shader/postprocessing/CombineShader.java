package fr.onsiea.engine.client.graphics.opengl.shader.postprocessing;

import fr.onsiea.engine.client.graphics.opengl.shader.Shader;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformInt;

public class CombineShader extends Shader
{
	private static final String	VERTEX_FILE		= "resources/shaders/postprocessing/simpleVertex.glsl";
	private static final String	FRAGMENT_FILE	= "resources/shaders/postprocessing/combineFragment.glsl";

	private GLUniformInt		uniformColourTexture;
	private GLUniformInt		uniformHighlightTexture0;
	private GLUniformInt		uniformHighlightTexture1;
	private GLUniformInt		uniformHighlightTexture2;
	private GLUniformInt		uniformHighlightTexture3;

	public CombineShader() throws Exception
	{
		super(CombineShader.VERTEX_FILE, CombineShader.FRAGMENT_FILE, "position");

		this.uniformColourTexture(this.intUniform("colourTexture"));
		this.uniformHighlightTexture0(this.intUniform("highlightTexture0"));
		this.uniformHighlightTexture1(this.intUniform("highlightTexture1"));
		this.uniformHighlightTexture2(this.intUniform("highlightTexture2"));
		this.uniformHighlightTexture3(this.intUniform("highlightTexture3"));

		this.attach();
		this.connectTextureUnits();
	}

	private void connectTextureUnits()
	{
		this.uniformColourTexture().load(0);
		this.uniformHighlightTexture0().load(1);
		this.uniformHighlightTexture1().load(2);
		this.uniformHighlightTexture2().load(3);
		this.uniformHighlightTexture3().load(4);
	}

	public final GLUniformInt uniformColourTexture()
	{
		return this.uniformColourTexture;
	}

	private final void uniformColourTexture(GLUniformInt uniformColourTextureIn)
	{
		this.uniformColourTexture = uniformColourTextureIn;
	}

	public final GLUniformInt uniformHighlightTexture0()
	{
		return this.uniformHighlightTexture0;
	}

	private final void uniformHighlightTexture0(GLUniformInt uniformHighlightTexture0In)
	{
		this.uniformHighlightTexture0 = uniformHighlightTexture0In;
	}

	public final GLUniformInt uniformHighlightTexture1()
	{
		return this.uniformHighlightTexture1;
	}

	private final void uniformHighlightTexture1(GLUniformInt uniformHighlightTexture1In)
	{
		this.uniformHighlightTexture1 = uniformHighlightTexture1In;
	}

	public final GLUniformInt uniformHighlightTexture2()
	{
		return this.uniformHighlightTexture2;
	}

	private final void uniformHighlightTexture2(GLUniformInt uniformHighlightTexture2In)
	{
		this.uniformHighlightTexture2 = uniformHighlightTexture2In;
	}

	public final GLUniformInt uniformHighlightTexture3()
	{
		return this.uniformHighlightTexture3;
	}

	private final void uniformHighlightTexture3(GLUniformInt uniformHighlightTexture3In)
	{
		this.uniformHighlightTexture3 = uniformHighlightTexture3In;
	}
}