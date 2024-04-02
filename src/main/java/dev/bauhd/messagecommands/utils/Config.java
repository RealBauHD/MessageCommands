package dev.bauhd.messagecommands.utils;

import java.util.Arrays;
import java.util.List;

public class Config {

  private final String prefix = "§6MessageCommands §8» ";
  private final List<MessageCommand> messageCommands = Arrays.asList(
      new MessageCommand("discord", new String[]{"dc"}, "§eYour Discord server", true),
      new MessageCommand("teamspeak", new String[]{"teamspeak"}, "Your TeamSpeak server", true),
      new MessageCommand("forum", new String[]{}, "§eYour forum", true));

  public String getPrefix() {
    return this.prefix;
  }

  public List<MessageCommand> getMessageCommands() {
    return this.messageCommands;
  }
}
