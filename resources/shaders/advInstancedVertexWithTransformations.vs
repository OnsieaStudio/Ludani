#version 330

layout (location=0) in vec3 positions;
layout (location=1) in vec2 uvs;
layout (location=2) in vec3 normals;
layout (location=3) in vec3 tangents;
layout (location=4) in mat4 m_transformations;
layout (location=8) in float m_texture_layer;

uniform mat4 projection;
uniform mat4 view;
uniform mat4 projectionView;
uniform mat4 transformations;
uniform float textured;

out vec2 pass_uvs;
out float pass_texture_layer;
out float pass_textured;

void main() 
{
	vec4 basePosition = transformations * vec4(positions, 1.0);
	gl_Position = projection * basePosition;
	pass_uvs = uvs;
	pass_texture_layer = m_texture_layer;
	pass_textured = textured;
}