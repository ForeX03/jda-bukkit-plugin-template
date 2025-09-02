package cc.dreamcode.jdatemplate.core.config.subconfig;

import cc.dreamcode.utilities.DateUtil;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.CustomKey;

import java.time.Instant;

public class WorldBorderConfig extends OkaeriConfig {

    @CustomKey("center-x")
    public int centerX = 0;

    @CustomKey("center-z")
    public int centerZ = 0;

    @Comment("ustaw tylko początkowe, plugin będzie potem sam to zwiększał")
    @CustomKey("current-size")
    public int size = 1250;

    @Comment("łączny wzrost, czyli bedzie 2x mniej po kazdej ze stron")
    @CustomKey("growth-per-day")
    public int growth = 200;

    @Comment("godzina, o której border sie zwiekszy")
    @CustomKey("hour-of-grow")
    public short hour = 16;

    @Comment("tego nie zmieniaj, to informacja dla pluginu ;)")
    @CustomKey("last-change")
    public String lastChange = DateUtil.formatOnlyDate(Instant.now());
}
