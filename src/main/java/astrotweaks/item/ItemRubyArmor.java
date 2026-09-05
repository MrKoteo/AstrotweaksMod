package astrotweaks.item;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;

public final class ItemRubyArmor {
    private static final String MOD_ID = "astrotweaks";

    private static final ItemArmor.ArmorMaterial MATERIAL =
        EnumHelper.addArmorMaterial(
        "RUBY_", MOD_ID + ":ruby_", 34, new int[]{4, 6, 8, 4}, 16,
        SoundEvent.REGISTRY.getObject(new ResourceLocation("item.armor.equip_diamond")), 1.5F);

    public static final Item HELMET = new ItemArmor(MATERIAL, 0, EntityEquipmentSlot.HEAD)
            .setRegistryName(MOD_ID, "ruby_helmet")
            .setUnlocalizedName("ruby_helmet");
    public static final Item CHESTPLATE = new ItemArmor(MATERIAL, 0, EntityEquipmentSlot.CHEST)
            .setRegistryName(MOD_ID, "ruby_chestplate")
            .setUnlocalizedName("ruby_chestplate");
    public static final Item LEGGINGS = new ItemArmor(MATERIAL, 0, EntityEquipmentSlot.LEGS)
            .setRegistryName(MOD_ID, "ruby_leggings")
            .setUnlocalizedName("ruby_leggings");
    public static final Item BOOTS = new ItemArmor(MATERIAL, 0, EntityEquipmentSlot.FEET)
            .setRegistryName(MOD_ID, "ruby_boots")
            .setUnlocalizedName("ruby_boots");

    private ItemRubyArmor() {
    }
}