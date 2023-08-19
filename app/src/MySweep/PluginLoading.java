package MySweep;
import examplepluginloader.api.MyAPI;
import examplepluginloader.api.MyPlugin;
import java.awt.EventQueue;
import javax.swing.UIManager;

public class PluginLoading implements MyPlugin {
    public void launchPlugin(MyAPI api){
        MineSweeper.StartMineSweeperMain(new String[]{""});
    }
    public String getName() {
        return this.getClass().getName()+" - "+this.getClass().getTypeName();
    }
}
