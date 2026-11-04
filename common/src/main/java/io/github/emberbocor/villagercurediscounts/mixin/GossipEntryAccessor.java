package io.github.emberbocor.villagercurediscounts.mixin;

import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.entity.ai.gossip.GossipType;

@Mixin(targets = "net.minecraft.world.entity.ai.gossip.GossipContainer$GossipEntry")
public interface GossipEntryAccessor {
    @Accessor("target")
    UUID villagercurediscounts$getTarget();

    @Accessor("type")
    GossipType villagercurediscounts$getType();
}
