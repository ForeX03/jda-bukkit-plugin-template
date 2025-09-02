package cc.dreamcode.jdatemplate;

import cc.dreamcode.command.bukkit.BukkitCommandProvider;
import cc.dreamcode.jdatemplate.bukkit.command.ExampleCommand;
import cc.dreamcode.jdatemplate.bukkit.command.handler.InvalidInputHandlerImpl;
import cc.dreamcode.jdatemplate.bukkit.command.handler.InvalidPermissionHandlerImpl;
import cc.dreamcode.jdatemplate.bukkit.command.handler.InvalidSenderHandlerImpl;
import cc.dreamcode.jdatemplate.bukkit.command.handler.InvalidUsageHandlerImpl;
import cc.dreamcode.jdatemplate.bukkit.command.result.BukkitNoticeResolver;
import cc.dreamcode.jdatemplate.core.config.MessageConfig;
import cc.dreamcode.jdatemplate.core.config.PluginConfig;
import cc.dreamcode.jdatemplate.core.profile.ProfileCache;
import cc.dreamcode.jdatemplate.core.profile.ProfileController;
import cc.dreamcode.jdatemplate.core.profile.ProfileFactory;
import cc.dreamcode.jdatemplate.core.profile.ProfileRepository;
import cc.dreamcode.jdatemplate.discord.jda.JDAExtension;
import cc.dreamcode.jdatemplate.discord.jda.embed.serializer.DreamEmbedFieldSerializer;
import cc.dreamcode.jdatemplate.discord.jda.embed.serializer.DreamEmbedSerializer;
import cc.dreamcode.jdatemplate.discord.jda.sender.BotSender;
import cc.dreamcode.jdatemplate.discord.jda.sender.message.serializer.BotMessageSerializer;
import cc.dreamcode.menu.bukkit.BukkitMenuProvider;
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
import cc.dreamcode.utilities.adventure.AdventureProcessor;
import cc.dreamcode.utilities.adventure.AdventureUtil;
import cc.dreamcode.utilities.bukkit.StringColorUtil;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import eu.okaeri.persistence.document.DocumentPersistence;
import eu.okaeri.tasker.bukkit.BukkitTasker;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class JDAPlugin extends DreamBukkitPlatform implements DreamBukkitConfig, DreamPersistence {

    @Getter private static JDAPlugin instance;

    @Override
    public void load(@NonNull ComponentService componentService) {
        instance = this;

        AdventureUtil.setRgbSupport(true);
        StringColorUtil.setColorProcessor(new AdventureProcessor());
    }

    @Override
    public void enable(@NonNull ComponentService componentService) {
        componentService.setDebug(false);

        this.registerInjectable(BukkitTasker.newPool(this));
        this.registerInjectable(BukkitMenuProvider.create(this));

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
        });

        componentService.registerExtension(JDAExtension.class);

        componentService.registerComponent(ProfileRepository.class);
        componentService.registerComponent(ProfileCache.class);
        componentService.registerComponent(ProfileFactory.class, ProfileFactory::loadAll);
        componentService.registerComponent(ProfileController.class);

        componentService.registerComponent(BotSender.class);

        componentService.registerComponent(ExampleCommand.class);
    }

    public void onBotLoad(ComponentService componentService) {}


    @Override
    public void disable() {}


    @Override
    public @NonNull DreamVersion getDreamVersion() {
        return DreamVersion.create("jda-bukkit-plugin-template", "1.0-pre.1", "author");
    }

    @Override
    public @NonNull OkaeriSerdesPack getConfigSerdesPack() {
        return registry -> {
            registry.register(new BukkitNoticeSerializer());
            registry.register(new DreamEmbedSerializer());
            registry.register(new DreamEmbedFieldSerializer());
            registry.register(new BotMessageSerializer());
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
