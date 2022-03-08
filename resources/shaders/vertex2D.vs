#version 330

layout (location=0) in vec2 position;

out vec2 out_uvs;

void main() 
{
	gl_Position = vec4(position, 0.0, 1.0);

	out_uvs = (position + 1.0f)/2.0f;
}