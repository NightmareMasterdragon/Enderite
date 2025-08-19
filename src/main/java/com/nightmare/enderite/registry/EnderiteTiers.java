package com.nightmare.enderite.registry;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public final class EnderiteTiers {
    public static final TagKey<Block> INCORRECT_FOR_ENDERITE_TOOL = TagKey.create(
            BuiltInRegistries.BLOCK.key(),
            ResourceLocation.fromNamespaceAndPath("enderite", "incorrect_for_enderite_tool")
    );

    // Use NeoForge's helper
    public static final Tier ENDERITE = new net.neoforged.neoforge.common.SimpleTier(
            INCORRECT_FOR_ENDERITE_TOOL,
            2031 * 3,     // uses (3x netherite)
            18.0f,        // mining speed
            4.0f,         // attack dmg bonus (sword total -> 35 via createAttributes)
            50,           // enchantability
            () -> Ingredient.of(EnderiteItems.ENDERITE_INGOT.get())
    );

    private EnderiteTiers() {}
}

