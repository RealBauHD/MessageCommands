package dev.bauhd.messagecommands.common;

public record MessageCommand(
    String name,
    String[] aliases,
    String message,
    String permission,
    boolean enabled
) {

}
