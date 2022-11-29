#version 330

struct Attenuation
{
    float constant;
    float linear;
    float exponent;
};

struct PointLight
{
	vec3 position;
	vec3 colour;
	float intensity;
	Attenuation attenuation;
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
};

const int MAX_POINT_LIGHTS = 5;
const int MAX_SPOT_LIGHTS = 5;

in float pass_selectedJointMatrix;
in float pass_selected;
in vec2 pass_textureCoords;
in vec3 pass_mvVertexNormal;
in vec3 pass_mvVertexPos;
in vec4 pass_mlightviewVertexPos;
in mat4 pass_modelView;

uniform sampler2D texture_sampler;
uniform sampler2D normalMap_sampler;
uniform sampler2D shadowMap_sampler;
uniform vec3 ambientLight;
uniform float specularPower;
uniform Material material;
uniform PointLight pointLights[MAX_POINT_LIGHTS];
uniform SpotLight spotLights[MAX_SPOT_LIGHTS];
uniform DirectionalLight directionalLight;
uniform Fog fog;

out vec4 fragColor;

vec4 ambientC;
vec4 diffuseC;
vec4 speculrC;

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

vec4 calcLightColour(vec3 light_colour, float light_intensity, vec3 position, vec3 to_light_dir, vec3 normal)
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
    specColour = speculrC * light_intensity  * specularFactor * material.reflectance * vec4(light_colour, 1.0);

    return (diffuseColour + specColour);
}

vec4 calcPointLight(PointLight light, vec3 position, vec3 normal)
{
    vec3 light_direction = light.position - position;
    vec3 to_light_dir  = normalize(light_direction);
    vec4 light_colour = calcLightColour(light.colour, light.intensity, position, to_light_dir, normal);

    // Apply Attenuation
    float distance = length(light_direction);
    float attenuationInv = light.attenuation.constant + light.attenuation.linear * distance +
        light.attenuation.exponent * distance * distance;
    return light_colour / attenuationInv;
}

vec4 calcSpotLight(SpotLight light, vec3 position, vec3 normal)
{
    vec3 light_direction = light.pl.position - position;
    vec3 to_light_dir  = normalize(light_direction);
    vec3 from_light_dir  = -to_light_dir;
    float spot_alfa = dot(from_light_dir, normalize(light.conedir));
    
    vec4 colour = vec4(0, 0, 0, 0);
    
    if ( spot_alfa > light.cutoff ) 
    {
        colour = calcPointLight(light.pl, position, normal);
        colour *= (1.0 - (1.0 - spot_alfa)/(1.0 - light.cutoff));
    }
    return colour;    
}

vec4 calcDirectionalLight(DirectionalLight light, vec3 position, vec3 normal)
{
    return calcLightColour(light.colour, light.intensity, position, normalize(light.direction), normal);
}

vec4 calcFog(vec3 pos, vec4 colour, Fog fog, vec3 ambientLight, DirectionalLight dirLight)
{
    vec3 fogColor = fog.colour * (ambientLight + dirLight.colour * dirLight.intensity);
    float distance = length(pos);
    float fogFactor = 1.0 / exp( (distance * fog.density)* (distance * fog.density));
    fogFactor = clamp( fogFactor, 0.0, 1.0 );

    vec3 resultColour = mix(fogColor, colour.xyz, fogFactor);
    return vec4(resultColour.xyz, colour.w);
}

vec3 calcNormal(Material material, vec3 normal, vec2 text_coord, mat4 modelViewMatrix)
{
    vec3 newNormal = normal;
    if ( material.hasNormalMap == 1 )
    {
        newNormal = texture(normalMap_sampler, text_coord).rgb;
        newNormal = normalize(newNormal * 2 - 1);
        newNormal = normalize(modelViewMatrix * vec4(newNormal, 0.0)).xyz;
    }
    return newNormal;
}

float calcShadow(vec4 position)
{
    vec3 projCoords = position.xyz / position.w;
    // Transform from screen coordinates to texture coordinates

    float bias = 0.01f;
   
    float shadowFactor = 0.0;
	vec2 inc = 1.0 / textureSize(shadowMap_sampler, 0);
	for(int row = -1; row <= 1; ++row)
	{
	    for(int col = -1; col <= 1; ++col)
	    {
	        float textDepth = texture(shadowMap_sampler, projCoords.xy + vec2(row, col) * inc).r; 
	        shadowFactor += projCoords.z - bias >= textDepth ? 1.0 : 0.0;
	    }
	}
	shadowFactor /= 9.0;

    if(projCoords.z >= 1.0)
    {
        shadowFactor = 1.0;
    }

    return 1 - shadowFactor;
} 

void main()
{
	if(pass_selected >= 5.0f)
	{
		fragColor = vec4(1.0f, pass_selectedJointMatrix/33.0f, pass_selectedJointMatrix/33.0f, 1.0f);
	}
	else
	{
	    setupColours(material, pass_textureCoords);
	
	    vec3 currNomal = calcNormal(material, pass_mvVertexNormal, pass_textureCoords, pass_modelView);
	
	    vec4 diffuseSpecularComp = calcDirectionalLight(directionalLight, pass_mvVertexPos, currNomal);
	
	    for (int i=0; i<MAX_POINT_LIGHTS; i++)
	    {
	        if ( pointLights[i].intensity > 0 )
	        {
	            diffuseSpecularComp += calcPointLight(pointLights[i], pass_mvVertexPos, currNomal); 
	        }
	    }
	
	    for (int i=0; i<MAX_SPOT_LIGHTS; i++)
	    {
	        if ( spotLights[i].pl.intensity > 0 )
	        {
	            diffuseSpecularComp += calcSpotLight(spotLights[i], pass_mvVertexPos, currNomal);
	        }
	    }
	    
	    float shadow = calcShadow(pass_mlightviewVertexPos);
	    
	    fragColor = clamp(ambientC * vec4(ambientLight, 1) + diffuseSpecularComp * shadow, 0, 1);
	
	    if ( fog.activeFog == 1 ) 
	    {
	        fragColor = calcFog(pass_mvVertexPos, fragColor, fog, ambientLight, directionalLight);
	    }
	}
}