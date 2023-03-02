#version 440

in vec2 pass_uvs;
in float pass_isSelected;
in float pass_selectedUniqueItemId;
in vec3 pass_itemInfo;

uniform sampler2DArray textureSampler0;
uniform sampler2DArray textureSampler1;
uniform sampler2DArray textureSampler2;

out vec4 out_colour;

void main()
{
	vec4 textureColor = vec4(1.0f, 1.0f, 1.0f, 1.0f);
	if(pass_itemInfo.y >= 0 && pass_itemInfo.y < 10)
	{
		textureColor = texture(textureSampler0, vec3(pass_uvs, pass_itemInfo.x));
	}
	else if(pass_itemInfo.y >= 10 && pass_itemInfo.y < 20)
	{
		textureColor = texture(textureSampler1, vec3(pass_uvs, pass_itemInfo.x));
	}
	else if(pass_itemInfo.y >= 20 && pass_itemInfo.y < 30)
	{
		textureColor = texture(textureSampler2, vec3(pass_uvs, pass_itemInfo.x));
	}
	

	out_colour = textureColor;

	if(pass_isSelected >= 0.5f)
	{
		out_colour *= 1.5f;
		out_colour.x = 0.5f;
	}

	/**if(pass_chunkIsSelected >= 0.5f)
	{
		out_colour.x = 1.0f;
	}**/
}