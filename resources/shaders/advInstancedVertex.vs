#version 330

layout (location=0) in vec3 positions;
layout (location=1) in vec2 uvs;
layout (location=2) in vec3 normals;
layout (location=3) in vec3 tangents;
layout (location=4) in mat4 m_transformations;
layout (location=8) in vec3 itemInfo;

uniform mat4 projection;
uniform mat4 view;
uniform mat4 projectionView;
uniform float selectedInstanceId;
uniform float instanceIsSelected;
uniform vec3 selectedPosition;
uniform float chunkIsSelected;
uniform float selectedUniqueItemId;

out vec2 pass_uvs;
out float pass_texture_layer;
out float pass_selectedUniqueItemId;
out float pass_isSelected;
out vec3 pass_itemInfo;

void main() 
{
	vec4 basePosition = m_transformations * vec4(positions, 1.0);
	gl_Position = projection * view * basePosition;;
	pass_uvs = uvs;

	if(itemInfo.z == selectedUniqueItemId)
		pass_isSelected = 2;
	else
		pass_isSelected = 0;

	pass_selectedUniqueItemId = selectedUniqueItemId;
	pass_itemInfo = itemInfo;
}