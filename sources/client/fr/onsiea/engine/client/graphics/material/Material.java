package fr.onsiea.engine.client.graphics.material;

import org.joml.Vector4f;

import fr.onsiea.engine.client.graphics.texture.ITexture;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Material
{
	private static final Vector4f	DEFAULT_COLOUR	= new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);

	private Vector4f				ambientColour;
	private Vector4f				diffuseColour;
	private Vector4f				specularColour;
	private float					reflectance;
	private ITexture				texture;
	private ITexture				normalMap;

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

	public Material(Vector4f ambientColourIn, Vector4f diffuseColourIn, Vector4f specularColourIn, ITexture textureIn,
			float reflectanceIn, ITexture normalMapIn)
	{
		this.ambientColour(ambientColourIn);
		this.diffuseColour(diffuseColourIn);
		this.specularColour(specularColourIn);
		this.texture(textureIn);
		this.reflectance(reflectanceIn);
		this.normalMap(normalMapIn);
	}

	public boolean hasNormalMap()
	{
		return this.normalMap() != null;
	}

	public boolean isTextured()
	{
		return this.texture() != null;
	}
}