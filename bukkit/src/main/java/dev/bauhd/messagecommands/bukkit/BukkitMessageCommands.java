package dev.bauhd.messagecommands.bukkit;

import dev.bauhd.messagecommands.common.Configuration;
import dev.bauhd.messagecommands.common.MessageCommand;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class BukkitMessageCommands extends JavaPlugin {

  private BukkitAudiences audiences;

  @Override
  public void onEnable() {
    this.audiences = BukkitAudiences.create(this);

    final var configuration = Configuration.load(this.getDataFolder().toPath());
    final CommandMap commandMap;
    try {
      commandMap = (CommandMap)
          this.getServer().getClass().getMethod("getCommandMap").invoke(this.getServer());
    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
    final var label = "messagecommands";
    for (final var command : configuration.commands()) {
      if (command.enabled()) {
        commandMap.register(label, this.toBukkitCommand(command));
      }
    }
  }

  private Command toBukkitCommand(final MessageCommand messageCommand) {
    final var command = new Command(messageCommand.name(),
        "A custom command from the MessageCommands plugin.",
        "/" + messageCommand.name(),
        Arrays.asList(messageCommand.aliases())) {
      @Override
      public boolean execute(CommandSender sender, String label, String[] arguments) {
        BukkitMessageCommands.this.audiences.sender(sender)
            .sendMessage(MiniMessage.miniMessage().deserialize(messageCommand.message()));
        return true;
      }
    };
    command.setPermission(messageCommand.permission());
    return command;
  }
}
