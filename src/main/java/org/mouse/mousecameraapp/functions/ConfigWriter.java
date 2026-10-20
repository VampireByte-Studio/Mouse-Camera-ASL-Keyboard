package org.mouse.mousecameraapp.functions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class ConfigWriter {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final Path file = Path.of(System.getProperty("user.dir"), "config.json");
    private Config Config;

    public Config get() {
        if (Config == null) Config = load();
        return Config;
    }

    private Config load() {
        try {
            if (Files.exists(file)) {
                Config s = GSON.fromJson(Files.readString(file), Config.class);
                if (s != null) return s;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Config();
    }

    public void save() {
        try {
            // write to a temp file, then rename, so Python never reads a half-written file
            Path tmp = file.resolveSibling("config.json.tmp");
            Files.writeString(tmp, GSON.toJson(get()), StandardCharsets.UTF_8);
            Files.move(tmp, file, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}