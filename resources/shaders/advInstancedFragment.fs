#version 440

in vec2 pass_uvs;
in float pass_texture_layer;
in float pass_isSelected;
in float pass_selectedInstanceId;
in float pass_gl_InstanceID;

uniform sampler2DArray textureSampler;

out vec4 out_colour;

void main()
{
	out_colour = texture(textureSampler, vec3(pass_uvs, pass_texture_layer));//vec4(1.0f, 1.0f, 1.0f, 1.0f);
	// texture(textureSampler, pass_uvs);
	
	if(pass_isSelected >= 0.5f)
	{
		out_colour *= 8.0f;
	out_colour.x = 1.0f;
	}
}