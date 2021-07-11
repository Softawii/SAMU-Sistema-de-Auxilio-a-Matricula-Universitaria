echo off
set arg1=%~dp0%~1
set currentFolder=%~dp0
set astah_commandw="C:\Program Files\astah-UML\astah-commandw.exe"

@REM echo "Arquivo: " %arg1%
@REM echo %currentFolder%

if "%~1"=="" goto :sem

:com
@REM Com argumento
echo Com argumento
@REM echo %astah_commandw% -image all -f "%arg1%" -o "%currentFolder%"
%astah_commandw% -image all -f "%arg1%" -o "%currentFolder%"
goto fim

:sem
@REM Sem argumento
echo Sem argumento
@REM echo %astah_commandw% -image all -f "%currentFolder%Caso de Uso.asta" -o "%currentFolder%"
%astah_commandw% -image all -f "%currentFolder%Caso de Uso.asta" -o "%currentFolder%"

:fim
echo Imagens salvas em: %currentFolder%

pause