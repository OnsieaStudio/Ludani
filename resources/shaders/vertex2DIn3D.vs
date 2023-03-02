#version 330

layout (location=0) in vec2 position;

uniform mat4 transformations;
uniform vec4 color;

out vec2 out_uvs;
out vec4 pass_color;

void main() 
{
	gl_Position = transformations * vec4(position, 0.9, 1.0);

	out_uvs = (position + 1.0f)/2.0f;
	
	pass_color = color;
}