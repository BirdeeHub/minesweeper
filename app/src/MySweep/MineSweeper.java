package MySweep;

import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.EventQueue;
import java.awt.Image;
import java.io.FileInputStream;
import java.nio.file.Paths;

class MineSweeper {
    public static final Image MineIcon = new ImageIcon(MineSweeper.class.getResource(((isJarFile())?"/src/MySweep/":"") + "Icons/MineSweeperIcon.png")).getImage();
    public static final Image ExplosionIcon = new ImageIcon(MineSweeper.class.getResource(((isJarFile())?"/src/MySweep/":"") + "Icons/GameOverExplosion.png")).getImage();
    private static boolean isJarFile() {//<-- apparently .jar files have a magic number that shows if it is a jar file.
        try (FileInputStream fileInputStream = new FileInputStream(Paths.get(System.getProperty("java.class.path")).toFile())) {
            byte[] magicNumber = new byte[4];
            int bytesRead = fileInputStream.read(magicNumber);
            return bytesRead == 4 &&
                    (magicNumber[0] == 0x50 && magicNumber[1] == 0x4B && magicNumber[2] == 0x03 && magicNumber[3] == 0x04)
                    || (magicNumber[0] == (byte) 0x80 && magicNumber[1] == 0x75 && magicNumber[2] == 0x03 && magicNumber[3] == 0x04);
        } catch (Exception e) {
            return false;
        }
    }
    /** --a fancy comment-- @param aaaaaarg!!! These are our possible command line arguments (all > 0)
     * 
     * @param args [String <o or m>], int width, int height, int BombCount, int lives
     */
    public static void main(String[] args) {
        try {//(I found out that if you dont do this thing some Swing library stuff breaks on mac)
            // Set cross-platform Java L&F (also called "Metal") 
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }                                          //^I copy pasted this straight from the oracle documentation
        catch (UnsupportedLookAndFeelException e) {//and thats just fine. Now I can set button backgrounds on mac.
        }
        catch (ClassNotFoundException e) {
        }
        catch (InstantiationException e) {
        }
        catch (IllegalAccessException e) {
        }
        int width, height, bombCount, lives;
        if(args.length == 4){
            try{
                width = (int)(Integer.parseInt(args[0]));//<-- checking and saving each item in our args array as an int
                height = (int)(Integer.parseInt(args[1]));//<-- each index corresponds to a string. This is the second index.
                bombCount = (int)(Integer.parseInt(args[2]));//<-- Integer.parseInt(String) converts strings to integers
                lives = (int)(Integer.parseInt(args[3]));//<-- (int) makes sure it is read as an integer. this is called a "cast"
                if(width > 0 && height > 0 && bombCount > 0 && lives > 0){//<-- if you put in 4 positive numbers
                    EventQueue.invokeLater(new Runnable(){public void run(){new MainGameWindow(width, height, bombCount, lives).setVisible(true);}});
                }else{//                                                                      <--- else if not correct arguments, 
                    System.out.println("integer arguments only: width, height, BombCount, lives (where all are > 0)");//<-- print an error message
                    EventQueue.invokeLater(new Runnable(){public void run(){new OpeningWindow().setVisible(true);}});
                }
            }catch(NumberFormatException e){
                System.out.println("integer arguments only: width, height, BombCount, lives (where all are > 0)");
                EventQueue.invokeLater(new Runnable(){public void run(){new OpeningWindow().setVisible(true);}});
            }
        }else if(args.length == 5){
            if(args[0].equals("o")||args[0].equals("m")){//<-- aString.equals(anotherString) is how you compare strings.
                try{
                    width = (int)(Integer.parseInt(args[1]));//<-- same as before
                    height = (int)(Integer.parseInt(args[2]));
                    bombCount = (int)(Integer.parseInt(args[3]));
                    lives = (int)(Integer.parseInt(args[4]));
                    if(width > 0 && height > 0 && bombCount > 0 && lives > 0){
                        if(args[0].equals("m")){//<-- check if the first arg was an "m"
                            EventQueue.invokeLater(new Runnable(){public void run(){
                                new MainGameWindow(width, height, bombCount, lives).setVisible(true);
                            }});
                        }
                        if(args[0].equals("o")){//<-- check if the first arg was an "o"
                            EventQueue.invokeLater(new Runnable(){public void run(){
                                new OpeningWindow(args[1],args[2], args[3], args[4]).setVisible(true);//<-- Opening window can take strings
                            }});       //reference items in an array with name[index] where index starts at 0
                        }
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