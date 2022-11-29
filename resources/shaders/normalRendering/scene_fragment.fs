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

vec4 diffuse;
vec4 specular;

void calcLightColour(vec3 light_colour, float light_intensity, vec3 position, vec3 to_light_dir, vec3 normal)
{
    // Diffuse Light
    float diffuseFactor = max(dot(normal, to_light_dir), 0.0);
    diffuse = diffuseC * vec4(light_colour, 1.0) * light_intensity * diffuseFactor;

    // Specular Light
    vec3 camera_direction = normalize(-position);
    vec3 from_light_dir = -to_light_dir;
    vec3 reflected_light = normalize(reflect(from_light_dir , normal));
    float specularFactor = max( dot(camera_direction, reflected_light), 0.0);
    specularFactor = pow(specularFactor, specularPower);
    specular = speculrC * light_intensity  * specularFactor * material.reflectance * vec4(light_colour, 1.0);
}

void calcPointLight(PointLight light, vec3 position, vec3 normal)
{
    vec3 light_direction = light.position - position;
    vec3 to_light_dir  = normalize(light_direction);
    calcLightColour(light.colour, light.intensity, position, to_light_dir, normal);

    // Apply Attenuation
    float distance = length(light_direction);
    float attenuationInv = light.attenuation.constant + light.attenuation.linear * distance +
        light.attenuation.exponent * distance * distance;

	diffuse /= attenuationInv;
	specular /= attenuationInv;
}

void calcSpotLight(SpotLight light, vec3 position, vec3 normal)
{
    vec3 light_direction = light.pl.position - position;
    vec3 to_light_dir  = normalize(light_direction);
    vec3 from_light_dir  = -to_light_dir;
    float spot_alfa = dot(from_light_dir, normalize(light.conedir));

    if ( spot_alfa > light.cutoff ) 
    {
        calcPointLight(light.pl, position, normal);
        float factor = (1.0 - (1.0 - spot_alfa)/(1.0 - light.cutoff));
		diffuse *= factor;
		specular *= factor;
    }
}

void calcDirectionalLight(DirectionalLight light, vec3 position, vec3 normal)
{
    calcLightColour(light.colour, light.intensity, position, normalize(light.direction), normal);
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
        newNormal = normalize(texture(normalMap_sampler, text_coord, -1.0f).rgb * 2.0f - 1.0f);
        newNormal = normalize(modelViewMatrix * vec4(newNormal, 0.0)).xyz;
    }
    return newNormal;
}

float calcShadow(vec4 positionIn, vec3 normalIn, DirectionalLight lightIn)
{
    // (vv all done from dir light's perspective)

    // perform perspective divide (used later w/perspective projection; not needed w/orthographic proj)
    vec3 projCoords = positionIn.xyz / positionIn.w;
    // transform to [0,1] range
    projCoords = projCoords * 0.5 + 0.5;
  	//projCoords = clamp(projCoords, 0.0f, 1.0f);

    float shadow = 1.0f;
   	/**if(projCoords.z <= 1.00f)
   	{
   		return 0.0f;
   	}**/

    	vec3 L = normalize(-lightIn.direction); // calc to light vector
        /*
        float bias = max(0.005 * (1.0 - dot(normalIn, L)), 0.0015);  // calc bias (to avoid 'shadow acne' // moiré pattern aliasing)
         */
        // impl PCF (percentage-closer filtering) to produce softer shadows
        float currentDepth = projCoords.z;  // get depth of current frag
        float closestDepth = texture(shadowMap_sampler, projCoords.xy).r;   // get closest depth value
        float bias = max(0.05 * (1.0 - dot(normalIn, L)), 0.005);  // calc bias (to avoid 'shadow acne' // moiré pattern aliasing)
		shadow = (currentDepth - bias) / positionIn.w >= closestDepth  ? 1.0 : 0.0; // check whether current frag pos is in shadow

		float cosTheta = clamp( dot( normalIn,L ), 0.0f ,1.0f );
		bias = max(0.0020f * (tan(acos(cosTheta))), 0.0025f);  // calc bias (to avoid 'shadow acne' // moiré pattern aliasing)

        vec2 texelSize = 1.0 / textureSize(shadowMap_sampler, 0);

        int pcfLength = 2;
        for(int x = -pcfLength; x <= pcfLength; ++x)
        {
            for(int y = -pcfLength; y <= pcfLength; ++y)
            {
                float pcfDepth = texture(shadowMap_sampler, projCoords.xy + vec2(x, y) * texelSize).r;
                shadow += (currentDepth - bias) / positionIn.w >= pcfDepth ? 1.0 : 0.0;
            }
        }
        shadow /= (pcfLength * 2.0f + 1.0f) * (pcfLength * 2.0f + 1.0f);
	
    return shadow;
}

void main()
{
	    setupColours(material, pass_textureCoords);
	
	    vec3 currNomal = calcNormal(material, pass_mvVertexNormal, pass_textureCoords, pass_modelView);
	
	    vec4 totalDiffuse = vec4(0.0f);
	    vec4 totalSpecular = vec4(0.0f);

	    calcDirectionalLight(directionalLight, pass_mvVertexPos, currNomal);

	    totalDiffuse += diffuse;
	    totalDiffuse += totalSpecular;
	
	   /** for (int i=0; i<MAX_POINT_LIGHTS; i++)
	    {
	        if ( pointLights[i].intensity > 0 )
	        {
	            calcPointLight(pointLights[i], pass_mvVertexPos, currNomal); 
			    totalDiffuse += diffuse;
			    totalDiffuse += totalSpecular;
	        }
	    }
	
	    for (int i=0; i<MAX_SPOT_LIGHTS; i++)
	    {
	        if ( spotLights[i].pl.intensity > 0 )
	        {
	            calcSpotLight(spotLights[i], pass_mvVertexPos, currNomal);
			    totalDiffuse += diffuse;
			    totalDiffuse += totalSpecular;
	        }
	    }**/
		totalDiffuse = max(totalDiffuse, 0.2);
	    
	    float shadowFactor = calcShadow(pass_mlightviewVertexPos, currNomal, directionalLight);
	    
	    totalDiffuse.w = 1.0f;
	    totalSpecular.w = 1.0f;
		fragColor =  clamp(ambientC * vec4(ambientLight, 1) + ambientC * totalDiffuse * shadowFactor + totalSpecular * shadowFactor, 0.0f, 1.0f);
	
	    if ( fog.activeFog == 1 ) 
	    {
	        fragColor = calcFog(pass_mvVertexPos, fragColor, fog, ambientLight, directionalLight);
	    }
}