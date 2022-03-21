#version 330

layout (location=0) in vec3 position;
layout (location=1) in vec2 texCoord;
layout (location=2) in vec3 vertexNormal;

uniform mat4 projection;
uniform mat4 view;
uniform mat4 transformations;
uniform mat4 orthoProjection;
uniform mat4 modelLightView;

out vec2 pass_textureCoords;
out vec3 pass_mvVertexNormal;
out vec3 pass_mvVertexPos;
out vec4 pass_mlightviewVertexPos;
out mat4 pass_modelView;


void main()
{
	mat4 modelView = view * transformations;
    vec4 mvPos = modelView * vec4(position, 1.0);
    gl_Position = projection * mvPos;

    pass_textureCoords = texCoord;
    pass_mvVertexNormal = normalize(modelView * vec4(vertexNormal, 0.0)).xyz;
    pass_mvVertexPos = mvPos.xyz;
    pass_modelView = modelView;
    pass_mlightviewVertexPos = orthoProjection * modelLightView * vec4(position, 1.0);
}