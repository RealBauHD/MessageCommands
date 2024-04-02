package dev.bauhd.messagecommands.utils;

public class MessageCommand {

  private final String name;
  private final String[] aliases;
  private final String message;
  private final boolean enabled;

  public MessageCommand(final String name, final String[] aliases, final String message,
      final boolean enabled) {
    this.name = name;
    this.aliases = aliases;
    this.message = message;
    this.enabled = enabled;
  }

  public String getName() {
    return this.name;
  }

  public String[] getAliases() {
    return this.aliases;
  }

  public String getMessage() {
    return this.message;
  }

  public boolean isEnabled() {
    return this.enabled;
  }
}
