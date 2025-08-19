package com.nightmare.enderite.registry;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;

import static com.nightmare.enderite.EnderiteMod.MODID;

public final class EnderiteTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(
        net.minecraft.core.registries.Registries.CREATIVE_MODE_TAB, MODID);

    public static final Supplier<CreativeModeTab> ENDERITE_TAB = TABS.register("enderite", () ->
        CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.enderite"))
            .icon(() -> new ItemStack(EnderiteItems.ENDERITE_INGOT.get()))
            .displayItems((params, out) -> {
                out.accept(EnderiteItems.ENDERITE_INGOT.get());
                out.accept(EnderiteItems.ENDERITE_GEM.get());
                out.accept(EnderiteItems.RAW_ENDERITE.get());
                out.accept(EnderiteBlocks.ENDERITE_ORE.get());
                out.accept(EnderiteItems.ENDERITE_SMITHING_TEMPLATE.get());

                out.accept(EnderiteItems.ENDERITE_SWORD.get());
                out.accept(EnderiteItems.ENDERITE_PICKAXE.get());
                out.accept(EnderiteItems.ENDERITE_AXE.get());
                out.accept(EnderiteItems.ENDERITE_SHOVEL.get());
                out.accept(EnderiteItems.ENDERITE_HOE.get());
                out.accept(EnderiteItems.ENDERITE_PAXEL.get());

                out.accept(EnderiteItems.ENDERITE_HELMET.get());
                out.accept(EnderiteItems.ENDERITE_CHESTPLATE.get());
                out.accept(EnderiteItems.ENDERITE_LEGGINGS.get());
                out.accept(EnderiteItems.ENDERITE_BOOTS.get());
            })
            .build());

    private EnderiteTabs() {}
}
