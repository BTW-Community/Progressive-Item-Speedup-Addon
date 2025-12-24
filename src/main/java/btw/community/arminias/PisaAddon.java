package btw.community.arminias;

import api.AddonHandler;
import api.BTWAddon;

public class PisaAddon extends BTWAddon {
    public PisaAddon() {
        super();
    }

    @Override
    public void initialize() {
        AddonHandler.logMessage(this.getName() + " Version " + this.getVersionString() + " Initializing...");
    }
}
