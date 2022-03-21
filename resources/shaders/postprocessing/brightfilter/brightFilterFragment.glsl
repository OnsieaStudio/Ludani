#version 150

in vec2 textureCoords;

uniform sampler2D originalTexture;

out vec4 out_colour;

void main(void)
{
	out_colour = texture(originalTexture, textureCoords);
	float brightness = (out_colour.r * 0.2126) + (out_colour.g * 0.7152) + (out_colour.b * 0.0722);
	
	if(brightness > 0.7)
	{
		out_colour = out_colour;
	}
	else
	{
		out_colour = vec4(0.0);
	}
}