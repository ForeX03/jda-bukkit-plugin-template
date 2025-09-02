package cc.dreamcode.jdatemplate.core.profile;

import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.persistence.document.Document;
import eu.okaeri.tasker.core.Tasker;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class ProfileController implements Listener {

    private final ProfileFactory profileFactory;
    private final ProfileCache profileCache;
    private final Tasker tasker;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        this.profileFactory.createProfile(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        this.tasker.newChain()
                .supplyAsync(() -> this.profileCache.get(player))
                .abortIfNull()
                .acceptAsync(Document::save)
                .acceptAsync(this.profileCache::remove)
                .execute();
    }
}