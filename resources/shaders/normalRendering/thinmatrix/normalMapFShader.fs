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

in vec2 pass_textureCoordinates;
in vec3 toCameraVector;
in float visibility;
in PointLight pass_pointLights[4];

out vec4 out_Color;

uniform sampler2D modelTexture;
uniform sampler2D normalMap;
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColour;

void main(void){

	vec4 normalMapValue = 2.0 * texture(normalMap, pass_textureCoordinates, -1.0) - 1.0;

	vec3 unitNormal = normalize(normalMapValue.rgb);
	vec3 unitVectorToCamera = normalize(toCameraVector);
	
	vec3 totalDiffuse = vec3(0.0);
	vec3 totalSpecular = vec3(0.0);
	
	for(int i=0;i<4;i++)
	{
		PointLight light = pass_pointLights[i];

		vec3 transformedPosition = light.position;

		float distance = length(transformedPosition);
		float attFactor = light.attenuation.constant + (light.attenuation.linear * distance) + (light.attenuation.exponent * distance * distance);
		vec3 unitLightVector = normalize(transformedPosition);	
		float nDotl = dot(unitNormal,unitLightVector);
		float brightness = max(nDotl,0.0);
		vec3 lightDirection = -unitLightVector;
		vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
		float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
		specularFactor = max(specularFactor,0.0);
		float dampedFactor = pow(specularFactor,shineDamper);
		float specular = dampedFactor * reflectivity;

		//if(brightness > 0 || specular > 0)
		//{
			totalDiffuse = totalDiffuse + (brightness * light.colour)/attFactor;
			totalSpecular = totalSpecular + (specular * light.colour)/attFactor;
		//}
	}
	totalDiffuse = max(totalDiffuse, 0.2);
	
	vec4 textureColour = texture(modelTexture,pass_textureCoordinates, -1.0);
	if(textureColour.a<0.5){
		discard;
	}

	out_Color =  vec4(totalDiffuse,1.0) * textureColour + vec4(totalSpecular,1.0);
	out_Color = mix(vec4(skyColour,1.0),out_Color, visibility);

}