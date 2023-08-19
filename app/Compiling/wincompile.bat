javac -cp .\exampleAPI.jar ..\src\MySweep\*.java -d ..\minesweeper_jar_in && cd ..\minesweeper_jar_in && jar -cvfe ..\minesweeper.jar MySweep.MineSweeper . ..\src\MySweep\Icons && cd ..
del /s /q .\minesweeper_jar_in\*
rmdir /s /q .\minesweeper_jar_in\