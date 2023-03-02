#version 330

layout (location=0) in vec3 positions;
layout (location=1) in vec2 uvs;
layout (location=2) in vec3 normals;
layout (location=3) in vec3 tangents;
layout (location=4) in mat4 m_transformations;

uniform mat4 projection;
uniform mat4 view;
uniform mat4 projectionView;

out vec2 pass_uvs;

void main() 
{
	gl_Position = projection * view * m_transformations * vec4(positions, 1.0);

	pass_uvs = uvs;
}