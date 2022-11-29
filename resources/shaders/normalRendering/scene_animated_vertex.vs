#version 330

const int MAX_WEIGHTS = 4;
const int MAX_JOINTS = 150;

layout (location=0) in vec3 position;
layout (location=1) in vec2 texCoord;
layout (location=2) in vec3 vertexNormal;
layout (location=3) in vec4 jointWeights;
layout (location=4) in ivec4 jointIndices;

uniform mat4 jointsMatrix[MAX_JOINTS];
uniform float selectedJointMatrix;
uniform mat4 projection;
uniform mat4 view;
uniform mat4 transformations;
uniform mat4 lightProjection;
uniform mat4 lightView;
uniform mat4 bias;

out float pass_selectedJointMatrix;
out float pass_selected;
out vec2 pass_textureCoords;
out vec3 pass_mvVertexNormal;
out vec3 pass_mvVertexPos;
out vec4 pass_mlightviewVertexPos;
out mat4 pass_modelView;

void main()
{
	pass_selectedJointMatrix = selectedJointMatrix;
	pass_selected = 0.0f;

    vec4 initPos = vec4(0, 0, 0, 0);
    vec4 initNormal = vec4(0, 0, 0, 0);
    int count = 0;
    for(int i = 0; i < MAX_WEIGHTS; i++)
    {
        float weight = jointWeights[i];
        if(weight > 0) {
            count++;
            int jointIndex = jointIndices[i];

			if(jointIndex == selectedJointMatrix)
				pass_selected = 10.0f;
			

            vec4 tmpPos = jointsMatrix[jointIndex] * vec4(position, 1.0);
            initPos += weight * tmpPos;

            vec4 tmpNormal = jointsMatrix[jointIndex] * vec4(vertexNormal, 0.0);
            initNormal += weight * tmpNormal;
        }
    }
    if (count == 0)
    {
        initPos = vec4(position, 1.0);
        initNormal = vec4(vertexNormal, 0.0);
    }
	mat4 modelView = view * transformations;
    vec4 mvPos = modelView * initPos;
    gl_Position = projection * mvPos;

    pass_textureCoords = texCoord;
    pass_mvVertexNormal = normalize(modelView * initNormal).xyz;
    pass_mvVertexPos = mvPos.xyz;
    pass_modelView = modelView;
    pass_mlightviewVertexPos = bias * lightProjection * lightView * initPos;
}