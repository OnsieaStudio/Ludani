cls
@echo off
call define_librairies.bat

cd ..\target\generated-sources\grouped

set destination_path=target\generated-sources\output
rmdir /S /Q %destination_path%
mkdir "%destination_path%"

mkdir ..\output

"C:\Program Files\Java\jdk-19\bin\jar.exe" cvfe ..\output\Output.jar fr.onsiea.engine.game.GameTest *

@echo off

cd ..\..\..\scripts
@echo on