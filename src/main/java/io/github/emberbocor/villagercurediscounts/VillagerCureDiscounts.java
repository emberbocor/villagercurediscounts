package io.github.emberbocor.villagercurediscounts;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

@Mod(VillagerCureDiscounts.MODID)
public class VillagerCureDiscounts {
    public static final String MODID = "villagercurediscounts";
    public static final Logger LOGGER = LogUtils.getLogger();

    public VillagerCureDiscounts(IEventBus modEventBus, ModContainer modContainer) {
    }
}
