cls
@echo off
call define_librairies.bat
cd ..

set destination_path="target\generated-sources\compiled"
rmdir /S /Q %destination_path%
mkdir "%destination_path%"
mkdir "target\generated-sources\tmp"

dir /s /B sources\*.java > target\generated-sources\tmp\sources.txt

%javac_exe% -verbose -Xmaxerrs 301 -implicit:none %module_info% %main_class% @target\generated-sources\tmp\sources.txt -d %destination_path% --module-path %modules_path% -classpath "%lombok_path%" -sourcepath %sources_path%

rem rmdir /S /Q "target\generated-sources\tmp"

rem %module_info% %main_class%
rem https://stackoverflow.com/questions/6623161/javac-option-to-compile-all-java-files-under-a-given-directory-recursively
rem tests :
rem with --<...>path %..._path% and --<...>_path=%..._path%
rem with sources in same folder
rem by specifying the module-info directly or its directory in source or module path
cd scripts
@echo on