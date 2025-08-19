package com.nightmare.enderite.content.event;

import com.nightmare.enderite.EnderiteMod;
import com.nightmare.enderite.registry.EnderiteItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.EnderManAngerEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.minecraft.server.level.ServerPlayer;

import java.util.Set;

@EventBusSubscriber(modid = EnderiteMod.MODID)
public class ArmorSetEffects {

    private static boolean hasFullEnderite(Player p) {
        return p.getInventory().armor.get(3).getItem() == EnderiteItems.ENDERITE_HELMET.get()
                && p.getInventory().armor.get(2).getItem() == EnderiteItems.ENDERITE_CHESTPLATE.get()
                && p.getInventory().armor.get(1).getItem() == EnderiteItems.ENDERITE_LEGGINGS.get()
                && p.getInventory().armor.get(0).getItem() == EnderiteItems.ENDERITE_BOOTS.get();
    }

    // === Flight toggle (server-side, every tick) ===
    @SubscribeEvent
    public static void onPlayerTick(net.neoforged.neoforge.event.tick.PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer sp)) return; // getEntity() is correct
        boolean full = hasFullEnderite(sp);

        var ab = sp.getAbilities();
        if (full) {
            if (!ab.mayfly) {
                ab.mayfly = true;
                sp.onUpdateAbilities();
            }
        } else {
            if (ab.mayfly || ab.flying) {
                ab.mayfly = false;
                ab.flying = false;
                sp.onUpdateAbilities();
            }
        }
    }

    @SubscribeEvent
    public static void onEndermanAnger(EnderManAngerEvent event) {
        if (event.getPlayer() != null && hasFullEnderite(event.getPlayer())) {
            event.setCanceled(true); // Gaze Veil
        }
    }

    // Void rescue, Magic immunity, Fire immunity, Evasion
    @SubscribeEvent
    public static void onIncomingDamage(LivingIncomingDamageEvent event) {
        LivingEntity e = event.getEntity();
        if (!(e instanceof Player p)) return;
        if (!hasFullEnderite(p)) return;

        // Void rescue (teleport to safe top, no fall damage, no cooldown)
        if (event.getSource().is(DamageTypes.FELL_OUT_OF_WORLD) && p.level() instanceof ServerLevel sl) {
            BlockPos safe = sl.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, p.blockPosition());
            p.teleportTo(sl, safe.getX() + 0.5, safe.getY() + 0.1, safe.getZ() + 0.5, Set.of(), p.getYRot(), p.getXRot());
            p.fallDistance = 0;
            event.setCanceled(true);
            return;
        }

        // Magic immunity
        if (event.getSource().is(DamageTypes.MAGIC)
                || event.getSource().is(DamageTypes.INDIRECT_MAGIC)
                || event.getSource().is(DamageTypes.DRAGON_BREATH)
                || event.getSource().is(DamageTypes.WITHER)) {
            event.setCanceled(true);
            return;
        }

        // Fire immunity (covers lava, fire, hot floor, etc. via tag)
        if (event.getSource().is(DamageTypeTags.IS_FIRE)) {
            event.setCanceled(true);
            return;
        }

        // Evasion: 25% negate any other hit (tune as desired)
        if (p.getRandom().nextFloat() < 0.25f) {
            event.setCanceled(true);
        }
    }

    // No fall damage while in full set
    @SubscribeEvent
    public static void onFall(LivingFallEvent event) {
        if (event.getEntity() instanceof Player p && hasFullEnderite(p)) {
            event.setCanceled(true);
        }
    }
}
