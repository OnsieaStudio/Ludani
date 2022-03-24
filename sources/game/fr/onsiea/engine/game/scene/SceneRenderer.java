/**
* Copyright 2021 Onsiea All rights reserved.<br><br>
*
* This file is part of Onsiea Engine project. (https://github.com/Onsiea/OnsieaEngine)<br><br>
*
* Onsiea Engine is [licensed] (https://github.com/Onsiea/OnsieaEngine/blob/main/LICENSE) under the terms of the "GNU General Public Lesser License v3.0" (GPL-3.0).
* https://github.com/Onsiea/OnsieaEngine/wiki/License#license-and-copyright<br><br>
*
* Onsiea Engine is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 3.0 of the License, or
* (at your option) any later version.<br><br>
*
* Onsiea Engine is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU Lesser General Public License for more details.<br><br>
*
* You should have received a copy of the GNU Lesser General Public License
* along with Onsiea Engine.  If not, see <https://www.gnu.org/licenses/>.<br><br>
*
* Neither the name "Onsiea", "Onsiea Engine", or any derivative name or the names of its authors / contributors may be used to endorse or promote products derived from this software and even less to name another project or other work without clear and precise permissions written in advance.<br><br>
*
* @Author : Seynax (https://github.com/seynax)<br>
* @Organization : Onsiea Studio (https://github.com/Onsiea)
*/
package fr.onsiea.engine.game.scene;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

import fr.onsiea.engine.client.graphics.fog.Fog;
import fr.onsiea.engine.client.graphics.mesh.IMaterialMesh;
import fr.onsiea.engine.client.graphics.opengl.fbo.FBO;
import fr.onsiea.engine.client.graphics.opengl.flare.FlareManager;
import fr.onsiea.engine.client.graphics.opengl.nanovg.NanoVGManager;
import fr.onsiea.engine.client.graphics.opengl.postprocessing.PostProcessing;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformDirectionalLight;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformPointLight;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformSpotLight;
import fr.onsiea.engine.client.graphics.opengl.shaders.ShaderAnimatedBasic;
import fr.onsiea.engine.client.graphics.opengl.shaders.ShaderBasic;
import fr.onsiea.engine.client.graphics.opengl.shaders.effects.AnimatedShadowShader;
import fr.onsiea.engine.client.graphics.opengl.shaders.effects.ShadowShader;
import fr.onsiea.engine.client.graphics.opengl.shaders.normalMapping.ShaderNormalMappingThinMatrix;
import fr.onsiea.engine.client.graphics.opengl.shadow.ShadowMap;
import fr.onsiea.engine.client.graphics.opengl.skybox.SkyboxRenderer;
import fr.onsiea.engine.client.graphics.opengl.utils.OpenGLUtils;
import fr.onsiea.engine.client.graphics.particles.ParticlesManager;
import fr.onsiea.engine.client.graphics.render.IRenderAPIContext;
import fr.onsiea.engine.client.graphics.shader.IShadersManager;
import fr.onsiea.engine.client.graphics.window.IWindow;
import fr.onsiea.engine.core.entity.Camera;
import fr.onsiea.engine.game.scene.item.GameItem;
import fr.onsiea.engine.utils.maths.MathInstances;
import fr.onsiea.engine.utils.maths.projections.Projections;
import fr.onsiea.engine.utils.maths.transformations.Transformations3f;
import fr.onsiea.engine.utils.maths.view.View3f;

/**
 * @author Seynax
 *
 */
public class SceneRenderer
{
	// Static matrices and vector instances

	private final static Matrix4f				lightViewMatrix	= new Matrix4f().identity();
	private final static Matrix4f				orthoProjMatrix	= new Matrix4f().identity();
	private final static Matrix4f				TRANSPOSE_MAT	= new Matrix4f();
	// private final static Vector4f	TRANSPOSE_VEC	= new Vector4f();

	// Static 2D rectangle positions

	// private static final float[]				POSITIONS		=
	// { -1.0f, -1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f };

	// Base instances

	private final IShadersManager				shadersManager;
	// private final IRenderAPIContext	renderAPIContext;
	private final NanoVGManager					nanoVG;

	// Clear color

	private final Vector4f						clearColor;

	// Base shader

	private final ShaderBasic					shaderBasic;
	private final ShaderAnimatedBasic			shaderAnimatedBasic;
	private final ShaderNormalMappingThinMatrix	shaderNormalMappingThinMatrix;
	//private InstancedShader							shader;

	// Shadow

	private final ShadowShader					shadowShader;
	private final AnimatedShadowShader			animatedShadowShader;
	private final ShadowMap						shadowMap;

	// 2D

	// private final Shader2D						shader2D;
	// private final IMesh							rectangleMesh;

	// Skybox

	private final SkyboxRenderer				skyboxRenderer;

	// PostProcessing

	private final PostProcessing				postProcessing;
	private final FBO							postProcessingFBO;

	// Lens flare

	private final FlareManager					flareManager;

	public SceneRenderer(IRenderAPIContext renderAPIContextIn, IWindow windowIn, float clearColorRIn,
			float clearColorGIn, float clearColorBIn, Fog fogIn, Camera cameraIn, FlareManager flareManagerIn,
			SkyboxRenderer skyboxRendererIn, Vector3f ambientLightIn) throws Exception
	{
		// Base instances

		// this.renderAPIContext	= renderAPIContextIn;
		this.shadersManager	= renderAPIContextIn.shadersManager();
		this.nanoVG			= new NanoVGManager(windowIn);

		// ClearColor

		this.clearColor		= new Vector4f();
		this.clearColor.x	= clearColorRIn;
		this.clearColor.y	= clearColorGIn;
		this.clearColor.z	= clearColorBIn;
		this.clearColor.w	= 1.0f;

		this.clearColor(this.clearColor);

		// Base shader

		this.shaderBasic = (ShaderBasic) renderAPIContextIn.shadersManager().get("scene");
		this.shaderBasic.attach();
		this.shaderBasic.fog().load(fogIn);
		this.shadersManager.detach();
		this.shaderAnimatedBasic = (ShaderAnimatedBasic) renderAPIContextIn.shadersManager().get("animatedScene");
		this.shaderAnimatedBasic.attach();
		this.shaderAnimatedBasic.fog().load(fogIn);
		this.shadersManager.detach();
		this.shaderNormalMappingThinMatrix = (ShaderNormalMappingThinMatrix) renderAPIContextIn.shadersManager()
				.get("normalMappingThinMatrix");
		this.shaderNormalMappingThinMatrix.attach();
		this.shaderNormalMappingThinMatrix.fog().load(fogIn);
		this.shaderNormalMappingThinMatrix.ambientLight().load(ambientLightIn);
		this.shadersManager.detach();

		// Shadow

		this.shadowShader			= (ShadowShader) renderAPIContextIn.shadersManager().get("shadow");
		this.animatedShadowShader	= (AnimatedShadowShader) renderAPIContextIn.shadersManager().get("animatedShadow");
		this.shadowMap				= new ShadowMap(renderAPIContextIn.texturesManager());

		// 2D

		// this.shader2D				= (Shader2D) renderAPIContextIn.shadersManager().get("2D");
		// this.rectangleMesh			= renderAPIContextIn.meshsManager().create(SceneRenderer.POSITIONS, 2);

		// Skybox

		this.skyboxRenderer			= skyboxRendererIn;

		// PostProcessing

		this.postProcessing			= new PostProcessing(windowIn, renderAPIContextIn);
		this.postProcessingFBO		= new FBO(windowIn.settings().width(), windowIn.settings().height(), windowIn);

		// Lens flare

		this.flareManager			= flareManagerIn;

		this.shadersManager.updateProjectionAndView(MathInstances.projectionMatrix(), cameraIn);

		GL11.glFrontFace(GL11.GL_CCW);
	}

	/**
	 * @param rIn
	 * @param gIn
	 * @param bIn
	 * @return
	 */
	public final SceneRenderer clearColor(float rIn, float gIn, float bIn)
	{
		this.clearColor.set(rIn, gIn, bIn, 1.0f);
		GL11.glClearColor(rIn, gIn, bIn, 1.0f);

		return this;
	}

	/**
	 * @param rIn
	 * @param gIn
	 * @param bIn
	 * @param aIn
	 * @return
	 */
	public final SceneRenderer clearColor(float rIn, float gIn, float bIn, float aIn)
	{
		this.clearColor.set(rIn, gIn, bIn, aIn);
		GL11.glClearColor(rIn, gIn, bIn, aIn);

		return this;
	}

	/**
	 * @param clearColorIn
	 */
	public void clearColor(Vector3f clearColorIn)
	{
		this.clearColor.set(clearColorIn, 1.0f);
		GL11.glClearColor(clearColorIn.x(), clearColorIn.y(), clearColorIn.z(), 1.0f);
	}

	/**
	 * @param clearColorIn
	 */
	public void clearColor(Vector4f clearColorIn)
	{
		this.clearColor.set(clearColorIn);
		GL11.glClearColor(clearColorIn.x(), clearColorIn.y(), clearColorIn.z(), 1.0f);
	}

	public final SceneRenderer render(Scene sceneIn, IWindow windowIn, boolean postProcessingIn)
	{
		this.shadersManager.updateView(sceneIn.camera());

		if (postProcessingIn)
		{
			this.postProcessingFBO.start();
		}

		this.clearColor(this.clearColor);
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_COLOR_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
		OpenGLUtils.restoreState();

		this.renderDepthMap(sceneIn, windowIn);
		GL11.glDisable(GL11.GL_CULL_FACE);
		/**this.shader2D.attach();
		this.rectangleMesh.attach();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.shadowMap.depthMapTexture()); // this.shadowMap.depthMapTexture().attach();
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, SceneRenderer.POSITIONS.length / 2);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0); // this.shadowMap.depthMapTexture().detach();
		this.rectangleMesh.detach();
		this.shadersManager.detach();**/

		this.renderSkybox(sceneIn);

		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_FRONT);
		this.renderItems(sceneIn);

		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_FRONT);
		this.renderAnimatedItems(sceneIn);

		if (sceneIn.hasSun())
		{
			this.flareManager.render(sceneIn.camera(), sceneIn.sunPosition(), windowIn);
		}

		for (final ParticlesManager<?> particlesManager : sceneIn.particlesManagers().values())
		{
			particlesManager.render();
		}

		if (postProcessingIn)
		{
			this.postProcessingFBO.stop(windowIn);

			this.postProcessing.doPostProcessing(this.postProcessingFBO.colourTexture(), windowIn);
		}

		/**this.nanoVG.startRender(windowIn);
		this.nanoVG.nanoVGFonts().draw(42, "ARIAL", NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP,
				NanoVGManager.set(this.color, 100, 125, 127, 255), 0, 0, "Ceci est un texte !");
		this.nanoVG.finishRender();**/

		return this;
	}

	public static Matrix4f lookAt(Vector3f eye, Vector3f center, Vector3f up)
	{

		final var	f	= center.sub(eye).normalize();
		var			u	= up.normalize();
		final var	s	= f.cross(u).normalize();
		u = s.cross(f);

		final var result = new Matrix4f().identity();
		result.set(0, 0, s.x);
		result.set(1, 0, s.y);
		result.set(2, 0, s.z);
		result.set(0, 1, u.x);
		result.set(1, 1, u.y);
		result.set(2, 1, u.z);
		result.set(0, 2, -f.x);
		result.set(1, 2, -f.y);
		result.set(2, 2, -f.z);

		return result.translate(new Vector3f(-eye.x, -eye.y, -eye.z));
	}

	private final void renderDepthMap(Scene sceneIn, IWindow windowIn)
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, this.shadowMap.depthMapFBO());
		GL11.glViewport(0, 0, ShadowMap.SHADOW_MAP_WIDTH, ShadowMap.SHADOW_MAP_HEIGHT);
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);

		this.shadowShader.attach();

		final var	lightDirection	= sceneIn.sceneLights().directionalLight().direction();

		final var	lightAngleX		= (float) Math.toDegrees(Math.acos(lightDirection.z()));
		final var	lightAngleY		= (float) Math.toDegrees(Math.asin(lightDirection.x()));
		final var	lightAngleZ		= 0F;

		View3f.view(new Vector3f(lightDirection).mul(sceneIn.sceneLights().directionalLight().shadowPosMult()),
				new Vector3f(lightAngleX, lightAngleY, lightAngleZ), SceneRenderer.lightViewMatrix);
		final var orthCoords = sceneIn.sceneLights().directionalLight().orthoCoords();
		Projections.ortho(orthCoords.left, orthCoords.right, orthCoords.bottom, orthCoords.top, orthCoords.near,
				orthCoords.far, SceneRenderer.orthoProjMatrix);
		this.shadowShader.orthoProjection().load(SceneRenderer.orthoProjMatrix);

		GL11.glEnable(GL11.GL_CULL_FACE);
		sceneIn.sceneItems().execute((gameItemPropertiesIn, gameItemsIn) ->
		{
			if (gameItemPropertiesIn.name().contentEquals("rockPlane"))
			{
				GL11.glCullFace(GL11.GL_BACK);
			}
			else
			{
				GL11.glCullFace(GL11.GL_FRONT);
			}

			gameItemPropertiesIn.mesh().attach();

			for (final GameItem gameItem : gameItemsIn)
			{
				final var	transformations			= Transformations3f.transformations(gameItem.position(),
						gameItem.orientation(), gameItem.scale());

				final var	modelLightViewMatrix	= SceneRenderer.TRANSPOSE_MAT.set(SceneRenderer.lightViewMatrix)
						.mul(transformations);
				this.shadowShader.modelLightView().load(modelLightViewMatrix);

				gameItemPropertiesIn.mesh().draw();
			}

			gameItemPropertiesIn.mesh().detach();
		});

		this.shadersManager.detach();
		this.animatedShadowShader.attach();
		this.animatedShadowShader.orthoProjection().load(SceneRenderer.orthoProjMatrix);

		GL11.glCullFace(GL11.GL_BACK);
		sceneIn.sceneItems().executeAnimated((gameItemPropertiesIn, gameItemsIn) ->
		{
			for (final IMaterialMesh mesh : gameItemPropertiesIn.meshes())
			{
				mesh.attach();

				for (final GameItem gameItem : gameItemsIn)
				{
					final var	transformations			= Transformations3f.transformations(gameItem.position(),
							gameItem.orientation(), gameItem.scale());

					final var	modelLightViewMatrix	= SceneRenderer.TRANSPOSE_MAT.set(SceneRenderer.lightViewMatrix)
							.mul(transformations);
					this.animatedShadowShader.modelLightView().load(modelLightViewMatrix);

					final var	frame	= gameItemPropertiesIn.getCurrentFrame();

					var			i		= 0;
					for (final Matrix4f joint : frame.getJointMatrices())
					{
						this.animatedShadowShader.jointsMatrix()[i].load(joint);

						i++;
					}

					mesh.draw();
				}

				mesh.detach();
			}
		});

		this.shadersManager.detach();
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
		GL11.glViewport(0, 0, windowIn.settings().width(), windowIn.settings().height());
	}

	private final void renderSkybox(Scene sceneIn)
	{
		this.skyboxRenderer.attach(sceneIn.fog(), sceneIn.sceneLights().ambientLight(),
				sceneIn.sceneLights().directionalLight());
		this.skyboxRenderer.draw();
		this.skyboxRenderer.detach();
	}

	private final void renderItems(Scene sceneIn)
	{
		GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
		this.shaderNormalMappingThinMatrix.attach();
		this.shaderNormalMappingThinMatrix.plane().load(new Vector4f(0, -1, 0, 100000));
		this.shaderNormalMappingThinMatrix.specularPower().load(sceneIn.sceneLights().specularPower());
		this.shaderNormalMappingThinMatrix.orthoProjection().load(SceneRenderer.orthoProjMatrix);

		((GLUniformDirectionalLight) this.shaderNormalMappingThinMatrix.directionalLight())
				.load(sceneIn.sceneLights().directionalLight(), sceneIn.camera().view());
		var i = 0;
		for (final var pointLight : sceneIn.sceneLights().pointLights())
		{
			if (i < this.shaderNormalMappingThinMatrix.pointLights().length)
			{
				((GLUniformPointLight) this.shaderNormalMappingThinMatrix.pointLights()[i]).load(pointLight,
						sceneIn.camera().view());
			}
			i++;
		}

		i = 0;
		for (final var spotLight : sceneIn.sceneLights().spotLights())
		{
			if (i < this.shaderNormalMappingThinMatrix.spotLights().length)
			{
				((GLUniformSpotLight) this.shaderNormalMappingThinMatrix.spotLights()[i]).load(spotLight,
						sceneIn.camera().view());
			}
			i++;
		}

		sceneIn.sceneItems().execute((gameItemPropertiesIn, gameItemsIn) ->
		{
			if (gameItemPropertiesIn.name().contentEquals("rockPlane"))
			{
				GL11.glCullFace(GL11.GL_FRONT);
			}
			else
			{
				GL11.glCullFace(GL11.GL_BACK);
			}
			gameItemPropertiesIn.mesh().attach();
			this.shaderNormalMappingThinMatrix.material().load(gameItemPropertiesIn.material());

			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			gameItemPropertiesIn.material().texture().attach();

			if (gameItemPropertiesIn.material().hasNormalMap())
			{
				GL13.glActiveTexture(GL13.GL_TEXTURE1);
				gameItemPropertiesIn.material().normalMap().attach();
			}
			GL13.glActiveTexture(GL13.GL_TEXTURE2);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.shadowMap.depthMapTexture()); //this.shadowMap.depthMapTexture().attach();

			for (final GameItem gameItem : gameItemsIn)
			{
				final var	transformations			= Transformations3f.transformations(gameItem.position(),
						gameItem.orientation(), gameItem.scale());

				final var	modelLightViewMatrix	= SceneRenderer.TRANSPOSE_MAT.set(SceneRenderer.lightViewMatrix)
						.mul(transformations);
				this.shaderNormalMappingThinMatrix.modelLightView().load(modelLightViewMatrix);

				this.shaderNormalMappingThinMatrix.transformations().load(transformations);

				gameItemPropertiesIn.mesh().draw();
			}

			GL13.glActiveTexture(GL13.GL_TEXTURE1);
			if (gameItemPropertiesIn.material().hasNormalMap())
			{
				gameItemPropertiesIn.material().normalMap().detach();
			}
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			gameItemPropertiesIn.material().texture().detach();

			gameItemPropertiesIn.mesh().detach();
		});

		this.shadersManager.detach();
		GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
	}

	public void renderAnimatedItems(Scene sceneIn)
	{
		this.shaderAnimatedBasic.attach();
		this.shaderAnimatedBasic.ambientLight().load(sceneIn.sceneLights().ambientLight());
		((GLUniformDirectionalLight) this.shaderAnimatedBasic.directionalLight())
				.load(sceneIn.sceneLights().directionalLight(), sceneIn.camera().view());
		this.shaderAnimatedBasic.specularPower().load(sceneIn.sceneLights().specularPower());
		this.shaderAnimatedBasic.orthoProjection().load(SceneRenderer.orthoProjMatrix);
		this.shaderAnimatedBasic.selectedJointMatrix().load(-2.0f);//(float) Scene.selectedJointMatrix);

		var i = 0;
		for (final var pointLight : sceneIn.sceneLights().pointLights())
		{
			if (i < this.shaderAnimatedBasic.pointLights().length)
			{
				((GLUniformPointLight) this.shaderAnimatedBasic.pointLights()[i]).load(pointLight,
						sceneIn.camera().view());
			}
			i++;
		}

		i = 0;
		for (final var spotLight : sceneIn.sceneLights().spotLights())
		{
			if (i < this.shaderAnimatedBasic.pointLights().length)
			{
				((GLUniformSpotLight) this.shaderAnimatedBasic.spotLights()[i]).load(spotLight,
						sceneIn.camera().view());
			}
			i++;
		}

		sceneIn.sceneItems().executeAnimated((gameItemPropertiesIn, gameItemsIn) ->
		{
			for (final IMaterialMesh mesh : gameItemPropertiesIn.meshes())
			{
				final var material = mesh.material();
				mesh.attach();
				this.shaderAnimatedBasic.material().load(material);

				GL13.glActiveTexture(GL13.GL_TEXTURE0);
				material.texture().attach();

				if (material.hasNormalMap())
				{
					GL13.glActiveTexture(GL13.GL_TEXTURE1);
					material.normalMap().attach();
				}
				GL13.glActiveTexture(GL13.GL_TEXTURE2);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.shadowMap.depthMapTexture()); //this.shadowMap.depthMapTexture().attach();

				for (final GameItem gameItem : gameItemsIn)
				{
					final var transformations = Transformations3f.transformations(gameItem.position(),
							gameItem.orientation(), gameItem.scale());
					this.shaderAnimatedBasic.transformations().load(transformations);

					final var	frame	= gameItemPropertiesIn.getCurrentFrame();

					var			i0		= 0;
					for (final Matrix4f joint : frame.getJointMatrices())
					{
						this.shaderAnimatedBasic.jointsMatrix()[i0].load(joint);

						i0++;
					}

					final var modelLightViewMatrix = SceneRenderer.TRANSPOSE_MAT.set(SceneRenderer.lightViewMatrix)
							.mul(transformations);
					this.shaderAnimatedBasic.modelLightView().load(modelLightViewMatrix);

					mesh.draw();
				}

				GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0); // this.shadowMap.depthMapTexture().detach();
				//this.shadowMap.depthMapTexture().detach();
				GL13.glActiveTexture(GL13.GL_TEXTURE1);
				if (material.hasNormalMap())
				{
					material.normalMap().detach();
				}
				GL13.glActiveTexture(GL13.GL_TEXTURE0);
				material.texture().detach();

				mesh.detach();
			}
		});

		this.shadersManager.detach();
	}

	public final void cleanup()
	{
		if (this.nanoVG != null)
		{
			this.nanoVG.cleanup();
		}
	}
}