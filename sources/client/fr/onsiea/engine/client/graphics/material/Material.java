package fr.onsiea.engine.client.graphics.material;

import org.joml.Vector4f;

import fr.onsiea.engine.client.graphics.texture.ITexture;

public class Material
{
	private static final Vector4f	DEFAULT_COLOUR	= new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);

	private Vector4f				ambientColour;

	private Vector4f				diffuseColour;

	private Vector4f				specularColour;

	private float					reflectance;

	private ITexture				texture;

	public Material()
	{
		this.ambientColour(Material.DEFAULT_COLOUR);
		this.diffuseColour(Material.DEFAULT_COLOUR);
		this.specularColour(Material.DEFAULT_COLOUR);
		this.reflectance(0);
	}

	public Material(Vector4f colourIn, float reflectanceIn)
	{
		this(colourIn, colourIn, colourIn, null, reflectanceIn);
	}

	public Material(ITexture textureIn)
	{
		this(Material.DEFAULT_COLOUR, Material.DEFAULT_COLOUR, Material.DEFAULT_COLOUR, textureIn, 0);
	}

	public Material(ITexture textureIn, float reflectanceIn)
	{
		this(Material.DEFAULT_COLOUR, Material.DEFAULT_COLOUR, Material.DEFAULT_COLOUR, textureIn, reflectanceIn);
	}

	public Material(Vector4f ambientColourIn, Vector4f diffuseColourIn, Vector4f specularColourIn, ITexture textureIn,
			float reflectanceIn)
	{
		this.ambientColour(ambientColourIn);
		this.diffuseColour(diffuseColourIn);
		this.specularColour(specularColourIn);
		this.texture(textureIn);
		this.reflectance(reflectanceIn);
	}

	public boolean isTextured()
	{
		return this.texture != null;
	}

	public final Vector4f ambientColour()
	{
		return this.ambientColour;
	}

	private final void ambientColour(Vector4f ambientColourIn)
	{
		this.ambientColour = ambientColourIn;
	}

	public final Vector4f diffuseColour()
	{
		return this.diffuseColour;
	}

	private final void diffuseColour(Vector4f diffuseColourIn)
	{
		this.diffuseColour = diffuseColourIn;
	}

	public final Vector4f specularColour()
	{
		return this.specularColour;
	}

	private final void specularColour(Vector4f specularColourIn)
	{
		this.specularColour = specularColourIn;
	}

	public final float reflectance()
	{
		return this.reflectance;
	}

	public final void reflectance(float reflectanceIn)
	{
		this.reflectance = reflectanceIn;
	}

	public final ITexture texture()
	{
		return this.texture;
	}

	private final void texture(ITexture textureIn)
	{
		this.texture = textureIn;
	}

	public static final Vector4f defaultColour()
	{
		return Material.DEFAULT_COLOUR;
	}
}
