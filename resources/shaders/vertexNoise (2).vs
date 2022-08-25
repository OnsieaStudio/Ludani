#version 330

layout (location=0) in vec3 position;

uniform mat4 projection;
uniform mat4 view;
uniform mat4 transformations;
uniform float isWater;

out vec3 pass_position;
out float pass_isWater;

void main()
{
	gl_Position = projection * view * transformations * vec4(position, 1.0);

	pass_position = position;
	pass_isWater = isWater;
}