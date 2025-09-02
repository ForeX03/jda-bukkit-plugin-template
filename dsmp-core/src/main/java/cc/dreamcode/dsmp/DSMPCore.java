package cc.dreamcode.dsmp;

import cc.dreamcode.command.bukkit.BukkitCommandProvider;
import cc.dreamcode.dsmp.bukkit.command.*;
import cc.dreamcode.dsmp.bukkit.command.handler.InvalidInputHandlerImpl;
import cc.dreamcode.dsmp.bukkit.command.handler.InvalidPermissionHandlerImpl;
import cc.dreamcode.dsmp.bukkit.command.handler.InvalidSenderHandlerImpl;
import cc.dreamcode.dsmp.bukkit.command.handler.InvalidUsageHandlerImpl;
import cc.dreamcode.dsmp.bukkit.command.result.BukkitNoticeResolver;
import cc.dreamcode.dsmp.bukkit.listener.BukkitChatListener;
import cc.dreamcode.dsmp.core.activity.ActivityScheduler;
import cc.dreamcode.dsmp.core.activity.serializer.ActivitySerializer;
import cc.dreamcode.dsmp.core.channelstats.StatsScheduler;
import cc.dreamcode.dsmp.core.config.MessageConfig;
import cc.dreamcode.dsmp.core.config.PluginConfig;
import cc.dreamcode.dsmp.core.dimension.event.PortalListener;
import cc.dreamcode.dsmp.core.discordbridge.BridgeProvider;
import cc.dreamcode.dsmp.core.messenger.MessengerProvider;
import cc.dreamcode.dsmp.core.profile.ProfileCache;
import cc.dreamcode.dsmp.core.profile.ProfileController;
import cc.dreamcode.dsmp.core.profile.ProfileFactory;
import cc.dreamcode.dsmp.core.profile.ProfileRepository;
import cc.dreamcode.dsmp.core.verification.VerificationProvider;
import cc.dreamcode.dsmp.core.whitelist.WhitelistProvider;
import cc.dreamcode.dsmp.core.whitelist.command.WhitelistCommand;
import cc.dreamcode.dsmp.core.whitelist.modal.WhitelistModalOpenListener;
import cc.dreamcode.dsmp.core.whitelist.modal.WhitelistModalSubmittedListener;
import cc.dreamcode.dsmp.core.whitelist.suggestion.WhitelistedPlayersSuggestion;
import cc.dreamcode.dsmp.core.worldborder.BorderScheduler;
import cc.dreamcode.dsmp.discord.command.DiscordDSMPCommand;
import cc.dreamcode.dsmp.discord.jda.BotReadyHandler;
import cc.dreamcode.dsmp.discord.jda.embed.serializer.DreamEmbedFieldSerializer;
import cc.dreamcode.dsmp.discord.jda.embed.serializer.DreamEmbedSerializer;
import cc.dreamcode.dsmp.discord.jda.resolver.JDAListenerAdapterResolver;
import cc.dreamcode.dsmp.discord.jda.sender.BotSender;
import cc.dreamcode.dsmp.discord.jda.sender.logger.BotLogger;
import cc.dreamcode.dsmp.discord.jda.sender.message.serializer.BotMessageSerializer;
import cc.dreamcode.menu.adventure.BukkitMenuProvider;
import cc.dreamcode.menu.adventure.serializer.MenuBuilderSerializer;
import cc.dreamcode.notice.bukkit.BukkitNoticeProvider;
import cc.dreamcode.notice.serializer.BukkitNoticeSerializer;
import cc.dreamcode.platform.DreamVersion;
import cc.dreamcode.platform.bukkit.DreamBukkitConfig;
import cc.dreamcode.platform.bukkit.DreamBukkitPlatform;
import cc.dreamcode.platform.bukkit.component.ConfigurationResolver;
import cc.dreamcode.platform.bukkit.serializer.ItemMetaSerializer;
import cc.dreamcode.platform.bukkit.serializer.ItemStackSerializer;
import cc.dreamcode.platform.component.ComponentService;
import cc.dreamcode.platform.other.component.DreamCommandExtension;
import cc.dreamcode.platform.persistence.DreamPersistence;
import cc.dreamcode.platform.persistence.component.DocumentPersistenceResolver;
import cc.dreamcode.platform.persistence.component.DocumentRepositoryResolver;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import eu.okaeri.persistence.document.DocumentPersistence;
import eu.okaeri.tasker.bukkit.BukkitTasker;
import lombok.Getter;
import lombok.NonNull;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class DSMPCore extends DreamBukkitPlatform implements DreamBukkitConfig, DreamPersistence {

    @Getter private static DSMPCore instance;

    @Override
    public void load(@NonNull ComponentService componentService) {
        instance = this;
    }

    @Override
    public void enable(@NonNull ComponentService componentService) {
        componentService.setDebug(true);

        // -- tasker --
        this.registerInjectable(BukkitTasker.newPool(this));

        // -- providers --
        this.registerInjectable(BukkitMenuProvider.create(this));
        this.registerInjectable(BukkitNoticeProvider.create(this));
        this.registerInjectable(BukkitCommandProvider.create(this));

        componentService.registerExtension(DreamCommandExtension.class);

        componentService.registerResolver(ConfigurationResolver.class);
        componentService.registerComponent(MessageConfig.class);

        componentService.registerComponent(BukkitNoticeResolver.class);
        componentService.registerComponent(InvalidInputHandlerImpl.class);
        componentService.registerComponent(InvalidPermissionHandlerImpl.class);
        componentService.registerComponent(InvalidSenderHandlerImpl.class);
        componentService.registerComponent(InvalidUsageHandlerImpl.class);

        componentService.registerComponent(PluginConfig.class, pluginConfig -> {
            // register persistence + repositories
            this.registerInjectable(pluginConfig.storageConfig);

            componentService.registerResolver(DocumentPersistenceResolver.class);
            componentService.registerComponent(DocumentPersistence.class);
            componentService.registerResolver(DocumentRepositoryResolver.class);

            // enable additional logs and debug messages
            componentService.setDebug(pluginConfig.debug);

            // -- Java Discord API ---

            if (pluginConfig.botConfig.botToken.isEmpty()) {
                getDreamLogger().error("Token bota nie może być pusty.");
                getServer().getPluginManager().disablePlugin(this);

                return;
            }

            JDA jda = JDABuilder.createLight(pluginConfig.botConfig.botToken, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MODERATION, GatewayIntent.MESSAGE_CONTENT)
                    .build();

            this.registerInjectable(jda);

            SlashCommandData verify = Commands.slash("verify", "System weryfikacji")
                    .setDefaultPermissions(DefaultMemberPermissions.DISABLED)
                    .addSubcommands(
                            new SubcommandData("send-embed", "Wysyła embed do weryfikacji"),
                            new SubcommandData("user", "Weryfikuje użytkownika")
                                    .addOption(OptionType.USER, "user", "Użytkownik",true)
                    );

            SlashCommandData wl = Commands.slash("wl", "Zarządzanie białą listą")
                    .setDefaultPermissions(DefaultMemberPermissions.DISABLED)
                    .addSubcommands(
                            new SubcommandData("on", "Włącza białą listę"),
                            new SubcommandData("off", "Wyłącza białą listę"),
                            new SubcommandData("embed", "Wysyła embed z przyciskiem"),
                            new SubcommandData("add", "Dodaje użytkownika na białą listę")
                                    .addOption(OptionType.STRING, "nick", "Nick gracza", true),
                            new SubcommandData("remove", "Usuwa użytkownika z białej listy")
                                    .addOption(OptionType.STRING, "nick", "Nick gracza", true)
                    );

            SlashCommandData dsmp = Commands.slash("dsmp", "Narzędzia administracji")
                    .setDefaultPermissions(DefaultMemberPermissions.DISABLED)
                    .addSubcommands(
                            new SubcommandData("send-embed", "Wysyła wiadomość embed, ustawioną w configu")
                                    .addOptions(
                                            new OptionData(OptionType.STRING, "embed-id", "ID embeda z configu (lista nie aktualizuje sie po reloadzie configu, trzeba zrestartowac serwer)", true),
                                            new OptionData(OptionType.CHANNEL, "channel", "Kanał, na który ma być wysłany embed (jeśli puste, wyśle na kanał, na którym wykonasz komende)", false)),

                            new SubcommandData("embed-list", "Wysyła liste predefiniowanych embed")
                    );

            jda.updateCommands().addCommands(verify, wl, dsmp).queue();

            componentService.registerResolver(JDAListenerAdapterResolver.class);

        });
        componentService.registerComponent(BotReadyHandler.class);

        componentService.registerComponent(ProfileRepository.class);
        componentService.registerComponent(ProfileCache.class);
        componentService.registerComponent(ProfileFactory.class, ProfileFactory::loadAll);
        componentService.registerComponent(ProfileController.class);

        componentService.registerComponent(BukkitChatListener.class);
        componentService.registerComponent(PortalListener.class);
        componentService.registerComponent(BorderScheduler.class);
        componentService.registerComponent(MessengerProvider.class, MessengerProvider::load);

        componentService.registerComponent(BotSender.class);
        componentService.registerComponent(BotLogger.class);

        componentService.registerComponent(DiscordDSMPCommand.class);
        componentService.registerComponent(BridgeProvider.class, BridgeProvider::load);
        componentService.registerComponent(WhitelistProvider.class, WhitelistProvider::load);
        componentService.registerComponent(VerificationProvider.class, VerificationProvider::load);

        getInject(BukkitCommandProvider.class).ifPresent(bukkitCommandProvider -> {
            WhitelistedPlayersSuggestion wlPlayersSuggestion = createInstance(WhitelistedPlayersSuggestion.class);
            bukkitCommandProvider.registerSuggestion("@wlplayers", wlPlayersSuggestion);
        });

        componentService.registerComponent(WhitelistCommand.class);
        componentService.registerComponent(WhitelistModalOpenListener.class);
        componentService.registerComponent(WhitelistModalSubmittedListener.class);

        getInject(PluginConfig.class).ifPresent(pluginConfig -> {
            if (pluginConfig.activityConfig.enabled) {
                componentService.registerComponent(ActivityScheduler.class);
            }

            if (pluginConfig.statsConfig.enabled) {
                componentService.registerComponent(StatsScheduler.class);
            }
        });

        componentService.registerComponent(BukkitWhitelistCommand.class);
        componentService.registerComponent(BukkitDreamSMPCommand.class);
        componentService.registerComponent(BukkitMsgToggleCommand.class);
        componentService.registerComponent(BukkitMsgCommand.class);
        componentService.registerComponent(BukkitReplyCommand.class);
        componentService.registerComponent(BukkitIgnoreCommand.class);
    }

    public void onBotLoad(ComponentService componentService) {

    }

    @Override
    public void disable() {

    }


    @Override
    public @NonNull DreamVersion getDreamVersion() {
        return DreamVersion.create("DreamSMP-Core", "1.0", "ForeX03");
    }

    @Override
    public @NonNull OkaeriSerdesPack getConfigSerdesPack() {
        return registry -> {
            registry.register(new BukkitNoticeSerializer());
            registry.register(new MenuBuilderSerializer());
            registry.register(new DreamEmbedSerializer());
            registry.register(new DreamEmbedFieldSerializer());
            registry.register(new BotMessageSerializer());
            registry.register(new ActivitySerializer());
        };
    }

    @Override
    public @NonNull OkaeriSerdesPack getPersistenceSerdesPack() {
        return registry -> {
            registry.register(new SerdesBukkit());

            registry.registerExclusive(ItemStack.class, new ItemStackSerializer());
            registry.registerExclusive(ItemMeta.class, new ItemMetaSerializer());
        };
    }

}
