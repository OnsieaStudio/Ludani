cls
@echo off

set lib_path=librairies
set lwjgl_path=%lib_path%\LWJGL
set natives_path=%natives_path%
set lombok_path=%lib_path%\Lombok\lombok-1.18.26.jar

set modules_path="%lombok_path%;%lib_path%\OnseaLogger\OnseaLogger-3.0.jar;%lwjgl_path%\lwjgl.jar;%lwjgl_path%\joml-1.10.5.jar;%lwjgl_path%\lwjgl-vulkan.jar;%lwjgl_path%\lwjgl-opengl.jar;%lwjgl_path%\lwjgl-assimp.jar;%lwjgl_path%\lwjgl-glfw.jar;%lwjgl_path%\lwjgl-spvc.jar;%lwjgl_path%\lwjgl-nanovg.jar;%lwjgl_path%\lwjgl-stb.jar;%lwjgl_path%\lwjgl-openal.jar;%lwjgl_path%\lwjgl-vma.jar;%lwjgl_path%\lwjgl-shaderc.jar"
set sources_path="sources\common;sources\core;sources\client;sources\server;sources\utils;sources\game"
set classes_path="%lombok_path%"

set module_info="sources\common\module-info.java"
set main_class="sources\game\fr\onsiea\engine\game\GameTest.java"
set destination_path="target\generated-sources\compiled"

set java_bin_path=
set java_exe=java
set jar_exe=jar
set javac_exe=javac

set processor=--processor-path "%lombok_path%" -proc:class -implicit:class
set print=-verbose -Xlint -J-verbose -g