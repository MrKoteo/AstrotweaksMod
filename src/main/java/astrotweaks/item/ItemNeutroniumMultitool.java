package astrotweaks.item;

import com.google.common.collect.Multimap;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import astrotweaks.creativetab.ATCreativeTabs;

public final class ItemNeutroniumMultitool {
    public static final Item MULTITOOL = new ItemToolCustom() {
    }.setRegistryName("astrotweaks", "neutronium_multitool").setUnlocalizedName("neutronium_multitool").setCreativeTab(ATCreativeTabs.ASTRO_TWEAKS_CT);

    private ItemNeutroniumMultitool() {}
    private static class ItemToolCustom extends Item {
        protected ItemToolCustom() {
            setMaxDamage(100000);
            setMaxStackSize(1);
        }

        @Override
        public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
            Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
            if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
                multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(),
                        new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", 199f, 0));
                multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", -2.7, 0));
            }
            return multimap;
        }

        @Override
        public boolean canHarvestBlock(IBlockState blockIn) {
            return true;
        }

        @Override
        public float getDestroySpeed(ItemStack par1ItemStack, IBlockState par2Block) {
            return 80f;
        }

        @Override
        public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
            stack.damageItem(1, attacker);
            return true;
        }

        @Override
        public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
            stack.damageItem(1, entityLiving);
            return true;
        }

        @Override
        public boolean isFull3D() {
            return true;
        }

        @Override
        public int getItemEnchantability() {
            return 1;
        }
    }
}