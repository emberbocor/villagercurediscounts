package io.github.emberbocor.villagercurediscounts;

import io.github.emberbocor.villagercurediscounts.config.ServerConfig;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;

@Mod(VillagerCureDiscounts.MODID)
public class VillagerCureDiscountsNeoForge {
    public VillagerCureDiscountsNeoForge() {
        NeoForge.EVENT_BUS.addListener((ServerAboutToStartEvent event) -> ServerConfig.load());
    }
}
