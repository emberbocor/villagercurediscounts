package io.github.emberbocor.villagercurediscounts.mixin;

import java.util.Map;
import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.entity.ai.gossip.GossipContainer;

@Mixin(GossipContainer.class)
public interface GossipContainerAccessor {
    @Accessor("gossips")
    Map<UUID, ?> villagercurediscounts$getGossips();
}
