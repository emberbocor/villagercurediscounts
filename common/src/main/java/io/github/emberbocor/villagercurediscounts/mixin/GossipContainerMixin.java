package io.github.emberbocor.villagercurediscounts.mixin;

import java.util.Collection;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import io.github.emberbocor.villagercurediscounts.cure.CureDiscounts;
import net.minecraft.world.entity.ai.gossip.GossipContainer;

@Mixin(GossipContainer.class)
public abstract class GossipContainerMixin {
    @ModifyVariable(method = "selectGossipsForTransfer", at = @At("STORE"), ordinal = 0)
    private List<?> villagercurediscounts$keepSharedStack(List<?> entries) {
        return CureDiscounts.withoutSharedStack(entries);
    }

    @ModifyVariable(method = "transferFrom", at = @At("STORE"), ordinal = 0)
    private Collection<?> villagercurediscounts$keepMajorCureGossip(Collection<?> entries) {
        return CureDiscounts.withoutMajorCureGossip(entries);
    }
}
