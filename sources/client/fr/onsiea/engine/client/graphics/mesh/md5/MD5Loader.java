package fr.onsiea.engine.client.graphics.mesh.md5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import fr.onsiea.engine.client.graphics.material.Material;
import fr.onsiea.engine.client.graphics.mesh.IMaterialMesh;
import fr.onsiea.engine.client.graphics.mesh.IMeshsManager;
import fr.onsiea.engine.client.graphics.mesh.anim.AnimVertex;
import fr.onsiea.engine.client.graphics.mesh.anim.AnimatedFrame;
import fr.onsiea.engine.client.graphics.texture.ITextureSettings;
import fr.onsiea.engine.client.graphics.texture.ITexturesManager;
import fr.onsiea.engine.game.scene.item.GameAnimatedItemProperties;
import fr.onsiea.engine.utils.ArrayUtils;
import fr.onsiea.engine.utils.file.FileUtils;

public class MD5Loader<T extends ITextureSettings<T>>
{
	private IMeshsManager		meshsManager;
	private ITexturesManager<T>	texturesManager;

	public MD5Loader()
	{

	}

	public MD5Loader<T> link(IMeshsManager meshsManagerIn, ITexturesManager<T> texturesManagerIn)
	{
		this.meshsManager		= meshsManagerIn;
		this.texturesManager	= texturesManagerIn;

		return this;
	}

	/**
	 * Constructs and GameAnimatedItemProperties instace based on a MD5 Model an MD5 Animation
	 * @param nameIn
	 *
	 * @param md5Model The MD5 Model
	 * @param animModel The MD5 Animation
	 * @param defaultColour Default colour to use if there are no textures
	 * @return
	 * @throws Exception
	 */
	public GameAnimatedItemProperties process(String nameIn, MD5Model md5Model, MD5AnimModel animModel,
			Vector4f defaultColour, ITextureSettings<T> textureSettingsIn) throws Exception
	{
		final var					invJointMatrices	= MD5Loader.calcInJointMatrices(md5Model);
		final var					animatedFrames		= MD5Loader.processAnimationFrames(md5Model, animModel,
				invJointMatrices);

		final List<IMaterialMesh>	list				= new ArrayList<>();
		for (final MD5Mesh md5Mesh : md5Model.getMeshes())
		{
			final var mesh = this.generateMesh(md5Model, md5Mesh);
			this.handleTexture(mesh, md5Mesh, defaultColour, textureSettingsIn);
			list.add(mesh);
		}
		var meshes = new IMaterialMesh[list.size()];
		meshes = list.toArray(meshes);

		return new GameAnimatedItemProperties(nameIn, meshes, animatedFrames, invJointMatrices);
	}

	private static List<Matrix4f> calcInJointMatrices(MD5Model md5Model)
	{
		final List<Matrix4f>	result	= new ArrayList<>();

		final var				joints	= md5Model.getJointInfo().getJoints();
		for (final MD5JointInfo.MD5JointData joint : joints)
		{
			// Calculate translation matrix using joint position
			// Calculates rotation matrix using joint orientation
			// Gets transformation matrix bu multiplying translation matrix by rotation matrix
			// Instead of multiplying we can apply rotation which is optimized internally
			final var mat = new Matrix4f().translate(joint.getPosition()).rotate(joint.getOrientation()).invert();
			result.add(mat);
		}
		return result;
	}

	private IMaterialMesh generateMesh(MD5Model md5Model, MD5Mesh md5Mesh) throws Exception
	{
		final List<AnimVertex>	vertices	= new ArrayList<>();
		final List<Integer>		indices		= new ArrayList<>();

		final var				md5Vertices	= md5Mesh.getVertices();
		final var				weights		= md5Mesh.getWeights();
		final var				joints		= md5Model.getJointInfo().getJoints();

		for (final MD5Mesh.MD5Vertex md5Vertex : md5Vertices)
		{
			final var vertex = new AnimVertex();
			vertices.add(vertex);

			vertex.position		= new Vector3f();
			vertex.textCoords	= md5Vertex.getTextCoords();

			final var	startWeight	= md5Vertex.getStartWeight();
			final var	numWeights	= md5Vertex.getWeightCount();

			vertex.jointIndices = new int[numWeights];
			Arrays.fill(vertex.jointIndices, -1);
			vertex.weights = new float[numWeights];
			Arrays.fill(vertex.weights, -1);
			for (var i = startWeight; i < startWeight + numWeights; i++)
			{
				final var	weight		= weights.get(i);
				final var	joint		= joints.get(weight.getJointIndex());
				final var	rotatedPos	= new Vector3f(weight.getPosition()).rotate(joint.getOrientation());
				final var	acumPos		= new Vector3f(joint.getPosition()).add(rotatedPos);
				acumPos.mul(weight.getBias());
				vertex.position.add(acumPos);
				vertex.jointIndices[i - startWeight]	= weight.getJointIndex();
				vertex.weights[i - startWeight]			= weight.getBias();
			}
		}

		for (final MD5Mesh.MD5Triangle tri : md5Mesh.getTriangles())
		{
			indices.add(tri.getVertex0());
			indices.add(tri.getVertex1());
			indices.add(tri.getVertex2());

			// Normals
			final var	v0		= vertices.get(tri.getVertex0());
			final var	v1		= vertices.get(tri.getVertex1());
			final var	v2		= vertices.get(tri.getVertex2());
			final var	pos0	= v0.position;
			final var	pos1	= v1.position;
			final var	pos2	= v2.position;

			final var	normal	= new Vector3f(pos2).sub(pos0).cross(new Vector3f(pos1).sub(pos0));

			v0.normal.add(normal);
			v1.normal.add(normal);
			v2.normal.add(normal);
		}

		// Once the contributions have been added, normalize the result
		for (final AnimVertex v : vertices)
		{
			v.normal.normalize();
		}

		return this.createMesh(vertices, indices);
	}

	private static List<AnimatedFrame> processAnimationFrames(MD5Model md5Model, MD5AnimModel animModel,
			List<Matrix4f> invJointMatrices)
	{
		final List<AnimatedFrame>	animatedFrames	= new ArrayList<>();
		final var					frames			= animModel.getFrames();
		for (final MD5Frame frame : frames)
		{
			final var data = MD5Loader.processAnimationFrame(md5Model, animModel, frame, invJointMatrices);
			animatedFrames.add(data);
		}
		return animatedFrames;
	}

	private static AnimatedFrame processAnimationFrame(MD5Model md5Model, MD5AnimModel animModel, MD5Frame frame,
			List<Matrix4f> invJointMatrices)
	{
		final var	result			= new AnimatedFrame();

		final var	baseFrame		= animModel.getBaseFrame();
		final var	hierarchyList	= animModel.getHierarchy().getHierarchyDataList();

		final var	joints			= md5Model.getJointInfo().getJoints();
		final var	numJoints		= joints.size();
		final var	frameData		= frame.getFrameData();
		for (var i = 0; i < numJoints; i++)
		{
			final var	joint			= joints.get(i);
			final var	baseFrameData	= baseFrame.getFrameDataList().get(i);
			final var	position		= baseFrameData.getPosition();
			var			orientation		= baseFrameData.getOrientation();

			final var	flags			= hierarchyList.get(i).getFlags();
			var			startIndex		= hierarchyList.get(i).getStartIndex();

			if ((flags & 1) != 0)
			{
				position.x = frameData[startIndex];
				startIndex++;
			}
			if ((flags & 2) != 0)
			{
				position.y = frameData[startIndex];
				startIndex++;
			}
			if ((flags & 4) != 0)
			{
				position.z = frameData[startIndex];
				startIndex++;
			}
			if ((flags & 8) != 0)
			{
				orientation.x = frameData[startIndex];
				startIndex++;
			}
			if ((flags & 16) != 0)
			{
				orientation.y = frameData[startIndex];
				startIndex++;
			}
			if ((flags & 32) != 0)
			{
				orientation.z = frameData[startIndex];
				startIndex++;
			}
			// Update Quaternion's w component
			orientation = MD5Utils.calculateQuaternion(orientation.x, orientation.y, orientation.z);

			// Calculate translation and rotation matrices for this joint
			final var	translateMat	= new Matrix4f().translate(position);
			final var	rotationMat		= new Matrix4f().rotate(orientation);
			var			jointMat		= translateMat.mul(rotationMat);

			// Joint position is relative to joint's parent index position. Use parent matrices
			// to transform it to model space
			if (joint.getParentIndex() > -1)
			{
				final var parentMatrix = result.getLocalJointMatrices()[joint.getParentIndex()];
				jointMat = new Matrix4f(parentMatrix).mul(jointMat);
			}

			result.setMatrix(i, jointMat, invJointMatrices.get(i));
		}

		return result;
	}

	private IMaterialMesh createMesh(List<AnimVertex> vertices, List<Integer> indices) throws Exception
	{
		final List<Float>	positions		= new ArrayList<>();
		final List<Float>	textCoords		= new ArrayList<>();
		final List<Float>	normals			= new ArrayList<>();
		final List<Integer>	jointIndices	= new ArrayList<>();
		final List<Float>	weights			= new ArrayList<>();

		for (final AnimVertex vertex : vertices)
		{
			positions.add(vertex.position.x);
			positions.add(vertex.position.y);
			positions.add(vertex.position.z);

			textCoords.add(vertex.textCoords.x);
			textCoords.add(vertex.textCoords.y);

			normals.add(vertex.normal.x);
			normals.add(vertex.normal.y);
			normals.add(vertex.normal.z);

			final var numWeights = vertex.weights.length;
			for (var i = 0; i < GameAnimatedItemProperties.MAX_WEIGHTS; i++)
			{
				if (i < numWeights)
				{
					jointIndices.add(vertex.jointIndices[i]);
					weights.add(vertex.weights[i]);
				}
				else
				{
					jointIndices.add(-1);
					weights.add(-1.0f);
				}
			}
		}

		final var	positionsArr	= ArrayUtils.listToArray(positions);
		final var	textCoordsArr	= ArrayUtils.listToArray(textCoords);
		final var	normalsArr		= ArrayUtils.listToArray(normals);
		final var	indicesArr		= ArrayUtils.listIntToArray(indices);
		final var	jointIndicesArr	= ArrayUtils.listIntToArray(jointIndices);
		final var	weightsArr		= ArrayUtils.listToArray(weights);

		return this.meshsManager.createMeshWithMaterial(positionsArr, textCoordsArr, normalsArr, indicesArr,
				jointIndicesArr, weightsArr, 3);
	}

	private void handleTexture(IMaterialMesh mesh, MD5Mesh md5Mesh, Vector4f defaultColour,
			ITextureSettings<T> textureSettingsIn) throws Exception
	{
		final var texturePath = md5Mesh.getTexture();
		if (texturePath != null && texturePath.length() > 0)
		{
			final var	texture		= this.texturesManager.load(texturePath, textureSettingsIn);
			final var	material	= new Material(texture);

			// Handle normal Maps;
			final var	pos			= texturePath.lastIndexOf(".");
			if (pos > 0)
			{
				final var	basePath			= texturePath.substring(0, pos);
				final var	extension			= texturePath.substring(pos);
				final var	normalMapFileName	= basePath + "_local" + extension;
				if (FileUtils.exists(normalMapFileName))
				{
					final var normalMap = this.texturesManager.load(normalMapFileName, textureSettingsIn);
					material.textures().add(normalMap);
				}
			}
			mesh.material(material);
		}
		else
		{
			mesh.material(new Material(defaultColour, 1));
		}
	}
}
