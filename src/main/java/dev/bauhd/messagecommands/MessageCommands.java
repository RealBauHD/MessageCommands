package dev.bauhd.messagecommands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.bauhd.messagecommands.utils.Config;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MessageCommands {

  private Config config;

  public MessageCommands(final File file) {
    final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    if (file.exists()) {
      try (final FileReader fileReader = new FileReader(file)) {
        this.config = gson.fromJson(fileReader, Config.class);
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      try {
        if (file.createNewFile()) {
          this.config = new Config();
          try (final FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(gson.toJson(this.config));
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }

  public Config getConfig() {
    return this.config;
  }
}
