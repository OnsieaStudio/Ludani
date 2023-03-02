#version 440

in vec2 out_uvs;

uniform sampler2D textureSampler;

out vec4 out_colour;

/**const float near = 0.1f;
const float far = 1000.0f;
	// vec2 res =  out_uvs;
    // vec2 res = gl_FragCoord.xy / vec2(1024, 1024);


	vec4 tex = 
	float depth = tex.x;
	float eye_z = near * far / ((depth * (far - near)) - far);
	float val = ( eye_z - (-near) ) / ( -far - (-near) );**/

void main()
{
	out_colour =	texture(textureSampler, out_uvs);
}