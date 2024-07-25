# MINESWEEPER

Minesweeper, except you can set the size of the grid, number of bombs and number of lives to whatever you want!

|                                                     |                                                     |
|-----------------------------------------------------|-----------------------------------------------------|
| ![Screenshot](https://github.com/BirdeeHub/make_minesweeper_tutorial/raw/main/Screenshots/Screenshot.png)         | ![Screenshot](https://github.com/BirdeeHub/make_minesweeper_tutorial/raw/main/Screenshots/WinScreenshot.png)      |
| ![Screenshot](https://github.com/BirdeeHub/make_minesweeper_tutorial/raw/main/Screenshots/ScreenshotWithZoom.png) | ![Screenshot](https://github.com/BirdeeHub/make_minesweeper_tutorial/raw/main/Screenshots/LossScreenshot.png)     |


---

## Installation

- Download the .deb package for x86_64 debian based linux distributions, then run 
```bash
sudo apt install </path/to/minesweeper_linux_64.deb>`
```

- Download the .dmg installer package for x86_64 mac

- Download the .exe installer package for x86_64 windows

- Or download the AppImage and run it directly (x86_64 linux)

- via nix
```bash
nix run github:birdeeHub/minesweeper
```
- Or download the .jar and run with
```bash
java -jar minesweeper.jar
```

## Build

- Linux or mac:
```bash
git clone https://github.com/BirdeeHub/minesweeper && cd minesweeper && \
./src/jar_it.sh
```

If argument 1 is package it will create a `.deb` package with jpackage at `./dist`

if argument 2 is mac, it will instead be a `.dmg` for mac

You may set `$JAROUT` `$RUNOUT` to set location of the jar and run script.

- Windows:
```cmd
git clone https://github.com/BirdeeHub/minesweeper && cd minesweeper
cd .\src && .\jar_it.bat && cd ..
```
If argument 1 is package it will create a .exe installer at `./dist`

---

This repository is a repackaging of [the original repo](https://github.com/BirdeeHub/make_minesweeper_tutorial) which I turned into a coding tutorial.

In that repo I packaged it in such a way that includes the source in the jar, and a compiler in the windows packaging.

It has a ton of comments and explanations, and has a bug as an exercise and the answer in another branch.

That other branch also does some crazy stuff saving its scores within its own jar for fun.

The two branches thus cannot be merged together, and diverged long ago, thus the .git file is very large.

I wanted an actual distributable version that wouldnt take forever to clone and look at.
