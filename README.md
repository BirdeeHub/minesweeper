# MINESWEEPER

Minesweeper, except you can set the size of the grid, number of bombs and number of lives to whatever you want!

|                                                     |                                                     |
|-----------------------------------------------------|-----------------------------------------------------|
| ![Screenshot](https://github.com/BirdeeHub/make_minesweeper_tutorial/raw/main/Screenshots/Screenshot.png)         | ![Screenshot](https://github.com/BirdeeHub/make_minesweeper_tutorial/raw/main/Screenshots/WinScreenshot.png)      |
| ![Screenshot](https://github.com/BirdeeHub/make_minesweeper_tutorial/raw/main/Screenshots/ScreenshotWithZoom.png) | ![Screenshot](https://github.com/BirdeeHub/make_minesweeper_tutorial/raw/main/Screenshots/LossScreenshot.png)     |


---

## Installation

[Release builds](https://github.com/BirdeeHub/minesweeper/releases)

- Download the .deb package for x86_64 debian based linux distributions, then run 
```bash
sudo apt install </path/to/minesweeper_linux_64.deb>`
```

- Download the .dmg installer package for x86_64 mac

- Download the .exe installer package for x86_64 windows

- via nix
```bash
nix run github:BirdeeHub/minesweeper
nix shell github:BirdeeHub/minesweeper
# and
nix repl
:lf github:BirdeeHub/minesweeper
# in order to see the things output by the flake
# for including in your system flake.
```
- Or on any platform, download java and the .jar and run with
```bash
java -jar minesweeper.jar
```

## Build

Install java to path or set JAVA_HOME

- Linux or mac:
```bash
git clone https://github.com/BirdeeHub/minesweeper && cd minesweeper && \
./src/jar_it.sh
```

It will create a .jar at `./lib` and a run script at `./bin`

If argument 1 is package it will create a `.deb` package with jpackage at `./dist`

if argument 2 is mac, it will instead be a `.dmg` for mac

You may set `$JAROUT` `$RUNOUT` to set location of the jar and run script.

You may set `$DISTOUT` `$INSTALL_DIR` to set location of the installer, and the post-install path.

- Windows:
```cmd
git clone https://github.com/BirdeeHub/minesweeper && cd minesweeper
cd .\src && .\jar_it.bat && cd ..
```
If argument 1 is package it will create a .exe installer at `./dist`

Otherwise it will only create a .jar at `./lib`

---

This repository is a repackaging of [the original repo](https://github.com/BirdeeHub/make_minesweeper_tutorial) which I turned into a coding tutorial.

Only read it if you want to see how I was after like, 4 months of learning to code. In other words, don't.

The Jarred branch is kinda cool though. Highly inadvisable, but cool. Its saves its scores within its own jar. Yes even when you force quit.

In that repo I also packaged it in such a way that includes the source in the jar, and a compiler in the windows packaging.

It has a ton of comments and explanations, and has a bug as an exercise and the answer in another branch.

The two branches thus cannot be merged together, and diverged long ago, thus the .git file is very large.

I wanted an actual distributable version that wouldnt take forever to clone and look at.

I also wanted to learn how to use github actions.
