package de.bauhd.messagecommands.bootstrap.bungee;

import de.bauhd.messagecommands.MessageCommands;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.File;

public class BungeeBootstrap extends Plugin {

    private MessageCommands messageCommands;

    @Override
    public void onEnable() {
        this.getDataFolder().mkdir();

        this.messageCommands = new MessageCommands(new File(this.getDataFolder(), "config.json"));
        final String prefix = this.messageCommands.getConfig().getPrefix();

        this.messageCommands.getConfig().getMessageCommands().forEach(messageCommand -> {
            if (!messageCommand.isEnabled()) return;

            this.getProxy().getPluginManager().registerCommand(this,
                    new Command(messageCommand.getName(), null, messageCommand.getAliases()) {

                        @Override
                        public void execute(CommandSender commandSender, String[] strings) {
                            commandSender.sendMessage(TextComponent.fromLegacyText(prefix + messageCommand.getMessage()));
                        }

                    });
        });

        this.getLogger().info(prefix + "§aThe plugin MessageCommands has been activated.");
        this.getLogger().info(prefix + "§7Developer: §bBauHD");
        this.getLogger().info(prefix + "§7Version: §b" + this.getDescription().getVersion());
    }

    @Override
    public void onDisable() {
        this.getLogger().info(this.messageCommands.getConfig().getPrefix() + "§cThe plugin MessageCommands has been deactivated.");
    }

}
