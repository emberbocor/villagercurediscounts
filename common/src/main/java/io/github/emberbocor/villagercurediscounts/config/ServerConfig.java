package io.github.emberbocor.villagercurediscounts.config;

import java.nio.file.Path;

import io.github.emberbocor.villagercurediscounts.platform.Services;

public final class ServerConfig {
    static final Settings DEFAULTS = new Settings(5, true);

    private static volatile Settings settings = DEFAULTS;

    private ServerConfig() {
    }

    public static void load() {
        Path file = Services.PLATFORM.getConfigDirectory().resolve(ServerConfigFile.FILE_NAME);
        settings = new ServerConfigReader(file).read(ServerConfigFile.load(file).orElse(null));
    }

    public static Settings settings() {
        return settings;
    }

    public record Settings(int maxCures, boolean shareWithAllPlayers) {
    }
}
