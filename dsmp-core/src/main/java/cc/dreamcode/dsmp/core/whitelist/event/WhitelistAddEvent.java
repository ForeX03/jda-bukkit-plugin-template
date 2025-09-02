package cc.dreamcode.dsmp.core.whitelist.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@RequiredArgsConstructor()
public class WhitelistAddEvent extends Event {

    private final HandlerList handlers = new HandlerList();
    private final String name;
    private final List<String> whitelist;


    @Override
    public @NotNull HandlerList getHandlers() {
        return this.handlers;
    }
}
