package MySweep;
import examplepluginloader.api.MyAPI;
import examplepluginloader.api.MyPlugin;
import java.awt.EventQueue;
import javax.swing.UIManager;

public class PluginLoading implements MyPlugin {
    public void launchPlugin(MyAPI api){
        String[] args = new String[1];
        args[0]="";
        MineSweeper.StartMineSweeperMain(args);
    }
    public String getName() {
        return "minesweeper";
    }
}
