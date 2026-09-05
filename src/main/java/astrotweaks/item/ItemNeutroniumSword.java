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

public final class ItemNeutroniumSword {
    public static final Item SWORD = new ItemSword(EnumHelper.addToolMaterial("NEUTRONIUM_SWORD", 6, 100000, 48f, 196f, 1)) {
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
            ret.put("sword", 6);
            return ret.keySet();
        }
    }.setRegistryName("astrotweaks", "neutronium_sword").setUnlocalizedName("neutronium_sword").setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);

    private ItemNeutroniumSword() {}
}