package dev.bauhd.messagecommands.bungee;

import dev.bauhd.messagecommands.common.Configuration;
import dev.bauhd.messagecommands.common.MessageCommand;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

public final class BungeeMessageCommands extends Plugin {

  private BungeeAudiences audiences;

  @Override
  public void onEnable() {
    this.audiences = BungeeAudiences.create(this);

    final var configuration = Configuration.load(this.getDataFolder().toPath());
    final var pluginManager = this.getProxy().getPluginManager();
    for (final var command : configuration.commands()) {
      if (command.enabled()) {
        pluginManager.registerCommand(this, this.toBungeeCommand(command));
      }
    }
  }

  private Command toBungeeCommand(final MessageCommand command) {
    return new Command(command.name(), command.permission(), command.aliases()) {
      @Override
      public void execute(CommandSender sender, String[] arguments) {
        BungeeMessageCommands.this.audiences.sender(sender)
            .sendMessage(MiniMessage.miniMessage().deserialize(command.message()));
      }
    };
  }
}
