#version 150

in vec2 position;

uniform float contrast;

out vec2 textureCoords;
out float out_contrast;

void main(void)
{
	gl_Position = vec4(position, 0.0, 1.0);
	textureCoords = position * 0.5 + 0.5;
	
	out_contrast = contrast;
}