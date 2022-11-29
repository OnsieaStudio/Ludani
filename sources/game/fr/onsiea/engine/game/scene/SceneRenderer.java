/**
 * Copyright 2021 Onsiea All rights reserved.<br>
 * <br>
 *
 * This file is part of Onsiea Engine project.
 * (https://github.com/Onsiea/OnsieaEngine)<br>
 * <br>
 *
 * Onsiea Engine is [licensed]
 * (https://github.com/Onsiea/OnsieaEngine/blob/main/LICENSE) under the terms of
 * the "GNU General Public Lesser License v3.0" (GPL-3.0).
 * https://github.com/Onsiea/OnsieaEngine/wiki/License#license-and-copyright<br>
 * <br>
 *
 * Onsiea Engine is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3.0 of the License, or
 * (at your option) any later version.<br>
 * <br>
 *
 * Onsiea Engine is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.<br>
 * <br>
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Onsiea Engine. If not, see <https://www.gnu.org/licenses/>.<br>
 * <br>
 *
 * Neither the name "Onsiea", "Onsiea Engine", or any derivative name or the
 * names of its authors / contributors may be used to endorse or promote
 * products derived from this software and even less to name another project or
 * other work without clear and precise permissions written in advance.<br>
 * <br>
 *
 * @Author : Seynax (https://github.com/seynax)<br>
 * @Organization : Onsiea Studio (https://github.com/Onsiea)
 */
package fr.onsiea.engine.game.scene;

import java.nio.ByteBuffer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;

import fr.onsiea.engine.client.graphics.fog.Fog;
import fr.onsiea.engine.client.graphics.mesh.IMaterialMesh;
import fr.onsiea.engine.client.graphics.opengl.fbo.FBO;
import fr.onsiea.engine.client.graphics.opengl.flare.FlareManager;
import fr.onsiea.engine.client.graphics.opengl.nanovg.NanoVGManager;
import fr.onsiea.engine.client.graphics.opengl.postprocessing.PostProcessing;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniformDirectionalLight;
import fr.onsiea.engine.client.graphics.opengl.shaders.AdvInstancedShader;
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
import fr.onsiea.engine.game.world.World;
import fr.onsiea.engine.utils.LoopUtils;
import fr.onsiea.engine.utils.maths.MathInstances;
import fr.onsiea.engine.utils.maths.transformations.Transformations3f;

/**
 * @author Seynax
 *
 */
public class SceneRenderer
{
	// Static matrices and vector instances

	public final static Matrix4f				lightViewMatrix	= new Matrix4f().identity();
	private static Matrix4f						orthoProjMatrix	= new Matrix4f().identity();
	public final static Matrix4f				TRANSPOSE_MAT	= new Matrix4f();
	// private final static Vector4f TRANSPOSE_VEC = new Vector4f();

	// Static 2D rectangle positions

	// private static final float[] POSITIONS =
	// { -1.0f, -1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f };

	// Base instances

	private final IShadersManager				shadersManager;
	// private final IRenderAPIContext renderAPIContext;
	private final NanoVGManager					nanoVG;

	// Clear color

	private final Vector4f						clearColor;

	// Base shader

	private final ShaderBasic					shaderBasic;
	private final ShaderAnimatedBasic			shaderAnimatedBasic;
	private final ShaderNormalMappingThinMatrix	shaderNormalMappingThinMatrix;
	// private InstancedShader shader;
	private final AdvInstancedShader			advInstancedShader;

	// Shadow

	private final ShadowShader					shadowShader;
	private final AnimatedShadowShader			animatedShadowShader;
	private final ShadowMap						shadowMap;

	// 2D

	// private final Shader2D shader2D;
	// private final IMesh rectangleMesh;

	// Skybox

	private final SkyboxRenderer				skyboxRenderer;

	// PostProcessing

	private final PostProcessing				postProcessing;
	private final FBO							postProcessingFBO;

	// Lens flare

	private final FlareManager					flareManager;

	// World

	private final World							world;

	// Tests

	final Matrix4f								bias			= new Matrix4f(0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.5f, 0.0f,
			0.0f, 0.0f, 0.0f, 0.5f, 0.0f, 0.5f, 0.5f, 0.5f, 1.0f);

	public SceneRenderer(final IRenderAPIContext renderAPIContextIn, final IWindow windowIn, final float clearColorRIn,
			final float clearColorGIn, final float clearColorBIn, final Fog fogIn, final Camera cameraIn,
			final FlareManager flareManagerIn, final SkyboxRenderer skyboxRendererIn, final Vector3f ambientLightIn,
			final World worldIn) throws Exception
	{
		// Base instances

		// this.renderAPIContext = renderAPIContextIn;
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

		this.advInstancedShader		= (AdvInstancedShader) renderAPIContextIn.shadersManager().get("advInstanced");
		// this.advInstancedShader.attach();
		// this.shadersManager.detach();
		// Shadow

		this.shadowShader			= (ShadowShader) renderAPIContextIn.shadersManager().get("shadow");
		this.animatedShadowShader	= (AnimatedShadowShader) renderAPIContextIn.shadersManager().get("animatedShadow");
		this.shadowMap				= new ShadowMap(renderAPIContextIn.texturesManager());

		// 2D

		// this.shader2D = (Shader2D) renderAPIContextIn.shadersManager().get("2D");
		// this.rectangleMesh =
		// renderAPIContextIn.meshsManager().create(SceneRenderer.POSITIONS, 2);

		// Skybox

		this.skyboxRenderer			= skyboxRendererIn;

		// PostProcessing

		this.postProcessing			= new PostProcessing(windowIn, renderAPIContextIn);
		this.postProcessingFBO		= new FBO(windowIn.settings().width(), windowIn.settings().height(), windowIn);

		// Lens flare

		this.flareManager			= flareManagerIn;

		this.shadersManager.updateProjectionAndView(MathInstances.projectionMatrix(), cameraIn);

		this.world = worldIn;

		GL11.glFrontFace(GL11.GL_CCW);
	}

	/**
	 * @param rIn
	 * @param gIn
	 * @param bIn
	 * @return
	 */
	public final SceneRenderer clearColor(final float rIn, final float gIn, final float bIn)
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
	public final SceneRenderer clearColor(final float rIn, final float gIn, final float bIn, final float aIn)
	{
		this.clearColor.set(rIn, gIn, bIn, aIn);
		GL11.glClearColor(rIn, gIn, bIn, aIn);

		return this;
	}

	/**
	 * @param clearColorIn
	 */
	public void clearColor(final Vector3f clearColorIn)
	{
		this.clearColor.set(clearColorIn, 1.0f);
		GL11.glClearColor(clearColorIn.x(), clearColorIn.y(), clearColorIn.z(), 1.0f);
	}

	/**
	 * @param clearColorIn
	 */
	public void clearColor(final Vector4f clearColorIn)
	{
		this.clearColor.set(clearColorIn);
		GL11.glClearColor(clearColorIn.x(), clearColorIn.y(), clearColorIn.z(), 1.0f);
	}

	public final SceneRenderer render(final Scene sceneIn, final IWindow windowIn, final boolean postProcessingIn)
	{
		this.shadersManager.updateView(sceneIn.camera());

		if (postProcessingIn)
		{
			this.postProcessingFBO.start();
		}

		OpenGLUtils.restoreState();

		this.calcDepthMapMatrices(sceneIn);
		if (Scene.depthMode)
		{
			this.clearColor(this.clearColor);
			GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_COLOR_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
			GL11.glEnable(GL11.GL_CULL_FACE);
			this.renderDepthMapColor(sceneIn);
		}
		else
		{
			this.renderDepthMap(sceneIn, windowIn);

			this.clearColor(this.clearColor);
			GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_COLOR_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);

			GL11.glDisable(GL11.GL_CULL_FACE);
			/**
			 * this.shader2D.attach();
			 * this.rectangleMesh.attach();
			 * GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.shadowMap.depthMapTexture()); //
			 * this.shadowMap.depthMapTexture().attach();
			 * GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, SceneRenderer.POSITIONS.length /
			 * 2);
			 * GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0); //
			 * this.shadowMap.depthMapTexture().detach();
			 * this.rectangleMesh.detach();
			 * this.shadersManager.detach();
			 **/

			this.renderSkybox(sceneIn);

			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glCullFace(GL11.GL_BACK);
			this.renderItems(sceneIn);

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
		}

		/**
		 * this.nanoVG.startRender(windowIn);
		 * this.nanoVG.nanoVGFonts().draw(42, "ARIAL", NanoVG.NVG_ALIGN_LEFT |
		 * NanoVG.NVG_ALIGN_TOP,
		 * NanoVGManager.set(NVGColor.create().r(5).g(25).b(150).a(255), 100, 125, 127,
		 * 255), 0, 0, "Ceci est un texte !");
		 * this.nanoVG.finishRender();
		 **/

		return this;
	}

	/**
	 * private final static Matrix4f light = new Matrix4f().identity();
	 * private final static Vector3f lightPosition = new Vector3f();
	 * private final static Vector3f lightLookAt = new Vector3f(0.0f, 1.0f, 0.0f);
	 * private final static Vector3f UP = new Vector3f(0.0f, 1.0f, 0.0f);
	 * private final static float lightDistance = 10.0f;
	 **/

	private final void calcDepthMapMatrices(final Scene sceneIn)
	{
		final var lightDirection = sceneIn.sceneLights().directionalLight().direction();

		// final var lightAngleX = (float)
		// Math.toDegrees(Math.acos(lightDirection.z()));
		// final var lightAngleY = (float)
		// Math.toDegrees(Math.asin(lightDirection.x()));
		// final var lightAngleZ = 0F;
		// final var x = (float) Math.sin(sceneIn.lightAngle());
		// final var z = (float) Math.cos(sceneIn.lightAngle());
		// SceneRenderer.lightPosition.set(SceneRenderer.lightDistance * x, 3 + (float)
		// Math.sin(sceneIn.lightAngle()),
		// SceneRenderer.lightDistance * z);

		// View3f.view(new
		// Vector3f(lightDirection).mul(sceneIn.sceneLights().directionalLight().shadowPosMult()),
		// new Vector3f(lightAngleX, lightAngleY, lightAngleZ),
		// SceneRenderer.lightViewMatrix);

		SceneRenderer.lightViewMatrix.set(new Matrix4f().lookAt(new Vector3f(lightDirection).mul(10), new Vector3f(0),
				new Vector3f(0.0f, 1.0f, 0.0f)));

		// SceneRenderer.light.identity().lookAt(
		// new
		// Vector3f(lightDirection).mul(sceneIn.sceneLights().directionalLight().shadowPosMult()),
		// SceneRenderer.lightLookAt, SceneRenderer.UP);

		// SceneRenderer.lightViewMatrix.set(SceneRenderer.light);

		SceneRenderer.orthoProjMatrix.set(new Matrix4f().ortho(-20.0f, 20.0f, -20.0f, 20.0f, -1.0f, 40f));
		// sceneIn.sceneLights().directionalLight().orthoCoords();
		// Projections.ortho(orthCoords.left, orthCoords.right, orthCoords.bottom,
		// orthCoords.top, orthCoords.near,
		// orthCoords.far, SceneRenderer.orthoProjMatrix);

		// SceneRenderer.orthoProjMatrix = new
		// Matrix4f().identity().setPerspective((float) Math.toRadians(90.0f), 1.0f,
		// 0.01f, 60.0f);
	}

	private final void renderDepthMap(final Scene sceneIn, final IWindow windowIn)
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.shadowMap.depthMapTexture());
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL14.GL_DEPTH_COMPONENT24, ShadowMap.SHADOW_MAP_WIDTH,
				ShadowMap.SHADOW_MAP_HEIGHT, 0, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, (ByteBuffer) null);

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, this.shadowMap.depthMapFBO());
		GL11.glViewport(0, 0, ShadowMap.SHADOW_MAP_WIDTH, ShadowMap.SHADOW_MAP_HEIGHT);
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);

		GL11.glDepthFunc(GL11.GL_LESS);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		// GL11.glDisable(GL11.GL_ALPHA_TEST);
		// GL11.glDisable(GL11.GL_SCISSOR_TEST);
		// GL11.glDisable(GL11.GL_STENCIL_TEST);

		GL11.glEnable(GL32.GL_DEPTH_CLAMP);

		this.renderDepthMapColor(sceneIn);

		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
		GL11.glViewport(0, 0, windowIn.settings().width(), windowIn.settings().height());
		// GL11.glEnable(GL11.GL_ALPHA_TEST);
		// GL11.glEnable(GL11.GL_SCISSOR_TEST);
		// GL11.glEnable(GL11.GL_STENCIL_TEST);
	}

	private final void renderDepthMapColor(final Scene sceneIn)
	{
		this.shadowShader.attach();
		this.shadowShader.lightProjection().load(SceneRenderer.orthoProjMatrix);
		this.shadowShader.bias().load(this.bias);

		sceneIn.sceneItems().execute((gameItemPropertiesIn, gameItemsIn) ->
		{
			OpenGLUtils.cullFace(gameItemPropertiesIn.faceCulling(), GL11.GL_CCW);
			for (final GameItem gameItem : gameItemsIn)
			{

				gameItemPropertiesIn.mesh().attach();

				final var	transformations			= Transformations3f.transformations(gameItem.position(),
						gameItem.orientation(), gameItem.scale());

				final var	modelLightViewMatrix	= SceneRenderer.TRANSPOSE_MAT.set(SceneRenderer.lightViewMatrix)
						.mul(transformations);
				this.shadowShader.lightView().load(modelLightViewMatrix);

				gameItemPropertiesIn.mesh().draw();
			}

			gameItemPropertiesIn.mesh().detach();
		});

		this.shadersManager.detach();
		this.animatedShadowShader.attach();
		this.animatedShadowShader.lightProjection().load(SceneRenderer.orthoProjMatrix);
		this.animatedShadowShader.bias().load(this.bias);

		GL11.glCullFace(GL11.GL_FRONT);
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
					this.animatedShadowShader.lightView().load(modelLightViewMatrix);

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
	}

	private final void renderSkybox(final Scene sceneIn)
	{
		this.skyboxRenderer.attach(sceneIn.fog(), sceneIn.sceneLights().ambientLight(),
				sceneIn.sceneLights().directionalLight());
		this.skyboxRenderer.draw();
		this.skyboxRenderer.detach();
	}

	private final void renderItems(final Scene sceneIn)
	{
		GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
		this.shaderNormalMappingThinMatrix.attach();
		this.shaderNormalMappingThinMatrix.plane().load(new Vector4f(0, -1, 0, 100000));
		this.shaderNormalMappingThinMatrix.specularPower().load(sceneIn.sceneLights().specularPower());
		this.shaderNormalMappingThinMatrix.lightProjection().load(SceneRenderer.orthoProjMatrix);
		this.shaderNormalMappingThinMatrix.bias().load(this.bias);

		((GLUniformDirectionalLight) this.shaderNormalMappingThinMatrix.directionalLight())
				.load(sceneIn.sceneLights().directionalLight(), sceneIn.camera().view());
		this.shaderNormalMappingThinMatrix.pointLights().load(sceneIn.sceneLights().pointLights());
		this.shaderNormalMappingThinMatrix.spotLights().load(sceneIn.sceneLights().spotLights());

		sceneIn.sceneItems().execute((gameItemPropertiesIn, gameItemsIn) ->
		{
			OpenGLUtils.cullFace(gameItemPropertiesIn.faceCulling(), GL11.GL_CCW);

			gameItemPropertiesIn.mesh().attach();
			this.shaderNormalMappingThinMatrix.material().load(gameItemPropertiesIn.material());

			LoopUtils.loop((indexIn, textureIn) -> OpenGLUtils.bindTexture(textureIn, indexIn),
					gameItemPropertiesIn.material().textures());

			GL13.glActiveTexture(GL13.GL_TEXTURE2);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.shadowMap.depthMapTexture()); // this.shadowMap.depthMapTexture().attach();

			for (final GameItem gameItem : gameItemsIn)
			{
				final var	transformations			= Transformations3f.transformations(gameItem.position(),
						gameItem.orientation(), gameItem.scale());

				final var	modelLightViewMatrix	= SceneRenderer.TRANSPOSE_MAT.set(SceneRenderer.lightViewMatrix)
						.mul(transformations);
				this.shaderNormalMappingThinMatrix.lightView().load(modelLightViewMatrix);
				this.shaderNormalMappingThinMatrix.transformations().load(transformations);

				gameItemPropertiesIn.mesh().draw();
			}

			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
			LoopUtils.loop((indexIn, textureIn) -> OpenGLUtils.unbindTexture(textureIn, indexIn),
					gameItemPropertiesIn.material().textures());

			gameItemPropertiesIn.mesh().detach();
		});

		GL13.glActiveTexture(GL13.GL_TEXTURE2);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.shadowMap.depthMapTexture()); // this.shadowMap.depthMapTexture().attach();

		this.advInstancedShader.attach();
		this.world.draw(null, null);
		this.shadersManager.detach();
		GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
	}

	public void renderAnimatedItems(final Scene sceneIn)
	{
		this.shaderAnimatedBasic.attach();
		this.shaderAnimatedBasic.ambientLight().load(sceneIn.sceneLights().ambientLight());
		((GLUniformDirectionalLight) this.shaderAnimatedBasic.directionalLight())
				.load(sceneIn.sceneLights().directionalLight(), sceneIn.camera().view());
		this.shaderAnimatedBasic.specularPower().load(sceneIn.sceneLights().specularPower());
		this.shaderAnimatedBasic.orthoProjection().load(SceneRenderer.orthoProjMatrix);
		this.shaderAnimatedBasic.selectedJointMatrix().load(-2.0f);// (float) Scene.selectedJointMatrix);
		this.shaderAnimatedBasic.bias().load(this.bias);
		this.shaderAnimatedBasic.pointLights().load(sceneIn.sceneLights().pointLights());
		this.shaderAnimatedBasic.spotLights().load(sceneIn.sceneLights().spotLights());

		sceneIn.sceneItems().executeAnimated((gameItemPropertiesIn, gameItemsIn) ->
		{
			for (final IMaterialMesh mesh : gameItemPropertiesIn.meshes())
			{
				final var material = mesh.material();
				mesh.attach();
				this.shaderAnimatedBasic.material().load(material);

				LoopUtils.loop((indexIn, textureIn) -> OpenGLUtils.bindTexture(textureIn, indexIn),
						material.textures());

				GL13.glActiveTexture(GL13.GL_TEXTURE2);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.shadowMap.depthMapTexture()); // this.shadowMap.depthMapTexture().attach();

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

				GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
				LoopUtils.loop((indexIn, textureIn) -> OpenGLUtils.unbindTexture(textureIn, indexIn),
						material.textures());

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