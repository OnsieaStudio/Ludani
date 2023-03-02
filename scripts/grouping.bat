cls
@echo off
call define_librairies.bat
cd ..

set destination_path=target\generated-sources\grouped
rmdir /S /Q %destination_path%
mkdir "%destination_path%"

cd %destination_path%

mkdir "../tmp"
dir /s /B ..\..\..\%lib_path%\*.jar > ../tmp/sources.txt

for /F "delims=" %%a in (../tmp/sources.txt) do (
	"C:\Program Files\Java\jdk-19\bin\jar.exe" xf %%a ./
)

del /F /Q ../tmp/sources.txt
rmdir /S /Q "../tmp"

robocopy /ndl /E "..\compiled\fr" "fr"

DEL /F /Q "module-info.class"
mklink /h "module-info.class" "..\compiled\module-info.class"

rmdir /S /Q META-INF

cd ..\..\..\scripts