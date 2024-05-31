package MySweep;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import java.awt.EventQueue;
import java.awt.Image;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.awt.Frame;
class MineSweeper {
    public static final Image ExplosionIcon = new ImageIcon(MineSweeper.class.getResource("/Icons/GameOverExplosion.png")).getImage();
    public static final Image MineIcon = new ImageIcon(MineSweeper.class.getResource("/Icons/MineSweeperIcon.png")).getImage();
    public static boolean isJarFile() {//<-- apparently .jar files have a magic number that shows if it is a jar file.
        try (FileInputStream fileInputStream = new FileInputStream(Path.of(System.getProperty("java.class.path")).toFile())) {
            byte[] magicNumber = new byte[4];
            int bytesRead = fileInputStream.read(magicNumber);
            return bytesRead == 4 &&
                    (magicNumber[0] == 0x50 && magicNumber[1] == 0x4B && magicNumber[2] == 0x03 && magicNumber[3] == 0x04)
                    || (magicNumber[0] == (byte) 0x80 && magicNumber[1] == 0x75 && magicNumber[2] == 0x03 && magicNumber[3] == 0x04);
        } catch (Exception e) {return false;}
    }
    private static boolean DarkMode = true;
    public static boolean isDarkMode(){return DarkMode;}
    public static void toggleDarkMode() {
        Frame[] frames = Frame.getFrames();
        DarkMode = !DarkMode;
        for (Frame frame : frames) {
            if(frame instanceof DarkModeToggleable){
                ((DarkModeToggleable)frame).toggleDarkMode();
            }
        }
    }
    /**
     * @param args [String "o" or "m"], int width, int height, int BombCount, int lives
     */
    public static void main(String[] args) {
        try {UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());}
        catch (Exception e) {e.printStackTrace();}
        int width, height, bombCount, lives;
        if(args.length == 4){
            try{
                width = (int)(Integer.parseInt(args[0]));
                height = (int)(Integer.parseInt(args[1]));
                bombCount = (int)(Integer.parseInt(args[2]));
                lives = (int)(Integer.parseInt(args[3]));
                if(width > 0 && height > 0 && bombCount > 0 && lives > 0){
                    EventQueue.invokeLater(new Runnable(){public void run(){new MainGameWindow(width, height, bombCount, lives).setVisible(true);}});
                }else{
                    System.out.println("integer arguments only: width, height, BombCount, lives (where all are > 0)");
                    EventQueue.invokeLater(new Runnable(){public void run(){new OpeningWindow().setVisible(true);}});
                }
            }catch(NumberFormatException e){
                System.out.println("integer arguments only: width, height, BombCount, lives (where all are > 0)");
                EventQueue.invokeLater(new Runnable(){public void run(){new OpeningWindow().setVisible(true);}});
            }
        }else if(args.length == 5){
            if(args[0].equals("o")||args[0].equals("m")){
                try{
                    width = (int)(Integer.parseInt(args[1]));
                    height = (int)(Integer.parseInt(args[2]));
                    bombCount = (int)(Integer.parseInt(args[3]));
                    lives = (int)(Integer.parseInt(args[4]));
                    if(width > 0 && height > 0 && bombCount > 0 && lives > 0){
                        if(args[0].equals("m"))EventQueue.invokeLater(new Runnable(){public void run(){
                            new MainGameWindow(width, height, bombCount, lives).setVisible(true);}});
                        if(args[0].equals("o"))EventQueue.invokeLater(new Runnable(){public void run(){
                            new OpeningWindow(args[1],args[2],args[3],args[4]).setVisible(true);}});
                    }else{
                        System.out.println("<m or o>, width, height, BombCount, lives");
                        EventQueue.invokeLater(new Runnable(){public void run(){new OpeningWindow().setVisible(true);}});
                    }
                }catch(NumberFormatException e){
                    System.out.println("<m or o>, width, height, BombCount, lives");
                    EventQueue.invokeLater(new Runnable(){public void run(){new OpeningWindow().setVisible(true);}});
                }
            }else{
                System.out.println("<m or o>, width, height, BombCount, lives");
                EventQueue.invokeLater(new Runnable(){public void run(){new OpeningWindow().setVisible(true);}});
            }
        }else{
            EventQueue.invokeLater(new Runnable(){public void run(){new OpeningWindow().setVisible(true);}});
        }
    }
}
