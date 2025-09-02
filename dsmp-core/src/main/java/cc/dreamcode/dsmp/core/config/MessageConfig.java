package cc.dreamcode.dsmp.core.config;

import cc.dreamcode.dsmp.discord.jda.sender.message.BotMessage;
import cc.dreamcode.notice.bukkit.BukkitNotice;
import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.CustomKey;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.Headers;

@Configuration(child = "message.yml")
@Headers({
        @Header("## Dream-Template (Message-Config) ##"),
        @Header("Dostepne type: (DO_NOT_SEND, CHAT, ACTION_BAR, SUBTITLE, TITLE, TITLE_SUBTITLE)")
})
public class MessageConfig extends OkaeriConfig {

    @CustomKey("command-usage")
    public BukkitNotice usage = BukkitNotice.chat("&7Przyklady uzycia komendy: &c{label}");
    @CustomKey("command-usage-help")
    public BukkitNotice usagePath = BukkitNotice.chat("&f{usage} &8- &7{description}");

    @CustomKey("command-usage-not-found")
    public BukkitNotice usageNotFound = BukkitNotice.chat("&cNie znaleziono pasujacych do kryteriow komendy.");
    @CustomKey("command-path-not-found")
    public BukkitNotice pathNotFound = BukkitNotice.chat("&cTa komenda jest pusta lub nie posiadasz dostepu do niej.");
    @CustomKey("command-no-permission")
    public BukkitNotice noPermission = BukkitNotice.chat("&cNie posiadasz uprawnien.");
    @CustomKey("command-not-player")
    public BukkitNotice notPlayer = BukkitNotice.chat("&cTa komende mozna tylko wykonac z poziomu gracza.");
    @CustomKey("command-not-console")
    public BukkitNotice notConsole = BukkitNotice.chat("&cTa komende mozna tylko wykonac z poziomu konsoli.");
    @CustomKey("command-invalid-format")
    public BukkitNotice invalidFormat = BukkitNotice.chat("&cPodano nieprawidlowy format argumentu komendy. ({input})");

    @CustomKey("player-not-found")
    public BukkitNotice playerNotFound = BukkitNotice.chat("&cPodanego gracza nie znaleziono.");
    @CustomKey("world-not-found")
    public BukkitNotice worldNotFound = BukkitNotice.chat("&cPodanego swiata nie znaleziono.");

    @CustomKey("config-reloaded")
    public BukkitNotice reloaded = BukkitNotice.chat("&aPrzeladowano! &7({time})");
    @CustomKey("config-reload-error")
    public BukkitNotice reloadError = BukkitNotice.chat("&cZnaleziono problem w konfiguracji: &6{error}");

    @CustomKey("whitelist-already-added")
    public BukkitNotice wlAlrAdded = BukkitNotice.chat("&cGracz &l{nick} &r&cznajduje się już na białej liście!");

    @CustomKey("whitelist-not-added")
    public BukkitNotice wlNotAdded = BukkitNotice.chat("&cGracz {nick} &r&cnie znajduje się na białej liście!");

    @CustomKey("whitelist-added")
    public BukkitNotice wlAdded = BukkitNotice.chat("&aDodano &f{nick} &a na &f&lbiałą listę");

    @CustomKey("whitelist-removed")
    public BukkitNotice wlRemoved = BukkitNotice.chat("&aUsunięto &f{nick} &az &f&lbiałej listy");

    @CustomKey("whitelist-already-enabled")
    public BukkitNotice wlAlrEnabled = BukkitNotice.chat("&cBiała lista jest już aktywna");

    @CustomKey("whitelist-already-disabled")
    public BukkitNotice wlAlrDisabled = BukkitNotice.chat("&cBiała lista jest już nieaktywna");

    @CustomKey("whitelist-enabled")
    public BukkitNotice wlEnabled = BukkitNotice.chat("&aBiała lista została aktywowana");

    @CustomKey("whitelist-disabled")
    public BukkitNotice wlDisabled = BukkitNotice.chat("&aBiała lista została dezaktowowana");

    @CustomKey("msg-target-not-found")
    public BukkitNotice msgTargetNotFound = BukkitNotice.chat("&cNie masz komu odpowiedziec");

    @CustomKey("msg-target-dm-off")
    public BukkitNotice msgTargetDmOff = BukkitNotice.chat("&cGracz {nick} ma wyłączone wiadomości prywatne");

    @CustomKey("msg-target-ignored")
    public BukkitNotice msgTargetIgnored = BukkitNotice.chat("&cJesteś na liście ignorowanych gracza {nick}. Nie możesz wysłać mu wiadomości");

    @CustomKey("msg-ignored")
    public BukkitNotice msgIgnored = BukkitNotice.chat("&aDodano gracza {nick} do listy ignorowanych");

    @CustomKey("msg-unignored")
    public BukkitNotice msgUnignored = BukkitNotice.chat("&aUsunieto gracza {nick} z listy ignorowanych");

    @CustomKey("msg-disabled")
    public BukkitNotice msgDisabled = BukkitNotice.chat("&aWiadomosci prywatne zostaly wylaczone");

    @CustomKey("msg-enabled")
    public BukkitNotice msgEnabled = BukkitNotice.chat("&aWiadmosci prywatne zostaly wlaczone");

    @CustomKey("cannot-talk-to-yourself")
    public BukkitNotice cannotTalkToYourself = BukkitNotice.chat("&cNie mozesz pisac do siebie");

    @CustomKey("nether-enabled")
    public BukkitNotice netherEnabled = BukkitNotice.chat("&aNether został włączony");

    @CustomKey("nether-enabled-broadcast")
    public BukkitNotice netherEnabledBroadcast = BukkitNotice.chat("&a&lNETHER ZOSTAŁ WŁĄCZONY");

    @CustomKey("nether-disabled-broadcast")
    public BukkitNotice netherDisabledBroadcast = BukkitNotice.chat("&c&lNETHER ZOSTAŁ WYŁĄCZONY");

    @CustomKey("nether-disabled")
    public BukkitNotice netherDisabled = BukkitNotice.chat("&aNether został wyłączony");

    @CustomKey("nether-already-enabled")
    public BukkitNotice netherAlreadyEnabled = BukkitNotice.chat("&cNether jest już włączony");

    @CustomKey("nether-already-disabled")
    public BukkitNotice netherAlreadyDisabled = BukkitNotice.chat("&cNether jest już wyłączony");

    @CustomKey("end-enabled")
    public BukkitNotice endEnabled = BukkitNotice.chat("&aEnd został włączony");

    @CustomKey("end-enabled-broadcast")
    public BukkitNotice endEnabledBroadcast = BukkitNotice.chat("&a&lEND ZOSTAŁ WŁĄCZONY");

    @CustomKey("end-Disabled-broadcast")
    public BukkitNotice endDisabledBroadcast = BukkitNotice.chat("&c&lEND ZOSTAŁ WYŁĄCZONY");

    @CustomKey("end-disabled")
    public BukkitNotice endDisabled = BukkitNotice.chat("&aEnd został wyłączony");

    @CustomKey("end-already-enabled")
    public BukkitNotice endAlreadyEnabled = BukkitNotice.chat("&cEnd jest już włączony");

    @CustomKey("end-already-disabled")
    public BukkitNotice endAlreadyDisabled = BukkitNotice.chat("&cEnd jest już wyłączony");

    @CustomKey("portal-nether-disabled")
    public BukkitNotice portalNetherDisabled = BukkitNotice.titleSubtitle("&8* &cNETHER OFF &8*", "&7Nether jest aktualnie wyłączony.");

    @CustomKey("portal-end-disabled")
    public BukkitNotice portalEndDisabled = BukkitNotice.titleSubtitle("&8* &5END OFF &8*", "&7End jest aktualnie wyłączony.");

    @CustomKey("border-is-grower")
    public BukkitNotice borderBecomeGrower = BukkitNotice.chat("&e&lBORDER ZOSTAL POWIEKSZONY O {grow} KRATEK, AKTUALNA WIELKOŚĆ: {size}x{size}.");

    // -- Discord --

    @CustomKey("bot-log-whitelist-add")
    public BotMessage logWhitelistAdd = new BotMessage(false,"Użytkownik {member} dodał na białą listę nick **{nick}**");

    @CustomKey("bot-log-whitelist-remove")
    public BotMessage logWhitelistRemove = new BotMessage(false,"Administrator {member} usunął nick **{nick}** z białej listy");

    @CustomKey("bot-log-whitelist-enabled")
    public BotMessage logWhitelistEnabled = new BotMessage( "Administrator {member} włączył białą listę");

    @CustomKey("bot-log-whitelist-disabled")
    public BotMessage logWhitelistDisabled = new BotMessage( "Administrator {member} wyłączył białą listę");

    @CustomKey("bot-command-nick-already-added")
    public String cmdNickAlreadyAdded = ":x: Nick {nick} znajduje się już na **białej liście**";

    @CustomKey("bot-command-nick-added")
    public String cmdNickAdded = ":white_check_mark: Pomyślnie dodano nick **{nick}** na **białą listę**!";

    @CustomKey("bot-command-nick-not-whitelisted")
    public String nickNotWhitelisted = ":x: Nick {nick} nie znajduje się na **białej liście**";

    @CustomKey("bot-command-nick-removed")
    public String cmdNickRemoved = ":white_check_mark: Pomyślnie usunięto nick **{nick}** z **białej listy**!";

    @CustomKey("bot-whitelist-already-enabled")
    public String whitelistAlreadyEnabled = ":x: Lista jest już aktywna";

    @CustomKey("bot-whitelist-already-disabled")
    public String whitelistAlreadyDisabled = ":x: Lista jest już nieaktywna";

    @CustomKey("bot-whitelist-enabled")
    public String whitelistEnabled = ":white_check_mark: Biała lista została aktywowana";

    @CustomKey("bot-whitelist-disabled")
    public String whitelistDisabled = ":white_check_mark: Biała lista została dezaktywowana";

    @CustomKey("bot-embed-send")
    public String embedSend = ":white_check_mark: Wysłano wiadomość embed";

    @CustomKey("bot-user-not-found")
    public String memberNotFound = ":x: Nie znaleziono użytkownika";

    @CustomKey("bot-user-verified")
    public String userVerified = ":white_check_mark: Użytkownik zweryfikowany";

    @CustomKey("bot-verify-wrong-answer")
    public String wrongAnswer = ":x: Odpowiedź niepoprawna";

    @CustomKey("bot-verify-success")
    public String verifySuccess = ":white_check_mark: Zostałeś pomyślnie zweryfikowany!";

    @CustomKey("verified-yet")
    public String verifiedYet = ":x: Jesteś już zweryfikowany!";

    @CustomKey("bot-nick-already-added")
    public String nickAlreadyAdded = ":x: Nick {nick} znajduje się już na **białej liście**";

    @CustomKey("bot-nick-added")
    public String nickAdded = ":white_check_mark: Pomyślnie dodano nick **{nick}** na **białą listę**!";

    @CustomKey("user-verified-yet")
    public String userVerifiedYet = ":x: Użytkownik {member} jest już zweryfikowany";

    @CustomKey("embed-not-found")
    public String embedNotFound = ":x: Nie znaleziono embeda o podanym id";

    @CustomKey("must-be-text-channel")
    public String mustBeTextChannel = ":x: Podany kanał nie jest tekstowy";

    @CustomKey("embed-list")
    public String embedList = "> Lista przedefiniowanych wiadomości embed: **{list}**";

}