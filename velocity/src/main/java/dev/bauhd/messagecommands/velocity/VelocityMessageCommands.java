package dev.bauhd.messagecommands.velocity;

import com.mojang.brigadier.Command;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.bauhd.messagecommands.common.Configuration;
import dev.bauhd.messagecommands.common.MessageCommand;
import java.nio.file.Path;
import javax.inject.Inject;
import net.kyori.adventure.text.minimessage.MiniMessage;

@Plugin(
    id = "messagecommands",
    name = "MessageCommands",
    version = "3.1",
    authors = "BauHD",
    description = "A plugin to create custom message commands.",
    url = "https://modrinth.com/project/messagecommands"
)
public final class VelocityMessageCommands {

  private final ProxyServer proxyServer;
  private final Path dataDirectory;

  @Inject
  public VelocityMessageCommands(
      final ProxyServer proxyServer,
      final @DataDirectory Path dataDirectory
  ) {
    this.proxyServer = proxyServer;
    this.dataDirectory = dataDirectory;
  }

  @Subscribe
  public void handleInit(final ProxyInitializeEvent event) {
    final var configuration = Configuration.load(this.dataDirectory);
    final var commandManager = this.proxyServer.getCommandManager();
    for (final var command : configuration.commands()) {
      if (command.enabled()) {
        commandManager.register(command.name(), this.toBrigadierCommand(command),
            command.aliases());
      }
    }
  }

  private BrigadierCommand toBrigadierCommand(final MessageCommand command) {
    return new BrigadierCommand(BrigadierCommand.literalArgumentBuilder(command.name())
        .requires(source -> source.hasPermission(command.permission()))
        .executes(context -> {
          context.getSource().sendMessage(MiniMessage.miniMessage().deserialize(command.message()));
          return Command.SINGLE_SUCCESS;
        })
        .build()
    );
  }
}
