#version 150

in vec2 textureCoords;
in float out_contrast;

uniform sampler2D originalTexture;
uniform sampler2D highlightTexture2;
uniform sampler2D highlightTexture4;
uniform sampler2D highlightTexture8;

out vec4 out_colour;

void main(void)
{
	vec4 sceneColour = texture(originalTexture, textureCoords);
	vec4 highlightColour2 = texture(highlightTexture2, textureCoords); 
	vec4 highlightColour4 = texture(highlightTexture4, textureCoords); 
	vec4 highlightColour8 = texture(highlightTexture8, textureCoords); 
	out_colour = sceneColour + highlightColour2 + highlightColour4 + highlightColour8;
}