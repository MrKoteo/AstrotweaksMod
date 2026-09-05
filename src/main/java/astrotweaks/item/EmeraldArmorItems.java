package astrotweaks.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;

public final class EmeraldArmorItems {
    private static final String MOD_ID = "astrotweaks";

    private static final ItemArmor.ArmorMaterial MATERIAL =
        EnumHelper.addArmorMaterial(
        "EMERALD",MOD_ID + ":emerald_", 25, new int[]{2, 5, 5, 3}, 15,
        SoundEvent.REGISTRY.getObject(new ResourceLocation("item.armor.equip_diamond")), 0.5F);

    public static final Item HELMET = new ItemArmor(MATERIAL, 0, EntityEquipmentSlot.HEAD)
            .setRegistryName(MOD_ID, "emerald_helmet")
            .setUnlocalizedName("emerald_helmet");
    public static final Item CHESTPLATE = new ItemArmor(MATERIAL, 0, EntityEquipmentSlot.CHEST)
            .setRegistryName(MOD_ID, "emerald_chestplate")
            .setUnlocalizedName("emerald_chestplate");
    public static final Item LEGGINGS = new ItemArmor(MATERIAL, 0, EntityEquipmentSlot.LEGS)
            .setRegistryName(MOD_ID, "emerald_leggings")
            .setUnlocalizedName("emerald_leggings");
    public static final Item BOOTS = new ItemArmor(MATERIAL, 0, EntityEquipmentSlot.FEET)
            .setRegistryName(MOD_ID, "emerald_boots")
            .setUnlocalizedName("emerald_boots");

    private EmeraldArmorItems() {
    }
}
