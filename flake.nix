{
  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs/nixpkgs-unstable";
  };
  outputs = { nixpkgs, ... }@inputs: let
    forEachSystem = with builtins; f: let # flake-utils.lib.eachSystem
      op = attrs: system: let
        ret = f system;
        op = attrs: key: attrs // {
          ${key} = (attrs.${key} or { })
          // { ${system} = ret.${key}; };
        };
      in foldl' op attrs (attrNames ret);
    in foldl' op { } nixpkgs.lib.platforms.all;

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
