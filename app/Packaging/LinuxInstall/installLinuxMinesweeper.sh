#!/bin/bash
InstallDir="/usr/local/games"
LaunchDir="/usr/local/bin"
PackageName="minesweeper-4.0_1.0-1_amd64.deb"
SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
sudo dpkg -i $SCRIPT_DIR/$PackageName
sudo echo "#\!/bin/bash" > $LaunchDir/minesweeper && \
sudo echo "$InstallDir/minesweeper/bin/minesweeper \$@ >/dev/null 2>&1 < /dev/null &" > $LaunchDir/minesweeper && \
sudo chmod +x $LaunchDir/minesweeper