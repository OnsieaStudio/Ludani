#version 330

layout (location=0) in vec3 position;
layout (location=1) in vec2 uvs;

uniform mat4 projection;
uniform mat4 view;
uniform mat4 transformations;

out vec3 pos;
out vec2 out_uvs;

void main()
{
   gl_Position = projection * view * transformations * vec4(position, 1.0);
   
   pos = position;
   out_uvs = uvs;
}