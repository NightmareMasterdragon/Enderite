package com.nightmare.enderite.registry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.nightmare.enderite.EnderiteMod.MODID;

public final class EnderiteBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);

    public static final DeferredBlock<Block> ENDERITE_ORE = BLOCKS.registerSimpleBlock(
        "enderite_ore",
        BlockBehaviour.Properties.of()
            .strength(30f, 1200f)
            .requiresCorrectToolForDrops()
            .sound(SoundType.STONE)
    );
}
