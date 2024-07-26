{
  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs/nixpkgs-unstable";
    flake-utils.url = "github:numtide/flake-utils";
    nix-appimage.url = "github:ralismark/nix-appimage";
    nix-appimage.inputs.flake-utils.follows = "flake-utils";
  };
  outputs = { nixpkgs, nix-appimage, flake-utils, ... }@inputs: let
    forEachSystem = flake-utils.lib.eachSystem inputs.nixpkgs.lib.platforms.all;
    APPNAME = "minesweeper";
    appOverlay = self: _: {
      ${APPNAME} = self.callPackage ./. { inherit inputs APPNAME; inherit (self) system; };
    };
  in {
    overlays.default = appOverlay;
  } // (
    forEachSystem (system: let
      pkgs = import nixpkgs { inherit system; overlays = [ appOverlay ]; };
    in{
      packages = {
        default = pkgs.${APPNAME};
      };
      app-images = {
        default = nix-appimage.bundlers.${system}.default pkgs.${APPNAME};
      };
      devShells = with pkgs; {
        default = mkShell {
          packages = [ jdk ];
          DEVSHELL = 0;
          JAVA_HOME = "${jdk}";
          shellHook = ''exec ${zsh}/bin/zsh'';
        };
      };
    })
  );
}
