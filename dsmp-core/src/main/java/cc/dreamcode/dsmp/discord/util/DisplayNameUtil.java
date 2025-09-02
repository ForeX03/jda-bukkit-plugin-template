package cc.dreamcode.dsmp.discord.util;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

public class DisplayNameUtil {
    public static String getDisplayName(Member member, User fallbackUser) {
        if (member == null) {
            return fallbackUser.getEffectiveName();
        }

        return member.getNickname() == null ? member.getUser().getEffectiveName() : member.getNickname();
    }
}
