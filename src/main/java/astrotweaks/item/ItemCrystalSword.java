package astrotweaks.item;

import java.util.HashMap;
import java.util.Set;

import com.google.common.collect.Multimap;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.common.util.EnumHelper;

import astrotweaks.creativetab.ATCreativeTabs;

public final class ItemCrystalSword {
    public static final Item SWORD = new ItemSword(EnumHelper.addToolMaterial("CRYSTAL_SWORD", 4, 15000, 20f, 36f, 5)) {
        @Override
        public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot slot) {
            Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(slot);
            if (slot == EntityEquipmentSlot.MAINHAND) {
                multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(),
                        new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double) this.getAttackDamage(), 0));
                multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(),
                        new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -1.9, 0));
            }
            return multimap;
        }

        public Set<String> getToolClasses(ItemStack stack) {
            HashMap<String, Integer> ret = new HashMap<String, Integer>();
            ret.put("sword", 4);
            return ret.keySet();
        }
    }.setRegistryName("astrotweaks", "crystal_sword").setUnlocalizedName("crystal_sword").setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);

    private ItemCrystalSword() {}
}