IF EXIST "..\Minesweeper_1.0.cfg" (move ..\Minesweeper_1.0.cfg ..\..\)
IF EXIST "..\Minesweeper_2.0.cfg" (move ..\Minesweeper_2.0.cfg ..\..\)
IF EXIST "..\Minesweeper_3.0.cfg" (move ..\Minesweeper_3.0.cfg ..\..\)
IF EXIST "..\Minesweeper_4.0.cfg" (move ..\Minesweeper_4.0.cfg ..\..\)
jpackage --name Minesweeper_4.0 --description "Minesweeper for Windows" --input ..\..\app --dest ..\..\ --main-jar minesweeper.jar --icon .\Minesweeper.ico --add-modules java.desktop --win-dir-chooser --win-menu --win-shortcut --win-shortcut-prompt --win-per-user-install --app-version 1.0 --vendor Birdee
IF EXIST "..\Minesweeper_1.0.cfg" (move ..\Minesweeper_1.0.cfg ..\..\)
IF EXIST "..\Minesweeper_2.0.cfg" (move ..\Minesweeper_2.0.cfg ..\..\)
IF EXIST "..\Minesweeper_3.0.cfg" (move ..\Minesweeper_3.0.cfg ..\..\)
IF EXIST "..\Minesweeper_4.0.cfg" (move ..\Minesweeper_4.0.cfg ..\..\)
