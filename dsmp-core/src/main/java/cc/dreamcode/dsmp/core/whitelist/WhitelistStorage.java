package cc.dreamcode.dsmp.core.whitelist;

import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import cc.dreamcode.utilities.builder.ListBuilder;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Header;

import java.util.List;

@Configuration(child = "whitelist.yml")
@Header("## DSMP-Whitelist (Nic tu nie zmieniaj) ##")
public class WhitelistStorage extends OkaeriConfig {

    public boolean enabled = true;

    public List<String> whitelist = ListBuilder.of("Notch", "Keymilo", "ForeX03");
}
