#!/bin/bash
javac -cp ./exampleAPI.jar ../src/MySweep/*.java -d ../minesweeper_jar_in && cd ../minesweeper_jar_in/ && \
jar --create -v --file=../minesweeper.jar --main-class=MySweep.MineSweeper  . ../src/MySweep/Icons  && \
cd .. && rm -r ./minesweeper_jar_in/
