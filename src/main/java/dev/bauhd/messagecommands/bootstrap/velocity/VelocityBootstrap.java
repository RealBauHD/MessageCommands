package dev.bauhd.messagecommands.bootstrap.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.bauhd.messagecommands.MessageCommands;
import java.io.File;
import java.util.Objects;
import java.util.logging.Logger;
import net.kyori.adventure.text.Component;

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
  private final Logger logger;

  private MessageCommands messageCommands;

  @Inject
  public VelocityBootstrap(final ProxyServer proxyServer, final Logger logger) {
    this.proxyServer = proxyServer;
    this.logger = logger;
  }

  @Subscribe
  public void handle(final ProxyInitializeEvent event) {
    final File dataFolder = new File("plugins/MessageCommands");
    dataFolder.mkdir();
    this.messageCommands = new MessageCommands(new File(dataFolder, "config.json"));
    final String prefix = this.messageCommands.getConfig().getPrefix();

    this.messageCommands.getConfig().getMessageCommands().forEach(messageCommand -> {
      if (!messageCommand.isEnabled()) {
        return;
      }

      this.proxyServer.getCommandManager().register(messageCommand.getName(),
          (SimpleCommand) invocation -> invocation.source()
              .sendMessage(Component.text(prefix + messageCommand.getMessage())),
          messageCommand.getAliases());
    });

    this.logger.info(prefix + "§aThe plugin MessageCommands has been activated.");
    this.logger.info(prefix + "§7Developer: §bBauHD");
    this.logger.info(
        prefix + "§7Version: §b" + Objects.requireNonNull(this.proxyServer.getPluginManager()
            .getPlugin("messagecommands").orElse(null)).getDescription().getVersion());
  }

  @Subscribe
  public void handle(final ProxyShutdownEvent event) {
    this.logger.info(this.messageCommands.getConfig().getPrefix()
        + "§cThe plugin MessageCommands has been deactivated.");
  }
}
