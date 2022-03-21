#version 150

in vec2 blurTextureCoords[11];

uniform sampler2D originalTexture;
uniform float[] values;

out vec4 out_colour;

void main(void)
{
	out_colour = vec4(0.0);
	out_colour += texture(originalTexture, blurTextureCoords[0]) * values[0];
    out_colour += texture(originalTexture, blurTextureCoords[1]) * values[1];
    out_colour += texture(originalTexture, blurTextureCoords[2]) * values[2];
    out_colour += texture(originalTexture, blurTextureCoords[3]) * values[3];
    out_colour += texture(originalTexture, blurTextureCoords[4]) * values[4];
    out_colour += texture(originalTexture, blurTextureCoords[5]) * values[5];
    out_colour += texture(originalTexture, blurTextureCoords[6]) * values[6];
    out_colour += texture(originalTexture, blurTextureCoords[7]) * values[7];
    out_colour += texture(originalTexture, blurTextureCoords[8]) * values[8];
    out_colour += texture(originalTexture, blurTextureCoords[9]) * values[9];
    out_colour += texture(originalTexture, blurTextureCoords[10]) * values[10];
}