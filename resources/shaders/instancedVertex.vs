#version 330

layout (location=0) in vec2 position;
layout (location=1)  in mat4 m_transformations;
layout (location=5)  in vec2 tex;

uniform mat4 projection;
uniform mat4 view;
uniform vec2 rowsAndColumns;

out vec2 out_uvs;

void main() 
{
	gl_Position = m_transformations * vec4(position, 0.0, 1.0);

	out_uvs = position;
	out_uvs.y = 1.0 - out_uvs.y;
	out_uvs /= rowsAndColumns;
	out_uvs.x += ((1 / rowsAndColumns.x) * tex.x);
	
	out_uvs.y += ((1 / rowsAndColumns.y) * tex.y);
}