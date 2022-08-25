/**
 *
 */
/**
 * @author Onsiea
 *
 */
module OnsieaEngine
{
	exports fr.onsiea.engine.core.entity;
	exports fr.onsiea.engine.common.game;
	exports fr.onsiea.engine.common;
	exports fr.onsiea.engine.game;
	exports fr.onsiea.engine.server.modular.section;
	exports fr.onsiea.engine.client.modular.section;
	exports fr.onsiea.engine.client.lwjgl;
	exports fr.onsiea.engine.client.settings;
	exports fr.onsiea.engine.client.input;
	exports fr.onsiea.engine.client.graphics;
	exports fr.onsiea.engine.client.graphics.glfw.callback;
	exports fr.onsiea.engine.client.graphics.glfw.window;
	exports fr.onsiea.engine.client.graphics.glfw.monitor;
	exports fr.onsiea.engine.client.graphics.window.context;
	exports fr.onsiea.engine.client.graphics.glfw;
	exports fr.onsiea.engine.client.graphics.render;
	exports fr.onsiea.engine.client.graphics.window;
	exports fr.onsiea.engine.client.graphics.glfw.window.context;
	exports fr.onsiea.engine.client.graphics.opengl;
	exports fr.onsiea.engine.client.graphics.opengl.mesh;
	exports fr.onsiea.engine.client.graphics.opengl.mesh.components;
	exports fr.onsiea.engine.client.graphics.opengl.mesh.draw;
	exports fr.onsiea.engine.client.graphics.opengl.vao;
	exports fr.onsiea.engine.client.graphics.opengl.vbo;
	exports fr.onsiea.engine.client.graphics.opengl.model;
	exports fr.onsiea.engine.client.graphics.texture;
	exports fr.onsiea.engine.client.graphics.texture.data;
	exports fr.onsiea.engine.client.graphics.opengl.texture;
	exports fr.onsiea.engine.client.graphics.drawable;
	exports fr.onsiea.engine.client.graphics.mesh;
	exports fr.onsiea.engine.client.graphics.mesh.obj;
	exports fr.onsiea.engine.client.graphics.shader;
	exports fr.onsiea.engine.client.graphics.shader.uniform;
	exports fr.onsiea.engine.client.graphics.light;
	exports fr.onsiea.engine.client.graphics.material;
	exports fr.onsiea.engine.client.graphics.fog;
	exports fr.onsiea.engine.client.resources;
	exports fr.onsiea.engine.utils;
	exports fr.onsiea.engine.utils.time;
	exports fr.onsiea.engine.utils.function;
	exports fr.onsiea.engine.utils.maths.vector;
	exports fr.onsiea.engine.utils.maths.vector.timed;

	requires lombok;
	requires transitive org.joml;
	requires org.lwjgl;
	requires transitive org.lwjgl.glfw;
	requires transitive org.lwjgl.opengl;
	requires org.lwjgl.nanovg;
	requires org.lwjgl.stb;
	requires org.lwjgl.vulkan;
	requires org.lwjgl.vma;
	requires org.lwjgl.shaderc;
	requires org.lwjgl.openal;
	requires java.desktop;
	requires fr.onsiea.logger;
}