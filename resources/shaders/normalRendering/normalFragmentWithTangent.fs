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

struct LightComponents
{
	vec4 diffuse;
	vec4 specular;
};

const int MAX_POINT_LIGHTS = 1;
const int MAX_SPOT_LIGHTS = 1;

vec4 ambientC;
vec4 diffuseC;
vec4 speculrC;

in mat4 pass_modelView;
in vec2 pass_textureCoords;
in vec3 pass_mvVertexPos;
in vec3 pass_mvVertexNormal;
in mat3 pass_mvTangentSpace;
in vec3 pass_toCameraVector;
in vec3 pass_position;
in vec3 pass_normal;
in vec3 pass_tangent;

uniform sampler2D texture_sampler;
uniform sampler2D normalMap_sampler;
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

LightComponents calcLightColour(vec3 light_colour, float light_intensity, vec3 position, vec3 to_light_dir, vec3 normal)
{
    vec4 diffuseColour = vec4(0, 0, 0, 0);
    vec4 specColour = vec4(0, 0, 0, 0);

    // Diffuse Light
    float diffuseFactor = max(dot(normal, to_light_dir), 0.0);
    diffuseColour = diffuseC * vec4(light_colour, 1.0) * light_intensity * diffuseFactor;

    // Specular Light
    vec3 camera_direction = normalize(-position);
    vec3 from_light_dir = -to_light_dir;
    vec3 reflected_light = normalize(reflect(from_light_dir , normal));
    float specularFactor = max( dot(camera_direction, reflected_light), 0.0);
    specularFactor = pow(specularFactor, specularPower);
    specColour = speculrC * light_intensity * specularFactor * material.reflectance * vec4(light_colour, 1.0);

	LightComponents light_components;
	light_components.diffuse = diffuseColour;
	light_components.specular = specColour;

    return light_components;
}

LightComponents calcPointLight(PointLight light, vec3 position, vec3 normal)
{
    vec3 light_direction = pass_mvTangentSpace * (light.position - position);
    vec3 to_light_dir = normalize(light_direction);
    LightComponents light_components = calcLightColour(light.colour, light.intensity, position, to_light_dir, normal);

    // Apply Attenuation
    float distance = length(light_direction);
    float attenuationInv = light.att.constant + light.att.linear * distance +
        light.att.exponent * distance * distance;
    light_components.diffuse /= attenuationInv;
    light_components.specular /= attenuationInv;
        

    return light_components;
}

LightComponents calcSpotLight(SpotLight light, vec3 position, vec3 normal)
{
    vec3 light_direction = pass_mvTangentSpace * (light.pl.position - position);
    vec3 to_light_dir  = normalize(light_direction);
    vec3 from_light_dir  = -to_light_dir;
    float spot_alfa = dot(from_light_dir, normalize(light.conedir));

	LightComponents light_components;

    if ( spot_alfa > light.cutoff ) 
    {
        light_components = calcPointLight(light.pl, position, normal);
        light_components.diffuse *= (1.0 - (1.0 - spot_alfa)/(1.0 - light.cutoff));
        light_components.specular *= (1.0 - (1.0 - spot_alfa)/(1.0 - light.cutoff));
    }
    return light_components;    
}

LightComponents calcDirectionalLight(DirectionalLight light, vec3 position, vec3 normal)
{
    return calcLightColour(light.colour, light.intensity, position, pass_mvTangentSpace * normalize(light.direction), normal);
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
	vec3 vertexNormal = pass_mvVertexNormal;
	/**if(material.hasNormalMap >= 1)
	{
		vec4 normalMapValue = 2.0f * texture(normalMap_sampler, pass_textureCoords, -1.0f) -1.0f;
		// vertexNormal = clamp(normalize(pass_modelView * vec4(normalMapValue.rgb, 0.0f)).xyz, 0.0, 1.0);
    	//vertexNormal = (vertexNormal + pass_mvVertexNormal)/2.0f;
    	vertexNormal = normalize((normalMapValue).xyz);
	}**/

    setupColours(material, pass_textureCoords);

    LightComponents light_components = calcDirectionalLight(directionalLight, pass_mvVertexPos, vertexNormal);

    for (int i=0; i<MAX_POINT_LIGHTS; i++)
    {
        if ( pointLights[i].intensity > 0 )
        {
           LightComponents m_light_components = calcPointLight(pointLights[i], pass_mvVertexPos, vertexNormal);
            light_components.diffuse += m_light_components.diffuse;
            light_components.specular += m_light_components.specular; 
        }
    }

    for (int i=0; i<MAX_SPOT_LIGHTS; i++)
    {
        if ( spotLights[i].pl.intensity > 0 )
        {
            LightComponents m_light_components = calcSpotLight(spotLights[i], pass_mvVertexPos, vertexNormal);
            light_components.diffuse += m_light_components.diffuse;
            light_components.specular += m_light_components.specular; 
        }
    }

    fragColor = light_components.diffuse * ambientC * vec4(ambientLight, 1) + (light_components.specular);
	if ( fog.activeFog == 1 ) 
	{
	    fragColor = calcFog(pass_mvVertexPos, fragColor, fog, ambientLight, directionalLight);
	}
}