package astrotweaks.item;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;

public final class ItemBrassArmor {
    private static final String MOD_ID = "astrotweaks";

    private static final ItemArmor.ArmorMaterial MATERIAL =
        EnumHelper.addArmorMaterial(
        "BRASS_ARMOR", MOD_ID + ":brass_", 15, new int[]{2, 5, 4, 2}, 12,
        SoundEvent.REGISTRY.getObject(new ResourceLocation("item.armor.equip_iron")), 0.5F);

    public static final Item HELMET = new ItemArmor(MATERIAL, 0, EntityEquipmentSlot.HEAD)
            .setRegistryName(MOD_ID, "brass_helmet")
            .setUnlocalizedName("brass_helmet");
    public static final Item CHESTPLATE = new ItemArmor(MATERIAL, 0, EntityEquipmentSlot.CHEST)
            .setRegistryName(MOD_ID, "brass_chestplate")
            .setUnlocalizedName("brass_chestplate");
    public static final Item LEGGINGS = new ItemArmor(MATERIAL, 0, EntityEquipmentSlot.LEGS)
            .setRegistryName(MOD_ID, "brass_leggings")
            .setUnlocalizedName("brass_leggings");
    public static final Item BOOTS = new ItemArmor(MATERIAL, 0, EntityEquipmentSlot.FEET)
            .setRegistryName(MOD_ID, "brass_boots")
            .setUnlocalizedName("brass_boots");

    private ItemBrassArmor() {
    }
}