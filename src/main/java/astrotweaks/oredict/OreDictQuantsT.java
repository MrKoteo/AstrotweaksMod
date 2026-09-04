package astrotweaks.oredict;

import net.minecraftforge.oredict.OreDictionary;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;

import astrotweaks.item.*;



public class OreDictQuantsT {
    public OreDictQuantsT() {}

    public static void init() {
        registerQuantItems("singleQuant",
            ItemAlphaQuant.block, ItemBetaQuant.block, ItemGammaQuant.block, ItemDeltaQuant.block,
            ItemStrangeQuant.block,
            ATItems.NULL_QUANT
        );

        //registerQuantItems("doubleQuant",
        //);

        registerQuantItems("tripleQuant",

			ATItems.TRIPLE_ABD_QUANT,
            ATItems.TRIPLE_ABG_QUANT,
            ATItems.TRIPLE_AGD_QUANT,
			ATItems.TRIPLE_BGD_QUANT
        );

        registerQuantItems("quadQuant",

            ATItems.QUAD_AAAA_QUANT,
            ATItems.QUAD_ABGD_QUANT,
            ATItems.QUAD_BBBB_QUANT,
            ATItems.QUAD_GGGG_QUANT,
            ATItems.QUAD_DDDD_QUANT
        );
    }

    private static void registerQuantItems(String oreName, Item... items) {
        for (Item item : items) {
            OreDictionary.registerOre(oreName, new ItemStack(item, 1));
        }
    }
}