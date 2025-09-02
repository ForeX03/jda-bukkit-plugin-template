package cc.dreamcode.jdatemplate.core.config.subconfig;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.CustomKey;

public class PortalConfig extends OkaeriConfig {
    @CustomKey("nether-enabled")
    public boolean netherEnabled = false;

    @CustomKey("end-enabled")
    public boolean endEnabled = false;
}
