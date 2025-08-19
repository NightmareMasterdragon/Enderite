package com.nightmare.enderite;

import com.nightmare.enderite.registry.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(EnderiteMod.MODID)
public final class EnderiteMod {
    public static final String MODID = "enderite";

    public EnderiteMod(IEventBus modBus) {
        EnderiteBlocks.BLOCKS.register(modBus);
        EnderiteItems.ITEMS.register(modBus);
        EnderiteTabs.TABS.register(modBus);
        EnderiteArmorMaterials.ARMOR_MATERIALS.register(modBus);
    }
}
