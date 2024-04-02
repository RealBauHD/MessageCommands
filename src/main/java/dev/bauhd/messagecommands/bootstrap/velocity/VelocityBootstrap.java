package dev.bauhd.messagecommands.bootstrap.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.PluginDescription;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.bauhd.messagecommands.MessageCommands;
import dev.bauhd.messagecommands.utils.MessageCommand;
import java.io.File;
import java.nio.file.Path;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

@Plugin(
    id = "messagecommands",
    name = "MessageCommands",
    authors = {"BauHD"},
    version = "3.1.0",
    description = "Plugin that creates commands to send messages.",
    url = "https://www.spigotmc.org/resources/61780"
)
public class VelocityBootstrap {

  private final ProxyServer proxyServer;
  private final ComponentLogger logger;
  private final PluginDescription description;
  private final Path dataDirectory;

  private Component prefix;

  @Inject
  public VelocityBootstrap(
      final ProxyServer proxyServer,
      final ComponentLogger logger,
      final PluginDescription description,
      final @DataDirectory Path dataDirectory
  ) {
    this.proxyServer = proxyServer;
    this.logger = logger;
    this.description = description;
    this.dataDirectory = dataDirectory;
  }

  @Subscribe
  public void handle(final ProxyInitializeEvent event) {
    final File dataFolder = this.dataDirectory.toFile();
    dataFolder.mkdir();
    final MessageCommands messageCommands = new MessageCommands(
        new File(dataFolder, "config.json"));
    final LegacyComponentSerializer serializer = LegacyComponentSerializer.legacySection();
    this.prefix = serializer.deserialize(messageCommands.getConfig().getPrefix());

    for (final MessageCommand command : messageCommands.getConfig().getMessageCommands()) {
      if (!command.isEnabled()) {
        continue;
      }

      this.proxyServer.getCommandManager().register(command.getName(),
          (SimpleCommand) invocation -> invocation.source().sendMessage(
              Component.textOfChildren(prefix, serializer.deserialize(command.getMessage()))),
          command.getAliases());
    }

    this.logger.info(Component.textOfChildren(
        this.prefix,
        Component.text("The plugin MessageCommands has been activated.", NamedTextColor.GREEN),
        Component.newline(),
        this.prefix,
        Component.text("Developer: ", NamedTextColor.GRAY),
        Component.text("BauHD", NamedTextColor.AQUA),
        Component.newline(),
        this.prefix,
        Component.text("Version: ", NamedTextColor.GRAY),
        Component.text(this.description.getVersion().orElse(""), NamedTextColor.AQUA)
    ));
  }

  @Subscribe
  public void handle(final ProxyShutdownEvent event) {
    this.logger.info(Component.textOfChildren(this.prefix,
        Component.text("The plugin MessageCommands has been deactivated.", NamedTextColor.RED)));
  }
}
