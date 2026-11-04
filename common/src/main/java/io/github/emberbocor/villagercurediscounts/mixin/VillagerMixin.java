package io.github.emberbocor.villagercurediscounts.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.emberbocor.villagercurediscounts.cure.CureDiscounts;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.village.ReputationEventType;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.player.Player;

@Mixin(Villager.class)
public abstract class VillagerMixin {
    @Inject(method = "onReputationEventFrom", at = @At("HEAD"), cancellable = true)
    private void villagercurediscounts$stackCure(ReputationEventType type, Entity target, CallbackInfo ci) {
        if (type == ReputationEventType.ZOMBIE_VILLAGER_CURED) {
            CureDiscounts.cure(((Villager) (Object) this).getGossips(), target.getUUID());
            ci.cancel();
        }
    }

    @ModifyVariable(method = "updateSpecialPrices", at = @At("STORE"), ordinal = 0)
    private int villagercurediscounts$shareCureDiscount(int reputation, Player player) {
        return CureDiscounts.priceReputation(((Villager) (Object) this).getGossips(), player.getUUID(), reputation);
    }
}
