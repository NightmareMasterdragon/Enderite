package com.nightmare.enderite.registry;

import com.nightmare.enderite.EnderiteMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public final class EnderiteTags {
    public static final TagKey<Block> INCORRECT_FOR_ENDERITE_TOOL =
            TagKey.create(BuiltInRegistries.BLOCK.key(),
                    ResourceLocation.fromNamespaceAndPath(EnderiteMod.MODID, "incorrect_for_enderite_tool"));
    private EnderiteTags() {}
}