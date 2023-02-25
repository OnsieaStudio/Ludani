cls
@echo off

set lib_path=librairies
set lwjgl_path=%lib_path%\LWJGL
set natives_path=%natives_path%
set lombok_path=%lib_path%\Lombok\lombok-1.18.26.jar

set modules_path="%lombok_path%;%lib_path%\OnseaLogger\OnseaLogger-3.0.jar;%lib_path%\mslinks-1.1.0.jar;%lib_path%\JNA\jna-5.5.0.jar;%lib_path%\JNA\jna-platform-5.5.0.jar;%lwjgl_path%\lwjgl-nanovg.jar;%lwjgl_path%\lwjgl-vma.jar;%lwjgl_path%\lwjgl-assimp.jar;%lwjgl_path%\lwjgl-stb.jar;%lwjgl_path%\lwjgl-openal.jar;%lwjgl_path%\lwjgl-vulkan.jar;%lwjgl_path%\lwjgl-glfw.jar;%lwjgl_path%\lwjgl-spvc.jar;%lwjgl_path%\lwjgl-shaderc.jar;%lwjgl_path%\lwjgl.jar;%lwjgl_path%\lwjgl-opengl.jar;%lwjgl_path%\joml-1.10.5.jar"
set classes_path="%natives_path%\lwjgl-vma-natives-windows.jar;%natives_path%\lwjgl-opengl-natives-windows.jar;%natives_path%\lwjgl-glfw-natives-windows.jar;%natives_path%\lwjgl-stb-natives-windows.jar;%natives_path%\lwjgl-assimp-natives-windows.jar;%natives_path%\lwjgl-nanovg-natives-windows.jar;%natives_path%\lwjgl-spvc-natives-windows.jar;%natives_path%\lwjgl-openal-natives-windows.jar;%natives_path%\lwjgl-shaderc-natives-windows.jar;%natives_path%\lwjgl-natives-windows.jar"
set sources_path="sources\common;sources\core;sources\client;sources\server;sources\utils;sources\game"

set module_info="sources\common\module-info.java"
set main_class="sources\game\fr\onsiea\engine\game\GameTest.java"
set destination_path="target\generated-sources\compiled"

set java_path=C:\Program Files\Java\jdk-19
set javac_exe="%java_path%\bin\javac.exe"
set processor=--processor-module-path "%lombok_path%"
set print=-verbose -Xlint -J-verbose -g

rem -implicit:class
rem %print%
rem %processor%

cls
@echo on

%javac_exe% %module_info% %main_class% %processor% -implicit:class -d %destination_path% --module-path %modules_path% -classpath %classes_path% -sourcepath %sources_path%

@echo off
rem tests :
rem with --<...>path %..._path% and --<...>_path=%..._path%
rem with sources in same folder
rem by specifying the module-info directly or its directory in source or module path
@echo on