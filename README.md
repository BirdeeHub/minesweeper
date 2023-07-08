**MINESWEEPER**

I couldn't easily find a minesweeper game on the store that allowed you to actually set the size of the field
or the number of bombs or lives... 

So I made one! 

Mostly I just wanted to learn some Java.

----------------------------------------------------------------------------------------------------------------------------------------------------

This version exists so I can change random stuff without messing with someones learn-to-code experience. 

Its just main, but without most of the comments from the tutorial, 

and some small changes, such as having ScoresFileIO as an actual static class and not a fake one

---------------------------------------------------------------------------------------------------------------------------------------------------
|                                                     |                                                     |
|-----------------------------------------------------|-----------------------------------------------------|
| ![Screenshot](./Screenshots/Screenshot.png)         | ![Screenshot](./Screenshots/WinScreenshot.png)      |
| ![Screenshot](./Screenshots/ScreenshotWithZoom.png) | ![Screenshot](./Screenshots/LossScreenshot.png)     |

************************************************************************************

**WINDOWS**

____________________________________________________________________________________

Download the EXE. Launch it. 

This will launch an installer.

game installs to %userprofile%\AppData\Local\Minesweeper (or wherever you tell it to)

scores save to %userprofile%\AppData\Roaming\minesweeperScores

It will have source code and compile scripts inside the game folder after install. 

(Also, it will give you a windows defender warning because I didnt pay for it to be a signed installer.
If that worries you, the source code and scripts used to build it are available right here,
and instructions are below. Or get a jdk and use the jar file.)

---------------------------------------------------------------------------------------------

**MAC**

I was unfortunately unable to make a mac .pkg file with the system i had access to.
If you want to run it on mac, use the .jar file. You will need to download a JDK for Mac.

************************************************************************************

**LINUX USERS:**

____________________________________________________________________________________

Also contains the source code and compile and package scripts with the package.

Use the script and not the .deb file to install to add to path and make it background itself when run from terminal,
otherwise some package launchers like dmenu (the default on i3) cant find it.

It would work fine though if you used the .deb other than being less convenient to use it from the command line

move to a writeable directory and run the following command (requires wget):
```bash
wget -O minesweeper_linux_dist.zip https://github.com/BirdeeHub/minesweeper/raw/main/minesweeper_linux_dist.zip && \
unzip minesweeper_linux_dist.zip -d minesweeper_linux_dist && \
sudo ./minesweeper_linux_dist/installLinuxMinesweeper.sh
```

and, optionally, you can run these to move the install scripts to the folder scores save in after the install
```bash
[ ! -d ~/.minesweeper/ ] && mkdir ~/.minesweeper; \
mv ./minesweeper_linux_dist/installLinuxMinesweeper.sh ~/.minesweeper/ && \
mv ./minesweeper_linux_dist/uninstallLinuxMinesweeper.sh ~/.minesweeper/ && \
rm -r ./minesweeper_linux_dist/ ./minesweeper_linux_dist.zip
```

and then to uninstall if installed this way (assuming you ran the optional commands):
```bash
sudo ~/.minesweeper/uninstallLinuxMinesweeper.sh
```

the uninstall script will cleanup the script from /usr/local/bin, but will not delete ~/.minesweeper/ or its contents

The .deb installer script may not work if your system is unable to install jdk 17 and run programs with it, 
because Java requires C libraries that are not present at runtime.
You dont have to install jdk 17 to run it, just be capable of installing it.

**********************************************************************************************************************

**PLATFORM INDEPENDENT:(and should work with older versions)**

**(the .jar file)**

____________________________________________________________________________________

Download the .jar file inside app directory.

Install a Java runtime (jdk 17+ preferred), 

https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html

use "java -jar <_path_>/<_to_>/minesweeper.jar" to play!

Make sure jdk is added to your path, or run it with the path to the java binary directly.

Inside the Jar, there is a replica of the app directory. (yes, in a 1/5th of a MB file, that is also a game. Most of that is Icon.)

If you wish to access these files to edit the program(or learn java), use:

jar -xvf ./minesweeper.jar && rm -r MySweep/ META-INF/

the rm -r command is optional, but those things arent needed to compile. They ARE what was compiled. Or, well, copies of the ones in the jar.

If you have another version of jdk already, 
but it wont let you run due to incompatible version,
you can probably just recompile. run the appropriate compile script. It will require the file structure of the app folder to remain the same.

***************************************************************************************************************************

**If you cloned the repo rather than following one of the above options:**

____________________________________________________________________________________

Cool! All you did was download extra stuff and cheat yourself out of running it from the start menu. 

You can run it from the jar, or just run the installer. Or, look through the code, recompile, repackage, and then run the installer idk.

------------------------------------------------------------------------------------------------------------------------------------------------

**Extra info for linux users:**

Install directory is /usr/local/games, scores save in ~/.minesweeper/

It will create short script called minesweeper in /usr/local/bin so that you can run the game from terminal without it hogging your terminal.

Running via this script will also hide any errors you make, but you dont have to run it via the script. You could go to the actual folder.

To run, use command "minesweeper" or find it in your start menu equivalent.

the script in your /usr/local/bin directory runs 
```bash
$install_directory/minesweeper/bin/Minesweeper $@ >/dev/null 2>&1 < /dev/null &
```
in this case, install_directory is /usr/local/games. Its hard coded in the script, so if you move it, you will have to change that.

*************************************************************************************************************************************************

**OTHER INSTRUCTIONS:**

____________________________________________________________________________________________________________________________

Download a Java Development kit for the below instructions (if you have 2.0, you will only need it to create an installer):

https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html

______________________________________________________________________________________________________________________

**WINDOWS COMPILE**

____________________________________________________________________________________

to compile to jar on windows, go to ".\app\Compiling" and run wincompile.bat

************************************************************************************

**LINUX COMPILE**

____________________________________________________________________________________

TO COMPILE JAR:

make sure you have a JDK.
cd to app/Compiling and run bashcompile.sh to compile jar.

*************************************************************************************

**MAC**

_____________________________________________________________________________________

I havent had a computer that can make a package installer for Mac. Download a jdk and the .jar file in the app directory.

run with java -jar path/to/minesweeper.jar

Inside the Jar, you will find a replica of the app directory.

If you wish to access these files to edit the program, use:

jar -xvf ./minesweeper.jar && rm -r MySweep/ META-INF/

To recompile, use the script ./Compiling/bashcompile.sh just like for linux.

If you can get the package script working on mac let me know and send the script so I can package it on a friend's mac when I have the time! 

I dont have a mac that can install a new enough version of xcode to meet the dependency requirements for jpackage so I can't do it myself right now.

_____________________________________________________________________________________

**for further instructions on compiling and creating installers**

go to README.md inside app folder.

____________________________________________________________________________________
***Have fun!!!!!!!!!!***
************************************************************************************

(also you can 1.5 click without waiting for mouse release, and always hit a 0 first click and you can turn off the question marks)
