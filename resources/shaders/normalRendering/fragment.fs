#version 440

struct Attenuation
{
    float constant;
    float linear;
    float exponent;
};

struct PointLight
{
    vec3 colour;
    // Light position is assumed to be in view coordinates
    vec3 position;
    float intensity;
    Attenuation att;
};

struct SpotLight
{
    PointLight pl;
    vec3 conedir;
    float cutoff;
};

struct DirectionalLight
{
    vec3 colour;
    vec3 direction;
    float intensity;
};

struct Material
{
    vec4 ambient;
    vec4 diffuse;
    vec4 specular;
    int hasTexture;
    int hasNormalMap;
    float reflectance;
};

struct Fog
{
	int activeFog;
	vec3 colour;
	float density;
	float gradient;
};

const int MAX_POINT_LIGHTS = 5;
const int MAX_SPOT_LIGHTS = 5;

vec4 ambientC;
vec4 diffuseC;
vec4 speculrC;

in mat4 pass_modelView;
in vec2 pass_textureCoords;
in vec3 pass_mvVertexPos;

uniform sampler2D texture_sampler;
uniform vec3 ambientLight;
uniform float specularPower;
uniform Material material;
uniform PointLight pointLights[MAX_POINT_LIGHTS];
uniform SpotLight spotLights[MAX_SPOT_LIGHTS];
uniform DirectionalLight directionalLight;
uniform Fog fog;

out vec4 fragColor;

void setupColours(Material material, vec2 textCoord)
{
    if (material.hasTexture == 1)
    {
        ambientC = texture(texture_sampler, textCoord);
        diffuseC = ambientC;
        speculrC = ambientC;
    }
    else
    {
        ambientC = material.ambient;
        diffuseC = material.diffuse;
        speculrC = material.specular;
    }
}

vec4 calcLightColour(vec3 light_colour, float light_intensity, vec3 position, vec3 to_light_dir)
{
    vec4 diffuseColour = vec4(0, 0, 0, 0);
    vec4 specColour = vec4(0, 0, 0, 0);

    // Diffuse Light
    float diffuseFactor = max(length(to_light_dir)/(10.0f), 0.0);
    diffuseColour = diffuseC * vec4(light_colour, 1.0) * light_intensity * diffuseFactor;

    // Specular Light
    vec3 camera_direction = normalize(-position);
    vec3 from_light_dir = -to_light_dir;
    vec3 reflected_light = normalize(reflect(from_light_dir , camera_direction));
    float specularFactor = max( dot(camera_direction, reflected_light), 0.0);
    specularFactor = pow(specularFactor, specularPower);
    specColour = speculrC * light_intensity * specularFactor * material.reflectance * vec4(light_colour, 1.0);

    return (diffuseColour + specColour);
}

vec4 calcPointLight(PointLight light, vec3 position)
{
    vec3 light_direction = (light.position - position);
    vec3 to_light_dir = normalize(light_direction);
    vec4 light_colour = calcLightColour(light.colour, light.intensity, position, to_light_dir);

    // Apply Attenuation
    float distance = length(light_direction);
    float attenuationInv = light.att.constant + light.att.linear * distance +
        light.att.exponent * distance * distance;
    return light_colour / attenuationInv;
}

vec4 calcSpotLight(SpotLight light, vec3 position)
{
    vec3 light_direction = (light.pl.position - position);
    vec3 to_light_dir  = normalize(light_direction);
    vec3 from_light_dir  = -to_light_dir;
    float spot_alfa = dot(from_light_dir, normalize(light.conedir));
    
    vec4 colour = vec4(0, 0, 0, 0);

    if ( spot_alfa > light.cutoff ) 
    {
        colour = calcPointLight(light.pl, position);
        colour *= (1.0 - (1.0 - spot_alfa)/(1.0 - light.cutoff));
    }
    return colour;    
}

vec4 calcDirectionalLight(DirectionalLight light, vec3 position)
{
    return calcLightColour(light.colour, light.intensity, position, normalize(light.direction));
}

vec4 calcFog(vec3 pos, vec4 colour, Fog fog, vec3 ambientLight, DirectionalLight dirLight)
{
	vec3 fogColour = fog.colour * (ambientLight + dirLight.colour * dirLight.intensity);
	float distance = length(pos.xyz);
    float fogFactor = exp(-pow((distance * fog.density),fog. gradient));
    fogFactor = clamp( fogFactor, 0.0, 1.0 );

    vec3 resultColour = mix(fogColour, colour.xyz, fogFactor);

    return vec4(resultColour.xyz, colour.w);
}

void main()
{
    setupColours(material, pass_textureCoords);

    vec4 diffuseSpecularComp = calcDirectionalLight(directionalLight, pass_mvVertexPos);

    for (int i=0; i<MAX_POINT_LIGHTS; i++)
    {
        if ( pointLights[i].intensity > 0 )
        {
            diffuseSpecularComp += calcPointLight(pointLights[i], pass_mvVertexPos); 
        }
    }

    for (int i=0; i<MAX_SPOT_LIGHTS; i++)
    {
        if ( spotLights[i].pl.intensity > 0 )
        {
            diffuseSpecularComp += calcSpotLight(spotLights[i], pass_mvVertexPos);
        }
    }
    
    fragColor = ambientC * vec4(ambientLight, 1) + diffuseSpecularComp;
	if ( fog.activeFog == 1 ) 
	{
	    fragColor = calcFog(pass_mvVertexPos, fragColor, fog, ambientLight, directionalLight);
	}
}