package io.github.emberbocor.villagercurediscounts.config;

import java.nio.file.Path;
import java.util.Set;

import org.jetbrains.annotations.Nullable;
import org.slf4j.helpers.MessageFormatter;

import com.electronwill.nightconfig.core.UnmodifiableConfig;

import io.github.emberbocor.villagercurediscounts.VillagerCureDiscounts;

final class ServerConfigReader {
    private static final Set<String> KEYS = Set.of("maxCures", "shareWithAllPlayers");

    private final Path file;

    ServerConfigReader(Path file) {
        this.file = file;
    }

    ServerConfig.Settings read(@Nullable UnmodifiableConfig config) {
        ServerConfig.Settings defaults = ServerConfig.DEFAULTS;
        if (config == null) {
            return defaults;
        }
        for (UnmodifiableConfig.Entry entry : config.entrySet()) {
            if (!KEYS.contains(entry.getKey())) {
                warn("unknown option '{}', ignored", entry.getKey());
            }
        }
        return new ServerConfig.Settings(maxCures(config, defaults.maxCures()),
                shareWithAllPlayers(config, defaults.shareWithAllPlayers()));
    }

    private int maxCures(UnmodifiableConfig config, int fallback) {
        Object value = config.get("maxCures");
        if (value == null) {
            return fallback;
        }
        if ((value instanceof Integer || value instanceof Long)
                && ((Number) value).longValue() >= 1 && ((Number) value).longValue() <= 20) {
            return ((Number) value).intValue();
        }
        warn("maxCures must be a whole number from 1 to 20, got '{}'; using {}", value, fallback);
        return fallback;
    }

    private boolean shareWithAllPlayers(UnmodifiableConfig config, boolean fallback) {
        Object value = config.get("shareWithAllPlayers");
        if (value == null) {
            return fallback;
        }
        if (value instanceof Boolean share) {
            return share;
        }
        warn("shareWithAllPlayers must be true or false, got '{}'; using {}", value, fallback);
        return fallback;
    }

    private void warn(String message, Object... args) {
        VillagerCureDiscounts.LOGGER.warn("{}: {}", file, MessageFormatter.arrayFormat(message, args).getMessage());
    }
}
