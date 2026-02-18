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
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.EnderManAngerEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.Set;

@EventBusSubscriber(modid = EnderiteMod.MODID)
public class ArmorSetEffects {

    private static boolean hasFullEnderite(Player p) {
        return p.getInventory().armor.get(3).getItem() == EnderiteItems.ENDERITE_HELMET.get()
                && p.getInventory().armor.get(2).getItem() == EnderiteItems.ENDERITE_CHESTPLATE.get()
                && p.getInventory().armor.get(1).getItem() == EnderiteItems.ENDERITE_LEGGINGS.get()
                && p.getInventory().armor.get(0).getItem() == EnderiteItems.ENDERITE_BOOTS.get();
    }

   
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer sp)) return;
        if (sp.isCreative() || sp.isSpectator()) return; 

        boolean full = hasFullEnderite(sp);
        var ab = sp.getAbilities();
        if (full) {
            if (!ab.mayfly) { ab.mayfly = true; sp.onUpdateAbilities(); }
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
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onIncomingDamage(LivingIncomingDamageEvent event) {
        LivingEntity e = event.getEntity();
        if (!(e instanceof Player p)) return;
        if (!hasFullEnderite(p)) return;

        
        if (event.getSource().is(DamageTypes.FELL_OUT_OF_WORLD) && p.level() instanceof ServerLevel sl) {
            BlockPos safe = findNearestTop(sl, p.blockPosition(), 24);
            p.teleportTo(sl, safe.getX() + 0.5, safe.getY() + 0.1, safe.getZ() + 0.5,
                    Set.of(), p.getYRot(), p.getXRot());
            p.setDeltaMovement(Vec3.ZERO);
            p.fallDistance = 0;
            event.setCanceled(true);
            return;
        }

        
        if (event.getSource().is(DamageTypes.MAGIC)
                || event.getSource().is(DamageTypes.INDIRECT_MAGIC)
                || event.getSource().is(DamageTypes.DRAGON_BREATH)
                || event.getSource().is(DamageTypes.WITHER)) {
            event.setCanceled(true);
            return;
        }

        
        if (event.getSource().is(DamageTypeTags.IS_FIRE)) {
            event.setCanceled(true);
            return;
        }

        
        if (p.getRandom().nextFloat() < 0.25f) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onFall(LivingFallEvent event) {
        if (event.getEntity() instanceof Player p && hasFullEnderite(p)) {
            event.setCanceled(true);
        }
    }

    private static BlockPos findNearestTop(ServerLevel level, BlockPos near, int radius) {
        for (int r = 0; r <= radius; r++) {
            for (int dx = -r; dx <= r; dx++) {
                int x = near.getX() + dx;
                int z1 = near.getZ() + r, z2 = near.getZ() - r;
                BlockPos t1 = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, new BlockPos(x, 0, z1));
                if (!level.getBlockState(t1.below()).isAir()) return t1;
                BlockPos t2 = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, new BlockPos(x, 0, z2));
                if (!level.getBlockState(t2.below()).isAir()) return t2;
            }
            for (int dz = -r + 1; dz <= r - 1; dz++) {
                int z = near.getZ() + dz;
                int x1 = near.getX() + r, x2 = near.getX() - r;
                BlockPos t1 = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, new BlockPos(x1, 0, z));
                if (!level.getBlockState(t1.below()).isAir()) return t1;
                BlockPos t2 = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, new BlockPos(x2, 0, z));
                if (!level.getBlockState(t2.below()).isAir()) return t2;
            }
        }
        BlockPos colTop = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, near);
        return new BlockPos(colTop.getX(), Math.max(colTop.getY(), level.getMinBuildHeight() + 1), colTop.getZ());
    }
}
