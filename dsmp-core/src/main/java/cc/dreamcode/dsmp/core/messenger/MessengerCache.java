package cc.dreamcode.dsmp.core.messenger;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class MessengerCache {
    private final Map<Player, Player> replyMap = new HashMap<>();

    public Player getReplyPlayer(Player player) {
        return this.replyMap.get(player);
    }

    public void setPlayerReply(Player sender, Player target) {
        this.replyMap.put(sender, target);
        this.replyMap.put(target, sender);
    }

    public boolean hasReply(Player player) {
        return this.replyMap.get(player) != null;
    }
}
