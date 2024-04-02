package dev.bauhd.messagecommands.bootstrap.spigot;

import dev.bauhd.messagecommands.MessageCommands;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class SpigotBootstrap extends JavaPlugin {

  private MessageCommands messageCommands;

  @Override
  public void onEnable() {
    this.getDataFolder().mkdir();

    this.messageCommands = new MessageCommands(new File(this.getDataFolder(), "config.json"));
    final String prefix = this.messageCommands.getConfig().getPrefix();

    try {
      final CommandMap commandMap = (CommandMap)
          this.getServer().getClass().getMethod("getCommandMap").invoke(this.getServer());

      this.messageCommands.getConfig().getMessageCommands().forEach(messageCommand -> {
          if (!messageCommand.isEnabled()) {
              return;
          }

        commandMap.register("messagecommands",
            new Command(messageCommand.getName(), "", "",
                Arrays.asList(messageCommand.getAliases())) {

              @Override
              public boolean execute(CommandSender commandSender, String s, String[] strings) {
                commandSender.sendMessage(prefix + messageCommand.getMessage());
                return true;
              }

            });
      });
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
    }

    this.getLogger().info(prefix + "§aThe plugin MessageCommands has been activated.");
    this.getLogger().info(prefix + "§7Developer: §bBauHD");
    this.getLogger().info(prefix + "§7Version: §b" + this.getDescription().getVersion());
  }

  @Override
  public void onDisable() {
    this.getLogger().info(this.messageCommands.getConfig().getPrefix()
        + "§cThe plugin MessageCommands has been deactivated.");
  }
}
