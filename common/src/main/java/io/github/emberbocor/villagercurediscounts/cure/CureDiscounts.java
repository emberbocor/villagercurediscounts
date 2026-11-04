package io.github.emberbocor.villagercurediscounts.cure;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import io.github.emberbocor.villagercurediscounts.config.ServerConfig;
import io.github.emberbocor.villagercurediscounts.mixin.EntityGossipsAccessor;
import io.github.emberbocor.villagercurediscounts.mixin.GossipContainerAccessor;
import io.github.emberbocor.villagercurediscounts.mixin.GossipEntryAccessor;
import net.minecraft.world.entity.ai.gossip.GossipContainer;
import net.minecraft.world.entity.ai.gossip.GossipType;

public final class CureDiscounts {
    // Holds the shared cure stack. Version 0, so no player or entity UUID can be equal to it.
    private static final UUID SHARED = UUID.fromString("76636400-0000-0000-0000-000000000001");

    private CureDiscounts() {
    }

    public static void cure(GossipContainer gossips, UUID player) {
        ServerConfig.Settings settings = ServerConfig.settings();
        addCure(gossips, player, settings.maxCures());
        if (settings.shareWithAllPlayers()) {
            addCure(gossips, SHARED, settings.maxCures());
        }
    }

    public static int priceReputation(GossipContainer gossips, UUID player, int reputation) {
        if (!ServerConfig.settings().shareWithAllPlayers()) {
            return reputation;
        }
        int personal = gossips.getReputation(player, CureDiscounts::isCureGossip);
        int shared = gossips.getReputation(SHARED, CureDiscounts::isCureGossip);
        return shared > personal ? reputation - personal + shared : reputation;
    }

    public static List<?> withoutSharedStack(List<?> entries) {
        if (entries.stream().noneMatch(CureDiscounts::isShared)) {
            return entries;
        }
        return entries.stream().filter(entry -> !isShared(entry)).toList();
    }

    public static Collection<?> withoutMajorCureGossip(Collection<?> entries) {
        if (ServerConfig.settings().maxCures() == 1) {
            return entries;
        }
        return entries.stream().filter(entry -> ((GossipEntryAccessor) entry).villagercurediscounts$getType() != GossipType.MAJOR_POSITIVE).toList();
    }

    private static void addCure(GossipContainer gossips, UUID target, int maxCures) {
        add(gossips, target, GossipType.MAJOR_POSITIVE, 20, maxCures);
        add(gossips, target, GossipType.MINOR_POSITIVE, 25, maxCures);
    }

    private static void add(GossipContainer gossips, UUID target, GossipType type, int value, int maxCures) {
        if (maxCures == 1) {
            gossips.add(target, type, value);
            return;
        }
        int existing = gossips.getReputation(target, type::equals) / type.weight;
        gossips.add(target, type, value);
        Object targetGossips = ((GossipContainerAccessor) gossips).villagercurediscounts$getGossips().get(target);
        ((EntityGossipsAccessor) targetGossips).villagercurediscounts$getEntries()
                .put(type, Math.min(existing + value, value * maxCures));
    }

    private static boolean isCureGossip(GossipType type) {
        return type == GossipType.MAJOR_POSITIVE || type == GossipType.MINOR_POSITIVE;
    }

    private static boolean isShared(Object entry) {
        return SHARED.equals(((GossipEntryAccessor) entry).villagercurediscounts$getTarget());
    }
}
