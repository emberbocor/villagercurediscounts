package io.github.emberbocor.villagercurediscounts.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.world.entity.ai.gossip.GossipType;

@Mixin(targets = "net.minecraft.world.entity.ai.gossip.GossipContainer$EntityGossips")
public interface EntityGossipsAccessor {
    @Accessor("entries")
    Object2IntMap<GossipType> villagercurediscounts$getEntries();
}
