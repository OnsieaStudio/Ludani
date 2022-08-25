#version 330

layout (location=0) in vec3 in_position;
layout (location=1) in vec2 texCoord;
layout (location=2) in vec3 vertexNormal;

uniform mat4 lightProjection;
uniform mat4 lightView;

out float pass_visible;

void main()
{
    gl_Position = lightProjection * lightView * vec4(in_position, 1.0f);
}