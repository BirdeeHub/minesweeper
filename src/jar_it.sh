#!/usr/bin/env bash
if [ -n "$JAVA_HOME" ]; then
    JAVA_BIN="${JAVA_HOME}/bin"
fi
JAVA_BIN=${JAVA_BIN:-$(realpath "$(dirname "$(which java)")")}
if [ -z "$JAVA_BIN" ]; then
    echo "Please set JAVA_HOME or add the java binary to your PATH"
    exit 1
fi
ORIGIN_DIR=$(realpath .)
SRC="$(realpath "$(dirname "$(readlink -f "$0")")")"
JAVA_J=$JAVA_BIN/java
JAVA_JC=$JAVA_BIN/javac
JAVA_JAR=$JAVA_BIN/jar
JAVA_JP=$JAVA_BIN/jpackage
INSTALL_DIR=${INSTALL_DIR:-"/usr/local/games"}
JAROUT=${JAROUT:-"$SRC/../lib"}
RUNOUT=${RUNOUT:-"$SRC/../bin"}
DISTOUT=${DISTOUT:-"$SRC/../dist"}
BASH_BIN=${BASH_BIN:-$(which bash)}
BASH_BIN=${BASH_BIN:-$(which sh)}
mkdir -p "$JAROUT" && \
mkdir -p "$RUNOUT" && \
$JAVA_JC $SRC/MySweep/*.java -d "$SRC/minesweeper_jar_in" && \
cd "$SRC/minesweeper_jar_in" && \
$JAVA_JAR --create --file="$JAROUT/minesweeper.jar" --main-class=MySweep.MineSweeper  . ../Icons && \
cd "$SRC" && \
echo "#!$BASH_BIN" > "$RUNOUT/minesweeper" && \
echo "exec $JAVA_J -jar $JAROUT/minesweeper.jar" >> "$RUNOUT/minesweeper" && \
chmod +x "$RUNOUT/minesweeper"

if [ "$1" == "package" ]; then
    if [ "$2" == "mac" ]; then
        "$JAVA_JP" \
            --name minesweeper \
            --description "Minesweeper for Mac" \
            --input "$JAROUT" \
            --dest "$DISTOUT" \
            --main-jar minesweeper.jar \
            --add-modules java.desktop \
            --type dmg
    else
        RESC_DIR=$SRC/minesweeper_jar_in/resources && \
        mkdir -p "$RESC_DIR" && \
        echo "#!$BASH_BIN" > "$RESC_DIR/postinst" && \
        echo "ln -s $INSTALL_DIR/minesweeper/bin/minesweeper /usr/local/bin/minesweeper" >> "$RESC_DIR/postinst" && \
        echo "#!$BASH_BIN" > "$RESC_DIR/postrm" && \
        echo "rm /usr/local/bin/minesweeper" >> "$RESC_DIR/postrm" && \
        chmod +x $RESC_DIR/*
        "$JAVA_JP" --name minesweeper \
            --description "Minesweeper for Linux" \
            --input "$JAROUT" \
            --dest "$DISTOUT" \
            --main-jar minesweeper.jar \
            --icon "$SRC/Icons/MineSweeperIcon.png" \
            --add-modules java.desktop \
            --install-dir "$INSTALL_DIR" \
            --resource-dir "$RESC_DIR"
        rm -r "$RESC_DIR" && \
        chmod 644 $DISTOUT/minesweeper*
    fi
fi
cd "$ORIGIN_DIR"
