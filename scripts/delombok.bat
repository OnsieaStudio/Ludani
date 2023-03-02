cls
@echo off
cd ..

call "scripts\define_librairies.bat"
set destination_path="..\target\generated-sources\delomboked"
rmdir /S /Q %destination_path%
mkdir "%destination_path%"

cls
@echo on

%java_exe% --module-path %modules_path% -Dfile.encoding=UTF-8 %delombok% -v --module-path %modules_path% --classpath %classes_path% %sources_path% -d %destination_path%

@echo off
cd scripts
rem tests :
rem with --<...>path %..._path% and --<...>_path=%..._path%
rem with sources in same folder
rem by specifying the module-info directly or its directory in source or module path
@echo on