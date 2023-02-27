
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
 * the "GNU General Public Lesser License v2.1" (LGPL-2.1).
 * https://github.com/Onsiea/OnsieaEngine/wiki/License#license-and-copyright<br>
 * <br>
 *
 * Onsiea Engine is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
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
package fr.onsiea.engine.game;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.opengl.GL11;

import fr.onsea.logger.FileLogger;
import fr.onsea.logger.Loggers;
import fr.onsiea.engine.client.graphics.fog.Fog;
import fr.onsiea.engine.client.graphics.light.DirectionalLight;
import fr.onsiea.engine.client.graphics.mesh.md5.MD5Loader;
import fr.onsiea.engine.client.graphics.opengl.hud.HudManager;
import fr.onsiea.engine.client.graphics.opengl.hud.inventory.HudInventory;
import fr.onsiea.engine.client.graphics.opengl.hud.inventory.components.HotBar.OutHotBar;
import fr.onsiea.engine.client.graphics.opengl.nanovg.NanoVGFonts.Text;
import fr.onsiea.engine.client.graphics.opengl.nanovg.NanoVGManager;
import fr.onsiea.engine.client.graphics.opengl.shaders.AdvInstancedShader;
import fr.onsiea.engine.client.graphics.opengl.shaders.AdvInstancedShaderWithTransformations;
import fr.onsiea.engine.client.graphics.opengl.shaders.InstancedShader;
import fr.onsiea.engine.client.graphics.opengl.shaders.NoiseShader;
import fr.onsiea.engine.client.graphics.opengl.shaders.Shader2D;
import fr.onsiea.engine.client.graphics.opengl.shaders.Shader2DIn3D;
import fr.onsiea.engine.client.graphics.opengl.shaders.Shader3DTo2D;
import fr.onsiea.engine.client.graphics.opengl.shaders.ShaderAnimatedBasic;
import fr.onsiea.engine.client.graphics.opengl.shaders.ShaderBasic;
import fr.onsiea.engine.client.graphics.opengl.shaders.ShaderSkybox;
import fr.onsiea.engine.client.graphics.opengl.shaders.effects.AnimatedShadowShader;
import fr.onsiea.engine.client.graphics.opengl.shaders.effects.FlareShader;
import fr.onsiea.engine.client.graphics.opengl.shaders.effects.ShadowShader;
import fr.onsiea.engine.client.graphics.opengl.shaders.normalMapping.ShaderNormalMappingThinMatrix;
import fr.onsiea.engine.client.graphics.opengl.shaders.postprocessing.BrightFilterShader;
import fr.onsiea.engine.client.graphics.opengl.shaders.postprocessing.CombineShader;
import fr.onsiea.engine.client.graphics.opengl.shaders.postprocessing.ContrastShader;
import fr.onsiea.engine.client.graphics.opengl.shaders.postprocessing.HorizontalBlurShader;
import fr.onsiea.engine.client.graphics.opengl.shaders.postprocessing.VerticalBlurShader;
import fr.onsiea.engine.client.graphics.opengl.texture.GLTextureSettings;
import fr.onsiea.engine.client.graphics.opengl.utils.OpenGLUtils;
import fr.onsiea.engine.client.graphics.render.IRenderAPIContext;
import fr.onsiea.engine.client.graphics.render.Renderer;
import fr.onsiea.engine.client.graphics.texture.ITexturesManager;
import fr.onsiea.engine.client.graphics.window.IWindow;
import fr.onsiea.engine.client.input.InputManager;
import fr.onsiea.engine.common.OnsieaGearings;
import fr.onsiea.engine.common.OnsieaGearings.DataCounter;
import fr.onsiea.engine.common.game.GameOptions;
import fr.onsiea.engine.common.game.IGameLogic;
import fr.onsiea.engine.game.scene.Scene;
import fr.onsiea.engine.game.world.World;
import fr.onsiea.engine.utils.time.DateUtils;

/**
 * @author Seynax
 *
 */
public class GameTest implements IGameLogic
{
	public final static int MINOR = 0;
	public final static int MINOR = 1;
	public final static String version = MINOR + "." + MAJOR;

	public final static void main(final String[] argsIn)
	{
		/**try
		{
			final var sl = new ShellLink().setWorkingDir(Paths.get("E:\\eclipse\\projects\\OnsieaEngine\\")
					.toAbsolutePath().normalize().toString());

			sl.setIconLocation("E:\\eclipse\\projects\\OnsieaEngine\\resources\\Aeison.ico");
			final var	targetPath	= Paths.get("E:\\eclipse\\projects\\OnsieaEngine\\resources\\test.txt")
					.toAbsolutePath();
			final var	root		= targetPath.getRoot().toString();
			final var	path		= targetPath.subpath(0, targetPath.getNameCount()).toString();
			System.out.println(path);

			new ShellLinkHelper(sl).setLocalTarget(root, path, Options.ForceTypeFile).saveTo("testlink.lnk");
		}
		catch (IOException | ShellLinkException e1)
		{
			e1.printStackTrace();
		}**/

		try
		{
			OnsieaGearings.start(new GameTest(), GameOptions.of(), argsIn);
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

	public final static Matrix4f			temp				= new Matrix4f().identity();
	public static int						selectedJointMatrix	= 15;
	public final static Vector3f			position			= new Vector3f();

	private Scene							scene;
	// private GameItem gameItem;
	private MD5Loader<GLTextureSettings>	md5Loader;
	//private PointLight						pointLight;
	//private Timer							updateTimer;
	private boolean							mustBeBlockedIsPressed;
	private World							world;
	private HudManager						hudManager;
	private HudInventory					hudCreativeInventory;
	private boolean							mustBeBlocked;
	private NanoVGManager					nanoVGManager;

	// Texts

	private Text							textFPS;
	private Text							textPlayerPosition;
	private Text							textPlayerOrientation;

	@SuppressWarnings("exports")
	public final static Loggers				loggers				= GameTest.loggers().log("test");

	private final static Loggers loggers()
	{
		final var date = DateUtils.str("yyyy.MM.dd-hh-m");
		try
		{
			return new Loggers()
					//.put("consoleLogger",
					//		new ConsoleLogger()
					//				.withPattern("[<time:HH'h'mm'_'ss'-'S>]-[<thread>] <severity_alias> : <content>"))
					.put("fileLogger",
							new FileLogger.Builder("gameData\\logs\\out_" + date, "gameData\\logs\\errors\\err_" + date)
									.build()
									.withPattern("[<time:HH'h'mm'_'ss'-'S>]-[<thread>] <severity_alias> : <content>"));
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean preInitialization()
	{
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean initialization(final IWindow windowIn, final IRenderAPIContext renderAPIContextIn,
			final InputManager inputManagerIn) throws Exception
	{
		this.mustBeBlocked	= true;
		this.md5Loader		= new MD5Loader<>();
		this.md5Loader.link(renderAPIContextIn.meshsManager(),
				(ITexturesManager<GLTextureSettings>) renderAPIContextIn.texturesManager());

		{
			renderAPIContextIn.shadersManager().add("normalMappingThinMatrix", new ShaderNormalMappingThinMatrix());
			renderAPIContextIn.shadersManager().add("scene", new ShaderBasic(ShaderBasic.Normal.SCENE));
			renderAPIContextIn.shadersManager().add("animatedScene", new ShaderAnimatedBasic());
			renderAPIContextIn.shadersManager().add("shadow", new ShadowShader());
			renderAPIContextIn.shadersManager().add("animatedShadow", new AnimatedShadowShader());
			renderAPIContextIn.shadersManager().add("flare", new FlareShader());
			renderAPIContextIn.shadersManager().add("2Din3D", new Shader2DIn3D());
			renderAPIContextIn.shadersManager().add("shader3DTo2D", new Shader3DTo2D());
			renderAPIContextIn.shadersManager().add("2D", new Shader2D());
			renderAPIContextIn.shadersManager().add("skybox", new ShaderSkybox());
			renderAPIContextIn.shadersManager().add("brightFilter", new BrightFilterShader());
			renderAPIContextIn.shadersManager().add("combineFilter", new CombineShader());
			renderAPIContextIn.shadersManager().add("contrastChanger", new ContrastShader());
			renderAPIContextIn.shadersManager().add("horizontalBlur", new HorizontalBlurShader());
			renderAPIContextIn.shadersManager().add("verticalBlur", new VerticalBlurShader());
			renderAPIContextIn.shadersManager().add("instanced", new InstancedShader());
			renderAPIContextIn.shadersManager().add("advInstanced", new AdvInstancedShader());
			renderAPIContextIn.shadersManager().add("advInstancedWithTransformations",
					new AdvInstancedShaderWithTransformations());
			renderAPIContextIn.shadersManager().add("noise", new NoiseShader());
		}

		/**final var skyboxRenderer = new SkyboxRenderer(renderAPIContextIn.shadersManager(),
				renderAPIContextIn.meshsManager().create(ShapeCube.withSize(100.0f), ShapeCube.INDICES, 3),
				((ITexturesManager<GLTextureSettings>) renderAPIContextIn.texturesManager()).load("skybox",
						GLTextureSettings.Builder.of((OpenGLRenderAPIContext) renderAPIContextIn, GL11.GL_LINEAR,
								GL11.GL_LINEAR, GL13.GL_CLAMP_TO_BORDER, GL13.GL_CLAMP_TO_BORDER, true),
						ResourcesPath.of(new ResourcesPath(GraphicsConstants.TEXTURES, "skybox"))));**/

		this.scene			= new Scene(renderAPIContextIn, windowIn, 0.125f, 0.125f, 0.125f,
				new DirectionalLight(new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(0.00f, -1.00f, 0.00f), 1.0f)
						.shadowPosMult(60).orthoCords(-120.0f, 120.0f, 120.0f, -120.0f, -80.0f, 120.0f),
				10.0f, new Vector3f(0.125f, 0.125f, 0.125f),
				new Fog(true, new Vector3f(0.125f, 0.125f, 0.125f), 0.00050f, 0.75f),

				null/**new FlareManager(0.125f, renderAPIContextIn, i -> 0.75f / (3.0f * i), i -> "tex" + (i + 1) + ".png", 9)**/
				, null /** skyboxRenderer**/
		);

		this.world			= new World(renderAPIContextIn.shadersManager(), renderAPIContextIn,
				this.scene.playerEntity(), windowIn);
		this.nanoVGManager	= new NanoVGManager(windowIn);

		this.nanoVGManager.nanoVGFonts().addText("FPS", this.textFPS = new Text(32, "ARIAL",
				NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP, 1.0f, 255, 255, 255, 255, 0, 0, "0 FPS / 0", -1));
		this.nanoVGManager.nanoVGFonts().addText("TPS", this.textPlayerPosition = new Text(32, "ARIAL",
				NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP, 1.0f, 255, 255, 255, 255, 0, 32, "", -1));
		this.nanoVGManager.nanoVGFonts().addText("UPS", this.textPlayerOrientation = new Text(32, "ARIAL",
				NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_TOP, 1.0f, 255, 255, 255, 255, 0, 64, "", -1));

		this.hudManager				= new HudManager(renderAPIContextIn);

		this.hudCreativeInventory	= new HudInventory("creativeInventory", true, renderAPIContextIn, windowIn,
				this.world, this.nanoVGManager, this.hudManager);
		this.hudManager.add(this.hudCreativeInventory);
		final var outHobar = new OutHotBar("outHotbar", false, this.hudCreativeInventory.hotBar());
		this.hudManager.add(outHobar);

		this.hudManager.canReverseOpeningWith(GLFW.GLFW_KEY_P, this.hudCreativeInventory);
		this.hudManager.open(this.hudCreativeInventory, windowIn, inputManagerIn);
		this.hudManager.canReverseOpeningWith(GLFW.GLFW_KEY_P, outHobar);

		this.scene.playerEntity().hotBar(outHobar);

		// this.gameItem = new GameItem(new Vector3f(105 + 2.0f * 4.0f * 4.0f + 8.0f,
		// 16, -75),
		// new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(4.0f, 4.0f, 4.0f));

		//final var	reflectance	= 0.5f;
		/**
		 * this.scene.sceneItems().add( new GameItemProperties("rockPlane", new
		 * Material(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), new Vector4f(1.0f, 1.0f, 1.0f,
		 * 1.0f), new Vector4f(1.0f, 1.0f, 1.0f, 1.0f),
		 * renderAPIContextIn.texturesManager().load("resources/textures/rock.png"),
		 * reflectance,
		 * renderAPIContextIn.texturesManager().load("resources/textures/rock_normals.png")),
		 * renderAPIContextIn.meshsManager().load("plane",
		 * "resources\\models\\plane.obj")), new Vector3f(0, 0, 0), new
		 * Vector3f(-180.0f, 0, 0.0f), new Vector3f(1.0f, 1.0f, 1.0f));
		 *
		 * this.scene.sceneItems() .add(new GameItemProperties("rockCube", new
		 * Material(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), new Vector4f(1.0f, 1.0f, 1.0f,
		 * 1.0f), new Vector4f(1.0f, 1.0f, 1.0f, 1.0f),
		 * renderAPIContextIn.texturesManager().load("resources/textures/rock.png"),
		 * reflectance,
		 * renderAPIContextIn.texturesManager().load("resources/textures/rock_normals.png")),
		 * renderAPIContextIn.meshsManager().load("plane",
		 * "resources\\models\\cube.obj")), this.gameItem);
		 *
		 * var ry; for (var x = -8; x < 8; x++) { for (var z = -8; z < 8; z++) {
		 * this.scene .sceneItems().add( new GameItemProperties("rockPlane", new
		 * Material(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), new Vector4f(1.0f, 1.0f, 1.0f,
		 * 1.0f), new Vector4f(1.0f, 1.0f, 1.0f, 1.0f),
		 * renderAPIContextIn.texturesManager().load("resources/textures/rock.png"),
		 * reflectance, renderAPIContextIn.texturesManager()
		 * .load("resources/textures/rock_normals.png")),
		 * renderAPIContextIn.meshsManager().load("plane",
		 * "resources\\models\\plane.obj")), new Vector3f(x * 2.0f, 0, z * 2.0f), new
		 * Vector3f(-180.0f, ry, 0.0f), new Vector3f(1.00f, 1.00f, 1.00f)); //ry -=
		 * 90.0f; } // ry -= 90.0f; }
		 **/

		//final var ry = -0.0f;
		/**for (var x = -4; x < 4; x++)
		{
			for (var z = -4; z < 4; z++)
			{
				this.scene
						.sceneItems().add(
								new GameItemProperties("rockCube", new Material(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f),
										new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), new Vector4f(1.0f, 1.0f, 1.0f, 1.0f),
										reflectance,
										((ITexturesManager<GLTextureSettings>) renderAPIContextIn.texturesManager())
												.load("resources/textures/complex_inverted_timberframe_2_2.png",
														GLTextureSettings.Builder
																.of((OpenGLRenderAPIContext) renderAPIContextIn)),

										((ITexturesManager<GLTextureSettings>) renderAPIContextIn.texturesManager())
												.load("resources/textures/rock_normals.png",
														GLTextureSettings.Builder
																.of((OpenGLRenderAPIContext) renderAPIContextIn))),
										renderAPIContextIn.meshsManager().load("cube", "resources\\models\\cube.obj"),
										GL11.GL_BACK),
								// .0001f to avoid the worries of z fighting
								new Vector3f(x * 2.0001f, 0, z * 2.0001f), new Vector3f(0.0f, ry, 0.0f),
								new Vector3f(1.00f, 1.00f, 1.00f));
				// ry -= 90.0f;
			}
			// ry -= 90.0f;
		}**/
		/**
		 * final var type = new ItemType("dirt",
		 * renderAPIContextIn.meshsManager().load("cube",
		 * "resources\\models\\cube.obj"),
		 * new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(1.0f, 1.0f, 1.0f),
		 * new Material(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), new Vector4f(1.0f, 1.0f,
		 * 1.0f, 1.0f),
		 * new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), reflectance,
		 * ((ITexturesManager<GLTextureSettings>)
		 * renderAPIContextIn.texturesManager()).load(
		 * "resources/textures/complex_inverted_timberframe_2.png",
		 * GLTextureSettings.Builder.of((OpenGLRenderAPIContext) renderAPIContextIn))));
		 **/
		/**
		 * this.scene.world().addItem(type, new Item(type, new Vector3f(0.0f, 10.0f,
		 * 0.0f), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(1.0f, 1.0f, 1.0f)));
		 *
		 * this.scene.sceneItems().add( new GameItemProperties("barrel", new
		 * Material(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), new Vector4f(1.0f, 1.0f, 1.0f,
		 * 1.0f), new Vector4f(1.0f, 1.0f, 1.0f, 1.0f),
		 * renderAPIContextIn.texturesManager().load("resources/textures/rock.png"),
		 * reflectance,
		 * renderAPIContextIn.texturesManager().load("resources/textures/rock_normals.png")),
		 * renderAPIContextIn.meshsManager().load("barrel",
		 * "resources\\models\\barrel.obj")), new Vector3f(0.0f, 3.0f, 0f), new
		 * Vector3f(180.0f, 0.0f, 0.0f), new Vector3f(0.25f, 0.25f, 0.25f));
		 *
		 * this.scene.sceneItems().add( new GameItemProperties("barrel", new
		 * Material(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), new Vector4f(1.0f, 1.0f, 1.0f,
		 * 1.0f), new Vector4f(1.0f, 1.0f, 1.0f, 1.0f),
		 * renderAPIContextIn.texturesManager().load("resources/textures/rock.png"),
		 * reflectance,
		 * renderAPIContextIn.texturesManager().load("resources/textures/rock_normals.png")),
		 * renderAPIContextIn.meshsManager().load("barrel",
		 * "resources\\models\\barrel.obj")), new Vector3f(0.0f, -3.0f, 0f), new
		 * Vector3f(180.0f, 0.0f, 0.0f), new Vector3f(0.25f, 0.25f, 0.25f));
		 **/

		/**final var					md5Meshodel		= MD5Model.parse("resources/models/boblamp.md5mesh");
		final var					md5AnimModel	= MD5AnimModel.parse("resources/models/boblamp.md5anim");
		GameAnimatedItemProperties	bob				= null;
		try
		{
			bob = this.md5Loader.process("bob", md5Meshodel, md5AnimModel, new Vector4f(1, 1, 1, 1),
					GLTextureSettings.Builder.of((OpenGLRenderAPIContext) renderAPIContextIn));
			final var bobGameItem = new GameItem(new Vector3f(4.0f, 1.0f, 0.0f), new Vector3f(90.0f, 0.0f, 90.0f),
					new Vector3f(0.05f));
			this.scene.sceneItems().add(bob, bobGameItem);
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}**/

		//this.pointLight		= new PointLight(new Vector3f(1.0f, 1.0f, 0.0f), new Vector3f(), 1.0f,
		//		new Attenuation(0.05f, 0.1f, 0.5f));
		//this.scene.sceneLights().add(this.pointLight);
		//this.updateTimer	= new Timer();

		// this.projView = new Matrix4f(MathInstances.projectionMatrix()).identity();
		// this.projView.mul(this.scene.playerEntity().view());

		/**this.cursorTexture	= ((ITexturesManager<GLTextureSettings>) renderAPIContextIn.texturesManager())
				.load("cursor.png", GLTextureSettings.Builder.of((OpenGLRenderAPIContext) renderAPIContextIn,
						GL11.GL_NEAREST, GL11.GL_NEAREST, GL13.GL_CLAMP_TO_BORDER, GL13.GL_CLAMP_TO_BORDER, true).);**/

		return true;
	}

	@Override
	public void highRateInput()
	{
	}

	@Override
	public void input(final IWindow windowIn, final InputManager inputManagerIn)
	{
		if (windowIn.key(GLFW.GLFW_KEY_F1) == GLFW.GLFW_PRESS && !this.mustBeBlockedIsPressed)
		{
			this.mustBeBlocked			= !this.mustBeBlocked;
			this.mustBeBlockedIsPressed	= true;
		}
		else if (windowIn.key(GLFW.GLFW_KEY_F1) == GLFW.GLFW_RELEASE && this.mustBeBlockedIsPressed)
		{
			this.mustBeBlockedIsPressed = false;
		}

		this.hudManager.update(windowIn, inputManagerIn);

		if ((this.hudManager.needFocus() || !this.mustBeBlocked) && inputManagerIn.cursor().isMustBeBlocked())
		{
			inputManagerIn.cursor().mustBeFree();
		}
		else if (this.mustBeBlocked && !this.hudManager.needFocus() && !inputManagerIn.cursor().isMustBeBlocked())
		{
			inputManagerIn.cursor().mustBeBlocked();
		}

		if (this.hudManager.needFocus())
		{
			this.world.reset();
		}
		this.world.update(inputManagerIn, this.scene.playerEntity(), windowIn, !this.mustBeBlocked);

		this.scene.input(windowIn, inputManagerIn, this.hudManager);
	}

	@Override
	public void update(final DataCounter dataCounterIn)
	{
		this.textFPS.change(dataCounterIn.framerateValue() + " FPS / " + dataCounterIn.refreshRate());
		this.textPlayerPosition.change(this.scene.playerEntity().position().x + ", "
				+ this.scene.playerEntity().position().y + ", " + this.scene.playerEntity().position().z);
		this.textPlayerOrientation.change(this.scene.playerEntity().orientation().x + ", "
				+ this.scene.playerEntity().orientation().y + ", " + this.scene.playerEntity().orientation().z);

		this.scene.update();
		//if (this.updateTimer.isTime(5_000_000_0L))
		//{
		/**this.scene.sceneItems().executeAnimated((item, gameItems) -> {
			item.nextFrame();
		
			final var	frame	= item.getCurrentFrame();
		
			final var	mat		= frame.getLocalJointMatrices()[GameTest.selectedJointMatrix];
		
			GameTest.temp.identity().translate(0.0f, 0.0f, 0.0f).rotateXYZ(0.0f, 0.0f, 0.0f).scale(1.0f).mul(mat);
			final var	m30	= GameTest.temp.m30();
			final var	m31	= GameTest.temp.m32();
			final var	m32	= GameTest.temp.m31();
			GameTest.temp.m30(m32);
			GameTest.temp.m31(m31);
			GameTest.temp.m32(m30);
			GameTest.temp.scale(4.0f);
		
			var	angles		= new Quaternionf();
			var	rotation	= new Vector3f();
			rotation	= mat.getNormalizedRotation(angles).getEulerAnglesXYZ(rotation);
			angles		= MD5Utils.calculateQuaternion(rotation);
		
			GameTest.temp.identity().translate(m32, m31, m30)
					.rotateXYZ((float) Math.toRadians(90.0f), (float) -Math.toRadians(90.0f),
							(float) Math.toRadians(90.0f))
					.rotateXYZ(angles.x - (float) Math.toRadians(180.0f),
							-angles.z - (float) Math.toRadians(180.0f), angles.y - (float) Math.toRadians(180.0f))
					.scale(1.0f, 1.0f, 20.0f).translate(-0.5f, 0.5f, 0.5f).translate(0.0f, 0.0f, -1.5f);
		
			GameTest.position.set(m32, m31, m30);
			this.pointLight.position().set(GameTest.position).mul(0.05f);
		});**/
		//}

		/**
		 * var rotX = this.gameItem.orientation().x; rotX += 0.5f; if (rotX >= 360) {
		 * rotX -= 360; } this.gameItem.orientation().x = rotX; var rotY =
		 * this.gameItem.orientation().y; rotY += 0.5f; if (rotY >= 360) { rotY -= 360;
		 * } this.gameItem.orientation().y = rotY; var rotZ =
		 * this.gameItem.orientation().z; rotZ += 0.5f; if (rotZ >= 360) { rotZ -= 360;
		 * } this.gameItem.orientation().z = rotZ;
		 **/
	}

	@Override
	public void draw(final IWindow windowIn, final IRenderAPIContext renderAPIContextIn, final Renderer rendererIn)
	{
		//this.scene.render(windowIn);

		renderAPIContextIn.shadersManager().updateView(this.scene.playerEntity());
		OpenGLUtils.restoreState();
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_COLOR_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);

		//GL11.glEnable(GL11.GL_CULL_FACE);

		this.world.worldRenderer().draw(this.scene.playerEntity(), renderAPIContextIn);

		this.hudManager.draw(windowIn);

		OpenGLUtils.initialize2DWithStencil();
		this.nanoVGManager.startRender(windowIn);
		this.nanoVGManager.nanoVGFonts().drawAllTexts();
		this.nanoVGManager.finishRender();
		OpenGLUtils.restoreState();
	}

	@Override
	public void cleanup()
	{
		if (this.hudManager != null)
		{
			this.hudManager.cleanup();
		}

		if (this.scene != null)
		{
			this.scene.cleanup();
		}

		if (this.nanoVGManager != null)
		{
			this.nanoVGManager.cleanup();
		}
	}
}
