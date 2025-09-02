package cc.dreamcode.dsmp.bukkit.command;

import cc.dreamcode.command.CommandBase;
import cc.dreamcode.command.annotation.Arg;
import cc.dreamcode.command.annotation.Command;
import cc.dreamcode.command.annotation.Executor;
import cc.dreamcode.dsmp.core.messenger.MessengerService;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

@Command(name = "r", aliases = { "reply", "re" })
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class BukkitReplyCommand implements CommandBase {

    private final MessengerService messengerService;

    @Executor()
    void reply(Player player, @Arg String message) {
        this.messengerService.sendReply(player, message);
    }
}
