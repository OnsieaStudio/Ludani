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
out mat3 pass_mvTangentSpace;
out vec3  pass_toCameraVector;
out vec3  pass_position;
out vec3  pass_normal;
out vec3  pass_tangent;

//uniform vec4 plane;

void main()
{
	vec4 worldPosition =  transformations * vec4(position, 1.0);
	//gl_ClipDistance[0] = dot(worldPosition, plane);
	pass_modelView = view * transformations;
	vec4 positionRelativeToCam = view * worldPosition;
	gl_Position = projection * positionRelativeToCam;

	pass_textureCoords = texCoord;
	pass_mvVertexPos = positionRelativeToCam.xyz;

	vec3 surfaceNormal = (pass_modelView  * vec4(vertexNormal, 0.0)).xyz;	
	pass_mvVertexNormal = normalize(surfaceNormal);
	vec3 tang = normalize((pass_modelView * vec4(tangent, 0.0)).xyz);
	vec3 bitang = normalize(cross(pass_mvVertexNormal, tang));
    pass_mvTangentSpace = mat3(
		tang.x, bitang.x, pass_mvVertexNormal.x,
		tang.y, bitang.y, pass_mvVertexNormal.y,
		tang.z, bitang.z, pass_mvVertexNormal.z
	);

    /**for(int i=0;i<4;i++){
		toLightVector[i] = toTangentSpace * (lightPositionEyeSpace[i] - positionRelativeToCam.xyz);
	}**/
	pass_toCameraVector = pass_mvTangentSpace * (-positionRelativeToCam.xyz);
	pass_position = position;
	pass_normal = vertexNormal;
	pass_tangent = tangent;
}