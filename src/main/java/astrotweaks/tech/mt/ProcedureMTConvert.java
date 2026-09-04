package astrotweaks.tech.mt;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Blocks;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Random;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections; 
import java.util.ArrayList;
import java.util.List;

import astrotweaks.item.ATItems;
import astrotweaks.item.ItemGavel;


import astrotweaks.ModVariables;


public class ProcedureMTConvert {
    private static final Map<Item, Item> UPGRADE_MAP = new HashMap<>();
    private static final Map<Item, Item> DOWNGRADE_MAP = new HashMap<>();
    private static boolean mapsInitialized = false;
    private static List<ItemStack> COPPER_PLATE_ORES = null;
    private static final Random RAND = new Random();

    private static final boolean Money_Can_Craft = ModVariables.Money_Can_Craft;

    public ProcedureMTConvert() {}

	private static boolean isCopperPlate(ItemStack stack) {
	    if (stack.isEmpty()) return false;
	    ensureCopperPlateOres();
	    // use OreDictionary.itemMatches
	    for (ItemStack ore : COPPER_PLATE_ORES) {
	        if (OreDictionary.itemMatches(ore, stack, false)) return true;
	    }
	    return false;
	}

    // Ensure the maps are filled when items are already registered -> avoid "null" keys
    private static void ensureMapsInitialized() {
        if (mapsInitialized) return;
        UPGRADE_MAP.put(ATItems.COPPER_COIN, ATItems.SILVER_COIN);
        UPGRADE_MAP.put(ATItems.SILVER_COIN, ATItems.GOLD_COIN);
        UPGRADE_MAP.put(ATItems.GOLD_COIN, ATItems.PLATINUM_COIN);
        UPGRADE_MAP.put(ATItems.PLATINUM_COIN, ATItems.DIAMANT_COIN);
        UPGRADE_MAP.put(ATItems.DIAMANT_COIN, ATItems.PALLADIUM_COIN);
        UPGRADE_MAP.put(ATItems.PALLADIUM_COIN, ATItems.ELUNITE_COIN);
        UPGRADE_MAP.put(ATItems.ELUNITE_COIN, ATItems.MYTHRIL_COIN);
        UPGRADE_MAP.put(ATItems.MYTHRIL_COIN, ATItems.ADAMANTIUM_COIN);
        UPGRADE_MAP.put(ATItems.ADAMANTIUM_COIN, ATItems.UNI_COIN);
        UPGRADE_MAP.put(ATItems.WOOD_COIN, ATItems.STONE_COIN);
        UPGRADE_MAP.put(ATItems.STONE_COIN, ATItems.COPPER_COIN);


        DOWNGRADE_MAP.put(ATItems.SILVER_COIN, ATItems.COPPER_COIN);
        DOWNGRADE_MAP.put(ATItems.GOLD_COIN, ATItems.SILVER_COIN);
        DOWNGRADE_MAP.put(ATItems.PLATINUM_COIN, ATItems.GOLD_COIN);
        DOWNGRADE_MAP.put(ATItems.DIAMANT_COIN, ATItems.PLATINUM_COIN);
        DOWNGRADE_MAP.put(ATItems.PALLADIUM_COIN, ATItems.DIAMANT_COIN);
        DOWNGRADE_MAP.put(ATItems.ELUNITE_COIN, ATItems.PALLADIUM_COIN);
        DOWNGRADE_MAP.put(ATItems.MYTHRIL_COIN, ATItems.ELUNITE_COIN);
        DOWNGRADE_MAP.put(ATItems.ADAMANTIUM_COIN, ATItems.MYTHRIL_COIN);
        DOWNGRADE_MAP.put(ATItems.UNI_COIN, ATItems.ADAMANTIUM_COIN);
        DOWNGRADE_MAP.put(ATItems.STONE_COIN, ATItems.WOOD_COIN);
        DOWNGRADE_MAP.put(ATItems.COPPER_COIN, ATItems.STONE_COIN);


        mapsInitialized = true;
    }

    // Helper that safely returns ItemStack.EMPTY instead of null
    private static ItemStack safeGetSlotItemStack(TileEntity inv, int slot) {
        if (inv instanceof TileEntityLockableLoot) {
            ItemStack s = ((TileEntityLockableLoot) inv).getStackInSlot(slot);
            return s == null ? ItemStack.EMPTY : s;
        }
        return ItemStack.EMPTY;
    }
	/*
    private static int getSlotAmount(World world, BlockPos pos, int slot) {
        TileEntity inv = world.getTileEntity(pos);
        ItemStack stack = safeGetStack(inv, slot);
        return stack.isEmpty() ? 0 : stack.getCount();
    }*/
	/*
    private static ItemStack getSlotItemStack(World world, BlockPos pos, int slot) {
        TileEntity inv = world.getTileEntity(pos);
        return safeGetStack(inv, slot);
    }*/

    private static void setSlotItem(TileEntity te, int slot, ItemStack stack) {
        //TileEntity inv = world.getTileEntity(pos);
        if (te instanceof TileEntityLockableLoot) {
            ((TileEntityLockableLoot) te).setInventorySlotContents(slot, stack);
        }
    }
    private static void decreaseSlot(TileEntity te, int slot, int amount) {
        //TileEntity inv = world.getTileEntity(pos);
        if (te instanceof TileEntityLockableLoot) {
            ((TileEntityLockableLoot) te).decrStackSize(slot, amount);
        }
    }
    private static void damageGavel(TileEntity te, int slot) {
        //TileEntity inv = world.getTileEntity(pos);
        if (te instanceof TileEntityLockableLoot) {
            ItemStack stack = ((TileEntityLockableLoot) te).getStackInSlot(slot);
            if (stack != null && !stack.isEmpty()) {
                // Try to damage; when broken shrink the stack (same logic as your original)
                if (stack.attemptDamageItem(1, RAND, null)) {
                    stack.shrink(1);
                    stack.setItemDamage(0);
                }
                ((TileEntityLockableLoot) te).setInventorySlotContents(slot, stack);
            }
        }
    }
    public static void executeProcedure(int x, int y, int z, World world) {

        BlockPos pos = new BlockPos(x, y, z);

        // lazy init the maps here (after items have been registered)
        ensureMapsInitialized();

		TileEntity te = world.getTileEntity(pos);
		if (!(te instanceof TileEntityLockableLoot)) return;

		ItemStack gavelStack = safeGetSlotItemStack(te, 4);
		if (gavelStack.isEmpty() || gavelStack.getItem() != ItemGavel.block) return;

		boolean did = false;
		if (canProcessUpgrade(te) && processUpgradeConversion(te)) {
		    did = true;
		} else if (canProcessDowngrade(te) && processDowngradeConversion(te)) {
		    did = true;
		}

		if (did) damageGavel(te, 4);
    }

	private static void ensureCopperPlateOres() {
	    if (COPPER_PLATE_ORES != null) return;
        if (!Money_Can_Craft) return;
	    List<ItemStack> ores = OreDictionary.getOres("plateCopper");

	    COPPER_PLATE_ORES = ores == null ? Collections.emptyList() : new ArrayList<>(ores);
	}


    private static boolean canProcessUpgrade(TileEntity te) {
        //ItemStack slot0Stack = safeGetSlotItemStack(world, pos, 0);

		ItemStack slot0Stack = safeGetSlotItemStack(te, 0);
		ItemStack slot1Stack = safeGetSlotItemStack(te, 1);
		if (slot0Stack.isEmpty()) return false;

        if (isCopperPlate(slot0Stack)) { // check for CopperPlate
            if (slot1Stack.isEmpty()) return true;
            if (slot1Stack.getItem() != ATItems.COPPER_COIN) return false;
            return slot1Stack.getCount() + 1 <= slot1Stack.getMaxStackSize();
        }

	    Item inputItem = slot0Stack.getItem();
	    Item outputItem = UPGRADE_MAP.get(inputItem);
	    if (outputItem == null) return false;

        int required = 10;
        if (slot0Stack.getCount() < required) return false;


        if (slot1Stack.isEmpty()) return true;
        if (slot1Stack.getItem() != outputItem) return false;
        return slot1Stack.getCount() + 1 <= slot1Stack.getMaxStackSize();
    }
    private static boolean processUpgradeConversion(TileEntity te) {
        ItemStack slot0Stack = safeGetSlotItemStack(te, 0);
        if (slot0Stack.isEmpty()) return false;

		if (isCopperPlate(slot0Stack)) {
            // rem 1 copper plate
            decreaseSlot(te, 0, 1);
            
            // add 1 copper coin
            ItemStack outStack = safeGetSlotItemStack(te, 1);
            int newCount = (outStack.isEmpty() ? 0 : outStack.getCount()) + 1;
            int max = new ItemStack(ATItems.COPPER_COIN).getMaxStackSize();
            if (newCount > max) newCount = max;
            setSlotItem(te, 1, new ItemStack(ATItems.COPPER_COIN, newCount));
            return true;
        }
        
        Item inputItem = slot0Stack.getItem();
        Item outputItem = UPGRADE_MAP.get(inputItem);
        if (outputItem == null) return false;

        int removeCount = 10;
        decreaseSlot(te, 0, removeCount);

        ItemStack outStack = safeGetSlotItemStack(te, 1);
        int newCount = (outStack.isEmpty() ? 0 : outStack.getCount()) + 1;
        int max = (outStack.isEmpty() ? outputItem.getItemStackLimit(new ItemStack(outputItem, 1)) : outStack.getMaxStackSize());
        if (newCount > max) newCount = max;
        //setSlotItem(te, 1, new ItemStack(outputItem, newCount));
		if (!outStack.isEmpty() && outStack.getItem() == outputItem) {
		    outStack.setCount(newCount);
		    setSlotItem(te, 1, outStack);
		} else {
		    setSlotItem(te, 1, new ItemStack(outputItem, newCount));
		}

        return true;
    }

    private static boolean canProcessDowngrade(TileEntity te) {
        ItemStack slot2Stack = safeGetSlotItemStack(te, 2);
        ItemStack slot3Stack = safeGetSlotItemStack(te, 3);
        if (slot2Stack.isEmpty()) return false;

        Item inputItem = slot2Stack.getItem();
        Item outputItem = DOWNGRADE_MAP.get(inputItem);
        if (outputItem == null) return false;

        //ItemStack slot3Stack = safeGetSlotItemStack(world, pos, 3);
        if (slot3Stack.isEmpty()) return true;
        if (slot3Stack.getItem() != outputItem) return false;
        return slot3Stack.getCount() + 10 <= slot3Stack.getMaxStackSize();
    }
    private static boolean processDowngradeConversion(TileEntity te) {
        ItemStack slot2Stack = safeGetSlotItemStack(te, 2);
        if (slot2Stack.isEmpty()) return false;
        Item inputItem = slot2Stack.getItem();
        Item outputItem = DOWNGRADE_MAP.get(inputItem);
        if (outputItem == null) return false;

        decreaseSlot(te, 2, 1);

        ItemStack outStack = safeGetSlotItemStack(te, 3);
        int newCount = (outStack.isEmpty() ? 0 : outStack.getCount()) + 10;
        int max = (outStack.isEmpty() ? outputItem.getItemStackLimit(new ItemStack(outputItem, 1)) : outStack.getMaxStackSize());
        if (newCount > max) newCount = max;
        //setSlotItem(te, 3, new ItemStack(outputItem, newCount));
		if (!outStack.isEmpty() && outStack.getItem() == outputItem) {
		    outStack.setCount(newCount);
		    setSlotItem(te, 3, outStack);
		} else {
		    setSlotItem(te, 3, new ItemStack(outputItem, newCount));
		}

        return true;
    }
}