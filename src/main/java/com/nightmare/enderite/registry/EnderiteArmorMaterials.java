package com.nightmare.enderite.registry;

import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.EnumMap;
import java.util.List;

import static com.nightmare.enderite.EnderiteMod.MODID;

public final class EnderiteArmorMaterials {
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(
            net.minecraft.core.registries.Registries.ARMOR_MATERIAL, MODID);

    public static final Holder<ArmorMaterial> ENDERITE = ARMOR_MATERIALS.register("enderite", () -> new ArmorMaterial(
            Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.HELMET, 10);
                map.put(ArmorItem.Type.CHESTPLATE, 15);
                map.put(ArmorItem.Type.LEGGINGS, 13);
                map.put(ArmorItem.Type.BOOTS, 9);
            }),
            50,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            () -> Ingredient.of(EnderiteItems.ENDERITE_INGOT.get()),
            List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(MODID, "enderite"))),
            20.0f,
            0.25f
    ));

    private EnderiteArmorMaterials() {}
}
