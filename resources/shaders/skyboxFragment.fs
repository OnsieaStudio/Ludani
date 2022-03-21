#version 440

struct Fog
{
	int activeFog;
	vec3 colour;
	float density;
	float gradient;
};

struct DirectionalLight
{
    vec3 colour;
    vec3 direction;
    float intensity;
};

in vec3 out_uvs;
in vec3 pos;

uniform samplerCube textureSampler;
uniform vec3 ambientLight;
uniform DirectionalLight dirLight;
uniform Fog fog;

out vec4 fragColor;

vec4 calcFog(vec3 pos, vec4 colour, Fog fog, vec3 ambientLight, DirectionalLight dirLight)
{
	vec3 fogColour = fog.colour * (ambientLight + dirLight.colour * dirLight.intensity);
	float distance = pos.y / 400.0f;
    float fogFactor = exp(-pow((distance * fog.density),distance*fog.gradient));
    fogFactor = clamp( fogFactor, 0.0, 1.0 ) * distance;

    vec3 resultColour = mix(fogColour, colour.xyz, fogFactor);
	
    return vec4(resultColour.xyz, colour.w);
}

void main()
{
	fragColor = texture(textureSampler, out_uvs);

	if ( fog.activeFog == 1 ) 
	{
	    fragColor = calcFog(pos, fragColor, fog, ambientLight, dirLight);
	}
}