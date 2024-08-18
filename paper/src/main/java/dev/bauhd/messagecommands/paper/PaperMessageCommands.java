package dev.bauhd.messagecommands.paper;

import dev.bauhd.messagecommands.common.Configuration;
import dev.bauhd.messagecommands.common.MessageCommand;
import java.util.Arrays;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class PaperMessageCommands extends JavaPlugin {

  @Override
  public void onEnable() {
    final var configuration = Configuration.load(this.getDataFolder().toPath());
    final var commandMap = this.getServer().getCommandMap();
    final var label = "messagecommands";
    for (final var command : configuration.commands()) {
      if (command.enabled()) {
        commandMap.register(label, this.toBukkitCommand(command));
      }
    }
  }

  private @NotNull Command toBukkitCommand(final MessageCommand messageCommand) {
    final var command = new Command(messageCommand.name(),
        "A custom command from the MessageCommands plugin.",
        "/" + messageCommand.name(),
        Arrays.asList(messageCommand.aliases())) {
      @Override
      public boolean execute(
          @NotNull CommandSender sender, @NotNull String label, @NotNull String[] arguments
      ) {
        sender.sendMessage(MiniMessage.miniMessage().deserialize(messageCommand.message()));
        return true;
      }
    };
    command.setPermission(messageCommand.permission());
    return command;
  }
}
