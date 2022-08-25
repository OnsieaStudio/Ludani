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
uniform float selectedInstanceId;
uniform vec3 selectedPosition;

out vec2 pass_uvs;
out float pass_texture_layer;
out float pass_isSelected;
out float pass_selectedInstanceId;
out float pass_gl_InstanceID;

void main() 
{
	vec4 basePosition = m_transformations * vec4(positions, 1.0);
	gl_Position = projection * view * basePosition;;
	pass_uvs = uvs;
	pass_texture_layer = m_texture_layer;
	
	if(selectedInstanceId == gl_InstanceID)
		pass_isSelected = 2;
	else
		pass_isSelected = 0;
	pass_selectedInstanceId = selectedInstanceId;	
	pass_gl_InstanceID = gl_InstanceID;
}