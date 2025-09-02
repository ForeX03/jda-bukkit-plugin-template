package cc.dreamcode.dsmp.bukkit.command;

import cc.dreamcode.command.CommandBase;
import cc.dreamcode.command.annotation.Arg;
import cc.dreamcode.command.annotation.Command;
import cc.dreamcode.command.annotation.Completion;
import cc.dreamcode.command.annotation.Executor;
import cc.dreamcode.dsmp.core.messenger.MessengerService;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

@Command(name = "msg", aliases = { "dm", "tell" })
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class BukkitMsgCommand implements CommandBase {

    private final MessengerService messengerService;

    @Completion(arg = "target", value = "@allplayers")
    @Executor()
    void sendMessage(Player player, @Arg Player target, @Arg String message) {
        this.messengerService.sendDirect(player, target, message);
    }
}
