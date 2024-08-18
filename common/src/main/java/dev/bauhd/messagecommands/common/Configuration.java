package dev.bauhd.messagecommands.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class Configuration {

  private final String prefix = "<gold>MessageCommands <dark_gray>» ";
  private final List<MessageCommand> commands = List.of(
      new MessageCommand("discord", new String[]{"dc"},
          "<yellow>Your Discord server", null, true),
      new MessageCommand("teamspeak", new String[]{"teamspeak"},
          "<yellow>Your TeamSpeak server", null, true),
      new MessageCommand("forum", new String[]{},
          "<yellow>Your forum", null, true),
      new MessageCommand("secret", new String[]{},
          "<yellow>This is a secret", "secret", true)
  );

  public String prefix() {
    return this.prefix;
  }

  public List<MessageCommand> commands() {
    return this.commands;
  }

  public static Configuration load(final Path path) {
    final Path configPath = path.resolve("config.json");
    final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    if (Files.exists(path)) {
      if (Files.exists(configPath)) {
        try (final BufferedReader reader = Files.newBufferedReader(configPath)) {
          return gson.fromJson(reader, Configuration.class);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } else {
      try {
        Files.createDirectory(path);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    final Configuration configuration = new Configuration();
    try (final BufferedWriter writer = Files.newBufferedWriter(path)) {
      gson.toJson(configuration, writer);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return configuration;
  }
}
