#version 330

layout (location=0) in vec3 position;

uniform mat4 projection;
uniform mat4 view;
uniform mat4 transformations;

out vec3 out_uvs;
out vec3 pos;

void main()
{
	gl_Position = projection * view * vec4(position, 1.0);

	out_uvs = position;
	pos = (projection * vec4(position, 1.0)).xyz;
}