package org.mouse.mousecameraapp.functions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class ConfigWriter {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final Path file;

    public ConfigWriter(Path file) {
        this.file = file.toAbsolutePath();
    }

    public Path getFile() { return file; }

    public Config load() {
        try {
            if (Files.exists(file)) {
                return GSON.fromJson(Files.readString(file), Config.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Config();   // defaults if missing or broken
    }

    public void save(Config s) throws IOException {
        // write to a temp file, then rename, so Python never reads a half-written file
        Path tmp = file.resolveSibling(file.getFileName() + ".tmp");
        Files.writeString(tmp, GSON.toJson(s), StandardCharsets.UTF_8);
        Files.move(tmp, file, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
    }
}