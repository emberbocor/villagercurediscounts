package io.github.emberbocor.villagercurediscounts.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

import com.electronwill.nightconfig.core.UnmodifiableConfig;
import com.electronwill.nightconfig.toml.TomlFormat;

import io.github.emberbocor.villagercurediscounts.VillagerCureDiscounts;

final class ServerConfigFile {
    static final String FILE_NAME = VillagerCureDiscounts.MODID + "-server.toml";

    private static final String DEFAULT_TEXT = """
            # Villager Cure Discounts server config.
            # Created on the first server or world start.
            # Restart the server or reopen the singleplayer world to apply changes.

            # How many cures of the same villager add up to a bigger discount. Whole number from 1 to 20.
            # 1 is vanilla: the first cure already gives the full discount.
            # 5 matches the five-cure limit Minecraft had before 1.20.2.
            maxCures = 5

            # Give the cure discount of a cured villager to every player who trades with it,
            # not only to the player who cured it. Every cure by anyone adds to this shared discount.
            # Each player gets the larger of their own and the shared cure discount, never both.
            shareWithAllPlayers = true
            """;

    private ServerConfigFile() {
    }

    static Optional<UnmodifiableConfig> load(Path file) {
        if (!Files.exists(file)) {
            try {
                Files.createDirectories(file.getParent());
                Files.writeString(file, DEFAULT_TEXT, StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW);
                VillagerCureDiscounts.LOGGER.info("Created {}", file);
            } catch (IOException e) {
                VillagerCureDiscounts.LOGGER.warn("Could not create {}, using the default settings: {}", file, e.getMessage());
            }
            return parse(file, DEFAULT_TEXT);
        }

        try {
            String text = Files.readString(file, StandardCharsets.UTF_8);
            return parse(file, text.startsWith("﻿") ? text.substring(1) : text);
        } catch (IOException e) {
            VillagerCureDiscounts.LOGGER.warn("Could not read {}, using the default settings; file left unchanged: {}", file, e.getMessage());
            return Optional.empty();
        }
    }

    private static Optional<UnmodifiableConfig> parse(Path file, String text) {
        try {
            return Optional.of(TomlFormat.instance().createParser().parse(text));
        } catch (RuntimeException e) {
            VillagerCureDiscounts.LOGGER.warn("Could not parse {}, using the default settings; file left unchanged: {}", file, e.getMessage());
            return Optional.empty();
        }
    }
}
