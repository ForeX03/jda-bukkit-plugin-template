package cc.dreamcode.dsmp.discord.jda.sender.message;

import cc.dreamcode.utilities.StringUtil;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Data
public class BotMessage {
    private final boolean enabled;
    private String text;
    private final long deleteDelay;
    private final TimeUnit deleteTimeUnit;

    public BotMessage(String text) {
        this.enabled = true;
        this.text = text;
        this.deleteDelay = 0L;
        this.deleteTimeUnit = TimeUnit.SECONDS;
    }

    public BotMessage(Boolean enabled, String text) {
        this.enabled = enabled;
        this.text = text;
        this.deleteDelay = 0L;
        this.deleteTimeUnit = TimeUnit.SECONDS;
    }

    public BotMessage(boolean enabled, String text, long deleteDelay) {
        this.enabled = enabled;
        this.text = text;
        this.deleteDelay = deleteDelay;
        this.deleteTimeUnit = TimeUnit.SECONDS;
    }

    public BotMessage(boolean enabled, String text, long deleteDelay, TimeUnit deleteTimeUnit) {
        this.enabled = enabled;
        this.text = text;
        this.deleteDelay = deleteDelay;
        this.deleteTimeUnit = deleteTimeUnit;
    }

    public static BotMessage createEmpty() {
        return new BotMessage(false, "");
    }

    public BotMessage with(Map<String, Object> replaceMap) {
        this.text = StringUtil.replace(this.text, replaceMap);
        return this;
    }
}
