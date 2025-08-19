package com.nightmare.enderite.content.item;

import com.nightmare.enderite.registry.EnderiteTiers;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.common.extensions.IItemExtension;

import java.util.Set;

public class PaxelItem extends DiggerItem implements IItemExtension {
    private final Tier paxelTier;

    private static final Set<ItemAbility> PAXEL_ABILITIES = Set.of(
            ItemAbilities.PICKAXE_DIG,
            ItemAbilities.AXE_DIG,
            ItemAbilities.SHOVEL_DIG,
            ItemAbilities.HOE_DIG,
            ItemAbilities.AXE_STRIP,
            ItemAbilities.AXE_SCRAPE,
            ItemAbilities.AXE_WAX_OFF,
            ItemAbilities.SHOVEL_FLATTEN,
            ItemAbilities.HOE_TILL
    );

    public PaxelItem(Tier tier, Item.Properties props) {
        // 1.21.x DiggerItem ctor: (Tier, TagKey<Block> incorrectForTool, Properties)
        super(tier, EnderiteTiers.INCORRECT_FOR_ENDERITE_TOOL, props);
        this.paxelTier = tier;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility ability) {
        return PAXEL_ABILITIES.contains(ability);
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return state.is(BlockTags.MINEABLE_WITH_PICKAXE)
                || state.is(BlockTags.MINEABLE_WITH_AXE)
                || state.is(BlockTags.MINEABLE_WITH_SHOVEL)
                || super.isCorrectToolForDrops(stack, state);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (state.is(BlockTags.MINEABLE_WITH_PICKAXE)
                || state.is(BlockTags.MINEABLE_WITH_AXE)
                || state.is(BlockTags.MINEABLE_WITH_SHOVEL)) {
            return paxelTier.getSpeed() * 1.05f; // slightly faster than the tier baseline
        }
        return super.getDestroySpeed(stack, state);
    }

    // Right-click: first makes Dirt Path; right-clicking that Path makes Farmland.
    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        var level = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        BlockState state = level.getBlockState(pos);
        BlockPos above = pos.above();
        Player player = ctx.getPlayer();
        boolean client = level.isClientSide;
        ItemStack held = ctx.getItemInHand();

        // Map hand -> EquipmentSlot (required overload in 1.21.1)
        EquipmentSlot slot = (ctx.getHand() == InteractionHand.MAIN_HAND)
                ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;

        // Path -> Farmland
        if (state.is(Blocks.DIRT_PATH) && level.isEmptyBlock(above)) {
            if (!client) {
                level.setBlock(pos, Blocks.FARMLAND.defaultBlockState(), 11);
                level.playSound(null, pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                if (player != null) held.hurtAndBreak(1, player, slot);
            }
            return InteractionResult.sidedSuccess(client);
        }

        // Make Dirt Path if block is pathable and air above (avoid ShovelItem.FLATTENABLES mapping issues)
        if (level.isEmptyBlock(above) && isPathable(state)) {
            if (!client) {
                level.setBlock(pos, Blocks.DIRT_PATH.defaultBlockState(), 11);
                level.playSound(null, pos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
                if (player != null) held.hurtAndBreak(1, player, slot);
            }
            return InteractionResult.sidedSuccess(client);
        }

        return super.useOn(ctx);
    }

    private static boolean isPathable(BlockState state) {
        return state.is(Blocks.GRASS_BLOCK)
                || state.is(Blocks.DIRT)
                || state.is(Blocks.COARSE_DIRT)
                || state.is(Blocks.PODZOL)
                || state.is(Blocks.MYCELIUM)
                || state.is(Blocks.ROOTED_DIRT);
    }
}