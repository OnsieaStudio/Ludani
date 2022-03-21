#version 330

layout (location=0) in vec3 in_position;
layout (location=1) in vec2 texCoord;
layout (location=2) in vec3 vertexNormal;

uniform mat4 orthoProjection;
uniform mat4 modelLightView;

void main()
{
    gl_Position = orthoProjection * modelLightView * vec4(in_position, 1.0f);
}