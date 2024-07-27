{ APPNAME, lib, jdk, bash, stdenv, coreutils, ... }: let
  APPDRV = stdenv.mkDerivation {
    name = APPNAME;
    src = ./src;
    buildInputs = [ jdk coreutils ];
    buildPhase = ''
      runHook preBuild
      export JAVA_HOME=${jdk}
      export BASH_BIN=${bash}/bin/bash
      export RUNOUT=$out/bin
      export JAROUT=$out/lib
      rm ./Icons/Minesweeper.ico
      echo '#!${bash}/bin/bash' > ./jar_it.sh
      cat ${./src/jar_it.sh} >> ./jar_it.sh
      ./jar_it.sh
      runHook postBuild
    '';
    installPhase = ''
      runHook preInstall
      mkdir -p $out/share/applications
      cat > $out/share/applications/${APPNAME}.desktop <<EOFTAG
      [Desktop Entry]
      Type=Application
      Name=${APPNAME}
      Comment=Launches ${APPNAME}
      Terminal=false
      Icon=${./src/Icons/MineSweeperIcon.png}
      Exec=$out/bin/${APPNAME}
      EOFTAG
      runHook postInstall
    '';
    meta = {
      mainProgram = APPNAME;
      description = "classic minesweeper where you can choose any size, number of bombs, or number of lives";
      license = lib.licenses.mit;
      homepage = "https://github.com/BirdeeHub/minesweeper";
      maintainers = if lib.maintainers ? birdee then [ lib.maintainers.birdee ] else [];
    };
  };
in
APPDRV
