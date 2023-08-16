package MySweep;
import examplepluginloader.api.MyAPI;
import examplepluginloader.api.MyPlugin;
import java.awt.EventQueue;
import javax.swing.UIManager;

public class PluginLoading implements MyPlugin {
    public void launchPlugin(MyAPI api){
        try {//(I found out that if you dont do this thing some Swing library stuff breaks on mac)
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }catch (Exception e) {}
        EventQueue.invokeLater(new Runnable(){public void run(){new OpeningWindow().setVisible(true);}});
    }
    public String getName() {
        return "minesweeper";
    }
}
