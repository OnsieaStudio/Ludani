#version 330

layout (location = 0) out vec4 out_colour;

void main()
{
    gl_FragDepth = gl_FragCoord.z;
    out_colour = vec4( gl_FragCoord.z-1.0f+0.05f, gl_FragCoord.z+0.05f,gl_FragCoord.z+0.05f, 1.0f);
}