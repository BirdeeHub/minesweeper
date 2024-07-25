{ APPNAME, lib, symlinkJoin, writeTextDir, jdk, bash, stdenv, coreutils, ... }: let
  APPDRV = stdenv.mkDerivation {
    name = "${APPNAME}-DRV";
    src = ./src;
    buildInputs = [ jdk coreutils ];
    buildPhase = ''
      source $stdenv/setup
      export JAVA_HOME=${jdk}
      export BASH_BIN=${bash}/bin/bash
      export RUNOUT=$out/bin
      export JAROUT=$out/lib
      rm ./Icons/MineSweeperIcon.png
      echo '#!${bash}/bin/bash' > ./jar_it.sh
      cat ${./src/jar_it.sh} >> ./jar_it.sh
      exec ./jar_it.sh
    '';
  };
  DESKTOP = writeTextDir "share/applications/${APPNAME}.desktop" ''
    [Desktop Entry]
    Type=Application
    Name=${APPNAME}
    Comment=Launches ${APPNAME}
    Terminal=false
    Icon=${./src/Icons/MineSweeperIcon.png}
    Exec=${APPDRV}/bin/${APPNAME}
  '';
in
symlinkJoin {
  name = APPNAME;
  paths = [ APPDRV DESKTOP ];
  meta = {
    mainProgram = APPNAME;
    maintainers = [ lib.maintainers.birdee ];
  };
}
