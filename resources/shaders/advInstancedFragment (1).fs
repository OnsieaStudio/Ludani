#version 440

in vec2 pass_uvs;

uniform sampler2D textureSampler;
out vec4 out_colour;

void main()
{
	out_colour = vec4(0.5f, 1.0f, 1.0f, 1.0f);
	// texture(textureSampler, pass_uvs);
}