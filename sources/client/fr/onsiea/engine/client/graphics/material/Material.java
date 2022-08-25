/**
 * 
 */
package fr.onsiea.engine.client.graphics.material;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joml.Vector4f;

import fr.onsiea.engine.client.graphics.texture.Texture;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Seynax
 *
 */

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
	private List<Texture<?>>		textures;

	public Material()
	{
		this.ambientColour(Material.DEFAULT_COLOUR);
		this.diffuseColour(Material.DEFAULT_COLOUR);
		this.specularColour(Material.DEFAULT_COLOUR);
		this.reflectance(0);

		this.textures(new ArrayList<>());
	}

	public Material(Vector4f colourIn, float reflectanceIn)
	{
		this(colourIn, colourIn, colourIn, reflectanceIn);
	}

	public Material(Texture<?>... texturesIn)
	{
		this(Material.DEFAULT_COLOUR, Material.DEFAULT_COLOUR, Material.DEFAULT_COLOUR, 0, texturesIn);
	}

	public Material(float reflectanceIn, Texture<?>... texturesIn)
	{
		this(Material.DEFAULT_COLOUR, Material.DEFAULT_COLOUR, Material.DEFAULT_COLOUR, reflectanceIn, texturesIn);
	}

	public Material(Vector4f ambientColourIn, Vector4f diffuseColourIn, Vector4f specularColourIn, float reflectanceIn,
			Texture<?>... texturesIn)
	{
		this.ambientColour(ambientColourIn);
		this.diffuseColour(diffuseColourIn);
		this.specularColour(specularColourIn);
		this.reflectance(reflectanceIn);

		this.textures(new ArrayList<>());

		Collections.addAll(this.textures(), texturesIn);
	}

	/**public boolean hasNormalMap()
	{
		for (final TypedTexture<?> texture : this.textures)
		{
			if (EnumTextureType.NORMAL.equals(texture.type()))
			{
				return true;
			}
		}
	
		return false;
	}**/

	public boolean isTextured()
	{
		return this.textures().size() >= 0;
	}
}
