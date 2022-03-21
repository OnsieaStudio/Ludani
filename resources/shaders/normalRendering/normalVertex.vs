#version 330

layout (location=0) in vec3 position;
layout (location=1) in vec2 texCoord;
layout (location=2) in vec3 vertexNormal;
layout (location=3) in vec3 tangent;

uniform mat4 projection;
uniform mat4 view;
uniform mat4 transformations;

out mat4 pass_modelView;
out vec2 pass_textureCoords;
out vec3 pass_mvVertexPos;
out vec3 pass_mvVertexNormal;

//uniform vec4 plane;

void main()
{
	vec4 position_4 = vec4(position, 1.0);
	vec4 worldPosition =  transformations * position_4;
	//gl_ClipDistance[0] = dot(worldPosition, plane);
	pass_modelView = view * transformations;
	vec4 positionRelativeToCam = pass_modelView * position_4;
	gl_Position = projection * positionRelativeToCam;

	pass_textureCoords = texCoord;
	pass_mvVertexPos = positionRelativeToCam.xyz;

	pass_mvVertexNormal = normalize(pass_modelView  * vec4(vertexNormal, 0.0)).xyz;
}