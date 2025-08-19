package com.nightmare.enderite.content.event;

import com.nightmare.enderite.EnderiteMod;
import com.nightmare.enderite.registry.EnderiteItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;            // Shift detection (client)
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;                 // Client-only subscriber
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@EventBusSubscriber(modid = EnderiteMod.MODID, value = Dist.CLIENT)
public class TooltipHandler {

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();

        boolean isEnderiteArmor =
                stack.is(EnderiteItems.ENDERITE_HELMET.get())
                        || stack.is(EnderiteItems.ENDERITE_CHESTPLATE.get())
                        || stack.is(EnderiteItems.ENDERITE_LEGGINGS.get())
                        || stack.is(EnderiteItems.ENDERITE_BOOTS.get());
        if (!isEnderiteArmor) return;

        if (Screen.hasShiftDown()) {
            event.getToolTip().add(Component.translatable("tooltip.enderite.full_set")
                    .withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.BOLD));
            event.getToolTip().add(Component.translatable("tooltip.enderite.bonus.void").withStyle(ChatFormatting.GRAY));
            event.getToolTip().add(Component.translatable("tooltip.enderite.bonus.evasion").withStyle(ChatFormatting.GRAY));
            event.getToolTip().add(Component.translatable("tooltip.enderite.bonus.gaze").withStyle(ChatFormatting.GRAY));
            event.getToolTip().add(Component.translatable("tooltip.enderite.bonus.magic").withStyle(ChatFormatting.GRAY));
            event.getToolTip().add(Component.translatable("tooltip.enderite.bonus.fall").withStyle(ChatFormatting.GRAY));
            event.getToolTip().add(Component.translatable("tooltip.enderite.bonus.flight").withStyle(ChatFormatting.GRAY));
            event.getToolTip().add(Component.translatable("tooltip.enderite.bonus.fire").withStyle(ChatFormatting.GRAY));
        } else {
            event.getToolTip().add(Component.translatable("tooltip.enderite.hold_shift")
                    .withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
        }
    }
}