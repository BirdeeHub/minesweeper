set arg1=%1
mkdir ..\lib
javac .\MySweep\*.java -d .\minesweeper_jar_in && cd .\minesweeper_jar_in && jar -cvfe ..\..\lib\minesweeper.jar MySweep.MineSweeper . ..\Icons && cd ..
if "%arg1%"=="package" (
    mkdir ..\dist
    jpackage --name Minesweeper --description "Minesweeper for Windows" --input ..\lib --dest ..\dist --main-jar minesweeper.jar --icon .\Icons\Minesweeper.ico --add-modules java.desktop --win-dir-chooser --win-menu --win-shortcut --win-shortcut-prompt --win-per-user-install --app-version 1.0 --vendor Birdee
)
