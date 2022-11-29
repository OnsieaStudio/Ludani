#version 150

in vec2 textureCoords;
in float out_contrast;

uniform sampler2D originalTexture;

out vec4 out_colour;

void main(void)
{
	out_colour = texture(originalTexture, textureCoords);
	out_colour.rgb = (out_colour.rgb - 0.5) * (out_contrast) + 0.5;
}