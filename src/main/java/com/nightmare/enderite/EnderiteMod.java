package com.nightmare.enderite;

import com.nightmare.enderite.registry.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.minecraft.core.registries.BuiltInRegistries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(EnderiteMod.MODID)
public final class EnderiteMod {
    public static final String MODID = "enderite";
    private static final Logger LOG = LoggerFactory.getLogger(EnderiteMod.class);

    public EnderiteMod(IEventBus modBus) {
        EnderiteBlocks.BLOCKS.register(modBus);
        EnderiteItems.ITEMS.register(modBus);
        EnderiteTabs.TABS.register(modBus);
        EnderiteArmorMaterials.ARMOR_MATERIALS.register(modBus);

        modBus.addListener((net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent e) -> {
            e.enqueueWork(() -> {
                var id = BuiltInRegistries.BLOCK.getKey(EnderiteBlocks.ENDERITE_ORE.get());
                LOG.info("[Enderite] Registered ore block id = {}", id);
            });
        });
    }
}
