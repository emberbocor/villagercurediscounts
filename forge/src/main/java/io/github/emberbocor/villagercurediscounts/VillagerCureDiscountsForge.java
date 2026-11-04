package io.github.emberbocor.villagercurediscounts;

import io.github.emberbocor.villagercurediscounts.config.ServerConfig;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(VillagerCureDiscounts.MODID)
public class VillagerCureDiscountsForge {
    public VillagerCureDiscountsForge() {
        ServerAboutToStartEvent.BUS.addListener(event -> ServerConfig.load());
    }
}
