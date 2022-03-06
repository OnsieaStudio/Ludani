package fr.onsiea.engine.client.graphics.opengl.shader.postprocessing;

import fr.onsiea.engine.client.graphics.opengl.shader.Shader;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.UniformInt;

public class CombineShader extends Shader
{
	private static final String	VERTEX_FILE		= "resources/shaders/postprocessing/simpleVertex.glsl";
	private static final String	FRAGMENT_FILE	= "resources/shaders/postprocessing/combineFragment.glsl";

	private UniformInt			uniformColourTexture;
	private UniformInt			uniformHighlightTexture0;
	private UniformInt			uniformHighlightTexture1;
	private UniformInt			uniformHighlightTexture2;
	private UniformInt			uniformHighlightTexture3;

	public CombineShader() throws Exception
	{
		super(CombineShader.VERTEX_FILE, CombineShader.FRAGMENT_FILE, "position");

		this.uniformColourTexture(this.intUniform("colourTexture"));
		this.uniformHighlightTexture0(this.intUniform("highlightTexture0"));
		this.uniformHighlightTexture1(this.intUniform("highlightTexture1"));
		this.uniformHighlightTexture2(this.intUniform("highlightTexture2"));
		this.uniformHighlightTexture3(this.intUniform("highlightTexture3"));

		this.start();
		this.connectTextureUnits();
		Shader.stop();
	}

	private void connectTextureUnits()
	{
		this.uniformColourTexture().load(0);
		this.uniformHighlightTexture0().load(1);
		this.uniformHighlightTexture1().load(2);
		this.uniformHighlightTexture2().load(3);
		this.uniformHighlightTexture3().load(4);
	}

	public final UniformInt uniformColourTexture()
	{
		return this.uniformColourTexture;
	}

	private final void uniformColourTexture(UniformInt uniformColourTextureIn)
	{
		this.uniformColourTexture = uniformColourTextureIn;
	}

	public final UniformInt uniformHighlightTexture0()
	{
		return this.uniformHighlightTexture0;
	}

	private final void uniformHighlightTexture0(UniformInt uniformHighlightTexture0In)
	{
		this.uniformHighlightTexture0 = uniformHighlightTexture0In;
	}

	public final UniformInt uniformHighlightTexture1()
	{
		return this.uniformHighlightTexture1;
	}

	private final void uniformHighlightTexture1(UniformInt uniformHighlightTexture1In)
	{
		this.uniformHighlightTexture1 = uniformHighlightTexture1In;
	}

	public final UniformInt uniformHighlightTexture2()
	{
		return this.uniformHighlightTexture2;
	}

	private final void uniformHighlightTexture2(UniformInt uniformHighlightTexture2In)
	{
		this.uniformHighlightTexture2 = uniformHighlightTexture2In;
	}

	public final UniformInt uniformHighlightTexture3()
	{
		return this.uniformHighlightTexture3;
	}

	private final void uniformHighlightTexture3(UniformInt uniformHighlightTexture3In)
	{
		this.uniformHighlightTexture3 = uniformHighlightTexture3In;
	}
}