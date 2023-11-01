package btw.community.arminias;

import btw.AddonHandler;
import btw.BTWAddon;

public class PisaAddon extends BTWAddon {
    private static PisaAddon instance;

    private PisaAddon() {
        super("Progressive Item Speedup", "0.1.0", "PISA");
    }

    @Override
    public void initialize() {
        AddonHandler.logMessage(this.getName() + " Version " + this.getVersionString() + " Initializing...");
    }

    public static PisaAddon getInstance() {
        if (instance == null)
            instance = new PisaAddon();
        return instance;
    }
}
