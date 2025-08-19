package com.nightmare.enderite.registry;

import com.nightmare.enderite.content.item.PaxelItem;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;

import static com.nightmare.enderite.EnderiteMod.MODID;

public final class EnderiteItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final Supplier<Item> RAW_ENDERITE = ITEMS.register("raw_enderite",
        () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.RARE)));
    public static final Supplier<Item> ENDERITE_GEM = ITEMS.register("enderite_gem",
        () -> new Item(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.EPIC)));
    public static final Supplier<Item> ENDERITE_INGOT = ITEMS.register("enderite_ingot",
        () -> new Item(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.EPIC)));

    public static final Supplier<Item> ENDERITE_SMITHING_TEMPLATE = ITEMS.register("enderite_smithing_template",
        () -> new Item(new Item.Properties().stacksTo(64).rarity(Rarity.RARE)));

    public static final Supplier<Item> ENDERITE_ORE_ITEM = ITEMS.register("enderite_ore",
            () -> new BlockItem(EnderiteBlocks.ENDERITE_ORE.get(), new Item.Properties()));

    public static final Supplier<SwordItem> ENDERITE_SWORD = ITEMS.register("enderite_sword", () ->
        new SwordItem(EnderiteTiers.ENDERITE,
            new Item.Properties().fireResistant().rarity(Rarity.EPIC).attributes(
                    SwordItem.createAttributes(EnderiteTiers.ENDERITE, 32, -1.0f))));

    public static final Supplier<PickaxeItem> ENDERITE_PICKAXE = ITEMS.register("enderite_pickaxe", () ->
        new PickaxeItem(EnderiteTiers.ENDERITE,
            new Item.Properties().fireResistant().rarity(Rarity.EPIC).attributes(
                DiggerItem.createAttributes(EnderiteTiers.ENDERITE, 1.0f, -2.8f))));

    public static final Supplier<AxeItem> ENDERITE_AXE = ITEMS.register("enderite_axe", () ->
        new AxeItem(EnderiteTiers.ENDERITE,
            new Item.Properties().fireResistant().rarity(Rarity.EPIC).attributes(
                AxeItem.createAttributes(EnderiteTiers.ENDERITE, 6.0f, -3.0f))));

    public static final Supplier<ShovelItem> ENDERITE_SHOVEL = ITEMS.register("enderite_shovel", () ->
        new ShovelItem(EnderiteTiers.ENDERITE,
            new Item.Properties().fireResistant().rarity(Rarity.EPIC).attributes(
                ShovelItem.createAttributes(EnderiteTiers.ENDERITE, 1.5f, -3.0f))));

    public static final Supplier<HoeItem> ENDERITE_HOE = ITEMS.register("enderite_hoe", () ->
        new HoeItem(EnderiteTiers.ENDERITE,
            new Item.Properties().fireResistant().rarity(Rarity.EPIC).attributes(
                HoeItem.createAttributes(EnderiteTiers.ENDERITE, 0.0f, 0.0f))));

    public static final Supplier<Item> ENDERITE_PAXEL = ITEMS.register("enderite_paxel", () ->
            new PaxelItem(EnderiteTiers.ENDERITE,
                    new Item.Properties().fireResistant().rarity(Rarity.EPIC)
                            .attributes(DiggerItem.createAttributes(EnderiteTiers.ENDERITE, 3.0f, -2.6f))));

    public static final Supplier<ArmorItem> ENDERITE_HELMET = ITEMS.register("enderite_helmet", () ->
        new ArmorItem(EnderiteArmorMaterials.ENDERITE, ArmorItem.Type.HELMET,
            new Item.Properties().fireResistant().durability(ArmorItem.Type.HELMET.getDurability(74))));

    public static final Supplier<ArmorItem> ENDERITE_CHESTPLATE = ITEMS.register("enderite_chestplate", () ->
        new ArmorItem(EnderiteArmorMaterials.ENDERITE, ArmorItem.Type.CHESTPLATE,
            new Item.Properties().fireResistant().durability(ArmorItem.Type.CHESTPLATE.getDurability(74))));

    public static final Supplier<ArmorItem> ENDERITE_LEGGINGS = ITEMS.register("enderite_leggings", () ->
        new ArmorItem(EnderiteArmorMaterials.ENDERITE, ArmorItem.Type.LEGGINGS,
            new Item.Properties().fireResistant().durability(ArmorItem.Type.LEGGINGS.getDurability(74))));

    public static final Supplier<ArmorItem> ENDERITE_BOOTS = ITEMS.register("enderite_boots", () ->
        new ArmorItem(EnderiteArmorMaterials.ENDERITE, ArmorItem.Type.BOOTS,
            new Item.Properties().fireResistant().durability(ArmorItem.Type.BOOTS.getDurability(74))));


    private EnderiteItems() {}
}
