#version 440

in vec2 pass_uvs;
in float pass_texture_layer;
in float pass_textured;

uniform sampler2D textureSampler;

out vec4 out_colour;

void main()
{
	if(pass_textured >= 0.5f)
		out_colour = texture(textureSampler, pass_uvs);
	else
		out_colour = vec4(1.0f, 1.0f, 1.0f, 1.0f);
}