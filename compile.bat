cls
@echo off

set lib_path=librairies
set lwjgl_path=%lib_path%\LWJGL
set natives_path=%lwjgl_path%\natives
set lombok_path=%lib_path%\Lombok\lombok-1.18.26.jar

set modules_path="%lombok_path%;%lwjgl_path%\lwjglx-debug-1.0.0.jar;%lib_path%\OnseaLogger\OnseaLogger-3.0.jar;%lib_path%\mslinks-1.1.0.jar;%lib_path%\JNA\jna-5.5.0.jar;%lib_path%\JNA\jna-platform-5.5.0.jar;%lwjgl_path%\lwjgl3-awt-0.1.8.jar;%lwjgl_path%\lwjgl-freetype.jar;%lwjgl_path%\lwjgl-jemalloc.jar;%lwjgl_path%\lwjgl-nanovg.jar;%lwjgl_path%\lwjgl-egl.jar;%lwjgl_path%\lwjgl-vma.jar;%lwjgl_path%\lwjgl-assimp.jar;%lwjgl_path%\lwjgl-harfbuzz.jar;%lwjgl_path%\lwjgl-par.jar;%lwjgl_path%\lwjgl-stb.jar;%lwjgl_path%\lwjgl-openal.jar;%lwjgl_path%\lwjgl-zstd.jar;%lwjgl_path%\lwjgl-vulkan.jar;%lwjgl_path%\lwjgl-yoga.jar;%lwjgl_path%\lwjgl-glfw.jar;%lwjgl_path%\lwjgl-openxr.jar;%lwjgl_path%\lwjgl-llvm.jar;%lwjgl_path%\steamworks4j-1.9.0.jar;%lwjgl_path%\lwjgl-cuda.jar;%lwjgl_path%\lwjgl-meshoptimizer.jar;%lwjgl_path%\lwjgl-ovr.jar;%lwjgl_path%\lwjgl-lmdb.jar;%lwjgl_path%\lwjgl-remotery.jar;%lwjgl_path%\lwjgl-opus.jar;%lwjgl_path%\lwjgl-xxhash.jar;%lwjgl_path%\lwjgl-nuklear.jar;%lwjgl_path%\lwjgl-spvc.jar;%lwjgl_path%\lwjgl-sse.jar;%lwjgl_path%\lwjgl-fmod.jar;%lwjgl_path%\steamworks4j-server-1.9.0.jar;%lwjgl_path%\lwjgl-odbc.jar;%lwjgl_path%\lwjgl-bgfx.jar;%lwjgl_path%\lwjgl-jawt.jar;%lwjgl_path%\lwjgl-shaderc.jar;%lwjgl_path%\lwjgl.jar;%lwjgl_path%\lwjgl-tootle.jar;%lwjgl_path%\lwjgl-tinyfd.jar;%lwjgl_path%\lwjgl-opencl.jar;%lwjgl_path%\lwjgl-opengl.jar;%lwjgl_path%\lwjgl-lz4.jar;%lwjgl_path%\lwjgl-libdivide.jar;%lwjgl_path%\lwjgl-openvr.jar;%lwjgl_path%\lwjgl-opengles.jar;%lwjgl_path%\lwjgl-tinyexr.jar;%lwjgl_path%\lwjgl-nfd.jar;%lwjgl_path%\lwjgl-meow.jar;%lwjgl_path%\lwjgl-ktx.jar;%lwjgl_path%\joml-1.10.5.jar;%lwjgl_path%\lwjgl-rpmalloc.jar"
set classes_path="%natives_path%\lwjgl-vma-natives-windows.jar;%natives_path%\lwjgl-opengl-natives-windows.jar;%natives_path%\lwjgl-nuklear-natives-windows.jar;%natives_path%\lwjgl-glfw-natives-windows.jar;%natives_path%\lwjgl-tootle-natives-windows.jar;%natives_path%\lwjgl-ovr-natives-windows.jar;%natives_path%\lwjgl-stb-natives-windows.jar;%natives_path%\lwjgl-opus-natives-windows.jar;%natives_path%\lwjgl-assimp-natives-windows.jar;%natives_path%\lwjgl-opengles-natives-windows.jar;%natives_path%\lwjgl-nanovg-natives-windows.jar;%natives_path%\lwjgl-xxhash-natives-windows.jar;%natives_path%\lwjgl-remotery-natives-windows.jar;%natives_path%\lwjgl-meshoptimizer-natives-windows.jar;%natives_path%\lwjgl-tinyfd-natives-windows.jar;%natives_path%\lwjgl-yoga-natives-windows.jar;%natives_path%\lwjgl-lmdb-natives-windows.jar;%natives_path%\lwjgl-nfd-natives-windows.jar;%natives_path%\lwjgl-meow-natives-windows.jar;%natives_path%\lwjgl-openvr-natives-windows.jar;%natives_path%\lwjgl-lz4-natives-windows.jar;%natives_path%\lwjgl-rpmalloc-natives-windows.jar;%natives_path%\lwjgl-spvc-natives-windows.jar;%natives_path%\lwjgl-freetype-natives-windows.jar;%natives_path%\lwjgl-llvm-natives-windows.jar;%natives_path%\lwjgl-openal-natives-windows.jar;%natives_path%\lwjgl-harfbuzz-natives-windows.jar;%natives_path%\lwjgl-shaderc-natives-windows.jar;%natives_path%\lwjgl-bgfx-natives-windows.jar;%natives_path%\lwjgl-tinyexr-natives-windows.jar;%natives_path%\lwjgl-natives-windows.jar;%natives_path%\lwjgl-openxr-natives-windows.jar;%natives_path%\lwjgl-jemalloc-natives-windows.jar;%natives_path%\lwjgl-sse-natives-windows.jar;%natives_path%\lwjgl-zstd-natives-windows.jar;%natives_path%\lwjgl-ktx-natives-windows.jar;%natives_path%\lwjgl-libdivide-natives-windows.jar;%natives_path%\lwjgl-par-natives-windows.jar"
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