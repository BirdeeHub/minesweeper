package MySweep;
import java.awt.Frame;
public class Settings {
    public static boolean isDarkMode = true;
    public static void toggleDarkMode() {
        Frame[] frames = Frame.getFrames();
        isDarkMode = !isDarkMode;
        for (Frame frame : frames) {
            if(frame instanceof MainGameWindow){
                ((MainGameWindow)frame).toggleDarkMode();
            }
        }
    }
}
