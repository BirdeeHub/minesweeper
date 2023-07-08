#!/bin/bash
[ -f "../minesweeper.cfg" ] && mv ../minesweeper.cfg ../../
InstallDir="/usr/local/games"
jpackage --name minesweeper --description "Minesweeper for Linux" --input ../../app --dest ../../ --main-jar minesweeper.jar --icon ../src/MySweep/Icons/MineSweeperIcon.png --add-modules java.desktop --app-version 4.0 --install-dir $InstallDir
[ -f "../../minesweeper.cfg" ] && mv ../../minesweeper.cfg ../