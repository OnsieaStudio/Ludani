#version 440

in vec2 out_uvs;

uniform sampler2D textureSampler;
out vec4 fragColor;

void main()
{
	fragColor = texture(textureSampler, out_uvs);
}